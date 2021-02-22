/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transaction_Servelet;

import DBoperationsDBO.pdcDAO;
import Master_Servlets.CustomerServletDate;
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
@WebServlet(name = "PDCentries", urlPatterns = {"/NDBRMS/PDCentries"})
public class PDCentries extends HttpServlet {

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
            DBOops.pdcDAO pdc = new DBOops.pdcDAO();
            boolean system_availability = pdc.checkSystemAvailability();
            boolean day_end_process_status = pdc.checkDayEndProcess();

//            if (m_strParaid.equals("mstr_bulk_auth")) {
//
//                String prorm_type_rf = request.getParameter("prorm_type_rf");
//                String prorm_type_cw = request.getParameter("prorm_type_cw");
//                if (prorm_type_rf.equals("ACTIVE")) {
//                    m_strParaid = "msrt_save_pdcrf_auth_data";
//                }
//
//                if (prorm_type_cw.equals("ACTIVE")) {
//                    m_strParaid = "msrt_save_pdccw_auth_data";
//                }
//
//            }
            if (m_strParaid.equals("mstr_bulk_reject")) {

                String prorm_type_rf = request.getParameter("prorm_type_rf");
                String prorm_type_cw = request.getParameter("prorm_type_cw");
                if (prorm_type_rf.equals("ACTIVE")) {
                    m_strParaid = "msrt_save_pdcrf_reject_data";
                }

                if (prorm_type_cw.equals("ACTIVE")) {
                    m_strParaid = "msrt_save_pdccw_reject_data";
                }

            }

            if (m_strParaid.equals("save_cw_pdc_manual_entry")) {
                data.put("idndb_pdc_txn_master", request.getParameter("idndb_pdc_txn_master"));
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("idndb_customer_define_buyer_id", request.getParameter("idndb_customer_define_buyer_id"));
                data.put("cust_bank", request.getParameter("cust_bank"));
                data.put("cust_bank_code", request.getParameter("cust_bank_code"));
                data.put("cust_branch", request.getParameter("cust_branch"));
                data.put("cust_branch_code", request.getParameter("cust_branch_code"));
                data.put("cw_value_date", request.getParameter("cw_value_date"));
                data.put("cw_chq_number", request.getParameter("cw_chq_number"));
                data.put("cw_cheque_amu", request.getParameter("cw_cheque_amu"));
                data.put("chq_wh_dr_to_cr_spe_narr", request.getParameter("chq_wh_dr_to_cr_spe_narr"));
                data.put("cw_cheque_modification", request.getParameter("cw_cheque_modification"));
                data.put("message", request.getParameter("message"));
                data.put("user_id", session.getAttribute("userid").toString());

            }

            if (m_strParaid.equals("save_rf_pdc_manual_entry")) {
                data.put("idndb_pdc_txn_master", request.getParameter("idndb_pdc_txn_master"));
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("idndb_customer_define_buyer_id", request.getParameter("idndb_customer_define_buyer_id"));
                data.put("cust_bank", request.getParameter("cust_bank"));
                data.put("cust_bank_code", request.getParameter("cust_bank_code"));
                data.put("cust_branch", request.getParameter("cust_branch"));
                data.put("cust_branch_code", request.getParameter("cust_branch_code"));
                data.put("rf_value_date", request.getParameter("rf_value_date"));
                data.put("rf_chq_number", request.getParameter("rf_chq_number"));
                data.put("rf_cheque_amu", request.getParameter("rf_cheque_amu"));
                data.put("message", request.getParameter("message"));
                data.put("rf_cheque_modification", request.getParameter("rf_cheque_modification"));
                data.put("user_id", session.getAttribute("userid").toString());

            }

            if (m_strParaid.equals("save_cw_to_rf_pdc_conversion_entry")) {
                data.put("idndb_pdc_txn_master", request.getParameter("idndb_pdc_txn_master"));
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("idndb_customer_define_buyer_id", request.getParameter("idndb_customer_define_buyer_id"));
                data.put("cust_bank", request.getParameter("cust_bank"));
                data.put("cust_bank_code", request.getParameter("cust_bank_code"));
                data.put("cust_branch", request.getParameter("cust_branch"));
                data.put("cust_branch_code", request.getParameter("cust_branch_code"));
                data.put("rf_value_date", request.getParameter("rf_value_date"));
                data.put("rf_chq_number", request.getParameter("rf_chq_number"));
                data.put("rf_cheque_amu", request.getParameter("rf_cheque_amu"));
                data.put("message", request.getParameter("message"));
                data.put("user_id", session.getAttribute("userid").toString());

            }

            pdcDAO pdcDAO = new pdcDAO();

