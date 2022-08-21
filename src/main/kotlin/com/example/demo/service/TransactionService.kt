package com.example.demo.service

import com.example.demo.entity.Transaction
import com.example.demo.repo.TransactionRepo
import org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation.REPEATABLE_READ
import org.springframework.transaction.annotation.Isolation.SERIALIZABLE
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ResponseStatus

@Service
class TransactionService(
    private val accountService: AccountService,
    private val transactionRepo: TransactionRepo
) {

    @Transactional(isolation = REPEATABLE_READ)
    fun findTransactionsByAccountId(accountId: Long): Set<Transaction> {
        val byFromAccountId = transactionRepo.findByFromAccountId(accountId)
        val byToAccountId = transactionRepo.findByToAccountId(accountId)
        return byToAccountId.union(byFromAccountId)
    }

    @Transactional(isolation = SERIALIZABLE)
    fun makeTransaction(transaction: Transaction): Transaction {
        val fromAccount = accountService.findAccountById(transaction.fromAccountId)
            ?: throw TransactionException("No account with id=${transaction.fromAccountId}")
        val toAccount = accountService.findAccountById(transaction.toAccountId)
            ?: throw TransactionException("No account with id=${transaction.toAccountId}")
        if (fromAccount.amount < transaction.amount)
            throw TransactionException("No money on account ${transaction.fromAccountId}")

        val newFromAmount = fromAccount.amount.minus(transaction.amount)
        accountService.updateAccount(fromAccount.copy(amount = newFromAmount))
        val newToAmount = toAccount.amount.plus(transaction.amount)
        accountService.updateAccount(toAccount.copy(amount = newToAmount))

        return transactionRepo.save(transaction)
    }

    @ResponseStatus(NOT_ACCEPTABLE)
    class TransactionException(message: String?) : RuntimeException(message)
}