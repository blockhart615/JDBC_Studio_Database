CREATE TABLE RecordingStudio (
    studio_name     VARCHAR(30) NOT NULL,
    studio_address  VARCHAR(50) NOT NULL,
    studio_owner    VARCHAR(25),
    studio_phone    VARCHAR(15),
    CONSTRAINT pk_RecordingStudio PRIMARY KEY (studio_name)
);

CREATE TABLE RecordingGroup (
    group_name      VARCHAR(30) NOT NULL,
    lead_singer     VARCHAR(50),
    year_formed     INTEGER,
    genre           VARCHAR(20),
    CONSTRAINT pk_RecordingGroup PRIMARY KEY (group_name)
);

CREATE TABLE Album (
    album_title     VARCHAR(30) NOT NULL,
    date_recorded   DATE,
    length          TIME,
    genre           VARCHAR(20),
    studio_name     VARCHAR(30) NOT NULL,
    group_name      VARCHAR(30) NOT NULL,
    CONSTRAINT fk_StudioAlbum FOREIGN KEY (studio_name)
    REFERENCES RecordingStudio(studio_name),
    CONSTRAINT fk_GroupAlbum FOREIGN KEY (group_name)
    REFERENCES RecordingGroup(group_name),
    CONSTRAINT pk_Album PRIMARY KEY (album_title, group_name)
);

INSERT INTO RecordingGroup VALUES ('Water Bottle', 'Blue', 2015, 'Liquid'),
    ('Pencil', 'Bic', 2015, 'The Writers'), ('Whiteboard', 'Expo', 2015, 'Draw Something'); 

INSERT INTO RecordingStudio VALUES ('some studio', '123 fake street', 'bill murray', '555-555-5555'),
    ('Banded Rocking Records', '1337 maple street', 'Maple Ari', '333-333-3333');

INSERT INTO Album VALUES ('New Album', '2014-06-06' , '23:59:59' , 'stinky', 'some studio', 'Whiteboard'),
    ('Anther Album', '2014-06-06' , '23:59:59' , 'stinky', 'some studio', 'Water Bottle'), 
    ('New Album', '2011-04-06', '10:00', 'Cool Genre', 'Banded Rocking Records', 'Pencil');

Select * FROM RecordingStudio;
Select * FROM RecordingGroup;
Select * FROM Album;

-- DROP TABLE Album;
-- 
-- DROP TABLE RecordingGroup;
-- 
-- DROP TABLE RecordingStudio;
