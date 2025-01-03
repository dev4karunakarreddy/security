package com.security.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidToken extends RuntimeException {
    public InvalidToken(String message) {
        super(message,null,false,false);
    }
}
