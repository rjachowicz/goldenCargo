package com.goldencargo.repository;

import com.goldencargo.model.entities.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByFcmToken(String fcmToken);

    @Query("SELECT f FROM FcmToken f WHERE f.user.userId = :userId order by f.tokenId desc limit 1")
    Optional<FcmToken> findByUserId(Long userId);
}
