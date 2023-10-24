package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.dto.CommentDTO;
import com.itacademy.waceplare.mapper.CommentMapper;
import com.itacademy.waceplare.service.interfaces.ICommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final ICommentService commentService;

    private final CommentMapper commentMapper;

    @GetMapping("/{adId}")
    public List<CommentDTO> getCommentsByAdId(
            @PathVariable Long adId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return commentService.getCommentsByAdId(adId, PageRequest.of(page, size)).stream()
                .map(commentMapper::toCommentDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PostMapping("/{adId}")
    public CommentDTO publishCommentByAdId(@PathVariable Long adId, @RequestBody String text) {
        return commentMapper.toCommentDTO(commentService.publishCommentByAdId(adId, text));
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PutMapping("/{adId}")
    public CommentDTO changeCommentByAdId(@PathVariable Long adId, @RequestBody String text) {
        return commentMapper.toCommentDTO(commentService.changeCommentByAdId(adId, text));
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @DeleteMapping("/{commentId}")
    public void deleteCommentById(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @DeleteMapping("/ad/{adId}")
    public void deleteCommentsByAdId(@PathVariable Long adId) {
        commentService.deleteCommentsByAdId(adId);
    }
}
