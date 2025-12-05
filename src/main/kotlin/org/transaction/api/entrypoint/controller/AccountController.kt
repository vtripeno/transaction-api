package org.transaction.api.entrypoint.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.transaction.api.core.service.AccountService
import org.transaction.api.entrypoint.controller.dto.AccountRequest
import org.transaction.api.entrypoint.controller.dto.AccountResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/accounts")
class AccountController @Autowired constructor(
    private val accountService: AccountService
) {
    @GetMapping("/{accountId}")
    fun getAccount(@PathVariable accountId: String): AccountResponse {
        return try {
            val account = accountService.getAccount(accountId.toLong())
            AccountResponse(accountId = account.accountId.toString(), documentNumber = account.documentNumber)
        } catch (e: org.transaction.api.core.service.AccountNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@RequestBody @Valid accountRequest: AccountRequest): AccountResponse {
        return try {
            val account = accountService.createAccount(accountRequest.documentNumber)
            AccountResponse(accountId = account.accountId.toString(), documentNumber = account.documentNumber)
        } catch (e: org.transaction.api.core.service.AccountAlreadyExistsException) {
            throw ResponseStatusException(HttpStatus.CONFLICT, e.message)
        }
    }
}
