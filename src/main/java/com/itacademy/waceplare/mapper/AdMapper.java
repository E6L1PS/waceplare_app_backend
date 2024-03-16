package com.itacademy.waceplare.mapper;

import com.itacademy.waceplare.dto.AdDto;
import com.itacademy.waceplare.model.Ad;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdMapper extends Mappable<Ad, AdDto> {
}
