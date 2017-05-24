package com.roi.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestProcessingTimeInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RequestProcessingTimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String login="null";
        if(request.getUserPrincipal()!=null){
            login=request.getUserPrincipal().getName();
        }

        logger.info("Request URL::" + request.getRequestURL().toString()
                + "::User::"+ login+
                ":: Start ");
        //if returned false, we need to make sure 'response' is sent
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        String login="null";
        if(request.getUserPrincipal()!=null){
            login=request.getUserPrincipal().getName();
        }
        logger.info("Request URL::" + request.getRequestURL().toString()+
                "::User::"+ login+"::Sent to Handler " );
        //we can add attributes in the modelAndView and use that in the view page
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        String login="null";
        if(request.getUserPrincipal()!=null){
            login=request.getUserPrincipal().getName();
        }
        logger.info("Request URL::" + request.getRequestURL().toString()
                + "::User::"+ login + ":: End");
    }

}
