package org.transaction.api.core.service

import org.transaction.api.dataprovider.entity.OperationType
import org.transaction.api.dataprovider.repository.OperationTypeRepository
import org.springframework.stereotype.Service

class OperationTypeNotFoundException(message: String) : RuntimeException(message)

@Service
class OperationTypeService(private val operationTypeRepository: OperationTypeRepository) {
    fun getOperationType(operationTypeId: Int): OperationType {
        return operationTypeRepository.findByOperationTypeId(operationTypeId)
            ?: throw OperationTypeNotFoundException("Operation type not found: $operationTypeId")
    }
    fun getAllOperationTypes(): List<OperationType> = operationTypeRepository.findAll()
}
