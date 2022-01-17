package com.wp.system;

import com.wp.system.dto.permission.PermissionDTO;
import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserRole;
import com.wp.system.entity.user.UserRolePermission;
import com.wp.system.exception.ServiceException;
import com.wp.system.other.WalletType;
import com.wp.system.other.user.UserType;
import com.wp.system.permissions.Permission;
import com.wp.system.permissions.PermissionManager;
import com.wp.system.request.user.AddPermissionToRoleRequest;
import com.wp.system.request.user.CreateUserRequest;
import com.wp.system.request.user.CreateUserRoleRequest;
import com.wp.system.request.user.EditUserRequest;
import com.wp.system.services.user.UserRolePermissionService;
import com.wp.system.services.user.UserRoleService;
import com.wp.system.services.user.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

@SpringBootApplication
@SecurityScheme(
		name = "Bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
@OpenAPIDefinition(
		info = @Info(
				title = "WP API",
				version = "1.0",
				description = "WP system")
)
@EnableScheduling
@ComponentScan(basePackages = "com.wp.system.*")
@EnableAsync
public class SystemApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserRolePermissionService userRolePermissionService;

	@Autowired
	private PermissionManager permissionManager;

	@Autowired
	private JavaMailSender mailSender;

	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = null;
		UserRole role = null;

		try {
			role = userRoleService.getUserRoleByName("forTesting");
		} catch (ServiceException e) {
			CreateUserRoleRequest request = new CreateUserRoleRequest();

			request.setAutoApply(Optional.of(false));
			request.setName("forTesting");
			request.setIsAdmin(Optional.of(true));

			role = userRoleService.createUserRole(request);
		}

		try {
			user = userService.getUserByUsername("+77777777777");
		} catch (ServiceException e) {
			CreateUserRequest request = new CreateUserRequest();

			request.setPassword(new String(Base64.getEncoder().encode("test".getBytes())));
			request.setUsername("+77777777777");
			request.setWalletType(WalletType.RUB);
			request.setType(UserType.SYSTEM);
			request.setRoleName("forTesting");

			user = userService.createUser(request);
		}

		if(role.getPermissions().size() != permissionManager.getPermissionList().size()) {
			for (UserRolePermission permission : role.getPermissions())
				userRolePermissionService.removePermissionFromRole(permission.getId());

			for (Permission permission : permissionManager.getPermissionList())
				userRolePermissionService.addPermissionToRole(new AddPermissionToRoleRequest(permission.getPermissionSystemValue()), role.getId());
		}

		if(!user.getRole().getId().equals(role.getId())) {
			EditUserRequest request = new EditUserRequest();
			request.setRoleName(role.getName());
			userService.updateUser(request, user.getId());
		}
	}
}
