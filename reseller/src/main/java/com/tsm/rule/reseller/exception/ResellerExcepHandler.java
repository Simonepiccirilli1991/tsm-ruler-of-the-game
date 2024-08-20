package com.tsm.rule.reseller.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResellerExcepHandler {

    @ExceptionHandler(ResellerException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ResellerException ex){
        var resp = new ErrorResponse();
        var status = statusMapper(ex.getCode());
        return ResponseEntity.status(status).body(resp);
    }


    private HttpStatus statusMapper(String code){
        //TODO: inserire un switch case con i codici
        return HttpStatus.BAD_REQUEST;
    }
}

@Data
class ErrorResponse {

    private String message;
    private String codice;
}
