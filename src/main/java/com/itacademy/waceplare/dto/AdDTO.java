package com.itacademy.waceplare.dto;

import com.itacademy.waceplare.model.StateAd;
import com.itacademy.waceplare.model.TypeAd;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdDTO {
    private Integer price;
    private String title;
    private String description;
    private TypeAd type;
    private StateAd state;

}
