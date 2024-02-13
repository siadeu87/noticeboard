package com.example.noticeboard.domain.board.service

import com.example.noticeboard.domain.board.dto.CreatedBoardRequest
import com.example.noticeboard.domain.board.dto.UpdateBoardRequest
import com.example.noticeboard.domain.board.model.Board
import com.example.noticeboard.domain.board.model.Category
import com.example.noticeboard.domain.board.repository.BoardRepository
import com.example.noticeboard.domain.exception.ModelNotFoundException
import com.example.noticeboard.domain.user.model.User
import com.example.noticeboard.domain.user.repository.UserRepository
import com.example.noticeboard.infra.querydsl.QueryDslSupport
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslSupport::class])
@ActiveProfiles("test")
class BoardServiceTest  @Autowired constructor(
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository
) {
    private val boardService = BoardServiceImpl(boardRepository, userRepository)


    @Test
    fun `board존재하지 않을때 boardId를 요청을 했을때 예외가 발생되는지 확인`() {
        // GiVEN
        val boardId = 1L

        // WHEN & THEN
        shouldThrow<ModelNotFoundException> {
            boardService.getBoard(boardId)
        }.let {
            it.message shouldBe "Model Board not found with given id: 1"
        }
    }
    @Test
    fun `정상적으로 board 생성되는 시나리오 확인`(){
        // GIVEN
        val user = userRepository.saveAndFlush(DEFAULT_USER)
        val createdBoard = CreatedBoardRequest(title = "title", category = Category.ALL, tag = "tag", content = "content")

        // WHEN
        val result = user.id?.let { boardService.createdBoard(it, createdBoard) }

        // THEN
        result?.let {
            val foundBoard = boardRepository.findByIdAndDeletedAtIsNull(it.id!!)
            foundBoard?.title shouldBe "title"
            foundBoard?.tag shouldBe "tag"
            foundBoard?.content shouldBe "content"
            foundBoard?.category shouldBe Category.ALL
        }
        boardRepository.findAll()
            .filter { it.title == "title" }
            .let {
                it.size shouldBe 1
                it[0].tag shouldBe "tag"
            }
    }

    @Test
    fun `정상적으로 board 수정되는 시나리오 확인`(){
        // GIVEN
        val user = userRepository.saveAndFlush(DEFAULT_USER)
        val board = boardRepository.saveAndFlush(DEFAULT_BOARD)
        val updateBoard = UpdateBoardRequest(title = "updateTitle", category = Category.NOTICE, tag = "updateTag", content = "update")

        // WHEN
        val updated = user.id?.let { board.id?.let { boardService.updateBoard(it,it,updateBoard) } }

        // THEN
        updated?.let {
            val foundBoard = boardRepository.findByIdAndDeletedAtIsNull(it.id!!)
            foundBoard?.title shouldBe "updateTitle"
            foundBoard?.tag shouldBe "updateTag"
            foundBoard?.content shouldBe "update"
        }
    }
    companion object{
        private val DEFAULT_USER = User(username = "test111", password = "test111!")
        private val DEFAULT_BOARD = Board(user = DEFAULT_USER, title = "title1", tag = "tag1", category = Category.COMMON, content = "content1")
    }

}