/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBoperationsDBO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import ndb.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Madhawa_4799
 */
public class customerDAO {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(customerDAO.class.getName());
    static ConnectionPool _connectionPool;
    static Connection _currentCon = null;
    private Statement _stmnt = null;
    private Statement _stmnt2 = null;
    private PreparedStatement _prepStmnt = null;
    private PreparedStatement _prepStmnt2 = null;
    private PreparedStatement _prepStmnt3 = null;
    private ResultSet _rs = null;
    private ResultSet _rs2 = null;
    private Exception _exception;

    public boolean saveCustomerDifineData(JSONObject prm_obj) {
        log.info("customer record recevied :");
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_customer_define = "";

        String m_Strcust_id = "";
        String m_StrOldcust_id = "";

        String m_Strcust_name = "";
        String m_StrOldcust_name = "";

        String m_Strcust_short_name = "";
        String m_StrOldcust_short_name = "";

        String m_Strcust_indutry_id = "";
        String m_StrOldcust_indutry_id = "";

        String m_Strcust_contct_no = "";
        String m_StrOldcust_contct_no = "";

        String m_Strcust_fx_number = "";
        String m_StrOldcust_fx_number = "";

        String m_Strcust_contct_per1 = "";
        String m_StrOldcust_contct_per1 = "";

        String m_Strcust_contct_per2 = "";
        String m_StrOldcust_contct_per2 = "";

        String m_Strcust_email = "";
        String m_StrOldcust_email = "";

        String m_Strcust_address = "";
        String m_StrOldcust_address = "";

        String m_Strcust_rec_acc_no = "";
        String m_StrOldcust_rec_acc_no = "";

        String m_Strcust_cre_des_pros_to_acc_no = "";
        String m_StrOldcust_cre_des_pros_to_acc_no = "";

        String m_Strcust_curr_ac_no = "";
        String m_StrOldcust_curr_ac_no = "";

        String m_Strcust_margin_ac_no = "";
        String m_StrOldcust_margin_ac_no = "";

        String m_Strcust_margin = "";
        String m_StrOldcust_margin = "";

        String m_Strcust_credt_rate = "";
        String m_StrOldcust_credt_rate = "";

        String m_Strcust_bankid = "";
        String m_StrOldcust_bankid = "";

        String m_Strcust_branchid = "";
        String m_StrOldcust_branchid = "";

        String m_Strcust_other_bank_ac_no = "";
        String m_StrOldcust_other_bank_ac_no = "";

        String m_Strcust_geo_marketid = "";
        String m_StrOldcust_geo_marketid = "";

        String m_Strcust_ch_active = "";
        String m_StrOldcust_ch_active = "";

        String m_Strcust_ndb_customer_active = "";
        String m_StrOldcust_ndb_customer_active = "";

        String m_strQry = "";

        boolean m_ardy_exist_data = true;

        try {

            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_customer_define = prm_obj.getString("idndb_customer_define");
            log.info("customer record recevied m_str_idndb_customer_define:" + m_str_idndb_customer_define);

            m_Strcust_id = prm_obj.getString("cust_id");
            log.info("customer record recevied m_Strcust_id:" + m_Strcust_id);

            m_Strcust_name = prm_obj.getString("cust_name");
            m_Strcust_short_name = prm_obj.getString("cust_short_name");
            m_Strcust_indutry_id = prm_obj.getString("cust_indutry_id");
            m_Strcust_contct_no = prm_obj.getString("cust_contct_no");
            m_Strcust_fx_number = prm_obj.getString("cust_fx_number");
            m_Strcust_contct_per1 = prm_obj.getString("cust_contct_per1");
            m_Strcust_contct_per2 = prm_obj.getString("cust_contct_per2");
            m_Strcust_email = prm_obj.getString("cust_email");
            m_Strcust_address = prm_obj.getString("cust_address");
            m_Strcust_rec_acc_no = prm_obj.getString("cust_rec_acc_no");
            m_Strcust_cre_des_pros_to_acc_no = prm_obj.getString("cust_cre_des_pros_to_acc_no");
            m_Strcust_curr_ac_no = prm_obj.getString("cust_curr_ac_no");
            m_Strcust_margin_ac_no = prm_obj.getString("cust_margin_ac_no");
            m_Strcust_margin = prm_obj.getString("cust_margin");
            m_Strcust_credt_rate = prm_obj.getString("cust_credt_rate");
            m_Strcust_bankid = prm_obj.getString("cust_bank");
            m_Strcust_branchid = prm_obj.getString("cust_branch");
            m_Strcust_other_bank_ac_no = prm_obj.getString("cust_other_bank_ac_no");
            m_Strcust_geo_marketid = prm_obj.getString("cust_geo_market");
            m_Strcust_ch_active = prm_obj.getString("cust_ch_active");
            m_Strcust_ndb_customer_active = prm_obj.getString("cust_ndb_customer_active");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }

            log.info("check for received record all ready exist:" + m_str_idndb_customer_define);
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            m_strQry = "select * from ndb_customer_define where idndb_customer_define='" + m_str_idndb_customer_define + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                log.info("customer already exist:" + m_str_idndb_customer_define);

                m_StrOldcust_id = _rs.getString("cust_id");
                m_StrOldcust_name = _rs.getString("cust_name");
                m_StrOldcust_short_name = _rs.getString("cust_short_name");
                m_StrOldcust_indutry_id = _rs.getString("cust_industry_id");
                m_StrOldcust_contct_no = _rs.getString("cust_contact_number");
                m_StrOldcust_fx_number = _rs.getString("cust_fax_number");
                m_StrOldcust_contct_per1 = _rs.getString("cust_contact_per1");
                m_StrOldcust_contct_per2 = _rs.getString("cust_contact_per2");
                m_StrOldcust_email = _rs.getString("cust_email");
                m_StrOldcust_address = _rs.getString("cust_address");
                m_StrOldcust_rec_acc_no = _rs.getString("rec_finance_acc_num");
                m_StrOldcust_cre_des_pros_to_acc_no = _rs.getString("rec_finance_cr_dsc_proc_acc_num");
                m_StrOldcust_curr_ac_no = _rs.getString("rec_finance_curr_ac");
                m_StrOldcust_margin_ac_no = _rs.getString("rec_finance_margin_ac");
                m_StrOldcust_margin = _rs.getString("rec_finance_margin");
                m_StrOldcust_credt_rate = _rs.getString("cust_credit_rate");
                m_StrOldcust_bankid = _rs.getString("idndb_bank_master_file");
                m_StrOldcust_branchid = _rs.getString("idndb_branch_master_file");
                m_StrOldcust_other_bank_ac_no = _rs.getString("cust_other_bank_ac_no");
                m_StrOldcust_geo_marketid = _rs.getString("idndb_geo_market_master_file");
                m_StrOldcust_ch_active = _rs.getString("cust_status");
                m_StrOldcust_ndb_customer_active = _rs.getString("cust_is_ndb_cust");

                m_ardy_exist_data = false;
            }

            if (m_ardy_exist_data) {
                log.info("New customer");
                m_strQry = "insert into ndb_customer_define (cust_id,"
                        + "cust_name,"
                        + "cust_short_name"
                        + ",cust_industry_id"
                        + ",cust_contact_number"
                        + ",cust_fax_number"
                        + ",cust_contact_per1"
                        + ",cust_contact_per2"
                        + ",cust_email"
                        + ",cust_address"
                        + ",rec_finance_acc_num"
                        + ",rec_finance_cr_dsc_proc_acc_num"
                        + ",rec_finance_curr_ac"
                        + ",rec_finance_margin_ac"
                        + ",rec_finance_margin"
                        + ",cust_credit_rate"
                        + ",idndb_bank_master_file"
                        + ",idndb_branch_master_file"
                        + ",cust_other_bank_ac_no"
                        + ",idndb_geo_market_master_file"
                        + ",cust_status"
                        + ",cust_auth"
                        + ",cust_is_ndb_cust"
                        + ",cust_creat_by"
                        + ",cust_creat_date"
                        + ",cust_mod_by"
                        + ",cust_mod_date"
                        + ""
                        + ") values("
                        + "'" + m_Strcust_id + "',"
                        + "'" + m_Strcust_name + "',"
                        + "'" + m_Strcust_short_name + "',"
                        + "'" + m_Strcust_indutry_id + "',"
                        + "'" + m_Strcust_contct_no + "',"
                        + "'" + m_Strcust_fx_number + "',"
                        + "'" + m_Strcust_contct_per1 + "',"
                        + "'" + m_Strcust_contct_per2 + "',"
                        + "'" + m_Strcust_email + "',"
                        + "'" + m_Strcust_address + "',"
                        + "'" + m_Strcust_rec_acc_no + "',"
                        + "'" + m_Strcust_cre_des_pros_to_acc_no + "',"
                        + "'" + m_Strcust_curr_ac_no + "',"
                        + "'" + m_Strcust_margin_ac_no + "',"
                        + "'" + m_Strcust_margin + "',"
                        + "'" + m_Strcust_credt_rate + "',"
                        + "'" + m_Strcust_bankid + "',"
                        + "'" + m_Strcust_branchid + "',"
                        + "'" + m_Strcust_other_bank_ac_no + "',"
                        + "'" + m_Strcust_geo_marketid + "',"
                        + "'" + m_Strcust_ch_active + "',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_Strcust_ndb_customer_active + "',"
                        + "'" + m_str_user_id + "',"
                        + "now(),"
                        + "'" + m_str_user_id + "',"
                        + "now())";

                log.info("New customer mysql quiery " + m_strQry);

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                String max_idndb_customer_define = "";
                m_strQry = "select MAX(idndb_customer_define) as idndb_customer_define from ndb_customer_define";
                _rs2 = _stmnt2.executeQuery(m_strQry);
                if (_rs2.next()) {
                    max_idndb_customer_define = _rs2.getString("idndb_customer_define");
                }

                m_strQry = "insert into ndb_change_log (ndb_change_log_type,"
                        + "ndb_attached_id,"
                        + "ndb_change"
                        + ",status"
                        + ",creat_by"
                        + ",creat_date"
                        + ""
                        + ") values("
                        + "'DEFINECUSTOMER',"
                        + "'" + max_idndb_customer_define + "',"
                        + "'NEW CUSTOMER',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            } else {

                String m_condition = "";
                String m_change = "";
                int i = 0;
                if (!m_StrOldcust_id.equals(m_Strcust_id)) {
                    i++;
                    m_condition = "cust_id='" + m_Strcust_id + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_id + " ID CHANGE TO " + m_Strcust_id + "<br>";
                }

                if (!m_StrOldcust_name.equals(m_Strcust_name)) {
                    i++;
                    m_condition = m_condition + "cust_name='" + m_Strcust_name + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_name + " NAME CHANGE TO " + m_Strcust_name + "<br>";
                }
                if (!m_StrOldcust_short_name.equals(m_Strcust_short_name)) {
                    i++;
                    m_condition = m_condition + "cust_short_name='" + m_Strcust_short_name + "',";

                    m_change = m_change + i + ")" + m_StrOldcust_short_name + " SHORT NAME CHANGE TO " + m_Strcust_short_name + "<br>";
                }
                if (!m_StrOldcust_indutry_id.equals(m_Strcust_indutry_id)) {
                    i++;
                    m_condition = m_condition + "cust_industry_id='" + m_Strcust_indutry_id + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_indutry_id + " INDUSTRY CHANGE TO " + m_Strcust_indutry_id + "<br>";
                }
                if (!m_StrOldcust_contct_no.equals(m_Strcust_contct_no)) {
                    i++;
                    m_condition = m_condition + "cust_contact_number='" + m_Strcust_contct_no + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_contct_no + " CONTACT NUMBER CHANGE TO " + m_Strcust_contct_no + "<br>";
                }
                if (!m_StrOldcust_fx_number.equals(m_Strcust_fx_number)) {
                    i++;
                    m_condition = m_condition + "cust_fax_number='" + m_Strcust_fx_number + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_fx_number + " FAX NUM. CHANGE TO " + m_Strcust_fx_number + "<br>";
                }
                if (!m_StrOldcust_contct_per1.equals(m_Strcust_contct_per1)) {
                    i++;
                    m_condition = m_condition + "cust_contact_per1='" + m_Strcust_contct_per1 + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_contct_per1 + " CONTACT PER1 CHANGE TO " + m_Strcust_contct_per1 + "<br>";
                }
                if (!m_StrOldcust_contct_per2.equals(m_Strcust_contct_per2)) {
                    i++;
                    m_condition = m_condition + "cust_contact_per2='" + m_Strcust_contct_per2 + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_contct_per2 + " CONTACT PER2 CHANGE TO " + m_Strcust_contct_per2 + "<br>";
                }
                if (!m_StrOldcust_email.equals(m_Strcust_email)) {
                    i++;
                    m_condition = m_condition + "cust_email='" + m_Strcust_email + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_email + " EMAIL CHANGE TO " + m_Strcust_email + "<br>";
                }
                if (!m_StrOldcust_address.equals(m_Strcust_address)) {
                    i++;
                    m_condition = m_condition + "cust_address='" + m_Strcust_address + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_address + " ADDRESS CHANGE TO " + m_Strcust_address + "<br>";
                }
                if (!m_StrOldcust_rec_acc_no.equals(m_Strcust_rec_acc_no)) {
                    i++;
                    m_condition = m_condition + "rec_finance_acc_num='" + m_Strcust_rec_acc_no + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_rec_acc_no + " RF ACC NUM. CHANGE TO " + m_Strcust_rec_acc_no + "<br>";
                }
                if (!m_StrOldcust_cre_des_pros_to_acc_no.equals(m_Strcust_cre_des_pros_to_acc_no)) {
                    i++;
                    m_condition = m_condition + "rec_finance_cr_dsc_proc_acc_num='" + m_Strcust_cre_des_pros_to_acc_no + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_cre_des_pros_to_acc_no + " COLL. PRC. TO ACC. CHANGE TO " + m_Strcust_cre_des_pros_to_acc_no + "<br>";
                }
                if (!m_StrOldcust_curr_ac_no.equals(m_Strcust_curr_ac_no)) {
                    i++;
                    m_condition = m_condition + "rec_finance_curr_ac='" + m_Strcust_curr_ac_no + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_curr_ac_no + " CURR. ACC. CHANGE TO " + m_Strcust_curr_ac_no + "<br>";
                }
                if (!m_StrOldcust_margin_ac_no.equals(m_Strcust_margin_ac_no)) {
                    i++;
                    m_condition = m_condition + "rec_finance_margin_ac='" + m_Strcust_margin_ac_no + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_margin_ac_no + " MARGIN ACC. CHANGE TO " + m_Strcust_margin_ac_no + "<br>";
                }
                if (!m_StrOldcust_margin.equals(m_Strcust_margin)) {
                    i++;
                    m_condition = m_condition + "rec_finance_margin='" + m_Strcust_margin + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_margin + " CMARGIN CHANGE TO " + m_Strcust_margin + "<br>";
                }
                if (!m_StrOldcust_credt_rate.equals(m_Strcust_credt_rate)) {
                    i++;
                    m_condition = m_condition + "cust_credit_rate='" + m_Strcust_credt_rate + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_credt_rate + " CREDIT RATE CHANGE TO " + m_Strcust_credt_rate + "<br>";
                }

                if (!m_StrOldcust_bankid.equals(m_Strcust_bankid)) {
                    i++;
                    m_condition = m_condition + "idndb_bank_master_file='" + m_Strcust_bankid + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_bankid + " BANK CHANGE TO " + m_Strcust_bankid + "<br>";
                }
                if (!m_StrOldcust_branchid.equals(m_Strcust_branchid)) {
                    i++;
                    m_condition = m_condition + "idndb_branch_master_file='" + m_Strcust_branchid + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_branchid + " BRANCH CHANGE TO " + m_Strcust_branchid + "<br>";
                }
                if (!m_StrOldcust_other_bank_ac_no.equals(m_Strcust_other_bank_ac_no)) {
                    m_condition = m_condition + "cust_other_bank_ac_no='" + m_Strcust_other_bank_ac_no + "',";
                    m_change = m_change + m_StrOldcust_other_bank_ac_no + " OTHER BANK ACC. CHANGE TO " + m_Strcust_other_bank_ac_no + "<br>";
                }
                if (!m_StrOldcust_geo_marketid.equals(m_Strcust_geo_marketid)) {
                    i++;
                    m_condition = m_condition + "idndb_geo_market_master_file='" + m_Strcust_geo_marketid + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_geo_marketid + " GEOR. MARKT. CHANGE TO " + m_Strcust_geo_marketid + "<br>";
                }
                if (!m_StrOldcust_ch_active.equals(m_Strcust_ch_active)) {
                    i++;
                    m_condition = m_condition + "cust_status='" + m_Strcust_ch_active + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_ch_active + " STATUS CHANGE TO " + m_Strcust_ch_active + "<br>";
                }

                if (!m_StrOldcust_ndb_customer_active.equals(m_Strcust_ndb_customer_active)) {
                    i++;
                    m_condition = m_condition + "cust_is_ndb_cust='" + m_Strcust_ndb_customer_active + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_ndb_customer_active + " CUSTOMER TYPE CHANGE TO " + m_Strcust_ndb_customer_active + "<br>";

                }

                if (!m_condition.equals("")) {
                    m_condition = m_condition + "cust_auth='UN-AUTHORIZED',";
                }

                m_strQry = "update ndb_customer_define set " + m_condition + " "
                        + "cust_mod_by='" + m_str_user_id + "',"
                        + "cust_mod_date=now()"
                        + " where idndb_customer_define='" + m_str_idndb_customer_define + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                m_strQry = "insert into ndb_change_log (ndb_change_log_type,"
                        + "ndb_attached_id,"
                        + "ndb_change"
                        + ",status"
                        + ",creat_by"
                        + ",creat_date"
                        + ""
                        + ") values("
                        + "'DEFINECUSTOMER',"
                        + "'" + m_str_idndb_customer_define + "',"
                        + "'" + m_change + "',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            success = true;
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in saving cutomer define data, Exception" + e);
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
        return success;
    }

    public boolean saveAuthCustomerDifineData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_idndb_customer_define_bulk = "";
        String m_str_idndb_customer_define = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            m_idndb_customer_define_bulk = prm_obj.getString("idndb_customer_define");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            String[] parts = m_idndb_customer_define_bulk.split(",");
            for (int j = 0; j < parts.length; j++) {
                m_str_idndb_customer_define = parts[j];
                m_strQry = "update ndb_customer_define set cust_auth='AUTHORIZED',"
                        + "cust_mod_by='" + m_str_user_id + "',"
                        + "cust_mod_date=now()"
                        + " where idndb_customer_define='" + m_str_idndb_customer_define + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                m_strQry = "update ndb_change_log set status='AUTHORIZED',"
                        + "authby='" + m_str_user_id + "',"
                        + "auth_date=now()"
                        + " where ndb_attached_id='" + m_str_idndb_customer_define + "' and ndb_change_log_type='DEFINECUSTOMER'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            success = true;
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in authorizing customer define data, Exception" + e);
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
        return success;
    }

    public boolean saveRejectedCustomerDifineData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_idndb_customer_define_bulk = "";
        String m_str_idndb_customer_define = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            m_idndb_customer_define_bulk = prm_obj.getString("idndb_customer_define");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            String[] parts = m_idndb_customer_define_bulk.split(",");
            for (int j = 0; j < parts.length; j++) {
                m_str_idndb_customer_define = parts[j];
                m_strQry = "update ndb_customer_define set cust_auth='REJECTED',"
                        + "cust_mod_by='" + m_str_user_id + "',"
                        + "cust_mod_date=now()"
                        + " where idndb_customer_define='" + m_str_idndb_customer_define + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                m_strQry = "update ndb_change_log set status='REJECTED',"
                        + "authby='" + m_str_user_id + "',"
                        + "auth_date=now()"
                        + " where ndb_attached_id='" + m_str_idndb_customer_define + "' and ndb_change_log_type='DEFINECUSTOMER'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            success = true;
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in rejecting customer define data, Exception" + e);
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
        return success;
    }

    public boolean saveAuthCustProdMappingData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_cust_prod_map = "";
        String m_idndb_cust_prod_map_bulk = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            m_idndb_cust_prod_map_bulk = prm_obj.getString("idndb_cust_prod_map");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            String[] parts = m_idndb_cust_prod_map_bulk.split(",");
            for (int j = 0; j < parts.length; j++) {
                m_str_idndb_cust_prod_map = parts[j];

                m_strQry = "update ndb_cust_prod_map set prod_relationship_auth='AUTHORIZED',"
                        + "prod_relationship_mod_by='" + m_str_user_id + "',"
                        + "prod_relationship_mod_date=now()"
                        + " where idndb_cust_prod_map='" + m_str_idndb_cust_prod_map + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                m_strQry = "update ndb_change_log set status='AUTHORIZED',"
                        + "authby='" + m_str_user_id + "',"
                        + "auth_date=now()"
                        + " where ndb_attached_id='" + m_str_idndb_cust_prod_map + "' and ndb_change_log_type='CUSTPRODMAP'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            success = true;
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in authorizing cutomer product mapping data, Exception" + e);
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
        return success;
    }

    public boolean saveRegectedCustProdMappingData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_cust_prod_map = "";
        String m_idndb_cust_prod_map_bulk = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            m_idndb_cust_prod_map_bulk = prm_obj.getString("idndb_cust_prod_map");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            String[] parts = m_idndb_cust_prod_map_bulk.split(",");
            for (int j = 0; j < parts.length; j++) {
                m_str_idndb_cust_prod_map = parts[j];

                m_strQry = "update ndb_cust_prod_map set prod_relationship_auth='REJECTED',"
                        + "prod_relationship_mod_by='" + m_str_user_id + "',"
                        + "prod_relationship_mod_date=now()"
                        + " where idndb_cust_prod_map='" + m_str_idndb_cust_prod_map + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                m_strQry = "update ndb_change_log set status='REJECTED',"
                        + "authby='" + m_str_user_id + "',"
                        + "auth_date=now()"
                        + " where ndb_attached_id='" + m_str_idndb_cust_prod_map + "' and ndb_change_log_type='CUSTPRODMAP'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            success = true;
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in rejecting cutomer product mapping data, Exception" + e);
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
        return success;
    }

    public boolean saveAuthSllerHasBuyerData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_seller_has_buyers = "";
        String m_str_idndb_seller_has_buyers_bulk = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_seller_has_buyers_bulk = prm_obj.getString("idndb_seller_has_buyers");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            String[] parts = m_str_idndb_seller_has_buyers_bulk.split(",");
            for (int j = 0; j < parts.length; j++) {
                m_str_idndb_seller_has_buyers = parts[j];
                m_strQry = "update ndb_seller_has_buyers set sl_has_byr_auth='AUTHORIZED',"
                        + "sl_has_byr_mod_by='" + m_str_user_id + "',"
                        + "sl_has_byr_mod_date=now()"
                        + " where idndb_seller_has_buyers='" + m_str_idndb_seller_has_buyers + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                m_strQry = "update ndb_change_log set status='AUTHORIZED',"
                        + "authby='" + m_str_user_id + "',"
                        + "auth_date=now()"
                        + " where ndb_attached_id='" + m_str_idndb_seller_has_buyers + "' and ndb_change_log_type='RELESTB'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }
            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            success = true;
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in authorizing relationship estabilishment data, Exception" + e);
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
        return success;
    }

    public boolean saveRejectSllerHasBuyerData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_seller_has_buyers = "";
        String m_str_idndb_seller_has_buyers_bulk = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_seller_has_buyers_bulk = prm_obj.getString("idndb_seller_has_buyers");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            String[] parts = m_str_idndb_seller_has_buyers_bulk.split(",");
            for (int j = 0; j < parts.length; j++) {
                m_str_idndb_seller_has_buyers = parts[j];
                m_strQry = "update ndb_seller_has_buyers set sl_has_byr_auth='REJECTED',"
                        + "sl_has_byr_mod_by='" + m_str_user_id + "',"
                        + "sl_has_byr_mod_date=now()"
                        + " where idndb_seller_has_buyers='" + m_str_idndb_seller_has_buyers + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                m_strQry = "update ndb_change_log set status='REJECTED',"
                        + "authby='" + m_str_user_id + "',"
                        + "auth_date=now()"
                        + " where ndb_attached_id='" + m_str_idndb_seller_has_buyers + "' and ndb_change_log_type='RELESTB'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }
            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            success = true;
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in rejecting relationship estabilishment data, Exception" + e);
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
        return success;
    }

