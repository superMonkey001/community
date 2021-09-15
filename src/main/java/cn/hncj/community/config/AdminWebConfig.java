package cn.hncj.community.config;

import cn.hncj.community.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminWebConfig {
    @Autowired
    LoginInterceptor loginInterceptor;
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/","/css/**","/fonts/**","/images/**","/js/**","/callback"
                        ,"/question/*","/comment/**"
                        )
                ;
            }
        };
    }
}
