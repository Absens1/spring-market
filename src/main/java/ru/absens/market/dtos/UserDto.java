package ru.absens.market.dtos;

import lombok.Data;
import ru.absens.market.models.User;

@Data
public class UserDto {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
