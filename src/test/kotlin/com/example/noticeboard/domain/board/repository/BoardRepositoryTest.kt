package com.example.noticeboard.domain.board.repository

import com.example.noticeboard.domain.board.model.Board
import com.example.noticeboard.domain.board.model.Category
import com.example.noticeboard.domain.user.model.User
import com.example.noticeboard.domain.user.repository.UserRepository
import com.example.noticeboard.infra.querydsl.QueryDslSupport
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslSupport::class])
@ActiveProfiles("test")
class BoardRepositoryTest @Autowired constructor(
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository
) {
    @Test
    fun `title, tag, content 가 NUll 일 경우 전체 데이터 조회되는지 확인`() {
        // GIVEN
        userRepository.saveAndFlush(DEFAULT_USER)
        boardRepository.saveAllAndFlush(DEFAULT_BOARD_LIST)

        // WHEN
        val result = boardRepository.searchBoardList(Category.ALL, "", "", "")

        // THEN
        result.size shouldBe 6

    }

    @Test
    fun `title, tag, content 가 NUll 이 아닌 경우 작성된것에 맞게 검색되는지 결과 확인`(){
        // GIVEN
        userRepository.saveAndFlush(DEFAULT_USER)
        boardRepository.saveAllAndFlush(DEFAULT_BOARD_LIST)

        // WHEN
        val result1 = boardRepository.searchBoardList(Category.ALL, "title", "","")
        val result2 = boardRepository.searchBoardList(Category.ALL, "", "tag","")
        val result3 = boardRepository.searchBoardList(Category.ALL, "","","content")

        // THEN
        result1.size shouldBe 2
        result2.size shouldBe 4
        result3.size shouldBe 1

    }

    @Test
    fun `title, tag, content 통해 조회된 결과가 0일때`(){
        // GIVEN
        userRepository.saveAndFlush(DEFAULT_USER)
        boardRepository.saveAllAndFlush(DEFAULT_BOARD_LIST)

        // WHEN
        val result1 = boardRepository.searchBoardList(Category.ALL, "name", "","")
        val result2 = boardRepository.searchBoardList(Category.ALL, "", "name","")
        val result3 = boardRepository.searchBoardList(Category.ALL, "","","name")
        val result4 = boardRepository.searchBoardList(Category.ALL, "title","tag","name")

        // THEN
        result1.size shouldBe 0
        result2.size shouldBe 0
        result3.size shouldBe 0
        result4.size shouldBe 0
    }

    @Test
    fun `카테고리에 따라 전체 조회 결과확인`(){
        // GIVEN
        userRepository.saveAndFlush(DEFAULT_USER)
        boardRepository.saveAllAndFlush(DEFAULT_BOARD_LIST)

        // WHEN
        val result1 = boardRepository.searchBoardList(Category.ALL, "", "", "")
        val result2 = boardRepository.searchBoardList(Category.COMMON, "", "", "")
        val result3 = boardRepository.searchBoardList(Category.INFORMATION, "", "", "")
        val result4 = boardRepository.searchBoardList(Category.NOTICE, "", "", "")

        // THEN
        result1.size shouldBe 6
        result2.size shouldBe 3
        result3.size shouldBe 2
        result4.size shouldBe 1
    }

    @Test
    fun `카테고리와 title, tag, content 통해 조회된 결과확인`(){
        // GIVEN
        userRepository.saveAndFlush(DEFAULT_USER)
        boardRepository.saveAllAndFlush(DEFAULT_BOARD_LIST)

        // WHEN
        val result1 = boardRepository.searchBoardList(Category.ALL, "test", "test", "test")
        val result2 = boardRepository.searchBoardList(Category.COMMON, "test", "test", "test")
        val result3 = boardRepository.searchBoardList(Category.INFORMATION, "test", "test", "test")
        val result4 = boardRepository.searchBoardList(Category.NOTICE, "test", "test", "test")

        // THEN
        result1.size shouldBe 2
        result2.size shouldBe 1
        result3.size shouldBe 0
        result4.size shouldBe 1
    }

    @Test
    fun `조회된 결과가 6개, PageSize 4일 때 0Page 결과 확인`(){
        // GIVEN
        userRepository.saveAndFlush(DEFAULT_USER)
        boardRepository.saveAllAndFlush(DEFAULT_BOARD_LIST)

        // WHEN
        val result = boardRepository.findByPageableAndDeletedAtIsNull(PageRequest.of(0, 4))

        // THEN
        result.content.size shouldBe 4
        result.isLast shouldBe false
        result.totalPages shouldBe 2
        result.number shouldBe 0
        result.totalElements shouldBe 6
    }

    @Test
    fun `조회된 결과가 6개, PageSize 4일 때 1Page 결과 확인`(){
        // GIVEN
        userRepository.saveAndFlush(DEFAULT_USER)
        boardRepository.saveAllAndFlush(DEFAULT_BOARD_LIST)

        // WHEN
        val result = boardRepository.findByPageableAndDeletedAtIsNull(PageRequest.of(1, 4))

        // THEN
        result.content.size shouldBe 2
        result.isLast shouldBe true
        result.totalPages shouldBe 2
        result.number shouldBe 1
        result.totalElements shouldBe 6
    }

    companion object{
        private val DEFAULT_USER = User(username = "test111", password = "test111!")
        private val DEFAULT_BOARD_LIST = listOf(
            Board(user = DEFAULT_USER, title = "title1", tag = "tag1", category = Category.COMMON, content = "content1"),
            Board(user = DEFAULT_USER, title = "title2", tag = "tag2", category = Category.COMMON, content = "test2"),
            Board(user = DEFAULT_USER, title = "test3", tag = "tag3", category = Category.INFORMATION, content = "test3"),
            Board(user = DEFAULT_USER, title = "test4", tag = "tag4", category = Category.INFORMATION, content = "test4"),
            Board(user = DEFAULT_USER, title = "test5", tag = "test5", category = Category.COMMON, content = "test5"),
            Board(user = DEFAULT_USER, title = "test6", tag = "test6", category = Category.NOTICE, content = "test6")
        )
    }
}
