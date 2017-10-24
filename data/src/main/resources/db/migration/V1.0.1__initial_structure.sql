-- CREATE EXTENSION pgcrypto;

CREATE TABLE IF NOT EXISTS oauth_clients
(
  id               UUID PRIMARY KEY            DEFAULT gen_random_uuid(),
  client_id        CHARACTER VARYING(256),
  client_secret    CHARACTER VARYING(1024),
  client_resources CHARACTER VARYING(1024),
  created_time     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
  created_by       UUID,
  updated_time     TIMESTAMP WITHOUT TIME ZONE,
  updated_by       UUID,
  version          BIGINT NOT NULL             DEFAULT 0,

  CONSTRAINT oauth_clients_client_id_uq UNIQUE (client_id)
);

CREATE TABLE IF NOT EXISTS users
(
  id           UUID PRIMARY KEY                             DEFAULT gen_random_uuid(),
  email        CHARACTER VARYING(100) NOT NULL,
  password     CHARACTER VARYING(1024) NOT NULL,
  user_name    CHARACTER VARYING(50) NOT NULL,
  user_surname CHARACTER VARYING(50) NOT NULL,
  user_status  CHARACTER VARYING(50)  NOT NULL,
  image_id     UUID,

  created_time TIMESTAMP WITHOUT TIME ZONE                  DEFAULT NOW(),
  created_by   UUID,
  updated_time TIMESTAMP WITHOUT TIME ZONE,
  updated_by   UUID,
  version      BIGINT                  NOT NULL             DEFAULT 0,

  CONSTRAINT users_email_uq UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS user2roles
(
  id          UUID PRIMARY KEY                             DEFAULT gen_random_uuid(),
  user_id     UUID                  NOT NULL,
  role        CHARACTER VARYING(50) NOT NULL,

  CONSTRAINT user2roles__users_FK FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS images
(
  id           UUID PRIMARY KEY                             DEFAULT gen_random_uuid(),
  content      BYTEA                   NOT NULL,
  mime_type    CHARACTER VARYING(1024) NOT NULL,
  image_type   CHARACTER VARYING(1024) NULL,
  created_time TIMESTAMP WITHOUT TIME ZONE                  DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS image2entity
(
  id              UUID PRIMARY KEY                             DEFAULT gen_random_uuid(),
  image_id        UUID                  NULL,
  entity_id       UUID                  NULL
);

CREATE TABLE IF NOT EXISTS adverts
(
  id           UUID PRIMARY KEY                             DEFAULT gen_random_uuid(),
  advert_type  CHARACTER VARYING(50) NOT NULL,
  advert_status  CHARACTER VARYING(50) NOT NULL,
  headline     CHARACTER VARYING(200) NOT NULL,
  price        BIGINT                  NULL,
  currency_type   CHARACTER VARYING(50) NULL,
  description     CHARACTER VARYING(200) NULL,
  user_id      UUID                     NOT NULL,

  created_time TIMESTAMP WITHOUT TIME ZONE                  DEFAULT NOW(),
  created_by   UUID,
  updated_time TIMESTAMP WITHOUT TIME ZONE,
  updated_by   UUID,
  version      BIGINT                  NOT NULL             DEFAULT 0
);

CREATE TABLE IF NOT EXISTS advert_history
(
  id           UUID PRIMARY KEY                             DEFAULT gen_random_uuid(),
  advert_history_status  CHARACTER VARYING(50) NOT NULL,
  advert_type  CHARACTER VARYING(50) NOT NULL,
  advert_status  CHARACTER VARYING(50) NOT NULL,
  headline     CHARACTER VARYING(200) NOT NULL,
  price        BIGINT                  NULL,
  currency_type   CHARACTER VARYING(50) NULL,
  description     CHARACTER VARYING(200) NULL,
  headline_image_id     UUID                     NULL,
  user_id      UUID                     NOT NULL,

  created_time TIMESTAMP WITHOUT TIME ZONE                  DEFAULT NOW(),
  created_by   UUID,
  updated_time TIMESTAMP WITHOUT TIME ZONE,
  updated_by   UUID
);
