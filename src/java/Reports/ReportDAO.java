/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reports;

import DBAutoFillBeans.comboDAO;
import entities.NDB_Seller_has_Buyers;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import ndb.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Madhawa_4799
 */
public class ReportDAO {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(ReportDAO.class.getName());
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
    private Exception _exception;

    public JSONArray get_pdc_cw_reveived_chques_data_seller_wise(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            String idndb_customer_define_seller_id = "";
            String date_from = "";
            String date_to = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            date_from = prm_obj.getString("date_from");
            date_to = prm_obj.getString("date_to");
            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_req_financing='CW' and STR_TO_DATE(pdc_booking_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

            } else {
                m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and pdc_req_financing='CW' and STR_TO_DATE(pdc_booking_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

            }

            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                m_jsObj = new JSONObject();
                String current_status = "";
                String pdc_chq_status = m_rs1.getString("pdc_chq_status");
                String pdc_chq_status_auth = m_rs1.getString("pdc_chq_status_auth");
                String idndb_seller_has_buyers = m_rs1.getString("idndb_customer_define_buyer_id");
                String idndb_customer_define_seller_id2 = m_rs1.getString("idndb_customer_define_seller_id");
                DecimalFormat df = new DecimalFormat("#,###.00");

                String pdc_value_date_ext = "";
                if (m_rs1.getString("pdc_value_date_ext").equals("ACTIVE")) {
                    pdc_value_date_ext = " / VAL.DATE EXT. ";
                }
                current_status = pdc_chq_status + pdc_value_date_ext + "( " + pdc_chq_status_auth + " )";

                m_strQry2 = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String idndb_customer_define_buyer = "";
                String idndb_customer_define_buyer_idndb_customer_define = "";
                if (m_rs2.next()) {
                    idndb_customer_define_buyer = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_buyer + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_buyer_idndb_customer_define = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_buyer_idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name = "";
                String cust_id = "";
                if (m_rs2.next()) {

                    cust_name = m_rs2.getString("cust_name");
                    cust_id = m_rs2.getString("cust_id");
                }

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller_id2 + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String idndb_customer_define_seller_idndb_customer_define = "";
                if (m_rs2.next()) {
                    idndb_customer_define_seller_idndb_customer_define = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name_seller = "";
                String cust_id_seller = "";
                if (m_rs2.next()) {

                    cust_name_seller = m_rs2.getString("cust_name");
                    cust_id_seller = m_rs2.getString("cust_id");
                }

                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);

                m_jsObj.put("cust_id", cust_id);
                m_jsObj.put("cust_name", cust_name);

                m_jsObj.put("pdc_booking_date", m_rs1.getString("pdc_booking_date"));
                m_jsObj.put("pdc_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));
                m_jsObj.put("current_status", current_status);
                m_jsArr.put(i, m_jsObj);
                i++;
            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_pdc_cw_unauth_bulk_pdc_data(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            String idndb_customer_define_seller_id = "";
            String date_from = "";
            String prorm_type_rf = "";
            String prorm_type_cw = "";
            String program_type = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");

            prorm_type_rf = prm_obj.getString("prorm_type_rf");
            prorm_type_cw = prm_obj.getString("prorm_type_cw");
            if (prorm_type_cw.equals("ACTIVE")) {
                program_type = "CW";
            }
            if (prorm_type_rf.equals("ACTIVE")) {
                program_type = "RF";

            }

            m_strQry = "select idndb_seller_has_buyers, shb_chq_dis_adv_rate_prectange from ndb_seller_has_buyers";
            _rs = _stmnt.executeQuery(m_strQry);
            List<NDB_Seller_has_Buyers> ndb_seeler_has_buyers_List = new ArrayList<NDB_Seller_has_Buyers>();
            while (_rs.next()) {
                NDB_Seller_has_Buyers buy = new NDB_Seller_has_Buyers();
                buy.setIdndb_seller_has_buyers(_rs.getString("idndb_seller_has_buyers"));
                buy.setShb_chq_dis_adv_rate_prectange(_rs.getString("shb_chq_dis_adv_rate_prectange"));
                ndb_seeler_has_buyers_List.add(buy);
            }
            _stmnt.clearBatch();

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and pdc_req_financing='" + program_type + "' and pdc_chq_status in ('ACTIVE','ERLYLIQUDED') and pdc_chq_status_auth='UN-AUTHORIZED' and pdc_chq_batch_no not in ('*')";
            m_rs1 = _stmnt.executeQuery(m_strQry);

            while (m_rs1.next()) {

                m_jsObj = new JSONObject();
                String pdc_chq_status = m_rs1.getString("pdc_chq_status");
                String pdc_chq_status_auth = m_rs1.getString("pdc_chq_status_auth");
                String idndb_seller_has_buyers = m_rs1.getString("idndb_customer_define_buyer_id");

                m_strQry2 = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String idndb_customer_define_buyer = "";
                String idndb_customer_define_seller_idndb_customer_define = "";
                if (m_rs2.next()) {
                    idndb_customer_define_buyer = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_buyer + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller_idndb_customer_define = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_pdc_txn_master") + "' and ndb_change_log_type='PDCTXN' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name = "";
                String cust_id = "";
                if (m_rs2.next()) {
                    cust_name = m_rs2.getString("cust_name");
                    cust_id = m_rs2.getString("cust_id");
                }

                m_jsObj.put("cust_id", cust_name + "," + cust_id);
                m_jsObj.put("cust_name", cust_name);
                m_jsObj.put("pdc_booking_date", m_rs1.getString("pdc_booking_date"));
                m_jsObj.put("pdc_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("modification", modification);
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));
                m_jsObj.put("pdc_chq_batch_no", m_rs1.getString("pdc_chq_batch_no"));
                m_jsObj.put("pdc_chq_creat_by", m_rs1.getString("pdc_chq_creat_by"));
                m_jsObj.put("checkbox", "<div class=\"controls\"><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + m_rs1.getString("idndb_pdc_txn_master") + "\"/></div>");
                m_jsArr.put(i, m_jsObj);
                i++;
            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_pdc_cw_vde_data_seller_wise(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            DecimalFormat df = new DecimalFormat("#,###.00");
            String idndb_customer_define_seller_id = "";
            String date_from = "";
            String date_to = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            date_from = prm_obj.getString("date_from");
            date_to = prm_obj.getString("date_to");
            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_req_financing='CW' and pdc_value_date_ext='ACTIVE' and STR_TO_DATE(pdc_booking_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

            } else {
                m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and pdc_req_financing='CW' and pdc_value_date_ext='ACTIVE' and STR_TO_DATE(pdc_booking_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

            }

            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_jsObj = new JSONObject();
                String current_status = "";
                String pdc_chq_status = m_rs1.getString("pdc_chq_status");
                String pdc_chq_status_auth = m_rs1.getString("pdc_chq_status_auth");
                String idndb_seller_has_buyers = m_rs1.getString("idndb_customer_define_buyer_id");
                String idndb_customer_define_seller_id_se = m_rs1.getString("idndb_customer_define_seller_id");

                if (pdc_chq_status.equals("ERLYLIQUDED") && pdc_chq_status_auth.equals("AUTHORIZED")) {
                    current_status = "EARLY LIQD (AUTHORIZED)";

                }

                if (pdc_chq_status.equals("ERLYLIQUDED") && pdc_chq_status_auth.equals("UN-AUTHORIZED")) {
                    current_status = "EARLY LIQD (UN-AUTHORIZED)";

                }

                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("AUTHORIZED")) {
                    current_status = "ACTIVE (AUTHORIZED)";

                }
                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("UN-AUTHORIZED")) {
                    current_status = "ACTIVE (UN-AUTHORIZED)";

                }

                if (pdc_chq_status.equals("DEACTIVE") && pdc_chq_status_auth.equals("AUTHORIZED")) {
                    current_status = "DEACTIVE (AUTHORIZED)";

                }
                if (pdc_chq_status.equals("DEACTIVE") && pdc_chq_status_auth.equals("UN-AUTHORIZED")) {
                    current_status = "DEACTIVE (UN-AUTHORIZED)";

                }

                if (pdc_chq_status.equals("RETURNED")) {
                    current_status = "RETURNED";

                }

                if (pdc_chq_status.equals("PROCESSED")) {
                    current_status = "CLEARED";

                }

                m_strQry2 = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String idndb_customer_define_buyer = "";
                String idndb_customer_define_seller_idndb_customer_define = "";
                if (m_rs2.next()) {
                    idndb_customer_define_buyer = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_buyer + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller_idndb_customer_define = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name = "";
                String cust_id = "";
                if (m_rs2.next()) {

                    cust_name = m_rs2.getString("cust_name");
                    cust_id = m_rs2.getString("cust_id");
                }

                String idndb_customer_define_seller = "";

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller_id_se + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name_seller = "";
                String cust_id_seller = "";
                if (m_rs2.next()) {

                    cust_name_seller = m_rs2.getString("cust_name");
                    cust_id_seller = m_rs2.getString("cust_id");
                }

                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);

                m_jsObj.put("cust_id", cust_id);
                m_jsObj.put("cust_name", cust_name);

                m_jsObj.put("pdc_booking_date", m_rs1.getString("pdc_booking_date"));
                m_jsObj.put("pdc_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_old_value_date", m_rs1.getString("pdc_old_value_date"));
                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));
                m_jsObj.put("current_status", current_status);
                m_jsArr.put(i, m_jsObj);
                i++;
            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_pdc_cw_additional_day_data_seller_wise(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            DecimalFormat df = new DecimalFormat("#,###.00");
            String idndb_customer_define_seller_id = "";
            String date_from = prm_obj.getString("date_from");
            String date_to = prm_obj.getString("date_to");

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_req_financing='CW' and pdc_additional_day='ACTIVE' and STR_TO_DATE(pdc_old_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

            } else {
                m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and pdc_req_financing='CW' and pdc_additional_day='ACTIVE' and STR_TO_DATE(pdc_old_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

            }

            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_jsObj = new JSONObject();
                String current_status = "";
                String pdc_chq_status = m_rs1.getString("pdc_chq_status");
                String pdc_chq_status_auth = m_rs1.getString("pdc_chq_status_auth");
                String idndb_seller_has_buyers = m_rs1.getString("idndb_customer_define_buyer_id");
                String idndb_customer_define_seller_id_se = m_rs1.getString("idndb_customer_define_seller_id");

                String pdc_value_date_ext = "";
                if (m_rs1.getString("pdc_value_date_ext").equals("ACTIVE")) {
                    pdc_value_date_ext = " / VAL.DATE EXT. ";
                }
                current_status = pdc_chq_status + pdc_value_date_ext + "( " + pdc_chq_status_auth + " )";

                m_strQry2 = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String idndb_customer_define_buyer = "";
                String idndb_customer_define_seller_idndb_customer_define = "";
                if (m_rs2.next()) {
                    idndb_customer_define_buyer = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_buyer + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller_idndb_customer_define = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name = "";
                String cust_id = "";
                if (m_rs2.next()) {

                    cust_name = m_rs2.getString("cust_name");
                    cust_id = m_rs2.getString("cust_id");
                }

                String idndb_customer_define_seller = "";

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller_id_se + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name_seller = "";
                String cust_id_seller = "";
                if (m_rs2.next()) {

                    cust_name_seller = m_rs2.getString("cust_name");
                    cust_id_seller = m_rs2.getString("cust_id");
                }

                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);

                m_jsObj.put("cust_id", cust_id);
                m_jsObj.put("cust_name", cust_name);

                m_jsObj.put("pdc_booking_date", m_rs1.getString("pdc_booking_date"));
                m_jsObj.put("pdc_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_old_value_date", m_rs1.getString("pdc_old_value_date"));
                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));
                m_jsObj.put("current_status", current_status);
                m_jsArr.put(i, m_jsObj);
                i++;
            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_pdc_cw_return_data_seller_wise(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            DecimalFormat df = new DecimalFormat("#,###.00");
            String idndb_customer_define_seller_id = "";
            String status = prm_obj.getString("status");
            String date_from = prm_obj.getString("date_from");
            String date_to = prm_obj.getString("date_to");

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_req_financing='CW' and pdc_chq_status='" + status + "' and pdc_chq_status_auth='AUTHORIZED' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

            } else {
                m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and pdc_req_financing='CW' and pdc_chq_status='" + status + "' and pdc_chq_status_auth='AUTHORIZED' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

            }

            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_jsObj = new JSONObject();
                String pdc_chq_status = m_rs1.getString("pdc_chq_status");
                String pdc_chq_status_auth = m_rs1.getString("pdc_chq_status_auth");
                String idndb_seller_has_buyers = m_rs1.getString("idndb_customer_define_buyer_id");
                String idndb_customer_define_seller_id_se = m_rs1.getString("idndb_customer_define_seller_id");

                m_strQry2 = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String idndb_customer_define_buyer = "";
                String idndb_customer_define_seller_idndb_customer_define = "";
                if (m_rs2.next()) {
                    idndb_customer_define_buyer = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_buyer + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller_idndb_customer_define = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name = "";
                String cust_id = "";
                if (m_rs2.next()) {

                    cust_name = m_rs2.getString("cust_name");
                    cust_id = m_rs2.getString("cust_id");
                }

                String idndb_customer_define_seller = "";

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller_id_se + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name_seller = "";
                String cust_id_seller = "";
                if (m_rs2.next()) {

                    cust_name_seller = m_rs2.getString("cust_name");
                    cust_id_seller = m_rs2.getString("cust_id");
                }

                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);

                m_jsObj.put("cust_id", cust_id);
                m_jsObj.put("cust_name", cust_name);

                m_jsObj.put("pdc_booking_date", m_rs1.getString("pdc_booking_date"));
                m_jsObj.put("pdc_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_old_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));
                m_jsArr.put(i, m_jsObj);
                i++;
            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_pdc_cw_chq_status_seller_wise(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            String idndb_customer_define_seller_id = "";

            String status = "";
            String date_from = "";
            String date_to = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");

            status = prm_obj.getString("status");
            date_from = prm_obj.getString("date_from");
            date_to = prm_obj.getString("date_to");

            String date_type = "";
            String quiery_date_type = "";
            date_type = prm_obj.getString("date_type");

            if (date_type.equals("BOOKING_DATE")) {

                quiery_date_type = "pdc_booking_date";
            }

            if (date_type.equals("VALUE_DATE")) {
                quiery_date_type = "pdc_value_date";

            }

            if (idndb_customer_define_seller_id.equals("all")) {
                if (status.equals("AUTHORIZED")) {
                    m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_req_financing='CW' and  pdc_chq_status_auth='" + status + "'and pdc_chq_status='ACTIVE' and STR_TO_DATE(" + quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

                } else if (status.equals("UN-AUTHORIZED")) {
                    m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_req_financing='CW' and pdc_chq_status_auth='" + status + "'and pdc_chq_status='ACTIVE'and STR_TO_DATE(" + quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

                } else {
                    m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_req_financing='CW' and pdc_chq_status='" + status + "'and STR_TO_DATE(" + quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

                }

            } else {
                if (status.equals("AUTHORIZED")) {
                    m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and pdc_req_financing='CW' and  pdc_chq_status_auth='" + status + "' and pdc_chq_status='ACTIVE'and STR_TO_DATE(" + quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

                } else if (status.equals("UN-AUTHORIZED")) {
                    m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and pdc_req_financing='CW' and  pdc_chq_status_auth='" + status + "' and pdc_chq_status='ACTIVE'and STR_TO_DATE(" + quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

                } else {
                    m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and pdc_req_financing='CW' and  pdc_chq_status='" + status + "' and STR_TO_DATE(" + quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

                }

            }

            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_jsObj = new JSONObject();
                String current_status = "";
                String pdc_chq_status = m_rs1.getString("pdc_chq_status");
                String pdc_chq_status_auth = m_rs1.getString("pdc_chq_status_auth");
                String idndb_seller_has_buyers = m_rs1.getString("idndb_customer_define_buyer_id");
                String idndb_customer_define_seller_id_se = m_rs1.getString("idndb_customer_define_seller_id");

                if (pdc_chq_status.equals("ERLYLIQUDED") && pdc_chq_status_auth.equals("AUTHORIZED")) {
                    current_status = "EARLY LIQD (AUTHORIZED)";

                }

                if (pdc_chq_status.equals("ERLYLIQUDED") && pdc_chq_status_auth.equals("UN-AUTHORIZED")) {
                    current_status = "EARLY LIQD (UN-AUTHORIZED)";

                }

                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("AUTHORIZED")) {
                    current_status = "ACTIVE (AUTHORIZED)";

                }
                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("UN-AUTHORIZED")) {
                    current_status = "ACTIVE (UN-AUTHORIZED)";

                }
                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("REJECTED")) {
                    current_status = "ACTIVE (REJECTED)";

                }

                if (pdc_chq_status.equals("RETURNED")) {
                    current_status = "RETURNED";

                }

                if (pdc_chq_status.equals("PROCESSED")) {
                    current_status = "CLEARED";

                }

                m_strQry2 = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String idndb_customer_define_buyer = "";
                String idndb_customer_define_seller_idndb_customer_define = "";
                if (m_rs2.next()) {
                    idndb_customer_define_buyer = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_buyer + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller_idndb_customer_define = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name = "";
                String cust_id = "";
                if (m_rs2.next()) {

                    cust_name = m_rs2.getString("cust_name");
                    cust_id = m_rs2.getString("cust_id");
                }

                String idndb_customer_define_seller = "";

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller_id_se + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name_seller = "";
                String cust_id_seller = "";
                if (m_rs2.next()) {

                    cust_name_seller = m_rs2.getString("cust_name");
                    cust_id_seller = m_rs2.getString("cust_id");
                }

                DecimalFormat df = new DecimalFormat("#,###.00");

                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);
                m_jsObj.put("cust_id", cust_id);
                m_jsObj.put("cust_name", cust_name);
                m_jsObj.put("pdc_booking_date", m_rs1.getString("pdc_booking_date"));
                m_jsObj.put("pdc_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));
                m_jsObj.put("current_status", current_status);
                m_jsArr.put(i, m_jsObj);
                i++;
            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_pdc_rf_chq_status_seller_wise(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            String idndb_customer_define_seller_id = "";

            String date_from = "";
            String date_to = "";
            String status = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            status = prm_obj.getString("status");

            date_from = prm_obj.getString("date_from");
            date_to = prm_obj.getString("date_to");

            String date_type = "";
            String quiery_date_type = "";
            date_type = prm_obj.getString("date_type");

            if (date_type.equals("BOOKING_DATE")) {

                quiery_date_type = "pdc_booking_date";
            }

            if (date_type.equals("VALUE_DATE")) {
                quiery_date_type = "pdc_value_date";

            }

            if (idndb_customer_define_seller_id.equals("all")) {
                if (status.equals("AUTHORIZED")) {
                    m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_req_financing='RF' and  pdc_chq_status_auth='" + status + "' and pdc_chq_status='ACTIVE' and STR_TO_DATE(" + quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

                } else if (status.equals("UN-AUTHORIZED")) {
                    m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_req_financing='RF' and  pdc_chq_status_auth='" + status + "' and pdc_chq_status='ACTIVE' and STR_TO_DATE(" + quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

                } else {
                    m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_req_financing='RF' and pdc_chq_status='" + status + "' and STR_TO_DATE(" + quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

                }

            } else {
                if (status.equals("AUTHORIZED")) {
                    m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and pdc_req_financing='RF' and  pdc_chq_status_auth='" + status + "' and pdc_chq_status='ACTIVE' and STR_TO_DATE(" + quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

                } else if (status.equals("UN-AUTHORIZED")) {
                    m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and pdc_req_financing='RF' and  pdc_chq_status_auth='" + status + "' and pdc_chq_status='ACTIVE' and STR_TO_DATE(" + quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

                } else {
                    m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and pdc_req_financing='RF' and  pdc_chq_status='" + status + "' and STR_TO_DATE(" + quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

                }

            }

            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_jsObj = new JSONObject();
                String current_status = "";
                String pdc_chq_status = m_rs1.getString("pdc_chq_status");
                String pdc_chq_status_auth = m_rs1.getString("pdc_chq_status_auth");
                String idndb_seller_has_buyers = m_rs1.getString("idndb_customer_define_buyer_id");
                String idndb_customer_define_seller_id_se = m_rs1.getString("idndb_customer_define_seller_id");

                if (pdc_chq_status.equals("ERLYLIQUDED") && pdc_chq_status_auth.equals("AUTHORIZED")) {
                    current_status = "EARLY LIQD (AUTHORIZED)";

                }

                if (pdc_chq_status.equals("ERLYLIQUDED") && pdc_chq_status_auth.equals("UN-AUTHORIZED")) {
                    current_status = "EARLY LIQD (UN-AUTHORIZED)";

                }

                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("AUTHORIZED")) {
                    current_status = "ACTIVE (AUTHORIZED)";

                }
                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("UN-AUTHORIZED")) {
                    current_status = "ACTIVE (UN-AUTHORIZED)";

                }
                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("REJECTED")) {
                    current_status = "ACTIVE (REJECTED)";

                }

                if (pdc_chq_status.equals("RETURNED")) {
                    current_status = "RETURNED";

                }

                if (pdc_chq_status.equals("PROCESSED")) {
                    current_status = "CLEARED";

                }

                m_strQry2 = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String idndb_customer_define_buyer = "";
                String idndb_customer_define_seller_idndb_customer_define = "";
                if (m_rs2.next()) {
                    idndb_customer_define_buyer = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_buyer + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller_idndb_customer_define = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name = "";
                String cust_id = "";
                if (m_rs2.next()) {

                    cust_name = m_rs2.getString("cust_name");
                    cust_id = m_rs2.getString("cust_id");
                }

                String idndb_customer_define_seller = "";

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller_id_se + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name_seller = "";
                String cust_id_seller = "";
                if (m_rs2.next()) {

                    cust_name_seller = m_rs2.getString("cust_name");
                    cust_id_seller = m_rs2.getString("cust_id");
                }

                DecimalFormat df = new DecimalFormat("#,###.00");

                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);

                m_jsObj.put("cust_id", cust_id);
                m_jsObj.put("cust_name", cust_name);
                m_jsObj.put("pdc_booking_date", m_rs1.getString("pdc_booking_date"));
                m_jsObj.put("pdc_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));
                m_jsObj.put("pdc_chq_discounting_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_discounting_amu"))));
                m_jsObj.put("current_status", current_status);
                m_jsArr.put(i, m_jsObj);
                i++;
            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getFullPortpolio(JSONObject prm_obj) {
        String m_strQry = "";
        String m_strQry2 = "";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONArray m_jsArr = new JSONArray();
        JSONObject m_jsObj = null;
        try {

            String status = "";
            int i = 0;

            status = prm_obj.getString("status");

            if (status.equals("AUTHORIZED") || status.equals("UN-AUTHORIZED")) {
                m_strQry = "SELECT \n"
                        + "nptm_txn_master.* ,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                        + "ngmmf_geo_market.geo_market_desc,\n"
                        + "nbmf_branch_master.branch_name\n"
                        + "FROM \n"
                        + "ndb_pdc_txn_master nptm_txn_master left join\n"
                        + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                        + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master inner join\n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers on \n"
                        + "nptm_txn_master.idndb_customer_define_buyer_id = nshb_seller_has_buyers.idndb_seller_has_buyers inner join\n"
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
                        + "ncd_cust_define_seller.idndb_branch_master_file = nbmf_branch_master.idndb_branch_master_file where\n"
                        + "nptm_txn_master.pdc_chq_status_auth='" + status + "' and\n"
                        + "nptm_txn_master.pdc_chq_status='ACTIVE'";

            } else {
                m_strQry = "SELECT \n"
                        + "nptm_txn_master.* ,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                        + "ngmmf_geo_market.geo_market_desc,\n"
                        + "nbmf_branch_master.branch_name\n"
                        + "FROM \n"
                        + "ndb_pdc_txn_master nptm_txn_master left join\n"
                        + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                        + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master inner join\n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers on \n"
                        + "nptm_txn_master.idndb_customer_define_buyer_id = nshb_seller_has_buyers.idndb_seller_has_buyers inner join\n"
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
                        + "ncd_cust_define_seller.idndb_branch_master_file = nbmf_branch_master.idndb_branch_master_file where\n"
                        + "nptm_txn_master.pdc_chq_status='" + status + "'";

            }

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                m_jsObj = new JSONObject();
                String current_status = "";
                String pdc_chq_status = _rs.getString("pdc_chq_status");
                String pdc_chq_status_auth = _rs.getString("pdc_chq_status_auth");

                if (pdc_chq_status.equals("ERLYLIQUDED") && pdc_chq_status_auth.equals("AUTHORIZED")) {
                    current_status = "EARLY LIQD (AUTHORIZED)";

                }

                if (pdc_chq_status.equals("ERLYLIQUDED") && pdc_chq_status_auth.equals("UN-AUTHORIZED")) {
                    current_status = "EARLY LIQD (UN-AUTHORIZED)";

                }

                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("AUTHORIZED")) {
                    current_status = "ACTIVE (AUTHORIZED)";

                }
                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("UN-AUTHORIZED")) {
                    current_status = "ACTIVE (UN-AUTHORIZED)";

                }
                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("REJECTED")) {
                    current_status = "ACTIVE (REJECTED)";

                }

                if (pdc_chq_status.equals("RETURNED")) {
                    current_status = "RETURNED";

                }

                if (pdc_chq_status.equals("PROCESSED")) {
                    current_status = "CLEARED";

                }

                String cust_name = _rs.getString("buyer_cust_name");
                String cust_id = _rs.getString("buyer_cust_id");

                String cust_name_seller = _rs.getString("seller_cust_name");
                String cust_id_seller = _rs.getString("seller_cust_id");

                String seller_officer = _rs.getString("geo_market_desc");

                DecimalFormat df = new DecimalFormat("#,###.00");

                m_jsObj.put("pdc_req_financing", _rs.getString("pdc_req_financing"));
                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);
                m_jsObj.put("seller_officer", seller_officer);
                m_jsObj.put("cust_id", cust_id);
                m_jsObj.put("cust_name", cust_name);
                m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                m_jsObj.put("pdc_chq_discounting_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_discounting_amu"))));
                m_jsObj.put("current_status", current_status);
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

                if (_prepStmnt2 != null) {
                    _prepStmnt2.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_pdc_cw_seller_wise_buyer_facility(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        String m_strQry2 = "";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {

            DecimalFormat df = new DecimalFormat("#,###.00");
            String idndb_customer_define_seller_id = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");

            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                        + "nshb_seller_has_buyers.sl_has_byr_warehs_limit,\n"
                        + "ncw_chq_wh.chq_wh_limit,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                        + "ngmmf_geo_market_file.geo_market_desc\n"
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
                        + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                        + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
                        + "ndb_chq_wh ncw_chq_wh on\n"
                        + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = ncw_chq_wh.idndb_cust_prod_map where \n"
                        + "nshb_seller_has_buyers.sl_has_byr_prorm_type='CW' and nshb_seller_has_buyers.sl_has_byr_status='ACTIVE'";
            } else {
                m_strQry = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                        + "nshb_seller_has_buyers.sl_has_byr_warehs_limit,\n"
                        + "ncw_chq_wh.chq_wh_limit,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                        + "ngmmf_geo_market_file.geo_market_desc\n"
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
                        + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                        + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
                        + "ndb_chq_wh ncw_chq_wh on\n"
                        + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = ncw_chq_wh.idndb_cust_prod_map where \n"
                        + "nshb_seller_has_buyers.sl_has_byr_prorm_type='CW' and nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_customer_define_seller_id + "'";

            }

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                m_jsObj = new JSONObject();

                String idndb_customer_define_seller = _rs.getString("idndb_customer_define_seller");
                String idndb_seller_has_buyers = _rs.getString("idndb_seller_has_buyers");
                String cust_name_buyer = _rs.getString("buyer_cust_name");
                String cust_id_buyer = _rs.getString("buyer_cust_id");
                String cust_name_seller = _rs.getString("seller_cust_name");
                String cust_id_seller = _rs.getString("seller_cust_id");

                double sl_has_byr_warehs_limit_percentage = _rs.getDouble("sl_has_byr_warehs_limit");
                double cw_seller_limit = _rs.getDouble("chq_wh_limit");

                //adding temporary limit feature - CFU-BRD-4 - Janaka_5977
                cw_seller_limit += getTotCWTemporaryLimits(idndb_customer_define_seller);

                double sl_has_byr_warehs_limit = sl_has_byr_warehs_limit_percentage / 100 * cw_seller_limit;

                String seller_officer = _rs.getString("geo_market_desc");

                m_strQry2 = "select SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + idndb_seller_has_buyers + "' and idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED')";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                double pdc_chq_amu = 0.00;
                double available_amu = 0.00;

                if (_rs2.next()) {

                    pdc_chq_amu = _rs2.getDouble("pdc_chq_amu");
                    available_amu = sl_has_byr_warehs_limit - pdc_chq_amu;

                }

                m_jsObj.put("cust_name_seller", cust_name_seller);
                m_jsObj.put("cust_id_seller", cust_id_seller);

                m_jsObj.put("cust_name_buyer", cust_name_buyer);
                m_jsObj.put("cust_id_buyer", cust_id_buyer);
                m_jsObj.put("seller_officer", seller_officer);

                m_jsObj.put("sl_has_byr_warehs_limit_percentage", df.format(sl_has_byr_warehs_limit_percentage));
                m_jsObj.put("sl_has_byr_warehs_limit", df.format(sl_has_byr_warehs_limit));
                m_jsObj.put("pdc_chq_amu", df.format(pdc_chq_amu));
                m_jsObj.put("available_amu", df.format(available_amu));
                m_jsArr.put(i, m_jsObj);
                i++;

            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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

    public JSONArray get_pdc_cw_seller_wise_facility(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        String m_strQry2 = "";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {

            String idndb_cust_prod_map = "";

            String idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT \n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map,\n"
                        + "ncpm_cust_prod_map.idndb_customer_define,\n"
                        + "ncd_cust_define.cust_name,\n"
                        + "ncd_cust_define.cust_id,\n"
                        + "ncw_chq_wh.chq_wh_limit,\n"
                        + "ngmmf_geo_market_file.geo_market_desc\n"
                        + "FROM \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map inner join\n"
                        + "ndb_customer_define ncd_cust_define on\n"
                        + "ncpm_cust_prod_map.idndb_customer_define = ncd_cust_define.idndb_customer_define left join\n"
                        + "ndb_chq_wh ncw_chq_wh on\n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map = ncw_chq_wh.idndb_cust_prod_map left join\n"
                        + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                        + "ncd_cust_define.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file\n"
                        + "where \n"
                        + "ncpm_cust_prod_map.prod_relationship_chq_ware='ACTIVE'";

            } else {
                m_strQry = "SELECT \n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map,\n"
                        + "ncpm_cust_prod_map.idndb_customer_define,\n"
                        + "ncd_cust_define.cust_name,\n"
                        + "ncd_cust_define.cust_id,\n"
                        + "ncw_chq_wh.chq_wh_limit,\n"
                        + "ngmmf_geo_market_file.geo_market_desc\n"
                        + "FROM \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map inner join\n"
                        + "ndb_customer_define ncd_cust_define on\n"
                        + "ncpm_cust_prod_map.idndb_customer_define = ncd_cust_define.idndb_customer_define left join\n"
                        + "ndb_chq_wh ncw_chq_wh on\n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map = ncw_chq_wh.idndb_cust_prod_map left join\n"
                        + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                        + "ncd_cust_define.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file\n"
                        + "where \n"
                        + "ncpm_cust_prod_map.prod_relationship_chq_ware='ACTIVE' and\n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map='" + idndb_customer_define_seller_id + "'";

            }

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                m_jsObj = new JSONObject();
                idndb_cust_prod_map = _rs.getString("idndb_cust_prod_map");
                String cust_name_seller = _rs.getString("cust_name");
                String cust_id_seller = _rs.getString("cust_id");
                String seller_officer = _rs.getString("geo_market_desc");
                double seller_chq_ware_housing_limit = _rs.getDouble("chq_wh_limit");
                double seller_chq_ware_housing_lavailability = 0.00;
                double total_seller_outstanding = 0.00;

                seller_chq_ware_housing_limit += getTotCWTemporaryLimits(idndb_customer_define_seller_id);

                String idndb_customer_define_seller = idndb_cust_prod_map;

                m_strQry2 = "select \n"
                        + "SUM(pdc_chq_amu) as pdc_chq_amu \n"
                        + "from ndb_pdc_txn_master \n"
                        + "where idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and \n"
                        + "pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and \n"
                        + "pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED') and \n"
                        + "pdc_req_financing='CW'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();

                if (_rs2.next()) {
                    total_seller_outstanding = _rs2.getDouble("pdc_chq_amu");
                }
                seller_chq_ware_housing_lavailability = seller_chq_ware_housing_limit - total_seller_outstanding;

                m_jsObj.put("idndb_cust_prod_map", idndb_cust_prod_map);
                m_jsObj.put("cust_id_seller", cust_name_seller);
                m_jsObj.put("seller_officer", seller_officer);
                m_jsObj.put("cust_name_seller", cust_id_seller);
                m_jsObj.put("seller_chq_ware_housing_limit", df.format(seller_chq_ware_housing_limit));
                m_jsObj.put("total_seller_outstanding", df.format(total_seller_outstanding));
                m_jsObj.put("seller_chq_ware_housing_lavailability", df.format(seller_chq_ware_housing_lavailability));
                m_jsArr.put(i, m_jsObj);
                i++;

            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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

    public JSONArray get_pdc_rf_seller_wise_facility(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        String m_strQry2 = "";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {

            DecimalFormat df = new DecimalFormat("#,###.00");

            String idndb_customer_define = "";
            String idndb_cust_prod_map = "";
            String idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT \n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map,\n"
                        + "ncpm_cust_prod_map.idndb_customer_define,\n"
                        + "ncd_cust_define.cust_name,\n"
                        + "ncd_cust_define.cust_id,\n"
                        + "nrf_rec_fin.rec_finance_limit,\n"
                        + "ngmmf_geo_market_file.geo_market_desc\n"
                        + "FROM \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map inner join\n"
                        + "ndb_customer_define ncd_cust_define on\n"
                        + "ncpm_cust_prod_map.idndb_customer_define = ncd_cust_define.idndb_customer_define left join\n"
                        + "ndb_rec_fin nrf_rec_fin on\n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map = nrf_rec_fin.idndb_cust_prod_map left join\n"
                        + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                        + "ncd_cust_define.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file\n"
                        + "where ncpm_cust_prod_map.prod_relationship_res_fin='ACTIVE'";

            } else {
                m_strQry = "SELECT \n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map,\n"
                        + "ncpm_cust_prod_map.idndb_customer_define,\n"
                        + "ncd_cust_define.cust_name,\n"
                        + "ncd_cust_define.cust_id,\n"
                        + "nrf_rec_fin.rec_finance_limit,\n"
                        + "ngmmf_geo_market_file.geo_market_desc\n"
                        + "FROM \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map inner join\n"
                        + "ndb_customer_define ncd_cust_define on\n"
                        + "ncpm_cust_prod_map.idndb_customer_define = ncd_cust_define.idndb_customer_define left join\n"
                        + "ndb_rec_fin nrf_rec_fin on\n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map = nrf_rec_fin.idndb_cust_prod_map left join\n"
                        + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                        + "ncd_cust_define.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file\n"
                        + "where \n"
                        + "ncpm_cust_prod_map.prod_relationship_res_fin='ACTIVE' and\n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map='" + idndb_customer_define_seller_id + "'";

            }

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                m_jsObj = new JSONObject();

                idndb_customer_define = _rs.getString("idndb_customer_define");
                idndb_cust_prod_map = _rs.getString("idndb_cust_prod_map");
                String cust_name_seller = _rs.getString("cust_name");
                String cust_id_seller = _rs.getString("cust_id");
                String seller_officer = _rs.getString("geo_market_desc");
                double rec_finance_limit = _rs.getDouble("rec_finance_limit");
                double seller_rec_finance_limit_lavailability = 0.00;

                rec_finance_limit += getTotRFTemporaryLimits(idndb_customer_define_seller_id);

                String idndb_customer_define_seller = idndb_cust_prod_map;

                m_strQry2 = "select \n"
                        + "SUM(pdc_chq_discounting_amu) as pdc_chq_amu \n"
                        + "from ndb_pdc_txn_master \n"
                        + "where idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and \n"
                        + "pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and \n"
                        + "pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED') and \n"
                        + "pdc_req_financing='RF'";

                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();

                double total_seller_outstanding = 0.00;
                if (_rs2.next()) {
                    total_seller_outstanding = _rs2.getDouble("pdc_chq_amu");
                }

                seller_rec_finance_limit_lavailability = rec_finance_limit - total_seller_outstanding;
                m_jsObj.put("idndb_cust_prod_map", idndb_cust_prod_map);
                m_jsObj.put("cust_id_seller", cust_name_seller);
                m_jsObj.put("cust_name_seller", cust_id_seller);
                m_jsObj.put("seller_officer", seller_officer);
                m_jsObj.put("rec_finance_limit", df.format(rec_finance_limit));
                m_jsObj.put("total_seller_outstanding", df.format(total_seller_outstanding));
                m_jsObj.put("seller_rec_finance_limit_lavailability", df.format(seller_rec_finance_limit_lavailability));
                m_jsArr.put(i, m_jsObj);
                i++;

            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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

    public JSONArray get_pdc_rf_rurned_anal(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        String m_strQry2 = "";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {

            String idndb_cust_prod_map = "";
            String date_from = prm_obj.getString("date_from");
            String idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            String date_to = prm_obj.getString("date_to");

            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT \n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map,\n"
                        + "ncpm_cust_prod_map.idndb_customer_define,cms_collection_acc_number,\n"
                        + "ncd_cust_define.cust_id,\n"
                        + "ncd_cust_define.cust_name,\n"
                        + "ngmmf_geomark_file.geo_market_desc\n"
                        + "FROM \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map inner join\n"
                        + "ndb_customer_define ncd_cust_define on\n"
                        + "ncpm_cust_prod_map.idndb_customer_define = ncd_cust_define.idndb_customer_define left join\n"
                        + "ndb_geo_market_master_file ngmmf_geomark_file on\n"
                        + "ncd_cust_define.idndb_geo_market_master_file = ngmmf_geomark_file.idndb_geo_market_master_file\n"
                        + "where \n"
                        + "ncpm_cust_prod_map.prod_relationship_status='ACTIVE' and \n"
                        + "ncpm_cust_prod_map.prod_relationship_auth='AUTHORIZED' and \n"
                        + "ncpm_cust_prod_map.prod_relationship_res_fin='ACTIVE'";

            } else {

                m_strQry = "SELECT \n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map,\n"
                        + "ncpm_cust_prod_map.idndb_customer_define,cms_collection_acc_number,\n"
                        + "ncd_cust_define.cust_id,\n"
                        + "ncd_cust_define.cust_name,\n"
                        + "ngmmf_geomark_file.geo_market_desc\n"
                        + "FROM \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map inner join\n"
                        + "ndb_customer_define ncd_cust_define on\n"
                        + "ncpm_cust_prod_map.idndb_customer_define = ncd_cust_define.idndb_customer_define left join\n"
                        + "ndb_geo_market_master_file ngmmf_geomark_file on\n"
                        + "ncd_cust_define.idndb_geo_market_master_file = ngmmf_geomark_file.idndb_geo_market_master_file\n"
                        + "where \n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map='" + idndb_customer_define_seller_id + "' and \n"
                        + "ncpm_cust_prod_map.prod_relationship_status='ACTIVE' and \n"
                        + "ncpm_cust_prod_map.prod_relationship_auth='AUTHORIZED' and \n"
                        + "ncpm_cust_prod_map.prod_relationship_res_fin='ACTIVE'";

            }
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();

            while (_rs.next()) {

                m_jsObj = new JSONObject();
                idndb_cust_prod_map = _rs.getString("idndb_cust_prod_map");
                String cust_name_seller = _rs.getString("cust_name");
                String cust_id_seller = _rs.getString("cust_id");

                String seller_officer = _rs.getString("geo_market_desc");

                double sent_to_clearing = 0.00;
                double sent_to_present = 0.00;
                double return_from_clearing = 0.00;
                double processed_chqs = 0.00;
                double return_from_present = 0.00;
                // CUSTOMER DATA

                //ISSUE ID          : SIR A-8283
                //ISSUE DESCRIPTION : Cheque Return Analysis Report is Incorrect for RF & CW ( BW & SW)
                //MODIFIED BY       : Madhawa_4799
                //CHANGE            : Selection area changed, pdc_value_date changed to pdc_old_value_date
                // 2019/09/17 This report incorrect. Above change revised as per the instructions given by rukmal and Shlini(Tested). 
                m_strQry2 = "SELECT SUM(pdc_chq_amu) as pdc_chq_amu FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_cust_prod_map + "' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and pdc_chq_status in ('PROCESSED','RETURNED') and pdc_req_financing='RF'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {

                    if (!(_rs2.getString("pdc_chq_amu") == null)) {
                        sent_to_clearing = Double.parseDouble(_rs2.getString("pdc_chq_amu"));
                    }

                }
                m_strQry2 = "SELECT SUM(pdc_chq_amu) as pdc_chq_amu FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_cust_prod_map + "' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and pdc_chq_status in ('RETURNED') and pdc_req_financing='RF'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {

                    if (!(_rs2.getString("pdc_chq_amu") == null)) {
                        return_from_clearing = Double.parseDouble(_rs2.getString("pdc_chq_amu"));
                    }

                }
                processed_chqs = sent_to_clearing - return_from_clearing;

                if (!(processed_chqs == 0.00 && sent_to_clearing == 0.00)) {
                    sent_to_present = processed_chqs / sent_to_clearing * 100;
                }

                if (!(return_from_clearing == 0.00 && sent_to_clearing == 0.00)) {
                    return_from_present = return_from_clearing / sent_to_clearing * 100;
                }

                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);
                m_jsObj.put("seller_officer", seller_officer);
                m_jsObj.put("sent_to_clearing", df.format(sent_to_clearing));
                m_jsObj.put("return_from_clearing", df.format(return_from_clearing));
                m_jsObj.put("processed_chqs", df.format(processed_chqs));
                m_jsObj.put("sent_to_present", df.format(sent_to_present));
                m_jsObj.put("return_from_present", df.format(return_from_present));
                m_jsArr.put(i, m_jsObj);
                i++;

            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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

    public JSONArray get_pdc_rf_rurned_anal_bw(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        String m_strQry2 = "";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {
            log.info("get_pdc_rf_rurned_anal_bw request received");

            String idndb_seller_has_buyers_id_sl_has_byr = "";
            String date_from = prm_obj.getString("date_from");
            String date_to = prm_obj.getString("date_to");
            String idndb_customer_define_buyer_id = prm_obj.getString("idndb_customer_define_buyer_id");
            String idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");

            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                        + "ncd_cust_define_seller.cust_status as cust_status,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                        + "ngmmf_geo_market_file.geo_market_desc \n"
                        + "FROM \n"
                        + "ndb_pdc_txn_master nptm_txn_master inner join\n"
                        + "ndb_seller_has_buyers nshb_seller_has_buyers on\n"
                        + "nptm_txn_master.idndb_customer_define_buyer_id = nshb_seller_has_buyers.idndb_seller_has_buyers inner join \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map_seller on\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller = ncpm_cust_prod_map_seller.idndb_cust_prod_map inner join\n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
                        + "ndb_customer_define ncd_cust_define_seller on\n"
                        + "ncpm_cust_prod_map_seller.idndb_customer_define =ncd_cust_define_seller.idndb_customer_define inner join\n"
                        + "ndb_customer_define ncd_cust_define_buyer on\n"
                        + "ncpm_cust_prod_map_buyer.idndb_customer_define =ncd_cust_define_buyer.idndb_customer_define left join\n"
                        + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                        + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
                        + "ndb_rec_fin nrf_rec_fin on\n"
                        + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = nrf_rec_fin.idndb_cust_prod_map\n"
                        + "where \n"
                        + "STR_TO_DATE(nptm_txn_master.pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and \n"
                        + "nptm_txn_master.pdc_chq_status in ('PROCESSED','RETURNED') and nptm_txn_master.pdc_req_financing='RF' \n"
                        + "group by \n"
                        + "nptm_txn_master.idndb_customer_define_buyer_id";

            } else {
                if (idndb_customer_define_buyer_id.equals("all")) {

                    m_strQry = "SELECT \n"
                            + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                            + "ncd_cust_define_seller.cust_status as cust_status,\n"
                            + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                            + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                            + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                            + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                            + "ngmmf_geo_market_file.geo_market_desc \n"
                            + "FROM \n"
                            + "ndb_pdc_txn_master nptm_txn_master inner join\n"
                            + "ndb_seller_has_buyers nshb_seller_has_buyers on\n"
                            + "nptm_txn_master.idndb_customer_define_buyer_id = nshb_seller_has_buyers.idndb_seller_has_buyers inner join \n"
                            + "ndb_cust_prod_map ncpm_cust_prod_map_seller on\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_seller = ncpm_cust_prod_map_seller.idndb_cust_prod_map inner join\n"
                            + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
                            + "ndb_customer_define ncd_cust_define_seller on\n"
                            + "ncpm_cust_prod_map_seller.idndb_customer_define =ncd_cust_define_seller.idndb_customer_define inner join\n"
                            + "ndb_customer_define ncd_cust_define_buyer on\n"
                            + "ncpm_cust_prod_map_buyer.idndb_customer_define =ncd_cust_define_buyer.idndb_customer_define left join\n"
                            + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                            + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
                            + "ndb_rec_fin nrf_rec_fin on\n"
                            + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = nrf_rec_fin.idndb_cust_prod_map\n"
                            + "where \n"
                            + "STR_TO_DATE(nptm_txn_master.pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and \n"
                            + "nptm_txn_master.pdc_chq_status in ('PROCESSED','RETURNED') and nptm_txn_master.pdc_req_financing='RF' and nptm_txn_master.idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' group by nptm_txn_master.idndb_customer_define_buyer_id ";

//                    m_strQry = "SELECT \n"
//                            + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
//                            + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
//                            + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
//                            + "nshb_seller_has_buyers.shb_facty_det_crd_loam_limit,\n"
//                            + "nrf_rec_fin.rec_finance_limit,\n"
//                            + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
//                            + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
//                            + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
//                            + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
//                            + "ngmmf_geo_market_file.geo_market_desc\n"
//                            + "FROM \n"
//                            + "ndb_seller_has_buyers nshb_seller_has_buyers inner join \n"
//                            + "ndb_cust_prod_map ncpm_cust_prod_map_seller on\n"
//                            + "nshb_seller_has_buyers.idndb_customer_define_seller = ncpm_cust_prod_map_seller.idndb_cust_prod_map inner join\n"
//                            + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
//                            + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
//                            + "ndb_customer_define ncd_cust_define_seller on\n"
//                            + "ncpm_cust_prod_map_seller.idndb_customer_define =ncd_cust_define_seller.idndb_customer_define inner join\n"
//                            + "ndb_customer_define ncd_cust_define_buyer on\n"
//                            + "ncpm_cust_prod_map_buyer.idndb_customer_define =ncd_cust_define_buyer.idndb_customer_define left join\n"
//                            + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
//                            + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
//                            + "ndb_rec_fin nrf_rec_fin on\n"
//                            + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = nrf_rec_fin.idndb_cust_prod_map where\n"
//                            + "nshb_seller_has_buyers.sl_has_byr_prorm_type='RF' and \n"
//                            + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_customer_define_seller_id + "' and \n"
//                            + "nshb_seller_has_buyers.sl_has_byr_status in ('ACTIVE','DEACTIVE') and \n"
//                            + "nshb_seller_has_buyers.sl_has_byr_auth='AUTHORIZED'";

                } else {
                    
                    
                     m_strQry = "SELECT \n"
                            + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                            + "ncd_cust_define_seller.cust_status as cust_status,\n"
                            + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                            + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                            + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                            + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                            + "ngmmf_geo_market_file.geo_market_desc \n"
                            + "FROM \n"
                            + "ndb_pdc_txn_master nptm_txn_master inner join\n"
                            + "ndb_seller_has_buyers nshb_seller_has_buyers on\n"
                            + "nptm_txn_master.idndb_customer_define_buyer_id = nshb_seller_has_buyers.idndb_seller_has_buyers inner join \n"
                            + "ndb_cust_prod_map ncpm_cust_prod_map_seller on\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_seller = ncpm_cust_prod_map_seller.idndb_cust_prod_map inner join\n"
                            + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
                            + "ndb_customer_define ncd_cust_define_seller on\n"
                            + "ncpm_cust_prod_map_seller.idndb_customer_define =ncd_cust_define_seller.idndb_customer_define inner join\n"
                            + "ndb_customer_define ncd_cust_define_buyer on\n"
                            + "ncpm_cust_prod_map_buyer.idndb_customer_define =ncd_cust_define_buyer.idndb_customer_define left join\n"
                            + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                            + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
                            + "ndb_rec_fin nrf_rec_fin on\n"
                            + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = nrf_rec_fin.idndb_cust_prod_map\n"
                            + "where \n"
                            + "STR_TO_DATE(nptm_txn_master.pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and \n"
                            + "nptm_txn_master.pdc_chq_status in ('PROCESSED','RETURNED') and nptm_txn_master.pdc_req_financing='RF' and nptm_txn_master.idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and nptm_txn_master.idndb_customer_define_buyer_id='"+idndb_customer_define_buyer_id+"' group by nptm_txn_master.idndb_customer_define_buyer_id  ";


//                    m_strQry = "SELECT \n"
//                            + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
//                            + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
//                            + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
//                            + "nshb_seller_has_buyers.shb_facty_det_crd_loam_limit,\n"
//                            + "nrf_rec_fin.rec_finance_limit,\n"
//                            + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
//                            + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
//                            + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
//                            + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
//                            + "ngmmf_geo_market_file.geo_market_desc\n"
//                            + "FROM \n"
//                            + "ndb_seller_has_buyers nshb_seller_has_buyers inner join \n"
//                            + "ndb_cust_prod_map ncpm_cust_prod_map_seller on\n"
//                            + "nshb_seller_has_buyers.idndb_customer_define_seller = ncpm_cust_prod_map_seller.idndb_cust_prod_map inner join\n"
//                            + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
//                            + "nshb_seller_has_buyers.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
//                            + "ndb_customer_define ncd_cust_define_seller on\n"
//                            + "ncpm_cust_prod_map_seller.idndb_customer_define =ncd_cust_define_seller.idndb_customer_define inner join\n"
//                            + "ndb_customer_define ncd_cust_define_buyer on\n"
//                            + "ncpm_cust_prod_map_buyer.idndb_customer_define =ncd_cust_define_buyer.idndb_customer_define left join\n"
//                            + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
//                            + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
//                            + "ndb_rec_fin nrf_rec_fin on\n"
//                            + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = nrf_rec_fin.idndb_cust_prod_map where\n"
//                            + "nshb_seller_has_buyers.sl_has_byr_prorm_type='RF' and \n"
//                            + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_customer_define_seller_id + "' and \n"
//                            + "nshb_seller_has_buyers.idndb_seller_has_buyers='" + idndb_customer_define_buyer_id + "' and \n"
//                            + "nshb_seller_has_buyers.sl_has_byr_status in ('ACTIVE','DEACTIVE') and \n"
//                            + "nshb_seller_has_buyers.sl_has_byr_auth='AUTHORIZED'";

                }
            }

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();

            log.info("quiery : " + m_strQry);
            log.info("executing quiery");
            log.info("cust_id_seller~cust_name_seller~seller_officer~cust_id_buyer~cust_name_buyer~sent_to_clearing~return_from_clearing~processed_chqs~sent_to_present~return_from_present");

            while (_rs.next()) {

                idndb_seller_has_buyers_id_sl_has_byr = _rs.getString("idndb_seller_has_buyers");

                String cust_name_seller_status = _rs.getString("cust_status");

                String cust_name_seller = _rs.getString("seller_cust_name");
                String cust_id_seller = _rs.getString("seller_cust_id");

                String cust_name_buyer = _rs.getString("buyer_cust_name");
                String cust_id_buyer = _rs.getString("buyer_cust_id");

                String seller_officer = _rs.getString("geo_market_desc");

                m_jsObj = new JSONObject();
                double sent_to_clearing = 0.00;
                double sent_to_present = 0.00;
                double return_from_clearing = 0.00;
                double processed_chqs = 0.00;
                double return_from_present = 0.00;

                //ISSUE ID          : SIR A-8283
                //ISSUE DESCRIPTION : Cheque Return Analysis Report is Incorrect for RF & CW ( BW & SW)
                //MODIFIED BY       : Madhawa_4799
                //CHANGE            : Selection area changed, pdc_value_date changed to pdc_old_value_date
                // 2019/09/17 This report incorrect. Above change revised as per the instructions given by rukmal and Shlini(Tested). 
                m_strQry2 = "SELECT (SELECT SUM(pdc_chq_amu) as pdc_chq_amu FROM ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + idndb_seller_has_buyers_id_sl_has_byr + "' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and pdc_chq_status in ('PROCESSED','RETURNED') and pdc_req_financing='RF') as total_pdc_amount,"
                        + "(SELECT SUM(pdc_chq_amu) as pdc_chq_amu FROM ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + idndb_seller_has_buyers_id_sl_has_byr + "' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and pdc_chq_status in ('RETURNED') and pdc_req_financing='RF') as total_return_pdc_amount";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {

                    if (!(_rs2.getString("total_pdc_amount") == null)) {
                        sent_to_clearing = Double.parseDouble(_rs2.getString("total_pdc_amount"));
                    }
                    if (!(_rs2.getString("total_return_pdc_amount") == null)) {
                        return_from_clearing = Double.parseDouble(_rs2.getString("total_return_pdc_amount"));
                    }

                }

                processed_chqs = sent_to_clearing - return_from_clearing;

                if (!(processed_chqs == 0.00 && sent_to_clearing == 0.00)) {
                    sent_to_present = processed_chqs / sent_to_clearing * 100;
                }

                if (!(return_from_clearing == 0.00 && sent_to_clearing == 0.00)) {
                    return_from_present = return_from_clearing / sent_to_clearing * 100;
                }

                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);
                m_jsObj.put("seller_officer", seller_officer);
                m_jsObj.put("cust_id_buyer", cust_id_buyer);
                m_jsObj.put("cust_name_buyer", cust_name_buyer);
                m_jsObj.put("sent_to_clearing", df.format(sent_to_clearing));
                m_jsObj.put("return_from_clearing", df.format(return_from_clearing));
                m_jsObj.put("processed_chqs", df.format(processed_chqs));
                m_jsObj.put("sent_to_present", df.format(sent_to_present));
                m_jsObj.put("return_from_present", df.format(return_from_present));
                m_jsArr.put(i, m_jsObj);
                if (i == 0) {
                    log.info("~Seller Name~Seller ID~Buyer Name~Buyer ID~Sent On Cle.~Cleared Amu.~Returned Amu.~Cleared %~Returned %~Relationship");
                }
                log.info("~" + i + "~" + cust_name_seller + "~" + cust_id_seller + "~" + cust_name_buyer + "~" + cust_id_buyer + "~" + df.format(sent_to_clearing) + "~" + df.format(processed_chqs) + "~" + df.format(return_from_clearing) + "~" + df.format(sent_to_present) + "~" + df.format(return_from_present) + "~" + seller_officer);

//                log.info("~" + i + "~" + cust_id_seller + "~" + cust_name_seller + "~" + seller_officer + "~" + cust_id_buyer + "~" + cust_name_buyer + "~" + df.format(sent_to_clearing) + "~" + df.format(return_from_clearing) + "~" + df.format(processed_chqs) + "~" + df.format(sent_to_present) + "~" + df.format(return_from_present));
                i++;

            }

            log.info("end of execution");

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
        log.info("return the responce");
        return m_jsArr;
    }

    public String downloadChequeRetAnalysisBw(JSONObject prm_obj) {

        String m_strQry = "";
        String m_strQry2 = "";
        String result = "fail";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {
            log.info("get_pdc_rf_rurned_anal_bw request received");

            String idndb_seller_has_buyers_id_sl_has_byr = "";
            String date_from = prm_obj.getString("date_from");
            String date_to = prm_obj.getString("date_to");
            String idndb_customer_define_buyer_id = prm_obj.getString("idndb_customer_define_buyer_id");
            String idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");

            String filename = idndb_customer_define_buyer_id + "_" + idndb_customer_define_seller_id + ".txt";
            ResourceBundle rb = ResourceBundle.getBundle("System", Locale.getDefault());
            String folserpath = rb.getString("GEFUDir");
            File file_dir = new File(folserpath);
            if (!file_dir.exists()) {
                if (file_dir.mkdir()) {

                } else {

                }
            }
            File file = new File(folserpath + "\\" + filename);
            FileOutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                        + "nshb_seller_has_buyers.shb_facty_det_crd_loam_limit,\n"
                        + "nrf_rec_fin.rec_finance_limit,\n"
                        + "ncd_cust_define_seller.cust_status as cust_status,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                        + "ngmmf_geo_market_file.geo_market_desc\n"
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
                        + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                        + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
                        + "ndb_rec_fin nrf_rec_fin on\n"
                        + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = nrf_rec_fin.idndb_cust_prod_map where\n"
                        + "nshb_seller_has_buyers.sl_has_byr_prorm_type='RF' and \n"
                        + "nshb_seller_has_buyers.sl_has_byr_status='ACTIVE' and \n"
                        + "nshb_seller_has_buyers.sl_has_byr_auth='AUTHORIZED'";

            } else {
                if (idndb_customer_define_buyer_id.equals("all")) {
                    m_strQry = "SELECT \n"
                            + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                            + "nshb_seller_has_buyers.shb_facty_det_crd_loam_limit,\n"
                            + "nrf_rec_fin.rec_finance_limit,\n"
                            + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                            + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                            + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                            + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                            + "ngmmf_geo_market_file.geo_market_desc\n"
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
                            + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                            + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
                            + "ndb_rec_fin nrf_rec_fin on\n"
                            + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = nrf_rec_fin.idndb_cust_prod_map where\n"
                            + "nshb_seller_has_buyers.sl_has_byr_prorm_type='RF' and \n"
                            + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_customer_define_seller_id + "' and \n"
                            + "nshb_seller_has_buyers.sl_has_byr_status in ('ACTIVE','DEACTIVE') and \n"
                            + "nshb_seller_has_buyers.sl_has_byr_auth='AUTHORIZED'";

                } else {

                    m_strQry = "SELECT \n"
                            + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                            + "nshb_seller_has_buyers.shb_facty_det_crd_loam_limit,\n"
                            + "nrf_rec_fin.rec_finance_limit,\n"
                            + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                            + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                            + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                            + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                            + "ngmmf_geo_market_file.geo_market_desc\n"
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
                            + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                            + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
                            + "ndb_rec_fin nrf_rec_fin on\n"
                            + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = nrf_rec_fin.idndb_cust_prod_map where\n"
                            + "nshb_seller_has_buyers.sl_has_byr_prorm_type='RF' and \n"
                            + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_customer_define_seller_id + "' and \n"
                            + "nshb_seller_has_buyers.idndb_seller_has_buyers='" + idndb_customer_define_buyer_id + "' and \n"
                            + "nshb_seller_has_buyers.sl_has_byr_status in ('ACTIVE','DEACTIVE') and \n"
                            + "nshb_seller_has_buyers.sl_has_byr_auth='AUTHORIZED'";

                }
            }

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();

            log.info("quiery : " + m_strQry);
            log.info("executing quiery");
            log.info("cust_id_seller~cust_name_seller~seller_officer~cust_id_buyer~cust_name_buyer~sent_to_clearing~return_from_clearing~processed_chqs~sent_to_present~return_from_present");

            while (_rs.next()) {

                idndb_seller_has_buyers_id_sl_has_byr = _rs.getString("idndb_seller_has_buyers");

                String cust_name_seller_status = _rs.getString("cust_status");

                String cust_name_seller = _rs.getString("seller_cust_name");
                String cust_id_seller = _rs.getString("seller_cust_id");

                String cust_name_buyer = _rs.getString("buyer_cust_name");
                String cust_id_buyer = _rs.getString("buyer_cust_id");

                String seller_officer = _rs.getString("geo_market_desc");

                m_jsObj = new JSONObject();
                double sent_to_clearing = 0.00;
                double sent_to_present = 0.00;
                double return_from_clearing = 0.00;
                double processed_chqs = 0.00;
                double return_from_present = 0.00;

                //ISSUE ID          : SIR A-8283
                //ISSUE DESCRIPTION : Cheque Return Analysis Report is Incorrect for RF & CW ( BW & SW)
                //MODIFIED BY       : Madhawa_4799
                //CHANGE            : Selection area changed, pdc_value_date changed to pdc_old_value_date
                // 2019/09/17 This report incorrect. Above change revised as per the instructions given by rukmal and Shlini(Tested). 
                m_strQry2 = "SELECT (SELECT SUM(pdc_chq_amu) as pdc_chq_amu FROM ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + idndb_seller_has_buyers_id_sl_has_byr + "' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and pdc_chq_status in ('PROCESSED','RETURNED') and pdc_req_financing='RF') as total_pdc_amount,"
                        + "(SELECT SUM(pdc_chq_amu) as pdc_chq_amu FROM ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + idndb_seller_has_buyers_id_sl_has_byr + "' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and pdc_chq_status in ('RETURNED') and pdc_req_financing='RF') as total_return_pdc_amount";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {

                    if (!(_rs2.getString("total_pdc_amount") == null)) {
                        sent_to_clearing = Double.parseDouble(_rs2.getString("total_pdc_amount"));
                    }
                    if (!(_rs2.getString("total_return_pdc_amount") == null)) {
                        return_from_clearing = Double.parseDouble(_rs2.getString("total_return_pdc_amount"));
                    }

                }

                processed_chqs = sent_to_clearing - return_from_clearing;

                if (!(processed_chqs == 0.00 && sent_to_clearing == 0.00)) {
                    sent_to_present = processed_chqs / sent_to_clearing * 100;
                }

                if (!(return_from_clearing == 0.00 && sent_to_clearing == 0.00)) {
                    return_from_present = return_from_clearing / sent_to_clearing * 100;
                }

                String mLine = "";

                if (i == 0) {
                    mLine = "Seller Name~Seller ID~Buyer Name~Buyer ID~Sent On Cle.~Cleared Amu.~Returned Amu.~Cleared %~Returned %~Relationship";
                    bufferedWriter.write(mLine);
                    bufferedWriter.newLine();
                }

                mLine = cust_name_seller + "~" + cust_id_seller + "~" + cust_name_buyer + "~" + cust_id_buyer + "~" + df.format(sent_to_clearing) + "~" + df.format(processed_chqs) + "~" + df.format(return_from_clearing) + "~" + df.format(sent_to_present) + "~" + df.format(return_from_present) + "~" + seller_officer;
                bufferedWriter.write(mLine);
                bufferedWriter.newLine();
                log.info(i + "~" + mLine);
                i++;

            }
            bufferedWriter.close();
            result = "success=" + folserpath;
            log.info("end of execution");

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
        log.info("return the responce");
        return result;
    }

    public JSONArray get_pdc_cw_rurned_anal_bw(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        String m_strQry2 = "";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {

            String idndb_seller_has_buyers_id_sl_has_byr = "";
            String date_from = prm_obj.getString("date_from");
            String date_to = prm_obj.getString("date_to");
            String idndb_customer_define_buyer_id = prm_obj.getString("idndb_customer_define_buyer_id");
            String idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");

            if (idndb_customer_define_seller_id.equals("all")) {

                m_strQry = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                        + "nshb_seller_has_buyers.sl_has_byr_warehs_limit,\n"
                        + "ncw_chq_wh.chq_wh_limit,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                        + "ngmmf_geo_market_file.geo_market_desc\n"
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
                        + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                        + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
                        + "ndb_chq_wh ncw_chq_wh on\n"
                        + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = ncw_chq_wh.idndb_cust_prod_map where \n"
                        + "nshb_seller_has_buyers.sl_has_byr_prorm_type='CW' and nshb_seller_has_buyers.sl_has_byr_status='ACTIVE' and nshb_seller_has_buyers.sl_has_byr_auth='AUTHORIZED'";

            } else {
                if (idndb_customer_define_buyer_id.equals("all")) {

                    m_strQry = "SELECT \n"
                            + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                            + "nshb_seller_has_buyers.sl_has_byr_warehs_limit,\n"
                            + "ncw_chq_wh.chq_wh_limit,\n"
                            + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                            + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                            + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                            + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                            + "ngmmf_geo_market_file.geo_market_desc\n"
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
                            + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                            + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
                            + "ndb_chq_wh ncw_chq_wh on\n"
                            + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = ncw_chq_wh.idndb_cust_prod_map where \n"
                            + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_customer_define_seller_id + "' and \n"
                            + "nshb_seller_has_buyers.sl_has_byr_prorm_type='CW' and \n"
                            + "nshb_seller_has_buyers.sl_has_byr_status in ('ACTIVE','DEACTIVE') and \n"
                            + "nshb_seller_has_buyers.sl_has_byr_auth='AUTHORIZED'";

                } else {

                    m_strQry = "SELECT \n"
                            + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                            + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                            + "nshb_seller_has_buyers.sl_has_byr_warehs_limit,\n"
                            + "ncw_chq_wh.chq_wh_limit,\n"
                            + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                            + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                            + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                            + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                            + "ngmmf_geo_market_file.geo_market_desc\n"
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
                            + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                            + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
                            + "ndb_chq_wh ncw_chq_wh on\n"
                            + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = ncw_chq_wh.idndb_cust_prod_map where \n"
                            + "nshb_seller_has_buyers.idndb_seller_has_buyers='" + idndb_customer_define_buyer_id + "' and \n"
                            + "nshb_seller_has_buyers.idndb_customer_define_seller='" + idndb_customer_define_seller_id + "' and \n"
                            + "nshb_seller_has_buyers.sl_has_byr_prorm_type='CW' and \n"
                            + "nshb_seller_has_buyers.sl_has_byr_status in ('ACTIVE','DEACTIVE') and \n"
                            + "nshb_seller_has_buyers.sl_has_byr_auth='AUTHORIZED'";

                }
            }

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                idndb_seller_has_buyers_id_sl_has_byr = _rs.getString("idndb_seller_has_buyers");

                String cust_name_seller = _rs.getString("seller_cust_name");
                String cust_id_seller = _rs.getString("seller_cust_id");

                String cust_name_buyer = _rs.getString("buyer_cust_name");
                String cust_id_buyer = _rs.getString("buyer_cust_id");

                String seller_officer = _rs.getString("geo_market_desc");

                m_jsObj = new JSONObject();
                double sent_to_clearing = 0.00;
                double sent_to_present = 0.00;
                double return_from_clearing = 0.00;
                double processed_chqs = 0.00;
                double return_from_present = 0.00;

                //ISSUE ID          : SIR A-8283
                //ISSUE DESCRIPTION : Cheque Return Analysis Report is Incorrect for RF & CW ( BW & SW)
                //MODIFIED BY       : Madhawa_4799
                //CHANGE            : Selection area changed, pdc_value_date changed to pdc_old_value_date
                // 2019/09/17 This report incorrect. Above change revised as per the instructions given by rukmal and Shlini(Tested). 
                m_strQry2 = "SELECT SUM(pdc_chq_amu) as pdc_chq_amu FROM ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + idndb_seller_has_buyers_id_sl_has_byr + "' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and pdc_chq_status in ('PROCESSED','RETURNED') and pdc_req_financing='CW'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {

                    if (!(_rs2.getString("pdc_chq_amu") == null)) {
                        sent_to_clearing = Double.parseDouble(_rs2.getString("pdc_chq_amu"));
                    }

                }
                m_strQry2 = "SELECT SUM(pdc_chq_amu) as pdc_chq_amu FROM ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + idndb_seller_has_buyers_id_sl_has_byr + "' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and pdc_chq_status in ('RETURNED') and pdc_req_financing='CW'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {

                    if (!(_rs2.getString("pdc_chq_amu") == null)) {
                        return_from_clearing = Double.parseDouble(_rs2.getString("pdc_chq_amu"));
                    }

                }

                processed_chqs = sent_to_clearing - return_from_clearing;

                if (!(processed_chqs == 0.00 && sent_to_clearing == 0.00)) {
                    sent_to_present = processed_chqs / sent_to_clearing * 100;
                }

                if (!(return_from_clearing == 0.00 && sent_to_clearing == 0.00)) {
                    return_from_present = return_from_clearing / sent_to_clearing * 100;
                }

                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);
                m_jsObj.put("seller_officer", seller_officer);
                m_jsObj.put("cust_id_buyer", cust_id_buyer);
                m_jsObj.put("cust_name_buyer", cust_name_buyer);
                m_jsObj.put("sent_to_clearing", df.format(sent_to_clearing));
                m_jsObj.put("return_from_clearing", df.format(return_from_clearing));
                m_jsObj.put("processed_chqs", df.format(processed_chqs));
                m_jsObj.put("sent_to_present", df.format(sent_to_present));
                m_jsObj.put("return_from_present", df.format(return_from_present));
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

    public JSONArray get_pdc_cw_rurned_anal(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        String m_strQry2 = "";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            String idndb_customer_define = "";
            String idndb_cust_prod_map = "";
            String date_from = prm_obj.getString("date_from");
            String idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            String date_to = prm_obj.getString("date_to");

            if (idndb_customer_define_seller_id.equals("all")) {

                m_strQry = "SELECT \n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map,\n"
                        + "ncpm_cust_prod_map.idndb_customer_define,cms_collection_acc_number,\n"
                        + "ncd_cust_define.cust_id,\n"
                        + "ncd_cust_define.cust_name,\n"
                        + "ngmmf_geomark_file.geo_market_desc\n"
                        + "FROM \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map inner join\n"
                        + "ndb_customer_define ncd_cust_define on\n"
                        + "ncpm_cust_prod_map.idndb_customer_define = ncd_cust_define.idndb_customer_define left join\n"
                        + "ndb_geo_market_master_file ngmmf_geomark_file on\n"
                        + "ncd_cust_define.idndb_geo_market_master_file = ngmmf_geomark_file.idndb_geo_market_master_file\n"
                        + "where \n"
                        + "ncpm_cust_prod_map.prod_relationship_status='ACTIVE' and \n"
                        + "ncpm_cust_prod_map.prod_relationship_auth='AUTHORIZED' and \n"
                        + "ncpm_cust_prod_map.prod_relationship_chq_ware='ACTIVE'";

            } else {

                m_strQry = "SELECT \n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map,\n"
                        + "ncpm_cust_prod_map.idndb_customer_define,cms_collection_acc_number,\n"
                        + "ncd_cust_define.cust_id,\n"
                        + "ncd_cust_define.cust_name,\n"
                        + "ngmmf_geomark_file.geo_market_desc\n"
                        + "FROM \n"
                        + "ndb_cust_prod_map ncpm_cust_prod_map inner join\n"
                        + "ndb_customer_define ncd_cust_define on\n"
                        + "ncpm_cust_prod_map.idndb_customer_define = ncd_cust_define.idndb_customer_define left join\n"
                        + "ndb_geo_market_master_file ngmmf_geomark_file on\n"
                        + "ncd_cust_define.idndb_geo_market_master_file = ngmmf_geomark_file.idndb_geo_market_master_file\n"
                        + "where \n"
                        + "ncpm_cust_prod_map.idndb_cust_prod_map='" + idndb_customer_define_seller_id + "' and \n"
                        + "ncpm_cust_prod_map.prod_relationship_status='ACTIVE' and \n"
                        + "ncpm_cust_prod_map.prod_relationship_auth='AUTHORIZED' and \n"
                        + "ncpm_cust_prod_map.prod_relationship_chq_ware='ACTIVE'";

            }
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                m_jsObj = new JSONObject();
                idndb_customer_define = _rs.getString("idndb_customer_define");
                idndb_cust_prod_map = _rs.getString("idndb_cust_prod_map");
                String cust_name_seller = _rs.getString("cust_name");
                String cust_id_seller = _rs.getString("cust_id");
                String seller_officer = _rs.getString("geo_market_desc");

                double sent_to_clearing = 0.00;
                double sent_to_present = 0.00;
                double return_from_clearing = 0.00;
                double processed_chqs = 0.00;
                double return_from_present = 0.00;

                //ISSUE ID          : SIR A-8283
                //ISSUE DESCRIPTION : Cheque Return Analysis Report is Incorrect for RF & CW ( BW & SW)
                //MODIFIED BY       : Madhawa_4799
                //CHANGE            : Selection area changed, pdc_value_date changed to pdc_old_value_date
                // 2019/09/17 This report incorrect. Above change revised as per the instructions given by rukmal and Shlini(Tested). 
                m_strQry2 = "SELECT SUM(pdc_chq_amu) as pdc_chq_amu FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_cust_prod_map + "' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and pdc_chq_status in ('PROCESSED','RETURNED') and pdc_req_financing='CW'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {

                    if (!(_rs2.getString("pdc_chq_amu") == null)) {
                        sent_to_clearing = Double.parseDouble(_rs2.getString("pdc_chq_amu"));
                    }

                }
                m_strQry2 = "SELECT SUM(pdc_chq_amu) as pdc_chq_amu FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_cust_prod_map + "' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and pdc_chq_status in ('RETURNED') and pdc_req_financing='CW'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();
                if (_rs2.next()) {

                    if (!(_rs2.getString("pdc_chq_amu") == null)) {
                        return_from_clearing = Double.parseDouble(_rs2.getString("pdc_chq_amu"));
                    }

                }
                processed_chqs = sent_to_clearing - return_from_clearing;

                if (!(processed_chqs == 0.00 && sent_to_clearing == 0.00)) {
                    sent_to_present = processed_chqs / sent_to_clearing * 100;
                }

                if (!(return_from_clearing == 0.00 && sent_to_clearing == 0.00)) {
                    return_from_present = return_from_clearing / sent_to_clearing * 100;
                }

                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);
                m_jsObj.put("seller_officer", seller_officer);
                m_jsObj.put("sent_to_clearing", df.format(sent_to_clearing));
                m_jsObj.put("return_from_clearing", df.format(return_from_clearing));
                m_jsObj.put("processed_chqs", df.format(processed_chqs));
                m_jsObj.put("sent_to_present", df.format(sent_to_present));
                m_jsObj.put("return_from_present", df.format(return_from_present));
                m_jsArr.put(i, m_jsObj);
                i++;

            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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

    public JSONArray get_pdc_cw_commsion_chrges(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            String idndb_customer_define = "";
            String idndb_cust_prod_map = "";
            String date_from = prm_obj.getString("date_from");
            String idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            String date_to = prm_obj.getString("date_to");

            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT * FROM ndb_cust_prod_map where prod_relationship_status='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_chq_ware='ACTIVE'";

            } else {
                m_strQry = "SELECT * FROM ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller_id + "' and prod_relationship_status='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_chq_ware='ACTIVE'";

            }
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_jsObj = new JSONObject();
                idndb_customer_define = m_rs1.getString("idndb_customer_define");
                idndb_cust_prod_map = m_rs1.getString("idndb_cust_prod_map");
                String system_date = "";
                double scomm_chg = 0.00;
                // CUSTOMER DATA
                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name_seller = "";
                String cust_id_seller = "";
                if (m_rs2.next()) {

                    cust_name_seller = m_rs2.getString("cust_name");
                    cust_id_seller = m_rs2.getString("cust_id");
                }
                String seller_officer = "";
                m_strQry2 = "SELECT\n"
                        + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
                        + "     ndb_customer_define.`idndb_geo_market_master_file` AS ndb_customer_define_idndb_geo_market_master_file,\n"
                        + "     ndb_geo_market_master_file.`geo_market_id` AS ndb_geo_market_master_file_geo_market_id,\n"
                        + "     ndb_geo_market_master_file.`geo_market_desc` AS ndb_geo_market_master_file_geo_market_desc,\n"
                        + "     ndb_geo_market_master_file.`idndb_geo_market_master_file` AS ndb_geo_market_master_file_idndb_geo_market_master_file\n"
                        + "FROM\n"
                        + "     `ndb_geo_market_master_file` ndb_geo_market_master_file INNER JOIN `ndb_customer_define` ndb_customer_define ON ndb_geo_market_master_file.`idndb_geo_market_master_file` = ndb_customer_define.`idndb_geo_market_master_file` where ndb_customer_define.`idndb_customer_define`='" + idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    seller_officer = m_rs2.getString("ndb_geo_market_master_file_geo_market_desc");
                }

                m_strQry2 = "SELECT amount,system_date FROM gefu_file_generation_hst where idndb_pdc_txn_master='" + idndb_customer_define + "COMMCHRGCW" + "' and STR_TO_DATE(system_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and status ='AUTHORIZED' and gefu_type='COMCHGCD'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    if (!(m_rs2.getString("amount") == null)) {
                        scomm_chg = Double.parseDouble(m_rs2.getString("amount"));
                        system_date = m_rs2.getString("system_date");
                    }

                }

                m_strQry2 = "SELECT sum(gefu_file_generation_hst.`amount`) as amount,\n"
                        + "     ndb_pdc_txn_master.`idndb_pdc_txn_master` AS ndb_pdc_txn_master_idndb_pdc_txn_master,\n"
                        + "     ndb_pdc_txn_master.`pdc_req_financing` AS ndb_pdc_txn_master_pdc_req_financing,\n"
                        + "     ndb_pdc_txn_master.`idndb_customer_define_seller_id` AS ndb_pdc_txn_master_idndb_customer_define_seller_id,\n"
                        + "     gefu_file_generation_hst.`idgefu_file_generation` AS gefu_file_generation_idgefu_file_generation,\n"
                        + "     gefu_file_generation_hst.`idndb_pdc_txn_master` AS gefu_file_generation_idndb_pdc_txn_master,\n"
                        + "     gefu_file_generation_hst.`account` AS gefu_file_generation_account,\n"
                        + "     gefu_file_generation_hst.`currency` AS gefu_file_generation_currency,\n"
                        + "     gefu_file_generation_hst.`date` AS gefu_file_generation_date,\n"
                        + "     gefu_file_generation_hst.`amount` AS gefu_file_generation_amount,\n"
                        + "     gefu_file_generation_hst.`narration` AS gefu_file_generation_narration,\n"
                        + "     gefu_file_generation_hst.`credit_debit` AS gefu_file_generation_credit_debit,\n"
                        + "     gefu_file_generation_hst.`profit_centre` AS gefu_file_generation_profit_centre,\n"
                        + "     gefu_file_generation_hst.`DAO` AS gefu_file_generation_DAO,\n"
                        + "     gefu_file_generation_hst.`c_amount` AS gefu_file_generation_c_amount,\n"
                        + "     gefu_file_generation_hst.`d_amount` AS gefu_file_generation_d_amount,\n"
                        + "     gefu_file_generation_hst.`gefu_creation_status` AS gefu_file_generation_gefu_creation_status,\n"
                        + "     gefu_file_generation_hst.`status` AS gefu_file_generation_status,\n"
                        + "     gefu_file_generation_hst.`creat_by` AS gefu_file_generation_creat_by,\n"
                        + "     gefu_file_generation_hst.`creat_date` AS gefu_file_generation_creat_date,\n"
                        + "     gefu_file_generation_hst.`mod_by` AS gefu_file_generation_mod_by,\n"
                        + "     gefu_file_generation_hst.`mod_date` AS gefu_file_generation_mod_date,\n"
                        + "     gefu_file_generation_hst.`system_date` AS gefu_file_generation_system_date,\n"
                        + "     gefu_file_generation_hst.`cw_fixed_frequency` AS gefu_file_generation_cw_fixed_frequency,\n"
                        + "     gefu_file_generation_hst.`gefu_type` AS gefu_file_generation_gefu_type,\n"
                        + "     gefu_file_generation_hst.`bulk_credit` AS gefu_file_generation_bulk_credit\n"
                        + "FROM\n"
                        + "     `gefu_file_generation_hst` gefu_file_generation_hst INNER JOIN `ndb_pdc_txn_master` ndb_pdc_txn_master ON gefu_file_generation_hst.`idndb_pdc_txn_master` = ndb_pdc_txn_master.`idndb_pdc_txn_master` where ndb_pdc_txn_master.`idndb_customer_define_seller_id`='" + idndb_cust_prod_map + "' and ndb_pdc_txn_master.`pdc_req_financing`='CW' and gefu_file_generation_hst.`gefu_type` in ('VDEXTRCD','ERLEQDCD','CHQRETCHGCD') and gefu_file_generation_hst.`status`='AUTHORIZED' and gefu_file_generation_hst.`gefu_creation_status` ='PROCESSED' and STR_TO_DATE( gefu_file_generation_hst.`system_date`, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    if (!(m_rs2.getString("amount") == null)) {
                        scomm_chg = scomm_chg + Double.parseDouble(m_rs2.getString("amount"));
                    }

                }

                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);
                m_jsObj.put("scomm_chg", df.format(scomm_chg));
                m_jsObj.put("system_date", system_date);
                m_jsObj.put("seller_officer", seller_officer);
                m_jsArr.put(i, m_jsObj);
                i++;

            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_pdc_rf_commsion_chrges(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            String idndb_customer_define = "";
            String idndb_cust_prod_map = "";
            String date_from = prm_obj.getString("date_from");
            String idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            String date_to = prm_obj.getString("date_to");

            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT * FROM ndb_cust_prod_map where prod_relationship_status='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_res_fin='ACTIVE'";

            } else {
                m_strQry = "SELECT * FROM ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller_id + "' and prod_relationship_status='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_res_fin='ACTIVE'";

            }
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_jsObj = new JSONObject();
                idndb_customer_define = m_rs1.getString("idndb_customer_define");
                idndb_cust_prod_map = m_rs1.getString("idndb_cust_prod_map");
                String system_date = "";
                double scomm_chg = 0.00;
                // CUSTOMER DATA
                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name_seller = "";
                String cust_id_seller = "";
                if (m_rs2.next()) {

                    cust_name_seller = m_rs2.getString("cust_name");
                    cust_id_seller = m_rs2.getString("cust_id");
                }

                String seller_officer = "";
                m_strQry2 = "SELECT\n"
                        + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
                        + "     ndb_customer_define.`idndb_geo_market_master_file` AS ndb_customer_define_idndb_geo_market_master_file,\n"
                        + "     ndb_geo_market_master_file.`geo_market_id` AS ndb_geo_market_master_file_geo_market_id,\n"
                        + "     ndb_geo_market_master_file.`geo_market_desc` AS ndb_geo_market_master_file_geo_market_desc,\n"
                        + "     ndb_geo_market_master_file.`idndb_geo_market_master_file` AS ndb_geo_market_master_file_idndb_geo_market_master_file\n"
                        + "FROM\n"
                        + "     `ndb_geo_market_master_file` ndb_geo_market_master_file INNER JOIN `ndb_customer_define` ndb_customer_define ON ndb_geo_market_master_file.`idndb_geo_market_master_file` = ndb_customer_define.`idndb_geo_market_master_file` where ndb_customer_define.`idndb_customer_define`='" + idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {

                    seller_officer = m_rs2.getString("ndb_geo_market_master_file_geo_market_desc");
                }

                m_strQry2 = "SELECT amount,system_date FROM gefu_file_generation_hst where idndb_pdc_txn_master='" + idndb_customer_define + "COMMCHRGRF" + "' and STR_TO_DATE(system_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')  and status ='AUTHORIZED' and gefu_type in ('COMCHGCD')";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    if (!(m_rs2.getString("amount") == null)) {
                        scomm_chg = Double.parseDouble(m_rs2.getString("amount"));
                        system_date = m_rs2.getString("system_date");
                    }

                }

                m_strQry2 = "SELECT sum(gefu_file_generation_hst.`amount`) as amount,\n"
                        + "     ndb_pdc_txn_master.`idndb_pdc_txn_master` AS ndb_pdc_txn_master_idndb_pdc_txn_master,\n"
                        + "     ndb_pdc_txn_master.`pdc_req_financing` AS ndb_pdc_txn_master_pdc_req_financing,\n"
                        + "     ndb_pdc_txn_master.`idndb_customer_define_seller_id` AS ndb_pdc_txn_master_idndb_customer_define_seller_id,\n"
                        + "     gefu_file_generation_hst.`idgefu_file_generation` AS gefu_file_generation_idgefu_file_generation,\n"
                        + "     gefu_file_generation_hst.`idndb_pdc_txn_master` AS gefu_file_generation_idndb_pdc_txn_master,\n"
                        + "     gefu_file_generation_hst.`account` AS gefu_file_generation_account,\n"
                        + "     gefu_file_generation_hst.`currency` AS gefu_file_generation_currency,\n"
                        + "     gefu_file_generation_hst.`date` AS gefu_file_generation_date,\n"
                        + "     gefu_file_generation_hst.`amount` AS gefu_file_generation_amount,\n"
                        + "     gefu_file_generation_hst.`narration` AS gefu_file_generation_narration,\n"
                        + "     gefu_file_generation_hst.`credit_debit` AS gefu_file_generation_credit_debit,\n"
                        + "     gefu_file_generation_hst.`profit_centre` AS gefu_file_generation_profit_centre,\n"
                        + "     gefu_file_generation_hst.`DAO` AS gefu_file_generation_DAO,\n"
                        + "     gefu_file_generation_hst.`c_amount` AS gefu_file_generation_c_amount,\n"
                        + "     gefu_file_generation_hst.`d_amount` AS gefu_file_generation_d_amount,\n"
                        + "     gefu_file_generation_hst.`gefu_creation_status` AS gefu_file_generation_gefu_creation_status,\n"
                        + "     gefu_file_generation_hst.`status` AS gefu_file_generation_status,\n"
                        + "     gefu_file_generation_hst.`creat_by` AS gefu_file_generation_creat_by,\n"
                        + "     gefu_file_generation_hst.`creat_date` AS gefu_file_generation_creat_date,\n"
                        + "     gefu_file_generation_hst.`mod_by` AS gefu_file_generation_mod_by,\n"
                        + "     gefu_file_generation_hst.`mod_date` AS gefu_file_generation_mod_date,\n"
                        + "     gefu_file_generation_hst.`system_date` AS gefu_file_generation_system_date,\n"
                        + "     gefu_file_generation_hst.`cw_fixed_frequency` AS gefu_file_generation_cw_fixed_frequency,\n"
                        + "     gefu_file_generation_hst.`gefu_type` AS gefu_file_generation_gefu_type,\n"
                        + "     gefu_file_generation_hst.`bulk_credit` AS gefu_file_generation_bulk_credit\n"
                        + "FROM\n"
                        + "     `gefu_file_generation_hst` gefu_file_generation_hst INNER JOIN `ndb_pdc_txn_master` ndb_pdc_txn_master ON gefu_file_generation_hst.`idndb_pdc_txn_master` = ndb_pdc_txn_master.`idndb_pdc_txn_master` where ndb_pdc_txn_master.`idndb_customer_define_seller_id`='" + idndb_cust_prod_map + "' and ndb_pdc_txn_master.`pdc_req_financing`='RF' and gefu_file_generation_hst.`gefu_type` in ('VDEXTRCD','ERLEQDCD','CHQRETCHGCD') and gefu_file_generation_hst.`status`='AUTHORIZED' and gefu_file_generation_hst.`gefu_creation_status` ='PROCESSED' and STR_TO_DATE( gefu_file_generation_hst.`system_date`, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                System.out.println(m_strQry2);
                if (m_rs2.next()) {

                    if (!(m_rs2.getString("amount") == null)) {
                        scomm_chg = scomm_chg + Double.parseDouble(m_rs2.getString("amount"));
                    }

                }
                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);
                m_jsObj.put("scomm_chg", df.format(scomm_chg));
                m_jsObj.put("system_date", system_date);
                m_jsObj.put("seller_officer", seller_officer);
                m_jsArr.put(i, m_jsObj);
                i++;

            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_pdc_rf_seller_wise_buyer_facility(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        String m_strQry2 = "";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {

            String idndb_customer_define_seller_id = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                        + "nshb_seller_has_buyers.shb_facty_det_crd_loam_limit,\n"
                        + "nrf_rec_fin.rec_finance_limit,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                        + "ngmmf_geo_market_file.geo_market_desc\n"
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
                        + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                        + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
                        + "ndb_rec_fin nrf_rec_fin on\n"
                        + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = nrf_rec_fin.idndb_cust_prod_map where\n"
                        + "nshb_seller_has_buyers.sl_has_byr_prorm_type='RF' and nshb_seller_has_buyers.sl_has_byr_status='ACTIVE'";

            } else {
                m_strQry = "SELECT \n"
                        + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                        + "nshb_seller_has_buyers.shb_facty_det_crd_loam_limit,\n"
                        + "nrf_rec_fin.rec_finance_limit,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                        + "ngmmf_geo_market_file.geo_market_desc\n"
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
                        + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                        + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file left join\n"
                        + "ndb_rec_fin nrf_rec_fin on\n"
                        + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = nrf_rec_fin.idndb_cust_prod_map where \n"
                        + "nshb_seller_has_buyers.sl_has_byr_prorm_type='RF' and\n"
                        + "nshb_seller_has_buyers.idndb_customer_define_seller ='" + idndb_customer_define_seller_id + "'";

            }
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                m_jsObj = new JSONObject();

                String idndb_customer_define_seller = _rs.getString("idndb_customer_define_seller");
                String idndb_seller_has_buyers = _rs.getString("idndb_seller_has_buyers");

                double shb_facty_det_crd_loam_limit_percentage = _rs.getDouble("shb_facty_det_crd_loam_limit");
                double rf_seller_limit = _rs.getDouble("rec_finance_limit");

                String cust_name_buyer = _rs.getString("buyer_cust_name");
                String cust_id_buyer = _rs.getString("buyer_cust_id");

                String cust_name_seller = _rs.getString("seller_cust_name");
                String cust_id_seller = _rs.getString("seller_cust_id");

                String seller_officer = _rs.getString("geo_market_desc");

                //adding temporary limit feature - CFU-BRD-4 - Janaka_5977
                rf_seller_limit += getTotRFTemporaryLimits(idndb_customer_define_seller);

                double shb_facty_det_crd_loam_limit = shb_facty_det_crd_loam_limit_percentage / 100 * rf_seller_limit;

                m_strQry2 = "select SUM(pdc_chq_discounting_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + idndb_seller_has_buyers + "' and idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED') and pdc_req_financing='RF'";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _rs2 = _prepStmnt2.executeQuery();

                double pdc_chq_amu = 0.00;
                double available_amu = 0.00;

                if (_rs2.next()) {
                    pdc_chq_amu = _rs2.getDouble("pdc_chq_amu");
                    available_amu = shb_facty_det_crd_loam_limit - pdc_chq_amu;

                }

                m_jsObj.put("cust_name_seller", cust_name_seller);
                m_jsObj.put("cust_id_seller", cust_id_seller);

                m_jsObj.put("cust_name_buyer", cust_name_buyer);
                m_jsObj.put("cust_id_buyer", cust_id_buyer);
                m_jsObj.put("seller_officer", seller_officer);

                m_jsObj.put("shb_facty_det_crd_loam_limit_percentage", df.format(shb_facty_det_crd_loam_limit_percentage));
                m_jsObj.put("shb_facty_det_crd_loam_limit", df.format(shb_facty_det_crd_loam_limit));
                m_jsObj.put("pdc_chq_amu", df.format(pdc_chq_amu));
                m_jsObj.put("available_amu", df.format(available_amu));
                m_jsArr.put(i, m_jsObj);
                i++;

            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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

    public JSONArray getReceivedAllPdcChequesBuyerWise(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            String idndb_customer_define_seller_id = "";
            String idndb_customer_define_buyer_id = "";
            String date_from = "";
            String date_to = "";
            String pdc_product = "";
            String pdc_report_type = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            idndb_customer_define_buyer_id = prm_obj.getString("idndb_customer_define_buyer_id");
            date_from = prm_obj.getString("date_from");
            date_to = prm_obj.getString("date_to");
            pdc_product = prm_obj.getString("pdc_product");
            pdc_report_type = prm_obj.getString("pdc_report_type");

            m_strQry = "select \n"
                    + "nptm_txn_master.* ,\n"
                    + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                    + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                    + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                    + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                    + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5\n"
                    + "from \n"
                    + "ndb_pdc_txn_master nptm_txn_master left join \n"
                    + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                    + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master\n"
                    + "where \n"
                    + "nptm_txn_master.idndb_customer_define_buyer_id=? and \n"
                    + "nptm_txn_master.idndb_customer_define_seller_id=? and \n"
                    + "STR_TO_DATE(nptm_txn_master.pdc_booking_date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') and nptm_txn_master.pdc_req_financing=?";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _prepStmnt.setString(1, idndb_customer_define_buyer_id);
            _prepStmnt.setString(2, idndb_customer_define_seller_id);
            _prepStmnt.setString(3, date_from);
            _prepStmnt.setString(4, date_to);
            _prepStmnt.setString(5, pdc_product);

            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                if (pdc_report_type.equals("1")) {
                    m_jsObj = new JSONObject();
                    String current_status = "";
                    String pdc_chq_status = _rs.getString("pdc_chq_status");
                    String pdc_chq_status_auth = _rs.getString("pdc_chq_status_auth");
                    String pdc_value_date_ext = "";
                    if (_rs.getString("pdc_value_date_ext").equals("ACTIVE")) {
                        pdc_value_date_ext = " / VAL.DATE EXT. ";
                    }
                    current_status = pdc_chq_status + pdc_value_date_ext + "( " + pdc_chq_status_auth + " )";

                    m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                    m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                    m_jsObj.put("current_status", current_status);
                    m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                    m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_reference_1") == null || _rs.getString("pdc_reference_1").equals("")) ? "NA" : _rs.getString("pdc_reference_1"));
                    m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_reference_2") == null || _rs.getString("pdc_reference_2").equals("")) ? "NA" : _rs.getString("pdc_reference_2"));
                    m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_reference_3") == null || _rs.getString("pdc_reference_3").equals("")) ? "NA" : _rs.getString("pdc_reference_3"));
                    m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_reference_4") == null || _rs.getString("pdc_reference_4").equals("")) ? "NA" : _rs.getString("pdc_reference_4"));
                    m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_reference_5") == null || _rs.getString("pdc_reference_5").equals("")) ? "NA" : _rs.getString("pdc_reference_5"));
                    m_jsArr.put(i, m_jsObj);
                    i++;
                } else {

                    m_jsObj = new JSONObject();
                    String current_status = "";
                    String pdc_chq_status = _rs.getString("pdc_chq_status");
                    String pdc_chq_status_auth = _rs.getString("pdc_chq_status_auth");
                    String pdc_value_date_ext = "";
                    if (_rs.getString("pdc_value_date_ext").equals("ACTIVE")) {
                        pdc_value_date_ext = " / VAL.DATE EXT. ";
                    }
                    current_status = pdc_chq_status + pdc_value_date_ext + "( " + pdc_chq_status_auth + " )";

                    m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                    m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                    m_jsObj.put("current_status", current_status);
                    m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                    m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_reference_1") == null || _rs.getString("pdc_reference_1").equals("")) ? "NA" : _rs.getString("pdc_reference_1"));
                    m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_reference_2") == null || _rs.getString("pdc_reference_2").equals("")) ? "NA" : _rs.getString("pdc_reference_2"));
                    m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_reference_3") == null || _rs.getString("pdc_reference_3").equals("")) ? "NA" : _rs.getString("pdc_reference_3"));
                    m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_reference_4") == null || _rs.getString("pdc_reference_4").equals("")) ? "NA" : _rs.getString("pdc_reference_4"));
                    m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_reference_5") == null || _rs.getString("pdc_reference_5").equals("")) ? "NA" : _rs.getString("pdc_reference_5"));
                    m_jsArr.put(i, m_jsObj);
                    i++;

                    if (!(_rs.getString("pdc_cust_paym_reference_1") == null)) {

                        m_jsObj = new JSONObject();

                        m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                        m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                        m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                        m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                        m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                        m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                        m_jsObj.put("current_status", current_status);
                        m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                        m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_cust_paym_reference_1") == null || _rs.getString("pdc_cust_paym_reference_1").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_1"));
                        m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_cust_paym_reference_2") == null || _rs.getString("pdc_cust_paym_reference_2").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_2"));
                        m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_cust_paym_reference_3") == null || _rs.getString("pdc_cust_paym_reference_3").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_3"));
                        m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_cust_paym_reference_4") == null || _rs.getString("pdc_cust_paym_reference_4").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_4"));
                        m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_cust_paym_reference_5") == null || _rs.getString("pdc_cust_paym_reference_5").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_5"));
                        m_jsArr.put(i, m_jsObj);
                        i++;

                    }

                }

            }

        } catch (Exception e) {
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

    public JSONArray get_pdc_cw_unauth_cheques(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            String idndb_customer_define_seller_id = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*' and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            boolean data_exit = false;
            while (m_rs1.next()) {
                data_exit = true;
                m_jsObj = new JSONObject();
                //  m_strData = m_strData + "<tr>";
                // m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_pdc_txn_master") + ">" + m_rs1.getString("idndb_pdc_txn_master") + "</td>";
                m_jsObj.put("idndb_pdc_txn_master", m_rs1.getString("idndb_pdc_txn_master"));

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_jsObj.put("cust_data", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));

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
                        // m_strData = m_strData + "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";
                        m_jsObj.put("buy_data", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));
                    }

                }

                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));
                m_jsObj.put("pdc_chq_status", m_rs1.getString("pdc_chq_status"));
                m_jsObj.put("pdc_chq_status_auth", m_rs1.getString("pdc_chq_status_auth"));

                m_jsArr.put(i, m_jsObj);
                i++;
            }

