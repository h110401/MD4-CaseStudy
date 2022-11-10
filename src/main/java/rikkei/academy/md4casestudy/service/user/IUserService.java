package rikkei.academy.md4casestudy.service.user;

import rikkei.academy.md4casestudy.model.RoleName;
import rikkei.academy.md4casestudy.model.User;
import rikkei.academy.md4casestudy.security.userprincipal.UserPrincipal;
import rikkei.academy.md4casestudy.service.IGenericService;

import java.util.Optional;

public interface IUserService extends IGenericService<User> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findByUsernameOrEmail(String username);


    void addRoleToUser(String username, RoleName roleName);

    void removeRoleOfUser(String username, RoleName roleName);

    Optional<User>findByUsername(String username);
}
