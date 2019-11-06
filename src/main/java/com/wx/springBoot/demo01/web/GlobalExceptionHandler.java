package com.wx.springBoot.demo01.web;

import com.wx.springBoot.demo01.common.WrapMapper;
import com.wx.springBoot.demo01.common.Wrapper;
import com.wx.springBoot.demo01.common.enumeration.ErrorCodeEnum;
import com.wx.springBoot.demo01.common.exception.BussException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;

/**
 * @Author: wangxin
 * @Title: GlobalExceptionHandler
 * @ProjectName: uyw-master
 * @Description: TODO 全局的异常拦截器
 * @Date: Created in 2019/4/9 10:00
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*@Resource
    private TaskExecutor taskExecutor;*/
    //@Resource
    //private MdcExceptionLogFeignApi mdcExceptionLogFeignApi;

    /**
     * 参数非法异常.
     *
     * @param e the e
     *
     * @return the wrapper
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper illegalArgumentException(IllegalArgumentException e) {
        log.error("参数非法异常={}", e.getMessage(), e);
        return WrapMapper.wrap(ErrorCodeEnum.GL99990100.code(), e.getMessage());
    }

    /**
     * 业务异常.
     *
     * @param e the e
     *
     * @return the wrapper
     */
    @ExceptionHandler(BussException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper businessException(BussException e) {
        log.error("业务异常={}", e.getMessage(), e);
        return WrapMapper.wrap(e.getCode() == 0 ? Wrapper.ERROR_CODE : e.getCode(), e.getMessage());
    }

    /**
     * 参数非法异常.
     *
     * @param e the e
     *
     * @return the wrapper
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper dataAccessException(DataAccessException e) {
        log.error("操作数据库异常={}", e.getMessage(), e);
        return WrapMapper.wrap(ErrorCodeEnum.GL99990003.code(), e.getMessage());
    }

    /**
     * 全局异常.
     *
     * @param e the e
     *
     * @return the wrapper
     */
    //@ExceptionHandler(Exception.class)
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    //@ResponseBody
    //public Wrapper exception(Exception e) {
    //    log.info("保存全局异常信息 ex={}", e.getMessage(), e);
    //    taskExecutor.execute(() -> {
    //        GlobalExceptionLogDto globalExceptionLogDto = new GlobalExceptionLogDto().getGlobalExceptionLogDto(e, profile, applicationName);
    //        mdcExceptionLogFeignApi.saveAndSendExceptionLog(globalExceptionLogDto);
    //    });
    //    return WrapMapper.error();
    //}
}
