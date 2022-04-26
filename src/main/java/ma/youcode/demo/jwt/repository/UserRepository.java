package ma.youcode.demo.jwt.repository;

import ma.youcode.demo.jwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findByUsername(String userName);
}
