/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Report_servelets;

import Data_Retrive_Servlets.CustomerDataRetrive;
import Reports.ReportDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
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
import security.FolderZiper;

/**
 *
 * @author Madhawa_4799
 */
@WebServlet(name = "report_data_extract", urlPatterns = {"/report_data_extract"})
public class report_data_extract extends HttpServlet {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(report_data_extract.class.getName());

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
        PrintWriter out = null;

        ReportDAO ReportDAO = new ReportDAO();
        try {

            if (session == null) {
                throw new Exception("Session expired ! Please relog-in to the system");
            }
            String paramid = request.getParameter("paramid");

            if (paramid.equalsIgnoreCase("getReceivedAllPdcChequesBuyerWise")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("idndb_customer_define_buyer_id", request.getParameter("idndb_customer_define_buyer_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));
                data.put("pdc_product", request.getParameter("pdc_product"));
                data.put("pdc_report_type", request.getParameter("pdc_report_type"));

                out = response.getWriter();
                jsArr = ReportDAO.getReceivedAllPdcChequesBuyerWise(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("getReceivedAllPdcChequesSellerWise")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));
                data.put("pdc_product", request.getParameter("pdc_product"));
                data.put("pdc_report_type", request.getParameter("pdc_report_type"));

                out = response.getWriter();
                jsArr = ReportDAO.getReceivedAllPdcChequesSellerWise(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("getPdcValueDateExtentionedChequesSellerWise")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));
                data.put("pdc_product", request.getParameter("pdc_product"));
                data.put("pdc_report_type", request.getParameter("pdc_report_type"));

                out = response.getWriter();
                jsArr = ReportDAO.getPdcValueDateExtentionedChequesSellerWise(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("getPdcAdditionalDayChequesSellerWise")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));
                data.put("pdc_product", request.getParameter("pdc_product"));
                data.put("pdc_report_type", request.getParameter("pdc_report_type"));

                out = response.getWriter();
                jsArr = ReportDAO.getPdcAdditionalDayChequesSellerWise(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("getPdcChequeStatusReport")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));
                data.put("pdc_product", request.getParameter("pdc_product"));
                data.put("pdc_status", request.getParameter("pdc_status"));
                data.put("pdc_date_type", request.getParameter("pdc_date_type"));
                data.put("pdc_report_type", request.getParameter("pdc_report_type"));

