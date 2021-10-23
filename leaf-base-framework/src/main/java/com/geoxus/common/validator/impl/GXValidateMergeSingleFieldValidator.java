package com.geoxus.common.validator.impl;

import com.geoxus.common.annotation.GXMergeSingleField;
import com.geoxus.common.util.GXSpringContextUtil;
import com.geoxus.common.validator.GXValidateJSONFieldService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class GXValidateMergeSingleFieldValidator implements ConstraintValidator<GXMergeSingleField, Object> {
    /**
     * 实际处理的服务
     */
    private GXValidateJSONFieldService validateJsonFieldService;

    /**
     * 表名字
     */
    private String tableName;

    /**
     * 数据库表中扩展字段中的字段名字
     * {"ext":{"author":"枫叶思源"}}
     */
    private String parentFieldName;

    private String fieldName;

    @Override
    public void initialize(GXMergeSingleField annotation) {
        Class<? extends GXValidateJSONFieldService> serviceClazz = annotation.service();
        tableName = annotation.tableName();
        parentFieldName = annotation.parentFieldName();
        fieldName = annotation.fieldName();
        validateJsonFieldService = GXSpringContextUtil.getBean(serviceClazz);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        if (Objects.isNull(validateJsonFieldService)) {
            return true;
        }
        return validateJsonFieldService.validateJsonFieldData(o, tableName, parentFieldName, fieldName, context);
    }
}