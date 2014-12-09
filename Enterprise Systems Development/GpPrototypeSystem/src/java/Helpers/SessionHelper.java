package Helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionHelper {

    public static boolean isLoggedIn(HttpServletRequest request, String username) {
        HttpSession session = request.getSession(false);

        // Checks to see if the user is logged in by looking for an attribute in the session.
        if (session != null) {
            String user = (String) session.getAttribute("user");
            if (user != null && user.equals(username)) {
                return true;
            }
        }

        return false;
    }

    public static void login(HttpServletRequest request, String username) {
        HttpSession session = request.getSession();
        
        // Adds an attribute to the session to indicate the user has logged in.
        session.setAttribute("user", username);
        
        // Sets the max inactive interval for 20 minutes, 
        // which will cause the user to be logged out and redirected if any action happens after this.
        session.setMaxInactiveInterval(20 * 60);
    }

    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        // Removes the attribute from the session which indicates the user has logged out.
        session.removeAttribute("user");
    }
}
