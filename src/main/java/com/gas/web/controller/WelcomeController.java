package com.gas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/page/welcome-1")
    public String welcome() {
        return "page/welcome-1";
    }

    @GetMapping("/404")
    public String error() {
        return "page/404";
    }
}
