package com.triadsoft;

import com.triadsoft.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 16/04/19 17:33
 */
@Configuration
@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
