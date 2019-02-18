package cn.people.commons.utils.springconfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * 
* @ClassName: MybatisPlusConfig 
* @Description: MybatisPlus 配置类
* @author zhangchengchun
* @date 2018年12月4日 下午5:19:21 
*  
 */
@Configuration
public class MybatisPlusConfig
{
    /**
     * 
    * @Title: paginationInterceptor 
    * @author zhangchengchun
    * @date 2018年12月4日 下午5:19:44 
    * @Description: 解决MybatisPlus分页时total和pages一直是0的问题
    * @param @return  参数说明 
    * @return PaginationInterceptor    返回类型 
    * @throws 
     */
    @Bean
    public PaginationInterceptor paginationInterceptor()
    {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
}
