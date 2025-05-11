package com.example.studious;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class StudiousController {
    // connecting to MySQL database
    MySQLConnection sqlConnection = new MySQLConnection();
    Connection con = sqlConnection.getConnection();

    // Initialising MD5 algorithm object
    MD5Hash md5 = new MD5Hash();    

    @GetMapping("/login")
    public String renderLogin(Model model) {
        model.addAttribute("student", new Student());
        return "login";
    }


    @GetMapping("/alert") 
    public String renderAlert() {
        return "alert";
    }

    // retrieving student data and putting into local storage of student's device
    @PostMapping("/login")
    public String login(@ModelAttribute("student") Student student, Model model) {
        model.addAttribute("student", student);
        String sql = "SELECT name, year, major FROM students WHERE email = ? AND password = ?";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, student.getEmail());
            md5.hashPassword(student.getPassword());
            pst.setString(2, md5.getHashedPassword());
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) { // no such user found
                return "redirect:/alert";
            } else { // there is a match, student has logged in
                FileWriter writer = new FileWriter("src\\main\\resources\\templates\\api\\studentData.json");
                String jsonTemplate = """
{
    "name": "%s",
    "year": %d,
    "major": "%s",
    "email": "%s"
}
                        """;
                writer.write(String.format(jsonTemplate, rs.getString(1), rs.getInt(2), rs.getString(3), student.getEmail()));
                writer.close();
                // wait for the studentData to fully write
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "redirect:/home";
    }


    @GetMapping("/api/studentData.json")
    public String renderLoginAPI() {
        return "./api/studentData.json";
    }


    @GetMapping("/home")
    public String renderHome() {
        return "home";
    }


    @GetMapping("/courses")
    public String renderCourses(Model model) {
        model.addAttribute("sp", new SearchParameters());
        return "courses";
    }


    @PostMapping("/courses")
    public String showCourses(@ModelAttribute("sp") SearchParameters sp, Model model) throws SQLException, IOException, InterruptedException {
        model.addAttribute("sp", sp);
        Statement st = con.createStatement();
        String sql = "";
        
        switch (sp.getSearchBy()) {
            case "all":
                sql = "SELECT * FROM courses";
                break;
            case "course-code":
                sql = String.format("SELECT * FROM courses WHERE code LIKE '%s%%'", sp.getQuery());
                break;
            case "course-name":
                if (sp.getSearchMethod().equals("starts-with")) {
                    sql = String.format("SELECT * FROM courses WHERE title LIKE '%s%%'", sp.getQuery());
                };

                if (sp.getSearchMethod().equals("contains")) {
                    sql = String.format("SELECT * FROM courses WHERE title LIKE '%%%s%%'", sp.getQuery());
                };
                break;
        }
        ResultSet rs = st.executeQuery(sql);
        FileWriter writer = new FileWriter("src\\main\\resources\\templates\\api\\courses.json");
        ArrayList<String> array = new ArrayList<>();
        String jsonTemplate = """
{
    "code": "%s",
    "subject": "%s",
    "title": "%s",
    "difficulty": "%s",
    "commitment": %d,
    "credits": %d,
    "price": %d,
    "description": "%s"
}
                """;

        if (!rs.isBeforeFirst()) {
            // do nothing, array will be []
        } else {
            while (rs.next()) {
                array.add(String.format(jsonTemplate, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getString(8)));
            }
        }
        writer.write(array.toString());
        writer.close();
        Thread.sleep(2000);
        return "redirect:/courses";
    }


    @GetMapping("/api/courses.json")
    public String renderCoursesAPI() {
        return "./api/courses.json";
    }


    @GetMapping("/change-password")
    public String renderChangePassword(Model model) {
        model.addAttribute("pf", new PasswordForm());
        return "change-password";
    }


    @PostMapping("/change-password")
    public String updatePassword(@ModelAttribute("pf") PasswordForm pf, Model model) throws SQLException { 
        model.addAttribute("pf", pf);

        // verifying the identity of the requestor
        String getPassSql = "SELECT password FROM students WHERE email = ?";
        PreparedStatement pst1 = con.prepareStatement(getPassSql);
        pst1.setString(1, pf.getEmail()); 
        ResultSet rs = pst1.executeQuery();
        
        while (rs.next()) {
            md5.hashPassword(pf.getCurrentPassword());
            String currentPassword = md5.getHashedPassword();
            if (!rs.getString(1).equals(currentPassword)) {
                return "alert";
            }
        }

        // password matches, to update the password.
        String updatePassSql = "UPDATE students SET password = ? WHERE email = ?";
        PreparedStatement pst2 = con.prepareStatement(updatePassSql);
        md5.hashPassword(pf.getNewPassword());

        pst2.setString(1, md5.getHashedPassword());
        pst2.setString(2, pf.getEmail());
        pst2.executeUpdate();
        return "redirect:/logout";
    }


    @GetMapping("/logout")
    public String renderLogout() throws IOException {
        // reset all json files to the original state
        FileWriter courseWriter = new FileWriter("src\\main\\resources\\templates\\api\\courses.json");
        courseWriter.write("[]");
        courseWriter.close();
        FileWriter studentDataWriter = new FileWriter("src\\main\\resources\\templates\\api\\studentData.json");
        studentDataWriter.write("");
        studentDataWriter.close();
        return "logout";
    }
}


   