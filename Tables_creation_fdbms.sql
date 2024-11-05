-- Create a database 
create database football_management;

-- use the database
use football_management;

-- Table: club
CREATE TABLE club (
    Club_ID INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Market_value DECIMAL(15, 2) DEFAULT NULL,
    League_ID INT NOT NULL,
    Available_Balance DECIMAL(15, 2) DEFAULT NULL,
    Coach VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (Club_ID)
);

-- Table: league
CREATE TABLE league (
    league_id INT NOT NULL AUTO_INCREMENT,
    league_name VARCHAR(255) NOT NULL,
    winning_price DECIMAL(15, 2) NOT NULL,
    year YEAR NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    winning_club_id INT DEFAULT NULL,
    description TEXT DEFAULT NULL,
    PRIMARY KEY (league_id)
);

-- Table: login
CREATE TABLE login (
    login_id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'user', 'guest') NOT NULL,
    club_id INT DEFAULT NULL,
    PRIMARY KEY (login_id)
);

-- Table: match_appearance
CREATE TABLE match_appearance (
    App_ID INT NOT NULL AUTO_INCREMENT,
    Date DATE DEFAULT NULL,
    Match_ID INT NOT NULL,
    Player_ID INT NOT NULL,
    Position VARCHAR(255) DEFAULT NULL,
    Type VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (App_ID)
);

-- Table: match_events
CREATE TABLE match_events (
    Event_ID INT NOT NULL AUTO_INCREMENT,
    Match_ID INT NOT NULL,
    Player_ID INT NOT NULL,
    Min_occured INT DEFAULT NULL,
    Description TEXT DEFAULT NULL,
    PRIMARY KEY (Event_ID)
);

-- Table: matches
CREATE TABLE matches (
    Match_ID INT NOT NULL AUTO_INCREMENT,
    Home_club_ID INT NOT NULL,
    Away_club_ID INT NOT NULL,
    Goals_scored INT DEFAULT NULL,
    Goals_conceded INT DEFAULT NULL,
    Date DATE DEFAULT NULL,
    league_id INT DEFAULT NULL,
    time VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (Match_ID)
);

-- Table: player
CREATE TABLE player (
    player_ID INT NOT NULL AUTO_INCREMENT,
    Player_name VARCHAR(255) NOT NULL,
    Position VARCHAR(255) DEFAULT NULL,
    Player_Valuation DECIMAL(15, 2) DEFAULT NULL,
    Current_Club_ID INT DEFAULT NULL,
    PRIMARY KEY (player_ID)
);

-- Table: player_conditions
CREATE TABLE player_conditions (
    Condition_ID INT NOT NULL AUTO_INCREMENT,
    Player_ID INT NOT NULL,
    Injury VARCHAR(255) DEFAULT NULL,
    Recent_form VARCHAR(255) DEFAULT NULL,
    Description TEXT DEFAULT NULL,
    Training_details TEXT DEFAULT NULL,
    PRIMARY KEY (Condition_ID)
);

-- Table: points
CREATE TABLE points (
    points_id INT NOT NULL,
    league_id INT DEFAULT NULL,
    club_id INT DEFAULT NULL,
    position INT NOT NULL,
    wins INT DEFAULT 0,
    losses INT DEFAULT 0,
    draws INT DEFAULT 0,
    goals_scored INT DEFAULT 0,
    goals_conceded INT DEFAULT 0,
    goal_difference INT AS (goals_scored - goals_conceded) STORED,
    points INT AS (wins * 3 + draws) STORED,
    PRIMARY KEY (points_id)
);

-- Table: transfers
CREATE TABLE transfers (
    Transfer_ID INT NOT NULL AUTO_INCREMENT,
    Player_ID INT NOT NULL,
    From_club_id INT NOT NULL,
    To_club_id INT NOT NULL,
    Transfer_fees DECIMAL(15, 2) DEFAULT NULL,
    Clauses TEXT DEFAULT NULL,
    Date DATE DEFAULT NULL,
    PRIMARY KEY (Transfer_ID)
);

-- Foreign Key Constraints
ALTER TABLE club
    ADD CONSTRAINT fk_club_league FOREIGN KEY (League_ID) REFERENCES league(league_id);

ALTER TABLE league
    ADD CONSTRAINT fk_league_winning_club FOREIGN KEY (winning_club_id) REFERENCES club(Club_ID);

ALTER TABLE login
    ADD CONSTRAINT fk_login_club FOREIGN KEY (club_id) REFERENCES club(Club_ID);

ALTER TABLE match_appearance
    ADD CONSTRAINT fk_matchappearance_match FOREIGN KEY (Match_ID) REFERENCES matches(Match_ID),
    ADD CONSTRAINT fk_matchappearance_player FOREIGN KEY (Player_ID) REFERENCES player(player_ID);

ALTER TABLE match_events
    ADD CONSTRAINT fk_matchevents_match FOREIGN KEY (Match_ID) REFERENCES matches(Match_ID),
    ADD CONSTRAINT fk_matchevents_player FOREIGN KEY (Player_ID) REFERENCES player(player_ID);

ALTER TABLE matches
    ADD CONSTRAINT fk_matches_homeclub FOREIGN KEY (Home_club_ID) REFERENCES club(Club_ID),
    ADD CONSTRAINT fk_matches_awayclub FOREIGN KEY (Away_club_ID) REFERENCES club(Club_ID),
    ADD CONSTRAINT fk_matches_league FOREIGN KEY (league_id) REFERENCES league(league_id);

ALTER TABLE player
    ADD CONSTRAINT fk_player_club FOREIGN KEY (Current_Club_ID) REFERENCES club(Club_ID);

ALTER TABLE player_conditions
    ADD CONSTRAINT fk_playerconditions_player FOREIGN KEY (Player_ID) REFERENCES player(player_ID);

ALTER TABLE points
    ADD CONSTRAINT fk_points_league FOREIGN KEY (league_id) REFERENCES league(league_id),
    ADD CONSTRAINT fk_points_club FOREIGN KEY (club_id) REFERENCES club(Club_ID);

ALTER TABLE transfers
    ADD CONSTRAINT fk_transfers_player FOREIGN KEY (Player_ID) REFERENCES player(player_ID),
    ADD CONSTRAINT fk_transfers_fromclub FOREIGN KEY (From_club_id) REFERENCES club(Club_ID),
    ADD CONSTRAINT fk_transfers_toclub FOREIGN KEY (To_club_id) REFERENCES club(Club_ID);
