package com.tj.id.exception;

import com.tj.id.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdGenerateException.class)
    public R<Void> handleIdGenerateException(IdGenerateException e) {
        log.error("ID生成异常: code={}, message={}", e.getCode(), e.getMessage());
        return R.failed(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception e) {
        log.error("未知异常", e);
        return R.failed("系统内部错误");
    }
}
