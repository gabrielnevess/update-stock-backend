CREATE SEQUENCE tb_product_input_id_seq;

CREATE TABLE IF NOT EXISTS TB_PRODUCT_INPUT(
    ID INTEGER PRIMARY KEY NOT NULL DEFAULT NEXTVAL('tb_product_input_id_seq'),
    PRODUCT_ID BIGINT NOT NULL REFERENCES TB_PRODUCT(ID),
    QTD INTEGER NOT NULL,
    OBSERVATION VARCHAR(500) NOT NULL,
    CREATED_AT TIMESTAMP WITHOUT TIME ZONE DEFAULT TO_CHAR(now(), 'yyyy-MM-dd HH24:mm:ss')::timestamp,
    UPDATED_AT TIMESTAMP WITHOUT TIME ZONE DEFAULT TO_CHAR(now(), 'yyyy-MM-dd HH24:mm:ss')::timestamp
);