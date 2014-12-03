package Helpers;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ar2-nutt
 */
public class DatabaseManager {

    private Connection con;
    private Statement state;

    public ResultSet getResultSet(String query) {
        connectToDb();

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
        try {
            con.close();
            state.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void connectToDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gpinfo", "root", "");
            state = con.createStatement();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
