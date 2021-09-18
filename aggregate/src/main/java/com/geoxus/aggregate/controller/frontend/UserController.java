package com.geoxus.aggregate.controller.frontend;

import com.geoxus.aggregate.dto.protocol.req.UserReqProtocol;
import com.geoxus.common.annotation.GXRequestBody;
import com.geoxus.common.util.GXResultUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @PostMapping("index")
    public GXResultUtil<String> hello(@GXRequestBody @Validated UserReqProtocol userReqProtocol) {
        System.out.println(userReqProtocol);
        return GXResultUtil.ok("Hello World");
    }
}
