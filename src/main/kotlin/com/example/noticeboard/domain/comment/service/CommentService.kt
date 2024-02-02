package com.example.noticeboard.domain.comment.service

import com.example.noticeboard.domain.comment.dto.CommentResponse
import com.example.noticeboard.domain.comment.dto.WriteCommentRequest

interface CommentService {
    fun writeComment(userId: Long, boardId: Long, request: WriteCommentRequest): CommentResponse
}