package com.itacademy.waceplare.mapper;

import com.itacademy.waceplare.dto.UserInfoDto;
import com.itacademy.waceplare.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserInfoMapper extends Mappable<User, UserInfoDto> {

}
