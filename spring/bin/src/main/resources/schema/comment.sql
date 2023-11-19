CREATE TABLE comment (
    comment_id INT NOT NULL AUTO_INCREMENT,
    type VARCHAR(30) NOT NULL,
    content_id INT NOT NULL,
    content VARCHAR(10000) NOT NULL,
    user_id INT NOT NULL,
    user_name VARCHAR(30) NOT NULL,
    created_time TIMESTAMP DEFAULT NOW(),
    modified_time TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (comment_id)
);