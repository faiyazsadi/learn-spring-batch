package com.example.learnspringbatch.controller;

import com.example.learnspringbatch.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SimpleController {


    public SimpleController(@Qualifier("simpleName2") String name) {
        System.out.println(name);
    }

    @ResponseBody
    @GetMapping(value = "/hello", produces = MediaType.TEXT_HTML_VALUE)
    public String hello() {
        System.out.println(Thread.currentThread().getName());
        return "<h1>Hello World!</h1>";
    }



}
