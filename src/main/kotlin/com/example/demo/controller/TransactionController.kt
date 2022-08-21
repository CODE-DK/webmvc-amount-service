package com.example.demo.controller

import com.example.demo.entity.Transaction
import com.example.demo.service.TransactionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/v1"])
class TransactionController(private val transactionService: TransactionService) {

    @GetMapping(path = ["/transactions/{accountId}"])
    fun findTransactionsByAccountId(@PathVariable accountId: Long): Set<Transaction> {
        return transactionService.findTransactionsByAccountId(accountId)
    }

    @PostMapping(path = ["/transactions"])
    fun makeTransaction(@RequestBody transaction: Transaction): Transaction {
        return transactionService.makeTransaction(transaction)
    }
}