    public boolean saveCustomerRelationShipEstbmntData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";

        String m_str_idndb_seller_has_buyers = "";

//        String m_str_free_str_comm = "";
//        String m_strOld_free_str_comm = "";
        String m_Stridndb_customer_define_seller = "";
        String m_StrOldidndb_customer_define_seller = "";

        String m_Stridndb_customer_define_buyer = "";
        String m_StrOldidndb_customer_define_buyer = "";

        String m_Strsl_has_byr_prorm_type = "";
        String m_Strsl_has_byr_prorm_type_rf = "";
        String m_Strsl_has_byr_prorm_type_cw = "";

        String m_StrOldsl_has_byr_prorm_type = "";

        String m_Strsl_has_byr_fcilty_acc_no = "";
        String m_StrOldsl_has_byr_fcilty_acc_no = "";

        String m_Strsl_has_byr_fmax_chq_amu = "";
        String m_StrOldsl_has_byr_fmax_chq_amu = "";

        String m_Strsl_has_byr_cdt_loan_amu = "";
        String m_StrOldsl_has_byr_cdt_loan_amu = "";

        String m_Strsl_has_byr_otstaning = "";
        String m_StrOldsl_has_byr_otstaning = "";

        String m_Strsl_has_byr_tenor = "";
        String m_StrOldsl_has_byr_tenor = "";

        String m_Strsl_has_byr_itst_trsry = "";
        String m_StrOldsl_has_byr_itst_trsry = "";

        //  String m_Strsl_has_byr_min_comm_limt = "";
        // String m_StrOldsl_has_byr_min_comm_limt = "";
        //  String m_Strsl_has_byr_chq_disc_intstt = "";
        //  String m_StrOldsl_has_byr_chq_disc_intst = "";
        //   String m_Strch_chequ_dis_prestage = "";
        //  String m_StrOldch_chequ_dis_prestage = "";
        //  String m_Strch_chequ_dis_flatfee = "";
        //  String m_StrOldch_chequ_dis_flatfee = "";
        //  String m_Strsl_has_byr_frestrut_comm_prstage = "";
        //   String m_StrOldsl_has_byr_frestrut_comm_prstage = "";
        //  String m_Strsl_has_byr_frestrut_comm_ftfee = "";
        //  String m_StrOldsl_has_byr_frestrut_comm_ftfee = "";
        String m_Strsl_has_byr_advnce_rate_prstage = "";
        String m_StrOldsl_has_byr_advnce_rate_prstage = "";
        String m_Strsl_has_byr_remarks = "";
        String m_StrOldsl_has_byr_remarks = "";

        String m_Strsl_has_byr_active = "";
        String m_StrOldsl_has_byr_active = "";

        String m_Strsl_has_byr_warehs_limit = "";
        String m_StrOldsl_has_byr_warehs_limit = "";

        String m_Strsl_has_byr_warehs_otstaning = "";
        String m_StrOldsl_has_byr_warehs_otstaning = "";

        String m_Strsl_has_byr_warehs_tenor = "";
        String m_StrOldsl_has_byr_warehs_tenor = "";

        String m_Strsl_has_byr_warehs_fmax_chq_amu = "";
        String m_StrOldsl_has_byr_warehs_fmax_chq_amu = "";

        String m_Strrec_finance_commision_crg = "";
        String m_StrOldrec_finance_commision_crg = "";

        String m_Strrf_tran_base_falt_fee = "";
        String m_StrOldrf_tran_base_falt_fee = "";

        String m_Strrf_tran_base_from_tran = "";
        String m_StrOldrf_tran_base_from_tran = "";

        String m_Strrf_fixed_rate_amount = "";
        String m_StrOldrf_fixed_rate_amount = "";

        String m_Strrf_fixed_frequency = "";
        String m_StrOldrf_fixed_frequency = "";

        String m_Strchq_wh_commision_crg = "";
        String m_StrOldchq_wh_commision_crg = "";

        String m_Strcw_tran_base_falt_fee = "";
        String m_StrOldcw_tran_base_falt_fee = "";

        String m_Strcw_tran_base_from_tran = "";
        String m_StrOldcw_tran_base_from_tran = "";

        String m_Strcw_fixed_rate_amount = "";
        String m_StrOldcw_fixed_rate_amount = "";

        String m_Strcw_fixed_frequency = "";
        String m_StrOldcw_fixed_frequency = "";

        String m_strQry = "";

        boolean m_ardy_exist_data = true;

