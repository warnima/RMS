/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Report_servelets;

import Data_Retrive_Servlets.CustomerDataRetrive;
import static Report_servelets.report_data_extract.log;
import Reports.ReportDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Madhawa_4799
 */
@WebServlet(name = "frReport", urlPatterns = {"/frReport"})
public class frReport extends HttpServlet {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(frReport.class.getName());

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
        HttpSession session = request.getSession(false);

        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray jsArr = null;
        PrintWriter out = response.getWriter();

        ReportDAO ReportDAO = new ReportDAO();
        try {
            if (session == null) {
                throw new Exception("Session expired ! Please relog-in to the system");
            }
            String paramid = request.getParameter("paramid");

            if (paramid.equalsIgnoreCase("get_pdc_rf_rurned_anal_bw")) {
                log.info("get_pdc_rf_rurned_anal_bw servlet request...");

                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("idndb_customer_define_buyer_id", request.getParameter("idndb_customer_define_buyer_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));

                jsArr = ReportDAO.get_pdc_rf_rurned_anal_bw(data);
                out.print(jsArr);
                log.info("get_pdc_rf_rurned_anal_bw servlet responded...");

            }

        } catch (Exception exception) {
            log.error(exception);
            try {
                result.put("error", exception.getLocalizedMessage());
            } catch (JSONException ex) {
                Logger.getLogger(CustomerDataRetrive.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("pages/pdc_txns/modalsError.jsp?desigURL=ndb_pdc_fileupld.jsp&message=" + exception.getLocalizedMessage() + "");
        }
        log.info("get_pdc_rf_rurned_anal_bw out.flush called...");
        out.flush();

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
        processRequest(request, response);
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
