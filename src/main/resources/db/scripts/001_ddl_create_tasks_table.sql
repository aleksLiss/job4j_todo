CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   title varchar,
   description TEXT,
   created TIMESTAMP,
   done BOOLEAN
);