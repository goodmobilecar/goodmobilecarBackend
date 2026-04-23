package kr.goodmobilcar.presentation.controller;

import kr.goodmobilcar.application.stats.GetStatsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/admin/stats")
public class AdminStatsController {

    private final GetStatsUseCase getStatsUseCase;

    public AdminStatsController(GetStatsUseCase getStatsUseCase) {
        this.getStatsUseCase = getStatsUseCase;
    }

    @GetMapping("/today")
    public ResponseEntity<?> today() {
        return ResponseEntity.ok(getStatsUseCase.getToday());
    }

    @GetMapping("/daily")
    public ResponseEntity<?> daily(
            @RequestParam String from,
            @RequestParam String to) {
        return ResponseEntity.ok(getStatsUseCase.getDaily(Instant.parse(from), Instant.parse(to)));
    }

    @GetMapping("/channel")
    public ResponseEntity<?> channel() {
        return ResponseEntity.ok(getStatsUseCase.getByChannel());
    }
}
