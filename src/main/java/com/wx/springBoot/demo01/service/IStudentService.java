package com.wx.springBoot.demo01.service;

import com.wx.springBoot.demo01.model.pojo.Student;


import java.util.List;
import java.util.Map;

/**
 * @author wangxin
 * @Title
 * @Description
 * @date 2019-10-29 10:53
 */
public interface IStudentService {
    //List<Student> studenetList();

    Map<String,Object> selectTwoDataBase();
}
