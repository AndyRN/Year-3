package Classes;

import DatabaseAccessObjects.BillDao;
import DatabaseAccessObjects.PatientDao;
import Models.Bill;
import Models.BillMedicine;
import Models.Patient;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class BillHandler {

    public void Add(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";

        // Set up the data access objects and models.
        Bill bill = new Bill();
        BillDao bdao = new BillDao();

        // Add the relevant information to the model from the request.
        bill.setPatientId(Integer.parseInt(request.getParameter("patient")));
        bill.setConsultationFee(Integer.parseInt(request.getParameter("fee")));

        // Pass the model to the data access object to be added to the database.
        bdao.addBill(bill);

        request.setAttribute("page", page);
    }

    public void AddMedicine(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";

        // Set up the data access objects and models.
        BillMedicine billMedicine = new BillMedicine();
        BillDao bdao = new BillDao();
        Bill bill = bdao.getPatientBill(Integer.parseInt(request.getParameter("patient")));

        // Add the relevant information to the model from the request.
        billMedicine.setBillId(bill.getId());
        billMedicine.setMedicineId(Integer.parseInt(request.getParameter("medicineId")));
        billMedicine.setQuantity(Integer.parseInt(request.getParameter("quantity")));

        // Pass the model to the data access object to be added to the database.
        bdao.addBillMedicine(billMedicine);

        request.setAttribute("page", page);
    }

    public void AddToPatient(HttpServletRequest request) throws SQLException {
        String page = "AddMedicinePage.jsp";

        // Set up the data access objects.
        PatientDao pdao = new PatientDao();
        BillDao bdao = new BillDao();

        // Only list the patients that currently have a bill.
        ArrayList<Patient> patients = pdao.getAllPatients();
        ArrayList<Patient> patientsWithBill = new ArrayList<>();
        for (Patient patient : patients) {
            if (bdao.getPatientBill(patient.getId()) != null) {
                patientsWithBill.add(patient);
            }
        }

        if (patientsWithBill.isEmpty()) {
            // If there are no patients with a bill, remain on home page.
            page = "HomePage.jsp";
        } else {
            // Add the relevant information to the request so the user can add medicines to patients with a bill.
            request.setAttribute("PatientsWithBill", patientsWithBill);
            request.setAttribute("MedicineId", Integer.parseInt(request.getParameter("id")));
            request.setAttribute("MedicineName", request.getParameter("medicineName"));
        }

        request.setAttribute("page", page);
    }

    public void Update(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";

        // Set up the data access object.
        BillDao bdao = new BillDao();

        // Update a bill entry in the database with the relevant information.
        bdao.updateBill(Integer.parseInt(request.getParameter("fee")), Integer.parseInt(request.getParameter("patient")));

        request.setAttribute("page", page);
    }

    public void Pay(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";

        // Set up the data access object.
        BillDao bdao = new BillDao();

        // Sets the relevant bill in the database to be "paid".
        bdao.payBill(Integer.parseInt(request.getParameter("bill")));

        request.setAttribute("page", page);
    }
}
