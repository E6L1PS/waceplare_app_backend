package com.itacademy.waceplare.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDTO {

    private String text;
    private LocalDateTime date;
    private UserInfo author;

}