                out = response.getWriter();
                jsArr = ReportDAO.getPdcChequeStatusReport(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("getReturnedClearedChequesSellerWise")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));
                data.put("pdc_status", request.getParameter("pdc_status"));
                data.put("pdc_product", request.getParameter("pdc_product"));
                data.put("pdc_report_type", request.getParameter("pdc_report_type"));

                out = response.getWriter();
                jsArr = ReportDAO.getReturnedClearedChequesSellerWise(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("getGefuReport")) {
                data.put("gefu_date", request.getParameter("gefu_date"));
                out = response.getWriter();
                jsArr = ReportDAO.getGefuReport(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_cw_unauth_cheques")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_cw_unauth_cheques(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("getUnauthPdcChequesForAuthorization")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("pdc_product", request.getParameter("pdc_product"));
                data.put("pdc_report_type", request.getParameter("pdc_report_type"));

                out = response.getWriter();
                jsArr = ReportDAO.getUnauthPdcChequesForAuthorization(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_cw_unauth_cheques_for_auth")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_cw_unauth_cheques_for_auth(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_all_un_auth_data")) {

                out = response.getWriter();
                jsArr = ReportDAO.getAllUnAuthData(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_rf_unauth_cheques_for_auth")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_rf_unauth_cheques_for_auth(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_rf_unauth_cheques")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_rf_unauth_cheques(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_cw_received_cheques_seller_wise")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_cw_reveived_chques_data_seller_wise(data);
                out.print(jsArr);
            }
            if (paramid.equalsIgnoreCase("get_pdc_cw_unauth_bulk_pdc_data")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("prorm_type_rf", request.getParameter("prorm_type_rf"));
                data.put("prorm_type_cw", request.getParameter("prorm_type_cw"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_cw_unauth_bulk_pdc_data(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_cw_value_date_extention_seller_wise")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_cw_vde_data_seller_wise(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_cw_additional_day_seller_wise")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));

                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_cw_additional_day_data_seller_wise(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_cw_return_seller_wise")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));

                data.put("status", request.getParameter("status"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_cw_return_data_seller_wise(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_cw_chq_status_seller_wise")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));

                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));
                data.put("status", request.getParameter("status"));
                data.put("date_type", request.getParameter("date_type"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_cw_chq_status_seller_wise(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_rf_chq_status_seller_wise")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));
                data.put("status", request.getParameter("status"));
                data.put("date_type", request.getParameter("date_type"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_rf_chq_status_seller_wise(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_full_portpolio_reort")) {

                data.put("status", request.getParameter("status"));

                out = response.getWriter();
                jsArr = ReportDAO.getFullPortpolio(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_rf_returned_seller_wise")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("status", request.getParameter("status"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));
                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_rf_returned_seller_wise(data);

                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_cw_seller_wise_buyer_facility")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_cw_seller_wise_buyer_facility(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_cw_seller_wise_facility")) {

                out = response.getWriter();
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                jsArr = ReportDAO.get_pdc_cw_seller_wise_facility(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_rf_seller_wise_facility")) {

                out = response.getWriter();
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                jsArr = ReportDAO.get_pdc_rf_seller_wise_facility(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_rf_rurned_anal")) {

                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_rf_rurned_anal(data);
                out.print(jsArr);
            }
            if (paramid.equalsIgnoreCase("get_pdc_rf_rurned_anal_bw")) {
                log.info("get_pdc_rf_rurned_anal_bw servlet request...");

                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("idndb_customer_define_buyer_id", request.getParameter("idndb_customer_define_buyer_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_rf_rurned_anal_bw(data);
                log.info("get_pdc_rf_rurned_anal_bw servlet responded...");
                out.print(jsArr);

            }

            if (paramid.equalsIgnoreCase("get_pdc_cw_rurned_anal_bw")) {

                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("idndb_customer_define_buyer_id", request.getParameter("idndb_customer_define_buyer_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_cw_rurned_anal_bw(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_cw_rurned_anal")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_cw_rurned_anal(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_cw_commsion_chrges")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_cw_commsion_chrges(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_rf_commsion_chrges")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                data.put("date_from", request.getParameter("date_from"));
                data.put("date_to", request.getParameter("date_to"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_rf_commsion_chrges(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("get_pdc_rf_seller_wise_buyer_facility")) {
                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));

                out = response.getWriter();
                jsArr = ReportDAO.get_pdc_rf_seller_wise_buyer_facility(data);
                out.print(jsArr);
            }

            if (paramid.equalsIgnoreCase("msrt_verify_data")) {
                data.put("cheque_number_input", request.getParameter("cheque_number_input"));
                data.put("bank_code_input", request.getParameter("bank_code_input"));
                data.put("branch_code_input", request.getParameter("branch_code_input"));

                out = response.getWriter();
                jsArr = ReportDAO.cheque_verifyication(data);
                out.print(jsArr);
            }

        } catch (Exception exception) {
            try {
                result.put("error", exception.getLocalizedMessage());
            } catch (JSONException ex) {
                Logger.getLogger(CustomerDataRetrive.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("pages/pdc_txns/modalsError.jsp?desigURL=ndb_pdc_fileupld.jsp&message=" + exception.getLocalizedMessage() + "");
        }
        log.info("get_pdc_rf_rurned_anal_bw out.flush called...");

        out.flush();
        out.close();
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

//        String paramid = request.getParameter("paramid");
//        if (paramid.equals("get_pdc_rf_rurned_anal_bw")) {
//            try {
//                log.info("chequeRetAnalysisBw Request....");
//                JSONObject m_result = new JSONObject();
//                HttpSession session = request.getSession(false);
//                if (session == null) {
//                    log.info("chequeRetAnalysisBw Requestt,Session expired ! Please relog-in to the system");
//                    throw new Exception("Session expired ! Please relog-in to the system");
//                }
//
//                JSONObject data = new JSONObject();
//                data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
//                data.put("idndb_customer_define_buyer_id", request.getParameter("idndb_customer_define_buyer_id"));
//                data.put("date_from", request.getParameter("date_from"));
//                data.put("date_to", request.getParameter("date_to"));
//
//                ReportDAO ReportDAO = new ReportDAO();
//
//                String result = ReportDAO.downloadChequeRetAnalysisBw(data);
//                String[] result_spliter = result.split("=");
//                if (result_spliter[0].equals("success")) {
//
//                    String filePath = result_spliter[1];
//                    String ResfilePath = result_spliter[1] + ".zip";
//
//                    FolderZiper fldzip = new FolderZiper();
//                    fldzip.zipFolder(filePath, ResfilePath);
//
//                    File downloadFile = new File(ResfilePath);
//                    FileInputStream inStream = new FileInputStream(ResfilePath);
//                    String relativePath = getServletContext().getRealPath("");
//
//                    ServletContext context = getServletContext();
//
//                    String mimeType = context.getMimeType(ResfilePath);
//                    if (mimeType == null) {
//                        // set to binary type if MIME mapping not found
//                        mimeType = "application/octet-stream";
//                    }
//                    response.setContentType(mimeType);
//                    response.setContentLength((int) downloadFile.length());
//                    String headerKey = "Content-Disposition";
//                    String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
//                    response.setHeader(headerKey, headerValue);
//                    OutputStream outStream = response.getOutputStream();
//
//                    byte[] buffer = new byte[4096];
//                    int bytesRead = -1;
//
//                    while ((bytesRead = inStream.read(buffer)) != -1) {
//                        outStream.write(buffer, 0, bytesRead);
//                    }
//
//                    inStream.close();
//                    outStream.close();
//
//                    downloadFile.delete();
//                    response.sendRedirect("/NDBRMS/pages/process/ndb_pdc_acc_gen.jsp");
//                } else {
//                    if (result_spliter[1].equals("BULKCRDT&DATE")) {
//                        m_result.put("success", "Error");
//                        response.sendRedirect("/NDBRMS/pages/process/modalsError.jsp?message=Error occured in GEFU File generation&desigURL=ndb_pdc_acc_gen.jsp");
//
//                    } else if (result_spliter[1].equals("COMMISIONCHGCPOMRESS&DATE")) {
//                        response.sendRedirect("/NDBRMS/pages/process/modalsError.jsp?message=Error occured in GEFU File generation .(Commision chrages combining error .)&desigURL=ndb_pdc_acc_gen.jsp");
//
//                    } else if (result_spliter[1].equals("MARGINGEFU&DATE")) {
//                        response.sendRedirect("/NDBRMS/pages/process/modalsError.jsp?message=Error occured in GEFU File generation .(Margin entries generating error .)&desigURL=ndb_pdc_acc_gen.jsp");
//
//                    } else {
//                        response.sendRedirect("/NDBRMS/pages/process/modalsError.jsp?message=" + result_spliter[1] + "&desigURL=ndb_pdc_acc_gen.jsp");
//                    }
//                }
//
//            } catch (Exception e) {
//                response.sendRedirect("/NDBRMS/pages/process/modalsError.jsp?message=" + e.getLocalizedMessage() + "&desigURL=ndb_pdc_acc_gen.jsp");
//                e.printStackTrace();
//            }
//        }
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
