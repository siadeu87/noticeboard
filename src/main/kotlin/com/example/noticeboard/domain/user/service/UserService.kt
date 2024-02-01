package com.example.noticeboard.domain.user.service

import com.example.noticeboard.domain.user.dto.LoginRequest
import com.example.noticeboard.domain.user.dto.LoginResponse
import com.example.noticeboard.domain.user.dto.SignupRequest
import com.example.noticeboard.domain.user.dto.UserResponse

interface UserService {
    fun signup(request: SignupRequest): UserResponse
    fun login(request: LoginRequest): LoginResponse
}