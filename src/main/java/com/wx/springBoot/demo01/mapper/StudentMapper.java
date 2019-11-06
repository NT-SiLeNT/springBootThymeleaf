package com.wx.springBoot.demo01.mapper;

import com.wx.springBoot.demo01.model.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangxin
 * @Title
 * @Description
 * @date 2019-10-29 10:54
 */
@Component
@Mapper
public interface StudentMapper {
    List<Student> queryAll();
}

