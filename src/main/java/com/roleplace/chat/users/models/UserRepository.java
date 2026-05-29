package com.roleplace.chat.users.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findFirstByUsername(String username);
    User findFirstById(UUID userId);
}
