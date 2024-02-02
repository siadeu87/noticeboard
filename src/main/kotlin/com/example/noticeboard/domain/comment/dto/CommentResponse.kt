package com.example.noticeboard.domain.comment.dto

import com.example.noticeboard.domain.comment.model.Comment
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long?,
    val content: String,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime
){
    companion object{
        fun to(comment: Comment): CommentResponse{
            return CommentResponse(
                id = comment.id,
                content = comment.content,
                createdAt = comment.createdAt,
                lastModifiedAt = comment.lastModifiedAt
            )
        }
    }
}
