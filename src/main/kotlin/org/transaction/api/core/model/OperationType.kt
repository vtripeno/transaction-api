package org.transaction.api.core.model

enum class OperationType(val id: Int) {
    PURCHASE(1),
    INSTALLMENT_PURCHASE(2),
    WITHDRAWAL(3),
    CREDIT_VOUCHER(4);

    companion object {
        fun fromId(id: Int): OperationType? = entries.find { it.id == id }
    }
}

