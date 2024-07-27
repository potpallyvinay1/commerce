package com.commerce.fashion.service;
import com.commerce.fashion.entity.Address;
import com.commerce.fashion.entity.CustomerData;
import com.commerce.fashion.entity.RegisterCreds;
import com.commerce.fashion.entity.SignInCreds;
import com.commerce.fashion.enums.UserResponse;

import java.util.Optional;

public interface UserService {
    UserResponse signIn(SignInCreds signInCreds);

    UserResponse register(RegisterCreds registerCreds);

    Optional<SignInCreds> checkUserExistance(String mailId);

    CustomerData addShippingAddress(Address address,String emailId);
}
