package com.example.noticeboard.domain.board.service

import com.example.noticeboard.domain.board.dto.BoardResponse
import com.example.noticeboard.domain.board.dto.CreatedBoardRequest
import com.example.noticeboard.domain.board.model.Board
import com.example.noticeboard.domain.board.repository.BoardRepository
import com.example.noticeboard.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl(
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository
): BoardService {
    override fun createdBoard(userId: Long, request: CreatedBoardRequest): BoardResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw Exception("User not found")
        val board = Board(
            user = user,
            username = user.username,
            title = request.title,
            category = request.category,
            tag = request.tag,
            content = request.content,
        )
        val result = boardRepository.save(board)
        return BoardResponse.to(result)
    }
}