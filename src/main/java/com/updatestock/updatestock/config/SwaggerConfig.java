package com.updatestock.updatestock.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.collect.Lists;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api.*";

    @Bean
    public Docket productApi() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                                .apiInfo(apiInfo())
                                .pathMapping("/")
                                .forCodeGeneration(true)
                                .securityContexts(Lists.newArrayList(securityContext()))
                                .securitySchemes(Lists.newArrayList(apiKey()))
                                .useDefaultResponseMessages(false);
        docket = docket
                    .select()
                    .paths(regex(DEFAULT_INCLUDE_PATTERN))
                    .build();

        return docket;

    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {

        return SecurityContext.builder()
                              .securityReferences(defaultAuth())
                              .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)).build();
    }

    List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("API Rest UpdateStock", 
                                      "API Rest do Sistema de Gerenciamento de Estoque",
                                      "1.0", 
                                       null,
                                       new Contact("UpdateStock", null, null), 
                                       "Apache License Version 2.0",
                                       "https://www.apache.org/licesen.html", 
                                       new ArrayList<>());
        return apiInfo;
    }

    @Bean
    UiConfiguration uiConfig() {
	return UiConfigurationBuilder.builder() 
		.deepLinking(true)
		.displayOperationId(false)
		.defaultModelsExpandDepth(1)
		.defaultModelExpandDepth(1)
		.defaultModelRendering(ModelRendering.EXAMPLE)
		.displayRequestDuration(true)
		.docExpansion(DocExpansion.NONE)
		.filter(false)
        .maxDisplayedTags(null)
		.operationsSorter(OperationsSorter.ALPHA)
		.showExtensions(false)
        .tagsSorter(TagsSorter.ALPHA)
		.supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
		.validatorUrl(null)
		.build();
	}
}