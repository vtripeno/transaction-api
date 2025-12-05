package org.transaction.api.entrypoint.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.transaction.api.entrypoint.controller.dto.TransactionRequest
import org.transaction.api.entrypoint.controller.dto.TransactionResponse
import org.transaction.api.core.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/transactions")
class TransactionController @Autowired constructor(
    private val transactionService: TransactionService,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTransaction(@RequestBody @Valid transactionRequest: TransactionRequest): TransactionResponse {
        try {
            val transaction = transactionService.createTransaction(transactionRequest)
            return TransactionResponse(
                transactionId = transaction.transactionId.toString(),
                eventDate = transaction.eventDate.toString(),
                operationTypeDescription = transaction.operationType.description,
                documentNumber = transaction.account.documentNumber
            )
        } catch (e: org.transaction.api.core.service.TransactionCreationException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
}
