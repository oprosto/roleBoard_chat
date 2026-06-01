package com.roleplace.chat.aggregators.membership.services;

import com.roleplace.chat.aggregators.membership.Membership;
import com.roleplace.chat.aggregators.membership.MembershipId;
import com.roleplace.chat.chats.models.Chat;
import com.roleplace.chat.users.services.UserDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MembershipService {
    private final UserDataService userDataService;

    public Membership createMembership(Chat chat, UUID userId) {
        return new Membership(new MembershipId(userId, chat.getId()),
                userDataService.getReferenceById(userId), chat);
    }

    public List<Membership> createMemberships(Chat chat, List<UUID> userIds) {
        List<Membership> memberships = new ArrayList<>();
        for (UUID userId : userIds)
            memberships.add(createMembership(chat, userId));
        return memberships;
    }
}
