CREATE TABLE countries (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    code TEXT NOT NULL UNIQUE
);

CREATE TABLE leagues (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    country_id INTEGER NOT NULL,
    tier INTEGER NOT NULL,
    max_teams INTEGER NOT NULL,
    face_times INTEGER NOT NULL DEFAULT 2,
    year INTEGER NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countries(id)
);

CREATE TABLE cups (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    country_id INTEGER NOT NULL,
    max_teams INTEGER NOT NULL,
    max_matches INTEGER NOT NULL,
    year INTEGER NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countries(id)
);

CREATE TABLE teams (
    id INTEGER PRIMARY KEY,
    long_name TEXT NOT NULL,
    short_name TEXT NOT NULL,
    country_id INTEGER NOT NULL,
    league_id INTEGER,
    formation TEXT NOT NULL DEFAULT 'F_4_4_2',
    season_wins INTEGER DEFAULT 0,
    season_draws INTEGER DEFAULT 0,
    season_losses INTEGER DEFAULT 0,
    season_goals INTEGER DEFAULT 0,
    goals_conceded INTEGER DEFAULT 0,
    FOREIGN KEY (country_id) REFERENCES countries(id),
    FOREIGN KEY (league_id) REFERENCES leagues(id)
);

CREATE TABLE players (
    id INTEGER PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    display_name TEXT,
    dob TEXT NOT NULL,
    position TEXT NOT NULL,
    overall INTEGER NOT NULL,
    speed INTEGER NOT NULL,
    shooting INTEGER NOT NULL,
    tackling INTEGER NOT NULL,
    passing INTEGER NOT NULL,
    reactions INTEGER NOT NULL,
    blocking INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    FOREIGN KEY (team_id) REFERENCES teams(id)
);

CREATE TABLE starting_xi (
    team_id INTEGER NOT NULL,
    slot INTEGER NOT NULL,
    player_id INTEGER NOT NULL,
    PRIMARY KEY (team_id, slot),
    FOREIGN KEY (team_id) REFERENCES teams(id),
    FOREIGN KEY (player_id) REFERENCES players(id)
);

CREATE TABLE seasons (
    id INTEGER PRIMARY KEY,
    competition_id INTEGER NOT NULL,
    competition_type TEXT NOT NULL,
    year INTEGER NOT NULL,
    started INTEGER DEFAULT 0,
    completed INTEGER DEFAULT 0,
    curr_round INTEGER DEFAULT 1
);

CREATE TABLE season_teams (
    season_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    PRIMARY KEY (season_id, team_id),
    FOREIGN KEY (season_id) REFERENCES seasons(id),
    FOREIGN KEY (team_id) REFERENCES teams(id)
);

CREATE TABLE matches (
    id INTEGER PRIMARY KEY,
    team1_id INTEGER NOT NULL,
    team2_id INTEGER NOT NULL,
    country_id INTEGER NOT NULL,
    season_id INTEGER,
    match_type TEXT NOT NULL DEFAULT 'LEAGUE',
    round INTEGER DEFAULT 0,
    score1 INTEGER DEFAULT 0,
    score2 INTEGER DEFAULT 0,
    shots_on_target1 INTEGER DEFAULT 0,
    shots_on_target2 INTEGER DEFAULT 0,
    shots_off_target1 INTEGER DEFAULT 0,
    shots_off_target2 INTEGER DEFAULT 0,
    possession_minutes1 INTEGER DEFAULT 0,
    possession_minutes2 INTEGER DEFAULT 0,
    last_event_minute INTEGER DEFAULT 0,
    last_event_description TEXT,
    finished INTEGER DEFAULT 0,
    need_overtime INTEGER DEFAULT 0,
    FOREIGN KEY (team1_id) REFERENCES teams(id),
    FOREIGN KEY (team2_id) REFERENCES teams(id),
    FOREIGN KEY (country_id) REFERENCES countries(id),
    FOREIGN KEY (season_id) REFERENCES seasons(id)
);

CREATE TABLE game_states (
    id INTEGER PRIMARY KEY,
    managed_team_id INTEGER NOT NULL,
    current_day INTEGER NOT NULL DEFAULT 1,
    current_season_id INTEGER NOT NULL,
    FOREIGN KEY (managed_team_id) REFERENCES teams(id),
    FOREIGN KEY (current_season_id) REFERENCES seasons(id)
);
