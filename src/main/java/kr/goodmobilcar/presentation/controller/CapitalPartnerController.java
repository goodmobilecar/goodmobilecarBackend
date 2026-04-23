package kr.goodmobilcar.presentation.controller;

import kr.goodmobilcar.application.capital.ManageCapitalPartnersUseCase;
import kr.goodmobilcar.domain.capital.CapitalPartner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/capital-partners")
public class CapitalPartnerController {

    private final ManageCapitalPartnersUseCase useCase;

    public CapitalPartnerController(ManageCapitalPartnersUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public ResponseEntity<?> getActive() {
        List<Map<String, Object>> partners = useCase.getActive().stream()
                .map(p -> Map.<String, Object>of(
                        "id", p.getId(),
                        "name", p.getName(),
                        "logoUrl", p.getLogoUrl() != null ? p.getLogoUrl() : ""
                ))
                .toList();
        return ResponseEntity.ok(Map.of("partners", partners));
    }
}
