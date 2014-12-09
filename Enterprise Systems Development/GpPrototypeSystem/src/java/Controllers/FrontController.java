package Controllers;

import Classes.AccountHandler;
import Classes.BillHandler;
import Classes.HomeHandler;
import Classes.MedicineHandler;
import Classes.PatientHandler;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Layout.jsp");
        String direction = request.getParameter("direction");
        String action = request.getParameter("action");

        // Default location is the home page.
        request.setAttribute("page", "HomePage.jsp");

        // Determine where we need to go using the direction.
        switch (direction) {
            case "Account":
                AccountHandler accHandler = new AccountHandler();

                switch (action) {
                    case "Logout":
                        // Attempt to logout, then redirect to the login page.
                        accHandler.Logout(request);
                        dispatcher = request.getRequestDispatcher("LoginPage.jsp");
                        break;
                    default:
                        break;
                }
                break;
            case "Bill":
                BillHandler billHandler = new BillHandler();

                switch (action) {
                    case "Add":
                        // Attempt to add a bill to a patient.
                        billHandler.Add(request);
                        break;
                    case "AddToPatient":
                        // Show the "Add to Patient" page so we can add a medicine to a patient.
                        billHandler.AddToPatient(request);
                        break;
                    case "AddMedicine":
                        // Attempt to add a medicine to a patient's bill.
                        billHandler.AddMedicine(request);
                        break;
                    case "Update":
                        // Attempt to update a patient's bill.
                        billHandler.Update(request);
                        break;
                    case "Pay":
                        // Attempt to pay for a patient's bill.
                        billHandler.Pay(request);
                        break;
                    default:
                        break;
                }
                break;
            case "Medicine":
                MedicineHandler medHandler = new MedicineHandler();

                switch (action) {
                    case "Add":
                        // Attempt to add a medicine.
                        medHandler.Add(request);
                        break;
                    case "Create":
                        // Show the "Create Medicine" page so we can fill in the medicine's details.
                        medHandler.Create(request);
                        break;
                    case "Delete":
                        // Attempt to delete a medicine.
                        medHandler.Delete(request);
                        break;
                    case "Update":
                        // Attempt to update a medicine.
                        medHandler.Update(request);
                        break;
                    case "View":
                        // Show the medicine's page with details.
                        medHandler.View(request);
                        break;
                    default:
                        break;
                }
                break;
            case "Patient":
                PatientHandler patHandler = new PatientHandler();

                switch (action) {
                    case "Add":
                        // Attempt to add a patient.
                        patHandler.Add(request);
                        break;
                    case "Create":
                        // Show the "Create Patient" page so we can fill in the patient's details.
                        patHandler.Create(request);
                        break;
                    case "Delete":
                        // Attempt to delete a patient.
                        patHandler.Delete(request);
                        break;
                    case "Update":
                        // Attempt to update a patient.
                        patHandler.Update(request);
                        break;
                    case "View":
                        // Show the patient's page with details.
                        patHandler.View(request);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

        // Load all patient and medicine data from the database.
        HomeHandler homeHandler = new HomeHandler();
        homeHandler.PrepareData(request);
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
