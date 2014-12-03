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

/**
 *
 * @author Andy
 */
public class HomeHandler {

    public void PrepareData(HttpServletRequest request) throws ServletException, IOException, SQLException {
        PatientDao pdao = new PatientDao();
        ArrayList<Patient> patients = pdao.getAllPatients();
        request.setAttribute("Patients", patients);

        MedicineDao mdao = new MedicineDao();
        ArrayList<Medicine> medicine = mdao.getAllMedicine();
        request.setAttribute("Medicines", medicine);
    }
}
