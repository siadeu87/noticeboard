package com.example.noticeboard.domain.board.service

import com.example.noticeboard.domain.board.dto.BoardResponse
import com.example.noticeboard.domain.board.dto.CreatedBoardRequest
import com.example.noticeboard.domain.board.dto.UpdateBoardRequest
import com.example.noticeboard.domain.board.model.Board
import com.example.noticeboard.domain.board.repository.BoardRepository
import com.example.noticeboard.domain.exception.ModelNotFoundException
import com.example.noticeboard.domain.exception.UnauthorizedException
import com.example.noticeboard.domain.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BoardServiceImpl(
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository
): BoardService {
    override fun searchBoardByTitle(title: String): List<BoardResponse> {
        return boardRepository.searchBoardListByTitle(title).map { BoardResponse.to(it) }
    }

    override fun getPaginatedBoardList(pageable: Pageable): Page<BoardResponse> {
        return boardRepository.findByPageableAndDeletedAtIsNull(pageable).map { BoardResponse.to(it) }
    }


    override fun getBoard(boardId: Long): BoardResponse {
        val board = boardRepository.findByIdAndDeletedAtIsNull(boardId) ?: throw ModelNotFoundException("Board", boardId)
        return BoardResponse.to(board)
    }

    override fun createdBoard(userId: Long, request: CreatedBoardRequest): BoardResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val board = Board(
            user = user,
            title = request.title,
            category = request.category,
            tag = request.tag,
            content = request.content,
        )
        val result = boardRepository.save(board)
        return BoardResponse.to(result)
    }

    override fun updateBoard(userId: Long, boardId: Long, request: UpdateBoardRequest): BoardResponse {
        val board = boardRepository.findByIdAndDeletedAtIsNull(boardId) ?: throw ModelNotFoundException("Board", boardId)
        if(board.user.id != userId) throw UnauthorizedException()
        board.updateBoard(request.title, request.category, request.tag, request.content)
        board.also { it.lastModifiedAt = LocalDateTime.now() }
        boardRepository.save(board)

        return BoardResponse.to(board)
    }

    override fun deletedBoard(userId: Long, boardId: Long) {
        val board = boardRepository.findByIdAndDeletedAtIsNull(boardId) ?: throw ModelNotFoundException("Board", boardId)
        board.also { if(it.user.id != userId) throw UnauthorizedException() }
            .also { it.deletedAt = LocalDateTime.now() }
            .let { boardRepository.save(it) }
    }
}