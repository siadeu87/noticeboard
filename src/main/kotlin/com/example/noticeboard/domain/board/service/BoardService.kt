package com.example.noticeboard.domain.board.service

import com.example.noticeboard.domain.board.dto.BoardResponse
import com.example.noticeboard.domain.board.dto.CreatedBoardRequest
import com.example.noticeboard.domain.board.dto.UpdateBoardRequest
import com.example.noticeboard.domain.board.model.BoardSearchType
import com.example.noticeboard.domain.board.model.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardService {
    fun searchBoardByTitle(category: Category, searchType: BoardSearchType, keyword: String): List<BoardResponse>
    fun getPaginatedBoardList(pageable: Pageable): Page<BoardResponse>
    fun getBoard(boardId: Long): BoardResponse
    fun createdBoard(userId: Long, request: CreatedBoardRequest): BoardResponse
    fun updateBoard(userId: Long, boardId: Long, request: UpdateBoardRequest): BoardResponse
    fun deletedBoard(userId: Long, boardId: Long)
}