package com.example.noticeboard.domain.comment.model

import com.example.noticeboard.domain.board.model.Board
import com.example.noticeboard.domain.shared.model.BaseEntity
import com.example.noticeboard.domain.user.model.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Comment(
    val content: String,
    @ManyToOne @JoinColumn(name = "user_id") val user: User,
    @ManyToOne @JoinColumn(name = "board_id") val board: Board
): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}