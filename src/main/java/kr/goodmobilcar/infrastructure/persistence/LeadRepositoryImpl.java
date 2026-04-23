package kr.goodmobilcar.infrastructure.persistence;

import jakarta.persistence.criteria.Predicate;
import kr.goodmobilcar.domain.lead.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LeadRepositoryImpl implements LeadRepository {

    private final LeadJpaRepository jpaRepo;

    public LeadRepositoryImpl(LeadJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Lead save(Lead lead) {
        return jpaRepo.save(LeadJpaEntity.fromDomain(lead)).toDomain();
    }

    @Override
    public Optional<Lead> findById(Long id) {
        return jpaRepo.findById(id).map(LeadJpaEntity::toDomain);
    }

    @Override
    public boolean existsByPhoneAndCreatedAtAfter(String phone, Instant since) {
        return jpaRepo.count(
                (root, query, cb) -> cb.and(
                        cb.equal(root.get("phone"), phone),
                        cb.greaterThan(root.get("createdAt"), since),
                        cb.isFalse(root.get("deleted"))
                )
        ) > 0;
    }

    @Override
    public Optional<Lead> findLastAssigned() {
        return jpaRepo.findAll(
                        (root, query, cb) -> {
                            query.orderBy(cb.desc(root.get("createdAt")));
                            return cb.isNotNull(root.get("assigneeId"));
                        }
                ).stream()
                .findFirst()
                .map(LeadJpaEntity::toDomain);
    }

    @Override
    public List<Lead> findAll(LeadSearchCondition condition) {
        return jpaRepo.findAll(toSpec(condition),
                        org.springframework.data.domain.PageRequest.of(
                                condition.getPage(),
                                condition.getSize() == Integer.MAX_VALUE ? Integer.MAX_VALUE : condition.getSize(),
                                org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "createdAt")
                        ))
                .stream()
                .map(LeadJpaEntity::toDomain)
                .toList();
    }

    @Override
    public long countAll(LeadSearchCondition condition) {
        return jpaRepo.count(toSpec(condition));
    }

    private Specification<LeadJpaEntity> toSpec(LeadSearchCondition c) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!c.isIncludeDeleted()) {
                predicates.add(cb.isFalse(root.get("deleted")));
            }
            if (c.getDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), c.getDateFrom()));
            }
            if (c.getDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), c.getDateTo()));
            }
            if (c.getChannel() != null) {
                predicates.add(cb.equal(root.get("channel"), c.getChannel().name()));
            }
            if (c.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), c.getStatus().name()));
            }
            if (c.getAssigneeId() != null) {
                predicates.add(cb.equal(root.get("assigneeId"), c.getAssigneeId()));
            }
            if (c.getSearch() != null && !c.getSearch().isBlank()) {
                String pattern = "%" + c.getSearch() + "%";
                predicates.add(cb.or(
                        cb.like(root.get("customerName"), pattern),
                        cb.like(root.get("phone"), pattern)
                ));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
