package org.nbc.account.trollo.global.exception;

import org.nbc.account.trollo.global.dto.ApiResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ApiResponse<?> handleCustomException(CustomException customException) {
        ErrorCode errorCode = customException.getErrorCode();
        return new ApiResponse<>(errorCode.getHttpStatus().value(), errorCode.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException ex) {
        ErrorCode errorCode = ErrorCode.BAD_FORM;
        return new ApiResponse<>(errorCode.getHttpStatus().value(), errorCode.getMessage());
    }

}
