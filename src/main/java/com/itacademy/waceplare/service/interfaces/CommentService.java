package com.itacademy.waceplare.service.interfaces;

import com.itacademy.waceplare.model.Comment;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentsByAdId(Long adId, PageRequest pageRequest);

    Comment publishCommentByAdId(Long adId, String text);

    Comment changeCommentByAdId(Long adId, String text);

    void deleteCommentById(Long commentId);

    void deleteCommentsByAdId(Long adId);
}