//            if (data_exit) {
//                m_strQry = "SELECT SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*' and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
//                m_rs1 = _stmnt.executeQuery(m_strQry);
//                String tot_chq_sum = "";
//                m_jsObj = new JSONObject();
//                if (m_rs1.next()) {
//                    tot_chq_sum = m_rs1.getString("pdc_chq_amu");
//
//                }
//                m_strQry = "SELECT COUNT(idndb_pdc_txn_master) as chq_count from ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*' and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
//                m_rs1 = _stmnt.executeQuery(m_strQry);
//                String tot_chq_count = "";
//                if (m_rs1.next()) {
//                    tot_chq_count = m_rs1.getString("chq_count");
//
//                }
//
//                m_jsObj.put("idndb_pdc_txn_master", "");
//                m_jsObj.put("cust_data", "");
//                m_jsObj.put("buy_data", "");
//                m_jsObj.put("pdc_chq_number", "");
//                m_jsObj.put("pdc_bank_code", "");
//                m_jsObj.put("pdc_branch_code", "");
//                m_jsObj.put("pdc_value_date", "Total Amu.");
//                m_jsObj.put("pdc_chq_amu", tot_chq_sum);
//                m_jsObj.put("pdc_chq_status", "Chq Count");
//                m_jsObj.put("pdc_chq_status_auth", tot_chq_count);
//
//                m_jsArr.put(i, m_jsObj);
//                i++;
//
//            }
        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_pdc_cw_unauth_cheques_for_auth(JSONObject prm_obj) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            String idndb_customer_define_seller_id = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status in ('ACTIVE','ERLYLIQUDED') and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*' and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            boolean data_exit = false;
            while (m_rs1.next()) {
                data_exit = true;
                m_jsObj = new JSONObject();
                //  m_strData = m_strData + "<tr>";
                // m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_pdc_txn_master") + ">" + m_rs1.getString("idndb_pdc_txn_master") + "</td>";
                m_jsObj.put("idndb_pdc_txn_master", m_rs1.getString("idndb_pdc_txn_master"));

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_jsObj.put("cust_data", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));

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
                        // m_strData = m_strData + "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";
                        m_jsObj.put("buy_data", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));
                    }

                }

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_pdc_txn_master") + "' and ndb_change_log_type='PDCTXN' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }

                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));
                m_jsObj.put("modification", modification);
                m_jsObj.put("pdc_chq_mod_by", m_rs1.getString("pdc_chq_mod_by") + "</br>" + m_rs1.getString("pdc_chq_mod_date"));
                //m_jsObj.put("pdc_chq_mod_date", m_rs1.getString("pdc_chq_mod_date"));
                m_jsObj.put("chqbox", "<td><div class=\"controls\"><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + m_rs1.getString("idndb_pdc_txn_master") + "\"/></div><td>");

                m_jsArr.put(i, m_jsObj);
                i++;
            }

