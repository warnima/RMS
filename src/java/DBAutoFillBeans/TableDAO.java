/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAutoFillBeans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import ndb.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Madhawa_4799
 */
public class TableDAO {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(TableDAO.class.getName());

    static ConnectionPool _connectionPool;
    static Connection _currentCon = null;
    static ResultSet _rs = null;
    static ResultSet _rs2 = null;
    static Exception _exception;
    static Statement _stmnt;
    static Statement _stmnt2;
    static Statement _stmnt3;

    public String getBankData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT idndb_bank_master_file,bank_name,bank_code,bank_status,bank_approval"
                    + " FROM ndb_bank_master_file";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_bank_master_file") + ">" + m_rs1.getString("idndb_bank_master_file") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_name") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_status") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_approval") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getHolidayData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT * FROM ndb_holiday_marker";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_holiday_marker") + ">" + m_rs1.getString("idndb_holiday_marker") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_holiday") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_holiday_status") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_holiday_approval") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getCWPDCData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_pdc_txn_master") + ">" + m_rs1.getString("idndb_pdc_txn_master") + "</td>";

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_strData = m_strData + "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";

                    }

                }

                String buyer_idcust_prod_map = "";

                m_strQry2 = "SELECT idndb_customer_define_buyer from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_rs1.getString("idndb_customer_define_buyer_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    buyer_idcust_prod_map = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + buyer_idcust_prod_map + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_strData = m_strData + "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";

                    }

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_number") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_bank_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_branch_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_value_date") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_amu") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_status") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_status_auth") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getCWRFENABLEPDCData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED','AUTHORIZED')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                String m_idndb_customer_define_seller = m_rs1.getString("idndb_customer_define_seller_id");
                String m_idndb_customer_define_buyer = "";
                String seller_tr = "";
                String buyer_tr = "";

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        seller_tr = "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";

                    }

                }

                String buyer_idcust_prod_map = "";

                m_strQry2 = "SELECT idndb_customer_define_buyer from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_rs1.getString("idndb_customer_define_buyer_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    buyer_idcust_prod_map = m_rs2.getString("idndb_customer_define_buyer");

                    m_idndb_customer_define_buyer = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + buyer_idcust_prod_map + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        buyer_tr = "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";

                    }

                }
                boolean cw_enable = false;
                boolean rf_enable = false;
                m_strQry3 = "select * from ndb_seller_has_buyers where idndb_customer_define_seller='" + m_idndb_customer_define_seller + "' and idndb_customer_define_buyer='" + m_idndb_customer_define_buyer + "' and sl_has_byr_prorm_type in ('CW')";
                m_rs3 = _stmnt3.executeQuery(m_strQry3);
                if (m_rs3.next()) {
                    cw_enable = true;

                }
                m_strQry3 = "select * from ndb_seller_has_buyers where idndb_customer_define_seller='" + m_idndb_customer_define_seller + "' and idndb_customer_define_buyer='" + m_idndb_customer_define_buyer + "' and sl_has_byr_prorm_type in ('RF')";
                m_rs3 = _stmnt3.executeQuery(m_strQry3);
                if (m_rs3.next()) {
                    rf_enable = true;

                }

                if (cw_enable && rf_enable) {
                    m_strData = m_strData + "<tr>";
                    m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_pdc_txn_master") + ">" + m_rs1.getString("idndb_pdc_txn_master") + "</td>";
                    m_strData = m_strData + seller_tr;
                    m_strData = m_strData + buyer_tr;
                    m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_number") + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("pdc_bank_code") + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("pdc_branch_code") + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("pdc_value_date") + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_amu") + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_status") + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_status_auth") + "</td>";
                    m_strData = m_strData + "</tr>";
                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
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

    public String getCWPDCUnAuthData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_pdc_txn_master") + ">" + m_rs1.getString("idndb_pdc_txn_master") + "</td>";

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_strData = m_strData + "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";

                    }

                }

                String buyer_idcust_prod_map = "";

                m_strQry2 = "SELECT idndb_customer_define_buyer from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_rs1.getString("idndb_customer_define_buyer_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    buyer_idcust_prod_map = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + buyer_idcust_prod_map + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_strData = m_strData + "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";

                    }

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_number") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_bank_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_branch_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_value_date") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_amu") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_status") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_status_auth") + "</td>";
                // m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_mod_by") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_mod_date") + "</td>";
                m_strData = m_strData + "<td><div class=\"controls\"><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + m_rs1.getString("idndb_pdc_txn_master") + "\"/></div><td>";
                m_strData = m_strData + "</tr>";
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

    public String getRFPDCData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            DecimalFormat df = new DecimalFormat("#,###.00");

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='RF' and pdc_chq_status_auth in ('UN-AUTHORIZED','AUTHORIZED')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_pdc_txn_master") + ">" + m_rs1.getString("idndb_pdc_txn_master") + "</td>";

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_strData = m_strData + "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";

                    }

                }

                String buyer_idcust_prod_map = "";

                m_strQry2 = "SELECT idndb_customer_define_buyer from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_rs1.getString("idndb_customer_define_buyer_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    buyer_idcust_prod_map = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + buyer_idcust_prod_map + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_strData = m_strData + "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";

                    }

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_number") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_bank_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_branch_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_value_date") + "</td>";
                m_strData = m_strData + "<td>" + df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))) + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_status") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_status_auth") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getRFPDCUnAuthData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='RF' and pdc_chq_status_auth in ('UN-AUTHORIZED')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_pdc_txn_master") + ">" + m_rs1.getString("idndb_pdc_txn_master") + "</td>";

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_strData = m_strData + "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";

                    }

                }

                String buyer_idcust_prod_map = "";

                m_strQry2 = "SELECT idndb_customer_define_buyer from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_rs1.getString("idndb_customer_define_buyer_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    buyer_idcust_prod_map = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + buyer_idcust_prod_map + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_strData = m_strData + "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";

                    }

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_number") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_bank_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_branch_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_value_date") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_amu") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_status") + "</td>";
                //m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_status_auth") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_mod_by") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_mod_date") + "</td>";
                m_strData = m_strData + "<td><div class=\"controls\"><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + m_rs1.getString("idndb_pdc_txn_master") + "\"/></div><td>";

                m_strData = m_strData + "</tr>";
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

    public String getUnAuthHolidayData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT * FROM ndb_holiday_marker where ndb_holiday_approval in ('UN-AUTHORIZED','REJECTED')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_holiday_marker") + ">" + m_rs1.getString("idndb_holiday_marker") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_holiday") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_holiday_status") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_holiday_approval") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_holiday_mod_by") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_holiday_mod_date") + "</td>";
                m_strData = m_strData + "<td><div class=\"controls\"><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + m_rs1.getString("idndb_holiday_marker") + "\"/></div><td>";
                m_strData = m_strData + "</tr>";
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

    public String getUnAuthBankData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT idndb_bank_master_file,bank_name,bank_code,bank_status,bank_approval,bank_mod_by,bank_mod_date"
                    + " FROM ndb_bank_master_file where bank_approval in ('UN-AUTHORIZED','REJECTED')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_bank_master_file") + ">" + m_rs1.getString("idndb_bank_master_file") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_name") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_status") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_approval") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_mod_by") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_mod_date") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getUserData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT * From ndb_user_master";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_user_master") + ">" + m_rs1.getString("idndb_user_master") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_user_user_id") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_user_name") + "</td>";
                String piv_systems = "";

                if (m_rs1.getString("ndb_user_rms").equals("ACTVIVE")) {
                    piv_systems = "RMS & PDC , ";
                }
                if (m_rs1.getString("ndb_user_poms").equals("ACTVIVE")) {
                    piv_systems = piv_systems + "POMS , ";
                }
                if (m_rs1.getString("ndb_user_um").equals("ACTVIVE")) {
                    piv_systems = piv_systems + "User Management";
                }

                m_strData = m_strData + "<td>" + piv_systems + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("idndb_user_levels") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_user_status") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getUserLevelsData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT * From ndb_user_levels";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_user_levels") + ">" + m_rs1.getString("idndb_user_levels") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_user_level") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_user_level_status") + "</td>";

                m_strData = m_strData + "</tr>";
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

    public String getBranchData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
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

            m_strQry = "SELECT idndb_branch_master_file,branch_id,branch_name,idndb_bank_master_file,branch_status,branch_approval"
                    + " FROM ndb_branch_master_file";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                String m_idndb_bank_master_file = m_rs1.getString("idndb_bank_master_file");

                m_strQry2 = "SELECT *"
                        + " FROM ndb_bank_master_file where idndb_bank_master_file='" + m_idndb_bank_master_file
                        + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String m_bank_name = "";
                if (m_rs2.next()) {
                    m_bank_name = m_rs2.getString("bank_name");

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_branch_master_file") + ">" + m_rs1.getString("idndb_branch_master_file") + "</td>";
                m_strData = m_strData + "<td>" + m_bank_name + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("branch_id") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("branch_name") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("branch_status") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("branch_approval") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getUnAuthBranchData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
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

            m_strQry = "SELECT idndb_branch_master_file,branch_id,branch_name,idndb_bank_master_file,branch_status,branch_approval,branch_mod_by,branch_mod_date"
                    + " FROM ndb_branch_master_file where branch_approval in ('UN-AUTHORIZED','REJECTED')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                String m_idndb_bank_master_file = m_rs1.getString("idndb_bank_master_file");

                m_strQry2 = "SELECT *"
                        + " FROM ndb_bank_master_file where idndb_bank_master_file='" + m_idndb_bank_master_file
                        + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String m_bank_name = "";
                if (m_rs2.next()) {
                    m_bank_name = m_rs2.getString("bank_name");

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_branch_master_file") + ">" + m_rs1.getString("idndb_branch_master_file") + "</td>";
                m_strData = m_strData + "<td>" + m_bank_name + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("branch_id") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("branch_name") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("branch_status") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("branch_approval") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("branch_mod_by") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("branch_mod_date") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getUserFormsData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
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

            m_strQry = "SELECT * From ndb_user_forms";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_user_forms") + ">" + m_rs1.getString("idndb_user_forms") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_form_name") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_form_path") + "</td>";
                String m_attched_system = "";
                if (m_rs1.getString("ndb_user_system_group").equals("RMSACTIVE")) {
                    m_attched_system = "RMS & PDC";
                }
                if (m_rs1.getString("ndb_user_system_group").equals("POMSACTIVE")) {
                    m_attched_system = "POMS";
                }
                if (m_rs1.getString("ndb_user_system_group").equals("UMACTIVE")) {
                    m_attched_system = "User Management";
                }
                String m_ndb_form_main_menu_id_name = "";

                m_strQry2 = "SELECT * From ndb_user_forms where idndb_user_forms ='" + m_rs1.getString("ndb_form_main_menu_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry);
                if (m_rs2.next()) {
                    m_ndb_form_main_menu_id_name = m_rs1.getString("ndb_form_name");
                }

                m_strData = m_strData + "<td>" + m_attched_system + "</td>";
                m_strData = m_strData + "<td>" + m_ndb_form_main_menu_id_name + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_form_oder_by") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("ndb_form_status") + "</td>";

                m_strData = m_strData + "</tr>";
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

    public String getReturnFileData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
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

            m_strQry = "SELECT creat_date_time,creat_by,rtn_fileid, COUNT(*) FROM ndb_return_file_upload GROUP BY rtn_fileid ORDER BY creat_date_time DESC LIMIT 5";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            int i = 0;
            while (m_rs1.next()) {
                i++;
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td>" + i + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("creat_date_time") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("rtn_fileid") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("COUNT(*)") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("creat_by") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getAdditionalDayData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
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

            m_strQry = "SELECT creat_date_time,creat_by,addit_file_id, COUNT(*) as tot_count FROM ndb_additional_day_file GROUP BY addit_file_id ORDER BY creat_date_time DESC LIMIT 5";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            int i = 0;
            while (m_rs1.next()) {
                i++;
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td>" + i + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("creat_date_time") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("addit_file_id") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("tot_count") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("creat_by") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getIndustryData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT idndb_industry_master_file,indus_id,indus_des,indus_status"
                    + " FROM ndb_industry_master_file";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_industry_master_file") + ">" + m_rs1.getString("idndb_industry_master_file") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("indus_id") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("indus_des") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("indus_status") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getGeoMarketData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT idndb_geo_market_master_file,geo_market_id,geo_market_desc,geo_market_status"
                    + " FROM ndb_geo_market_master_file";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_geo_market_master_file") + ">" + m_rs1.getString("idndb_geo_market_master_file") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("geo_market_id") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("geo_market_desc") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("geo_market_status") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getCustomerDifineData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT *"
                    + " FROM ndb_customer_define";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_customer_define") + ">" + m_rs1.getString("idndb_customer_define") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_id") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_name") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_address") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_contact_number") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_status") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_auth") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getCustomerDifineUnAuthData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
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

            m_strQry = "SELECT *"
                    + " FROM ndb_customer_define where cust_auth in ('UN-AUTHORIZED','REJECTED')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_customer_define") + "' and ndb_change_log_type='DEFINECUSTOMER' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }

                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_customer_define") + ">" + m_rs1.getString("idndb_customer_define") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_id") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_name") + "</td>";
                m_strData = m_strData + "<td>" + modification + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_auth") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_mod_by") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_mod_date") + "</td>";
                m_strData = m_strData + "<td><div class=\"controls\"><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + m_rs1.getString("idndb_customer_define") + "\"/></div><td>";
                m_strData = m_strData + "</tr>";
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

    public String getCustomerProdMapData(String search_criteria) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
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

            m_strQry = "SELECT\n"
                    + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                    + "     ndb_cust_prod_map.`idndb_customer_define` AS ndb_cust_prod_map_idndb_customer_define,\n"
                    + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
                    + "     ndb_customer_define.`cust_id` AS ndb_customer_define_cust_id,\n"
                    + "     ndb_customer_define.`cust_name` AS ndb_customer_define_cust_name,\n"
                    + "     ndb_cust_prod_map.`prod_relationship_key_seller` AS ndb_cust_prod_map_prod_relationship_key_seller,\n"
                    + "     ndb_cust_prod_map.`prod_relationship_key_buyer` AS ndb_cust_prod_map_prod_relationship_key_buyer,\n"
                    + "     ndb_cust_prod_map.`prod_relationship_status` AS ndb_cust_prod_map_prod_relationship_status,\n"
                    + "     ndb_cust_prod_map.`prod_relationship_auth` AS ndb_cust_prod_map_prod_relationship_auth,\n"
                    + "     ndb_cust_prod_map.`prod_relationship_res_fin` AS ndb_cust_prod_map_prod_relationship_res_fin,\n"
                    + "     ndb_cust_prod_map.`prod_relationship_chq_ware` AS ndb_cust_prod_map_prod_relationship_chq_ware,\n"
                    + "     ndb_cust_prod_map.`prod_relationship_reship_only` AS ndb_cust_prod_map_prod_relationship_reship_only,\n"
                    + "     ndb_cust_prod_map.`prod_relationship_creat_by` AS ndb_cust_prod_map_prod_relationship_creat_by,\n"
                    + "     ndb_cust_prod_map.`prod_relationship_creat_date` AS ndb_cust_prod_map_prod_relationship_creat_date,\n"
                    + "     ndb_cust_prod_map.`prod_relationship_mod_by` AS ndb_cust_prod_map_prod_relationship_mod_by,\n"
                    + "     ndb_cust_prod_map.`prod_relationship_mod_date` AS ndb_cust_prod_map_prod_relationship_mod_date\n"
                    + "FROM\n"
                    + "     `ndb_customer_define` ndb_customer_define INNER JOIN `ndb_cust_prod_map` ndb_cust_prod_map ON ndb_customer_define.`idndb_customer_define` = ndb_cust_prod_map.`idndb_customer_define` where ndb_customer_define.`cust_id` like '%" + search_criteria + "%' or  ndb_customer_define.`cust_name` like '%" + search_criteria + "%';";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                String m_idndb_customer_define = m_rs1.getString("ndb_customer_define_idndb_customer_define");

                m_strQry2 = "SELECT * FROM ndb_customer_define where idndb_customer_define='" + m_idndb_customer_define + "' where cust_status='ACTIVE' and cust_auth='AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String m_cust_id = "";
                String m_cust_name = "";
                String m_cust_prod_rel = "";
                boolean data_load_customer = false;

                if (m_rs2.next()) {
                    m_cust_id = m_rs2.getString("cust_id");
                    m_cust_name = m_rs2.getString("cust_name");
                    data_load_customer = true;

                }

                String m_cust_bs = "";
                if (m_rs1.getString("ndb_cust_prod_map_prod_relationship_key_seller").equals("ACTIVE")) {
                    m_cust_bs = "SELLER";
                }

                if (m_rs1.getString("ndb_cust_prod_map_prod_relationship_key_buyer").equals("ACTIVE")) {
                    m_cust_bs = "BUYER";
                    m_cust_prod_rel = "Relationship Only";
                }

                if (m_rs1.getString("ndb_cust_prod_map_prod_relationship_res_fin").equals("ACTIVE")) {
                    m_cust_prod_rel = "Receivable Finance";
                }

                if (m_rs1.getString("ndb_cust_prod_map_prod_relationship_chq_ware").equals("ACTIVE")) {
                    m_cust_prod_rel = "Cheque Warehousing, " + m_cust_prod_rel;
                }

                if (data_load_customer) {

                    //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                    m_strData = m_strData + "<tr>";
                    m_strData = m_strData + "<td id=" + m_rs1.getString("ndb_cust_prod_map_idndb_cust_prod_map") + ">" + m_rs1.getString("ndb_cust_prod_map_idndb_cust_prod_map") + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_id + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_name + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_bs + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_prod_rel + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("ndb_cust_prod_map_prod_relationship_status") + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("ndb_cust_prod_map_prod_relationship_auth") + "</td>";
                    m_strData = m_strData + "</tr>";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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

    public String getCustomerProdMapUnauthData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
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

            m_strQry = "SELECT * FROM ndb_cust_prod_map where prod_relationship_auth in ('UN-AUTHORIZED','REJECTED')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                String m_idndb_customer_define = m_rs1.getString("idndb_customer_define");

                m_strQry2 = "SELECT *"
                        + " FROM ndb_customer_define where idndb_customer_define='" + m_idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String m_cust_id = "";
                String m_cust_name = "";
                if (m_rs2.next()) {
                    m_cust_id = m_rs2.getString("cust_id");
                    m_cust_name = m_rs2.getString("cust_name");

                }

                String m_cust_bs = "";
                if (m_rs1.getString("prod_relationship_key_seller").equals("ACTIVE")) {
                    m_cust_bs = "SELLER";
                }

                if (m_rs1.getString("prod_relationship_key_buyer").equals("ACTIVE")) {
                    m_cust_bs = "BUYER";
                }

                String m_cust_prod_rel = "";
                if (m_rs1.getString("prod_relationship_res_fin").equals("ACTIVE")) {
                    m_cust_prod_rel = "Receivable Finance";
                }

                if (m_rs1.getString("prod_relationship_chq_ware").equals("ACTIVE")) {
                    m_cust_prod_rel = "Cheque Warehousing, " + m_cust_prod_rel;
                }

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_cust_prod_map") + "' and ndb_change_log_type='CUSTPRODMAP' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_cust_prod_map") + ">" + m_rs1.getString("idndb_cust_prod_map") + "</td>";
                m_strData = m_strData + "<td>" + m_cust_id + "</td>";
                m_strData = m_strData + "<td>" + m_cust_name + "</td>";
                m_strData = m_strData + "<td>" + m_cust_bs + "</td>";
                m_strData = m_strData + "<td>" + m_cust_prod_rel + "</td>";
                m_strData = m_strData + "<td>" + modification + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("prod_relationship_auth") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("prod_relationship_mod_by") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("prod_relationship_mod_date") + "</td>";
                m_strData = m_strData + "<td><div class=\"controls\"><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + m_rs1.getString("idndb_cust_prod_map") + "\"/></div><td>";

                m_strData = m_strData + "</tr>";
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

    public String getCustomerRelationEstblishData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
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

            m_strQry = "SELECT *"
                    + " FROM ndb_seller_has_buyers";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                String idndb_customer_define_seller = m_rs1.getString("idndb_customer_define_seller");
                String idndb_customer_define_buyer = m_rs1.getString("idndb_customer_define_buyer");

                m_strQry2 = "SELECT * FROM ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String m_cust_idndb_customer_define = "";
                String m_cust_id_seller = "";
                String m_cust_name_seller = "";
                if (m_rs2.next()) {
                    m_cust_idndb_customer_define = m_rs2.getString("idndb_customer_define");

                }

                m_strQry2 = "SELECT * FROM ndb_customer_define where idndb_customer_define='" + m_cust_idndb_customer_define + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    m_cust_id_seller = m_rs2.getString("cust_id");
                    m_cust_name_seller = m_rs2.getString("cust_name");

                }

                m_strQry2 = "SELECT * FROM ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_buyer + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String m_cust_idndb_customer_define2 = "";
                String m_cust_id_buyer = "";
                String m_cust_name_buyer = "";
                if (m_rs2.next()) {
                    m_cust_idndb_customer_define2 = m_rs2.getString("idndb_customer_define");

                }

                m_strQry2 = "SELECT * FROM ndb_customer_define where idndb_customer_define='" + m_cust_idndb_customer_define2 + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    m_cust_id_buyer = m_rs2.getString("cust_id");
                    m_cust_name_buyer = m_rs2.getString("cust_name");

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_seller_has_buyers") + ">" + m_rs1.getString("idndb_seller_has_buyers") + "</td>";
                m_strData = m_strData + "<td>" + m_cust_id_seller + " ," + m_cust_name_seller + "</td>";
                m_strData = m_strData + "<td>" + m_cust_id_buyer + " ," + m_cust_name_buyer + "</td>";

                String product = "";
                if (m_rs1.getString("sl_has_byr_prorm_type").equals("RF")) {
                    product = "Receivable Finance";
                }

                if (m_rs1.getString("sl_has_byr_prorm_type").equals("CW")) {
                    product = "Cheque Warehousing";
                }

                m_strData = m_strData + "<td>" + product + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("sl_has_byr_status") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("sl_has_byr_auth") + "</td>";
                m_strData = m_strData + "</tr>";
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

    public String getCustomerRelationEstblishUnAuthData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
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

            m_strQry = "SELECT *FROM ndb_seller_has_buyers where sl_has_byr_auth in ('UN-AUTHORIZED','REJECTED')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                String idndb_customer_define_seller = m_rs1.getString("idndb_customer_define_seller");
                String idndb_customer_define_buyer = m_rs1.getString("idndb_customer_define_buyer");

                m_strQry2 = "SELECT * FROM ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String m_cust_idndb_customer_define = "";
                String m_cust_id_seller = "";
                String m_cust_name_seller = "";
                if (m_rs2.next()) {
                    m_cust_idndb_customer_define = m_rs2.getString("idndb_customer_define");

                }

                m_strQry2 = "SELECT * FROM ndb_customer_define where idndb_customer_define='" + m_cust_idndb_customer_define + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    m_cust_id_seller = m_rs2.getString("cust_id");
                    m_cust_name_seller = m_rs2.getString("cust_name");

                }

                m_strQry2 = "SELECT * FROM ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_buyer + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String m_cust_idndb_customer_define2 = "";
                String m_cust_id_buyer = "";
                String m_cust_name_buyer = "";
                if (m_rs2.next()) {
                    m_cust_idndb_customer_define2 = m_rs2.getString("idndb_customer_define");

                }

                m_strQry2 = "SELECT * FROM ndb_customer_define where idndb_customer_define='" + m_cust_idndb_customer_define2 + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    m_cust_id_buyer = m_rs2.getString("cust_id");
                    m_cust_name_buyer = m_rs2.getString("cust_name");

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_seller_has_buyers") + ">" + m_rs1.getString("idndb_seller_has_buyers") + "</td>";
                m_strData = m_strData + "<td>" + m_cust_id_seller + " ," + m_cust_name_seller + "</td>";
                m_strData = m_strData + "<td>" + m_cust_id_buyer + " ," + m_cust_name_buyer + "</td>";

                String product = "";
                if (m_rs1.getString("sl_has_byr_prorm_type").equals("RF")) {
                    product = "Receivable Finance";
                }

                if (m_rs1.getString("sl_has_byr_prorm_type").equals("CW")) {
                    product = "Cheque Warehousing";
                }

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_customer_define") + "' and ndb_change_log_type='DEFINECUSTOMER' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }

                m_strData = m_strData + "<td>" + product + "</td>";

                m_strData = m_strData + "<td>" + modification + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("sl_has_byr_auth") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("sl_has_byr_mod_by") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("sl_has_byr_mod_date") + "</td>";
                m_strData = m_strData + "<td><div class=\"controls\"><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + m_rs1.getString("idndb_seller_has_buyers") + "\"/></div><td>";
                m_strData = m_strData + "</tr>";
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
