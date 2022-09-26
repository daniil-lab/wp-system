package com.wp.system.controller.image;

import com.wp.system.controller.DocumentedRestController;
import com.wp.system.dto.image.SystemImageDTO;
import com.wp.system.utils.SystemImageTag;
import com.wp.system.request.image.UploadImageRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.image.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.util.function.Tuple2;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@Tag(name = "Image Store API")
@RequestMapping("/api/v1/image")
@SecurityRequirement(name = "Bearer")
public class ImageController extends DocumentedRestController {

    @Autowired
    private ImageService imageService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('IMAGES_CREATE', 'IMAGES_FULL')")
    @Operation(summary = "Загрузка изображения в общее хранилище")
    @SecurityRequirement(name = "Bearer")
    @PostMapping(value = "/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ServiceResponse<SystemImageDTO>> uploadImage(
            @Valid
            @ModelAttribute
                UploadImageRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new SystemImageDTO(imageService.uploadImage(request)), "Image saved"), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('IMAGES_GET', 'IMAGES_FULL')")
    @Operation(summary = "Получение данных изображения")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/{imageId}")
    public ResponseEntity<ServiceResponse<SystemImageDTO>> getImageById(
            @PathVariable
                    UUID imageId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SystemImageDTO(imageService.getImageById(imageId)), "Image data returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('IMAGES_GET', 'IMAGES_FULL')")
    @Operation(summary = "Получение изображений по тегу")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/tag/{imageTag}")
    public ResponseEntity<ServiceResponse<List<SystemImageDTO>>> getImagesByTag(
            @PathVariable
                    SystemImageTag imageTag) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), imageService.getImagesByTag(imageTag).stream().map(SystemImageDTO::new).collect(Collectors.toList()), "Image data returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('IMAGES_GET', 'IMAGES_FULL')")
    @Operation(summary = "Получение данных всех изображений")
    @SecurityRequirement(name = "Bearer")
    @GetMapping(value = "/")
    public ResponseEntity<ServiceResponse<List<SystemImageDTO>>> getAllImages() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), imageService.getAllImages().stream().map(SystemImageDTO::new).collect(Collectors.toList()), "Image data returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Получение изображения")
    @GetMapping(value = "/content/{imageName}")
    public ResponseEntity<byte[]> getImageContent(
            @PathVariable
                    String imageName) {
        Tuple2<byte[], String> data = imageService.getImageContent(imageName);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(data.getT2()));

        return ResponseEntity.ok().headers(httpHeaders).body(data.getT1());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('IMAGES_DELETE', 'IMAGES_FULL')")
    @Operation(summary = "Удаление изображения")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping(value = "/{imageId}")
    public ResponseEntity<ServiceResponse<SystemImageDTO>> removeImage (
            @PathVariable
                    UUID imageId) {

        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SystemImageDTO(imageService.removeImage(imageId)), "Image removed"), HttpStatus.OK);
    }
}
