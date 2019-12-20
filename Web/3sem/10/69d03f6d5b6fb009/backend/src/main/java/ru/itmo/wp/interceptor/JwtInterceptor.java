package ru.itmo.wp.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.itmo.wp.controller.ApiController;
import ru.itmo.wp.service.JwtService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final String BEARER = "Bearer";

    private JwtService jwtService;

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            if (ApiController.class.isAssignableFrom(method.getDeclaringClass())) {
                String authorization = request.getHeader("Authorization");
                if (authorization != null && authorization.startsWith(BEARER)) {
                    String token = authorization.substring(BEARER.length()).trim();
                    jwtService.find(token).ifPresent(user -> request.setAttribute("user", user));
                }
            }
        }

        return true;
    }
}
