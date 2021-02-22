/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transaction_Servelet;

import DBOops.fileUploadDAO;
import DBOops.pdcDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.json.JSONException;
import org.json.JSONObject;
import utility.Utility;

/**
 *
 * @author Amila_3270
 */
@WebServlet(name = "PDCBukUpload", urlPatterns = {"/NDBRMS/PDCBukUpload"})
@MultipartConfig(maxFileSize = 16177215)
public class PDCBukUpload extends HttpServlet {

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
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        PrintWriter out1 = response.getWriter();
        ResourceBundle rb = ResourceBundle.getBundle("System", Locale.getDefault());
        OutputStream outs = null;
        InputStream filecontent = null;
        PrintWriter writer = response.getWriter();
        JSONObject m_jsObj;
        JSONObject result = new JSONObject();
        m_jsObj = new JSONObject();

        JSONObject data;
        data = new JSONObject();

        Part filePart = request.getPart("PDCBulkUploadFileData");
        String PDCBulkUploadFileID = Utility.sysDate("yyyyMMddHHmmss");
        String PDCBulkUploadFileName = PDCBulkUploadFileID + getFileName(filePart);
        String PDCBulkUploadFilePath = rb.getString("PDCBULUPLOADDir") + File.separator + PDCBulkUploadFileName;
        fileUploadDAO fileUPLD = new fileUploadDAO();

        pdcDAO pdc = new pdcDAO();
        boolean system_availability = pdc.checkSystemAvailability();
        boolean day_end_process_status = pdc.checkDayEndProcess();

        try {

            outs = new FileOutputStream(new File(PDCBulkUploadFilePath));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                outs.write(bytes, 0, read);
            }

            try {
                if (session == null) {
                    throw new Exception("Session expired ! Please relog-in to the system");
                }
                if (day_end_process_status) {
                    response.sendRedirect("pages/pdc_txns/modalsError.jsp?desigURL=ndb_pdc_fileupld.jsp&message=Dayend Process processing, currently NDB RMS system not available for data entry oparations.");

                } else if (system_availability) {
                    response.sendRedirect("pages/pdc_txns/modalsError.jsp?desigURL=ndb_pdc_fileupld.jsp&message=After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");

                } else {

                    data.put("PDCBulkUploadFileID", PDCBulkUploadFileID);
                    data.put("user_id", session.getAttribute("userid").toString());
                    data.put("idndb_customer_define_seller_id", request.getParameter("idndb_customer_define_seller_id"));
                    String PDCBulkUploadFileCWData = request.getParameter("PDCBulkUploadFileCWData");
                    String PDCBulkUploadFileRFData = request.getParameter("PDCBulkUploadFileRFData");
                    String paramid = request.getParameter("paramid");
                    if (PDCBulkUploadFileCWData == null) {
                        PDCBulkUploadFileCWData = "DEACTIVE";
                    }

                    if (PDCBulkUploadFileRFData == null) {
                        PDCBulkUploadFileRFData = "DEACTIVE";
                    }

                    data.put("PDCBulkUploadFileCWData", PDCBulkUploadFileCWData);
                    data.put("PDCBulkUploadFileRFData", PDCBulkUploadFileRFData);
                    data.put("PDCBulkUploadFilePath", PDCBulkUploadFilePath);
                    data.put("paramid", paramid);
                    String data_rec = "NA";
                    String desigUrl = "";
                    if (paramid.equals("pdc_file_upld")) {
                        data_rec = fileUPLD.uploadPDCBulkFileData(data);
                        desigUrl="ndb_pdc_fileupld.jsp";
                    }

                    if (paramid.equals("customized_pdc_file_upld")) {
                        data_rec = fileUPLD.uploadCustomizedPDCBulkFileData(data);
                        desigUrl="ndb_pdc_paydtl_upld.jsp";
                    }

                    String[] result_out = data_rec.split("/");
                    String error_code = result_out[0];
                    String error_cmessage = result_out[1];

                    if (error_code.equals("success")) {

                        response.sendRedirect("pages/pdc_txns/modalsSucess.jsp?desigURL="+desigUrl+"&message=PDC transaction file uploaded Successfully !");
                    } else {

                        response.sendRedirect("pages/pdc_txns/modalsError.jsp?desigURL="+desigUrl+"&message=" + error_cmessage + "");
                    }
                }

            } catch (Exception exception) {
                try {
                    result.put("error", "Error occured in PDC bulk upload process !");
                } catch (JSONException ex) {
                    response.sendRedirect("pages/pdc_txns/modalsError.jsp?desigURL=ndb_pdc_fileupld.jsp&message=" + exception.getLocalizedMessage() + "");
                }
                response.sendRedirect("pages/pdc_txns/modalsError.jsp?desigURL=ndb_pdc_fileupld.jsp&message=" + exception.getLocalizedMessage() + "");
            }
            out1.flush();

        } catch (FileNotFoundException fne) {

            writer.println("<br/> ERROR: " + fne.getMessage());

        } finally {
            if (outs != null) {
                outs.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(PDCBukUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(PDCBukUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}
