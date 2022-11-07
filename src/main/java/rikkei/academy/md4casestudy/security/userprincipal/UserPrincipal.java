package rikkei.academy.md4casestudy.security.userprincipal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rikkei.academy.md4casestudy.model.User;

import java.util.Collection;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private Long id;
    private String name;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private String avatar;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal build(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getAvatar(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name())).collect(Collectors.toSet())
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
