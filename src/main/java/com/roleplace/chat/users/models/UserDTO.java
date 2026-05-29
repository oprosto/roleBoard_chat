package com.roleplace.chat.users.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class UserDTO {
    private UUID id;
    private String username;

    public UserDTO() {}
    public UserDTO(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

}