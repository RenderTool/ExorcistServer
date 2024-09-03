package com.exorcist.util;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class EncryptionUtil {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String encryptPassword(String password, String salt) {
        return passwordEncoder.encode(password + salt);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword, String salt) {
        return passwordEncoder.matches(rawPassword + salt, encodedPassword);
    }
}
