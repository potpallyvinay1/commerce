package com.commerce.fashion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "userProfileData")
public class UserProfile {
    @Id
    String userName;
    @Indexed
    String emailId;
    String mobileNumber;
    List<Address> shippingAddress;
    List<Address> billingAddress;
}
