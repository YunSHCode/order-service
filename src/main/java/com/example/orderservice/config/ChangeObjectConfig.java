package com.example.orderservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChangeObjectConfig {
    //외부 라이브러리
    //객체를 변환하기위해 사용
    //---------
    // dto -> entity
    // entity -> dto
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
    //Jackson라이브러리에서 제공하는 클래스
    //자바객체를 json으로 변환(직렬화), json을 자바객체로 변환(역직렬화)
    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
