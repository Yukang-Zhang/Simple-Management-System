CREATE DATABASE library;

USE library;

CREATE TABLE user(
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    major VARCHAR(255) DEFAULT '未知',
    max_amount INT DEFAULT 5,
    debt DOUBLE DEFAULT 0,
    is_admin INT DEFAULT 0
);

CREATE TABLE book(
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    author VARCHAR(255) DEFAULT '未知',
    press VARCHAR(255) DEFAULT '未知',
    major VARCHAR(255) DEFAULT '未知',
    location VARCHAR(255) DEFAULT '未知'
);

CREATE TABLE borrow_record(
    user_id INT,
    book_id INT,
    start_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    end_time DATETIME,
    deadline DATETIME,
    PRIMARY KEY (user_id, book_id, start_time),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (book_id) REFERENCES book(book_id)
);

CREATE VIEW borrow_count(book_id, count) AS
    SELECT book_id, COUNT(user_id)
    FROM borrow_record
    GROUP BY book_id;

CREATE VIEW major_record(book_id, major) AS
    SELECT book_id, major
    FROM borrow_record, user
	WHERE borrow_record.user_id=user.user_id
	GROUP BY book_id, major;

CREATE VIEW at_lib(book_id) AS
	SELECT book_id
    FROM book
    WHERE book_id NOT IN (
        SELECT book_id
        FROM borrow_record
        WHERE end_time IS NULL
    );

CREATE VIEW local_user AS
    SELECT *
    FROM user
    WHERE name=(
        select user
        from mysql.user
        where concat(user, "@localhost")=user()
    );

CREATE VIEW local_record AS
    SELECT *
    FROM borrow_record
    WHERE user_id=(
        select user_id
        from user
        where name=(
            select user
            from mysql.user
            where concat(user, "@localhost")=user()
        )
    );

