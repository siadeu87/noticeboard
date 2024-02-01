package com.example.noticeboard.domain.user.dto

import com.example.noticeboard.domain.user.model.User

data class UserResponse(
    val id: Long?,
    val username: String,
){
    companion object{
        fun to(user: User): UserResponse{
            return UserResponse(
                id = user.id,
                username = user.username
            )
        }
    }
}
