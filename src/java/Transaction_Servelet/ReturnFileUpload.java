/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transaction_Servelet;

import DBOops.fileUploadDAO;
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
@WebServlet(name = "ReturnFileUpload", urlPatterns = {"/NDBRMS/ReturnFileUpload"})
@MultipartConfig(maxFileSize = 16177215)
public class ReturnFileUpload extends HttpServlet {

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
        OutputStream outs = null;
        InputStream filecontent = null;
        PrintWriter writer = response.getWriter();
        JSONObject m_jsObj;
        m_jsObj = new JSONObject();

        JSONObject data;
        data = new JSONObject();
        ResourceBundle rb = ResourceBundle.getBundle("System", Locale.getDefault());

        Part filePart = request.getPart("ReturnFileData");
        String ReturnFileID = Utility.sysDate("yyyyMMddHHmmss");
        String ReturnFileName = ReturnFileID + ".txt";
        String ReturnFilePath = rb.getString("PDCRETURNFILEDir") + File.separator + ReturnFileName;
        fileUploadDAO fileUPLD = new fileUploadDAO();
        try {
            if (session == null) {
                throw new Exception("Session expired ! Please relog-in to the system");
            }
            outs = new FileOutputStream(new File(ReturnFilePath));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                outs.write(bytes, 0, read);
            }

            data.put("ReturnFileID", ReturnFileID);
            data.put("user_id", session.getAttribute("userid").toString());
            data.put("ReturnFilePath", ReturnFilePath);

            String data_rec = fileUPLD.uploadReturnFileData(data);
            if (data_rec.equals("success")) {

                response.sendRedirect("pages/process/modalsSucess.jsp?message=Return file uploaded successfully !&desigURL=ndb_pdc_retupld.jsp");
            }else if(data_rec.equals("0011")){
                response.sendRedirect("pages/process/modalsError.jsp?message=Error occurred in uploading return file. Return file cannot be uploaded. UN-Authorized transactions are found. &desigURL=ndb_pdc_retupld.jsp");
            
            }else if(data_rec.equals("0012")){
                response.sendRedirect("pages/process/modalsError.jsp?message=Error occurred in uploading return file. Invalid return file format.&desigURL=ndb_pdc_retupld.jsp");
            
            } else {
                response.sendRedirect("pages/process/modalsError.jsp?message=Error occurred in uploading return file !&desigURL=ndb_pdc_retupld.jsp");
            }

        } catch (Exception e) {
           e.printStackTrace();
            response.sendRedirect("pages/process/modalsError.jsp?message=Error ccured in additionalday file upload.&desigURL=ndb_pdc_additionalupld.jsp");
            
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
