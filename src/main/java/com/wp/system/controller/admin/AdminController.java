package com.wp.system.controller.admin;

import com.wp.system.dto.activity.ActivityDTO;
import com.wp.system.dto.bill.BillDTO;
import com.wp.system.dto.bill.BillTransactionDTO;
import com.wp.system.dto.category.CategoryDTO;
import com.wp.system.dto.loyalty.LoyaltyCardDTO;
import com.wp.system.dto.sber.SberCardDTO;
import com.wp.system.dto.sber.SberTransactionDTO;
import com.wp.system.dto.subscription.SubscriptionDTO;
import com.wp.system.dto.tinkoff.TinkoffCardDTO;
import com.wp.system.dto.tinkoff.TinkoffTransactionDTO;
import com.wp.system.dto.tochka.TochkaCardDTO;
import com.wp.system.dto.tochka.TochkaTransactionDTO;
import com.wp.system.dto.user.UserDTO;
import com.wp.system.request.admin.ExtendSubscriptionRequest;
import com.wp.system.request.user.EditUserRequest;
import com.wp.system.response.PagingResponse;
import com.wp.system.response.ServiceResponse;
import com.wp.system.services.admin.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_GET_USER', 'ADMIN_FULL')")
    @GetMapping("/user")
    public ResponseEntity<ServiceResponse<PagingResponse<UserDTO>>> getUsers(
            @RequestParam
                int page,
            @RequestParam
                int pageSize
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getPagedUsers(page, pageSize)), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_BILL_TRANSACTIONS', 'ADMIN_FULL')")
    @GetMapping("/user/bill-transactions/{userId}")
    public ResponseEntity<ServiceResponse<PagingResponse<BillTransactionDTO>>> getUserBillTransactions(
            @PathVariable
                    UUID userId,
            @RequestParam
                    int page,
            @RequestParam
                    int pageSize
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getUserBillTransactions(userId, page, pageSize)), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_SBER_TRANSACTIONS', 'ADMIN_FULL')")
    @GetMapping("/user/sber-transactions/{userId}")
    public ResponseEntity<ServiceResponse<PagingResponse<SberTransactionDTO>>> getUserSberTransactions(
            @PathVariable
                    UUID userId,
            @RequestParam
                    int page,
            @RequestParam
                    int pageSize
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getUserSberTransactions(userId, page, pageSize)), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_TOCHKA_TRANSACTIONS', 'ADMIN_FULL')")
    @GetMapping("/user/tochka-transactions/{userId}")
    public ResponseEntity<ServiceResponse<PagingResponse<TochkaTransactionDTO>>> getUserTochkaTransactions(
            @PathVariable
                    UUID userId,
            @RequestParam
                    int page,
            @RequestParam
                    int pageSize
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getUserTochkaTransactions(userId, page, pageSize)), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_TINKOFF_TRANSACTIONS', 'ADMIN_FULL')")
    @GetMapping("/user/tinkoff-transactions/{userId}")
    public ResponseEntity<ServiceResponse<PagingResponse<TinkoffTransactionDTO>>> getUserTinkoffTransactions(
            @PathVariable
                    UUID userId,
            @RequestParam
                    int page,
            @RequestParam
                    int pageSize
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getUserTinkoffTransactions(userId, page, pageSize)), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_REMOVE_USER', 'ADMIN_FULL')")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> removeUser(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(adminService.removeUser(userId))), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_USER_BILLS', 'ADMIN_FULL')")
    @GetMapping("/user/bills/{userId}")
    public ResponseEntity<ServiceResponse<List<BillDTO>>> getUserBills(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getUserBills(userId).stream().map(BillDTO::new).collect(Collectors.toList())), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_USER_BILLS', 'ADMIN_FULL')")
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_USER_CATEGORIES', 'ADMIN_FULL')")
    @GetMapping("/user/categories/{userId}")
    public ResponseEntity<ServiceResponse<List<CategoryDTO>>> getUserCategories(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getUserCategories(userId).stream().map(CategoryDTO::new).collect(Collectors.toList())), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_TOCHKA_CARDS', 'ADMIN_FULL')")
    @GetMapping("/user/tochka-cards/{userId}")
    public ResponseEntity<ServiceResponse<List<TochkaCardDTO>>> getUserTochkaCards(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getUserTochkaCards(userId).stream().map(TochkaCardDTO::new).collect(Collectors.toList())), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_SBER_CARDS', 'ADMIN_FULL')")
    @GetMapping("/user/sber-cards/{userId}")
    public ResponseEntity<ServiceResponse<List<SberCardDTO>>> getUserSberCards(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getUserSberCards(userId).stream().map(SberCardDTO::new).collect(Collectors.toList())), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_TINKOFF_CARDS', 'ADMIN_FULL')")
    @GetMapping("/user/tinkoff-cards/{userId}")
    public ResponseEntity<ServiceResponse<List<TinkoffCardDTO>>> getUserTinkoffCards(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getUserTinkoffCards(userId).stream().map(TinkoffCardDTO::new).collect(Collectors.toList())), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_USER_ACTIVITY_GET', 'ADMIN_FULL')")
    @GetMapping("/user/activity/{userId}")
    public ResponseEntity<ServiceResponse<List<ActivityDTO>>> getUserActivitiesByPeriod(
            @PathVariable
                    UUID userId,
            @RequestParam
                    Instant startTime,
            @RequestParam
                    Instant endTime
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getUserActivityByPeriod(userId, startTime, endTime).stream().map(ActivityDTO::new).collect(Collectors.toList())), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_GET_USER', 'ADMIN_FULL')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> getUserById(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(adminService.getUserById(userId))), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_USER_SUBSCRIPTION_GET', 'ADMIN_FULL')")
    @GetMapping("/user/subscription/{userId}")
    public ResponseEntity<ServiceResponse<SubscriptionDTO>> getUserSubscription(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionDTO(adminService.getUserSubscription(userId))), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_USER_SUBSCRIPTION_RESET', 'ADMIN_FULL')")
    @GetMapping("/user/subscription/reset/{userId}")
    public ResponseEntity<ServiceResponse<SubscriptionDTO>> resetUserSubscription(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionDTO(adminService.resetSubscription(userId))), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_USER_SUBSCRIPTION_EXTEND', 'ADMIN_FULL')")
    @PostMapping("/user/subscription/extend/{userId}")
    public ResponseEntity<ServiceResponse<SubscriptionDTO>> extendUserSubscription(
            @RequestBody
            @Valid
                    ExtendSubscriptionRequest request,
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new SubscriptionDTO(adminService.updateSubscription(request, userId))), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_USER_RESET_PIN', 'ADMIN_FULL')")
    @PostMapping("/user/reset-pin/{userId}")
    public ResponseEntity<ServiceResponse<UserDTO>> resetUserPin(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), new UserDTO(adminService.resetPin(userId))), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PreAuthorize("hasAnyAuthority('ADMIN_USER_LOYALTY_CARDS', 'ADMIN_FULL')")
    @GetMapping("/user/loyalty-cards/{userId}")
    public ResponseEntity<ServiceResponse<List<LoyaltyCardDTO>>> getUserLoyaltyCards(
            @PathVariable
                    UUID userId
    ) {
        return new ResponseEntity<>(new ServiceResponse<>(HttpStatus.OK.value(), adminService.getUserLoyaltyCards(userId).stream().map(LoyaltyCardDTO::new).collect(Collectors.toList())), HttpStatus.OK);
    }
}
