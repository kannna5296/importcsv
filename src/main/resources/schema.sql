CREATE TABLE app_user (
   ID INT auto_increment primary key,
   NAME VARCHAR(255) NOT NULL
);
CREATE TABLE task (
   ID INT auto_increment primary key,
   NAME VARCHAR(255) NOT NULL,
   created_at DATETIME NOT NULL
);
CREATE TABLE task_detail (
   ID INT auto_increment primary key,
   task_id INT NOT NULL,
   user_id INT NOT NULL,
   content VARCHAR(255) NOT NULL,
   deadline DATE NOT NULL,
   created_at DATETIME NOT NULL
);