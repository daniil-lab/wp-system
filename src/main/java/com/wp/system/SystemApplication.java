package com.wp.system;

import com.wp.system.entity.sber.SberIntegrationState;
import com.wp.system.entity.user.User;
import com.wp.system.entity.user.UserRole;
import com.wp.system.entity.user.UserRolePermission;
import com.wp.system.exception.ServiceException;
import com.wp.system.utils.WalletType;
import com.wp.system.utils.user.UserType;
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
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.*;

@SpringBootApplication
@SecurityScheme(
		name = "Bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
@OpenAPIDefinition(
//		servers = {
//				@Server(url = "http://188.225.45.41/")
//		},
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

	@PersistenceContext
	private EntityManager entityManager;

	public static void main(String[] args) {
		SpringApplication.run(SystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		entityManager.createQuery("UPDATE SberIntegration SET state = :state1 WHERE state = :state2")
				.setParameter("state1", SberIntegrationState.ERROR_SYNC)
				.setParameter("state2", SberIntegrationState.SYNC);
//		System.setProperty("http.proxyHost", "ru3.mproxy.top");
//		System.setProperty("http.proxyPort", "20004");
//		System.setProperty("http.proxyUser", "robocontext");
//		System.setProperty("http.proxyPassword", "34LAFVWNUC");
//
//		Authenticator.setDefault(
//				new Authenticator() {
//					public PasswordAuthentication getPasswordAuthentication() {
//						return new PasswordAuthentication("robocontext", "34LAFVWNUC".toCharArray());
//					}
//				}
//		);

		User user = null;
		UserRole role = null;

		try {
			role = userRoleService.getUserRoleByName("forTesting");
		} catch (ServiceException e) {
			CreateUserRoleRequest request = new CreateUserRoleRequest();

			request.setRoleForBlocked(Optional.of(false));
			request.setAutoApply(Optional.of(false));
			request.setName("forTesting");
			request.setIsAdmin(Optional.of(true));
			request.setRoleAfterBuy(Optional.of(false));
			request.setRoleAfterBuyExpiration(Optional.of(false));

			role = userRoleService.createUserRole(request);
		}

		try {
			user = userService.getUserByUsername("+77777777777");
		} catch (ServiceException e) {
			CreateUserRequest request = new CreateUserRequest();

			request.setPassword(new String(Base64.getEncoder().encode("tessst".getBytes())));
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
			userService.updateUser(request);
		}
	}
}
