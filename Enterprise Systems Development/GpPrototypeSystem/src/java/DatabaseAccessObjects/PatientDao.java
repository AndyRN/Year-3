package DatabaseAccessObjects;

import Helpers.DatabaseManager;
import Models.Patient;
import java.sql.*;
import java.util.ArrayList;

public class PatientDao {

    private final DatabaseManager dbm = new DatabaseManager();

    public Patient getPatient(int id) throws SQLException {
        Patient patient = null;

        // Execute SQL query to find a specific patient.
        ResultSet patientSet = dbm.getResultSet("SELECT * from patients WHERE id = " + id);
        if (patientSet != null) {
            while (patientSet.next()) {
                patient = new Patient();
                patient.setId(patientSet.getInt("id"));
                patient.setName(patientSet.getString("name"));
            }
            dbm.disconnectFromDB();
        }

        return patient;
    }

    public Patient getLatestPatient() throws SQLException {
        Patient patient = null;

        // Execute SQL query to find the most recently added patient.
        ResultSet patientSet = dbm.getResultSet("SELECT * from patients WHERE id = LAST_INSERT_ID()");
        if (patientSet != null) {
            while (patientSet.next()) {
                patient = new Patient();
                patient.setId(patientSet.getInt("id"));
                patient.setName(patientSet.getString("name"));
            }
            dbm.disconnectFromDB();
        }

        return patient;
    }

    public ArrayList<Patient> getAllPatients() throws SQLException {
        ArrayList<Patient> patientList = new ArrayList<>();

        // Execute SQL query to find all patients.
        ResultSet patientSet = dbm.getResultSet("SELECT * from patients");
        if (patientSet != null) {
            while (patientSet.next()) {
                Patient patient = new Patient();
                patient.setId(patientSet.getInt("id"));
                patient.setName(patientSet.getString("name"));

                // Add each found patient to the patient list.
                patientList.add(patient);
            }
            dbm.disconnectFromDB();
        }

        return patientList;
    }

    public void addPatient(Patient patient) throws SQLException {
        PreparedStatement pState = dbm.getPreparedStatement("INSERT INTO patients (name) values (?)");

        // Add the relevant information to the prepared statement.
        pState.setString(1, patient.getName());

        execute(pState);
    }

    public void updatePatient(Patient patient) throws SQLException {
        PreparedStatement pState = dbm.getPreparedStatement("UPDATE patients SET name = ? WHERE id = ?");

        // Add the relevant information to the prepared statement.
        pState.setString(1, patient.getName());
        pState.setInt(2, patient.getId());

        execute(pState);
    }

    public void deletePatient(int id) throws SQLException {
        PreparedStatement pState = dbm.getPreparedStatement("DELETE FROM patients WHERE id = ?");

        // Add the relevant information to the prepared statement.
        pState.setInt(1, id);

        execute(pState);
    }

    private void execute(PreparedStatement pState) throws SQLException {
        pState.executeUpdate();
        dbm.disconnectFromDB();
    }
}
