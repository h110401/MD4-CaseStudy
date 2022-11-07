package rikkei.academy.md4casestudy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import rikkei.academy.md4casestudy.model.Role;
import rikkei.academy.md4casestudy.model.RoleName;

public interface IRoleRepo extends JpaRepository<Role, Long> {
    Role findByName(RoleName roleName);
}
