-- WIPE OLD DATA AND START FRESH
DROP TABLE POSTS;
DROP TABLE USERS;

CREATE TABLE users (
	username CHAR(12) NOT NULL PRIMARY KEY,
	password CHAR(20) NOT NULL,
	joined   DATE DEFAULT CURRENT_DATE
);

CREATE TABLE posts (
	author  CHAR(12) REFERENCES users(username),
	content VARCHAR(200) NOT NULL,
	posted  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id      INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY
);

-- POPULATE THE TABLES WITH SOME INITIAL DATA
INSERT INTO USERS VALUES
	('johndoe',  'P@ssw0rd', '2017-05-09'),
	('jilljack', 'P@ssw0rd', '2017-06-03'),
	('janedoe',  'P@ssw0rd', '2018-01-13');
	
INSERT INTO POSTS (author,content,posted) VALUES
	('johndoe',  'My first Hubbub post!', '2017-05-09 08:23:47.110'),
	('johndoe',  'I''ve invited Jill to join.', '2017-06-02 19:00:05.965'),
	('jilljack', 'Glad to join the team, johndoe.', '2017-06-03 13:44:34.376'),
	('johndoe',  'Let''s recruit more friends, Jill.', '2017-11-29 02:51:18.656'),
	('jilljack', 'I''ll reach out to Jane.', '2017-11-29 07:03:05.123'),
	('janedoe',  'Alright guys, I''ve signed up. Now what?', '2018-01-13 6:30:45.888');
 