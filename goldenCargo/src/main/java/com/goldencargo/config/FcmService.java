package com.goldencargo.config;

import com.goldencargo.model.entities.FcmToken;
import com.goldencargo.model.entities.User;
import com.goldencargo.repository.FcmTokenRepository;
import com.goldencargo.repository.UserRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class FcmService {

    private final UserRepository userRepository;
    private final FcmTokenRepository fcmTokenRepository;

    public FcmService(UserRepository userRepository, FcmTokenRepository fcmTokenRepository) {
        this.userRepository = userRepository;
        this.fcmTokenRepository = fcmTokenRepository;
    }

    @PostConstruct
    public void initialize() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/goldencargoapp-firebase-adminsdk-fbsvc-04018ab415.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

    public void sendNotification(String token, String title, String body, Map<String, String> data) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
        Message.Builder messageBuilder = Message.builder()
                .setToken(token)
                .setNotification(notification);
        if (data != null) {
            messageBuilder.putAllData(data);
        }
        Message message = messageBuilder.build();
        FirebaseMessaging.getInstance().send(message);
    }

    @Transactional
    public FcmToken saveFcmTokenForUser(Long userId, String fcmToken) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + userId));

        Optional<FcmToken> existingTokenOpt = fcmTokenRepository.findByFcmToken(fcmToken);
        if (existingTokenOpt.isPresent()) {
            FcmToken existingToken = existingTokenOpt.get();
            if (!existingToken.getUser().getUserId().equals(userId)) {
                existingToken.setUser(user);
            }
            return fcmTokenRepository.save(existingToken);
        }

        FcmToken newToken = new FcmToken();
        newToken.setFcmToken(fcmToken);
        newToken.setUser(user);
        return fcmTokenRepository.save(newToken);
    }


    public FcmToken findTokenByUserId(Long userId) {
        Optional<FcmToken> existingTokenOpt = fcmTokenRepository.findByUserId(userId);
        return existingTokenOpt.orElse(null);
    }

    public void sendAlert(Long driverId) throws FirebaseMessagingException {
        FcmToken tokenByUserId = findTokenByUserId(driverId);
        sendNotification(tokenByUserId.getFcmToken(), "GOLDEN CARGO", "Przypisano nowe zlecenie", null);
    }
}

