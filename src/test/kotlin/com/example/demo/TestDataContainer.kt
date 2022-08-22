package com.example.demo

import com.example.demo.entity.Account
import com.example.demo.entity.Transaction
import org.mockito.Mockito
import java.math.BigDecimal
import java.math.BigDecimal.valueOf

fun <T> any(): T = Mockito.any()

val TEST_ACCOUNTS = listOf(
    Account(id = 0L, amount = BigDecimal.ZERO),
    Account(id = 1L, amount = BigDecimal.ONE),
    Account(id = 2L, amount = BigDecimal.ONE)
)

val TEST_TRANSACTIONS = setOf(
    Transaction(id = 0L, fromAccountId = 0L, toAccountId = 1L, amount = valueOf(100L)),
    Transaction(id = 1L, fromAccountId = 1L, toAccountId = 0L, amount = valueOf(50L)),
    Transaction(id = 2L, fromAccountId = 2L, toAccountId = 1L, amount = valueOf(20L)),
)