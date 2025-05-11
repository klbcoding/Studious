-- sample data values for the courses in the University

INSERT INTO courses (code, subject, title, difficulty, commitment, credits, price, description) VALUES (
"CS101", "Computing", "Introduction to Programming with Python", "Easy", 24, 3, 600,
"Learn the basics of programming with Python, a high level programming language widely used today."
),
(
"CS201", "Computing", "Algorithms and Data Structures", "Medium", 24, 3, 600,
"Understand search and sorting algorithms, various ways to organise data, and the concept of time and space complexity."
),
(
"CS202", "Computing", "Introduction to Databases with SQL", "Medium", 32, 4, 800,
"Learn to CRUD (Create, Read, Update, Delete) using Structured Query Language (SQL). Create entity relationship diagrams. Understand race conditions and SQL injections. Learn how to set up a MySQL server."
),
("MA301", "Mathematics", "Probability and Statistics", "Hard", 32, 4, 800, 
"Probability is the measure of the likelihood of an outcome occurring in a given circumstance, Statistics involves the collection, analysis, interpretation, presentation, and organization of data."
);

-- sample data values of all students registered in Studious

INSERT INTO students (email, password, name, year, major) VALUES
("admin@studious.com", "827ccb0eea8a706c4c34a16891f84e7b", "Admin", 1, "Computing"), 
("john_smith@studious.com", "2ab96390c7dbe3439de74d0c9b0b1767", "John Smith", 2, "Engineering");