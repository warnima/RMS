/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBOops;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
    private Statement _stmnt3 = null;
    private ResultSet _rs = null;
    private ResultSet _rs2 = null;
    private ResultSet _rs3 = null;
    private Exception _exception;

    public String saveCustomerDifineData(JSONObject prm_obj) {
        log.info("customer record recevied :");
        String success = "1000";
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

        String m_Strcust_cms_curr_acc_number = "";
        String m_StrOldcust_cms_curr_acc_number = "";

        String m_Strcust_cms_collection_acc_number = "";
        String m_StrOldcust_cms_collection_acc_number = "";

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

        String m_Strcust_status_reason = "";
        String m_StrOldcust_status_reason = "";

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
            m_Strcust_cms_curr_acc_number = prm_obj.getString("cms_curr_acc_number");
            m_Strcust_cms_collection_acc_number = prm_obj.getString("cms_collection_acc_number");
            m_Strcust_margin_ac_no = prm_obj.getString("cust_margin_ac_no");
            m_Strcust_margin = prm_obj.getString("cust_margin");
            m_Strcust_credt_rate = prm_obj.getString("cust_credt_rate");
            m_Strcust_bankid = prm_obj.getString("cust_bank");
            m_Strcust_branchid = prm_obj.getString("cust_branch");
            m_Strcust_other_bank_ac_no = prm_obj.getString("cust_other_bank_ac_no");
            m_Strcust_geo_marketid = prm_obj.getString("cust_geo_market");
            m_Strcust_ch_active = prm_obj.getString("cust_ch_active");
            m_Strcust_ndb_customer_active = prm_obj.getString("cust_ndb_customer_active");
            m_Strcust_status_reason = prm_obj.getString("statusReason");

            if (m_Strcust_margin.equals("")) {
                m_Strcust_margin = "0";
            }

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }

            log.info("check for received record all ready exist:" + m_str_idndb_customer_define);
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            if (m_str_idndb_customer_define.equals("") && (!m_Strcust_id.equals(""))) {
                m_strQry = "select * from ndb_customer_define where cust_id='" + m_Strcust_id + "' and cust_status='ACTIVE'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1101";
                    throw new Exception("Customer All Ready Exist");
                }

            }

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
                m_StrOldcust_cms_curr_acc_number = _rs.getString("cms_curr_acc_number");
                m_StrOldcust_cms_collection_acc_number = _rs.getString("cms_collection_acc_number");
                m_StrOldcust_margin_ac_no = _rs.getString("rec_finance_margin_ac");
                m_StrOldcust_margin = _rs.getString("rec_finance_margin");
                m_StrOldcust_credt_rate = _rs.getString("cust_credit_rate");
                m_StrOldcust_bankid = _rs.getString("idndb_bank_master_file");
                m_StrOldcust_branchid = _rs.getString("idndb_branch_master_file");
                m_StrOldcust_other_bank_ac_no = _rs.getString("cust_other_bank_ac_no");
                m_StrOldcust_geo_marketid = _rs.getString("idndb_geo_market_master_file");
                m_StrOldcust_ch_active = _rs.getString("cust_status");
                m_StrOldcust_ndb_customer_active = _rs.getString("cust_is_ndb_cust");
                m_StrOldcust_status_reason = _rs.getString("cust_status_reason");

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
                        + ",cms_curr_acc_number"
                        + ",cms_collection_acc_number"
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
                        + ",cust_status_reason"
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
                        + "'" + m_Strcust_cms_curr_acc_number + "',"
                        + "'" + m_Strcust_cms_collection_acc_number + "',"
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
                        + "now(),"
                        + "'" + m_Strcust_status_reason + "')";

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

                m_strQry = "select * from ndb_cust_prod_map where idndb_customer_define='" + m_str_idndb_customer_define + "' and prod_relationship_auth='UN-AUTHORIZED'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1104";
                    throw new Exception("Cannot amend this data");
                }
//                m_strQry = "select * from ndb_cust_prod_map where idndb_customer_define='" + m_str_idndb_customer_define + "'";
//                _rs = _stmnt.executeQuery(m_strQry);
//                while (_rs.next()) {
//                    String m_strQry2 = "select * from ndb_seller_has_buyers where sl_has_byr_auth='UN-AUTHORIZED' and (idndb_customer_define_seller='" + _rs.getString("idndb_cust_prod_map") + "' or idndb_customer_define_buyer='" + _rs.getString("idndb_cust_prod_map") + "')";
//                    _rs2 = _stmnt2.executeQuery(m_strQry2);
//                    while (_rs2.next()) {
//                        success = "1105";
//                        throw new Exception("Cannot amend this data");
//                    }
//
//                }

                m_strQry = "select nshb.idndb_seller_has_buyers\n"
                        + "from ndb_seller_has_buyers nshb, \n"
                        + "     ndb_cust_prod_map ncpm\n"
                        + "where nshb.sl_has_byr_auth='UN-AUTHORIZED' and \n"
                        + "(nshb.idndb_customer_define_seller = ncpm.idndb_cust_prod_map or nshb.idndb_customer_define_buyer=ncpm.idndb_cust_prod_map) and\n"
                        + "ncpm.idndb_customer_define='" + m_str_idndb_customer_define + "'";
                _rs = _stmnt.executeQuery(m_strQry);

                if (_rs.next()) {
                    success = "1105";
                    throw new Exception("Cannot amend this data");
                }

