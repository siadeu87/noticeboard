package com.example.noticeboard.domain.comment.service

import com.example.noticeboard.domain.comment.dto.CommentResponse
import com.example.noticeboard.domain.comment.dto.UpdateCommentRequest
import com.example.noticeboard.domain.comment.dto.WriteCommentRequest

interface CommentService {
    fun writeComment(userId: Long, boardId: Long, request: WriteCommentRequest): CommentResponse
    fun updateComment(userId: Long, boardId: Long, commentId: Long, request: UpdateCommentRequest): CommentResponse
    fun deletedComment(userId: Long, boardId: Long, commentId: Long)
}