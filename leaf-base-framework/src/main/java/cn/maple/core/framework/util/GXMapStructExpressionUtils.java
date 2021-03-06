package cn.maple.core.framework.util;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class GXMapStructExpressionUtils {
    /**
     * 私有构造函数
     */
    private GXMapStructExpressionUtils() {
    }

    /**
     * mapstruct的表达式
     * 将json字符串转换为指定的数据类型
     *
     * @param jsonStr     字符串
     * @param targetClass 目标数据类型
     * @param <T>         指定类型
     * @return T
     */
    public static <T> T convertJsonStringToTargetClazz(@NotNull String jsonStr, Class<T> targetClass) {
        if (JSONUtil.isTypeJSON(jsonStr)) {
            return JSONUtil.toBean(jsonStr, targetClass);
        }
        return null;
    }

    /**
     * mapstruct的表达式
     * 将json字符串转换为指定的数据类型
     *
     * @param jsonStr 字符串
     * @return Dict
     */
    public static Dict convertJsonStrToDict(@NotNull String jsonStr) {
        Dict retData = Dict.create();
        if (JSONUtil.isTypeJSON(jsonStr)) {
            Dict data = JSONUtil.toBean(jsonStr, Dict.class);
            data.forEach((key, value) -> retData.set(CharSequenceUtil.toCamelCase(key), value));
        }
        return retData;
    }

    /**
     * mapstruct的表达式
     * 将json字符串转换为指定的数据类型
     *
     * @param jsonStr     字符串
     * @param elementType 列表中的数据元素类型
     * @param <T>         数据元素类型
     * @return List
     */
    public static <T> List<T> convertJsonStrToList(@NotNull String jsonStr, Class<T> elementType) {
        List<T> list = new ArrayList<>();
        if (JSONUtil.isTypeJSONArray(jsonStr)) {
            list = JSONUtil.toList(jsonStr, elementType);
        }
        return list;
    }
}
