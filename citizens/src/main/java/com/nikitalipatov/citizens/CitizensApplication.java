package com.nikitalipatov.citizens;

import com.nikitalipatov.common.feign.CarClient;
import com.nikitalipatov.common.feign.CitizenClient;
import com.nikitalipatov.kafkastarter.Start;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients(clients = {CarClient.class, CitizenClient.class})
public class CitizensApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitizensApplication.class, args);
    }

}
