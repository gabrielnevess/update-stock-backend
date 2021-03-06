CREATE TABLE IF NOT EXISTS TB_USER_ROLE (
  USER_ID INTEGER NOT NULL,
  ROLE_ID INTEGER NOT NULL,
  PRIMARY KEY (USER_ID, ROLE_ID),
  FOREIGN KEY (USER_ID) REFERENCES TB_USER(ID),
  FOREIGN KEY (ROLE_ID) REFERENCES TB_ROLE(ID),
  CREATED_AT TIMESTAMP WITHOUT TIME ZONE DEFAULT TO_CHAR(now(), 'yyyy-MM-dd HH24:mm:ss')::timestamp,
  UPDATED_AT TIMESTAMP WITHOUT TIME ZONE DEFAULT TO_CHAR(now(), 'yyyy-MM-dd HH24:mm:ss')::timestamp  
);