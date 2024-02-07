package com.example.noticeboard.domain.board.repository

import com.example.noticeboard.domain.board.model.Board
import com.example.noticeboard.domain.board.model.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomBoardRepository {
    fun searchBoardList(category: Category, title: String?, tag: String?, content: String?): List<Board>
    fun findByPageableAndDeletedAtIsNull(pageable: Pageable): Page<Board>
}