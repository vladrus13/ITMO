package ru.itmo.wp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.itmo.wp.interceptor.JwtInterceptor;

@SpringBootApplication
public class WpApplication implements WebMvcConfigurer {
    private JwtInterceptor jwtInterceptor;

    @Autowired
    public void setJwtInterceptor(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor);
    }

    public static void main(String[] args) {
        SpringApplication.run(WpApplication.class, args);
    }

}
