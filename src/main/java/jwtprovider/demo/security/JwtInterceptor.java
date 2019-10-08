package jwtprovider.demo.security;

import jwtprovider.demo.error.UnauthorizedException;
import jwtprovider.demo.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Value("${app.jwtHeader}")
    private String HEADER_AUTH ;

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String token = request.getHeader(HEADER_AUTH);
        System.out.println(request.getContextPath());

        if(token != null && jwtService.validateToken(token)){
            return true;
        }else{
            throw new UnauthorizedException();
        }
    }
}
