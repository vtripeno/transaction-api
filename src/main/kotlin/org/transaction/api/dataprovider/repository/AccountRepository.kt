package org.transaction.api.dataprovider.repository

import org.transaction.api.dataprovider.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByAccountId(accountId: Long): Account?
}
