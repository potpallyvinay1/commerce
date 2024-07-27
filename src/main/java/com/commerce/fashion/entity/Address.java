package com.commerce.fashion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    private String addressId;
    private String name;
    private String line1;
    private String line2;
    private String city;
    private String district;
    private Integer pinCode;
    private String state;
    private String country;
    private String mobileNumber;
}
