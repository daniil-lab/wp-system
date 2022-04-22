package com.wp.system.controller.admin;

import com.wp.system.dto.bill.BillDTO;
import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.dto.user.UserDTO;
import com.wp.system.request.user.EditUserRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.admin.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Admin API")
@RequestMapping("/api/v1/admin")
@SecurityRequirement(name = "Bearer")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/user")
    public ResponseEntity<ServiceResponse<PagingResponse<UserDTO>>> getUsers(
            @RequestParam
                int page,
            @RequestParam
                int pageSize
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getPagedUsers(page, pageSize)), HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> removeUser(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(adminService.removeUser(userId))), HttpStatus.OK);
    }

    @GetMapping("/user/bills/{userId}")
    public ResponseEntity<ServiceResponse<List<BillDTO>>> getUserBills(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getUserBills(userId).stream().map(BillDTO::new).collect(Collectors.toList())), HttpStatus.OK);
    }

    @PatchMapping("/user/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> updateUser(
            @RequestBody
            @Valid
                    EditUserRequest request,
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(adminService.updateUser(request, userId))), HttpStatus.OK);
    }

    @GetMapping("/user/categories/{userId}")
    public ResponseEntity<ServiceResponse<List<CategoryDTO>>> getUserCategories(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getUserCategories(userId).stream().map(CategoryDTO::new).collect(Collectors.toList())), HttpStatus.OK);
    }
}
