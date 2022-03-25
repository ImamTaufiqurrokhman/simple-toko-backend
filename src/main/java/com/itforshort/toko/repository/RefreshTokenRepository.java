package com.itforshort.toko.repository;

import com.itforshort.toko.model.RefreshToken;
import com.itforshort.toko.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Override
    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findRefreshTokenByToken(String token);

    int deleteByUser(User user);
}
