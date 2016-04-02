CREATE TABLE IF NOT EXISTS users (
  dni INTEGER NOT NULL ,
  first_name VARCHAR (50) ,
  last_name VARCHAR (50) ,
  genre CHAR (1) ,
  birthday DATE ,
  email VARCHAR(100) ,

  PRIMARY KEY (dni)
);

CREATE TABLE IF NOT EXISTS address (
  country VARCHAR(50) NOT NULL ,
  city VARCHAR(50) NOT NULL ,
  neighborhood VARCHAR(50) NOT NULL ,
  street VARCHAR(100) NOT NULL,
  number INTEGER NOT NULL,
  floor INTEGER,
  door INTEGER,
  dni INTEGER NOT NULL ,

  PRIMARY KEY (country, city, neighborhood, street, number, dni),
  FOREIGN KEY (dni) REFERENCES "user" ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS student (
  docket SERIAL NOT NULL ,
  dni INTEGER NOT NULL ,
  password VARCHAR (100) ,

  PRIMARY KEY (docket),
  FOREIGN KEY (dni) REFERENCES "user"
);

CREATE TABLE IF NOT EXISTS course (
  id INTEGER NOT NULL,
  name VARCHAR(50) NOT NULL,
  credits INTEGER NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS inscription (
  docket INTEGER NOT NULL,
  course_id INTEGER NOT NULL,

  PRIMARY KEY (docket, course_id),
  FOREIGN KEY (docket) REFERENCES student ON DELETE CASCADE,
  FOREIGN KEY (course_id) REFERENCES course ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS grade (
  docket INTEGER NOT NULL,
  course_id INTEGER NOT NULL ,
  grade DECIMAL NOT NULL ,

  PRIMARY KEY (docket, course_id, grade),
  FOREIGN KEY (docket) REFERENCES student ON DELETE CASCADE,
  FOREIGN KEY (course_id) REFERENCES course ON DELETE RESTRICT
);