package com.example.noticeboard.domain.board.repository

import com.example.noticeboard.domain.board.model.Board
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomBoardRepository {
    fun searchBoardListByTitle(title: String): List<Board>
    fun findByPageableAndDeletedAtIsNull(pageable: Pageable): Page<Board>
}