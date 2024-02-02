package com.example.noticeboard.domain.comment.controller

import com.example.noticeboard.domain.comment.dto.CommentResponse
import com.example.noticeboard.domain.comment.dto.UpdateCommentRequest
import com.example.noticeboard.domain.comment.dto.WriteCommentRequest
import com.example.noticeboard.domain.comment.service.CommentService
import com.example.noticeboard.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/boards/{boardId}/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    fun writeComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable boardId: Long,
        @RequestBody writeCommentRequest: WriteCommentRequest
    ): ResponseEntity<CommentResponse>{
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.writeComment(userPrincipal.id,boardId, writeCommentRequest))
    }
    @PutMapping("/{commentId}")
    fun updateComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable boardId: Long,
        @PathVariable commentId: Long,
        @RequestBody updateCommentRequest: UpdateCommentRequest
    ): ResponseEntity<CommentResponse>{
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(userPrincipal.id, boardId, commentId, updateCommentRequest))
    }
    @DeleteMapping("/{commentId}")
    fun deletedComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable boardId: Long,
        @PathVariable commentId: Long,
    ): ResponseEntity<Unit>{
        commentService.deletedComment(userPrincipal.id, boardId, commentId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}