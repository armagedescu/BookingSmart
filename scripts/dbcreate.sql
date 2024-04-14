create database if not exists smartbook;
create user if not exists 'smart'@'localhost' identified by 'smt';
grant all privileges on smartbook.* to 'smart'@'localhost';
flush privileges;