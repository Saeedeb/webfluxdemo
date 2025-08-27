-- drop  table if EXISTS  user_roles;
-- drop  table if EXISTS  roles;
-- drop  table if EXISTS  users;

CREATE TABLE  IF NOT EXISTS  users
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name       varchar(100) NOT NULL,
    username   varchar(50)  not null UNIQUE,
    email      varchar(255) not null UNIQUE,
    password   varchar(255) not null,
    status     varchar(255) not null,
    created_By BIGINT,
    created_At timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_By BIGINT,
    updated_At timestamp DEFAULT CURRENT_TIMESTAMP
);
-- ALTER TABLE users
--     ADD CONSTRAINT unique_username UNIQUE (username);

--  ALTER TABLE users
--      ADD COLUMN IF NOT EXISTS updated_At timestamp


--  CREATE UNIQUE INDEX IF NOT EXISTS users_email_idx
--      ON users USING btree
--      (email COLLATE pg_catalog."default" ASC NULLS LAST)
--  WITH (deduplicate_items=True)
--      TABLESPACE pg_default;

--  CREATE UNIQUE INDEX IF NOT EXISTS users_username_idx
--      ON users USING btree
--      (username COLLATE pg_catalog."default" ASC NULLS LAST)
--  WITH (deduplicate_items=True)
--      TABLESPACE pg_default;

 CREATE TABLE  IF NOT EXISTS  roles
 (
     id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
     name   VARCHAR(50) UNIQUE,
         created_By BIGINT,
     created_At timestamp DEFAULT CURRENT_TIMESTAMP,
     updated_By BIGINT,
     updated_At timestamp DEFAULT CURRENT_TIMESTAMP
 );

 CREATE TABLE  IF NOT EXISTS user_roles
 (
     id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
     user_id BIGINT,
     role_id BIGINT,
     created_By BIGINT,
     created_At timestamp DEFAULT CURRENT_TIMESTAMP,
     updated_By BIGINT,
     updated_At timestamp DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (user_id) REFERENCES users (id),
     FOREIGN KEY (role_id) REFERENCES roles (id),
     UNIQUE(user_id, role_id)
 );

