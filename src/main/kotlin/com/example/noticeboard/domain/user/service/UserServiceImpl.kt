package com.example.noticeboard.domain.user.service

import com.example.noticeboard.domain.user.dto.LoginRequest
import com.example.noticeboard.domain.user.dto.LoginResponse
import com.example.noticeboard.domain.user.dto.SignupRequest
import com.example.noticeboard.domain.user.dto.UserResponse
import com.example.noticeboard.domain.user.model.User
import com.example.noticeboard.domain.user.repository.UserRepository
import com.example.noticeboard.infra.security.jwt.JwtPlugin
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val jwtPlugin: JwtPlugin
): UserService {
    override fun signup(request: SignupRequest): UserResponse {
        if(userRepository.existsByUsername(request.username)){
            throw Exception("Username is already in use")
        }

        val result = if(request.password == request.checkPassword){
            userRepository.save(
                User(
                    username = request.username,
                    password = request.password,
                )
            )
        } else {
            throw Exception("Password does not match")
        }

        return UserResponse.to(result)
    }

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByUsername(request.username) ?: throw Exception("Username or Password not found")

        if(user.password != request.password){
            throw Exception("Username or Password not found")
        }
        val login = LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                username = user.username
            )
        )
        return login
    }
}