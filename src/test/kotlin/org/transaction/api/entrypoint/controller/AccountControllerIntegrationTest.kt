package org.transaction.api.entrypoint.controller

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.transaction.api.entrypoint.controller.dto.AccountRequest
import org.junit.jupiter.api.Test
import org.springframework.transaction.annotation.Transactional
import org.springframework.jdbc.core.JdbcTemplate
import org.junit.jupiter.api.BeforeEach
import org.transaction.api.utils.TestDatabaseUtils

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AccountControllerIntegrationTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @BeforeEach
    fun init() {
        TestDatabaseUtils.cleanDatabase(jdbcTemplate)
    }

    @Test
    fun `should get account and return ok status`() {
        val documentNumber = "12345678900"
        val requestJson = jacksonObjectMapper().writeValueAsString(AccountRequest(documentNumber))
        mockMvc.perform(post("/api/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isCreated)
        mockMvc.perform(get("/api/accounts/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.document_number").value(documentNumber))
    }

    @Test
    fun `should return 404 when account not found`() {
        mockMvc.perform(get("/api/accounts/999"))
            .andExpect(status().isNotFound)
    }
}
