package com.example.demo.repo

import com.example.demo.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDateTime

@Repository
interface AccountRepo : JpaRepository<Account, Long> {

    companion object {
        const val UPDATE_ACCOUNT_BY_ID = """
            update accounts 
            set created_at = :created_at, 
                amount = :amount 
            where id = :account_id
        """
    }

    @Modifying
    @Query(UPDATE_ACCOUNT_BY_ID, nativeQuery = true)
    fun update(
        @Param("account_id") id: Long,
        @Param("created_at") createdAt: LocalDateTime,
        @Param("amount") amount: BigDecimal
    )
}
