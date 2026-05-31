package com.roleplace.chat.aggregators.membership;

import com.roleplace.chat.chats.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, MembershipId> {
    @Query("SELECT m.chat FROM Membership m WHERE m.user.id = :userId")
    List<Chat> findUserChats(@Param("userId") UUID userId);
}
