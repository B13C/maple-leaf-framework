package com.geoxus.core.framework.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.geoxus.core.annotation.GXValidateDBExists;
import com.geoxus.core.entity.GXBaseEntity;
import com.geoxus.core.framework.service.impl.GXCoreModelServiceImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@TableName("core_config")
@EqualsAndHashCode(callSuper = false)
public class GXCoreConfigEntity extends GXBaseEntity {
    @TableId
    private Integer configId;

    @GXValidateDBExists(service = GXCoreModelServiceImpl.class)
    private Integer coreModelId;

    @NotBlank()
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String paramKey;

    @NotBlank()
    private String paramValue;

    private Integer status;

    private String remark;
}