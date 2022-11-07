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

}

