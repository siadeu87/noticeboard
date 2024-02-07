package com.example.noticeboard.domain.board.repository

import com.example.noticeboard.domain.board.model.Board
import com.example.noticeboard.domain.board.model.Category
import com.example.noticeboard.domain.board.model.QBoard
import com.example.noticeboard.infra.querydsl.QueryDslSupport
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class BoardRepositoryImpl: CustomBoardRepository, QueryDslSupport() {
    private val board = QBoard.board
    override fun searchBoardList(category: Category, title: String?, tag: String?, content: String?): List<Board> {
        return queryFactory.selectFrom(board)
            .where(categoryEq(category), titleLike(title), tagLike(tag), contentLike(content)
                ?.and(board.deletedAt.isNull))
            .orderBy(board.createdAt.desc())
            .fetch()
    }

    override fun findByPageableAndDeletedAtIsNull(pageable: Pageable): Page<Board> {
        val totalCount = queryFactory.select(board.count()).from(board).where(board.deletedAt.isNull).fetchOne() ?: 0L

        val contents = queryFactory.selectFrom(board)
            .where(board.deletedAt.isNull)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(
                if (pageable.sort.isSorted){
                    when(pageable.sort.first()?.property){
                        "id" -> board.id.asc()
                        "title" -> board.title.asc()
                        "heart" -> board.countHeart.desc()
                        "createdAt" -> board.createdAt.desc()
                        else -> board.createdAt.desc()
                    }
                } else board.createdAt.desc()
            )
            .fetch()

        return PageImpl(contents, pageable, totalCount)
    }

    private fun titleLike(title: String?): BooleanExpression?{
        if(title == null) return null
        return board.title.like("%$title%")
    }

    private fun tagLike(tag: String?): BooleanExpression?{
        if (tag == null) return null
        return board.tag.like("%$tag%")
    }

    private fun contentLike(content: String?): BooleanExpression?{
        if (content == null) return null
        return board.content.like("%$content%")
    }

    private fun categoryEq(category: Category): BooleanExpression?{
        return when(category) {
            Category.ALL -> null
            Category.COMMON -> board.category.eq(Category.COMMON)
            Category.NOTICE -> board.category.eq(Category.NOTICE)
            Category.INFORMATION -> board.category.eq(Category.INFORMATION)
        }
    }
}