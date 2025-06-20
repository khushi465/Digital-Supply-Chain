package com.supplytracker.Repository;

import com.supplytracker.Entity.User;
import com.supplytracker.Enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    long countByRole(Role role);
}
