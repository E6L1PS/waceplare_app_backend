package com.itacademy.waceplare.repository;

import com.itacademy.waceplare.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<Comment, Long> {
}
