package org.transaction.api.core.service

import org.transaction.api.dataprovider.entity.Transaction
import org.transaction.api.dataprovider.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import org.transaction.api.entrypoint.controller.dto.TransactionRequest
import org.transaction.api.core.model.OperationType

class TransactionCreationException(message: String) : RuntimeException(message)

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val accountService: AccountService,
    private val operationTypeService: OperationTypeService
) {
    fun getTransaction(transactionId: Long): Transaction? =
        transactionRepository.findByTransactionId(transactionId)

    fun getTransactionsByAccount(accountId: Long): List<Transaction> =
        transactionRepository.findByAccount_AccountId(accountId)

    fun createTransaction(request: TransactionRequest): Transaction {
        val accountId = request.accountId ?: throw TransactionCreationException("Account id is required")
        val operationTypeId =
            request.operationTypeId ?: throw TransactionCreationException("Operation type id is required")
        val amount = request.amount ?: throw TransactionCreationException("Amount is required")
        val account = try {
            accountService.getAccount(accountId.toLong())
        } catch (_: AccountNotFoundException) {
            throw TransactionCreationException("Account not found")
        }
        val operationType = try {
            operationTypeService.getOperationType(operationTypeId)
        } catch (_: OperationTypeNotFoundException) {
            throw TransactionCreationException("Operation type not found")
        }

        val finalAmount = calculateFinalAmount(operationType.operationTypeId, amount)

        val transaction = Transaction(
            account = account,
            operationType = operationType,
            amount = BigDecimal.valueOf(finalAmount),
            eventDate = LocalDateTime.now()
        )
        return transactionRepository.save(transaction)
    }

    private fun calculateFinalAmount(operationTypeId: Int, amount: Double): Double {
        val operationTypeEnum = OperationType.fromId(operationTypeId)
        return when (operationTypeEnum) {
            OperationType.PURCHASE,
            OperationType.INSTALLMENT_PURCHASE,
            OperationType.WITHDRAWAL -> if (amount > 0) -amount else amount
            OperationType.CREDIT_VOUCHER -> if (amount < 0) -amount else amount
            null -> amount
        }
    }
}
