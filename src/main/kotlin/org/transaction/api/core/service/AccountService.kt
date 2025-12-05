package org.transaction.api.core.service

import org.transaction.api.dataprovider.entity.Account
import org.transaction.api.dataprovider.repository.AccountRepository
import org.springframework.stereotype.Service

class AccountNotFoundException(message: String) : RuntimeException(message)
class AccountAlreadyExistsException(message: String) : RuntimeException(message)

@Service
class AccountService(private val accountRepository: AccountRepository) {
    fun getAccount(accountId: Long): Account {
        return accountRepository.findByAccountId(accountId)
            ?: throw AccountNotFoundException("Account not found: $accountId")
    }

    fun createAccount(documentNumber: String): Account {
        val existing = accountRepository.findAll().find { it.documentNumber == documentNumber }
        if (existing != null) {
            throw AccountAlreadyExistsException("Account with document number $documentNumber already exists")
        }
        return accountRepository.save(Account(documentNumber = documentNumber))
    }
}
