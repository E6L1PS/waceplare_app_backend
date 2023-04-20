package com.itacademy.waceplare.dto;

import com.itacademy.waceplare.model.StateAd;
import com.itacademy.waceplare.model.TypeAd;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class AdDTO {
    private Integer price;
    private String title;
    private String description;
    private TypeAd type;
    private StateAd state;

    /*private List<MultipartFile> images;*/

}
