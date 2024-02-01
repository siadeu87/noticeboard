package com.example.noticeboard.domain.user.dto

data class SignupRequest(
    val username: String,
    val password: String,
    val checkPassword: String
)
