package trackit.trackit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import trackit.trackit.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<AppUser> findByEmail(String email);
}
