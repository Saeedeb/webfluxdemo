 CREATE TABLE  IF NOT EXISTS  users (
                       id int GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
                       name varchar(100) NOT NULL
);

 ALTER TABLE users
     ADD COLUMN IF NOT EXISTS email varchar(255);
ALTER TABLE users
     ADD COLUMN IF NOT EXISTS username varchar(50);

ALTER TABLE users
     ADD COLUMN IF NOT EXISTS password varchar(255);

 ALTER TABLE users
     ADD COLUMN IF NOT EXISTS status varchar(255);

 CREATE UNIQUE INDEX IF NOT EXISTS users_email_idx
     ON public.users USING btree
     (email COLLATE pg_catalog."default" ASC NULLS LAST)
 WITH (deduplicate_items=True)
     TABLESPACE pg_default;

 CREATE UNIQUE INDEX IF NOT EXISTS users_username_idx
     ON public.users USING btree
     (username COLLATE pg_catalog."default" ASC NULLS LAST)
 WITH (deduplicate_items=True)
     TABLESPACE pg_default;

 CREATE TABLE  IF NOT EXISTS  roles (
                          id int GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
                          name VARCHAR(50) UNIQUE
 );

 CREATE TABLE  IF NOT EXISTS user_roles (
                             user_id int,
                             role_id int,
                             PRIMARY KEY (user_id, role_id),
                             FOREIGN KEY (user_id) REFERENCES users(id),
                             FOREIGN KEY (role_id) REFERENCES roles(id)
 );