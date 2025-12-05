package org.transaction.api.dataprovider.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    val transactionId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    val account: Account = Account(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_type_id", nullable = false)
    val operationType: OperationType = OperationType(),

    @Column(name = "amount", nullable = false)
    val amount: BigDecimal = BigDecimal.ZERO,

    @Column(name = "event_date", nullable = false)
    val eventDate: LocalDateTime = LocalDateTime.now()
)
