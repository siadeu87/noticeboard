package com.example.noticeboard.domain.board.repository

import com.example.noticeboard.domain.board.model.Board
import com.example.noticeboard.domain.board.model.QBoard
import com.example.noticeboard.infra.querydsl.QueryDslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class BoardRepositoryImpl: CustomBoardRepository, QueryDslSupport() {
    private val board = QBoard.board
    override fun searchBoardListByTitle(title: String): List<Board> {
        return queryFactory.selectFrom(board)
            .where(board.title.containsIgnoreCase(title))
            .fetch()
    }

    override fun findByPageableAndDeletedAtIsNull(pageable: Pageable): Page<Board> {
        val totalCount = queryFactory.select(board.count()).from(board).where(board.deletedAt.isNull).fetchOne() ?: 0L

        val query = queryFactory.selectFrom(board)
            .where(board.deletedAt.isNull)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        if (pageable.sort.isSorted){
            when(pageable.sort.first()?.property){
                "id" -> query.orderBy(board.id.asc())
                "title" -> query.orderBy(board.title.asc())
                "heart" -> query.orderBy(board.countHeart.desc())
                else -> query.orderBy(board.id.asc())
            }
        } else {
            query.orderBy(board.id.asc())
        }

        val contents = query.fetch()

        return PageImpl(contents, pageable, totalCount)
    }
}