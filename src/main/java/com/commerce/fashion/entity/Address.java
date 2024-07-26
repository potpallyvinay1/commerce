package com.commerce.fashion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "userAddressData")
public class Address {
    String Name;
    String line1;
    String line2;
    String city;
    String district;
    Integer pinCode;
    String state;
    String country;
    String mobileNumber;
}
