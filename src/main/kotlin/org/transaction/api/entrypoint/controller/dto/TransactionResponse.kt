package org.transaction.api.entrypoint.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TransactionResponse(
    @field:JsonProperty("transaction_id")
    val transactionId: String,

    @field:JsonProperty("event_date")
    val eventDate: String,

    @field:JsonProperty("operation_type_description")
    val operationTypeDescription: String,

    @field:JsonProperty("document_number")
    val documentNumber: String
)