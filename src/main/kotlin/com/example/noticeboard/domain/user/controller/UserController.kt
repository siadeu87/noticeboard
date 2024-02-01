package com.example.noticeboard.domain.user.controller

import com.example.noticeboard.domain.user.dto.LoginRequest
import com.example.noticeboard.domain.user.dto.LoginResponse
import com.example.noticeboard.domain.user.dto.SignupRequest
import com.example.noticeboard.domain.user.dto.UserResponse
import com.example.noticeboard.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class UserController(
    private val userService: UserService
) {
    @PostMapping
    fun signup(@RequestBody signupRequest: SignupRequest): ResponseEntity<UserResponse>{
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(signupRequest))
    }
    @PostMapping
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse>{
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequest))
    }
}