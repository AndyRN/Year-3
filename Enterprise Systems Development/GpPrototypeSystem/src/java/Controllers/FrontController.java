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

/**
 *
 * @author Andy
 */
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

        request.setAttribute("page", "HomePage.jsp");

        switch (direction) {
            case "Account":
                AccountHandler accHandler = new AccountHandler();

                switch (action) {
                    case "Logout":
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
                        billHandler.Add(request);
                        break;
                    case "AddToPatient":
                        billHandler.AddToPatient(request);
                        break;
                    case "AddMedicine":
                        billHandler.AddMedicine(request);
                        break;
                    case "Update":
                        billHandler.Update(request);
                        break;
                    case "Pay":
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
                        medHandler.Add(request);
                        break;
                    case "Create":
                        medHandler.Create(request);
                        break;
                    case "Delete":
                        medHandler.Delete(request);
                        break;
                    case "Update":
                        medHandler.Update(request);
                        break;
                    case "View":
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
                        patHandler.Add(request);
                        break;
                    case "Create":
                        patHandler.Create(request);
                        break;
                    case "Delete":
                        patHandler.Delete(request);
                        break;
                    case "Update":
                        patHandler.Update(request);
                        break;
                    case "View":
                        patHandler.View(request);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }

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
