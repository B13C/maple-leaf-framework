package cn.maple.core.framework.dto.inner.condition;

import cn.hutool.core.text.CharSequenceUtil;

public class GXConditionStrNE extends GXCondition<String> {
    public GXConditionStrNE(String tableNameAlias, String fieldName, String value) {
        super(tableNameAlias, fieldName, value);
    }

    @Override
    public String getOp() {
        return "!=";
    }

    @Override
    public String getFieldValue() {
        return CharSequenceUtil.format("'{}'", value);
    }
}
