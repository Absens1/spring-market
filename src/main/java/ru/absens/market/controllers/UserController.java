package ru.absens.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.absens.market.dtos.UserDto;
import ru.absens.market.error_handling.ResourceNotFoundException;
import ru.absens.market.models.User;
import ru.absens.market.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserDto getCurrentUser(Principal principal) {
        // todo fix null principal
        User currentUser = principal != null ?
                userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist")) : new User();
        return new UserDto(currentUser);
    }

    @PutMapping
    public UserDto updateUserInfo(Principal principal, @RequestParam String firstName, @RequestParam String lastName) {
        return userService.updateUserInfo(principal, firstName, lastName);
    }

    @PutMapping("change-password")
    @Transactional
    public void changeUserPassword(Principal principal,
                                   @RequestParam String oldPassword,
                                   @RequestParam String newPassword,
                                   @RequestParam String repeatNewPassword) {
        userService.changeUserPassword(principal, oldPassword, newPassword, repeatNewPassword);
    }
}
