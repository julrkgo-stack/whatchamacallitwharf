package com.whatchamacallitwharf.resources.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.whatchamacallitwharf.exception.DataAccessException;
import java.sql.Statement;

public final class Database {
    
    private static final String URL = "jdbc:h2:file:./target/h2db/carafe;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "";

    private Database() {}

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new DataAccessException("Database connection failed", e);
        }
    }

    // this is designed to recreate the table on every launch of myLittleDAOProj at this point
    // myLittleDAOProj is for learning and demonstrations
    public static void initializeDatabase() {
        String dropSQL = "DROP TABLE IF EXISTS vessel";
        String createSQL = ""
                + "CREATE TABLE vessel ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "vessel_name VARCHAR(100) NOT NULL, "
                + "vessel_type VARCHAR(100) NOT NULL, "
                + "color VARCHAR(100) NOT NULL, "
                + "pattern VARCHAR(100) NOT NULL, "
                + "material VARCHAR(100) NOT NULL, "
                + "capacity_oz INT NOT NULL CHECK (capacity_oz > 0), "
                + "dish_washer_safe BOOLEAN NOT NULL DEFAULT FALSE"
                + ")";

        try (Connection conn = getConnection();
            Statement st = conn.createStatement()) {
            st.execute(dropSQL);
            st.execute(createSQL);
        } catch (SQLException e) {
            throw new DataAccessException("Database Bootstrap failed", e);
        }
    }
}



// 1. Database Connection
// (or db.js, connection.pool, etc.)