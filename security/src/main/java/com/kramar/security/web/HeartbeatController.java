package com.kramar.security.web;

import com.kramar.data.dto.Heartbeat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(HeartbeatController.REQUEST_MAPPING)
public class HeartbeatController {

    public static final String REQUEST_MAPPING = "/oauth/heartbeat";

    @Value("${spring.application.name:authserver-app}")
    private String appName;

    @GetMapping
    public Heartbeat getHeartbeat() {
        return new Heartbeat(appName);
    }
}
