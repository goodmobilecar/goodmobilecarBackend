package kr.goodmobilcar.presentation.controller;

import kr.goodmobilcar.application.capital.ManageCapitalPartnersUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/capital-partners")
public class AdminCapitalController {

    private final ManageCapitalPartnersUseCase useCase;

    public AdminCapitalController(ManageCapitalPartnersUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(useCase.getAll().stream()
                .map(p -> Map.of("id", p.getId(), "name", p.getName(),
                        "logoUrl", p.getLogoUrl() != null ? p.getLogoUrl() : "",
                        "displayOrder", p.getDisplayOrder(), "active", p.isActive()))
                .toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        var partner = useCase.create(
                (String) body.get("name"),
                (String) body.get("logoUrl"),
                (Integer) body.get("displayOrder"));
        return ResponseEntity.status(201).body(Map.of("id", partner.getId(), "name", partner.getName()));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        var partner = useCase.update(id,
                (String) body.get("name"),
                (String) body.get("logoUrl"),
                (Integer) body.get("displayOrder"));
        return ResponseEntity.ok(Map.of("id", partner.getId(), "name", partner.getName()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        useCase.deactivate(id);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
