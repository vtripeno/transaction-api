package org.transaction.api.dataprovider.repository

import org.transaction.api.dataprovider.entity.OperationType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OperationTypeRepository : JpaRepository<OperationType, Int> {
    fun findByOperationTypeId(operationTypeId: Int): OperationType?
}
