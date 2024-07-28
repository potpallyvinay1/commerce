package com.commerce.fashion.exception;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {

    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

}
