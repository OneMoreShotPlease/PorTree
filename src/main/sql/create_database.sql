CREATE USER 'portree'@'%' IDENTIFIED BY 'portree';
CREATE DATABASE portree CHARACTER SET = utf8;
GRANT ALL PRIVILEGES on portree.* to 'portree'@'%';
ALTER USER 'portree'@'localhost' IDENTIFIED WITH mysql_native_password BY 'portree';
USE portree;

CREATE TABLE portree.USER (
	USER_ID INT AUTO_INCREMENT PRIMARY KEY,
	EMAIL VARCHAR(255) NOT NULL,
	PASSWORD VARCHAR(100) NOT NULL,
	NAME VARCHAR(100) NOT NULL,
	GITHUB VARCHAR(255),
	FIELD VARCHAR(255),
	PICTURE VARCHAR(500),
	UNIQUE KEY (EMAIL)
) engine=InnoDB CHARACTER SET = utf8;

CREATE TABLE portree.PORTFOLIO (
	PORTFOLIO_ID INT AUTO_INCREMENT PRIMARY KEY,
	USER_ID INT NOT NULL,
	TITLE VARCHAR(255) NOT NULL,
	PUBLISH_DATE DATE,
	GITHUB VARCHAR(500),
	DEMO VARCHAR(500),
	CATEGORY VARCHAR(500),
	DESCRIPTION VARCHAR(20000),
	FOREIGN KEY (USER_ID) REFERENCES `USER`(`USER_ID`)
) engine=InnoDB CHARACTER SET = utf8;