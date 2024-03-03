package com.irembo.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ExceptionPayload {
    public String message;
    public HttpStatus status;
}
