package com.wp.system.permissions;

import com.wp.system.permissions.bill.BillPermissions;
import com.wp.system.permissions.bill.BillTransactionPermissions;
import com.wp.system.permissions.category.CategoryPermissions;
import com.wp.system.permissions.image.ImagePermissions;
import com.wp.system.permissions.loyalty.LoyaltyBlankPermissions;
import com.wp.system.permissions.loyalty.LoyaltyCardPermissions;
import com.wp.system.permissions.notification.NotificationPermissions;
import com.wp.system.permissions.user.UserPermissions;
import com.wp.system.permissions.user.UserRolePermissionPermissions;
import com.wp.system.permissions.user.UserRolePermissions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PermissionManager {
    private List<Permission> permissionList = new ArrayList<>();

    public PermissionManager() {
        permissionList.addAll(Arrays.stream(UserPermissions.values()).toList());
        permissionList.addAll(Arrays.stream(UserRolePermissions.values()).toList());
        permissionList.addAll(Arrays.stream(UserRolePermissionPermissions.values()).toList());
        permissionList.addAll(Arrays.stream(CategoryPermissions.values()).toList());
        permissionList.addAll(Arrays.stream(NotificationPermissions.values()).toList());
        permissionList.addAll(Arrays.stream(BillPermissions.values()).toList());
        permissionList.addAll(Arrays.stream(BillTransactionPermissions.values()).toList());
        permissionList.addAll(Arrays.stream(ImagePermissions.values()).toList());
        permissionList.addAll(Arrays.stream(LoyaltyBlankPermissions.values()).toList());
        permissionList.addAll(Arrays.stream(LoyaltyCardPermissions.values()).toList());

    }

    public Permission getPermissionBySystemName(String systemName) {
        for (Permission permission : permissionList) {
            if(permission.getPermissionSystemValue().equals(systemName))
                return permission;
        }

        return null;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }
}
