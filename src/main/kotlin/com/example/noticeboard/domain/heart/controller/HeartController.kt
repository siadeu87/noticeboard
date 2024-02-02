package com.example.noticeboard.domain.heart.controller

import com.example.noticeboard.domain.heart.dto.HeartResponse
import com.example.noticeboard.domain.heart.service.HeartService
import com.example.noticeboard.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/board/{boardId}/hearts")
class HeartController(
    private val heartService: HeartService
) {

    @PostMapping
    fun reviewHeart(
        @PathVariable boardId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<HeartResponse>{
        val heart = heartService.reviewHeart(boardId, userPrincipal.id)
        return ResponseEntity.status(HttpStatus.OK).body(heart)
    }
}