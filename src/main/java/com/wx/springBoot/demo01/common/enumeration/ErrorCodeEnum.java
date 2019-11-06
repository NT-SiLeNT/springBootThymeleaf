package com.wx.springBoot.demo01.common.enumeration;

/**
 * @author wangxin
 * @Title
 * @Description
 * @date 2019-10-29 16:15
 */
public enum ErrorCodeEnum {

    GL99990100(9999100, "参数异常"),
    GL99990003(9999103, "操作数据库错误"),
    ;

    private int code;
    private String msg;

    /**
     * Msg string.
     *
     * @return the string
     */
    public String msg() {
        return msg;
    }

    /**
     * Code int.
     *
     * @return the int
     */
    public int code() {
        return code;
    }

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Gets enum.
     *
     * @param code the code
     *
     * @return the enum
     */
    public static ErrorCodeEnum getEnum(int code) {
        for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
            if (ele.code() == code) {
                return ele;
            }
        }
        return null;
    }
}
