package com.wp.system.services.image;

import com.wp.system.entity.image.SystemImage;
import com.wp.system.exception.ServiceException;
import com.wp.system.utils.AdminChecker;
import com.wp.system.utils.FileUploadUtil;
import com.wp.system.utils.SystemImageTag;
import com.wp.system.repository.image.SystemImageRepository;
import com.wp.system.request.image.UploadImageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    private SystemImageRepository systemImageRepository;

    @Autowired
    private AdminChecker adminChecker;

    public List<SystemImage> getImagesByTag(SystemImageTag tag) {
        return systemImageRepository.findByTag(tag);
    }

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
            throw new ServiceException("Error on send image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public SystemImage getImageById(UUID id) {
        Optional<SystemImage> image = systemImageRepository.findById(id);

        if(image.isEmpty())
            throw new ServiceException("Image not found", HttpStatus.NOT_FOUND);

        return image.get();
    }

    @Transactional
    public SystemImage removeImage(UUID id) {
        SystemImage image = this.getImageById(id);

        Path path = FileSystems.getDefault().getPath(image.getPath());

        try {
            Files.deleteIfExists(path);
        } catch (Exception e) {
            throw new ServiceException("Error on remove file", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        systemImageRepository.delete(image);

        return image;
    }

    public SystemImage uploadImage(UploadImageRequest request) {
        try {
            String fileName = Instant.now().toString() + "-" + UUID.randomUUID() + "." + request.getFile().getOriginalFilename().split("\\.")[1];

            String uploadDir = "images/";

            FileUploadUtil.saveFile(uploadDir, fileName, request.getFile());

            SystemImage image = new SystemImage(fileName, uploadDir + fileName, request.getFile().getContentType());

            image.setTag(request.getTag());

            systemImageRepository.save(image);

            return image;
        } catch (Exception e) {
            throw new ServiceException("Upload error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
