package com.itacademy.waceplare.dto;

import com.itacademy.waceplare.model.Category;
import lombok.Data;

@Data
public class AdDTO {
    private Integer price;
    private String title;
    private String description;
    private Long userId;
    private Category category;

}
