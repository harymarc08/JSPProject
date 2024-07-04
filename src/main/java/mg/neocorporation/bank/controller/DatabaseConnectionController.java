package mg.neocorporation.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseConnectionController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/checkConnection")
    public String checkConnection() {
        try {
            jdbcTemplate.execute("SELECT 1");
            return "Connection to the database is successful!";
        } catch (Exception e) {
            return "Error connecting to the database: " + e.getMessage();
        }
    }
}
