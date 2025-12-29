package com.opencode.alumxbackend.users.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.opencode.alumxbackend.common.exception.Errors.BadRequestException;
import com.opencode.alumxbackend.users.dto.UserProfileDTO;
import com.opencode.alumxbackend.users.dto.UserRequest;
import com.opencode.alumxbackend.users.dto.UserResponseDto;
import com.opencode.alumxbackend.users.model.User;
import com.opencode.alumxbackend.users.model.UserRole;
import com.opencode.alumxbackend.users.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserRequest request) {

        // 1️⃣ Check uniqueness
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists: " + request.getEmail());
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists: " + request.getUsername());
        }

        // 2️⃣ Validate role
        UserRole role;
        try {
            role = UserRole.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role. Must be STUDENT, ALUMNI, or PROFESSOR.");
        }

        // 3️⃣ Optional: validate email format, password length etc.
        if (!request.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}(\\.[A-Za-z]{2,})?$")) {
            throw new BadRequestException("Invalid email format: " + request.getEmail());
        }

        if (request.getPassword().length() < 6) {
            throw new BadRequestException("Password must be at least 6 characters");
        }

        // 4️⃣ Create and save user
        User user = User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .profileCompleted(true) // default for dev
                .build();

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserProfileDTO getUserProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return mapToProfileDTO(user);

        // return userRepository.findById(id)
        // .map(user -> new UserProfileDTO(
        // user.getId(),
        // user.getUsername(),
        // user.getName(),
        // user.getEmail(),
        // user.getSkills(),
        // user.getEducation(),
        // user.getTechStack()

        // ));
    }

    @Override
    @Transactional
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    private UserResponseDto mapToResponseDTO(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .profileCompleted(user.isProfileCompleted())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .skills(copy(user.getSkills()))
                .education(copy(user.getEducation()))
                .techStack(copy(user.getTechStack()))
                .languages(copy(user.getLanguages()))
                .frameworks(copy(user.getFrameworks()))
                .communicationSkills(copy(user.getCommunicationSkills()))
                .certifications(copy(user.getCertifications()))
                .projects(copy(user.getProjects()))
                .softSkills(copy(user.getSoftSkills()))
                .hobbies(copy(user.getHobbies()))
                .experience(copy(user.getExperience()))
                .internships(copy(user.getInternships()))
                .build();
    }
    private UserProfileDTO mapToProfileDTO(User user) {
        return UserProfileDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .skills(copy(user.getSkills()))
                .education(copy(user.getEducation()))
                .techStack(copy(user.getTechStack()))
                .build();
    }

    private List<String> copy(List<String> list) {
        return list == null ? List.of() : List.copyOf(list);
    }

}
