package com.planner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigure implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 이미지 파일들에 대한 경로 설정
        // 배포시 배포 서버에 맞는 경로로 수정해야함
        registry
                .addResourceHandler("/img/**")
                .addResourceLocations("file:///C:/Users/sin91/IdeaProjects/DailyPlanner/src/main/frontend/public/img/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // 리액트 앱의 클라이언트 사이드 라우팅 처리를 위한 설정입니다.
        registry.addViewController("/{spring:\\w+}")
                .setViewName("forward:/");
        registry.addViewController("/**/{spring:\\w+}")
                .setViewName("forward:/");
        registry.addViewController("/{spring:\\w+}/**{spring:?!(\\.js|\\.css|\\.png|\\.jpg|\\.jpeg|\\.gif|\\.svg)$}")
                .setViewName("forward:/");
    }
}
