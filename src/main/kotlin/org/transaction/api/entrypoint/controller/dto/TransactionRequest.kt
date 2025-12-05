package org.transaction.api.entrypoint.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class TransactionRequest(
    @field:JsonProperty("account_id")
    @field:NotNull(message = "The account Id must not be null")
    val accountId: Int?,

    @field:JsonProperty("operation_type_id")
    @field:NotNull(message = "The operation type must not be null")
    val operationTypeId: Int?,

    @field:NotNull(message = "The amount value must not be null")
    @field:Positive(message = "The amount value must be positive")
    val amount: Double?
)