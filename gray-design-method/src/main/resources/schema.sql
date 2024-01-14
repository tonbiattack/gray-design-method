DROP TABLE IF EXISTS `books`;

CREATE TABLE books (
    id CHAR(36) NOT NULL PRIMARY KEY COMMENT 'UUID形式のユニークなID',
    title VARCHAR(255) NOT NULL COMMENT '本のタイトル',
    author VARCHAR(255) NOT NULL COMMENT '著者名',
    genre VARCHAR(100) COMMENT 'ジャンル',
    publication_year YEAR COMMENT '出版年'
) COMMENT = '本の基本情報を管理するテーブル';