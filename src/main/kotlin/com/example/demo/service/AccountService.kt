package com.example.demo.service

import com.example.demo.entity.Account
import com.example.demo.repo.AccountRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation.*
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(private val accountRepo: AccountRepo) {

    @Transactional(isolation = REPEATABLE_READ)
    fun findAll(): List<Account> = accountRepo.findAll().sortedBy { it.id }

    @Transactional(isolation = READ_COMMITTED)
    fun findAccountById(accountId: Long): Account? {
        return accountRepo.findById(accountId).orElse(null)
    }

    @Transactional(isolation = SERIALIZABLE)
    fun saveAccount(account: Account): Account = accountRepo.save(account)

    @Transactional(isolation = SERIALIZABLE)
    fun updateAccount(account: Account) {
        accountRepo.update(
            account.id,
            account.createdAt,
            account.amount
        )
    }
}
