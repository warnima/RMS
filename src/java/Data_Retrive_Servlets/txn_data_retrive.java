/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Retrive_Servlets;

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
@WebServlet(name = "txn_data_retrive", urlPatterns = {"/NDBRMS/txn_data_retrive"})
public class txn_data_retrive extends HttpServlet {

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

        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray jsArr = null;
        PrintWriter out = null;
        pdc_DAO pdcDAO = new pdc_DAO();
        try {
            String paramid = request.getParameter("paramid");

            if (paramid.equalsIgnoreCase("get_PDC_CW_txn_data_to_fill")) {
                out = response.getWriter();
                String idndb_pdc_txn_master = request.getParameter("idndb_pdc_txn_master");
                jsArr = pdcDAO.getPDCCWtxnDataToFill(idndb_pdc_txn_master);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_PDC_RF_txn_data_to_fill")) {
                out = response.getWriter();
                String idndb_pdc_txn_master = request.getParameter("idndb_pdc_txn_master");
                jsArr = pdcDAO.getPDCRFtxnDataToFill(idndb_pdc_txn_master);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("getActiveChequeDetails")) {
                out = response.getWriter();
                data.put("pdc_product", request.getParameter("pdc_product"));
                data.put("pdc_chq_number", request.getParameter("pdc_chq_number"));
                data.put("cust_bank_code", request.getParameter("cust_bank_code"));
                data.put("cust_branch_code", request.getParameter("cust_branch_code"));
                jsArr = pdcDAO.getActiveChequeDetails(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("getChequeDetails")) {
                out = response.getWriter();
                data.put("idndb_pdc_txn_master", request.getParameter("idndb_pdc_txn_master"));
                jsArr = pdcDAO.getChequeDetails(data);
                out.print(jsArr);
            }
            
            if (paramid.equalsIgnoreCase("getSellerBranchAcOfficer")) {
                out = response.getWriter();
                data.put("idndb_cust_prod_map", request.getParameter("idndb_cust_prod_map_seller"));
                jsArr = pdcDAO.getSellerBranchAcOfficer(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("isChqNumAllReadyExist")) {
                out = response.getWriter();
                String idndb_customer_define_seller_id = request.getParameter("idndb_customer_define_seller_id");
                String idndb_customer_define_buyer_id = request.getParameter("idndb_customer_define_buyer_id");
                String cust_bank_code = request.getParameter("cust_bank_code");
                String cust_branch_code = request.getParameter("cust_branch_code");
                String cw_chq_number = request.getParameter("cw_chq_number");
                jsArr = pdcDAO.getPDCCWtxnDataToFillAllReadyExist(idndb_customer_define_seller_id, idndb_customer_define_buyer_id, cw_chq_number, cust_bank_code, cust_branch_code);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("isChqNumAllReadyExistForCwToRF")) {
                out = response.getWriter();
                String idndb_customer_define_seller_id = request.getParameter("idndb_customer_define_seller_id");
                String idndb_customer_define_buyer_id = request.getParameter("idndb_customer_define_buyer_id");
                String cust_bank_code = request.getParameter("cust_bank_code");
                String cust_branch_code = request.getParameter("cust_branch_code");
                String cw_chq_number = request.getParameter("cw_chq_number");
                jsArr = pdcDAO.getPDCCWtoRFAvailability(idndb_customer_define_seller_id, idndb_customer_define_buyer_id, cw_chq_number, cust_bank_code, cust_branch_code);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_PDC_CW_RF_CONV_txn_data_to_fill")) {
                out = response.getWriter();
                String idndb_pdc_txn_master = request.getParameter("idndb_pdc_txn_master");
                jsArr = pdcDAO.getPDCRFCOVtxnDataToFill(idndb_pdc_txn_master);
                out.print(jsArr);
            }

//            if (paramid.equalsIgnoreCase("get_PDC_CW_RF_CONV_txn_data_to_fill")) {
//                out = response.getWriter();
//                String idndb_pdc_txn_master = request.getParameter("idndb_pdc_txn_master");
//                jsArr = pdc_DAO.getPDCRFCOVtxnDataToFill(idndb_pdc_txn_master);
//                out.print(jsArr);
//            }
            if (paramid.equalsIgnoreCase("get_today_cheque_sending")) {
                out = response.getWriter();
                String value_date = request.getParameter("value_date");
                String idndb_customer_define_seller_id = request.getParameter("idndb_customer_define_seller_id");
                String PDCBulkUploadFileRFData = request.getParameter("PDCBulkUploadFileRFData");
                String PDCBulkUploadFileCWData = request.getParameter("PDCBulkUploadFileCWData");
                jsArr = pdcDAO.getTodayChequeSending(value_date, idndb_customer_define_seller_id, PDCBulkUploadFileRFData, PDCBulkUploadFileCWData);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_today_cheque_sending_all")) {
                out = response.getWriter();
                String value_date = request.getParameter("value_date");
                jsArr = pdcDAO.getTodayChequeSendingAll(value_date);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_today_booked_cheque_sending")) {
                out = response.getWriter();
                String booked_date = request.getParameter("booked_date");
                String idndb_customer_define_seller_id = request.getParameter("idndb_customer_define_seller_id");
                String PDCBulkUploadFileRFData = request.getParameter("PDCBulkUploadFileRFData");
                String PDCBulkUploadFileCWData = request.getParameter("PDCBulkUploadFileCWData");
                jsArr = pdcDAO.getTodayBookedChequeSending(booked_date, idndb_customer_define_seller_id, PDCBulkUploadFileRFData, PDCBulkUploadFileCWData);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_seller_buyer_key_data")) {
                out = response.getWriter();

                String idndb_customer_define_seller_id = request.getParameter("idndb_customer_define_seller_id");
                String PDCBulkUploadFileRFData = request.getParameter("PDCBulkUploadFileRFData");
                String PDCBulkUploadFileCWData = request.getParameter("PDCBulkUploadFileCWData");
                jsArr = pdcDAO.getSellerBuyerKeyData(idndb_customer_define_seller_id, PDCBulkUploadFileRFData, PDCBulkUploadFileCWData);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_seller_buyer_status_data")) {
                out = response.getWriter();

                String idndb_customer_define_seller_id = request.getParameter("idndb_customer_define_seller_id");
                String PDCBulkUploadFileRFData = request.getParameter("PDCBulkUploadFileRFData");
                String PDCBulkUploadFileCWData = request.getParameter("PDCBulkUploadFileCWData");
                jsArr = pdcDAO.getSellerBuyerStatusData(idndb_customer_define_seller_id, PDCBulkUploadFileRFData, PDCBulkUploadFileCWData);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_today_gefu_data_report")) {
                out = response.getWriter();
                String booked_date = request.getParameter("booked_date");
                jsArr = pdcDAO.getTodayGEFUREPORT(booked_date);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("getPdcVdeElqUnauthData")) {
                data.put("selection_param", request.getParameter("selection_param"));
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));

                out = response.getWriter();
                jsArr = pdcDAO.getPdcVdeElqUnauthData(data);
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
