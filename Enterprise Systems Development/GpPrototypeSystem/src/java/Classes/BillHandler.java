package Classes;

import DatabaseAccessObjects.BillDao;
import DatabaseAccessObjects.PatientDao;
import Models.Bill;
import Models.BillMedicine;
import Models.Patient;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ar2-nutt
 */
public class BillHandler {

    public void Add(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";
        Bill bill = new Bill();
        BillDao bdao = new BillDao();

        bill.setPatientId(Integer.parseInt(request.getParameter("patient")));
        bill.setConsultationFee(Integer.parseInt(request.getParameter("fee")));

        bdao.addBill(bill);

        request.setAttribute("page", page);
    }

    public void AddMedicine(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";
        BillMedicine billMedicine = new BillMedicine();
        BillDao bdao = new BillDao();

        Bill bill = bdao.getPatientBill(Integer.parseInt(request.getParameter("patient")));

        billMedicine.setBillId(bill.getId());
        billMedicine.setMedicineId(Integer.parseInt(request.getParameter("medicineId")));
        billMedicine.setQuantity(Integer.parseInt(request.getParameter("quantity")));

        bdao.addBillMedicine(billMedicine);

        request.setAttribute("page", page);
    }

    public void AddToPatient(HttpServletRequest request) throws SQLException {
        String page = "AddMedicinePage.jsp";
        PatientDao pdao = new PatientDao();
        BillDao bdao = new BillDao();

        ArrayList<Patient> patients = pdao.getAllPatients();
        ArrayList<Patient> patientsWithBill = new ArrayList<>();
        for (Patient patient : patients) {
            if (bdao.getPatientBill(patient.getId()) != null) {
                patientsWithBill.add(patient);
            }
        }

        request.setAttribute("PatientsWithBill", patientsWithBill);
        request.setAttribute("MedicineId", Integer.parseInt(request.getParameter("id")));
        request.setAttribute("MedicineName", request.getParameter("medicineName"));

        request.setAttribute("page", page);
    }

    public void Update(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";
        BillDao bdao = new BillDao();
                
        bdao.updateBill(Integer.parseInt(request.getParameter("fee")), Integer.parseInt(request.getParameter("patient"))); 
        
        request.setAttribute("page", page);
    }

    public void Pay(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";

        BillDao bdao = new BillDao();
        bdao.payBill(Integer.parseInt(request.getParameter("bill")));

        request.setAttribute("page", page);
    }
}
