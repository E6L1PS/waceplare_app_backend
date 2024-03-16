package com.itacademy.waceplare.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    private String text;

    private LocalDateTime date;

    private UserInfoDto author;

}
