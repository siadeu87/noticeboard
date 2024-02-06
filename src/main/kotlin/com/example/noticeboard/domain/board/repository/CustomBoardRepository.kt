package com.example.noticeboard.domain.board.repository

import com.example.noticeboard.domain.board.model.Board
import com.example.noticeboard.domain.board.model.BoardSearchType
import com.example.noticeboard.domain.board.model.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomBoardRepository {
    fun searchBoardList(category: Category, searchType: BoardSearchType, keyword: String): List<Board>
    fun findByPageableAndDeletedAtIsNull(pageable: Pageable): Page<Board>
}