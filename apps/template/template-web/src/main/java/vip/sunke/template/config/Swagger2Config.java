package vip.sunke.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import vip.sunke.common.SkList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunke
 * @Date 2018/8/16 10:22
 * @description
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    private static final String REGEX = "^(?!auth).*$";

    private List<ApiKey> securitySchemes() {

        return new SkList<ApiKey>().addObjToList(new ApiKey("authorization", "authorization", "header"));

    }

    private List<SecurityContext> securityContexts() {

        return new SkList<SecurityContext>().addObjToList(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(REGEX))
                .build());


    }


    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new SkList<SecurityReference>().addObjToList(new SecurityReference("authorization", authorizationScopes));

    }


    @Bean
    public Docket createRestApi() {

        List<Parameter> pars = new ArrayList<Parameter>();


        ParameterBuilder ticketPar = new ParameterBuilder();

        ticketPar.name("authorization").description("用户令牌")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build(); //header中的ticket参数非必填，传空也可以


        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数


        ticketPar = new ParameterBuilder();

        ticketPar.name("jsonType").description("jsonType")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).defaultValue("jsonType").build(); //header中的ticket参数非必填，传空也可以


        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("vip.sunke"))
                .paths(PathSelectors.any())
                // .paths(PathSelectors.regex(REGEX))
                .build()
                .globalOperationParameters(pars);

        // .securitySchemes(securitySchemes())
        // .securityContexts(securityContexts());


    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("电子签章 RESTful APIs")
                .description("电子签章APP api接口文档<br/>返回数据格式:{\n" +
                        "  \"errorCode\": errorCode,\n" +
                        "  \"message\": \"message\",\n" +
                        "  \"data\": {\n" +

                        "  },\n" +
                        "  \"success\": true\n" +
                        "}")
                .version("1.0")


                .build();
    }
}
