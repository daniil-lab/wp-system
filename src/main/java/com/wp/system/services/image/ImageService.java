package com.wp.system.services.image;

import com.wp.system.dto.image.SystemImageDTO;
import com.wp.system.entity.image.SystemImage;
import com.wp.system.exception.ServiceException;
import com.wp.system.exception.image.ImageErrorCode;
import com.wp.system.exception.system.SystemErrorCode;
import com.wp.system.other.FileUploadUtil;
import com.wp.system.other.SystemImageTag;
import com.wp.system.repository.image.SystemImageRepository;
import com.wp.system.request.image.UploadImageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.persistence.Entity;
import javax.persistence.Tuple;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    private SystemImageRepository systemImageRepository;

    public List<SystemImage> getAllImages() {
        Iterable<SystemImage> foundImages = systemImageRepository.findAll();
        List<SystemImage> images = new ArrayList<>();

        foundImages.forEach(images::add);

        return images;
    }


    public Tuple2<byte[], String> getImageContent(String imageName) {
        try {
            File image = new File("images/" + imageName);

            byte[] bytes = Files.readAllBytes(image.toPath());

            return Tuples.of(bytes, Files.probeContentType(image.toPath()));
        } catch(Exception e) {
            throw new ServiceException(ImageErrorCode.SEND_ERROR);
        }
    }

    public SystemImage getImageById(UUID id) {
        Optional<SystemImage> image = systemImageRepository.findById(id);

        if(image.isEmpty())
            throw new ServiceException(ImageErrorCode.NOT_FOUND);

        return image.get();
    }

    @Transactional
    public SystemImage removeImage(UUID id) {
        SystemImage image = this.getImageById(id);

        Path path = FileSystems.getDefault().getPath(image.getPath());

        try {
            Files.deleteIfExists(path);
        } catch (Exception ignored) {

        }

        systemImageRepository.delete(image);

        return image;
    }

    public SystemImage uploadImage(UploadImageRequest request) {
        try {
            String fileName = request.getFile().getOriginalFilename().split("\\.")[0]
                    + "-" + UUID.randomUUID() + "." + request.getFile().getOriginalFilename().split("\\.")[1];

            String uploadDir = "images/";

            FileUploadUtil.saveFile(uploadDir, fileName, request.getFile());

            SystemImage image = new SystemImage(fileName, uploadDir + fileName, request.getFile().getContentType());

            image.setTag(request.getTag());

            systemImageRepository.save(image);

            return image;
        } catch (Exception e) {
            throw new ServiceException(ImageErrorCode.UPLOAD_ERROR);
        }
    }
}
