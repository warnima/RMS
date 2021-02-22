/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAutoFillBean;

import DBAutoFillBeans.*;
import static DBAutoFillBean.TableDAO._connectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
    private Statement _stmnt = null;
    private Statement _stmnt2 = null;
    private Statement _stmnt3 = null;
    private PreparedStatement _prepStmnt = null;
    private PreparedStatement _prepStmnt2 = null;
    private PreparedStatement _prepStmnt3 = null;
    private ResultSet _rs = null;
    private ResultSet _rs2 = null;
    private ResultSet _rs3 = null;
    static Exception _exception;

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
                    + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' ORDER BY cust_name ASC";
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
                    + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' ORDER BY cust_id ASC";
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

//            m_strQry = "SELECT idndb_customer_define,idndb_cust_prod_map"
//                    + " FROM ndb_cust_prod_map where prod_relationship_key_seller='ACTIVE' and prod_relationship_auth='AUTHORIZED'";
//            
//            
            m_strQry = "SELECT\n"
                    + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                    + "     ndb_cust_prod_map.`idndb_customer_define` AS ndb_cust_prod_map_idndb_customer_define,\n"
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
                    + "     ndb_cust_prod_map.`prod_relationship_mod_date` AS ndb_cust_prod_map_prod_relationship_mod_date,\n"
                    + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
                    + "     ndb_customer_define.`cust_id` AS ndb_customer_define_cust_id,\n"
                    + "     ndb_customer_define.`cust_name` AS ndb_customer_define_cust_name,\n"
                    + "     ndb_customer_define.`cust_short_name` AS ndb_customer_define_cust_short_name,\n"
                    + "     ndb_customer_define.`cust_industry_id` AS ndb_customer_define_cust_industry_id,\n"
                    + "     ndb_customer_define.`cust_contact_number` AS ndb_customer_define_cust_contact_number,\n"
                    + "     ndb_customer_define.`cust_fax_number` AS ndb_customer_define_cust_fax_number,\n"
                    + "     ndb_customer_define.`cust_contact_per1` AS ndb_customer_define_cust_contact_per1,\n"
                    + "     ndb_customer_define.`cust_contact_per2` AS ndb_customer_define_cust_contact_per2,\n"
                    + "     ndb_customer_define.`cust_email` AS ndb_customer_define_cust_email,\n"
                    + "     ndb_customer_define.`cust_address` AS ndb_customer_define_cust_address,\n"
                    + "     ndb_customer_define.`rec_finance_acc_num` AS ndb_customer_define_rec_finance_acc_num,\n"
                    + "     ndb_customer_define.`rec_finance_cr_dsc_proc_acc_num` AS ndb_customer_define_rec_finance_cr_dsc_proc_acc_num,\n"
                    + "     ndb_customer_define.`rec_finance_curr_ac` AS ndb_customer_define_rec_finance_curr_ac,\n"
                    + "     ndb_customer_define.`rec_finance_margin_ac` AS ndb_customer_define_rec_finance_margin_ac,\n"
                    + "     ndb_customer_define.`rec_finance_margin` AS ndb_customer_define_rec_finance_margin,\n"
                    + "     ndb_customer_define.`cust_credit_rate` AS ndb_customer_define_cust_credit_rate,\n"
                    + "     ndb_customer_define.`idndb_bank_master_file` AS ndb_customer_define_idndb_bank_master_file,\n"
                    + "     ndb_customer_define.`idndb_branch_master_file` AS ndb_customer_define_idndb_branch_master_file,\n"
                    + "     ndb_customer_define.`cust_other_bank_ac_no` AS ndb_customer_define_cust_other_bank_ac_no,\n"
                    + "     ndb_customer_define.`idndb_geo_market_master_file` AS ndb_customer_define_idndb_geo_market_master_file,\n"
                    + "     ndb_customer_define.`cust_status` AS ndb_customer_define_cust_status,\n"
                    + "     ndb_customer_define.`cust_auth` AS ndb_customer_define_cust_auth,\n"
                    + "     ndb_customer_define.`cust_is_ndb_cust` AS ndb_customer_define_cust_is_ndb_cust,\n"
                    + "     ndb_customer_define.`cust_creat_by` AS ndb_customer_define_cust_creat_by,\n"
                    + "     ndb_customer_define.`cust_creat_date` AS ndb_customer_define_cust_creat_date,\n"
                    + "     ndb_customer_define.`cust_mod_by` AS ndb_customer_define_cust_mod_by,\n"
                    + "     ndb_customer_define.`cust_mod_date` AS ndb_customer_define_cust_mod_date,\n"
                    + "     ndb_customer_define.`cms_curr_acc_number` AS ndb_customer_define_cms_curr_acc_number,\n"
                    + "     ndb_customer_define.`cms_collection_acc_number` AS ndb_customer_define_cms_collection_acc_number\n"
                    + "FROM\n"
                    + "     `ndb_customer_define` ndb_customer_define INNER JOIN `ndb_cust_prod_map` ndb_cust_prod_map ON ndb_customer_define.`idndb_customer_define` = ndb_cust_prod_map.`idndb_customer_define` where ndb_cust_prod_map.`prod_relationship_key_seller`='ACTIVE' and ndb_cust_prod_map.`prod_relationship_status`='ACTIVE' and ndb_cust_prod_map.`prod_relationship_auth`='AUTHORIZED' and ndb_customer_define.`cust_status`='ACTIVE' and ndb_customer_define.`cust_auth`='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT idndb_customer_define,cust_id,cust_name"
                        + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs1.getString("ndb_cust_prod_map_idndb_customer_define") + "' ORDER BY cust_id ASC ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    m_strData = m_strData + "<option id=" + m_rs1.getString("ndb_cust_prod_map_idndb_customer_define") + " value=" + m_rs1.getString("ndb_cust_prod_map_idndb_cust_prod_map") + ">" + m_rs2.getString("cust_id") + "</option>";

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
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {

            m_strQry = "SELECT \n"
                    + "ncpm_cust_prod_map_seller.idndb_cust_prod_map,\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define,\n"
                    + "ncd_cust_define_seller.cust_id,\n"
                    + "ncd_cust_define_seller.cust_name\n"
                    + "FROM \n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_seller inner join\n"
                    + "ndb_customer_define ncd_cust_define_seller on\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define = ncd_cust_define_seller.idndb_customer_define\n"
                    + "where \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_key_seller='ACTIVE' and \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_status='ACTIVE' and \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_auth='AUTHORIZED' and \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_res_fin='ACTIVE' and \n"
                    + "ncd_cust_define_seller.cust_status='ACTIVE' and \n"
                    + "ncd_cust_define_seller.cust_auth='AUTHORIZED'";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {
                boolean buyer_relation = true;
                boolean buyer_cust_define = true;
                boolean cust_prod_map = true;

                String idndb_cust_prod_map = _rs.getString("idndb_cust_prod_map");
                String idndb_customer_define = _rs.getString("idndb_customer_define");

                String cust_id = _rs.getString("cust_id");
                String cust_name = _rs.getString("cust_name");

                m_strQry2 = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers\n"
                        + "FROM \n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers \n"
                        + "where\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_cust_prod_map + "' and\n"
                        + "nshb_seller_has_buyers.sl_has_byr_auth='UN-AUTHORIZED'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {
                    buyer_relation = false;
          
                }

                m_strQry2 = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers\n"
                        + "FROM \n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers inner join \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map where\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_cust_prod_map + "' and\n"
                        + "ncpm_cust_prod_map_buyer.prod_relationship_auth='UN-AUTHORIZED'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {
                    cust_prod_map = false;
               

                }

                m_strQry2 = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers\n"
                        + "FROM \n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers inner join \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
                        + "ndb_customer_define ncd_cust_define_buyer on\n"
                        + "ncpm_cust_prod_map_buyer.idndb_customer_define =  ncd_cust_define_buyer.idndb_customer_define\n"
                        + "where\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_cust_prod_map + "' and\n"
                        + "ncd_cust_define_buyer.cust_auth='UN-AUTHORIZED'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {
                    buyer_cust_define = false;
          
                }

                if (buyer_relation && buyer_cust_define && cust_prod_map) {

                    m_strData = m_strData + "<option id=" + idndb_customer_define + " value=" + idndb_cust_prod_map + ">" + cust_id + "</option>";

                }

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

    public String getCust_Id_ActiveRFSellerDataCmbAll(String user_id) {
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
                    + "ncd_customer_define_seller.idndb_customer_define,\n"
                    + "ncd_customer_define_seller.cust_id,\n"
                    + "ncd_customer_define_seller.cust_name,\n"
                    + "ncpm_cust_prod_map_seller.idndb_cust_prod_map\n"
                    + "FROM \n"
                    + "ndb_seller_has_buyers nshb_seller_has_buyer inner join\n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_seller on\n"
                    + "nshb_seller_has_buyer.idndb_customer_define_seller = ncpm_cust_prod_map_seller.idndb_customer_define inner join\n"
                    + "ndb_customer_define ncd_customer_define_seller on\n"
                    + "nshb_seller_has_buyer.idndb_customer_define_seller=ncd_customer_define_seller.idndb_customer_define\n"
                    + "where \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_key_seller='ACTIVE' group by nshb_seller_has_buyer.idndb_customer_define_seller";

            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                m_strData = m_strData + "<option id=" + m_rs1.getString("idndb_customer_define") + " value=" + m_rs1.getString("idndb_cust_prod_map") + ">" + m_rs1.getString("cust_id") + "</option>";

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

    public String getCust_Id_AllRFSellerDataCmb(String user_id) {
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
                    + "ncp_cust_prod_map.idndb_cust_prod_map AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                    + "ncp_cust_prod_map.idndb_customer_define AS ndb_cust_prod_map_idndb_customer_define \n"
                    + "FROM ndb_customer_define ncd_cust_define\n"
                    + "INNER JOIN ndb_cust_prod_map ncp_cust_prod_map\n"
                    + "ON \n"
                    + "ncd_cust_define.idndb_customer_define = ncp_cust_prod_map.idndb_customer_define \n"
                    + "where \n"
                    + "ncp_cust_prod_map.prod_relationship_key_seller='ACTIVE' and \n"
                    + "ncp_cust_prod_map.prod_relationship_status='ACTIVE' and \n"
                    + "ncp_cust_prod_map.prod_relationship_auth='AUTHORIZED' and \n"
                    + "ncp_cust_prod_map.prod_relationship_res_fin='ACTIVE' and\n"
                    + "ncd_cust_define.cust_status='ACTIVE' and \n"
                    + "ncd_cust_define.cust_auth='AUTHORIZED'";

            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT idndb_customer_define,cust_id,cust_name"
                        + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs1.getString("ndb_cust_prod_map_idndb_customer_define") + "' ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    m_strData = m_strData + "<option id=" + m_rs1.getString("ndb_cust_prod_map_idndb_customer_define") + " value=" + m_rs1.getString("ndb_cust_prod_map_idndb_cust_prod_map") + ">" + m_rs2.getString("cust_id") + "</option>";

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
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {

            m_strQry = "SELECT \n"
                    + "ncpm_cust_prod_map_seller.idndb_cust_prod_map,\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define,\n"
                    + "ncd_cust_define_seller.cust_id,\n"
                    + "ncd_cust_define_seller.cust_name\n"
                    + "FROM \n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_seller inner join\n"
                    + "ndb_customer_define ncd_cust_define_seller on\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define = ncd_cust_define_seller.idndb_customer_define\n"
                    + "where \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_key_seller='ACTIVE' and \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_status='ACTIVE' and \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_auth='AUTHORIZED' and \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_chq_ware='ACTIVE' and \n"
                    + "ncd_cust_define_seller.cust_status='ACTIVE' and \n"
                    + "ncd_cust_define_seller.cust_auth='AUTHORIZED'";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {
                boolean buyer_relation = true;
                boolean buyer_cust_define = true;
                boolean cust_prod_map = true;

                String idndb_cust_prod_map = _rs.getString("idndb_cust_prod_map");
                String idndb_customer_define = _rs.getString("idndb_customer_define");

                String cust_id = _rs.getString("cust_id");
                String cust_name = _rs.getString("cust_name");

                m_strQry2 = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers\n"
                        + "FROM \n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers \n"
                        + "where\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_cust_prod_map + "' and\n"
                        + "nshb_seller_has_buyers.sl_has_byr_auth='UN-AUTHORIZED'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {
                    buyer_relation = false;
             
                }

                m_strQry2 = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers\n"
                        + "FROM \n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers inner join \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map where\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_cust_prod_map + "' and\n"
                        + "ncpm_cust_prod_map_buyer.prod_relationship_auth='UN-AUTHORIZED'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {
                    cust_prod_map = false;
             

                }

                m_strQry2 = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers\n"
                        + "FROM \n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers inner join \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
                        + "ndb_customer_define ncd_cust_define_buyer on\n"
                        + "ncpm_cust_prod_map_buyer.idndb_customer_define =  ncd_cust_define_buyer.idndb_customer_define\n"
                        + "where\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_cust_prod_map + "' and\n"
                        + "ncd_cust_define_buyer.cust_auth='UN-AUTHORIZED'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {
                    buyer_cust_define = false;
                 
                }

                if (buyer_relation && buyer_cust_define && cust_prod_map) {

                    m_strData = m_strData + "<option id=" + idndb_customer_define + " value=" + idndb_cust_prod_map + ">" + cust_id + "</option>";

                }

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

    public String getCust_Id_ActiveCWRFSellerDataCmb(String user_id) {
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
                    + " FROM ndb_cust_prod_map where prod_relationship_key_seller='ACTIVE' and prod_relationship_auth='AUTHORIZED' ";
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

//            m_strQry = "SELECT idndb_customer_define,idndb_cust_prod_map"
//                    + " FROM ndb_cust_prod_map where prod_relationship_key_seller='ACTIVE' and prod_relationship_auth='AUTHORIZED'";
//            
            m_strQry = "SELECT\n"
                    + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                    + "     ndb_cust_prod_map.`idndb_customer_define` AS ndb_cust_prod_map_idndb_customer_define,\n"
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
                    + "     ndb_cust_prod_map.`prod_relationship_mod_date` AS ndb_cust_prod_map_prod_relationship_mod_date,\n"
                    + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
                    + "     ndb_customer_define.`cust_id` AS ndb_customer_define_cust_id,\n"
                    + "     ndb_customer_define.`cust_name` AS ndb_customer_define_cust_name,\n"
                    + "     ndb_customer_define.`cust_short_name` AS ndb_customer_define_cust_short_name,\n"
                    + "     ndb_customer_define.`cust_industry_id` AS ndb_customer_define_cust_industry_id,\n"
                    + "     ndb_customer_define.`cust_contact_number` AS ndb_customer_define_cust_contact_number,\n"
                    + "     ndb_customer_define.`cust_fax_number` AS ndb_customer_define_cust_fax_number,\n"
                    + "     ndb_customer_define.`cust_contact_per1` AS ndb_customer_define_cust_contact_per1,\n"
                    + "     ndb_customer_define.`cust_contact_per2` AS ndb_customer_define_cust_contact_per2,\n"
                    + "     ndb_customer_define.`cust_email` AS ndb_customer_define_cust_email,\n"
                    + "     ndb_customer_define.`cust_address` AS ndb_customer_define_cust_address,\n"
                    + "     ndb_customer_define.`rec_finance_acc_num` AS ndb_customer_define_rec_finance_acc_num,\n"
                    + "     ndb_customer_define.`rec_finance_cr_dsc_proc_acc_num` AS ndb_customer_define_rec_finance_cr_dsc_proc_acc_num,\n"
                    + "     ndb_customer_define.`rec_finance_curr_ac` AS ndb_customer_define_rec_finance_curr_ac,\n"
                    + "     ndb_customer_define.`rec_finance_margin_ac` AS ndb_customer_define_rec_finance_margin_ac,\n"
                    + "     ndb_customer_define.`rec_finance_margin` AS ndb_customer_define_rec_finance_margin,\n"
                    + "     ndb_customer_define.`cust_credit_rate` AS ndb_customer_define_cust_credit_rate,\n"
                    + "     ndb_customer_define.`idndb_bank_master_file` AS ndb_customer_define_idndb_bank_master_file,\n"
                    + "     ndb_customer_define.`idndb_branch_master_file` AS ndb_customer_define_idndb_branch_master_file,\n"
                    + "     ndb_customer_define.`cust_other_bank_ac_no` AS ndb_customer_define_cust_other_bank_ac_no,\n"
                    + "     ndb_customer_define.`idndb_geo_market_master_file` AS ndb_customer_define_idndb_geo_market_master_file,\n"
                    + "     ndb_customer_define.`cust_status` AS ndb_customer_define_cust_status,\n"
                    + "     ndb_customer_define.`cust_auth` AS ndb_customer_define_cust_auth,\n"
                    + "     ndb_customer_define.`cust_is_ndb_cust` AS ndb_customer_define_cust_is_ndb_cust,\n"
                    + "     ndb_customer_define.`cust_creat_by` AS ndb_customer_define_cust_creat_by,\n"
                    + "     ndb_customer_define.`cust_creat_date` AS ndb_customer_define_cust_creat_date,\n"
                    + "     ndb_customer_define.`cust_mod_by` AS ndb_customer_define_cust_mod_by,\n"
                    + "     ndb_customer_define.`cust_mod_date` AS ndb_customer_define_cust_mod_date,\n"
                    + "     ndb_customer_define.`cms_curr_acc_number` AS ndb_customer_define_cms_curr_acc_number,\n"
                    + "     ndb_customer_define.`cms_collection_acc_number` AS ndb_customer_define_cms_collection_acc_number\n"
                    + "FROM\n"
                    + "     `ndb_customer_define` ndb_customer_define INNER JOIN `ndb_cust_prod_map` ndb_cust_prod_map ON ndb_customer_define.`idndb_customer_define` = ndb_cust_prod_map.`idndb_customer_define` where ndb_cust_prod_map.`prod_relationship_key_seller`='ACTIVE' and ndb_cust_prod_map.`prod_relationship_status`='ACTIVE' and ndb_cust_prod_map.`prod_relationship_auth`='AUTHORIZED' and ndb_customer_define.`cust_status`='ACTIVE' and ndb_customer_define.`cust_auth`='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);

            while (m_rs1.next()) {

                m_strQry2 = "SELECT idndb_customer_define,cust_id,cust_name"
                        + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs1.getString("ndb_cust_prod_map_idndb_customer_define") + "' ORDER BY cust_name ASC";

                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    m_strData = m_strData + "<option id=" + m_rs1.getString("ndb_cust_prod_map_idndb_customer_define") + " value=" + m_rs1.getString("ndb_cust_prod_map_idndb_cust_prod_map") + ">" + m_rs2.getString("cust_name") + "</option>";

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
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {

            m_strQry = "SELECT \n"
                    + "ncpm_cust_prod_map_seller.idndb_cust_prod_map,\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define,\n"
                    + "ncd_cust_define_seller.cust_id,\n"
                    + "ncd_cust_define_seller.cust_name\n"
                    + "FROM \n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_seller inner join\n"
                    + "ndb_customer_define ncd_cust_define_seller on\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define = ncd_cust_define_seller.idndb_customer_define\n"
                    + "where \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_key_seller='ACTIVE' and \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_status='ACTIVE' and \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_auth='AUTHORIZED' and \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_res_fin='ACTIVE' and \n"
                    + "ncd_cust_define_seller.cust_status='ACTIVE' and \n"
                    + "ncd_cust_define_seller.cust_auth='AUTHORIZED'";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {
                boolean buyer_relation = true;
                boolean buyer_cust_define = true;
                boolean cust_prod_map = true;

                String idndb_cust_prod_map = _rs.getString("idndb_cust_prod_map");
                String idndb_customer_define = _rs.getString("idndb_customer_define");

                String cust_id = _rs.getString("cust_id");
                String cust_name = _rs.getString("cust_name");

                m_strQry2 = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers\n"
                        + "FROM \n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers \n"
                        + "where\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_cust_prod_map + "' and\n"
                        + "nshb_seller_has_buyers.sl_has_byr_auth='UN-AUTHORIZED'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {
                    buyer_relation = false;
             
                }

                m_strQry2 = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers\n"
                        + "FROM \n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers inner join \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map where\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_cust_prod_map + "' and\n"
                        + "ncpm_cust_prod_map_buyer.prod_relationship_auth='UN-AUTHORIZED'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {
                    cust_prod_map = false;
           

                }

                m_strQry2 = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers\n"
                        + "FROM \n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers inner join \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
                        + "ndb_customer_define ncd_cust_define_buyer on\n"
                        + "ncpm_cust_prod_map_buyer.idndb_customer_define =  ncd_cust_define_buyer.idndb_customer_define\n"
                        + "where\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_cust_prod_map + "' and\n"
                        + "ncd_cust_define_buyer.cust_auth='UN-AUTHORIZED'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {
                    buyer_cust_define = false;
           
                }

                if (buyer_relation && buyer_cust_define && cust_prod_map) {

                    m_strData = m_strData + "<option id=" + idndb_customer_define + " value=" + idndb_cust_prod_map + ">" + cust_name + "</option>";

                }

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

    public String getCust_Name_ActiveRFSellerDataCmbAll(String user_id) {
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
                    + "ncd_customer_define_seller.idndb_customer_define,\n"
                    + "ncd_customer_define_seller.cust_id,\n"
                    + "ncd_customer_define_seller.cust_name,\n"
                    + "ncpm_cust_prod_map_seller.idndb_cust_prod_map\n"
                    + "FROM \n"
                    + "ndb_seller_has_buyers nshb_seller_has_buyer inner join\n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_seller on\n"
                    + "nshb_seller_has_buyer.idndb_customer_define_seller = ncpm_cust_prod_map_seller.idndb_customer_define inner join\n"
                    + "ndb_customer_define ncd_customer_define_seller on\n"
                    + "nshb_seller_has_buyer.idndb_customer_define_seller=ncd_customer_define_seller.idndb_customer_define\n"
                    + "where \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_key_seller='ACTIVE' group by nshb_seller_has_buyer.idndb_customer_define_seller";

            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                m_strData = m_strData + "<option id=" + m_rs1.getString("idndb_customer_define") + " value=" + m_rs1.getString("idndb_cust_prod_map") + ">" + m_rs1.getString("cust_name") + "</option>";

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
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {

            m_strQry = "SELECT \n"
                    + "ncpm_cust_prod_map_seller.idndb_cust_prod_map,\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define,\n"
                    + "ncd_cust_define_seller.cust_id,\n"
                    + "ncd_cust_define_seller.cust_name\n"
                    + "FROM \n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_seller inner join\n"
                    + "ndb_customer_define ncd_cust_define_seller on\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define = ncd_cust_define_seller.idndb_customer_define\n"
                    + "where \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_key_seller='ACTIVE' and \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_status='ACTIVE' and \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_auth='AUTHORIZED' and \n"
                    + "ncpm_cust_prod_map_seller.prod_relationship_chq_ware='ACTIVE' and \n"
                    + "ncd_cust_define_seller.cust_status='ACTIVE' and \n"
                    + "ncd_cust_define_seller.cust_auth='AUTHORIZED'";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {
                boolean buyer_relation = true;
                boolean buyer_cust_define = true;
                boolean cust_prod_map = true;

                String idndb_cust_prod_map = _rs.getString("idndb_cust_prod_map");
                String idndb_customer_define = _rs.getString("idndb_customer_define");

                String cust_id = _rs.getString("cust_id");
                String cust_name = _rs.getString("cust_name");

                m_strQry2 = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers\n"
                        + "FROM \n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers \n"
                        + "where\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_cust_prod_map + "' and\n"
                        + "nshb_seller_has_buyers.sl_has_byr_auth='UN-AUTHORIZED'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {
                    buyer_relation = false;
                   
                }

                m_strQry2 = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers\n"
                        + "FROM \n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers inner join \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map where\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_cust_prod_map + "' and\n"
                        + "ncpm_cust_prod_map_buyer.prod_relationship_auth='UN-AUTHORIZED'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {
                    cust_prod_map = false;
                   

                }

                m_strQry2 = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers\n"
                        + "FROM \n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers inner join \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
                        + "ndb_customer_define ncd_cust_define_buyer on\n"
                        + "ncpm_cust_prod_map_buyer.idndb_customer_define =  ncd_cust_define_buyer.idndb_customer_define\n"
                        + "where\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_cust_prod_map + "' and\n"
                        + "ncd_cust_define_buyer.cust_auth='UN-AUTHORIZED'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {
                    buyer_cust_define = false;
                 
                }

                if (buyer_relation && buyer_cust_define && cust_prod_map) {

                    m_strData = m_strData + "<option id=" + idndb_customer_define + " value=" + idndb_cust_prod_map + ">" + cust_name + "</option>";

                }

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

    public String getCust_Name_ActiveCWRFSellerDataCmb(String user_id) {
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

//            m_strQry = "SELECT idndb_customer_define,idndb_cust_prod_map"
//                    + " FROM ndb_cust_prod_map where prod_relationship_key_buyer='ACTIVE' and prod_relationship_auth='AUTHORIZED'";
//           
            m_strQry = "SELECT\n"
                    + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                    + "     ndb_cust_prod_map.`idndb_customer_define` AS ndb_cust_prod_map_idndb_customer_define,\n"
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
                    + "     ndb_cust_prod_map.`prod_relationship_mod_date` AS ndb_cust_prod_map_prod_relationship_mod_date,\n"
                    + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
                    + "     ndb_customer_define.`cust_id` AS ndb_customer_define_cust_id,\n"
                    + "     ndb_customer_define.`cust_name` AS ndb_customer_define_cust_name,\n"
                    + "     ndb_customer_define.`cust_short_name` AS ndb_customer_define_cust_short_name,\n"
                    + "     ndb_customer_define.`cust_industry_id` AS ndb_customer_define_cust_industry_id,\n"
                    + "     ndb_customer_define.`cust_contact_number` AS ndb_customer_define_cust_contact_number,\n"
                    + "     ndb_customer_define.`cust_fax_number` AS ndb_customer_define_cust_fax_number,\n"
                    + "     ndb_customer_define.`cust_contact_per1` AS ndb_customer_define_cust_contact_per1,\n"
                    + "     ndb_customer_define.`cust_contact_per2` AS ndb_customer_define_cust_contact_per2,\n"
                    + "     ndb_customer_define.`cust_email` AS ndb_customer_define_cust_email,\n"
                    + "     ndb_customer_define.`cust_address` AS ndb_customer_define_cust_address,\n"
                    + "     ndb_customer_define.`rec_finance_acc_num` AS ndb_customer_define_rec_finance_acc_num,\n"
                    + "     ndb_customer_define.`rec_finance_cr_dsc_proc_acc_num` AS ndb_customer_define_rec_finance_cr_dsc_proc_acc_num,\n"
                    + "     ndb_customer_define.`rec_finance_curr_ac` AS ndb_customer_define_rec_finance_curr_ac,\n"
                    + "     ndb_customer_define.`rec_finance_margin_ac` AS ndb_customer_define_rec_finance_margin_ac,\n"
                    + "     ndb_customer_define.`rec_finance_margin` AS ndb_customer_define_rec_finance_margin,\n"
                    + "     ndb_customer_define.`cust_credit_rate` AS ndb_customer_define_cust_credit_rate,\n"
                    + "     ndb_customer_define.`idndb_bank_master_file` AS ndb_customer_define_idndb_bank_master_file,\n"
                    + "     ndb_customer_define.`idndb_branch_master_file` AS ndb_customer_define_idndb_branch_master_file,\n"
                    + "     ndb_customer_define.`cust_other_bank_ac_no` AS ndb_customer_define_cust_other_bank_ac_no,\n"
                    + "     ndb_customer_define.`idndb_geo_market_master_file` AS ndb_customer_define_idndb_geo_market_master_file,\n"
                    + "     ndb_customer_define.`cust_status` AS ndb_customer_define_cust_status,\n"
                    + "     ndb_customer_define.`cust_auth` AS ndb_customer_define_cust_auth,\n"
                    + "     ndb_customer_define.`cust_is_ndb_cust` AS ndb_customer_define_cust_is_ndb_cust,\n"
                    + "     ndb_customer_define.`cust_creat_by` AS ndb_customer_define_cust_creat_by,\n"
                    + "     ndb_customer_define.`cust_creat_date` AS ndb_customer_define_cust_creat_date,\n"
                    + "     ndb_customer_define.`cust_mod_by` AS ndb_customer_define_cust_mod_by,\n"
                    + "     ndb_customer_define.`cust_mod_date` AS ndb_customer_define_cust_mod_date,\n"
                    + "     ndb_customer_define.`cms_curr_acc_number` AS ndb_customer_define_cms_curr_acc_number,\n"
                    + "     ndb_customer_define.`cms_collection_acc_number` AS ndb_customer_define_cms_collection_acc_number\n"
                    + "FROM\n"
                    + "     `ndb_customer_define` ndb_customer_define INNER JOIN `ndb_cust_prod_map` ndb_cust_prod_map ON ndb_customer_define.`idndb_customer_define` = ndb_cust_prod_map.`idndb_customer_define` where ndb_cust_prod_map.`prod_relationship_key_buyer`='ACTIVE' and ndb_cust_prod_map.`prod_relationship_status`='ACTIVE' and ndb_cust_prod_map.`prod_relationship_auth`='AUTHORIZED' and ndb_customer_define.`cust_status`='ACTIVE' and ndb_customer_define.`cust_auth`='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT idndb_customer_define,cust_id,cust_name"
                        + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs1.getString("ndb_cust_prod_map_idndb_customer_define") + "' ORDER BY cust_id ASC";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    m_strData = m_strData + "<option id=" + m_rs1.getString("ndb_cust_prod_map_idndb_customer_define") + " value=" + m_rs1.getString("ndb_cust_prod_map_idndb_cust_prod_map") + ">" + m_rs2.getString("cust_id") + "</option>";

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

//            m_strQry = "SELECT idndb_customer_define,idndb_cust_prod_map"
//                    + " FROM ndb_cust_prod_map where prod_relationship_key_buyer='ACTIVE' and prod_relationship_auth='AUTHORIZED'";
            m_strQry = "SELECT\n"
                    + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                    + "     ndb_cust_prod_map.`idndb_customer_define` AS ndb_cust_prod_map_idndb_customer_define,\n"
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
                    + "     ndb_cust_prod_map.`prod_relationship_mod_date` AS ndb_cust_prod_map_prod_relationship_mod_date,\n"
                    + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
                    + "     ndb_customer_define.`cust_id` AS ndb_customer_define_cust_id,\n"
                    + "     ndb_customer_define.`cust_name` AS ndb_customer_define_cust_name,\n"
                    + "     ndb_customer_define.`cust_short_name` AS ndb_customer_define_cust_short_name,\n"
                    + "     ndb_customer_define.`cust_industry_id` AS ndb_customer_define_cust_industry_id,\n"
                    + "     ndb_customer_define.`cust_contact_number` AS ndb_customer_define_cust_contact_number,\n"
                    + "     ndb_customer_define.`cust_fax_number` AS ndb_customer_define_cust_fax_number,\n"
                    + "     ndb_customer_define.`cust_contact_per1` AS ndb_customer_define_cust_contact_per1,\n"
                    + "     ndb_customer_define.`cust_contact_per2` AS ndb_customer_define_cust_contact_per2,\n"
                    + "     ndb_customer_define.`cust_email` AS ndb_customer_define_cust_email,\n"
                    + "     ndb_customer_define.`cust_address` AS ndb_customer_define_cust_address,\n"
                    + "     ndb_customer_define.`rec_finance_acc_num` AS ndb_customer_define_rec_finance_acc_num,\n"
                    + "     ndb_customer_define.`rec_finance_cr_dsc_proc_acc_num` AS ndb_customer_define_rec_finance_cr_dsc_proc_acc_num,\n"
                    + "     ndb_customer_define.`rec_finance_curr_ac` AS ndb_customer_define_rec_finance_curr_ac,\n"
                    + "     ndb_customer_define.`rec_finance_margin_ac` AS ndb_customer_define_rec_finance_margin_ac,\n"
                    + "     ndb_customer_define.`rec_finance_margin` AS ndb_customer_define_rec_finance_margin,\n"
                    + "     ndb_customer_define.`cust_credit_rate` AS ndb_customer_define_cust_credit_rate,\n"
                    + "     ndb_customer_define.`idndb_bank_master_file` AS ndb_customer_define_idndb_bank_master_file,\n"
                    + "     ndb_customer_define.`idndb_branch_master_file` AS ndb_customer_define_idndb_branch_master_file,\n"
                    + "     ndb_customer_define.`cust_other_bank_ac_no` AS ndb_customer_define_cust_other_bank_ac_no,\n"
                    + "     ndb_customer_define.`idndb_geo_market_master_file` AS ndb_customer_define_idndb_geo_market_master_file,\n"
                    + "     ndb_customer_define.`cust_status` AS ndb_customer_define_cust_status,\n"
                    + "     ndb_customer_define.`cust_auth` AS ndb_customer_define_cust_auth,\n"
                    + "     ndb_customer_define.`cust_is_ndb_cust` AS ndb_customer_define_cust_is_ndb_cust,\n"
                    + "     ndb_customer_define.`cust_creat_by` AS ndb_customer_define_cust_creat_by,\n"
                    + "     ndb_customer_define.`cust_creat_date` AS ndb_customer_define_cust_creat_date,\n"
                    + "     ndb_customer_define.`cust_mod_by` AS ndb_customer_define_cust_mod_by,\n"
                    + "     ndb_customer_define.`cust_mod_date` AS ndb_customer_define_cust_mod_date,\n"
                    + "     ndb_customer_define.`cms_curr_acc_number` AS ndb_customer_define_cms_curr_acc_number,\n"
                    + "     ndb_customer_define.`cms_collection_acc_number` AS ndb_customer_define_cms_collection_acc_number\n"
                    + "FROM\n"
                    + "     `ndb_customer_define` ndb_customer_define INNER JOIN `ndb_cust_prod_map` ndb_cust_prod_map ON ndb_customer_define.`idndb_customer_define` = ndb_cust_prod_map.`idndb_customer_define` where ndb_cust_prod_map.`prod_relationship_key_buyer`='ACTIVE' and ndb_cust_prod_map.`prod_relationship_status`='ACTIVE' and ndb_cust_prod_map.`prod_relationship_auth`='AUTHORIZED' and ndb_customer_define.`cust_status`='ACTIVE' and ndb_customer_define.`cust_auth`='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_strQry2 = "SELECT idndb_customer_define,cust_id,cust_name"
                        + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs1.getString("ndb_cust_prod_map_idndb_customer_define") + "' ORDER BY cust_name ASC ";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    m_strData = m_strData + "<option id=" + m_rs1.getString("ndb_cust_prod_map_idndb_customer_define") + " value=" + m_rs1.getString("ndb_cust_prod_map_idndb_cust_prod_map") + ">" + m_rs2.getString("cust_name") + "</option>";

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

    public String getSellerBuyserCount() {
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        String SELLER_BUYER_COUNT = "0";
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT COUNT(idndb_customer_define) AS idndb_customer_define FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                SELLER_BUYER_COUNT = m_rs1.getString("idndb_customer_define");

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
        return SELLER_BUYER_COUNT;
    }

    public String getActiveChequesCount() {
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        String ACTIVE_CHQ_COUNT = "0";
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT COUNT(idndb_pdc_txn_master) AS idndb_pdc_txn_master FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_chq_status_auth='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                ACTIVE_CHQ_COUNT = m_rs1.getString("idndb_pdc_txn_master");

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
        return ACTIVE_CHQ_COUNT;
    }

    public String getActiveChqAmount() {
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        String actve_chques_amu = "0.00";
        String actve_chques_amu_formatted = "0.00";
        String _system_date = new DBAutoFillBeans.comboDAO().getSystemDate();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT SUM(pdc_chq_amu) AS total FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_chq_status_auth='AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                actve_chques_amu = m_rs1.getString("total");
                DecimalFormat df = new DecimalFormat("#,###.00");
                actve_chques_amu_formatted = df.format(Double.parseDouble(actve_chques_amu));
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
        return actve_chques_amu_formatted;
    }

    public String getTodayProcessChqsCount() {
        String m_strData = "";
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        String today_Proceesed_chqs = "0";
        String _system_date = new DBAutoFillBeans.comboDAO().getSystemDate();
        try {
            _stmnt = _currentCon.createStatement();

            m_strQry = "SELECT COUNT(idndb_pdc_txn_master) AS idndb_pdc_txn_master FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_chq_status_auth='AUTHORIZED' and pdc_booking_date='" + _system_date + "'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                today_Proceesed_chqs = m_rs1.getString("idndb_pdc_txn_master");

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
        return today_Proceesed_chqs;
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

            m_strQry = "SELECT idndb_geo_market_master_file,geo_market_id,geo_market_desc FROM ndb_geo_market_master_file where geo_market_status='ACTIVE' ORDER BY geo_market_desc ASC";
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

            m_strQry = "SELECT idndb_bank_master_file,bank_name,bank_code,bank_status FROM ndb_bank_master_file where bank_status='ACTIVE' and bank_approval='AUTHORIZED' ORDER BY bank_name ASC";
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

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            Calendar cV = Calendar.getInstance();
            cV.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

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
