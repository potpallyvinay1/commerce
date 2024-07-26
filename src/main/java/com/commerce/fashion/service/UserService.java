package com.commerce.fashion.service;
import com.commerce.fashion.entity.RegisterCreds;
import com.commerce.fashion.entity.SignInCreds;
import com.commerce.fashion.enums.UserResponse;

public interface UserService {
    UserResponse signIn(SignInCreds signInCreds);

    UserResponse register(RegisterCreds registerCreds);
}
