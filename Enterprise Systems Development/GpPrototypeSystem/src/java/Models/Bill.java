package Models;

import java.util.ArrayList;

public class Bill {

    private int id;
    private int patientId;
    private int consultationFee;
    private boolean paid;
    private ArrayList<BillMedicine> medicines = new ArrayList<>();

    public Bill() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(int consultationFee) {
        this.consultationFee = consultationFee;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public ArrayList<BillMedicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(ArrayList<BillMedicine> medicines) {
        this.medicines = medicines;
    }
}
