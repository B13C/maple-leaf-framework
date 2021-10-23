package com.geoxus.core.framework.constant;

import com.geoxus.common.annotation.GXFieldComment;

public class GXCoreModelConstant {
    @GXFieldComment(zhDesc = "主键ID")
    public static final String PRIMARY_KEY = "model_id";

    @GXFieldComment(zhDesc = "数据表名")
    public static final String TABLE_NAME = "core_model";

    @GXFieldComment(zhDesc = "数据表的别名")
    public static final String TABLE_ALIAS_NAME = "core_model";

    @GXFieldComment(zhDesc = "模型的值")
    public static final String MODEL_IDENTIFICATION_VALUE = "core_model";

    private GXCoreModelConstant() {
    }
}