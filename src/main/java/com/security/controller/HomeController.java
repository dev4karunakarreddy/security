package com.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String greet(HttpServletRequest httpServlet){
        return "hello world"+httpServlet.getSession().getId();
    }
}
