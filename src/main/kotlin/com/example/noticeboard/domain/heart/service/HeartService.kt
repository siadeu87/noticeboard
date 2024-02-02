package com.example.noticeboard.domain.heart.service

import com.example.noticeboard.domain.board.repository.BoardRepository
import com.example.noticeboard.domain.heart.dto.HeartResponse
import com.example.noticeboard.domain.heart.model.Heart
import com.example.noticeboard.domain.heart.repository.HeartRepository
import com.example.noticeboard.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class HeartService(
    private val userRepository: UserRepository,
    private val boardRepository: BoardRepository,
    private val heartRepository: HeartRepository
) {
    @Transactional
    fun reviewHeart(reviewId: Long, userId: Long): HeartResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw IllegalArgumentException()
        val board = boardRepository.findByIdOrNull(reviewId) ?: throw IllegalArgumentException()

        val existingHeart = heartRepository.findByUserAndBoard(user, board)

        return if (existingHeart != null) {
            heartRepository.deleteByUserAndBoard(user, board)
            board.countHeart -= 1
            boardRepository.save(board)
            HeartResponse(message = "좋아요 취소")
        } else {
            heartRepository.save(Heart(user=user, board = board))
            board.countHeart += 1
            boardRepository.save(board)
            HeartResponse(message = "좋아요 성공")
        }
    }
}