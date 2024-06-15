package com.example.swagger;


import com.example.result.Result;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Configuration
@ConditionalOnProperty(prefix = "ms.swagger", name = "enable",havingValue = "true")
@EnableConfigurationProperties(SwaggerConfigProperties.class)
public class Knife4jConfiguration {

    @Resource
    private SwaggerConfigProperties swaggerConfigProperties;

    @Bean(value = "defaultApi2")
    public Docket defaultApi2(TypeResolver typeResolver) {
        // 1.初始化Docket
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        // 2.是否需要包装R
        if(swaggerConfigProperties.getEnableResponseWrap()){
            docket.additionalModels(typeResolver.resolve(Result.class));
        }
        return docket.apiInfo(new ApiInfoBuilder()
                .title(this.swaggerConfigProperties.getTitle())
                .description(this.swaggerConfigProperties.getDescription())
                .contact(new Contact(
                        this.swaggerConfigProperties.getContactName(),
                        this.swaggerConfigProperties.getContactUrl(),
                        this.swaggerConfigProperties.getContactEmail()))
                .version(this.swaggerConfigProperties.getVersion())
                .build())
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage(swaggerConfigProperties.getPackagePath()))
                .paths(PathSelectors.any())
                .build();

    }
    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "ms.swagger", name = "enableResponseWrap",havingValue = "true")
    public BaseSwaggerResponseModelPlugin baseSwaggerResponseModelPlugin(){
        return new BaseSwaggerResponseModelPlugin();
    }
    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "ms.swagger", name = "enableResponseWrap",havingValue = "true")
    public BaseSwaggerResponseBuilderPlugin baseSwaggerResponseBuilderPlugin(){
        return new BaseSwaggerResponseBuilderPlugin();
    }
}