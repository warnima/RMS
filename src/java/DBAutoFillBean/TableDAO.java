/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAutoFillBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private PreparedStatement _prepStmnt = null;
    private PreparedStatement _prepStmnt2 = null;
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

    public String getPDCVerificationSummeryData(String m_system_date) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "select\n"
                    + "\n"
                    + "count(npcv.idndb_pdc_txn_master) as chq_count,\n"
                    + "npcv.pdc_verify_user as pdc_verfy_user,\n"
                    + "SUM(nptm.pdc_chq_amu) as total_chq_amount\n"
                    + "\n"
                    + "from\n"
                    + "\n"
                    + "ndb_pdc_chq_verification npcv,\n"
                    + "ndb_pdc_txn_master nptm\n"
                    + "\n"
                    + "\n"
                    + "where npcv.pdc_verify_date='" + m_system_date + "'\n"
                    + "and nptm.idndb_pdc_txn_master =npcv.idndb_pdc_txn_master\n"
                    + "group by npcv.pdc_verify_user";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            DecimalFormat df = new DecimalFormat("#,###.00");
            int m_user_count = 1;
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td>" + m_user_count + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_verfy_user") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("chq_count") + "</td>";
                String total_chq_amount = m_rs1.getString("total_chq_amount");
                if (total_chq_amount == null) {
                    total_chq_amount = "0";
                }

                m_strData = m_strData + "<td>" + df.format(Double.parseDouble(total_chq_amount)) + "</td>";

                m_strData = m_strData + "</tr>";
                m_user_count++;
            }

            m_strQry = "select\n"
                    + "\n"
                    + "count(npcv.idndb_pdc_txn_master) as chq_count,\n"
                    + "npcv.pdc_verify_user as pdc_verfy_user,\n"
                    + "SUM(nptm.pdc_chq_amu) as total_chq_amount\n"
                    + "\n"
                    + "from\n"
                    + "\n"
                    + "ndb_pdc_chq_verification npcv,\n"
                    + "ndb_pdc_txn_master nptm\n"
                    + "\n"
                    + "\n"
                    + "where npcv.pdc_verify_date='" + m_system_date + "'\n"
                    + "and nptm.idndb_pdc_txn_master =npcv.idndb_pdc_txn_master";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            if (m_rs1.next()) {
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td> </td>";
                m_strData = m_strData + "<td>Verified Cheques Total</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("chq_count") + "</td>";
                String total_chq_amount = m_rs1.getString("total_chq_amount");
                if (total_chq_amount == null) {
                    total_chq_amount = "0";
                }

                m_strData = m_strData + "<td>" + df.format(Double.parseDouble(total_chq_amount)) + "</td>";

                m_strData = m_strData + "</tr>";

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

    public String getPDCNotVerifiedChequeSummeryData(String m_system_date) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "select\n"
                    + "ncd.cust_name as seller_name,\n"
                    + "ncd1.cust_name as buyer_name,\n"
                    + "nptm.pdc_chq_number as pdc_chq_number,\n"
                    + "nptm.pdc_chq_amu as pdc_chq_amu,\n"
                    + "nptm.pdc_bank_code as pdc_bank_code,\n"
                    + "nptm.pdc_branch_code as pdc_branch_code,\n"
                    + "nptm.pdc_value_date as pdc_value_date\n"
                    + "from\n"
                    + "ndb_pdc_txn_master nptm,\n"
                    + "ndb_seller_has_buyers nshb,\n"
                    + "ndb_cust_prod_map ncpm,\n"
                    + "ndb_cust_prod_map ncpm1,\n"
                    + "ndb_customer_define ncd,\n"
                    + "ndb_customer_define ncd1\n"
                    + "\n"
                    + "where\n"
                    + "\n"
                    + "(nptm.idndb_pdc_txn_master not in (select idndb_pdc_txn_master from ndb_pdc_chq_verification where pdc_verify_date='" + m_system_date + "')) and STR_TO_DATE(nptm.pdc_value_date, '%d/%m/%Y') >= STR_TO_DATE('" + m_system_date + "', '%d/%m/%Y')and STR_TO_DATE(nptm.pdc_booking_date, '%d/%m/%Y') != STR_TO_DATE('" + m_system_date + "', '%d/%m/%Y') and\n"
                    + "nptm.idndb_customer_define_buyer_id =nshb.idndb_seller_has_buyers and\n"
                    + "ncpm.idndb_cust_prod_map=nshb.idndb_customer_define_seller and\n"
                    + "ncpm1.idndb_cust_prod_map=nshb.idndb_customer_define_buyer and\n"
                    + "ncd.idndb_customer_define=ncpm.idndb_customer_define and\n"
                    + "ncd1.idndb_customer_define=ncpm1.idndb_customer_define and\n"
                    + "nptm.pdc_chq_status = 'ACTIVE' and nptm.pdc_chq_status_auth = 'AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            System.out.println(m_strQry);
            DecimalFormat df = new DecimalFormat("#,###.00");
            int m_user_count = 1;
            while (m_rs1.next()) {
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td>" + m_user_count + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("seller_name") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_number") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_bank_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_branch_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_value_date") + "</td>";

                String pdc_chq_amu = m_rs1.getString("pdc_chq_amu");
                if (pdc_chq_amu == null) {
                    pdc_chq_amu = "0";
                }

                m_strData = m_strData + "<td>" + df.format(Double.parseDouble(pdc_chq_amu)) + "</td>";
                m_strData = m_strData + "</tr>";
                m_user_count++;
            }

            m_strQry = "select\n"
                    + "SUM(pdc_chq_amu) as total_chq_amount,\n"
                    + "count(idndb_pdc_txn_master) as chq_count\n"
                    + "from ndb_pdc_txn_master where (idndb_pdc_txn_master not in (select idndb_pdc_txn_master from ndb_pdc_chq_verification where pdc_verify_date='" + m_system_date + "')) and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') >= STR_TO_DATE('" + m_system_date + "', '%d/%m/%Y') and STR_TO_DATE(pdc_booking_date, '%d/%m/%Y') != STR_TO_DATE('" + m_system_date + "', '%d/%m/%Y') and pdc_chq_status = 'ACTIVE' and pdc_chq_status_auth = 'AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);

            if (m_rs1.next()) {
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td> </td>";
                m_strData = m_strData + "<td> </td>";
                m_strData = m_strData + "<td> </td>";
                m_strData = m_strData + "<td> </td>";
                m_strData = m_strData + "<td> Verified Cheques Total</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("chq_count") + "</td>";
                m_strData = m_strData + "<td>" + df.format(Double.parseDouble(m_rs1.getString("total_chq_amount"))) + "</td>";
                m_strData = m_strData + "</tr>";

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

    public String getHolidayData(String user_id) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT * FROM ndb_holiday_marker order by ndb_holiday ASC";
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
            DecimalFormat df = new DecimalFormat("#,###.00");
            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status ='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*'";
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

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status ='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED','AUTHORIZED')";
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

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status in ('ACTIVE','ERLYLIQUDED') and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*'";
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

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_pdc_txn_master") + "' and ndb_change_log_type='PDCTXN' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_number") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_bank_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_branch_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_value_date") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_amu") + "</td>";
                m_strData = m_strData + "<td>" + modification + "</td>";
                // m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_status_auth") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_mod_by") + "</br>" + m_rs1.getString("pdc_chq_mod_date") + "</td>";
                // m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_mod_date") + "</td>";
                m_strData = m_strData + "<td><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + m_rs1.getString("idndb_pdc_txn_master") + "\"/></td>";
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

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status ='ACTIVE' and pdc_req_financing='RF' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*'";
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

    public String getPdcVdeElqUnauthData(String user_id) {
        String m_strData = "";
        String m_strQry = "";

        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        try {
            m_strQry = "SELECT \n"
                    + "nptm_txn_master.* ,\n"
                    + "nptm_txn_master.idndb_pdc_txn_master,\n"
                    + "nptm_txn_master.pdc_chq_amu,\n"
                    + "nptm_txn_master.pdc_chq_number,\n"
                    + "nptm_txn_master.pdc_chq_status,\n"
                    + "nshb_seller_has_buyers.shb_facty_det_tenor,\n"
                    + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                    + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                    + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                    + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                    + "FROM \n"
                    + "ndb_pdc_txn_master nptm_txn_master inner join\n"
                    + "ndb_seller_has_buyers nshb_seller_has_buyers on \n"
                    + "nptm_txn_master.idndb_customer_define_buyer_id = nshb_seller_has_buyers.idndb_seller_has_buyers inner join\n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_seller on\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_seller = ncpm_cust_prod_map_seller.idndb_cust_prod_map inner join\n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
                    + "ndb_customer_define ncd_cust_define_seller on\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define =ncd_cust_define_seller.idndb_customer_define inner join\n"
                    + "ndb_customer_define ncd_cust_define_buyer on\n"
                    + "ncpm_cust_prod_map_buyer.idndb_customer_define =ncd_cust_define_buyer.idndb_customer_define where\n"
                    + "nptm_txn_master.pdc_chq_status in ('ACTIVE','ERLYLIQUDED') and nptm_txn_master.pdc_chq_status_auth in ('UN-AUTHORIZED') and nptm_txn_master.pdc_latest_modification in ('VDE','ELQ','UPD') ";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();

            log.info(_prepStmnt);

            while (_rs.next()) {
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + _rs.getString("idndb_pdc_txn_master") + ">" + _rs.getString("idndb_pdc_txn_master") + "</td>";
                m_strData = m_strData + "<td>" + ((_rs.getString("pdc_req_financing").equals("RF")) ? "RF" : "CW") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("seller_cust_id") + "|" + _rs.getString("seller_cust_name") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("buyer_cust_id") + "|" + _rs.getString("buyer_cust_name") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("pdc_chq_number") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("pdc_bank_code") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("pdc_branch_code") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("pdc_value_date") + "</td>";
                m_strData = m_strData + "<td>" + df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))) + "</td>";
                String m_status = "NA";
                if (_rs.getString("pdc_latest_modification").equals("VDE")) {
                    m_status = "VALUE DATE EXTENSION";
                }
                if (_rs.getString("pdc_latest_modification").equals("ELQ")) {
                    m_status = "EARLY LIQUIDATION";
                }
                m_strData = m_strData + "<td>" + m_status + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("pdc_chq_status_auth") + "</td>";
                m_strData = m_strData + "</tr>";
            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (_rs != null) {
                    _rs.close();
                }

                if (_prepStmnt != null) {
                    _prepStmnt.close();
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

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status in ('ACTIVE','ERLYLIQUDED') and pdc_req_financing='RF' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*'";
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
                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_pdc_txn_master") + "' and ndb_change_log_type='PDCTXN' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_number") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_bank_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_branch_code") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_value_date") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_amu") + "</td>";
                m_strData = m_strData + "<td>" + modification + "</td>";
                //m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_status_auth") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_mod_by") + "</br>" + m_rs1.getString("pdc_chq_mod_date") + "</td>";
                // m_strData = m_strData + "<td>" + m_rs1.getString("pdc_chq_mod_date") + "</td>";
                m_strData = m_strData + "<td><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + m_rs1.getString("idndb_pdc_txn_master") + "\"/></td>";

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
        String m_strQry2 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            m_strQry = "SELECT idndb_bank_master_file,bank_name,bank_code,bank_status,bank_approval,bank_mod_by,bank_mod_date"
                    + " FROM ndb_bank_master_file where bank_approval in ('UN-AUTHORIZED','REJECTED')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_bank_master_file") + "' and ndb_change_log_type='DEFINEBANK' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }
                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_bank_master_file") + ">" + m_rs1.getString("idndb_bank_master_file") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_name") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_code") + "</td>";
                m_strData = m_strData + "<td>" + modification + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_approval") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_mod_by") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("bank_mod_date") + "</td>";
                m_strData = m_strData + "<td><div class=\"controls\"><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + m_rs1.getString("idndb_bank_master_file") + "\"/></div><td>";

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
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {

            m_strQry = "SELECT \n"
                    + "nbmf_branch_file.*,\n"
                    + "nbmf_bank_file.bank_name\n"
                    + "FROM \n"
                    + "ndb_branch_master_file  nbmf_branch_file inner join\n"
                    + "ndb_bank_master_file nbmf_bank_file on\n"
                    + "nbmf_branch_file.idndb_bank_master_file = nbmf_bank_file.idndb_bank_master_file";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + _rs.getString("idndb_branch_master_file") + ">" + _rs.getString("idndb_branch_master_file") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("bank_name") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("branch_id") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("branch_name") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("branch_status") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("branch_approval") + "</td>";
                m_strData = m_strData + "</tr>";
            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (_rs != null) {
                    _rs.close();
                }
                if (_rs2 != null) {
                    _rs2.close();
                }

                if (_prepStmnt != null) {
                    _prepStmnt.close();
                }
                if (_prepStmnt2 != null) {
                    _prepStmnt2.close();
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
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {

            m_strQry = "SELECT \n"
                    + "nbmf_branch_file.*,\n"
                    + "nbmf_bank_file.bank_name\n"
                    + "FROM \n"
                    + "ndb_branch_master_file  nbmf_branch_file inner join\n"
                    + "ndb_bank_master_file nbmf_bank_file on\n"
                    + "nbmf_branch_file.idndb_bank_master_file = nbmf_bank_file.idndb_bank_master_file\n"
                    + "where \n"
                    + "nbmf_branch_file.branch_approval in ('UN-AUTHORIZED','REJECTED')";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + _rs.getString("idndb_branch_master_file") + "' and ndb_change_log_type='DEFINEBRANCH' and status='UN-AUTHORIZED'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                String modification = "";
                while (_rs2.next()) {
                    modification = modification + _rs2.getString("ndb_change") + "</br>";

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + _rs.getString("idndb_branch_master_file") + ">" + _rs.getString("idndb_branch_master_file") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("bank_name") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("branch_id") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("branch_name") + "</td>";
                m_strData = m_strData + "<td>" + modification + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("branch_approval") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("branch_mod_by") + "</td>";
                m_strData = m_strData + "<td>" + _rs.getString("branch_mod_date") + "</td>";
                m_strData = m_strData + "<td><div class=\"controls\"><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + _rs.getString("idndb_branch_master_file") + "\"/></div><td>";

                m_strData = m_strData + "</tr>";
            }

        } catch (Exception e) {
            m_strData = "";
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }

                if (_rs != null) {
                    _rs.close();
                }

                if (_rs2 != null) {
                    _rs2.close();
                }
                if (_prepStmnt != null) {
                    _prepStmnt.close();
                }

                if (_prepStmnt2 != null) {
                    _prepStmnt2.close();
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

            m_strQry = "SELECT creat_date_time,creat_by,rtn_fileid, COUNT(*) FROM ndb_return_file_upload GROUP BY rtn_fileid";
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

    public String getCustomerDifineData(String getCustomerDifineData) {
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
            if (getCustomerDifineData == null) {
                getCustomerDifineData = "";
            }

            m_strQry = "SELECT * FROM ndb_customer_define where cust_id LIKE '%" + getCustomerDifineData + "%' OR cust_name LIKE '%" + getCustomerDifineData + "%' OR cust_short_name LIKE '%" + getCustomerDifineData + "%' OR cust_short_name LIKE '%" + getCustomerDifineData + "%' OR cust_status LIKE '%" + getCustomerDifineData + "%' OR cust_auth LIKE '%" + getCustomerDifineData + "%'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_customer_define") + "' and ndb_change_log_type='DEFINECUSTOMER' ORDER BY creat_date DESC LIMIT 1";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                String creat_by = "";
                String creat_date = "";
                String auth_by = "";
                String auth_date = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";
                    creat_by = m_rs2.getString("creat_by");
                    creat_date = m_rs2.getString("creat_date");
                    if (m_rs2.getString("authby") == null) {
                        auth_by = "N/A";
                        auth_date = "N/A";
                    } else {
                        auth_by = m_rs2.getString("authby");
                        auth_date = m_rs2.getString("auth_date");
                    }

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                m_strData = m_strData + "<tr>";
                m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_customer_define") + ">" + m_rs1.getString("idndb_customer_define") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_id") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_name") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_status") + "</td>";
                m_strData = m_strData + "<td>" + m_rs1.getString("cust_auth") + "</td>";
                m_strData = m_strData + "<td>" + modification + "</td>";
                m_strData = m_strData + "<td>" + creat_by + "|" + creat_date + "</td>";
                m_strData = m_strData + "<td>" + auth_by + "|" + auth_date + "</td>";

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
                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_stmnt2 != null) {
                    _stmnt2.close();
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
                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_stmnt2 != null) {
                    _stmnt2.close();
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
                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("ndb_cust_prod_map_idndb_cust_prod_map") + "' and ndb_change_log_type='CUSTPRODMAP' ORDER BY creat_date DESC LIMIT 1";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                String creat_by = "";
                String creat_date = "";
                String auth_by = "";
                String auth_date = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";
                    creat_by = m_rs2.getString("creat_by");
                    creat_date = m_rs2.getString("creat_date");
                    if (m_rs2.getString("authby") == null) {
                        auth_by = "N/A";
                        auth_date = "N/A";
                    } else {
                        auth_by = m_rs2.getString("authby");
                        auth_date = m_rs2.getString("auth_date");
                    }

                }

                String m_idndb_customer_define = m_rs1.getString("ndb_cust_prod_map_idndb_customer_define");

                m_strQry2 = "SELECT * FROM ndb_customer_define where idndb_customer_define='" + m_idndb_customer_define + "' and cust_auth='AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String m_cust_id = "";
                String m_cust_name = "";
                String m_cust_prod_rel = "";
                boolean cust_check = false;

                if (m_rs2.next()) {
                    cust_check = true;
                    m_cust_id = m_rs2.getString("cust_id");
                    m_cust_name = m_rs2.getString("cust_name");

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

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                if (cust_check) {

                    m_strData = m_strData + "<tr>";
                    m_strData = m_strData + "<td id=" + m_rs1.getString("ndb_cust_prod_map_idndb_cust_prod_map") + ">" + m_rs1.getString("ndb_cust_prod_map_idndb_cust_prod_map") + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_id + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_name + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_bs + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_prod_rel + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("ndb_cust_prod_map_prod_relationship_status") + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("ndb_cust_prod_map_prod_relationship_auth") + "</td>";
                    m_strData = m_strData + "<td>" + modification + "</td>";
                    m_strData = m_strData + "<td>" + creat_by + "|" + creat_date + "</td>";
                    m_strData = m_strData + "<td>" + auth_by + "|" + auth_date + "</td>";
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
                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_stmnt2 != null) {
                    _stmnt2.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getCustomerProdMapDataTest(String user_id) {
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

            m_strQry = "SELECT * FROM ndb_cust_prod_map";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_cust_prod_map") + "' and ndb_change_log_type='CUSTPRODMAP' ORDER BY creat_date DESC LIMIT 1";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                String creat_by = "";
                String creat_date = "";
                String auth_by = "";
                String auth_date = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";
                    creat_by = m_rs2.getString("creat_by");
                    creat_date = m_rs2.getString("creat_date");
                    if (m_rs2.getString("authby") == null) {
                        auth_by = "N/A";
                        auth_date = "N/A";
                    } else {
                        auth_by = m_rs2.getString("authby");
                        auth_date = m_rs2.getString("auth_date");
                    }

                }

                String m_idndb_customer_define = m_rs1.getString("idndb_customer_define");

                m_strQry2 = "SELECT * FROM ndb_customer_define where idndb_customer_define='" + m_idndb_customer_define + "' and cust_auth='AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String m_cust_id = "";
                String m_cust_name = "";
                String m_cust_prod_rel = "";
                boolean cust_check = false;

                if (m_rs2.next()) {
                    cust_check = true;
                    m_cust_id = m_rs2.getString("cust_id");
                    m_cust_name = m_rs2.getString("cust_name");

                }

                String m_cust_bs = "";
                if (m_rs1.getString("prod_relationship_key_seller").equals("ACTIVE")) {
                    m_cust_bs = "SELLER";
                }

                if (m_rs1.getString("prod_relationship_key_buyer").equals("ACTIVE")) {
                    m_cust_bs = "BUYER";
                    m_cust_prod_rel = "Relationship Only";
                }

                if (m_rs1.getString("prod_relationship_res_fin").equals("ACTIVE")) {
                    m_cust_prod_rel = "Receivable Finance";
                }

                if (m_rs1.getString("prod_relationship_chq_ware").equals("ACTIVE")) {
                    m_cust_prod_rel = "Cheque Warehousing, " + m_cust_prod_rel;
                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                if (cust_check) {

                    m_strData = m_strData + "<tr>";
                    m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_cust_prod_map") + ">" + m_rs1.getString("idndb_cust_prod_map") + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_id + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_name + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_bs + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_prod_rel + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("prod_relationship_status") + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("prod_relationship_auth") + "</td>";
                    m_strData = m_strData + "<td>" + modification + "</td>";
                    m_strData = m_strData + "<td>" + creat_by + "|" + creat_date + "</td>";
                    m_strData = m_strData + "<td>" + auth_by + "|" + auth_date + "</td>";
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

            m_strQry = "SELECT * FROM ndb_cust_prod_map where prod_relationship_auth in ('UN-AUTHORIZED')";
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
                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_stmnt2 != null) {
                    _stmnt2.close();
                }
            } catch (Exception e) {
            }

        }
        return m_strData;
    }

    public String getCustomerRelationEstblishData(String search_criteria) {
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
                    + "     ndb_seller_has_buyers.`idndb_seller_has_buyers` AS ndb_seller_has_buyers_idndb_seller_has_buyers,\n"
                    + "     ndb_seller_has_buyers.`sl_has_byr_prorm_type` AS ndb_seller_has_buyers_sl_has_byr_prorm_type,\n"
                    + "     ndb_seller_has_buyers.`sl_has_byr_status` AS ndb_seller_has_buyers_sl_has_byr_status,\n"
                    + "     ndb_seller_has_buyers.`sl_has_byr_auth` AS ndb_seller_has_buyers_sl_has_byr_auth,\n"
                    + "     ndb_seller_has_buyers.`idndb_customer_define_seller` AS ndb_seller_has_buyers_idndb_customer_define_seller,\n"
                    + "     ndb_seller_has_buyers.`idndb_customer_define_buyer` AS ndb_seller_has_buyers_idndb_customer_define_buyer,\n"
                    + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                    + "     ndb_cust_prod_map.`idndb_customer_define` AS ndb_cust_prod_map_idndb_customer_define,\n"
                    + "     ndb_cust_prod_map_A.`idndb_cust_prod_map` AS ndb_cust_prod_map_A_idndb_cust_prod_map,\n"
                    + "     ndb_cust_prod_map_A.`idndb_customer_define` AS ndb_cust_prod_map_A_idndb_customer_define,\n"
                    + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
                    + "     ndb_customer_define.`cust_id` AS ndb_customer_define_cust_id,\n"
                    + "     ndb_customer_define.`cust_name` AS ndb_customer_define_cust_name,\n"
                    + "     ndb_customer_define_A.`idndb_customer_define` AS ndb_customer_define_A_idndb_customer_define,\n"
                    + "     ndb_customer_define_A.`cust_id` AS ndb_customer_define_A_cust_id,\n"
                    + "     ndb_customer_define_A.`cust_name` AS ndb_customer_define_A_cust_name\n"
                    + "FROM\n"
                    + "     `ndb_customer_define` ndb_customer_define INNER JOIN `ndb_cust_prod_map` ndb_cust_prod_map ON ndb_customer_define.`idndb_customer_define` = ndb_cust_prod_map.`idndb_customer_define`\n"
                    + "     INNER JOIN `ndb_seller_has_buyers` ndb_seller_has_buyers ON ndb_cust_prod_map.`idndb_cust_prod_map` = ndb_seller_has_buyers.`idndb_customer_define_seller`\n"
                    + "     INNER JOIN `ndb_cust_prod_map` ndb_cust_prod_map_A ON ndb_seller_has_buyers.`idndb_customer_define_buyer` = ndb_cust_prod_map_A.`idndb_cust_prod_map`\n"
                    + "     INNER JOIN `ndb_customer_define` ndb_customer_define_A ON ndb_cust_prod_map_A.`idndb_customer_define` = ndb_customer_define_A.`idndb_customer_define` WHERE ndb_customer_define.`cust_id` LIKE '%" + search_criteria + "%' or ndb_customer_define.`cust_name` LIKE '%" + search_criteria + "%' or ndb_customer_define_A.`cust_id` LIKE '%" + search_criteria + "%' or ndb_customer_define_A.`cust_name` LIKE '%" + search_criteria + "%'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("ndb_seller_has_buyers_idndb_seller_has_buyers") + "' and ndb_change_log_type='RELESTB' ORDER BY creat_date DESC LIMIT 1";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                String creat_by = "";
                String creat_date = "";
                String auth_by = "";
                String auth_date = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";
                    creat_by = m_rs2.getString("creat_by");
                    creat_date = m_rs2.getString("creat_date");
                    if (m_rs2.getString("authby") == null) {
                        auth_by = "N/A";
                        auth_date = "N/A";
                    } else {
                        auth_by = m_rs2.getString("authby");
                        auth_date = m_rs2.getString("auth_date");
                    }

                }

                String idndb_customer_define_seller = m_rs1.getString("ndb_seller_has_buyers_idndb_customer_define_seller");
                String idndb_customer_define_buyer = m_rs1.getString("ndb_seller_has_buyers_idndb_customer_define_buyer");

                m_strQry2 = "SELECT * FROM ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller + "' and prod_relationship_auth='AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String m_cust_idndb_customer_define = "";
                String m_cust_id_seller = "";
                String m_cust_name_seller = "";
                boolean seller_prod_map = false;
                if (m_rs2.next()) {
                    seller_prod_map = true;
                    m_cust_idndb_customer_define = m_rs2.getString("idndb_customer_define");

                }

                m_strQry2 = "SELECT * FROM ndb_customer_define where idndb_customer_define='" + m_cust_idndb_customer_define + "' and cust_auth='AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                boolean seller_active = false;
                if (m_rs2.next()) {
                    seller_active = true;
                    m_cust_id_seller = m_rs2.getString("cust_id");
                    m_cust_name_seller = m_rs2.getString("cust_name");

                }

                m_strQry2 = "SELECT * FROM ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_buyer + "' and prod_relationship_auth='AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String m_cust_idndb_customer_define2 = "";
                String m_cust_id_buyer = "";
                String m_cust_name_buyer = "";
                boolean buyer_active = false;
                boolean buyer_prod_map = false;
                if (m_rs2.next()) {
                    buyer_prod_map = true;
                    m_cust_idndb_customer_define2 = m_rs2.getString("idndb_customer_define");

                }

                m_strQry2 = "SELECT * FROM ndb_customer_define where idndb_customer_define='" + m_cust_idndb_customer_define2 + "' and cust_auth='AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    buyer_active = true;
                    m_cust_id_buyer = m_rs2.getString("cust_id");
                    m_cust_name_buyer = m_rs2.getString("cust_name");

                }

                //m_strData = m_strData + " <tr><td><a href=\"./authorizeupdater.jsp?id=" + m_rs1.getString("id") + "&desc=" + m_rs1.getString("name") + "&modiby=" + m_rs1.getString("moduser") + "&modidate=" + m_rs1.getString("moddate") + "&change=" + m_rs1.getString("statdescri") + "&doctype=" + m_rs1.getString("doctype") + "\" class=\"popup\">result1</a></td></tr>";
                if (seller_active && buyer_active && seller_prod_map && buyer_prod_map) {

                    m_strData = m_strData + "<tr>";
                    m_strData = m_strData + "<td id=" + m_rs1.getString("ndb_seller_has_buyers_idndb_seller_has_buyers") + ">" + m_rs1.getString("ndb_seller_has_buyers_idndb_seller_has_buyers") + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_id_seller + " ," + m_cust_name_seller + "</td>";
                    m_strData = m_strData + "<td>" + m_cust_id_buyer + " ," + m_cust_name_buyer + "</td>";

                    String product = "";
                    if (m_rs1.getString("ndb_seller_has_buyers_sl_has_byr_prorm_type").equals("RF")) {
                        product = "Receivable Finance";
                    }

                    if (m_rs1.getString("ndb_seller_has_buyers_sl_has_byr_prorm_type").equals("CW")) {
                        product = "Cheque Warehousing";
                    }

                    m_strData = m_strData + "<td>" + product + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("ndb_seller_has_buyers_sl_has_byr_status") + "</td>";
                    m_strData = m_strData + "<td>" + m_rs1.getString("ndb_seller_has_buyers_sl_has_byr_auth") + "</td>";
                    m_strData = m_strData + "<td>" + modification + "</td>";
                    m_strData = m_strData + "<td>" + creat_by + "|" + creat_date + "</td>";
                    m_strData = m_strData + "<td>" + auth_by + "|" + auth_date + "</td>";

                    m_strData = m_strData + "</tr>";
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
                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_stmnt2 != null) {
                    _stmnt2.close();
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
                String idndb_seller_has_buyers = m_rs1.getString("idndb_seller_has_buyers");

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

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + idndb_seller_has_buyers + "' and ndb_change_log_type='RELESTB' and status='UN-AUTHORIZED'";
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

    public String noOfChequesPortfolioReport(String m_system_date) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            DecimalFormat df = new DecimalFormat("#,###.00");
            m_strQry = "select SUM(pdc_chq_amu) as total_pdc_chq_amu, COUNT(idndb_pdc_txn_master) as pdc_chq_count from ndb_pdc_txn_master where pdc_chq_status = 'ACTIVE' and pdc_chq_status_auth = 'AUTHORIZED' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') >= STR_TO_DATE('" + m_system_date + "', '%d/%m/%Y') and STR_TO_DATE(pdc_booking_date, '%d/%m/%Y') != STR_TO_DATE('" + m_system_date + "', '%d/%m/%Y')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            if (m_rs1.next()) {

                String m_total_chq_count = m_rs1.getString("pdc_chq_count");

                if (m_total_chq_count == null) {
                    m_total_chq_count = "0";
                }

                m_strData = m_total_chq_count;

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

    public String noOfChequesVerified(String m_system_date) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            DecimalFormat int_valf = new DecimalFormat("#,###");
            m_strQry = "select count(*) as verfied_cheque_count from ndb_pdc_chq_verification where pdc_verify_date='" + m_system_date + "'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            if (m_rs1.next()) {

                String verfied_cheque_count = m_rs1.getString("verfied_cheque_count");

                if (verfied_cheque_count == null) {
                    verfied_cheque_count = "0";
                }

                m_strData = int_valf.format(Double.parseDouble(verfied_cheque_count));

            }

        } catch (Exception e) {
            e.printStackTrace();
            m_strData = "0";
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

    public String noOfChequesNotVerified(String m_system_date) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            DecimalFormat int_valf = new DecimalFormat("#,###");

            int verfied_cheque_count = 0;
            m_strQry = "select count(*) as verfied_cheque_count from ndb_pdc_chq_verification where pdc_verify_date='" + m_system_date + "'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            if (m_rs1.next()) {
                verfied_cheque_count = Integer.parseInt(m_rs1.getString("verfied_cheque_count"));
            }

            int m_total_chq_count = 0;
            m_strQry = "select SUM(pdc_chq_amu) as total_pdc_chq_amu, COUNT(idndb_pdc_txn_master) as pdc_chq_count from ndb_pdc_txn_master where pdc_chq_status = 'ACTIVE' and pdc_chq_status_auth = 'AUTHORIZED' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') >= STR_TO_DATE('" + m_system_date + "', '%d/%m/%Y') and STR_TO_DATE(pdc_booking_date, '%d/%m/%Y') != STR_TO_DATE('" + m_system_date + "', '%d/%m/%Y')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            if (m_rs1.next()) {
                m_total_chq_count = Integer.parseInt(m_rs1.getString("pdc_chq_count"));
            }
            m_strData = int_valf.format(m_total_chq_count - verfied_cheque_count);
        } catch (Exception e) {
            e.printStackTrace();
            m_strData = "0";
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

    public String chequeAmountChequesPortfolioReport(String m_system_date) {
        //<tr><td><a href=\"./authorizeupdater.jsp?paramid=aabbcc\" class=\"popup\">result1</a></td></tr>
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {
            _stmnt = _currentCon.createStatement();
            DecimalFormat df = new DecimalFormat("#,###.00");
            m_strQry = "select SUM(pdc_chq_amu) as total_pdc_chq_amu, COUNT(idndb_pdc_txn_master) as pdc_chq_count from ndb_pdc_txn_master where pdc_chq_status = 'ACTIVE' and pdc_chq_status_auth = 'AUTHORIZED' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') >= STR_TO_DATE('" + m_system_date + "', '%d/%m/%Y') and STR_TO_DATE(pdc_booking_date, '%d/%m/%Y') != STR_TO_DATE('" + m_system_date + "', '%d/%m/%Y')";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            if (m_rs1.next()) {

                String m_totak_pdc_chq_amount = m_rs1.getString("total_pdc_chq_amu");

                if (m_totak_pdc_chq_amount == null) {
                    m_totak_pdc_chq_amount = "0.00";
                }

                m_strData = df.format(Double.parseDouble(m_totak_pdc_chq_amount));
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
