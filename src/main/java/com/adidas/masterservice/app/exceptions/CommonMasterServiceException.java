package com.adidas.masterservice.app.exceptions;

public class CommonMasterServiceException extends RuntimeException {
    public CommonMasterServiceException() {
        super();
    }

    public CommonMasterServiceException(String s) {
        super(s);
    }

    public CommonMasterServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CommonMasterServiceException(Throwable throwable) {
        super(throwable);
    }
}
