package vn.edu.bookverse.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import vn.edu.bookverse.entity.User_24162040;
import vn.edu.bookverse.repository.UserRepository_24162040;
import java.security.*;
import java.time.*;
import java.util.*;

public class AuthService_24162040 {
    private final UserRepository_24162040 users = new UserRepository_24162040();

    private static String hash(String value) {
        try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes())); }
        catch (Exception e) { throw new IllegalStateException(e); }
    }

    public String requestOtp(String email) throws MessagingException {
        if (users.byEmail(email.toLowerCase()) != null) throw new IllegalArgumentException("Email đã tồn tại");
        String code = "%06d".formatted(new SecureRandom().nextInt(1_000_000));
        sendMail(email, code);
        return hash(code);
    }

    public boolean validOtp(String otpHash, LocalDateTime expiresAt, String code) {
        return otpHash != null && expiresAt != null && !expiresAt.isBefore(LocalDateTime.now()) && hash(code).equals(otpHash);
    }

    public void register(String name, String email, String phone, String password) {
        if (users.byEmail(email.toLowerCase()) != null) throw new IllegalArgumentException("Email đã tồn tại");
        User_24162040 user = new User_24162040();
        user.fullName = name; user.email = email.toLowerCase(); user.phone = phone; user.passwordHash = hash(password); user.active = true;
        users.save(user);
    }

    private void sendMail(String email, String code) throws MessagingException {
        String username = System.getenv("BOOKVERSE_SMTP_USER");
        String password = System.getenv("BOOKVERSE_SMTP_PASSWORD");
        if (username == null || username.isBlank() || password == null || password.isBlank())
            throw new MessagingException("Chưa cấu hình BOOKVERSE_SMTP_USER và BOOKVERSE_SMTP_PASSWORD.");

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true"); properties.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() { return new PasswordAuthentication(username, password); }
        });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username)); message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("BookVerse - Xác thực đăng ký", "UTF-8");
        message.setText("Mã OTP của bạn: " + code + "\nMã có hiệu lực 5 phút. Không chia sẻ mã với người khác.", "UTF-8");
        Transport.send(message);
    }

    public User_24162040 login(String email, String password) {
        User_24162040 user = users.byEmail(email.toLowerCase());
        if (user == null || !user.active || !user.passwordHash.equals(hash(password))) return null;
        user.lastLogin = LocalDateTime.now(); users.save(user); return user;
    }
}
