package com.wp.system.controller.advertising;

import com.wp.system.dto.advertising.AdvertisingDTO;
import com.wp.system.dto.advertising.AdvertisingViewDTO;
import com.wp.system.dto.bill.BillDTO;
import com.wp.system.dto.bill.BillTransactionDTO;
import com.wp.system.exception.ServiceException;
import com.wp.system.request.advertising.AddFileToAdvertisingRequest;
import com.wp.system.request.advertising.CreateAdvertisingRequest;
import com.wp.system.request.advertising.RemoveFileFromAdvertisingRequest;
import com.wp.system.request.advertising.UpdateAdvertisingRequest;
import com.wp.system.request.bill.CreateBillRequest;
import com.wp.system.request.bill.DepositBillRequest;
import com.wp.system.request.bill.EditBillRequest;
import com.wp.system.request.bill.WithdrawBillRequest;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.advertising.AdvertisingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.util.function.Tuple2;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Advertising API")
@RequestMapping("/api/v1/advertising")
@SecurityRequirement(name = "Bearer")
public class AdvertisingController {
    @Autowired
    private AdvertisingService advertisingService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADVERTISING_CREATE', 'ADVERTISING_FULL')")
    @Operation(summary = "Создание рекламы")
    @PostMapping("/")
    public ResponseEntity<ServiceResponse<AdvertisingDTO>> createAdvertising(
            @Valid
            @RequestBody
                    CreateAdvertisingRequest request) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.CREATED.value(), new AdvertisingDTO(this.advertisingService.createAdvertising(request)), "Advertising created"), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADVERTISING_UPDATE', 'ADVERTISING_FULL')")
    @Operation(summary = "Изменение рекламы")
    @PatchMapping("/{advertisingId}")
    public ResponseEntity<ServiceResponse<AdvertisingDTO>> updateAdvertising(@Valid @RequestBody UpdateAdvertisingRequest request, @PathVariable UUID advertisingId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new AdvertisingDTO(this.advertisingService.updateAdvertising(request, advertisingId)), "Advertising updated"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADVERTISING_UPDATE', 'ADVERTISING_FULL')")
    @Operation(summary = "Прикрепление файла к рекламе")
    @PatchMapping(path = "/file/{advertisingId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ServiceResponse<AdvertisingDTO>> addFileToAdvertising(@Valid @ModelAttribute AddFileToAdvertisingRequest request, @PathVariable UUID advertisingId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new AdvertisingDTO(this.advertisingService.addFileToAdvertising(request, advertisingId)), "Advertising updated"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADVERTISING_UPDATE', 'ADVERTISING_FULL')")
    @Operation(summary = "Открепление файла от рекламе")
    @DeleteMapping("/file/{advertisingId}/{fileId}")
    public ResponseEntity<ServiceResponse<AdvertisingDTO>> removeFileFromAdvertising(@Valid @PathVariable UUID fileId, @PathVariable UUID advertisingId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new AdvertisingDTO(this.advertisingService.removeFileFromAdvertising(fileId, advertisingId)), "Advertising updated"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADVERTISING_GET', 'ADVERTISING_FULL')")
    @Operation(summary = "Удаление рекламы")
    @DeleteMapping("/{advertisingId}")
    public ResponseEntity<ServiceResponse<AdvertisingDTO>> removeAdvertising(@PathVariable UUID advertisingId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new AdvertisingDTO(this.advertisingService.removeAdvertising(advertisingId)), "Advertising removed"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADVERTISING_GET', 'ADVERTISING_FULL')")
    @Operation(summary = "Получение всей рекламы")
    @GetMapping("/")
    public ResponseEntity<ServiceResponse<List<AdvertisingDTO>>> getAdvertising() {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.advertisingService.getAllAdvertising().stream().map(AdvertisingDTO::new).collect(Collectors.toList()), "Advertising returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADVERTISING_UPDATE', 'ADVERTISING_FULL')")
    @Operation(summary = "Добавление просмотра к рекламе")
    @GetMapping("/view/{advertisingId}")
    public ResponseEntity<ServiceResponse<AdvertisingDTO>> addViewToAdvertising(@PathVariable UUID advertisingId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new AdvertisingDTO(advertisingService.addViewToAdvertising(advertisingId)), "Advertising returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADVERTISING_GET', 'ADVERTISING_FULL')")
    @Operation(summary = "Получение просмотров рекламы")
    @GetMapping("/views/{advertisingId}")
    public ResponseEntity<ServiceResponse<List<AdvertisingViewDTO>>> getAdvertisingViews(@PathVariable UUID advertisingId) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.advertisingService.getAdvertisingViews(advertisingId).stream().map(AdvertisingViewDTO::new).collect(Collectors.toList()), "Advertising returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADVERTISING_GET', 'ADVERTISING_FULL')")
    @Operation(summary = "Получение просмотров рекламы за период")
    @GetMapping("/views/period/{advertisingId}")
    public ResponseEntity<ServiceResponse<List<AdvertisingViewDTO>>> getAdvertisingViewsPeriod(@PathVariable UUID advertisingId,
                                                                                               @RequestParam Instant startDate,
                                                                                               @RequestParam Instant endDate) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), this.advertisingService.getAdvertisingViewsByPeriod(advertisingId, startDate, endDate).stream().map(AdvertisingViewDTO::new).collect(Collectors.toList()), "Advertising returned"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Получение контента рекламы")
    @GetMapping(value = "/content/{contentName}")
    public ResponseEntity<byte[]> getImageContent(
            @PathVariable
                    String contentName) {
        try {
            File image = new File("ads/" + contentName);

            byte[] bytes = Files.readAllBytes(image.toPath());

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.valueOf(Files.probeContentType(image.toPath())));

            return ResponseEntity.ok().headers(httpHeaders).body(bytes);
        } catch (Exception e) {
            throw new ServiceException("Error on send image", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
