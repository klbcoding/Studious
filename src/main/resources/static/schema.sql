CREATE TABLE students (
	id INT UNSIGNED PRIMARY KEY auto_increment,
    email VARCHAR(64) NOT NULL,
    password VARCHAR(32) NOT NULL,
    name VARCHAR(255) NOT NULL,
    year TINYINT NOT NULL,
    major VARCHAR(32) NOT NULL
);

CREATE TABLE courses (
	code VARCHAR(7) NOT NULL,
    subject VARCHAR(32) NOT NULL,
    title VARCHAR(64) NOT NULL,
    difficulty VARCHAR(6) NOT NULL,
    commitment TINYINT NOT NULL,
    credits TINYINT NOT NULL,
    price SMALLINT NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE INDEX student_idx ON students (email, password);