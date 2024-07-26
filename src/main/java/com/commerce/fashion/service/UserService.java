package com.commerce.fashion.service;
import com.commerce.fashion.Entity.RegisterCreds;
import com.commerce.fashion.Entity.SignInCreds;
import com.commerce.fashion.enums.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface UserService {
    UserResponse signIn(SignInCreds signInCreds);

    UserResponse register(RegisterCreds registerCreds);
}
