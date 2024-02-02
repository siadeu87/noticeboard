package com.example.noticeboard.domain.comment.service

import com.example.noticeboard.domain.board.repository.BoardRepository
import com.example.noticeboard.domain.comment.dto.CommentResponse
import com.example.noticeboard.domain.comment.dto.UpdateCommentRequest
import com.example.noticeboard.domain.comment.dto.WriteCommentRequest
import com.example.noticeboard.domain.comment.model.Comment
import com.example.noticeboard.domain.comment.repository.CommentRepository
import com.example.noticeboard.domain.exception.ModelNotFoundException
import com.example.noticeboard.domain.exception.UnauthorizedException
import com.example.noticeboard.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

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

    override fun updateComment(userId: Long, boardId: Long, commentId: Long, request: UpdateCommentRequest
    ): CommentResponse {
        boardRepository.findByIdAndDeletedAtIsNull(boardId) ?: throw ModelNotFoundException("Board", boardId)
        val comment = commentRepository.findByIdAndDeletedAtIsNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        if(comment.user.id != userId) throw throw UnauthorizedException()
        comment.also { it.updateContent(request.content) }
            .also { it.lastModifiedAt = LocalDateTime.now() }
        commentRepository.save(comment)
        return CommentResponse.to(comment)
    }

    override fun deletedComment(userId: Long, boardId: Long, commentId: Long) {
        boardRepository.findByIdAndDeletedAtIsNull(boardId) ?: throw ModelNotFoundException("Board", boardId)
        val comment = commentRepository.findByIdAndDeletedAtIsNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)
        if(comment.user.id != userId) throw throw UnauthorizedException()
        comment.deletedAt = LocalDateTime.now()
        commentRepository.save(comment)
    }
}