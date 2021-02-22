/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBOops;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import ndb.connection.ConnectionPool;
import ndb.connection.ConnectionPool2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import security.LDAPAuth;

/**
 *
 * @author indika_4496
 */
public class UserDAO {

    static Logger log = LogManager.getLogger(UserDAO.class);
    static ConnectionPool _connectionPool;
    static ConnectionPool2 _connectionPool2;
    static Connection _currentCon = null;
    static ResultSet _rs = null;
    private static int _intNoOfLogins = 0;
    private Exception _exception;
    private Statement _stmnt;

    static Statement _stmt;

    public static UserBean login(UserBean bean) {

        PreparedStatement prepStmt = null;
        _connectionPool = ConnectionPool.getInstance();
        try {
            String m_strUserID = bean.getStrUserId();
            String m_strPassword = bean.getStrpassword();
//            LdapContext ctx = ActiveDirectory.getConnection(m_strUserID, m_strPassword);
//            ctx.close();

            LDAPAuth lhd = new LDAPAuth();
//            if(!lhd.logauthonticate(m_strUserID, m_strPassword, "ndblk.int")){
//                throw new Exception("AD logging error");
//            }
            //           String m_username = lhd.getStrUserFullName();

            String m_strsql = "select * from user_mast where binary user_id=? and user_status='A'";

            _currentCon = _connectionPool.getJDBCConnection();
            prepStmt = _currentCon.prepareStatement(m_strsql);
            prepStmt.setString(1, m_strUserID);
            _rs = prepStmt.executeQuery();
            boolean more = _rs.next();
            if (!more) {
                log.error("Sorry, you are not a registered user! Please sign up first");
                bean.setValid(false);
            } else if (more) {
//                if (!_rs.getString("user_passwd").equals(new CompairPassword().encrypt(_currentCon, m_strPassword))) {
//                    bean.setValid(false);
//                    log.warn("Invalid password for User " + m_strUserID);
//                    m_strsql = "update user_mast set nooflogs=nooflogs+1,"
//                            + " user_status = if(nooflogs=" + (_intNoOfLogins) + ",2,1)"
//                            + " where user_id=?  and user_status='A' ";
//                    prepStmt = _currentCon.prepareStatement(m_strsql);
//                    prepStmt.setString(1, m_strUserID);
//                    prepStmt.executeUpdate();
//                    log.error("Invalid UserName/Password");
//                } else {
                m_strsql = "update user_mast set logdat=now() "
                        + "where user_id=? ";
                prepStmt = _currentCon.prepareStatement(m_strsql);
                prepStmt.setString(1, m_strUserID);
                prepStmt.executeUpdate();

                //String firstName = _rs.getString("user_name");
                String lastLogin = _rs.getString("logdat");
                String m_username = _rs.getString("user_name");
                bean.setStrLastLogin(lastLogin);
                bean.setStrUserName(m_username);
                bean.setValid(true);
//                }
            }
        } catch (Exception ex) {
            log.error("Log In failed: An Exception has occurred! " + ex);
        } finally {
            if (_rs != null) {
                try {
                    _rs.close();
                } catch (Exception e) {
                }
                _rs = null;
            }

            if (prepStmt != null) {
                try {
                    prepStmt.close();
                } catch (Exception e) {
                }
                prepStmt = null;
            }

            if (_currentCon != null) {
                try {
                    _currentCon.close();
                } catch (Exception e) {
                }

                _currentCon = null;
            }
        }

        return bean;
    }

