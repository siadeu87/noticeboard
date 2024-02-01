package com.example.noticeboard.domain.board.dto

import com.example.noticeboard.domain.board.model.Board
import com.example.noticeboard.domain.board.model.Category
import java.time.LocalDateTime

data class BoardResponse(
    val id: Long?,
    val username: String,
    val title: String,
    val tag: String,
    val category: Category,
    val content: String,
    val createdAt: LocalDateTime,
    val lastModifiedAt: LocalDateTime
){
    companion object{
        fun to(board: Board): BoardResponse{
            return BoardResponse(
                id = board.id,
                username = board.user.username,
                title = board.title,
                tag = board.tag,
                category = board.category,
                content = board.content,
                createdAt = board.createdAt,
                lastModifiedAt = board.lastModifiedAt
            )
        }
    }
}
