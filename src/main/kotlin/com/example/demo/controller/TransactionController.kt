package com.example.demo.controller

import com.example.demo.entity.Transaction
import com.example.demo.service.TransactionService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping(path = ["/api/v1"])
class TransactionController(private val transactionService: TransactionService) {

    @GetMapping(path = ["/transactions/{accountId}"])
    fun findTransactionsByAccountId(@PathVariable accountId: Long): ResponseEntity<Set<Transaction>> {
        val transactions = transactionService.findTransactionsByAccountId(accountId)
        return if (transactions.isEmpty()) noContent() else ok(transactions)
    }

    @PostMapping(path = ["/transactions"])
    fun makeTransaction(@RequestBody transaction: Transaction): ResponseEntity<Transaction> {
        val newTransaction = transactionService.makeTransaction(transaction)
        return created(URI.create("/api/v1/transactions/${newTransaction.id}")).build()
    }

    private fun <T> noContent(): ResponseEntity<T> = ResponseEntity.noContent().build()
}