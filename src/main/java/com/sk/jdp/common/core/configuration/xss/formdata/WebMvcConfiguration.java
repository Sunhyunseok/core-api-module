package com.sk.jdp.common.core.configuration.xss.formdata;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;



/**
 * @ClassName WebMvcConfiguration.java
 * @Description XSS 서블릿 필터 설정
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
        FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new XssEscapeServletFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/**");

        return filterRegistrationBean;
    }

}
