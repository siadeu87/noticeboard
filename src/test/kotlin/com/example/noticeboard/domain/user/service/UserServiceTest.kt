package com.example.noticeboard.domain.user.service

import com.example.noticeboard.domain.user.dto.SignupRequest
import com.example.noticeboard.domain.user.model.User
import com.example.noticeboard.domain.user.repository.UserRepository
import com.example.noticeboard.infra.querydsl.QueryDslSupport
import com.example.noticeboard.infra.security.jwt.JwtPlugin
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = [QueryDslSupport::class])
@ActiveProfiles("test")
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
) {
    @MockBean
    private lateinit var jwtPlugin: JwtPlugin
    @MockBean
    private lateinit var passwordEncoder: PasswordEncoder

    private lateinit var userService: UserServiceImpl
    @BeforeEach
    fun setup() {
        passwordEncoder = mockk()
        userService = UserServiceImpl(userRepository, jwtPlugin, passwordEncoder)
    }
    @Test
    fun `이미 회원가입되어있는 이메일이라면 예외가 발생하는지 확인`() {
        // GIVEN
        val 기존_회원 = User(username = "user", password = "1234")
        userRepository.saveAndFlush(기존_회원)
        val req = SignupRequest(username = "user", password = "4321", checkPassword = "4321")

        // WHEN & THEN
        shouldThrow<IllegalStateException> {
            userService.signup(req)
        }.let {
            it.message shouldBe "Username is already in use"
        }

        // 추가로 데이터베이스에 저장된 값까지 검증할 수 있다.
        userRepository.findAll()
            .filter { it.username == "user" }
            .let {
                it.size shouldBe 1
                it[0].password == "1234"
            }
    }

    @Test
    fun `정상적으로 회원가입되는 시나리오 확인`() {
        // GIVEN
        val req = SignupRequest(username = "user", password = "4321", checkPassword = "4321")

        // WHEN
        every { passwordEncoder.encode(any()) } returns "encodedPassword"
        val result = userService.signup(req)

        // THEN
        result.username shouldBe "user"
        userRepository.findAll()
            .filter { it.username == "user" }
            .let {
                it.size shouldBe 1L
            }
    }

}