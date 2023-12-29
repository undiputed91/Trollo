package org.nbc.account.trollo.global.exception;

import java.util.NoSuchElementException;
import java.util.concurrent.RejectedExecutionException;
import org.nbc.account.trollo.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ApiResponse<?> handleCustomException(CustomException customException){
        ErrorCode errorCode = customException.getErrorCode();
        return new ApiResponse<>(errorCode.getHttpStatus().value(), errorCode.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ApiResponse<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler({NullPointerException.class})
    public ApiResponse<?> handleNullPointerException(NullPointerException ex) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler({RejectedExecutionException.class})
    public ApiResponse<?> handleRejectedExecutionException(RejectedExecutionException ex) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ApiResponse<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ApiResponse<?> handleNoSuchElementException(NoSuchElementException ex) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorCode errorCode = ErrorCode.BAD_FORM;
        return new ApiResponse<>(errorCode.getHttpStatus().value(), errorCode.getMessage());
    }

}
