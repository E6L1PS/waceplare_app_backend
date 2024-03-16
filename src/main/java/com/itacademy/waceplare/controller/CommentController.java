package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.dto.CommentDto;
import com.itacademy.waceplare.mapper.CommentMapper;
import com.itacademy.waceplare.service.interfaces.CommentService;
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

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    @GetMapping("/{adId}")
    public List<CommentDto> getCommentsByAdId(
            @PathVariable Long adId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return commentService.getCommentsByAdId(adId, PageRequest.of(page, size)).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PostMapping("/{adId}")
    public CommentDto publishCommentByAdId(@PathVariable Long adId, @RequestBody String text) {
        return commentMapper.toDto(commentService.publishCommentByAdId(adId, text));
    }

    @PreAuthorize("hasRole(Role.USER.name())")
    @PutMapping("/{adId}")
    public CommentDto changeCommentByAdId(@PathVariable Long adId, @RequestBody String text) {
        return commentMapper.toDto(commentService.changeCommentByAdId(adId, text));
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