            if (m_strParaid.equalsIgnoreCase("save_cw_pdc_manual_entry")) {

                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    String out_put_resut_str = pdcDAO.saveCWPDCEntry(data);
                    String[] output_result = out_put_resut_str.split("/");

                    if (output_result[0].equals("1100")) {
                        result.put("success", "Successfully Saved/" + output_result[1]);
                        out.print(result);
                    } else if (output_result[0].equals("1101")) {
                        result.put("cn_error", "Cheque allready exist in the CW product. You can amend the cheque on PDC liquidations & Extentsions funtion !/" + output_result[1]);
                        out.print(result);
                    } else if (output_result[0].equals("1102")) {
                        result.put("cn_error", "Invalid valu date extention process. Extention value date cannot be after given value date !/" + output_result[1]);
                        out.print(result);
                    } else if (output_result[0].equals("1103")) {
                        result.put("cn_error", "Valu date extention process and early lequdation process canot be done once. !/" + output_result[1]);
                        out.print(result);
                    } else if (output_result[0].equals("1104")) {
                        result.put("cn_error", "CW Transactions cannot be processed. Un-authorized holiday marked data is available in the system. !/" + output_result[1]);
                        out.print(result);
                    } else if (output_result[0].equals("1105")) {
                        result.put("cn_error", "CW Transactions cannot be processed. Bank code and branch code missing in the input. !/" + output_result[1]);
                        out.print(result);
                    } else if (output_result[0].equals("0000")) {
                        result.put("error", "Error Ocurred in saving CW PDC Data!/" + output_result[1]);
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("save_rf_pdc_manual_entry")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    String out_put_resut_str = pdcDAO.saveRFPDCEntry(data);
                    String[] output_result = out_put_resut_str.split("/");

                    if (output_result[0].equals("1100")) {
                        result.put("success", "Successfully Saved/" + output_result[1]);
                        out.print(result);
                    } else if (output_result[0].equals("1101")) {
                        result.put("cn_error", "Cheque allready exist in the RF product. You can amend the cheque on PDC liquidations & Extentsions funtion !  !/" + output_result[1]);
                        out.print(result);
                    } else if (output_result[0].equals("1102")) {
                        result.put("cn_error", "Invalid valu date extention process. Extention value date cannot be after given value date !/" + output_result[1]);
                        out.print(result);
                    } else if (output_result[0].equals("1103")) {
                        result.put("cn_error", "Valu date extention process and early lequdation process canot be done once. !/" + output_result[1]);
                        out.print(result);
                    } else if (output_result[0].equals("1104")) {
                        result.put("cn_error", "RF Transactions cannot be processed. Un-authorized holiday marked data is available in the system. !/" + output_result[1]);
                        out.print(result);
                    } else if (output_result[0].equals("1105")) {
                        result.put("cn_error", "RF Transactions cannot be processed. Bank code and branch code missing in the input. !/" + output_result[1]);
                        out.print(result);
                    }  else if (output_result[0].equals("0000")) {
                        result.put("error", "Error Ocurred in saving CW PDC Data!/" + output_result[1]);
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }
            if (m_strParaid.equalsIgnoreCase("save_cw_to_rf_pdc_conversion_entry")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    String output_result = pdcDAO.saveCWTORFPDCEntry(data);

                    if (output_result.equals("1100")) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else if (output_result.equals("1101")) {
                        result.put("cn_error", "Cheque Number Allready exist !");
                        out.print(result);
                    } else if (output_result.equals("1104")) {
                        result.put("cn_error", "CW To RF Transactions conversion cannot be processed. Un-authorized holiday marked data is available in the system. !/");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("pdcChequesAuthorization")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    data.put("idndb_pdc_txn_master", request.getParameter("idndb_pdc_txn_master"));

                    data.put("user_id", session.getAttribute("userid").toString());
                    data.put("auth_status", "AUTHORIZED");
                    if (pdcDAO.pdcChequesAuthorization(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_pdccw_reject_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    data.put("idndb_pdc_txn_master", request.getParameter("idndb_pdc_txn_master"));

                    data.put("user_id", session.getAttribute("userid").toString());
                    data.put("auth_status", "REJECTED");
                    if (pdcDAO.savePDCStatusUpdateRejecetData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_pdcrf_reject_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    data.put("idndb_pdc_txn_master", request.getParameter("idndb_pdc_txn_master"));

                    data.put("user_id", session.getAttribute("userid").toString());
                    data.put("auth_status", "REJECTED");
                    if (pdcDAO.savePDCStatusUpdateRejecetData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

//            if (m_strParaid.equalsIgnoreCase("msrt_save_cust_define_auth_data")) {
//                if (custDAO.saveAuthCustomerDifineData(data)) {
//                    result.put("success", "Successfully Saved");
//                    out.print(result);
//                } else {
//                    throw new Exception("accessing error");
//                }
//            }
//
//            if (m_strParaid.equalsIgnoreCase("msrt_save_cust_define_reject_data")) {
//                if (custDAO.saveRejectedCustomerDifineData(data)) {
//                    result.put("success", "Successfully Saved");
//                    out.print(result);
//                } else {
//                    throw new Exception("accessing error");
//                }
//            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println(exception.getLocalizedMessage());
            try {
                result.put("error", exception.getLocalizedMessage());
            } catch (JSONException ex) {

                Logger.getLogger(CustomerServletDate.class.getName()).log(Level.SEVERE, null, ex);
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
