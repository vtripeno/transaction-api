package org.transaction.api.entrypoint.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class AccountRequest(
    @field:JsonProperty("document_number")
    @field:NotBlank(message = "The document number must not be blank")
    val documentNumber: String
)
