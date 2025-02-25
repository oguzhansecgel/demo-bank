package com.bank.bank_demo.exception.type;

public class NotFoundAccountException extends RuntimeException{
    public NotFoundAccountException(String message) {
        super(message);
    }
}
