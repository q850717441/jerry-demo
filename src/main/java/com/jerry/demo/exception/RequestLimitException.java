package com.jerry.demo.exception;

import com.jerry.demo.exception.code.BaseResponseCode;

/**
 *  HTTP请求超出设定的限制
 */
public class RequestLimitException extends RuntimeException {
    /**
     * 异常编号
     */
    private final int messageCode;

    /**
     * 对messageCode 异常信息进行补充说明
     */
    private final String detailMessage;

    public RequestLimitException(int messageCode,String message){
        super(message);
        this.messageCode = messageCode;
        this.detailMessage = message;
    }

    public RequestLimitException(String message){
        super(message);
        this.messageCode = BaseResponseCode.OPERATION_ERRO.getCode();
        this.detailMessage = message;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
