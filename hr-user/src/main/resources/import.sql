INSERT INTO tb_user (name, email, password) VALUES ('John Doe', 'john@email.com', '$2a$10$xQCZ/2y79no8rOHE.KzWnuE47P9JXSErCUFiH7Uz0O7sgv9joiIn6');
INSERT INTO tb_user (name, email, password) VALUES ('Jane Doe', 'jane@email.com', '$2a$10$xQCZ/2y79no8rOHE.KzWnuE47P9JXSErCUFiH7Uz0O7sgv9joiIn6');

INSERT INTO tb_role (role_name) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (role_name) VALUES ('ROLE_OPERATOR');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
