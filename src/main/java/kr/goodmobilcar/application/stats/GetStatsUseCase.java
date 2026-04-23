package kr.goodmobilcar.application.stats;

import kr.goodmobilcar.application.port.StatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;

@Service
public class GetStatsUseCase {

    private final StatsRepository statsRepository;

    public GetStatsUseCase(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Transactional(readOnly = true)
    public TodayStats getToday() {
        long total = statsRepository.countTodayLeads();
        Map<String, Long> byChannel = statsRepository.countTodayByChannel();
        return new TodayStats(total, byChannel);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getDaily(Instant from, Instant to) {
        return statsRepository.countDailyLeads(from, to);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getByChannel() {
        return statsRepository.countAllByChannel();
    }

    public record TodayStats(long total, Map<String, Long> byChannel) {}
}
