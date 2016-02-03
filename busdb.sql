DROP DATABASE IF EXISTS busdb;

CREATE DATABASE busdb CHARACTER SET utf8;

USE busdb;

DROP TABLE IF EXISTS busdata;

CREATE TABLE busdata (
  datatime VARCHAR(20),
  cartype INT,
  providerid float(10,1),
  providername VARCHAR(10),
  carid INT,
  
  busid VARCHAR(10),
  dutystatus INT,
  busstatus INT,
  routeid float(10,1),
  routename VARCHAR(25),

  goback INT,
  longitude float(15,6),
  latitude float(15,6),
  speed float(15,6),
  azimuth float(15,6),
  
  stopid INT,
  stopLocationName VARCHAR(30)
);


INSERT INTO busdata VALUES ('2016/2/3 14:13:44',1,1100,'大南汽車',222233972,'078-FR',1,0,157863,'266(北投-市府)',0,121.524755,25.084855,11,185.2,41527,'捷運劍潭站(基河)');
INSERT INTO busdata VALUES ('2016/2/3 14:13:45',1,1100,'大南汽車',222233972,'078-FR',1,0,157863,'266(北投-市府)',0,121.524755,25.084855,11,185.2,41527,NULL);