package com.commerce.fashion.controller;

import com.commerce.fashion.entity.RegisterCreds;
import com.commerce.fashion.entity.SignInCreds;
import com.commerce.fashion.enums.UserResponse;
import com.commerce.fashion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RequiredArgsConstructor
@RestController
public class userController {
    private final UserService userService;

    @PostMapping("/signIn")
    public UserResponse signIn(@RequestBody SignInCreds signInCreds){
        return userService.signIn(signInCreds);
    }
    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterCreds registerCreds){
        return userService.register(registerCreds);
    }

}
