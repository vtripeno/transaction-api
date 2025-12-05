CREATE TABLE operations_types (
    operation_type_id INTEGER PRIMARY KEY,
    description VARCHAR(100) NOT NULL
);

INSERT INTO operations_types (operation_type_id, description) VALUES
    (1, 'Normal Purchase'),
    (2, 'Purchase with installments'),
    (3, 'Withdrawal'),
    (4, 'Credit Voucher');
