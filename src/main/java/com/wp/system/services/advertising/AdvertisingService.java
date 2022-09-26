package com.wp.system.services.advertising;

import com.wp.system.entity.advertising.Advertising;
import com.wp.system.entity.advertising.AdvertisingFile;
import com.wp.system.entity.advertising.AdvertisingView;
import com.wp.system.exception.ServiceException;
import com.wp.system.utils.FileUploadUtil;
import com.wp.system.repository.advertising.AdvertisingRepository;
import com.wp.system.request.advertising.AddFileToAdvertisingRequest;
import com.wp.system.request.advertising.CreateAdvertisingRequest;
import com.wp.system.request.advertising.RemoveFileFromAdvertisingRequest;
import com.wp.system.request.advertising.UpdateAdvertisingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("Advertising Service")
public class AdvertisingService {
    @Autowired
    private AdvertisingRepository advertisingRepository;

    public Advertising createAdvertising(CreateAdvertisingRequest request) {
        Advertising advertising = new Advertising();

        advertising.setTitle(request.getTitle());
        advertising.setContent(request.getContent());
        advertising.setSubTitle(request.getSubTitle());

        advertisingRepository.save(advertising);

        return advertising;
    }

    public Set<AdvertisingView> getAdvertisingViews(UUID id) {
        Advertising advertising = getAdvertisingById(id);

        return advertising.getViews();
    }

    public Set<AdvertisingView> getAdvertisingViewsByPeriod(UUID id,
                                                            Instant startDate,
                                                            Instant endDate) {
        Advertising advertising = getAdvertisingById(id);

        return advertising.getViews().stream().filter(item -> item.getCreateAt().isBefore(endDate) && item.getCreateAt().isAfter(startDate)).collect(Collectors.toSet());
    }

    public List<Advertising> getAllAdvertising() {
        return advertisingRepository.findAll();
    }

    public Advertising getAdvertisingById(UUID id) {
        return advertisingRepository.findById(id).orElseThrow(() -> {
            throw new ServiceException("Advertising not found", HttpStatus.NOT_FOUND);
        });
    }

    @Transactional
    public Advertising removeAdvertising(UUID id) {
        Advertising advertising = getAdvertisingById(id);

        advertisingRepository.delete(advertising);

        return advertising;
    }

    public Page<Advertising> getAdvertisingByPage(int page, int pageSize) {
        return advertisingRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Advertising updateAdvertising(UpdateAdvertisingRequest request, UUID id) {
        Advertising advertising = getAdvertisingById(id);

        if(request.getContent() != null)
            advertising.setContent(request.getContent());

        if(request.getSubTitle() != null)
            advertising.setSubTitle(request.getSubTitle());

        if(request.getTitle() != null)
            advertising.setTitle(request.getTitle());

        advertisingRepository.save(advertising);

        return advertising;
    }

    public Advertising addViewToAdvertising(UUID id) {
        Advertising advertising = getAdvertisingById(id);

        advertising.addView(new AdvertisingView());

        advertisingRepository.save(advertising);

        return advertising;
    }

    @Transactional
    public Advertising addFileToAdvertising(AddFileToAdvertisingRequest request, UUID id) {
        try {
            Advertising advertising = getAdvertisingById(id);

            String[] splittedFileName = request.getFile().getOriginalFilename().split("\\.");

            String fileName = UUID.randomUUID() + "." + splittedFileName[splittedFileName.length - 1];

            String uploadDir = "ads/";

            FileUploadUtil.saveFile(uploadDir, fileName, request.getFile());

            advertising.addFile(new AdvertisingFile(fileName));

            advertisingRepository.save(advertising);

            return advertising;
        } catch (Exception e) {
            throw new ServiceException("Upload error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public Advertising removeFileFromAdvertising(UUID fileId, UUID id) {
        Advertising advertising = getAdvertisingById(id);

        AdvertisingFile file = null;

        for (AdvertisingFile f : advertising.getFiles())
            if(f.getId().equals(fileId))
                file = f;

        if(file == null)
            throw new ServiceException("File with given ID not found", HttpStatus.NOT_FOUND);

        Path path = FileSystems.getDefault().getPath("ads/" + file.getPath());

        try {
            Files.deleteIfExists(path);
        } catch (Exception e) {
            throw new ServiceException("Error on remove file", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        advertising.removeFile(file);

        advertisingRepository.save(advertising);

        return advertising;
    }
}
