package com.itacademy.waceplare.mapper;

import com.itacademy.waceplare.dto.AdDTO;
import com.itacademy.waceplare.model.Ad;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdMapper {
    AdMapper INSTANCE = Mappers.getMapper(AdMapper.class);

    AdDTO toAdDTO(Ad ad);

    Ad toAd(AdDTO adDTO);
}
