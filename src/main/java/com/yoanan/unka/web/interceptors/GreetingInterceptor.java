package com.yoanan.unka.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

@Component
public class GreetingInterceptor implements HandlerInterceptor {


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LocalTime time = LocalTime.now();
        int hrs = time.getHour();
        if (hrs >= 0 && hrs <= 12) {
            modelAndView.addObject("greeting", "Добро утро");
        } else if (hrs > 12 && hrs <= 17) {
            modelAndView.addObject("greeting", "Добър ден");
        } else {
            modelAndView.addObject("greeting", "Добър вечер");
        }
    }
}
