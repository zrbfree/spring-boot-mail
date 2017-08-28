package com.rick.demo.controller;

import com.github.pagehelper.PageHelper;
import com.rick.demo.entity.Demo;
import com.rick.demo.service.DemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DemoController {

    @Resource
    private DemoService demoService;
    /**
     * Desc :  根据姓名查询用户
     * User : RICK
     * Time : 2017/8/28 15:15
      */

    @RequestMapping(value = "/likeName", method = RequestMethod.GET)
    public List<Demo> likeName(String name){
        return demoService.likeName(name);
    }

    /**
     * Desc :  分页查询数据
     * User : RICK
     * Time : 2017/8/28 15:16
      */
    @RequestMapping(value = "/likeNamePage", method = RequestMethod.GET)
    public List<Demo> likeNamePage(String name){
        //第一个参数是第几页,第二个参数是每页显示条数
        PageHelper.startPage(1,5);
        return demoService.likeName(name);
    }

    @RequestMapping("/hello")
    public String hello(String name,int state){
        return"name = "+name+"---state = "+state;
    }
}
