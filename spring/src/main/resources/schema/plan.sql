DROP TABLE IF EXISTS attraction_plan;
DROP TABLE IF EXISTS plan;

CREATE TABLE plan (
    plan_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
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

insert into plan (user_id, name, description, theme, departures_id, departures_name, arrivals_id, arrivals_name)
values (1, "plan1", "편안한 여행", "Healing", 125266, "국립 청태산자연휴양림", 125406, "비슬산자연휴양림");
        
insert into attraction_plan 
values 
    (125266, 1, 1), 
    (125411, 1, 2), 
    (125412, 1, 3), 
    (125413, 1, 4), 
    (125414, 1, 5), 
    (125415, 1, 6), 
    (125416, 1, 7), 
    (125417, 1, 8), 
    (125418, 1, 9), 
    (125419, 1, 10),
    (125406, 1, 11);

insert into plan (user_id, name, description, theme, departures_id, departures_name, arrivals_id, arrivals_name)
values (2, "plan2", "즐거운 여행", "Trip", 125266, "국립 청태산자연휴양림", 125406, "비슬산자연휴양림");
        
insert into attraction_plan 
values 
    (125266, 1, 1), 
    (125411, 1, 2), 
    (125412, 1, 3), 
    (125413, 1, 4), 
    (125414, 1, 5), 
    (125415, 1, 6), 
    (125416, 1, 7), 
    (125417, 1, 8), 
    (125418, 1, 9), 
    (125419, 1, 10),
    (125406, 1, 11);