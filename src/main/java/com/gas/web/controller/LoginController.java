package com.gas.web.controller;

import com.gas.web.service.SysMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("login")
public class LoginController {
    @Resource
    private SysMenuService sysMenuService;

    @GetMapping("/menu")
    public Map<String, Object> menu() {
        return sysMenuService.menu();
    }
}