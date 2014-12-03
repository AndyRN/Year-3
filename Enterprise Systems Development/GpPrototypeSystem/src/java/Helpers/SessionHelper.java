/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andy
 */
public class SessionHelper {

    public static boolean isLoggedIn(HttpServletRequest request, String username) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String user = (String) session.getAttribute("user");
            if (user != null && user.equals(username)) {
                return true;
            }
        }

        return false;
    }

    public static void login(HttpServletRequest request, String username) {
        HttpSession session = request.getSession(false);
        session.setAttribute("user", username);
        session.setMaxInactiveInterval(20 * 60);
    }

    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.removeAttribute("user");
    }
}
