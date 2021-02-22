/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.IOException;
import java.util.ArrayList;
import javax.naming.directory.*;
import javax.naming.*;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

/**
 *
 * @author priyantha_2236
 */
public class ADReader {

    public ArrayList updateUsermast() throws Exception {
        ArrayList arl = new ArrayList();

        String email = "";
        Hashtable envVars = new Hashtable();
        envVars.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        //  envVars.put(Context.PROVIDER_URL, "ldap://" + "ndblk.int:389");
        //       envVars.put(Context.PROVIDER_URL,"ldap://" + "10.96.1.233:389");
        envVars.put(Context.PROVIDER_URL, "ldap://" + "10.96.2.230:389");

        envVars.put(Context.SECURITY_AUTHENTICATION, "simple");
        envVars.put(Context.SECURITY_PRINCIPAL, "AP-InvandHDSystem@ndblk.int");
        envVars.put(Context.SECURITY_CREDENTIALS, "AAbank11");
        DirContext myContext = new InitialDirContext(envVars);
        SearchControls searchCtrls = new SearchControls();
        searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] attributes = {"sAMAccountName", "mail"};
        searchCtrls.setReturningAttributes(attributes);
        String filter = "(&(objectClass=organizationalPerson)(department=*)) ";
        NamingEnumeration values = myContext.search("DC=NDBLK,DC=int", filter, searchCtrls);

        while (values.hasMoreElements()) {

            SearchResult result = (SearchResult) values.next();
            Attributes attribs = result.getAttributes();

            if (null != attribs) {
                for (NamingEnumeration ae = attribs.getAll(); ae.hasMoreElements();) {
                    Attribute atr = (Attribute) ae.next();
                    String attributeID = atr.getID();
                    Enumeration vals = atr.getAll();
                    /*for (
                     Enumeration vals = atr.getAll();
                     vals.hasMoreElements();
                     System.out.println(attributeID + ": " + vals.nextElement())
                     );*/

                    while (vals.hasMoreElements()) {
                        String k = vals.nextElement().toString();

                        if (k.contains("-") || k.length() == 0) {

                            continue;
                        }

                        String s = k + "|";
                        email = email + s;

                    }

                }
                arl.add(email);
                // System.out.println(email);
                email = "";

            }

        }

