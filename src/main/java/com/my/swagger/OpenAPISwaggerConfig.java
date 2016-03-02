/**
 * 
 */
package com.my.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author I311862
 *
 */
@Configuration
@PropertySource("classpath:/META-INF/swagger/swagger.properties")
@ComponentScan("com.eshop.controller")
@EnableWebMvc
@EnableSwagger2
public class OpenAPISwaggerConfig {

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2).pathMapping("/").apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("SAP Anywhere ESHOP Open API", "SAP Anywhere ESHOP Open API Documentations", "1.0",
                "http://www.sap.com/terms", "eshop_openapi@sap.com", "SAP License", "http://www.sap.com/license");
    }

}