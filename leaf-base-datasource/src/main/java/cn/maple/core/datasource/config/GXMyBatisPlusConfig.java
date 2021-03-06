package cn.maple.core.datasource.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.text.CharSequenceUtil;
import cn.maple.core.datasource.service.GXTenantIdService;
import cn.maple.core.framework.config.aware.GXApplicationContextSingleton;
import cn.maple.core.framework.constant.GXCommonConstant;
import cn.maple.core.framework.util.GXSpringContextUtils;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.handlers.MybatisMapWrapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@EnableTransactionManagement
@Configuration
public class GXMyBatisPlusConfig {
    @Resource
    private ApplicationContext applicationContext;

    private static void customize(org.apache.ibatis.session.Configuration configuration) {
        configuration.setObjectWrapperFactory(new ObjectWrapperFactory() {
            @Override
            public boolean hasWrapperFor(Object object) {
                return object instanceof Map;
            }

            @Override
            public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
                final Map<String, Object> map = Convert.convert(new TypeReference<>() {
                }, object);
                return new MybatisMapWrapper(metaObject, map) {
                    @Override
                    public String findProperty(String name, boolean useCamelCaseMapping) {
                        if (useCamelCaseMapping && !StringUtils.isCamel(name)) {
                            return StringUtils.underlineToCamel(name);
                        }
                        return name;
                    }
                };
            }
        });
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // ???ApplicationContext????????????
        if (GXApplicationContextSingleton.INSTANCE.getApplicationContext() == null) {
            GXApplicationContextSingleton.INSTANCE.setApplicationContext(applicationContext);
        }
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // ????????????
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setOptimizeJoin(true);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        // ?????????????????????????????????
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // ???????????????
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // sql??????????????????
        List<String> activeProfiles = Convert.toList(String.class, applicationContext.getEnvironment().getActiveProfiles());
        // ??????????????????,?????????????????????sql??????????????????
        if (!CollUtil.contains(activeProfiles, GXCommonConstant.RUN_ENV_PROD)) {
            //interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        }
        // ???????????????(???????????????????????????tenant_id??????)
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new GXTenantLineHandler()));
        // ??????????????????
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> tableName);
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
        return interceptor;
    }

    @Bean
    @ConditionalOnExpression("'${use-camel-case-mapping}'.equals('true')")
    public ConfigurationCustomizer configurationCustomizer() {
        return GXMyBatisPlusConfig::customize;
    }

    /**
     * ???????????????????????????
     *
     * @author britton
     * @since 2021-11-17
     */
    private static class GXTenantLineHandler implements TenantLineHandler {
        /**
         * ?????????????????????
         */
        private static final CaffeineCacheManager caffeineCacheManager;

        static {
            caffeineCacheManager = GXSpringContextUtils.getBean(CaffeineCacheManager.class);
        }

        /**
         * ???????????? ID ?????????????????????????????? ID ???
         * <p>
         *
         * @return ?????? ID ????????????
         */
        @Override
        public Expression getTenantId() {
            GXTenantIdService tenantIdService = GXSpringContextUtils.getBean(GXTenantIdService.class);
            if (Objects.isNull(tenantIdService)) {
                return new LongValue(0);
            }
            return tenantIdService.getTenantId();
        }

        /**
         * ?????????????????????
         * <p>
         * ??????????????????: tenant_id
         *
         * @return ???????????????
         */
        @Override
        public String getTenantIdColumn() {
            return "tenant_id";
        }

        /**
         * ???????????????????????????????????????????????????
         * <p>
         * ????????????????????????????????????????????????
         *
         * @param tableName ??????
         * @return ????????????, true:???????????????false:????????????????????????????????????
         */
        @Override
        public boolean ignoreTable(String tableName) {
            assert caffeineCacheManager != null;
            Cache cache = caffeineCacheManager.getCache("FRAMEWORK-CACHE");
            assert cache != null;
            Boolean hasTenantIdField = cache.get(tableName, Boolean.class);
            if (Objects.nonNull(hasTenantIdField)) {
                return Boolean.TRUE.equals(hasTenantIdField);
            }
            TableInfo tableInfo = TableInfoHelper.getTableInfo(tableName);
            if (Objects.isNull(tableInfo)) {
                cache.put(tableName, Boolean.TRUE);
                return true;
            }
            boolean contains = !CollUtil.contains(tableInfo.getFieldList(), field -> CharSequenceUtil.equalsIgnoreCase(field.getColumn(), getTenantIdColumn()));
            cache.put(tableName, contains);
            return contains;
        }
    }
}