        myContext.close();
        return arl;
        //  return email.substring(0, email.length() - 1);

    }

    ///////////////

    /*
     public String getITEmail(String Department) throws Exception 
     {
     Department="*";

     String email = "";
     Hashtable envVars = new Hashtable();
     envVars.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
     envVars.put(Context.PROVIDER_URL, "ldap://" + "ndblk.int:389");
     envVars.put(Context.SECURITY_AUTHENTICATION, "simple");
     envVars.put(Context.SECURITY_PRINCIPAL, "AP-InvandHDSystem@ndblk.int");
     envVars.put(Context.SECURITY_CREDENTIALS, "AAbank11");
     DirContext myContext = new InitialDirContext(envVars);
     SearchControls searchCtrls = new SearchControls();
     searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
     String[] attributes = {"mail"};
     searchCtrls.setReturningAttributes(attributes);
     String filter = "(&(objectClass=organizationalPerson)(Department=" + Department + ")) ";

     // String filter = "ALL";



     NamingEnumeration values = myContext.search("DC=NDBLK,DC=int", filter, searchCtrls);

     while (values.hasMoreElements()) {

     SearchResult result = (SearchResult) values.next();
     Attributes attribs = result.getAttributes();


     if (null != attribs) {
     for (NamingEnumeration ae = attribs.getAll(); ae.hasMoreElements();) {
     Attribute atr = (Attribute) ae.next();
     String attributeID = atr.getID();
                   

     Enumeration vals = atr.getAll();
     while (vals.hasMoreElements()) {
     String k = vals.nextElement().toString();
     //System.out.println(k);
     if (k.contains("-")) {
     //  System.out.println("priyantha");
     continue;
     }

     String s = "'" + k + "',";
     System.out.println(s);
     email = email + s;


     }




     }

     }

     }

     myContext.close();

     email = email + new Inventory.Admin.InventoryControl().getOtherEmail();
     return email.substring(0, email.length() - 1);




     }
     */
    ////////
    public String getITEmail(String Department) throws Exception {

        String email = "";
        Hashtable<String, Object> env = new Hashtable<String, Object>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");

        /* Specify host and port to use for directory service */
        //env.put(Context.PROVIDER_URL,"ldap://" + "ndblk.int:389/DC=NDBLK,DC=int");
        //   env.put(Context.PROVIDER_URL,"ldap://" + "10.96.1.233:389/DC=NDBLK,DC=int");
        env.put(Context.PROVIDER_URL, "ldap://" + "10.96.2.230/DC=NDBLK,DC=int");

        //   env.put(Context.PROVIDER_URL,"ldap://" + "10.96.1.237:389/DC=NDBLK,DC=int");
//env.put(Context.PROVIDER_URL, "ldap://" + "10.96.2.230:389");
        env.put(Context.SECURITY_PRINCIPAL, "AP-InvandHDSystem@ndblk.int");
        env.put(Context.SECURITY_CREDENTIALS, "AAbank11");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        try {
            LdapContext ctx = new InitialLdapContext(env, null);
            SearchControls searchCtrls = new SearchControls();
            searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attributes = {"mail"};
            searchCtrls.setReturningAttributes(attributes);
            // Activate paged results
            int pageSize = 1000;
            byte[] cookie = null;
            ctx.setRequestControls(new Control[]{
                new PagedResultsControl(pageSize, Control.NONCRITICAL)});
            int total;

            do {
                /* perform the search */
                NamingEnumeration results
                        = ctx.search("CN=Users", "(objectclass=User)", searchCtrls);
                int t = 0;
                /* for each entry print out name + all attrs and values */
                while (results != null && results.hasMore()) {
                    SearchResult entry = (SearchResult) results.next();

                    Attributes attribs = entry.getAttributes();

                    /////////////////////////////////
                    if (null != attribs) {

                        for (NamingEnumeration ae = attribs.getAll(); ae.hasMoreElements();) {
                            Attribute atr = (Attribute) ae.next();
                            String attributeID = atr.getID();
                            Enumeration vals = atr.getAll();
                            /*for (
                             Enumeration vals = atr.getAll();
                             vals.hasMoreElements();
                             System.out.println(attributeID + ": " + vals.nextElement())
                             );*/

                            while (vals.hasMoreElements()) {
                                ++t;
                                String k = vals.nextElement().toString();

                                if (k.contains("-") || k.length() == 0) {

                                    continue;
                                }

                                String s = "'" + k + "',";
                                //  System.out.println( t+ s);
                                email = email + s;

                            }

                        }

                        // System.out.println(email);
                        //   email ="";
                    }

                    //////////////////////////////////////////////////
                    //System.out.println(entry.getAttributes().toString());
                }

                // Examine the paged results control response
                Control[] controls = ctx.getResponseControls();
                if (controls != null) {
                    for (int i = 0; i < controls.length; i++) {
                        if (controls[i] instanceof PagedResultsResponseControl) {
                            PagedResultsResponseControl prrc
                                    = (PagedResultsResponseControl) controls[i];
                            total = prrc.getResultSize();
                            if (total != 0) {
                                // System.out.println("***************** END-OF-PAGE " +
                                //	"(total : " + total +
                                //	") *****************\n");
                            } else {
                                // System.out.println("***************** END-OF-PAGE " +
                                // "(total: unknown) ***************\n");
                            }
                            cookie = prrc.getCookie();
                        }
                    }
                } else {
                    System.out.println("No controls were sent from the server");
                }
                // Re-activate paged results
                ctx.setRequestControls(new Control[]{
                    new PagedResultsControl(pageSize, cookie, Control.CRITICAL)});

            } while (cookie != null);

            ctx.close();

         //   email = email + new Inventory.Admin.InventoryControl().getOtherEmail();
            return email.substring(0, email.length() - 1);
            // return email.substring(0, email.length() - 1);

        } catch (NamingException e) {

            System.err.println("PagedSearch failed. + QQQQQQQQQQqq");
//            SystemMessage.getInstance().writeMessage((new StringBuilder("AD ERROR :  PagedSearch failed. - ")).append(e.getMessage()).toString());
            return "Error";
        } catch (IOException ie) {
         //   SystemMessage.getInstance().writeMessage((new StringBuilder("AD ERROR : - ")).append(ie.getMessage()).toString());

            System.err.println("PagedSearch failed.");

            return "Error";
        }
    }

    public boolean isvalidUser(String username) throws Exception {
        boolean b = false;

        String email = "";
        Hashtable envVars = new Hashtable();
        envVars.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        // envVars.put(Context.PROVIDER_URL, "ldap://" + "ndblk.int:389");
        //    envVars.put(Context.PROVIDER_URL,"ldap://" + "10.96.1.233:389");
        envVars.put(Context.PROVIDER_URL, "ldap://" + "10.96.2.230:389");

        envVars.put(Context.SECURITY_AUTHENTICATION, "simple");
        envVars.put(Context.SECURITY_PRINCIPAL, "AP-InvandHDSystem@ndblk.int");
        envVars.put(Context.SECURITY_CREDENTIALS, "AAbank11");
        DirContext myContext = new InitialDirContext(envVars);
        SearchControls searchCtrls = new SearchControls();
        searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] attributes = {"sAMAccountName", "mail"};
        searchCtrls.setReturningAttributes(attributes);
        String filter = "(&(objectClass=organizationalPerson)(department=*)(sAMAccountName=" + username + ")) ";
        NamingEnumeration values = myContext.search("DC=NDBLK,DC=int", filter, searchCtrls);

        while (values.hasMoreElements()) {

            SearchResult result = (SearchResult) values.next();
            Attributes attribs = result.getAttributes();

            if (null != attribs) {
                for (NamingEnumeration ae = attribs.getAll(); ae.hasMoreElements();) {
                    Attribute atr = (Attribute) ae.next();
                    String attributeID = atr.getID();
                    Enumeration vals = atr.getAll();
                    /*for (
                     Enumeration vals = atr.getAll();
                     vals.hasMoreElements();
                     System.out.println(attributeID + ": " + vals.nextElement())
                     );*/

                    while (vals.hasMoreElements()) {

                        return true;

                    }

                }

            }

        }

        myContext.close();
        return b;
        //  return email.substring(0, email.length() - 1);

    }

