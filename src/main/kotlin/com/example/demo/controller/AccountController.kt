package com.example.demo.controller

import com.example.demo.entity.Account
import com.example.demo.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/v1"])
class AccountController(private val accountService: AccountService) {

    @GetMapping(path = ["/accounts"])
    fun findAccounts(): ResponseEntity<List<Account>> {
        val accounts = accountService.findAll()
        return if (accounts.isEmpty()) noContent() else ResponseEntity.ok(accounts)
    }

    @GetMapping(path = ["/accounts/{accountId}"])
    fun findAccountById(@PathVariable accountId: Long): ResponseEntity<Account> {
        val account = accountService.findAccountById(accountId)
        return if (account != null) ResponseEntity.ok(account) else noContent()
    }

    @PostMapping(path = ["/accounts"])
    fun newAccount(@RequestBody account: Account): ResponseEntity<Account> {
        return ResponseEntity.ok(accountService.saveAccount(account))
    }

    private fun <T> noContent(): ResponseEntity<T> =
        ResponseEntity.noContent().build()
}