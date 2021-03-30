package com.study.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {

    @RequestMapping("/hello")
    public String hello(Model model) {

        model.addAttribute("msg","妈耶，HelloWorld终于出来了！！！");
        return "helloWord";
    }
}