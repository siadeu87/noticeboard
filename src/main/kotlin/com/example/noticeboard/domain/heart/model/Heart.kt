package com.example.noticeboard.domain.heart.model

import com.example.noticeboard.domain.board.model.Board
import com.example.noticeboard.domain.user.model.User
import jakarta.persistence.*

@Entity
data class Heart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @ManyToOne @JoinColumn(name = "user_id") val user: User,
    @ManyToOne @JoinColumn(name = "board_id") val board: Board
)
