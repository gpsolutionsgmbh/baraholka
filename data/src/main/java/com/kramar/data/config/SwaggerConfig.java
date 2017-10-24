package com.kramar.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.kramar.data.config.ResourceServerConfig.RESOURCE_ID;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

//    http://localhost:8080/swagger-ui.html

    @Bean
    public Docket apiDocumentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kramar.data.web"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Baraholka REST API",
                "Some custom description of Baraholka API.",
                "API TOS",
                "Terms of service",
                new Contact("Yri Kramar", "KramerIT.com", "y.kramar@gpsolutions.com"),
                "License of Baraholka API",
                "Baraholka API license URL",
                Collections.singleton(new StringVendorExtension("kramar", "1.0")));
    }

    private List<SecurityScheme> securitySchemes() {
        final AuthorizationScope readScope = new AuthorizationScope("read", "read");
        final AuthorizationScope writeScope = new AuthorizationScope("write", "write");

        final GrantType grantType = new ImplicitGrant(
                new LoginEndpoint("http://localhost:8000/oauth/authorize"), "token");

        final SecurityScheme scheme = new OAuth("oauth2scheme",
                Arrays.asList(readScope, writeScope), Collections.singletonList(grantType));

        return Collections.singletonList(scheme);
    }

    private List<SecurityContext> securityContexts() {
        final SecurityReference reference = new SecurityReference("oauth2scheme",
                new AuthorizationScope[]{
                        new AuthorizationScope("read", "read"),
                        new AuthorizationScope("write", "write")
                });

        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(Collections.singletonList(reference))
                .build());
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                "swagger-ui",
                "secret",
                RESOURCE_ID,
                "baraholka",
                "baraholka_api_key",
                ApiKeyVehicle.HEADER,
                "baraholka_api",
                " "
        );
    }
}