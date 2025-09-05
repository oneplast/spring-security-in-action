INSERT INTO user (username, password, algorithm)
VALUES ('john', '$2a$10$MG8ent8ysFjw8W/ZXgr06uHonMl7lGMkQIuPxbHEFJDS7u20A4O3e', 'BCRYPT');

INSERT INTO authority (name, user) VALUES ('READ', 1), ('WRITE', 1);

INSERT INTO product (name, price, currency) VALUES ('Chocolate', 10, 'USD');