package ma.youcode.demo.jwt.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROLE_TABLE")

public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String roleName;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY,
    cascade = CascadeType.ALL,
//    it's the Set<RoleEntity> roles that is inside the UserEntity
    mappedBy = "roles")
    private Set<UserEntity>  users = new HashSet<>();
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
