package com.example.noticeboard.infra.security.jwt

import com.example.noticeboard.infra.security.UserPrincipal
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetails
import java.io.Serializable

class JwtAuthenticationToken(
    private val principal: UserPrincipal,
    details: WebAuthenticationDetails,
): AbstractAuthenticationToken(null), Serializable {

    init {
        super.setAuthenticated(true)
        super.setDetails(details)
    }

    override fun getPrincipal() = principal

    override fun getCredentials() = null

    override fun isAuthenticated(): Boolean {
        return true
    }
}