package com.example.noticeboard.domain.heart.repository

import com.example.noticeboard.domain.board.model.Board
import com.example.noticeboard.domain.heart.model.Heart
import com.example.noticeboard.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface HeartRepository: JpaRepository<Heart, Long> {
    fun deleteByUserAndBoard(user: User, board: Board)
    fun findByUserAndBoard(user: User, board: Board): Heart?
}