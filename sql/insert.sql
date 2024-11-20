INSERT INTO book (name, author, press, major, location) VALUES
    ('战争与和平', '托尔斯泰', 'A出版社', '文学', '江安图书馆'),
    ('安娜卡列尼娜', '托尔斯泰', 'B出版社', '文学', '江安图书馆'),
    ('复活', '托尔斯泰', 'A出版社', '文学', '望江图书馆'),
    ('罗密欧与朱丽叶', '莎士比亚', 'B出版社', '文学', '华西图书馆'),
    ('哈姆雷特', '莎士比亚', 'B出版社', '文学', '华西图书馆'),
    ('仲夏夜之梦', '莎士比亚', 'A出版社', '文学', '华西图书馆'),
    ('初级英语', 'Alice', 'C出版社', '语言', '江安图书馆'),
    ('高级英语', 'Alice', 'C出版社', '语言', '江安图书馆'),
    ('初级法语', 'Bob', 'C出版社', '语言', '江安图书馆'),
    ('高级法语', 'Bob', 'C出版社', '语言', '江安图书馆'),
    ('初级物理学', 'Carol', 'D出版社', '理学', '望江图书馆'),
    ('高级物理学', 'Carol', 'D出版社', '理学', '望江图书馆'),
    ('计算机系统', 'Dennis', 'D出版社', '工学', '江安图书馆'),
    ('数据库系统', 'Dennis', 'D出版社', '工学', '江安图书馆'),
    ('计算机网络', 'Dennis', 'D出版社', '工学', '江安图书馆'),
    ('初级哲学', 'Edward', 'C出版社', '哲学', '望江图书馆'),
    ('高级哲学', 'Fred', 'A出版社', '哲学', '望江图书馆'),
    ('素描', 'George', 'E出版社', '艺术', '江安图书馆'),
    ('油画', 'Halen', 'E出版社', '艺术', '江安图书馆'),
    ('版画', 'Ian', 'E出版社', '艺术', '江安图书馆'),
    ('雕塑', 'Jack', 'E出版社', '艺术', '望江图书馆'),
    ('书法', 'Kristina', 'F出版社', '艺术', '江安图书馆'),
    ('中国历史', 'Larry', 'C出版社', '历史', '望江图书馆'),
    ('世界历史', 'Michael', 'A出版社', '历史', '望江图书馆'),
    ('初级医学', 'Nick', 'D出版社', '工学', '华西图书馆'),
    ('高级医学', 'Nick', 'D出版社', '工学', '华西图书馆'),
    ('机械制造', 'Olivia', 'G出版社', '工学', '望江图书馆'),
    ('营养学', 'Plant', 'G出版社', '其他', '华西图书馆'),
    ('养殖学', 'Quin', 'G出版社', '其他', '华西图书馆'),
    ('社交学', 'Richard', 'G出版社', '其他', '华西图书馆'),
    ('法学', 'Swan', 'G出版社', '其他', '望江图书馆'),
    ('电子音乐', 'Travis', 'G出版社', '其他', '江安图书馆');

CREATE USER 'Alice'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON library.* TO 'Alice'@'localhost';

CREATE USER 'Bob'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON library.* TO 'Bob'@'localhost';

CREATE USER 'Carol'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON library.* TO 'Carol'@'localhost';

CREATE USER 'Dennis'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON library.* TO 'Dennis'@'localhost';

CREATE USER 'Edward'@'localhost' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON library.* TO 'Edward'@'localhost';

CREATE USER 'Fred'@'localhost' IDENTIFIED BY '123456';
GRANT SELECT ON library.book TO 'Fred'@'localhost';
GRANT SELECT ON library.borrow_count TO 'Fred'@'localhost';
GRANT SELECT ON library.major_record TO 'Fred'@'localhost';
GRANT SELECT ON library.at_lib TO 'Fred'@'localhost';
GRANT SELECT ON library.local_user TO 'Fred'@'localhost';
GRANT SELECT ON library.local_record TO 'Fred'@'localhost';

INSERT INTO user (name, major, is_admin) VALUES
    ('root', '其他', 1),
    ('Alice', '文学', 1),
    ('Bob', '理学', 1),
    ('Carol', '工学', 1),
    ('Dennis', '哲学', 1),
    ('Edward', '哲学', 1),
    ('Fred', '哲学', 0);

INSERT INTO borrow_record (user_id, book_id, end_time, deadline) VALUES
    (2, 1, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (2, 2, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (2, 3, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (2, 4, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (2, 5, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (2, 6, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (3, 2, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (4, 2, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (5, 2, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (3, 3, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (3, 10, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (5, 20, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (5, 25, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (5, 14, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (5, 15, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (5, 16, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY));



INSERT INTO borrow_record (user_id, book_id, deadline) VALUES
    (2, 1, DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (2, 2, DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (2, 5, DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (3, 3, DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (3, 11, DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (3, 13, DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (4, 14, DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (4, 15, DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (5, 16, DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (6, 25, DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (7, 26, DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (7, 27, DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (7, 28, DATE_ADD(NOW(), INTERVAL 30 DAY)),
    (7, 29, DATE_ADD(NOW(), INTERVAL 30 DAY));












drop user 'Alice'@'localhost';
drop user 'Bob'@'localhost';
drop user 'Carol'@'localhost';
drop user 'Dennis'@'localhost';
drop user 'Edward'@'localhost';
drop user 'Fred'@'localhost';