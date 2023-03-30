package com.itacademy.waceplare.dto;

import com.itacademy.waceplare.model.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdDTO {
    private Integer price;
    private String title;
    private String description;
    private Category category;

}
