package ma.youcode.demo.jwt.services;

import ma.youcode.demo.jwt.entity.RoleEntity;
import ma.youcode.demo.jwt.model.RoleModel;
import ma.youcode.demo.jwt.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public RoleModel createRole(RoleModel roleModel) {
        RoleEntity roleEntity = new RoleEntity();
        // model to entity conversion
;        BeanUtils.copyProperties(roleModel, roleEntity);
RoleEntity roleEntity1 = roleRepository.save(roleEntity);
// entity to model conversion
BeanUtils.copyProperties(roleEntity1, roleModel);
        return roleModel;
    }

    @Override
    public List<RoleModel> getAllRoles() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        List<RoleModel> roleModels = new ArrayList<>();
        RoleModel roleModel = null;
        for (RoleEntity re : roleEntities) {
            roleModel = new RoleModel();
            BeanUtils.copyProperties(re, roleModel);
            roleModels.add(roleModel);
        }
        return roleModels;
    }

    @Override
    public RoleModel getRoleById(Long roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId).get();
        RoleModel roleModel = new RoleModel();
        BeanUtils.copyProperties(roleEntity, roleModel);
        return roleModel;
    }

    @Override
    public void deleteRoleById(Long roleId) {
        roleRepository.deleteById(roleId);

    }
}
