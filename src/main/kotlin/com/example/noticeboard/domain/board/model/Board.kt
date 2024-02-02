package com.example.noticeboard.domain.board.model

import com.example.noticeboard.domain.shared.model.BaseEntity
import com.example.noticeboard.domain.user.model.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Board(
    @ManyToOne @JoinColumn(name = "user_id") val user: User,
    var title: String,
    var tag: String,
    var content: String,
    @Enumerated(EnumType.STRING) var category: Category,
): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var deletedAt: LocalDateTime? = null

    fun updateBoard(title: String, category: Category, tag: String, content: String){
        this.title = title
        this.category = category
        this.tag = tag
        this.content = content
    }
}
