package com.itacademy.waceplare.mapper;

import com.itacademy.waceplare.dto.CommentDTO;
import com.itacademy.waceplare.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {
    @Mapping(source = "author", target = "author")
    CommentDTO toCommentDTO(Comment comment);
}
