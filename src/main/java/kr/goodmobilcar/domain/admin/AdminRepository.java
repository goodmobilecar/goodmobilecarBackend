package kr.goodmobilcar.domain.admin;

import java.util.List;
import java.util.Optional;

public interface AdminRepository {
    Admin save(Admin admin);
    Optional<Admin> findById(Long id);
    Optional<Admin> findByEmail(String email);
    List<Admin> findAllActive();
    List<Admin> findAllLiveOrderByPriority();
    void delete(Long id);
}
