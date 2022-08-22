package com.example.demo.controller

import com.example.demo.TEST_TRANSACTIONS
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

@WebMvcTest
@ActiveProfiles("test")
@Suppress("SpringJavaInjectionPointsAutowiringInspection")
internal class TransactionControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var accountService: AccountService

    @MockBean
    private lateinit var transactionService: TransactionService

    @Test
    fun `find transactions when no content`() {
        this.mockMvc
            .perform(get("/api/v1/transactions/0"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    fun `find transactions when content present`() {
        Mockito.`when`(transactionService.findTransactionsByAccountId(0L))
            .thenAnswer { TEST_TRANSACTIONS }
        this.mockMvc
            .perform(get("/api/v1/transactions/0"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].createdAt").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].amount").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].fromAccountId").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[*].toAccountId").isNotEmpty)
    }

    @Test
    fun `make transaction test`() {
        Mockito.`when`(transactionService.makeTransaction(any()))
            .thenAnswer { TEST_TRANSACTIONS.first() }
        this.mockMvc
            .perform(
                post("/api/v1/transactions")
                    .content(
                        """{
                       "fromAccountId": 0,
                       "toAccountId": 1,
                       "amount": 500 
                    }"""
                    )
                    .contentType("application/json")
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }
}