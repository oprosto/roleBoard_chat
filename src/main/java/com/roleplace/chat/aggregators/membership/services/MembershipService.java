package com.roleplace.chat.aggregators.membership.services;

import com.roleplace.chat.aggregators.membership.Membership;
import com.roleplace.chat.aggregators.membership.MembershipId;
import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.users.services.UserDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MembershipService {
    private final UserDataService userDataService;

    public Membership createMembership(Chat chat, UUID userId) {
        return new Membership(new MembershipId(userId, chat.getId()),
                userDataService.getReferenceById(userId), chat);
    }

    public Set<Membership> createMemberships(Chat chat, Set<UUID> userIds) {
        Set<Membership> memberships = new HashSet<>();
        for (UUID userId : userIds)
            memberships.add(createMembership(chat, userId));
        return memberships;
    }
}
