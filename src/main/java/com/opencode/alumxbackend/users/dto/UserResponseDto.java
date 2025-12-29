package com.opencode.alumxbackend.users.dto;

import com.opencode.alumxbackend.users.model.UserRole;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private UserRole role;
    private boolean profileCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Profile fields
    private List<String> skills;
    private List<String> education;
    private List<String> techStack;
    private List<String> languages;
    private List<String> frameworks;
    private List<String> communicationSkills;
    private List<String> certifications;
    private List<String> projects;
    private List<String> softSkills;
    private List<String> hobbies;
    private List<String> experience;
    private List<String> internships;
}
