DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS qna_board;
DROP TABLE IF EXISTS attraction_plan;
DROP TABLE IF EXISTS plan;
DROP TABLE IF EXISTS member;

CREATE TABLE member (
    user_id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    email VARCHAR(255) NOT NULL,
    type VARCHAR(10),
    registered_time TIMESTAMP DEFAULT NOW(),
    profile_image MEDIUMBLOB,
    PRIMARY KEY (user_id)
);

CREATE TABLE plan (
    plan_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(10000),
    theme VARCHAR(50) NOT NULL,
    departures_id INT NOT NULL,
    departures_name VARCHAR(100) NOT NULL,
    arrivals_id INT NOT NULL,
    arrivals_name VARCHAR(100) NOT NULL,
    created_time TIMESTAMP DEFAULT NOW(),
    modified_time TIMESTAMP DEFAULT NOW(),
    hit BIGINT DEFAULT 0,
    PRIMARY KEY (plan_id),
    CONSTRAINT departures_fk FOREIGN KEY (departures_id)
        REFERENCES attraction_info (content_id),
    CONSTRAINT arrivals_fk FOREIGN KEY (arrivals_id)
        REFERENCES attraction_info (content_id),
    CONSTRAINT member_fk FOREIGN KEY (user_id)
        REFERENCES member (user_id)
);

CREATE TABLE attraction_plan (
    content_id INT NOT NULL,
    plan_id INT NOT NULL,
    plan_order INT NOT NULL,
    CONSTRAINT attraction_fk FOREIGN KEY (content_id)
        REFERENCES attraction_info (content_id),
    CONSTRAINT plan_fk FOREIGN KEY (plan_id)
        REFERENCES plan (plan_id),
    INDEX idx_attraction_id (content_id),
    INDEX idx_plan_id (plan_id)
);

CREATE TABLE qna_board (
    article_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    subject VARCHAR(20) NOT NULL,
    content VARCHAR(10000) NOT NULL,
    hits int DEFAULT 0,
    created_time TIMESTAMP DEFAULT NOW(),
    modified_time TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (article_id),
    CONSTRAINT qna_member_fk FOREIGN KEY (user_id)
        REFERENCES member (user_id)
);

CREATE TABLE comment (
    comment_id INT NOT NULL AUTO_INCREMENT,
    type VARCHAR(30) NOT NULL,
    content_id INT NOT NULL,
    content VARCHAR(10000) NOT NULL,
    user_id INT NOT NULL,
    user_name VARCHAR(30) NOT NULL,
    created_time TIMESTAMP DEFAULT NOW(),
    modified_time TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (comment_id),
    CONSTRAINT comment_member_fk FOREIGN KEY (user_id)
        REFERENCES member (user_id)
);