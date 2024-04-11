package com.inexture.controller;


import com.gbs.common.dto.UserDTO;
import com.gbs.common.web.ApiResponse;
import com.inexture.repository.UserRepository;
import com.inexture.service.UserServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user/api/v1")
@RefreshScope
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserServices userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> saveUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(new ApiResponse(LocalDateTime.now(),
                HttpStatus.CREATED, "register user successfully", userService.saveUser(userDTO)), HttpStatus.CREATED);
    }

    @GetMapping("/all-user")
    public List getAllUser() {
        return userService.getAllUsers();
    }

}
