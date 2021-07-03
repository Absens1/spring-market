package ru.absens.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.absens.market.dtos.JwtRegRequest;
import ru.absens.market.dtos.UserDto;
import ru.absens.market.error_handling.ResourceNotFoundException;
import ru.absens.market.models.Role;
import ru.absens.market.models.User;
import ru.absens.market.repositories.UserRepository;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Transactional
    public UserDto updateUserInfo(Principal principal, String firstName, String lastName) {
        User currentUser = findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        return new UserDto(save(currentUser));
    }

    @Transactional
    public void changeUserPassword(Principal principal, String oldPassword, String newPassword, String repeatNewPassword) {
        User currentUser = findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(oldPassword, currentUser.getPassword())
                && newPassword.equals(repeatNewPassword)
                && !passwordEncoder.matches(newPassword, currentUser.getPassword())) {
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            save(currentUser);
        }
    }

    @Transactional
    public void createNewUser(JwtRegRequest regRequest) {
        User user = new User();
        user.setUsername(regRequest.getUsername());
        user.setPassword(regRequest.getPassword());
        user.setFirstName(regRequest.getFirstName());
        user.setLastName(regRequest.getLastName());
        List<Role> roles = new ArrayList<>(Arrays.asList(roleService.findByName("ROLE_USER").get()));
        user.setRoles(roles);
        userRepository.save(user);
    }
}