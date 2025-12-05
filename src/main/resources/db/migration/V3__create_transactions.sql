CREATE TABLE transactions (
    transaction_id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL REFERENCES accounts(account_id),
    operation_type_id INTEGER NOT NULL REFERENCES operations_types(operation_type_id),
    amount NUMERIC(15,2) NOT NULL,
    event_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

