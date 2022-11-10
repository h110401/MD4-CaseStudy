package rikkei.academy.md4casestudy.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rikkei.academy.md4casestudy.model.User;

import java.util.Optional;

public interface IUserRepo extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
Optional<User>findByUsername(String username);

    @Query("select u from User u where u.username = :principal or u.email = :principal")
    Optional<User> findByUsernameOrEmail(@Param("principal") String principal);
}
