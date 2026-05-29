package com.roleplace.chat.security;

import com.roleplace.chat.users.models.User;
import com.roleplace.chat.users.models.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import security.lib.token.model.TokenValidator;

@AllArgsConstructor
@Component
public class ValidateLock implements TokenValidator {
    UserRepository userRepository;
    //TODO подумать над тем, чтобы урать этот валидатор т.к. дергать базу при каждой отправке сообщения - такое

    @Override
    public void validate(Claims claims) {
        User user = userRepository.findFirstByUsername(claims.getSubject());
        if (user.isLocked())
            throw new JwtException("User is blocked");
    }
}
