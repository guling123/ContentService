package cn.people.commons.utils.springconfig;

import cn.people.interceptor.CommonParamsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 用于设置公共参数拦截
 * <p>
 * Created by wilson on 2018-10-09.
 */
@Configuration
public class CommonParamsInterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private CommonParamsInterceptor commonParamsInterceptor;

    /**
     * 在这里设置要拦截的路径和不拦截的路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonParamsInterceptor)
                .addPathPatterns("/**")             // 拦截
                .excludePathPatterns("/check");     // 不拦截
    }

}
