package Helpers;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {

    private Connection con;
    private Statement state;

    public ResultSet getResultSet(String query) {
        connectToDb();

        // Returns the result set with the query provided.
        if (state != null) {
            try {
                return state.executeQuery(query);
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public PreparedStatement getPreparedStatement(String query) {
        connectToDb();

        // Returns the prepared statement with the query provided.
        if (state != null) {
            try {
                return con.prepareStatement(query);
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public void disconnectFromDB() {
        // Close all connections to the database.
        try {
            con.close();
            state.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void connectToDb() {
        // Set up the necessary connections to the database.
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gpinfo", "root", "");
            state = con.createStatement();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
