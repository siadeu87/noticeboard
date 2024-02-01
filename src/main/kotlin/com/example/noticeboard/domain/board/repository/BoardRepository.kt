package com.example.noticeboard.domain.board.repository

import com.example.noticeboard.domain.board.model.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository: JpaRepository<Board, Long> {
}