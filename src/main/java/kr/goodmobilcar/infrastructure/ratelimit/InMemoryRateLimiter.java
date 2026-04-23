package kr.goodmobilcar.infrastructure.ratelimit;

import kr.goodmobilcar.application.port.RateLimiter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

// 데모용 — 본개발 시 RedisRateLimiter로 교체
@Component
public class InMemoryRateLimiter implements RateLimiter {

    private static final int MAX_REQUESTS = 10;
    private static final long WINDOW_SECONDS = 60;

    private final ConcurrentHashMap<String, Window> windows = new ConcurrentHashMap<>();

    @Override
    public boolean isAllowed(String key) {
        if (key == null) return true;
        Window window = windows.compute(key, (k, w) -> {
            if (w == null || Instant.now().isAfter(w.resetAt)) {
                return new Window(Instant.now().plusSeconds(WINDOW_SECONDS));
            }
            return w;
        });
        return window.count.incrementAndGet() <= MAX_REQUESTS;
    }

    private static class Window {
        final AtomicInteger count = new AtomicInteger(0);
        final Instant resetAt;
        Window(Instant resetAt) { this.resetAt = resetAt; }
    }
}
