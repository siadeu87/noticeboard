package com.example.noticeboard.domain.board.dto

import com.example.noticeboard.domain.board.model.Category

data class UpdateBoardRequest(
    val title: String,
    val category: Category,
    val tag: String,
    val content: String,
)
