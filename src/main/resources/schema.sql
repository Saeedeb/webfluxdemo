 CREATE TABLE  IF NOT EXISTS  users (
                       id int GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
                       name varchar(100) NOT NULL
);

 ALTER TABLE users
     ADD COLUMN IF NOT EXISTS email varchar(255);

ALTER TABLE users
     ADD COLUMN IF NOT EXISTS password varchar(255);

 ALTER TABLE users
     ADD COLUMN IF NOT EXISTS status varchar(255);

--  CREATE UNIQUE INDEX IF NOT EXISTS users_email_idx
--      ON public.users USING btree
--      (email COLLATE pg_catalog."default" ASC NULLS LAST)
--  WITH (deduplicate_items=True)
--      TABLESPACE pg_default;
