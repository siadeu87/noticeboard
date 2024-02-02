package com.example.noticeboard.domain.comment.service

import com.example.noticeboard.domain.board.repository.BoardRepository
import com.example.noticeboard.domain.comment.dto.CommentResponse
import com.example.noticeboard.domain.comment.dto.WriteCommentRequest
import com.example.noticeboard.domain.comment.model.Comment
import com.example.noticeboard.domain.comment.repository.CommentRepository
import com.example.noticeboard.domain.exception.ModelNotFoundException
import com.example.noticeboard.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val userRepository: UserRepository,
    private val boardRepository: BoardRepository,
    private val commentRepository: CommentRepository
): CommentService {
    override fun writeComment(userId: Long, boardId: Long, request: WriteCommentRequest): CommentResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val board = boardRepository.findByIdAndDeletedAtIsNull(boardId) ?: throw ModelNotFoundException("Board", boardId)
        val comment = commentRepository.save(
            Comment(
            user = user,
            board = board,
            content = request.content
            )
        )
        return CommentResponse.to(comment)
    }
}