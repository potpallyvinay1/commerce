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
@Document(collection = "userSignInDetails")
public class SignInCreds {
    @Indexed
    @Id
    private String mailId;
    private String password;
}
