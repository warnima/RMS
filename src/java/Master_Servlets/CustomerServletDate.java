/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Master_Servlets;

import DBOops.customerDAO;
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
@WebServlet(name = "CustomerServletDate", urlPatterns = {"/NDBRMS/CustomerServletDate"})
public class CustomerServletDate extends HttpServlet {

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
            out.println("<title>Servlet CustomerServletDate</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerServletDate at " + request.getContextPath() + "</h1>");
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
        pdcDAO pdc = new pdcDAO();
        boolean system_availability = pdc.checkSystemAvailability();
        boolean day_end_process_status = pdc.checkDayEndProcess();
        try {
            if (session == null) {
                throw new Exception("Session expired ! Please relog-in to the system");
            }
            if (m_strParaid.equals("cust_prod_maping_data_save")) {

                data.put("idndb_customer_define", request.getParameter("idndb_customer_define_id"));
                data.put("idndb_customer_define_id", request.getParameter("idndb_customer_define_id"));
                data.put("idndb_cust_prod_map", request.getParameter("idndb_cust_prod_map"));
                data.put("cust_as_seller", request.getParameter("cust_as_seller"));
                data.put("cust_as_buyer", request.getParameter("cust_as_buyer"));

                data.put("ch_rev_fin", request.getParameter("ch_rev_fin"));
                data.put("ch_ch_we", request.getParameter("ch_ch_we"));

                data.put("prod_rel_status", request.getParameter("prod_rel_status"));
                data.put("statusReason", request.getParameter("statusReason"));

                //rec finance------------
                data.put("rec_finance_curr", request.getParameter("rec_finance_curr").replace("'", "\\'"));
                data.put("rec_finance_limit", request.getParameter("rec_finance_limit").replace("'", "\\'"));

                //addedd for the RF Temporary Limit -- CFU-BRD-4 -- Janaka_5977 
                data.put("rf_temporary_limits", request.getParameter("rf_temporary_limits"));

                data.put("rf_buyer_accs", request.getParameter("rf_buyer_accs"));

                data.put("rec_finance_Outstanding", request.getParameter("rec_finance_Outstanding").replace("'", "\\'"));
                data.put("rec_finance_tenor", request.getParameter("rec_finance_tenor").replace("'", "\\'"));
                data.put("rec_finance_inerest_rate", request.getParameter("rec_finance_inerest_rate").replace("'", "\\'"));
                data.put("rec_finance_financing", request.getParameter("rec_finance_financing").replace("'", "\\'"));
                data.put("rec_finance_acc_num", request.getParameter("rec_finance_acc_num").replace("'", "\\'"));
                data.put("rec_finance_cr_dsc_proc_acc_num", request.getParameter("rec_finance_cr_dsc_proc_acc_num").replace("'", "\\'"));
                data.put("rec_finance_current_ac", request.getParameter("rec_finance_current_ac").replace("'", "\\'"));
                data.put("rec_finance_margin_ac", request.getParameter("rec_finance_margin_ac").replace("'", "\\'"));
                data.put("rec_finance_margin", request.getParameter("rec_finance_margin").replace("'", "\\'"));
                data.put("cust_credit_rate", request.getParameter("cust_credit_rate").replace("'", "\\'"));

                String rec_finance_bulk_credit = "NOT DEFINE";
                String ch_rec_fin_b_c_yes = request.getParameter("ch_rec_fin_b_c_yes");
                String ch_rec_fin_b_c_no = request.getParameter("ch_rec_fin_b_c_no");

                if (ch_rec_fin_b_c_yes.equals("ACTIVE")) {
                    rec_finance_bulk_credit = "BULK CREDIT ACTIVE";
                }
                if (ch_rec_fin_b_c_no.equals("ACTIVE")) {
                    rec_finance_bulk_credit = "BULK CREDIT DEACTIVE";

                }
                String rec_finance_balance_transfer = "NOT DEFINE";
                String ch_rec_fin_balance_transfer_yes = request.getParameter("ch_rec_fin_balance_transfer_yes");
                String ch_rec_fin_balance_transfer_no = request.getParameter("ch_rec_fin_balance_transfer_no");

                if (ch_rec_fin_balance_transfer_yes.equals("ACTIVE")) {
                    rec_finance_balance_transfer = "BALANCE TRANSFER ACTIVE";
                }
                if (ch_rec_fin_balance_transfer_no.equals("ACTIVE")) {
                    rec_finance_balance_transfer = "BALANCE TRANSFER DEACTIVE";

                }

                data.put("rec_finance_bulk_credit", rec_finance_bulk_credit);
                data.put("rec_finance_balance_transfer", rec_finance_balance_transfer);

                data.put("rec_finance_erly_wdr_chg", request.getParameter("rec_finance_erly_wdr_chg").replace("'", "\\'"));
                data.put("rec_finance_vale_dte_extr_chg", request.getParameter("rec_finance_vale_dte_extr_chg").replace("'", "\\'"));
                data.put("rec_finance_erly_stlemnt_chg", request.getParameter("rec_finance_erly_stlemnt_chg").replace("'", "\\'"));

                //chequw were---------------
                data.put("chq_wh_limit", request.getParameter("chq_wh_limit").replace("'", "\\'"));
                //addedd for the CW Temporary Limit -- CFU-BRD-4 -- Janaka_5977 
                data.put("cw_temporary_limits", request.getParameter("cw_temporary_limits"));
                data.put("sl_has_byr_otstaning", request.getParameter("sl_has_byr_otstaning").replace("'", "\\'"));
                data.put("sl_has_byr_tenor", request.getParameter("sl_has_byr_tenor").replace("'", "\\'"));

                String chq_wh_dr_to_cr_spe_narr = "0";
                String chq_wh_dr_to_cr_spe_narr_yes = request.getParameter("chq_wh_dr_to_cr_spe_narr_yes");
                String chq_wh_dr_to_cr_spe_narr_no = request.getParameter("chq_wh_dr_to_cr_spe_narr_no");

                if (chq_wh_dr_to_cr_spe_narr_yes.equals("1")) {
                    chq_wh_dr_to_cr_spe_narr = "1";
                }
                if (chq_wh_dr_to_cr_spe_narr_no.equals("1")) {
                    chq_wh_dr_to_cr_spe_narr = "0";

                }
                data.put("chq_wh_dr_to_cr_spe_narr", chq_wh_dr_to_cr_spe_narr);

                data.put("chq_wh_erly_wdr_chg", request.getParameter("chq_wh_erly_wdr_chg").replace("'", "\\'"));
                data.put("chq_wh_vale_dte_extr_chg", request.getParameter("chq_wh_vale_dte_extr_chg").replace("'", "\\'"));
                data.put("chq_wh_erly_stlemnt_chg", request.getParameter("chq_wh_erly_stlemnt_chg").replace("'", "\\'"));
                data.put("user_id", session.getAttribute("userid").toString());

            }
            if (m_strParaid.equals("msrt_save_cust_define_data")) {
                data.put("paramid", request.getParameter("paramid"));
                data.put("idndb_customer_define", request.getParameter("idndb_customer_define"));
                data.put("cust_id", request.getParameter("cust_id").replace("'", "\\'"));
                data.put("cust_name", request.getParameter("cust_name").replace("'", "\\'"));
                data.put("cust_short_name", request.getParameter("cust_short_name").replace("'", "\\'"));
                data.put("cust_indutry_id", request.getParameter("cust_indutry_id"));
                data.put("cust_contct_no", request.getParameter("cust_contct_no"));
                data.put("cust_fx_number", request.getParameter("cust_fx_number"));
                data.put("cust_contct_per1", request.getParameter("cust_contct_per1"));
                data.put("cust_contct_per2", request.getParameter("cust_contct_per2"));
                data.put("cust_email", request.getParameter("cust_email").replace("'", "\\'"));
                data.put("cust_address", request.getParameter("cust_address").replace("'", "\\'"));
                data.put("cust_rec_acc_no", request.getParameter("cust_rec_acc_no"));
                data.put("cust_cre_des_pros_to_acc_no", request.getParameter("cust_cre_des_pros_to_acc_no"));
                data.put("cust_curr_ac_no", request.getParameter("cust_curr_ac_no"));
                data.put("cms_curr_acc_number", request.getParameter("cms_curr_acc_number"));
                data.put("cms_collection_acc_number", request.getParameter("cms_collection_acc_number"));
                data.put("msrt_save_cust_define_data", request.getParameter("msrt_save_cust_define_data"));
                data.put("cust_margin_ac_no", request.getParameter("cust_margin_ac_no"));
                data.put("cust_margin", request.getParameter("cust_margin"));
                data.put("cust_credt_rate", request.getParameter("cust_credt_rate").replace("'", "\\'"));
                data.put("cust_bank", request.getParameter("cust_bank"));
                data.put("cust_branch", request.getParameter("cust_branch"));
                data.put("cust_other_bank_ac_no", request.getParameter("cust_other_bank_ac_no"));
                data.put("cust_geo_market", request.getParameter("cust_geo_market"));
                data.put("cust_ndb_customer_active", request.getParameter("cust_ndb_customer_active"));
                data.put("cust_ch_active", request.getParameter("cust_ch_active"));
                data.put("statusReason", request.getParameter("statusReason"));
                data.put("user_id", session.getAttribute("userid").toString());

            }

            if (m_strParaid.equals("save_sl_has_byr_data")) {

                data.put("paramid", request.getParameter("save_sl_has_byr_data"));
                data.put("idndb_seller_has_buyers", request.getParameter("idndb_seller_has_buyers"));
                data.put("idndb_customer_define_seller", request.getParameter("idndb_customer_define_seller"));
                data.put("idndb_customer_define_buyer", request.getParameter("idndb_customer_define_buyer"));

                data.put("sl_has_byr_prorm_type_rf", request.getParameter("sl_has_byr_prorm_type_rf"));
                data.put("sl_has_byr_prorm_type_cw", request.getParameter("sl_has_byr_prorm_type_cw"));

                data.put("sl_has_byr_fmax_chq_amu", request.getParameter("sl_has_byr_fmax_chq_amu").replace("'", "\\'"));
                data.put("sl_has_byr_cdt_loan_amu", request.getParameter("sl_has_byr_cdt_loan_amu").replace("'", "\\'"));
                data.put("sl_has_byr_otstaning", request.getParameter("sl_has_byr_otstaning").replace("'", "\\'"));
                data.put("sl_has_byr_tenor", request.getParameter("sl_has_byr_tenor").replace("'", "\\'"));
                data.put("sl_has_byr_itst_trsry", request.getParameter("sl_has_byr_itst_trsry"));

                String rec_finance_commision_crg = "NOT DEFINE";
                String chx_recfin_tran = request.getParameter("chx_recfin_tran");
                String chx_recfin_fixed = request.getParameter("chx_recfin_fixed");

                if (chx_recfin_tran.equals("ACTIVE")) {
                    rec_finance_commision_crg = "TRANSACTION BASED";
                }
                if (chx_recfin_fixed.equals("ACTIVE")) {
                    rec_finance_commision_crg = "FIXED CHARGE BASED";

                }

                data.put("rec_finance_commision_crg", rec_finance_commision_crg);
                data.put("rf_tran_base_falt_fee", request.getParameter("rf_tran_base_falt_fee").replace("'", "\\'"));
                data.put("rf_tran_base_from_tran", request.getParameter("rf_tran_base_from_tran").replace("'", "\\'"));

                data.put("rf_fixed_rate_amount", request.getParameter("rf_fixed_rate_amount").replace("'", "\\'"));

                String rf_fixed_frequency = "NOT DEFINE";
                String rf_fixed_rate_daily = request.getParameter("rf_fixed_rate_daily");
                String rf_fixed_rate_weekly = request.getParameter("rf_fixed_rate_weekly");
                String rf_fixed_rate_monthly = request.getParameter("rf_fixed_rate_monthly");
                String rf_fixed_rate_yearly = request.getParameter("rf_fixed_rate_yearly");

                if (rf_fixed_rate_daily.equals("ACTIVE")) {
                    rf_fixed_frequency = "DAILY";
                }
                if (rf_fixed_rate_weekly.equals("ACTIVE")) {
                    rf_fixed_frequency = "WEEKLY";

                }
                if (rf_fixed_rate_monthly.equals("ACTIVE")) {
                    rf_fixed_frequency = "MONTHLY";

                }
                if (rf_fixed_rate_yearly.equals("ACTIVE")) {
                    rf_fixed_frequency = "YEARLY";

                }

                data.put("rf_fixed_frequency", rf_fixed_frequency);

                data.put("sl_has_byr_advnce_rate_prstage", request.getParameter("sl_has_byr_advnce_rate_prstage"));
                data.put("sl_has_byr_remarks", request.getParameter("sl_has_byr_remarks").replace("'", "\\'"));
                data.put("sl_has_byr_active", request.getParameter("sl_has_byr_active"));
                data.put("statusReason", request.getParameter("statusReason"));

                //cw facility data
                data.put("sl_has_byr_warehs_limit", request.getParameter("sl_has_byr_warehs_limit").replace("'", "\\'"));
                data.put("sl_has_byr_warehs_otstaning", request.getParameter("sl_has_byr_warehs_otstaning").replace("'", "\\'"));
                data.put("sl_has_byr_warehs_tenor", request.getParameter("sl_has_byr_warehs_tenor").replace("'", "\\'"));
                data.put("sl_has_byr_warehs_fmax_chq_amu", request.getParameter("sl_has_byr_warehs_fmax_chq_amu").replace("'", "\\'"));

                String chq_wh_commision_crg = "NOT DEFINE";
                String chx_chqware_tran = request.getParameter("chx_chqware_tran");
                String chx_chqware_fixed = request.getParameter("chx_chqware_fixed");

                if (chx_chqware_tran.equals("ACTIVE")) {
                    chq_wh_commision_crg = "TRANSACTION BASED";
                }
                if (chx_chqware_fixed.equals("ACTIVE")) {
                    chq_wh_commision_crg = "FIXED CHARGE BASED";

                }

                data.put("chq_wh_commision_crg", chq_wh_commision_crg);

                data.put("cw_tran_base_falt_fee", request.getParameter("cw_tran_base_falt_fee"));
                data.put("cw_tran_base_from_tran", request.getParameter("cw_tran_base_from_tran"));

                data.put("cw_fixed_rate_amount", request.getParameter("cw_fixed_rate_amount"));

                String cw_fixed_frequency = "NOT DEFINE";
                String cw_fixed_rate_daily = request.getParameter("cw_fixed_rate_daily");
                String cw_fixed_rate_weekly = request.getParameter("cw_fixed_rate_weekly");
                String cw_fixed_rate_monthly = request.getParameter("cw_fixed_rate_monthly");
                String cw_fixed_rate_yearly = request.getParameter("cw_fixed_rate_yearly");

                if (cw_fixed_rate_daily.equals("ACTIVE")) {
                    cw_fixed_frequency = "DAILY";
                }
                if (cw_fixed_rate_weekly.equals("ACTIVE")) {
                    cw_fixed_frequency = "WEEKLY";

                }
                if (cw_fixed_rate_monthly.equals("ACTIVE")) {
                    cw_fixed_frequency = "MONTHLY";

                }
                if (cw_fixed_rate_yearly.equals("ACTIVE")) {
                    cw_fixed_frequency = "YEARLY";

                }

                data.put("cw_fixed_frequency", cw_fixed_frequency);

                data.put("user_id", session.getAttribute("userid").toString());

            }

            if (m_strParaid.equals("msrt_save_cust_define_auth_data")) {
                data.put("paramid", request.getParameter("paramid"));
                data.put("idndb_customer_define", request.getParameter("idndb_customer_define"));
                data.put("user_id", session.getAttribute("userid").toString());

            }

            if (m_strParaid.equals("msrt_save_cust_define_reject_data")) {
                data.put("paramid", request.getParameter("paramid"));
                data.put("idndb_customer_define", request.getParameter("idndb_customer_define"));
                data.put("user_id", session.getAttribute("userid").toString());

            }

            if (m_strParaid.equals("cust_prod_maping_auth_data_save")) {
                data.put("paramid", request.getParameter("paramid"));
                data.put("idndb_cust_prod_map", request.getParameter("idndb_cust_prod_map"));
                data.put("user_id", session.getAttribute("userid").toString());

            }

            if (m_strParaid.equals("cust_prod_maping_areject_data_save")) {
                data.put("paramid", request.getParameter("paramid"));
                data.put("idndb_cust_prod_map", request.getParameter("idndb_cust_prod_map"));
                data.put("user_id", session.getAttribute("userid").toString());

            }

            if (m_strParaid.equals("sl_has_byr_auth_data_save")) {
                data.put("paramid", request.getParameter("paramid"));
                data.put("idndb_seller_has_buyers", request.getParameter("idndb_seller_has_buyers"));
                data.put("user_id", session.getAttribute("userid").toString());

            }

            if (m_strParaid.equals("sl_has_byr_reject_data_save")) {
                data.put("paramid", request.getParameter("paramid"));
                data.put("idndb_seller_has_buyers", request.getParameter("idndb_seller_has_buyers"));
                data.put("user_id", session.getAttribute("userid").toString());

            }

            customerDAO custDAO = new customerDAO();

            if (m_strParaid.equalsIgnoreCase("msrt_save_cust_define_data")) {

                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    String ser_result = custDAO.saveCustomerDifineData(data);
                    if (ser_result.equals("1100")) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else if (ser_result.equals("1101")) {
                        result.put("alreadyexdata", "AllReady Exist Data");
                        out.print(result);
                    } else if (ser_result.equals("1104")) {
                        result.put("cannotamendunauthdat", "Error occurred in saving customer define data. Customer define date cannot be amend, because this customer related un-authorized customer product mapping data available! ");
                        out.print(result);
                    } else if (ser_result.equals("1105")) {
                        result.put("cannotamendunauthdat", "Error occurred in saving customer define data. Customer define date cannot be amend, because this customer related un-authorized relationship establishment data available! ");
                        out.print(result);
                    } else if (ser_result.equals("1106")) {
                        result.put("cannotamendunauthdat", "Error occurred in saving customer define data. Customer define date cannot be amend, because this customer related un-authorized PDC transaction data available! ");
                        out.print(result);
                    } else if (ser_result.equals("1000")) {
                        result.put("error", "accessing error");
                        out.print(result);

                    } else {
                        throw new Exception("accessing error");
                    }
                }

            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_cust_define_auth_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    if (custDAO.saveAuthCustomerDifineData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("msrt_save_cust_define_reject_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    if (custDAO.saveRejectedCustomerDifineData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("cust_prod_maping_auth_data_save")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    if (custDAO.saveAuthCustProdMappingData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("cust_prod_maping_areject_data_save")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    if (custDAO.saveRegectedCustProdMappingData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("sl_has_byr_auth_data_save")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    if (custDAO.saveAuthSllerHasBuyerData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("sl_has_byr_reject_data_save")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    if (custDAO.saveRejectSllerHasBuyerData(data)) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }
            }

            if (m_strParaid.equalsIgnoreCase("cust_prod_maping_data_save")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {
                    String ser_result = custDAO.saveCustomerProdMapData(data);
                    if (ser_result.equals("1100")) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else if (ser_result.equals("1101")) {
                        result.put("alreadyexdatasel", "AllReady Exist Data seller");
                        out.print(result);
                    } else if (ser_result.equals("1102")) {
                        result.put("alreadyexdatabuy", "AllReady Exist Data buyer");
                        out.print(result);
                    } else if (ser_result.equals("1104")) {
                        result.put("unauthdataamend", "Error occurred in saving customer product mapping data. Customer product mapping data cannot be amend , because this product mapping  related un-authorized relationship establishment data available! ");
                        out.print(result);
                    } else if (ser_result.equals("1105")) {
                        result.put("unauthdataamend", "Error occurred in saving customer product mapping data. Customer product mapping data cannot be amend , because this product mapping  related un-authorized PDC transaction data available! ");
                        out.print(result);
                    } else if (ser_result.equals("1106")) {
                        result.put("unauthdataamend", "Error occurred in saving customer product mapping data. Customer limit reducing error, seller limit cannot be less than whole active buyer limits !");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }

            }

            if (m_strParaid.equalsIgnoreCase("save_sl_has_byr_data")) {
                if (day_end_process_status) {
                    result.put("systemna", "Dayend Process processing, currently NDB RMS system not available for data entry oparations.");
                    out.print(result);
                } else if (system_availability) {
                    result.put("systemna", "After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");
                    out.print(result);
                } else {

                    String ser_result = custDAO.saveCustomerRelationShipEstbmntData(data);
                    if (ser_result.equals("1100")) {
                        result.put("success", "Successfully Saved");
                        out.print(result);
                    } else if (ser_result.equals("1101")) {
                        result.put("alreadyexdatarf", "AllReady Exist Data RF");
                        out.print(result);
                    } else if (ser_result.equals("1102")) {
                        result.put("alreadyexdatacw", "AllReady Exist Data CW");
                        out.print(result);
                    } else if (ser_result.equals("1103")) {
                        result.put("samesellernuyer", "Error occured in saving relationship estabilishment data. The seller and buyer cannot be same customer.");
                        out.print(result);
                    } else if (ser_result.equals("1104")) {
                        result.put("samesellernuyer", "Error occurred in saving relationship establishment data. Relationship establishment data cannot be amend , because this relationship related un-authorized PDC transaction data available!");
                        out.print(result);
                    } else {
                        throw new Exception("accessing error");
                    }
                }

            }

        } catch (Exception exception) {
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
