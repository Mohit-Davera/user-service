package com.testingmehta.user_service.controller;


import com.testingmehta.user_service.dto.UserDto;
import com.testingmehta.user_service.entity.User;
import com.testingmehta.user_service.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // POST /users -> save user
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User saved = userRepository.save(user);
        // return 201 Created with Location header
        return ResponseEntity
                .created(URI.create("/users/" + saved.getId()))
                .body(saved);
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

