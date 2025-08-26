package com.company.uiamp.permission.controller;

import com.company.uiamp.permission.entity.Role;
import com.company.uiamp.permission.entity.UserAppRole;
import com.company.uiamp.permission.service.RoleService;
import com.company.uiamp.permission.service.UserAppRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UserAppRoleService userAppRoleService;

    // Role management
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @GetMapping("/roles/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @PostMapping("/roles")
    public boolean createRole(@RequestBody Role role) {
        return roleService.save(role);
    }

    @PutMapping("/roles/{id}")
    public boolean updateRole(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        return roleService.updateById(role);
    }

    @DeleteMapping("/roles/{id}")
    public boolean deleteRole(@PathVariable Long id) {
        return roleService.removeById(id);
    }

    // User-App-Role management
    @GetMapping("/user-app-roles")
    public List<UserAppRole> getAllUserAppRoles() {
        return userAppRoleService.list();
    }

    @GetMapping("/user-app-roles/{id}")
    public UserAppRole getUserAppRoleById(@PathVariable Long id) {
        return userAppRoleService.getById(id);
    }

    @PostMapping("/user-app-roles")
    public boolean createUserAppRole(@RequestBody UserAppRole userAppRole) {
        return userAppRoleService.save(userAppRole);
    }

    @PutMapping("/user-app-roles/{id}")
    public boolean updateUserAppRole(@PathVariable Long id, @RequestBody UserAppRole userAppRole) {
        userAppRole.setId(id);
        return userAppRoleService.updateById(userAppRole);
    }

    @DeleteMapping("/user-app-roles/{id}")
    public boolean deleteUserAppRole(@PathVariable Long id) {
        return userAppRoleService.removeById(id);
    }
}