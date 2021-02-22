/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import static javax.naming.directory.SearchControls.SUBTREE_SCOPE;
import javax.naming.directory.SearchResult;
import security.ActiveDirectory.User;

/**
 *
 * @author indika_4496
 */
public class LDAPAuth {

    public Hashtable<String, String> env = null;
    String[] userAttributes;
    String _strError = "";
    String _strusername;
    String _strpassword;
    String _strdomainName;
    String _strserverIP;
    private String _strUserFullName;

    public LDAPAuth() {
        userAttributes = new String[]{
            "distinguishedName", "cn", "name", "uid",
            "sn", "givenname", "memberOf", "samaccountname",
            "userPrincipalName"
        };
    }

    public boolean ldapauthonticate(String m_username, String m_password, String m_domainName, String m_serverIP) {
        boolean success = false;
        _strusername = m_username;
        _strpassword = m_password;
        _strdomainName = m_domainName;
        _strserverIP = m_serverIP;
        String m_strprincipalName = _strusername + "@" + _strdomainName;
        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://" + _strserverIP + ":389");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, m_strprincipalName);
            env.put(Context.SECURITY_CREDENTIALS, _strpassword);
            DirContext ctx = new InitialDirContext(env);
            boolean result = ctx != null;
            if (ctx != null) {
                SearchControls controls = new SearchControls();
                controls.setSearchScope(SUBTREE_SCOPE);
                controls.setReturningAttributes(userAttributes);
                NamingEnumeration<SearchResult> answer = ctx.search(toDC(m_domainName), "(& (userPrincipalName=" + m_strprincipalName + ")(objectClass=user))", controls);
                if (answer.hasMore()) {
                    Attributes attr = answer.next().getAttributes();
                    Attribute user = attr.get("userPrincipalName");
                    if (user != null) {
                        _strUserFullName = new User(attr).getCommonName();
                    }
                }
                ctx.close();
            }
            success = result;
        } catch (Exception e) {
            _strError = "";
            if (e.getLocalizedMessage().contains("Connection timed out")) {
                _strError = "Connection timed out";
            }
            success = false;
        }
        return success;
    }

    public boolean logauthonticate(String username, String password, String domainName) {
        boolean success = false;
        String sCurrentLine;
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/ldapip.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((sCurrentLine = br.readLine()) != null) {
                success = ldapauthonticate(username, password, domainName, sCurrentLine);
                if (success) {
                    if (!_strError.contains("Connection timed out")) {
                        break;
                    } 
                }else{
                break;
                }
            }
        } catch (Exception e) {
        }
        return success;
    }

    private String toDC(String domainName) {
        StringBuilder buf = new StringBuilder();
        for (String token : domainName.split("\\.")) {
            if (token.length() == 0) {
                continue;
            }
            if (buf.length() > 0) {
                buf.append(",");
            }
            buf.append("DC=").append(token);
        }
        return buf.toString();
    }

    /**
     * @return the _strUserFullName
     */
    public String getStrUserFullName() {
        return _strUserFullName;
    }
}