//                m_strQry = "select * from ndb_cust_prod_map where idndb_customer_define='" + m_str_idndb_customer_define + "'";
//                _rs = _stmnt.executeQuery(m_strQry);
//                while (_rs.next()) {
//                    String m_strQry2 = "select * from ndb_seller_has_buyers where idndb_customer_define_seller='" + _rs.getString("idndb_cust_prod_map") + "' or idndb_customer_define_buyer='" + _rs.getString("idndb_cust_prod_map") + "'";
//                    _rs2 = _stmnt2.executeQuery(m_strQry2);
//                    while (_rs2.next()) {
//
//                        String m_strQry3 = "select * from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + _rs2.getString("idndb_seller_has_buyers") + "' and pdc_chq_status_auth='UN-AUTHORIZED'";
//                        _rs3 = _stmnt3.executeQuery(m_strQry3);
//                        while (_rs3.next()) {
//                            success = "1106";
//                            throw new Exception("Cannot amend this data");
//                        }
//
//                    }
//
//                }
                m_strQry = "select \n"
                        + "nptm.idndb_pdc_txn_master\n"
                        + "from\n"
                        + "ndb_pdc_txn_master nptm,\n"
                        + "ndb_seller_has_buyers nshb,\n"
                        + "ndb_cust_prod_map ncpm\n"
                        + "where \n"
                        + "nptm.idndb_customer_define_buyer_id= nshb.idndb_seller_has_buyers and \n"
                        + "nptm.pdc_chq_status_auth='UN-AUTHORIZED' and \n"
                        + "(nshb.idndb_customer_define_seller = ncpm.idndb_cust_prod_map or\n"
                        + "nshb.idndb_customer_define_buyer = ncpm.idndb_cust_prod_map) and\n"
                        + "ncpm.idndb_customer_define='" + m_str_idndb_customer_define + "'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1106";
                    throw new Exception("Cannot amend this data");
                }

                String m_condition = "";
                String m_change = "";
                int i = 0;
                if (!m_StrOldcust_id.equals(m_Strcust_id)) {

                    i++;
                    m_condition = "cust_id='" + m_Strcust_id + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_id + " ID CHANGE TO " + m_Strcust_id + "<br>";
                    m_strQry = "select * from ndb_customer_define where cust_id='" + m_Strcust_id + "' and cust_status='ACTIVE'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        success = "1101";
                        throw new Exception("Customer All Ready Exist");
                    }

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

                if (!(m_StrOldcust_cms_curr_acc_number == null ? "" : m_StrOldcust_cms_curr_acc_number).equals(m_Strcust_cms_curr_acc_number)) {
                    i++;
                    m_condition = m_condition + "cms_curr_acc_number='" + m_Strcust_cms_curr_acc_number + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_cms_curr_acc_number + "CMS CURR. ACC. CHANGE TO " + m_Strcust_cms_curr_acc_number + "<br>";
                }

                if (!(m_StrOldcust_cms_collection_acc_number == null ? "" : m_StrOldcust_cms_collection_acc_number).equals(m_Strcust_cms_collection_acc_number)) {
                    i++;
                    m_condition = m_condition + "cms_collection_acc_number='" + m_Strcust_cms_collection_acc_number + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_cms_collection_acc_number + "CMS COLLECTION ACC. CHANGE TO " + m_Strcust_cms_collection_acc_number + "<br>";
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
                if (m_Strcust_ndb_customer_active.equals("ACTIVE")) {

                    if (!m_StrOldcust_bankid.equals(m_Strcust_bankid)) {
                        i++;
                        m_condition = m_condition + "idndb_bank_master_file='" + m_Strcust_bankid + "',";
                        m_change = m_change + i + ")" + m_StrOldcust_bankid + " BANK CHANGE TO " + m_Strcust_bankid + "<br>";
                    }
                }

                if (m_Strcust_ndb_customer_active.equals("ACTIVE")) {
                    if (!m_StrOldcust_branchid.equals(m_Strcust_branchid)) {
                        i++;
                        m_condition = m_condition + "idndb_branch_master_file='" + m_Strcust_branchid + "',";
                        m_change = m_change + i + ")" + m_StrOldcust_branchid + " BRANCH CHANGE TO " + m_Strcust_branchid + "<br>";
                    }
                }

                if (!m_StrOldcust_other_bank_ac_no.equals(m_Strcust_other_bank_ac_no)) {
                    m_condition = m_condition + "cust_other_bank_ac_no='" + m_Strcust_other_bank_ac_no + "',";
                    m_change = m_change + m_StrOldcust_other_bank_ac_no + " OTHER BANK ACC. CHANGE TO " + m_Strcust_other_bank_ac_no + "<br>";
                }

                if (m_Strcust_ndb_customer_active.equals("ACTIVE")) {
                    if (!m_StrOldcust_geo_marketid.equals(m_Strcust_geo_marketid)) {
                        i++;
                        m_condition = m_condition + "idndb_geo_market_master_file='" + m_Strcust_geo_marketid + "',";
                        m_change = m_change + i + ")" + m_StrOldcust_geo_marketid + " GEOR. MARKT. CHANGE TO " + m_Strcust_geo_marketid + "<br>";
                    }
                }

                if (!m_StrOldcust_ch_active.equals(m_Strcust_ch_active)) {
                    i++;
                    m_condition = m_condition + "cust_status='" + m_Strcust_ch_active + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_ch_active + " STATUS CHANGE TO " + m_Strcust_ch_active + "<br>";
                }
                if (!m_StrOldcust_status_reason.equals(m_Strcust_status_reason)) {
                    i++;
                    m_condition = m_condition + "cust_status_reason='" + m_Strcust_status_reason + "',";
                    m_change = m_change + i + ")" + m_StrOldcust_status_reason + " STATUS REASON CHANGE TO " + m_Strcust_status_reason + "<br>";
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
            success = "1100";
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in saving cutomer define data, Exception" + e);

        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_rs2 != null) {
                    _rs2.close();
                }
                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_stmnt2 != null) {
                    _stmnt2.close();
                }
                if (_stmnt3 != null) {
                    _stmnt3.close();
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
            log.error("Error occured in authorizing cutomer define data, Exception" + e);
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
            log.error("Error occured in rejecting cutomer define data, Exception" + e);
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
                throw new Exception("Error occured in start connection");
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
                    throw new Exception("Error Occured in authorizing customer product mapping data");
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end connection");
            }
            success = true;
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in authorizing  cutomer product mapping data, Exception" + e);
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
                throw new Exception("Error occured in start connection");
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
                    throw new Exception("Error Occured in rejecting customer product mapping data.");
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end connection");
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

    public String saveCustomerRelationShipEstbmntData(JSONObject prm_obj) {
        String success = "1000";
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

        String m_Strsl_has_byr_status_reason = "";
        String m_StrOldsl_has_byr_status_reason = "";

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
            m_Strsl_has_byr_status_reason = prm_obj.getString("statusReason");

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

            if (m_str_idndb_seller_has_buyers.equals("") && (!m_Stridndb_customer_define_seller.equals("") && (!m_Stridndb_customer_define_buyer.equals("")) && m_Strsl_has_byr_prorm_type.equals("RF"))) {
                m_strQry = "select * from ndb_seller_has_buyers where idndb_customer_define_seller='" + m_Stridndb_customer_define_seller + "' and idndb_customer_define_buyer='" + m_Stridndb_customer_define_buyer + "' and sl_has_byr_prorm_type='RF' and sl_has_byr_status='ACTIVE'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1101";
                    throw new Exception("Customer All Ready Exist");
                }

            }

            if (m_str_idndb_seller_has_buyers.equals("") && (!m_Stridndb_customer_define_seller.equals("") && (!m_Stridndb_customer_define_buyer.equals("")) && m_Strsl_has_byr_prorm_type.equals("CW"))) {
                m_strQry = "select * from ndb_seller_has_buyers where idndb_customer_define_seller='" + m_Stridndb_customer_define_seller + "' and idndb_customer_define_buyer='" + m_Stridndb_customer_define_buyer + "' and sl_has_byr_prorm_type='CW' and sl_has_byr_status='ACTIVE'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1102";
                    throw new Exception("Customer All Ready Exist");
                }

            }

            m_strQry = "SELECT\n"
                    + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                    + "     ndb_cust_prod_map.`idndb_customer_define` AS ndb_cust_prod_map_idndb_customer_define,\n"
                    + "     ndb_cust_prod_map_A.`idndb_cust_prod_map` AS ndb_cust_prod_map_A_idndb_cust_prod_map,\n"
                    + "     ndb_cust_prod_map_A.`idndb_customer_define` AS ndb_cust_prod_map_A_idndb_customer_define,\n"
                    + "     ndb_cust_prod_map_A.`prod_relationship_key_seller` AS ndb_cust_prod_map_A_prod_relationship_key_seller,\n"
                    + "     ndb_cust_prod_map_A.`prod_relationship_key_buyer` AS ndb_cust_prod_map_A_prod_relationship_key_buyer,\n"
                    + "     ndb_cust_prod_map_A.`prod_relationship_status` AS ndb_cust_prod_map_A_prod_relationship_status,\n"
                    + "     ndb_cust_prod_map_A.`prod_relationship_auth` AS ndb_cust_prod_map_A_prod_relationship_auth,\n"
                    + "     ndb_cust_prod_map_A.`prod_relationship_res_fin` AS ndb_cust_prod_map_A_prod_relationship_res_fin,\n"
                    + "     ndb_cust_prod_map_A.`prod_relationship_chq_ware` AS ndb_cust_prod_map_A_prod_relationship_chq_ware,\n"
                    + "     ndb_cust_prod_map_A.`prod_relationship_reship_only` AS ndb_cust_prod_map_A_prod_relationship_reship_only,\n"
                    + "     ndb_cust_prod_map_A.`prod_relationship_creat_by` AS ndb_cust_prod_map_A_prod_relationship_creat_by,\n"
                    + "     ndb_cust_prod_map_A.`prod_relationship_creat_date` AS ndb_cust_prod_map_A_prod_relationship_creat_date,\n"
                    + "     ndb_cust_prod_map_A.`prod_relationship_mod_by` AS ndb_cust_prod_map_A_prod_relationship_mod_by,\n"
                    + "     ndb_cust_prod_map_A.`prod_relationship_mod_date` AS ndb_cust_prod_map_A_prod_relationship_mod_date\n"
                    + "FROM\n"
                    + "     `ndb_cust_prod_map` ndb_cust_prod_map INNER JOIN `ndb_cust_prod_map` ndb_cust_prod_map_A ON ndb_cust_prod_map.`idndb_customer_define` = ndb_cust_prod_map_A.`idndb_customer_define` where ndb_cust_prod_map.`idndb_cust_prod_map`='" + m_Stridndb_customer_define_seller + "' and ndb_cust_prod_map_A.`idndb_cust_prod_map`='" + m_Stridndb_customer_define_buyer + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "1103";
                throw new Exception("Customer All Ready Exist");
            }

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
                m_StrOldsl_has_byr_advnce_rate_prstage = _rs.getString("shb_chq_dis_adv_rate_prectange");

                m_StrOldsl_has_byr_remarks = _rs.getString("sl_has_byr_remarks");
                m_StrOldsl_has_byr_active = _rs.getString("sl_has_byr_status");
                m_StrOldsl_has_byr_status_reason = _rs.getString("sl_has_byr_status_reason");

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
                        + ",sl_has_byr_status_reason"
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
                        + "now(),"
                        + "'" + m_Strsl_has_byr_status_reason + "')";

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
                m_strQry = "select * from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + m_str_idndb_seller_has_buyers + "' and pdc_chq_status_auth='UN-AUTHORIZED'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1104";
                    throw new Exception("Cannot amend this data");
                }

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

                double m_StrOldsl_has_byr_fmax_chq_amu_dub = Double.parseDouble(m_StrOldsl_has_byr_fmax_chq_amu);
                double m_Strsl_has_byr_fmax_chq_amu_dub = Double.parseDouble(m_Strsl_has_byr_fmax_chq_amu);

                if (!(m_StrOldsl_has_byr_fmax_chq_amu_dub == m_Strsl_has_byr_fmax_chq_amu_dub)) {
                    m_condition = m_condition + "sl_has_byr_max_chq_amu='" + m_Strsl_has_byr_fmax_chq_amu + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_fmax_chq_amu + " RF PER CHQ AMU. CHANGE TO " + m_Strsl_has_byr_fmax_chq_amu + "<br>";

                }

                double m_StrOldsl_has_byr_cdt_loan_amu_dub = Double.parseDouble(m_StrOldsl_has_byr_cdt_loan_amu);
                double m_Strsl_has_byr_cdt_loan_amu_dub = Double.parseDouble(m_Strsl_has_byr_cdt_loan_amu);

                if (!(m_StrOldsl_has_byr_cdt_loan_amu_dub == m_Strsl_has_byr_cdt_loan_amu_dub)) {
                    m_condition = m_condition + "shb_facty_det_crd_loam_limit='" + m_Strsl_has_byr_cdt_loan_amu + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_cdt_loan_amu + " RF LOAN LIMIT PERCENTAGE CHANGE TO " + m_Strsl_has_byr_cdt_loan_amu + "<br>";

                }

                double m_StrOldsl_has_byr_otstaning_dub = Double.parseDouble(m_StrOldsl_has_byr_otstaning);
                double m_Strsl_has_byr_otstaning_dub = Double.parseDouble(m_Strsl_has_byr_otstaning);

//                if (!(m_StrOldsl_has_byr_otstaning_dub == m_Strsl_has_byr_otstaning_dub)) {
//                    m_condition = m_condition + "shb_facty_det_os='" + m_Strsl_has_byr_otstaning + "',";
//                    i++;
//                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_cdt_loan_amu + " RF BUYER UOTSTND. CHANGE TO " + m_Strsl_has_byr_cdt_loan_amu + "<br>";
//
//                }
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

                if (!m_StrOldsl_has_byr_status_reason.equals(m_Strsl_has_byr_status_reason)) {
                    m_condition = m_condition + "sl_has_byr_status_reason='" + m_Strsl_has_byr_status_reason + "',";

                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_status_reason + " RELATIONSHIP ESTB. STATUS REASON CHANGE TO " + m_Strsl_has_byr_status_reason + "<br>";
                }

                //cw facility details
                double m_StrOldsl_has_byr_warehs_limit_dub = Double.parseDouble(m_StrOldsl_has_byr_warehs_limit);
                double m_Strsl_has_byr_warehs_limit_dub = Double.parseDouble(m_Strsl_has_byr_warehs_limit);

                if (!(m_StrOldsl_has_byr_warehs_limit_dub == m_Strsl_has_byr_warehs_limit_dub)) {
                    m_condition = m_condition + "sl_has_byr_warehs_limit='" + m_Strsl_has_byr_warehs_limit + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_warehs_limit + " CW BUYER LIMIT PERCENTAGE CHANGE TO " + m_Strsl_has_byr_warehs_limit + "<br>";

                }

                double m_StrOldsl_has_byr_warehs_otstaning_dub = Double.parseDouble(m_StrOldsl_has_byr_warehs_otstaning);
                double m_Strsl_has_byr_warehs_otstaning_dub = Double.parseDouble(m_Strsl_has_byr_warehs_otstaning);

