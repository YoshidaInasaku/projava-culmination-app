INSERT INTO users (user_id, user_name, email, password)
    VALUES ('1', 'tester', 'example.com', '$2a$10$brwjno3JJ4042zwnadLUcu9RooTuj83jWqhu/cqZuOPBOBTPl/L12');

INSERT INTO tasklist (id, task, deadline, done)
    VALUES ('1', 'MySQLの導入', '2023-03-13', false),
           ('2', 'ログインページの作成', '2023-03-17', false),
           ('3', 'ESの作成', '2023-03-20', false);