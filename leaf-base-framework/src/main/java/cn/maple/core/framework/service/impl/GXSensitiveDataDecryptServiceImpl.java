package cn.maple.core.framework.service.impl;

import cn.hutool.core.util.ReflectUtil;
import cn.maple.core.framework.util.GXSpringContextUtils;
import cn.maple.core.framework.annotation.GXSensitiveField;
import cn.maple.core.framework.service.GXSensitiveDataDecryptService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Objects;

@Service
public class GXSensitiveDataDecryptServiceImpl implements GXSensitiveDataDecryptService {
    @Override
    public <T> T decrypt(T result) throws IllegalAccessException {
        Class<?> resultClass = result.getClass();
        Field[] declaredFields = resultClass.getDeclaredFields();
        for (Field field : declaredFields) {
            GXSensitiveField sensitiveField = field.getAnnotation(GXSensitiveField.class);
            if (Objects.nonNull(sensitiveField)) {
                final Field accessible = ReflectUtil.setAccessible(field);
                Object object = accessible.get(result);
                // 只实现对String的解密
                if (object instanceof String) {
                    final Class<?> serviceClazz = sensitiveField.serviceClazz();
                    final String decryAlgorithm = sensitiveField.decryAlgorithm();
                    final String deEncryptKey = sensitiveField.deEncryptKey();
                    final String[] params = sensitiveField.params();
                    String value = (String) object;
                    final Object bean = GXSpringContextUtils.getBean(serviceClazz);
                    final Object invoke = ReflectUtil.invoke(bean, decryAlgorithm, value, deEncryptKey, params);
                    ReflectUtil.setFieldValue(result, accessible, invoke);
                }
            }
        }
        return result;
    }
}
