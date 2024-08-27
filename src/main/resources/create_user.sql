DROP USER IF EXISTS 'coursecraft_usr';
CREATE USER IF NOT EXISTS 'coursecraft_usr'@'%' IDENTIFIED BY 'coursecraft_pwd';
GRANT ALL PRIVILEGES ON *.* TO 'coursecraft_usr'@'%' WITH GRANT OPTION;
CREATE DATABASE IF NOT EXISTS coursecraft_db;
