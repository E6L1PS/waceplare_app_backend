package com.itacademy.waceplare.service;

import com.itacademy.waceplare.exception.AdNotFoundException;
import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.Comment;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.AdRepository;
import com.itacademy.waceplare.repository.CommentRepository;
import com.itacademy.waceplare.service.interfaces.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    public final CommentRepository commentRepository;

    public final AdRepository adRepository;


    @Transactional(readOnly = true)
    @Override
    public List<Comment> getCommentsByAdId(Long adId, PageRequest pageRequest) {
        Page<Comment> page = commentRepository.findAllByAdId(adId, pageRequest);
        return page.getContent();
    }


    @Override
    public Comment publishCommentByAdId(Long adId, String text) {
        Optional<Ad> adByAdId = adRepository.findById(adId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (adByAdId.isPresent()) {
            return commentRepository.save(
                    Comment.builder()
                            .ad(adByAdId.get())
                            .text(text)
                            .date(LocalDateTime.now())
                            .author(user)
                            .build()
            );
        } else {
            throw new AdNotFoundException("Ad with id " + adId + " not found.");
        }
    }

    @Override
    public Comment changeCommentByAdId(Long adId, String text) {
        return commentRepository.updateComment(adId, text, LocalDateTime.now());
    }

    @Override
    public void deleteCommentById(Long commentId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentRepository.deleteByIdAndUserId(commentId, user);
    }

    @Override
    public void deleteCommentsByAdId(Long adId) {
        Long userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Optional<Ad> ad = adRepository.findById(adId);

        if (ad.isPresent()) {
            User user = ad.get().getUser();
            log.info(user.getId() + " " + userId);

            if (Objects.equals(user.getId(), userId)) {
                commentRepository.deleteByAdId(adId);
            } else {
                throw new AccessDeniedException("У вас нет разрешения на удаление комментариев для данного объявления");
            }
        }
    }
}