//            if (data_exit) {
//                m_strQry = "SELECT SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*' and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
//                m_rs1 = _stmnt.executeQuery(m_strQry);
//                String tot_chq_sum = "";
//                m_jsObj = new JSONObject();
//                if (m_rs1.next()) {
//                    tot_chq_sum = m_rs1.getString("pdc_chq_amu");
//
//                }
//                m_strQry = "SELECT COUNT(idndb_pdc_txn_master) as chq_count from ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*' and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
//                m_rs1 = _stmnt.executeQuery(m_strQry);
//                String tot_chq_count = "";
//                if (m_rs1.next()) {
//                    tot_chq_count = m_rs1.getString("chq_count");
//
//                }
//
//                m_jsObj.put("idndb_pdc_txn_master", "");
//                m_jsObj.put("cust_data", "");
//                m_jsObj.put("buy_data", "");
//                m_jsObj.put("pdc_chq_number", "");
//                m_jsObj.put("pdc_bank_code", "");
//                m_jsObj.put("pdc_branch_code", "");
//                m_jsObj.put("pdc_value_date", "Total Amu.");
//                m_jsObj.put("pdc_chq_amu", tot_chq_sum);
//                m_jsObj.put("pdc_chq_status", "Chq Count");
//                m_jsObj.put("pdc_chq_status_auth", tot_chq_count);
//
//                m_jsArr.put(i, m_jsObj);
//                i++;
//
//            }
        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getAllUnAuthData(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj1 = null;
        JSONObject m_jsObj2 = null;
        JSONObject m_jsObj3 = null;
        JSONObject m_jsObj4 = null;
        JSONObject m_jsObj5 = null;
        JSONObject m_jsObj6 = null;
        JSONObject m_jsObj7 = null;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            m_strQry = "SELECT * FROM ndb_bank_master_file where bank_approval ='UN-AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);

            while (m_rs1.next()) {
                m_jsObj1 = new JSONObject();

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_bank_master_file") + "' and ndb_change_log_type='DEFINEBANK' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }
                System.out.println(m_strQry2);
                System.out.println(modification);

                m_jsObj1.put("module", "BANK MASTER FILE");
                m_jsObj1.put("module_id", "BANK CODE : " + m_rs1.getString("bank_code"));

                m_jsObj1.put("modification", modification);
                m_jsObj1.put("mod_by", m_rs1.getString("bank_mod_by"));
                m_jsObj1.put("mod_time", m_rs1.getString("bank_mod_date"));

                m_jsArr.put(i, m_jsObj1);
                i++;
            }

            m_strQry = "SELECT * FROM ndb_branch_master_file where branch_approval ='UN-AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);

            while (m_rs1.next()) {
                m_jsObj2 = new JSONObject();

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_branch_master_file") + "' and ndb_change_log_type='DEFINEBRANCH' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }

                m_strQry2 = "SELECT * FROM ndb_bank_master_file where idndb_bank_master_file='" + m_rs1.getString("idndb_bank_master_file") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String bank_code = "";
                while (m_rs2.next()) {
                    bank_code = m_rs2.getString("bank_code") + "</br>";

                }

                m_jsObj2.put("module", "BRANCH MASTER FILE");
                m_jsObj2.put("module_id", "BANK CODE : " + bank_code + "BRANCH CODE : " + m_rs1.getString("branch_id"));

                m_jsObj2.put("modification", modification);
                m_jsObj2.put("mod_by", m_rs1.getString("branch_mod_by"));
                m_jsObj2.put("mod_time", m_rs1.getString("branch_mod_date"));

                m_jsArr.put(i, m_jsObj2);
                i++;
            }

            m_strQry = "SELECT * FROM ndb_holiday_marker where ndb_holiday_approval ='UN-AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            System.out.println("holiday");
            while (m_rs1.next()) {
                m_jsObj3 = new JSONObject();

                m_jsObj3.put("module", "HOLIDAY MASTER FILE");
                m_jsObj3.put("module_id", "HOLIDAY : " + m_rs1.getString("ndb_holiday"));

                m_jsObj3.put("modification", " 1 )NEW HOLIDAY" + m_rs1.getString("ndb_holiday"));
                m_jsObj3.put("mod_by", m_rs1.getString("ndb_holiday_mod_by"));
                m_jsObj3.put("mod_time", m_rs1.getString("ndb_holiday_mod_date"));

                m_jsArr.put(i, m_jsObj3);
                i++;
            }

            m_strQry = "SELECT * FROM ndb_customer_define where cust_auth ='UN-AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);

            while (m_rs1.next()) {
                m_jsObj4 = new JSONObject();

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_customer_define") + "' and ndb_change_log_type='DEFINECUSTOMER' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }

                m_jsObj4.put("module", "CUSTOMER MASTER FILE");
                m_jsObj4.put("module_id", "CUSTOMER CODE : " + m_rs1.getString("cust_id") + "</br> CUSTOMER NAME: " + m_rs1.getString("cust_name"));

                m_jsObj4.put("modification", modification);
                m_jsObj4.put("mod_by", m_rs1.getString("cust_mod_by"));
                m_jsObj4.put("mod_time", m_rs1.getString("cust_mod_date"));

                m_jsArr.put(i, m_jsObj4);
                i++;
            }

            m_strQry = "SELECT * FROM ndb_cust_prod_map where prod_relationship_auth ='UN-AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);

            while (m_rs1.next()) {
                m_jsObj5 = new JSONObject();

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_cust_prod_map") + "' and ndb_change_log_type='CUSTPRODMAP' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }
                m_strQry2 = "SELECT * FROM ndb_customer_define where idndb_customer_define='" + m_rs1.getString("idndb_customer_define") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_id = "";
                String cust_name = "";
                while (m_rs2.next()) {
                    cust_id = m_rs2.getString("cust_id") + "</br>";
                    cust_name = m_rs2.getString("cust_name") + "</br>";

                }

                m_jsObj5.put("module", "CUSTOMER PRODUCT MAPING");
                m_jsObj5.put("module_id", "CUSTOMER CODE : " + cust_id + "CUSTOMER NAME: " + cust_name);

                m_jsObj5.put("modification", modification);
                m_jsObj5.put("mod_by", m_rs1.getString("prod_relationship_mod_by"));
                m_jsObj5.put("mod_time", m_rs1.getString("prod_relationship_mod_date"));

                m_jsArr.put(i, m_jsObj5);
                i++;
            }

            m_strQry = "SELECT * FROM ndb_seller_has_buyers where sl_has_byr_auth ='UN-AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);

            while (m_rs1.next()) {
                m_jsObj6 = new JSONObject();

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_seller_has_buyers") + "' and ndb_change_log_type='RELESTB' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }
                System.out.println(m_strQry2);
                System.out.println(modification);

                String id_prod_map_seller = m_rs1.getString("idndb_customer_define_seller");
                String id_prod_map_buyer = m_rs1.getString("idndb_customer_define_buyer");

                m_strQry2 = "SELECT * FROM ndb_cust_prod_map where idndb_cust_prod_map='" + id_prod_map_seller + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String id_cust_seller = "";
                while (m_rs2.next()) {
                    id_cust_seller = m_rs2.getString("idndb_customer_define");

                }

                m_strQry2 = "SELECT * FROM ndb_cust_prod_map where idndb_cust_prod_map='" + id_prod_map_buyer + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String id_cust_buyer = "";
                while (m_rs2.next()) {
                    id_cust_buyer = m_rs2.getString("idndb_customer_define");

                }

                m_strQry2 = "SELECT * FROM ndb_customer_define where idndb_customer_define='" + id_cust_seller + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String sellercust_id = "";
                String sellercust_name = "";
                while (m_rs2.next()) {
                    sellercust_id = m_rs2.getString("cust_id") + "</br>";
                    sellercust_name = m_rs2.getString("cust_name") + "</br>";

                }
                m_strQry2 = "SELECT * FROM ndb_customer_define where idndb_customer_define='" + id_cust_buyer + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String buyercust_id = "";
                String buyercust_name = "";
                while (m_rs2.next()) {
                    buyercust_id = m_rs2.getString("cust_id") + "</br>";
                    buyercust_name = m_rs2.getString("cust_name") + "</br>";

                }

                m_jsObj6.put("module", "CUSTOMER RELATIONSHIP ESTB.");
                m_jsObj6.put("module_id", "SELLER CODE : " + sellercust_id + "SELLER NAME: " + sellercust_name + "BUYER CODE : " + buyercust_id + "BUYER NAME: " + buyercust_name);

                m_jsObj6.put("modification", modification);
                m_jsObj6.put("mod_by", m_rs1.getString("sl_has_byr_mod_by"));
                m_jsObj6.put("mod_time", m_rs1.getString("sl_has_byr_mod_date"));

                m_jsArr.put(i, m_jsObj6);
                i++;
            }

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status_auth ='UN-AUTHORIZED'";
            m_rs1 = _stmnt.executeQuery(m_strQry);

            while (m_rs1.next()) {
                m_jsObj7 = new JSONObject();

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_pdc_txn_master") + "' and ndb_change_log_type='PDCTXN' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }
                String id_cust_seller = m_rs1.getString("idndb_customer_define_seller_id");

                m_strQry2 = "SELECT * FROM ndb_cust_prod_map where idndb_cust_prod_map='" + id_cust_seller + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String id_seller_def_id = "";
                while (m_rs2.next()) {
                    id_seller_def_id = m_rs2.getString("idndb_customer_define");

                }

                m_strQry2 = "SELECT * FROM ndb_customer_define where idndb_customer_define='" + id_seller_def_id + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String sellercust_idd = "";
                String sellercust_namee = "";
                while (m_rs2.next()) {
                    sellercust_idd = m_rs2.getString("cust_id") + "</br>";
                    sellercust_namee = m_rs2.getString("cust_name") + "</br>";

                }

                m_jsObj7.put("module", "PDC TXN. FILE");
                m_jsObj7.put("module_id", "SELLER CODE : " + sellercust_idd + "SELLER NAME :" + sellercust_namee + "CHQ NUMBER :" + m_rs1.getString("pdc_chq_number"));

                m_jsObj7.put("modification", modification);
                m_jsObj7.put("mod_by", m_rs1.getString("pdc_chq_mod_by"));
                m_jsObj7.put("mod_time", m_rs1.getString("pdc_chq_mod_date"));

                m_jsArr.put(i, m_jsObj7);
                System.out.println(m_jsArr);
                i++;
            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_pdc_rf_unauth_cheques_for_auth(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        DecimalFormat df = new DecimalFormat("#,###.00");
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            String idndb_customer_define_seller_id = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status in ('ACTIVE','ERLYLIQUDED') and pdc_req_financing='RF' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*' and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            boolean data_exit = false;
            while (m_rs1.next()) {
                data_exit = true;
                m_jsObj = new JSONObject();
                //  m_strData = m_strData + "<tr>";
                // m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_pdc_txn_master") + ">" + m_rs1.getString("idndb_pdc_txn_master") + "</td>";
                m_jsObj.put("idndb_pdc_txn_master", m_rs1.getString("idndb_pdc_txn_master"));

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_jsObj.put("cust_data", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));

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
                        // m_strData = m_strData + "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";
                        m_jsObj.put("buy_data", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));
                    }

                }

                m_strQry2 = "SELECT * FROM ndb_change_log where ndb_attached_id='" + m_rs1.getString("idndb_pdc_txn_master") + "' and ndb_change_log_type='PDCTXN' and status='UN-AUTHORIZED'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String modification = "";
                while (m_rs2.next()) {
                    modification = modification + m_rs2.getString("ndb_change") + "</br>";

                }

                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));
                m_jsObj.put("modification", modification);
                m_jsObj.put("pdc_chq_mod_by", m_rs1.getString("pdc_chq_mod_by") + "</br>" + m_rs1.getString("pdc_chq_mod_date"));
                //m_jsObj.put("pdc_chq_mod_date", m_rs1.getString("pdc_chq_mod_date"));
                m_jsObj.put("chqbox", "<td><div class=\"controls\"><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + m_rs1.getString("idndb_pdc_txn_master") + "\"/></div><td>");

                m_jsArr.put(i, m_jsObj);
                i++;
            }

