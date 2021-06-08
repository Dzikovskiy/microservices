package by.dzikovskiy.usermicroservice.security.repository;


import by.dzikovskiy.usermicroservice.security.entity.RefreshToken;
import by.dzikovskiy.usermicroservice.security.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(UserAuth user);
}