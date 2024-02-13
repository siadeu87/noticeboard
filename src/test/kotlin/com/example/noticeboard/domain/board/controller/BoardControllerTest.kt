package com.example.noticeboard.domain.board.controller

import com.example.noticeboard.domain.board.dto.BoardResponse
import com.example.noticeboard.domain.board.model.Category
import com.example.noticeboard.domain.board.service.BoardServiceImpl
import com.example.noticeboard.infra.security.jwt.JwtPlugin
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import java.time.LocalDateTime
import java.time.Month

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
@ActiveProfiles("test")
class BoardControllerTest @Autowired constructor(
    private val mockMvc: MockMvc, private val jwtPlugin: JwtPlugin
): DescribeSpec ({
    extension(SpringExtension)

    afterContainer {
        clearAllMocks()
    }

    // BoardService를 구현한 클래스를 목 객체로 생성
    val boardService = mockk<BoardServiceImpl>()

    describe("GET /boards/{boardId}") {
        context("존재하는 ID를 요청을 보낼 때") {
            it("200 status code를 응답해야한다") {
                val boardId = 1L
                val fixedTime = LocalDateTime.of(2023, Month.FEBRUARY, 5, 12, 30, 15) // 2024년 2월 5일 12시 30분 15초

                every { boardService.getBoard(any()) } returns BoardResponse(
                    id = boardId,
                    title = "test_title",
                    category = Category.ALL,
                    tag = "test_tag",
                    content = "test_content",
                    createdAt = fixedTime,
                    lastModifiedAt = fixedTime,
                    heart = 0,
                    username = "user"
                )

                val result = mockMvc.perform(
                    get("/boards/$boardId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andReturn()

                result.response.status shouldBe 200
                // 테스트 결과 404 ????

                val responseDto = jacksonObjectMapper().readValue(
                    result.response.contentAsString,
                    BoardResponse::class.java
                )

                responseDto.id shouldBe boardId
            }
        }
    }
})
