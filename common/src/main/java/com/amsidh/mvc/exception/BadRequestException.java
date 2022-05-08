package com.amsidh.mvc.exception;

import lombok.Data;

import java.io.Serializable;

@Data
public class BadRequestException extends RuntimeException implements Serializable {
    private final Integer status;
    private final String message;
}
