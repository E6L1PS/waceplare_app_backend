package com.itacademy.waceplare.repository;

import com.itacademy.waceplare.model.Comment;
import com.itacademy.waceplare.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.ad.id = :adId ORDER BY c.date")
    Page<Comment> findAllByAdId(Long adId, PageRequest pageRequest);

    @Modifying
    @Query("UPDATE Comment c SET c.text = :text, c.date = :date WHERE c.ad.id = :adId")
    Comment updateComment(Long adId, String text, LocalDateTime date);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.ad.id = :adId")
    void deleteByAdId(Long adId);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.id = :commentId AND c.author = :user")
    void deleteByIdAndUserId(Long commentId, User user);
}
