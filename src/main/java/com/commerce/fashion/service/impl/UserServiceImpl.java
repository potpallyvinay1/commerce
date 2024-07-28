package com.commerce.fashion.service.impl;

import com.commerce.fashion.Repository.CustomerDataRepository;
import com.commerce.fashion.entity.Address;
import com.commerce.fashion.entity.CustomerData;
import com.commerce.fashion.entity.RegisterCreds;
import com.commerce.fashion.entity.SignInCreds;
import com.commerce.fashion.Repository.RegistrationRepository;
import com.commerce.fashion.Repository.SignInRepository;
import com.commerce.fashion.enums.UserResponse;
import com.commerce.fashion.exception.*;
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
    public UserResponse register(RegisterCreds registerCreds, boolean isUsernameIsEmailId) {
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
            if(isUsernameIsEmailId){
                customerData.setUserName(registerCreds.getMailId());
            }
            else{
                customerData.setUserName(registerCreds.getUserName());
            }
            customerDataRepository.save(customerData);
            return UserResponse.SUCCESS;
        } catch (Exception ex) {
            logger.error("An error occurred during registration: ", ex);
            return UserResponse.FAILED;
        }
    }

    @Override
    public Optional<SignInCreds> checkUserExistance(String emailId) {
        return signInRepository.findById(emailId);
    }

    @Override
    public CustomerData addShippingAddresswithBilling(Address address, String emailId,boolean sameForBilling) {
    if(sameForBilling){
        addShippingAddress(address,emailId);
        return addBillingAddress(address,emailId);
    }
    else{
        return addShippingAddress(address,emailId);
    }
    }
    public CustomerData addShippingAddress(Address address, String emailId) {
        CustomerData existingData = getExistingData(emailId);
        Address shippingAddressVal = new Address(address);
        if (existingData == null) {
            logger.error("Customer not found with email: {}", emailId);
            throw new CustomEntityNotFoundException("Customer not found with email: " + emailId);
        }
        List<Address> shippingAddress = new ArrayList<>();
        if (existingData.getShippingAddress() != null) {
            shippingAddress.addAll(existingData.getShippingAddress());
        }
        if (shippingAddress.contains(shippingAddressVal)) {
            logger.error("Duplicate shipping address found, unable to add the ShippingAddress");
            throw new DuplicateShippingAddressException("Duplicate shipping address found, unable to add ShippingAddress");
        }
        if (shippingAddressVal.isMakeDefault()) {
            shippingAddress.forEach(addr -> addr.setMakeDefault(false));
        }
        if(shippingAddress.size() == 0) {
            shippingAddressVal.setMakeDefault(true);
        }
        shippingAddress.add(shippingAddressVal);
        existingData.setShippingAddress(shippingAddress);
        customerDataRepository.save(existingData);
        return existingData;
    }

    @Override
    public CustomerData addBillingAddress(Address address, String emailId) {
        Address billingAddressVal = new Address(address);
        CustomerData existingData = getExistingData(emailId);
        if (existingData == null) {
            logger.error("Customer not found with email: {}", emailId);
            throw new CustomEntityNotFoundException("Customer not found with email: " + emailId);
        }
        List<Address> billingAddress = new ArrayList<>();
        if(existingData.getBillingAddress()!= null){
            billingAddress.addAll(existingData.getBillingAddress());
        }
        if(billingAddress.contains(billingAddressVal)){
            logger.error("Duplicate Billing address found, unable to add the BillingAddress");
            throw new DuplicateBillingAddressException("Duplicate Billing address found, unable to add the BillingAddress");
        }
        if (billingAddressVal.isMakeDefault()) {
            billingAddress.forEach(addr -> addr.setMakeDefault(false));
        }
        if(billingAddress.size() == 0) {
            billingAddressVal.setMakeDefault(true);
        }
        billingAddress.add(billingAddressVal);
        existingData.setBillingAddress(billingAddress);
        customerDataRepository.save(existingData);
        return existingData;
    }

    @Override
    public CustomerData removeShippingAddress(List<Address> address, String emailId) {
        CustomerData existingData = getExistingData(emailId);
        if (existingData == null) {
            logger.error("Customer not found with email: {}", emailId);
            throw new CustomEntityNotFoundException("Customer not found with email: " + emailId);
        }
        address.forEach(givenAddress -> {
            if(givenAddress.isMakeDefault() && existingData.getShippingAddress().size() > address.size()){
                existingData.getShippingAddress().getFirst().setMakeDefault(true);
            }
            if(existingData.getShippingAddress() != null && existingData.getShippingAddress().contains(givenAddress)){
                existingData.getShippingAddress().remove(givenAddress);
            }
            else{
                throw new ExistingShippingAddressNotFoundException
                        ("The given shipping address not found, unable to delete");
            }
        });
        customerDataRepository.save(existingData);
        return existingData;
    }
    @Override
    public CustomerData removeBillingAddress(List<Address> address, String emailId) {
        CustomerData existingData = getExistingData(emailId);
        if (existingData == null) {
            logger.error("Customer not found with email: {}", emailId);
            throw new CustomEntityNotFoundException("Customer not found with email: " + emailId);
        }
        address.forEach(givenAddress -> {
            if(givenAddress.isMakeDefault() && existingData.getBillingAddress().size() > address.size()){
                existingData.getBillingAddress().getFirst().setMakeDefault(true);
            }
            if(existingData.getBillingAddress() != null && existingData.getBillingAddress().contains(givenAddress)){
                existingData.getBillingAddress().remove(givenAddress);
            }
            else{
                throw new ExistingBillingAddressNotFoundException
                        ("The given billing address not found, unable to delete");
            }
        });
        customerDataRepository.save(existingData);
        return existingData;
    }

    @Override
    public CustomerData getCustomerData(String emailId) {
        CustomerData existingData = getExistingData(emailId);
        if (existingData == null) {
            logger.error("Customer not found with email: {}", emailId);
            throw new CustomEntityNotFoundException("Customer not found with email: " + emailId);
        }
        return existingData;
    }

    @Override
    public CustomerData updateShippingAddress(Address newAddress, Address existingAddress, String emailId) {
        CustomerData existingData = getExistingData(emailId);
        if(!existingData.getShippingAddress().contains(existingAddress)){
            logger.error("The given existing shipping address not found, unable to update");
           throw new ExistingShippingAddressNotFoundException
                   ("The given existing shipping address not found, unable to update");
        }
        if (newAddress.isMakeDefault()) {
            existingData.getShippingAddress().forEach(addr -> addr.setMakeDefault(false));
        }
        existingData.getShippingAddress().stream()
                .filter(extAddress -> extAddress.equals(existingAddress))
                .findFirst()
                .ifPresent(existingData.getShippingAddress()::remove);
        existingData.getShippingAddress().add(newAddress);
        return existingData;
    }

    @Override
    public CustomerData updateBillingAddress(Address newAddress, Address existingAddress, String emailId) {
        CustomerData existingData = getExistingData(emailId);
        if(!existingData.getBillingAddress().contains(existingAddress)){
            logger.error("The given existing billing address not found, unable to update");
            throw new ExistingBillingAddressNotFoundException
                    ("The given existing billing address not found, unable to update");
        }
        if (newAddress.isMakeDefault()) {
            existingData.getShippingAddress().forEach(addr -> addr.setMakeDefault(false));
        }
        existingData.getBillingAddress().stream()
                .filter(extAddress -> extAddress.equals(existingAddress))
                .findFirst()
                .ifPresent(existingData.getBillingAddress()::remove);
        existingData.getBillingAddress().add(newAddress);
        return existingData;
    }

    public CustomerData getExistingData (String emailId){
        return customerDataRepository.findByEmailId(emailId);
    }
}
