package com.example.noticeboard.domain.board.controller

import com.example.noticeboard.domain.board.dto.BoardResponse
import com.example.noticeboard.domain.board.dto.CreatedBoardRequest
import com.example.noticeboard.domain.board.dto.UpdateBoardRequest
import com.example.noticeboard.domain.board.model.Category
import com.example.noticeboard.domain.board.service.BoardService
import com.example.noticeboard.infra.security.UserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/boards")
class BoardController(
    private val boardService: BoardService
) {
    @GetMapping("/search")
    fun searchBoardByList(
        @RequestParam (name = "category") category: Category,
        @RequestParam (name = "title") title: String?,
        @RequestParam (name = "tag") tag: String?,
        @RequestParam (name = "content") content: String?,
    ): ResponseEntity<List<BoardResponse>>{
        return ResponseEntity.status(HttpStatus.OK).body(boardService.searchBoardList(category, title, tag, content))
    }
    @GetMapping
    fun getBoardList(@PageableDefault(size = 15, sort = ["id"]) pageable: Pageable): ResponseEntity<Page<BoardResponse>>{
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getPaginatedBoardList(pageable))
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
    @PutMapping("/{boardId}")
    fun updateBoard(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable boardId: Long,
        @RequestBody updateBoardRequest: UpdateBoardRequest
    ): ResponseEntity<BoardResponse>{
        return ResponseEntity.status(HttpStatus.OK).body(boardService.updateBoard(userPrincipal.id, boardId, updateBoardRequest))
    }
    @DeleteMapping("/{boardId}")
    fun deletedBoard(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable boardId: Long
    ): ResponseEntity<Unit>{
        boardService.deletedBoard(userPrincipal.id, boardId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}