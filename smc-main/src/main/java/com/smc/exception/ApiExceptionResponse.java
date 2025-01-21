package com.smc.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiExceptionResponse {
    private  String responseDescription;
    private String responseCode;
}
