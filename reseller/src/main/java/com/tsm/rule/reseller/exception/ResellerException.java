package com.tsm.rule.reseller.exception;

import lombok.Data;

@Data
public class ResellerException extends RuntimeException{

    private String code;

    public ResellerException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }
}
