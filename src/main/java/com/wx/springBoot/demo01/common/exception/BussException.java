package com.wx.springBoot.demo01.common.exception;

import com.wx.springBoot.demo01.common.enumeration.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangxin
 * @Title
 * @Description
 * @date 2019-10-29 16:12
 */
@Slf4j
public class BussException extends RuntimeException {
    /**
     * 异常码
     */
    protected int code;

    private static final long serialVersionUID = 3160241586346324994L;

    public BussException() {
    }

    public BussException(Throwable cause) {
        super(cause);
    }

    public BussException(String message) {
        super(message);
    }

    public BussException(String message, Throwable cause) {
        super(message, cause);
    }

    public BussException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BussException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }

    public BussException(ErrorCodeEnum codeEnum, Object... args) {
        super(String.format(codeEnum.msg(), args));
        this.code = codeEnum.code();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
