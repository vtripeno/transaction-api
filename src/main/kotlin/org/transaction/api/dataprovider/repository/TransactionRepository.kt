package org.transaction.api.dataprovider.repository

import org.transaction.api.dataprovider.entity.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
    fun findByTransactionId(transactionId: Long): Transaction?
    fun findByAccount_AccountId(accountId: Long): List<Transaction>
}