//                if (!(m_StrOldsl_has_byr_warehs_otstaning_dub == m_Strsl_has_byr_warehs_otstaning_dub)) {
//                    m_condition = m_condition + "sl_has_byr_warehs_otstaning='" + m_Strsl_has_byr_warehs_otstaning + "',";
//                    i++;
//                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_warehs_otstaning + " CW BUYER OUTSATANDING CHANGE TO " + m_Strsl_has_byr_warehs_otstaning + "<br>";
//
//                }
                if (!m_StrOldsl_has_byr_warehs_tenor.equals(m_Strsl_has_byr_warehs_tenor)) {
                    m_condition = m_condition + "sl_has_byr_warehs_tenor='" + m_Strsl_has_byr_warehs_tenor + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_warehs_tenor + " CW BUYER TENOR CHANGE TO " + m_Strsl_has_byr_warehs_tenor + "<br>";

                }

                double m_StrOldsl_has_byr_warehs_fmax_chq_amu_dub = Double.parseDouble(m_StrOldsl_has_byr_warehs_fmax_chq_amu);
                double m_Strsl_has_byr_warehs_fmax_chq_amu_dub = Double.parseDouble(m_Strsl_has_byr_warehs_fmax_chq_amu);

                if (!(m_StrOldsl_has_byr_warehs_fmax_chq_amu_dub == m_Strsl_has_byr_warehs_fmax_chq_amu_dub)) {
                    m_condition = m_condition + "sl_has_byr_warehs_fmax_chq_amu='" + m_Strsl_has_byr_warehs_fmax_chq_amu + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldsl_has_byr_warehs_fmax_chq_amu + " CW BUYER PER. CHQ. AMU. CHANGE TO " + m_Strsl_has_byr_warehs_fmax_chq_amu + "<br>";

                }

                if (!m_StrOldrec_finance_commision_crg.equals(m_Strrec_finance_commision_crg)) {
                    m_condition = m_condition + "rec_finance_commision_crg='" + m_Strrec_finance_commision_crg + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrec_finance_commision_crg + " RF COMM. CHG. CHANGE TO " + m_Strrec_finance_commision_crg + "<br>";

                }

                double m_StrOldrf_tran_base_falt_fee_dub = Double.parseDouble(m_StrOldrf_tran_base_falt_fee);
                double m_Strrf_tran_base_falt_fee_dub = Double.parseDouble(m_Strrf_tran_base_falt_fee);

                if (!(m_StrOldrf_tran_base_falt_fee_dub == m_Strrf_tran_base_falt_fee_dub)) {
                    m_condition = m_condition + "rf_tran_base_falt_fee='" + m_Strrf_tran_base_falt_fee + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_tran_base_falt_fee + " RF COMM. CHG. FLAT FEE CHANGE TO " + m_Strrf_tran_base_falt_fee + "<br>";

                }

                double m_StrOldrf_tran_base_from_tran_dub = Double.parseDouble(m_StrOldrf_tran_base_from_tran);
                double m_Strrf_tran_base_from_tran_dub = Double.parseDouble(m_Strrf_tran_base_from_tran);

                if (!(m_StrOldrf_tran_base_from_tran_dub == m_Strrf_tran_base_from_tran_dub)) {
                    m_condition = m_condition + "rf_tran_base_from_tran='" + m_Strrf_tran_base_from_tran + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_tran_base_falt_fee + " RF COMM. CHG. TRN. BASE. PRESENTAGE CHANGE TO " + m_Strrf_tran_base_falt_fee + "<br>";

                }

                double m_StrOldrf_fixed_rate_amount_dub = Double.parseDouble(m_StrOldrf_fixed_rate_amount);
                double m_Strrf_fixed_rate_amount_dub = Double.parseDouble(m_Strrf_fixed_rate_amount);

                if (!(m_StrOldrf_fixed_rate_amount_dub == m_Strrf_fixed_rate_amount_dub)) {
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

                double m_StrOldcw_tran_base_falt_fee_dub = Double.parseDouble(m_StrOldcw_tran_base_falt_fee);
                double m_Strcw_tran_base_falt_fee_dub = Double.parseDouble(m_Strcw_tran_base_falt_fee);

                if (!(m_StrOldcw_tran_base_falt_fee_dub == m_Strcw_tran_base_falt_fee_dub)) {
                    m_condition = m_condition + "cw_tran_base_falt_fee='" + m_Strcw_tran_base_falt_fee + "',";

                    i++;
                    m_change = m_change + i + ")" + m_StrOldcw_tran_base_falt_fee + "CW COMM. CHG. FLAT FEE CHANGE TO " + m_Strcw_tran_base_falt_fee + "<br>";

                }

                double m_StrOldcw_tran_base_from_tran_dub = Double.parseDouble(m_StrOldcw_tran_base_from_tran);
                double m_Strcw_tran_base_from_tran_dub = Double.parseDouble(m_Strcw_tran_base_from_tran);

                if (!(m_StrOldcw_tran_base_from_tran_dub == m_Strcw_tran_base_from_tran_dub)) {
                    m_condition = m_condition + "cw_tran_base_from_tran='" + m_Strcw_tran_base_from_tran + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcw_tran_base_from_tran + "CW COMM. CHG. PRESENTAGE FEE CHANGE TO " + m_Strcw_tran_base_from_tran + "<br>";

                }

                double m_StrOldcw_fixed_rate_amount_dub = Double.parseDouble(m_StrOldcw_fixed_rate_amount);
                double m_Strcw_fixed_rate_amount_dub = Double.parseDouble(m_Strcw_fixed_rate_amount);

                if (!(m_StrOldcw_fixed_rate_amount_dub == m_Strcw_fixed_rate_amount_dub)) {
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
            success = "1100";
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in saving relationship estabilishment data, Exception" + e);
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_rs2 != null) {
                    _rs2.close();
                }
                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_stmnt2 != null) {
                    _stmnt2.close();
                }
                if (_currentCon != null) {
                    _currentCon.close();
                }
            } catch (Exception e) {
            }
        }
        return success;
    }

    public String saveCustomerProdMapData(JSONObject prm_obj) {
        String success = "1000";
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

        String m_Strprod_rel_status_reason = "";
        String m_StrOldprod_rel_status_reason = "";

        //rec finance------------
        String m_Strrec_finance_curr = "";
        String m_StrOldrec_finance_curr = "";

        String m_Strrec_finance_limit = "";
        String m_StrOldrec_finance_limit = "";

        //addedd for the RF Temporary Limit -- CFU-BRD-4 -- Janaka_5977
        String m_Strrf_temporary_limits = "";
        String m_StrOldrf_temporary_limits = "";

        String m_Strrf_buyer_accs = "";
        String m_StrOldrf_buyer_accs = "";

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

        String m_Strrec_finance_balance_transfer = "";
        String m_StrOldrec_finance_balance_transfer = "";

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

        //addedd for the CW Temporary Limit -- CFU-BRD-4 -- Janaka_5977
        String m_Strcw_temporary_limits = "";
        String m_StrOldcw_temporary_limits = "";

        String m_Strsl_has_byr_otstaning = "";
        String m_StrOldsl_has_byr_otstaning = "";

        String m_Strsl_has_byr_tenor = "";
        String m_StrOldsl_has_byr_tenor = "";

        String m_Strchq_wh_dr_to_cr_spe_narr = "";
        String m_StrOldchq_wh_dr_to_cr_spe_narr = "";

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
            m_Strprod_rel_status_reason = prm_obj.getString("statusReason");

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

            //addedd for the RF Temporary Limit -- CFU-BRD-4 -- Janaka_5977
            m_Strrf_temporary_limits = prm_obj.getString("rf_temporary_limits");
            String[] m_Strrf_temporary_limits_arr = m_Strrf_temporary_limits.split(",");

            m_Strrf_buyer_accs = prm_obj.getString("rf_buyer_accs");
            String[] m_Strrf_buyer_accs_arr = m_Strrf_buyer_accs.split(",");

            m_Strrec_finance_Outstanding = prm_obj.getString("rec_finance_Outstanding");
            if (m_Strrec_finance_Outstanding.equals("")) {
                m_Strrec_finance_Outstanding = "0.00";
            }
            m_Strrec_finance_tenor = prm_obj.getString("rec_finance_tenor");
            m_Strrec_finance_inerest_rate = prm_obj.getString("rec_finance_inerest_rate");
            if (m_Strrec_finance_inerest_rate.equals("")) {
                m_Strrec_finance_inerest_rate = "0.00";
            }
            m_Strrec_finance_financing = prm_obj.getString("rec_finance_financing");
            m_Strrec_finance_bulk_credit = prm_obj.getString("rec_finance_bulk_credit");
            m_Strrec_finance_balance_transfer = prm_obj.getString("rec_finance_balance_transfer");

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

            //addedd for the CW Temporary Limit -- CFU-BRD-4 -- Janaka_5977
            m_Strcw_temporary_limits = prm_obj.getString("cw_temporary_limits");

            String[] m_Strcw_temporary_limits_arr = m_Strcw_temporary_limits.split(",");

            m_Strsl_has_byr_otstaning = prm_obj.getString("sl_has_byr_otstaning");
            if (m_Strsl_has_byr_otstaning.equals("")) {
                m_Strsl_has_byr_otstaning = "0.00";
            }

            m_Strsl_has_byr_tenor = prm_obj.getString("sl_has_byr_tenor");

            m_Strchq_wh_dr_to_cr_spe_narr = prm_obj.getString("chq_wh_dr_to_cr_spe_narr");

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

            if (m_Strididndb_cust_prod_map.equals("") && (!m_Stridndb_customer_define.equals("")) && m_Strcust_as_seller.equals("ACTIVE")) {
                m_strQry = "select * from ndb_cust_prod_map where idndb_customer_define='" + m_Stridndb_customer_define + "' and prod_relationship_key_seller='" + m_Strcust_as_seller + "' and prod_relationship_status='ACTIVE'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1101";
                    throw new Exception("Customer All Ready Exist as seller");
                }

            }
            if (m_Strididndb_cust_prod_map.equals("") && (!m_Stridndb_customer_define.equals("")) && m_Strcust_as_buyer.equals("ACTIVE")) {
                m_strQry = "select * from ndb_cust_prod_map where idndb_customer_define='" + m_Stridndb_customer_define + "' and prod_relationship_key_buyer='" + m_Strcust_as_buyer + "' and prod_relationship_status='ACTIVE'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1101";
                    throw new Exception("Customer All Ready Exist as buyer");
                }

            }

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
                m_StrOldprod_rel_status_reason = _rs.getString("prod_relationship_status_reason");

                m_ardy_exist_data = false;
            }

            if (m_Strcust_as_buyer.equals("ACTIVE") && !m_ardy_exist_data) {
                m_strQry = "SELECT * FROM buyer_accs_details where idndb_cust_prod_map='" + m_Strididndb_cust_prod_map + "' order by idbuyer_accs_details";
                _rs = _stmnt.executeQuery(m_strQry);
                while (_rs.next()) {
                    String recordNo = _rs.getString("buyer_accs_record_no");
                    String buyer_accs_bank_code = _rs.getString("buyer_accs_bank_code");
                    String buyer_accs_branch_code = _rs.getString("buyer_accs_branch_code");
                    String buyer_accs_acc_no = _rs.getString("buyer_accs_acc_no");
                    m_StrOldrf_buyer_accs += recordNo + "##" + buyer_accs_bank_code + "##" + buyer_accs_branch_code + "##" + buyer_accs_acc_no + ",";
                }
                if (!m_StrOldrf_buyer_accs.equals("")) {
                    m_StrOldrf_buyer_accs = m_StrOldrf_buyer_accs.substring(0, m_StrOldrf_buyer_accs.length() - 1);
                }
            }

            String max_idndb_cust_prod_map = "";
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
                        + ",prod_relationship_status_reason"
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
                        + "now(),"
                        + "'" + m_Strprod_rel_status_reason + "')";
                log.info("new cust prod maap mysql quiery m_strQry:" + m_strQry);
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                m_strQry = "select MAX(idndb_cust_prod_map) as idndb_cust_prod_map from ndb_cust_prod_map";
                _rs2 = _stmnt2.executeQuery(m_strQry);
                if (_rs2.next()) {
                    max_idndb_cust_prod_map = _rs2.getString("idndb_cust_prod_map");
                }

                if (m_Strcust_as_buyer.equals("ACTIVE")) {
                    m_change = " " + i + " ) CUSTOMER ASSIGNED AS BUYER </br>";
                    i++;
                }
                if (m_Strcust_as_seller.equals("ACTIVE")) {
                    m_change = " " + i + " ) CUSTOMER ASSIGNED AS SELLER </br>";
                    i++;
                }

                if (m_Strcust_as_buyer.equals("ACTIVE") && !m_Strrf_buyer_accs.isEmpty()) {

                    for (String buyer_accs_record : m_Strrf_buyer_accs_arr) {
                        if (!buyer_accs_record.equals("")) {

                            String[] rf_buyer_accs_arr = buyer_accs_record.split("##");
                            m_strQry = "insert into "
                                    + "buyer_accs_details ("
                                    + "buyer_accs_record_no,"
                                    + "buyer_accs_bank_code,"
                                    + "buyer_accs_branch_code,"
                                    + "buyer_accs_acc_no,"
                                    + "buyer_accs_creat_user,"
                                    + "buyer_accs_creat_date,"
                                    + "idndb_cust_prod_map)"
                                    + " values ("
                                    + "'" + rf_buyer_accs_arr[0] + "',"
                                    + "'" + rf_buyer_accs_arr[1] + "',"
                                    + "'" + rf_buyer_accs_arr[2] + "',"
                                    + "'" + rf_buyer_accs_arr[3] + "',"
                                    + "'" + m_str_user_id + "',"
                                    + "now(),"
                                    + "'" + max_idndb_cust_prod_map + "')";

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert buyer_accs_details");
                            }
                        }
                    }

                    String strNewBuyrAccDtls = m_Strrf_buyer_accs.equals("") ? m_Strrf_buyer_accs : m_Strrf_buyer_accs.replace("##", ":");
                    m_change = m_change + "" + i + " ) BUYER ACCOUNT DETAILS INCLUDED.  " + strNewBuyrAccDtls + "<br>";
                    i++;
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
                        + "'" + m_change + "',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            } else {

                m_strQry = "select * from ndb_seller_has_buyers where  sl_has_byr_auth='UN-AUTHORIZED' and (idndb_customer_define_seller='" + m_Strididndb_cust_prod_map + "' or idndb_customer_define_buyer='" + m_Strididndb_cust_prod_map + "')";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1104";
                    throw new Exception("Cannot amend this data");
                }

