package ru.absens.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.absens.market.error_handling.ResourceNotFoundException;
import ru.absens.market.models.User;
import ru.absens.market.models.UserShippingInfo;
import ru.absens.market.services.UserService;
import ru.absens.market.services.UserShippingInfoService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user-shipping")
@RequiredArgsConstructor
public class UserShippingInfoController {
    private final UserShippingInfoService userShippingInfoService;
    private final UserService userService;

    @GetMapping
    @Transactional
    public UserShippingInfo getUserShippingInfo(Principal principal) {
        if (principal == null) return new UserShippingInfo();
        User currentUser = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));
        // todo fix get user in UserShippingInfo (maybe need dto)
        return userShippingInfoService.findByUserId(currentUser.getId()).orElse(new UserShippingInfo());
    }

    @PutMapping
    @Transactional
    public UserShippingInfo saveUserShippingInfo(Principal principal, @RequestBody @Validated UserShippingInfo userShippingInfo) {
        if (userShippingInfo.getUser() == null) {
            User currentUser = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));
            userShippingInfo.setUser(currentUser);
        }
        return userShippingInfoService.save(userShippingInfo);
    }

}
