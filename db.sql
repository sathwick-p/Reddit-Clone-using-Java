create table comments(postid varchar(50), memid varchar(50), text varchar(50), voteCount Integer);
-- create table answers(postid varchar(50), memid varchar(50), answer_text varchar(50), voteCount Integer);
create table vote(voteCount Integer, postid varchar(50), memid varchar(50));
create table post(postid varchar(50), title varchar(50), caption varchar(200), voteCount Integer, memid varchar(50));
create table users(name varchar(50), password varchar(50), email varchar(50), phone char(10), memid varchar(50), acc_blocked boolean, reputation integer, isModerator boolean, isAdmin boolean);
create table currentmemid(memid varchar(50));