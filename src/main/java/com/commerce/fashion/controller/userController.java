package com.commerce.fashion.controller;

import com.commerce.fashion.entity.Address;
import com.commerce.fashion.entity.CustomerData;
import com.commerce.fashion.entity.RegisterCreds;
import com.commerce.fashion.entity.SignInCreds;
import com.commerce.fashion.enums.UserResponse;
import com.commerce.fashion.service.UserService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    @PostMapping("/{emailId}/addShippingAddress")
    public CustomerData addShippingAddress(@RequestBody Address address, @PathVariable String emailId){
        return userService.addShippingAddress(address,emailId);
    }
    @GetMapping("/isUserExisting/{mailId}")
    public boolean isUserExisting(@PathVariable String mailId){
        return userService.checkUserExistance(mailId).isPresent();
    }

}
