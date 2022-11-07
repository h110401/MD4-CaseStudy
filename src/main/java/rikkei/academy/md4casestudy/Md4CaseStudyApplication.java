package rikkei.academy.md4casestudy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rikkei.academy.md4casestudy.model.Role;
import rikkei.academy.md4casestudy.model.User;
import rikkei.academy.md4casestudy.model.UserFactory;
import rikkei.academy.md4casestudy.service.role.IRoleService;
import rikkei.academy.md4casestudy.service.user.IUserService;

import java.util.HashSet;

import static rikkei.academy.md4casestudy.model.RoleName.*;

@SpringBootApplication
public class Md4CaseStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(Md4CaseStudyApplication.class, args);
    }
//    @Bean
//    CommandLineRunner run(IUserService userService, IRoleService roleService, UserFactory userFactory) {
//        return args -> {
//            roleService.save(new Role(null, ADMIN));
//            roleService.save(new Role(null, PM));
//            roleService.save(new Role(null, USER));
//
//            User build = userFactory.build(
//                    null,
//                    "Admin",
//                    "admin",
//                    "admin@gmail.com",
//                    "1234",
//                    new HashSet<>()
//            );
//            userService.save(build);
//            userService.save(userFactory.build(
//                    null,
//                    "Manager",
//                    "manager",
//                    "manager@gmail.com",
//                    "1234",
//                    new HashSet<>()
//            ));
//            userService.save(userFactory.build(
//                    null,
//                    "User",
//                    "user",
//                    "user@gmail.com",
//                    "1234",
//                    new HashSet<>()
//            ));
//
//            userService.addRoleToUser("admin", ADMIN);
//            userService.addRoleToUser("manager", PM);
//            userService.addRoleToUser("user", USER);
//        };
//
//    }
}

