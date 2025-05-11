# Studious
A student web application that can display a plethora of courses offered, and share news in a University.

This is my first project using [Spring Boot](https://spring.io/projects/spring-boot#overview), a Java framework for running Java applications that is easy to configure and setup. I have also used MySQL database to simulate a relational database management system running on a server, instead of using an in-memory database like SQLite in my other projects.

### Libraries
- [Spring Initializr](https://start.spring.io/index.html) to create the framework for the project.
- MySQL server.  
For a step-by-step guide on setting up your own MySQL server, you may refer to [mysql.md](mysql.md)
- [Thymeleaf](https://www.thymeleaf.org/), a modern server-side templating engine for webpages. A templating engine is required to send form data from the View to the Controller.

## Table of contents
#### Setting up
- [Spring Initializr](#spring-initializr)
- [MySQL Workbench](#mysql-workbench)
- [Java Database Connectivity](#java-database-connectivity-jdbc)
#### Project showcase
- [Login page](#login-page)
- [Home page](#home-page)
- [Navigation bar](#navigation-bar)
- [Course library](#course-library)
- [Change password](#change-password)
- [Password hashing](#password-hashing)
- [Logging out](#logging-out)

### Spring Initializr 
1. Go to [Spring Initializr](https://start.spring.io/index.html).
2. Set "Project" to Maven, "Language" to Java, and "Spring Boot" to version 3.4.5.
3. Under "Project Metadata", you may leave everything as is. The name of the project is not important.
4. Set "Packaging" to Jar (a.k.a Java Archive), Set "Java" to 17.
5. Click the "GENERATE" button.
6. Unzip the downloaded zip file and open in your favourite IDE. I am using Microsoft VS Code.
7. Replace the `src` file with this repository's [src](src) file.
8. Check that the `pom.xml` file generated contains the following dependencies:

![dependencies](media\dependencies.png)

### MySQL Workbench
After setting up the program, you have to set up the database as well. I have created 2 tables and 1 index, namely `students`, `courses` and `student_idx` respectively.  
To use MySQL Workbench and create the tables for this project, perform the following steps:  
1. Go to *Start* on Windows and search "MySQL Workbench 8.0 CE".
2. After opening the app, click on the local instance created on localhost:3306, and login with your "root" credentials.
3. Once inside the local instance, you will see the MySQL 8.0 ribbon, containing tabs and buttons that provide access to various commands and features.
![](media\mysql-ribbon.png)  
The <span style="color: #e5de00">yellow highlighted</span> button allows you to create a SQL tab to run queries. The <span style="color: #00ff00">green highlighted</span> button allows you to run SQL queries.  
4. Run the following commands in your newly created tab:  
`create database my_db;` - creates a database.  
`use database my_db;` - uses `my_db` as the active database.  
Afterwards, run the `CREATE TABLE` and `CREATE INDEX` statements in [schema.sql](src\main\resources\static\schema.sql)
5. Lastly, insert the sample data by running the `INSERT INTO` statements in [data.sql](src\main\resources\static\data.sql)

When performing CRUD (Create, Read, Update, Delete) on a database, it is good practice to use another account with only necessary permissions other than the "root" account. This is because the root account has all permissions over the database. When giving permissions to accounts, we always follow the "Principle of Least Privilege", meaning to grant users the minimum level of access necessary to perform tasks. This mitigates the risk of data breaches and unauthorised access.

You can create another user account under "MANAGEMENT" > "Users and Privileges" > click the "Add Account" button.

### Java Database Connectivity (JDBC)
In order to actually run SQL statements in Java, we have to establish a connection to the MySQL database.

The basic use case is as shown:  
```
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

String url = "jdbc:mysql://localhost:3306/my_db";
String username = "your_username";
String password = "your_password";

Connection con = DriverManager.getConnection(url, username, password);
```
Replace "my_db", "your_username" and "your_password" to the values you have set.

Running a SQL query in Java returns a `ResultSet` object, a `ResultSet` object is created by executing a query using a `Statement` or `PreparedStatement` object. For example:  
```
String sql = "SELECT * FROM table";
ResultSet rs = st.executeQuery(sql);
```
or
```
String sql = “INSERT INTO users (name, age) VALUES (?, ?)”;
PreparedStatement pst = con.prepareStatement(sql);
pst.setString(1, “John Smith”)
pst.setInt(2, 20)
ResultSet rs = pst.executeQuery(); 
```

In a `PreparedStatement`, the <kbd>?</kbd> symbol represents a placeholder value. The leftmost <kbd>?</kbd> symbol has index 1, and the adjacent <kbd>?</kbd> has index 2, and so on.

A `ResultSet` object has `getString()` and `getInt()` methods, which both take `columnIndex` as the parameter. The `columnIndex` refers to the column index of the output table based on your SQL query, and it starts from 1.

The next() method shifts the `rs` pointer from the 0th row to the 1st row. It returns `null` if there are no rows on the pointer.
```
// run while there are rows, and simultaneously shift the pointer to 1st row
while (rs.next()) { 
    System.out.println(rs.getString(1));
    System.out.println(rs.getInt(2));
}

// Result:
// "John Smith"
// 2
```

### Login page
The login page ensures that only registered students of the University are able to access the application. Only students in the `students` table in MySQL will be able to log in. An invalid login attempt will lead to an error popup. The program will query for an input if the user tries to submit an empty field.

![login-showcase](youtube_link)

### Home page
Upon reaching the home page, the program greets the student based on the time set in the client's machine. For example, a student will be greeted with "Good Evening" when the time is between 6.00 pm and 11.59 pm.
```
const greetStudent = (name) => {
    const date = new Date();
    const hour = date.getHours();
    if (hour >= 0 && hour <= 11) {
        greetTextDiv.textContent = "Good Morning,";
    } else if (hour >= 12 && hour <= 17) {
        greetTextDiv.textContent = "Good Afternoon,";
    } else {
        greetTextDiv.textContent = "Good Evening,";
    }
    nameTextDiv.textContent = name;
}
```
The date is received via the Date object in JavaScript. A JavaScript date is specified as the time in milliseconds since the epoch (January 1, 1970, UTC).

### Navigation bar
A navigation bar provides the ease of accessing different pages of a web application. This enhances the overall User eXperience (UX).

![home-and-navbar]()

### Course library
On this page, the program receives the student's input, queries the database, writes the result into a Javascript Object Notation (JSON) file, and finally displays the data from the JSON in a visually appealing manner using HTML and CSS.

Javascript is used to dynamically change the form fields based on the dropdown value. This is done by setting the `myInputElement.style.display` value to either `block` (show) or `none` (hide). There is also a need to set the `myInputElement.required` value to a boolean as hiding a field does not remove its requirement.

![search-mode]()

There are 3 search modes, `All`, `Course Code`, and `Course Name`. Each mode has different parameters to be filled up. A few search examples will be performed to showcase the robustness of the function. The text within quotation marks represent the search value.

1. Search by `All`
2. Search by `Course Code`:
- "MA301"
- "cs1"
3. Search by `Course Name`:
- "Introduction", Starts With
- "and", Contains
- When no search mode is selected

![search-test]()

### Change password
Changing passwords regularly is vital in securing online accounts. Hovering over "Account", you can see a "Change Password" option on the dropdown. Clicking on that option directs you to change your password.

![hover-account]()

Below are the test cases:

1. Less than 12 characters: "shortp@ss"
2. Does not contain at least 1 special character: "nospecialchar"
3. Contains whitespace: "hello world!"
4. Wrong current password and valid new password: "@validpassword"
5. Correct current password and valid new password
When the password change is successful, the user will be logged out and will have to login again.

![change-password-test]()

![change-pw-success-re-login]()

### Password Hashing
It is imperative to store hashed passwords in databases. If a database is compromised, sensitive information such as passwords stored in plaintext format may be leaked. Plaintext can be converted to ciphertext, through *hashing*.

In the Studious application, a one-way hashing function is used to hash passwords, and the password is updated in the database. When a user logs in, the *hash function* takes the input password and hashes it. The login will only be successful if the saved hash in the database and the input hash by the user is an exact match. This algorithm works because the same hash function always yields the same hash for the same input.

### Logging out
Finally, the user can logout by clicking "Logout" at the right side of the navigation bar. This clears all `localStorage` values, and resets the JSON files to its original state.

### Learning resources
- Form handling in Spring Boot and Thymeleaf:  
https://www.codejava.net/frameworks/spring-boot/spring-boot-thymeleaf-form-handling-tutorial#google_vignette

- Hashing algorithms in Java:  
https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
