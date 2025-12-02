package com.testingmehta.user_service.dto;

import com.testingmehta.user_service.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    List<User> data;
    int pages;
    long total;
}
