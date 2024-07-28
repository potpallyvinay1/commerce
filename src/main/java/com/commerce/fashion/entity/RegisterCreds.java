package com.commerce.fashion.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NonNull
@NotEmpty
@Document(collection = "userRegistrationDetails")
public class RegisterCreds {
    @Indexed
    @Id
    private String mailId;
    private String userName;
    private String password;
    private String confirmPassword;
    private String mobileNumberWithCountryCode;
}
