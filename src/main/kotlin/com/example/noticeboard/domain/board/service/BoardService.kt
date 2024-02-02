package com.example.noticeboard.domain.board.service

import com.example.noticeboard.domain.board.dto.BoardResponse
import com.example.noticeboard.domain.board.dto.CreatedBoardRequest

interface BoardService {
    fun getBoardList(): List<BoardResponse>
    fun getBoard(boardId: Long): BoardResponse
    fun createdBoard(userId: Long, request: CreatedBoardRequest): BoardResponse
}