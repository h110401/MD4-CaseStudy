package rikkei.academy.md4casestudy.security.userprincipal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rikkei.academy.md4casestudy.model.User;
import rikkei.academy.md4casestudy.repo.IUserRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceIMPL implements UserDetailsService {

    private final IUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserPrincipal.build(userRepo.findByUsernameOrEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User %s not found", username))
        ));
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            return userRepo.findByUsernameOrEmail(((UserPrincipal) principal).getUsername()).orElse(null);
        } else {
            return userRepo.findByUsernameOrEmail(String.valueOf(principal)).orElse(null);
        }
    }
}
