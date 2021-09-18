package com.geoxus.core.common.exception;

import cn.hutool.core.lang.Dict;
import cn.hutool.http.HttpStatus;
import com.geoxus.common.exception.GXBeanValidateException;
import com.geoxus.common.exception.GXBusinessException;
import com.geoxus.common.util.GXResultUtil;
import com.geoxus.common.pojo.GXResultCode;
import com.mysql.cj.exceptions.CJException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ValidationException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GXExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(GXBusinessException.class)
    public GXResultUtil<Dict> handleRRException(GXBusinessException e) {
        log.error(e.getMessage(), e);
        return GXResultUtil.error(e.getCode(), e.getMsg(), e.getData());
    }

    @ExceptionHandler(GXBeanValidateException.class)
    public GXResultUtil<Dict> handleRRBeanValidateException(GXBeanValidateException e) {
        log.error(e.getMessage(), e);
        return GXResultUtil.error(e.getCode(), e.getMsg(), e.getDict());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public GXResultUtil<String> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return GXResultUtil.error(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public GXResultUtil<String> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return GXResultUtil.error("数据库中已存在该记录");
    }

    @ExceptionHandler(Exception.class)
    public GXResultUtil<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return GXResultUtil.error("系统错误");
    }

    @ExceptionHandler(BindException.class)
    public GXResultUtil<Map<String, Object>> handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return GXResultUtil.error(GXResultCode.COMMON_ERROR, errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GXResultUtil<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        log.error(e.getMessage(), e);
        return GXResultUtil.error(GXResultCode.COMMON_ERROR, errors);
    }

    @ExceptionHandler(ValidationException.class)
    public GXResultUtil<Map<String, Object>> handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("msg", e.getCause().getMessage());
        return GXResultUtil.error(GXResultCode.COMMON_ERROR, hashMap);
    }

    /**
     * 处理MySQL语法错误异常,主要解决的是调用自定义函数时发生的错误
     *
     * @param e
     */
    @ExceptionHandler(value = {CJException.class})
    public GXResultUtil<String> handleMysqlSyntaxError(Exception e) {
        log.error(e.getMessage(), e);
        return GXResultUtil.error(e.getMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    public GXResultUtil<String> handleException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return GXResultUtil.error(403, "没有权限进行此操作");
    }

    @ExceptionHandler(MultipartException.class)
    public GXResultUtil<String> handleMultipartException(MultipartException e, RedirectAttributes redirectAttributes) {
        log.error(e.getMessage(), e);
        return GXResultUtil.error(HttpStatus.HTTP_INTERNAL_ERROR, e.getCause().getMessage());
    }

    @ExceptionHandler(java.net.BindException.class)
    public GXResultUtil<String> handleBindException(MultipartException e, RedirectAttributes redirectAttributes) {
        log.error(e.getMessage(), e);
        return GXResultUtil.error(HttpStatus.HTTP_INTERNAL_ERROR, e.getMessage());
    }

    @ExceptionHandler(GXTokenEmptyException.class)
    public GXResultUtil<String> handleGXTokenEmptyException(GXTokenEmptyException e, RedirectAttributes redirectAttributes) {
        log.error(e.getMessage(), e);
        return GXResultUtil.error(HttpStatus.HTTP_INTERNAL_ERROR, e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public GXResultUtil<String> handleSQLException(SQLException e, RedirectAttributes redirectAttributes) {
        log.error(e.getMessage(), e);
        return GXResultUtil.error(HttpStatus.HTTP_INTERNAL_ERROR, e.getMessage());
    }

}
