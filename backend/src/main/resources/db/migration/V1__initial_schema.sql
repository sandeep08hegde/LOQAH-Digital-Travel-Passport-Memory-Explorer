CREATE TABLE app_users (
 id UUID PRIMARY KEY,
 email VARCHAR(180) NOT NULL UNIQUE,
 password_hash VARCHAR(255) NOT NULL,
 display_name VARCHAR(80) NOT NULL,
 role VARCHAR(30) NOT NULL DEFAULT 'USER',
 created_at TIMESTAMPTZ NOT NULL
);
CREATE TABLE destinations (
 id UUID PRIMARY KEY,
 name VARCHAR(140) NOT NULL,
 state VARCHAR(80) NOT NULL,
 category VARCHAR(40) NOT NULL,
 description VARCHAR(1000),
 image_url VARCHAR(500),
 latitude DOUBLE PRECISION,
 longitude DOUBLE PRECISION,
 active BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE visits (
 id UUID PRIMARY KEY,
 user_id UUID NOT NULL REFERENCES app_users(id),
 destination_id UUID NOT NULL REFERENCES destinations(id),
 visited_at TIMESTAMPTZ NOT NULL,
 status VARCHAR(20) NOT NULL,
 note VARCHAR(500),
 CONSTRAINT uq_user_destination UNIQUE(user_id,destination_id)
);
CREATE INDEX idx_destinations_state ON destinations(state);
CREATE INDEX idx_destinations_category ON destinations(category);
CREATE INDEX idx_visits_user_date ON visits(user_id,visited_at DESC);
INSERT INTO destinations(id,name,state,category,description,image_url,latitude,longitude,active) VALUES
(gen_random_uuid(),'Mysore Palace','Karnataka','TEMPLE','Historic royal palace and one of Mysuru’s best-known landmarks.',NULL,12.3052,76.6552,TRUE),
(gen_random_uuid(),'Jog Falls','Karnataka','WATERFALL','A spectacular waterfall in the Western Ghats.',NULL,14.2290,74.8100,TRUE),
(gen_random_uuid(),'Coorg','Karnataka','NATURE','Hill region known for coffee estates, forests and scenic landscapes.',NULL,12.3375,75.8069,TRUE),
(gen_random_uuid(),'Hampi','Karnataka','HERITAGE','UNESCO-listed historic landscape of Vijayanagara-era monuments.',NULL,15.3350,76.4600,TRUE),
(gen_random_uuid(),'Ladakh','Ladakh','TREK','High-altitude Himalayan landscapes and trekking routes.',NULL,34.1526,77.5771,TRUE);