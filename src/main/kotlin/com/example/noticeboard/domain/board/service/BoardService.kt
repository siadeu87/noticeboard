package com.example.noticeboard.domain.board.service

import com.example.noticeboard.domain.board.dto.BoardResponse
import com.example.noticeboard.domain.board.dto.CreatedBoardRequest

interface BoardService {
    fun createdBoard(userId: Long, request: CreatedBoardRequest): BoardResponse
}