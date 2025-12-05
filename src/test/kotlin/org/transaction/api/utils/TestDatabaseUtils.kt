package org.transaction.api.utils

import org.springframework.jdbc.core.JdbcTemplate

object TestDatabaseUtils {
    fun cleanDatabase(jdbcTemplate: JdbcTemplate) {
        jdbcTemplate.execute("DELETE FROM transactions")
        jdbcTemplate.execute("DELETE FROM accounts")
        jdbcTemplate.execute("ALTER TABLE accounts ALTER COLUMN account_id RESTART WITH 1")
    }

    fun populateOperationTypes(jdbcTemplate: JdbcTemplate) {
        jdbcTemplate.execute("INSERT INTO operations_types (operation_type_id, description) VALUES (1, 'Normal Purchase')")
        jdbcTemplate.execute("INSERT INTO operations_types (operation_type_id, description) VALUES (2, 'Purchase with installments')")
        jdbcTemplate.execute("INSERT INTO operations_types (operation_type_id, description) VALUES (3, 'Withdrawal')")
        jdbcTemplate.execute("INSERT INTO operations_types (operation_type_id, description) VALUES (4, 'Credit Voucher')")
    }
}
