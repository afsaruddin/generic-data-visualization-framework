package com.wsdhaka.gdvf;

import com.wsdhaka.gdvf.utils.JSONUtils;

public class ErrorResponse {
    public static int ERROR_CODE_API_SPEC_VIOLATION = 1;

    private int errorCode;
    private String errorMessage;

    public ErrorResponse(int errorCode, String errorMessage) {
        setErrorCode(errorCode);
        setErrorMessage(errorMessage);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String toJson() {
        return JSONUtils.toJson(this);
    }
}
