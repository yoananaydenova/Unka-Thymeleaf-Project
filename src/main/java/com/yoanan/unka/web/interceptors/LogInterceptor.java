package com.yoanan.unka.web.interceptors;

import com.yoanan.unka.service.LogService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogInterceptor implements HandlerInterceptor {

    private final LogService logService;

    public LogInterceptor(LogService logService) {
        this.logService = logService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long startTime = System.currentTimeMillis();
//        System.out.println("\n-------- LogInterception.preHandle --- ");
//        System.out.println("Request URL: " + request.getRequestURL());
//        System.out.println("Start Time: " + System.currentTimeMillis());

        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//        System.out.println("\n-------- LogInterception.postHandle --- ");
//        System.out.println("Request URL: " + request.getRequestURL());

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
//        System.out.println("\n-------- LogInterception.afterCompletion --- ");

        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();

        StringBuffer requestURL = request.getRequestURL();
//        System.out.println("Request URL: " + requestURL);
//        System.out.println("End Time: " + endTime);
        long timeTaken = endTime - startTime;

//        System.out.println("Time Taken: " + timeTaken);

        logService.createLog(requestURL.toString(), timeTaken);
    }
}
