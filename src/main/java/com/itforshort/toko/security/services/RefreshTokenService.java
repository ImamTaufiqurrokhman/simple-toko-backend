package com.itforshort.toko.security.services;

import com.itforshort.toko.exception.TokenRefreshException;
import com.itforshort.toko.model.RefreshToken;
import com.itforshort.toko.model.User;
import com.itforshort.toko.repository.RefreshTokenRepository;
import com.itforshort.toko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${itforshort.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private RefreshTokenRepository refreshTokenRepository;
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findRefreshTokenByToken(token);
    }

    public RefreshToken createRefreshToken(UserDetailsImpl userDetails) {
        RefreshToken refreshToken = new RefreshToken();
        Optional<User> _user = userRepository.findById(userDetails.getId());
        if (_user.isPresent()) {
            User user = _user.get();
            refreshToken.setUser(user);
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken = refreshTokenRepository.save(refreshToken);
        }
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
          refreshTokenRepository.delete(token);
          throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
      }
      @Transactional
      public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
      }
}
