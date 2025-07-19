package com.weyland.synthetic_core_starter.core.config;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@ConfigurationProperties(prefix = "treadpool")
@Validated //Зачем
public class ThreadPoolConfigProperties {

    @Min(value = 1, message = "corePoolSize должен быть больше 0")
    private int corePoolSize =2;

    @Min(value = 1, message = "maxPoolSize должен быть больше 0")
    private int maxPoolSize = 5;


    @Min(value = 0, message = "keepAliveTime должен быть не меньше 0" )
    private long keepAliveTime = 60L;

    @Min(value = 1, message = "queueCapacity должен быть больше 0" )
    private int queueCapacity = 10;

}
