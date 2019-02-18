/**   
* @Title: SwaggerConfig.java 
* @Package cn.people.config 
* @Description: Swagger配置类 
* @author fuqiang
* @date 2018年12月4日 下午15:50:19 
* @version V1.0   
*/
package cn.people.commons.utils.springconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/** 
* @ClassName: SwaggerConfig 
* @Description: Swagger配置类 
* @author fuqiang
* @date 2018年12月4日 下午15:50:19 
*  
*/
@Configuration
public class SwaggerConfig
{

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("cn.people.controller"))
                .paths(PathSelectors.any())
                .build();
    }
 
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("内容服务")
                .description("提供内容处理的各种服务")
                //服务条款网址
                //.termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
