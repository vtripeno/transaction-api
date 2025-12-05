package org.transaction.api.core.service

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.transaction.api.dataprovider.entity.Account
import org.transaction.api.dataprovider.repository.AccountRepository
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AccountServiceTest {
    private val accountRepository = mockk<AccountRepository>()
    private val service = AccountService(accountRepository)

    @Test
    fun `getAccount returns account when found`() {
        val account = Account(documentNumber = "123")
        every { accountRepository.findByAccountId(1L) } returns account
        val result = service.getAccount(1L)
        assertEquals(account, result)
    }

    @Test
    fun `getAccount throws AccountNotFoundException when not found`() {
        every { accountRepository.findByAccountId(1L) } returns null
        assertFailsWith<AccountNotFoundException> { service.getAccount(1L) }
    }

    @Test
    fun `createAccount saves when not exists`() {
        every { accountRepository.findAll() } returns emptyList()
        every { accountRepository.save(any()) } returns Account(documentNumber = "123")
        val result = service.createAccount("123")
        assertEquals("123", result.documentNumber)
    }

    @Test
    fun `createAccount throws AccountAlreadyExistsException when document exists`() {
        every { accountRepository.findAll() } returns listOf(Account(documentNumber = "123"))
        assertFailsWith<AccountAlreadyExistsException> { service.createAccount("123") }
    }
}
