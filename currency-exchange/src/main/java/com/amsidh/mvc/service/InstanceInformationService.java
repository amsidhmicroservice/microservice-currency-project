package com.amsidh.mvc.service;

import com.amsidh.mvc.exception.MyCustomException;

public interface InstanceInformationService {
    String retrieveInstanceInfo();

    void throwException() throws MyCustomException;
}
