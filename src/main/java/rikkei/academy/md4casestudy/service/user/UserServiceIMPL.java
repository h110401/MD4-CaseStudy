package rikkei.academy.md4casestudy.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rikkei.academy.md4casestudy.model.Role;
import rikkei.academy.md4casestudy.model.RoleName;
import rikkei.academy.md4casestudy.model.User;
import rikkei.academy.md4casestudy.model.UserFactory;
import rikkei.academy.md4casestudy.repo.IRoleRepo;
import rikkei.academy.md4casestudy.repo.IUserRepo;
import rikkei.academy.md4casestudy.security.userprincipal.UserPrincipal;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceIMPL implements IUserService {

    private final IUserRepo userRepo;
    private final IRoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public User findByUsernameOrEmail(String username) {
        return userRepo.findByUsernameOrEmail(username).orElse(null);
    }

    @Override
    public void addRoleToUser(String username, RoleName roleName) {
        User user = userRepo.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public void removeRoleOfUser(String username, RoleName roleName) {
        User user = userRepo.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
        Role role = roleRepo.findByName(roleName);
        user.getRoles().remove(role);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
