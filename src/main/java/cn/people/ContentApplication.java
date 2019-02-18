/**   
* @Title: ContentApplication.java 
* @Package cn.people 
* @Description: 内容相关服务 
* @author gaoyongjiu
* @date 2018年12月04日 上午9:18:39 
* @version V1.0   
*/
package cn.people;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** 
* @ClassName: ModelApplication 
* @Description: 内容服务启动类1 
* @author gaoyongjiu
* @date 2018年12月04日 上午9:18:39 
*  
*/
@MapperScan({"cn.people.mapper"})
@EnableEurekaClient
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
public class ContentApplication
{

    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class, args);
    }

    /**
     * SQL执行效率插件
     */
    @Bean
    @Profile({"dev","test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }
}
