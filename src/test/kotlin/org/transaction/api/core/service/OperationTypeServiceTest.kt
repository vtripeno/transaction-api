package org.transaction.api.core.service

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.transaction.api.dataprovider.entity.OperationType
import org.transaction.api.dataprovider.repository.OperationTypeRepository
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class OperationTypeServiceTest {
    private val operationTypeRepository = mockk<OperationTypeRepository>()
    private val service = OperationTypeService(operationTypeRepository)

    @Test
    fun `getOperationType returns type when found`() {
        val opType = OperationType(operationTypeId = 1, description = "Test")
        every { operationTypeRepository.findByOperationTypeId(1) } returns opType

        val result = service.getOperationType(1)
        assertEquals(opType, result)
    }

    @Test
    fun `getOperationType throws OperationTypeNotFoundException when not found`() {
        every { operationTypeRepository.findByOperationTypeId(1) } returns null

        assertFailsWith<OperationTypeNotFoundException> { service.getOperationType(1) }
    }

    @Test
    fun `getAllOperationTypes returns all`() {
        val list = listOf(
            OperationType(1, "Operation A"),
            OperationType(2, "Operation B")
        )
        every { operationTypeRepository.findAll() } returns list

        val result = service.getAllOperationTypes()
        assertEquals(list, result)
    }
}

