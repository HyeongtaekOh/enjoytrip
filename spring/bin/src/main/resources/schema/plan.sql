DROP TABLE IF EXISTS attraction_plan;
DROP TABLE IF EXISTS plan;

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