package com.example.noticeboard.domain.board.repository

import com.example.noticeboard.domain.board.model.Board

interface CustomBoardRepository {
    fun searchBoardListByTitle(title: String): List<Board>
}