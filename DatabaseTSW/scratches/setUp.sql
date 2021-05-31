DROP DATABASE IF EXISTS scuolacalciodb;
CREATE DATABASE scuolacalciodb;
USE scuolacalciodb;
DROP USER IF EXISTS 'userscuolacalcio'@'%';
CREATE USER 'userscuolacalcio'@'%' IDENTIFIED BY 'psw';
GRANT ALL PRIVILEGES ON *.* TO 'userscuolacalcio'@'%' WITH GRANT OPTION;