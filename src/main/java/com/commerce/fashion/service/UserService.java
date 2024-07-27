package com.commerce.fashion.service;
import com.commerce.fashion.entity.Address;
import com.commerce.fashion.entity.CustomerData;
import com.commerce.fashion.entity.RegisterCreds;
import com.commerce.fashion.entity.SignInCreds;
import com.commerce.fashion.enums.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse signIn(SignInCreds signInCreds);

    UserResponse register(RegisterCreds registerCreds,boolean isUsernameIsEmailId);

    Optional<SignInCreds> checkUserExistance(String emailId);

    CustomerData addShippingAddresswithBilling(Address address,String emailId,boolean sameForBilling);

    CustomerData addBillingAddress(Address address, String emailId);

    CustomerData removeShippingAddress(List<Address> address, String emailId);

    CustomerData removeBillingAddress(List<Address> address, String emailId);

    CustomerData getCustomerData(String emailId);

    CustomerData updateShippingAddress(Address newAddress, Address existingAddress, String emailId);

    CustomerData updateBillingAddress(Address newAddress, Address existingAddress, String emailId);
}
