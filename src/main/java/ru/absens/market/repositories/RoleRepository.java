package ru.absens.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.absens.market.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String title);
}
