package com.commerce.fashion.service.impl;

import com.commerce.fashion.entity.RegisterCreds;
import com.commerce.fashion.entity.SignInCreds;
import com.commerce.fashion.Repository.RegistrationRepository;
import com.commerce.fashion.Repository.SignInRepository;
import com.commerce.fashion.enums.UserResponse;
import com.commerce.fashion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final SignInRepository signInRepository;
    private final RegistrationRepository registrationRepository;
    @Override
    public UserResponse signIn(SignInCreds userCreds) {
        Optional<SignInCreds> retrieved = signInRepository.findById(userCreds.getMailId());
        try {
            boolean isLoginSuccess = retrieved.get().getPassword().equals(userCreds.getPassword());
            if(isLoginSuccess) {
                return UserResponse.SUCCESS;
            }
            else {
                return UserResponse.ACCOUNT_DOESNOT_EXIST_CREATE_AN_ACCOUT;
            }
        }
        catch(Exception ex){
            return UserResponse.FAILED;
        }
    }

    @Override
    public UserResponse register(RegisterCreds registerCreds) {
        Optional<SignInCreds> retrieved = signInRepository.findById(registerCreds.getMailId());
        if(retrieved.isPresent()){
            return UserResponse.ACCOUNT_ALREADY_EXISTS;
        }
        if(!registerCreds.getPassword().equals(registerCreds.getConfirmPassword())){
            return UserResponse.PASSWORD_AND_CONFIRMPASSWORD_DOESNT_MATCH;
        }
        try {
            registrationRepository.save(registerCreds);
            SignInCreds signIncreds = new SignInCreds(registerCreds.getMailId(), registerCreds.getPassword());
            signInRepository.save(signIncreds);
            return UserResponse.SUCCESS;
        }
        catch(Exception ex){
            return UserResponse.FAILED;
        }
    }
}
