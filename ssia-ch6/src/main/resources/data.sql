INSERT INTO user (username, password, algorithm)
VALUES ('john', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4tlKsnmG', 'BCRYPT');

INSERT INTO authority (name, user) VALUES ('READ', 1), ('WRITE', 1);

INSERT INTO product (name, price, currency) VALUES ('Chocolate', 10, 'USD');