        try {

            m_str_user_id = prm_obj.getString("user_id");

            m_str_idndb_seller_has_buyers = prm_obj.getString("idndb_seller_has_buyers");
            m_Stridndb_customer_define_seller = prm_obj.getString("idndb_customer_define_seller");
            m_Stridndb_customer_define_buyer = prm_obj.getString("idndb_customer_define_buyer");

            m_Strsl_has_byr_prorm_type_rf = prm_obj.getString("sl_has_byr_prorm_type_rf");
            m_Strsl_has_byr_prorm_type_cw = prm_obj.getString("sl_has_byr_prorm_type_cw");

            if (m_Strsl_has_byr_prorm_type_rf.equals("ACTIVE")) {
                m_Strsl_has_byr_prorm_type = "RF";
            } else if (m_Strsl_has_byr_prorm_type_cw.equals("ACTIVE")) {
                m_Strsl_has_byr_prorm_type = "CW";

            }

            //  m_Strsl_has_byr_fcilty_acc_no = prm_obj.getString("sl_has_byr_fcilty_acc_no");
            m_Strsl_has_byr_fmax_chq_amu = prm_obj.getString("sl_has_byr_fmax_chq_amu");
            if (m_Strsl_has_byr_fmax_chq_amu.equals("")) {
                m_Strsl_has_byr_fmax_chq_amu = "0.00";
            }

            m_Strsl_has_byr_cdt_loan_amu = prm_obj.getString("sl_has_byr_cdt_loan_amu");
            if (m_Strsl_has_byr_cdt_loan_amu.equals("")) {
                m_Strsl_has_byr_cdt_loan_amu = "0.00";
            }

            m_Strsl_has_byr_otstaning = prm_obj.getString("sl_has_byr_otstaning");
            if (m_Strsl_has_byr_otstaning.equals("")) {
                m_Strsl_has_byr_otstaning = "0.00";
            }

            m_Strsl_has_byr_tenor = prm_obj.getString("sl_has_byr_tenor");
            if (m_Strsl_has_byr_tenor.equals("")) {
                m_Strsl_has_byr_tenor = "0.00";
            }

            m_Strsl_has_byr_itst_trsry = prm_obj.getString("sl_has_byr_itst_trsry");
            if (m_Strsl_has_byr_itst_trsry.equals("")) {
                m_Strsl_has_byr_itst_trsry = "0.00";
            }

            //   m_Strsl_has_byr_min_comm_limt = prm_obj.getString("sl_has_byr_min_comm_limt");
            //   m_Strsl_has_byr_chq_disc_intstt = prm_obj.getString("sl_has_byr_chq_disc_intst");
//            m_Strch_chequ_dis_prestage = prm_obj.getString("ch_chequ_dis_prestage");
//            m_Strch_chequ_dis_flatfee = prm_obj.getString("ch_chequ_dis_flatfee");
//
//            m_Strsl_has_byr_frestrut_comm_prstage = prm_obj.getString("sl_has_byr_frestrut_comm_prstage");
//            m_Strsl_has_byr_frestrut_comm_ftfee = prm_obj.getString("sl_has_byr_frestrut_comm_ftfee");
//
//            if (m_Strch_chequ_dis_prestage.equals("ACTIVE")) {
//                m_str_free_str_comm = "PERCENTAGE";
//            } else {
//                m_Strsl_has_byr_frestrut_comm_prstage = "0.00";
//
//            }
//            if (m_Strch_chequ_dis_flatfee.equals("ACTIVE")) {
//                m_str_free_str_comm = "FLATFEE";
//            } else {
//                m_Strsl_has_byr_frestrut_comm_ftfee = "0.00";
//
//            }
//
            m_Strsl_has_byr_advnce_rate_prstage = prm_obj.getString("sl_has_byr_advnce_rate_prstage");

            if (m_Strsl_has_byr_advnce_rate_prstage.equals("")) {
                m_Strsl_has_byr_advnce_rate_prstage = "0.00";
            }
            m_Strsl_has_byr_remarks = prm_obj.getString("sl_has_byr_remarks");
            m_Strsl_has_byr_active = prm_obj.getString("sl_has_byr_active");

            // cw facility details
            m_Strsl_has_byr_warehs_limit = prm_obj.getString("sl_has_byr_warehs_limit");
            if (m_Strsl_has_byr_warehs_limit.equals("")) {
                m_Strsl_has_byr_warehs_limit = "0.00";
            }

            m_Strsl_has_byr_warehs_tenor = prm_obj.getString("sl_has_byr_warehs_tenor");
            if (m_Strsl_has_byr_warehs_tenor.equals("")) {
                m_Strsl_has_byr_warehs_tenor = "0";
            }

            m_Strsl_has_byr_warehs_otstaning = prm_obj.getString("sl_has_byr_warehs_otstaning");
            if (m_Strsl_has_byr_warehs_otstaning.equals("")) {
                m_Strsl_has_byr_warehs_otstaning = "0.00";
            }

            m_Strsl_has_byr_warehs_fmax_chq_amu = prm_obj.getString("sl_has_byr_warehs_fmax_chq_amu");
            if (m_Strsl_has_byr_warehs_fmax_chq_amu.equals("")) {
                m_Strsl_has_byr_warehs_fmax_chq_amu = "0.00";
            }

            m_Strrec_finance_commision_crg = prm_obj.getString("rec_finance_commision_crg");

            if (m_Strrec_finance_commision_crg.equals("TRANSACTION BASED")) {
                m_Strrf_tran_base_falt_fee = prm_obj.getString("rf_tran_base_falt_fee");
                if (m_Strrf_tran_base_falt_fee.equals("")) {
                    m_Strrf_tran_base_falt_fee = "0.00";
                }

                m_Strrf_tran_base_from_tran = prm_obj.getString("rf_tran_base_from_tran");
                if (m_Strrf_tran_base_from_tran.equals("")) {
                    m_Strrf_tran_base_from_tran = "0.00";
                }
            } else {
                m_Strrf_tran_base_falt_fee = "0.00";
                m_Strrf_tran_base_from_tran = "0.00";

            }
            if (m_Strrec_finance_commision_crg.equals("FIXED CHARGE BASED")) {
                m_Strrf_fixed_rate_amount = prm_obj.getString("rf_fixed_rate_amount");
                if (m_Strrf_fixed_rate_amount.equals("")) {
                    m_Strrf_fixed_rate_amount = "0.00";
                }

                m_Strrf_fixed_frequency = prm_obj.getString("rf_fixed_frequency");
            } else {
                m_Strrf_fixed_rate_amount = "0.00";
                m_Strrf_fixed_frequency = "NOT DEFINE";

            }

            m_Strchq_wh_commision_crg = prm_obj.getString("chq_wh_commision_crg");

            if (m_Strchq_wh_commision_crg.equals("TRANSACTION BASED")) {
                m_Strcw_tran_base_falt_fee = prm_obj.getString("cw_tran_base_falt_fee");
                if (m_Strcw_tran_base_falt_fee.equals("")) {
                    m_Strcw_tran_base_falt_fee = "0.00";
                }
                m_Strcw_tran_base_from_tran = prm_obj.getString("cw_tran_base_from_tran");
                if (m_Strcw_tran_base_from_tran.equals("")) {
                    m_Strcw_tran_base_from_tran = "0.00";
                }
            } else {
                m_Strcw_tran_base_falt_fee = "0.00";
                m_Strcw_tran_base_from_tran = "0.00";

            }
            if (m_Strchq_wh_commision_crg.equals("FIXED CHARGE BASED")) {
                m_Strcw_fixed_rate_amount = prm_obj.getString("cw_fixed_rate_amount");
                m_Strcw_fixed_frequency = prm_obj.getString("cw_fixed_frequency");
            } else {
                m_Strcw_fixed_rate_amount = "0.00";
                m_Strcw_fixed_frequency = "NOT DEFINE";

            }

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            m_strQry = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_str_idndb_seller_has_buyers + "'";
            _rs = _stmnt.executeQuery(m_strQry);

            String m_change = "";
            int i = 0;
            if (_rs.next()) {

                m_StrOldidndb_customer_define_seller = _rs.getString("idndb_customer_define_seller");
                m_StrOldidndb_customer_define_buyer = _rs.getString("idndb_customer_define_buyer");
                m_StrOldsl_has_byr_prorm_type = _rs.getString("sl_has_byr_prorm_type");
                //   m_StrOldsl_has_byr_fcilty_acc_no = _rs.getString("sl_has_byr_facilty_ac_no");
                m_StrOldsl_has_byr_fmax_chq_amu = _rs.getString("sl_has_byr_max_chq_amu");
                m_StrOldsl_has_byr_cdt_loan_amu = _rs.getString("shb_facty_det_crd_loam_limit");
                m_StrOldsl_has_byr_otstaning = _rs.getString("shb_facty_det_os");
                m_StrOldsl_has_byr_tenor = _rs.getString("shb_facty_det_tenor");
                m_StrOldsl_has_byr_itst_trsry = _rs.getString("shb_facty_det_irest_trry");
                //   m_StrOldsl_has_byr_min_comm_limt = _rs.getString("shb_facty_det_main_cmiss_limit");
                //   m_StrOldsl_has_byr_chq_disc_intst = _rs.getString("shb_chq_dis_ricing_intrest");
//                String m_free_str_comm = _rs.getString("sl_has_byr_frestrut_comm");
//                if (m_free_str_comm.equals("PERCENTAGE")) {
//                    m_StrOldch_chequ_dis_prestage = "PERCENTAGE";
//                    m_strOld_free_str_comm = "PERCENTAGE";
//
//                }
//                if (m_free_str_comm.equals("FLATFEE")) {
//                    m_StrOldch_chequ_dis_flatfee = "FLATFEE";
//                    m_strOld_free_str_comm = "FLATFEE";
//
//                }
//
//                m_StrOldsl_has_byr_frestrut_comm_prstage = _rs.getString("shb_chq_dis_adv_rate_prectange");
//                m_StrOldsl_has_byr_frestrut_comm_ftfee = _rs.getString("shb_chq_dis_free_strt_comm_flatfee");
                m_StrOldsl_has_byr_advnce_rate_prstage = _rs.getString("shb_chq_dis_adv_rate_prectange");

                m_StrOldsl_has_byr_remarks = _rs.getString("sl_has_byr_remarks");
                m_StrOldsl_has_byr_active = _rs.getString("sl_has_byr_status");

                m_StrOldsl_has_byr_warehs_limit = _rs.getString("sl_has_byr_warehs_limit");
                m_StrOldsl_has_byr_warehs_otstaning = _rs.getString("sl_has_byr_warehs_otstaning");
                m_StrOldsl_has_byr_warehs_tenor = _rs.getString("sl_has_byr_warehs_tenor");
                m_StrOldsl_has_byr_warehs_fmax_chq_amu = _rs.getString("sl_has_byr_warehs_fmax_chq_amu");

                m_StrOldrec_finance_commision_crg = _rs.getString("rec_finance_commision_crg");
                m_StrOldrf_tran_base_falt_fee = _rs.getString("rf_tran_base_falt_fee");
                m_StrOldrf_tran_base_from_tran = _rs.getString("rf_tran_base_from_tran");
                m_StrOldrf_fixed_rate_amount = _rs.getString("rf_fixed_rate_amount");
                m_StrOldrf_fixed_frequency = _rs.getString("rf_fixed_frequency");

                m_StrOldchq_wh_commision_crg = _rs.getString("chq_wh_commision_crg");
                m_StrOldcw_tran_base_falt_fee = _rs.getString("cw_tran_base_falt_fee");
                m_StrOldcw_tran_base_from_tran = _rs.getString("cw_tran_base_from_tran");
                m_StrOldcw_fixed_rate_amount = _rs.getString("cw_fixed_rate_amount");
                m_StrOldcw_fixed_frequency = _rs.getString("cw_fixed_frequency");

                m_ardy_exist_data = false;
            }

            if (m_ardy_exist_data) {
                m_strQry = "insert into ndb_seller_has_buyers ("
                        + "idndb_customer_define_seller,"
                        + "idndb_customer_define_buyer"
                        + ",sl_has_byr_prorm_type"
                        //  + ",sl_has_byr_facilty_ac_no"
                        + ",sl_has_byr_max_chq_amu"
                        + ",shb_facty_det_crd_loam_limit"
                        + ",shb_facty_det_os"
                        + ",shb_facty_det_tenor"
                        + ",shb_facty_det_irest_trry"
                        //   + ",shb_facty_det_main_cmiss_limit"
                        //   + ",shb_chq_dis_ricing_intrest"
                        //      + ",sl_has_byr_frestrut_comm"
                        //                        + ",shb_chq_dis_free_strt_comm_prsntage"
                        //                        + ",shb_chq_dis_free_strt_comm_flatfee"
                        + ",shb_chq_dis_adv_rate_prectange"
                        + ",sl_has_byr_remarks"
                        + ",sl_has_byr_status"
                        + ",sl_has_byr_auth"
                        + ",sl_has_byr_warehs_limit"
                        + ",sl_has_byr_warehs_otstaning"
                        + ",sl_has_byr_warehs_tenor"
                        + ",sl_has_byr_warehs_fmax_chq_amu"
                        + ",rec_finance_commision_crg"
                        + ",rf_tran_base_falt_fee"
                        + ",rf_tran_base_from_tran"
                        + ",rf_fixed_rate_amount"
                        + ",rf_fixed_frequency"
                        + ",chq_wh_commision_crg"
                        + ",cw_tran_base_falt_fee"
                        + ",cw_tran_base_from_tran"
                        + ",cw_fixed_rate_amount"
                        + ",cw_fixed_frequency"
                        + ",sl_has_byr_creat_by"
                        + ",sl_has_byr_creat_date"
                        + ",sl_has_byr_mod_by"
                        + ",sl_has_byr_mod_date"
                        + ""
                        + ") values("
                        + "'" + m_Stridndb_customer_define_seller + "',"
                        + "'" + m_Stridndb_customer_define_buyer + "',"
                        + "'" + m_Strsl_has_byr_prorm_type + "',"
                        //    + "'" + m_Strsl_has_byr_fcilty_acc_no + "',"
                        + "'" + m_Strsl_has_byr_fmax_chq_amu + "',"
                        + "'" + m_Strsl_has_byr_cdt_loan_amu + "',"
                        + "'" + m_Strsl_has_byr_otstaning + "',"
                        + "'" + m_Strsl_has_byr_tenor + "',"
                        + "'" + m_Strsl_has_byr_itst_trsry + "',"
                        //    + "'" + m_Strsl_has_byr_min_comm_limt + "',"
                        //    + "'" + m_Strsl_has_byr_chq_disc_intstt + "',"
                        //  + "'" + m_str_free_str_comm + "',"
                        //                        + "'" + m_Strsl_has_byr_frestrut_comm_prstage + "',"
                        //                        + "'" + m_Strsl_has_byr_frestrut_comm_ftfee + "',"
                        + "'" + m_Strsl_has_byr_advnce_rate_prstage + "',"
                        + "'" + m_Strsl_has_byr_remarks + "',"
                        + "'" + m_Strsl_has_byr_active + "',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_Strsl_has_byr_warehs_limit + "',"
                        + "'" + m_Strsl_has_byr_warehs_otstaning + "',"
                        + "'" + m_Strsl_has_byr_warehs_tenor + "',"
                        + "'" + m_Strsl_has_byr_warehs_fmax_chq_amu + "',"
                        + "'" + m_Strrec_finance_commision_crg + "',"
                        + "'" + m_Strrf_tran_base_falt_fee + "',"
                        + "'" + m_Strrf_tran_base_from_tran + "',"
                        + "'" + m_Strrf_fixed_rate_amount + "',"
                        + "'" + m_Strrf_fixed_frequency + "',"
                        + "'" + m_Strchq_wh_commision_crg + "',"
                        + "'" + m_Strcw_tran_base_falt_fee + "',"
                        + "'" + m_Strcw_tran_base_from_tran + "',"
                        + "'" + m_Strcw_fixed_rate_amount + "',"
                        + "'" + m_Strcw_fixed_frequency + "',"
                        + "'" + m_str_user_id + "',"
                        + "now(),"
                        + "'" + m_str_user_id + "',"
                        + "now())";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                String max_idndb_seller_has_buyers = "";
                m_strQry = "select MAX(idndb_seller_has_buyers) as idndb_seller_has_buyers from ndb_seller_has_buyers";
                _rs2 = _stmnt2.executeQuery(m_strQry);
                if (_rs2.next()) {
                    max_idndb_seller_has_buyers = _rs2.getString("idndb_seller_has_buyers");
                }

                m_strQry = "insert into ndb_change_log (ndb_change_log_type,"
                        + "ndb_attached_id,"
                        + "ndb_change"
                        + ",status"
                        + ",creat_by"
                        + ",creat_date"
                        + ""
                        + ") values("
                        + "'RELESTB',"
                        + "'" + max_idndb_seller_has_buyers + "',"
                        + "' " + i + ") NEW RELATIONSHIP ESTABLISHMENT',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            } else {

                String m_condition = "";
                if (!m_StrOldidndb_customer_define_seller.equals(m_Stridndb_customer_define_seller)) {
                    m_condition = "idndb_customer_define_seller='" + m_Stridndb_customer_define_seller + "',";
                }
                if (!m_StrOldidndb_customer_define_buyer.equals(m_Stridndb_customer_define_buyer)) {
                    m_condition = m_condition + "idndb_customer_define_buyer='" + m_Stridndb_customer_define_buyer + "',";
                }
                if (!m_StrOldsl_has_byr_prorm_type.equals(m_Strsl_has_byr_prorm_type)) {
                    m_condition = m_condition + "sl_has_byr_prorm_type='" + m_Strsl_has_byr_prorm_type + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_prorm_type + " PROGRAME TYPE CHANGE TO " + m_Strsl_has_byr_prorm_type + "<br>";
                }
                //   if (!m_StrOldsl_has_byr_fcilty_acc_no.equals(m_Strsl_has_byr_fcilty_acc_no)) {
                //       m_condition = m_condition + "sl_has_byr_facilty_ac_no='" + m_Strsl_has_byr_fcilty_acc_no + "',";
                //   }
                if (!m_StrOldsl_has_byr_fmax_chq_amu.equals(m_Strsl_has_byr_fmax_chq_amu)) {
                    m_condition = m_condition + "sl_has_byr_max_chq_amu='" + m_Strsl_has_byr_fmax_chq_amu + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_fmax_chq_amu + " RF PER CHQ AMU. CHANGE TO " + m_Strsl_has_byr_fmax_chq_amu + "<br>";

                }
                if (!m_StrOldsl_has_byr_cdt_loan_amu.equals(m_Strsl_has_byr_cdt_loan_amu)) {
                    m_condition = m_condition + "shb_facty_det_crd_loam_limit='" + m_Strsl_has_byr_cdt_loan_amu + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_cdt_loan_amu + " RF LOAN LIMIT PERCENTAGE CHANGE TO " + m_Strsl_has_byr_cdt_loan_amu + "<br>";

                }
                if (!m_StrOldsl_has_byr_otstaning.equals(m_Strsl_has_byr_otstaning)) {
                    m_condition = m_condition + "shb_facty_det_os='" + m_Strsl_has_byr_otstaning + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_cdt_loan_amu + " RF BUYER UOTSTND. CHANGE TO " + m_Strsl_has_byr_cdt_loan_amu + "<br>";

                }
                if (!m_StrOldsl_has_byr_tenor.equals(m_Strsl_has_byr_tenor)) {
                    m_condition = m_condition + "shb_facty_det_tenor='" + m_Strsl_has_byr_tenor + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_tenor + " RF BUYER TENOR CHANGE TO " + m_Strsl_has_byr_tenor + "<br>";

                }
                if (!m_StrOldsl_has_byr_itst_trsry.equals(m_Strsl_has_byr_itst_trsry)) {
                    m_condition = m_condition + "shb_facty_det_irest_trry='" + m_Strsl_has_byr_itst_trsry + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_itst_trsry + " RF INTEREST RATE CHANGE TO " + m_Strsl_has_byr_itst_trsry + "<br>";

                }

//                if (!m_StrOldsl_has_byr_min_comm_limt.equals(m_Strsl_has_byr_min_comm_limt)) {
//                    m_condition = m_condition + "shb_facty_det_main_cmiss_limit='" + m_Strsl_has_byr_min_comm_limt + "',";
//                }
//                if (!m_StrOldsl_has_byr_chq_disc_intst.equals(m_Strsl_has_byr_chq_disc_intstt)) {
//                    m_condition = m_condition + "shb_chq_dis_ricing_intrest='" + m_Strsl_has_byr_chq_disc_intstt + "',";
//                }
//                if (!m_strOld_free_str_comm.equals(m_str_free_str_comm)) {
//                    m_condition = m_condition + "sl_has_byr_frestrut_comm='" + m_str_free_str_comm + "',";
//                }
//
//                if (!m_StrOldsl_has_byr_frestrut_comm_prstage.equals(m_Strsl_has_byr_frestrut_comm_prstage)) {
//                    m_condition = m_condition + "shb_chq_dis_free_strt_comm_prsntage='" + m_Strsl_has_byr_frestrut_comm_prstage + "',";
//                }
//                if (!m_StrOldsl_has_byr_frestrut_comm_ftfee.equals(m_Strsl_has_byr_frestrut_comm_ftfee)) {
//                    m_condition = m_condition + "shb_chq_dis_free_strt_comm_flatfee='" + m_Strsl_has_byr_frestrut_comm_ftfee + "',";
//                }
//
                if (!m_StrOldsl_has_byr_advnce_rate_prstage.equals(m_Strsl_has_byr_advnce_rate_prstage)) {
                    m_condition = m_condition + "shb_chq_dis_adv_rate_prectange='" + m_Strsl_has_byr_advnce_rate_prstage + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_advnce_rate_prstage + " RF IADVANCE RATE CHANGE TO " + m_Strsl_has_byr_advnce_rate_prstage + "<br>";

                }
                if (!m_StrOldsl_has_byr_remarks.equals(m_Strsl_has_byr_remarks)) {
                    m_condition = m_condition + "sl_has_byr_remarks='" + m_Strsl_has_byr_remarks + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_remarks + " REMARKS CHANGE TO " + m_Strsl_has_byr_remarks + "<br>";

                }
                if (!m_StrOldsl_has_byr_active.equals(m_Strsl_has_byr_active)) {
                    m_condition = m_condition + "sl_has_byr_status='" + m_Strsl_has_byr_active + "',";

                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_active + " RELATIONSHIP ESTB. STATUS CHANGE TO " + m_Strsl_has_byr_active + "<br>";
                }

                //cw facility details
                if (!m_StrOldsl_has_byr_warehs_limit.equals(m_Strsl_has_byr_warehs_limit)) {
                    m_condition = m_condition + "sl_has_byr_warehs_limit='" + m_Strsl_has_byr_warehs_limit + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_warehs_limit + " CW BUYER LIMIT CHANGE TO " + m_Strsl_has_byr_warehs_limit + "<br>";

                }
                if (!m_StrOldsl_has_byr_warehs_otstaning.equals(m_Strsl_has_byr_warehs_otstaning)) {
                    m_condition = m_condition + "sl_has_byr_warehs_otstaning='" + m_Strsl_has_byr_warehs_otstaning + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_warehs_otstaning + " CW BUYER OUTSATANDING CHANGE TO " + m_Strsl_has_byr_warehs_otstaning + "<br>";

                }
                if (!m_StrOldsl_has_byr_warehs_tenor.equals(m_Strsl_has_byr_warehs_tenor)) {
                    m_condition = m_condition + "sl_has_byr_warehs_tenor='" + m_Strsl_has_byr_warehs_tenor + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_warehs_tenor + " CW BUYER TENOR CHANGE TO " + m_Strsl_has_byr_warehs_tenor + "<br>";

                }
                if (!m_StrOldsl_has_byr_warehs_fmax_chq_amu.equals(m_Strsl_has_byr_warehs_fmax_chq_amu)) {
                    m_condition = m_condition + "sl_has_byr_warehs_fmax_chq_amu='" + m_Strsl_has_byr_warehs_fmax_chq_amu + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_warehs_fmax_chq_amu + " CW BUYER PER. CHQ. AMU. CHANGE TO " + m_Strsl_has_byr_warehs_fmax_chq_amu + "<br>";

                }

                if (!m_StrOldrec_finance_commision_crg.equals(m_Strrec_finance_commision_crg)) {
                    m_condition = m_condition + "rec_finance_commision_crg='" + m_Strrec_finance_commision_crg + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrec_finance_commision_crg + " RF COMM. CHG. CHANGE TO " + m_Strrec_finance_commision_crg + "<br>";

                }
                if (!m_StrOldrf_tran_base_falt_fee.equals(m_Strrf_tran_base_falt_fee)) {
                    m_condition = m_condition + "rf_tran_base_falt_fee='" + m_Strrf_tran_base_falt_fee + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_tran_base_falt_fee + " RF COMM. CHG. FLAT FEE CHANGE TO " + m_Strrf_tran_base_falt_fee + "<br>";

                }
                if (!m_StrOldrf_tran_base_from_tran.equals(m_Strrf_tran_base_from_tran)) {
                    m_condition = m_condition + "rf_tran_base_from_tran='" + m_Strrf_tran_base_from_tran + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_tran_base_falt_fee + " RF COMM. CHG. TRN. BASE. PRESENTAGE CHANGE TO " + m_Strrf_tran_base_falt_fee + "<br>";

                }
                if (!m_StrOldrf_fixed_rate_amount.equals(m_Strrf_fixed_rate_amount)) {
                    m_condition = m_condition + "rf_fixed_rate_amount='" + m_Strrf_fixed_rate_amount + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_fixed_rate_amount + " RF FIXED RATE AMU. CHANGE TO " + m_Strrf_fixed_rate_amount + "<br>";

                }
                if (!m_StrOldrf_fixed_frequency.equals(m_Strrf_fixed_frequency)) {
                    m_condition = m_condition + "rf_fixed_frequency='" + m_Strrf_fixed_frequency + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_fixed_frequency + " RF FIXED RATE FREQ. CHANGE TO " + m_Strrf_fixed_frequency + "<br>";

                }

                if (!m_StrOldchq_wh_commision_crg.equals(m_Strchq_wh_commision_crg)) {
                    m_condition = m_condition + "chq_wh_commision_crg='" + m_Strchq_wh_commision_crg + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldchq_wh_commision_crg + "CW COMM. CHG. CHANGE TO " + m_Strchq_wh_commision_crg + "<br>";

                }
                if (!m_StrOldcw_tran_base_falt_fee.equals(m_Strcw_tran_base_falt_fee)) {
                    m_condition = m_condition + "cw_tran_base_falt_fee='" + m_Strcw_tran_base_falt_fee + "',";

                    i++;
                    m_change = m_change + i + ")" + m_StrOldcw_tran_base_falt_fee + "CW COMM. CHG. FLAT FEE CHANGE TO " + m_Strcw_tran_base_falt_fee + "<br>";

                }
                if (!m_StrOldcw_tran_base_from_tran.equals(m_Strcw_tran_base_from_tran)) {
                    m_condition = m_condition + "cw_tran_base_from_tran='" + m_Strcw_tran_base_from_tran + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcw_tran_base_from_tran + "CW COMM. CHG. PRESENTAGE FEE CHANGE TO " + m_Strcw_tran_base_from_tran + "<br>";

                }
                if (!m_StrOldcw_fixed_rate_amount.equals(m_Strcw_fixed_rate_amount)) {
                    m_condition = m_condition + "cw_fixed_rate_amount='" + m_Strcw_fixed_rate_amount + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcw_fixed_rate_amount + "CW COMM. CHG. FIXED RATE AMU. CHANGE TO " + m_Strcw_fixed_rate_amount + "<br>";

                }
                if (!m_StrOldcw_fixed_frequency.equals(m_Strcw_fixed_frequency)) {
                    m_condition = m_condition + "cw_fixed_frequency='" + m_Strcw_fixed_frequency + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcw_fixed_frequency + "CW COMM. CHG. FIXED RATE FREQ. CHANGE TO " + m_Strcw_fixed_frequency + "<br>";

                }

                if (!m_condition.equals("")) {
                    m_condition = m_condition + "sl_has_byr_auth='UN-AUTHORIZED',";
                }

                m_strQry = "update ndb_seller_has_buyers set " + m_condition + " "
                        + "sl_has_byr_mod_by='" + m_str_user_id + "',"
                        + "sl_has_byr_mod_date=now()"
                        + " where idndb_seller_has_buyers='" + m_str_idndb_seller_has_buyers + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            }

            if (!m_str_idndb_seller_has_buyers.equals("")) {

                m_strQry = "insert into ndb_change_log (ndb_change_log_type,"
                        + "ndb_attached_id,"
                        + "ndb_change"
                        + ",status"
                        + ",creat_by"
                        + ",creat_date"
                        + ""
                        + ") values("
                        + "'RELESTB',"
                        + "'" + m_str_idndb_seller_has_buyers + "',"
                        + "'" + m_change + "',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            success = true;
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in saving relationship estabilishment data, Exception" + e);
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
        return success;
    }

    public boolean saveCustomerProdMapData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        log.info("cutomer prod map record reveived");
        String m_str_user_id = "";

        String m_Strididndb_cust_prod_map = "";

        String m_Strididndb_cust_prod_mapNeedToSave = "";

        String m_Stridndb_customer_define = "";
        String m_StrOldidndb_customer_define = "";

        String m_Strcust_as_seller = "";
        String m_StrOldcust_as_seller = "";

        String m_Strcust_as_buyer = "";
        String m_StrOldcust_as_buyer = "";

        String m_Strch_rev_fin = "";
        String m_StrOldch_rev_fin = "";

        String m_Strch_ch_we = "";
        String m_StrOldch_ch_we = "";

        String m_Strprod_rel_status = "";
        String m_StrOldprod_rel_status = "";

        //rec finance------------
        String m_Strrec_finance_curr = "";
        String m_StrOldrec_finance_curr = "";

        String m_Strrec_finance_limit = "";
        String m_StrOldrec_finance_limit = "";

        String m_Strrec_finance_Outstanding = "";
        String m_StrOldrec_finance_Outstanding = "";

        String m_Strrec_finance_tenor = "";
        String m_StrOldrec_finance_tenor = "";

        String m_Strrec_finance_inerest_rate = "";
        String m_StrOldrec_finance_inerest_rate = "";

        String m_Strrec_finance_financing = "";
        String m_StrOldrec_finance_financing = "";

        String m_Strrec_finance_acc_num = "";
        String m_StrOldrec_finance_acc_num = "";

        String m_Strrec_finance_cr_dsc_proc_acc_num = "";
        String m_StrOldrec_finance_cr_dsc_proc_acc_num = "";

        String m_Strrec_finance_current_ac = "";
        String m_StrOldrec_finance_current_ac = "";

        String m_Strrec_finance_margin_ac = "";
        String m_StrOldrec_finance_margin_ac = "";

        String m_Strrec_finance_margin = "";
        String m_StrOldrec_finance_margin = "";

        String m_Strcust_credit_rate = "";
        String m_StrOldcust_credit_rate = "";

        String m_Strrec_finance_bulk_credit = "";
        String m_StrOldrec_finance_bulk_credit = "";

        String m_Strrec_finance_erly_wdr_chg = "";
        String m_StrOldrec_finance_erly_wdr_chg = "";

        String m_Strrec_finance_vale_dte_extr_chg = "";
        String m_StrOldrec_finance_vale_dte_extr_chg = "";

        String m_Strrec_finance_erly_stlemnt_chg = "";
        String m_StrOldrec_finance_erly_stlemnt_chg = "";

        String m_Strrec_finance_status = "";
        String m_StrOldrec_finance_status = "";

        //chequw were---------------
        String m_Strchq_wh_limit = "";
        String m_StrOldchq_wh_limit = "";

        String m_Strsl_has_byr_otstaning = "";
        String m_StrOldsl_has_byr_otstaning = "";

        String m_Strsl_has_byr_tenor = "";
        String m_StrOldsl_has_byr_tenor = "";

        String m_Strchq_wh_erly_wdr_chg = "";
        String m_StrOldchq_wh_erly_wdr_chg = "";

        String m_Strchq_wh_vale_dte_extr_chg = "";
        String m_StrOldchq_wh_vale_dte_extr_chg = "";

        String m_Strchq_wh_erly_stlemnt_chg = "";
        String m_StrOldchq_wh_erly_stlemnt_chg = "";

        String m_Strmchq_status = "";
        String m_StrOldchq_status = "";

        String m_strQry = "";

        boolean m_ardy_exist_data = true;

        boolean rec_fin = false;
        boolean ch_we = false;
        boolean rel_on2 = false;

        try {

            m_str_user_id = prm_obj.getString("user_id");
            m_Strididndb_cust_prod_map = prm_obj.getString("idndb_cust_prod_map");
            m_Stridndb_customer_define = prm_obj.getString("idndb_customer_define_id");
            m_Strcust_as_seller = prm_obj.getString("cust_as_seller");
            m_Strcust_as_buyer = prm_obj.getString("cust_as_buyer");
            m_Strch_rev_fin = prm_obj.getString("ch_rev_fin");
            m_Strch_ch_we = prm_obj.getString("ch_ch_we");
            m_Strprod_rel_status = prm_obj.getString("prod_rel_status");

            if (m_Strch_rev_fin.equals("ACTIVE")) {

                rec_fin = true;
                m_Strrec_finance_status = "ACTIVE";
            }
            if (m_Strch_ch_we.equals("ACTIVE")) {
                ch_we = true;
                m_Strmchq_status = "ACTIVE";
            }

            m_Strrec_finance_curr = prm_obj.getString("rec_finance_curr");
            m_Strrec_finance_limit = prm_obj.getString("rec_finance_limit");

            m_Strrec_finance_Outstanding = prm_obj.getString("rec_finance_Outstanding");
            if (m_Strrec_finance_Outstanding.equals("")) {
                m_Strrec_finance_Outstanding = "0.00";
            }
            m_Strrec_finance_tenor = prm_obj.getString("rec_finance_tenor");
            m_Strrec_finance_inerest_rate = prm_obj.getString("rec_finance_inerest_rate");
            m_Strrec_finance_financing = prm_obj.getString("rec_finance_financing");
            m_Strrec_finance_bulk_credit = prm_obj.getString("rec_finance_bulk_credit");

            m_Strrec_finance_erly_wdr_chg = prm_obj.getString("rec_finance_erly_wdr_chg");
            if (m_Strrec_finance_erly_wdr_chg.equals("")) {
                m_Strrec_finance_erly_wdr_chg = "0.00";
            }

            m_Strrec_finance_vale_dte_extr_chg = prm_obj.getString("rec_finance_vale_dte_extr_chg");
            if (m_Strrec_finance_vale_dte_extr_chg.equals("")) {
                m_Strrec_finance_vale_dte_extr_chg = "0.00";
            }

            m_Strrec_finance_erly_stlemnt_chg = prm_obj.getString("rec_finance_erly_stlemnt_chg");
            if (m_Strrec_finance_erly_stlemnt_chg.equals("")) {
                m_Strrec_finance_erly_stlemnt_chg = "0.00";
            }

            //cheque ware housing
            m_Strchq_wh_limit = prm_obj.getString("chq_wh_limit");

            m_Strsl_has_byr_otstaning = prm_obj.getString("sl_has_byr_otstaning");
            m_Strsl_has_byr_tenor = prm_obj.getString("sl_has_byr_tenor");

            m_Strchq_wh_erly_wdr_chg = prm_obj.getString("chq_wh_erly_wdr_chg");
            if (m_Strchq_wh_erly_wdr_chg.equals("")) {
                m_Strchq_wh_erly_wdr_chg = "0.00";
            }

            m_Strchq_wh_vale_dte_extr_chg = prm_obj.getString("chq_wh_vale_dte_extr_chg");
            if (m_Strchq_wh_vale_dte_extr_chg.equals("")) {
                m_Strchq_wh_vale_dte_extr_chg = "0.00";
            }

            m_Strchq_wh_erly_stlemnt_chg = prm_obj.getString("chq_wh_erly_stlemnt_chg");
            if (m_Strchq_wh_erly_stlemnt_chg.equals("")) {
                m_Strchq_wh_erly_stlemnt_chg = "0.00";
            }

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            m_strQry = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + m_Strididndb_cust_prod_map + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            log.info("cheking for recevied cut prod map all ready entr");
            int i = 0;
            String m_change = "";
            if (_rs.next()) {
                log.info("cut prod map all reay exist");

                m_StrOldidndb_customer_define = _rs.getString("idndb_customer_define");
                m_StrOldcust_as_seller = _rs.getString("prod_relationship_key_seller");
                m_StrOldcust_as_buyer = _rs.getString("prod_relationship_key_buyer");
                m_StrOldch_rev_fin = _rs.getString("prod_relationship_res_fin");
                m_StrOldch_ch_we = _rs.getString("prod_relationship_chq_ware");
                m_StrOldprod_rel_status = _rs.getString("prod_relationship_status");

                m_ardy_exist_data = false;
            }

            if (m_ardy_exist_data) {
                i++;
                log.info("new cut prod map");
                m_strQry = "insert into ndb_cust_prod_map (idndb_customer_define,"
                        + "prod_relationship_key_seller,"
                        + "prod_relationship_key_buyer"
                        + ",prod_relationship_status"
                        + ",prod_relationship_auth"
                        + ",prod_relationship_res_fin"
                        + ",prod_relationship_chq_ware"
                        + ",prod_relationship_creat_by"
                        + ",prod_relationship_creat_date"
                        + ",prod_relationship_mod_by"
                        + ",prod_relationship_mod_date"
                        + ""
                        + ") values("
                        + "'" + m_Stridndb_customer_define + "',"
                        + "'" + m_Strcust_as_seller + "',"
                        + "'" + m_Strcust_as_buyer + "',"
                        + "'" + m_Strprod_rel_status + "',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_Strch_rev_fin + "',"
                        + "'" + m_Strch_ch_we + "',"
                        + "'" + m_str_user_id + "',"
                        + "now(),"
                        + "'" + m_str_user_id + "',"
                        + "now())";
                log.info("new cust prod maap mysql quiery m_strQry:" + m_strQry);
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                String max_idndb_cust_prod_map = "";
                m_strQry = "select MAX(idndb_cust_prod_map) as idndb_cust_prod_map from ndb_cust_prod_map";
                _rs2 = _stmnt2.executeQuery(m_strQry);
                if (_rs2.next()) {
                    max_idndb_cust_prod_map = _rs2.getString("idndb_cust_prod_map");
                }

                m_strQry = "insert into ndb_change_log (ndb_change_log_type,"
                        + "ndb_attached_id,"
                        + "ndb_change"
                        + ",status"
                        + ",creat_by"
                        + ",creat_date"
                        + ""
                        + ") values("
                        + "'CUSTPRODMAP',"
                        + "'" + max_idndb_cust_prod_map + "',"
                        + "'" + i + ") NEW CUST PROD MAP',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            } else {

                String m_condition = "";
                if (!m_StrOldidndb_customer_define.equals(m_Stridndb_customer_define)) {
                    i++;
                    m_change = m_change + i + ")" + m_StrOldidndb_customer_define + " ID NDB ID CUSTOMER DEFINE TO " + m_Stridndb_customer_define + "<br>";
                    m_condition = "idndb_customer_define='" + m_Stridndb_customer_define + "',";

                }
                if (!m_StrOldcust_as_seller.equals(m_Strcust_as_seller)) {

                    m_condition = m_condition + "prod_relationship_key_seller='" + m_Strcust_as_seller + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcust_as_seller + " CUST AS SELLER TO " + m_Strcust_as_seller + "<br>";

                }
                if (!m_StrOldcust_as_buyer.equals(m_Strcust_as_buyer)) {
                    m_condition = m_condition + "prod_relationship_key_buyer='" + m_Strcust_as_buyer + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcust_as_buyer + " CUST AS BUYER TO " + m_Strcust_as_buyer + "<br>";

                }
                if (!m_StrOldch_rev_fin.equals(m_Strch_rev_fin)) {
                    m_condition = m_condition + "prod_relationship_res_fin='" + m_Strch_rev_fin + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldch_rev_fin + " RF PROD CHANGE TO " + m_Strch_rev_fin + "<br>";

                }
                if (!m_StrOldch_ch_we.equals(m_Strch_ch_we)) {
                    m_condition = m_condition + "prod_relationship_chq_ware='" + m_Strch_ch_we + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldch_ch_we + " CW PROD TO " + m_Strch_ch_we + "<br>";

                }

                if (!m_StrOldprod_rel_status.equals(m_Strprod_rel_status)) {
                    m_condition = m_condition + "prod_relationship_status='" + m_Strprod_rel_status + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldprod_rel_status + " PROD MAP STATUS CHANGE TO " + m_Strprod_rel_status + "<br>";

                }

                if (!m_condition.equals("")) {
                    m_condition = m_condition + "prod_relationship_auth='UN-AUTHORIZED',";
                }

                m_strQry = "update ndb_cust_prod_map set " + m_condition + " "
                        + "prod_relationship_mod_by='" + m_str_user_id + "',"
                        + "prod_relationship_mod_date=now()"
                        + " where idndb_cust_prod_map='" + m_Strididndb_cust_prod_map + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            }

            if (m_Strididndb_cust_prod_map.equals("")) {
                m_strQry = "select MAX(idndb_cust_prod_map) AS idndb_cust_prod_map  from ndb_cust_prod_map";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    int nextValueIDPRODMAP = Integer.parseInt(_rs.getString("idndb_cust_prod_map"));

                    m_Strididndb_cust_prod_mapNeedToSave = String.valueOf(nextValueIDPRODMAP);
                }

            } else {

                m_Strididndb_cust_prod_mapNeedToSave = m_Strididndb_cust_prod_map;

            }

            if (rec_fin) {
                m_ardy_exist_data = true;

                // RF Data save & Update
                //m_strQry = "select * from ndb_rec_fin where idndb_cust_prod_map='" + m_Strididndb_cust_prod_mapNeedToSave + "' and idndb_customer_define='" + m_Stridndb_customer_define + "'";
                //addedd for the RF Temporary Limit,Temporary Limit Exp Date -- CFU-BRD-4 -- Janaka_5977
                m_strQry = "select ndb_rec_fin.*, temp.ndb_rec_fin_temp_value,temp.ndb_rec_fin_temp_exp_date from ndb_rec_fin , ndb_rec_fin_temp_limits as temp where idndb_cust_prod_map='" + m_Strididndb_cust_prod_mapNeedToSave + "' and idndb_customer_define='" + m_Stridndb_customer_define + "' and ndb_rec_fin.idndb_rec_fin=temp.idndb_rec_fin";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {

                    m_StrOldidndb_customer_define = _rs.getString("idndb_customer_define");
                    m_StrOldrec_finance_curr = _rs.getString("rec_finance_curr");
                    m_StrOldrec_finance_limit = _rs.getString("rec_finance_limit");

                    m_StrOldrec_finance_Outstanding = _rs.getString("rec_finance_Outstanding");
                    m_StrOldrec_finance_tenor = _rs.getString("rec_finance_tenor");
                    m_StrOldrec_finance_inerest_rate = _rs.getString("rec_finance_inerest_rate");
                    m_StrOldrec_finance_financing = _rs.getString("rec_finance_financing");
                    m_StrOldrec_finance_bulk_credit = _rs.getString("rec_finance_bulk_credit");
                    m_StrOldrec_finance_erly_wdr_chg = _rs.getString("rec_finance_erly_wdr_chg");
                    m_StrOldrec_finance_vale_dte_extr_chg = _rs.getString("rec_finance_vale_dte_extr_chg");
                    m_StrOldrec_finance_erly_stlemnt_chg = _rs.getString("rec_finance_erly_stlemnt_chg");
                    m_StrOldrec_finance_status = _rs.getString("rec_finance_status");

                    m_ardy_exist_data = false;
                }

                if (m_ardy_exist_data) {
                    i++;
                    log.info("New RF Recived");
                    m_strQry = "insert into ndb_rec_fin (idndb_customer_define,"
                            + "idndb_cust_prod_map,"
                            + "rec_finance_curr"
                            + ",rec_finance_limit"
                            + ",rec_finance_Outstanding"
                            + ",rec_finance_tenor"
                            + ",rec_finance_inerest_rate"
                            + ",rec_finance_financing"
                            + ",rec_finance_bulk_credit"
                            + ",rec_finance_erly_wdr_chg"
                            + ",rec_finance_vale_dte_extr_chg"
                            + ",rec_finance_erly_stlemnt_chg"
                            + ",rec_finance_status"
                            + ",rec_finance_creat_by"
                            + ",rec_finance_creat_date"
                            + ",rec_finance_mod_by"
                            + ",rec_finance_mod_date"
                            + ""
                            + ") values("
                            + "'" + m_Stridndb_customer_define + "',"
                            + "'" + m_Strididndb_cust_prod_mapNeedToSave + "',"
                            + "'" + m_Strrec_finance_curr + "',"
                            + "'" + m_Strrec_finance_limit + "',"
                            + "'" + m_Strrec_finance_Outstanding + "',"
                            + "'" + m_Strrec_finance_tenor + "',"
                            + "'" + m_Strrec_finance_inerest_rate + "',"
                            + "'" + m_Strrec_finance_financing + "',"
                            + "'" + m_Strrec_finance_bulk_credit + "',"
                            + "'" + m_Strrec_finance_erly_wdr_chg + "',"
                            + "'" + m_Strrec_finance_vale_dte_extr_chg + "',"
                            + "'" + m_Strrec_finance_erly_stlemnt_chg + "',"
                            + "'" + m_Strrec_finance_status + "',"
                            + "'" + m_str_user_id + "',"
                            + "now(),"
                            + "'" + m_str_user_id + "',"
                            + "now())";
                    log.info("New RF Recived m_strQry : " + m_strQry);
                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    String max_idndb_cust_prod_map = "";
                    m_strQry = "select MAX(idndb_cust_prod_map) as idndb_cust_prod_map from ndb_cust_prod_map";
                    _rs2 = _stmnt2.executeQuery(m_strQry);
                    if (_rs2.next()) {
                        max_idndb_cust_prod_map = _rs2.getString("idndb_cust_prod_map");
                    }

                    m_strQry = "insert into ndb_change_log (ndb_change_log_type,"
                            + "ndb_attached_id,"
                            + "ndb_change"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ""
                            + ") values("
                            + "'CUSTPRODMAP',"
                            + "'" + max_idndb_cust_prod_map + "',"
                            + "'" + i + ") NEW CUST PROD MAP RF',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "now())";
                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                } else {

                    String m_condition = "";

                    if (!m_StrOldidndb_customer_define.equals(m_Stridndb_customer_define)) {
                        m_condition = "idndb_customer_define='" + m_Stridndb_customer_define + "',";

                    }

                    if (!m_StrOldrec_finance_curr.equals(m_Strrec_finance_curr)) {
                        m_condition = m_condition + "rec_finance_curr='" + m_Strrec_finance_curr + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_curr + " RF CUR. CHANGE TO " + m_Strrec_finance_curr + "<br>";

                    }
                    if (!m_StrOldrec_finance_limit.equals(m_Strrec_finance_limit)) {
                        m_condition = m_condition + "rec_finance_limit='" + m_Strrec_finance_limit + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_limit + " RF LIMIT CHANGE TO " + m_Strrec_finance_limit + "<br>";

                    }

                    if (!m_StrOldrec_finance_Outstanding.equals(m_Strrec_finance_Outstanding)) {
                        m_condition = m_condition + "rec_finance_Outstanding='" + m_Strrec_finance_Outstanding + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_Outstanding + " RF OUTSTANDING CHANGE TO " + m_Strrec_finance_Outstanding + "<br>";

                    }

                    if (!m_StrOldrec_finance_tenor.equals(m_Strrec_finance_tenor)) {
                        m_condition = m_condition + "rec_finance_tenor='" + m_Strrec_finance_tenor + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_tenor + " RF TENOR CHANGE TO " + m_Strrec_finance_tenor + "<br>";

                    }
                    if (!m_StrOldrec_finance_inerest_rate.equals(m_Strrec_finance_inerest_rate)) {
                        m_condition = m_condition + "rec_finance_inerest_rate='" + m_Strrec_finance_inerest_rate + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_inerest_rate + " RF INTEREST RATE CHANGE TO " + m_Strrec_finance_inerest_rate + "<br>";

                    }
                    if (!m_StrOldrec_finance_financing.equals(m_Strrec_finance_financing)) {
                        m_condition = m_condition + "rec_finance_financing='" + m_Strrec_finance_financing + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_financing + " RF FINANCING RATE CHANGE TO " + m_Strrec_finance_financing + "<br>";

                    }

                    if (!m_StrOldrec_finance_bulk_credit.equals(m_Strrec_finance_bulk_credit)) {
                        m_condition = m_condition + "rec_finance_bulk_credit='" + m_Strrec_finance_bulk_credit + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_bulk_credit + " RF BULK CREDIT OPT. CHANGE TO " + m_Strrec_finance_bulk_credit + "<br>";

                    }

                    if (!m_StrOldrec_finance_erly_wdr_chg.equals(m_Strrec_finance_erly_wdr_chg)) {
                        m_condition = m_condition + "rec_finance_erly_wdr_chg='" + m_Strrec_finance_erly_wdr_chg + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_erly_wdr_chg + " RF ERLY WDR CHG. CHANGE TO " + m_Strrec_finance_erly_wdr_chg + "<br>";

                    }
                    if (!m_StrOldrec_finance_vale_dte_extr_chg.equals(m_Strrec_finance_vale_dte_extr_chg)) {
                        m_condition = m_condition + "rec_finance_vale_dte_extr_chg='" + m_Strrec_finance_vale_dte_extr_chg + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_vale_dte_extr_chg + " RF VALDT. EXT CHG. CHANGE TO " + m_Strrec_finance_vale_dte_extr_chg + "<br>";

                    }

                    if (!m_StrOldrec_finance_erly_stlemnt_chg.equals(m_Strrec_finance_erly_stlemnt_chg)) {
                        m_condition = m_condition + "rec_finance_erly_stlemnt_chg='" + m_Strrec_finance_erly_stlemnt_chg + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_erly_stlemnt_chg + " RF ERLY. STLMT CHG. CHANGE TO " + m_Strrec_finance_erly_stlemnt_chg + "<br>";

                    }

                    if (!m_StrOldrec_finance_status.equals(m_Strrec_finance_status)) {
                        m_condition = m_condition + "rec_finance_status='" + m_Strrec_finance_status + "',";
                    }

                    m_strQry = "update ndb_rec_fin set " + m_condition + " "
                            + "rec_finance_mod_by='" + m_str_user_id + "',"
                            + "rec_finance_mod_date=now()"
                            + " where idndb_cust_prod_map='" + m_Strididndb_cust_prod_mapNeedToSave + "'";

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }
                    m_strQry = "update ndb_cust_prod_map set prod_relationship_auth='UN-AUTHORIZED' "
                            + "prod_relationship_mod_by='" + m_str_user_id + "',"
                            + "prod_relationship_mod_date=now()"
                            + " where idndb_cust_prod_map='" + m_Strididndb_cust_prod_map + "'";

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                }

            }

            //chequw were---------------
            if (ch_we) {
                log.info("new cw received ");
                m_ardy_exist_data = true;
                m_strQry = "select * from ndb_chq_wh where idndb_cust_prod_map='" + m_Strididndb_cust_prod_mapNeedToSave + "' and idndb_customer_define='" + m_Stridndb_customer_define + "'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {

                    m_StrOldidndb_customer_define = _rs.getString("idndb_customer_define");
                    m_StrOldchq_wh_limit = _rs.getString("chq_wh_limit");
                    m_StrOldsl_has_byr_otstaning = _rs.getString("sl_has_byr_otstaning");
                    m_StrOldsl_has_byr_tenor = _rs.getString("sl_has_byr_tenor");

                    m_StrOldchq_wh_erly_wdr_chg = _rs.getString("chq_wh_erly_wdr_chg");
                    m_StrOldchq_wh_vale_dte_extr_chg = _rs.getString("chq_wh_vale_dte_extr_chg");
                    m_StrOldchq_wh_erly_stlemnt_chg = _rs.getString("chq_wh_erly_stlemnt_chg");
                    m_StrOldchq_status = _rs.getString("chq_status");

                    m_ardy_exist_data = false;
                }

                if (m_ardy_exist_data) {
                    i++;
                    m_strQry = "insert into ndb_chq_wh (idndb_customer_define,"
                            + "idndb_cust_prod_map"
                            + ",chq_wh_limit"
                            + ",sl_has_byr_otstaning"
                            + ",sl_has_byr_tenor"
                            + ",chq_wh_erly_wdr_chg"
                            + ",chq_wh_vale_dte_extr_chg"
                            + ",chq_wh_erly_stlemnt_chg"
                            + ",chq_status"
                            + ",chq_creat_by"
                            + ",chq_creat_date"
                            + ",chq_mod_by"
                            + ",chq_mod_date"
                            + ""
                            + ") values("
                            + "'" + m_Stridndb_customer_define + "',"
                            + "'" + m_Strididndb_cust_prod_mapNeedToSave + "',"
                            + "'" + m_Strchq_wh_limit + "',"
                            + "'" + m_Strsl_has_byr_otstaning + "',"
                            + "'" + m_Strsl_has_byr_tenor + "',"
                            + "'" + m_Strchq_wh_erly_wdr_chg + "',"
                            + "'" + m_Strchq_wh_vale_dte_extr_chg + "',"
                            + "'" + m_Strchq_wh_erly_stlemnt_chg + "',"
                            + "'" + m_Strmchq_status + "',"
                            + "'" + m_str_user_id + "',"
                            + "now(),"
                            + "'" + m_str_user_id + "',"
                            + "now())";

                    log.info("new cw received m_strQry : " + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }
                    String max_idndb_cust_prod_map = "";
                    m_strQry = "select MAX(idndb_cust_prod_map) as idndb_cust_prod_map from ndb_cust_prod_map";
                    _rs2 = _stmnt2.executeQuery(m_strQry);
                    if (_rs2.next()) {
                        max_idndb_cust_prod_map = _rs2.getString("idndb_cust_prod_map");
                    }

                    m_strQry = "insert into ndb_change_log (ndb_change_log_type,"
                            + "ndb_attached_id,"
                            + "ndb_change"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ""
                            + ") values("
                            + "'CUSTPRODMAP',"
                            + "'" + max_idndb_cust_prod_map + "',"
                            + "'" + i + ") NEW CUST PROD MAP CW',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "now())";
                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                } else {

                    String m_condition = "";

                    if (!m_StrOldidndb_customer_define.equals(m_Stridndb_customer_define)) {
                        m_condition = "idndb_customer_define='" + m_Stridndb_customer_define + "',";
                    }

                    if (!m_StrOldchq_wh_limit.equals(m_Strchq_wh_limit)) {
                        m_condition = m_condition + "chq_wh_limit='" + m_Strchq_wh_limit + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldchq_wh_limit + " CW CLIMIT CHANGE TO " + m_Strchq_wh_limit + "<br>";

                    }

                    if (!m_StrOldsl_has_byr_otstaning.equals(m_Strsl_has_byr_otstaning)) {
                        m_condition = m_condition + "sl_has_byr_otstaning='" + m_Strsl_has_byr_otstaning + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldsl_has_byr_otstaning + " CW OUTSTANDING CHANGE TO " + m_Strsl_has_byr_otstaning + "<br>";

                    }

                    if (!m_StrOldsl_has_byr_tenor.equals(m_Strsl_has_byr_tenor)) {
                        m_condition = m_condition + "sl_has_byr_tenor='" + m_Strsl_has_byr_tenor + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldsl_has_byr_tenor + " CW TENOR CHANGE TO " + m_Strsl_has_byr_tenor + "<br>";

                    }

                    if (!m_StrOldchq_wh_erly_wdr_chg.equals(m_Strchq_wh_erly_wdr_chg)) {
                        m_condition = m_condition + "chq_wh_erly_wdr_chg='" + m_Strchq_wh_erly_wdr_chg + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldchq_wh_erly_wdr_chg + " CW ERLY. WDR. CHG. CHANGE TO " + m_Strchq_wh_erly_wdr_chg + "<br>";

                    }

                    if (!m_StrOldchq_wh_vale_dte_extr_chg.equals(m_Strchq_wh_vale_dte_extr_chg)) {
                        m_condition = m_condition + "chq_wh_vale_dte_extr_chg='" + m_Strchq_wh_vale_dte_extr_chg + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldchq_wh_vale_dte_extr_chg + " CW VALDTE. EXT. CHG. CHANGE TO " + m_Strchq_wh_vale_dte_extr_chg + "<br>";

                    }
                    if (!m_StrOldchq_wh_erly_stlemnt_chg.equals(m_Strchq_wh_erly_stlemnt_chg)) {
                        m_condition = m_condition + "chq_wh_erly_stlemnt_chg='" + m_Strchq_wh_erly_stlemnt_chg + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldchq_wh_erly_stlemnt_chg + " CW ERLYSET CHG. CHANGE TO " + m_Strchq_wh_erly_stlemnt_chg + "<br>";

                    }
                    if (!m_StrOldchq_status.equals(m_Strmchq_status)) {
                        m_condition = m_condition + "chq_status='" + m_Strmchq_status + "',";
                    }

                    m_strQry = "update ndb_chq_wh set " + m_condition + " "
                            + "chq_mod_by='" + m_str_user_id + "',"
                            + "chq_mod_date=now()"
                            + " where idndb_cust_prod_map='" + m_Strididndb_cust_prod_mapNeedToSave + "'";

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }
                    m_strQry = "update ndb_cust_prod_map set prod_relationship_auth='UN-AUTHORIZED' "
                            + "prod_relationship_mod_by='" + m_str_user_id + "',"
                            + "prod_relationship_mod_date=now()"
                            + " where idndb_cust_prod_map='" + m_Strididndb_cust_prod_map + "'";

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                }

            }
            if (!m_Strididndb_cust_prod_map.equals("")) {

                m_strQry = "insert into ndb_change_log (ndb_change_log_type,"
                        + "ndb_attached_id,"
                        + "ndb_change"
                        + ",status"
                        + ",creat_by"
                        + ",creat_date"
                        + ""
                        + ") values("
                        + "'CUSTPRODMAP',"
                        + "'" + m_Strididndb_cust_prod_map + "',"
                        + "'" + m_change + "',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            success = true;
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in saving cutomer product mapping data, Exception" + e);
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
        return success;
    }

    public JSONArray getBankDataTofill(String prm_stridndb_bank_master_file) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_bank_master_file where idndb_bank_master_file='" + prm_stridndb_bank_master_file + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_bank_master_file", m_rs.getString("idndb_bank_master_file"));
                m_jsObj.put("bank_name", m_rs.getString("bank_name"));
                m_jsObj.put("bank_code", m_rs.getString("bank_code"));
                m_jsObj.put("bank_status", m_rs.getString("bank_status"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getUserDataTofill(String prm_stridndb_user_master) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_user_master where idndb_user_master='" + prm_stridndb_user_master + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_user_master", m_rs.getString("idndb_user_master"));
                m_jsObj.put("ndb_user_user_id", m_rs.getString("ndb_user_user_id"));
                m_jsObj.put("ndb_user_name", m_rs.getString("ndb_user_name"));
                m_jsObj.put("ndb_user_department", m_rs.getString("ndb_user_department"));
                m_jsObj.put("ndb_user_rms", m_rs.getString("ndb_user_rms"));
                m_jsObj.put("ndb_user_poms", m_rs.getString("ndb_user_poms"));
                m_jsObj.put("ndb_user_um", m_rs.getString("ndb_user_um"));
                m_jsObj.put("idndb_user_levels", m_rs.getString("idndb_user_levels"));
                m_jsObj.put("ndb_user_status", m_rs.getString("ndb_user_status"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getUserLevelsDataTofill(String prm_stridndb_user_levels) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_user_levels where idndb_user_levels='" + prm_stridndb_user_levels + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_user_levels", m_rs.getString("idndb_user_levels"));
                m_jsObj.put("ndb_user_level", m_rs.getString("ndb_user_level"));
                m_jsObj.put("ndb_user_level_status", m_rs.getString("ndb_user_level_status"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getFormDataTofill(String prm_stridndb_user_forms) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_user_forms where idndb_user_forms='" + prm_stridndb_user_forms + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_user_forms", m_rs.getString("idndb_user_forms"));
                m_jsObj.put("ndb_form_name", m_rs.getString("ndb_form_name"));
                m_jsObj.put("ndb_form_path", m_rs.getString("ndb_form_path"));

                if (m_rs.getString("ndb_form_main_menu_id").equals("#")) {
                    m_jsObj.put("ndb_form_main_menu_id", "MM");
                } else {
                    m_jsObj.put("ndb_form_main_menu_id", m_rs.getString("ndb_form_main_menu_id"));
                }

                m_jsObj.put("ndb_form_oder_by", m_rs.getString("ndb_form_oder_by"));
                m_jsObj.put("ndb_user_system_group", m_rs.getString("ndb_user_system_group"));
                m_jsObj.put("ndb_form_status", m_rs.getString("ndb_form_status"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getIndustryDataTofill(String prm_stridndb_bank_master_file) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_industry_master_file where idndb_industry_master_file='" + prm_stridndb_bank_master_file + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_industry_master_file", m_rs.getString("idndb_industry_master_file"));
                m_jsObj.put("indus_id", m_rs.getString("indus_id"));
                m_jsObj.put("indus_des", m_rs.getString("indus_des"));
                m_jsObj.put("indus_status", m_rs.getString("indus_status"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getGeoDataTofill(String prm_stridndb_bank_master_file) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_geo_market_master_file where idndb_geo_market_master_file='" + prm_stridndb_bank_master_file + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_geo_market_master_file", m_rs.getString("idndb_geo_market_master_file"));
                m_jsObj.put("geo_market_id", m_rs.getString("geo_market_id"));
                m_jsObj.put("geo_market_desc", m_rs.getString("geo_market_desc"));
                m_jsObj.put("geo_market_status", m_rs.getString("geo_market_status"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getHolidayDataTofill(String prm_stridndb_holiday_marker) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_holiday_marker where idndb_holiday_marker='" + prm_stridndb_holiday_marker + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_holiday_marker", m_rs.getString("idndb_holiday_marker"));
                m_jsObj.put("ndb_holiday", m_rs.getString("ndb_holiday"));
                m_jsObj.put("ndb_holiday_status", m_rs.getString("ndb_holiday_status"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getCustomerDataTofill(String prm_stridndb_customer_define) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_customer_define where idndb_customer_define='" + prm_stridndb_customer_define + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_customer_define", m_rs.getString("idndb_customer_define"));
                m_jsObj.put("cust_id", m_rs.getString("cust_id"));
                m_jsObj.put("cust_name", m_rs.getString("cust_name"));
                m_jsObj.put("cust_short_name", m_rs.getString("cust_short_name"));
                m_jsObj.put("cust_industry_id", m_rs.getString("cust_industry_id"));
                m_jsObj.put("cust_contact_number", m_rs.getString("cust_contact_number"));
                m_jsObj.put("cust_fax_number", m_rs.getString("cust_fax_number"));
                m_jsObj.put("cust_contact_per1", m_rs.getString("cust_contact_per1"));
                m_jsObj.put("cust_contact_per2", m_rs.getString("cust_contact_per2"));
                m_jsObj.put("cust_email", m_rs.getString("cust_email"));
                m_jsObj.put("cust_address", m_rs.getString("cust_address"));
                m_jsObj.put("rec_finance_acc_num", m_rs.getString("rec_finance_acc_num"));
                m_jsObj.put("rec_finance_cr_dsc_proc_acc_num", m_rs.getString("rec_finance_cr_dsc_proc_acc_num"));
                m_jsObj.put("rec_finance_curr_ac", m_rs.getString("rec_finance_curr_ac"));
                m_jsObj.put("cms_curr_acc_number", m_rs.getString("cms_curr_acc_number"));
                m_jsObj.put("cms_collection_acc_number", m_rs.getString("cms_collection_acc_number"));
                m_jsObj.put("rec_finance_margin_ac", m_rs.getString("rec_finance_margin_ac"));
                m_jsObj.put("rec_finance_margin", m_rs.getString("rec_finance_margin"));
                m_jsObj.put("cust_credit_rate", m_rs.getString("cust_credit_rate"));
                m_jsObj.put("idndb_bank_master_file", m_rs.getString("idndb_bank_master_file"));
                m_jsObj.put("idndb_branch_master_file", m_rs.getString("idndb_branch_master_file"));
                m_jsObj.put("cust_other_bank_ac_no", m_rs.getString("cust_other_bank_ac_no"));
                m_jsObj.put("idndb_geo_market_master_file", m_rs.getString("idndb_geo_market_master_file"));
                m_jsObj.put("cust_status", m_rs.getString("cust_status"));
                m_jsObj.put("cust_is_ndb_cust", m_rs.getString("cust_is_ndb_cust"));
                m_jsObj.put("statusReason", m_rs.getString("cust_status_reason"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_customer_data_tofill_USING_CUST_ID(String cust_id) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_customer_define where cust_id='" + cust_id + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_customer_define", m_rs.getString("idndb_customer_define"));
                m_jsObj.put("cust_id", m_rs.getString("cust_id"));
                m_jsObj.put("cust_name", m_rs.getString("cust_name"));
                m_jsObj.put("cust_short_name", m_rs.getString("cust_short_name"));
                m_jsObj.put("cust_industry_id", m_rs.getString("cust_industry_id"));
                m_jsObj.put("cust_contact_number", m_rs.getString("cust_contact_number"));
                m_jsObj.put("cust_fax_number", m_rs.getString("cust_fax_number"));
                m_jsObj.put("cust_contact_per1", m_rs.getString("cust_contact_per1"));
                m_jsObj.put("cust_contact_per2", m_rs.getString("cust_contact_per2"));
                m_jsObj.put("cust_email", m_rs.getString("cust_email"));
                m_jsObj.put("cust_address", m_rs.getString("cust_address"));
                m_jsObj.put("rec_finance_acc_num", m_rs.getString("rec_finance_acc_num"));
                m_jsObj.put("rec_finance_cr_dsc_proc_acc_num", m_rs.getString("rec_finance_cr_dsc_proc_acc_num"));
                m_jsObj.put("rec_finance_curr_ac", m_rs.getString("rec_finance_curr_ac"));
                m_jsObj.put("cms_curr_acc_number", m_rs.getString("cms_curr_acc_number"));
                m_jsObj.put("cms_collection_acc_number", m_rs.getString("cms_collection_acc_number"));

                m_jsObj.put("rec_finance_margin_ac", m_rs.getString("rec_finance_margin_ac"));
                m_jsObj.put("rec_finance_margin", m_rs.getString("rec_finance_margin"));
                m_jsObj.put("cust_credit_rate", m_rs.getString("cust_credit_rate"));
                m_jsObj.put("idndb_bank_master_file", m_rs.getString("idndb_bank_master_file"));
                m_jsObj.put("idndb_branch_master_file", m_rs.getString("idndb_branch_master_file"));
                m_jsObj.put("cust_other_bank_ac_no", m_rs.getString("cust_other_bank_ac_no"));
                m_jsObj.put("idndb_geo_market_master_file", m_rs.getString("idndb_geo_market_master_file"));
                m_jsObj.put("cust_status", m_rs.getString("cust_status"));
                m_jsObj.put("cust_is_ndb_cust", m_rs.getString("cust_is_ndb_cust"));
                m_jsObj.put("statusReason", m_rs.getString("cust_status_reason"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getProdMapDataTofill(String prm_stridndb_cust_prod_map) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();
            m_jsObj = new JSONObject();
            m_chstrsql = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + prm_stridndb_cust_prod_map + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String prm_stridndb_customer_define = "";
            if (m_rs.next()) {

                m_jsObj.put("idndb_cust_prod_map", m_rs.getString("idndb_cust_prod_map"));
                prm_stridndb_customer_define = m_rs.getString("idndb_customer_define");
                m_jsObj.put("idndb_customer_define", m_rs.getString("idndb_customer_define"));
                m_jsObj.put("prod_relationship_key_seller", m_rs.getString("prod_relationship_key_seller"));
                m_jsObj.put("prod_relationship_key_buyer", m_rs.getString("prod_relationship_key_buyer"));
                m_jsObj.put("prod_relationship_status", m_rs.getString("prod_relationship_status"));
                m_jsObj.put("statusReason", m_rs.getString("prod_relationship_status_reason"));
                m_jsObj.put("prod_relationship_res_fin", m_rs.getString("prod_relationship_res_fin"));
                m_jsObj.put("prod_relationship_chq_ware", m_rs.getString("prod_relationship_chq_ware"));
                m_jsObj.put("prod_relationship_reship_only", m_rs.getString("prod_relationship_reship_only"));

            }

            m_chstrsql = "select SUM(pdc_chq_discounting_amu) as rec_finance_Outstanding  from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + prm_stridndb_cust_prod_map + "' and pdc_req_financing='RF' and pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and pdc_chq_status_auth in ('AUTHORIZED','UN-AUTHORIZED')";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String rec_finance_Outstanding = "0.00";
            if (m_rs.next()) {
                if (!(m_rs.getString("rec_finance_Outstanding") == null)) {
                    rec_finance_Outstanding = m_rs.getString("rec_finance_Outstanding");
                }

            }

            m_chstrsql = "select * from ndb_rec_fin where idndb_cust_prod_map='" + prm_stridndb_cust_prod_map + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            int idndb_rec_fin = 0;
            if (m_rs.next()) {
                idndb_rec_fin = m_rs.getInt("idndb_rec_fin");
                m_jsObj.put("idndb_rec_fin", m_rs.getString("idndb_rec_fin"));
                m_jsObj.put("rec_finance_limit", df.format(Double.parseDouble(m_rs.getString("rec_finance_limit"))));

                m_jsObj.put("rec_finance_Outstanding", df.format(Double.parseDouble(rec_finance_Outstanding)));
                m_jsObj.put("rec_finance_curr", m_rs.getString("rec_finance_curr"));
                m_jsObj.put("rec_finance_tenor", m_rs.getString("rec_finance_tenor"));
                m_jsObj.put("rec_finance_inerest_rate", m_rs.getString("rec_finance_inerest_rate"));
                m_jsObj.put("rec_finance_financing", m_rs.getString("rec_finance_financing"));
                m_jsObj.put("rec_finance_bulk_credit", m_rs.getString("rec_finance_bulk_credit"));
                m_jsObj.put("rec_finance_balance_transfer", m_rs.getString("rec_finance_balance_transfer"));
                m_jsObj.put("rec_finance_erly_wdr_chg", df.format(Double.parseDouble(m_rs.getString("rec_finance_erly_wdr_chg"))));
                m_jsObj.put("rec_finance_vale_dte_extr_chg", df.format(Double.parseDouble(m_rs.getString("rec_finance_vale_dte_extr_chg"))));
                m_jsObj.put("rec_finance_erly_stlemnt_chg", df.format(Double.parseDouble(m_rs.getString("rec_finance_erly_stlemnt_chg"))));
                m_jsObj.put("rec_finance_status", m_rs.getString("rec_finance_status"));

            }

            String buyerAccsDtls = "";
            if (!prm_stridndb_cust_prod_map.equals("0")) {
                m_chstrsql = "SELECT * FROM buyer_accs_details where idndb_cust_prod_map='" + prm_stridndb_cust_prod_map + "' order by idbuyer_accs_details";
                m_rs = m_stamt.executeQuery(m_chstrsql);

                while (m_rs.next()) {
                    String recordNo = m_rs.getString("buyer_accs_record_no");
                    String bankCode = m_rs.getString("buyer_accs_bank_code");
                    String BranchCode = m_rs.getString("buyer_accs_branch_code");
                    String AccNo = m_rs.getString("buyer_accs_acc_no");
                    buyerAccsDtls += recordNo + "##" + bankCode + "##" + BranchCode + "##" + AccNo + ",";
                }
                if (!buyerAccsDtls.equals("")) {
                    buyerAccsDtls = buyerAccsDtls.substring(0, buyerAccsDtls.length() - 1);
                }
            }

            m_jsObj.put("buyer_accs", buyerAccsDtls);

            String temporaryLimits = "";
            if (idndb_rec_fin != 0) {
                m_chstrsql = "SELECT * FROM ndb_rec_fin_temp_limits where idndb_rec_fin='" + idndb_rec_fin + "' order by idndb_rec_fin_temp_limits";
                m_rs = m_stamt.executeQuery(m_chstrsql);

                while (m_rs.next()) {
                    String recordNo = m_rs.getString("ndb_rec_fin_temp_limit_record_no");
                    String tempValue = m_rs.getString("ndb_rec_fin_temp_value");
                    String expDate = m_rs.getString("ndb_rec_fin_temp_exp_date");
                    temporaryLimits += recordNo + "##" + tempValue + "##" + expDate + ",";
                }
                if (!temporaryLimits.equals("")) {
                    temporaryLimits = temporaryLimits.substring(0, temporaryLimits.length() - 1);
                }
            }

            m_jsObj.put("rf_temporary_limits", temporaryLimits);

            m_chstrsql = "select * from ndb_customer_define where idndb_customer_define='" + prm_stridndb_customer_define + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj.put("rec_finance_acc_num", m_rs.getString("rec_finance_acc_num"));
                m_jsObj.put("rec_finance_cr_dsc_proc_acc_num", m_rs.getString("rec_finance_cr_dsc_proc_acc_num"));
                m_jsObj.put("rec_finance_current_ac", m_rs.getString("rec_finance_curr_ac"));
                m_jsObj.put("cms_curr_acc_number", m_rs.getString("cms_curr_acc_number"));
                m_jsObj.put("cms_collection_acc_number", m_rs.getString("cms_collection_acc_number"));
                m_jsObj.put("rec_finance_margin_ac", m_rs.getString("rec_finance_margin_ac"));
                m_jsObj.put("rec_finance_margin", m_rs.getString("rec_finance_margin"));
                m_jsObj.put("cust_credit_rate", m_rs.getString("cust_credit_rate"));

            }

            m_chstrsql = "select SUM(pdc_chq_amu) as sl_has_byr_otstaning  from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + prm_stridndb_cust_prod_map + "' and pdc_req_financing='CW' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth='AUTHORIZED'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String sl_has_byr_otstaning = "0.00";
            if (m_rs.next()) {

                if (!(m_rs.getString("sl_has_byr_otstaning") == null)) {
                    sl_has_byr_otstaning = m_rs.getString("sl_has_byr_otstaning");
                }
            }

            m_chstrsql = "select * from ndb_chq_wh where idndb_cust_prod_map='" + prm_stridndb_cust_prod_map + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            int idndb_chq_wh = 0;
            if (m_rs.next()) {
                idndb_chq_wh = m_rs.getInt("idndb_chq_wh");
                m_jsObj.put("idndb_chq_wh", m_rs.getString("idndb_chq_wh"));
                m_jsObj.put("chq_wh_limit", df.format(Double.parseDouble(m_rs.getString("chq_wh_limit"))));
                // m_jsObj.put("sl_has_byr_fmax_chq_amu", df.format(Double.parseDouble(m_rs.getString("sl_has_byr_fmax_chq_amu"))));
                m_jsObj.put("sl_has_byr_otstaning", df.format(Double.parseDouble(sl_has_byr_otstaning)));
                m_jsObj.put("sl_has_byr_tenor", m_rs.getString("sl_has_byr_tenor"));
                m_jsObj.put("chq_wh_dr_to_cr_spe_narr", m_rs.getString("chq_wh_dr_to_cr_spe_narr"));

                m_jsObj.put("chq_wh_erly_wdr_chg", df.format(Double.parseDouble(m_rs.getString("chq_wh_erly_wdr_chg"))));
                m_jsObj.put("chq_wh_vale_dte_extr_chg", df.format(Double.parseDouble(m_rs.getString("chq_wh_vale_dte_extr_chg"))));
                m_jsObj.put("chq_wh_erly_stlemnt_chg", df.format(Double.parseDouble(m_rs.getString("chq_wh_erly_stlemnt_chg"))));
                m_jsObj.put("chq_status", m_rs.getString("chq_status"));

            }

            String cwTemporaryLimits = "";
            if (idndb_chq_wh != 0) {
                m_chstrsql = "SELECT * FROM ndb_chq_wh_temp_limits where idndb_chq_wh='" + idndb_chq_wh + "' order by idndb_chq_wh_temp_limits";
                m_rs = m_stamt.executeQuery(m_chstrsql);

                while (m_rs.next()) {
                    String recordNo = m_rs.getString("ndb_chq_wh_temp_limit_record_no");
                    String tempValue = m_rs.getString("ndb_chq_wh_temp_value");
                    String expDate = m_rs.getString("ndb_chq_wh_temp_exp_date");
                    cwTemporaryLimits += recordNo + "##" + tempValue + "##" + expDate + ",";
                }
                if (!cwTemporaryLimits.equals("")) {
                    cwTemporaryLimits = cwTemporaryLimits.substring(0, cwTemporaryLimits.length() - 1);
                }
            }

            m_jsObj.put("cw_temporary_limits", cwTemporaryLimits);

            m_jsArr.put(i, m_jsObj);
            i++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getProdMapDataTofillAsSeller(String idndb_customer_define_id, String cust_as_seller) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();
            m_jsObj = new JSONObject();
            m_chstrsql = "select * from ndb_cust_prod_map where idndb_customer_define='" + idndb_customer_define_id + "' and prod_relationship_key_seller='" + cust_as_seller + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String prm_stridndb_customer_define = "";
            String idndb_cust_prod_map = "";
            if (m_rs.next()) {

                idndb_cust_prod_map = m_rs.getString("idndb_cust_prod_map");

                m_jsObj.put("idndb_cust_prod_map", m_rs.getString("idndb_cust_prod_map"));
                m_jsObj.put("data_availablity", "ACTIVE");
                prm_stridndb_customer_define = m_rs.getString("idndb_customer_define");
                m_jsObj.put("idndb_customer_define", m_rs.getString("idndb_customer_define"));
                m_jsObj.put("prod_relationship_key_seller", m_rs.getString("prod_relationship_key_seller"));
                m_jsObj.put("prod_relationship_key_buyer", m_rs.getString("prod_relationship_key_buyer"));
                m_jsObj.put("prod_relationship_status", m_rs.getString("prod_relationship_status"));
                m_jsObj.put("statusReason", m_rs.getString("prod_relationship_status_reason"));
                m_jsObj.put("prod_relationship_res_fin", m_rs.getString("prod_relationship_res_fin"));
                m_jsObj.put("prod_relationship_chq_ware", m_rs.getString("prod_relationship_chq_ware"));
                m_jsObj.put("prod_relationship_reship_only", m_rs.getString("prod_relationship_reship_only"));

            }

            m_chstrsql = "select SUM(pdc_chq_discounting_amu) as rec_finance_Outstanding  from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_cust_prod_map + "' and pdc_req_financing='RF' and pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and pdc_chq_status_auth in ('AUTHORIZED','UN-AUTHORIZED')";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String rec_finance_Outstanding = "0.00";
            if (m_rs.next()) {
                if (!(m_rs.getString("rec_finance_Outstanding") == null)) {
                    rec_finance_Outstanding = m_rs.getString("rec_finance_Outstanding");
                }

            }

            m_chstrsql = "select * from ndb_rec_fin where idndb_cust_prod_map='" + idndb_cust_prod_map + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            int idndb_rec_fin = 0;
            if (m_rs.next()) {
                idndb_rec_fin = m_rs.getInt("idndb_rec_fin");
                m_jsObj.put("idndb_rec_fin", m_rs.getString("idndb_rec_fin"));
                m_jsObj.put("rec_finance_limit", df.format(Double.parseDouble(m_rs.getString("rec_finance_limit"))));

                m_jsObj.put("rec_finance_Outstanding", df.format(Double.parseDouble(rec_finance_Outstanding)));
                m_jsObj.put("rec_finance_tenor", m_rs.getString("rec_finance_tenor"));
                m_jsObj.put("rec_finance_inerest_rate", m_rs.getString("rec_finance_inerest_rate"));
                m_jsObj.put("rec_finance_financing", m_rs.getString("rec_finance_financing"));
                m_jsObj.put("rec_finance_bulk_credit", m_rs.getString("rec_finance_bulk_credit"));
                m_jsObj.put("rec_finance_balance_transfer", m_rs.getString("rec_finance_balance_transfer"));
                m_jsObj.put("rec_finance_erly_wdr_chg", df.format(Double.parseDouble(m_rs.getString("rec_finance_erly_wdr_chg"))));
                m_jsObj.put("rec_finance_vale_dte_extr_chg", df.format(Double.parseDouble(m_rs.getString("rec_finance_vale_dte_extr_chg"))));
                m_jsObj.put("rec_finance_erly_stlemnt_chg", df.format(Double.parseDouble(m_rs.getString("rec_finance_erly_stlemnt_chg"))));
                m_jsObj.put("rec_finance_status", m_rs.getString("rec_finance_status"));

            }
            String temporaryLimits = "";
            if (idndb_rec_fin != 0) {
                m_chstrsql = "SELECT * FROM ndb_rec_fin_temp_limits where idndb_rec_fin='" + idndb_rec_fin + "' order by idndb_rec_fin_temp_limits";
                m_rs = m_stamt.executeQuery(m_chstrsql);

                while (m_rs.next()) {
                    String recordNo = m_rs.getString("ndb_rec_fin_temp_limit_record_no");
                    String tempValue = m_rs.getString("ndb_rec_fin_temp_value");
                    String expDate = m_rs.getString("ndb_rec_fin_temp_exp_date");
                    temporaryLimits += recordNo + "##" + tempValue + "##" + expDate + ",";
                }
                if (!temporaryLimits.equals("")) {
                    temporaryLimits = temporaryLimits.substring(0, temporaryLimits.length() - 1);
                }
            }

            m_jsObj.put("rf_temporary_limits", temporaryLimits);

            m_chstrsql = "select * from ndb_customer_define where idndb_customer_define='" + prm_stridndb_customer_define + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj.put("rec_finance_acc_num", m_rs.getString("rec_finance_acc_num"));
                m_jsObj.put("rec_finance_cr_dsc_proc_acc_num", m_rs.getString("rec_finance_cr_dsc_proc_acc_num"));
                m_jsObj.put("rec_finance_current_ac", m_rs.getString("rec_finance_curr_ac"));
                m_jsObj.put("cms_curr_acc_number", m_rs.getString("cms_curr_acc_number"));
                m_jsObj.put("cms_collection_acc_number", m_rs.getString("cms_collection_acc_number"));
                m_jsObj.put("rec_finance_margin_ac", m_rs.getString("rec_finance_margin_ac"));
                m_jsObj.put("rec_finance_margin", m_rs.getString("rec_finance_margin"));
                m_jsObj.put("cust_credit_rate", m_rs.getString("cust_credit_rate"));

            }

            m_chstrsql = "select SUM(pdc_chq_amu) as sl_has_byr_otstaning  from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_cust_prod_map + "' and pdc_req_financing='CW' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth='AUTHORIZED'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String sl_has_byr_otstaning = "0.00";
            if (m_rs.next()) {

                if (!(m_rs.getString("sl_has_byr_otstaning") == null)) {
                    sl_has_byr_otstaning = m_rs.getString("sl_has_byr_otstaning");
                }
            }

            m_chstrsql = "select * from ndb_chq_wh where idndb_cust_prod_map='" + idndb_cust_prod_map + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            int idndb_chq_wh = 0;
            if (m_rs.next()) {
                idndb_chq_wh = m_rs.getInt("idndb_chq_wh");
                m_jsObj.put("idndb_chq_wh", m_rs.getString("idndb_chq_wh"));
                m_jsObj.put("chq_wh_limit", df.format(Double.parseDouble(m_rs.getString("chq_wh_limit"))));
                // m_jsObj.put("sl_has_byr_fmax_chq_amu", df.format(Double.parseDouble(m_rs.getString("sl_has_byr_fmax_chq_amu"))));
                m_jsObj.put("sl_has_byr_otstaning", df.format(Double.parseDouble(sl_has_byr_otstaning)));
                m_jsObj.put("sl_has_byr_tenor", m_rs.getString("sl_has_byr_tenor"));
                m_jsObj.put("chq_wh_dr_to_cr_spe_narr", m_rs.getString("chq_wh_dr_to_cr_spe_narr"));

                m_jsObj.put("chq_wh_erly_wdr_chg", df.format(Double.parseDouble(m_rs.getString("chq_wh_erly_wdr_chg"))));
                m_jsObj.put("chq_wh_vale_dte_extr_chg", df.format(Double.parseDouble(m_rs.getString("chq_wh_vale_dte_extr_chg"))));
                m_jsObj.put("chq_wh_erly_stlemnt_chg", df.format(Double.parseDouble(m_rs.getString("chq_wh_erly_stlemnt_chg"))));
                m_jsObj.put("chq_status", m_rs.getString("chq_status"));

            }

            String cwTemporaryLimits = "";
            if (idndb_chq_wh != 0) {
                m_chstrsql = "SELECT * FROM ndb_chq_wh_temp_limits where idndb_chq_wh='" + idndb_chq_wh + "' order by idndb_chq_wh_temp_limits";
                m_rs = m_stamt.executeQuery(m_chstrsql);

                while (m_rs.next()) {
                    String recordNo = m_rs.getString("ndb_chq_wh_temp_limit_record_no");
                    String tempValue = m_rs.getString("ndb_chq_wh_temp_value");
                    String expDate = m_rs.getString("ndb_chq_wh_temp_exp_date");
                    cwTemporaryLimits += recordNo + "##" + tempValue + "##" + expDate + ",";
                }
                if (!cwTemporaryLimits.equals("")) {
                    cwTemporaryLimits = cwTemporaryLimits.substring(0, cwTemporaryLimits.length() - 1);
                }
            }

            m_jsObj.put("cw_temporary_limits", cwTemporaryLimits);
            m_jsArr.put(i, m_jsObj);
            i++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getProdMapDataTofillAsBuyer(String idndb_customer_define_id, String cust_as_buyer) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();
            m_jsObj = new JSONObject();
            m_chstrsql = "select * from ndb_cust_prod_map where idndb_customer_define='" + idndb_customer_define_id + "' and prod_relationship_key_buyer='" + cust_as_buyer + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String prm_stridndb_customer_define = "";
            String idndb_cust_prod_map = "";
            if (m_rs.next()) {

                idndb_cust_prod_map = m_rs.getString("idndb_cust_prod_map");

                m_jsObj.put("idndb_cust_prod_map", m_rs.getString("idndb_cust_prod_map"));
                m_jsObj.put("data_availablity", "ACTIVE");
                prm_stridndb_customer_define = m_rs.getString("idndb_customer_define");
                m_jsObj.put("idndb_customer_define", m_rs.getString("idndb_customer_define"));
                m_jsObj.put("prod_relationship_key_seller", m_rs.getString("prod_relationship_key_seller"));
                m_jsObj.put("prod_relationship_key_buyer", m_rs.getString("prod_relationship_key_buyer"));
                m_jsObj.put("prod_relationship_status", m_rs.getString("prod_relationship_status"));
                m_jsObj.put("statusReason", m_rs.getString("prod_relationship_status_reason"));
                m_jsObj.put("prod_relationship_res_fin", m_rs.getString("prod_relationship_res_fin"));
                m_jsObj.put("prod_relationship_chq_ware", m_rs.getString("prod_relationship_chq_ware"));
                m_jsObj.put("prod_relationship_reship_only", m_rs.getString("prod_relationship_reship_only"));

            }

            m_chstrsql = "select SUM(pdc_chq_discounting_amu) as rec_finance_Outstanding  from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_cust_prod_map + "' and pdc_req_financing='RF' and pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and pdc_chq_status_auth='AUTHORIZED'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String rec_finance_Outstanding = "0.00";
            if (m_rs.next()) {
                if (!(m_rs.getString("rec_finance_Outstanding") == null)) {
                    rec_finance_Outstanding = m_rs.getString("rec_finance_Outstanding");
                }

            }

            m_chstrsql = "select * from ndb_rec_fin where idndb_cust_prod_map='" + idndb_cust_prod_map + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            int idndb_rec_fin = 0;
            if (m_rs.next()) {
                idndb_rec_fin = m_rs.getInt("idndb_rec_fin");
                m_jsObj.put("idndb_rec_fin", m_rs.getString("idndb_rec_fin"));
                m_jsObj.put("rec_finance_limit", df.format(Double.parseDouble(m_rs.getString("rec_finance_limit"))));
                m_jsObj.put("rec_finance_Outstanding", df.format(Double.parseDouble(rec_finance_Outstanding)));
                m_jsObj.put("rec_finance_tenor", m_rs.getString("rec_finance_tenor"));
                m_jsObj.put("rec_finance_inerest_rate", m_rs.getString("rec_finance_inerest_rate"));
                m_jsObj.put("rec_finance_financing", m_rs.getString("rec_finance_financing"));
                m_jsObj.put("rec_finance_bulk_credit", m_rs.getString("rec_finance_bulk_credit"));
                m_jsObj.put("rec_finance_balance_transfer", m_rs.getString("rec_finance_balance_transfer"));
                m_jsObj.put("rec_finance_erly_wdr_chg", df.format(Double.parseDouble(m_rs.getString("rec_finance_erly_wdr_chg"))));
                m_jsObj.put("rec_finance_vale_dte_extr_chg", df.format(Double.parseDouble(m_rs.getString("rec_finance_vale_dte_extr_chg"))));
                m_jsObj.put("rec_finance_erly_stlemnt_chg", df.format(Double.parseDouble(m_rs.getString("rec_finance_erly_stlemnt_chg"))));
                m_jsObj.put("rec_finance_status", m_rs.getString("rec_finance_status"));

            }

            String buyerAccsDtls = "";
            if (!idndb_cust_prod_map.equals("0")) {
                m_chstrsql = "SELECT * FROM buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map + "' order by idbuyer_accs_details";
                m_rs = m_stamt.executeQuery(m_chstrsql);

                while (m_rs.next()) {
                    String recordNo = m_rs.getString("buyer_accs_record_no");
                    String bankCode = m_rs.getString("buyer_accs_bank_code");
                    String BranchCode = m_rs.getString("buyer_accs_branch_code");
                    String AccNo = m_rs.getString("buyer_accs_acc_no");
                    buyerAccsDtls += recordNo + "##" + bankCode + "##" + BranchCode + "##" + AccNo + ",";
                }
                if (!buyerAccsDtls.equals("")) {
                    buyerAccsDtls = buyerAccsDtls.substring(0, buyerAccsDtls.length() - 1);
                }
            }

            m_jsObj.put("buyer_accs", buyerAccsDtls);

            String temporaryLimits = "";
            if (idndb_rec_fin != 0) {
                m_chstrsql = "SELECT * FROM ndb_rec_fin_temp_limits where idndb_rec_fin='" + idndb_rec_fin + "' order by idndb_rec_fin_temp_limits";
                m_rs = m_stamt.executeQuery(m_chstrsql);

                while (m_rs.next()) {
                    String recordNo = m_rs.getString("ndb_rec_fin_temp_limit_record_no");
                    String tempValue = m_rs.getString("ndb_rec_fin_temp_value");
                    String expDate = m_rs.getString("ndb_rec_fin_temp_exp_date");
                    temporaryLimits += recordNo + "##" + tempValue + "##" + expDate + ",";
                }
                if (!temporaryLimits.equals("")) {
                    temporaryLimits = temporaryLimits.substring(0, temporaryLimits.length() - 1);
                }
            }

            m_jsObj.put("rf_temporary_limits", temporaryLimits);

            m_chstrsql = "select * from ndb_customer_define where idndb_customer_define='" + prm_stridndb_customer_define + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj.put("rec_finance_acc_num", m_rs.getString("rec_finance_acc_num"));
                m_jsObj.put("rec_finance_cr_dsc_proc_acc_num", m_rs.getString("rec_finance_cr_dsc_proc_acc_num"));
                m_jsObj.put("rec_finance_current_ac", m_rs.getString("rec_finance_curr_ac"));
                m_jsObj.put("cms_curr_acc_number", m_rs.getString("cms_curr_acc_number"));
                m_jsObj.put("cms_collection_acc_number", m_rs.getString("cms_collection_acc_number"));
                m_jsObj.put("rec_finance_margin_ac", m_rs.getString("rec_finance_margin_ac"));
                m_jsObj.put("rec_finance_margin", m_rs.getString("rec_finance_margin"));
                m_jsObj.put("cust_credit_rate", m_rs.getString("cust_credit_rate"));

            }

            m_chstrsql = "select SUM(pdc_chq_amu) as sl_has_byr_otstaning  from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_cust_prod_map + "' and pdc_req_financing='CW' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth='AUTHORIZED'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String sl_has_byr_otstaning = "0.00";
            if (m_rs.next()) {

                if (!(m_rs.getString("sl_has_byr_otstaning") == null)) {
                    sl_has_byr_otstaning = m_rs.getString("sl_has_byr_otstaning");
                }
            }

            m_chstrsql = "select * from ndb_chq_wh where idndb_cust_prod_map='" + idndb_cust_prod_map + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            int idndb_chq_wh = 0;
            if (m_rs.next()) {
                idndb_chq_wh = m_rs.getInt("idndb_chq_wh");
                m_jsObj.put("idndb_chq_wh", m_rs.getString("idndb_chq_wh"));
                m_jsObj.put("chq_wh_limit", df.format(Double.parseDouble(m_rs.getString("chq_wh_limit"))));
                // m_jsObj.put("sl_has_byr_fmax_chq_amu", df.format(Double.parseDouble(m_rs.getString("sl_has_byr_fmax_chq_amu"))));
                m_jsObj.put("sl_has_byr_otstaning", df.format(Double.parseDouble(sl_has_byr_otstaning)));
                m_jsObj.put("sl_has_byr_tenor", m_rs.getString("sl_has_byr_tenor"));
                m_jsObj.put("chq_wh_dr_to_cr_spe_narr", m_rs.getString("chq_wh_dr_to_cr_spe_narr"));

                m_jsObj.put("chq_wh_erly_wdr_chg", df.format(Double.parseDouble(m_rs.getString("chq_wh_erly_wdr_chg"))));
                m_jsObj.put("chq_wh_vale_dte_extr_chg", df.format(Double.parseDouble(m_rs.getString("chq_wh_vale_dte_extr_chg"))));
                m_jsObj.put("chq_wh_erly_stlemnt_chg", df.format(Double.parseDouble(m_rs.getString("chq_wh_erly_stlemnt_chg"))));
                m_jsObj.put("chq_status", m_rs.getString("chq_status"));

            }

            String cwTemporaryLimits = "";
            if (idndb_chq_wh != 0) {
                m_chstrsql = "SELECT * FROM ndb_rec_chq_wh_limits where idndb_chq_wh='" + idndb_chq_wh + "' order by idndb_chq_wh_temp_limits";
                m_rs = m_stamt.executeQuery(m_chstrsql);

                while (m_rs.next()) {
                    String recordNo = m_rs.getString("ndb_chq_wh_temp_limit_record_no");
                    String tempValue = m_rs.getString("ndb_chq_wh_temp_value");
                    String expDate = m_rs.getString("ndb_chq_wh_temp_exp_date");
                    cwTemporaryLimits += recordNo + "##" + tempValue + "##" + expDate + ",";
                }
                if (!cwTemporaryLimits.equals("")) {
                    cwTemporaryLimits = cwTemporaryLimits.substring(0, cwTemporaryLimits.length() - 1);
                }
            }

            m_jsObj.put("cw_temporary_limits", cwTemporaryLimits);
            m_jsArr.put(i, m_jsObj);
            i++;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getRelMestbDataTofill(String prm_stridndb_seller_has_buyers) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        DecimalFormat df = new DecimalFormat("#,###.00");
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();
            double cw_total_buyer_chq = 0.00;
            double rf_total_buyer_chq = 0.00;

            m_chstrsql = "select SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + prm_stridndb_seller_has_buyers + "' and pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED') and pdc_req_financing='CW'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            if (m_rs.next()) {
                if (!(m_rs.getString("pdc_chq_amu") == null)) {
                    cw_total_buyer_chq = Double.parseDouble(m_rs.getString("pdc_chq_amu"));
                }

            }

            m_chstrsql = "select SUM(pdc_chq_discounting_amu) as pdc_chq_discounting_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + prm_stridndb_seller_has_buyers + "' and pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED') and pdc_req_financing='RF'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            if (m_rs.next()) {
                if (!(m_rs.getString("pdc_chq_discounting_amu") == null)) {
                    rf_total_buyer_chq = Double.parseDouble(m_rs.getString("pdc_chq_discounting_amu"));
                }
            }

            m_chstrsql = "SELECT\n"
                    + "     ndb_seller_has_buyers.`idndb_seller_has_buyers` AS ndb_seller_has_buyers_idndb_seller_has_buyers,\n"
                    + "     ndb_seller_has_buyers.`idndb_customer_define_seller` AS ndb_seller_has_buyers_idndb_customer_define_seller,\n"
                    + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                    + "     ndb_rec_fin.`idndb_rec_fin` AS ndb_rec_fin_idndb_rec_fin,\n"
                    //getting rec_finance_limit of the seller for CFU-BRD-4 - Janaka_5977
                    + "     ndb_rec_fin.`rec_finance_limit` AS rec_finance_limit,\n"
                    + "     ndb_rec_fin.`rec_finance_financing` AS ndb_rec_fin_rec_finance_financing,\n"
                    + "     ndb_rec_fin.`idndb_customer_define` AS ndb_rec_fin_idndb_customer_define,\n"
                    + "     ndb_rec_fin.`rec_finance_tenor` AS ndb_rec_fin_rec_finance_tenor,\n"
                    + "     ndb_rec_fin.`idndb_cust_prod_map` AS ndb_rec_fin_idndb_cust_prod_map\n"
                    + "FROM\n"
                    + "     `ndb_cust_prod_map` ndb_cust_prod_map INNER JOIN `ndb_seller_has_buyers` ndb_seller_has_buyers ON ndb_cust_prod_map.`idndb_cust_prod_map` = ndb_seller_has_buyers.`idndb_customer_define_seller`\n"
                    + "     INNER JOIN `ndb_rec_fin` ndb_rec_fin ON ndb_cust_prod_map.`idndb_cust_prod_map` = ndb_rec_fin.`idndb_cust_prod_map` where ndb_seller_has_buyers.`idndb_seller_has_buyers`='" + prm_stridndb_seller_has_buyers + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String seller_advnc_prtage = "";
            String seller_tenor = "";
            //getting rec_finance_limit of the seller for CFU-BRD-4 - Janaka_5977
            double seller_rec_finance_limit = 0;
            if (m_rs.next()) {
                seller_advnc_prtage = m_rs.getString("ndb_rec_fin_rec_finance_financing");
                seller_tenor = m_rs.getString("ndb_rec_fin_rec_finance_tenor");
                //getting rec_finance_limit of the seller for CFU-BRD-4 - Janaka_5977
                seller_rec_finance_limit = m_rs.getDouble("rec_finance_limit");

            }

            m_chstrsql = "SELECT\n"
                    + "     ndb_seller_has_buyers.`idndb_seller_has_buyers` AS ndb_seller_has_buyers_idndb_seller_has_buyers,\n"
                    + "     ndb_seller_has_buyers.`idndb_customer_define_seller` AS ndb_seller_has_buyers_idndb_customer_define_seller,\n"
                    + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                    + "     ndb_chq_wh.`idndb_chq_wh` AS ndb_chq_wh_idndb_chq_wh,\n"
                    + "     ndb_chq_wh.`idndb_customer_define` AS ndb_chq_wh_idndb_customer_define,\n"
                    + "     ndb_chq_wh.`idndb_cust_prod_map` AS ndb_chq_wh_idndb_cust_prod_map,\n"
                    //getting chq_wh_limit of the seller for CFU-BRD-4 - Janaka_5977
                    + "     ndb_chq_wh.`chq_wh_limit` AS chq_wh_limit,\n"
                    + "     ndb_chq_wh.`sl_has_byr_tenor` AS ndb_chq_wh_sl_has_byr_tenor\n"
                    + "FROM\n"
                    + "     `ndb_cust_prod_map` ndb_cust_prod_map INNER JOIN `ndb_seller_has_buyers` ndb_seller_has_buyers ON ndb_cust_prod_map.`idndb_cust_prod_map` = ndb_seller_has_buyers.`idndb_customer_define_seller`\n"
                    + "     INNER JOIN `ndb_chq_wh` ndb_chq_wh ON ndb_cust_prod_map.`idndb_cust_prod_map` = ndb_chq_wh.`idndb_cust_prod_map` where ndb_seller_has_buyers.`idndb_seller_has_buyers`='" + prm_stridndb_seller_has_buyers + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String cw_seller_tenor = "";
            //getting chq_wh_limit of the seller for CFU-BRD-4 - Janaka_5977
            double seller_chq_wh_limit = 0;
            if (m_rs.next()) {
                cw_seller_tenor = m_rs.getString("ndb_chq_wh_sl_has_byr_tenor");
                //getting chq_wh_limit of the seller for CFU-BRD-4 - Janaka_5977
                seller_chq_wh_limit = m_rs.getDouble("chq_wh_limit");

            }

            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + prm_stridndb_seller_has_buyers + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_seller_has_buyers", m_rs.getString("idndb_seller_has_buyers"));
                m_jsObj.put("idndb_customer_define_seller", m_rs.getString("idndb_customer_define_seller"));
                m_jsObj.put("idndb_customer_define_buyer", m_rs.getString("idndb_customer_define_buyer"));
                m_jsObj.put("sl_has_byr_prorm_type", m_rs.getString("sl_has_byr_prorm_type"));
                //  m_jsObj.put("sl_has_byr_facilty_ac_no", m_rs.getString("sl_has_byr_facilty_ac_no"));
                m_jsObj.put("sl_has_byr_max_chq_amu", df.format(Double.parseDouble(m_rs.getString("sl_has_byr_max_chq_amu"))));
                m_jsObj.put("shb_facty_det_crd_loam_limit", df.format(Double.parseDouble(m_rs.getString("shb_facty_det_crd_loam_limit"))));
                m_jsObj.put("shb_facty_det_os", df.format(rf_total_buyer_chq));
                m_jsObj.put("shb_facty_det_tenor", m_rs.getString("shb_facty_det_tenor"));

                //getting rec_finance_limit of the seller for CFU-BRD-4 - Janaka_5977
                double buyers_loan_limit = Double.parseDouble(m_rs.getString("shb_facty_det_crd_loam_limit"));
                double seller_rec_fin_limit = seller_rec_finance_limit;
                //double buyers_loan_percentage=(buyers_loan_limit/seller_rec_fin_limit)*100;
                m_jsObj.put("buyers_loan_percentage", df.format(buyers_loan_limit));
                m_jsObj.put("seller_rec_fin_limit", seller_rec_fin_limit);

                m_jsObj.put("seller_tenor", seller_tenor);
                m_jsObj.put("shb_facty_det_irest_trry", m_rs.getString("shb_facty_det_irest_trry"));
                m_jsObj.put("shb_chq_dis_adv_rate_prectange", m_rs.getString("shb_chq_dis_adv_rate_prectange"));
                m_jsObj.put("seller_advnc_prtage", seller_advnc_prtage);
                //  m_jsObj.put("shb_facty_det_main_cmiss_limit", m_rs.getString("shb_facty_det_main_cmiss_limit"));
                //  m_jsObj.put("shb_chq_dis_ricing_intrest", m_rs.getString("shb_chq_dis_ricing_intrest"));
                m_jsObj.put("rec_finance_commision_crg", m_rs.getString("rec_finance_commision_crg"));
                m_jsObj.put("rf_tran_base_falt_fee", df.format(Double.parseDouble(m_rs.getString("rf_tran_base_falt_fee"))));
                m_jsObj.put("rf_tran_base_from_tran", m_rs.getString("rf_tran_base_from_tran"));
                m_jsObj.put("rf_fixed_rate_amount", df.format(Double.parseDouble(m_rs.getString("rf_fixed_rate_amount"))));
                m_jsObj.put("rf_fixed_frequency", m_rs.getString("rf_fixed_frequency"));

                m_jsObj.put("chq_wh_commision_crg", m_rs.getString("chq_wh_commision_crg"));
                m_jsObj.put("cw_tran_base_falt_fee", df.format(Double.parseDouble(m_rs.getString("cw_tran_base_falt_fee"))));
                m_jsObj.put("cw_tran_base_from_tran", m_rs.getString("cw_tran_base_from_tran"));
                m_jsObj.put("cw_fixed_rate_amount", df.format(Double.parseDouble(m_rs.getString("cw_fixed_rate_amount"))));
                m_jsObj.put("cw_fixed_frequency", m_rs.getString("cw_fixed_frequency"));

                m_jsObj.put("sl_has_byr_remarks", m_rs.getString("sl_has_byr_remarks"));
                m_jsObj.put("sl_has_byr_status", m_rs.getString("sl_has_byr_status"));
                m_jsObj.put("statusReason", m_rs.getString("sl_has_byr_status_reason"));

                //cw facility details
                m_jsObj.put("sl_has_byr_warehs_limit", df.format(Double.parseDouble(m_rs.getString("sl_has_byr_warehs_limit"))));
                m_jsObj.put("sl_has_byr_warehs_otstaning", df.format(cw_total_buyer_chq));
                m_jsObj.put("sl_has_byr_warehs_tenor", m_rs.getString("sl_has_byr_warehs_tenor"));
                m_jsObj.put("cw_seller_tenor", cw_seller_tenor);

                //getting chq_wh_limit of the seller for CFU-BRD-4 - Janaka_5977
                double buyers_cw_limit = Double.parseDouble(m_rs.getString("shb_facty_det_crd_loam_limit"));
                double seller_chq_wearhouse_limit = seller_chq_wh_limit;
                //double buyers_cw_loan_percentage=(buyers_cw_limit/seller_chq_wearhouse_limit)*100;
                m_jsObj.put("buyers_cw_loan_percentage", df.format(buyers_cw_limit));
                m_jsObj.put("seller_chq_wh_limit", seller_chq_wearhouse_limit);

                m_jsObj.put("sl_has_byr_warehs_fmax_chq_amu", df.format(Double.parseDouble(m_rs.getString("sl_has_byr_warehs_fmax_chq_amu"))));

                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getRelMestbDataTofillSelected(String idndb_customer_define_seller_id, String idndb_customer_define_buyer_id) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        Statement m_stamt2 = null;
        ResultSet m_rs = null;
        ResultSet m_rs2 = null;
        String m_chstrsql = "";
        String m_chstrsql2 = "";
        JSONObject m_jsObj;
        DecimalFormat df = new DecimalFormat("#,###.00");
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();
            m_stamt2 = _currentCon.createStatement();

            double cw_total_buyer_chq = 0.00;
            double rf_total_buyer_chq = 0.00;

            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_customer_define_seller='" + idndb_customer_define_seller_id + "' and idndb_customer_define_buyer='" + idndb_customer_define_buyer_id + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("data_availablity", "ACTIVE");
                m_jsObj.put("idndb_seller_has_buyers", m_rs.getString("idndb_seller_has_buyers"));

                String id_seller_buyer_link = m_rs.getString("idndb_seller_has_buyers");
                m_chstrsql2 = "select SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + id_seller_buyer_link + "' and pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED') and pdc_req_financing='CW'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (m_rs2.next()) {
                    if (!(m_rs2.getString("pdc_chq_amu") == null)) {
                        cw_total_buyer_chq = Double.parseDouble(m_rs2.getString("pdc_chq_amu"));
                    }

                }

                m_chstrsql2 = "select SUM(pdc_chq_discounting_amu) as pdc_chq_discounting_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + id_seller_buyer_link + "' and pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED') and pdc_req_financing='RF'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (m_rs2.next()) {
                    if (!(m_rs2.getString("pdc_chq_discounting_amu") == null)) {
                        rf_total_buyer_chq = Double.parseDouble(m_rs2.getString("pdc_chq_discounting_amu"));
                    }
                }

                m_chstrsql = "SELECT\n"
                        + "     ndb_seller_has_buyers.`idndb_seller_has_buyers` AS ndb_seller_has_buyers_idndb_seller_has_buyers,\n"
                        + "     ndb_seller_has_buyers.`idndb_customer_define_seller` AS ndb_seller_has_buyers_idndb_customer_define_seller,\n"
                        + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                        + "     ndb_rec_fin.`idndb_rec_fin` AS ndb_rec_fin_idndb_rec_fin,\n"
                        //getting rec_finance_limit of the seller for CFU-BRD-4 - Janaka_5977
                        + "     ndb_rec_fin.`rec_finance_limit` AS rec_finance_limit,\n"
                        + "     ndb_rec_fin.`rec_finance_financing` AS ndb_rec_fin_rec_finance_financing,\n"
                        + "     ndb_rec_fin.`idndb_customer_define` AS ndb_rec_fin_idndb_customer_define,\n"
                        + "     ndb_rec_fin.`rec_finance_tenor` AS ndb_rec_fin_rec_finance_tenor,\n"
                        + "     ndb_rec_fin.`idndb_cust_prod_map` AS ndb_rec_fin_idndb_cust_prod_map\n"
                        + "FROM\n"
                        + "     `ndb_cust_prod_map` ndb_cust_prod_map INNER JOIN `ndb_seller_has_buyers` ndb_seller_has_buyers ON ndb_cust_prod_map.`idndb_cust_prod_map` = ndb_seller_has_buyers.`idndb_customer_define_seller`\n"
                        + "     INNER JOIN `ndb_rec_fin` ndb_rec_fin ON ndb_cust_prod_map.`idndb_cust_prod_map` = ndb_rec_fin.`idndb_cust_prod_map` where ndb_seller_has_buyers.`idndb_seller_has_buyers`='" + id_seller_buyer_link + "'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql);
                String seller_advnc_prtage = "";
                String seller_tenor = "";
                //getting rec_finance_limit of the seller for CFU-BRD-4 - Janaka_5977
                double seller_rec_finance_limit = 0;
                if (m_rs2.next()) {
                    seller_advnc_prtage = m_rs2.getString("ndb_rec_fin_rec_finance_financing");
                    seller_tenor = m_rs2.getString("ndb_rec_fin_rec_finance_tenor");
                    //getting rec_finance_limit of the seller for CFU-BRD-4 - Janaka_5977
                    seller_rec_finance_limit = m_rs2.getDouble("rec_finance_limit");

                }

                m_chstrsql = "SELECT\n"
                        + "     ndb_seller_has_buyers.`idndb_seller_has_buyers` AS ndb_seller_has_buyers_idndb_seller_has_buyers,\n"
                        + "     ndb_seller_has_buyers.`idndb_customer_define_seller` AS ndb_seller_has_buyers_idndb_customer_define_seller,\n"
                        + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                        + "     ndb_chq_wh.`idndb_chq_wh` AS ndb_chq_wh_idndb_chq_wh,\n"
                        //getting chq_wh_limit of the seller for CFU-BRD-4 - Janaka_5977
                        + "     ndb_chq_wh.`chq_wh_limit` AS chq_wh_limit,\n"
                        + "     ndb_chq_wh.`idndb_customer_define` AS ndb_chq_wh_idndb_customer_define,\n"
                        + "     ndb_chq_wh.`idndb_cust_prod_map` AS ndb_chq_wh_idndb_cust_prod_map,\n"
                        + "     ndb_chq_wh.`sl_has_byr_tenor` AS ndb_chq_wh_sl_has_byr_tenor\n"
                        + "FROM\n"
                        + "     `ndb_cust_prod_map` ndb_cust_prod_map INNER JOIN `ndb_seller_has_buyers` ndb_seller_has_buyers ON ndb_cust_prod_map.`idndb_cust_prod_map` = ndb_seller_has_buyers.`idndb_customer_define_seller`\n"
                        + "     INNER JOIN `ndb_chq_wh` ndb_chq_wh ON ndb_cust_prod_map.`idndb_cust_prod_map` = ndb_chq_wh.`idndb_cust_prod_map` where ndb_seller_has_buyers.`idndb_seller_has_buyers`='" + id_seller_buyer_link + "'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql);
                String cw_seller_tenor = "";
                double seller_chq_wh_limit = 0;
                if (m_rs2.next()) {
                    cw_seller_tenor = m_rs2.getString("ndb_chq_wh_sl_has_byr_tenor");
                    seller_chq_wh_limit = m_rs2.getDouble("chq_wh_limit");

                }
                //getting rec_finance_limit of the seller for CFU-BRD-4 - Janaka_5977
                double buyers_loan_limit = Double.parseDouble(m_rs.getString("shb_facty_det_crd_loam_limit"));
                double seller_rec_fin_limit = seller_rec_finance_limit;
                // double buyers_loan_percentage=(buyers_loan_limit/seller_rec_fin_limit)*100;
                m_jsObj.put("buyers_loan_percentage", df.format(buyers_loan_limit));
                m_jsObj.put("seller_rec_fin_limit", df.format(seller_rec_fin_limit));

                m_jsObj.put("seller_tenor", seller_tenor);
                m_jsObj.put("seller_advnc_prtage", seller_advnc_prtage);
                m_jsObj.put("cw_seller_tenor", cw_seller_tenor);
                m_jsObj.put("idndb_customer_define_seller", m_rs.getString("idndb_customer_define_seller"));
                m_jsObj.put("idndb_customer_define_buyer", m_rs.getString("idndb_customer_define_buyer"));
                m_jsObj.put("sl_has_byr_prorm_type", m_rs.getString("sl_has_byr_prorm_type"));
                //  m_jsObj.put("sl_has_byr_facilty_ac_no", m_rs.getString("sl_has_byr_facilty_ac_no"));
                m_jsObj.put("sl_has_byr_max_chq_amu", df.format(Double.parseDouble(m_rs.getString("sl_has_byr_max_chq_amu"))));
                m_jsObj.put("shb_facty_det_crd_loam_limit", df.format(Double.parseDouble(m_rs.getString("shb_facty_det_crd_loam_limit"))));
                m_jsObj.put("shb_facty_det_os", df.format(rf_total_buyer_chq));
                m_jsObj.put("shb_facty_det_tenor", m_rs.getString("shb_facty_det_tenor"));
                m_jsObj.put("shb_facty_det_irest_trry", m_rs.getString("shb_facty_det_irest_trry"));
                m_jsObj.put("shb_chq_dis_adv_rate_prectange", m_rs.getString("shb_chq_dis_adv_rate_prectange"));
                //  m_jsObj.put("shb_facty_det_main_cmiss_limit", m_rs.getString("shb_facty_det_main_cmiss_limit"));
                //  m_jsObj.put("shb_chq_dis_ricing_intrest", m_rs.getString("shb_chq_dis_ricing_intrest"));
                m_jsObj.put("rec_finance_commision_crg", m_rs.getString("rec_finance_commision_crg"));
                m_jsObj.put("rf_tran_base_falt_fee", df.format(Double.parseDouble(m_rs.getString("rf_tran_base_falt_fee"))));
                m_jsObj.put("rf_tran_base_from_tran", m_rs.getString("rf_tran_base_from_tran"));
                m_jsObj.put("rf_fixed_rate_amount", df.format(Double.parseDouble(m_rs.getString("rf_fixed_rate_amount"))));
                m_jsObj.put("rf_fixed_frequency", m_rs.getString("rf_fixed_frequency"));

                m_jsObj.put("chq_wh_commision_crg", m_rs.getString("chq_wh_commision_crg"));
                m_jsObj.put("cw_tran_base_falt_fee", df.format(Double.parseDouble(m_rs.getString("cw_tran_base_falt_fee"))));
                m_jsObj.put("cw_tran_base_from_tran", m_rs.getString("cw_tran_base_from_tran"));
                m_jsObj.put("cw_fixed_rate_amount", df.format(Double.parseDouble(m_rs.getString("cw_fixed_rate_amount"))));
                m_jsObj.put("cw_fixed_frequency", m_rs.getString("cw_fixed_frequency"));

                m_jsObj.put("sl_has_byr_remarks", m_rs.getString("sl_has_byr_remarks"));
                m_jsObj.put("sl_has_byr_status", m_rs.getString("sl_has_byr_status"));
                m_jsObj.put("statusReason", m_rs.getString("sl_has_byr_status_reason"));

                //cw facility details
                m_jsObj.put("sl_has_byr_warehs_limit", df.format(Double.parseDouble(m_rs.getString("sl_has_byr_warehs_limit"))));
                m_jsObj.put("sl_has_byr_warehs_otstaning", df.format(cw_total_buyer_chq));
                m_jsObj.put("sl_has_byr_warehs_tenor", m_rs.getString("sl_has_byr_warehs_tenor"));
                m_jsObj.put("sl_has_byr_warehs_fmax_chq_amu", df.format(Double.parseDouble(m_rs.getString("sl_has_byr_warehs_fmax_chq_amu"))));

                //getting chq_wh_limit of the seller for CFU-BRD-4 - Janaka_5977
                double buyers_cw_limit = Double.parseDouble(m_rs.getString("shb_facty_det_crd_loam_limit"));
                double seller_chq_wearhouse_limit = seller_chq_wh_limit;
                //double buyers_cw_loan_percentage=(buyers_cw_limit/seller_chq_wearhouse_limit)*100;
                m_jsObj.put("buyers_cw_loan_percentage", df.format(buyers_cw_limit));
                m_jsObj.put("seller_chq_wh_limit", df.format(seller_chq_wearhouse_limit));

                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getAvailbleSubLimitLimit(String prm_stridndb_customer_define_seller, String prm_str_sl_has_byr_cdt_loan_amu, String idndb_seller_has_buyers) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            DecimalFormat df = new DecimalFormat("#,###.00");
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            double sl_has_byr_cdt_loan_amu = 0.00;
            if (m_rs.next()) {
                sl_has_byr_cdt_loan_amu = m_rs.getDouble("shb_facty_det_crd_loam_limit");
            }

            m_chstrsql = "select * from ndb_rec_fin where idndb_cust_prod_map='" + prm_stridndb_customer_define_seller + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            double rec_finance_limit = 0.00;
            if (m_rs.next()) {
                rec_finance_limit = m_rs.getDouble("rec_finance_limit");
            }

            rec_finance_limit += getTotRFTemporaryLimits(prm_stridndb_customer_define_seller);

            m_chstrsql = "select SUM(shb_facty_det_crd_loam_limit) As SUMOFSUBLIMITS from ndb_seller_has_buyers where idndb_customer_define_seller='" + prm_stridndb_customer_define_seller + "' and sl_has_byr_prorm_type='RF' and sl_has_byr_status='ACTIVE'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {
                m_jsObj = new JSONObject();
                double available_sub_limt = 0.00;
                if (!(m_rs.getString("SUMOFSUBLIMITS") == null)) {

                    available_sub_limt = rec_finance_limit - (Double.parseDouble(m_rs.getString("SUMOFSUBLIMITS")) - sl_has_byr_cdt_loan_amu);

                } else {
                    available_sub_limt = rec_finance_limit;

                }

                m_jsObj.put("available_sub_limt", available_sub_limt);
                m_jsObj.put("available_sub_limt_formatted", df.format(available_sub_limt));
                //added by Janaka_5977 - CFU-BRD-4 To check the buyers limit based on the percentage
                m_jsObj.put("rec_finance_limit", rec_finance_limit);

                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getAvailbleCWSubLimitLimit(String prm_stridndb_customer_define_seller, String idndb_seller_has_buyers) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String sl_has_byr_warehs_limit = "0.00";
            if (m_rs.next()) {
                sl_has_byr_warehs_limit = m_rs.getString("sl_has_byr_warehs_limit");
            }

            m_chstrsql = "select * from ndb_chq_wh where idndb_cust_prod_map='" + prm_stridndb_customer_define_seller + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            String chq_wh_limit = "0.00";
            if (m_rs.next()) {
                chq_wh_limit = m_rs.getString("chq_wh_limit");

            }
            chq_wh_limit += getTotCWTemporaryLimits(prm_stridndb_customer_define_seller);

            m_chstrsql = "select SUM(sl_has_byr_warehs_limit) As SUMOFSUBLIMITS from ndb_seller_has_buyers where idndb_customer_define_seller='" + prm_stridndb_customer_define_seller + "' and sl_has_byr_prorm_type='CW' and sl_has_byr_status='ACTIVE'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {
                String SUMOFSUBLIMITS = "0.00";
                if (!(m_rs.getString("SUMOFSUBLIMITS") == null)) {
                    SUMOFSUBLIMITS = m_rs.getString("SUMOFSUBLIMITS");
                }

                m_jsObj = new JSONObject();

                double available_sub_limt = Double.parseDouble(chq_wh_limit) - (Double.parseDouble(SUMOFSUBLIMITS) - Double.parseDouble(sl_has_byr_warehs_limit));
                m_jsObj.put("available_sub_limt", available_sub_limt);
                m_jsObj.put("available_sub_limt_formtted", df.format(available_sub_limt));

                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getBranchDataTofill(String prm_stridndb_bank_master_file) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_branch_master_file where idndb_branch_master_file='" + prm_stridndb_bank_master_file + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_branch_master_file", m_rs.getString("idndb_branch_master_file"));
                m_jsObj.put("idndb_bank_master_file", m_rs.getString("idndb_bank_master_file"));
                m_jsObj.put("branch_id", m_rs.getString("branch_id"));
                m_jsObj.put("branch_name", m_rs.getString("branch_name"));
                m_jsObj.put("branch_status", m_rs.getString("branch_status"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getBankDifineBranchData(String prm_stridndb_bank_master_file) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_branch_master_file where idndb_bank_master_file='" + prm_stridndb_bank_master_file + "' and branch_status ='ACTIVE' and branch_approval='AUTHORIZED' ORDER BY branch_id ASC";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            while (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_branch_master_file", m_rs.getString("idndb_branch_master_file"));
                m_jsObj.put("branch_name", m_rs.getString("branch_name"));
                m_jsObj.put("branch_id", m_rs.getString("branch_id"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getSellerDifineBuyerData(String prm_stridndb_cust_prod_map) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_strQry = "SELECT \n"
                    + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                    + "nshb_seller_has_buyers.sl_has_byr_warehs_tenor,\n"
                    + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                    + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                    + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                    + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                    + "ngmmf_geo_market.geo_market_desc,\n"
                    + "nbmf_branch_master.branch_name\n"
                    + "FROM \n"
                    + "ndb_seller_has_buyers nshb_seller_has_buyers inner join \n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_seller on\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_seller = ncpm_cust_prod_map_seller.idndb_cust_prod_map inner join\n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
                    + "ndb_customer_define ncd_cust_define_seller on\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define =ncd_cust_define_seller.idndb_customer_define inner join\n"
                    + "ndb_customer_define ncd_cust_define_buyer on\n"
                    + "ncpm_cust_prod_map_buyer.idndb_customer_define =ncd_cust_define_buyer.idndb_customer_define left join\n"
                    + "ndb_geo_market_master_file ngmmf_geo_market on\n"
                    + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market.idndb_geo_market_master_file left join\n"
                    + "ndb_branch_master_file nbmf_branch_master on\n"
                    + "ncd_cust_define_seller.idndb_branch_master_file = nbmf_branch_master.idndb_branch_master_file\n"
                    + "where\n"
                    + "nshb_seller_has_buyers.sl_has_byr_auth = 'AUTHORIZED' and\n"
                    + "nshb_seller_has_buyers.sl_has_byr_status='ACTIVE' and \n"
                    + "nshb_seller_has_buyers.sl_has_byr_auth='AUTHORIZED' and \n"
                    + "nshb_seller_has_buyers.sl_has_byr_prorm_type='CW' and \n"
                    + "ncpm_cust_prod_map_buyer.prod_relationship_status='ACTIVE' and \n"
                    + "ncpm_cust_prod_map_buyer.prod_relationship_auth='AUTHORIZED' and \n"
                    + "ncd_cust_define_buyer.cust_status='ACTIVE' and \n"
                    + "ncd_cust_define_buyer.cust_auth='AUTHORIZED' and \n"
                    + "nshb_seller_has_buyers.idndb_customer_define_seller='" + prm_stridndb_cust_prod_map + "'";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {
                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_seller_has_buyers", _rs.getString("idndb_seller_has_buyers"));
                m_jsObj.put("sl_has_byr_warehs_tenor", _rs.getString("sl_has_byr_warehs_tenor"));
                m_jsObj.put("cust_id", _rs.getString("buyer_cust_id"));
                m_jsObj.put("cust_name", _rs.getString("buyer_cust_name"));
                m_jsObj.put("geo_market_desc", _rs.getString("geo_market_desc"));
                m_jsObj.put("branch_name", _rs.getString("branch_name"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
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
        return m_jsArr;
    }

    public JSONArray getSellersData(String prm_programe_type) {
        JSONArray m_jsArr = new JSONArray();
        JSONObject m_jsObj;
        String m_strQry = "";
        String m_strQry2 = "";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {

            int i = 0;

            String m_sel_param_sql = "";

            if (prm_programe_type.equals("RF")) {
                m_sel_param_sql = "prod_relationship_res_fin";
            }
            if (prm_programe_type.equals("CW")) {
                m_sel_param_sql = "prod_relationship_chq_ware";
            }

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
                    + "ncpm_cust_prod_map_seller." + m_sel_param_sql + "='ACTIVE' and \n"
                    + "ncd_cust_define_seller.cust_status='ACTIVE' and \n"
                    + "ncd_cust_define_seller.cust_auth='AUTHORIZED'";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {
                boolean buyer_relation = true;
                boolean buyer_cust_define = true;
                boolean cust_prod_map = true;

                String idndb_cust_prod_map = _rs.getString("idndb_cust_prod_map");

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

                    m_jsObj = new JSONObject();

                    m_jsObj.put("idndb_cust_prod_map", idndb_cust_prod_map);
                    m_jsObj.put("cust_id", cust_id);
                    m_jsObj.put("cust_name", cust_name);
                    m_jsArr.put(i, m_jsObj);
                    i++;

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
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
        return m_jsArr;
    }

    public JSONArray getSellerDifineBuyerDataRMS(String prm_stridndb_cust_prod_map) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_strQry = "SELECT \n"
                    + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                    + "nshb_seller_has_buyers.shb_facty_det_tenor,\n"
                    + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                    + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                    + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                    + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                    + "ngmmf_geo_market.geo_market_desc,\n"
                    + "nbmf_branch_master.branch_name\n"
                    + "FROM \n"
                    + "ndb_seller_has_buyers nshb_seller_has_buyers inner join \n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_seller on\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_seller = ncpm_cust_prod_map_seller.idndb_cust_prod_map inner join\n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
                    + "ndb_customer_define ncd_cust_define_seller on\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define =ncd_cust_define_seller.idndb_customer_define inner join\n"
                    + "ndb_customer_define ncd_cust_define_buyer on\n"
                    + "ncpm_cust_prod_map_buyer.idndb_customer_define =ncd_cust_define_buyer.idndb_customer_define left join\n"
                    + "ndb_geo_market_master_file ngmmf_geo_market on\n"
                    + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market.idndb_geo_market_master_file left join\n"
                    + "ndb_branch_master_file nbmf_branch_master on\n"
                    + "ncd_cust_define_seller.idndb_branch_master_file = nbmf_branch_master.idndb_branch_master_file\n"
                    + "where\n"
                    + "nshb_seller_has_buyers.sl_has_byr_auth = 'AUTHORIZED' and\n"
                    + "nshb_seller_has_buyers.sl_has_byr_status='ACTIVE' and \n"
                    + "nshb_seller_has_buyers.sl_has_byr_auth='AUTHORIZED' and \n"
                    + "nshb_seller_has_buyers.sl_has_byr_prorm_type='RF' and \n"
                    + "ncpm_cust_prod_map_buyer.prod_relationship_status='ACTIVE' and \n"
                    + "ncpm_cust_prod_map_buyer.prod_relationship_auth='AUTHORIZED' and \n"
                    + "ncd_cust_define_buyer.cust_status='ACTIVE' and \n"
                    + "ncd_cust_define_buyer.cust_auth='AUTHORIZED' and \n"
                    + "nshb_seller_has_buyers.idndb_customer_define_seller='" + prm_stridndb_cust_prod_map + "'";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {
                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_seller_has_buyers", _rs.getString("idndb_seller_has_buyers"));
                m_jsObj.put("shb_facty_det_tenor", _rs.getString("shb_facty_det_tenor"));
                m_jsObj.put("cust_id", _rs.getString("buyer_cust_id"));
                m_jsObj.put("cust_name", _rs.getString("buyer_cust_name"));
                m_jsObj.put("geo_market_desc", _rs.getString("geo_market_desc"));
                m_jsObj.put("branch_name", _rs.getString("branch_name"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
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
        return m_jsArr;
    }

    public JSONArray getSellerDifineBuyerDataRMSAll(String prm_stridndb_cust_prod_map) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "SELECT \n"
                    + "nshb_seller_has_buyre.idndb_seller_has_buyers,\n"
                    + "nshb_seller_has_buyre.shb_facty_det_tenor,\n"
                    + "ncd_cust_defaine.cust_id as buyer_cust_id,\n"
                    + "ncd_cust_defaine.cust_name as buyer_cust_name\n"
                    + "FROM ndb_rms_db.ndb_seller_has_buyers nshb_seller_has_buyre inner join\n"
                    + "ndb_customer_define ncd_cust_defaine\n"
                    + "on\n"
                    + "nshb_seller_has_buyre.idndb_customer_define_buyer = ncd_cust_defaine.idndb_customer_define\n"
                    + "where nshb_seller_has_buyre.idndb_customer_define_seller='" + prm_stridndb_cust_prod_map + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            while (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_seller_has_buyers", m_rs.getString("idndb_seller_has_buyers"));
                m_jsObj.put("shb_facty_det_tenor", m_rs.getString("shb_facty_det_tenor"));

                m_jsObj.put("cust_id", m_rs.getString("buyer_cust_id"));
                m_jsObj.put("cust_name", m_rs.getString("buyer_cust_name"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }

            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getRMSSeller() {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        Statement m_stamt2 = null;
        ResultSet m_rs = null;
        ResultSet m_rs2 = null;
        String m_chstrsql = "";
        String m_chstrsql2 = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();
            m_stamt2 = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_cust_prod_map where prod_relationship_status ='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_res_fin='ACTIVE'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            while (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_cust_prod_map", m_rs.getString("idndb_cust_prod_map"));

                String m_cust_id = "";
                String m_cust_name = "";
                m_chstrsql2 = "select cust_id,cust_name from ndb_customer_define where idndb_customer_define='" + m_rs.getString("idndb_customer_define") + "'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (m_rs2.next()) {
                    m_cust_id = m_rs2.getString("cust_id");
                    m_cust_name = m_rs2.getString("cust_name");

                }

                m_jsObj.put("cust_id", m_cust_id);
                m_jsObj.put("cust_name", m_cust_name);
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_rs2 != null) {
                    m_rs2.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
                if (m_stamt2 != null) {
                    m_stamt2.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getSellerProdData(String prm_stridndb_cust_prod_map) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        Statement m_stamt2 = null;
        ResultSet m_rs = null;
        ResultSet m_rs2 = null;
        String m_chstrsql = "";
        String m_chstrsql2 = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();
            m_stamt2 = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + prm_stridndb_cust_prod_map + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String prod_relationship_res_fin = "";
            String prod_relationship_chq_ware = "";
            m_jsObj = new JSONObject();
            if (m_rs.next()) {
                prod_relationship_res_fin = m_rs.getString("prod_relationship_res_fin");
                prod_relationship_chq_ware = m_rs.getString("prod_relationship_chq_ware");

                m_jsObj.put("prod_relationship_res_fin", m_rs.getString("prod_relationship_res_fin"));
                m_jsObj.put("prod_relationship_chq_ware", m_rs.getString("prod_relationship_chq_ware"));

            }

            if (prod_relationship_res_fin.equals("ACTIVE")) {
                m_chstrsql = "select * from ndb_rec_fin where idndb_cust_prod_map='" + prm_stridndb_cust_prod_map + "'";
                m_rs = m_stamt.executeQuery(m_chstrsql);
                String rec_finance_financing = "";
                String rec_finance_tenor = "";
                String seller_rec_fin_limit = "";
                if (m_rs.next()) {

                    rec_finance_financing = m_rs.getString("rec_finance_financing");
                    rec_finance_tenor = m_rs.getString("rec_finance_tenor");
                    seller_rec_fin_limit = m_rs.getString("rec_finance_limit");
                    m_jsObj.put("seller_rec_fin_limit", seller_rec_fin_limit);

                    m_jsObj.put("rec_finance_financing", rec_finance_financing);

                    m_jsObj.put("rec_finance_tenor", rec_finance_tenor);

                }

            }

            if (prod_relationship_chq_ware.equals("ACTIVE")) {
                String sl_has_byr_fmax_chq_amu = "";
                String sl_has_byr_tenor = "";
                String seller_chq_wh_limit = "";
                m_chstrsql = "select * from ndb_chq_wh where idndb_cust_prod_map='" + prm_stridndb_cust_prod_map + "'";
                m_rs = m_stamt.executeQuery(m_chstrsql);
                if (m_rs.next()) {

                    sl_has_byr_fmax_chq_amu = m_rs.getString("sl_has_byr_fmax_chq_amu");
                    sl_has_byr_tenor = m_rs.getString("sl_has_byr_tenor");
                    m_jsObj.put("sl_has_byr_fmax_chq_amu", sl_has_byr_fmax_chq_amu);
                    seller_chq_wh_limit = m_rs.getString("chq_wh_limit");
                    m_jsObj.put("seller_chq_wh_limit", seller_chq_wh_limit);

                    m_jsObj.put("sl_has_byr_tenor", sl_has_byr_tenor);

                }

            }

            m_jsArr.put(i, m_jsObj);
            i++;

        } catch (Exception e) {
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_rs2 != null) {
                    m_rs2.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
                }
                if (m_stamt2 != null) {
                    m_stamt2.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getUnAuthTxnsSellerDetails(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            String pdc_product = "";
            String pdc_manual_bulk = "";

            pdc_product = prm_obj.getString("pdc_product");
            pdc_manual_bulk = prm_obj.getString("pdc_manual_bulk");

            if (pdc_manual_bulk.equals("MANUAL")) {
                pdc_manual_bulk = " and nptm_txn_master.pdc_chq_batch_no ='*' or (nptm_txn_master.pdc_chq_batch_no not in ('*') and nptm_txn_master.pdc_latest_modification not in ('NEW') ) ";
            }
            if (pdc_manual_bulk.equals("BULK")) {
                pdc_manual_bulk = "and nptm_txn_master.pdc_chq_batch_no not in ('*') and nptm_txn_master.pdc_latest_modification ='NEW' ";
            }

            m_strQry = "SELECT \n"
                    + "nptm_txn_master.idndb_customer_define_seller_id,\n"
                    + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                    + "ncd_cust_define_seller.cust_name as seller_cust_name\n"
                    + "FROM \n"
                    + "ndb_pdc_txn_master nptm_txn_master inner join\n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_seller on\n"
                    + "nptm_txn_master.idndb_customer_define_seller_id = ncpm_cust_prod_map_seller.idndb_cust_prod_map inner join\n"
                    + "ndb_customer_define ncd_cust_define_seller on\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define =ncd_cust_define_seller.idndb_customer_define\n"
                    + "where\n"
                    + "nptm_txn_master.pdc_req_financing=? and \n"
                    + "nptm_txn_master.pdc_chq_status_auth = 'UN-AUTHORIZED' " + pdc_manual_bulk + " group by nptm_txn_master.idndb_customer_define_seller_id";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);

            _prepStmnt.setString(1, pdc_product);
            _rs = _prepStmnt.executeQuery();
            log.info(_prepStmnt);
            while (_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_customer_define_seller_id", _rs.getString("idndb_customer_define_seller_id"));
                m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error occured in generate report, Exception" + e);
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
        return m_jsArr;
    }

    public JSONArray getBuyerRelData(String prm_stridndb_seller_has_buyers, String cw_cheque_amu, String cust_bank_code, String cust_branch_code, String idndb_pdc_txn_master) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        Statement m_stamt2 = null;
        Statement m_stamt3 = null;
        Statement m_stamt4 = null;
        ResultSet m_rs = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        ResultSet m_rs4 = null;
        String m_chstrsql = "";
        String m_chstrsql2 = "";
        String m_chstrsql3 = "";
        String m_chstrsql4 = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();
            m_stamt2 = _currentCon.createStatement();
            m_stamt3 = _currentCon.createStatement();
            m_stamt4 = _currentCon.createStatement();
            DecimalFormat df = new DecimalFormat("#,###.00");
//            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + prm_stridndb_seller_has_buyers + "' and sl_has_byr_status ='ACTIVE' and sl_has_byr_auth='AUTHORIZED'";
//            CFU-CR-03, CFU-CR-04,CFU-BRD-08 & BUB-BRD-6 (LOLC PDC UPLOAD)           
            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + prm_stridndb_seller_has_buyers + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            m_jsObj = new JSONObject();
            if (m_rs.next()) {

                double sl_has_byr_warehs_limit_percentage = m_rs.getDouble("sl_has_byr_warehs_limit");
                String idndb_customer_define_seller = m_rs.getString("idndb_customer_define_seller");
                String idndb_cust_prod_map_buyer = m_rs.getString("idndb_customer_define_buyer");

                m_jsObj.put("idndb_seller_has_buyers", m_rs.getString("idndb_seller_has_buyers"));
                // m_jsObj.put("sl_has_byr_warehs_limit", m_rs.getString("sl_has_byr_warehs_limit"));
                m_jsObj.put("sl_has_byr_warehs_tenor", m_rs.getString("sl_has_byr_warehs_tenor"));
                m_jsObj.put("sl_has_byr_warehs_otstaning", m_rs.getString("sl_has_byr_warehs_otstaning"));
                m_jsObj.put("sl_has_byr_warehs_fmax_chq_amu", m_rs.getString("sl_has_byr_warehs_fmax_chq_amu"));
                m_jsObj.put("sl_has_byr_warehs_fmax_chq_amu_banner", df.format(Double.parseDouble(m_rs.getString("sl_has_byr_warehs_fmax_chq_amu"))));

                m_chstrsql2 = "select SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + prm_stridndb_seller_has_buyers + "' and idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED')";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                double pdc_chq_amu = 0.00;
                double available_amu = 0.00;
                double cw_cheque_amu_ctd = 0.00;
                double cw_old_cheque_amu_ctd = 0.00;

                if (!((idndb_pdc_txn_master == null) || idndb_pdc_txn_master.equals(""))) {
                    m_chstrsql3 = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master='" + idndb_pdc_txn_master + "'";
                    m_rs3 = m_stamt3.executeQuery(m_chstrsql2);
                    if (m_rs3.next()) {
                        cw_old_cheque_amu_ctd = m_rs3.getDouble("pdc_chq_amu");

                    }
                }

                m_chstrsql4 = "select * from ndb_chq_wh where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
                m_rs4 = m_stamt4.executeQuery(m_chstrsql4);
                double cw_seller_limit = 0.00;
                double cw_seller_all_txn_amu = 0.00;
                double cw_seller_limit_exeeded_total = 0.00;
                String chq_wh_dr_to_cr_spe_narr = "";
                String seller_message = "";
                if (!(m_rs4 == null)) {
                    if (m_rs4.next()) {
                        cw_seller_limit = m_rs4.getDouble("chq_wh_limit");
                        chq_wh_dr_to_cr_spe_narr = m_rs4.getString("chq_wh_dr_to_cr_spe_narr");
                    }
                }

                m_jsObj.put("chq_wh_dr_to_cr_spe_narr", chq_wh_dr_to_cr_spe_narr);

                //adding temporary limit feature - CFU-BRD-4 - Janaka_5977
                cw_seller_limit += getTotCWTemporaryLimits(idndb_customer_define_seller);

                double sl_has_byr_warehs_limit = sl_has_byr_warehs_limit_percentage / 100 * cw_seller_limit;
                m_jsObj.put("sl_has_byr_warehs_limit", "" + sl_has_byr_warehs_limit);
                if (!(cw_cheque_amu == null)) {
                    cw_cheque_amu_ctd = Double.parseDouble(cw_cheque_amu) - cw_old_cheque_amu_ctd;
                } else {
                    cw_cheque_amu_ctd = 0.00;
                }
                if (!(m_rs2 == null)) {
                    if (m_rs2.next()) {

                        pdc_chq_amu = m_rs2.getDouble("pdc_chq_amu");
                        available_amu = sl_has_byr_warehs_limit - (cw_cheque_amu_ctd + pdc_chq_amu);
//                        available_amu = sl_has_byr_warehs_limit - (pdc_chq_amu);

                    }
                } else {
                    pdc_chq_amu = 0.00;
                    available_amu = sl_has_byr_warehs_limit - (cw_cheque_amu_ctd + pdc_chq_amu);
//                    available_amu = sl_has_byr_warehs_limit - (pdc_chq_amu);

                }

                m_chstrsql2 = "select SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED') and pdc_req_financing='CW'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (!(m_rs2 == null)) {
                    if (m_rs2.next()) {
                        cw_seller_all_txn_amu = m_rs2.getDouble("pdc_chq_amu") - cw_old_cheque_amu_ctd;
                    }
                }
                if (cw_seller_limit < (cw_seller_all_txn_amu + cw_cheque_amu_ctd)) {
                    cw_seller_limit_exeeded_total = cw_seller_limit - (cw_seller_all_txn_amu + cw_cheque_amu_ctd);
                    seller_message = "Seller cheque warehousing limit has exceeded.Exceeded Amount : " + df.format(cw_seller_limit_exeeded_total) + "";

                }

                boolean m_buyer_acc_dtails_availability = false;
                m_chstrsql2 = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (m_rs2.next()) {
                    m_buyer_acc_dtails_availability = true;
                }

                boolean m_buyer_acc_dtails_validity = false;

                m_chstrsql2 = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "' and buyer_accs_bank_code='" + cust_bank_code + "' and buyer_accs_branch_code='" + cust_branch_code + "'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (m_rs2.next()) {
                    m_buyer_acc_dtails_validity = true;
                }

                m_jsObj.put("m_buyer_acc_dtails_availability", m_buyer_acc_dtails_availability);
                m_jsObj.put("m_buyer_acc_dtails_validity", m_buyer_acc_dtails_validity);

                m_jsObj.put("sl_has_byr_warehs_balance", available_amu);
                m_jsObj.put("sl_has_byr_warehs_balance_banner", df.format(available_amu));
                m_jsObj.put("seller_message", seller_message);
                m_jsArr.put(i, m_jsObj);
                i++;

            }

        } catch (Exception e) {
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }

                if (m_stamt != null) {
                    m_stamt.close();
                }

            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

//    public JSONArray getBuyerRelDataRMS(String prm_stridndb_seller_has_buyers, String rf_cheque_amu, String cust_bank_code, String cust_branch_code, String idndb_pdc_txn_master) {
//        JSONArray m_jsArr = new JSONArray();
//        Statement m_stamt = null;
//        Statement m_stamt2 = null;
//        Statement m_stamt3 = null;
//        Statement m_stamt4 = null;
//        ResultSet m_rs = null;
//        ResultSet m_rs2 = null;
//        ResultSet m_rs3 = null;
//        ResultSet m_rs4 = null;
//        String m_chstrsql = "";
//        String m_chstrsql2 = "";
//        String m_chstrsql3 = "";
//        String m_chstrsql4 = "";
//        JSONObject m_jsObj;
//        int i = 0;
//        try {
//            _connectionPool = ConnectionPool.getInstance();
//            _currentCon = _connectionPool.getJDBCConnection();
//            m_stamt = _currentCon.createStatement();
//            m_stamt2 = _currentCon.createStatement();
//            m_stamt3 = _currentCon.createStatement();
//            m_stamt4 = _currentCon.createStatement();
//
//            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + prm_stridndb_seller_has_buyers + "' and sl_has_byr_status ='ACTIVE' and sl_has_byr_auth='AUTHORIZED'";
//            m_rs = m_stamt.executeQuery(m_chstrsql);
//
//            if (m_rs.next()) {
//
//                m_jsObj = new JSONObject();
//                DecimalFormat df = new DecimalFormat("#,###.00");
//                double shb_facty_det_crd_loam_limit_percentage = m_rs.getDouble("shb_facty_det_crd_loam_limit");
//                String idndb_customer_define_seller = m_rs.getString("idndb_customer_define_seller");
//                String idndb_cust_prod_map_buyer = m_rs.getString("idndb_customer_define_buyer");
//
//                m_jsObj.put("idndb_seller_has_buyers", m_rs.getString("idndb_seller_has_buyers"));
//                //m_jsObj.put("shb_facty_det_crd_loam_limit", m_rs.getString("shb_facty_det_crd_loam_limit"));
//                m_jsObj.put("shb_facty_det_os", m_rs.getString("shb_facty_det_os"));
//                m_jsObj.put("shb_facty_det_tenor", m_rs.getString("shb_facty_det_tenor"));
//                m_jsObj.put("sl_has_byr_max_chq_amu", m_rs.getString("sl_has_byr_max_chq_amu"));
//                m_jsObj.put("sl_has_byr_max_chq_amu_banner", df.format(Double.parseDouble(m_rs.getString("sl_has_byr_max_chq_amu"))));
//
//                String shb_chq_dis_adv_rate_prectange = m_rs.getString("shb_chq_dis_adv_rate_prectange");
//
//                m_chstrsql2 = "select SUM(pdc_chq_discounting_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + prm_stridndb_seller_has_buyers + "' and idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED')";
//                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
//                double pdc_chq_amu = 0.00;
//                double available_amu = 0.00;
//
//                double rf_cheque_amu_ctd = 0.00;
//                double rf_old_cheque_amu_ctd = 0.00;
//
//                if (!((idndb_pdc_txn_master == null) || idndb_pdc_txn_master.equals(""))) {
//                    m_chstrsql3 = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master='" + idndb_pdc_txn_master + "'";
//                    m_rs3 = m_stamt3.executeQuery(m_chstrsql3);
//                    if (m_rs3.next()) {
//                        rf_old_cheque_amu_ctd = m_rs3.getDouble("pdc_chq_discounting_amu");
//
//                    }
//                }
//
//                if (!(rf_cheque_amu == null)) {
//                    double DIS_RF_AMU = Double.parseDouble(rf_cheque_amu) / 100 * Double.parseDouble(shb_chq_dis_adv_rate_prectange);
//
//                    rf_cheque_amu_ctd = DIS_RF_AMU;
//                } else {
//                    rf_cheque_amu_ctd = 0.00;
//                }
//
//                m_chstrsql4 = "select * from ndb_rec_fin where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
//                m_rs4 = m_stamt4.executeQuery(m_chstrsql4);
//                double rf_seller_limit = 0.00;
//                double rf_seller_all_txn_amu = 0.00;
//                double rf_seller_limit_exeeded_total = 0.00;
//                String seller_message = "";
//                if (!(m_rs4 == null)) {
//                    if (m_rs4.next()) {
//                        rf_seller_limit = m_rs4.getDouble("rec_finance_limit");
//                    }
//                }
//                //adding temporary limit feature - CFU-BRD-4 - Janaka_5977
//                rf_seller_limit += getTotRFTemporaryLimits(idndb_customer_define_seller);
//
//                double shb_facty_det_crd_loam_limit = shb_facty_det_crd_loam_limit_percentage / 100 * rf_seller_limit;
//                m_jsObj.put("shb_facty_det_crd_loam_limit", "" + shb_facty_det_crd_loam_limit);
//                if (!(m_rs2 == null)) {
//                    if (m_rs2.next()) {
//
//                        pdc_chq_amu = m_rs2.getDouble("pdc_chq_amu") - rf_old_cheque_amu_ctd;
//                        available_amu = shb_facty_det_crd_loam_limit - (rf_cheque_amu_ctd + pdc_chq_amu);
////                        available_amu = shb_facty_det_crd_loam_limit - (pdc_chq_amu);
//
//                    }
//                } else {
//                    pdc_chq_amu = 0.00;
//                    available_amu = shb_facty_det_crd_loam_limit - (rf_cheque_amu_ctd + pdc_chq_amu);
////                    available_amu = shb_facty_det_crd_loam_limit - (pdc_chq_amu);
//                }
//
//                m_chstrsql2 = "select SUM(pdc_chq_discounting_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED') and pdc_req_financing='RF'";
//                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
//                if (!(m_rs2 == null)) {
//                    if (m_rs2.next()) {
//                        rf_seller_all_txn_amu = m_rs2.getDouble("pdc_chq_amu") - rf_old_cheque_amu_ctd;
//                    }
//                }
//
//                if (rf_seller_limit < (rf_seller_all_txn_amu + rf_cheque_amu_ctd)) {
//                    rf_seller_limit_exeeded_total = rf_seller_limit - (rf_seller_all_txn_amu + rf_cheque_amu_ctd);
//
//                    seller_message = "Seller receviable finance loan limit has exceeded.Exceeded Amount : " + df.format(rf_seller_limit_exeeded_total) + "";
//                }
//
//                boolean m_buyer_acc_dtails_availability = false;
//                m_chstrsql2 = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "'";
//                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
//                if (m_rs2.next()) {
//                    m_buyer_acc_dtails_availability = true;
//                }
//
//                boolean m_buyer_acc_dtails_validity = false;
//
//                m_chstrsql2 = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "' and buyer_accs_bank_code='" + cust_bank_code + "' and buyer_accs_branch_code='" + cust_branch_code + "'";
//                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
//                if (m_rs2.next()) {
//                    m_buyer_acc_dtails_validity = true;
//                }
//
//                m_jsObj.put("m_buyer_acc_dtails_availability", m_buyer_acc_dtails_availability);
//                m_jsObj.put("m_buyer_acc_dtails_validity", m_buyer_acc_dtails_validity);
//
//                m_jsObj.put("seller_message", seller_message);
//                m_jsObj.put("sl_has_byr_rms_balance", available_amu);
//                m_jsObj.put("sl_has_byr_rms_balance_banner", df.format(available_amu));
//                m_jsArr.put(i, m_jsObj);
//                i++;
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (_currentCon != null) {
//                    _currentCon.close();
//                }
//                if (m_rs != null) {
//                    m_rs.close();
//                }
//
//                if (m_stamt != null) {
//                    m_stamt.close();
//                }
//
//            } catch (Exception e) {
//            }
//        }
//        return m_jsArr;
//    }
    public JSONArray getBuyerRelDataRMS(String prm_stridndb_seller_has_buyers, String rf_cheque_amu, String cust_bank_code, String cust_branch_code, String idndb_pdc_txn_master) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        Statement m_stamt2 = null;
        Statement m_stamt3 = null;
        Statement m_stamt4 = null;
        ResultSet m_rs = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        ResultSet m_rs4 = null;
        String m_chstrsql = "";
        String m_chstrsql2 = "";
        String m_chstrsql3 = "";
        String m_chstrsql4 = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();
            m_stamt2 = _currentCon.createStatement();
            m_stamt3 = _currentCon.createStatement();
            m_stamt4 = _currentCon.createStatement();

//            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + prm_stridndb_seller_has_buyers + "' and sl_has_byr_status ='ACTIVE' and sl_has_byr_auth='AUTHORIZED'";
//CFU-CR-03, CFU-CR-04,CFU-BRD-08 & BUB-BRD-6 (LOLC PDC UPLOAD) 
            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + prm_stridndb_seller_has_buyers + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
           
            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                DecimalFormat df = new DecimalFormat("#,###.00");
                double shb_facty_det_crd_loam_limit_percentage = m_rs.getDouble("shb_facty_det_crd_loam_limit");
                String idndb_customer_define_seller = m_rs.getString("idndb_customer_define_seller");
                String idndb_cust_prod_map_buyer = m_rs.getString("idndb_customer_define_buyer");

                m_jsObj.put("idndb_seller_has_buyers", m_rs.getString("idndb_seller_has_buyers"));
                //m_jsObj.put("shb_facty_det_crd_loam_limit", m_rs.getString("shb_facty_det_crd_loam_limit"));
                m_jsObj.put("shb_facty_det_os", m_rs.getString("shb_facty_det_os"));
                m_jsObj.put("shb_facty_det_tenor", m_rs.getString("shb_facty_det_tenor"));
                m_jsObj.put("sl_has_byr_max_chq_amu", m_rs.getString("sl_has_byr_max_chq_amu"));
                m_jsObj.put("sl_has_byr_max_chq_amu_banner", df.format(Double.parseDouble(m_rs.getString("sl_has_byr_max_chq_amu"))));

                String shb_chq_dis_adv_rate_prectange = m_rs.getString("shb_chq_dis_adv_rate_prectange");

                m_chstrsql2 = "select SUM(pdc_chq_discounting_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + prm_stridndb_seller_has_buyers + "' and idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED')";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                double pdc_chq_amu = 0.00;
                double available_amu = 0.00;

                double rf_cheque_amu_ctd = 0.00;
                double rf_old_cheque_amu_ctd = 0.00;

                if (!((idndb_pdc_txn_master == null) || idndb_pdc_txn_master.equals(""))) {
                    m_chstrsql3 = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master='" + idndb_pdc_txn_master + "'";
                    m_rs3 = m_stamt3.executeQuery(m_chstrsql3);
                    if (m_rs3.next()) {
                        rf_old_cheque_amu_ctd = m_rs3.getDouble("pdc_chq_discounting_amu");

                    }
                }

                if (!(rf_cheque_amu == null)) {
                    double DIS_RF_AMU = Double.parseDouble(rf_cheque_amu) / 100 * Double.parseDouble(shb_chq_dis_adv_rate_prectange);

                    rf_cheque_amu_ctd = DIS_RF_AMU;
                } else {
                    rf_cheque_amu_ctd = 0.00;
                }

                m_chstrsql4 = "select * from ndb_rec_fin where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
                m_rs4 = m_stamt4.executeQuery(m_chstrsql4);
                double rf_seller_limit = 0.00;
                double rf_seller_all_txn_amu = 0.00;
                double rf_seller_limit_exeeded_total = 0.00;
                String seller_message = "";
                if (!(m_rs4 == null)) {
                    if (m_rs4.next()) {
                        rf_seller_limit = m_rs4.getDouble("rec_finance_limit");
                    }
                }
                //adding temporary limit feature - CFU-BRD-4 - Janaka_5977
                rf_seller_limit += getTotRFTemporaryLimits(idndb_customer_define_seller);

                double shb_facty_det_crd_loam_limit = shb_facty_det_crd_loam_limit_percentage / 100 * rf_seller_limit;
                m_jsObj.put("shb_facty_det_crd_loam_limit", "" + shb_facty_det_crd_loam_limit);
                if (!(m_rs2 == null)) {
                    if (m_rs2.next()) {

                        pdc_chq_amu = m_rs2.getDouble("pdc_chq_amu") - rf_old_cheque_amu_ctd;
                        available_amu = shb_facty_det_crd_loam_limit - (rf_cheque_amu_ctd + pdc_chq_amu);
//                        available_amu = shb_facty_det_crd_loam_limit - (pdc_chq_amu);

                    }
                } else {
                    pdc_chq_amu = 0.00;
                    available_amu = shb_facty_det_crd_loam_limit - (rf_cheque_amu_ctd + pdc_chq_amu);
//                    available_amu = shb_facty_det_crd_loam_limit - (pdc_chq_amu);
                }

                m_chstrsql2 = "select SUM(pdc_chq_discounting_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED') and pdc_req_financing='RF'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (!(m_rs2 == null)) {
                    if (m_rs2.next()) {
                        rf_seller_all_txn_amu = m_rs2.getDouble("pdc_chq_amu") - rf_old_cheque_amu_ctd;
                    }
                }

                if (rf_seller_limit < (rf_seller_all_txn_amu + rf_cheque_amu_ctd)) {
                    rf_seller_limit_exeeded_total = rf_seller_limit - (rf_seller_all_txn_amu + rf_cheque_amu_ctd);

                    seller_message = "Seller receviable finance loan limit has exceeded.Exceeded Amount : " + df.format(rf_seller_limit_exeeded_total) + "";
                }

                boolean m_buyer_acc_dtails_availability = false;
                m_chstrsql2 = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (m_rs2.next()) {
                    m_buyer_acc_dtails_availability = true;
                }

                boolean m_buyer_acc_dtails_validity = false;

                m_chstrsql2 = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "' and buyer_accs_bank_code='" + cust_bank_code + "' and buyer_accs_branch_code='" + cust_branch_code + "'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (m_rs2.next()) {
                    m_buyer_acc_dtails_validity = true;
                }

                m_jsObj.put("m_buyer_acc_dtails_availability", m_buyer_acc_dtails_availability);
                m_jsObj.put("m_buyer_acc_dtails_validity", m_buyer_acc_dtails_validity);

                m_jsObj.put("seller_message", seller_message);
                m_jsObj.put("sl_has_byr_rms_balance", available_amu);
                m_jsObj.put("sl_has_byr_rms_balance_banner", df.format(available_amu));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }

                if (m_stamt != null) {
                    m_stamt.close();
                }

            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getBuyerRelDataRMScw_to_rf(String prm_stridndb_seller_has_buyers, String cust_bank_code, String cust_branch_code, String rf_cheque_amu) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        Statement m_stamt2 = null;
        Statement m_stamt3 = null;
        Statement m_stamt4 = null;
        ResultSet m_rs = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        ResultSet m_rs4 = null;
        String m_chstrsql = "";
        String m_chstrsql2 = "";
        String m_chstrsql3 = "";
        String m_chstrsql4 = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();
            m_stamt2 = _currentCon.createStatement();
            m_stamt3 = _currentCon.createStatement();
            m_stamt4 = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + prm_stridndb_seller_has_buyers + "' and sl_has_byr_status ='ACTIVE' and sl_has_byr_auth='AUTHORIZED'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                DecimalFormat df = new DecimalFormat("#,###.00");
                double shb_facty_det_crd_loam_limit_percentage = m_rs.getDouble("shb_facty_det_crd_loam_limit");
                //double shb_facty_det_crd_loam_limit = m_rs.getDouble("shb_facty_det_crd_loam_limit");
                String idndb_customer_define_seller = m_rs.getString("idndb_customer_define_seller");
                String idndb_cust_prod_map_buyer = m_rs.getString("idndb_customer_define_buyer");
                m_jsObj.put("idndb_seller_has_buyers", m_rs.getString("idndb_seller_has_buyers"));
                m_jsObj.put("shb_facty_det_crd_loam_limit", m_rs.getString("shb_facty_det_crd_loam_limit"));
                m_jsObj.put("shb_facty_det_os", m_rs.getString("shb_facty_det_os"));
                m_jsObj.put("shb_facty_det_tenor", m_rs.getString("shb_facty_det_tenor"));
                m_jsObj.put("sl_has_byr_max_chq_amu", m_rs.getString("sl_has_byr_max_chq_amu"));
                m_jsObj.put("sl_has_byr_max_chq_amu_banner", df.format(Double.parseDouble(m_rs.getString("sl_has_byr_max_chq_amu"))));

                String shb_chq_dis_adv_rate_prectange = m_rs.getString("shb_chq_dis_adv_rate_prectange");

                m_chstrsql4 = "select * from ndb_rec_fin where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
                m_rs4 = m_stamt4.executeQuery(m_chstrsql4);
                double rf_seller_limit = 0.00;
                double rf_seller_all_txn_amu = 0.00;
                double rf_seller_limit_exeeded_total = 0.00;
                String seller_message = "";
                if (!(m_rs4 == null)) {
                    if (m_rs4.next()) {
                        rf_seller_limit = m_rs4.getDouble("rec_finance_limit");
                    }
                }
                //adding temporary limit feature - CFU-BRD-4 - Janaka_5977
                rf_seller_limit += getTotRFTemporaryLimits(idndb_customer_define_seller);

                double shb_facty_det_crd_loam_limit = shb_facty_det_crd_loam_limit_percentage / 100 * rf_seller_limit;
                m_jsObj.put("shb_facty_det_crd_loam_limit", "" + shb_facty_det_crd_loam_limit);

                m_chstrsql2 = "select SUM(pdc_chq_discounting_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + prm_stridndb_seller_has_buyers + "' and idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED')";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                double pdc_chq_amu = 0.00;
                double available_amu = 0.00;

                double rf_cheque_amu_ctd = 0.00;
                double rf_old_cheque_amu_ctd = 0.00;

                if (!(rf_cheque_amu == null)) {
                    double DIS_RF_AMU = Double.parseDouble(rf_cheque_amu) / 100 * Double.parseDouble(shb_chq_dis_adv_rate_prectange);

                    rf_cheque_amu_ctd = DIS_RF_AMU;
                } else {
                    rf_cheque_amu_ctd = 0.00;
                }

                if (!(m_rs2 == null)) {
                    if (m_rs2.next()) {

                        pdc_chq_amu = m_rs2.getDouble("pdc_chq_amu") - rf_old_cheque_amu_ctd;
                        available_amu = shb_facty_det_crd_loam_limit - (rf_cheque_amu_ctd + pdc_chq_amu);

                    }
                } else {
                    pdc_chq_amu = 0.00;
                    available_amu = shb_facty_det_crd_loam_limit - (rf_cheque_amu_ctd + pdc_chq_amu);
                }

                m_chstrsql2 = "select SUM(pdc_chq_discounting_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED') and pdc_req_financing='RF'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (!(m_rs2 == null)) {
                    if (m_rs2.next()) {
                        rf_seller_all_txn_amu = m_rs2.getDouble("pdc_chq_amu") - rf_old_cheque_amu_ctd;
                    }
                }

                if (rf_seller_limit < (rf_seller_all_txn_amu + rf_cheque_amu_ctd)) {
                    rf_seller_limit_exeeded_total = rf_seller_limit - (rf_seller_all_txn_amu + rf_cheque_amu_ctd);

                    seller_message = "Seller receviable finance loan limit has exceeded.Exceeded Amount : " + df.format(rf_seller_limit_exeeded_total) + "";
                }

                boolean m_buyer_acc_dtails_availability = false;
                m_chstrsql2 = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (m_rs2.next()) {
                    m_buyer_acc_dtails_availability = true;
                }

                boolean m_buyer_acc_dtails_validity = false;

                m_chstrsql2 = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "' and buyer_accs_bank_code='" + cust_bank_code + "' and buyer_accs_branch_code='" + cust_branch_code + "'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (m_rs2.next()) {
                    m_buyer_acc_dtails_validity = true;
                }

                m_jsObj.put("m_buyer_acc_dtails_availability", m_buyer_acc_dtails_availability);
                m_jsObj.put("m_buyer_acc_dtails_validity", m_buyer_acc_dtails_validity);

                m_jsObj.put("seller_message", seller_message);

                m_jsObj.put("sl_has_byr_rms_balance", available_amu);
                m_jsObj.put("sl_has_byr_rms_balance_banner", df.format(available_amu));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_rs2 != null) {
                    m_rs2.close();
                }

                if (m_stamt != null) {
                    m_stamt.close();
                }
                if (m_stamt2 != null) {
                    m_stamt2.close();
                }
                if (m_stamt3 != null) {
                    m_stamt3.close();
                }

            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getCustomerDifineData(String prm_stridndb_customer_define) {
        JSONArray m_jsArr = new JSONArray();
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_customer_define where idndb_customer_define='" + prm_stridndb_customer_define + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_customer_define", m_rs.getString("idndb_customer_define"));
                m_jsObj.put("cust_id", m_rs.getString("cust_id"));
                m_jsObj.put("cust_name", m_rs.getString("cust_name"));
                m_jsObj.put("rec_finance_acc_num", m_rs.getString("rec_finance_acc_num"));
                m_jsObj.put("rec_finance_cr_dsc_proc_acc_num", m_rs.getString("rec_finance_cr_dsc_proc_acc_num"));
                m_jsObj.put("rec_finance_curr_ac", m_rs.getString("rec_finance_curr_ac"));
                m_jsObj.put("cms_curr_acc_number", m_rs.getString("cms_curr_acc_number"));
                m_jsObj.put("cms_collection_acc_number", m_rs.getString("cms_collection_acc_number"));
                m_jsObj.put("rec_finance_margin_ac", m_rs.getString("rec_finance_margin_ac"));
                m_jsObj.put("rec_finance_margin", m_rs.getString("rec_finance_margin"));
                m_jsObj.put("cust_credit_rate", m_rs.getString("cust_credit_rate"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
        } finally {
            try {
                if (_currentCon != null) {
                    _currentCon.close();
                }
                if (m_rs != null) {
                    m_rs.close();
                }
                if (m_stamt != null) {
                    m_stamt.close();
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

    private double getTotRFTemporaryLimits(String idndb_customer_define_seller) {
        double notExpiredRFTemporaryLimits = 0;
        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getJDBCConnection();
            String sql = "Select sum(ndb_rec_fin_temp_value) as rfTempTotal from ndb_rec_fin_temp_limits where ndb_rec_fin_temp_exp_date>= date(now()) and idndb_rec_fin in (select idndb_rec_fin from ndb_rec_fin where idndb_cust_prod_map=?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idndb_customer_define_seller);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                notExpiredRFTemporaryLimits = rs.getDouble("rfTempTotal");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (Exception e) {
            }
        }
        return notExpiredRFTemporaryLimits;
    }

    private double getTotCWTemporaryLimits(String idndb_customer_define_seller) {
        double notExpiredCWTemporaryLimits = 0;

        ConnectionPool connectionPool = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getJDBCConnection();
            String sql = "Select sum(ndb_chq_wh_temp_value) as cwTempTotal from ndb_chq_wh_temp_limits where ndb_chq_wh_temp_exp_date>= date(now()) and idndb_chq_wh in (select idndb_chq_wh from ndb_chq_wh where idndb_cust_prod_map=?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idndb_customer_define_seller);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                notExpiredCWTemporaryLimits = rs.getDouble("cwTempTotal");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (Exception e) {
            }
        }
        return notExpiredCWTemporaryLimits;
    }
}
