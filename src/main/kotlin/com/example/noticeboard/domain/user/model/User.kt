package com.example.noticeboard.domain.user.model

import com.example.noticeboard.domain.shared.model.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class User (
    val username: String,
    var password: String,
): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}