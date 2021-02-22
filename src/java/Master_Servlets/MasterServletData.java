/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Master_Servlets;

import DBOops.MenuDAO;
import DBOops.msfDAO;
import DBOops.pdcDAO;
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

/**
 *
 * @author Madhawa_4799
 */
@WebServlet(name = "MasterServletData", urlPatterns = {"/NDBRMS/MasterServletData"})
public class MasterServletData extends HttpServlet {

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
            out.println("<title>Servlet MasterServletData</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MasterServletData at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        PrintWriter out = response.getWriter();
        String m_strParaid = request.getParameter("paramid");

        try {

            pdcDAO pdc = new pdcDAO();
            boolean system_availability = pdc.checkSystemAvailability();
            boolean day_end_process_status = pdc.checkDayEndProcess();

            data.put("paramid", request.getParameter("paramid"));
            data.put("bank_name", request.getParameter("bank_name"));
            data.put("bank_code", request.getParameter("bank_code"));
            data.put("bank_status", request.getParameter("bank_status"));
            data.put("idndb_bank_master_file", request.getParameter("idndb_bank_master_file"));

            data.put("bnkcodecmb", request.getParameter("bnkcodecmb"));
            data.put("branch_id", request.getParameter("branch_id"));
            data.put("branch_name", request.getParameter("branch_name"));
            data.put("ch_branch_status", request.getParameter("ch_branch_status"));
            data.put("idndb_branch_master_file", request.getParameter("idndb_branch_master_file"));

            data.put("industry_id", request.getParameter("industry_id"));
            data.put("industry_des", request.getParameter("industry_des"));
            data.put("industry_status", request.getParameter("industry_status"));
            data.put("idndb_industry_master_file", request.getParameter("idndb_industry_master_file"));

            data.put("geo_mrket_id", request.getParameter("geo_mrket_id"));
            data.put("geo_mrkrt_des", request.getParameter("geo_mrkrt_des"));
            data.put("ch_geomrket_status", request.getParameter("ch_geomrket_status"));
            data.put("idndb_geo_market_master_file", request.getParameter("idndb_geo_market_master_file"));

            data.put("idndb_user_levels", request.getParameter("idndb_user_levels"));
            data.put("ndb_user_level", request.getParameter("ndb_user_level"));
            data.put("ndb_user_level_status", request.getParameter("ndb_user_level_status"));

            data.put("idndb_user_master", request.getParameter("idndb_user_master"));
            data.put("ndb_user_user_id", request.getParameter("ndb_user_user_id"));
            data.put("ndb_user_name", request.getParameter("ndb_user_name"));
            data.put("ndb_user_department", request.getParameter("ndb_user_department"));
            data.put("idndb_user_levels", request.getParameter("idndb_user_levels"));
            data.put("ndb_user_status", request.getParameter("ndb_user_status"));
            data.put("ndb_user_rms", request.getParameter("ndb_user_rms"));
            data.put("ndb_user_poms", request.getParameter("ndb_user_poms"));
            data.put("ndb_user_um", request.getParameter("ndb_user_um"));

            data.put("idndb_user_forms", request.getParameter("idndb_user_forms"));
            data.put("ndb_form_name", request.getParameter("ndb_form_name"));
            data.put("ndb_form_path", request.getParameter("ndb_form_path"));
            data.put("ndb_form_main_menu_id", request.getParameter("ndb_form_main_menu_id"));
            data.put("ndb_form_oder_by", request.getParameter("ndb_form_oder_by"));
            data.put("ndb_form_status", request.getParameter("ndb_form_status"));
            data.put("ndb_user_rms", request.getParameter("ndb_user_rms"));
            data.put("ndb_user_poms", request.getParameter("ndb_user_poms"));
            data.put("ndb_user_um", request.getParameter("ndb_user_um"));

            data.put("idndb_holiday_marker", request.getParameter("idndb_holiday_marker"));
            data.put("ndb_holiday", request.getParameter("ndb_holiday"));
            data.put("ndb_holiday_status", request.getParameter("ndb_holiday_status"));
            
            data.put("idndb_pdc_txn_master", request.getParameter("idndb_pdc_txn_master"));

            data.put("user_id", session.getAttribute("userid").toString());
            msfDAO msfDAO = new msfDAO();
            MenuDAO menuDAO = new MenuDAO();

            if (m_strParaid.equalsIgnoreCase("msrt_save_bank_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    String ser_result = msfDAO.saveBankData(data);
                    if (ser_result.equals("1100")) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else if (ser_result.equals("1101")) {
                        result.put("alreadyexdata", "AllReady Exist Data");
                        out.print(result);
                    } else if (ser_result.equals("1000")) {
                        result.put("error", "accessing error");
                        out.print(result);

                    } else {
                        throw new Exception("accessing error");
                    }
                }

            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_branch_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    String ser_result = msfDAO.saveBranchData(data);
                    if (ser_result.equals("1100")) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else if (ser_result.equals("1101")) {
                        result.put("alreadyexdata", "AllReady Exist Data");
                        out.print(result);
                    } else if (ser_result.equals("1000")) {
                        result.put("error", "accessing error");
                        out.print(result);

                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_holiday_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    String ser_result = msfDAO.saveHolidayData(data);

                    if (ser_result.equals("1100")) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else if (ser_result.equals("1101")) {
                        result.put("alreadyexdata", "AllReady Exist Data");
                        out.print(result);
                    } else if (ser_result.equals("1102")) {
                        result.put("backdatedata", "Holiday is back date Data");
                        out.print(result);
                    } else if (ser_result.equals("1103")) {
                        result.put("holidayToday", "Holiday is back date Data");
                        out.print(result);
                    } else if (ser_result.equals("1104")) {
                        result.put("holidayWeekend", "Holiday is Weekend");
                        out.print(result);
                    } else if (ser_result.equals("1000")) {
                        result.put("error", "accessing error");
                        out.print(result);

                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_form_data")) {
                if (menuDAO.creatForm(data)) {
                    result.put("success", "Successfully Saved");
                    out.print(result);
                } else {
                    throw new Exception("accessing error");
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_user_levels_data")) {
                if (menuDAO.creatUserLevel(data)) {
                    result.put("success", "Successfully Saved");
                    out.print(result);
                } else {
                    throw new Exception("accessing error");
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_indsrt_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {

                    String ser_result = msfDAO.saveIndsrtyData(data);
                    if (ser_result.equals("1100")) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else if (ser_result.equals("1101")) {
                        result.put("alreadyexdata", "AllReady Exist Data");
                        out.print(result);
                    } else if (ser_result.equals("1000")) {
                        result.put("error", "accessing error");
                        out.print(result);

                    } else {
                        throw new Exception("accessing error");
                    }
                }

            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_geomrket_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {

                    String ser_result = msfDAO.saveGeoMrketData(data);
                    if (ser_result.equals("1100")) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else if (ser_result.equals("1101")) {
                        result.put("alreadyexdata", "AllReady Exist Data");
                        out.print(result);
                    } else if (ser_result.equals("1000")) {
                        result.put("error", "accessing error");
                        out.print(result);

                    } else {
                        throw new Exception("accessing error");
                    }
                }

            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_user_data")) {
                if (menuDAO.creatUser(result)) {
                    result.put("success", "Successfully Saved");
                    out.print(result);
                } else {
                    throw new Exception("accessing error");
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_bank_auth_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {

                    if (msfDAO.saveAuthBankData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_bank_reject_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    if (msfDAO.saveRejectBankData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_holiday_auth_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    if (msfDAO.saveAuthHolidayData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_holiday_reject_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    if (msfDAO.saveRejectHolidayData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_branch_auth_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    if (msfDAO.saveAuthBranchData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_branch_reject_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    if (msfDAO.saveRejectBranchData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_chq_verify_data")) {

                String ser_result = msfDAO.savepdcChequeVerification(data);
                if (ser_result.equals("1100")) {
                    result.put("success", "Cheque verified successfully...");
                    out.print(result);
                } else if (ser_result.equals("1101")) {
                    result.put("alreadyexdata", "Cheque already verified..");
                    out.print(result);
                } else if (ser_result.equals("1000")) {
                    result.put("error", "Error occured in accessing database for cheque verification..");
                    out.print(result);

                } else {
                    throw new Exception("accessing error");
                }

            }

        } catch (Exception exception) {
            try {
                result.put("error", exception.getLocalizedMessage());
            } catch (JSONException ex) {

                Logger.getLogger(MasterServletData.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("pages/pdc_txns/modalsError.jsp?desigURL=ndb_pdc_fileupld.jsp&message=" + exception.getLocalizedMessage() + "");
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
