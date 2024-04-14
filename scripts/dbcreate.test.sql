create database if not exists smartbook_test;
create user if not exists 'smart'@'localhost' identified by 'smt';
grant all privileges on smartbook_test.* to 'smart'@'localhost';
flush privileges;
show databases;