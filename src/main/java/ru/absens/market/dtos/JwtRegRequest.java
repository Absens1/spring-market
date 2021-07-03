package ru.absens.market.dtos;

import lombok.Data;

@Data
public class JwtRegRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
