package kr.goodmobilcar.domain.lead;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface LeadRepository {
    Lead save(Lead lead);
    Optional<Lead> findById(Long id);
    boolean existsByPhoneAndCreatedAtAfter(String phone, Instant since);
    Optional<Lead> findLastAssigned();
    List<Lead> findAll(LeadSearchCondition condition);
    long countAll(LeadSearchCondition condition);
}
