package com.kramar.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kramar.data.util.CommonUtils;
import lombok.Getter;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@JsonInclude(NON_NULL)
public class Heartbeat {

    private Long currentTimeStamp;
    private Long uptime;
    private String moduleName;

    public Heartbeat(final String moduleName) {
        this.moduleName = moduleName;
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        this.uptime = rb.getUptime();
        this.currentTimeStamp = CommonUtils.nowUtc().toInstant(CommonUtils.UTC_OFFSET).toEpochMilli();
    }
}