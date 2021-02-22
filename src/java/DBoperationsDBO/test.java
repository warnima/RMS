/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBoperationsDBO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import ndb.connection.ConnectionPool;
import org.json.JSONObject;

/**
 *
 * @author Madhawa_4799
 */
public class test {

    public static void main(String[] args) {
        test t = new test();
        ConnectionPool _connectionPool;
        Connection _currentCon = null;
        Statement _stmnt = null;
        Statement _stmnt2 = null;
        String datas = "no";
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {

            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            if (!t.startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            System.out.println("all oh");

            m_chstrsql = "SELECT\n"
                    + "     gefu_file_generation.`idgefu_file_generation` AS gefu_file_generation_idgefu_file_generation,\n"
                    + "     gefu_file_generation.`idndb_pdc_txn_master` AS gefu_file_generation_idndb_pdc_txn_master,\n"
                    + "     ndb_pdc_txn_master.`idndb_pdc_txn_master` AS ndb_pdc_txn_master_idndb_pdc_txn_master,\n"
                    + "     ndb_pdc_txn_master.`pdc_req_financing` AS ndb_pdc_txn_master_pdc_req_financing,\n"
                    + "     ndb_pdc_txn_master.`idndb_customer_define_seller_id` AS ndb_pdc_txn_master_idndb_customer_define_seller_id,\n"
                    + "     ndb_pdc_txn_master.`idndb_customer_define_buyer_id` AS ndb_pdc_txn_master_idndb_customer_define_buyer_id,\n"
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
                    + "     ndb_cust_prod_map_A.`prod_relationship_mod_date` AS ndb_cust_prod_map_A_prod_relationship_mod_date,\n"
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
                    + "     gefu_file_generation.`account` AS gefu_file_generation_account,\n"
                    + "     gefu_file_generation.`currency` AS gefu_file_generation_currency,\n"
                    + "     gefu_file_generation.`date` AS gefu_file_generation_date,\n"
                    + "     gefu_file_generation.`amount` AS gefu_file_generation_amount,\n"
                    + "     gefu_file_generation.`narration` AS gefu_file_generation_narration,\n"
                    + "     gefu_file_generation.`credit_debit` AS gefu_file_generation_credit_debit,\n"
                    + "     gefu_file_generation.`profit_centre` AS gefu_file_generation_profit_centre,\n"
                    + "     gefu_file_generation.`DAO` AS gefu_file_generation_DAO,\n"
                    + "     gefu_file_generation.`c_amount` AS gefu_file_generation_c_amount,\n"
                    + "     gefu_file_generation.`d_amount` AS gefu_file_generation_d_amount,\n"
                    + "     gefu_file_generation.`gefu_creation_status` AS gefu_file_generation_gefu_creation_status,\n"
                    + "     gefu_file_generation.`status` AS gefu_file_generation_status,\n"
                    + "     gefu_file_generation.`creat_by` AS gefu_file_generation_creat_by,\n"
                    + "     gefu_file_generation.`creat_date` AS gefu_file_generation_creat_date,\n"
                    + "     gefu_file_generation.`mod_by` AS gefu_file_generation_mod_by,\n"
                    + "     gefu_file_generation.`mod_date` AS gefu_file_generation_mod_date,\n"
                    + "     gefu_file_generation.`system_date` AS gefu_file_generation_system_date,\n"
                    + "     gefu_file_generation.`cw_fixed_frequency` AS gefu_file_generation_cw_fixed_frequency,\n"
                    + "     gefu_file_generation.`gefu_type` AS gefu_file_generation_gefu_type,\n"
                    + "     gefu_file_generation.`bulk_credit` AS gefu_file_generation_bulk_credit\n"
                    + "FROM\n"
                    + "     `ndb_pdc_txn_master` ndb_pdc_txn_master INNER JOIN `gefu_file_generation` gefu_file_generation ON ndb_pdc_txn_master.`idndb_pdc_txn_master` = gefu_file_generation.`idndb_pdc_txn_master`\n"
                    + "     INNER JOIN `ndb_cust_prod_map` ndb_cust_prod_map_A ON ndb_pdc_txn_master.`idndb_customer_define_seller_id` = ndb_cust_prod_map_A.`idndb_cust_prod_map`\n"
                    + "     INNER JOIN `ndb_customer_define` ndb_customer_define ON ndb_cust_prod_map_A.`idndb_customer_define` = ndb_customer_define.`idndb_customer_define`,\n"
                    + "     `ndb_cust_prod_map` ndb_cust_prod_map WHERE gefu_file_generation.`gefu_type`='COMCHGBC'";
            m_rs = _stmnt.executeQuery(m_chstrsql);

            String cust_curr_acc = "";
            String gefu_id = "";
            while (m_rs.next()) {

                cust_curr_acc = m_rs.getString("ndb_customer_define_rec_finance_curr_ac");
                gefu_id = m_rs.getString("gefu_file_generation_idgefu_file_generation");
                System.out.println("account gefu_id" + gefu_id);
                String m_strQry = "update gefu_file_generation set account ='" + cust_curr_acc + "' where idgefu_file_generation='" + gefu_id + "'";

                if (_stmnt2.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            }

            if (!t.endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            datas = "ok";

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
