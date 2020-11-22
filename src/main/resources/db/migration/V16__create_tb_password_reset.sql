CREATE SEQUENCE IF NOT EXISTS tb_password_reset_id_seq;

CREATE TABLE IF NOT EXISTS TB_PASSWORD_RESET (
  ID INTEGER PRIMARY KEY NOT NULL DEFAULT NEXTVAL('tb_password_reset_id_seq'),
  USER_ID INTEGER NOT NULL REFERENCES TB_USER(ID),
  TOKEN VARCHAR(255) NOT NULL,
  USED_TOKEN BOOLEAN NOT NULL DEFAULT FALSE,
  DATE_EXPIRATION TIMESTAMP WITHOUT TIME ZONE,
  CREATED_AT TIMESTAMP WITHOUT TIME ZONE DEFAULT TO_CHAR(now(), 'yyyy-MM-dd HH24:mm:ss')::timestamp,
  UPDATED_AT TIMESTAMP WITHOUT TIME ZONE DEFAULT TO_CHAR(now(), 'yyyy-MM-dd HH24:mm:ss')::timestamp 
);