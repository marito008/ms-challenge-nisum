package com.challenge.nisum.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${swagger.host}")
    private String host;
    @Value("${swagger.enviroment}")
    private String enviroment;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.challenge.nisum"))
                .build()
                .apiInfo(getApiInformation());
    }

    private ApiInfo getApiInformation() {
        return new ApiInfo("API challengue Mario chichiricco"  ,
                "Api challengue",
                "1.0",
                null,
                new Contact("Mario chichiricco ", null, "mariog_ch@hotmail.com"),
                null,
                null,
                Collections.emptyList()
        );
    }
}
