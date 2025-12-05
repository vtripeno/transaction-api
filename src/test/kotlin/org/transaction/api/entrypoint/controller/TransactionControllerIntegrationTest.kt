package org.transaction.api.entrypoint.controller

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.transaction.api.entrypoint.controller.dto.TransactionRequest
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.junit.jupiter.api.BeforeEach
import org.transaction.api.utils.TestDatabaseUtils

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TransactionControllerIntegrationTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @BeforeEach
    fun init() {
        TestDatabaseUtils.cleanDatabase(jdbcTemplate)
        TestDatabaseUtils.populateOperationTypes(jdbcTemplate)
    }

    @Test
    fun `should create a transaction and return ok status`() {
        // Create account
        val documentNumber = "12345678900"
        val accountRequestJson = jacksonObjectMapper().writeValueAsString(org.transaction.api.entrypoint.controller.dto.AccountRequest(documentNumber))
        mockMvc.perform(post("/api/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(accountRequestJson))
            .andExpect(status().isCreated)

        // Create transaction
        val transactionRequest = TransactionRequest(
            accountId = 1,
            operationTypeId = 1,
            amount = 100.0
        )
        val transactionRequestJson = jacksonObjectMapper().writeValueAsString(transactionRequest)
        mockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(transactionRequestJson))
            .andExpect(status().isCreated)
    }


}
