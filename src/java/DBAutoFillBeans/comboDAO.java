/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAutoFillBeans;

import static DBAutoFillBeans.TableDAO._connectionPool;
import DBoperationsDBO.pdcDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import ndb.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Madhawa_4799
 */
public class comboDAO {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(TableDAO.class.getName());

    static ConnectionPool _connectionPool;
    static Connection _currentCon = null;
    static ResultSet _rs = null;
    static Exception _exception;
    static Statement _stmnt;
    static Statement _stmnt2;

    public JSONArray getFileBankData(String userid) {
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
            m_strsql = "SELECT idndb_bank_master_file,bank_name,bank_code,bank_status"
                    + " FROM ndb_bank_master_file where bank_status='ACTIVE'";
            m_rs1 = m_stamt1.executeQuery(m_strsql);
            while (m_rs1.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("value", m_rs1.getString("bank_code"));
                m_jsObj.put("label", m_rs1.getString("bank_name"));
                m_jsObj.put("idndb_bank_master_file", m_rs1.getString("idndb_bank_master_file"));
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

    public String getFileBankCodeDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT idndb_bank_master_file,bank_name,bank_code,bank_status,bank_approval"
                    + " FROM ndb_bank_master_file where bank_status='ACTIVE' and bank_approval='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";

                m_strData = m_strData + "<option id=" + m_rs1.getString("bank_name") + " value=" + m_rs1.getString("idndb_bank_master_file") + ">" + m_rs1.getString("bank_code") + "</option>";

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getCust_name_ActiveDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT idndb_customer_define,cust_id,cust_name"
                    + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";

                m_strData = m_strData + "<option id=" + m_rs1.getString("cust_id") + " value=" + m_rs1.getString("idndb_customer_define") + ">" + m_rs1.getString("cust_name") + "</option>";

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getCust_Id_ActiveDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT idndb_customer_define,cust_id,cust_name"
                    + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";

                m_strData = m_strData + "<option id=" + m_rs1.getString("cust_id") + " value=" + m_rs1.getString("idndb_customer_define") + ">" + m_rs1.getString("cust_id") + "</option>";

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getCust_Id_ActiveSellerDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            m_strQry = "SELECT idndb_customer_define,idndb_cust_prod_map"
                    + " FROM ndb_cust_prod_map where prod_relationship_key_seller='ACTIVE' and prod_relationship_auth='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT idndb_customer_define,cust_id,cust_name"
                        + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs1.getString("idndb_customer_define") + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    m_strData = m_strData + "<option id=" + m_rs1.getString("idndb_customer_define") + " value=" + m_rs1.getString("idndb_cust_prod_map") + ">" + m_rs2.getString("cust_id") + "</option>";

                }

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }

                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (_stmnt2 != null) {
                    _stmnt2.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getCust_Id_ActiveRFSellerDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            m_strQry = "SELECT idndb_customer_define,idndb_cust_prod_map"
                    + " FROM ndb_cust_prod_map where prod_relationship_key_seller='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_res_fin='ACTIVE'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT idndb_customer_define,cust_id,cust_name"
                        + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs1.getString("idndb_customer_define") + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    m_strData = m_strData + "<option id=" + m_rs1.getString("idndb_customer_define") + " value=" + m_rs1.getString("idndb_cust_prod_map") + ">" + m_rs2.getString("cust_id") + "</option>";

                }

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }

                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (_stmnt2 != null) {
                    _stmnt2.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getCust_Id_ActiveCWSellerDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            m_strQry = "SELECT idndb_customer_define,idndb_cust_prod_map"
                    + " FROM ndb_cust_prod_map where prod_relationship_key_seller='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_chq_ware='ACTIVE'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT idndb_customer_define,cust_id,cust_name"
                        + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs1.getString("idndb_customer_define") + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    m_strData = m_strData + "<option id=" + m_rs1.getString("idndb_customer_define") + " value=" + m_rs1.getString("idndb_cust_prod_map") + ">" + m_rs2.getString("cust_id") + "</option>";

                }

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }

                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (_stmnt2 != null) {
                    _stmnt2.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getCust_Name_ActiveSellerDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            m_strQry = "SELECT idndb_customer_define,idndb_cust_prod_map"
                    + " FROM ndb_cust_prod_map where prod_relationship_key_seller='ACTIVE' and prod_relationship_auth='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT idndb_customer_define,cust_id,cust_name"
                        + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs1.getString("idndb_customer_define") + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    m_strData = m_strData + "<option id=" + m_rs1.getString("idndb_customer_define") + " value=" + m_rs1.getString("idndb_cust_prod_map") + ">" + m_rs2.getString("cust_name") + "</option>";

                }

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }

                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (_stmnt2 != null) {
                    _stmnt2.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getCust_Name_ActiveRFSellerDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            m_strQry = "SELECT idndb_customer_define,idndb_cust_prod_map"
                    + " FROM ndb_cust_prod_map where prod_relationship_key_seller='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_res_fin='ACTIVE'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT idndb_customer_define,cust_id,cust_name"
                        + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs1.getString("idndb_customer_define") + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    m_strData = m_strData + "<option id=" + m_rs1.getString("idndb_customer_define") + " value=" + m_rs1.getString("idndb_cust_prod_map") + ">" + m_rs2.getString("cust_name") + "</option>";

                }

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }

                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (_stmnt2 != null) {
                    _stmnt2.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getCust_Name_ActiveCWSellerDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            m_strQry = "SELECT idndb_customer_define,idndb_cust_prod_map"
                    + " FROM ndb_cust_prod_map where prod_relationship_key_seller='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_chq_ware='ACTIVE'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT idndb_customer_define,cust_id,cust_name"
                        + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs1.getString("idndb_customer_define") + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    m_strData = m_strData + "<option id=" + m_rs1.getString("idndb_customer_define") + " value=" + m_rs1.getString("idndb_cust_prod_map") + ">" + m_rs2.getString("cust_name") + "</option>";

                }

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }

                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (_stmnt2 != null) {
                    _stmnt2.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getCust_Id_ActiveBuyerDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            m_strQry = "SELECT idndb_customer_define,idndb_cust_prod_map"
                    + " FROM ndb_cust_prod_map where prod_relationship_key_buyer='ACTIVE' and prod_relationship_auth='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT idndb_customer_define,cust_id,cust_name"
                        + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs1.getString("idndb_customer_define") + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    m_strData = m_strData + "<option id=" + m_rs1.getString("idndb_customer_define") + " value=" + m_rs1.getString("idndb_cust_prod_map") + ">" + m_rs2.getString("cust_id") + "</option>";

                }

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }

                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (_stmnt2 != null) {
                    _stmnt2.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getCust_Name_ActiveSBuyerDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            m_strQry = "SELECT idndb_customer_define,idndb_cust_prod_map"
                    + " FROM ndb_cust_prod_map where prod_relationship_key_buyer='ACTIVE' and prod_relationship_auth='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT idndb_customer_define,cust_id,cust_name"
                        + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs1.getString("idndb_customer_define") + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    m_strData = m_strData + "<option id=" + m_rs1.getString("idndb_customer_define") + " value=" + m_rs1.getString("idndb_cust_prod_map") + ">" + m_rs2.getString("cust_name") + "</option>";

                }

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }

                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (_stmnt2 != null) {
                    _stmnt2.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getFileIndustryActiveDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT idndb_industry_master_file,indus_id,indus_des"
                    + " FROM ndb_industry_master_file where indus_status='ACTIVE'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";

                m_strData = m_strData + "<option id=" + m_rs1.getString("indus_id") + " value=" + m_rs1.getString("idndb_industry_master_file") + ">" + m_rs1.getString("indus_des") + "</option>";

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getFileGeoMrketActiveDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT idndb_geo_market_master_file,geo_market_id,geo_market_desc"
                    + " FROM ndb_geo_market_master_file where geo_market_status='ACTIVE'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";

                m_strData = m_strData + "<option id=" + m_rs1.getString("geo_market_id") + " value=" + m_rs1.getString("idndb_geo_market_master_file") + ">" + m_rs1.getString("geo_market_desc") + "</option>";

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getFileBankNameDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT idndb_bank_master_file,bank_name,bank_code,bank_status"
                    + " FROM ndb_bank_master_file where bank_status='ACTIVE' and bank_approval='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);

            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";

                m_strData = m_strData + "<option value=" + m_rs1.getString("idndb_bank_master_file") + ">" + m_rs1.getString("bank_name") + "</option>";

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getFormAttachedMenusDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT * From ndb_user_forms where ndb_form_main_menu_id='#' and ndb_form_status='ACTIVE'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            m_strData = "<option value=\"MM\">#</option>";

            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";

                m_strData = m_strData + "<option value=" + m_rs1.getString("idndb_user_forms") + ">" + m_rs1.getString("ndb_form_name") + "</option>";

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getFileUserLevelsDataCmb(String user_id) {
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT * FROM ndb_user_levels where ndb_user_level_status='ACTIVE'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";

                m_strData = m_strData + "<option value=" + m_rs1.getString("idndb_user_levels") + ">" + m_rs1.getString("ndb_user_level") + "</option>";

            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (m_rs1 != null) {
                    m_rs1.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public JSONArray getBankDataLi() {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt1 = null;
        ResultSet m_rs1 = null;
        String m_strsql = "";
        String m_status = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt1 = _currentCon.createStatement();
            m_strsql = "select idndb_bank_master_file,bank_name,bank_code from ndb_bank_master_file where bank_status ='ACTIVE' ";
            m_rs1 = m_stamt1.executeQuery(m_strsql);
            while (m_rs1.next()) {
                m_jsObj = new JSONObject();
                m_jsObj.put("value", m_rs1.getString("bank_code"));
                m_jsObj.put("label", m_rs1.getString("bank_name"));
                m_jsObj.put("idndb_bank_master_file", m_rs1.getString("idndb_bank_master_file"));
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

    public String getSystemDate() {
        String m_strQry = "";
        String _system_date = "";
        Statement m_stamt = null;
        try {

            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();
            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }

            m_strQry = "select * from ndb_system_date";
            _rs = m_stamt.executeQuery(m_strQry);
            if (_rs.next()) {

                _system_date = _rs.getString("_system_date");

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
                if (_currentCon != null) {
                    _currentCon.close();
                }
            } catch (Exception e) {
            }
        }
        return _system_date;

    }

    public String getchequeValueDate(String m_date) {
        pdcDAO pdc = new pdcDAO();
        String system_date_now = pdc.getSystemDate();

        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_strQry = "";
        String m_Strcw_value_date_act = "";

        try {
            String[] date_spliter = m_date.split("/");
            int m_year = Integer.parseInt(date_spliter[2]);
            int m_month = Integer.parseInt(date_spliter[1]) - 1;
            int m_day = Integer.parseInt(date_spliter[0]);

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            Calendar ced = Calendar.getInstance();

            ced.set(Calendar.YEAR, m_year); // set the year
            ced.set(Calendar.MONTH, m_month); // set the month
            ced.set(Calendar.DAY_OF_MONTH, m_day);

            String[] system_date_spliter = system_date_now.split("/");
            int sys_m_year = Integer.parseInt(system_date_spliter[2]);
            int sys_m_month = Integer.parseInt(system_date_spliter[1]) - 1;
            int sys_m_day = Integer.parseInt(system_date_spliter[0]);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            Calendar cV = Calendar.getInstance();
            cV.set(Calendar.YEAR, sys_m_year); // set the year
            cV.set(Calendar.MONTH, sys_m_month); // set the month
            cV.set(Calendar.DAY_OF_MONTH, sys_m_day);
            //cV.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

            for (int i = 0; i <= 1000; i++) {

                
                if (df.format(cV.getTime()).equals(df.format(ced.getTime()))) {

                    int dayOfWeek = cV.get(Calendar.DAY_OF_WEEK);

                    if (dayOfWeek == Calendar.SATURDAY) {
                        cV.add(Calendar.DATE, 2);
                    }
                    if (dayOfWeek == Calendar.SUNDAY) {
                        cV.add(Calendar.DATE, 1);
                    }

                    int dayOfWeek2 = ced.get(Calendar.DAY_OF_WEEK);

//                  
                    if (dayOfWeek2 == Calendar.SATURDAY) {
                        ced.add(Calendar.DATE, 2);
                    }
                    if (dayOfWeek2 == Calendar.SUNDAY) {
                        ced.add(Calendar.DATE, 1);
                    }

                    m_strQry = "select * from ndb_holiday_marker where ndb_holiday='" + df.format(ced.getTime()) + "' and ndb_holiday_status='ACTIVE' and ndb_holiday_approval='AUTHORIZED'";

                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        ced.add(Calendar.DATE, 1);

                    }

                    m_Strcw_value_date_act = df.format(ced.getTime());

                }

                cV.add(Calendar.DATE, 1);

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
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

        return m_Strcw_value_date_act;
    }

    public JSONArray getCustomers() {
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
            m_strsql = "select * from ndb_customer_define where cust_status='ACTIVE'";
            m_rs1 = m_stamt1.executeQuery(m_strsql);
            while (m_rs1.next()) {
                m_jsObj = new JSONObject();
                m_jsObj.put("value", m_rs1.getString("cust_id"));
                m_jsObj.put("label", m_rs1.getString("cust_name"));
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

}
