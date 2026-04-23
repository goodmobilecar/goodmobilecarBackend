package kr.goodmobilcar.application.admin;

import kr.goodmobilcar.application.port.EmailSender;
import kr.goodmobilcar.domain.admin.Admin;
import kr.goodmobilcar.domain.admin.AdminRepository;
import kr.goodmobilcar.domain.admin.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class InviteAdminUseCase {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    public InviteAdminUseCase(AdminRepository adminRepository,
                               PasswordEncoder passwordEncoder,
                               EmailSender emailSender) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
    }

    @Transactional
    public Admin execute(String email, String name, String phone, String title, Role role) {
        if (adminRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException(email);
        }

        String tempPassword = UUID.randomUUID().toString().substring(0, 8);
        Admin admin = Admin.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(tempPassword))
                .name(name)
                .phone(phone)
                .title(title)
                .role(role)
                .build();

        Admin saved = adminRepository.save(admin);

        emailSender.send(email, "[굿모빌카] 관리자 초대",
                String.format("안녕하세요 %s님, 임시 비밀번호: %s", name, tempPassword));

        return saved;
    }

    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String email) { super("이미 존재하는 이메일입니다: " + email); }
    }
}
