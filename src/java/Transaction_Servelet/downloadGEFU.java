/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transaction_Servelet;

import DBOops.GEFUFileGenerator;
import DBOops.pdcDAO;
import DBOops.proccessDAO;
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
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;
import security.FolderZiper;
import utility.CryptoException;
import utility.CryptoUtils;

/**
 *
 * @author Madhawa_4799
 */
@WebServlet(name = "downloadGEFU", urlPatterns = {"/NDBRMS/downloadGEFU"})
public class downloadGEFU extends HttpServlet {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(downloadGEFU.class.getName());

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

        try {
            log.info("GEFU Request, GEFU file process request....");
            JSONObject m_result = new JSONObject();
            HttpSession session = request.getSession(false);
            if (session == null) {
                log.info("GEFU Request,Session expired ! Please relog-in to the system");
                throw new Exception("Session expired ! Please relog-in to the system");
            }
            pdcDAO pdc = new pdcDAO();
            log.info("GEFU Request, Check sustem status for GEFU process...");
            boolean day_gefu_process = pdc.checkGEFUProcessStatus();

            if (day_gefu_process) {
                log.info("GEFU Request, GEFU process start....");
                String user_id = session.getAttribute("userid").toString();
                String acc_gen_date = request.getParameter("acc_gen_date");
                GEFUFileGenerator GEFUFileGenerator = new GEFUFileGenerator();
                String result = GEFUFileGenerator.generateGEFUFile(user_id, acc_gen_date);
                String[] result_spliter = result.split("=");
                if (result_spliter[0].equals("success")) {

                    String filePath = result_spliter[1];
                    String ResfilePath = result_spliter[1] + ".zip";

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
                    response.sendRedirect("/NDBRMS/pages/process/ndb_pdc_acc_gen.jsp");
                } else {
                    if (result_spliter[1].equals("BULKCRDT&DATE")) {
                        m_result.put("success", "Error");
                        response.sendRedirect("/NDBRMS/pages/process/modalsError.jsp?message=Error occured in GEFU File generation&desigURL=ndb_pdc_acc_gen.jsp");

                    } else if (result_spliter[1].equals("COMMISIONCHGCPOMRESS&DATE")) {
                        response.sendRedirect("/NDBRMS/pages/process/modalsError.jsp?message=Error occured in GEFU File generation .(Commision chrages combining error .)&desigURL=ndb_pdc_acc_gen.jsp");

                    } else if (result_spliter[1].equals("MARGINGEFU&DATE")) {
                        response.sendRedirect("/NDBRMS/pages/process/modalsError.jsp?message=Error occured in GEFU File generation .(Margin entries generating error .)&desigURL=ndb_pdc_acc_gen.jsp");

                    } else {
                        response.sendRedirect("/NDBRMS/pages/process/modalsError.jsp?message=" + result_spliter[1] + "&desigURL=ndb_pdc_acc_gen.jsp");
                    }
                }

            } else {
                response.sendRedirect("/NDBRMS/pages/process/modalsError.jsp?message=GEFU File already genarated for given date.&desigURL=ndb_pdc_acc_gen.jsp");

            }

        } catch (Exception e) {
            response.sendRedirect("/NDBRMS/pages/process/modalsError.jsp?message=" + e.getLocalizedMessage() + "&desigURL=ndb_pdc_acc_gen.jsp");
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
