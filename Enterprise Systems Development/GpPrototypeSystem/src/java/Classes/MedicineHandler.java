package Classes;

import DatabaseAccessObjects.MedicineDao;
import Models.Medicine;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;

public class MedicineHandler {

    public void Add(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";

        // Set up the data access objects and models.
        MedicineDao mdao = new MedicineDao();
        Medicine medicine = new Medicine();

        // Add the relevant information to the model from the request.
        medicine.setName(request.getParameter("name"));
        medicine.setCost(Integer.parseInt(request.getParameter("cost")));

        // Pass the model to the data access object to be added to the database.
        mdao.addMedicine(medicine);

        request.setAttribute("page", page);
    }

    public void Create(HttpServletRequest request) throws SQLException {
        String page = "MedicinePage.jsp";

        // Redirect to the medicine page so that the user can create and add a medicine to the database.
        request.setAttribute("Action", "Add");
        request.setAttribute("page", page);
    }

    public void Delete(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";

        // Set up the data access object.
        MedicineDao mdao = new MedicineDao();

        // Delete the relevant medicine from the database.
        mdao.deleteMedicine(Integer.parseInt(request.getParameter("id")));

        request.setAttribute("page", page);
    }

    public void Update(HttpServletRequest request) throws SQLException {
        String page = "HomePage.jsp";

        // Set up the data access objects and models.
        MedicineDao mdao = new MedicineDao();
        Medicine medicine = new Medicine();

        // Add the relevant information to the model from the request.
        medicine.setId(Integer.parseInt(request.getParameter("id")));
        medicine.setName(request.getParameter("name"));
        medicine.setCost(Integer.parseInt(request.getParameter("cost")));

        // Update the relevant medicine with the information provided.
        mdao.updateMedicine(medicine);

        request.setAttribute("page", page);
    }

    public void View(HttpServletRequest request) throws SQLException {
        String page = "MedicinePage.jsp";

        // Set up the data access object.
        MedicineDao mdao = new MedicineDao();

        // Get the relevant information from the database to show in the view medicine page.
        request.setAttribute("Action", "Update");
        request.setAttribute("Medicine", mdao.getMedicine(Integer.parseInt(request.getParameter("id"))));

        request.setAttribute("page", page);
    }
}
