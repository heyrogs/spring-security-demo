package com.rog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hey, rog
 * @version v1.0.1
 * @since 2021/9/14
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

}
