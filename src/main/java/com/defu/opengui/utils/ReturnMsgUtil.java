package com.defu.opengui.utils;

/**
 * 返回消息
 */
public class ReturnMsgUtil {

    private Boolean success;
    private String code;//错误代码
    private String message;//错误消息
    private String detail;//错误详情-命令执行中的信息
    private String cmd;//执行的命令
    private String result;//返回给调用者的结果

    public ReturnMsgUtil(Boolean success, String code, String message, String detail) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "ReturnMsgUtil{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", detail='" + detail + '\'' +
                ", cmd='" + cmd + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public ReturnMsgUtil(Boolean success, String detail, String cmd) {
        this.success = success;
        this.detail = detail;
        this.cmd = cmd;
    }

    public ReturnMsgUtil setCode(String code){
        this.code = code;
        return this;
    }

    public ReturnMsgUtil setMessage(String message){
        this.message = message;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getDetail() {
        return detail;
    }

    public String getCmd() {
        return cmd;
    }
}
