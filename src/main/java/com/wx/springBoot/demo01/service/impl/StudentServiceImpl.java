package com.wx.springBoot.demo01.service.impl;

import com.wx.springBoot.demo01.mapper.StudentMapper;
import com.wx.springBoot.demo01.mapper.mysql.StudentMysqlMapper;
import com.wx.springBoot.demo01.mapper.oracle.StudentOracleMapper;
import com.wx.springBoot.demo01.model.pojo.Student;
import com.wx.springBoot.demo01.service.IStudentService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangxin
 * @Title
 * @Description
 * @date 2019-10-29 10:54
 */
@Service
public class StudentServiceImpl implements IStudentService {

    private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    /*@Resource
    private StudentMapper studentMapper;*/
    @Resource
    private StudentMysqlMapper studentMysqlMapper;
    @Resource
    private StudentOracleMapper studentOracleMapper;

    /*@Override
    public List<Student> studenetList() {
        logger.info("进入方法");
        List<Student> students = studentMapper.queryAll();
        logger.info("查询结果条数："+students.size());
        return students;
    }*/

    @Override
    public Map<String, Object> selectTwoDataBase() {
        Map<String,Object> map = new HashMap<>();
        List<Student> students1 = studentMysqlMapper.queryAll();
        List<Student> students2 = studentOracleMapper.queryAll();
        map.put("mysqlData",students1);
        map.put("oracleData",students2);
        return map;
    }
}
