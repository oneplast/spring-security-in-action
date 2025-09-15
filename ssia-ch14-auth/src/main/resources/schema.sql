DROP TABLE IF EXISTS oauth2_registered_client;
DROP TABLE IF EXISTS oauth2_authorization;
DROP TABLE IF EXISTS oauth2_authorization_consent;

CREATE TABLE oauth2_registered_client
(
    id                            VARCHAR(100) PRIMARY KEY,
    client_id                     VARCHAR(100) NOT NULL,
    client_id_issued_at           TIMESTAMP,
    client_secret                 VARCHAR(200),
    client_secret_expires_at      TIMESTAMP,
    client_name                   VARCHAR(200) NOT NULL,
    client_authentication_methods TEXT         NOT NULL,
    authorization_grant_types     TEXT         NOT NULL,
    redirect_uris                 TEXT,
    post_logout_redirect_uris     TEXT         NULL,
    scopes                        TEXT,
    client_settings               TEXT         NOT NULL,
    token_settings                TEXT         NOT NULL
);

CREATE TABLE oauth2_authorization
(
    id                            VARCHAR(100) PRIMARY KEY,
    registered_client_id          VARCHAR(100) NOT NULL,
    principal_name                VARCHAR(200) NOT NULL,
    authorization_grant_type      VARCHAR(100) NOT NULL,
    authorized_scopes             TEXT,
    attributes                    TEXT,
    state                         VARCHAR(500),

    authorization_code_value      BLOB,
    authorization_code_issued_at  TIMESTAMP,
    authorization_code_expires_at TIMESTAMP,
    authorization_code_metadata   TEXT,

    access_token_value            BLOB,
    access_token_issued_at        TIMESTAMP,
    access_token_expires_at       TIMESTAMP,
    access_token_metadata         TEXT,
    access_token_type             VARCHAR(100),
    access_token_scopes           TEXT,

    refresh_token_value           BLOB,
    refresh_token_issued_at       TIMESTAMP,
    refresh_token_expires_at      TIMESTAMP,
    refresh_token_metadata        TEXT,

    oidc_id_token_value           BLOB,
    oidc_id_token_issued_at       TIMESTAMP,
    oidc_id_token_expires_at      TIMESTAMP,
    oidc_id_token_metadata        TIMESTAMP,

    user_code_value               BLOB,
    user_code_issued_at           TIMESTAMP,
    user_code_expires_at          TIMESTAMP,
    user_code_metadata            TEXT,

    device_code_value             BLOB,
    device_code_issued_at         TIMESTAMP,
    device_code_expires_at        TIMESTAMP,
    device_code_metadata          TEXT
);

CREATE TABLE oauth2_authorization_consent
(
    registered_client_id VARCHAR(100) NOT NULL,
    principal_name       VARCHAR(200) NOT NULL,
    authorities          TEXT         NOT NULL,
    authorized_scopes    TEXT         NULL,
    PRIMARY KEY (registered_client_id, principal_name)
);