package org.transaction.api.core.service

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.transaction.api.dataprovider.entity.Transaction
import org.transaction.api.dataprovider.repository.TransactionRepository
import org.transaction.api.entrypoint.controller.dto.TransactionRequest
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TransactionServiceTest {
    private val transactionRepository = mockk<TransactionRepository>()
    private val accountService = mockk<AccountService>()
    private val operationTypeService = mockk<OperationTypeService>()
    private val service = TransactionService(transactionRepository, accountService, operationTypeService)

    @Test
    fun `createTransaction saves transaction when valid`() {
        val request = TransactionRequest(1, 1, 100.0)
        val account = mockk<org.transaction.api.dataprovider.entity.Account>()
        val operationType = mockk<org.transaction.api.dataprovider.entity.OperationType>()
        val transaction = mockk<Transaction>()

        every { accountService.getAccount(1) } returns account
        every { operationTypeService.getOperationType(1) } returns operationType
        every { operationType.operationTypeId } returns 1
        every { transactionRepository.save(any()) } returns transaction

        val result = service.createTransaction(request)
        assertEquals(transaction, result)
    }

    @Test
    fun `createTransaction saves negative amount for PURCHASE even if input is positive`() {
        val request = TransactionRequest(1, 1, 100.0)
        val account = mockk<org.transaction.api.dataprovider.entity.Account>()
        val operationType = mockk<org.transaction.api.dataprovider.entity.OperationType>()
        var savedAmount: Double? = null

        every { accountService.getAccount(1) } returns account
        every { operationTypeService.getOperationType(1) } returns operationType
        every { operationType.operationTypeId } returns 1
        every { transactionRepository.save(match { savedAmount = it.amount.toDouble(); true }) } returns mockk()

        service.createTransaction(request)
        assertEquals(-100.0, savedAmount)
    }

    @Test
    fun `createTransaction saves negative amount for PURCHASE even if input is negative`() {
        val request = TransactionRequest(1, 1, -50.0)
        val account = mockk<org.transaction.api.dataprovider.entity.Account>()
        val operationType = mockk<org.transaction.api.dataprovider.entity.OperationType>()
        var savedAmount: Double? = null

        every { accountService.getAccount(1) } returns account
        every { operationTypeService.getOperationType(1) } returns operationType
        every { operationType.operationTypeId } returns 1
        every { transactionRepository.save(match { savedAmount = it.amount.toDouble(); true }) } returns mockk()

        service.createTransaction(request)
        assertEquals(-50.0, savedAmount)
    }

    @Test
    fun `createTransaction saves positive amount for CREDIT_VOUCHER even if input is positive`() {
        val request = TransactionRequest(1, 4, 200.0)
        val account = mockk<org.transaction.api.dataprovider.entity.Account>()
        val operationType = mockk<org.transaction.api.dataprovider.entity.OperationType>()
        var savedAmount: Double? = null

        every { accountService.getAccount(1) } returns account
        every { operationTypeService.getOperationType(4) } returns operationType
        every { operationType.operationTypeId } returns 4
        every { transactionRepository.save(match { savedAmount = it.amount.toDouble(); true }) } returns mockk()

        service.createTransaction(request)
        assertEquals(200.0, savedAmount)
    }

    @Test
    fun `createTransaction saves positive amount for CREDIT_VOUCHER even if input is negative`() {
        val request = TransactionRequest(1, 4, -300.0)
        val account = mockk<org.transaction.api.dataprovider.entity.Account>()
        val operationType = mockk<org.transaction.api.dataprovider.entity.OperationType>()
        var savedAmount: Double? = null

        every { accountService.getAccount(1) } returns account
        every { operationTypeService.getOperationType(4) } returns operationType
        every { operationType.operationTypeId } returns 4
        every { transactionRepository.save(match { savedAmount = it.amount.toDouble(); true }) } returns mockk()

        service.createTransaction(request)
        assertEquals(300.0, savedAmount)
    }

    @Test
    fun `createTransaction saves negative amount for WITHDRAWAL even if input is positive`() {
        val request = TransactionRequest(1, 3, 80.0)
        val account = mockk<org.transaction.api.dataprovider.entity.Account>()
        val operationType = mockk<org.transaction.api.dataprovider.entity.OperationType>()
        var savedAmount: Double? = null

        every { accountService.getAccount(1) } returns account
        every { operationTypeService.getOperationType(3) } returns operationType
        every { operationType.operationTypeId } returns 3
        every { transactionRepository.save(match { savedAmount = it.amount.toDouble(); true }) } returns mockk()

        service.createTransaction(request)
        assertEquals(-80.0, savedAmount)
    }

    @Test
    fun `createTransaction saves negative amount for INSTALLMENT_PURCHASE even if input is positive`() {
        val request = TransactionRequest(1, 2, 60.0)
        val account = mockk<org.transaction.api.dataprovider.entity.Account>()
        val operationType = mockk<org.transaction.api.dataprovider.entity.OperationType>()
        var savedAmount: Double? = null

        every { accountService.getAccount(1) } returns account
        every { operationTypeService.getOperationType(2) } returns operationType
        every { operationType.operationTypeId } returns 2
        every { transactionRepository.save(match { savedAmount = it.amount.toDouble(); true }) } returns mockk()

        service.createTransaction(request)
        assertEquals(-60.0, savedAmount)
    }

    @Test
    fun `createTransaction throws TransactionCreationException when accountId is null`() {
        val request = TransactionRequest(null, 1, 100.0)
        assertFailsWith<TransactionCreationException> { service.createTransaction(request) }
    }

    @Test
    fun `createTransaction throws TransactionCreationException when operationTypeId is null`() {
        val request = TransactionRequest(1, null, 100.0)
        assertFailsWith<TransactionCreationException> { service.createTransaction(request) }
    }

    @Test
    fun `createTransaction throws TransactionCreationException when amount is null`() {
        val request = TransactionRequest(1, 1, null)
        assertFailsWith<TransactionCreationException> { service.createTransaction(request) }
    }

    @Test
    fun `createTransaction throws TransactionCreationException when account not found`() {
        val request = TransactionRequest(1, 1, 100.0)
        every { accountService.getAccount(1) } throws AccountNotFoundException("Account not found")
        assertFailsWith<TransactionCreationException> { service.createTransaction(request) }
    }

    @Test
    fun `createTransaction throws TransactionCreationException when operation type not found`() {
        val request = TransactionRequest(1, 1, 100.0)
        val account = mockk<org.transaction.api.dataprovider.entity.Account>()

        every { accountService.getAccount(1) } returns account
        every { operationTypeService.getOperationType(1) } throws OperationTypeNotFoundException("Operation type not found")

        assertFailsWith<TransactionCreationException> { service.createTransaction(request) }
    }

    @Test
    fun `getTransaction returns transaction when found`() {
        val transaction = mockk<Transaction>()

        every { transactionRepository.findByTransactionId(1L) } returns transaction

        val result = service.getTransaction(1L)
        assertEquals(transaction, result)
    }

    @Test
    fun `getTransaction returns null when not found`() {
        every { transactionRepository.findByTransactionId(1L) } returns null

        val result = service.getTransaction(1L)
        assertEquals(null, result)
    }

    @Test
    fun `getTransactionsByAccount returns transactions for account`() {
        val transactions = listOf(mockk<Transaction>(), mockk<Transaction>())
        every { transactionRepository.findByAccount_AccountId(1L) } returns transactions

        val result = service.getTransactionsByAccount(1L)
        assertEquals(transactions, result)
    }

    @Test
    fun `getTransactionsByAccount returns empty list when no transactions found`() {
        every { transactionRepository.findByAccount_AccountId(1L) } returns emptyList()

        val result = service.getTransactionsByAccount(1L)
        assertEquals(emptyList(), result)
    }
}
