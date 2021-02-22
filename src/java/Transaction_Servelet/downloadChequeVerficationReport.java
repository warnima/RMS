/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transaction_Servelet;

import Reports.depositSLIPS;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import security.FolderZiper;

/**
 *
 * @author Madhawa_4799
 */
@WebServlet(name = "downloadChequeVerficationReport", urlPatterns = {"/downloadChequeVerficationReport"})
public class downloadChequeVerficationReport extends HttpServlet {

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
      try {
            depositSLIPS depositSLIPS = new depositSLIPS();
            JSONObject m_result = new JSONObject();
            HttpSession session = request.getSession(false);
            if (session == null) {
                throw new Exception("Session expired ! Please relog-in to the system");
            }
            String user_id = session.getAttribute("userid").toString();
            String verification_date = request.getParameter("verification_date");
           
            String out_put =depositSLIPS.downloadPhysicalVerificationReport(verification_date);
            String[] out_res = out_put.split("=");
            String acc_res = out_res[0];
            if (acc_res.equals("success")) {
                

                String filePath = out_res[1];
                String ResfilePath = out_res[1] + ".zip";

                FolderZiper fldzip = new FolderZiper();
                fldzip.zipFolder(filePath, ResfilePath);

                File downloadFile = new File(ResfilePath);
                FileInputStream inStream = new FileInputStream(ResfilePath);
                String relativePath = getServletContext().getRealPath("");
                
                ServletContext context = getServletContext();

                String mimeType = context.getMimeType(ResfilePath);
                if (mimeType == null) {
                    // set to binary type if MIME mapping not found
                    mimeType = "application/octet-stream";
                }
                response.setContentType(mimeType);
                response.setContentLength((int) downloadFile.length());
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
                response.setHeader(headerKey, headerValue);
                OutputStream outStream = response.getOutputStream();

                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                inStream.close();
                outStream.close();

                downloadFile.delete();

            }

            m_result.put("success", "Successfully Saved");

        } catch (Exception e) {
             response.sendRedirect("pages/PhysicalVerification/modalsError.jsp?desigURL=ndb_cheque_physical_verification_report.jsp&message=Error occured in downloading physical verification report,"+ e.getLocalizedMessage() + "");
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