    //------------------------------------------------------
    public static UserBean userLogin(UserBean bean) {

        JSONObject result = new JSONObject();
        Connection _currentCon = null;
        Connection _currentCon2 = null;
        boolean success = true;
        try {
            String m_strUserID = bean.getStrUserId();
            String m_strPasswd = bean.getStrpassword();

            String code = "0";

            _currentCon2 = null;

            _connectionPool2 = ConnectionPool2.getInstance();
            _currentCon2 = _connectionPool2.getJDBCConnection();

            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            _stmt = _currentCon2.createStatement();
            Statement m_stmt1 = _currentCon.createStatement();
            Statement m_stmt2 = _currentCon.createStatement();
            ResultSet _rs = _stmt.executeQuery("select * from user_mst where module=10 and user_id='" + m_strUserID + "' and status=1");

            boolean more = _rs.next();
            if (!m_strPasswd.equals("")) {

                if (!more) {
                    log.error("Sorry, you are not a registered user! Please sign up first");
                    bean.setValid(false);
                } else if (more) {

                    String status = _rs.getString("user_type").trim();
                    if (status.equals("3")) {
                        code = "3"; 
                    } else if (status.equals("6")) {
                        code = "6"; 
                    } else if (status.equals("7")) {
                        code = "7"; 
                    } else if (status.equals("8")) {
                        code = "8"; 
                    } else if (status.equals("9")) {
                        code = "9"; 
                    } else if (status.equals("10")) {
                        code = "10"; 
                    } else if (status.equals("11")) {
                        code = "11"; 
                    } else if (status.equals("12")) {
                        code = "12"; 
                    }
                    ResultSet _rs1 = m_stmt2.executeQuery("select max(logdat)logdat,user_id from ndb_user_login where user_id='" + m_strUserID + "'");

                    bean.setStrUserName(_rs.getString("user_name"));

                    bean.setValid(true);

                    if (_rs1.next()) {
                        if (_rs1.getString("logdat") == null) {
                            bean.setStrLastLogin("First Login");
                        } else {
                            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            Date date = dt.parse(_rs1.getString("logdat"));
                            bean.setStrLastLogin(dt.format(date).toString());
                        }
                        bean.setCode(code);
                    }
                    m_stmt1.executeUpdate("insert into ndb_user_login (user_id,logdat) values('" + m_strUserID + "',now())");
                }
            } else {
                log.error("Sorry, you are not a registered user! Please sign up first");
                bean.setValid(false);
            }

        } catch (Exception ex) {
            try {
                result.put("success", "error");
            } catch (JSONException ex1) {
                java.util.logging.Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace();
            log.error("Log In failed: An Exception has occurred! " + ex);
        } finally {
            if (_rs != null) {
                try {
                    _rs.close();
                } catch (Exception e) {
                }
                _rs = null;
            }

            if (_stmt != null) {
                try {
                    _stmt.close();
                } catch (Exception e) {
                }
                _stmt = null;
            }

            if (_currentCon != null) {
                try {
                    _currentCon.close();

                } catch (Exception e) {
                }

                _currentCon = null;

            }
            if (_currentCon2 != null) {
                try {
                    _currentCon2.close();

                } catch (Exception e) {
                }

                _currentCon2 = null;

            }
        }
        return bean;
    }

    public static String userDATA(String userID) {

        JSONObject result = new JSONObject();
        Connection _currentCon = null;
        Connection _currentCon2 = null;
        boolean success = true;
        String code = "0";
        try {
            String m_strUserID = userID;

            _currentCon2 = null;

            _connectionPool2 = ConnectionPool2.getInstance();
            _currentCon2 = _connectionPool2.getJDBCConnection();

            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            _stmt = _currentCon2.createStatement();
            Statement m_stmt1 = _currentCon.createStatement();
            Statement m_stmt2 = _currentCon.createStatement();
            ResultSet _rs = _stmt.executeQuery("select * from user_mst where module=8 and user_id='" + m_strUserID + "' and status=1");

            boolean more = _rs.next();
            if (more) {

                String status = _rs.getString("user_type").trim();
                if (status.equals("1")) {
                    code = "1"; //Inputter
                } else if (status.equals("2")) {
                    code = "2"; //Authorizer
                } else if (status.equals("3")) {
                    code = "3"; //Administrator
                }

            }

        } catch (Exception ex) {
            try {
                result.put("success", "error");
            } catch (JSONException ex1) {
                java.util.logging.Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace();
        } finally {
            if (_rs != null) {
                try {
                    _rs.close();
                } catch (Exception e) {
                }
                _rs = null;
            }

            if (_stmt != null) {
                try {
                    _stmt.close();
                } catch (Exception e) {
                }
                _stmt = null;
            }

            if (_currentCon != null) {
                try {
                    _currentCon.close();

                } catch (Exception e) {
                }

                _currentCon = null;

            }
            if (_currentCon2 != null) {
                try {
                    _currentCon2.close();

                } catch (Exception e) {
                }

                _currentCon2 = null;

            }
        }
        return code;
    }

    public static String getAllUsers() {

        JSONObject result = new JSONObject();
        Connection _currentCon = null;
        Connection _currentCon2 = null;
        boolean success = true;
        String code = "";
        try {

            _currentCon2 = null;

            _connectionPool2 = ConnectionPool2.getInstance();
            _currentCon2 = _connectionPool2.getJDBCConnection();

            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            _stmt = _currentCon2.createStatement();
            Statement m_stmt1 = _currentCon.createStatement();
            Statement m_stmt2 = _currentCon.createStatement();
            ResultSet _rs = _stmt.executeQuery("select user_id from user_mst where module=8 and user_type=2 and status=1");

            while (_rs.next()) {
                code = code + "," + _rs.getString("user_id");
            }

        } catch (Exception ex) {
            try {
                result.put("success", "error");
            } catch (JSONException ex1) {
                java.util.logging.Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
            ex.printStackTrace();
        } finally {
            if (_rs != null) {
                try {
                    _rs.close();
                } catch (Exception e) {
                }
                _rs = null;
            }

            if (_stmt != null) {
                try {
                    _stmt.close();
                } catch (Exception e) {
                }
                _stmt = null;
            }

            if (_currentCon != null) {
                try {
                    _currentCon.close();

                } catch (Exception e) {
                }

                _currentCon = null;

            }
            if (_currentCon2 != null) {
                try {
                    _currentCon2.close();

                } catch (Exception e) {
                }

                _currentCon2 = null;

            }
        }
        return code;
    }

    public boolean validatePassword(JSONObject jsonObj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        String m_strQry = "";
        ResultSet m_rs = null;
        try {
            _stmnt = _currentCon.createStatement();
            m_strQry = "select * from user_mast "
                    + " where "
                    + " user_id='" + jsonObj.getString("userid") + "' and "
                    + " user_passwd =sha1('" + jsonObj.getString("passwd") + "') and "
                    + " user_status='A'";

            m_rs = _stmnt.executeQuery(m_strQry);
            if (!m_rs.next()) {
                success = false;
            } else {
                success = true;
            }

        } catch (Exception e) {
            setException(e);
        } finally {
            try {
                if (m_rs != null) {
                    m_rs.close();
                }
                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_currentCon != null) {
                    _currentCon.close();
                }
            } catch (Exception e) {
                _exception = e;
            }
        }
        return success;
    }

    public boolean isPwdExist(JSONObject jsonObj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        String m_strQry = "";
        ResultSet m_rs = null;
        try {
            _stmnt = _currentCon.createStatement();
            m_strQry = "select * from ("
                    + " select *  from user_pass_history where user_id='" + jsonObj.getString("userid") + "' and user_type='USER' order by currdate desc limit 5 ) a "
                    + " where password = sha1('" + jsonObj.getString("passwd") + "') ";

            m_rs = _stmnt.executeQuery(m_strQry);
            if (m_rs.next()) {
                success = true;
            } else {
                success = false;
            }

        } catch (Exception e) {
            _exception = e;
        } finally {
            try {
                if (m_rs != null) {
                    m_rs.close();
                }
                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_currentCon != null) {
                    _currentCon.close();
                }
            } catch (Exception e) {
            }
        }
        return success;
    }

    public boolean resetPassword(JSONObject jsonObj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        String m_strQry = "";
        String m_strSubQry = "";
        String m_stroldval = "";
        String m_strnewval = "";
        String m_strStatusDescri = "";
        ResultSet m_rs = null;
        try {
            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            m_strQry = "select * from user_mast where user_id='" + jsonObj.get("userid").toString() + "'";
            m_rs = _stmnt.executeQuery(m_strQry);
            m_rs.next();
            if (jsonObj.get("paramid").toString().equalsIgnoreCase("reset")) {
                m_strStatusDescri = "User Password Reset";
                m_strSubQry = " user_passwd=sha1('" + jsonObj.get("passwd").toString() + "'),"
                        + " pending_status='" + m_strStatusDescri + "',"
                        + " modified_user='" + jsonObj.get("modiby").toString() + "',"
                        + " modified_date=now(),"
                        + " user_status='PA',";
                m_stroldval = m_rs.getString("user_passwd");
                m_strnewval = "sha1('" + jsonObj.get("passwd").toString() + "')";
            }
            if (jsonObj.get("paramid").toString().equalsIgnoreCase("change")) {
                m_strStatusDescri = "User  Password Changed ";
                m_strSubQry = " user_passwd=sha1('" + jsonObj.get("passwd").toString() + "'),"
                        + " modified_user='" + jsonObj.get("modiby").toString() + "',"
                        + " modified_date=now(),";
                m_stroldval = m_rs.getString("user_passwd");
                m_strnewval = "sha1('" + jsonObj.get("passwd").toString() + "')";
            }
            if (jsonObj.get("paramid").toString().equalsIgnoreCase("status")) {
                m_strStatusDescri = "User Status Changed";
                m_strSubQry = " user_status='" + jsonObj.get("status").toString() + "'"
                        + " pending_status='" + m_strStatusDescri + "',"
                        + " modified_user='" + jsonObj.get("modiby").toString() + "',"
                        + " modified_date=now(),";
                m_stroldval = m_rs.getString("user_status");
                m_strnewval = "'" + jsonObj.get("status").toString() + "'";
            }

            m_strQry = "update user_mast set "
                    + m_strSubQry
                    + " nooflogs=0 "
                    + " where user_id='" + jsonObj.get("userid").toString() + "'";
            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                throw new Exception("Error Occured in update the  user " + jsonObj.get("userid").toString());
            }
            m_strQry = "insert into changelog (type,oldval,newval,creaby,creadt,typeid,description) values("
                    + "'USER',"
                    + "'" + m_stroldval + "',"
                    + "" + m_strnewval + ","
                    + "'" + jsonObj.get("modiby").toString() + "',now(),"
                    + "'" + jsonObj.get("userid").toString() + "',"
                    + "'" + m_strStatusDescri + "')";
            _stmnt.executeUpdate(m_strQry);
            if (!jsonObj.get("paramid").toString().equalsIgnoreCase("status")) {
                m_strQry = "insert into user_pass_history (`user_id`, `password`, `user_type`, `currdate`)"
                        + "values('" + jsonObj.get("userid").toString() + "'," + m_strnewval + ",'USER',now())";
                _stmnt.executeUpdate(m_strQry);
            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in ending transaction");
            }
            success = true;
        } catch (Exception e) {
            _exception = e;
            abortConnection(_currentCon);
        } finally {
            try {
                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_currentCon != null) {
                    _currentCon.close();
                }
            } catch (Exception e) {
            }
        }
        return success;
    }

    public JSONArray getUsers() {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt1 = null;
        ResultSet m_rs1 = null;
        String m_strsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt1 = _currentCon.createStatement();
            m_strsql = "select user_id,user_name from  user_mast where user_status='A' ";
            m_rs1 = m_stamt1.executeQuery(m_strsql);
            while (m_rs1.next()) {
                m_jsObj = new JSONObject();
                m_jsObj.put("value", m_rs1.getString("user_id"));
                m_jsObj.put("label", m_rs1.getString("user_name"));
                m_jsArr.put(i, m_jsObj);
                i++;
            }

        } catch (Exception e) {
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (m_stamt1 != null) {
                    m_stamt1.close();
                }
            } catch (Exception e) {
            }

        }
        return m_jsArr;
    }

    public String[] getStrUsers() {
        String m_jsArr[] = null;
        Statement m_stamt1 = null;
        ResultSet m_rs1 = null;
        String m_strsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt1 = _currentCon.createStatement();
            m_strsql = "select user_id,user_name from  user_mast where user_status='A' ";
            m_rs1 = m_stamt1.executeQuery(m_strsql);
            m_rs1.last();
            int val = m_rs1.getRow();
            m_rs1.beforeFirst();
            m_jsArr = new String[val];
            while (m_rs1.next()) {
                m_jsArr[i] = m_rs1.getString("user_id");
                i++;
            }

        } catch (Exception e) {
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (m_stamt1 != null) {
                    m_stamt1.close();
                }
            } catch (Exception e) {
            }

        }
        return m_jsArr;
    }

    public String getUserIDForCombo() {
        Statement m_stamt1 = null;
        ResultSet m_rs1 = null;
        String m_strsql = "";
        String m_strData = "";
        int i = 0;
        try {
            _connectionPool2 = ConnectionPool2.getInstance();
            _currentCon = _connectionPool2.getJDBCConnection();
            m_stamt1 = _currentCon.createStatement();
            m_strsql = "select user_id,user_name  from  user_mst where module='7'";
            m_rs1 = m_stamt1.executeQuery(m_strsql);
            while (m_rs1.next()) {
                m_strData = m_strData + "<option  value=\"" + m_rs1.getString("user_id") + "\">" + m_rs1.getString("user_name") + "</option>";
            }
        } catch (Exception e) {
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (m_stamt1 != null) {
                    m_stamt1.close();
                }
            } catch (Exception e) {
            }
        }
        return m_strData;
    }

    private boolean startConnection(Connection con) {
        boolean success = false;
        try {
            if (!con.getAutoCommit()) {
                con.rollback();
                con.setAutoCommit(true);
            }
            con.setAutoCommit(false);
            success = true;
        } catch (Exception e) {
        }
        return success;
    }

    private boolean endConnection(Connection con) {
        boolean success = false;
        try {
            if (!con.getAutoCommit()) {
                con.commit();
                con.setAutoCommit(true);
            }
            success = true;
        } catch (Exception e) {
        }
        return success;
    }

    private boolean abortConnection(Connection con) {
        boolean success = false;
        try {
            if (!con.getAutoCommit()) {
                con.rollback();
                con.setAutoCommit(true);
            }
            success = true;
        } catch (Exception e) {
        }
        return success;
    }

    /**
     * @param _exception the _exception to set
     */
    public void setException(Exception _exception) {
        this._exception = _exception;
    }

    /**
     * @return the _exception
     */
    public Exception getException() {
        return _exception;
    }

}
