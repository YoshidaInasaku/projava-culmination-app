DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tasklist;

--ユーザー
CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(8) PRIMARY KEY,
    user_name VARCHAR(128) NOT NULL UNIQUE,
    email VARCHAR(256) NOT NULL,
    password VARCHAR(128) NOT NULL
);

-- タスクリスト
CREATE TABLE IF NOT EXISTS tasklist (
    id VARCHAR(8) PRIMARY KEY,
    task VARCHAR(256),
    deadline VARCHAR(10),
    done BOOLEAN
);

--ToDo: ユーザーテーブルとタスクリストの紐付け