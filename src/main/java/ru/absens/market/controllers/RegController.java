package ru.absens.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.absens.market.dtos.JwtRegRequest;
import ru.absens.market.dtos.JwtResponse;
import ru.absens.market.error_handling.MarketError;
import ru.absens.market.services.UserService;
import ru.absens.market.utils.JwtTokenUtil;

@RestController
@RequiredArgsConstructor
public class RegController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/reg")
    @Transactional
    public ResponseEntity<?> register(@RequestBody JwtRegRequest regRequest) {
        if(userService.findByUsername(regRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>(new MarketError(HttpStatus.CREATED.value(), "Username already exists"), HttpStatus.CREATED);
        }
        try {
            String pass = regRequest.getPassword();
            regRequest.setPassword(passwordEncoder.encode(regRequest.getPassword()));
            userService.createNewUser(regRequest);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(regRequest.getUsername(), pass));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new MarketError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(regRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
