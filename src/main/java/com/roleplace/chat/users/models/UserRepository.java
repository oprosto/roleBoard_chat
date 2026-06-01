package com.roleplace.chat.users.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findFirstByUserTag(String tag);
    User findFirstById(UUID userId);

    @Query("SELECT u.id FROM User u WHERE u.id IN :ids")
    Set<UUID> findAllById(@Param("ids") List<UUID> ids);
}
