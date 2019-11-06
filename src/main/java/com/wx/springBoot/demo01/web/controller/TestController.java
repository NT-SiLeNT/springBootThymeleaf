package com.wx.springBoot.demo01.web.controller;

import com.wx.springBoot.demo01.service.impl.StudentServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxin
 * @Title
 * @Description
 * @date 2019-10-25 10:28
 */
@RestController
public class TestController {
    private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    @GetMapping("/test")
    public String test(){
        logger.info("请求成功！");
        return "success";
    }
}