/////// get user list
    public String getUserList(String Department) throws Exception {

        String email = "";
        Hashtable envVars = new Hashtable();
        envVars.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        // envVars.put(Context.PROVIDER_URL, "ldap://" + "ndblk.int:389");
        // envVars.put(Context.PROVIDER_URL,"ldap://" + "10.96.1.233:389");
        envVars.put(Context.PROVIDER_URL, "ldap://" + "10.96.2.230:389");

        envVars.put(Context.SECURITY_AUTHENTICATION, "simple");
        envVars.put(Context.SECURITY_PRINCIPAL, "AP-InvandHDSystem@ndblk.int");
        envVars.put(Context.SECURITY_CREDENTIALS, "AAbank11");
        DirContext myContext = new InitialDirContext(envVars);
        SearchControls searchCtrls = new SearchControls();
        searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] attributes = {"sAMAccountName"};
        searchCtrls.setReturningAttributes(attributes);
        String filter = "(&(objectClass=organizationalPerson)(department=" + Department + ")) ";
        NamingEnumeration values = myContext.search("DC=NDBLK,DC=int", filter, searchCtrls);

        while (values.hasMoreElements()) {

            SearchResult result = (SearchResult) values.next();
            Attributes attribs = result.getAttributes();

            if (null != attribs) {
                for (NamingEnumeration ae = attribs.getAll(); ae.hasMoreElements();) {
                    Attribute atr = (Attribute) ae.next();
                    String attributeID = atr.getID();
                    /*  for (Enumeration vals = atr.getAll();
                     vals.hasMoreElements();





                     System.out.println(attributeID + ": " + vals.nextElement()));


                     */

                    Enumeration vals = atr.getAll();
                    while (vals.hasMoreElements()) {
                        String k = vals.nextElement().toString();
                        //System.out.println(k);
                        if (k.contains("-")) {
                            //  System.out.println("priyantha");
                            continue;
                        }

                        String s = "'" + k + "',";
                        email = email + s;

                    }

                }

            }

        }

        myContext.close();
        return email.substring(0, email.length() - 1);

    }

    /*
     *
     * */
    public String getUserEmail(String userid) {

        try {
            //ArrayList arl = new ArrayList();

            String email = "";
            Hashtable envVars = new Hashtable();
            envVars.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
      // envVars.put(Context.PROVIDER_URL, "ldap://" + "ndblk.int:389");

            //  envVars.put(Context.PROVIDER_URL,"ldap://" + "10.96.1.233:389");
            envVars.put(Context.PROVIDER_URL, "ldap://" + "10.96.2.230:389");

            // envVars.put(Context.PROVIDER_URL, "ldap://" + "10.96.1.230:389");
            envVars.put(Context.SECURITY_AUTHENTICATION, "simple");
            envVars.put(Context.SECURITY_PRINCIPAL, "AP-InvandHDSystem@ndblk.int");
            envVars.put(Context.SECURITY_CREDENTIALS, "AAbank11");
            DirContext myContext = new InitialDirContext(envVars);
            SearchControls searchCtrls = new SearchControls();
            searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attributes = {"mail"};
            searchCtrls.setReturningAttributes(attributes);
            String filter = "(&(objectClass=organizationalPerson)(sAMAccountName=" + userid + ")) ";
            NamingEnumeration values = myContext.search("DC=NDBLK,DC=int", filter, searchCtrls);

            while (values.hasMoreElements()) {

                SearchResult result = (SearchResult) values.next();
                Attributes attribs = result.getAttributes();

                if (null != attribs) {
                    for (NamingEnumeration ae = attribs.getAll(); ae.hasMoreElements();) {
                        Attribute atr = (Attribute) ae.next();
                        String attributeID = atr.getID();
                        Enumeration vals = atr.getAll();
                        /*for (
                         Enumeration vals = atr.getAll();
                         vals.hasMoreElements();
                         System.out.println(attributeID + ": " + vals.nextElement())
                         );*/

                        while (vals.hasMoreElements()) {
                            String k = vals.nextElement().toString();

                            if (k.contains("-") || k.length() == 0) {

                                continue;
                            }

                            String s = k;
                            email = email + s;

                        }

                    }
                    // arl.add(email);
                    // System.out.println(email);
                    //   email ="";

                }

            }

            myContext.close();
            return email;
            //  return email.substring(0, email.length() - 1);

        } catch (Exception e) {
         //   SystemMessage.getInstance().writeMessage((new StringBuilder("AD ERROR :  PagedSearch failed. - ")).append(e.getMessage()).toString());
            return " Error ";
        }

    }

    /*
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * */
    public String getUserID(String useremail) throws Exception {
        //ArrayList arl = new ArrayList();
        String email = "";
        Hashtable envVars = new Hashtable();
        envVars.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        envVars.put(Context.PROVIDER_URL, "ldap://" + getIP());
        envVars.put(Context.SECURITY_AUTHENTICATION, "simple");
        envVars.put(Context.SECURITY_PRINCIPAL, "AP-InvandHDSystem@ndblk.int");
        envVars.put(Context.SECURITY_CREDENTIALS, "AAbank11");

        DirContext myContext = new InitialDirContext(envVars);
        SearchControls searchCtrls = new SearchControls();
        searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] attributes = {"sAMAccountName", "msDS-UserAccountDisabled"};

        // String[] attributes = { "sAMAccountName"};
        searchCtrls.setReturningAttributes(attributes);
        String filter = "(&(objectClass=organizationalPerson)(mail=" + useremail + ")) ";
        NamingEnumeration values = myContext.search("DC=NDBLK,DC=int", filter, searchCtrls);

        while (values.hasMoreElements()) {

            SearchResult result = (SearchResult) values.next();
            Attributes attribs = result.getAttributes();

            if (null != attribs) {
                for (NamingEnumeration ae = attribs.getAll(); ae.hasMoreElements();) {
                    Attribute atr = (Attribute) ae.next();
                    String attributeID = atr.getID();
                    Enumeration vals = atr.getAll();
                    /*for (
                     Enumeration vals = atr.getAll();
                     vals.hasMoreElements();
                     System.out.println(attributeID + ": " + vals.nextElement())
                     );*/

                    while (vals.hasMoreElements()) {
                        String k = vals.nextElement().toString();

                        if (k.contains("-") || k.length() == 0) {

                            continue;
                        }

                        String s = k;
                        email = email + s;

                    }

                }
                // arl.add(email);
                // System.out.println(email);
                //   email ="";

            }

        }
        myContext.close();
        return email;
        //  return email.substring(0, email.length() - 1);

    }

    public static void main(String args[]) throws Exception {

        /*  ArrayList arl =   new ADReader().updateUsermast();
         for(int i=0;i<arl.size();i++)
         {
         String s = arl.get(i).toString();
         int validemai = s.indexOf("@ndbbank.com");
         int vallen = s.split("\\|").length;

         if(validemai > -1 && vallen >1)
         {
         System.out.println(s);
         }

         }
         */
//System.out.println(new ADReader().isvalidUser("Waruna_3528"));
//System.out.println(new ADReader().getITEmail("IT"));
        //System.out.println(new ADReader().getUserID("Suresh.Weerasinghe@ndbbank.com"));
       // System.out.println(new ADReader().getUserID("Velautham.Sivapalan@ndbbank.com"));

        System.out.println(new ADReader().getUserDepartment("anushka_3188"));
        //System.out.println(getDirContexttest());
        //   System.out.println(new ADReader().getUserEmail("Kasun_1239"));

    }

    public String getUserDepartment(String userid) throws Exception {
        //ArrayList arl = new ArrayList();
        String email = "";
        Hashtable envVars = new Hashtable();
        envVars.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        envVars.put(Context.PROVIDER_URL, "ldap://" + getIP());
        envVars.put(Context.SECURITY_AUTHENTICATION, "simple");
        envVars.put(Context.SECURITY_PRINCIPAL, "AP-InvandHDSystem@ndblk.int");
        envVars.put(Context.SECURITY_CREDENTIALS, "AAbank11");

        DirContext myContext = new InitialDirContext(envVars);
        SearchControls searchCtrls = new SearchControls();
        searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] attributes = {"department"};

        // String[] attributes = { "sAMAccountName"};
        searchCtrls.setReturningAttributes(attributes);
        String filter = "(&(objectClass=organizationalPerson)(sAMAccountName=" + userid + ")) ";
        NamingEnumeration values = myContext.search("DC=NDBLK,DC=int", filter, searchCtrls);

        while (values.hasMoreElements()) {

            SearchResult result = (SearchResult) values.next();
            Attributes attribs = result.getAttributes();

            if (null != attribs) {
                for (NamingEnumeration ae = attribs.getAll(); ae.hasMoreElements();) {
                    Attribute atr = (Attribute) ae.next();

                    Enumeration vals = atr.getAll();

                    while (vals.hasMoreElements()) {
                        String k = vals.nextElement().toString();

                       email =k;

                    }

                }
                // arl.add(email);
                // System.out.println(email);
                //   email ="";

            }

        }
        myContext.close();
        return email;
        //  return email.substring(0, email.length() - 1);

    }

    public static String getIP() {

        String IP = "";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("0");
                try {
                    checkLiveIPAvalibility();

                } catch (Exception ex) {
                    Logger.getLogger(ADReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        thread.start();

        long endTimeMillis = System.currentTimeMillis() + 250;
        while (thread.isAlive()) {
            if (System.currentTimeMillis() > endTimeMillis) {
                System.out.println("1");

                IP = "10.96.1.230:389";
                break;
            }
            IP = "10.96.2.230:389";
            try {
                System.out.println("2");
                Thread.sleep(500);
            } catch (InterruptedException t) {
            }
        }
        return IP;

    }

    public static String checkLiveIPAvalibility() throws Exception {
        DirContext myContext = null;
        String IP = "";
        try {
            Hashtable envVars = new Hashtable();
            envVars.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            envVars.put(Context.PROVIDER_URL, "ldap://" + "10.96.2.230:389");
            envVars.put(Context.SECURITY_AUTHENTICATION, "simple");
            envVars.put(Context.SECURITY_PRINCIPAL, "AP-InvandHDSystem@ndblk.int");
            envVars.put(Context.SECURITY_CREDENTIALS, "AAbank11");

            myContext = new InitialDirContext(envVars);
            myContext.close();

            IP = "10.96.2.230:389";
            return IP;
        } catch (Exception e) {
            IP = "10.96.1.230:389";
            return IP;
        }

    }

}
