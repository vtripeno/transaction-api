package org.transaction.api.entrypoint.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AccountResponse(
    @field:JsonProperty("account_id")
    val accountId: String,

    @field:JsonProperty("document_number")
    val documentNumber: String
)

