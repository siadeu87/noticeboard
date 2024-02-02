package com.example.noticeboard.domain.board.controller

import com.example.noticeboard.domain.board.dto.BoardResponse
import com.example.noticeboard.domain.board.dto.CreatedBoardRequest
import com.example.noticeboard.domain.board.service.BoardService
import com.example.noticeboard.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board")
class BoardController(
    private val boardService: BoardService
) {
    @GetMapping
    fun getBoardList(): ResponseEntity<List<BoardResponse>>{
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoardList())
    }
    @GetMapping("/{boardId}")
    fun getBoard(@PathVariable boardId: Long): ResponseEntity<BoardResponse>{
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoard(boardId))
    }
    @PostMapping
    fun createdBoard(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody createdBoardRequest: CreatedBoardRequest
    ): ResponseEntity<BoardResponse>{
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createdBoard(userPrincipal.id, createdBoardRequest))
    }
}