//            if (data_exit) {
//                m_strQry = "SELECT SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*' and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
//                m_rs1 = _stmnt.executeQuery(m_strQry);
//                String tot_chq_sum = "";
//                m_jsObj = new JSONObject();
//                if (m_rs1.next()) {
//                    tot_chq_sum = m_rs1.getString("pdc_chq_amu");
//
//                }
//                m_strQry = "SELECT COUNT(idndb_pdc_txn_master) as chq_count from ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*' and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
//                m_rs1 = _stmnt.executeQuery(m_strQry);
//                String tot_chq_count = "";
//                if (m_rs1.next()) {
//                    tot_chq_count = m_rs1.getString("chq_count");
//
//                }
//
//                m_jsObj.put("idndb_pdc_txn_master", "");
//                m_jsObj.put("cust_data", "");
//                m_jsObj.put("buy_data", "");
//                m_jsObj.put("pdc_chq_number", "");
//                m_jsObj.put("pdc_bank_code", "");
//                m_jsObj.put("pdc_branch_code", "");
//                m_jsObj.put("pdc_value_date", "Total Amu.");
//                m_jsObj.put("pdc_chq_amu", tot_chq_sum);
//                m_jsObj.put("pdc_chq_status", "Chq Count");
//                m_jsObj.put("pdc_chq_status_auth", tot_chq_count);
//
//                m_jsArr.put(i, m_jsObj);
//                i++;
//
//            }
        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_pdc_rf_unauth_cheques(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            DecimalFormat df = new DecimalFormat("#,###.00");
            String idndb_customer_define_seller_id = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='RF' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*' and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            boolean data_exit = false;
            while (m_rs1.next()) {
                data_exit = true;
                m_jsObj = new JSONObject();
                //  m_strData = m_strData + "<tr>";
                // m_strData = m_strData + "<td id=" + m_rs1.getString("idndb_pdc_txn_master") + ">" + m_rs1.getString("idndb_pdc_txn_master") + "</td>";
                m_jsObj.put("idndb_pdc_txn_master", m_rs1.getString("idndb_pdc_txn_master"));

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_jsObj.put("cust_data", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));

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
                        // m_strData = m_strData + "<td>" + m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name") + "</td>";
                        m_jsObj.put("buy_data", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));
                    }

                }

                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));
                m_jsObj.put("pdc_chq_status", m_rs1.getString("pdc_chq_status"));
                m_jsObj.put("pdc_chq_status_auth", m_rs1.getString("pdc_chq_status_auth"));

                m_jsArr.put(i, m_jsObj);
                i++;
            }

