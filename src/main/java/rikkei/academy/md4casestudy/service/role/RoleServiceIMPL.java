package rikkei.academy.md4casestudy.service.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rikkei.academy.md4casestudy.model.Role;
import rikkei.academy.md4casestudy.model.RoleName;
import rikkei.academy.md4casestudy.repo.IRoleRepo;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceIMPL implements IRoleService {

    private final IRoleRepo roleRepo;

    @Override
    public void save(Role role) {
        roleRepo.save(role);
    }

    @Override
    public Role findByName(RoleName name) {
        return roleRepo.findByName(name);
    }
}
