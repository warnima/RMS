/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transaction_Servelet;

import DBOops.fileUploadDAO;
import DBOops.pdcDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.json.JSONObject;
import utility.Utility;

/**
 *
 * @author Madhawa_4799
 */
@WebServlet(name = "HolidayFileUpload", urlPatterns = {"/NDBRMS/HolidayFileUpload"})
@MultipartConfig(maxFileSize = 16177215)
public class HolidayFileUpload extends HttpServlet {

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
        PrintWriter out1 = response.getWriter();
        ResourceBundle rb = ResourceBundle.getBundle("System", Locale.getDefault());
        OutputStream outs = null;
        InputStream filecontent = null;
        PrintWriter writer = response.getWriter();
        JSONObject m_jsObj;
        m_jsObj = new JSONObject();

        JSONObject data;
        data = new JSONObject();

        Part filePart = request.getPart("HolidayFileData");
        String HolidayFileID = Utility.sysDate("yyyyMMddHHmmss");
        String HolidayFileName = HolidayFileID + ".txt";
        String HolidayFilePath = rb.getString("HolidayDir") + File.separator + HolidayFileName;
        fileUploadDAO fileUPLD = new fileUploadDAO();
        pdcDAO pdc = new pdcDAO();
        boolean system_availability = pdc.checkSystemAvailability();
        boolean day_end_process_status = pdc.checkDayEndProcess();

        try {

            if (session == null) {
                throw new Exception("Session expired ! Please relog-in to the system");
            }
            if (day_end_process_status) {
                response.sendRedirect("pages/MasterFiles/modalsError.jsp?desigURL=ndb_holiday_file_upload.jsp&message=Dayend Process processing, currently NDB RMS system not available for data entry oparations.");

            } else if (system_availability) {
                response.sendRedirect("pages/MasterFiles/modalsError.jsp?desigURL=ndb_holiday_file_upload.jsp&message=After Today Dayend Process, NDB RMS system will be available on next working day for the data entry operations.");

            } else {

                outs = new FileOutputStream(new File(HolidayFilePath));
                filecontent = filePart.getInputStream();

                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    outs.write(bytes, 0, read);
                }

                data.put("HolidayFileID", HolidayFileID);
                data.put("user_id", session.getAttribute("userid").toString());
                data.put("HolidayFilePath", HolidayFilePath);

                String data_rec = fileUPLD.uploadHolidayFileData(data);
                if (data_rec.equals("success")) {

                    response.sendRedirect("pages/MasterFiles/modalsSucess.jsp?desigURL=ndb_holiday_file_upload.jsp&message=Holiday Marked file data uloaded to DB successfuly and sent for authorization.");
                } else if (data_rec.equals("1101")) {
                    response.sendRedirect("pages/MasterFiles/modalsError.jsp?desigURL=ndb_holiday_file_upload.jsp&message=Error occured in uploading holiday marked file upload.Holiday already marked");

                } else if (data_rec.equals("1102")) {
                    response.sendRedirect("pages/MasterFiles/modalsError.jsp?desigURL=ndb_holiday_file_upload.jsp&message=Error occured in uploading holiday marked file upload.Holiday is a back date");

                } else if (data_rec.equals("1103")) {
                    response.sendRedirect("pages/MasterFiles/modalsError.jsp?desigURL=ndb_holiday_file_upload.jsp&message=Error occured in uploading holiday marked file upload.Holiday cannot be today");

                } else if (data_rec.equals("1104")) {
                    response.sendRedirect("pages/MasterFiles/modalsError.jsp?desigURL=ndb_holiday_file_upload.jsp&message=Error occured in uploading holiday marked file upload.Holiday cannot be marked as a Weekend");

                } else {
                    String[] spliter_res = data_rec.split("=");
                    response.sendRedirect("pages/MasterFiles/modalsError.jsp?desigURL=ndb_holiday_file_upload.jsp&message=Error occured in uploading holiday marked file upload.</br>Error : " + spliter_res);
                }
            }

        } catch (Exception e) {
            response.sendRedirect("pages/MasterFiles/modalsError.jsp?desigURL=ndb_holiday_file_upload.jsp&message=Error occured in uploading holiday marked file upload.");
            e.printStackTrace();
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
