package ru.absens.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.absens.market.models.Role;
import ru.absens.market.repositories.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
