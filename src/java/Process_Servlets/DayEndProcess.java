/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process_Servlets;

import DBOops.proccessDAO;
import Master_Servlets.MasterServletData;
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
import org.json.JSONException;
import org.json.JSONObject;
import security.db_backup;

/**
 *
 * @author Madhawa_4799
 */
@WebServlet(name = "DayEndProcess", urlPatterns = {"/NDBRMS/DayEndProcess"})
public class DayEndProcess extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String m_strParaid = request.getParameter("paramid");

        try {
            if (session == null) {
                throw new Exception("Session expired ! Please relog-in to the system");
            }
            String user_id = session.getAttribute("userid").toString();

            proccessDAO proccessDAO = new proccessDAO();

            if (m_strParaid.equalsIgnoreCase("mstr_run_day_end_process")) {

                boolean db_backup_process = true;
                boolean db_backup_process_after_day_end = true;

                db_backup_process = new db_backup().runBeforeDEPDBBACKUP();

                if (!db_backup_process) {
                    result.put("errordbbackup", "Error occured in day end process,Before dayend dtabase backup processing error!");
                    out.print(result);

                } else {
                    String res_out = proccessDAO.dayEndProccess(user_id);

                    if (res_out.equals("1111")) {
                        db_backup_process_after_day_end = new db_backup().runafterDEPDBBACKUP();
                        if (!db_backup_process_after_day_end) {
                            result.put("errordbbackup", "Error occured in day end process,After day end database backup processing error!");
                            out.print(result);

                        } else {
                            result.put("success", "Successfully Saved");
                            out.print(result);
                        }

                    } else if (res_out.equals("0011")) {
                        result.put("erroruntr", "Day end process cant be processed. UN-Authorized transactions are found.");
                        out.print(result);
                    } else if (res_out.equals("0022")) {
                        result.put("errorretleq", "Day end process cannot be run until return liquidation process completed.");
                        out.print(result);
                    } else {
                        result.put("error", "error occured");
                        out.print(result);
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("mstr_run_rtn_liqd_process")) {
                boolean db_backup_process = true;
                db_backup_process = new db_backup().runBeforeELPDBBACKUP();
                if (!db_backup_process) {
                    result.put("errordbbackup", "Error occured Return ,Adional day and lequdation process,Database backup processing error!");
                    out.print(result);

                } else {
                    String res_out_aadd_day = proccessDAO.runAdditionalProccess();
                    if (res_out_aadd_day.equals("1111")) {
                        if (proccessDAO.runReturnChequeProccess(user_id).equals("1111")) {
                            if (proccessDAO.runHolidayMarkedProccess(user_id).equals("1111")) {
                                if (proccessDAO.runFixedChargedBaseCommisionProccess(user_id).equals("1111")) {
                                    result.put("success", "Successfully Saved");
                                    out.print(result);

                                } else {
                                    result.put("errorfcbc", "Additional day,return cheques updating process and holiday checking process completed. Error occured in processing fixed charg base commision setup  process");
                                    out.print(result);
                                }

                            } else {
                                result.put("errorhm", "Additional daya and return cheques updating process completed. Error occured in holiday data overriding process.");
                                out.print(result);

                            }
                        } else {

                            result.put("errorret", "Additional day process completed .Error occured in return cheque returning process !");
                            out.print(result);

                        }
                    } else if (res_out_aadd_day.equals("0022")) {
                        result.put("errorret", "Return and Additional day Liquidation process cannot be run until Additional day and Return files upload process completed");
                        out.print(result);
                    } else if (res_out_aadd_day.equals("0023")) {
                        result.put("erroradd", "Return and Additional day Liquidation process cannot be run until Additional day and Return files upload process completed");
                        out.print(result);
                    } else if (res_out_aadd_day.equals("0024")) {
                        result.put("erroradd", "Return ,Additional day and liquidation process already completed. please proceed with day end process.");
                        out.print(result);
                    } else if (res_out_aadd_day.equals("0011")) {
                        result.put("erroruath", "Return ,Adional day and lequdation process cant be processed. UN-Authorized records are found.");
                        out.print(result);
                    } else {
                        result.put("errorad", "Error occured Return ,Adional day and lequdation process !");
                        out.print(result);

                    }
                }
            }

        } catch (Exception exception) {
            try {
                result.put("errorad", exception.getLocalizedMessage());
            } catch (JSONException ex) {

                Logger.getLogger(MasterServletData.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.println(result);

        }
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
