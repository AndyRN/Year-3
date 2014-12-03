package Classes;

import DatabaseAccessObjects.MedicineDao;
import Models.Medicine;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Andy
 */
public class MedicineHandler {

    public void Add(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";
        MedicineDao mdao = new MedicineDao();
        Medicine medicine = new Medicine();

        medicine.setName(request.getParameter("name"));
        medicine.setCost(Integer.parseInt(request.getParameter("cost")));
        mdao.addMedicine(medicine);

        request.setAttribute("page", page);
    }

    public void Create(HttpServletRequest request) throws SQLException {
        String page = "MedicinePage.jsp";

        request.setAttribute("Action", "Add");
        request.setAttribute("page", page);
    }

    public void Delete(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";
        MedicineDao mdao = new MedicineDao();

        mdao.deleteMedicine(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("page", page);
    }

    public void Update(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";
        MedicineDao mdao = new MedicineDao();
        Medicine medicine = new Medicine();

        medicine.setId(Integer.parseInt(request.getParameter("id")));
        medicine.setName(request.getParameter("name"));
        medicine.setCost(Integer.parseInt(request.getParameter("cost")));
        mdao.updateMedicine(medicine);

        request.setAttribute("page", page);
    }

    public void View(HttpServletRequest request) throws SQLException {
        String page = "MedicinePage.jsp";
        MedicineDao mdao = new MedicineDao();

        request.setAttribute("Action", "Update");
        request.setAttribute("Medicine", mdao.getMedicine(Integer.parseInt(request.getParameter("id"))));
        
        request.setAttribute("page", page);
    }
}
