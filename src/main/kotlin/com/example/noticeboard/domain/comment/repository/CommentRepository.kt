package com.example.noticeboard.domain.comment.repository

import com.example.noticeboard.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
}