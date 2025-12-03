package com.testingmehta.user_service.controller;


import com.testingmehta.user_service.dto.UserDto;
import com.testingmehta.user_service.dto.UserRequest;
import com.testingmehta.user_service.dto.UserResponseDto;
import com.testingmehta.user_service.entity.User;
import com.testingmehta.user_service.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setGender(userRequest.getGender());
        user.setAddress(userRequest.getAddress());
        user.setEmail(userRequest.getEmail());

        try {
            User saved = userRepository.save(user);
            URI location = URI.create("/users/" + saved.getId());
            UserResponseDto response = new UserResponseDto(saved.getId(), saved.getName(), saved.getEmail(), saved.getGender(), saved.getAddress());
            return ResponseEntity.created(location).body(response); // 201 Created + Location header
        } catch (DataIntegrityViolationException e) {
            // return a clear error response; could include field/constraint info (don't leak internals)
            Map<String, String> error = Map.of("error", "Email already exists or constraint violated");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error); // 409
        }
    }


    // GET /users/{id} -> get user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<UserDto> getAllUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> userPage = userRepository.findAll(pageable);
        UserDto dto = new UserDto(userPage.getContent(),userPage.getTotalPages(), userPage.getTotalElements());

        return ResponseEntity.ok(dto);
    }
}

