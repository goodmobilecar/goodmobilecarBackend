package kr.goodmobilcar.application.admin;

import kr.goodmobilcar.domain.admin.Admin;
import kr.goodmobilcar.domain.admin.AdminRepository;
import kr.goodmobilcar.domain.admin.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateMemberUseCase {

    private final AdminRepository adminRepository;

    public UpdateMemberUseCase(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Transactional
    public Admin updateLive(Long adminId, boolean live) {
        Admin admin = findOrThrow(adminId);
        return adminRepository.save(admin.withLive(live));
    }

    @Transactional
    public Admin updatePriority(Long adminId, Integer priority) {
        Admin admin = findOrThrow(adminId);
        return adminRepository.save(admin.withOrderPriority(priority));
    }

    @Transactional
    public Admin updateInfo(Long adminId, String name, String phone, String title, Role role) {
        Admin admin = findOrThrow(adminId);
        Admin updated = admin.toBuilder()
                .name(name != null ? name : admin.getName())
                .phone(phone != null ? phone : admin.getPhone())
                .title(title != null ? title : admin.getTitle())
                .role(role != null ? role : admin.getRole())
                .build();
        return adminRepository.save(updated);
    }

    @Transactional
    public void delete(Long adminId) {
        findOrThrow(adminId);
        adminRepository.delete(adminId);
    }

    private Admin findOrThrow(Long adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException(adminId));
    }

    public static class AdminNotFoundException extends RuntimeException {
        public AdminNotFoundException(Long id) { super("관리자를 찾을 수 없습니다: " + id); }
    }
}
