package kr.goodmobilcar.presentation.controller;

import kr.goodmobilcar.application.lead.*;
import kr.goodmobilcar.domain.lead.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/leads")
public class AdminLeadController {

    private final GetLeadsUseCase getLeadsUseCase;
    private final UpdateLeadUseCase updateLeadUseCase;
    private final DeleteLeadUseCase deleteLeadUseCase;
    private final ExportLeadsUseCase exportLeadsUseCase;

    public AdminLeadController(GetLeadsUseCase getLeadsUseCase,
                                UpdateLeadUseCase updateLeadUseCase,
                                DeleteLeadUseCase deleteLeadUseCase,
                                ExportLeadsUseCase exportLeadsUseCase) {
        this.getLeadsUseCase = getLeadsUseCase;
        this.updateLeadUseCase = updateLeadUseCase;
        this.deleteLeadUseCase = deleteLeadUseCase;
        this.exportLeadsUseCase = exportLeadsUseCase;
    }

    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo,
            @RequestParam(required = false) String channel,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "false") boolean includeDeleted,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        LeadSearchCondition condition = LeadSearchCondition.builder()
                .dateFrom(dateFrom != null ? Instant.parse(dateFrom) : null)
                .dateTo(dateTo != null ? Instant.parse(dateTo) : null)
                .channel(channel != null ? Channel.valueOf(channel) : null)
                .status(status != null ? LeadStatus.valueOf(status) : null)
                .assigneeId(assigneeId)
                .search(search)
                .includeDeleted(includeDeleted)
                .page(page).size(size)
                .build();

        GetLeadsUseCase.LeadPage result = getLeadsUseCase.execute(condition);
        return ResponseEntity.ok(Map.of(
                "leads", result.leads().stream().map(this::toDto).toList(),
                "total", result.total(),
                "page", result.page(),
                "size", result.size()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(getLeadsUseCase.findById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, String> body) {
        LeadStatus status = body.containsKey("status") ? LeadStatus.valueOf(body.get("status")) : null;
        Lead updated = updateLeadUseCase.execute(id, status, body.get("memo"));
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal Long adminId) {
        deleteLeadUseCase.execute(id, adminId);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo,
            @RequestParam(required = false) String channel,
            @RequestParam(required = false) String status) {

        LeadSearchCondition condition = LeadSearchCondition.builder()
                .dateFrom(dateFrom != null ? Instant.parse(dateFrom) : null)
                .dateTo(dateTo != null ? Instant.parse(dateTo) : null)
                .channel(channel != null ? Channel.valueOf(channel) : null)
                .status(status != null ? LeadStatus.valueOf(status) : null)
                .build();

        byte[] excel = exportLeadsUseCase.execute(condition);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"leads.xlsx\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(excel);
    }

    private Map<String, Object> toDto(Lead lead) {
        return Map.of(
                "id", lead.getId(),
                "customerName", lead.getCustomerName(),
                "phone", lead.getPhone().getValue(),
                "carModel", lead.getCarModel(),
                "leasePeriod", lead.getLeasePeriod(),
                "incomeType", lead.getIncomeType().name(),
                "channel", lead.getChannel() != null ? lead.getChannel().name() : "",
                "status", lead.getStatus().name(),
                "assigneeId", lead.getAssigneeId() != null ? lead.getAssigneeId() : 0,
                "createdAt", lead.getCreatedAt() != null ? lead.getCreatedAt().toString() : ""
        );
    }
}
