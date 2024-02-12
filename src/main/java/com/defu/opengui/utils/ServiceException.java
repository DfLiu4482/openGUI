package com.defu.opengui.utils;

/**
 * 自定义业务异常
 * 
 * @author dfliu
 */
public final class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public ServiceException()
    {
    }

    public ServiceException(String message)
    {
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public ServiceException setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage()
    {
        return message;
    }

    public ServiceException setMessage(String message)
    {
        this.message = message;
        return this;
    }
}