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

/**
 *
 * @author Andy
 */
public class PatientHandler {

    public void Add(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";
        PatientDao pdao = new PatientDao();
        Patient patient = new Patient();

        patient.setName(request.getParameter("name"));
        pdao.addPatient(patient);

        request.setAttribute("page", page);
    }

    public void Create(HttpServletRequest request) throws SQLException {
        String page = "PatientPage.jsp";

        request.setAttribute("Action", "Add");
        request.setAttribute("page", page);
    }

    public void Delete(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";
        PatientDao pdao = new PatientDao();

        BillDao bdao = new BillDao();
        Bill bill = bdao.getPatientBill(Integer.parseInt(request.getParameter("id")));

        if (bill == null) {
            pdao.deletePatient(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("page", page);
        }
    }

    public void Update(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";
        PatientDao pdao = new PatientDao();
        Patient patient = new Patient();

        patient.setId(Integer.parseInt(request.getParameter("id")));
        patient.setName(request.getParameter("name"));
        pdao.updatePatient(patient);

        request.setAttribute("page", page);
    }

    public void View(HttpServletRequest request) throws SQLException {
        String page = "PatientPage.jsp";

        BillDao bdao = new BillDao();
        Bill bill = bdao.getPatientBill(Integer.parseInt(request.getParameter("id")));

        PatientDao pdao = new PatientDao();
        Patient patient = pdao.getPatient(Integer.parseInt(request.getParameter("id")));

        MedicineDao mdao = new MedicineDao();
        ArrayList<Medicine> allMedicine = mdao.getAllMedicine();

        int cost = 0;
        if (bill != null) {
            ArrayList<BillMedicine> countedBillmedicines = new ArrayList<>();
            ArrayList<BillMedicine> patientBillMedicines = bdao.getPatientBillMedicines(bill.getId());

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

            for (BillMedicine billMedicine : countedBillmedicines) {
                for (Medicine medicine : allMedicine) {
                    if (billMedicine.getMedicineId() == medicine.getId()) {
                        cost += billMedicine.getQuantity() * medicine.getCost();
                        billMedicine.setName(medicine.getName());
                        billMedicine.setCost(medicine.getCost());
                    }
                }
            }

            bill.setMedicines(countedBillmedicines);
            cost += bill.getConsultationFee();
        }

        request.setAttribute("Action", "Update");
        request.setAttribute("Bill", bill);
        request.setAttribute("Patient", patient);
        request.setAttribute("TotalCost", cost);

        request.setAttribute("page", page);
    }
}
