package com.itacademy.waceplare.mapper;

import com.itacademy.waceplare.dto.CommentDto;
import com.itacademy.waceplare.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper extends Mappable<Comment, CommentDto> {

    @Mapping(source = "author", target = "author")
    @Override
    CommentDto toDto(Comment entity);

}
