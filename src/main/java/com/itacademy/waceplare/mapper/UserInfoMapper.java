package com.itacademy.waceplare.mapper;

import com.itacademy.waceplare.dto.UserInfo;
import com.itacademy.waceplare.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserInfoMapper {
    UserInfo toUserInfo(User user);
}
