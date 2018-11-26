package com.hx.fastdfs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix ="appConfig" )
@Data
public class AppConfig {
    public static final String HTTP_PRODOCOL="http://";

    private String resHost;

    private String fdfsStoragePort;
}
