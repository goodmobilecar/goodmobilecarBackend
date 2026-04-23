package kr.goodmobilcar.application.port;

import java.time.Instant;
import java.util.Map;

public interface StatsRepository {
    long countTodayLeads();
    Map<String, Long> countTodayByChannel();
    Map<String, Long> countDailyLeads(Instant from, Instant to);
    Map<String, Long> countAllByChannel();
}
