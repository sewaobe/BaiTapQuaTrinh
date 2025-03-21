package com.example.demo;


import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TOTPUtil {
    private static final TimeBasedOneTimePasswordGenerator totpGenerator;
    static {
        try {
            totpGenerator = new TimeBasedOneTimePasswordGenerator();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi khởi tạo TOTP Generator", e);
        }
    }
    private static final int OTP_VALID_DURATION_MINUTES = 5; // Thời gian OTP có hiệu lực
    private static long timeStampOTP = 0; // Lưu timestamp khi tạo OTP

    // Tạo Secret Key từ email
    public static Key generateKeyFromEmail(String email) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(email.getBytes());
        return new SecretKeySpec(hashedBytes, 0, 16, "HmacSHA1"); // Chỉ lấy 16 byte đầu
    }

    // Tạo mã OTP và lưu timestamp
    public static String generateOtp(String email) throws Exception {
        Key secretKey = generateKeyFromEmail(email);
        timeStampOTP = Instant.now().getEpochSecond(); // Lưu thời gian tạo OTP
        return String.valueOf(totpGenerator.generateOneTimePassword(secretKey, Instant.ofEpochSecond(timeStampOTP)));
    }

    // Xác minh mã OTP
    public static boolean verifyOtp(String email, String otp) throws Exception {
        if (timeStampOTP == 0) return false; // Không có OTP nào được tạo trước đó

        Instant now = Instant.now();
        if (now.isAfter(Instant.ofEpochSecond(timeStampOTP).plus(OTP_VALID_DURATION_MINUTES, ChronoUnit.MINUTES))) {
            return false; // OTP đã hết hạn
        }

        Key secretKey = generateKeyFromEmail(email);
        String expectedOtp = String.valueOf(totpGenerator.generateOneTimePassword(secretKey, Instant.ofEpochSecond(timeStampOTP)));

        return expectedOtp.equals(otp);
    }
}
