package com.api.textsearch.exceptionhandler;

import lombok.Data;

@Data
public class UserFriendlyException extends RuntimeException
{
    private final Object[] properties;

    public UserFriendlyException(String message, Object... properties) {
        super(message);
        this.properties = properties;
    }

    public UserFriendlyException(String message, Throwable throwable,  Object... properties) {
        super(message, throwable);
        this.properties = properties;
    }


}
