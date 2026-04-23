package kr.goodmobilcar.infrastructure.persistence;

import jakarta.persistence.EntityManager;
import kr.goodmobilcar.application.port.StatsRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StatsRepositoryImpl implements StatsRepository {

    private final EntityManager em;

    public StatsRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public long countTodayLeads() {
        Instant startOfDay = Instant.now().truncatedTo(ChronoUnit.DAYS);
        return em.createQuery(
                        "SELECT COUNT(l) FROM LeadJpaEntity l WHERE l.createdAt >= :start AND l.deleted = false",
                        Long.class)
                .setParameter("start", startOfDay)
                .getSingleResult();
    }

    @Override
    public Map<String, Long> countTodayByChannel() {
        Instant startOfDay = Instant.now().truncatedTo(ChronoUnit.DAYS);
        List<Object[]> rows = em.createQuery(
                        "SELECT l.channel, COUNT(l) FROM LeadJpaEntity l WHERE l.createdAt >= :start AND l.deleted = false GROUP BY l.channel",
                        Object[].class)
                .setParameter("start", startOfDay)
                .getResultList();
        return toMap(rows);
    }

    @Override
    public Map<String, Long> countDailyLeads(Instant from, Instant to) {
        List<Object[]> rows = em.createQuery(
                        "SELECT CAST(l.createdAt AS date), COUNT(l) FROM LeadJpaEntity l WHERE l.createdAt BETWEEN :from AND :to AND l.deleted = false GROUP BY CAST(l.createdAt AS date) ORDER BY 1",
                        Object[].class)
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
        return toMap(rows);
    }

    @Override
    public Map<String, Long> countAllByChannel() {
        List<Object[]> rows = em.createQuery(
                        "SELECT l.channel, COUNT(l) FROM LeadJpaEntity l WHERE l.deleted = false GROUP BY l.channel",
                        Object[].class)
                .getResultList();
        return toMap(rows);
    }

    private Map<String, Long> toMap(List<Object[]> rows) {
        Map<String, Long> result = new LinkedHashMap<>();
        for (Object[] row : rows) {
            String key = row[0] != null ? row[0].toString() : "UNKNOWN";
            Long count = ((Number) row[1]).longValue();
            result.put(key, count);
        }
        return result;
    }
}
