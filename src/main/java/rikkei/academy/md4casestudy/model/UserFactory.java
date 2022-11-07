package rikkei.academy.md4casestudy.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserFactory {
    @Value("${default.avatar}")
    private String defaultAvatar;

    public User build(Long id, String name, String username, String email, String password, Set<Role> roles) {
//        System.out.println(defaultAvatar);
        return new User(id, name, username, email, password, defaultAvatar, roles);
    }
}
