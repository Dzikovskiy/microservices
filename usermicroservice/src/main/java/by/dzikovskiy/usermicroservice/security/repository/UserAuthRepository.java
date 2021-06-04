package by.dzikovskiy.usermicroservice.security.repository;

import by.dzikovskiy.usermicroservice.security.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findByLogin(String login);
}
