
CREATE SEQUENCE user_id_seq;

CREATE TABLE IF NOT EXISTS TB_USER (
  ID INTEGER PRIMARY KEY NOT NULL DEFAULT NEXTVAL('user_id_seq'),
  ROLE_ID INTEGER NOT NULL REFERENCES TB_ROLE(ID),
  NAME VARCHAR(255) NOT NULL,
  LOGIN VARCHAR(20) NOT NULL UNIQUE,
  PASSWORD VARCHAR(255) NOT NULL,
  TOKEN TEXT,
  ACTIVE BOOLEAN NOT NULL,
  CREATED_AT TIMESTAMP WITHOUT TIME ZONE DEFAULT TO_CHAR(now(), 'yyyy-MM-dd HH24:mm:ss')::timestamp,
  UPDATED_AT TIMESTAMP WITHOUT TIME ZONE DEFAULT TO_CHAR(now(), 'yyyy-MM-dd HH24:mm:ss')::timestamp  
);

INSERT INTO TB_USER(ID, ROLE_ID, NAME, LOGIN, PASSWORD, ACTIVE)
VALUES (1, 1, 'Administrador', '@dmin_2', '$2a$10$RYtz23zhOKtGZT2Qylo2UeGFi4yVq/Q4qLzIiQLYvfSD2gDnYzLBG', true);
INSERT INTO TB_USER(ID, ROLE_ID, NAME, LOGIN, PASSWORD, ACTIVE)
VALUES (2, 1, 'Rafael', 'r@fael_33', '$2a$10$RYtz23zhOKtGZT2Qylo2UeGFi4yVq/Q4qLzIiQLYvfSD2gDnYzLBG', true);
INSERT INTO TB_USER(ID, ROLE_ID, NAME, LOGIN, PASSWORD, ACTIVE)
VALUES (3, 1, 'Claudionilton', 'cl@udio_154', '$2a$10$RYtz23zhOKtGZT2Qylo2UeGFi4yVq/Q4qLzIiQLYvfSD2gDnYzLBG', true);

SELECT setval('user_id_seq', 3);