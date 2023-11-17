CREATE TABLE qna_board (
    article_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    subject VARCHAR(20) NOT NULL,
    content VARCHAR(10000) NOT NULL,
    hits int DEFAULT 0,
    created_time TIMESTAMP DEFAULT NOW(),
    modified_time TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (article_id)
);