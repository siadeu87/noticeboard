package com.example.noticeboard.domain.user.model

import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class User (
    val username: String,
    var password: String,
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}