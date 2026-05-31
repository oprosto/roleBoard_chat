package com.roleplace.chat.users.models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findFirstByUserTag(String tag);
    User findFirstById(UUID userId);
}
