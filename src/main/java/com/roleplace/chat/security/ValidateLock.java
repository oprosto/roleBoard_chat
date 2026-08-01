package com.roleplace.chat.security;

import com.roleplace.chat.users.models.User;
import com.roleplace.chat.users.models.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import security.lib.token.model.TokenValidator;

import java.util.UUID;

@AllArgsConstructor
@Component
public class ValidateLock implements TokenValidator {
    UserRepository userRepository;
    //TODO подумать над тем, чтобы урать этот валидатор т.к. дергать базу при каждой отправке сообщения - такое

    @Override
    public void validate(Claims claims) {
        User user;
        try {
            user = userRepository.findById(UUID.fromString(claims.getSubject())).orElse(null);
        } catch (IllegalArgumentException e) {
            throw new JwtException("Token subject is invalid", e);
        }
        if (user == null)
            throw new JwtException("Token subject is invalid");
        if (Boolean.TRUE.equals(user.getLocked()))
            throw new JwtException("User is blocked");
    }
}
