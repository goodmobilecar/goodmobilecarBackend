package kr.goodmobilcar.presentation.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.goodmobilcar.application.admin.LoginUseCase;
import kr.goodmobilcar.domain.admin.Admin;
import kr.goodmobilcar.infrastructure.security.JwtProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    private final LoginUseCase loginUseCase;
    private final JwtProvider jwtProvider;

    public AdminAuthController(LoginUseCase loginUseCase, JwtProvider jwtProvider) {
        this.loginUseCase = loginUseCase;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpServletResponse res) {
        Admin admin = loginUseCase.execute(body.get("email"), body.get("password"));

        String accessToken = jwtProvider.createAccessToken(admin.getId(), admin.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(admin.getId());

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/admin/auth");
        cookie.setMaxAge((int) jwtProvider.getRefreshTokenExpiry());
        res.addCookie(cookie);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "admin", Map.of(
                        "id", admin.getId(),
                        "email", admin.getEmail(),
                        "name", admin.getName(),
                        "role", admin.getRole().name()
                )
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null || !jwtProvider.isValid(refreshToken)) {
            return ResponseEntity.status(401).body(Map.of("error", "INVALID_REFRESH_TOKEN"));
        }
        Long adminId = jwtProvider.getAdminId(refreshToken);
        String newAccessToken = jwtProvider.createAccessToken(adminId, "ADMIN");
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("refreshToken", "");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/api/admin/auth");
        res.addCookie(cookie);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
