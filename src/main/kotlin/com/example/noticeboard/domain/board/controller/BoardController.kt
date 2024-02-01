package com.example.noticeboard.domain.board.controller

import com.example.noticeboard.domain.board.dto.BoardResponse
import com.example.noticeboard.domain.board.dto.CreatedBoardRequest
import com.example.noticeboard.domain.board.service.BoardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/board")
class BoardController(
    private val boardService: BoardService
) {
    @PostMapping
    fun createdBoard(
        @RequestParam userId: Long,
        @RequestBody createdBoardRequest: CreatedBoardRequest
    ): ResponseEntity<BoardResponse>{
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createdBoard(userId, createdBoardRequest))
    }
}