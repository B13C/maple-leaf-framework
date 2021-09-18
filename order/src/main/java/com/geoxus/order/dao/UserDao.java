package com.geoxus.order.dao;

import com.geoxus.core.common.dao.GXBaseDao;
import com.geoxus.order.entity.UserEntity;
import com.geoxus.order.mapper.UserMapper;
import com.geoxus.order.common.dto.protocol.res.UserResProtocol;
import com.geoxus.order.mapper.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends GXBaseDao<UserMapper, UserEntity> {
    public UserResProtocol getUserInfo(Long userId) {
        return baseMapper.getUserInfo(userId);
    }
}
