package ma.youcode.demo.jwt.controller;

import ma.youcode.demo.jwt.model.RoleModel;
import ma.youcode.demo.jwt.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/roles")
    public RoleModel crateRole(@RequestBody RoleModel roleModel){
        return roleService.createRole(roleModel);
    }

    @GetMapping("/roles")
    public List<RoleModel> getAllRoles() {
        return roleService.getAllRoles();
    }

    @DeleteMapping("/roles/{roleId}")
    public void deleteRole(@PathVariable Long roleId) {
        roleService.deleteRoleById(roleId);
    }
}
