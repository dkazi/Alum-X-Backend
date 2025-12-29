package com.opencode.alumxbackend.groupchat.controller;

import com.opencode.alumxbackend.groupchat.dto.GroupChatRequest;
import com.opencode.alumxbackend.groupchat.dto.GroupChatResponse;
import com.opencode.alumxbackend.groupchat.model.GroupChat;
import com.opencode.alumxbackend.groupchat.model.Participant;
import com.opencode.alumxbackend.groupchat.service.GroupChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/group-chats")
@RequiredArgsConstructor
public class GroupChatController {

    private final GroupChatService service;

    // Create a group
    // working
    @PostMapping
    public ResponseEntity<GroupChatResponse> createGroup(@Valid @RequestBody GroupChatRequest request) {
        GroupChat group = service.createGroup(request);
        return ResponseEntity.ok(mapToResponse(group));
    }

    // Get group by groupId
    //working
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupChatResponse> getGroupById(@PathVariable Long groupId) {
        GroupChat group = service.getGroupById(groupId);
        return ResponseEntity.ok(mapToResponse(group));
    }

    // Get all groups for a user
    //working
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getGroupsForUser(@PathVariable String userId) {
        var groups = service.getGroupsForUser(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
        return ResponseEntity.ok(groups);
    }

    // Helper method to map entity → DTO
    private GroupChatResponse mapToResponse(GroupChat group) {
        return GroupChatResponse.builder()
                .groupId(group.getGroupId())
                .name(group.getGroupName())
                .participants(group.getParticipants())
                .build();
    }
}
