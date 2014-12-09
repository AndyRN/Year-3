package Classes;

import DatabaseAccessObjects.BillDao;
import DatabaseAccessObjects.MedicineDao;
import DatabaseAccessObjects.PatientDao;
import Models.Bill;
import Models.BillMedicine;
import Models.Medicine;
import Models.Patient;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class PatientHandler {

    public void Add(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";

        // Set up the data access objects and models.
        PatientDao pdao = new PatientDao();
        Patient patient = new Patient();

        // Add the relevant information to the model from the request.
        patient.setName(request.getParameter("name"));

        // Pass the model to the data access object to be added to the database.
        pdao.addPatient(patient);

        request.setAttribute("page", page);
    }

    public void Create(HttpServletRequest request) throws SQLException {
        String page = "PatientPage.jsp";

        // Redirect to the patient page so that the user can create and add a patient to the database.
        request.setAttribute("Action", "Add");
        request.setAttribute("page", page);
    }

    public void Delete(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";

        // Set up the data access objects and models.
        PatientDao pdao = new PatientDao();
        BillDao bdao = new BillDao();

        // Attempt to get the current patient bill from the database.
        Bill bill = bdao.getPatientBill(Integer.parseInt(request.getParameter("id")));

        // If none exists, user cannot delete the patient.
        if (bill == null) {
            // Delete the relevant patient from the database.
            pdao.deletePatient(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("page", page);
        }
    }

    public void Update(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";

        // Set up the data access objects and models.
        PatientDao pdao = new PatientDao();
        Patient patient = new Patient();

        // Add the relevant information to the model from the request.
        patient.setId(Integer.parseInt(request.getParameter("id")));
        patient.setName(request.getParameter("name"));

        // Update the relevant patient with the information provided.
        pdao.updatePatient(patient);

        request.setAttribute("page", page);
    }

    public void View(HttpServletRequest request) throws SQLException {
        String page = "PatientPage.jsp";

        // Set up the data access objects and models.
        BillDao bdao = new BillDao();
        Bill bill = bdao.getPatientBill(Integer.parseInt(request.getParameter("id")));
        PatientDao pdao = new PatientDao();
        Patient patient = pdao.getPatient(Integer.parseInt(request.getParameter("id")));
        MedicineDao mdao = new MedicineDao();
        ArrayList<Medicine> allMedicine = mdao.getAllMedicine();

        // Attempt to get all medicines that have been added to the patient's bill.
        int cost = 0;
        if (bill != null) {
            ArrayList<BillMedicine> countedBillmedicines = new ArrayList<>();
            ArrayList<BillMedicine> patientBillMedicines = bdao.getPatientBillMedicines(bill.getId());

            // Compile all medicine quantities.
            for (BillMedicine billMedicine : patientBillMedicines) {
                boolean found = false;

                for (BillMedicine medicine : countedBillmedicines) {
                    if (billMedicine.getMedicineId() == medicine.getMedicineId()) {
                        medicine.setQuantity(medicine.getQuantity() + billMedicine.getQuantity());
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    countedBillmedicines.add(billMedicine);
                }
            }

            // Get extra information about each medicine for viewing.
            for (BillMedicine billMedicine : countedBillmedicines) {
                for (Medicine medicine : allMedicine) {
                    if (billMedicine.getMedicineId() == medicine.getId()) {
                        cost += billMedicine.getQuantity() * medicine.getCost();
                        billMedicine.setName(medicine.getName());
                        billMedicine.setCost(medicine.getCost());
                    }
                }
            }

            // Add these medicines to the model that is passed to the view.
            bill.setMedicines(countedBillmedicines);

            // Total up the medicine cost including the consultation fee.
            cost += bill.getConsultationFee();
        }

        // Get the relevant information from the database to show in the view patient page.
        request.setAttribute("Action", "Update");
        request.setAttribute("Bill", bill);
        request.setAttribute("Patient", patient);
        request.setAttribute("TotalCost", cost);

        request.setAttribute("page", page);
    }
}
