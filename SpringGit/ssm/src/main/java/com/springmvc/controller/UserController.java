package com.springmvc.controller;

import com.springmvc.bean.User;
import com.springmvc.dao.UserMapper;
import com.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/hello")
    public String hello(Model model) {

        model.addAttribute("msg","tttttt妈耶，HelloWorld终于出来了！！！");
        return "ttt";
    }

    @RequestMapping("/t")
    public ModelAndView getUser(){
        ModelAndView modelAndView = new ModelAndView();
        int id = 3;
        User user = userMapper.selectByPrimaryKey(String.valueOf(id));
        modelAndView.addObject("user",user);
        modelAndView.setViewName("ttt");
        return modelAndView;
    }

    @RequestMapping("/t1")
    public String insert(Model model) {
        User user = new User();
        user.setId("19");
        user.setName("王8呆");
        user.setSex("男");
        int res = userService.insert(user);
        model.addAttribute("msg","insert的返回值为：" + res);

        int id = 16;
        User user2 = userMapper.selectByPrimaryKey(String.valueOf(id));
        model.addAttribute(user2);
        return "ttt";
    }




}
