DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50) NOT NULL,
    password  TEXT        NOT NULL,
    algorithm VARCHAR(45) NOT NULL
);

CREATE TABLE authority
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    user INT         NOT NULL,
    CONSTRAINT fk_authority_user FOREIGN KEY (user) REFERENCES user(id)
);

CREATE TABLE product
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(45) NOT NULL,
    price    VARCHAR(45) NOT NULL,
    currency VARCHAR(45) NOT NULL
);