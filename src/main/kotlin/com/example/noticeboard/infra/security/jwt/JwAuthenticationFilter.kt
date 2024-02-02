package com.example.noticeboard.infra.security.jwt

import com.example.noticeboard.infra.security.UserPrincipal
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwAuthenticationFilter(
    private val jwtPlugin: JwtPlugin
): OncePerRequestFilter() {

    companion object{
        private val BEARER_PATTERN = Regex("^Bearer (.+?)$")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = request.getBearerToken()

        if(jwt != null){
            jwtPlugin.validateToken(jwt)
                .onSuccess {
                    val userId = it.payload.subject.toLong()
                    val username = it.payload.get("username", String::class.java)

                    val principal = UserPrincipal(
                        id = userId,
                        username = username
                    )
                    val authentication = JwtAuthenticationToken(
                        principal = principal,
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    )
                    SecurityContextHolder.getContext().authentication = authentication
                }
        }
        filterChain.doFilter(request, response)
    }

    private fun HttpServletRequest.getBearerToken(): String?{
        val headerValue = this.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        return BEARER_PATTERN.find(headerValue)?.groupValues?.get(1)
    }
}