//            if (data_exit) {
//                m_strQry = "SELECT SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*' and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
//                m_rs1 = _stmnt.executeQuery(m_strQry);
//                String tot_chq_sum = "";
//                m_jsObj = new JSONObject();
//                if (m_rs1.next()) {
//                    tot_chq_sum = m_rs1.getString("pdc_chq_amu");
//
//                }
//                m_strQry = "SELECT COUNT(idndb_pdc_txn_master) as chq_count from ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_req_financing='CW' and pdc_chq_status_auth in ('UN-AUTHORIZED') and pdc_chq_batch_no ='*' and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
//                m_rs1 = _stmnt.executeQuery(m_strQry);
//                String tot_chq_count = "";
//                if (m_rs1.next()) {
//                    tot_chq_count = m_rs1.getString("chq_count");
//
//                }
//
//                m_jsObj.put("idndb_pdc_txn_master", "");
//                m_jsObj.put("cust_data", "");
//                m_jsObj.put("buy_data", "");
//                m_jsObj.put("pdc_chq_number", "");
//                m_jsObj.put("pdc_bank_code", "");
//                m_jsObj.put("pdc_branch_code", "");
//                m_jsObj.put("pdc_value_date", "Total Amu.");
//                m_jsObj.put("pdc_chq_amu", tot_chq_sum);
//                m_jsObj.put("pdc_chq_status", "Chq Count");
//                m_jsObj.put("pdc_chq_status_auth", tot_chq_count);
//
//                m_jsArr.put(i, m_jsObj);
//                i++;
//
//            }
        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getReceivedAllPdcChequesSellerWise(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            String idndb_customer_define_seller_id = "";
            String date_from = "";
            String date_to = "";
            String pdc_product = "";
            String pdc_report_type = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            date_from = prm_obj.getString("date_from");
            date_to = prm_obj.getString("date_to");
            pdc_product = prm_obj.getString("pdc_product");
            pdc_report_type = prm_obj.getString("pdc_report_type");

            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT \n"
                        + "nptm_txn_master.* ,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                        + "FROM \n"
                        + "ndb_pdc_txn_master nptm_txn_master left join\n"
                        + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                        + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master inner join\n"
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
                        + "STR_TO_DATE(nptm_txn_master.pdc_booking_date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') and \n"
                        + "nptm_txn_master.pdc_req_financing=?";
                _prepStmnt = _currentCon.prepareStatement(m_strQry);
                _prepStmnt.setString(1, date_from);
                _prepStmnt.setString(2, date_to);
                _prepStmnt.setString(3, pdc_product);

            } else {

                m_strQry = "SELECT \n"
                        + "nptm_txn_master.* ,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                        + "FROM \n"
                        + "ndb_pdc_txn_master nptm_txn_master left join\n"
                        + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                        + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master join\n"
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
                        + "nptm_txn_master.idndb_customer_define_seller_id=? and \n"
                        + "STR_TO_DATE(nptm_txn_master.pdc_booking_date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') and \n"
                        + "nptm_txn_master.pdc_req_financing=?";

                _prepStmnt = _currentCon.prepareStatement(m_strQry);
                _prepStmnt.setString(1, idndb_customer_define_seller_id);
                _prepStmnt.setString(2, date_from);
                _prepStmnt.setString(3, date_to);
                _prepStmnt.setString(4, pdc_product);

            }

            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                if (pdc_report_type.equals("1")) {

                    m_jsObj = new JSONObject();
                    String current_status = "";
                    String pdc_chq_status = _rs.getString("pdc_chq_status");
                    String pdc_chq_status_auth = _rs.getString("pdc_chq_status_auth");
                    String pdc_value_date_ext = "";
                    if (_rs.getString("pdc_value_date_ext").equals("ACTIVE")) {
                        pdc_value_date_ext = " / VAL.DATE EXT. ";
                    }
                    current_status = pdc_chq_status + pdc_value_date_ext + "( " + pdc_chq_status_auth + " )";

                    m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                    m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                    m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                    m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                    m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                    m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                    m_jsObj.put("current_status", current_status);
                    m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                    m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_reference_1") == null || _rs.getString("pdc_reference_1").equals("")) ? "NA" : _rs.getString("pdc_reference_1"));
                    m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_reference_2") == null || _rs.getString("pdc_reference_2").equals("")) ? "NA" : _rs.getString("pdc_reference_2"));
                    m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_reference_3") == null || _rs.getString("pdc_reference_3").equals("")) ? "NA" : _rs.getString("pdc_reference_3"));
                    m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_reference_4") == null || _rs.getString("pdc_reference_4").equals("")) ? "NA" : _rs.getString("pdc_reference_4"));
                    m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_reference_5") == null || _rs.getString("pdc_reference_5").equals("")) ? "NA" : _rs.getString("pdc_reference_5"));
                    m_jsArr.put(i, m_jsObj);
                    i++;
                } else {
                    m_jsObj = new JSONObject();
                    String current_status = "";
                    String pdc_chq_status = _rs.getString("pdc_chq_status");
                    String pdc_chq_status_auth = _rs.getString("pdc_chq_status_auth");
                    String pdc_value_date_ext = "";
                    if (_rs.getString("pdc_value_date_ext").equals("ACTIVE")) {
                        pdc_value_date_ext = " / VAL.DATE EXT. ";
                    }
                    current_status = pdc_chq_status + pdc_value_date_ext + "( " + pdc_chq_status_auth + " )";

                    m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                    m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                    m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                    m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                    m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                    m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                    m_jsObj.put("current_status", current_status);
                    m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                    m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_reference_1") == null || _rs.getString("pdc_reference_1").equals("")) ? "NA" : _rs.getString("pdc_reference_1"));
                    m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_reference_2") == null || _rs.getString("pdc_reference_2").equals("")) ? "NA" : _rs.getString("pdc_reference_2"));
                    m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_reference_3") == null || _rs.getString("pdc_reference_3").equals("")) ? "NA" : _rs.getString("pdc_reference_3"));
                    m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_reference_4") == null || _rs.getString("pdc_reference_4").equals("")) ? "NA" : _rs.getString("pdc_reference_4"));
                    m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_reference_5") == null || _rs.getString("pdc_reference_5").equals("")) ? "NA" : _rs.getString("pdc_reference_5"));
                    m_jsArr.put(i, m_jsObj);
                    i++;

                    if (!(_rs.getString("pdc_cust_paym_reference_1") == null)) {
                        m_jsObj = new JSONObject();
                        m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                        m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                        m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                        m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                        m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                        m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                        m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                        m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                        m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                        m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                        m_jsObj.put("current_status", current_status);
                        m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                        m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_cust_paym_reference_1") == null || _rs.getString("pdc_cust_paym_reference_1").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_1"));
                        m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_cust_paym_reference_2") == null || _rs.getString("pdc_cust_paym_reference_2").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_2"));
                        m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_cust_paym_reference_3") == null || _rs.getString("pdc_cust_paym_reference_3").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_3"));
                        m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_cust_paym_reference_4") == null || _rs.getString("pdc_cust_paym_reference_4").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_4"));
                        m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_cust_paym_reference_5") == null || _rs.getString("pdc_cust_paym_reference_5").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_5"));
                        m_jsArr.put(i, m_jsObj);
                        i++;

                    }

                }

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

    public JSONArray getPdcValueDateExtentionedChequesSellerWise(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            String idndb_customer_define_seller_id = "";
            String date_from = "";
            String date_to = "";
            String pdc_product = "";
            String pdc_report_type = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            date_from = prm_obj.getString("date_from");
            date_to = prm_obj.getString("date_to");
            pdc_product = prm_obj.getString("pdc_product");
            pdc_report_type = prm_obj.getString("pdc_report_type");

            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT \n"
                        + "nptm_txn_master.* ,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                        + "FROM \n"
                        + "ndb_pdc_txn_master nptm_txn_master left join\n"
                        + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                        + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master inner join\n"
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
                        + "nptm_txn_master.pdc_value_date_ext='ACTIVE' and \n"
                        + "STR_TO_DATE(nptm_txn_master.pdc_booking_date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') and \n"
                        + "nptm_txn_master.pdc_req_financing=?";
                _prepStmnt = _currentCon.prepareStatement(m_strQry);
                _prepStmnt.setString(1, date_from);
                _prepStmnt.setString(2, date_to);
                _prepStmnt.setString(3, pdc_product);

            } else {

                m_strQry = "SELECT \n"
                        + "nptm_txn_master.* ,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                        + "FROM \n"
                        + "ndb_pdc_txn_master nptm_txn_master left join\n"
                        + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                        + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master inner join\n"
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
                        + "nptm_txn_master.pdc_value_date_ext='ACTIVE' and \n"
                        + "nptm_txn_master.idndb_customer_define_seller_id=? and \n"
                        + "STR_TO_DATE(nptm_txn_master.pdc_booking_date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') and \n"
                        + "nptm_txn_master.pdc_req_financing=?";

                _prepStmnt = _currentCon.prepareStatement(m_strQry);
                _prepStmnt.setString(1, idndb_customer_define_seller_id);
                _prepStmnt.setString(2, date_from);
                _prepStmnt.setString(3, date_to);
                _prepStmnt.setString(4, pdc_product);

            }

            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                if (pdc_report_type.equals("1")) {

                    m_jsObj = new JSONObject();
                    String current_status = "";
                    String pdc_chq_status = _rs.getString("pdc_chq_status");
                    String pdc_chq_status_auth = _rs.getString("pdc_chq_status_auth");
                    String pdc_value_date_ext = "";
                    if (_rs.getString("pdc_value_date_ext").equals("ACTIVE")) {
                        pdc_value_date_ext = " / VAL.DATE EXT. ";
                    }
                    current_status = pdc_chq_status + pdc_value_date_ext + "( " + pdc_chq_status_auth + " )";

                    m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                    m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                    m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                    m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                    m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                    m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_old_value_date", _rs.getString("pdc_old_value_date"));
                    m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                    m_jsObj.put("current_status", current_status);
                    m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                    m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_reference_1") == null || _rs.getString("pdc_reference_1").equals("")) ? "NA" : _rs.getString("pdc_reference_1"));
                    m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_reference_2") == null || _rs.getString("pdc_reference_2").equals("")) ? "NA" : _rs.getString("pdc_reference_2"));
                    m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_reference_3") == null || _rs.getString("pdc_reference_3").equals("")) ? "NA" : _rs.getString("pdc_reference_3"));
                    m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_reference_4") == null || _rs.getString("pdc_reference_4").equals("")) ? "NA" : _rs.getString("pdc_reference_4"));
                    m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_reference_5") == null || _rs.getString("pdc_reference_5").equals("")) ? "NA" : _rs.getString("pdc_reference_5"));
                    m_jsArr.put(i, m_jsObj);
                    i++;

                } else {

                    m_jsObj = new JSONObject();
                    String current_status = "";
                    String pdc_chq_status = _rs.getString("pdc_chq_status");
                    String pdc_chq_status_auth = _rs.getString("pdc_chq_status_auth");
                    String pdc_value_date_ext = "";
                    if (_rs.getString("pdc_value_date_ext").equals("ACTIVE")) {
                        pdc_value_date_ext = " / VAL.DATE EXT. ";
                    }
                    current_status = pdc_chq_status + pdc_value_date_ext + "( " + pdc_chq_status_auth + " )";

                    m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                    m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                    m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                    m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                    m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                    m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_old_value_date", _rs.getString("pdc_old_value_date"));
                    m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                    m_jsObj.put("current_status", current_status);
                    m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                    m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_reference_1") == null || _rs.getString("pdc_reference_1").equals("")) ? "NA" : _rs.getString("pdc_reference_1"));
                    m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_reference_2") == null || _rs.getString("pdc_reference_2").equals("")) ? "NA" : _rs.getString("pdc_reference_2"));
                    m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_reference_3") == null || _rs.getString("pdc_reference_3").equals("")) ? "NA" : _rs.getString("pdc_reference_3"));
                    m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_reference_4") == null || _rs.getString("pdc_reference_4").equals("")) ? "NA" : _rs.getString("pdc_reference_4"));
                    m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_reference_5") == null || _rs.getString("pdc_reference_5").equals("")) ? "NA" : _rs.getString("pdc_reference_5"));
                    m_jsArr.put(i, m_jsObj);
                    i++;

                    if (!(_rs.getString("pdc_cust_paym_reference_1") == null)) {

                        m_jsObj = new JSONObject();

                        m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                        m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                        m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                        m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                        m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                        m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                        m_jsObj.put("pdc_old_value_date", _rs.getString("pdc_old_value_date"));
                        m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                        m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                        m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                        m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                        m_jsObj.put("current_status", current_status);
                        m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                        m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_cust_paym_reference_1") == null || _rs.getString("pdc_cust_paym_reference_1").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_1"));
                        m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_cust_paym_reference_2") == null || _rs.getString("pdc_cust_paym_reference_2").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_2"));
                        m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_cust_paym_reference_3") == null || _rs.getString("pdc_cust_paym_reference_3").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_3"));
                        m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_cust_paym_reference_4") == null || _rs.getString("pdc_cust_paym_reference_4").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_4"));
                        m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_cust_paym_reference_5") == null || _rs.getString("pdc_cust_paym_reference_5").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_5"));
                        m_jsArr.put(i, m_jsObj);
                        i++;

                    }

                }

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

    public JSONArray getPdcAdditionalDayChequesSellerWise(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            String idndb_customer_define_seller_id = "";
            String date_from = "";
            String date_to = "";
            String pdc_product = "";
            String pdc_report_type = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            date_from = prm_obj.getString("date_from");
            date_to = prm_obj.getString("date_to");
            pdc_product = prm_obj.getString("pdc_product");
            pdc_report_type = prm_obj.getString("pdc_report_type");

            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT \n"
                        + "nptm_txn_master.* ,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                        + "FROM \n"
                        + "ndb_pdc_txn_master nptm_txn_master left join\n"
                        + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                        + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master inner join\n"
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
                        + "nptm_txn_master.pdc_additional_day='ACTIVE' and \n"
                        + "STR_TO_DATE(nptm_txn_master.pdc_booking_date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') and \n"
                        + "nptm_txn_master.pdc_req_financing=?";
                _prepStmnt = _currentCon.prepareStatement(m_strQry);
                _prepStmnt.setString(1, date_from);
                _prepStmnt.setString(2, date_to);
                _prepStmnt.setString(3, pdc_product);

            } else {

                m_strQry = "SELECT \n"
                        + "nptm_txn_master.* ,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                        + "FROM \n"
                        + "ndb_pdc_txn_master nptm_txn_master left join\n"
                        + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                        + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master inner join\n"
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
                        + "nptm_txn_master.pdc_additional_day='ACTIVE' and \n"
                        + "nptm_txn_master.idndb_customer_define_seller_id=? and \n"
                        + "STR_TO_DATE(nptm_txn_master.pdc_booking_date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') and \n"
                        + "nptm_txn_master.pdc_req_financing=?";

                _prepStmnt = _currentCon.prepareStatement(m_strQry);
                _prepStmnt.setString(1, idndb_customer_define_seller_id);
                _prepStmnt.setString(2, date_from);
                _prepStmnt.setString(3, date_to);
                _prepStmnt.setString(4, pdc_product);

            }

            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                if (pdc_report_type.equals("1")) {

                    m_jsObj = new JSONObject();
                    String current_status = "";
                    String pdc_chq_status = _rs.getString("pdc_chq_status");
                    String pdc_chq_status_auth = _rs.getString("pdc_chq_status_auth");
                    String pdc_value_date_ext = "";
                    if (_rs.getString("pdc_value_date_ext").equals("ACTIVE")) {
                        pdc_value_date_ext = " / VAL.DATE EXT. ";
                    }
                    current_status = pdc_chq_status + pdc_value_date_ext + "( " + pdc_chq_status_auth + " )";

                    m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                    m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                    m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                    m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                    m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                    m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_old_value_date", _rs.getString("pdc_old_value_date"));
                    m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                    m_jsObj.put("pdc_chq_discounting_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_discounting_amu"))));
                    m_jsObj.put("current_status", current_status);
                    m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                    m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_reference_1") == null || _rs.getString("pdc_reference_1").equals("")) ? "NA" : _rs.getString("pdc_reference_1"));
                    m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_reference_2") == null || _rs.getString("pdc_reference_2").equals("")) ? "NA" : _rs.getString("pdc_reference_2"));
                    m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_reference_3") == null || _rs.getString("pdc_reference_3").equals("")) ? "NA" : _rs.getString("pdc_reference_3"));
                    m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_reference_4") == null || _rs.getString("pdc_reference_4").equals("")) ? "NA" : _rs.getString("pdc_reference_4"));
                    m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_reference_5") == null || _rs.getString("pdc_reference_5").equals("")) ? "NA" : _rs.getString("pdc_reference_5"));
                    m_jsArr.put(i, m_jsObj);
                    i++;
                } else {

                    m_jsObj = new JSONObject();
                    String current_status = "";
                    String pdc_chq_status = _rs.getString("pdc_chq_status");
                    String pdc_chq_status_auth = _rs.getString("pdc_chq_status_auth");
                    String pdc_value_date_ext = "";
                    if (_rs.getString("pdc_value_date_ext").equals("ACTIVE")) {
                        pdc_value_date_ext = " / VAL.DATE EXT. ";
                    }
                    current_status = pdc_chq_status + pdc_value_date_ext + "( " + pdc_chq_status_auth + " )";

                    m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                    m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                    m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                    m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                    m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                    m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_old_value_date", _rs.getString("pdc_old_value_date"));
                    m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                    m_jsObj.put("pdc_chq_discounting_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_discounting_amu"))));
                    m_jsObj.put("current_status", current_status);
                    m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                    m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_reference_1") == null || _rs.getString("pdc_reference_1").equals("")) ? "NA" : _rs.getString("pdc_reference_1"));
                    m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_reference_2") == null || _rs.getString("pdc_reference_2").equals("")) ? "NA" : _rs.getString("pdc_reference_2"));
                    m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_reference_3") == null || _rs.getString("pdc_reference_3").equals("")) ? "NA" : _rs.getString("pdc_reference_3"));
                    m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_reference_4") == null || _rs.getString("pdc_reference_4").equals("")) ? "NA" : _rs.getString("pdc_reference_4"));
                    m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_reference_5") == null || _rs.getString("pdc_reference_5").equals("")) ? "NA" : _rs.getString("pdc_reference_5"));
                    m_jsArr.put(i, m_jsObj);
                    i++;

                    if (!(_rs.getString("pdc_cust_paym_reference_1") == null)) {

                        m_jsObj = new JSONObject();

                        m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                        m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                        m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                        m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                        m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                        m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                        m_jsObj.put("pdc_old_value_date", _rs.getString("pdc_old_value_date"));
                        m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                        m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                        m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                        m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                        m_jsObj.put("pdc_chq_discounting_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_discounting_amu"))));
                        m_jsObj.put("current_status", current_status);
                        m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                        m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_cust_paym_reference_1") == null || _rs.getString("pdc_cust_paym_reference_1").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_1"));
                        m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_cust_paym_reference_2") == null || _rs.getString("pdc_cust_paym_reference_2").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_2"));
                        m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_cust_paym_reference_3") == null || _rs.getString("pdc_cust_paym_reference_3").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_3"));
                        m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_cust_paym_reference_4") == null || _rs.getString("pdc_cust_paym_reference_4").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_4"));
                        m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_cust_paym_reference_5") == null || _rs.getString("pdc_cust_paym_reference_5").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_5"));
                        m_jsArr.put(i, m_jsObj);
                        i++;

                    }

                }

            }

        } catch (Exception e) {
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

    public JSONArray getPdcChequeStatusReport(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            String idndb_customer_define_seller_id = "";
            String date_from = "";
            String date_to = "";
            String pdc_product = "";
            String pdc_status = "";
            String pdc_date_type = "";
            String pdc_quiery_date_type = "";
            String pdc_report_type = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            date_from = prm_obj.getString("date_from");
            date_to = prm_obj.getString("date_to");
            pdc_product = prm_obj.getString("pdc_product");
            pdc_status = prm_obj.getString("pdc_status");
            pdc_date_type = prm_obj.getString("pdc_date_type");
            pdc_report_type = prm_obj.getString("pdc_report_type");

            if (pdc_date_type.equals("BOOKING_DATE")) {

                pdc_quiery_date_type = "pdc_booking_date";
            }

            if (pdc_date_type.equals("VALUE_DATE")) {
                pdc_quiery_date_type = "pdc_value_date";

            }

            if (idndb_customer_define_seller_id.equals("all")) {
                if (pdc_status.equals("AUTHORIZED") || pdc_status.equals("UN-AUTHORIZED")) {

                    m_strQry = "SELECT \n"
                            + "nptm_txn_master.* ,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                            + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                            + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                            + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                            + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                            + "FROM \n"
                            + "ndb_pdc_txn_master nptm_txn_master left join\n"
                            + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                            + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master inner join\n"
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
                            + "nptm_txn_master.pdc_chq_status_auth=? and \n"
                            + "nptm_txn_master.pdc_chq_status='ACTIVE' and \n"
                            + "STR_TO_DATE(nptm_txn_master." + pdc_quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') and \n"
                            + "nptm_txn_master.pdc_req_financing=?";

                    _prepStmnt = _currentCon.prepareStatement(m_strQry);
                    _prepStmnt.setString(1, pdc_status);
                    _prepStmnt.setString(2, date_from);
                    _prepStmnt.setString(3, date_to);
                    _prepStmnt.setString(4, pdc_product);

                } else {
                    m_strQry = "SELECT \n"
                            + "nptm_txn_master.* ,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                            + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                            + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                            + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                            + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                            + "FROM \n"
                            + "ndb_pdc_txn_master nptm_txn_master left join\n"
                            + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                            + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master inner join\n"
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
                            + "nptm_txn_master.pdc_chq_status=? and \n"
                            + "STR_TO_DATE(nptm_txn_master." + pdc_quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') and \n"
                            + "nptm_txn_master.pdc_req_financing=?";

                    _prepStmnt = _currentCon.prepareStatement(m_strQry);
                    _prepStmnt.setString(1, pdc_status);
                    _prepStmnt.setString(2, date_from);
                    _prepStmnt.setString(3, date_to);
                    _prepStmnt.setString(4, pdc_product);

                }

            } else {
                if (pdc_status.equals("AUTHORIZED") || pdc_status.equals("UN-AUTHORIZED")) {

                    m_strQry = "SELECT \n"
                            + "nptm_txn_master.* ,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                            + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                            + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                            + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                            + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                            + "FROM \n"
                            + "ndb_pdc_txn_master nptm_txn_master left join\n"
                            + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                            + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master inner join\n"
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
                            + "nptm_txn_master.idndb_customer_define_seller_id=? and \n"
                            + "nptm_txn_master.pdc_chq_status_auth=? and \n"
                            + "nptm_txn_master.pdc_chq_status='ACTIVE' and \n"
                            + "STR_TO_DATE(nptm_txn_master." + pdc_quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') and \n"
                            + "nptm_txn_master.pdc_req_financing=?";

                    _prepStmnt = _currentCon.prepareStatement(m_strQry);
                    _prepStmnt.setString(1, idndb_customer_define_seller_id);
                    _prepStmnt.setString(2, pdc_status);
                    _prepStmnt.setString(3, date_from);
                    _prepStmnt.setString(4, date_to);
                    _prepStmnt.setString(5, pdc_product);

                } else {
                    m_strQry = "SELECT \n"
                            + "nptm_txn_master.* ,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                            + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                            + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                            + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                            + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                            + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                            + "FROM \n"
                            + "ndb_pdc_txn_master nptm_txn_master left join\n"
                            + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                            + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master inner join\n"
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
                            + "nptm_txn_master.idndb_customer_define_seller_id=? and \n"
                            + "nptm_txn_master.pdc_chq_status=? and \n"
                            + "STR_TO_DATE(nptm_txn_master." + pdc_quiery_date_type + ", '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') and \n"
                            + "nptm_txn_master.pdc_req_financing=?";

                    _prepStmnt = _currentCon.prepareStatement(m_strQry);
                    _prepStmnt.setString(1, idndb_customer_define_seller_id);
                    _prepStmnt.setString(2, pdc_status);
                    _prepStmnt.setString(3, date_from);
                    _prepStmnt.setString(4, date_to);
                    _prepStmnt.setString(5, pdc_product);

                }

            }

            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                String current_status = "";
                String pdc_chq_status = _rs.getString("pdc_chq_status");
                String pdc_chq_status_auth = _rs.getString("pdc_chq_status_auth");

                if (pdc_chq_status.equals("ERLYLIQUDED") && pdc_chq_status_auth.equals("AUTHORIZED")) {
                    current_status = "EARLY LIQD (AUTHORIZED)";
                }

                if (pdc_chq_status.equals("ERLYLIQUDED") && pdc_chq_status_auth.equals("UN-AUTHORIZED")) {
                    current_status = "EARLY LIQD (UN-AUTHORIZED)";
                }

                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("AUTHORIZED")) {
                    current_status = "ACTIVE (AUTHORIZED)";
                }
                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("UN-AUTHORIZED")) {
                    current_status = "ACTIVE (UN-AUTHORIZED)";
                }
                if (pdc_chq_status.equals("ACTIVE") && pdc_chq_status_auth.equals("REJECTED")) {
                    current_status = "ACTIVE (REJECTED)";
                }

                if (pdc_chq_status.equals("RETURNED")) {
                    current_status = "RETURNED";
                }

                if (pdc_chq_status.equals("PROCESSED")) {
                    current_status = "CLEARED";
                }

