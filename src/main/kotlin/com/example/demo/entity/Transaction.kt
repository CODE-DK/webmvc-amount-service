package com.example.demo.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long,
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "from_account_id")
    val fromAccountId: Long,
    @Column(name = "to_account_id")
    val toAccountId: Long,
    val amount: BigDecimal
)