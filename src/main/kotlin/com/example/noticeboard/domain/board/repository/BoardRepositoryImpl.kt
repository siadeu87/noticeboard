package com.example.noticeboard.domain.board.repository

import com.example.noticeboard.domain.board.model.Board
import com.example.noticeboard.domain.board.model.QBoard
import com.example.noticeboard.infra.querydsl.QueryDslSupport
import org.springframework.stereotype.Repository

@Repository
class BoardRepositoryImpl: CustomBoardRepository, QueryDslSupport() {
    private val board = QBoard.board
    override fun searchBoardListByTitle(title: String): List<Board> {
        return queryFactory.selectFrom(board)
            .where(board.title.containsIgnoreCase(title))
            .fetch()
    }
}