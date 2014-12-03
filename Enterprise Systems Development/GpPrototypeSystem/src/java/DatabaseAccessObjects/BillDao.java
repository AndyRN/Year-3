package DatabaseAccessObjects;

import Helpers.DatabaseManager;
import Models.Bill;
import Models.BillMedicine;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillDao {

    private final DatabaseManager dbm = new DatabaseManager();

    public Bill getPatientBill(int id) throws SQLException {
        Bill invoice = null;

        ResultSet billSet = dbm.getResultSet("SELECT * from bills WHERE patient_id = " + id + " AND paid = false");
        if (billSet != null) {
            while (billSet.next()) {
                invoice = new Bill();

                invoice.setId(billSet.getInt("id"));
                invoice.setPatientId(billSet.getInt("patient_id"));
                invoice.setConsultationFee(billSet.getInt("consultation_fee"));
                invoice.setPaid(billSet.getBoolean("paid"));
            }
            dbm.disconnectFromDB();
        }

        return invoice;
    }

    public void addBill(Bill bill) throws SQLException {
        PreparedStatement pState = dbm.getPreparedStatement("INSERT INTO bills (patient_id, consultation_fee) VALUES (?, ?)");

        pState.setInt(1, bill.getPatientId());
        pState.setInt(2, bill.getConsultationFee());

        execute(pState);
    }

    public void addBillMedicine(BillMedicine billMedicine) throws SQLException {
        PreparedStatement pState = dbm.getPreparedStatement("INSERT INTO bills_medicines (bill_id, medicine_id, quantity) VALUES (?, ?, ?)");

        pState.setInt(1, billMedicine.getBillId());
        pState.setInt(2, billMedicine.getMedicineId());
        pState.setInt(3, billMedicine.getQuantity());

        execute(pState);
    }
    
    public ArrayList<BillMedicine> getPatientBillMedicines(int id) throws SQLException {
        ArrayList<BillMedicine> billMedicineList = new ArrayList<>();
        
        ResultSet billMedicineSet = dbm.getResultSet("SELECT * from bills_medicines WHERE bill_id = " + id);
        if (billMedicineSet != null) {
            while (billMedicineSet.next()) {
                BillMedicine billMedicine = new BillMedicine();
                billMedicine.setBillId(billMedicineSet.getInt("bill_id"));
                billMedicine.setMedicineId(billMedicineSet.getInt("medicine_id"));                
                billMedicine.setQuantity(billMedicineSet.getInt("quantity"));
                billMedicineList.add(billMedicine);
            }
            dbm.disconnectFromDB();
        }
        
        return billMedicineList;
    }
    
    public void updateBill(int fee, int patient) throws SQLException {        
        PreparedStatement pState = dbm.getPreparedStatement("UPDATE bills SET consultation_fee = ? WHERE patient_id = ?");

        pState.setInt(1, fee);
        pState.setInt(2, patient);

        execute(pState);
    }

    public void payBill(int billId) throws SQLException {
        PreparedStatement pState = dbm.getPreparedStatement("UPDATE bills SET paid = ? WHERE id = ?");

        pState.setBoolean(1, true);
        pState.setInt(2, billId);

        execute(pState);
    }

    private void execute(PreparedStatement pState) throws SQLException {
        pState.executeUpdate();
        dbm.disconnectFromDB();
    }
}
