package kr.goodmobilcar.infrastructure.persistence;

import kr.goodmobilcar.domain.admin.Admin;
import kr.goodmobilcar.domain.admin.AdminRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepositoryImpl implements AdminRepository {

    private final AdminJpaRepository jpaRepo;

    public AdminRepositoryImpl(AdminJpaRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Admin save(Admin admin) {
        return jpaRepo.save(AdminJpaEntity.fromDomain(admin)).toDomain();
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return jpaRepo.findById(id).map(AdminJpaEntity::toDomain);
    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        return jpaRepo.findByEmail(email).map(AdminJpaEntity::toDomain);
    }

    @Override
    public List<Admin> findAllActive() {
        Specification<AdminJpaEntity> spec = (root, query, cb) -> cb.isTrue(root.get("active"));
        return jpaRepo.findAll(spec).stream().map(AdminJpaEntity::toDomain).toList();
    }

    @Override
    public List<Admin> findAllLiveOrderByPriority() {
        Specification<AdminJpaEntity> spec = (root, query, cb) -> cb.and(
                cb.isTrue(root.get("live")),
                cb.isTrue(root.get("active"))
        );
        return jpaRepo.findAll(spec, org.springframework.data.domain.Sort.by("orderPriority"))
                .stream().map(AdminJpaEntity::toDomain).toList();
    }

    @Override
    public void delete(Long id) {
        jpaRepo.deleteById(id);
    }
}