//                m_strQry = "select * from ndb_seller_has_buyers where idndb_customer_define_seller='" + m_Strididndb_cust_prod_map + "' or idndb_customer_define_buyer='" + m_Strididndb_cust_prod_map + "'";
//                _rs = _stmnt.executeQuery(m_strQry);
//                while (_rs.next()) {
//                    String m_strQry2 = "select * from ndb_pdc_txn_master where  pdc_chq_status_auth='UN-AUTHORIZED' and (idndb_customer_define_buyer_id='" + _rs.getString("idndb_seller_has_buyers") + "' or idndb_customer_define_seller_id='" + m_Strididndb_cust_prod_map + "')";
//                    _rs2 = _stmnt2.executeQuery(m_strQry2);
//                    while (_rs2.next()) {
//                        success = "1105";
//                        throw new Exception("Cannot amend this data");
//                    }
//
//                }
                m_strQry = "select \n"
                        + "nptm.idndb_pdc_txn_master\n"
                        + "from\n"
                        + "ndb_pdc_txn_master nptm,\n"
                        + "ndb_seller_has_buyers nshb\n"
                        + "where \n"
                        + "nptm.idndb_customer_define_buyer_id= nshb.idndb_seller_has_buyers and \n"
                        + "nptm.pdc_chq_status_auth='UN-AUTHORIZED' and \n"
                        + "(nptm.idndb_customer_define_buyer_id=nshb.idndb_seller_has_buyers or nptm.idndb_customer_define_seller_id='" + m_Strididndb_cust_prod_map + "') and\n"
                        + "(nshb.idndb_customer_define_seller = '" + m_Strididndb_cust_prod_map + "' or\n"
                        + "nshb.idndb_customer_define_buyer = '" + m_Strididndb_cust_prod_map + "')";
                _rs2 = _stmnt.executeQuery(m_strQry);
                if (_rs2.next()) {
                    success = "1105";
                    throw new Exception("Cannot amend this data");
                }

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

                if (!m_StrOldprod_rel_status_reason.equals(m_Strprod_rel_status_reason)) {
                    i++;
                    m_condition = m_condition + "prod_relationship_status_reason='" + m_Strprod_rel_status_reason + "',";
                    m_change = m_change + i + ")" + m_StrOldprod_rel_status_reason + " STATUS REASON CHANGE TO " + m_Strprod_rel_status_reason + "<br>";
                }

                if (!m_condition.equals("")) {
                    m_condition = m_condition + "prod_relationship_auth='UN-AUTHORIZED',";
                }

                if (m_Strcust_as_buyer.equals("ACTIVE")) {

                    boolean buyerAccDetailsUpdateRequired = false;
                    if (!m_StrOldrf_buyer_accs.equals(m_Strrf_buyer_accs)) {
                        buyerAccDetailsUpdateRequired = true;
                        i++;
                        String strOldBuyrAccDtls = m_StrOldrf_buyer_accs.equals("") ? m_StrOldrf_buyer_accs : m_StrOldrf_buyer_accs.replace("##", ":");
                        String strNewBuyrAccDtls = m_Strrf_buyer_accs.equals("") ? m_Strrf_buyer_accs : m_Strrf_buyer_accs.replace("##", ":");
                        m_change = m_change + i + ")" + strOldBuyrAccDtls + " BUYER ACCOUNT DETAILS CHANGE TO " + strNewBuyrAccDtls + "<br>";
                        m_condition = m_condition + "prod_relationship_auth='UN-AUTHORIZED',";
                    }

                    if (buyerAccDetailsUpdateRequired) {
                        m_strQry = "delete from buyer_accs_details where idndb_cust_prod_map='" + m_Strididndb_cust_prod_map + "' and idbuyer_accs_details <>0";

                        _stmnt.executeUpdate(m_strQry);
                        for (String buyer_accs_record : m_Strrf_buyer_accs_arr) {
                            if (!buyer_accs_record.equals("")) {
                                String[] buyer_accs_record_arr = buyer_accs_record.split("##");
                                m_strQry = "insert into "
                                        + "buyer_accs_details ("
                                        + "buyer_accs_record_no,"
                                        + "buyer_accs_bank_code,"
                                        + "buyer_accs_branch_code,"
                                        + "buyer_accs_acc_no,"
                                        + "buyer_accs_creat_user,"
                                        + "buyer_accs_creat_date,"
                                        + "idndb_cust_prod_map)"
                                        + " values ("
                                        + "'" + buyer_accs_record_arr[0] + "',"
                                        + "'" + buyer_accs_record_arr[1] + "',"
                                        + "'" + buyer_accs_record_arr[2] + "',"
                                        + "'" + buyer_accs_record_arr[3] + "',"
                                        + "'" + m_str_user_id + "',"
                                        + "now(),"
                                        + "'" + m_Strididndb_cust_prod_map + "')";

                                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                    throw new Exception("Error Occured in insert ndb_rec_fin_temp_limits");
                                }
                            }
                        }

                    }
                }

                m_strQry = "update ndb_cust_prod_map set " + m_condition + " "
                        + "prod_relationship_mod_by='" + m_str_user_id + "',"
                        + "prod_relationship_mod_date=now()"
                        + " where idndb_cust_prod_map='" + m_Strididndb_cust_prod_map + "'";

                try {
                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }
                } catch (Exception e) {
                    e.printStackTrace();

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
                m_strQry = "select * from ndb_rec_fin where idndb_cust_prod_map='" + m_Strididndb_cust_prod_mapNeedToSave + "' and idndb_customer_define='" + m_Stridndb_customer_define + "'";
                _rs = _stmnt.executeQuery(m_strQry);
                int idndb_rec_fin = 0;
                if (_rs.next()) {
                    idndb_rec_fin = _rs.getInt("idndb_rec_fin");
                    m_StrOldidndb_customer_define = _rs.getString("idndb_customer_define");
                    m_StrOldrec_finance_curr = _rs.getString("rec_finance_curr");
                    m_StrOldrec_finance_limit = _rs.getString("rec_finance_limit");

                    m_StrOldrec_finance_Outstanding = _rs.getString("rec_finance_Outstanding");
                    m_StrOldrec_finance_tenor = _rs.getString("rec_finance_tenor");
                    m_StrOldrec_finance_inerest_rate = _rs.getString("rec_finance_inerest_rate");
                    m_StrOldrec_finance_financing = _rs.getString("rec_finance_financing");
                    m_StrOldrec_finance_bulk_credit = _rs.getString("rec_finance_bulk_credit");
                    m_StrOldrec_finance_balance_transfer = _rs.getString("rec_finance_balance_transfer");
                    m_StrOldrec_finance_erly_wdr_chg = _rs.getString("rec_finance_erly_wdr_chg");
                    m_StrOldrec_finance_vale_dte_extr_chg = _rs.getString("rec_finance_vale_dte_extr_chg");
                    m_StrOldrec_finance_erly_stlemnt_chg = _rs.getString("rec_finance_erly_stlemnt_chg");
                    m_StrOldrec_finance_status = _rs.getString("rec_finance_status");

                    m_ardy_exist_data = false;
                }

                if (idndb_rec_fin != 0) {
                    m_strQry = "SELECT * FROM ndb_rec_fin_temp_limits where idndb_rec_fin='" + idndb_rec_fin + "' order by idndb_rec_fin_temp_limits";
                    _rs = _stmnt.executeQuery(m_strQry);
                    while (_rs.next()) {
                        String recordNo = _rs.getString("ndb_rec_fin_temp_limit_record_no");
                        String tempValue = _rs.getString("ndb_rec_fin_temp_value");
                        String expDate = _rs.getString("ndb_rec_fin_temp_exp_date");
                        m_StrOldrf_temporary_limits += recordNo + "##" + tempValue + "##" + expDate + ",";
                    }
                    if (!m_StrOldrf_temporary_limits.equals("")) {
                        m_StrOldrf_temporary_limits = m_StrOldrf_temporary_limits.substring(0, m_StrOldrf_temporary_limits.length() - 1);
                    }
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
                            + ",rec_finance_balance_transfer"
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
                            + "'" + m_Strrec_finance_balance_transfer + "',"
                            + "'" + m_Strrec_finance_erly_wdr_chg + "',"
                            + "'" + m_Strrec_finance_vale_dte_extr_chg + "',"
                            + "'" + m_Strrec_finance_erly_stlemnt_chg + "',"
                            + "'" + m_Strrec_finance_status + "',"
                            + "'" + m_str_user_id + "',"
                            + "now(),"
                            + "'" + m_str_user_id + "',"
                            + "now())";
                    try {
                        log.info("New RF Recived m_strQry : " + m_strQry);
                        int idndb_rec_fin_last = 0;
                        if (_stmnt.executeUpdate(m_strQry, Statement.RETURN_GENERATED_KEYS) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        } else {
                            _rs = _stmnt.getGeneratedKeys();
                            if (_rs.next()) {
                                idndb_rec_fin_last = _rs.getInt(1);
                            }
                        }

                        //---------------------------------------------------------------------------------------//
                        if (idndb_rec_fin_last != 0) {

                            for (String rf_temp_limit_record : m_Strrf_temporary_limits_arr) {
                                if (!rf_temp_limit_record.equals("")) {
                                    String[] rf_temp_limit_arr = rf_temp_limit_record.split("##");
                                    m_strQry = "insert into ndb_rec_fin_temp_limits (ndb_rec_fin_temp_limit_record_no,ndb_rec_fin_temp_value,ndb_rec_fin_temp_exp_date,idndb_rec_fin)"
                                            + " values ("
                                            + "'" + rf_temp_limit_arr[0] + "'," //record_no
                                            + "'" + rf_temp_limit_arr[1] + "'," //limit value
                                            + "'" + rf_temp_limit_arr[2] + "'," //exp date
                                            + "'" + idndb_rec_fin_last + "')";

                                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                        throw new Exception("Error Occured in insert ndb_rec_fin_temp_limits");
                                    }
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //----------------------------------------------------------------------------------------//
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

                    m_strQry = "select SUM(shb_facty_det_crd_loam_limit) as SUMOFSUBLIMITS from ndb_seller_has_buyers where idndb_customer_define_seller='" + m_Strididndb_cust_prod_mapNeedToSave + "' and sl_has_byr_prorm_type='RF' and sl_has_byr_status='ACTIVE'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    double available_sub_limt = 0.00;
                    if (_rs.next()) {
                        if (!(_rs.getString("SUMOFSUBLIMITS") == null)) {

                            available_sub_limt = Double.parseDouble(_rs.getString("SUMOFSUBLIMITS"));

                        }

                    }
//                    if (Double.parseDouble(m_Strrec_finance_limit) < available_sub_limt) {
//                        success = "1106";
//                        throw new Exception("Cannot amend this data");
//                    }

                    String m_condition = "";

                    if (!m_StrOldidndb_customer_define.equals(m_Stridndb_customer_define)) {
                        m_condition = "idndb_customer_define='" + m_Stridndb_customer_define + "',";

                    }

                    if (!m_StrOldrec_finance_curr.equals(m_Strrec_finance_curr)) {
                        m_condition = m_condition + "rec_finance_curr='" + m_Strrec_finance_curr + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_curr + " RF CUR. CHANGE TO " + m_Strrec_finance_curr + "<br>";

                    }
                    double m_StrOldrec_finance_limit_dub = Double.parseDouble(m_StrOldrec_finance_limit);
                    double m_Strrec_finance_limit_dub = Double.parseDouble(m_Strrec_finance_limit);

                    if (!(m_StrOldrec_finance_limit_dub == m_Strrec_finance_limit_dub)) {
                        m_condition = m_condition + "rec_finance_limit='" + m_Strrec_finance_limit + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_limit + " RF LIMIT CHANGE TO " + m_Strrec_finance_limit + "<br>";

                    }

                    //addedd for the RF Temporary Limit Exp Date-- CFU-BRD-4 -- Janaka_5977
                    boolean rfTempLimitsUpdateRequired = false;
                    if (!m_StrOldrf_temporary_limits.equals(m_Strrf_temporary_limits)) {
                        rfTempLimitsUpdateRequired = true;
                        i++;
                        String strOldTempLimits = m_StrOldrf_temporary_limits.equals("") ? m_StrOldrf_temporary_limits : m_StrOldrf_temporary_limits.replace("##", ":");
                        String strNewTempLimits = m_Strrf_temporary_limits.equals("") ? m_Strrf_temporary_limits : m_Strrf_temporary_limits.replace("##", ":");
                        m_change = m_change + i + ")" + strOldTempLimits + " RF TEMPORARY LIMITS CHANGE TO " + strNewTempLimits + "<br>";

                    }

//                    double m_StrOldrec_finance_Outstanding_dub = Double.parseDouble(m_StrOldrec_finance_Outstanding);
//                    double m_Strrec_finance_Outstandingdub = Double.parseDouble(m_Strrec_finance_Outstanding);
//
//                    if (!(m_StrOldrec_finance_Outstanding_dub == m_Strrec_finance_Outstandingdub)) {
//                        m_condition = m_condition + "rec_finance_Outstanding='" + m_Strrec_finance_Outstanding + "',";
//                        i++;
//                        m_change = m_change + i + ")" + m_StrOldrec_finance_Outstanding + " RF OUTSTANDING CHANGE TO " + m_Strrec_finance_Outstanding + "<br>";
//
//                    }
                    if (!m_StrOldrec_finance_tenor.equals(m_Strrec_finance_tenor)) {
                        m_condition = m_condition + "rec_finance_tenor='" + m_Strrec_finance_tenor + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_tenor + " RF TENOR CHANGE TO " + m_Strrec_finance_tenor + "<br>";

                    }
//                    if (!m_StrOldrec_finance_inerest_rate.equals(m_Strrec_finance_inerest_rate)) {
//                        m_condition = m_condition + "rec_finance_inerest_rate='" + m_Strrec_finance_inerest_rate + "',";
//                        i++;
//                        m_change = m_change + i + ")" + m_StrOldrec_finance_inerest_rate + " RF INTEREST RATE CHANGE TO " + m_Strrec_finance_inerest_rate + "<br>";
//
//                    }
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

                    if (!m_StrOldrec_finance_balance_transfer.equals(m_Strrec_finance_balance_transfer)) {
                        m_condition = m_condition + "rec_finance_balance_transfer='" + m_Strrec_finance_balance_transfer + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_balance_transfer + " RF BALANCE TRANSFER OPT. CHANGE TO " + m_Strrec_finance_balance_transfer + "<br>";

                    }

                    double m_StrOldrec_finance_erly_wdr_chg_dub = Double.parseDouble(m_StrOldrec_finance_erly_wdr_chg);
                    double m_Strrec_finance_erly_wdr_chgdub = Double.parseDouble(m_Strrec_finance_erly_wdr_chg);

                    if (!(m_StrOldrec_finance_erly_wdr_chg_dub == m_Strrec_finance_erly_wdr_chgdub)) {
                        m_condition = m_condition + "rec_finance_erly_wdr_chg='" + m_Strrec_finance_erly_wdr_chg + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_erly_wdr_chg + " RF ERLY WDR CHG. CHANGE TO " + m_Strrec_finance_erly_wdr_chg + "<br>";

                    }

                    double m_StrOldrec_finance_vale_dte_extr_chg_dub = Double.parseDouble(m_StrOldrec_finance_vale_dte_extr_chg);
                    double m_Strrec_finance_vale_dte_extr_chg_dub = Double.parseDouble(m_Strrec_finance_vale_dte_extr_chg);

                    if (!(m_StrOldrec_finance_vale_dte_extr_chg_dub == m_Strrec_finance_vale_dte_extr_chg_dub)) {
                        m_condition = m_condition + "rec_finance_vale_dte_extr_chg='" + m_Strrec_finance_vale_dte_extr_chg + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrec_finance_vale_dte_extr_chg + " RF VALDT. EXT CHG. CHANGE TO " + m_Strrec_finance_vale_dte_extr_chg + "<br>";

                    }

                    double m_StrOldrec_finance_erly_stlemnt_chg_dub = Double.parseDouble(m_StrOldrec_finance_erly_stlemnt_chg);
                    double m_Strrec_finance_erly_stlemnt_chg_dub = Double.parseDouble(m_Strrec_finance_erly_stlemnt_chg);

                    if (!(m_StrOldrec_finance_erly_stlemnt_chg_dub == m_Strrec_finance_erly_stlemnt_chg_dub)) {
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

                    if (rfTempLimitsUpdateRequired) {
                        m_strQry = "delete from ndb_rec_fin_temp_limits where idndb_rec_fin='" + idndb_rec_fin + "' and idndb_rec_fin_temp_limits <>0";

                        _stmnt.executeUpdate(m_strQry);
                        for (String rf_temp_limit_record : m_Strrf_temporary_limits_arr) {
                            if (!rf_temp_limit_record.equals("")) {
                                String[] rf_temp_limit_arr = rf_temp_limit_record.split("##");
                                m_strQry = "insert into ndb_rec_fin_temp_limits (ndb_rec_fin_temp_limit_record_no,ndb_rec_fin_temp_value,ndb_rec_fin_temp_exp_date,idndb_rec_fin)"
                                        + " values ("
                                        + "'" + rf_temp_limit_arr[0] + "'," //record_no
                                        + "'" + rf_temp_limit_arr[1] + "'," //limit value
                                        + "'" + rf_temp_limit_arr[2] + "'," //exp date
                                        + "'" + idndb_rec_fin + "')";

                                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                    throw new Exception("Error Occured in insert ndb_rec_fin_temp_limits");
                                }
                            }
                        }

                    }

                    m_strQry = "update ndb_cust_prod_map set prod_relationship_auth='UN-AUTHORIZED', "
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
                int idndb_chq_wh = 0;
                if (_rs.next()) {
                    idndb_chq_wh = _rs.getInt("idndb_chq_wh");
                    m_StrOldidndb_customer_define = _rs.getString("idndb_customer_define");
                    m_StrOldchq_wh_limit = _rs.getString("chq_wh_limit");
                    m_StrOldsl_has_byr_otstaning = _rs.getString("sl_has_byr_otstaning");
                    m_StrOldsl_has_byr_tenor = _rs.getString("sl_has_byr_tenor");

                    m_StrOldchq_wh_dr_to_cr_spe_narr = _rs.getString("chq_wh_dr_to_cr_spe_narr");

                    m_StrOldchq_wh_erly_wdr_chg = _rs.getString("chq_wh_erly_wdr_chg");
                    m_StrOldchq_wh_vale_dte_extr_chg = _rs.getString("chq_wh_vale_dte_extr_chg");
                    m_StrOldchq_wh_erly_stlemnt_chg = _rs.getString("chq_wh_erly_stlemnt_chg");
                    m_StrOldchq_status = _rs.getString("chq_status");

                    m_ardy_exist_data = false;
                }

                if (idndb_chq_wh != 0) {
                    m_strQry = "SELECT * FROM ndb_chq_wh_temp_limits where idndb_chq_wh='" + idndb_chq_wh + "' order by idndb_chq_wh_temp_limits";
                    _rs = _stmnt.executeQuery(m_strQry);
                    while (_rs.next()) {
                        String recordNo = _rs.getString("ndb_chq_wh_temp_limit_record_no");
                        String tempValue = _rs.getString("ndb_chq_wh_temp_value");
                        String expDate = _rs.getString("ndb_chq_wh_temp_exp_date");
                        m_StrOldcw_temporary_limits += recordNo + "##" + tempValue + "##" + expDate + ",";
                    }
                    if (!m_StrOldcw_temporary_limits.equals("")) {
                        m_StrOldcw_temporary_limits = m_StrOldcw_temporary_limits.substring(0, m_StrOldcw_temporary_limits.length() - 1);
                    }
                }

                if (m_ardy_exist_data) {
                    i++;
                    m_strQry = "insert into ndb_chq_wh (idndb_customer_define,"
                            + "idndb_cust_prod_map"
                            + ",chq_wh_limit"
                            + ",sl_has_byr_otstaning"
                            + ",sl_has_byr_tenor"
                            + ",chq_wh_dr_to_cr_spe_narr"
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
                            + "'" + m_Strchq_wh_dr_to_cr_spe_narr + "',"
                            + "'" + m_Strchq_wh_erly_wdr_chg + "',"
                            + "'" + m_Strchq_wh_vale_dte_extr_chg + "',"
                            + "'" + m_Strchq_wh_erly_stlemnt_chg + "',"
                            + "'" + m_Strmchq_status + "',"
                            + "'" + m_str_user_id + "',"
                            + "now(),"
                            + "'" + m_str_user_id + "',"
                            + "now())";

                    log.info("new cw received m_strQry : " + m_strQry);
                    int idndb_chq_wh_last = 0;
                    if (_stmnt.executeUpdate(m_strQry, Statement.RETURN_GENERATED_KEYS) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    } else {
                        _rs = _stmnt.getGeneratedKeys();
                        if (_rs.next()) {
                            idndb_chq_wh_last = _rs.getInt(1);
                        }
                    }

                    //---------------------------------------------------------------------------------------//
                    //addedd for the CW Temporary Limit,Temporary Limit Exp Date -- CFU-BRD-4 -- Janaka_5977
                    if (idndb_chq_wh_last != 0) {

                        for (String cw_temp_limit_record : m_Strcw_temporary_limits_arr) {
                            if (!cw_temp_limit_record.equals("")) {
                                String[] cw_temp_limit_arr = cw_temp_limit_record.split("##");
                                m_strQry = "insert into ndb_chq_wh_temp_limits (ndb_chq_wh_temp_limit_record_no,ndb_chq_wh_temp_value,ndb_chq_wh_temp_exp_date,idndb_chq_wh)"
                                        + " values ("
                                        + "'" + cw_temp_limit_arr[0] + "'," //record_no
                                        + "'" + cw_temp_limit_arr[1] + "'," //limit value
                                        + "'" + cw_temp_limit_arr[2] + "'," //exp date
                                        + "'" + idndb_chq_wh_last + "')";

                                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                    throw new Exception("Error Occured in insert ndb_chq_wh_temp_limits");
                                }
                            }
                        }

                    }

                    //----------------------------------------------------------------------------------------//
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

                    m_strQry = "select SUM(sl_has_byr_warehs_limit) as SUMOFSUBLIMITS from ndb_seller_has_buyers where idndb_customer_define_seller='" + m_Strididndb_cust_prod_mapNeedToSave + "' and sl_has_byr_prorm_type='CW' and sl_has_byr_status='ACTIVE'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    double available_sub_limt = 0.00;
                    if (_rs.next()) {
                        if (_rs.getString("SUMOFSUBLIMITS") == null) {
                            available_sub_limt = 0.00;
                        } else {
                            available_sub_limt = Double.parseDouble(_rs.getString("SUMOFSUBLIMITS"));
                        }

                    }
                    if (Double.parseDouble(m_Strchq_wh_limit) < available_sub_limt) {
                        success = "1106";
                        throw new Exception("Cannot amend this data");
                    }

                    String m_condition = "";

                    if (!m_StrOldidndb_customer_define.equals(m_Stridndb_customer_define)) {
                        m_condition = "idndb_customer_define='" + m_Stridndb_customer_define + "',";
                    }

                    double m_StrOldchq_wh_limit_dub = Double.parseDouble(m_StrOldchq_wh_limit);
                    double m_Strchq_wh_limit_dub = Double.parseDouble(m_Strchq_wh_limit);

                    if (!(m_StrOldchq_wh_limit_dub == m_Strchq_wh_limit_dub)) {
                        m_condition = m_condition + "chq_wh_limit='" + m_Strchq_wh_limit + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldchq_wh_limit + " CW CLIMIT CHANGE TO " + m_Strchq_wh_limit + "<br>";

                    }

                    //addedd for the CW Temporary Limit Exp Date-- CFU-BRD-4 -- Janaka_5977
                    boolean cwTempLimitsUpdateRequired = false;
                    if (!m_StrOldcw_temporary_limits.equals(m_Strcw_temporary_limits)) {
                        cwTempLimitsUpdateRequired = true;
                        i++;
                        String strOldTempLimits = m_StrOldcw_temporary_limits.equals("") ? m_StrOldcw_temporary_limits : m_StrOldcw_temporary_limits.replace("##", ":");
                        String strNewTempLimits = m_Strcw_temporary_limits.equals("") ? m_Strcw_temporary_limits : m_Strcw_temporary_limits.replace("##", ":");
                        m_change = m_change + i + ")" + strOldTempLimits + " CW TEMPORARY LIMITS CHANGE TO " + strNewTempLimits + "<br>";

                    }

//                    double m_StrOldsl_has_byr_otstaning_dub = Double.parseDouble(m_StrOldsl_has_byr_otstaning);
//                    double m_Strsl_has_byr_otstaning_dub = Double.parseDouble(m_Strsl_has_byr_otstaning);
//
//                    if (!(m_StrOldsl_has_byr_otstaning_dub == m_Strsl_has_byr_otstaning_dub)) {
//                        m_condition = m_condition + "sl_has_byr_otstaning='" + m_Strsl_has_byr_otstaning + "',";
//                        i++;
//                        m_change = m_change + i + ")" + m_StrOldsl_has_byr_otstaning + " CW OUTSTANDING CHANGE TO " + m_Strsl_has_byr_otstaning + "<br>";
//
//                    }
                    if (!m_StrOldsl_has_byr_tenor.equals(m_Strsl_has_byr_tenor)) {
                        m_condition = m_condition + "sl_has_byr_tenor='" + m_Strsl_has_byr_tenor + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldsl_has_byr_tenor + " CW TENOR CHANGE TO " + m_Strsl_has_byr_tenor + "<br>";

                    }

                    if (!m_StrOldchq_wh_dr_to_cr_spe_narr.equals(m_Strchq_wh_dr_to_cr_spe_narr)) {
                        m_condition = m_condition + "chq_wh_dr_to_cr_spe_narr='" + m_Strchq_wh_dr_to_cr_spe_narr + "',";
                        i++;
                        m_change = m_change + i + ")" + ((m_StrOldchq_wh_dr_to_cr_spe_narr.equals("1")) ? "ACTIVE" : "DE-ACTIVE") + " CW DR/CR TRANSFER (SPECIAL NARRATION) " + ((m_Strchq_wh_dr_to_cr_spe_narr.equals("1")) ? "ACTIVE" : "DE-ACTIVE") + "<br>";

                    }

                    double m_StrOldchq_wh_erly_wdr_chg_dub = Double.parseDouble(m_StrOldchq_wh_erly_wdr_chg);
                    double m_Strchq_wh_erly_wdr_chg_dub = Double.parseDouble(m_Strchq_wh_erly_wdr_chg);

                    if (!(m_StrOldchq_wh_erly_wdr_chg_dub == m_Strchq_wh_erly_wdr_chg_dub)) {
                        m_condition = m_condition + "chq_wh_erly_wdr_chg='" + m_Strchq_wh_erly_wdr_chg + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldchq_wh_erly_wdr_chg + " CW ERLY. WDR. CHG. CHANGE TO " + m_Strchq_wh_erly_wdr_chg + "<br>";

                    }

                    double m_StrOldchq_wh_vale_dte_extr_chg_dub = Double.parseDouble(m_StrOldchq_wh_vale_dte_extr_chg);
                    double m_Strchq_wh_vale_dte_extr_chg_dub = Double.parseDouble(m_Strchq_wh_vale_dte_extr_chg);

                    if (!(m_StrOldchq_wh_vale_dte_extr_chg_dub == m_Strchq_wh_vale_dte_extr_chg_dub)) {
                        m_condition = m_condition + "chq_wh_vale_dte_extr_chg='" + m_Strchq_wh_vale_dte_extr_chg + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldchq_wh_vale_dte_extr_chg + " CW VALDTE. EXT. CHG. CHANGE TO " + m_Strchq_wh_vale_dte_extr_chg + "<br>";

                    }
                    double m_StrOldchq_wh_erly_stlemnt_chg_dub = Double.parseDouble(m_StrOldchq_wh_erly_stlemnt_chg);
                    double m_Strchq_wh_erly_stlemnt_chg_dub = Double.parseDouble(m_Strchq_wh_erly_stlemnt_chg);

                    if (!(m_StrOldchq_wh_erly_stlemnt_chg_dub == m_Strchq_wh_erly_stlemnt_chg_dub)) {
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

                    if (cwTempLimitsUpdateRequired) {
                        m_strQry = "delete from ndb_chq_wh_temp_limits where idndb_chq_wh='" + idndb_chq_wh + "' and idndb_chq_wh_temp_limits <>0";

                        _stmnt.executeUpdate(m_strQry);
                        for (String cw_temp_limit_record : m_Strcw_temporary_limits_arr) {
                            if (!cw_temp_limit_record.equals("")) {
                                String[] cw_temp_limit_arr = cw_temp_limit_record.split("##");
                                m_strQry = "insert into ndb_chq_wh_temp_limits (ndb_chq_wh_temp_limit_record_no,ndb_chq_wh_temp_value,ndb_chq_wh_temp_exp_date,idndb_chq_wh)"
                                        + " values ("
                                        + "'" + cw_temp_limit_arr[0] + "'," //record_no
                                        + "'" + cw_temp_limit_arr[1] + "'," //limit value
                                        + "'" + cw_temp_limit_arr[2] + "'," //exp date
                                        + "'" + idndb_chq_wh + "')";

                                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                    throw new Exception("Error Occured in insert ndb_rec_fin_temp_limits");
                                }
                            }
                        }

                    }

                    m_strQry = "update ndb_cust_prod_map set prod_relationship_auth='UN-AUTHORIZED', "
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
            success = "1100";
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in saving cutomer product mapping data, Exception" + e);
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_rs2 != null) {
                    _rs2.close();
                }
                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_stmnt2 != null) {
                    _stmnt2.close();
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
            log.error("Error occured in reading bank data, Exception" + e);
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
            log.error("Error occured in read in user data, Exception" + e);
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
            log.error("Error occured in reading industry data, Exception" + e);
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
            log.error("Error occured in reading geo market data, Exception" + e);
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
            log.error("Error occured in reading holiday data, Exception" + e);
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
                m_jsObj.put("rec_finance_margin_ac", m_rs.getString("rec_finance_margin_ac"));
                m_jsObj.put("rec_finance_margin", m_rs.getString("rec_finance_margin"));
                m_jsObj.put("cust_credit_rate", m_rs.getString("cust_credit_rate"));
                m_jsObj.put("idndb_bank_master_file", m_rs.getString("idndb_bank_master_file"));
                m_jsObj.put("idndb_branch_master_file", m_rs.getString("idndb_branch_master_file"));
                m_jsObj.put("cust_other_bank_ac_no", m_rs.getString("cust_other_bank_ac_no"));
                m_jsObj.put("idndb_geo_market_master_file", m_rs.getString("idndb_geo_market_master_file"));
                m_jsObj.put("cust_status", m_rs.getString("cust_status"));
                m_jsObj.put("cust_is_ndb_cust", m_rs.getString("cust_is_ndb_cust"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            log.error("Error occured in reading cutomer define data, Exception" + e);
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
                m_jsObj.put("prod_relationship_res_fin", m_rs.getString("prod_relationship_res_fin"));
                m_jsObj.put("prod_relationship_chq_ware", m_rs.getString("prod_relationship_chq_ware"));
                m_jsObj.put("prod_relationship_reship_only", m_rs.getString("prod_relationship_reship_only"));

            }

            m_chstrsql = "select SUM(pdc_chq_amu) as rec_finance_Outstanding  from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + prm_stridndb_cust_prod_map + "' and pdc_req_financing='RF' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth='AUTHORIZED'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String rec_finance_Outstanding = "0.00";
            if (m_rs.next()) {
                rec_finance_Outstanding = m_rs.getString("rec_finance_Outstanding");
            }

            m_chstrsql = "select * from ndb_rec_fin where idndb_cust_prod_map='" + prm_stridndb_cust_prod_map + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            int idndb_rec_fin = 0;
            if (m_rs.next()) {
                idndb_rec_fin = m_rs.getInt("idndb_rec_fin");
                m_jsObj.put("idndb_rec_fin", m_rs.getString("idndb_rec_fin"));
                m_jsObj.put("rec_finance_curr", m_rs.getString("rec_finance_curr"));
                m_jsObj.put("rec_finance_limit", m_rs.getString("rec_finance_limit"));

                m_jsObj.put("rec_finance_Outstanding", rec_finance_Outstanding);
                m_jsObj.put("rec_finance_tenor", m_rs.getString("rec_finance_tenor"));
                m_jsObj.put("rec_finance_inerest_rate", m_rs.getString("rec_finance_inerest_rate"));
                m_jsObj.put("rec_finance_financing", m_rs.getString("rec_finance_financing"));
                m_jsObj.put("rec_finance_bulk_credit", m_rs.getString("rec_finance_bulk_credit"));
                m_jsObj.put("rec_finance_erly_wdr_chg", m_rs.getString("rec_finance_erly_wdr_chg"));
                m_jsObj.put("rec_finance_vale_dte_extr_chg", m_rs.getString("rec_finance_vale_dte_extr_chg"));
                m_jsObj.put("rec_finance_erly_stlemnt_chg", m_rs.getString("rec_finance_erly_stlemnt_chg"));
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
                m_jsObj.put("rec_finance_margin_ac", m_rs.getString("rec_finance_margin_ac"));
                m_jsObj.put("rec_finance_margin", m_rs.getString("rec_finance_margin"));
                m_jsObj.put("cust_credit_rate", m_rs.getString("cust_credit_rate"));

            }

            m_chstrsql = "select SUM(pdc_chq_amu) as sl_has_byr_otstaning  from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + prm_stridndb_cust_prod_map + "' and pdc_req_financing='CW' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth='AUTHORIZED'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            String sl_has_byr_otstaning = "0.00";
            if (m_rs.next()) {
                sl_has_byr_otstaning = m_rs.getString("sl_has_byr_otstaning");
            }

            m_chstrsql = "select * from ndb_chq_wh where idndb_cust_prod_map='" + prm_stridndb_cust_prod_map + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);
            int idndb_chq_wh = 0;
            if (m_rs.next()) {
                idndb_chq_wh = m_rs.getInt("idndb_chq_wh");
                m_jsObj.put("idndb_chq_wh", m_rs.getString("idndb_chq_wh"));
                m_jsObj.put("chq_wh_limit", m_rs.getString("chq_wh_limit"));
                m_jsObj.put("sl_has_byr_fmax_chq_amu", m_rs.getString("sl_has_byr_fmax_chq_amu"));
                m_jsObj.put("sl_has_byr_otstaning", sl_has_byr_otstaning);
                m_jsObj.put("sl_has_byr_tenor", m_rs.getString("sl_has_byr_tenor"));

                m_jsObj.put("chq_wh_erly_wdr_chg", m_rs.getString("chq_wh_erly_wdr_chg"));
                m_jsObj.put("chq_wh_vale_dte_extr_chg", m_rs.getString("chq_wh_vale_dte_extr_chg"));
                m_jsObj.put("chq_wh_erly_stlemnt_chg", m_rs.getString("chq_wh_erly_stlemnt_chg"));
                m_jsObj.put("chq_status", m_rs.getString("chq_status"));

            }
            String chqTemporaryLimits = "";
            if (idndb_chq_wh != 0) {
                m_chstrsql = "SELECT * FROM ndb_chq_wh_temp_limits where idndb_chq_wh='" + idndb_chq_wh + "' order by idndb_chq_wh_temp_limits";
                m_rs = m_stamt.executeQuery(m_chstrsql);

                while (m_rs.next()) {
                    String recordNo = m_rs.getString("ndb_chq_wh_temp_limit_record_no");
                    String tempValue = m_rs.getString("ndb_chq_wh_temp_value");
                    String expDate = m_rs.getString("ndb_chq_wh_temp_exp_date");
                    chqTemporaryLimits += recordNo + "##" + tempValue + "##" + expDate + ",";
                }
                if (!chqTemporaryLimits.equals("")) {
                    chqTemporaryLimits = chqTemporaryLimits.substring(0, chqTemporaryLimits.length() - 1);
                }
            }

            m_jsObj.put("cw_temporary_limits", chqTemporaryLimits);

            m_jsArr.put(i, m_jsObj);
            i++;
        } catch (Exception e) {
            log.error("Error occured in reading customer product mapping data, Exception" + e);
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
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();

            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + prm_stridndb_seller_has_buyers + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_seller_has_buyers", m_rs.getString("idndb_seller_has_buyers"));
                m_jsObj.put("idndb_customer_define_seller", m_rs.getString("idndb_customer_define_seller"));
                m_jsObj.put("idndb_customer_define_buyer", m_rs.getString("idndb_customer_define_buyer"));
                m_jsObj.put("sl_has_byr_prorm_type", m_rs.getString("sl_has_byr_prorm_type"));
                //  m_jsObj.put("sl_has_byr_facilty_ac_no", m_rs.getString("sl_has_byr_facilty_ac_no"));
                m_jsObj.put("sl_has_byr_max_chq_amu", m_rs.getString("sl_has_byr_max_chq_amu"));
                m_jsObj.put("shb_facty_det_crd_loam_limit", m_rs.getString("shb_facty_det_crd_loam_limit"));
                m_jsObj.put("shb_facty_det_os", m_rs.getString("shb_facty_det_os"));
                m_jsObj.put("shb_facty_det_tenor", m_rs.getString("shb_facty_det_tenor"));
                m_jsObj.put("shb_facty_det_irest_trry", m_rs.getString("shb_facty_det_irest_trry"));
                m_jsObj.put("shb_chq_dis_adv_rate_prectange", m_rs.getString("shb_chq_dis_adv_rate_prectange"));
                //  m_jsObj.put("shb_facty_det_main_cmiss_limit", m_rs.getString("shb_facty_det_main_cmiss_limit"));
                //  m_jsObj.put("shb_chq_dis_ricing_intrest", m_rs.getString("shb_chq_dis_ricing_intrest"));
                m_jsObj.put("rec_finance_commision_crg", m_rs.getString("rec_finance_commision_crg"));
                m_jsObj.put("rf_tran_base_falt_fee", m_rs.getString("rf_tran_base_falt_fee"));
                m_jsObj.put("rf_tran_base_from_tran", m_rs.getString("rf_tran_base_from_tran"));
                m_jsObj.put("rf_fixed_rate_amount", m_rs.getString("rf_fixed_rate_amount"));
                m_jsObj.put("rf_fixed_frequency", m_rs.getString("rf_fixed_frequency"));

                m_jsObj.put("chq_wh_commision_crg", m_rs.getString("chq_wh_commision_crg"));
                m_jsObj.put("cw_tran_base_falt_fee", m_rs.getString("cw_tran_base_falt_fee"));
                m_jsObj.put("cw_tran_base_from_tran", m_rs.getString("cw_tran_base_from_tran"));
                m_jsObj.put("cw_fixed_rate_amount", m_rs.getString("cw_fixed_rate_amount"));
                m_jsObj.put("cw_fixed_frequency", m_rs.getString("cw_fixed_frequency"));

                m_jsObj.put("sl_has_byr_remarks", m_rs.getString("sl_has_byr_remarks"));
                m_jsObj.put("sl_has_byr_status", m_rs.getString("sl_has_byr_status"));

                //cw facility details
                m_jsObj.put("sl_has_byr_warehs_limit", m_rs.getString("sl_has_byr_warehs_limit"));
                m_jsObj.put("sl_has_byr_warehs_otstaning", m_rs.getString("sl_has_byr_warehs_otstaning"));
                m_jsObj.put("sl_has_byr_warehs_tenor", m_rs.getString("sl_has_byr_warehs_tenor"));
                m_jsObj.put("sl_has_byr_warehs_fmax_chq_amu", m_rs.getString("sl_has_byr_warehs_fmax_chq_amu"));

                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            log.error("Error occured in reading relationship estabilshment data, Exception" + e);
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

    public JSONArray getAvailbleSubLimitLimit(String prm_stridndb_customer_define_seller, String prm_str_sl_has_byr_cdt_loan_amu) {
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

            m_chstrsql = "select * from ndb_rec_fin where idndb_cust_prod_map='" + prm_stridndb_customer_define_seller + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            double rec_finance_limit = 0.00;
            if (m_rs.next()) {
                rec_finance_limit = m_rs.getDouble("rec_finance_limit");
            }

            m_chstrsql = "select SUM(shb_facty_det_crd_loam_limit) As SUMOFSUBLIMITS from ndb_seller_has_buyers where idndb_customer_define_seller='" + prm_stridndb_customer_define_seller + "' and sl_has_byr_prorm_type='RF' and sl_has_byr_status='ACTIVE'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {
                m_jsObj = new JSONObject();
                double available_sub_limt = 0.00;
                if (!(m_rs.getString("SUMOFSUBLIMITS") == null)) {

                    available_sub_limt = rec_finance_limit - Double.parseDouble(m_rs.getString("SUMOFSUBLIMITS"));

                } else {
                    available_sub_limt = rec_finance_limit;

                }

                m_jsObj.put("available_sub_limt", available_sub_limt);

                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            log.error("Error occured in reading rf seller sub limits, Exception" + e);
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

    public JSONArray getAvailbleCWSubLimitLimit(String prm_stridndb_customer_define_seller) {
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

            m_chstrsql = "select * from ndb_chq_wh where idndb_cust_prod_map='" + prm_stridndb_customer_define_seller + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            double chq_wh_limit = 0.00;
            if (m_rs.next()) {
                chq_wh_limit = m_rs.getDouble("chq_wh_limit");
            }

            m_chstrsql = "select SUM(sl_has_byr_warehs_limit) As SUMOFSUBLIMITS from ndb_seller_has_buyers where idndb_customer_define_seller='" + prm_stridndb_customer_define_seller + "' and sl_has_byr_prorm_type='RF' and sl_has_byr_status='ACTIVE'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();

                double available_sub_limt = chq_wh_limit - Double.parseDouble(m_rs.getString("SUMOFSUBLIMITS"));
                m_jsObj.put("available_sub_limt", available_sub_limt);

                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            log.error("Error occured in reading cw seller sub limits data, Exception" + e);
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
            log.error("Error occured in reading branch data, Exception" + e);
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

            m_chstrsql = "select * from ndb_branch_master_file where idndb_bank_master_file='" + prm_stridndb_bank_master_file + "' and branch_status ='ACTIVE' and branch_approval='AUTHORIZED'";
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
            log.error("Error occured in reading bank define branch data, Exception" + e);
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

            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_customer_define_seller='" + prm_stridndb_cust_prod_map + "' and sl_has_byr_status ='ACTIVE' and sl_has_byr_auth='AUTHORIZED' and sl_has_byr_prorm_type='CW'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            while (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_seller_has_buyers", m_rs.getString("idndb_seller_has_buyers"));
                m_jsObj.put("sl_has_byr_warehs_tenor", m_rs.getString("sl_has_byr_warehs_tenor"));

                String m_idndb_customer_define = "";
                m_chstrsql2 = "select idndb_customer_define from ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs.getString("idndb_customer_define_buyer") + "'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (m_rs2.next()) {
                    m_idndb_customer_define = m_rs2.getString("idndb_customer_define");

                }

                String m_cust_id = "";
                String m_cust_name = "";
                m_chstrsql2 = "select cust_id,cust_name from ndb_customer_define where idndb_customer_define='" + m_idndb_customer_define + "'";
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
            log.error("Error occured in reading seller define buyer data, Exception" + e);
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

    public JSONArray getSellerDifineBuyerDataRMS(String prm_stridndb_cust_prod_map) {
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

            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_customer_define_seller='" + prm_stridndb_cust_prod_map + "' and sl_has_byr_status ='ACTIVE' and sl_has_byr_auth='AUTHORIZED' and sl_has_byr_prorm_type='RF'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            while (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_seller_has_buyers", m_rs.getString("idndb_seller_has_buyers"));
                m_jsObj.put("shb_facty_det_tenor", m_rs.getString("shb_facty_det_tenor"));

                String m_idndb_customer_define = "";
                m_chstrsql2 = "select idndb_customer_define from ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs.getString("idndb_customer_define_buyer") + "'";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                if (m_rs2.next()) {
                    m_idndb_customer_define = m_rs2.getString("idndb_customer_define");

                }

                String m_cust_id = "";
                String m_cust_name = "";
                m_chstrsql2 = "select cust_id,cust_name from ndb_customer_define where idndb_customer_define='" + m_idndb_customer_define + "'";
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
            log.error("Error occured in reading seller define buyer data, Exception" + e);
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
                if (m_rs.next()) {

                    rec_finance_financing = m_rs.getString("rec_finance_financing");
                    rec_finance_tenor = m_rs.getString("rec_finance_tenor");

                    m_jsObj.put("rec_finance_financing", rec_finance_financing);

                    m_jsObj.put("rec_finance_tenor", rec_finance_tenor);

                }

            }

            if (prod_relationship_chq_ware.equals("ACTIVE")) {
                String sl_has_byr_fmax_chq_amu = "";
                String sl_has_byr_tenor = "";

                m_chstrsql = "select * from ndb_chq_wh where idndb_cust_prod_map='" + prm_stridndb_cust_prod_map + "'";
                m_rs = m_stamt.executeQuery(m_chstrsql);
                if (m_rs.next()) {

                    sl_has_byr_fmax_chq_amu = m_rs.getString("sl_has_byr_fmax_chq_amu");
                    sl_has_byr_tenor = m_rs.getString("sl_has_byr_tenor");
                    m_jsObj.put("sl_has_byr_fmax_chq_amu", sl_has_byr_fmax_chq_amu);

                    m_jsObj.put("sl_has_byr_tenor", sl_has_byr_tenor);

                }

            }

            m_jsArr.put(i, m_jsObj);
            i++;

        } catch (Exception e) {
            log.error("Error occured in reading seller product mapping data, Exception" + e);
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

    public JSONArray getBuyerRelData(String prm_stridndb_seller_has_buyers) {
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

            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + prm_stridndb_seller_has_buyers + "' and sl_has_byr_status ='ACTIVE' and sl_has_byr_auth='AUTHORIZED'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                double sl_has_byr_warehs_limit = m_rs.getDouble("sl_has_byr_warehs_limit");
                String idndb_customer_define_seller = m_rs.getString("idndb_customer_define_seller");
                m_jsObj.put("idndb_seller_has_buyers", m_rs.getString("idndb_seller_has_buyers"));
                m_jsObj.put("sl_has_byr_warehs_limit", m_rs.getString("sl_has_byr_warehs_limit"));
                m_jsObj.put("sl_has_byr_warehs_tenor", m_rs.getString("sl_has_byr_warehs_tenor"));
                m_jsObj.put("sl_has_byr_warehs_otstaning", m_rs.getString("sl_has_byr_warehs_otstaning"));
                m_jsObj.put("sl_has_byr_warehs_fmax_chq_amu", m_rs.getString("sl_has_byr_warehs_fmax_chq_amu"));

                m_chstrsql2 = "select SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + prm_stridndb_seller_has_buyers + "' and idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED')";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                double pdc_chq_amu = 0.00;
                double available_amu = 0.00;

                if (!(m_rs2 == null)) {
                    if (m_rs2.next()) {

                        pdc_chq_amu = m_rs2.getDouble("pdc_chq_amu");
                        available_amu = sl_has_byr_warehs_limit - pdc_chq_amu;

                    }
                } else {
                    pdc_chq_amu = 0.00;
                    available_amu = sl_has_byr_warehs_limit - pdc_chq_amu;

                }

                m_jsObj.put("sl_has_byr_warehs_balance", available_amu);
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            log.error("Error occured in reading buyer define data, Exception" + e);
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

    public JSONArray getBuyerRelDataRMS(String prm_stridndb_seller_has_buyers) {
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

            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + prm_stridndb_seller_has_buyers + "' and sl_has_byr_status ='ACTIVE' and sl_has_byr_auth='AUTHORIZED'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                double shb_facty_det_crd_loam_limit = m_rs.getDouble("shb_facty_det_crd_loam_limit");
                String idndb_customer_define_seller = m_rs.getString("idndb_customer_define_seller");
                m_jsObj.put("idndb_seller_has_buyers", m_rs.getString("idndb_seller_has_buyers"));
                m_jsObj.put("shb_facty_det_crd_loam_limit", m_rs.getString("shb_facty_det_crd_loam_limit"));
                m_jsObj.put("shb_facty_det_os", m_rs.getString("shb_facty_det_os"));
                m_jsObj.put("shb_facty_det_tenor", m_rs.getString("shb_facty_det_tenor"));
                m_jsObj.put("sl_has_byr_max_chq_amu", m_rs.getString("sl_has_byr_max_chq_amu"));

                m_chstrsql2 = "select SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + prm_stridndb_seller_has_buyers + "' and idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED')";
                m_rs2 = m_stamt2.executeQuery(m_chstrsql2);
                double pdc_chq_amu = 0.00;
                double available_amu = 0.00;
                if (!(m_rs2 == null)) {
                    if (m_rs2.next()) {

                        pdc_chq_amu = m_rs2.getDouble("pdc_chq_amu");
                        available_amu = shb_facty_det_crd_loam_limit - pdc_chq_amu;

                    }
                } else {
                    pdc_chq_amu = 0.00;
                    available_amu = shb_facty_det_crd_loam_limit - pdc_chq_amu;
                }

                m_jsObj.put("sl_has_byr_rms_balance", available_amu);
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            log.error("Error occured in buyer relationshi data, Exception" + e);
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
                m_jsObj.put("rec_finance_margin_ac", m_rs.getString("rec_finance_margin_ac"));
                m_jsObj.put("rec_finance_margin", m_rs.getString("rec_finance_margin"));
                m_jsObj.put("cust_credit_rate", m_rs.getString("cust_credit_rate"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
        } catch (Exception e) {
            log.error("Error occured in reading customer define data" + e);
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
}
