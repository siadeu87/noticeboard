package com.example.noticeboard.domain.board.service

import com.example.noticeboard.domain.board.dto.BoardResponse
import com.example.noticeboard.domain.board.dto.CreatedBoardRequest
import com.example.noticeboard.domain.board.dto.UpdateBoardRequest

interface BoardService {
    fun searchBoardByTitle(title: String): List<BoardResponse>
    fun getBoardList(): List<BoardResponse>
    fun getBoard(boardId: Long): BoardResponse
    fun createdBoard(userId: Long, request: CreatedBoardRequest): BoardResponse
    fun updateBoard(userId: Long, boardId: Long, request: UpdateBoardRequest): BoardResponse
    fun deletedBoard(userId: Long, boardId: Long)
}