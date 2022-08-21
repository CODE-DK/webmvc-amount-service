package com.example.demo.entity

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "accounts")
data class Account(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long,
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val amount: BigDecimal = BigDecimal.ZERO
)
