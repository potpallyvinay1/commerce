package com.commerce.fashion.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private boolean makeDefault;
    private String name;
    private String line1;
    private String line2;
    private String city;
    private String district;
    private Integer pinCode;
    private String state;
    private String country;
    private String mobileNumber;

    public Address(Address address) {
        this.makeDefault = address.isMakeDefault();
        this.name = address.getName();
        this.line1 = address.getLine1();
        this.line2 = address.getLine2();
        this.city = address.getCity();
        this.district = address.getDistrict();
        this.pinCode = address.getPinCode();
        this.state = address.getState();
        this.country = address.getCountry();
        this.mobileNumber = address.getMobileNumber();
    }
}
