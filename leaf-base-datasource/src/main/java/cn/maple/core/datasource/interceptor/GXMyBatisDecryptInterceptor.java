package cn.maple.core.datasource.interceptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import cn.maple.core.framework.annotation.GXSensitiveData;
import cn.maple.core.framework.service.GXSensitiveDataDecryptService;
import cn.maple.core.framework.util.GXTypeOfUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Resource;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Slf4j
//@Component
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class GXMyBatisDecryptInterceptor implements Interceptor {
    @Resource
    private GXSensitiveDataDecryptService sensitiveDataDecryptService;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 取出查询的结果
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }
        // 基于selectList
        if ("ArrayList".equalsIgnoreCase(GXTypeOfUtils.typeof(resultObject).getSimpleName())) {
            List<?> resultList = Convert.convert(new TypeReference<List<?>>() {
            }, resultObject);
            if (!CollectionUtils.isEmpty(resultList) && Objects.nonNull(resultList.get(0)) && needToDecrypt(resultList.get(0))) {
                for (Object result : resultList) {
                    sensitiveDataDecryptService.decrypt(result);
                }
            }
            //基于selectOne
        } else {
            if (needToDecrypt(resultObject)) {
                sensitiveDataDecryptService.decrypt(resultObject);
            }
        }
        return resultObject;
    }

    private boolean needToDecrypt(Object object) {
        Class<?> objectClass = object.getClass();
        GXSensitiveData sensitiveData = AnnotationUtils.findAnnotation(objectClass, GXSensitiveData.class);
        return Objects.nonNull(sensitiveData);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 自定义配置写入，没有自定义配置的可以直接置空此方法
    }
}