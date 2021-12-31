package ink.aos.boot.security.audit;

import lombok.Data;

@Data
public class SysAudit {

    private String userId;

    private String serviceId;

    private String module;

    private String browserName;

    private String browserType;

    private String browserGroup;

    private String browserManufacturer;

    private String browserRenderingEngine;

    private String browserVersion;

    private String osName;

    private String osDeviceType;

    private String osGroup;

    private String osManufacturer;

    private String ip;

    private String description;

    private String className;

    private String methodName;

    private String param;

}
