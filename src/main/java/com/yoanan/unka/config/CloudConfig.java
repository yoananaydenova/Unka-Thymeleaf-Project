package com.yoanan.unka.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudConfig {

//    @Value("${cloudinary.cloud-name}")
    @Value("${cloudinary.cloud_name:yoanan}")
    private String cloudName;

//    @Value("${cloudinary.api-key}")
    @Value("${cloudinary.api_key:885576798911148}")
    private String apiKey;

//    @Value("${cloudinary.api-secret}")
    @Value("${cloudinary.api_secret:XChmIS3REXBtySXugqSOYYMIsPs}")
    private String apiSecret;

    @Bean
    public Cloudinary createCloudinaryConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }
}
