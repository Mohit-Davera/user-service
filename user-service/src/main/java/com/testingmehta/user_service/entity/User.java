package com.testingmehta.user_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class User {

    // Removed columnDefinition = "serial"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Email
    @NotBlank
    // Removed @Column(nullable = false) for redundancy as @NotBlank implies it for constraints
    @Column(unique = true)
    private String email;

    private String gender;

    // Explicitly named for consistency and set nullable = false
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    private String address;
}