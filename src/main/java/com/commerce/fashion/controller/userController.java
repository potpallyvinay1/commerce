package com.commerce.fashion.controller;

import com.commerce.fashion.entity.*;
import com.commerce.fashion.enums.UserResponse;
import com.commerce.fashion.service.UserService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @PostMapping("/{isUsernameIsEmailId}/register")
    public UserResponse register(@RequestBody RegisterCreds registerCreds, @PathVariable boolean isUsernameIsEmailId){
        return userService.register(registerCreds,isUsernameIsEmailId);
    }
    @PostMapping("/{emailId}/{sameForBilling}/addShippingAddress")
    public CustomerData addShippingAddress(@RequestBody Address address, @PathVariable String emailId, @PathVariable boolean sameForBilling){
        return userService.addShippingAddresswithBilling(address,emailId,sameForBilling);
    }
    @PostMapping("/{emailId}/addBillingAddress")
    public CustomerData addBillingAddress(@RequestBody Address address, @PathVariable String emailId){
        return userService.addBillingAddress(address,emailId);
    }
    @GetMapping("/isUserExisting/{emailId}")
    public boolean isUserExisting(@PathVariable String emailId){
        return userService.checkUserExistance(emailId).isPresent();
    }
    @GetMapping("/getCustomerData/{emailId}")
    public CustomerData getCustomerData(@PathVariable String emailId){
        return userService.getCustomerData(emailId);
    }
    @DeleteMapping("/{emailId}/removeShippingAddress")
    public CustomerData removeShippingAddress(@RequestBody List<Address> address, @PathVariable String emailId){
        return userService.removeShippingAddress(address,emailId);
    }
    @DeleteMapping("/{emailId}/removeBillingAddress")
    public CustomerData removeBillingAddress(@RequestBody List<Address> address, @PathVariable String emailId){
        return userService.removeBillingAddress(address,emailId);
    }
    @PutMapping("/{emailId}/updateShippingAddress")
    public CustomerData updateShippingAddress(@RequestBody AddressUpdateRequest addressUpdateRequest,
                                              @PathVariable String emailId){
        return userService.updateShippingAddress(addressUpdateRequest.getNewAddress(),
                addressUpdateRequest.getExistingAddress(),emailId);
    }
    @PutMapping("/{emailId}/updateBillingAddress")
    public CustomerData updateBillingAddress(@RequestBody AddressUpdateRequest addressUpdateRequest,
                                             @PathVariable String emailId){
        return userService.updateBillingAddress(addressUpdateRequest.getNewAddress(),
                addressUpdateRequest.getExistingAddress(),emailId);
    }


}
