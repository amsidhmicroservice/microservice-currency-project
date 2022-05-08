package com.amsidh.mvc.client;

import com.amsidh.mvc.exception.BadRequestException;
import com.amsidh.mvc.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 400:
                return new BadRequestException(response.status(), "Bad Request");
            case 404:
                return new NotFoundException(response.status(), "Not Found");
            default:
                return new Exception("Generic error");
        }
    }
}
