package com.commerce.fashion.service.impl;

import com.commerce.fashion.Repository.CustomerDataRepository;
import com.commerce.fashion.entity.Address;
import com.commerce.fashion.entity.CustomerData;
import com.commerce.fashion.entity.RegisterCreds;
import com.commerce.fashion.entity.SignInCreds;
import com.commerce.fashion.Repository.RegistrationRepository;
import com.commerce.fashion.Repository.SignInRepository;
import com.commerce.fashion.enums.UserResponse;
import com.commerce.fashion.exception.CustomEntityNotFoundException;
import com.commerce.fashion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final SignInRepository signInRepository;
    private final RegistrationRepository registrationRepository;
    private final CustomerDataRepository customerDataRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserResponse signIn(SignInCreds userCreds) {
        Optional<SignInCreds> retrieved = checkUserExistance(userCreds.getMailId());
        try {
            if (retrieved.isEmpty()) {
                return UserResponse.ACCOUNT_DOESNOT_EXIST_CREATE_AN_ACCOUT;
            } else {
                if (!retrieved.get().getPassword().equals(userCreds.getPassword())) {
                    return UserResponse.INCORRECT_PASSWORD;
                } else {
                    return UserResponse.SUCCESS;
                }
            }
        } catch (Exception ex) {
            logger.error("An error occurred during sign in: ", ex);
            return UserResponse.FAILED;
        }
    }

    @Override
    @Transactional
    public UserResponse register(RegisterCreds registerCreds) {
        if (checkUserExistance(registerCreds.getMailId()).isPresent()) {
            return UserResponse.ACCOUNT_ALREADY_EXISTS;
        }
        if (!registerCreds.getPassword().equals(registerCreds.getConfirmPassword())) {
            return UserResponse.PASSWORD_AND_CONFIRMPASSWORD_DOESNT_MATCH;
        }
        try {
            registrationRepository.save(registerCreds);
            SignInCreds signInCreds = new SignInCreds(registerCreds.getMailId(), registerCreds.getPassword());
            signInRepository.save(signInCreds);
            CustomerData customerData = new CustomerData();
            customerData.setEmailId(registerCreds.getMailId());
            customerData.setMobileNumber(registerCreds.getMobileNumberWithCountryCode());
            customerDataRepository.save(customerData);
            return UserResponse.SUCCESS;
        } catch (Exception ex) {
            logger.error("An error occurred during registration: ", ex);
            return UserResponse.FAILED;
        }
    }

    @Override
    public Optional<SignInCreds> checkUserExistance(String mailId) {
        return signInRepository.findById(mailId);
    }

    @Override
    public CustomerData addShippingAddress(Address address, String emailId) {
        CustomerData existingData = customerDataRepository.findByEmailId(emailId);
        if (existingData == null) {
            logger.error("Customer not found with email: {}", emailId);
            throw new CustomEntityNotFoundException("Customer not found with email: " + emailId);
        }
        List<Address> shippingAddress = new ArrayList<>(existingData.getShippingAddress());
        shippingAddress.add(address);
        existingData.setShippingAddress(shippingAddress);
        return customerDataRepository.save(existingData);
    }
}
