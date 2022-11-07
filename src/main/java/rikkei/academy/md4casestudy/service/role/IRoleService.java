package rikkei.academy.md4casestudy.service.role;

import rikkei.academy.md4casestudy.model.Role;
import rikkei.academy.md4casestudy.model.RoleName;

public interface IRoleService {
    void save(Role role);

    Role findByName(RoleName name);
}
