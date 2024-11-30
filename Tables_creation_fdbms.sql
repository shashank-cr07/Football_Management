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
    player_club INT DEFAULT NULL,
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

-- Table: Name conversions (Used only for backend webscrapping has no other role)
CREATE TABLE Name_Conversion (
    Standings_name VARCHAR(50), 
    club_matches_name VARCHAR(50),
    FootyStats_naming VARCHAR(50), 
    teamLink INT
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

-- Inseting values in the name_conversion table(Used for webscrapping)
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('Rayo Vallecano de Madrid','Rayo Vallecano','Rayo Vallecano',87);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('UD Las Palmas','Las Palmas','UD Las Palmas',275);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('Real Madrid CF','Real Madrid','Real Madrid CF',86);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('CA Osasuna','Osasuna','CA Osasuna',79);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('Villarreal CF','Villarreal','Villarreal CF',94);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('Deportivo Alavés','Alavés','Deportivo Alavés',263);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('CD Leganés','Leganés','CD Leganés',745);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('Sevilla FC','Sevilla FC','Sevilla FC',559);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('Real Betis Balompié','Real Betis','Real Betis Balompié',90);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('RC Celta de Vigo','Celta','Real Club Celta de Vigo',558);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('RCD Mallorca','Mallorca','Real Club Deportivo Mallorca',89);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('Club Atlético de Madrid','Atleti','Club Atlético de Madrid',78);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('Real Valladolid CF','Valladolid','Real Valladolid',250);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('Athletic Club','Athletic','Athletic Club Bilbao',77);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('Getafe CF','Getafe','Getafe Club de Fútbol',82);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('Girona FC','Girona','Girona FC',298);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('Real Sociedad de Fútbol','Real Sociedad','Real Sociedad de Fútbol',92);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('FC Barcelona','Barça','FC Barcelona',81);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('RCD Espanyol de Barcelona','Espanyol','Reial Club Deportiu Espanyol',80);
INSERT IGNORE INTO Name_conversion (club_matches_name,Standings_name,FootyStats_naming,teamLink) VALUES ('Valencia CF','Valencia','Valencia CF',95);

--Triggers 

-- Trigger that will set the match_appearance.player_club as the player.player_club(Done to ensure transfers doesnt affect match appearance)    
DELIMITER $$
CREATE TRIGGER `set_player_club_before_insert` 
BEFORE INSERT ON `match_appearance` 
FOR EACH ROW 
BEGIN
    DECLARE club_name VARCHAR(45);
    -- Get the current club name of the player
    SELECT c.Name INTO club_name
    FROM `football_management`.`player` p
    JOIN `football_management`.`club` c 
        ON p.Current_Club_ID = c.Club_ID
    WHERE p.player_ID = NEW.Player_ID;
    -- Set the NEW player_club value
    SET NEW.player_club = club_name;
END $$
DELIMITER ;


-- To change the player's current club to his new club once a transfer has been completed
 DELIMITER $$
 CREATE TRIGGER `set_from_club_id_before_insert` 
     BEFORE INSERT ON `transfers` 
     FOR EACH ROW BEGIN
        DECLARE playerClubId INT;
        -- Fetch the player's current club_id
        SELECT current_club_id
        INTO playerClubId
        FROM Player
        WHERE player_id = NEW.player_id;
        -- Set the from_club_id based on the player's current_club_id
        IF playerClubId IS NOT NULL THEN
            SET NEW.from_club_id = playerClubId;
        ELSE
            SET NEW.from_club_id = NULL;
        END IF;
END $$
DELIMITER ; 

-- When 2 clubs are involved in a transfer the recieving club must loose money and the selling club must get the amount 
-- Also checks if the club is unable to afford the player and raises an error
 DELIMITER $$
 CREATE TRIGGER `manage_transfer_balances` 
     BEFORE INSERT ON `transfers` 
     FOR EACH ROW BEGIN
        DECLARE old_balance_from_club DECIMAL(15,2);
        DECLARE old_balance_to_club DECIMAL(15,2);
        -- Fetch the available balance of the from_club (previous club)
        SELECT available_balance
        INTO old_balance_from_club
        FROM Club
        WHERE club_id = NEW.from_club_id;
        -- Fetch the available balance of the to_club (new club)
        SELECT available_balance
        INTO old_balance_to_club
        FROM Club
        WHERE club_id = NEW.to_club_id;
        -- Check if the new club has sufficient balance for the transfer
        IF old_balance_to_club < NEW.transfer_fees THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Insufficient balance in the destination club!';
        ELSE
            -- Update the balances of both clubs
            UPDATE Club
            SET available_balance = old_balance_from_club + NEW.transfer_fees
            WHERE club_id = NEW.from_club_id;
            UPDATE Club
            SET available_balance = old_balance_to_club - NEW.transfer_fees
            WHERE club_id = NEW.to_club_id;
        END IF;
END $$
DELIMITER ; 

--Similar to the other trigger works after insert 
DELIMITER $$
CREATE TRIGGER `update_player_club_after_transfer` AFTER INSERT ON `transfers` FOR EACH ROW BEGIN
    UPDATE Player
    SET Current_Club_ID = NEW.To_club_id
    WHERE player_ID = NEW.Player_ID;
END $$
DELIMITER ;


--Procedures
-- Getting all the match events of one particular match
DELIMITER $$
 CREATE PROCEDURE `GetAllMatchEvents`(IN input_match_id INT)
BEGIN
    -- Selecting at least 3 events for the given match_id
    SELECT
        me.event_id AS event_id,
        p.player_name AS player_name,
        pc.name AS player_club,  -- Changed to get club name
        hc.name AS home_club,
        ac.name AS away_club,
        me.min_occured AS min_occured,
        me.description AS description
    FROM
        match_events AS me
    JOIN
        player AS p ON me.player_id = p.player_id
    JOIN
        matches AS m ON me.match_id = m.match_id
    JOIN
        club AS hc ON m.home_club_id = hc.club_id
    JOIN
        club AS ac ON m.away_club_id = ac.club_id
    JOIN
        club AS pc ON p.Current_Club_ID = pc.club_id  -- Joining to get the player's club name
    WHERE
        me.match_id = input_match_id;
    -- LIMIT 5; uncomment if needed 
END $$
DELIMITER ;
    
--To get the upcoming matches of a club with its id as parameter
DELIMITER $$
 CREATE PROCEDURE `getFutureClubMatches`(IN clubid INT)
BEGIN
    SELECT *
    FROM matches
    WHERE (Home_Club_ID = clubid OR Away_Club_ID = clubid)
      AND goals_scored IS NULL;
END $$
DELIMITER ;

--Get the recent played matches by 1 club 
DELIMITER $$
 CREATE PROCEDURE `GetLatestMatchesByClub`(IN club_id INT)
BEGIN
    SELECT *
    FROM Matches
    WHERE (Home_club_ID = club_id OR Away_club_ID = club_id)
      AND goals_scored IS NOT NULL
    ORDER BY Date DESC
    LIMIT 5;
END $$ 
DELIMITER ;

--To get the points table of a particular league in ascending order
DELIMITER $$
 CREATE PROCEDURE `GetPointsByLeague`(IN league_id INT)
BEGIN
    -- Selecting points data for the given league_id
    SELECT
       *
    FROM
        points p
    WHERE
        p.league_id = league_id
    ORDER BY
        p.position ASC;  -- Sorting 
END $$
DELIMITER ;


--To get in recent transfers made by a club
DELIMITER $$
 CREATE PROCEDURE `GetTransfersByClub`(IN club_id INT)
BEGIN
    SELECT
        *
    FROM
        transfers
    WHERE
        From_club_id = club_id OR To_club_id = club_id
    ORDER BY
        Date DESC
    LIMIT 5;
END $$
DELIMITER ;

--To update the points and the position once a match has been added in the match table
DELIMITER $$
 CREATE PROCEDURE `update_points`(IN input_match_id INT)
BEGIN
    DECLARE home_club INT;
    DECLARE away_club INT;
    DECLARE home_goals INT;
    DECLARE away_goals INT;
    DECLARE league_id1 INT;
    DECLARE result VARCHAR(42);

    -- Get match details
    SELECT home_club_id, away_club_id, goals_scored, goals_conceded,league_id
    INTO home_club, away_club, home_goals, away_goals,league_id1
    FROM matches
    WHERE match_id = input_match_id;

    -- Update goals in the Points table
    UPDATE Points
    SET goals_scored = goals_scored + home_goals,
        goals_conceded = goals_conceded + away_goals
    WHERE club_id = home_club;

    UPDATE Points
    SET goals_scored = goals_scored + away_goals,
        goals_conceded = goals_conceded + home_goals
    WHERE club_id = away_club;

    -- Determine match result
    IF home_goals > away_goals THEN
        SET result = 'home_winner';
    ELSEIF away_goals > home_goals THEN
        SET result = 'away_winner';
    ELSE
        SET result = 'draw';
    END IF;

    -- Update points based on the result
    IF result = 'home_winner' THEN
        UPDATE Points
        SET wins = wins + 1
        WHERE club_id = home_club;

        UPDATE Points
        SET losses = losses + 1
        WHERE club_id = away_club;

    ELSEIF result = 'away_winner' THEN
        UPDATE Points
        SET wins = wins + 1
        WHERE club_id = away_club;

        UPDATE Points
        SET losses = losses + 1
        WHERE club_id = home_club;

    ELSE -- draw
        UPDATE Points
        SET draws = draws + 1
        WHERE club_id = home_club;

        UPDATE Points
        SET draws = draws + 1
        WHERE club_id = away_club;
    END IF;

    -- Temporary table to calculate positions
    CREATE TEMPORARY TABLE temp_points AS
    SELECT club_id,
           points,
           (goals_scored - goals_conceded) AS goal_difference
    FROM Points
    WHERE league_id = league_id1;

    -- Update positions based on points and goal difference
    UPDATE Points AS p
    JOIN (
        SELECT club_id,
               ROW_NUMBER() OVER (ORDER BY points DESC, goal_difference DESC) AS new_position
        FROM temp_points
    ) AS ranked
    ON p.club_id = ranked.club_id
    SET p.position = ranked.new_position
    WHERE p.league_id = league_id1;

    DROP TEMPORARY TABLE temp_points; -- Clean up temporary table
END $$
DELIMITER ;
