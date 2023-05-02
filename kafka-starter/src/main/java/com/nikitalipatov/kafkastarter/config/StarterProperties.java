package com.nikitalipatov.kafkastarter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka-starter")
@Getter
@Setter
public class StarterProperties {

    private String port;
    private String group;
    private String command;
    private String result;
}
