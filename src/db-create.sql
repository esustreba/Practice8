DROP DATABASE usersDB;
CREATE DATABASE usersDB;

USE usersDB;

DROP TABLE users; 
DROP TABLE groups; 
DROP TABLE users_groups;

CREATE TABLE users (
id INTEGER PRIMARY KEY ,
login VARCHAR(30) NOT NULL UNIQUE); 

CREATE TABLE groups (
id INTEGER PRIMARY KEY ,
name VARCHAR(60) NOT NULL);

CREATE TABLE users_groups (
user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
groups_id INTEGER REFERENCES groups(id) ON DELETE CASCADE,
PRIMARY KEY(user_id, groups_id));

INSERT INTO users VALUES(DEFAULT, "ivanov");

INSERT INTO groups VALUES(DEFAULT, "teamA");