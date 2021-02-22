/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Master_Servlets;

import DBOops.MenuDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Madhawa_4799
 */
@WebServlet(name = "MenuControler", urlPatterns = {"/NDBRMS/MenuControler"})
public class MenuControler extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MenuControler</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MenuControler at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        processRequest(request, response);
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
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String m_strParaid = "";
        String m_idndb_user_levels = "";
        HttpSession session = request.getSession(false);
        try {
            Enumeration enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                String parameterName = (String) enumeration.nextElement();
                //System.out.println(parameterName);idndb_user_levels
                if (parameterName.equalsIgnoreCase("paramid")) {
                    m_strParaid = request.getParameter(parameterName);
                } else if (parameterName.equalsIgnoreCase("idndb_user_levels")) {
                    m_idndb_user_levels = request.getParameter(parameterName);

                } else {
                    data.put(parameterName, request.getParameter(parameterName));
                }

            }
            data.put("user_id", session.getAttribute("userid").toString());
            data.put("idndb_user_levels", m_idndb_user_levels);
            MenuDAO menuDAO = new MenuDAO();

            if (m_strParaid.equalsIgnoreCase("saveroleforms")) {
                if (menuDAO.saveRoleForms(data)) {
                    result.put("success", "Successfully Saved");
                    out.print(result);
                } else {
                    throw new Exception("accessing error");
                }
            }

        } catch (Exception exception) {
            try {
                result.put("error", exception.getLocalizedMessage());
            } catch (JSONException ex) {
                Logger.getLogger(MenuControler.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println(result);
        }
        out.flush();
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
