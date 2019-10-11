package jwtprovider.demo.security;

import jwtprovider.demo.error.UnauthorizedException;
import jwtprovider.demo.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.naming.NameAlreadyBoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final String  HEADER_AUTH = "authorization" ;
    private Map<String, String[]> params = new HashMap<>();

    @Autowired
    private JwtService jwtService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String token =getJwtFromRequest(request);
       // System.out.println("TOKEN: " + request.getHeader(HEADER_AUTH));



        String allHeader = "";
        Enumeration headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = (String)headerNames.nextElement();
            String value = request.getHeader(name);
            System.out.println( name + ": " + value + "\n");
        }
        if(token != null && jwtService.validateToken(token)){
            return true;
        }else{
            throw new UnauthorizedException(token);
        }




    }



    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_AUTH);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
