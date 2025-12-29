package com.opencode.alumxbackend.groupchatmessages.controller;

import com.opencode.alumxbackend.groupchatmessages.dto.GroupMessageResponse;
import com.opencode.alumxbackend.groupchatmessages.dto.SendGroupMessageRequest;
import com.opencode.alumxbackend.groupchatmessages.service.GroupMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupMessageController {

    private final GroupMessageService service;


    // user id sends mesesage to a group using a group id
    @PostMapping(value="/{groupId}/messages",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessage(
            @PathVariable Long groupId,
            @RequestBody @Valid SendGroupMessageRequest request
    ) {
        return ResponseEntity.ok(service.sendMessage(groupId, request));
    }
    // working
    @GetMapping("/{groupId}/messages")
    public List<GroupMessageResponse> getMessages(
            @PathVariable Long groupId,
            @RequestParam Long userId
    ) {
        return service.fetchMessages(groupId, userId);
    }
}
