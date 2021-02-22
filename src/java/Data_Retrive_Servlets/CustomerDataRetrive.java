/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Retrive_Servlets;

import DBoperationsDBO.customerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Madhawa_4799
 */
@WebServlet(name = "CustomerDataRetrive", urlPatterns = {"/NDBRMS/CustomerDataRetrive"})
public class CustomerDataRetrive extends HttpServlet {

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
        customerDAO custDAO = new customerDAO();
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray jsArr = null;
        PrintWriter out = null;

        try {
            String paramid = request.getParameter("paramid");
            if (paramid.equalsIgnoreCase("idndb_customer_define_data_read")) {
                out = response.getWriter();
                String idndb_customer_define = request.getParameter("idndb_customer_define");
                jsArr = custDAO.getCustomerDifineData(idndb_customer_define);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_bank_brnch_data")) {
                out = response.getWriter();
                String idndb_bank_master_file = request.getParameter("bnkcodecmb_bank_id");
                jsArr = custDAO.getBankDifineBranchData(idndb_bank_master_file);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_sellers_buyer_data")) {
                out = response.getWriter();
                String idndb_cust_prod_map = request.getParameter("idndb_cust_prod_map");
                jsArr = custDAO.getSellerDifineBuyerData(idndb_cust_prod_map);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_rf_sellers")) {
                out = response.getWriter();
                String prm_programe_type = request.getParameter("programe_type");
                jsArr = custDAO.getSellersData(prm_programe_type);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_sellers_buyer_data_for_rms")) {
                out = response.getWriter();
                String idndb_cust_prod_map = request.getParameter("idndb_cust_prod_map");
                jsArr = custDAO.getSellerDifineBuyerDataRMS(idndb_cust_prod_map);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_sellers_buyer_data_for_rms_all")) {
                out = response.getWriter();
                String idndb_cust_prod_map = request.getParameter("idndb_cust_prod_map");
                jsArr = custDAO.getSellerDifineBuyerDataRMSAll(idndb_cust_prod_map);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_sellers_rms_sellers")) {
                out = response.getWriter();
                jsArr = custDAO.getRMSSeller();
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_customer_products")) {
                out = response.getWriter();
                String idndb_cust_prod_map = request.getParameter("idndb_cust_prod_map");
                jsArr = custDAO.getSellerProdData(idndb_cust_prod_map);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("getUnAuthTxnsSellerDetails")) {
                out = response.getWriter();
                data.put("pdc_product", request.getParameter("pdc_product"));
                data.put("pdc_manual_bulk", request.getParameter("pdc_manual_bulk"));
                jsArr = custDAO.getUnAuthTxnsSellerDetails(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_buyer_rel_data")) {
                out = response.getWriter();
                String idndb_seller_has_buyers = request.getParameter("idndb_seller_has_buyers");
                String cw_cheque_amu = request.getParameter("cw_cheque_amu");
                String cust_bank_code = request.getParameter("cust_bank_code");
                String cust_branch_code = request.getParameter("cust_branch_code");
                String idndb_pdc_txn_master = request.getParameter("idndb_pdc_txn_master");
                jsArr = custDAO.getBuyerRelData(idndb_seller_has_buyers, cw_cheque_amu, cust_bank_code, cust_branch_code, idndb_pdc_txn_master);
                out.print(jsArr);
            }
            if (paramid.equalsIgnoreCase("get_buyer_rel_data_rms")) {
                out = response.getWriter();
                String idndb_seller_has_buyers = request.getParameter("idndb_seller_has_buyers");
                String rf_cheque_amu = request.getParameter("rf_cheque_amu");
                String cust_bank_code = request.getParameter("cust_bank_code");
                String cust_branch_code = request.getParameter("cust_branch_code");
                String idndb_pdc_txn_master = request.getParameter("idndb_pdc_txn_master");
                jsArr = custDAO.getBuyerRelDataRMS(idndb_seller_has_buyers, rf_cheque_amu, cust_bank_code, cust_branch_code, idndb_pdc_txn_master);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_buyer_rel_data_cw_to_rf")) {
                out = response.getWriter();
                String idndb_seller_has_buyers = request.getParameter("idndb_seller_has_buyers");
                String cust_bank_code = request.getParameter("cust_bank_code");
                String cust_branch_code = request.getParameter("cust_branch_code");
                String rf_cheque_amu = request.getParameter("rf_cheque_amu");
                jsArr = custDAO.getBuyerRelDataRMScw_to_rf(idndb_seller_has_buyers, cust_bank_code, cust_branch_code, rf_cheque_amu);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_bank_data_tofill")) {
                out = response.getWriter();
                String idndb_bank_master_file = request.getParameter("idndb_bank_master_file");
                jsArr = custDAO.getBankDataTofill(idndb_bank_master_file);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_user_data_tofill")) {
                out = response.getWriter();
                String idndb_user_master = request.getParameter("idndb_user_master");
                jsArr = custDAO.getUserDataTofill(idndb_user_master);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_user_levels_data_tofill")) {
                out = response.getWriter();
                String idndb_user_levels = request.getParameter("idndb_user_levels");
                jsArr = custDAO.getUserLevelsDataTofill(idndb_user_levels);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_form_data_tofill")) {
                out = response.getWriter();
                String idndb_user_forms = request.getParameter("idndb_user_forms");
                jsArr = custDAO.getFormDataTofill(idndb_user_forms);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_branch_data_tofill")) {
                out = response.getWriter();
                String idndb_branch_master_file = request.getParameter("idndb_branch_master_file");
                jsArr = custDAO.getBranchDataTofill(idndb_branch_master_file);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_industry_data_tofill")) {
                out = response.getWriter();
                String idndb_industry_master_file = request.getParameter("idndb_industry_master_file");
                jsArr = custDAO.getIndustryDataTofill(idndb_industry_master_file);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_geo_data_tofill")) {
                out = response.getWriter();
                String idndb_geo_market_master_file = request.getParameter("idndb_geo_market_master_file");
                jsArr = custDAO.getGeoDataTofill(idndb_geo_market_master_file);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_holiday_data_tofill")) {
                out = response.getWriter();
                String idndb_holiday_marker = request.getParameter("idndb_holiday_marker");
                jsArr = custDAO.getHolidayDataTofill(idndb_holiday_marker);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_customer_data_tofill_USING_CUST_ID")) {
                out = response.getWriter();
                String cust_id = request.getParameter("cust_id");
                jsArr = custDAO.get_customer_data_tofill_USING_CUST_ID(cust_id);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_customer_data_tofill")) {
                out = response.getWriter();
                String idndb_customer_define = request.getParameter("idndb_customer_define");
                jsArr = custDAO.getCustomerDataTofill(idndb_customer_define);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_prod_map_data_tofill")) {
                out = response.getWriter();
                String idndb_cust_prod_map = request.getParameter("idndb_cust_prod_map");
                jsArr = custDAO.getProdMapDataTofill(idndb_cust_prod_map);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_prod_map_data_tofillAsSeller")) {
                out = response.getWriter();
                String idndb_customer_define_id = request.getParameter("idndb_customer_define_id");
                String cust_as_seller = request.getParameter("cust_as_seller");
                jsArr = custDAO.getProdMapDataTofillAsSeller(idndb_customer_define_id, cust_as_seller);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_prod_map_data_tofillAsBuyer")) {
                out = response.getWriter();
                String idndb_customer_define_id = request.getParameter("idndb_customer_define_id");
                String cust_as_buyer = request.getParameter("cust_as_buyer");
                jsArr = custDAO.getProdMapDataTofillAsBuyer(idndb_customer_define_id, cust_as_buyer);
                out.print(jsArr);
            }

//             if (paramid.equalsIgnoreCase("get_prod_map_data_tofillAsBuyer")) {
//                out = response.getWriter();
//                String idndb_customer_define_id = request.getParameter("idndb_customer_define_id");
//                String cust_as_buyer = request.getParameter("cust_as_buyer");
//                jsArr = custDAO.getProdMapDataTofillAsSeller(idndb_customer_define_id,cust_as_buyer);
//                out.print(jsArr);
//            }
            if (paramid.equalsIgnoreCase("get_rel_mestb_data_tofill")) {
                out = response.getWriter();
                String idndb_seller_has_buyers = request.getParameter("idndb_seller_has_buyers");
                jsArr = custDAO.getRelMestbDataTofill(idndb_seller_has_buyers);
                out.print(jsArr);
            }
            if (paramid.equalsIgnoreCase("get_rel_mestb_data_tofillSelected")) {
                out = response.getWriter();
                String idndb_customer_define_seller_id = request.getParameter("idndb_customer_define_seller_id");
                String idndb_customer_define_buyer_id = request.getParameter("idndb_customer_define_buyer_id");
                jsArr = custDAO.getRelMestbDataTofillSelected(idndb_customer_define_seller_id, idndb_customer_define_buyer_id);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("validateSellerLimt")) {
                out = response.getWriter();
                String idndb_customer_define_seller = request.getParameter("idndb_customer_define_seller");
                String sl_has_byr_cdt_loan_amu = request.getParameter("sl_has_byr_cdt_loan_amu");
                String idndb_seller_has_buyers = request.getParameter("idndb_seller_has_buyers");
                jsArr = custDAO.getAvailbleSubLimitLimit(idndb_customer_define_seller, sl_has_byr_cdt_loan_amu, idndb_seller_has_buyers);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("validateSellerCWLimt")) {
                out = response.getWriter();
                String idndb_customer_define_seller = request.getParameter("idndb_customer_define_seller");
                String idndb_seller_has_buyers = request.getParameter("idndb_seller_has_buyers");
                jsArr = custDAO.getAvailbleCWSubLimitLimit(idndb_customer_define_seller, idndb_seller_has_buyers);
                out.print(jsArr);
            }

        } catch (Exception exception) {
            try {
                result.put("error", exception.getLocalizedMessage());
            } catch (JSONException ex) {
                Logger.getLogger(CustomerDataRetrive.class.getName()).log(Level.SEVERE, null, ex);
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
