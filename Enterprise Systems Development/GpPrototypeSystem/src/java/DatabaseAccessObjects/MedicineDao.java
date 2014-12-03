package DatabaseAccessObjects;

import Helpers.DatabaseManager;
import Models.Medicine;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Andy
 */
public class MedicineDao {

    private final DatabaseManager dbm = new DatabaseManager();

    public Medicine getMedicine(int id) throws SQLException {
        Medicine medicine = null;

        ResultSet medicineSet = dbm.getResultSet("SELECT * from medicine WHERE id = " + id);
        if (medicineSet != null) {
            while (medicineSet.next()) {
                medicine = new Medicine();
                medicine.setId(medicineSet.getInt("id"));
                medicine.setName(medicineSet.getString("name"));
                medicine.setCost(medicineSet.getInt("cost"));
            }
            dbm.disconnectFromDB();
        }

        return medicine;
    }

    public ArrayList<Medicine> getAllMedicine() throws SQLException {
        ArrayList<Medicine> medicineList = new ArrayList<>();

        ResultSet medicineSet = dbm.getResultSet("SELECT * from medicine");
        if (medicineSet != null) {
            while (medicineSet.next()) {
                Medicine medicine = new Medicine();
                medicine.setId(medicineSet.getInt("id"));
                medicine.setName(medicineSet.getString("name"));
                medicine.setCost(medicineSet.getInt("cost"));
                medicineList.add(medicine);
            }
            dbm.disconnectFromDB();
        }

        return medicineList;
    }

    public void addMedicine(Medicine medicine) throws SQLException {
        PreparedStatement pState = dbm.getPreparedStatement("INSERT INTO medicine (name, cost) values (?, ?)");

        pState.setString(1, medicine.getName());
        pState.setInt(2, medicine.getCost());

        execute(pState);
    }

    public void updateMedicine(Medicine medicine) throws SQLException {
        PreparedStatement pState = dbm.getPreparedStatement("UPDATE medicine SET name = ?, cost = ? WHERE id = ?");

        pState.setString(1, medicine.getName());
        pState.setInt(2, medicine.getCost());
        pState.setInt(3, medicine.getId());

        execute(pState);
    }

    public void deleteMedicine(int id) throws SQLException {
        PreparedStatement pState = dbm.getPreparedStatement("DELETE FROM medicine WHERE id = ?");

        pState.setInt(1, id);

        execute(pState);
    }

    private void execute(PreparedStatement pState) throws SQLException {
        pState.executeUpdate();
        dbm.disconnectFromDB();
    }
}