//                m_jsObj = new JSONObject();
//                m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
//                m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
//                m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
//                m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
//                m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
//                m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
//                m_jsObj.put("pdc_old_value_date", _rs.getString("pdc_old_value_date"));
//                m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
//                m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
//                m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
//                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
//                m_jsObj.put("pdc_chq_discounting_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_discounting_amu"))));
//                m_jsObj.put("current_status", current_status);
//                m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
//                m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_reference_1") == null || _rs.getString("pdc_reference_1").equals("")) ? "NA" : _rs.getString("pdc_reference_1"));
//                m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_reference_2") == null || _rs.getString("pdc_reference_2").equals("")) ? "NA" : _rs.getString("pdc_reference_2"));
//                m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_reference_3") == null || _rs.getString("pdc_reference_3").equals("")) ? "NA" : _rs.getString("pdc_reference_3"));
//                m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_reference_4") == null || _rs.getString("pdc_reference_4").equals("")) ? "NA" : _rs.getString("pdc_reference_4"));
//                m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_reference_5") == null || _rs.getString("pdc_reference_5").equals("")) ? "NA" : _rs.getString("pdc_reference_5"));
//                m_jsArr.put(i, m_jsObj);
//                i++;
                if (pdc_report_type.equals("1")) {
                    m_jsObj = new JSONObject();
                    m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                    m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                    m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                    m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                    m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                    m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_old_value_date", _rs.getString("pdc_old_value_date"));
                    m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                    m_jsObj.put("pdc_chq_discounting_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_discounting_amu"))));
                    m_jsObj.put("current_status", current_status);
                    m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                    m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_reference_1") == null || _rs.getString("pdc_reference_1").equals("")) ? "NA" : _rs.getString("pdc_reference_1"));
                    m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_reference_2") == null || _rs.getString("pdc_reference_2").equals("")) ? "NA" : _rs.getString("pdc_reference_2"));
                    m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_reference_3") == null || _rs.getString("pdc_reference_3").equals("")) ? "NA" : _rs.getString("pdc_reference_3"));
                    m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_reference_4") == null || _rs.getString("pdc_reference_4").equals("")) ? "NA" : _rs.getString("pdc_reference_4"));
                    m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_reference_5") == null || _rs.getString("pdc_reference_5").equals("")) ? "NA" : _rs.getString("pdc_reference_5"));
                    m_jsArr.put(i, m_jsObj);
                    i++;

                } else {
                    m_jsObj = new JSONObject();
                    m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                    m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                    m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                    m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                    m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                    m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_old_value_date", _rs.getString("pdc_old_value_date"));
                    m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                    m_jsObj.put("pdc_chq_discounting_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_discounting_amu"))));
                    m_jsObj.put("current_status", current_status);
                    m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                    m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_reference_1") == null || _rs.getString("pdc_reference_1").equals("")) ? "NA" : _rs.getString("pdc_reference_1"));
                    m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_reference_2") == null || _rs.getString("pdc_reference_2").equals("")) ? "NA" : _rs.getString("pdc_reference_2"));
                    m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_reference_3") == null || _rs.getString("pdc_reference_3").equals("")) ? "NA" : _rs.getString("pdc_reference_3"));
                    m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_reference_4") == null || _rs.getString("pdc_reference_4").equals("")) ? "NA" : _rs.getString("pdc_reference_4"));
                    m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_reference_5") == null || _rs.getString("pdc_reference_5").equals("")) ? "NA" : _rs.getString("pdc_reference_5"));
                    m_jsArr.put(i, m_jsObj);
                    i++;

                    if (!(_rs.getString("pdc_cust_paym_reference_1") == null)) {
                        m_jsObj = new JSONObject();
                        m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                        m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                        m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                        m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                        m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                        m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                        m_jsObj.put("pdc_old_value_date", _rs.getString("pdc_old_value_date"));
                        m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                        m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                        m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                        m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                        m_jsObj.put("pdc_chq_discounting_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_discounting_amu"))));
                        m_jsObj.put("current_status", current_status);
                        m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                        m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_cust_paym_reference_1") == null || _rs.getString("pdc_cust_paym_reference_1").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_1"));
                        m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_cust_paym_reference_2") == null || _rs.getString("pdc_cust_paym_reference_2").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_2"));
                        m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_cust_paym_reference_3") == null || _rs.getString("pdc_cust_paym_reference_3").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_3"));
                        m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_cust_paym_reference_4") == null || _rs.getString("pdc_cust_paym_reference_4").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_4"));
                        m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_cust_paym_reference_5") == null || _rs.getString("pdc_cust_paym_reference_5").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_5"));
                        m_jsArr.put(i, m_jsObj);
                        i++;

                    }
                }

            }

        } catch (Exception e) {
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

    public JSONArray getReturnedClearedChequesSellerWise(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            String idndb_customer_define_seller_id = "";
            String date_from = "";
            String date_to = "";
            String pdc_status = "";
            String pdc_product = "";
            String pdc_report_type = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            date_from = prm_obj.getString("date_from");
            date_to = prm_obj.getString("date_to");
            pdc_status = prm_obj.getString("pdc_status");
            pdc_product = prm_obj.getString("pdc_product");
            pdc_report_type = prm_obj.getString("pdc_report_type");

            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT \n"
                        + "nptm_txn_master.* ,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                        + "FROM \n"
                        + "ndb_pdc_txn_master nptm_txn_master left join\n"
                        + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                        + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master inner join\n"
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
                        + "STR_TO_DATE(nptm_txn_master.pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') and \n"
                        + "nptm_txn_master.pdc_chq_status=? and \n"
                        + "nptm_txn_master.pdc_chq_status_auth=? and \n"
                        + "nptm_txn_master.pdc_req_financing=?";
                _prepStmnt = _currentCon.prepareStatement(m_strQry);
                _prepStmnt.setString(1, date_from);
                _prepStmnt.setString(2, date_to);
                _prepStmnt.setString(3, pdc_status);
                _prepStmnt.setString(4, "AUTHORIZED");
                _prepStmnt.setString(5, pdc_product);

            } else {

                m_strQry = "SELECT \n"
                        + "nptm_txn_master.* ,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_1 as pdc_cust_paym_reference_1,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_2 as pdc_cust_paym_reference_2,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_3 as pdc_cust_paym_reference_3,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_4 as pdc_cust_paym_reference_4,\n"
                        + "npucd_customezed_cheque_data.pdc_reference_5 as pdc_cust_paym_reference_5,\n"
                        + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                        + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                        + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                        + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                        + "FROM \n"
                        + "ndb_pdc_txn_master nptm_txn_master left join\n"
                        + "ndb_pdc_uploaded_customized_data npucd_customezed_cheque_data on \n"
                        + "nptm_txn_master.idndb_pdc_txn_master = npucd_customezed_cheque_data.idndb_pdc_txn_master join\n"
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
                        + "nptm_txn_master.idndb_customer_define_seller_id=? and \n"
                        + "STR_TO_DATE(nptm_txn_master.pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') and \n"
                        + "nptm_txn_master.pdc_chq_status=? and \n"
                        + "nptm_txn_master.pdc_chq_status_auth=? and \n"
                        + "nptm_txn_master.pdc_req_financing=?";

                _prepStmnt = _currentCon.prepareStatement(m_strQry);
                _prepStmnt.setString(1, idndb_customer_define_seller_id);
                _prepStmnt.setString(2, date_from);
                _prepStmnt.setString(3, date_to);
                _prepStmnt.setString(4, pdc_status);
                _prepStmnt.setString(5, "AUTHORIZED");
                _prepStmnt.setString(6, pdc_product);

            }

            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                if (pdc_report_type.equals("1")) {

                    m_jsObj = new JSONObject();
                    String current_status = "";
                    String pdc_chq_status = _rs.getString("pdc_chq_status");
                    String pdc_chq_status_auth = _rs.getString("pdc_chq_status_auth");
                    String pdc_value_date_ext = "";
                    if (_rs.getString("pdc_value_date_ext").equals("ACTIVE")) {
                        pdc_value_date_ext = " / VAL.DATE EXT. ";
                    }
                    current_status = pdc_chq_status + pdc_value_date_ext + "( " + pdc_chq_status_auth + " )";

                    m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                    m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                    m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                    m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                    m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                    m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_old_value_date", _rs.getString("pdc_old_value_date"));
                    m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                    m_jsObj.put("current_status", current_status);
                    m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                    m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_reference_1") == null || _rs.getString("pdc_reference_1").equals("")) ? "NA" : _rs.getString("pdc_reference_1"));
                    m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_reference_2") == null || _rs.getString("pdc_reference_2").equals("")) ? "NA" : _rs.getString("pdc_reference_2"));
                    m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_reference_3") == null || _rs.getString("pdc_reference_3").equals("")) ? "NA" : _rs.getString("pdc_reference_3"));
                    m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_reference_4") == null || _rs.getString("pdc_reference_4").equals("")) ? "NA" : _rs.getString("pdc_reference_4"));
                    m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_reference_5") == null || _rs.getString("pdc_reference_5").equals("")) ? "NA" : _rs.getString("pdc_reference_5"));
                    m_jsArr.put(i, m_jsObj);
                    i++;
                } else {
                    m_jsObj = new JSONObject();
                    String current_status = "";
                    String pdc_chq_status = _rs.getString("pdc_chq_status");
                    String pdc_chq_status_auth = _rs.getString("pdc_chq_status_auth");
                    String pdc_value_date_ext = "";
                    if (_rs.getString("pdc_value_date_ext").equals("ACTIVE")) {
                        pdc_value_date_ext = " / VAL.DATE EXT. ";
                    }
                    current_status = pdc_chq_status + pdc_value_date_ext + "( " + pdc_chq_status_auth + " )";

                    m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                    m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                    m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                    m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                    m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                    m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_old_value_date", _rs.getString("pdc_old_value_date"));
                    m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                    m_jsObj.put("current_status", current_status);
                    m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                    m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_reference_1") == null || _rs.getString("pdc_reference_1").equals("")) ? "NA" : _rs.getString("pdc_reference_1"));
                    m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_reference_2") == null || _rs.getString("pdc_reference_2").equals("")) ? "NA" : _rs.getString("pdc_reference_2"));
                    m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_reference_3") == null || _rs.getString("pdc_reference_3").equals("")) ? "NA" : _rs.getString("pdc_reference_3"));
                    m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_reference_4") == null || _rs.getString("pdc_reference_4").equals("")) ? "NA" : _rs.getString("pdc_reference_4"));
                    m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_reference_5") == null || _rs.getString("pdc_reference_5").equals("")) ? "NA" : _rs.getString("pdc_reference_5"));
                    m_jsArr.put(i, m_jsObj);
                    i++;

                    if (!(_rs.getString("pdc_cust_paym_reference_1") == null)) {
                        m_jsObj = new JSONObject();
                        m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                        m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                        m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                        m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));
                        m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                        m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                        m_jsObj.put("pdc_old_value_date", _rs.getString("pdc_old_value_date"));
                        m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                        m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                        m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                        m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                        m_jsObj.put("current_status", current_status);
                        m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null || _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr").equals("")) ? "NA" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
                        m_jsObj.put("pdc_reference_1", (_rs.getString("pdc_cust_paym_reference_1") == null || _rs.getString("pdc_cust_paym_reference_1").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_1"));
                        m_jsObj.put("pdc_reference_2", (_rs.getString("pdc_cust_paym_reference_2") == null || _rs.getString("pdc_cust_paym_reference_2").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_2"));
                        m_jsObj.put("pdc_reference_3", (_rs.getString("pdc_cust_paym_reference_3") == null || _rs.getString("pdc_cust_paym_reference_3").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_3"));
                        m_jsObj.put("pdc_reference_4", (_rs.getString("pdc_cust_paym_reference_4") == null || _rs.getString("pdc_cust_paym_reference_4").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_4"));
                        m_jsObj.put("pdc_reference_5", (_rs.getString("pdc_cust_paym_reference_5") == null || _rs.getString("pdc_cust_paym_reference_5").equals("")) ? "NA" : _rs.getString("pdc_cust_paym_reference_5"));
                        m_jsArr.put(i, m_jsObj);
                        i++;

                    }

                }

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

    public JSONArray getGefuReport(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            String gefu_date = "";

            gefu_date = prm_obj.getString("gefu_date");

            String[] sys_date_mode = gefu_date.split("/");
            String mod_day = sys_date_mode[0];
            String mod_month = sys_date_mode[1];
            String mod_year = sys_date_mode[2];
            gefu_date = mod_year + mod_month + mod_day;

            m_strQry = "select\n"
                    + "(SELECT sum(amount) as total_amount FROM gefu_file_generation_hst where gefu_creation_status='PROCESSED' and status='AUTHORIZED' and date=?) as total_amount,\n"
                    + "(SELECT sum(amount) as total_credit_amount FROM gefu_file_generation_hst where gefu_creation_status='PROCESSED' and status='AUTHORIZED' and date=? and credit_debit='C') as total_credit_amount,\n"
                    + "(SELECT sum(amount) as total_debit_amount FROM gefu_file_generation_hst where gefu_creation_status='PROCESSED' and status='AUTHORIZED' and date=? and credit_debit='D')  as total_debit_amount,\n"
                    + "(SELECT count(idgefu_file_generation) as total_record_count FROM gefu_file_generation_hst where gefu_creation_status='PROCESSED' and status='AUTHORIZED' and date=?) as total_record_count,\n"
                    + "(SELECT count(idgefu_file_generation) as total_credit_record_count FROM gefu_file_generation_hst where gefu_creation_status='PROCESSED' and status='AUTHORIZED' and date=? and credit_debit='C') as total_credit_record_count,\n"
                    + "(SELECT count(idgefu_file_generation) as total_debit_record_count FROM gefu_file_generation_hst where gefu_creation_status='PROCESSED' and status='AUTHORIZED' and date=? and credit_debit='D') as total_debit_record_count";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _prepStmnt.setString(1, gefu_date);
            _prepStmnt.setString(2, gefu_date);
            _prepStmnt.setString(3, gefu_date);
            _prepStmnt.setString(4, gefu_date);
            _prepStmnt.setString(5, gefu_date);
            _prepStmnt.setString(6, gefu_date);
            _rs = _prepStmnt.executeQuery();

            String m_total_amount = "";
            String m_total_credit_amount = "";
            String m_total_debit_amount = "";
            String m_total_record_count = "";
            String m_total_credit_record_count = "";
            String m_total_debit_record_count = "";
            if (_rs.next()) {
                m_total_amount = _rs.getString("total_amount");
                m_total_credit_amount = _rs.getString("total_credit_amount");
                m_total_debit_amount = _rs.getString("total_debit_amount");
                m_total_record_count = _rs.getString("total_record_count");
                m_total_credit_record_count = _rs.getString("total_credit_record_count");
                m_total_debit_record_count = _rs.getString("total_debit_record_count");
            }

            m_strQry = "SELECT \n"
                    + "nptm_txn_master.* ,\n"
                    + "nggh_gefu_his.* ,\n"
                    + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                    + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                    + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                    + "ncd_cust_define_buyer.cust_name as buyer_cust_name\n"
                    + "FROM \n"
                    + "gefu_file_generation_hst nggh_gefu_his inner join\n"
                    + "ndb_pdc_txn_master nptm_txn_master on\n"
                    + "nggh_gefu_his.idndb_pdc_txn_master = nptm_txn_master.idndb_pdc_txn_master inner join\n"
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
                    + "nggh_gefu_his.gefu_creation_status=? and\n"
                    + "nggh_gefu_his.status=? and\n"
                    + "nggh_gefu_his.date = ? and \n"
                    + "(nggh_gefu_his.idndb_pdc_txn_master not like '%COMM%' and\n"
                    + "nggh_gefu_his.idndb_pdc_txn_master not like '%MRG%' and\n"
                    + "nggh_gefu_his.idndb_pdc_txn_master not like '%COMM%')";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _prepStmnt.setString(1, "PROCESSED");
            _prepStmnt.setString(2, "AUTHORIZED");
            _prepStmnt.setString(3, gefu_date);

            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_pdc_txn_master", _rs.getString("idndb_pdc_txn_master"));
                m_jsObj.put("seller", _rs.getString("seller_cust_id") + "," + _rs.getString("seller_cust_name"));
                m_jsObj.put("buyer", _rs.getString("buyer_cust_id") + "," + _rs.getString("buyer_cust_name"));
                m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                m_jsObj.put("credit_debit", _rs.getString("credit_debit"));
                m_jsObj.put("amount", df.format(Double.parseDouble(_rs.getString("amount"))));
                m_jsObj.put("narration", _rs.getString("narration"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }

            m_strQry = "SELECT \n"
                    + "ncd_cust.cust_id,\n"
                    + "ncd_cust.cust_name,\n"
                    + "nggh_gefu_his.* \n"
                    + "FROM \n"
                    + "gefu_file_generation_hst nggh_gefu_his inner join\n"
                    + "ndb_customer_define ncd_cust on\n"
                    + "nggh_gefu_his.idndb_pdc_txn_master = ncd_cust.idndb_customer_define\n"
                    + "where \n"
                    + "nggh_gefu_his.gefu_creation_status=? and\n"
                    + "nggh_gefu_his.status=? and\n"
                    + "nggh_gefu_his.date = ? and \n"
                    + "(nggh_gefu_his.idndb_pdc_txn_master like '%COMM%' or\n"
                    + "nggh_gefu_his.idndb_pdc_txn_master like '%MRG%' or\n"
                    + "nggh_gefu_his.idndb_pdc_txn_master like '%DISBBULKCREDIT%')";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _prepStmnt.setString(1, "PROCESSED");
            _prepStmnt.setString(2, "AUTHORIZED");
            _prepStmnt.setString(3, gefu_date);

            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("seller", _rs.getString("cust_id") + "," + _rs.getString("cust_name"));
                m_jsObj.put("buyer", "N/A");
                m_jsObj.put("pdc_chq_number", "N/A");
                m_jsObj.put("pdc_bank_code", "N/A");
                m_jsObj.put("pdc_branch_code", "N/A");
                m_jsObj.put("pdc_chq_amu", "N/A");
                m_jsObj.put("credit_debit", _rs.getString("credit_debit"));
                m_jsObj.put("amount", _rs.getString("amount"));
                m_jsObj.put("narration", _rs.getString("narration"));
                m_jsArr.put(i, m_jsObj);
                i++;

            }

            if (i >= 0) {

                m_jsObj = new JSONObject();

                m_jsObj.put("idndb_pdc_txn_master", "N/A");
                m_jsObj.put("seller", "N/A");
                m_jsObj.put("buyer", "N/A");
                m_jsObj.put("pdc_chq_number", "N/A");
                m_jsObj.put("pdc_bank_code", "N/A");
                m_jsObj.put("pdc_branch_code", "N/A");
                m_jsObj.put("pdc_chq_amu", "N/A");
                m_jsObj.put("credit_debit", "N/A");
                m_jsObj.put("amount", "Total Amount");
                m_jsObj.put("narration", df.format(Double.parseDouble(m_total_amount)));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
            if (i >= 0) {

                m_jsObj = new JSONObject();

                m_jsObj.put("idndb_pdc_txn_master", "N/A");
                m_jsObj.put("seller", "N/A");
                m_jsObj.put("buyer", "N/A");
                m_jsObj.put("pdc_chq_number", "N/A");
                m_jsObj.put("pdc_bank_code", "N/A");
                m_jsObj.put("pdc_branch_code", "N/A");
                m_jsObj.put("pdc_chq_amu", "N/A");
                m_jsObj.put("credit_debit", "N/A");
                m_jsObj.put("amount", "Total Record Count");
                m_jsObj.put("narration", m_total_record_count);
                m_jsArr.put(i, m_jsObj);
                i++;

            }
            if (i >= 0) {

                m_jsObj = new JSONObject();

                m_jsObj.put("idndb_pdc_txn_master", "N/A");
                m_jsObj.put("seller", "N/A");
                m_jsObj.put("buyer", "N/A");
                m_jsObj.put("pdc_chq_number", "N/A");
                m_jsObj.put("pdc_bank_code", "N/A");
                m_jsObj.put("pdc_branch_code", "N/A");
                m_jsObj.put("pdc_chq_amu", "N/A");
                m_jsObj.put("credit_debit", "N/A");
                m_jsObj.put("amount", "Total Credit Amount");
                m_jsObj.put("narration", df.format(Double.parseDouble(m_total_credit_amount)));
                m_jsArr.put(i, m_jsObj);
                i++;

            }
            if (i >= 0) {

                m_jsObj = new JSONObject();

                m_jsObj.put("idndb_pdc_txn_master", "N/A");
                m_jsObj.put("seller", "N/A");
                m_jsObj.put("buyer", "N/A");
                m_jsObj.put("pdc_chq_number", "N/A");
                m_jsObj.put("pdc_bank_code", "N/A");
                m_jsObj.put("pdc_branch_code", "N/A");
                m_jsObj.put("pdc_chq_amu", "N/A");
                m_jsObj.put("credit_debit", "N/A");
                m_jsObj.put("amount", "Total Credit Record Count");
                m_jsObj.put("narration", m_total_credit_record_count);
                m_jsArr.put(i, m_jsObj);
                i++;

            }

            if (i >= 0) {

                m_jsObj = new JSONObject();

                m_jsObj.put("idndb_pdc_txn_master", "N/A");
                m_jsObj.put("seller", "N/A");
                m_jsObj.put("buyer", "N/A");
                m_jsObj.put("pdc_chq_number", "N/A");
                m_jsObj.put("pdc_bank_code", "N/A");
                m_jsObj.put("pdc_branch_code", "N/A");
                m_jsObj.put("pdc_chq_amu", "N/A");
                m_jsObj.put("credit_debit", "N/A");
                m_jsObj.put("amount", "Total Debit Amount");
                m_jsObj.put("narration", df.format(Double.parseDouble(m_total_debit_amount)));
                m_jsArr.put(i, m_jsObj);
                i++;

            }

            if (i >= 0) {

                m_jsObj = new JSONObject();

                m_jsObj.put("idndb_pdc_txn_master", "N/A");
                m_jsObj.put("seller", "N/A");
                m_jsObj.put("buyer", "N/A");
                m_jsObj.put("pdc_chq_number", "N/A");
                m_jsObj.put("pdc_bank_code", "N/A");
                m_jsObj.put("pdc_branch_code", "N/A");
                m_jsObj.put("pdc_chq_amu", "N/A");
                m_jsObj.put("credit_debit", "N/A");
                m_jsObj.put("amount", "Total Debit Record Count");
                m_jsObj.put("narration", m_total_debit_record_count);
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

    public JSONArray getUnauthPdcChequesForAuthorization(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        String m_strQry2 = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            String idndb_customer_define_seller_id = "";
            String pdc_product = "";
            String pdc_report_type = "";

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            pdc_product = prm_obj.getString("pdc_product");
            pdc_report_type = prm_obj.getString("pdc_report_type");

            if (pdc_report_type.equals("MANUAL")) {
                pdc_report_type = "nptm_txn_master.pdc_chq_batch_no ='*'";
            }
            if (pdc_report_type.equals("BULK")) {
                pdc_report_type = "nptm_txn_master.pdc_chq_batch_no not in ('*')";
            }

            m_strQry = "SELECT \n"
                    + "nptm_txn_master.idndb_pdc_txn_master,\n"
                    + "nptm_txn_master.pdc_chq_number,\n"
                    + "nptm_txn_master.pdc_bank_code,\n"
                    + "nptm_txn_master.pdc_branch_code,\n"
                    + "nptm_txn_master.pdc_booking_date,\n"
                    + "nptm_txn_master.pdc_value_date,\n"
                    + "nptm_txn_master.pdc_chq_amu,\n"
                    + "nptm_txn_master.pdc_chq_batch_no,\n"
                    + "nptm_txn_master.pdc_chq_creat_by,\n"
                    + "nptm_txn_master.pdc_chq_mod_by,\n"
                    + "nptm_txn_master.pdc_chq_mod_date,\n"
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
                    + "ncpm_cust_prod_map_buyer.idndb_customer_define =ncd_cust_define_buyer.idndb_customer_define \n"
                    + "where\n"
                    + "nptm_txn_master.pdc_chq_status in ('ACTIVE','ERLYLIQUDED') and \n"
                    + "nptm_txn_master.pdc_req_financing=? and \n"
                    + "nptm_txn_master.pdc_chq_status_auth in ('UN-AUTHORIZED') and \n"
                    + "" + pdc_report_type + " and \n"
                    + "nptm_txn_master.idndb_customer_define_seller_id=?";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);

            _prepStmnt.setString(1, pdc_product);
            _prepStmnt.setString(2, idndb_customer_define_seller_id);

            _rs = _prepStmnt.executeQuery();
            log.info(_prepStmnt);
            while (_rs.next()) {

                m_strQry2 = "select ndb_change from ndb_change_log where ndb_change_log_type='PDCTXN' and status='UN-AUTHORIZED'  and ndb_attached_id=?";
                _prepStmnt2 = _currentCon.prepareStatement(m_strQry2);
                _prepStmnt2.setString(1, _rs.getString("idndb_pdc_txn_master"));

                _rs2 = _prepStmnt2.executeQuery();
                log.info(_prepStmnt2);
                String m_pdc_modification = "";
                while (_rs2.next()) {
                    m_pdc_modification = m_pdc_modification + _rs2.getString("ndb_change");
                }

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_pdc_txn_master", _rs.getString("idndb_pdc_txn_master"));

                m_jsObj.put("cust_data", _rs.getString("seller_cust_id") + "," + _rs.getString("seller_cust_name"));

                m_jsObj.put("buy_data", _rs.getString("buyer_cust_id") + "," + _rs.getString("buyer_cust_name"));

                m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                m_jsObj.put("modification", m_pdc_modification);
                m_jsObj.put("pdc_chq_batch_no", _rs.getString("pdc_chq_batch_no"));
                m_jsObj.put("pdc_chq_creat_by", _rs.getString("pdc_chq_creat_by"));
                m_jsObj.put("pdc_chq_mod_by", _rs.getString("pdc_chq_mod_by") + "</br>" + _rs.getString("pdc_chq_mod_date"));
                m_jsObj.put("chqbox", "<td><div class=\"controls\"><input class=\"check_boxes optional\" type=\"checkbox\" name=\"active\" id=\"active\" value=\"" + _rs.getString("idndb_pdc_txn_master") + "\"/></div><td>");

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

    public JSONArray cheque_verifyication(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        DecimalFormat df = new DecimalFormat("#,###.00");
        String m_strQry = "";
        ResultSet m_rs1 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            String cheque_number_input = "";
            String bank_code_input = "";
            String branch_code_input = "";
            comboDAO cmbDAO = new comboDAO();
            String _system_date = cmbDAO.getSystemDate();

            cheque_number_input = prm_obj.getString("cheque_number_input");
            bank_code_input = prm_obj.getString("bank_code_input");
            branch_code_input = prm_obj.getString("branch_code_input");
            int cheque_count = 0;
            if (bank_code_input.equals("") && branch_code_input.equals("")) {
                m_strQry = "select\n"
                        + "\n"
                        + "nptm.pdc_chq_number,\n"
                        + "COUNT(nptm.pdc_chq_number) as cheque_count,\n"
                        + "nptm.idndb_pdc_txn_master,\n"
                        + "nptm.pdc_bank_code,\n"
                        + "nptm.pdc_branch_code,\n"
                        + "nptm.pdc_chq_amu,\n"
                        + "nptm.pdc_value_date,\n"
                        + "ncd.cust_id as seller_id,\n"
                        + "ncd.cust_name as seller_name,\n"
                        + "ncd1.cust_id as buyer_id,\n"
                        + "ncd1.cust_name as buyer_name\n"
                        + "\n"
                        + "from ndb_pdc_txn_master nptm,\n"
                        + "ndb_seller_has_buyers nshb,\n"
                        + "ndb_cust_prod_map ncpm,\n"
                        + "ndb_cust_prod_map ncpm1,\n"
                        + "ndb_customer_define ncd,\n"
                        + "ndb_customer_define ncd1\n"
                        + "\n"
                        + "\n"
                        + "where STR_TO_DATE(nptm.pdc_value_date, '%d/%m/%Y') >= STR_TO_DATE('" + _system_date + "', '%d/%m/%Y') and STR_TO_DATE(nptm.pdc_booking_date, '%d/%m/%Y') != STR_TO_DATE('" + _system_date + "', '%d/%m/%Y') and\n"
                        + "nptm.pdc_chq_number ='" + cheque_number_input + "' and\n"
                        + "nptm.idndb_customer_define_buyer_id =nshb.idndb_seller_has_buyers and\n"
                        + "ncpm.idndb_cust_prod_map=nshb.idndb_customer_define_seller and\n"
                        + "ncpm1.idndb_cust_prod_map=nshb.idndb_customer_define_buyer and\n"
                        + "ncd.idndb_customer_define=ncpm.idndb_customer_define and\n"
                        + "ncd1.idndb_customer_define=ncpm1.idndb_customer_define and\n"
                        + "nptm.pdc_chq_status='ACTIVE' and nptm.pdc_chq_status_auth='AUTHORIZED'";
                m_rs1 = _stmnt.executeQuery(m_strQry);
                System.out.println(m_strQry);

                while (m_rs1.next()) {
                    m_jsObj = new JSONObject();
                    cheque_count = Integer.parseInt(m_rs1.getString("cheque_count"));
                    if (cheque_count == 1) {
                        m_jsObj.put("pdc_chq_verification", "Available");
                        m_jsObj.put("idndb_pdc_txn_master", m_rs1.getString("idndb_pdc_txn_master"));
                        m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                        m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                        m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                        m_jsObj.put("seller_id", m_rs1.getString("seller_id"));
                        m_jsObj.put("seller_name", m_rs1.getString("seller_name"));
                        m_jsObj.put("buyer_id", m_rs1.getString("buyer_id"));
                        m_jsObj.put("buyer_name", m_rs1.getString("buyer_name"));
                        m_jsObj.put("value_date", m_rs1.getString("pdc_value_date"));
                        m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));

                    } else if (cheque_count > 1) {
                        m_jsObj.put("pdc_chq_verification", "Duplicate");
                        m_jsObj.put("idndb_pdc_txn_master", m_rs1.getString("idndb_pdc_txn_master"));
                        m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                        m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                        m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                        m_jsObj.put("seller_id", m_rs1.getString("seller_id"));
                        m_jsObj.put("seller_name", m_rs1.getString("seller_name"));
                        m_jsObj.put("buyer_id", m_rs1.getString("buyer_id"));
                        m_jsObj.put("buyer_name", m_rs1.getString("buyer_name"));
                        m_jsObj.put("value_date", m_rs1.getString("pdc_value_date"));
                        m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));

                    } else if (cheque_count == 0) {
                        m_jsObj.put("pdc_chq_verification", "Notavailable");

                    }

                    m_jsArr.put(i, m_jsObj);
                    i++;
                }
            } else {
                m_strQry = "select\n"
                        + "\n"
                        + "nptm.pdc_chq_number,\n"
                        + "COUNT(nptm.pdc_chq_number) as cheque_count,\n"
                        + "nptm.idndb_pdc_txn_master,\n"
                        + "nptm.pdc_bank_code,\n"
                        + "nptm.pdc_branch_code,\n"
                        + "nptm.pdc_chq_amu,\n"
                        + "nptm.pdc_value_date,\n"
                        + "ncd.cust_id as seller_id,\n"
                        + "ncd.cust_name as seller_name,\n"
                        + "ncd1.cust_id as buyer_id,\n"
                        + "ncd1.cust_name as buyer_name\n"
                        + "\n"
                        + "from ndb_pdc_txn_master nptm,\n"
                        + "ndb_seller_has_buyers nshb,\n"
                        + "ndb_cust_prod_map ncpm,\n"
                        + "ndb_cust_prod_map ncpm1,\n"
                        + "ndb_customer_define ncd,\n"
                        + "ndb_customer_define ncd1\n"
                        + "\n"
                        + "\n"
                        + "where STR_TO_DATE(nptm.pdc_value_date, '%d/%m/%Y') >= STR_TO_DATE('" + _system_date + "', '%d/%m/%Y') and STR_TO_DATE(nptm.pdc_booking_date, '%d/%m/%Y') != STR_TO_DATE('" + _system_date + "', '%d/%m/%Y') and\n"
                        + "nptm.pdc_chq_number ='" + cheque_number_input + "' and \n"
                        + "nptm.pdc_bank_code ='" + bank_code_input + "' and \n"
                        + "nptm.pdc_branch_code='" + branch_code_input + "' and \n"
                        + "nptm.idndb_customer_define_buyer_id =nshb.idndb_seller_has_buyers and\n"
                        + "ncpm.idndb_cust_prod_map=nshb.idndb_customer_define_seller and\n"
                        + "ncpm1.idndb_cust_prod_map=nshb.idndb_customer_define_buyer and\n"
                        + "ncd.idndb_customer_define=ncpm.idndb_customer_define and\n"
                        + "ncd1.idndb_customer_define=ncpm1.idndb_customer_define and\n"
                        + "nptm.pdc_chq_status='ACTIVE' and nptm.pdc_chq_status_auth='AUTHORIZED'";
                System.out.println(m_strQry);
                m_rs1 = _stmnt.executeQuery(m_strQry);

                while (m_rs1.next()) {
                    m_jsObj = new JSONObject();
                    cheque_count = Integer.parseInt(m_rs1.getString("cheque_count"));
                    if (cheque_count == 1) {
                        m_jsObj.put("pdc_chq_verification", "Available");
                        m_jsObj.put("idndb_pdc_txn_master", m_rs1.getString("idndb_pdc_txn_master"));
                        m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                        m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                        m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                        m_jsObj.put("seller_id", m_rs1.getString("seller_id"));
                        m_jsObj.put("seller_name", m_rs1.getString("seller_name"));
                        m_jsObj.put("buyer_id", m_rs1.getString("buyer_id"));
                        m_jsObj.put("buyer_name", m_rs1.getString("buyer_name"));
                        m_jsObj.put("value_date", m_rs1.getString("pdc_value_date"));
                        m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));

                    } else if (cheque_count > 1) {
                        m_jsObj.put("pdc_chq_verification", "Duplicate");
                        m_jsObj.put("idndb_pdc_txn_master", m_rs1.getString("idndb_pdc_txn_master"));
                        m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                        m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                        m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                        m_jsObj.put("seller_id", m_rs1.getString("seller_id"));
                        m_jsObj.put("seller_name", m_rs1.getString("seller_name"));
                        m_jsObj.put("buyer_id", m_rs1.getString("buyer_id"));
                        m_jsObj.put("buyer_name", m_rs1.getString("buyer_name"));
                        m_jsObj.put("value_date", m_rs1.getString("pdc_value_date"));
                        m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));

                    } else if (cheque_count == 0) {
                        m_jsObj.put("pdc_chq_verification", "Notavailable");

                    }

                    m_jsArr.put(i, m_jsObj);
                    i++;
                }

            }

        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in generate report, Exception" + e);
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
        return m_jsArr;
    }

    public JSONArray get_pdc_rf_additional_day_seller_wise(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            String idndb_customer_define_seller_id = "";
            String date_from = prm_obj.getString("date_from");
            String date_to = prm_obj.getString("date_to");

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_req_financing='RF' and pdc_additional_day='ACTIVE' and STR_TO_DATE(pdc_old_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

            } else {
                m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and pdc_req_financing='RF' and pdc_additional_day='ACTIVE' and STR_TO_DATE(pdc_old_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

            }

            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_jsObj = new JSONObject();
                String current_status = "";
                String pdc_chq_status = m_rs1.getString("pdc_chq_status");
                String pdc_chq_status_auth = m_rs1.getString("pdc_chq_status_auth");
                String idndb_seller_has_buyers = m_rs1.getString("idndb_customer_define_buyer_id");
                String idndb_customer_define_seller_id_se = m_rs1.getString("idndb_customer_define_seller_id");

                String pdc_value_date_ext = "";
                if (m_rs1.getString("pdc_value_date_ext").equals("ACTIVE")) {
                    pdc_value_date_ext = " / VAL.DATE EXT. ";
                }
                current_status = pdc_chq_status + pdc_value_date_ext + "( " + pdc_chq_status_auth + " )";

                m_strQry2 = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String idndb_customer_define_buyer = "";
                String idndb_customer_define_seller_idndb_customer_define = "";
                if (m_rs2.next()) {
                    idndb_customer_define_buyer = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_buyer + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller_idndb_customer_define = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name = "";
                String cust_id = "";
                if (m_rs2.next()) {

                    cust_name = m_rs2.getString("cust_name");
                    cust_id = m_rs2.getString("cust_id");
                }

                String idndb_customer_define_seller = "";

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller_id_se + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name_seller = "";
                String cust_id_seller = "";
                if (m_rs2.next()) {

                    cust_name_seller = m_rs2.getString("cust_name");
                    cust_id_seller = m_rs2.getString("cust_id");
                }

                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);

                m_jsObj.put("cust_id", cust_id);
                m_jsObj.put("cust_name", cust_name);

                m_jsObj.put("pdc_booking_date", m_rs1.getString("pdc_booking_date"));
                m_jsObj.put("pdc_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_old_value_date", m_rs1.getString("pdc_old_value_date"));
                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));
                m_jsObj.put("pdc_chq_discounting_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_discounting_amu"))));
                m_jsObj.put("current_status", current_status);
                m_jsArr.put(i, m_jsObj);
                i++;
            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray get_pdc_rf_returned_seller_wise(JSONObject prm_obj) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            String idndb_customer_define_seller_id = "";
            String status = prm_obj.getString("status");
            String date_from = prm_obj.getString("date_from");
            String date_to = prm_obj.getString("date_to");

            idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            if (idndb_customer_define_seller_id.equals("all")) {
                m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_req_financing='RF' and pdc_chq_status='" + status + "' and pdc_chq_status_auth='AUTHORIZED' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

            } else {
                m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "' and pdc_req_financing='RF' and pdc_chq_status='" + status + "' and pdc_chq_status_auth='AUTHORIZED' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + date_from + "', '%d/%m/%Y') AND STR_TO_DATE('" + date_to + "', '%d/%m/%Y')";

            }

            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {

                m_jsObj = new JSONObject();
                String pdc_chq_status = m_rs1.getString("pdc_chq_status");
                String pdc_chq_status_auth = m_rs1.getString("pdc_chq_status_auth");
                String idndb_seller_has_buyers = m_rs1.getString("idndb_customer_define_buyer_id");
                String idndb_customer_define_seller_id_se = m_rs1.getString("idndb_customer_define_seller_id");

                m_strQry2 = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String idndb_customer_define_buyer = "";
                String idndb_customer_define_seller_idndb_customer_define = "";
                if (m_rs2.next()) {
                    idndb_customer_define_buyer = m_rs2.getString("idndb_customer_define_buyer");

                }

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_buyer + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller_idndb_customer_define = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name = "";
                String cust_id = "";
                if (m_rs2.next()) {

                    cust_name = m_rs2.getString("cust_name");
                    cust_id = m_rs2.getString("cust_id");
                }

                String idndb_customer_define_seller = "";

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller_id_se + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                if (m_rs2.next()) {
                    idndb_customer_define_seller = m_rs2.getString("idndb_customer_define");
                }

                m_strQry2 = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                String cust_name_seller = "";
                String cust_id_seller = "";
                if (m_rs2.next()) {

                    cust_name_seller = m_rs2.getString("cust_name");
                    cust_id_seller = m_rs2.getString("cust_id");
                }

                m_jsObj.put("cust_id_seller", cust_id_seller);
                m_jsObj.put("cust_name_seller", cust_name_seller);

                m_jsObj.put("cust_id", cust_id);
                m_jsObj.put("cust_name", cust_name);

                m_jsObj.put("pdc_booking_date", m_rs1.getString("pdc_booking_date"));
                m_jsObj.put("pdc_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_old_value_date", m_rs1.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_amu"))));
                m_jsObj.put("pdc_chq_discounting_amu", df.format(Double.parseDouble(m_rs1.getString("pdc_chq_discounting_amu"))));

                m_jsArr.put(i, m_jsObj);
                i++;
            }

        } catch (Exception e) {
            log.error("Error occured in generate report, Exception" + e);
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
                if (m_rs3 != null) {
                    m_rs3.close();
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
