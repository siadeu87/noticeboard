package com.example.noticeboard.domain.exception

data class InvalidCredentialException(
    override val message: String? = "The credential is invalid"
): RuntimeException()
