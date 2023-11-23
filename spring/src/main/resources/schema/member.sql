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