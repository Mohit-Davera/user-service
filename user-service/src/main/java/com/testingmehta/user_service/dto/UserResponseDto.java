package com.testingmehta.user_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String address;
    private boolean isActive = true;

    public UserResponseDto(Long id, String name, String email, String gender, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.address = address;
    }
}
