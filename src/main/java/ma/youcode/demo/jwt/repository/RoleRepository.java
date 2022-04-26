package ma.youcode.demo.jwt.repository;

import ma.youcode.demo.jwt.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

}
