package com.example.demo.service;

import com.example.demo.model.entities.mapper.CompetitionMapper;
import com.example.demo.model.entities.mapper.mapperImplementation.MyMapperImp;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Mapper(componentModel = "spring")
@Configuration
public class mapperConfig {
    @Bean
    public MyMapperImp myMapperImp() {
        return new MyMapperImp();
    }
}