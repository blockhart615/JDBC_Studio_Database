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



-- DROP TABLE Album;
-- 
-- DROP TABLE RecordingGroup;
-- 
-- DROP TABLE RecordingStudio;