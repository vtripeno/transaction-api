package org.transaction.api.dataprovider.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "operations_types")
data class OperationType(
    @Id
    @Column(name = "operation_type_id")
    val operationTypeId: Int = 0,

    @Column(name = "description", nullable = false)
    val description: String = ""
)
