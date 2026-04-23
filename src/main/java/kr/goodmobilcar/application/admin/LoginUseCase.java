package kr.goodmobilcar.application.admin;

import kr.goodmobilcar.domain.admin.Admin;
import kr.goodmobilcar.domain.admin.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginUseCase {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginUseCase(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Admin execute(String email, String rawPassword) {
        Admin admin = adminRepository.findByEmail(email)
                .filter(Admin::isActive)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(rawPassword, admin.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        return adminRepository.save(admin.recordLogin());
    }

    public static class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException() { super("이메일 또는 비밀번호가 올바르지 않습니다"); }
    }
}
