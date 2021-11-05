package cn.maple.core.framework.dto.protocol.req;

import cn.hutool.core.lang.Dict;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GXBaseSearchReqProtocol extends GXBaseReqProtocol {
    private static final long serialVersionUID = -7685836286570517029L;

    /**
     * 分页信息
     * eg:
     * "pagingInfo":{
     * "page":1,
     * "pageSize":20
     * }
     */
    protected Dict pagingInfo;

    /**
     * 搜索条件
     * eg:
     * "searchCondition":{
     * "username":"jack",
     * "openId":"XXXXX"
     * }
     */
    protected Dict searchCondition;
}