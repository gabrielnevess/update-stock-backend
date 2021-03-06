INSERT INTO TB_ROLE (ID, NAME) VALUES (1, 'ROLE_CADASTRAR_MARCA');
INSERT INTO TB_ROLE (ID, NAME) VALUES (2, 'ROLE_REMOVER_MARCA');
INSERT INTO TB_ROLE (ID, NAME) VALUES (3, 'ROLE_PESQUISAR_MARCA');

INSERT INTO TB_ROLE (ID, NAME) VALUES (4, 'ROLE_CADASTRAR_UNIDADE_MEDIDA');
INSERT INTO TB_ROLE (ID, NAME) VALUES (5, 'ROLE_REMOVER_UNIDADE_MEDIDA');
INSERT INTO TB_ROLE (ID, NAME) VALUES (6, 'ROLE_PESQUISAR_UNIDADE_MEDIDA');

INSERT INTO TB_ROLE (ID, NAME) VALUES (7, 'ROLE_CADASTRAR_PRODUTO');
INSERT INTO TB_ROLE (ID, NAME) VALUES (8, 'ROLE_REMOVER_PRODUTO');
INSERT INTO TB_ROLE (ID, NAME) VALUES (9, 'ROLE_PESQUISAR_PRODUTO');

INSERT INTO TB_ROLE (ID, NAME) VALUES (10, 'ROLE_REMOVER_ESTOQUE');
INSERT INTO TB_ROLE (ID, NAME) VALUES (11, 'ROLE_PESQUISAR_ESTOQUE');

INSERT INTO TB_ROLE (ID, NAME) VALUES (12, 'ROLE_CADASTRAR_ENTRADA_PRODUTO');
INSERT INTO TB_ROLE (ID, NAME) VALUES (13, 'ROLE_REMOVER_ENTRADA_PRODUTO');
INSERT INTO TB_ROLE (ID, NAME) VALUES (14, 'ROLE_PESQUISAR_ENTRADA_PRODUTO');

INSERT INTO TB_ROLE (ID, NAME) VALUES (15, 'ROLE_CADASTRAR_SAIDA_PRODUTO');
INSERT INTO TB_ROLE (ID, NAME) VALUES (16, 'ROLE_REMOVER_SAIDA_PRODUTO');
INSERT INTO TB_ROLE (ID, NAME) VALUES (17, 'ROLE_PESQUISAR_SAIDA_PRODUTO');

INSERT INTO TB_ROLE (ID, NAME) VALUES (18, 'ROLE_CADASTRAR_USUARIO');
INSERT INTO TB_ROLE (ID, NAME) VALUES (19, 'ROLE_REMOVER_USUARIO');
INSERT INTO TB_ROLE (ID, NAME) VALUES (20, 'ROLE_PESQUISAR_USUARIO');

INSERT INTO TB_ROLE (ID, NAME) VALUES (21, 'ROLE_CADASTRAR_PERMISSAO');
INSERT INTO TB_ROLE (ID, NAME) VALUES (22, 'ROLE_REMOVER_PERMISSAO');
INSERT INTO TB_ROLE (ID, NAME) VALUES (23, 'ROLE_PESQUISAR_PERMISSAO');

INSERT INTO TB_ROLE (ID, NAME) VALUES (24, 'ROLE_CADASTRAR_USUARIO_PERMISSAO');
INSERT INTO TB_ROLE (ID, NAME) VALUES (25, 'ROLE_REMOVER_USUARIO_PERMISSAO');
INSERT INTO TB_ROLE (ID, NAME) VALUES (26, 'ROLE_PESQUISAR_USUARIO_PERMISSAO');

SELECT setval('tb_role_id_seq', 26);