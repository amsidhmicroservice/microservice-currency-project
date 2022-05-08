package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.service.InstanceInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InstanceInformationServiceImpl implements InstanceInformationService {

    private static final String HOST_NAME = "HOSTNAME";

    @Value("${" + HOST_NAME + ":local}")
    private String nodeName;

    @Value("${info.app.version:NoVersion}")
    private String projectVersion;

    @Override
    public String retrieveInstanceInfo() {
        log.debug("Inside retrieveInstanceInfo method of InstanceInformationService in currency-conversion service " + projectVersion + " : " + nodeName);
        return projectVersion + " : " + nodeName;
    }
}
