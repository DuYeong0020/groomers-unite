package com.petstylelab.groomersunite.common.response;

import com.petstylelab.groomersunite.common.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CommonControllerAdvice {
    /**
     * Handles UnAuthorizedException.
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public <T> CommonResponse<T> handleUnAuthorizedException(UnAuthorizedException ex) {
        String errorMessage = ex.getMessage();
        log.error("UnAuthorizedException: {}", errorMessage);
        return CommonResponse.fail(errorMessage, ErrorCode.AUTH_UNAUTHORIZED.name());
    }

    /**
     * Handles IllegalArgumentException.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public <T> CommonResponse<T> handleIllegalArgumentException(IllegalArgumentException ex) {
        if (ex.getMessage() == null) {
            return CommonResponse.fail(ErrorCode.COMMON_ILLEGAL_STATUS);
        }
        log.error("IllegalArgumentException: {}", ex.getMessage());
        return CommonResponse.fail(ex.getMessage(), ErrorCode.COMMON_ILLEGAL_STATUS.name());
    }

    /**
     * Handles RuntimeException.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public <T> CommonResponse<T> handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage() == null) {
            return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER);
        }

        log.error("RuntimeException: {}", ex.getMessage());
        return CommonResponse.fail(ex.getMessage(), ErrorCode.COMMON_INVALID_PARAMETER.name());
    }

    /**
     * Handles MethodArgumentNotValidException.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> CommonResponse<T> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.error("MethodArgumentNotValidException: {}", errorMessage);
        return CommonResponse.fail(errorMessage, ErrorCode.COMMON_INVALID_PARAMETER.name());
    }
}