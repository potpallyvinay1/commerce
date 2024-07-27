package com.commerce.fashion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customerData")
public class CustomerData {
    @Id
    private String customerId;
    private String userName;
    @Indexed
    private String emailId;
    private String mobileNumber;
    @JsonIgnore
    private List<Address> shippingAddress;
    @JsonIgnore
    private List<Address> billingAddress;
}
