package com.example.demo.repo

import com.example.demo.entity.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepo : JpaRepository<Transaction, Long> {

    fun findByFromAccountId(accountId: Long): List<Transaction>

    fun findByToAccountId(accountId: Long): List<Transaction>
}
