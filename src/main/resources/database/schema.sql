DROP TABLE IF EXISTS shares;

CREATE TABLE IF NOT EXISTS shares
(
    id           BIGINT AUTO_INCREMENT,
    comments     VARCHAR(300) NOT NULL,
    capital      INTEGER      NOT NULL,
    code_company INTEGER      NOT NULL,
    quantity     INTEGER      NOT NULL,
    amount       DOUBLE       NOT NULL,
    price        DOUBLE       NOT NULL,
    duty         DOUBLE       NOT NULL,
    create_date  DATETIME,
    modify_date  DATETIME,
    version      INTEGER      NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS account
(
    user_id   BIGINT AUTO_INCREMENT,
    user_name VARCHAR(30)  NOT NULL,
    password  VARCHAR(200) NOT NULL,
    PRIMARY KEY (user_id)
);


CREATE TABLE IF NOT EXISTS roles
(
    account_id BIGINT      NOT NULL,
    roles      VARCHAR(10) NOT NULL,
    UNIQUE (account_id, roles)
);

ALTER TABLE roles
    ADD CONSTRAINT roles FOREIGN KEY (account_id) REFERENCES account (user_id);