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
)