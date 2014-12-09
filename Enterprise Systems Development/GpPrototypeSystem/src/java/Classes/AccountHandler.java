package Classes;

import Helpers.SessionHelper;
import javax.servlet.http.HttpServletRequest;

public class AccountHandler {

    public int Login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // If the username and password match, then attempt to login- creating a session.
        if (username.equals("doctor") && password.equals("doctor")) {
            SessionHelper.login(request, username);
            return 1;
        } else {
            return 0;
        }
    }

    public void Logout(HttpServletRequest request) {
        SessionHelper.logout(request);
    }
}
