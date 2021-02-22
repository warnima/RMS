/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Master_Servlets;

import DBOops.UserBean;
import DBOops.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Madhawa_4799
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/NDBRMS/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
        try {
            request.getHeader("Authorization");

            UserBean user = new UserBean();
            
           
            user.setStrUserId(request.getParameter("username"));
            user.setStrpassword(request.getParameter("password"));
           
            

            user = UserDAO.userLogin(user);
            if (user.isValid()) {
               
                

                LdapContext ctx = getConnection(user.getStrUserId(), user.getStrpassword(), null, null);

                session.setAttribute("currentSessionUser", user);

                session.setAttribute("userid", request.getParameter("username"));
                session.setAttribute("user_name",user.getStrUserName());
                session.setAttribute("user", user);
                session.setAttribute("usertype", user.getCode());

                session.setMaxInactiveInterval(-1);
                response.sendRedirect("indexl.jsp"); //logged-in page    

            } else {
               
                throw new Exception("Invalid login");
            }

        } catch (Throwable theException) {
            theException.printStackTrace();
            response.sendRedirect("index.jsp");
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

      public static LdapContext getConnection(String username, String password, String domainName, String serverName) throws NamingException {

            if (domainName == null) {
                try {
                    String fqdn = java.net.InetAddress.getLocalHost().getCanonicalHostName();
                    if (fqdn.split("\\.").length > 1) {
                        domainName = fqdn.substring(fqdn.indexOf(".") + 1);
                    }
                } catch (java.net.UnknownHostException e) {
                }
            }

            //System.out.println("Authenticating " + username + "@" + domainName + " through " + serverName);
            if (password != null) {
                password = password.trim();
                if (password.length() == 0) {
                    password = null;
                }
            }

            //bind by using the specified username/password
            Hashtable props = new Hashtable();
            String principalName = username + "@" + domainName;
            props.put(Context.SECURITY_PRINCIPAL, principalName);
            if (password != null) {
                props.put(Context.SECURITY_CREDENTIALS, password);
            }

            String ldapURL = "ldap://" + "10.96.1.230" + '/';

            props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            props.put(Context.PROVIDER_URL, ldapURL);

            try {
                return new InitialLdapContext(props, null);
            } catch (Exception e) {

                try {
                    Hashtable props1 = new Hashtable();
                    props1.put(Context.SECURITY_PRINCIPAL, principalName);
                    if (password != null) {
                        props1.put(Context.SECURITY_CREDENTIALS, password);
                    }
                    String ldapURL1 = "ldap://" + "10.96.2.230" + '/';

                    props1.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
                    props1.put(Context.PROVIDER_URL, ldapURL1);
                    return new InitialLdapContext(props1, null);

                } catch (javax.naming.CommunicationException et) {
                    throw new NamingException("Failed to connect to " + domainName + ((serverName == null) ? "" : " through " + serverName));
                } catch (NamingException et) {
                    throw new NamingException("Failed to authenticate " + username + "@" + domainName + ((serverName == null) ? "" : " through " + serverName));
                }

            }

        }
}
