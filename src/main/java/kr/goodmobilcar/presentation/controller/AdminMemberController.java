package kr.goodmobilcar.presentation.controller;

import kr.goodmobilcar.application.admin.InviteAdminUseCase;
import kr.goodmobilcar.application.admin.UpdateMemberUseCase;
import kr.goodmobilcar.domain.admin.Admin;
import kr.goodmobilcar.domain.admin.AdminRepository;
import kr.goodmobilcar.domain.admin.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/members")
public class AdminMemberController {

    private final AdminRepository adminRepository;
    private final InviteAdminUseCase inviteAdminUseCase;
    private final UpdateMemberUseCase updateMemberUseCase;

    public AdminMemberController(AdminRepository adminRepository,
                                  InviteAdminUseCase inviteAdminUseCase,
                                  UpdateMemberUseCase updateMemberUseCase) {
        this.adminRepository = adminRepository;
        this.inviteAdminUseCase = inviteAdminUseCase;
        this.updateMemberUseCase = updateMemberUseCase;
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(adminRepository.findAllActive().stream().map(this::toDto).toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> invite(@RequestBody Map<String, String> body) {
        Admin admin = inviteAdminUseCase.execute(
                body.get("email"), body.get("name"), body.get("phone"),
                body.get("title"), Role.valueOf(body.getOrDefault("role", "ADMIN")));
        return ResponseEntity.status(201).body(toDto(admin));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Role role = body.containsKey("role") ? Role.valueOf(body.get("role")) : null;
        Admin updated = updateMemberUseCase.updateInfo(id, body.get("name"), body.get("phone"), body.get("title"), role);
        return ResponseEntity.ok(toDto(updated));
    }

    @PatchMapping("/{id}/live")
    public ResponseEntity<?> toggleLive(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        Admin updated = updateMemberUseCase.updateLive(id, Boolean.TRUE.equals(body.get("live")));
        return ResponseEntity.ok(toDto(updated));
    }

    @PatchMapping("/{id}/priority")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updatePriority(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        Admin updated = updateMemberUseCase.updatePriority(id, body.get("priority"));
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        updateMemberUseCase.delete(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    private Map<String, Object> toDto(Admin a) {
        return Map.of(
                "id", a.getId(),
                "email", a.getEmail(),
                "name", a.getName(),
                "phone", a.getPhone() != null ? a.getPhone() : "",
                "title", a.getTitle() != null ? a.getTitle() : "",
                "role", a.getRole().name(),
                "live", a.isLive(),
                "mustReceive", a.isMustReceive(),
                "orderPriority", a.getOrderPriority() != null ? a.getOrderPriority() : 0
        );
    }
}
