package Classes;

import DatabaseAccessObjects.MedicineDao;
import DatabaseAccessObjects.PatientDao;
import Models.Medicine;
import Models.Patient;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class HomeHandler {

    public void PrepareData(HttpServletRequest request) throws ServletException, IOException, SQLException {
        // Set up the data access object.
        PatientDao pdao = new PatientDao();

        // Get all patients from the database and pass them onward in the request.
        ArrayList<Patient> patients = pdao.getAllPatients();
        request.setAttribute("Patients", patients);

        // Set up the data access object.
        MedicineDao mdao = new MedicineDao();

        // Get all medicines from the database and pass them onward in the request.
        ArrayList<Medicine> medicine = mdao.getAllMedicine();
        request.setAttribute("Medicines", medicine);
    }
}
