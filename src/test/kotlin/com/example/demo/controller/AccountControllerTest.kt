package com.example.demo.controller

import com.example.demo.TEST_ACCOUNTS
import com.example.demo.any
import com.example.demo.service.AccountService
import com.example.demo.service.TransactionService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal.ZERO

@WebMvcTest
@ActiveProfiles("test")
@Suppress("SpringJavaInjectionPointsAutowiringInspection")
internal class AccountControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var accountService: AccountService

    @MockBean
    private lateinit var transactionService: TransactionService

    @Test
    fun `find accounts when no content`() {
        this.mockMvc
            .perform(get("/api/v1/accounts"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    fun `find accounts when content present`() {
        Mockito.`when`(accountService.findAll()).thenAnswer { TEST_ACCOUNTS }
        this.mockMvc
            .perform(get("/api/v1/accounts"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].createdAt").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].amount").isNotEmpty)
    }

    @Test
    fun `find account by id when account exist`() {
        Mockito.`when`(accountService.findAccountById(0L)).thenAnswer { TEST_ACCOUNTS[0] }
        this.mockMvc
            .perform(get("/api/v1/accounts/0"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0L))
            .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(ZERO))
    }

    @Test
    fun `find account by id when account not exist`() {
        this.mockMvc
            .perform(get("/api/v1/accounts/10"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    fun `create new account`() {
        Mockito.`when`(accountService.saveAccount(any()))
            .thenAnswer { TEST_ACCOUNTS[2] }
        this.mockMvc
            .perform(
                post("/api/v1/accounts")
                    .content("{\"amount\":500}")
                    .contentType("application/json")
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }
}