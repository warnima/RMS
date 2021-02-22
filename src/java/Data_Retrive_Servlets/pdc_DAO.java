/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_Retrive_Servlets;

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
import utility.Parameters;

/**
 *
 * @author Madhawa_4799
 */
public class pdc_DAO {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(pdc_DAO.class.getName());
    static ConnectionPool _connectionPool;
    static Connection _currentCon = null;
    private Statement _stmnt = null;
    private Statement _stmnt2 = null;
    private Statement _stmnt3 = null;
    private PreparedStatement _prepStmnt = null;
    private PreparedStatement _prepStmnt2 = null;
    private ResultSet _rs = null;
    private ResultSet _rs2 = null;
    private Exception _exception;

    public String saveCWPDCEntry(JSONObject prm_obj) {
        String _system_date = "";
        String cw_pdc_save_status = "0000";

        String m_str_user_id = "";
        String m_str_idndb_pdc_txn_master = "";

        String m_Stridndb_customer_define_seller_id = "";
        String m_StrOldidndb_customer_define_seller_id = "";

        String m_Stridndb_customer_define_buyer_id = "";
        String m_StrOldidndb_customer_define_buyer_id = "";

        String m_Strcust_bank = "";
        String m_StrOldcust_bank = "";

        String m_Strpdc_bank_name = "";
        String m_StrOldpdc_bank_name = "";

        String m_Strcust_bank_code = "";
        String m_StrOldcust_bank_code = "";

        String m_Strcust_branch = "";
        String m_StrOldcust_branch = "";

        String m_Strpdc_branch_name = "";
        String m_StrOldpdc_branch_name = "";

        String m_Strcust_branch_code = "";
        String m_StrOldcust_branch_code = "";

        String m_Strcw_value_date_temp = "";
        String m_Strcw_value_date = "";

        String m_StrOldcw_value_date = "";

        String m_Strcw_cheque_liq_date_temp = "";
        String m_Strcw_cheque_liq_date = "";

        String m_StrOldcw_cheque_liq_date = "";

        String m_str_cw_lequid_date = "";
        String m_strOld_cw_lequid_date = "";

        String m_Strcw_chq_number = "";
        String m_StrOldcw_chq_number = "";

        String m_Strcw_cheque_amu = "";
        String m_StrOldcw_cheque_amu = "";

        String m_Strcw_cheque_liquidation = "";
        String m_StrOldcw_cheque_liquidation = "";

        String m_strQry = "";
        String NDBCommisionPLAcc = "";

        boolean m_ardy_exist_data = true;
        boolean m_ardy_exist_dcheque = true;
        log.info("Manual CW PDC Entry");

        try {
            pdc_DAO dao = new pdc_DAO();
            Parameters para = new Parameters();
            NDBCommisionPLAcc = para.getNDBCommisionPLAcc();
            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_pdc_txn_master = prm_obj.getString("idndb_pdc_txn_master");

            m_Stridndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            m_Stridndb_customer_define_buyer_id = prm_obj.getString("idndb_customer_define_buyer_id");
            m_Strcust_bank = prm_obj.getString("cust_bank");
            m_Strcust_branch = prm_obj.getString("cust_branch");
            m_Strcw_value_date_temp = dao.getchequeValueDate(prm_obj.getString("cw_value_date"));
            m_Strcw_chq_number = prm_obj.getString("cw_chq_number");
            m_Strcw_cheque_amu = prm_obj.getString("cw_cheque_amu");
            m_Strcw_cheque_liquidation = prm_obj.getString("cw_cheque_liquidation");

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            // java.util.Date dt = new java.util.Date();
            // java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // m_Strcw_value_date = sdf.format(m_Strcw_value_date_temp);
            String[] date_spliter = m_Strcw_value_date_temp.split("/");
            int m_year = Integer.parseInt(date_spliter[2]);
            int m_month = Integer.parseInt(date_spliter[1]) - 1;
            int m_day = Integer.parseInt(date_spliter[0]);
            Calendar ced = Calendar.getInstance();

            ced.set(Calendar.YEAR, m_year); // set the year
            ced.set(Calendar.MONTH, m_month); // set the month
            ced.set(Calendar.DAY_OF_MONTH, m_day);
            ced.add(Calendar.DATE, 1);

            m_Strcw_cheque_liq_date_temp = dao.getchequeValueDate(formatter.format(ced.getTime()));
            // m_Strcw_cheque_liq_date = sdf.format(m_Strcw_cheque_liq_date_temp);

            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            m_strQry = "select * from ndb_system_date";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                _system_date = _rs.getString("_system_date");

            }

            m_strQry = "select * from ndb_bank_master_file where idndb_bank_master_file='" + m_Strcust_bank + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                m_Strcust_bank_code = _rs.getString("bank_code");
                m_Strpdc_bank_name = _rs.getString("bank_name");

            }
            m_strQry = "select * from ndb_branch_master_file where idndb_branch_master_file='" + m_Strcust_branch + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                m_Strcust_branch_code = _rs.getString("branch_id");
                m_Strpdc_branch_name = _rs.getString("branch_name");

            }

            String m_uniq_id = m_Strcw_chq_number + m_Strcust_bank_code + m_Strcust_branch_code;
            m_strQry = "SELECT CONCAT(pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code) as txn_master_unq_id FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE'";
            _rs = _stmnt.executeQuery(m_strQry);
            while (_rs.next()) {
                if (m_uniq_id.equals(_rs.getString("txn_master_unq_id"))) {
                    m_ardy_exist_dcheque = false;
                    //cheque number all ready exist
                    cw_pdc_save_status = "1101";
                }

            }

            m_strQry = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";
            log.info("Checking for receved idndb_pdc_txn_master : " + m_str_idndb_pdc_txn_master);

            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                log.info("already exist :receved idndb_pdc_txn_master : " + m_str_idndb_pdc_txn_master);

                m_StrOldidndb_customer_define_seller_id = _rs.getString("idndb_customer_define_seller_id");
                m_StrOldidndb_customer_define_buyer_id = _rs.getString("idndb_customer_define_buyer_id");
                m_StrOldcust_bank = _rs.getString("idndb_bank_master_file");
                m_StrOldcust_bank_code = _rs.getString("pdc_bank_code");
                m_StrOldcust_branch = _rs.getString("idndb_branch_master_file");
                m_StrOldcust_branch_code = _rs.getString("pdc_branch_code");
                m_StrOldcw_value_date = _rs.getString("pdc_value_date");
                m_StrOldcw_cheque_liq_date = _rs.getString("pdc_lquid_date");
                m_StrOldcw_chq_number = _rs.getString("pdc_chq_number");
                m_StrOldcw_cheque_amu = _rs.getString("pdc_chq_amu");
                m_StrOldpdc_bank_name = _rs.getString("pdc_bank_name");
                m_StrOldpdc_branch_name = _rs.getString("pdc_branch_name");

                m_ardy_exist_data = false;
            }

            // extract cw commision data
            String chq_wh_commision_crg = "NOTDEFINE";
            String old_chq_wh_commision_crg = "NOTDEFINE";
            String idndb_customer_define_seller = "";
            String old_idndb_customer_define_seller = "";
            String idndb_customer_define_seller_idndb_customer_define = "";
            String old_idndb_customer_define_seller_idndb_customer_define = "";
            String rec_finance_curr_ac = "";
            String old_rec_finance_curr_ac = "";
            String rec_finance_acc_num = "";
            String old_rec_finance_acc_num = "";
            String cust_name = "";
            String old_cust_name = "";
            double chq_wh_erly_wdr_chg = 0.00;
            double old_chq_wh_erly_wdr_chg = 0.00;
            double chq_wh_vale_dte_extr_chg = 0.00;
            double old_chq_wh_vale_dte_extr_chg = 0.00;
            double chq_wh_erly_stlemnt_chg = 0.00;
            double old_chq_wh_erly_stlemnt_chg = 0.00;
            double cw_tran_base_falt_fee = 0.00;
            double old_cw_tran_base_falt_fee = 0.00;
            double cw_tran_base_from_tran = 0.00;
            double old_cw_tran_base_from_tran = 0.00;
            double cw_fixed_rate_amount = 0.00;
            double old_cw_fixed_rate_amount = 0.00;
            String cw_fixed_frequency = "DAILY";
            String old_cw_fixed_frequency = "DAILY";
            m_strQry = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                chq_wh_commision_crg = _rs.getString("chq_wh_commision_crg");
                idndb_customer_define_seller = _rs.getString("idndb_customer_define_seller");

                cw_tran_base_falt_fee = _rs.getDouble("cw_tran_base_falt_fee");
                cw_tran_base_from_tran = _rs.getDouble("cw_tran_base_from_tran");

                cw_fixed_rate_amount = _rs.getDouble("cw_fixed_rate_amount");
                cw_fixed_frequency = _rs.getString("cw_fixed_frequency");

            }

            m_strQry = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                idndb_customer_define_seller_idndb_customer_define = _rs.getString("idndb_customer_define");
            }

            m_strQry = "select * from ndb_chq_wh where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                chq_wh_erly_wdr_chg = _rs.getDouble("chq_wh_erly_wdr_chg");
                chq_wh_vale_dte_extr_chg = _rs.getDouble("chq_wh_vale_dte_extr_chg");
                chq_wh_erly_stlemnt_chg = _rs.getDouble("chq_wh_erly_stlemnt_chg");
            }

            m_strQry = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                rec_finance_curr_ac = _rs.getString("rec_finance_curr_ac");
                rec_finance_acc_num = _rs.getString("rec_finance_acc_num");
                cust_name = _rs.getString("cust_name");
            }

            if (!m_ardy_exist_data) {

                log.info("already exist :receved idndb_pdc_txn_master : " + m_str_idndb_pdc_txn_master + " checking for old commision data and details");

                m_strQry = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_StrOldidndb_customer_define_buyer_id + "'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    old_chq_wh_commision_crg = _rs.getString("chq_wh_commision_crg");
                    old_idndb_customer_define_seller = _rs.getString("idndb_customer_define_seller");

                    old_cw_tran_base_falt_fee = _rs.getDouble("cw_tran_base_falt_fee");
                    old_cw_tran_base_from_tran = _rs.getDouble("cw_tran_base_from_tran");

                    old_cw_fixed_rate_amount = _rs.getDouble("cw_fixed_rate_amount");
                    old_cw_fixed_frequency = _rs.getString("cw_fixed_frequency");

                }

                m_strQry = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + old_idndb_customer_define_seller + "'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    old_idndb_customer_define_seller_idndb_customer_define = _rs.getString("idndb_customer_define");
                }

                m_strQry = "select * from ndb_chq_wh where idndb_cust_prod_map='" + old_idndb_customer_define_seller + "'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    old_chq_wh_erly_wdr_chg = _rs.getDouble("chq_wh_erly_wdr_chg");
                    old_chq_wh_vale_dte_extr_chg = _rs.getDouble("chq_wh_vale_dte_extr_chg");
                    old_chq_wh_erly_stlemnt_chg = _rs.getDouble("chq_wh_erly_stlemnt_chg");
                }

                m_strQry = "select * from ndb_customer_define where idndb_customer_define='" + old_idndb_customer_define_seller_idndb_customer_define + "'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    old_rec_finance_curr_ac = _rs.getString("rec_finance_curr_ac");
                    old_rec_finance_acc_num = _rs.getString("rec_finance_acc_num");
                    old_cust_name = _rs.getString("cust_name");
                }

            }
            int i = 0;
            String m_change = "";
            // end of extract cw commision data
            if (m_ardy_exist_data) {
                if (m_ardy_exist_dcheque) {

                    m_strQry = "insert into ndb_pdc_txn_master ("
                            + "pdc_req_financing"
                            + ",idndb_customer_define_seller_id"
                            + ",idndb_customer_define_buyer_id"
                            + ",idndb_bank_master_file"
                            + ",pdc_bank_code"
                            + ",idndb_branch_master_file"
                            + ",pdc_branch_code"
                            + ",pdc_chq_number"
                            + ",pdc_booking_date"
                            + ",pdc_value_date"
                            + ",pdc_lquid_date"
                            + ",pdc_chq_amu"
                            + ",pdc_chq_discounting_amu"
                            + ",pdc_chq_net_amu"
                            + ",pdc_chq_cr_amu"
                            + ",pdc_chq_status"
                            + ",pdc_chq_status_auth"
                            + ",pdc_chq_batch_no"
                            + ",cust_account_number"
                            + ",cust_name"
                            + ",pdc_bank_name"
                            + ",pdc_branch_name"
                            + ",pdc_value_date_ext"
                            + ",pdc_old_value_date"
                            + ",pdc_chq_creat_by"
                            + ",pdc_chq_creat_date"
                            + ",pdc_chq_mod_by"
                            + ",pdc_chq_mod_date"
                            + ""
                            + ") values("
                            + "'CW',"
                            + "'" + m_Stridndb_customer_define_seller_id + "',"
                            + "'" + m_Stridndb_customer_define_buyer_id + "',"
                            + "'" + m_Strcust_bank + "',"
                            + "'" + m_Strcust_bank_code + "',"
                            + "'" + m_Strcust_branch + "',"
                            + "'" + m_Strcust_branch_code + "',"
                            + "'" + m_Strcw_chq_number + "',"
                            + "'" + _system_date + "',"
                            + "'" + m_Strcw_value_date_temp + "',"
                            + "'" + m_Strcw_cheque_liq_date_temp + "',"
                            + "'" + m_Strcw_cheque_amu + "',"
                            + "'0.00',"
                            + "'0.00',"
                            + "'" + m_Strcw_cheque_amu + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'*',"
                            + "'" + rec_finance_acc_num + "',"
                            + "'" + cust_name + "',"
                            + "'" + m_Strpdc_bank_name + "',"
                            + "'" + m_Strpdc_branch_name + "',"
                            + "'DEACTIVE',"
                            + "'" + _system_date + "',"
                            + "'" + m_str_user_id + "',"
                            + "now(),"
                            + "'" + m_str_user_id + "',"
                            + "now())";
                    log.info("CW PDC New Entry");
                    log.info("CW PDC New Entry MYSQL Quiery" + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    String max_idndb_pdc_txn_master = "";
                    m_strQry = "select MAX(idndb_pdc_txn_master) as max_idndb_pdc_txn_master from ndb_pdc_txn_master";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        max_idndb_pdc_txn_master = _rs.getString("max_idndb_pdc_txn_master");
                    }

                    m_strQry = "insert into ndb_change_log (ndb_change_log_type,"
                            + "ndb_attached_id,"
                            + "ndb_change"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ""
                            + ") values("
                            + "'PDCTXN',"
                            + "'" + max_idndb_pdc_txn_master + "',"
                            + "'" + i + ") NEW CW TXN ENTRY',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "now())";
                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    // getbuyer outsatndingbalance 
                    // available outsatanding seller
                    double buyer_outsatnding = 0.00;
                    double buyer_new_outsatnding = 0.00;
                    m_strQry = "select sl_has_byr_warehs_otstaning from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        log.info("checking for buyer outsatnding");
                        buyer_outsatnding = Double.parseDouble(_rs.getString("sl_has_byr_warehs_otstaning"));
                        log.info("receved outstanding :" + buyer_outsatnding + ". For buyer idndb_seller_has_buyers :" + m_Stridndb_customer_define_buyer_id);
                    }
                    buyer_new_outsatnding = buyer_outsatnding + Double.parseDouble(m_Strcw_cheque_amu);
                    log.info("Buyer new out standing :" + buyer_new_outsatnding);
                    // set buyer new outsatnding
                    m_strQry = "update ndb_seller_has_buyers set sl_has_byr_warehs_otstaning='" + buyer_new_outsatnding + "'"
                            + " where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
                    log.info("Buyer new out standing update MYSQL Quiery :" + m_strQry);
                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    //starting  gefu file entries............................................
                    log.info("Starting Account Entries for new CW PDC Manual");

                    max_idndb_pdc_txn_master = "";
                    String account = "";
                    String currency = "LKR";
                    String date = "";
                    double amount = 0.00;
                    String narration = "";
                    String credit_debit = "";
                    String profit_centre = "";
                    String DAO = "";
                    double c_amount = 0.00;
                    double d_amount = 0.00;
                    String system_date = _system_date;
                    String gefu_cw_fixed_frequency = cw_fixed_frequency;
                    String[] gefu_date = _system_date.split("/");
                    String gefu_day = gefu_date[0];
                    String gefu_month = gefu_date[1];
                    String gefu_year = gefu_date[2];
                    date = gefu_year + gefu_month + gefu_day;

                    m_strQry = "select MAX(idndb_pdc_txn_master) as max_idndb_pdc_txn_master from ndb_pdc_txn_master";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        max_idndb_pdc_txn_master = _rs.getString("max_idndb_pdc_txn_master");
                    }

                    // gefu cutomer debit cw commision entry.....................................
                    if (chq_wh_commision_crg.equals("TRANSACTION BASED")) {

                        if (!(cw_tran_base_falt_fee == 0.00)) {
                            amount = cw_tran_base_falt_fee;

                        } else {
                            amount = Double.parseDouble(m_Strcw_cheque_amu) / 100 * cw_tran_base_from_tran;

                        }
                        log.info("Buyer Cheque warehousing comission chg :" + chq_wh_commision_crg);

                        d_amount = amount;
                        account = rec_finance_curr_ac;
                        narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/CW/COM.SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;

                        log.info("Maximum idndb_pdc_txn_master recevied  :" + max_idndb_pdc_txn_master);

                        log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                        log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

                        m_strQry = "insert into gefu_file_generation ("
                                + "idndb_pdc_txn_master"
                                + ",account"
                                + ",currency"
                                + ",date"
                                + ",amount"
                                + ",narration"
                                + ",credit_debit"
                                + ",profit_centre"
                                + ",DAO"
                                + ",c_amount"
                                + ",d_amount"
                                + ",gefu_creation_status"
                                + ",status"
                                + ",creat_by"
                                + ",creat_date"
                                + ",mod_by"
                                + ",mod_date"
                                + ",system_date"
                                + ",gefu_type"
                                + ",bulk_credit"
                                + ",cw_fixed_frequency"
                                + ") values("
                                + "'" + max_idndb_pdc_txn_master + "',"
                                + "'" + account + "',"
                                + "'" + currency + "',"
                                + "'" + date + "',"
                                + "'" + amount + "',"
                                + "'" + narration + "',"
                                + "'D',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + c_amount + "',"
                                + "'" + d_amount + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + system_date + "',"
                                + "'COMCHGCD',"
                                + "'NO',"
                                + "'" + gefu_cw_fixed_frequency + "')";
                        log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry);

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                        d_amount = 0.00;
                        c_amount = amount;
                        account = NDBCommisionPLAcc;
                        narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/CW/COM.COMPLCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;

                        log.info("ACC. ENTRY : NDB bank Commision PL Credit.Credit Acc: " + account + ". Debit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGBC");

                        // gefu ndb bank credit cw commision entry.....................................
                        m_strQry = "insert into gefu_file_generation ("
                                + "idndb_pdc_txn_master"
                                + ",account"
                                + ",currency"
                                + ",date"
                                + ",amount"
                                + ",narration"
                                + ",credit_debit"
                                + ",profit_centre"
                                + ",DAO"
                                + ",c_amount"
                                + ",d_amount"
                                + ",gefu_creation_status"
                                + ",status"
                                + ",creat_by"
                                + ",creat_date"
                                + ",mod_by"
                                + ",mod_date"
                                + ",system_date"
                                + ",gefu_type"
                                + ",bulk_credit"
                                + ",cw_fixed_frequency"
                                + ") values("
                                + "'" + max_idndb_pdc_txn_master + "',"
                                + "'" + account + "',"
                                + "'" + currency + "',"
                                + "'" + date + "',"
                                + "'" + amount + "',"
                                + "'" + narration + "',"
                                + "'C',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + c_amount + "',"
                                + "'" + d_amount + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + system_date + "',"
                                + "'COMCHGBC',"
                                + "'NO',"
                                + "'" + gefu_cw_fixed_frequency + "')";
                        log.info("ACC. ENTRY : NDB Bank Commision PL Credit Entry. MYSQL QUIERY" + m_strQry);

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        log.info("PDC Entry successful and account entry successfuly ");

                    }

                    cw_pdc_save_status = "1100";
                    // end of cw gefu file entries.......................................................
                }

            } else {

                String m_condition = "";
                if (!m_StrOldidndb_customer_define_seller_id.equals(m_Stridndb_customer_define_seller_id)) {
                    m_condition = "idndb_customer_define_seller_id='" + m_Stridndb_customer_define_seller_id + "',";
                    m_condition = "cust_name='" + cust_name + "',";
                    m_condition = "cust_account_number='" + rec_finance_acc_num + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldidndb_customer_define_seller_id + " SELLER ID CHANGE TO " + m_Stridndb_customer_define_seller_id + "<br>";
                }

                if (!m_StrOldidndb_customer_define_buyer_id.equals(m_Stridndb_customer_define_buyer_id)) {
                    m_condition = m_condition + "idndb_customer_define_buyer_id='" + m_Stridndb_customer_define_buyer_id + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldidndb_customer_define_buyer_id + " BUYER ID CHANGE TO " + m_Stridndb_customer_define_buyer_id + "<br>";

                }
                if (!m_StrOldcust_bank.equals(m_Strcust_bank)) {
                    m_condition = m_condition + "idndb_bank_master_file='" + m_Strcust_bank + "',";

                }
                if (!m_StrOldcust_bank_code.equals(m_Strcust_bank_code)) {
                    m_condition = m_condition + "pdc_bank_code='" + m_Strcust_bank_code + "',";
                    m_condition = m_condition + "pdc_bank_name='" + m_Strpdc_bank_name + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcust_bank_code + " BANK CODE CHANGE TO " + m_Strcust_bank_code + "<br>";

                }
                if (!m_StrOldcust_branch.equals(m_Strcust_branch)) {
                    m_condition = m_condition + "idndb_branch_master_file='" + m_Strcust_branch + "',";

                }
                if (!m_StrOldcust_branch_code.equals(m_Strcust_branch_code)) {
                    m_condition = m_condition + "pdc_branch_code='" + m_Strcust_branch_code + "',";
                    m_condition = m_condition + "pdc_branch_name='" + m_Strpdc_branch_name + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcust_branch_code + " BRANCH CODE CHANGE TO " + m_Strcust_branch_code + "<br>";

                }
                if (!m_StrOldcw_chq_number.equals(m_Strcw_chq_number)) {
                    m_condition = m_condition + "pdc_chq_number='" + m_Strcw_chq_number + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcw_chq_number + " CHQ NUM. CHANGE TO " + m_Strcw_chq_number + "<br>";

                }
                if (!m_StrOldcw_cheque_amu.equals(m_Strcw_cheque_amu)) {
                    m_condition = m_condition + "pdc_chq_amu='" + m_Strcw_cheque_amu + "',";
                    m_condition = m_condition + "pdc_chq_cr_amu='" + m_Strcw_cheque_amu + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcw_cheque_amu + " CHQ AMU. CHANGE TO " + m_Strcw_cheque_amu + "<br>";

                    // gefu data entry
                }
                if (!m_StrOldcw_value_date.equals(m_Strcw_value_date_temp)) {
                    m_condition = m_condition + "pdc_value_date='" + m_Strcw_value_date_temp + "',";
                    m_condition = m_condition + "pdc_value_date_ext='ACTIVE',";
                    m_condition = m_condition + "pdc_old_value_date='" + m_StrOldcw_value_date + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcw_value_date + " VALUE DATE CHANGE TO " + m_Strcw_value_date_temp + "<br>";

                }
                if (!m_StrOldcw_cheque_liq_date.equals(m_Strcw_cheque_liq_date_temp)) {
                    m_condition = m_condition + "pdc_lquid_date='" + m_Strcw_cheque_liq_date_temp + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcw_cheque_liq_date + " LQD DATE CHANGE TO " + m_Strcw_cheque_liq_date_temp + "<br>";

                }

                if (m_Strcw_cheque_liquidation.equals("ACTIVE")) {
                    m_condition = m_condition + "pdc_chq_status='ERLYLIQUDED',";
                    i++;
                    m_change = m_change + i + ") CHQ ERLY LQD <br>";

                }

                log.info("Updating CW PDC Entry m_str_idndb_pdc_txn_master:" + m_str_idndb_pdc_txn_master);
                log.info("Updating CW PDC Entry codition:" + m_condition);

                m_strQry = "update ndb_pdc_txn_master set " + m_condition + "pdc_chq_status_auth='UN-AUTHORIZED',pdc_booking_date=NOW(), "
                        + "pdc_chq_mod_by='" + m_str_user_id + "',"
                        + "pdc_chq_mod_date=now()"
                        + " where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";
                log.info("Updating CW PDC Entry MYSQL Quiery:" + m_strQry);
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
                        + "'PDCTXN',"
                        + "'" + m_str_idndb_pdc_txn_master + "',"
                        + "'" + m_change + "',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                // getbuyer outsatndingbalance 
                // available outsatanding seller
                if (!m_StrOldcw_cheque_amu.equals(m_Strcw_cheque_amu)) {

                    double buyer_outsatnding = 0.00;
                    double buyer_new_outsatnding = 0.00;
                    m_strQry = "select sl_has_byr_warehs_otstaning from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        buyer_outsatnding = Double.parseDouble(_rs.getString("sl_has_byr_warehs_otstaning"));
                    }
                    buyer_new_outsatnding = buyer_outsatnding + Double.parseDouble(m_Strcw_cheque_amu) - Double.parseDouble(m_StrOldcw_cheque_amu);

                    // set buyer new outsatnding
                    m_strQry = "update ndb_seller_has_buyers set sl_has_byr_warehs_otstaning='" + buyer_new_outsatnding + "'"
                            + " where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    log.info("GEFU File update process/transdaction enties.");

                    // gefu creation
                    double amount = 0.00;
                    boolean gefu_genearation_check = true;
                    if (chq_wh_commision_crg.equals("TRANSACTION BASED")) {

                        if (!(cw_tran_base_falt_fee == 0.00)) {
                            amount = cw_tran_base_falt_fee;

                        } else {
                            amount = Double.parseDouble(m_Strcw_cheque_amu) / 100 * cw_tran_base_from_tran;

                        }

                        log.info("check for gefu entry seller commision chrg current account debit / not yet procced gefu.");

                        m_strQry = "select * from gefu_file_generation where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and gefu_creation_status='ACTIVE' and credit_debit='D' and gefu_type='COMCHGCD'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (_rs.next()) {

                            m_strQry = "update gefu_file_generation set amount='" + amount + "'"
                                    + ",d_amount='" + amount + "'"
                                    + ",status='UN-AUTHORIZED'"
                                    + ",mod_by='" + m_str_user_id + "'"
                                    + ",mod_date=NOW()"
                                    + " where idgefu_file_generation='" + _rs.getString("idgefu_file_generation") + "'";
                            log.info("check for gefu entry seller commision chrg current account debit / not yet procced gefu found and update it to new commision");
                            log.info("check for gefu entry seller commision chrg current account debit / not yet procced gefu found and update it to new commision MY SQL :" + m_strQry);

                            if (_stmnt2.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }
                            gefu_genearation_check = false;

                        }
                        log.info("check for gefu entry seller commision charg NDB commision PL credit / not yet procced gefu.");

                        m_strQry = "select * from gefu_file_generation where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and gefu_creation_status='ACTIVE' and credit_debit='C' and gefu_type='COMCHGBC'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (_rs.next()) {

                            m_strQry = "update gefu_file_generation set amount='" + amount + "'"
                                    + ",c_amount='" + amount + "'"
                                    + ",status='UN-AUTHORIZED'"
                                    + ",mod_by='" + m_str_user_id + "'"
                                    + ",mod_date=NOW()"
                                    + " where idgefu_file_generation='" + _rs.getString("idgefu_file_generation") + "'";
                            log.info("check for gefu entry seller commision charg NDB commision PL credit / not yet procced gefu found and update it to new commisions");
                            log.info("check for gefu entry seller commision charg NDB commision PL credit / not yet procced gefu found and update it to new commisions MY SQL Quiery :" + m_strQry);

                            if (_stmnt2.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }
                            gefu_genearation_check = false;

                        }

                    }

                    /// new entry
                    if (gefu_genearation_check) {

                        log.info("GEFU files were genereted for first entry");
                        log.info("GEFU files were genereted for first entry there for need reversel entry");
                        log.info("New Account entry for new amount");

                        String account = "";
                        String currency = "LKR";
                        String date = "";
                        String narration = "";
                        String credit_debit = "";
                        String profit_centre = "";
                        String DAO = "";
                        double c_amount = 0.00;
                        double d_amount = 0.00;
                        String system_date = _system_date;
                        String gefu_cw_fixed_frequency = cw_fixed_frequency;

                        // cutomer debit entry
                        if (chq_wh_commision_crg.equals("TRANSACTION BASED")) {

                            if (!(cw_tran_base_falt_fee == 0.00)) {
                                amount = cw_tran_base_falt_fee;

                            } else {
                                amount = Double.parseDouble(m_Strcw_cheque_amu) / 100 * cw_tran_base_from_tran;

                            }
                            d_amount = amount;
                            account = rec_finance_curr_ac;
                            narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/CW/UPDT/COM.SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;

                            String[] gefu_date = _system_date.split("/");
                            String gefu_day = gefu_date[0];
                            String gefu_month = gefu_date[1];
                            String gefu_year = gefu_date[2];
                            date = gefu_year + gefu_month + gefu_day;

                            log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                            log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

                            m_strQry = "insert into gefu_file_generation ("
                                    + "idndb_pdc_txn_master"
                                    + ",account"
                                    + ",currency"
                                    + ",date"
                                    + ",amount"
                                    + ",narration"
                                    + ",credit_debit"
                                    + ",profit_centre"
                                    + ",DAO"
                                    + ",c_amount"
                                    + ",d_amount"
                                    + ",gefu_creation_status"
                                    + ",status"
                                    + ",creat_by"
                                    + ",creat_date"
                                    + ",mod_by"
                                    + ",mod_date"
                                    + ",system_date"
                                    + ",gefu_type"
                                    + ",bulk_credit"
                                    + ",cw_fixed_frequency"
                                    + ") values("
                                    + "'" + m_str_idndb_pdc_txn_master + "',"
                                    + "'" + account + "',"
                                    + "'" + currency + "',"
                                    + "'" + date + "',"
                                    + "'" + amount + "',"
                                    + "'" + narration + "',"
                                    + "'D',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + c_amount + "',"
                                    + "'" + d_amount + "',"
                                    + "'ACTIVE',"
                                    + "'UN-AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'COMCHGCD',"
                                    + "'NO',"
                                    + "'" + gefu_cw_fixed_frequency + "')";
                            log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry);

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                            // ndb credit entry
                            c_amount = amount;
                            d_amount = 0.00;
                            account = NDBCommisionPLAcc;
                            narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/CW/UPDT/COM.COMPLCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                            log.info("ACC. ENTRY : NDB commison PL Credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGBC");

                            m_strQry = "insert into gefu_file_generation ("
                                    + "idndb_pdc_txn_master"
                                    + ",account"
                                    + ",currency"
                                    + ",date"
                                    + ",amount"
                                    + ",narration"
                                    + ",credit_debit"
                                    + ",profit_centre"
                                    + ",DAO"
                                    + ",c_amount"
                                    + ",d_amount"
                                    + ",gefu_creation_status"
                                    + ",status"
                                    + ",creat_by"
                                    + ",creat_date"
                                    + ",mod_by"
                                    + ",mod_date"
                                    + ",system_date"
                                    + ",gefu_type"
                                    + ",bulk_credit"
                                    + ",cw_fixed_frequency"
                                    + ") values("
                                    + "'" + m_str_idndb_pdc_txn_master + "',"
                                    + "'" + NDBCommisionPLAcc + "',"
                                    + "'" + currency + "',"
                                    + "'" + date + "',"
                                    + "'" + amount + "',"
                                    + "'" + narration + "',"
                                    + "'C',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + c_amount + "',"
                                    + "'" + d_amount + "',"
                                    + "'ACTIVE',"
                                    + "'UN-AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'COMCHGBC',"
                                    + "'NO',"
                                    + "'" + gefu_cw_fixed_frequency + "')";

                            log.info("ACC. ENTRY : NDB commision pl credit. MYSQL QUIERY" + m_strQry);

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }
                        }

                        // update reversel entry 
                        if (chq_wh_commision_crg.equals("TRANSACTION BASED")) {

                            if (!(cw_tran_base_falt_fee == 0.00)) {
                                amount = cw_tran_base_falt_fee;

                            } else {
                                amount = Double.parseDouble(m_StrOldcw_cheque_amu) / 100 * cw_tran_base_from_tran;

                            }

                            c_amount = amount;
                            d_amount = 0.00;
                            account = rec_finance_curr_ac;
                            narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/CW/RVSL/UPDT/COM.SELLERCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                            log.info("ACC. REVERSEL ENTRY : Seller current account number credit & NDB bank commision PL debit ");
                            log.info("ACC. REVERSEL ENTRY : Seller current account number credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGCDR");

                            // cutomer credit
                            m_strQry = "insert into gefu_file_generation ("
                                    + "idndb_pdc_txn_master"
                                    + ",account"
                                    + ",currency"
                                    + ",date"
                                    + ",amount"
                                    + ",narration"
                                    + ",credit_debit"
                                    + ",profit_centre"
                                    + ",DAO"
                                    + ",c_amount"
                                    + ",d_amount"
                                    + ",gefu_creation_status"
                                    + ",status"
                                    + ",creat_by"
                                    + ",creat_date"
                                    + ",mod_by"
                                    + ",mod_date"
                                    + ",system_date"
                                    + ",gefu_type"
                                    + ",bulk_credit"
                                    + ",cw_fixed_frequency"
                                    + ") values("
                                    + "'" + m_str_idndb_pdc_txn_master + "',"
                                    + "'" + account + "',"
                                    + "'" + currency + "',"
                                    + "'" + date + "',"
                                    + "'" + amount + "',"
                                    + "'" + narration + "/REVSL" + "',"
                                    + "'C',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + c_amount + "',"
                                    + "'" + d_amount + "',"
                                    + "'ACTIVE',"
                                    + "'UN-AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'COMCHGCDR',"
                                    + "'NO',"
                                    + "'" + gefu_cw_fixed_frequency + "')";
                            log.info("ACC. REVERSEL ENTRY : Seller current account number credit/ MYSQL QUIER" + m_strQry);

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                            // ndb debit entry
                            d_amount = amount;
                            c_amount = 0.00;
                            account = NDBCommisionPLAcc;
                            narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/CW/RVSL/UPDT/COM.COMPLDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                            log.info("ACC. REVERSEL ENTRY : NDB ank commision pl debit/ debit Acc: " + account + ". debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGBCR");

                            m_strQry = "insert into gefu_file_generation ("
                                    + "idndb_pdc_txn_master"
                                    + ",account"
                                    + ",currency"
                                    + ",date"
                                    + ",amount"
                                    + ",narration"
                                    + ",credit_debit"
                                    + ",profit_centre"
                                    + ",DAO"
                                    + ",c_amount"
                                    + ",d_amount"
                                    + ",gefu_creation_status"
                                    + ",status"
                                    + ",creat_by"
                                    + ",creat_date"
                                    + ",mod_by"
                                    + ",mod_date"
                                    + ",system_date"
                                    + ",gefu_type"
                                    + ",bulk_credit"
                                    + ",cw_fixed_frequency"
                                    + ") values("
                                    + "'" + m_str_idndb_pdc_txn_master + "',"
                                    + "'" + account + "',"
                                    + "'" + currency + "',"
                                    + "'" + date + "',"
                                    + "'" + amount + "',"
                                    + "'" + narration + "/REVSL" + "',"
                                    + "'D',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + c_amount + "',"
                                    + "'" + d_amount + "',"
                                    + "'ACTIVE',"
                                    + "'UN-AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'COMCHGBCR',"
                                    + "'NO',"
                                    + "'" + gefu_cw_fixed_frequency + "')";

                            log.info("ACC. REVERSEL ENTRY : NDB ank commision pl debit/MYSQL QUIERY : " + m_strQry);

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                        }

                    }

                }
                if (!m_StrOldcw_value_date.equals(m_Strcw_value_date_temp)) {
                    log.info("ACC. ENTRY : Value date extraction ");

                    String account = "";
                    String currency = "LKR";
                    String date = _system_date;
                    double amount = chq_wh_vale_dte_extr_chg;
                    String narration = "";
                    String credit_debit = "";
                    String profit_centre = "";
                    String DAO = "";
                    double c_amount = 0.00;
                    double d_amount = 0.00;
                    String system_date = _system_date;
                    String gefu_cw_fixed_frequency = cw_fixed_frequency;

                    d_amount = amount;
                    account = rec_finance_curr_ac;
                    narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/CW/VDATEEXTRACT/SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                    log.info("ACC. ENTRY : seller current account debit/ value date extraction charges & ndb commision pl credit value date extraaction charges");
                    log.info("ACC. ENTRY : Seller current account nummber debit value date extraction charges debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :VDEXTRCD");
                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + m_str_idndb_pdc_txn_master + "',"
                            + "'" + account + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + amount + "',"
                            + "'" + narration + "',"
                            + "'D',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + c_amount + "',"
                            + "'" + d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'VDEXTRCD',"
                            + "'NO',"
                            + "'" + gefu_cw_fixed_frequency + "')";

                    log.info("ACC. ENTRY : Seller current account nummber debit value date extraction charges / MYSQL QUIERY :" + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    d_amount = 0.00;
                    c_amount = amount;
                    account = NDBCommisionPLAcc;
                    narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/CW/VDATEEXTRACT/COMPLCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                    log.info("ACC. ENTRY : NDB commision PL credit value date extraction charges credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :VDEXTRBC");
                    // ndb credit entry
                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + m_str_idndb_pdc_txn_master + "',"
                            + "'" + account + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + amount + "',"
                            + "'" + narration + "',"
                            + "'C',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + c_amount + "',"
                            + "'" + d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'VDEXTRBC',"
                            + "'NO',"
                            + "'" + gefu_cw_fixed_frequency + "')";
                    log.info("ACC. ENTRY : NDB commision PL credit value date extraction charges credit Acc: MYSQL QUIERY :" + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }
                }

                if (m_Strcw_cheque_liquidation.equals("ACTIVE")) {

                    log.info("ACC. ENTRY : Erly Lequidation");
                    String account = "";
                    String currency = "LKR";
                    String date = _system_date;
                    double amount = chq_wh_erly_wdr_chg;
                    String narration = "";
                    String credit_debit = "";
                    String profit_centre = "";
                    String DAO = "";
                    double c_amount = 0.00;
                    double d_amount = 0.00;
                    String system_date = _system_date;
                    String gefu_cw_fixed_frequency = cw_fixed_frequency;
                    d_amount = amount;
                    account = rec_finance_curr_ac;
                    narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/CW/ERLYLEQD/SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                    log.info("ACC. ENTRY : seller current account debit/ early lequdation charges & ndb commision pl credit early lequdation charges");
                    log.info("ACC. ENTRY : Seller current account nummber debit early lequdation charges debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :ERLEQDCD");

                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + m_str_idndb_pdc_txn_master + "',"
                            + "'" + account + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + amount + "',"
                            + "'" + narration + "',"
                            + "'D',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + c_amount + "',"
                            + "'" + d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'ERLEQDCD',"
                            + "'NO',"
                            + "'" + gefu_cw_fixed_frequency + "')";
                    log.info("ACC. ENTRY : Seller current account nummber debit early lequdation charges / MYSQL QUIERY :" + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    d_amount = 0.00;
                    c_amount = amount;
                    account = NDBCommisionPLAcc;
                    narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/CW/ERLYLEQD/COMPL/BUYER/" + m_Stridndb_customer_define_buyer_id;
                    log.info("ACC. ENTRY : NDB commision PL credit early lequdation charges credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :ERLEQDBC");

                    // ndb credit entry
                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + m_str_idndb_pdc_txn_master + "',"
                            + "'" + NDBCommisionPLAcc + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + amount + "',"
                            + "'" + narration + "',"
                            + "'C',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + c_amount + "',"
                            + "'" + d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'ERLEQDBC',"
                            + "'NO',"
                            + "'" + gefu_cw_fixed_frequency + "')";

                    log.info("ACC. ENTRY : NDB commision PL credit early lequdation charges: MYSQL QUIERY :" + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }
                }

                cw_pdc_save_status = "1100";

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
        return cw_pdc_save_status;
    }

    public String saveRFPDCEntry(JSONObject prm_obj) {
        String rf_pdc_save_status = "0000";

        String m_str_user_id = "";
        String m_str_idndb_pdc_txn_master = "";

        String m_Stridndb_customer_define_seller_id = "";
        String m_StrOldidndb_customer_define_seller_id = "";

        String m_Stridndb_customer_define_buyer_id = "";
        String m_StrOldidndb_customer_define_buyer_id = "";

        String m_Strcust_bank = "";
        String m_StrOldcust_bank = "";

        String m_Strcust_bank_code = "";
        String m_StrOldcust_bank_code = "";

        String m_Strcust_branch = "";
        String m_StrOldcust_branch = "";

        String m_Strpdc_bank_name = "";
        String m_StrOldpdc_bank_name = "";

        String m_Strpdc_branch_name = "";
        String m_StrOldpdc_branch_name = "";

        String m_Strcust_branch_code = "";
        String m_StrOldcust_branch_code = "";

        String m_Strrf_value_date_temp = "";
        String m_Strrf_value_date = "";

        String m_StrOldrf_value_date = "";

        String m_Strrf_cheque_liq_date_temp = "";
        String m_Strrf_cheque_liq_date = "";

        String m_StrOldrf_cheque_liq_date = "";

        String m_str_rf_lequid_date = "";
        String m_strOld_rf_lequid_date = "";

        String m_Strrf_cheque_liquidation = "";

        String m_Strrf_chq_number = "";
        String m_StrOldrf_chq_number = "";

        String m_Strrf_cheque_amu = "";
        String m_StrOldrf_cheque_amu = "";
        double m_StrOldpdc_chq_cr_amu = 0.00;

        String m_strQry = "";
        String _system_date = "";
        String NDBCommisionPLAcc = "";

        boolean m_ardy_exist_data = true;
        boolean m_ardy_exist_dcheque = true;

        try {
            log.info("RF PDC manual entry receved");
            pdc_DAO dao = new pdc_DAO();
            Parameters para = new Parameters();
            NDBCommisionPLAcc = para.getNDBCommisionPLAcc();

            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_pdc_txn_master = prm_obj.getString("idndb_pdc_txn_master");

            m_Stridndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            m_Stridndb_customer_define_buyer_id = prm_obj.getString("idndb_customer_define_buyer_id");
            m_Strcust_bank = prm_obj.getString("cust_bank");
            m_Strcust_branch = prm_obj.getString("cust_branch");
            m_Strrf_value_date_temp = dao.getchequeValueDate(prm_obj.getString("rf_value_date"));
            m_Strrf_chq_number = prm_obj.getString("rf_chq_number");
            m_Strrf_cheque_amu = prm_obj.getString("rf_cheque_amu");
            m_Strrf_cheque_liquidation = prm_obj.getString("rf_cheque_liquidation");

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            // java.util.Date dt = new java.util.Date();
            // java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // m_Strcw_value_date = sdf.format(m_Strcw_value_date_temp);
            String[] date_spliter = m_Strrf_value_date_temp.split("/");
            int m_year = Integer.parseInt(date_spliter[2]);
            int m_month = Integer.parseInt(date_spliter[1]) - 1;
            int m_day = Integer.parseInt(date_spliter[0]) + 1;
            Calendar ced = Calendar.getInstance();

            ced.set(Calendar.YEAR, m_year); // set the year
            ced.set(Calendar.MONTH, m_month); // set the month
            ced.set(Calendar.DAY_OF_MONTH, m_day);

            m_Strrf_cheque_liq_date_temp = dao.getchequeValueDate(formatter.format(ced.getTime()));
            // m_Strcw_cheque_liq_date = sdf.format(m_Strcw_cheque_liq_date_temp);

            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            m_strQry = "select * from ndb_system_date";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                _system_date = _rs.getString("_system_date");

            }

            m_strQry = "select * from ndb_bank_master_file where idndb_bank_master_file='" + m_Strcust_bank + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                m_Strcust_bank_code = _rs.getString("bank_code");
                m_Strpdc_bank_name = _rs.getString("bank_name");

            }
            m_strQry = "select * from ndb_branch_master_file where idndb_branch_master_file='" + m_Strcust_branch + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                m_Strcust_branch_code = _rs.getString("branch_id");
                m_Strpdc_branch_name = _rs.getString("branch_name");

            }

            String m_uniq_id = m_Strrf_chq_number + m_Strcust_bank_code + m_Strcust_branch_code;
            m_strQry = "SELECT CONCAT(pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code) as txn_master_unq_id FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE'";
            _rs = _stmnt.executeQuery(m_strQry);
            while (_rs.next()) {
                if (m_uniq_id.equals(_rs.getString("txn_master_unq_id"))) {
                    m_ardy_exist_dcheque = false;
                    //cheque number all ready exist
                    rf_pdc_save_status = "1101";
                }

            }

            double m_discounting_advance_rate = 0.00;
            double m_discounting_amu_from_cheque_amu = 0.00;
            double m_Olddiscounting_amu_from_cheque_amu = 0.00;
            double m_alternat_amu = 0.00;

            m_strQry = "select shb_chq_dis_adv_rate_prectange from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                m_discounting_advance_rate = Double.parseDouble(_rs.getString("shb_chq_dis_adv_rate_prectange"));

                m_discounting_amu_from_cheque_amu = Double.parseDouble(m_Strrf_cheque_amu) / 100 * m_discounting_advance_rate;
                m_alternat_amu = Double.parseDouble(m_Strrf_cheque_amu) - m_discounting_amu_from_cheque_amu;

            }

            // start of extract receivable finance commision data.....................................
            String rec_finance_commision_crg = "NOTDEFINE";
            String idndb_customer_define_seller = "";
            String idndb_customer_define_seller_idndb_customer_define = "";
            String rec_finance_curr_ac = "";
            String rec_finance_acc_num = "";
            String rec_finance_cr_dsc_proc_acc_num = "";
            String rec_finance_margin_ac = "";
            String rec_finance_margin = "";
            String cust_name = "";
            String rec_finance_bulk_credit = "";
            double rec_finance_erly_wdr_chg = 0.00;
            double rec_finance_vale_dte_extr_chg = 0.00;
            double rec_finance_erly_stlemnt_chg = 0.00;
            double rf_tran_base_falt_fee = 0.00;
            double rf_tran_base_from_tran = 0.00;
            double rf_fixed_rate_amount = 0.00;
            String rf_fixed_frequency = "DAILY";
            m_strQry = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                rec_finance_commision_crg = _rs.getString("rec_finance_commision_crg");
                idndb_customer_define_seller = _rs.getString("idndb_customer_define_seller");

                rf_tran_base_falt_fee = _rs.getDouble("rf_tran_base_falt_fee");
                rf_tran_base_from_tran = _rs.getDouble("rf_tran_base_from_tran");

                rf_fixed_rate_amount = _rs.getDouble("rf_fixed_rate_amount");
                rf_fixed_frequency = _rs.getString("rf_fixed_frequency");

            }

            m_strQry = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                idndb_customer_define_seller_idndb_customer_define = _rs.getString("idndb_customer_define");
            }

            m_strQry = "select * from ndb_rec_fin where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                rec_finance_bulk_credit = _rs.getString("rec_finance_bulk_credit");
                rec_finance_erly_wdr_chg = _rs.getDouble("rec_finance_erly_wdr_chg");
                rec_finance_vale_dte_extr_chg = _rs.getDouble("rec_finance_vale_dte_extr_chg");
                rec_finance_erly_stlemnt_chg = _rs.getDouble("rec_finance_erly_stlemnt_chg");
            }

            m_strQry = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                rec_finance_curr_ac = _rs.getString("rec_finance_curr_ac");
                rec_finance_acc_num = _rs.getString("rec_finance_acc_num");
                rec_finance_cr_dsc_proc_acc_num = _rs.getString("rec_finance_cr_dsc_proc_acc_num");
                rec_finance_margin_ac = _rs.getString("rec_finance_margin_ac");
                rec_finance_margin = _rs.getString("rec_finance_margin");
                cust_name = _rs.getString("cust_name");
            }

            // End of extract receivable finance commision data....................................................
            log.info("Check for RF PDC manual entry receved already exist to update");
            m_strQry = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                log.info("RF PDC manual entry receved already exist idndb_pdc_txn_master :" + m_str_idndb_pdc_txn_master);

                m_StrOldidndb_customer_define_seller_id = _rs.getString("idndb_customer_define_seller_id");
                m_StrOldidndb_customer_define_buyer_id = _rs.getString("idndb_customer_define_buyer_id");
                m_StrOldcust_bank = _rs.getString("idndb_bank_master_file");
                m_StrOldcust_bank_code = _rs.getString("pdc_bank_code");
                m_StrOldcust_branch = _rs.getString("idndb_branch_master_file");
                m_StrOldcust_branch_code = _rs.getString("pdc_branch_code");
                m_StrOldrf_value_date = _rs.getString("pdc_value_date");
                m_StrOldrf_cheque_liq_date = _rs.getString("pdc_lquid_date");
                m_StrOldrf_chq_number = _rs.getString("pdc_chq_number");
                m_StrOldrf_cheque_amu = _rs.getString("pdc_chq_amu");
                m_StrOldpdc_chq_cr_amu = _rs.getDouble("pdc_chq_cr_amu");
                m_Olddiscounting_amu_from_cheque_amu = _rs.getDouble("pdc_chq_discounting_amu");

                m_ardy_exist_data = false;
            }
            int i = 0;
            String m_change = "";
            if (m_ardy_exist_data) {
                if (m_ardy_exist_dcheque) {

                    log.info("New RF PDC manual entry insert");

                    m_strQry = "insert into ndb_pdc_txn_master ("
                            + "pdc_req_financing"
                            + ",idndb_customer_define_seller_id"
                            + ",idndb_customer_define_buyer_id"
                            + ",idndb_bank_master_file"
                            + ",pdc_bank_code"
                            + ",idndb_branch_master_file"
                            + ",pdc_branch_code"
                            + ",pdc_chq_number"
                            + ",pdc_booking_date"
                            + ",pdc_value_date"
                            + ",pdc_lquid_date"
                            + ",pdc_chq_amu"
                            + ",pdc_chq_discounting_amu"
                            + ",pdc_chq_net_amu"
                            + ",pdc_chq_cr_amu"
                            + ",pdc_chq_status"
                            + ",pdc_chq_status_auth"
                            + ",pdc_chq_batch_no"
                            + ",cust_account_number"
                            + ",cust_name"
                            + ",pdc_bank_name"
                            + ",pdc_branch_name"
                            + ",pdc_value_date_ext"
                            + ",pdc_old_value_date"
                            + ",pdc_chq_creat_by"
                            + ",pdc_chq_creat_date"
                            + ",pdc_chq_mod_by"
                            + ",pdc_chq_mod_date"
                            + ""
                            + ") values("
                            + "'RF',"
                            + "'" + m_Stridndb_customer_define_seller_id + "',"
                            + "'" + m_Stridndb_customer_define_buyer_id + "',"
                            + "'" + m_Strcust_bank + "',"
                            + "'" + m_Strcust_bank_code + "',"
                            + "'" + m_Strcust_branch + "',"
                            + "'" + m_Strcust_branch_code + "',"
                            + "'" + m_Strrf_chq_number + "',"
                            + "'" + _system_date + "',"
                            + "'" + m_Strrf_value_date_temp + "',"
                            + "'" + m_Strrf_cheque_liq_date_temp + "',"
                            + "'" + m_Strrf_cheque_amu + "',"
                            + "'" + m_discounting_amu_from_cheque_amu + "',"
                            + "'" + m_alternat_amu + "',"
                            + "'" + m_alternat_amu + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'*',"
                            + "'" + rec_finance_acc_num + "',"
                            + "'" + cust_name + "',"
                            + "'" + m_Strpdc_bank_name + "',"
                            + "'" + m_Strpdc_branch_name + "',"
                            + "'DEACTIVE',"
                            + "'" + _system_date + "',"
                            + "'" + m_str_user_id + "',"
                            + "now(),"
                            + "'" + m_str_user_id + "',"
                            + "now())";

                    log.info("New RF PDC manual entry insert / MY SQL Quiery :" + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    String max_idndb_pdc_txn_master = "";

                    m_strQry = "select MAX(idndb_pdc_txn_master) as max_idndb_pdc_txn_master from ndb_pdc_txn_master";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        max_idndb_pdc_txn_master = _rs.getString("max_idndb_pdc_txn_master");
                    }

                    m_strQry = "insert into ndb_change_log (ndb_change_log_type,"
                            + "ndb_attached_id,"
                            + "ndb_change"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ""
                            + ") values("
                            + "'PDCTXN',"
                            + "'" + max_idndb_pdc_txn_master + "',"
                            + "'" + i + ") NEW RF TXN ENTRY',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "now())";
                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    double buyer_outsatnding = 0.00;
                    double buyer_new_outsatnding = 0.00;
                    log.info("New RF PDC manual entry/ Buyer outsatnd update");
                    m_strQry = "select shb_facty_det_os from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        buyer_outsatnding = Double.parseDouble(_rs.getString("shb_facty_det_os"));
                        log.info("New RF PDC manual entry/ Buyer current outsatnding :" + buyer_outsatnding);
                    }
                    buyer_new_outsatnding = buyer_outsatnding + Double.parseDouble(m_Strrf_cheque_amu);
                    log.info("New RF PDC manual entry/ Buyer new outsatnding :" + buyer_new_outsatnding);

                    // set buyer new outsatnding
                    m_strQry = "update ndb_seller_has_buyers set shb_facty_det_os='" + buyer_new_outsatnding + "'"
                            + " where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
                    log.info("New RF PDC manual entry/ Buyer new outsatnding update MY SQL Quiery:" + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    //...................................................gefu entries...................................
                    log.info("New RF PDC manual entry/ GEFU ACC ENTRIES start");

                    max_idndb_pdc_txn_master = "";
                    String account = "";
                    String currency = "LKR";
                    String date = "";
                    double amount = 0.00;
                    String narration = "";
                    String credit_debit = "";
                    String profit_centre = "";
                    String DAO = "";
                    double c_amount = 0.00;
                    double d_amount = 0.00;
                    String system_date = _system_date;
                    String gefu_rf_fixed_frequency = rf_fixed_frequency;
                    String gefu_rec_finance_bulk_credit = rec_finance_bulk_credit;

                    // cutomer debit entry
                    log.info("New RF PDC manual entry / MAX idndb_pdc_txn_master check");

                    m_strQry = "select MAX(idndb_pdc_txn_master) as max_idndb_pdc_txn_master from ndb_pdc_txn_master";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        max_idndb_pdc_txn_master = _rs.getString("max_idndb_pdc_txn_master");
                    }
                    if (rec_finance_commision_crg.equals("TRANSACTION BASED")) {

                        if (!(rf_tran_base_falt_fee == 0.00)) {
                            amount = rf_tran_base_falt_fee;

                        } else {
                            amount = Double.parseDouble(m_Strrf_cheque_amu) / 100 * rf_tran_base_from_tran;

                        }
                        log.info("New RF PDC manual entry/ Commision chg type :" + rec_finance_commision_crg);

                        d_amount = amount;
                        account = rec_finance_curr_ac;
                        narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/COM.SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;

                        String[] gefu_date = _system_date.split("/");
                        String gefu_day = gefu_date[0];
                        String gefu_month = gefu_date[1];
                        String gefu_year = gefu_date[2];
                        date = gefu_year + gefu_month + gefu_day;

                        log.info("New RF PDC manual entry / MAX idndb_pdc_txn_master max_idndb_pdc_txn_master : " + max_idndb_pdc_txn_master);
                        log.info("Start of ACC Commision entries");
                        // commision charges ndb customer debit entry............................................................
                        log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                        log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

                        m_strQry = "insert into gefu_file_generation ("
                                + "idndb_pdc_txn_master"
                                + ",account"
                                + ",currency"
                                + ",date"
                                + ",amount"
                                + ",narration"
                                + ",credit_debit"
                                + ",profit_centre"
                                + ",DAO"
                                + ",c_amount"
                                + ",d_amount"
                                + ",gefu_creation_status"
                                + ",status"
                                + ",creat_by"
                                + ",creat_date"
                                + ",mod_by"
                                + ",mod_date"
                                + ",system_date"
                                + ",gefu_type"
                                + ",bulk_credit"
                                + ",cw_fixed_frequency"
                                + ") values("
                                + "'" + max_idndb_pdc_txn_master + "',"
                                + "'" + account + "',"
                                + "'" + currency + "',"
                                + "'" + date + "',"
                                + "'" + amount + "',"
                                + "'" + narration + "',"
                                + "'D',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + c_amount + "',"
                                + "'" + d_amount + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + system_date + "',"
                                + "'COMCHGCD',"
                                + "'NO',"
                                + "'" + gefu_rf_fixed_frequency + "')";
                        log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry);
                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        // commision charges ndb bank credit entry............................................................

                        d_amount = 0.00;
                        c_amount = amount;
                        account = NDBCommisionPLAcc;
                        narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/COM.COMPLCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                        log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGCD");

                        m_strQry = "insert into gefu_file_generation ("
                                + "idndb_pdc_txn_master"
                                + ",account"
                                + ",currency"
                                + ",date"
                                + ",amount"
                                + ",narration"
                                + ",credit_debit"
                                + ",profit_centre"
                                + ",DAO"
                                + ",c_amount"
                                + ",d_amount"
                                + ",gefu_creation_status"
                                + ",status"
                                + ",creat_by"
                                + ",creat_date"
                                + ",mod_by"
                                + ",mod_date"
                                + ",system_date"
                                + ",gefu_type"
                                + ",bulk_credit"
                                + ",cw_fixed_frequency"
                                + ") values("
                                + "'" + max_idndb_pdc_txn_master + "',"
                                + "'" + account + "',"
                                + "'" + currency + "',"
                                + "'" + date + "',"
                                + "'" + amount + "',"
                                + "'" + narration + "',"
                                + "'C',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + c_amount + "',"
                                + "'" + d_amount + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + system_date + "',"
                                + "'COMCHGBC',"
                                + "'NO',"
                                + "'" + gefu_rf_fixed_frequency + "')";
                        log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ MY SQL Quiery :" + m_strQry);

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        log.info("End of ACC Commision entries");
                        //..................................................end ...........................................

                    }

                    // .................................................... RF facilityGEFU  Entries...............................
                    log.info("Start of RF Facility entries");
                    String _account = "";
                    double _amount = 0.00;
                    String _narration = "";
                    String _credit_debit = "";
                    String _profit_centre = "";
                    String _DAO = "";
                    double _c_amount = 0.00;
                    double _d_amount = 0.00;

                    _amount = m_discounting_amu_from_cheque_amu;
                    _account = rec_finance_acc_num;
                    _credit_debit = "D";
                    _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/ADVANCE/RECFINACC/SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                    d_amount = m_discounting_amu_from_cheque_amu;

                    log.info("ACC. ENTRY : Seller receviable finance account number debit & seller collection account credit");
                    log.info("ACC. ENTRY : Seller receviable finance number debit/ debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :RFCDCD");

                    // RF facility customer RFF ACC debit entry ...............................
                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + max_idndb_pdc_txn_master + "',"
                            + "'" + _account + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + _amount + "',"
                            + "'" + _narration + "',"
                            + "'" + _credit_debit + "',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + _c_amount + "',"
                            + "'" + _d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'RFCDCD',"
                            + "'NO',"
                            + "'" + gefu_rf_fixed_frequency + "')";

                    log.info("ACC. ENTRY : Seller receviable finance number debit/ MY SQL Quiery " + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    _amount = m_discounting_amu_from_cheque_amu;
                    _account = rec_finance_cr_dsc_proc_acc_num;
                    _credit_debit = "C";
                    _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/ADVANCE/CURRACCACC/SELLERCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                    _c_amount = m_discounting_amu_from_cheque_amu;
                    _d_amount = 0.00;
                    log.info("ACC. ENTRY : Seller collection account credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :RFCDBC");

                    // RF facility customer Collection Account Credit entry ...............................
                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + max_idndb_pdc_txn_master + "',"
                            + "'" + _account + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + _amount + "',"
                            + "'" + _narration + "',"
                            + "'" + _credit_debit + "',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + _c_amount + "',"
                            + "'" + _d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'RFCDBC',"
                            + "'" + gefu_rec_finance_bulk_credit + "',"
                            + "'" + gefu_rf_fixed_frequency + "')";

                    log.info("ACC. ENTRY : Seller collection account credit/ MY SQL Quiery " + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }
                    log.info("End of RF Facility entries");
                    //......................................................ens....................................

                    // ..................................................... margin entry .........................
                    if (!(rec_finance_margin_ac.equals("") && rec_finance_margin.equals(""))) {
                        log.info("Start Of marging entry");

                        double margin_amu = Double.parseDouble(m_Strrf_cheque_amu) / 100 * Double.parseDouble(rec_finance_margin);

                        _amount = margin_amu;
                        _account = rec_finance_curr_ac;
                        _credit_debit = "D";
                        _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/MARGIN/RECFINCURRACC/SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                        d_amount = margin_amu;
                        log.info("ACC. ENTRY : Seller current account number debit & seller marging account credit");
                        log.info("ACC. ENTRY : Sellercurrent account number debit / debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :RFMARCDCD");

                        // RF facility customer RFF ACC debit entry ...............................
                        m_strQry = "insert into gefu_file_generation ("
                                + "idndb_pdc_txn_master"
                                + ",account"
                                + ",currency"
                                + ",date"
                                + ",amount"
                                + ",narration"
                                + ",credit_debit"
                                + ",profit_centre"
                                + ",DAO"
                                + ",c_amount"
                                + ",d_amount"
                                + ",gefu_creation_status"
                                + ",status"
                                + ",creat_by"
                                + ",creat_date"
                                + ",mod_by"
                                + ",mod_date"
                                + ",system_date"
                                + ",gefu_type"
                                + ",bulk_credit"
                                + ",cw_fixed_frequency"
                                + ") values("
                                + "'" + max_idndb_pdc_txn_master + "',"
                                + "'" + _account + "',"
                                + "'" + currency + "',"
                                + "'" + date + "',"
                                + "'" + _amount + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + _c_amount + "',"
                                + "'" + _d_amount + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + system_date + "',"
                                + "'RFMARCDCD',"
                                + "'NO',"
                                + "'" + gefu_rf_fixed_frequency + "')";

                        log.info("ACC. ENTRY : Seller current account number debit /YSQL QUIERY :" + m_strQry);

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                        _amount = margin_amu;
                        _account = rec_finance_margin_ac;
                        _credit_debit = "C";
                        _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/MARGIN/MARGINACC/SELLERCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                        _c_amount = margin_amu;
                        _d_amount = 0.00;

                        log.info("ACC. ENTRY : Seller Marging account number credit / credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :RFMARCDCD");

                        // RF facility customer Collection Account Credit entry ...............................
                        m_strQry = "insert into gefu_file_generation ("
                                + "idndb_pdc_txn_master"
                                + ",account"
                                + ",currency"
                                + ",date"
                                + ",amount"
                                + ",narration"
                                + ",credit_debit"
                                + ",profit_centre"
                                + ",DAO"
                                + ",c_amount"
                                + ",d_amount"
                                + ",gefu_creation_status"
                                + ",status"
                                + ",creat_by"
                                + ",creat_date"
                                + ",mod_by"
                                + ",mod_date"
                                + ",system_date"
                                + ",gefu_type"
                                + ",bulk_credit"
                                + ",cw_fixed_frequency"
                                + ") values("
                                + "'" + max_idndb_pdc_txn_master + "',"
                                + "'" + _account + "',"
                                + "'" + currency + "',"
                                + "'" + date + "',"
                                + "'" + _amount + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + _c_amount + "',"
                                + "'" + _d_amount + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + system_date + "',"
                                + "'RFMARCDBC',"
                                + "'" + gefu_rec_finance_bulk_credit + "',"
                                + "'" + gefu_rf_fixed_frequency + "')";

                        log.info("ACC. ENTRY : Seller Marging account number credit / MY SQL QUIERY :" + m_strQry);

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        log.info("End of marging entries");
                    }
                    //......................................................ens....................................

                    rf_pdc_save_status = "1100";
                    log.info("RF PDC Entry insert to DB And ACC ENTRIES Entered");
                    // end
                }

            } else {
                log.info("RF PDC ENtry update start for idndb_pdc_txn_master :" + m_str_idndb_pdc_txn_master);

                String m_condition = "";
                if (!m_StrOldidndb_customer_define_seller_id.equals(m_Stridndb_customer_define_seller_id)) {
                    m_condition = "idndb_customer_define_seller_id='" + m_Stridndb_customer_define_seller_id + "',";
                    m_condition = "cust_name='" + cust_name + "',";
                    m_condition = "cust_account_number='" + rec_finance_acc_num + "',";

                    i++;
                    m_change = m_change + i + ")" + m_StrOldidndb_customer_define_seller_id + "SELLER ID CHANGE TO " + m_Stridndb_customer_define_seller_id + "<br>";

                }

                if (!m_StrOldidndb_customer_define_buyer_id.equals(m_Stridndb_customer_define_buyer_id)) {
                    m_condition = m_condition + "idndb_customer_define_buyer_id='" + m_Stridndb_customer_define_buyer_id + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldidndb_customer_define_buyer_id + "BUYER ID CHANGE TO " + m_Stridndb_customer_define_buyer_id + "<br>";

                }
                if (!m_StrOldcust_bank.equals(m_Strcust_bank)) {
                    m_condition = m_condition + "idndb_bank_master_file='" + m_Strcust_bank + "',";
                }
                if (!m_StrOldcust_bank_code.equals(m_Strcust_bank_code)) {
                    m_condition = m_condition + "pdc_bank_name='" + m_Strpdc_bank_name + "',";
                    m_condition = m_condition + "pdc_bank_code='" + m_Strcust_bank_code + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcust_bank_code + "BANK CODE CHANGE TO " + m_Strcust_bank_code + "<br>";

                }
                if (!m_StrOldcust_branch.equals(m_Strcust_branch)) {
                    m_condition = m_condition + "idndb_branch_master_file='" + m_Strcust_branch + "',";
                }
                if (!m_StrOldcust_branch_code.equals(m_Strcust_branch_code)) {
                    m_condition = m_condition + "pdc_branch_name='" + m_Strpdc_branch_name + "',";
                    m_condition = m_condition + "pdc_branch_code='" + m_Strcust_branch_code + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcust_branch_code + "BRANCH CODE CHANGE TO " + m_Strcust_branch_code + "<br>";

                }
                if (!m_StrOldrf_chq_number.equals(m_Strrf_chq_number)) {
                    m_condition = m_condition + "pdc_chq_number='" + m_Strrf_chq_number + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_chq_number + "CHQ NUM. CHANGE TO " + m_Strrf_chq_number + "<br>";

                }
                if (!m_StrOldrf_cheque_amu.equals(m_Strrf_cheque_amu)) {
                    m_condition = m_condition + "pdc_chq_amu='" + m_Strrf_cheque_amu + "',";
                    m_condition = m_condition + "pdc_chq_discounting_amu='" + m_discounting_amu_from_cheque_amu + "',";
                    m_condition = m_condition + "pdc_chq_net_amu='" + m_alternat_amu + "',";
                    m_condition = m_condition + "pdc_chq_cr_amu='" + m_alternat_amu + "',";

                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_cheque_amu + "CHQ AMU. CHANGE TO " + m_Strrf_cheque_amu + "<br>";

                }
                if (!m_StrOldrf_value_date.equals(m_Strrf_value_date_temp)) {
                    m_condition = m_condition + "pdc_value_date='" + m_Strrf_value_date_temp + "',";
                    m_condition = m_condition + "pdc_value_date_ext='ACTIVE',";
                    m_condition = m_condition + "pdc_old_value_date='" + m_StrOldrf_value_date + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_value_date + "VALUE DATE CHANGE TO " + m_Strrf_value_date_temp + "<br>";

                }
                if (!m_StrOldrf_cheque_liq_date.equals(m_Strrf_cheque_liq_date_temp)) {
                    m_condition = m_condition + "pdc_lquid_date='" + m_Strrf_cheque_liq_date_temp + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_cheque_liq_date + "LIQD DATE CHANGE TO " + m_Strrf_cheque_liq_date_temp + "<br>";

                }

                if (m_Strrf_cheque_liquidation.equals("ACTIVE")) {
                    m_condition = m_condition + "pdc_chq_status='ERLYLIQUDED',";
                    i++;
                    m_change = m_change + i + ") ERLY LIQD CCHQ <br>";

                }

                m_strQry = "update ndb_pdc_txn_master set " + m_condition + " pdc_chq_status_auth='UN-AUTHORIZED',pdc_booking_date=NOW(), "
                        + "pdc_chq_mod_by='" + m_str_user_id + "',"
                        + "pdc_chq_mod_date=now()"
                        + " where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";

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
                        + "'PDCTXN',"
                        + "'" + m_str_idndb_pdc_txn_master + "',"
                        + "'" + m_change + "',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                // getbuyer outsatndingbalance 
                // available outsatanding seller
                if (!m_StrOldrf_cheque_amu.equals(m_Strrf_cheque_amu)) {

                    double buyer_outsatnding = 0.00;
                    double buyer_new_outsatnding = 0.00;
                    log.info("Checking for buyer old chech amu outsatnding");
                    m_strQry = "select shb_facty_det_os from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        buyer_outsatnding = Double.parseDouble(_rs.getString("shb_facty_det_os"));
                    }
                    buyer_new_outsatnding = buyer_outsatnding + Double.parseDouble(m_Strrf_cheque_amu) - Double.parseDouble(m_StrOldrf_cheque_amu);
                    log.info("buyer new outsatnding :" + buyer_new_outsatnding);
                    // set buyer new outsatnding
                    m_strQry = "update ndb_seller_has_buyers set shb_facty_det_os='" + buyer_new_outsatnding + "'"
                            + " where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
                    log.info("buyer new outsatnding update to:" + buyer_new_outsatnding + "MY SQL Quiery " + m_strQry);
                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    //........................................ gefu entries.............................................
                    /// new entry
                    double amount = 0.00;
                    String account = "";
                    String currency = "LKR";
                    String date = "";
                    String narration = "";
                    String credit_debit = "";
                    String profit_centre = "";
                    String DAO = "";
                    double c_amount = 0.00;
                    double d_amount = 0.00;
                    String system_date = _system_date;
                    String gefu_rf_fixed_frequency = rf_fixed_frequency;
                    String gefu_rec_finance_bulk_credit = rec_finance_bulk_credit;
                    String[] gefu_date = _system_date.split("/");
                    String gefu_day = gefu_date[0];
                    String gefu_month = gefu_date[1];
                    String gefu_year = gefu_date[2];
                    date = gefu_year + gefu_month + gefu_day;

                    if (rec_finance_commision_crg.equals("TRANSACTION BASED")) {

                        if (!(rf_tran_base_falt_fee == 0.00)) {
                            amount = rf_tran_base_falt_fee;

                        } else {
                            amount = Double.parseDouble(m_Strrf_cheque_amu) / 100 * rf_tran_base_from_tran;

                        }

                        boolean gefu_genearation_check = true;
                        log.info("Need to reverse GEFU Entries of old chq amu.");

                        m_strQry = "select * from gefu_file_generation where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and gefu_creation_status='ACTIVE' and credit_debit='D' and gefu_type='COMCHGCD'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        log.info("Ncheq for GEFU commision entries custemr debit already exist in GEFU.");
                        if (_rs.next()) {

                            m_strQry = "update gefu_file_generation set amount='" + amount + "'"
                                    + ",d_amount='" + amount + "'"
                                    + ",status='UN-AUTHORIZED'"
                                    + ",mod_by='" + m_str_user_id + "'"
                                    + ",mod_date=NOW()"
                                    + " where idgefu_file_generation='" + _rs.getString("idgefu_file_generation") + "'";
                            log.info("Already exist and update customer commision debit mysql quiery :" + m_strQry);

                            if (_stmnt2.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }
                            gefu_genearation_check = false;

                        }
                        log.info("Ncheq for GEFU commision entries NDB Commision PL credit already exist in GEFU.");

                        m_strQry = "select * from gefu_file_generation where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and gefu_creation_status='ACTIVE' and credit_debit='C' and gefu_type='COMCHGBC'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (_rs.next()) {

                            m_strQry = "update gefu_file_generation set amount='" + amount + "'"
                                    + ",c_amount='" + amount + "'"
                                    + ",status='UN-AUTHORIZED'"
                                    + ",mod_by='" + m_str_user_id + "'"
                                    + ",mod_date=NOW()"
                                    + " where idgefu_file_generation='" + _rs.getString("idgefu_file_generation") + "'";
                            log.info("Already exist and update NDB commision PL commision Credit mysql quiery :" + m_strQry);

                            if (_stmnt2.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }
                            gefu_genearation_check = false;

                        }

                        if (gefu_genearation_check) {
                            log.info("NEW ACC ENTRY For new commsion charges start");

                            // cutomer debit entry
                            if (rec_finance_commision_crg.equals("TRANSACTION BASED")) {

                                if (!(rf_tran_base_falt_fee == 0.00)) {
                                    amount = rf_tran_base_falt_fee;

                                } else {
                                    amount = Double.parseDouble(m_Strrf_cheque_amu) / 100 * rf_tran_base_from_tran;

                                }

                            }
                            if (rec_finance_commision_crg.equals("FIXED CHARGE BASED")) {
                                amount = rf_fixed_rate_amount;
                            }
                            d_amount = amount;
                            account = rec_finance_curr_ac;
                            narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/UPDT/COM.SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                            log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                            log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");
                            m_strQry = "insert into gefu_file_generation ("
                                    + "idndb_pdc_txn_master"
                                    + ",account"
                                    + ",currency"
                                    + ",date"
                                    + ",amount"
                                    + ",narration"
                                    + ",credit_debit"
                                    + ",profit_centre"
                                    + ",DAO"
                                    + ",c_amount"
                                    + ",d_amount"
                                    + ",gefu_creation_status"
                                    + ",status"
                                    + ",creat_by"
                                    + ",creat_date"
                                    + ",mod_by"
                                    + ",mod_date"
                                    + ",system_date"
                                    + ",gefu_type"
                                    + ",bulk_credit"
                                    + ",cw_fixed_frequency"
                                    + ") values("
                                    + "'" + m_str_idndb_pdc_txn_master + "',"
                                    + "'" + account + "',"
                                    + "'" + currency + "',"
                                    + "'" + date + "',"
                                    + "'" + amount + "',"
                                    + "'" + narration + "',"
                                    + "'D',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + c_amount + "',"
                                    + "'" + d_amount + "',"
                                    + "'ACTIVE',"
                                    + "'UN-AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'COMCHGCD',"
                                    + "'NO',"
                                    + "'" + gefu_rf_fixed_frequency + "')";

                            log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry);

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                            // ndb credit entry
                            c_amount = amount;
                            d_amount = 0.00;
                            account = NDBCommisionPLAcc;

                            narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/UPDT/COM.COMPLCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;

                            log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGBC");
                            m_strQry = "insert into gefu_file_generation ("
                                    + "idndb_pdc_txn_master"
                                    + ",account"
                                    + ",currency"
                                    + ",date"
                                    + ",amount"
                                    + ",narration"
                                    + ",credit_debit"
                                    + ",profit_centre"
                                    + ",DAO"
                                    + ",c_amount"
                                    + ",d_amount"
                                    + ",gefu_creation_status"
                                    + ",status"
                                    + ",creat_by"
                                    + ",creat_date"
                                    + ",mod_by"
                                    + ",mod_date"
                                    + ",system_date"
                                    + ",gefu_type"
                                    + ",bulk_credit"
                                    + ",cw_fixed_frequency"
                                    + ") values("
                                    + "'" + m_str_idndb_pdc_txn_master + "',"
                                    + "'" + account + "',"
                                    + "'" + currency + "',"
                                    + "'" + date + "',"
                                    + "'" + amount + "',"
                                    + "'" + narration + "',"
                                    + "'C',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + c_amount + "',"
                                    + "'" + d_amount + "',"
                                    + "'ACTIVE',"
                                    + "'UN-AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'COMCHGBC',"
                                    + "'NO',"
                                    + "'" + gefu_rf_fixed_frequency + "')";

                            log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ MY SQL Quiery :" + m_strQry);
                            log.info("End of ACC Commision entries");

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                            // update reversel entry 
                            log.info("Start of reversel of commision entries");
                            if (rec_finance_commision_crg.equals("TRANSACTION BASED")) {

                                if (!(rf_tran_base_falt_fee == 0.00)) {
                                    amount = rf_tran_base_falt_fee;

                                } else {
                                    amount = Double.parseDouble(m_StrOldrf_cheque_amu) / 100 * rf_tran_base_from_tran;

                                }

                                c_amount = amount;
                                d_amount = 0.00;
                                account = rec_finance_curr_ac;
                                narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/RVSL/COM.SELLERCREDT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                                // cutomer credit
                                log.info("ACC. ENTRY COMMISION REVERSEL: Seller current account number credit & NDB bank commision PL debit");
                                log.info("ACC. ENTRY COMMISION REVERSEL: Seller current account number credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGCDR");
                                m_strQry = "insert into gefu_file_generation ("
                                        + "idndb_pdc_txn_master"
                                        + ",account"
                                        + ",currency"
                                        + ",date"
                                        + ",amount"
                                        + ",narration"
                                        + ",credit_debit"
                                        + ",profit_centre"
                                        + ",DAO"
                                        + ",c_amount"
                                        + ",d_amount"
                                        + ",gefu_creation_status"
                                        + ",status"
                                        + ",creat_by"
                                        + ",creat_date"
                                        + ",mod_by"
                                        + ",mod_date"
                                        + ",system_date"
                                        + ",gefu_type"
                                        + ",bulk_credit"
                                        + ",cw_fixed_frequency"
                                        + ") values("
                                        + "'" + m_str_idndb_pdc_txn_master + "',"
                                        + "'" + account + "',"
                                        + "'" + currency + "',"
                                        + "'" + date + "',"
                                        + "'" + amount + "',"
                                        + "'" + narration + "/REVSL" + "',"
                                        + "'C',"
                                        + "'" + profit_centre + "',"
                                        + "'" + DAO + "',"
                                        + "'" + c_amount + "',"
                                        + "'" + d_amount + "',"
                                        + "'ACTIVE',"
                                        + "'UN-AUTHORIZED',"
                                        + "'" + m_str_user_id + "',"
                                        + "NOW(),"
                                        + "'" + m_str_user_id + "',"
                                        + "NOW(),"
                                        + "'" + system_date + "',"
                                        + "'COMCHGCDR',"
                                        + "'NO',"
                                        + "'" + gefu_rf_fixed_frequency + "')";

                                log.info("ACC. ENTRY COMMISION REVERSEL: Seller current account number credit / MYSQL QUIERY " + m_strQry);

                                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                    throw new Exception("Error Occured in insert user-roles");
                                }

                                // ndb debit entry
                                d_amount = amount;
                                c_amount = 0.00;
                                account = NDBCommisionPLAcc;
                                narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/RVSL/COM.COMPLDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                                log.info("ACC. ENTRY COMMISION REVERSEL: NDB Bank commision PL debit/debit Acc: " + account + ". debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGBCR");
                                m_strQry = "insert into gefu_file_generation ("
                                        + "idndb_pdc_txn_master"
                                        + ",account"
                                        + ",currency"
                                        + ",date"
                                        + ",amount"
                                        + ",narration"
                                        + ",credit_debit"
                                        + ",profit_centre"
                                        + ",DAO"
                                        + ",c_amount"
                                        + ",d_amount"
                                        + ",gefu_creation_status"
                                        + ",status"
                                        + ",creat_by"
                                        + ",creat_date"
                                        + ",mod_by"
                                        + ",mod_date"
                                        + ",system_date"
                                        + ",gefu_type"
                                        + ",bulk_credit"
                                        + ",cw_fixed_frequency"
                                        + ") values("
                                        + "'" + m_str_idndb_pdc_txn_master + "',"
                                        + "'" + account + "',"
                                        + "'" + currency + "',"
                                        + "'" + date + "',"
                                        + "'" + amount + "',"
                                        + "'" + narration + "/REVSL" + "',"
                                        + "'D',"
                                        + "'" + profit_centre + "',"
                                        + "'" + DAO + "',"
                                        + "'" + c_amount + "',"
                                        + "'" + d_amount + "',"
                                        + "'ACTIVE',"
                                        + "'UN-AUTHORIZED',"
                                        + "'" + m_str_user_id + "',"
                                        + "NOW(),"
                                        + "'" + m_str_user_id + "',"
                                        + "NOW(),"
                                        + "'" + system_date + "',"
                                        + "'COMCHGBCR',"
                                        + "'NO',"
                                        + "'" + gefu_rf_fixed_frequency + "')";
                                log.info("ACC. ENTRY COMMISION REVERSEL: NDB Bank commision PL debit/ MYSQL QUIERY " + m_strQry);

                                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                    throw new Exception("Error Occured in insert user-roles");
                                }

                            }

                        }

                    }

                    ///..........................................FR Entryies
                    log.info("Start of RF Facility entries");
                    String _account = "";
                    double _amount = 0.00;
                    String _narration = "";
                    String _credit_debit = "";
                    String _profit_centre = "";
                    String _DAO = "";
                    double _c_amount = 0.00;
                    double _d_amount = 0.00;
                    boolean gefu_genearation_check = true;
                    log.info("check for RF fcility old cheq amu entries already exist in GEFU ");
                    m_strQry = "select * from gefu_file_generation where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and gefu_creation_status='ACTIVE' and credit_debit='D' and gefu_type='RFCDCD'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {

                        m_strQry = "update gefu_file_generation set amount='" + _amount + "'"
                                + ",d_amount='" + _amount + "'"
                                + ",status='UN-AUTHORIZED'"
                                + ",mod_by='" + m_str_user_id + "'"
                                + ",mod_date=NOW()"
                                + " where idgefu_file_generation='" + _rs.getString("idgefu_file_generation") + "'";
                        log.info("check for RF fcility old cheq amu seller receivable finance acc debit entries already exist iand update to MY SQL QUiery :" + m_strQry);
                        if (_stmnt2.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        gefu_genearation_check = false;

                    }
                    m_strQry = "select * from gefu_file_generation where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and gefu_creation_status='ACTIVE' and credit_debit='C' gefu_type='RFCDBC'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {

                        m_strQry = "update gefu_file_generation set amount='" + _amount + "'"
                                + ",c_amount='" + _amount + "'"
                                + ",status='UN-AUTHORIZED'"
                                + ",mod_by='" + m_str_user_id + "'"
                                + ",mod_date=NOW()"
                                + " where idgefu_file_generation='" + _rs.getString("idgefu_file_generation") + "'";
                        log.info("check for RF fcility old cheq amu seller current acc credit entries already exist iand update to MY SQL QUiery :" + m_strQry);

                        if (_stmnt2.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        gefu_genearation_check = false;

                    }

                    if (gefu_genearation_check) {

                        log.info("New entries for reversal rf  entries and new entries /cant update GEFU already created");

                        _amount = m_discounting_amu_from_cheque_amu;
                        _account = rec_finance_acc_num;
                        _credit_debit = "D";
                        _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/ADVANCE/UPDT/RECFINACC/SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                        _d_amount = m_discounting_amu_from_cheque_amu;
                        _c_amount = 0.00;

                        log.info("ACC. ENTRY : Seller receviable finance account number debit & seller collection account credit");
                        log.info("ACC. ENTRY : Seller receviable finance number debit/ debit Acc: " + _account + ". Debit Amu: " + _d_amount + "Narration : " + _narration + " Type :RFCDCD");

                        m_strQry = "insert into gefu_file_generation ("
                                + "idndb_pdc_txn_master"
                                + ",account"
                                + ",currency"
                                + ",date"
                                + ",amount"
                                + ",narration"
                                + ",credit_debit"
                                + ",profit_centre"
                                + ",DAO"
                                + ",c_amount"
                                + ",d_amount"
                                + ",gefu_creation_status"
                                + ",status"
                                + ",creat_by"
                                + ",creat_date"
                                + ",mod_by"
                                + ",mod_date"
                                + ",system_date"
                                + ",gefu_type"
                                + ",bulk_credit"
                                + ",cw_fixed_frequency"
                                + ") values("
                                + "'" + m_str_idndb_pdc_txn_master + "',"
                                + "'" + _account + "',"
                                + "'" + currency + "',"
                                + "'" + date + "',"
                                + "'" + _amount + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + _c_amount + "',"
                                + "'" + _d_amount + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + system_date + "',"
                                + "'RFCDCD',"
                                + "'NO',"
                                + "'" + gefu_rf_fixed_frequency + "')";

                        log.info("ACC. ENTRY : Seller receviable finance number debit/ MY SQL Quiery " + m_strQry);

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                        _amount = m_discounting_amu_from_cheque_amu;
                        _account = rec_finance_cr_dsc_proc_acc_num;
                        _credit_debit = "C";
                        _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/ADVANCE/UPDT/CURRACCACC/SELLERCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                        _c_amount = m_discounting_amu_from_cheque_amu;
                        _d_amount = 0.00;

                        log.info("ACC. ENTRY : Seller collection account credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :RFCDBC");

                        // ndb credit entry
                        m_strQry = "insert into gefu_file_generation ("
                                + "idndb_pdc_txn_master"
                                + ",account"
                                + ",currency"
                                + ",date"
                                + ",amount"
                                + ",narration"
                                + ",credit_debit"
                                + ",profit_centre"
                                + ",DAO"
                                + ",c_amount"
                                + ",d_amount"
                                + ",gefu_creation_status"
                                + ",status"
                                + ",creat_by"
                                + ",creat_date"
                                + ",mod_by"
                                + ",mod_date"
                                + ",system_date"
                                + ",gefu_type"
                                + ",bulk_credit"
                                + ",cw_fixed_frequency"
                                + ") values("
                                + "'" + m_str_idndb_pdc_txn_master + "',"
                                + "'" + NDBCommisionPLAcc + "',"
                                + "'" + currency + "',"
                                + "'" + date + "',"
                                + "'" + _amount + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + _c_amount + "',"
                                + "'" + _d_amount + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + system_date + "',"
                                + "'RFCDBC',"
                                + "'" + gefu_rec_finance_bulk_credit + "',"
                                + "'" + gefu_rf_fixed_frequency + "')";
                        log.info("ACC. ENTRY : Seller collection account credit/ MY SQL Quiery " + m_strQry);

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        log.info("End of RF Facility entries");

                        //............................... reversel entry...........................
                        _amount = m_Olddiscounting_amu_from_cheque_amu;
                        _account = rec_finance_acc_num;
                        _credit_debit = "C";

                        _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/ADVANCE/RVSL/RECFINACC/SELLERCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                        _d_amount = 0.00;
                        _c_amount = m_Olddiscounting_amu_from_cheque_amu;

                        log.info("Start of RF Facility reversel entries");

                        log.info("ACC. ENTRY REVERSEL: Seller receviable finance account number crdit old chq disconting amu & seller collection account debit old chq disconting amu");
                        log.info("ACC. ENTRY REVERSEL : Seller receviable finance account number crdit old chq disconting amu/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :RFCDCDR");

                        m_strQry = "insert into gefu_file_generation ("
                                + "idndb_pdc_txn_master"
                                + ",account"
                                + ",currency"
                                + ",date"
                                + ",amount"
                                + ",narration"
                                + ",credit_debit"
                                + ",profit_centre"
                                + ",DAO"
                                + ",c_amount"
                                + ",d_amount"
                                + ",gefu_creation_status"
                                + ",status"
                                + ",creat_by"
                                + ",creat_date"
                                + ",mod_by"
                                + ",mod_date"
                                + ",system_date"
                                + ",gefu_type"
                                + ",bulk_credit"
                                + ",cw_fixed_frequency"
                                + ") values("
                                + "'" + m_str_idndb_pdc_txn_master + "',"
                                + "'" + _account + "',"
                                + "'" + currency + "',"
                                + "'" + date + "',"
                                + "'" + _amount + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + _c_amount + "',"
                                + "'" + _d_amount + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + system_date + "',"
                                + "'RFCDCDR',"
                                + "'NO',"
                                + "'" + gefu_rf_fixed_frequency + "')";

                        log.info("ACC. ENTRY REVERSEL : Seller receviable finance account number crdit old chq disconting amu/ MY SQL Quiery " + m_strQry);

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                        _amount = m_Olddiscounting_amu_from_cheque_amu;
                        _account = rec_finance_cr_dsc_proc_acc_num;
                        _credit_debit = "D";
                        _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/ADVANCE/RVSL/CURRACCACC/SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                        _c_amount = 0.00;
                        _d_amount = m_Olddiscounting_amu_from_cheque_amu;

                        log.info("ACC. ENTRY REVERSEL : Seller receviable collection account number debit old chq disconting amu/ debit Acc: " + account + ". debit Amu: " + d_amount + "Narration : " + narration + " Type :RFCDBCR");

                        // ndb credit entry
                        m_strQry = "insert into gefu_file_generation ("
                                + "idndb_pdc_txn_master"
                                + ",account"
                                + ",currency"
                                + ",date"
                                + ",amount"
                                + ",narration"
                                + ",credit_debit"
                                + ",profit_centre"
                                + ",DAO"
                                + ",c_amount"
                                + ",d_amount"
                                + ",gefu_creation_status"
                                + ",status"
                                + ",creat_by"
                                + ",creat_date"
                                + ",mod_by"
                                + ",mod_date"
                                + ",system_date"
                                + ",gefu_type"
                                + ",bulk_credit"
                                + ",cw_fixed_frequency"
                                + ") values("
                                + "'" + m_str_idndb_pdc_txn_master + "',"
                                + "'" + NDBCommisionPLAcc + "',"
                                + "'" + currency + "',"
                                + "'" + date + "',"
                                + "'" + _amount + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + _c_amount + "',"
                                + "'" + _d_amount + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + system_date + "',"
                                + "'RFCDBCR',"
                                + "'NO',"
                                + "'" + gefu_rf_fixed_frequency + "')";

                        log.info("ACC. ENTRY REVERSEL : Seller receviable collection account number debit old chq disconting amu/ MY SQL Quiery" + m_strQry);

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                    }

                    // margin entry update..................................
                    if (!(rec_finance_margin_ac.equals("") && rec_finance_margin.equals(""))) {
                        ///..........................................FR Entryies
                        log.info("RF faclitiy margin entry upadte");

                        double margin_amu = Double.parseDouble(m_Strrf_cheque_amu) / 100 * Double.parseDouble(rec_finance_margin);
                        double old_margin_amu = Double.parseDouble(m_StrOldrf_cheque_amu) / 100 * Double.parseDouble(rec_finance_margin);

                        _account = "";
                        _amount = 0.00;
                        _narration = "";
                        _credit_debit = "";
                        _profit_centre = "";
                        _DAO = "";
                        _c_amount = 0.00;
                        _d_amount = 0.00;
                        gefu_genearation_check = true;
                        log.info("check for old marging amu already exist in GEFU file");
                        m_strQry = "select * from gefu_file_generation where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and gefu_creation_status='ACTIVE' and credit_debit='D' and gefu_type='RFMARCDCD'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (_rs.next()) {

                            m_strQry = "update gefu_file_generation set amount='" + margin_amu + "'"
                                    + ",d_amount='" + margin_amu + "'"
                                    + ",status='UN-AUTHORIZED'"
                                    + ",mod_by='" + m_str_user_id + "'"
                                    + ",mod_date=NOW()"
                                    + " where idgefu_file_generation='" + _rs.getString("idgefu_file_generation") + "'";
                            log.info("already exist in GEFU file and update MY SQL Quier :" + m_strQry);
                            if (_stmnt2.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }
                            gefu_genearation_check = false;

                        }
                        m_strQry = "select * from gefu_file_generation where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and gefu_creation_status='ACTIVE' and credit_debit='C' gefu_type='RFMARCDBC'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (_rs.next()) {

                            m_strQry = "update gefu_file_generation set amount='" + margin_amu + "'"
                                    + ",c_amount='" + margin_amu + "'"
                                    + ",status='UN-AUTHORIZED'"
                                    + ",mod_by='" + m_str_user_id + "'"
                                    + ",mod_date=NOW()"
                                    + " where idgefu_file_generation='" + _rs.getString("idgefu_file_generation") + "'";
                            log.info("already exist in GEFU file and update MY SQL Quier :" + m_strQry);
                            if (_stmnt2.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }
                            gefu_genearation_check = false;

                        }

                        if (gefu_genearation_check) {
                            log.info("GEFU File already creadeted and need to creat reversel entry and new entry for new amount");
                            _amount = margin_amu;
                            _account = rec_finance_curr_ac;
                            _credit_debit = "D";
                            _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/MARGIN/UPDT/RECFINACC/SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                            d_amount = margin_amu;
                            c_amount = 0.00;

                            log.info("Start Of marging entry");

                            log.info("ACC. ENTRY : Seller current account number debit & seller marging account credit");
                            log.info("ACC. ENTRY : Sellercurrent account number debit / debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :RFMARCDCD");

                            m_strQry = "insert into gefu_file_generation ("
                                    + "idndb_pdc_txn_master"
                                    + ",account"
                                    + ",currency"
                                    + ",date"
                                    + ",amount"
                                    + ",narration"
                                    + ",credit_debit"
                                    + ",profit_centre"
                                    + ",DAO"
                                    + ",c_amount"
                                    + ",d_amount"
                                    + ",gefu_creation_status"
                                    + ",status"
                                    + ",creat_by"
                                    + ",creat_date"
                                    + ",mod_by"
                                    + ",mod_date"
                                    + ",system_date"
                                    + ",gefu_type"
                                    + ",bulk_credit"
                                    + ",cw_fixed_frequency"
                                    + ") values("
                                    + "'" + m_str_idndb_pdc_txn_master + "',"
                                    + "'" + _account + "',"
                                    + "'" + currency + "',"
                                    + "'" + date + "',"
                                    + "'" + _amount + "',"
                                    + "'" + _narration + "',"
                                    + "'" + _credit_debit + "',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + _c_amount + "',"
                                    + "'" + _d_amount + "',"
                                    + "'ACTIVE',"
                                    + "'UN-AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'RFMARCDCD',"
                                    + "'NO',"
                                    + "'" + gefu_rf_fixed_frequency + "')";
                            log.info("ACC. ENTRY : Seller current account number debit /YSQL QUIERY :" + m_strQry);
                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                            _amount = margin_amu;
                            _account = rec_finance_margin_ac;
                            _credit_debit = "C";
                            _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/MARGIN/UPDT/CURRACCACC/SELLERCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                            _c_amount = margin_amu;
                            _d_amount = 0.00;

                            log.info("ACC. ENTRY : Seller Marging account number credit / credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :RFMARCDBC");

                            // ndb credit entry
                            m_strQry = "insert into gefu_file_generation ("
                                    + "idndb_pdc_txn_master"
                                    + ",account"
                                    + ",currency"
                                    + ",date"
                                    + ",amount"
                                    + ",narration"
                                    + ",credit_debit"
                                    + ",profit_centre"
                                    + ",DAO"
                                    + ",c_amount"
                                    + ",d_amount"
                                    + ",gefu_creation_status"
                                    + ",status"
                                    + ",creat_by"
                                    + ",creat_date"
                                    + ",mod_by"
                                    + ",mod_date"
                                    + ",system_date"
                                    + ",gefu_type"
                                    + ",bulk_credit"
                                    + ",cw_fixed_frequency"
                                    + ") values("
                                    + "'" + m_str_idndb_pdc_txn_master + "',"
                                    + "'" + NDBCommisionPLAcc + "',"
                                    + "'" + currency + "',"
                                    + "'" + date + "',"
                                    + "'" + _amount + "',"
                                    + "'" + _narration + "',"
                                    + "'" + _credit_debit + "',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + _c_amount + "',"
                                    + "'" + _d_amount + "',"
                                    + "'ACTIVE',"
                                    + "'UN-AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'RFMARCDBC',"
                                    + "'" + gefu_rec_finance_bulk_credit + "',"
                                    + "'" + gefu_rf_fixed_frequency + "')";

                            log.info("ACC. ENTRY : Seller Marging account number credit / MY SQL QUIERY :" + m_strQry);

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                            log.info("End of marging entries");

                            //............................... reversel entry...........................
                            _amount = old_margin_amu;
                            _account = rec_finance_curr_ac;
                            _credit_debit = "C";

                            log.info("start of reversel marging entries");

                            _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/MARGIN/RVSL/RECFINACC/SELLERCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                            _d_amount = 0.00;
                            _c_amount = old_margin_amu;

                            log.info("ACC. ENTRY : Seller current account number credit & seller marging account debit");
                            log.info("ACC. ENTRY : Sellercurrent account number credit / credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :RFRMARCDCD");

                            m_strQry = "insert into gefu_file_generation ("
                                    + "idndb_pdc_txn_master"
                                    + ",account"
                                    + ",currency"
                                    + ",date"
                                    + ",amount"
                                    + ",narration"
                                    + ",credit_debit"
                                    + ",profit_centre"
                                    + ",DAO"
                                    + ",c_amount"
                                    + ",d_amount"
                                    + ",gefu_creation_status"
                                    + ",status"
                                    + ",creat_by"
                                    + ",creat_date"
                                    + ",mod_by"
                                    + ",mod_date"
                                    + ",system_date"
                                    + ",gefu_type"
                                    + ",bulk_credit"
                                    + ",cw_fixed_frequency"
                                    + ") values("
                                    + "'" + m_str_idndb_pdc_txn_master + "',"
                                    + "'" + _account + "',"
                                    + "'" + currency + "',"
                                    + "'" + date + "',"
                                    + "'" + _amount + "',"
                                    + "'" + _narration + "',"
                                    + "'" + _credit_debit + "',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + _c_amount + "',"
                                    + "'" + _d_amount + "',"
                                    + "'ACTIVE',"
                                    + "'UN-AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'RFRMARCDCD',"
                                    + "'NO',"
                                    + "'" + gefu_rf_fixed_frequency + "')";
                            log.info("ACC. ENTRY : Sellercurrent account number credit /MY SQL QUIERY" + m_strQry);

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                            _amount = old_margin_amu;
                            _account = rec_finance_margin_ac;
                            _credit_debit = "D";
                            _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/MARGIN/RVSL/CURRACCACC/SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                            _c_amount = 0.00;
                            _d_amount = old_margin_amu;

                            log.info("ACC. ENTRY : Seller Marging account number debit / debit Acc: " + account + ". debit Amu: " + c_amount + "Narration : " + narration + " Type :RFRMARCDBC");

                            // ndb credit entry
                            m_strQry = "insert into gefu_file_generation ("
                                    + "idndb_pdc_txn_master"
                                    + ",account"
                                    + ",currency"
                                    + ",date"
                                    + ",amount"
                                    + ",narration"
                                    + ",credit_debit"
                                    + ",profit_centre"
                                    + ",DAO"
                                    + ",c_amount"
                                    + ",d_amount"
                                    + ",gefu_creation_status"
                                    + ",status"
                                    + ",creat_by"
                                    + ",creat_date"
                                    + ",mod_by"
                                    + ",mod_date"
                                    + ",system_date"
                                    + ",gefu_type"
                                    + ",bulk_credit"
                                    + ",cw_fixed_frequency"
                                    + ") values("
                                    + "'" + m_str_idndb_pdc_txn_master + "',"
                                    + "'" + NDBCommisionPLAcc + "',"
                                    + "'" + currency + "',"
                                    + "'" + date + "',"
                                    + "'" + _amount + "',"
                                    + "'" + _narration + "',"
                                    + "'" + _credit_debit + "',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + _c_amount + "',"
                                    + "'" + _d_amount + "',"
                                    + "'ACTIVE',"
                                    + "'UN-AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'RFRMARCDBC',"
                                    + "'NO',"
                                    + "'" + gefu_rf_fixed_frequency + "')";
                            log.info("ACC. ENTRY : Seller Marging account number debit / " + m_strQry);

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }
                            log.info("End of margin reversel entry");

                        }

                    }

                    // end
                }
                if (m_Strrf_cheque_liquidation.equals("ACTIVE")) {
                    double buyer_outsatnding = 0.00;
                    double buyer_new_outsatnding = 0.00;
                    m_strQry = "select shb_facty_det_os from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
                    _rs = _stmnt.executeQuery(m_strQry);

                    if (_rs.next()) {
                        buyer_outsatnding = Double.parseDouble(_rs.getString("shb_facty_det_os"));
                    }
                    buyer_new_outsatnding = buyer_outsatnding - Double.parseDouble(m_StrOldrf_cheque_amu);

                    // set buyer new outsatnding
                    m_strQry = "update ndb_seller_has_buyers set shb_facty_det_os='" + buyer_new_outsatnding + "'"
                            + " where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }
                }

                if (m_Strrf_cheque_liquidation.equals("ACTIVE")) {
                    double buyer_outsatnding = 0.00;
                    double buyer_new_outsatnding = 0.00;
                    m_strQry = "select shb_facty_det_os from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        buyer_outsatnding = Double.parseDouble(_rs.getString("shb_facty_det_os"));
                    }
                    buyer_new_outsatnding = buyer_outsatnding - Double.parseDouble(m_StrOldrf_cheque_amu);

                    // set buyer new outsatnding
                    m_strQry = "update ndb_seller_has_buyers set shb_facty_det_os='" + buyer_new_outsatnding + "'"
                            + " where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }
                }
                if (!m_StrOldrf_value_date.equals(m_Strrf_value_date_temp)) {

                    log.info("ACC. ENTRY : Value date extraction ");
                    String account = NDBCommisionPLAcc;
                    String currency = "LKR";
                    String date = _system_date;
                    double amount = rec_finance_vale_dte_extr_chg;
                    String narration = "";
                    String credit_debit = "";
                    String profit_centre = "";
                    String DAO = "";
                    double c_amount = 0.00;
                    double d_amount = 0.00;
                    String system_date = _system_date;
                    String gefu_rf_fixed_frequency = rf_fixed_frequency;
                    d_amount = amount;
                    narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/VDATEEXTRACT/SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                    log.info("ACC. ENTRY : seller current account debit/ value date extraction charges & ndb commision pl credit value date extraaction charges");
                    log.info("ACC. ENTRY : Seller current account nummber debit value date extraction charges debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :VDEXTRCD");

                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + m_str_idndb_pdc_txn_master + "',"
                            + "'" + account + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + amount + "',"
                            + "'" + narration + "',"
                            + "'D',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + c_amount + "',"
                            + "'" + d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'VDEXTRCD',"
                            + "'NO',"
                            + "'" + gefu_rf_fixed_frequency + "')";
                    log.info("ACC. ENTRY : Seller current account nummber debit value date extraction charges / MYSQL QUIERY :" + m_strQry);
                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    d_amount = 0.00;
                    c_amount = amount;
                    account = NDBCommisionPLAcc;
                    narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/VDATEEXTRACT/COMPLCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;

                    log.info("ACC. ENTRY : NDB commision PL credit value date extraction charges credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :VDEXTRBC");

                    // ndb credit entry
                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + m_str_idndb_pdc_txn_master + "',"
                            + "'" + account + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + amount + "',"
                            + "'" + narration + "',"
                            + "'C',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + c_amount + "',"
                            + "'" + d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'VDEXTRBC',"
                            + "'NO',"
                            + "'" + gefu_rf_fixed_frequency + "')";

                    log.info("ACC. ENTRY : NDB commision PL credit value date extraction charges credit Acc: MYSQL QUIERY :" + m_strQry);
                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }
                }
                if (m_Strrf_cheque_liquidation.equals("ACTIVE")) {
                    log.info("ACC. ENTRY : Erly Lequidation");
                    String account = NDBCommisionPLAcc;
                    String currency = "LKR";
                    String date = _system_date;
                    double amount = rec_finance_erly_wdr_chg;
                    String narration = "";
                    String credit_debit = "";
                    String profit_centre = "";
                    String DAO = "";
                    double c_amount = 0.00;
                    double d_amount = 0.00;
                    String system_date = _system_date;
                    String gefu_rf_fixed_frequency = rf_fixed_frequency;
                    d_amount = amount;
                    narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/ERLYLEQD/SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                    log.info("ACC. ENTRY : seller current account debit/ early lequdation charges & ndb commision pl credit early lequdation charges");
                    log.info("ACC. ENTRY : Seller current account nummber debit early lequdation charges debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :ERLEQDCD");

                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + m_str_idndb_pdc_txn_master + "',"
                            + "'" + account + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + amount + "',"
                            + "'" + narration + "',"
                            + "'D',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + c_amount + "',"
                            + "'" + d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'ERLEQDCD',"
                            + "'NO',"
                            + "'" + gefu_rf_fixed_frequency + "')";

                    log.info("ACC. ENTRY : Seller current account nummber debit early lequdation charges / MYSQL QUIERY :" + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    d_amount = 0.00;
                    c_amount = amount;
                    account = NDBCommisionPLAcc;

                    narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/ERLYLEQD/COMPL/BUYER/" + m_Stridndb_customer_define_buyer_id;
                    log.info("ACC. ENTRY : NDB commision PL credit early lequdation charges credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :ERLEQDBC");
                    // ndb credit entry
                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + m_str_idndb_pdc_txn_master + "',"
                            + "'" + account + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + amount + "',"
                            + "'" + narration + "',"
                            + "'C',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + c_amount + "',"
                            + "'" + d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'ERLEQDBC',"
                            + "'NO',"
                            + "'" + gefu_rf_fixed_frequency + "')";

                    log.info("ACC. ENTRY : NDB commision PL credit early lequdation charges: MYSQL QUIERY :" + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    //...........................................RF Cash.............................
                    //....................................... RF  balcne debit from rec fin acc number...............
                    log.info("RF Faaclity balance amount crediting start ");

                    // RF Return entry.............................
                    String _account = "";
                    double _amount = 0.00;
                    String _narration = "";
                    String _credit_debit = "";
                    String _profit_centre = "";
                    String _DAO = "";
                    double _c_amount = 0.00;
                    double _d_amount = 0.00;
                    _account = "";
                    _amount = 0.00;
                    _narration = "";
                    _credit_debit = "";
                    _profit_centre = "";
                    _DAO = "";
                    _c_amount = 0.00;
                    _d_amount = 0.00;

                    _amount = m_StrOldpdc_chq_cr_amu;
                    _account = rec_finance_acc_num;
                    _credit_debit = "D";
                    _narration = "CUSTID/" + idndb_customer_define_seller_idndb_customer_define + "/RFRETURN/BUYER/" + m_Stridndb_customer_define_buyer_id;
                    _d_amount = _amount;

                    log.info("ACC. ENTRY : Seller receviable finance account number debit & seller collection account credit");
                    log.info("ACC. ENTRY : Seller receviable finance number debit/ debit Acc: " + _account + ". Debit Amu: " + _d_amount + "Narration : " + _narration + " Type :RFCDRETCD");

                    // customer curr ACC debit entry ...............................
                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + m_str_idndb_pdc_txn_master + "',"
                            + "'" + _account + "',"
                            + "'LKR',"
                            + "'" + _system_date + "',"
                            + "'" + _amount + "',"
                            + "'" + _narration + "',"
                            + "'" + _credit_debit + "',"
                            + "'" + _profit_centre + "',"
                            + "'" + _DAO + "',"
                            + "'" + _c_amount + "',"
                            + "'" + _d_amount + "',"
                            + "'ACTIVE',"
                            + "'AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + _system_date + "',"
                            + "'RFCDRETCD',"
                            + "'NO',"
                            + "'DAILY')";

                    log.info("ACC. ENTRY : Seller receviable finance number debit/ MY SQL Quiery " + m_strQry);

                    if (_stmnt3.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    _account = rec_finance_curr_ac;
                    _c_amount = _amount;
                    _d_amount = 0.00;

                    log.info("ACC. ENTRY : Seller collection account credit/ credit Acc: " + _account + ". credit Amu: " + _c_amount + "Narration : " + _narration + " Type :RFCDRETBC");

                    // NDB bank collection account Credit entry ...............................
                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + m_str_idndb_pdc_txn_master + "',"
                            + "'" + _account + "',"
                            + "'LKR',"
                            + "'" + _system_date + "',"
                            + "'" + _amount + "',"
                            + "'" + _narration + "',"
                            + "'" + _credit_debit + "',"
                            + "'" + _profit_centre + "',"
                            + "'" + _DAO + "',"
                            + "'" + _c_amount + "',"
                            + "'" + _d_amount + "',"
                            + "'ACTIVE',"
                            + "'AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + _system_date + "',"
                            + "'RFCDRETBC',"
                            + "'NO',"
                            + "'DAILY')";

                    log.info("ACC. ENTRY : Seller collection account credit/ MY SQL Quiery " + m_strQry);

                    if (_stmnt3.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                }

                rf_pdc_save_status = "1100";

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
        return rf_pdc_save_status;
    }

    public String saveCWTORFPDCEntry(JSONObject prm_obj) {
        String rf_pdc_save_status = "0000";

        String m_str_user_id = "";
        String m_str_idndb_pdc_txn_master = "";

        String m_Stridndb_customer_define_seller_id = "";
        String m_StrOldidndb_customer_define_seller_id = "";

        String m_Stridndb_customer_define_buyer_id = "";
        String m_StrOldidndb_customer_define_buyer_id = "";

        String m_Strcust_bank = "";
        String m_StrOldcust_bank = "";

        String m_Strcust_bank_code = "";
        String m_StrOldcust_bank_code = "";

        String m_Strcust_branch = "";
        String m_StrOldcust_branch = "";

        String m_Strcust_branch_code = "";
        String m_StrOldcust_branch_code = "";

        String m_Strrf_value_date_temp = "";
        String m_Strrf_value_date = "";

        String m_Strpdc_bank_name = "";
        String m_StrOldpdc_bank_name = "";

        String m_Strpdc_branch_name = "";
        String m_StrOldpdc_branch_name = "";

        String m_StrOldrf_value_date = "";

        String m_Strrf_cheque_liq_date_temp = "";
        String m_Strrf_cheque_liq_date = "";

        String m_StrOldrf_cheque_liq_date = "";

        String m_str_rf_lequid_date = "";
        String m_strOld_rf_lequid_date = "";

        String m_Strrf_chq_number = "";
        String m_StrOldrf_chq_number = "";

        String m_Strrf_cheque_amu = "";
        String m_StrOldrf_cheque_amu = "";

        String m_strQry = "";
        String NDBCommisionPLAcc = "";
        String _system_date = "";

        boolean m_ardy_exist_data = true;
        boolean m_ardy_exist_dcheque = true;

        try {
            pdc_DAO dao = new pdc_DAO();

            log.info("CW to RF PDC conversion receved");

            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_pdc_txn_master = prm_obj.getString("idndb_pdc_txn_master");

            m_Stridndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            m_Stridndb_customer_define_buyer_id = prm_obj.getString("idndb_customer_define_buyer_id");
            m_Strcust_bank = prm_obj.getString("cust_bank");
            m_Strcust_branch = prm_obj.getString("cust_branch");
            m_Strrf_value_date_temp = dao.getchequeValueDate(prm_obj.getString("rf_value_date"));
            m_Strrf_chq_number = prm_obj.getString("rf_chq_number");
            m_Strrf_cheque_amu = prm_obj.getString("rf_cheque_amu");

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            // java.util.Date dt = new java.util.Date();
            // java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // m_Strcw_value_date = sdf.format(m_Strcw_value_date_temp);
            String[] date_spliter = m_Strrf_value_date_temp.split("/");
            int m_year = Integer.parseInt(date_spliter[2]);
            int m_month = Integer.parseInt(date_spliter[1]) - 1;
            int m_day = Integer.parseInt(date_spliter[0]) + 1;
            Calendar ced = Calendar.getInstance();

            ced.set(Calendar.YEAR, m_year); // set the year
            ced.set(Calendar.MONTH, m_month); // set the month
            ced.set(Calendar.DAY_OF_MONTH, m_day);

            m_Strrf_cheque_liq_date_temp = dao.getchequeValueDate(formatter.format(ced.getTime()));
            // m_Strcw_cheque_liq_date = sdf.format(m_Strcw_cheque_liq_date_temp);

            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            Parameters para = new Parameters();
            NDBCommisionPLAcc = para.getNDBCommisionPLAcc();

            m_strQry = "select * from ndb_system_date";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                _system_date = _rs.getString("_system_date");

            }

            m_strQry = "select * from ndb_bank_master_file where idndb_bank_master_file='" + m_Strcust_bank + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                m_Strcust_bank_code = _rs.getString("bank_code");
                m_Strpdc_bank_name = _rs.getString("bank_name");

            }
            m_strQry = "select * from ndb_branch_master_file where idndb_branch_master_file='" + m_Strcust_branch + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                m_Strcust_branch_code = _rs.getString("branch_id");
                m_Strpdc_branch_name = _rs.getString("branch_name");

            }

            String m_uniq_id = m_Strrf_chq_number + m_Strcust_bank_code + m_Strcust_branch_code;
            m_strQry = "SELECT CONCAT(pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code) as txn_master_unq_id FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE'";
            _rs = _stmnt.executeQuery(m_strQry);
            while (_rs.next()) {
                if (m_uniq_id.equals(_rs.getString("txn_master_unq_id"))) {
                    m_ardy_exist_dcheque = false;
                    //cheque number all ready exist
                    rf_pdc_save_status = "1101";
                }

            }

            double m_discounting_advance_rate = 0.00;
            double m_discounting_amu_from_cheque_amu = 0.00;
            double m_alternat_amu = 0.00;

            m_strQry = "select shb_chq_dis_adv_rate_prectange from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                m_discounting_advance_rate = Double.parseDouble(_rs.getString("shb_chq_dis_adv_rate_prectange"));

                m_discounting_amu_from_cheque_amu = Double.parseDouble(m_Strrf_cheque_amu) / 100 * m_discounting_advance_rate;
                m_alternat_amu = Double.parseDouble(m_Strrf_cheque_amu) - m_discounting_amu_from_cheque_amu;

            }
            log.info("CW to RF PDC conversion discontind dat extraction");

            //....................................................
            // extract commision data
            String rec_finance_commision_crg = "NOTDEFINE";
            String idndb_customer_define_seller = "";
            String idndb_customer_define_seller_idndb_customer_define = "";
            String rec_finance_curr_ac = "";
            String rec_finance_acc_num = "";
            String rec_finance_cr_dsc_proc_acc_num = "";
            String rec_finance_margin_ac = "";
            String rec_finance_margin = "";
            String cust_name = "";
            String rec_finance_bulk_credit = "";
            double rec_finance_erly_wdr_chg = 0.00;
            double rec_finance_vale_dte_extr_chg = 0.00;
            double rec_finance_erly_stlemnt_chg = 0.00;
            double rf_tran_base_falt_fee = 0.00;
            double rf_tran_base_from_tran = 0.00;
            double rf_fixed_rate_amount = 0.00;
            String rf_fixed_frequency = "DAILY";
            m_strQry = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                rec_finance_commision_crg = _rs.getString("rec_finance_commision_crg");
                idndb_customer_define_seller = _rs.getString("idndb_customer_define_seller");

                rf_tran_base_falt_fee = _rs.getDouble("rf_tran_base_falt_fee");
                rf_tran_base_from_tran = _rs.getDouble("rf_tran_base_from_tran");

                rf_fixed_rate_amount = _rs.getDouble("rf_fixed_rate_amount");
                rf_fixed_frequency = _rs.getString("rf_fixed_frequency");

            }

            m_strQry = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                idndb_customer_define_seller_idndb_customer_define = _rs.getString("idndb_customer_define");
            }

            m_strQry = "select * from ndb_rec_fin where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                rec_finance_bulk_credit = _rs.getString("rec_finance_bulk_credit");
                rec_finance_erly_wdr_chg = _rs.getDouble("rec_finance_erly_wdr_chg");
                rec_finance_vale_dte_extr_chg = _rs.getDouble("rec_finance_vale_dte_extr_chg");
                rec_finance_erly_stlemnt_chg = _rs.getDouble("rec_finance_erly_stlemnt_chg");
            }

            m_strQry = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                rec_finance_curr_ac = _rs.getString("rec_finance_curr_ac");
                rec_finance_acc_num = _rs.getString("rec_finance_acc_num");
                rec_finance_cr_dsc_proc_acc_num = _rs.getString("rec_finance_cr_dsc_proc_acc_num");
                rec_finance_margin_ac = _rs.getString("rec_finance_margin_ac");
                rec_finance_margin = _rs.getString("rec_finance_margin");
                cust_name = _rs.getString("cust_name");
            }

            m_strQry = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";

            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                m_StrOldidndb_customer_define_seller_id = _rs.getString("idndb_customer_define_seller_id");
                m_StrOldidndb_customer_define_buyer_id = _rs.getString("idndb_customer_define_buyer_id");
                m_StrOldcust_bank = _rs.getString("idndb_bank_master_file");
                m_StrOldcust_bank_code = _rs.getString("pdc_bank_code");
                m_StrOldcust_branch = _rs.getString("idndb_branch_master_file");
                m_StrOldcust_branch_code = _rs.getString("pdc_branch_code");
                m_StrOldrf_value_date = _rs.getString("pdc_value_date");
                m_StrOldrf_cheque_liq_date = _rs.getString("pdc_lquid_date");
                m_StrOldrf_chq_number = _rs.getString("pdc_chq_number");
                m_StrOldrf_cheque_amu = _rs.getString("pdc_chq_amu");

                m_ardy_exist_data = false;
            }

            if (m_ardy_exist_data) {

            } else {

                String m_condition = "";
                int i = 0;
                String m_change = "";
                if (!m_StrOldidndb_customer_define_seller_id.equals(m_Stridndb_customer_define_seller_id)) {
                    m_condition = "idndb_customer_define_seller_id='" + m_Stridndb_customer_define_seller_id + "',";

                    m_condition = "cust_name='" + cust_name + "',";
                    m_condition = "cust_account_number='" + rec_finance_acc_num + "',";

                }

                if (!m_StrOldidndb_customer_define_buyer_id.equals(m_Stridndb_customer_define_buyer_id)) {
                    m_condition = m_condition + "idndb_customer_define_buyer_id='" + m_Stridndb_customer_define_buyer_id + "',";
                }
                if (!m_StrOldcust_bank.equals(m_Strcust_bank)) {
                    m_condition = m_condition + "idndb_bank_master_file='" + m_Strcust_bank + "',";
                }
                if (!m_StrOldcust_bank_code.equals(m_Strcust_bank_code)) {
                    m_condition = m_condition + "pdc_bank_code='" + m_Strcust_bank_code + "',";
                    m_condition = m_condition + "pdc_bank_name='" + m_Strpdc_bank_name + "',";

                    i++;
                    m_change = m_change + i + ")" + m_StrOldcust_bank_code + " BANK CODE CHANGE TO " + m_Strcust_bank_code + "<br>";

                }
                if (!m_StrOldcust_branch.equals(m_Strcust_branch)) {
                    m_condition = m_condition + "idndb_branch_master_file='" + m_Strcust_branch + "',";
                }
                if (!m_StrOldcust_branch_code.equals(m_Strcust_branch_code)) {
                    m_condition = m_condition + "pdc_branch_code='" + m_Strcust_branch_code + "',";
                    m_condition = m_condition + "pdc_branch_name='" + m_Strpdc_branch_name + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldcust_branch_code + " BRANCH CODE CHANGE TO " + m_Strcust_branch_code + "<br>";

                }
                if (!m_StrOldrf_chq_number.equals(m_Strrf_chq_number)) {
                    m_condition = m_condition + "pdc_chq_number='" + m_Strrf_chq_number + "',";

                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_chq_number + " CHQ. NUM. CHANGE TO " + m_Strrf_chq_number + "<br>";

                }
                if (!m_StrOldrf_cheque_amu.equals(m_Strrf_cheque_amu)) {
                    m_condition = m_condition + "pdc_chq_amu='" + m_Strrf_cheque_amu + "',";
                    m_condition = m_condition + "pdc_chq_discounting_amu='" + m_discounting_amu_from_cheque_amu + "',";
                    m_condition = m_condition + "pdc_chq_net_amu='" + m_alternat_amu + "',";
                    m_condition = m_condition + "pdc_chq_cr_amu='" + m_alternat_amu + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_cheque_amu + " CHQ. AMU. CHANGE TO " + m_Strrf_cheque_amu + "<br>";

                }
                if (!m_StrOldrf_value_date.equals(m_Strrf_value_date_temp)) {
                    m_condition = m_condition + "pdc_value_date='" + m_Strrf_value_date_temp + "',";
                    m_condition = m_condition + "pdc_value_date_ext='ACTIVE',";
                    m_condition = m_condition + "pdc_old_value_date='" + m_StrOldrf_value_date + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_value_date + " VALUE DATE CHANGE TO " + m_Strrf_value_date_temp + "<br>";

                }
                if (!m_StrOldrf_cheque_liq_date.equals(m_Strrf_cheque_liq_date_temp)) {
                    m_condition = m_condition + "pdc_lquid_date='" + m_Strrf_cheque_liq_date_temp + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_cheque_liq_date + " LIQD DATE CHANGE TO " + m_Strrf_cheque_liq_date_temp + "<br>";

                }
                i++;
                m_change = m_change + i + ") CW CHANGE TO RF <br>";

                m_strQry = "update ndb_pdc_txn_master set " + m_condition + " pdc_chq_status='ACTIVE',pdc_chq_status_auth='UN-AUTHORIZED',"
                        + "pdc_req_financing='RF',"
                        + "pdc_chq_mod_by='" + m_str_user_id + "',"
                        + "pdc_chq_mod_date=now()"
                        + " where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";

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
                        + "'PDCTXN',"
                        + "'" + m_str_idndb_pdc_txn_master + "',"
                        + "'" + m_change + "',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                // getbuyer outsatndingbalance 
                // available outsatanding seller
                if (!m_StrOldrf_cheque_amu.equals(m_Strrf_cheque_amu)) {

                    double buyer_outsatnding = 0.00;
                    double buyer_new_outsatnding = 0.00;
                    m_strQry = "select shb_facty_det_os from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        buyer_outsatnding = Double.parseDouble(_rs.getString("shb_facty_det_os"));
                    }
                    buyer_new_outsatnding = buyer_outsatnding + Double.parseDouble(m_Strrf_cheque_amu) - Double.parseDouble(m_StrOldrf_cheque_amu);

                    // set buyer new outsatnding
                    m_strQry = "update ndb_seller_has_buyers set shb_facty_det_os='" + buyer_new_outsatnding + "'"
                            + " where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    // end
                }

                double buyer_outsatnding = 0.00;
                double buyer_new_outsatnding = 0.00;
                m_strQry = "select sl_has_byr_warehs_otstaning from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    buyer_outsatnding = Double.parseDouble(_rs.getString("sl_has_byr_warehs_otstaning"));
                }
                buyer_new_outsatnding = buyer_outsatnding - Double.parseDouble(m_StrOldrf_cheque_amu);

                // set buyer new outsatnding
                m_strQry = "update ndb_seller_has_buyers set sl_has_byr_warehs_otstaning='" + buyer_new_outsatnding + "'"
                        + " where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                buyer_outsatnding = 0.00;
                buyer_new_outsatnding = 0.00;
                m_strQry = "select shb_facty_det_os from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    buyer_outsatnding = Double.parseDouble(_rs.getString("shb_facty_det_os"));
                }
                buyer_new_outsatnding = buyer_outsatnding + Double.parseDouble(m_Strrf_cheque_amu);

                // set buyer new outsatnding
                m_strQry = "update ndb_seller_has_buyers set shb_facty_det_os='" + buyer_new_outsatnding + "'"
                        + " where idndb_seller_has_buyers='" + m_Stridndb_customer_define_buyer_id + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }
                rf_pdc_save_status = "1100";

                //.................................. gefu entries.......................................................
                log.info("Start of ACC Commision entries");

                String max_idndb_pdc_txn_master = m_str_idndb_pdc_txn_master;
                String account = "";
                String currency = "LKR";
                String date = "";
                double amount = 0.00;
                String narration = "CUSTID/" + idndb_customer_define_seller_idndb_customer_define + "/CW/COMMISION/BUYER/" + m_Stridndb_customer_define_buyer_id;
                String credit_debit = "";
                String profit_centre = "";
                String DAO = "";
                double c_amount = 0.00;
                double d_amount = 0.00;
                String system_date = _system_date;
                String gefu_rf_fixed_frequency = rf_fixed_frequency;
                String gefu_rec_finance_bulk_credit = rec_finance_bulk_credit;
                String[] gefu_date = _system_date.split("/");
                String gefu_day = gefu_date[0];
                String gefu_month = gefu_date[1];
                String gefu_year = gefu_date[2];
                date = gefu_year + gefu_month + gefu_day;

                // cutomer debit entry
                if (rec_finance_commision_crg.equals("TRANSACTION BASED")) {

                    if (!(rf_tran_base_falt_fee == 0.00)) {
                        amount = rf_tran_base_falt_fee;

                    } else {
                        amount = Double.parseDouble(m_Strrf_cheque_amu) / 100 * rf_tran_base_from_tran;

                    }

                    d_amount = amount;
                    account = rec_finance_curr_ac;

                    log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                    log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + max_idndb_pdc_txn_master + "',"
                            + "'" + account + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + amount + "',"
                            + "'" + narration + "',"
                            + "'D',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + c_amount + "',"
                            + "'" + d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'COMCHGCD',"
                            + "'NO',"
                            + "'" + gefu_rf_fixed_frequency + "')";

                    log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    d_amount = 0.00;
                    c_amount = amount;
                    account = NDBCommisionPLAcc;

                    log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGCD");

                    // ndb credit entry
                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + max_idndb_pdc_txn_master + "',"
                            + "'" + account + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + amount + "',"
                            + "'" + narration + "',"
                            + "'C',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + c_amount + "',"
                            + "'" + d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'COMCHGBC',"
                            + "'NO',"
                            + "'" + gefu_rf_fixed_frequency + "')";
                    log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ MY SQL Quiery :" + m_strQry);
                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                }

                log.info("End of ACC Commision entries");
                //..................................................end ...........................................
                // .................................................... RF facilityGEFU  Entries...............................
                String _account = "";
                double _amount = 0.00;
                String _narration = "";
                String _credit_debit = "";
                String _profit_centre = "";
                String _DAO = "";
                double _c_amount = 0.00;
                double _d_amount = 0.00;

                log.info("Start of RF Facility entries");

                _amount = m_discounting_amu_from_cheque_amu;
                _account = rec_finance_acc_num;
                _credit_debit = "D";
                _narration = "CUSTID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/BUYER/" + m_Stridndb_customer_define_buyer_id;
                _d_amount = m_discounting_amu_from_cheque_amu;

                log.info("ACC. ENTRY : Seller receviable finance account number debit & seller collection account credit");
                log.info("ACC. ENTRY : Seller receviable finance number debit/ debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :RFCDCD");
                m_strQry = "insert into gefu_file_generation ("
                        + "idndb_pdc_txn_master"
                        + ",account"
                        + ",currency"
                        + ",date"
                        + ",amount"
                        + ",narration"
                        + ",credit_debit"
                        + ",profit_centre"
                        + ",DAO"
                        + ",c_amount"
                        + ",d_amount"
                        + ",gefu_creation_status"
                        + ",status"
                        + ",creat_by"
                        + ",creat_date"
                        + ",mod_by"
                        + ",mod_date"
                        + ",system_date"
                        + ",gefu_type"
                        + ",bulk_credit"
                        + ",cw_fixed_frequency"
                        + ") values("
                        + "'" + max_idndb_pdc_txn_master + "',"
                        + "'" + _account + "',"
                        + "'" + currency + "',"
                        + "'" + date + "',"
                        + "'" + _amount + "',"
                        + "'" + _narration + "',"
                        + "'" + _credit_debit + "',"
                        + "'" + profit_centre + "',"
                        + "'" + DAO + "',"
                        + "'" + _c_amount + "',"
                        + "'" + _d_amount + "',"
                        + "'ACTIVE',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "NOW(),"
                        + "'" + m_str_user_id + "',"
                        + "NOW(),"
                        + "'" + system_date + "',"
                        + "'RFCDCD',"
                        + "'NO',"
                        + "'" + gefu_rf_fixed_frequency + "')";

                log.info("ACC. ENTRY : Seller receviable finance number debit/ MY SQL Quiery " + m_strQry);

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                _amount = m_discounting_amu_from_cheque_amu;
                _account = rec_finance_cr_dsc_proc_acc_num;
                _credit_debit = "C";
                _narration = "CUSTID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/BUYER/" + m_Stridndb_customer_define_buyer_id;
                _c_amount = m_discounting_amu_from_cheque_amu;
                _d_amount = 0.00;
                log.info("ACC. ENTRY : Seller collection account credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :RFCDBC");
                // ndb credit entry
                m_strQry = "insert into gefu_file_generation ("
                        + "idndb_pdc_txn_master"
                        + ",account"
                        + ",currency"
                        + ",date"
                        + ",amount"
                        + ",narration"
                        + ",credit_debit"
                        + ",profit_centre"
                        + ",DAO"
                        + ",c_amount"
                        + ",d_amount"
                        + ",gefu_creation_status"
                        + ",status"
                        + ",creat_by"
                        + ",creat_date"
                        + ",mod_by"
                        + ",mod_date"
                        + ",system_date"
                        + ",gefu_type"
                        + ",bulk_credit"
                        + ",cw_fixed_frequency"
                        + ") values("
                        + "'" + max_idndb_pdc_txn_master + "',"
                        + "'" + _account + "',"
                        + "'" + currency + "',"
                        + "'" + date + "',"
                        + "'" + _amount + "',"
                        + "'" + _narration + "',"
                        + "'" + _credit_debit + "',"
                        + "'" + profit_centre + "',"
                        + "'" + DAO + "',"
                        + "'" + _c_amount + "',"
                        + "'" + _d_amount + "',"
                        + "'ACTIVE',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "NOW(),"
                        + "'" + m_str_user_id + "',"
                        + "NOW(),"
                        + "'" + system_date + "',"
                        + "'RFCDBC',"
                        + "'" + gefu_rec_finance_bulk_credit + "',"
                        + "'" + gefu_rf_fixed_frequency + "')";

                log.info("ACC. ENTRY : Seller collection account credit/ MY SQL Quiery " + m_strQry);

                log.info("End of RF Facility entries");

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }
                //......................................................ens....................................
// ..................................................... margin entry .........................
                if (!(rec_finance_margin_ac.equals("") && rec_finance_margin.equals(""))) {
                    log.info("Start Of marging entry");
                    double margin_amu = Double.parseDouble(m_Strrf_cheque_amu) / 100 * Double.parseDouble(rec_finance_margin);

                    _amount = margin_amu;
                    _account = rec_finance_curr_ac;
                    _credit_debit = "D";
                    _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/MARGIN/RECFINCURRACC/SELLERDEBIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                    d_amount = margin_amu;
                    log.info("ACC. ENTRY : Seller current account number debit & seller marging account credit");
                    log.info("ACC. ENTRY : Sellercurrent account number debit / debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :RFMARCDCD");

                    // RF facility customer RFF ACC debit entry ...............................
                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + max_idndb_pdc_txn_master + "',"
                            + "'" + _account + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + _amount + "',"
                            + "'" + _narration + "',"
                            + "'" + _credit_debit + "',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + _c_amount + "',"
                            + "'" + _d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'RFMARCDCD',"
                            + "'NO',"
                            + "'" + gefu_rf_fixed_frequency + "')";
                    log.info("ACC. ENTRY : Seller current account number debit /YSQL QUIERY :" + m_strQry);
                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    _amount = margin_amu;
                    _account = rec_finance_margin_ac;
                    _credit_debit = "C";
                    _narration = "SELLERID/" + idndb_customer_define_seller_idndb_customer_define + "/RF/MARGIN/MARGINACC/SELLERCREDIT/BUYER/" + m_Stridndb_customer_define_buyer_id;
                    _c_amount = margin_amu;
                    _d_amount = 0.00;

                    log.info("ACC. ENTRY : Seller Marging account number credit / credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :RFMARCDCD");
                    // RF facility customer Collection Account Credit entry ...............................
                    m_strQry = "insert into gefu_file_generation ("
                            + "idndb_pdc_txn_master"
                            + ",account"
                            + ",currency"
                            + ",date"
                            + ",amount"
                            + ",narration"
                            + ",credit_debit"
                            + ",profit_centre"
                            + ",DAO"
                            + ",c_amount"
                            + ",d_amount"
                            + ",gefu_creation_status"
                            + ",status"
                            + ",creat_by"
                            + ",creat_date"
                            + ",mod_by"
                            + ",mod_date"
                            + ",system_date"
                            + ",gefu_type"
                            + ",bulk_credit"
                            + ",cw_fixed_frequency"
                            + ") values("
                            + "'" + max_idndb_pdc_txn_master + "',"
                            + "'" + _account + "',"
                            + "'" + currency + "',"
                            + "'" + date + "',"
                            + "'" + _amount + "',"
                            + "'" + _narration + "',"
                            + "'" + _credit_debit + "',"
                            + "'" + profit_centre + "',"
                            + "'" + DAO + "',"
                            + "'" + _c_amount + "',"
                            + "'" + _d_amount + "',"
                            + "'ACTIVE',"
                            + "'UN-AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'RFMARCDBC',"
                            + "'" + gefu_rec_finance_bulk_credit + "',"
                            + "'" + gefu_rf_fixed_frequency + "')";
                    log.info("ACC. ENTRY : Seller Marging account number credit / MY SQL QUIERY :" + m_strQry);

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    log.info("End of marging entries");

                }
                //......................................................ens....................................

                // end
                rf_pdc_save_status = "1100";

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
        return rf_pdc_save_status;
    }

    public boolean savePDCCWStatusUpdateData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_pdc_txn_master = "";
        String m_idndb_pdc_txn_master_bulk = "";
        String m_auth_status = "";
        String _system_date = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            //  m_str_idndb_holiday_marker = prm_obj.getString("idndb_holiday_marker");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            m_strQry = "select * from ndb_system_date";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                _system_date = _rs.getString("_system_date");

            }

            m_idndb_pdc_txn_master_bulk = prm_obj.getString("idndb_pdc_txn_master");
            m_auth_status = prm_obj.getString("auth_status");
            String[] parts = m_idndb_pdc_txn_master_bulk.split(",");

            for (int j = 0; j < parts.length; j++) {
                m_str_idndb_pdc_txn_master = parts[j];

                m_strQry = "update ndb_pdc_txn_master set pdc_chq_status_auth='" + m_auth_status + "',"
                        + "pdc_chq_mod_by='" + m_str_user_id + "',"
                        + "pdc_chq_mod_date=now()"
                        + " where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                m_strQry = "update gefu_file_generation set status='" + m_auth_status + "',"
                        + "mod_by='" + m_str_user_id + "',"
                        + "system_date='" + _system_date + "',"
                        + "mod_date=now()"
                        + " where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                m_strQry = "update ndb_change_log set status='" + m_auth_status + "',"
                        + "authby='" + m_str_user_id + "',"
                        + "auth_date=now()"
                        + " where ndb_attached_id='" + m_str_idndb_pdc_txn_master + "' and ndb_change_log_type='PDCTXN'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            success = true;
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
        return success;
    }

    public JSONArray getPDCCWtxnDataToFill(String prm_stridndb_pdc_txn_master) {
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

            m_chstrsql = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master='" + prm_stridndb_pdc_txn_master + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_pdc_txn_master", m_rs.getString("idndb_pdc_txn_master"));
                m_jsObj.put("idndb_customer_define_seller_id", m_rs.getString("idndb_customer_define_seller_id"));
                m_jsObj.put("idndb_customer_define_buyer_id", m_rs.getString("idndb_customer_define_buyer_id"));
                m_jsObj.put("idndb_bank_master_file", m_rs.getString("idndb_bank_master_file"));
                m_jsObj.put("idndb_branch_master_file", m_rs.getString("idndb_branch_master_file"));
                m_jsObj.put("pdc_chq_number", m_rs.getString("pdc_chq_number"));
                m_jsObj.put("pdc_value_date", m_rs.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_amu", m_rs.getString("pdc_chq_amu"));
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

    public JSONArray getPDCCWtxnDataToFillAllReadyExist(String idndb_customer_define_seller_id, String idndb_customer_define_buyer_id, String cw_chq_number, String cust_bank_code, String cust_branch_code) {
        JSONArray m_jsArr = new JSONArray();
        DecimalFormat df = new DecimalFormat("#,###.00");
        Statement m_stamt = null;
        ResultSet m_rs = null;
        String m_chstrsql = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            m_stamt = _currentCon.createStatement();
            String prm_stridndb_pdc_txn_master = "";
            String bank_code = "";
            m_chstrsql = "SELECT * from ndb_bank_master_file where idndb_bank_master_file='" + cust_bank_code + "'";
            _rs = m_stamt.executeQuery(m_chstrsql);
            if (_rs.next()) {
                bank_code = _rs.getString("bank_code");
            }
            String branch_id = "";
            m_chstrsql = "SELECT * from ndb_branch_master_file where idndb_branch_master_file='" + cust_branch_code + "'";
            _rs = m_stamt.executeQuery(m_chstrsql);
            if (_rs.next()) {
                branch_id = _rs.getString("branch_id");
            }

            String m_uniq_id = idndb_customer_define_seller_id + idndb_customer_define_buyer_id + cw_chq_number + bank_code + branch_id;
//            m_chstrsql = "SELECT CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code) as txn_master_unq_id,idndb_pdc_txn_master FROM ndb_pdc_txn_master where pdc_chq_status in ('ACTIVE','ERLYLIQUDED') and pdc_chq_status_auth in ('AUTHORIZED','UN-AUTHORIZED')";
//            Lequdated cheques shoud be able to input again into the RMS Systeme 
            m_chstrsql = "SELECT CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code) as txn_master_unq_id,idndb_pdc_txn_master FROM ndb_pdc_txn_master where CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code)='" + m_uniq_id + "' and pdc_chq_status in ('ACTIVE') and pdc_chq_status_auth in ('AUTHORIZED','UN-AUTHORIZED')";
            _rs = m_stamt.executeQuery(m_chstrsql);
            if (_rs.next()) {

                prm_stridndb_pdc_txn_master = _rs.getString("idndb_pdc_txn_master");

            }
            m_jsObj = new JSONObject();
            if (!prm_stridndb_pdc_txn_master.equals("")) {
                m_chstrsql = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master='" + prm_stridndb_pdc_txn_master + "'";
                m_rs = m_stamt.executeQuery(m_chstrsql);

                if (m_rs.next()) {

                    m_jsObj = new JSONObject();
                    m_jsObj.put("idndb_pdc_txn_master", prm_stridndb_pdc_txn_master);
                    m_jsObj.put("idndb_customer_define_seller_id", m_rs.getString("idndb_customer_define_seller_id"));
                    m_jsObj.put("idndb_customer_define_buyer_id", m_rs.getString("idndb_customer_define_buyer_id"));
                    m_jsObj.put("idndb_bank_master_file", m_rs.getString("idndb_bank_master_file"));
                    m_jsObj.put("idndb_branch_master_file", m_rs.getString("idndb_branch_master_file"));
                    m_jsObj.put("pdc_chq_number", m_rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_value_date", m_rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_booking_date", m_rs.getString("pdc_booking_date"));
                    m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(m_rs.getString("pdc_chq_amu"))));
                    m_jsObj.put("pdc_txn_mod", m_rs.getString("pdc_txn_mod"));
                    m_jsObj.put("pdc_req_financing", m_rs.getString("pdc_req_financing"));
                    m_jsObj.put("pdc_chq_status_auth", m_rs.getString("pdc_chq_status_auth"));
                    m_jsObj.put("pdc_chq_status", m_rs.getString("pdc_chq_status"));
                    m_jsObj.put("chq_wh_dr_to_cr_spe_narr", m_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));

                }
            }
            m_jsObj.put("idndb_pdc_txn_master", prm_stridndb_pdc_txn_master);
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

    public JSONArray getPDCCWtoRFAvailability(String idndb_customer_define_seller_id, String idndb_customer_define_buyer_id, String cw_chq_number, String cust_bank_code, String cust_branch_code) {
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
            String prm_stridndb_pdc_txn_master = "";
            String bank_code = "";
            m_chstrsql = "SELECT * from ndb_bank_master_file where idndb_bank_master_file='" + cust_bank_code + "'";
            _rs = m_stamt.executeQuery(m_chstrsql);
            if (_rs.next()) {
                bank_code = _rs.getString("bank_code");
            }
            String branch_id = "";
            m_chstrsql = "SELECT * from ndb_branch_master_file where idndb_branch_master_file='" + cust_branch_code + "'";
            _rs = m_stamt.executeQuery(m_chstrsql);
            if (_rs.next()) {
                branch_id = _rs.getString("branch_id");
            }

            String m_uniq_id = idndb_customer_define_seller_id + idndb_customer_define_buyer_id + cw_chq_number + bank_code + branch_id;
            m_chstrsql = "SELECT CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code) as txn_master_unq_id,idndb_pdc_txn_master FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE'";
            _rs = m_stamt.executeQuery(m_chstrsql);
            while (_rs.next()) {
                if (m_uniq_id.equals(_rs.getString("txn_master_unq_id"))) {
                    prm_stridndb_pdc_txn_master = _rs.getString("idndb_pdc_txn_master");

                }

            }

            m_chstrsql = "SELECT * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_customer_define_buyer_id + "'";
            _rs = m_stamt.executeQuery(m_chstrsql);
            String idndb_customer_define_buyer_for_check = "";
            while (_rs.next()) {
                idndb_customer_define_buyer_for_check = _rs.getString("idndb_customer_define_buyer");
            }

            m_chstrsql = "SELECT\n"
                    + "     ndb_seller_has_buyers.`idndb_seller_has_buyers` AS ndb_seller_has_buyers_idndb_seller_has_buyers,\n"
                    + "     ndb_seller_has_buyers.`idndb_customer_define_seller` AS ndb_seller_has_buyers_idndb_customer_define_seller,\n"
                    + "     ndb_seller_has_buyers.`idndb_customer_define_buyer` AS ndb_seller_has_buyers_idndb_customer_define_buyer,\n"
                    + "     ndb_seller_has_buyers.`sl_has_byr_warehs_tenor` AS ndb_seller_has_buyers_sl_has_byr_warehs_tenor,\n"
                    + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                    + "     ndb_cust_prod_map.`idndb_customer_define` AS ndb_cust_prod_map_idndb_customer_define,\n"
                    + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
                    + "     ndb_customer_define.`cust_id` AS ndb_customer_define_cust_id,\n"
                    + "     ndb_customer_define.`cust_name` AS ndb_customer_define_cust_name,\n"
                    + "     ndb_customer_define.`cust_short_name` AS ndb_customer_define_cust_short_name\n"
                    + "FROM\n"
                    + "     `ndb_cust_prod_map` ndb_cust_prod_map INNER JOIN `ndb_seller_has_buyers` ndb_seller_has_buyers ON ndb_cust_prod_map.`idndb_cust_prod_map` = ndb_seller_has_buyers.`idndb_customer_define_buyer`\n"
                    + "     INNER JOIN `ndb_customer_define` ndb_customer_define ON ndb_cust_prod_map.`idndb_customer_define` = ndb_customer_define.`idndb_customer_define` where  ndb_seller_has_buyers.`idndb_seller_has_buyers`='" + idndb_customer_define_buyer_id + "'";
            _rs = m_stamt.executeQuery(m_chstrsql);
            String cust_name = "";
            String cust_id = "";
            String cw_tenor = "";
            while (_rs.next()) {
                cust_name = _rs.getString("ndb_customer_define_cust_name");
                cust_id = _rs.getString("ndb_customer_define_cust_id");
                cw_tenor = _rs.getString("ndb_seller_has_buyers_sl_has_byr_warehs_tenor");
            }

            m_chstrsql = "SELECT * from ndb_seller_has_buyers where idndb_customer_define_buyer='" + idndb_customer_define_buyer_for_check + "' and idndb_customer_define_seller='" + idndb_customer_define_seller_id + "' and sl_has_byr_status='ACTIVE' and sl_has_byr_auth='AUTHORIZED' and sl_has_byr_prorm_type='RF'";
            _rs = m_stamt.executeQuery(m_chstrsql);
            String cw_to_rf_availability = "false";
            String idndb_customer_define_buyer_for_txn = "";
            if (_rs.next()) {
                idndb_customer_define_buyer_for_txn = _rs.getString("idndb_seller_has_buyers");
            }

            m_chstrsql = "SELECT * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller_id + "' and prod_relationship_status='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_res_fin='ACTIVE'";
            _rs = m_stamt.executeQuery(m_chstrsql);
            if (_rs.next()) {
                cw_to_rf_availability = "true";
            }

            m_jsObj = new JSONObject();

            if (!prm_stridndb_pdc_txn_master.equals("")) {
                m_chstrsql = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master='" + prm_stridndb_pdc_txn_master + "' and pdc_chq_status_auth='AUTHORIZED'";
                m_rs = m_stamt.executeQuery(m_chstrsql);

                if (m_rs.next()) {

                    m_jsObj = new JSONObject();
                    m_jsObj.put("idndb_pdc_txn_master", prm_stridndb_pdc_txn_master);
                    m_jsObj.put("idndb_customer_define_seller_id", idndb_customer_define_seller_id);
                    m_jsObj.put("idndb_customer_define_buyer_id", idndb_customer_define_buyer_for_txn);
                    m_jsObj.put("idndb_bank_master_file", m_rs.getString("idndb_bank_master_file"));
                    m_jsObj.put("idndb_branch_master_file", m_rs.getString("idndb_branch_master_file"));
                    m_jsObj.put("pdc_chq_number", m_rs.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_value_date", m_rs.getString("pdc_value_date"));
                    m_jsObj.put("pdc_chq_amu", m_rs.getString("pdc_chq_amu"));
                    m_jsObj.put("cw_tenor", cw_tenor);
                    m_jsObj.put("pdc_req_financing", m_rs.getString("pdc_req_financing"));
                    m_jsObj.put("cw_to_rf_availability", cw_to_rf_availability);
                    m_jsObj.put("cust_name", cust_name);
                    m_jsObj.put("cust_id", cust_id);

                }
            }
            m_jsObj.put("idndb_pdc_txn_master", prm_stridndb_pdc_txn_master);
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

    public JSONArray getPDCRFtxnDataToFill(String prm_stridndb_pdc_txn_master) {
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

            m_chstrsql = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master='" + prm_stridndb_pdc_txn_master + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();

                m_jsObj.put("idndb_pdc_txn_master", m_rs.getString("idndb_pdc_txn_master"));
                m_jsObj.put("idndb_customer_define_seller_id", m_rs.getString("idndb_customer_define_seller_id"));
                m_jsObj.put("idndb_customer_define_buyer_id", m_rs.getString("idndb_customer_define_buyer_id"));
                m_jsObj.put("idndb_bank_master_file", m_rs.getString("idndb_bank_master_file"));
                m_jsObj.put("idndb_branch_master_file", m_rs.getString("idndb_branch_master_file"));
                m_jsObj.put("pdc_chq_number", m_rs.getString("pdc_chq_number"));
                m_jsObj.put("pdc_value_date", m_rs.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_amu", m_rs.getString("pdc_chq_amu"));
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

    public JSONArray getActiveChequeDetails(JSONObject m_js_parm_obj) {
        JSONArray m_jsArr = new JSONArray();
        JSONObject m_jsObj;

        ResultSet m_rs = null;
        String m_strQry = "";

        int i = 0;
        try {
            DecimalFormat df = new DecimalFormat("#,###.00");
            String pdc_product = m_js_parm_obj.getString("pdc_product");
            String pdc_chq_number = m_js_parm_obj.getString("pdc_chq_number");
            String cust_bank_code = m_js_parm_obj.getString("cust_bank_code");
            String cust_branch_code = m_js_parm_obj.getString("cust_branch_code");

            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            m_strQry = "SELECT \n"
                    + "nptm_txn_master.* ,\n"
                    + "nptm_txn_master.idndb_pdc_txn_master,\n"
                    + "nptm_txn_master.pdc_chq_amu,\n"
                    + "nptm_txn_master.pdc_chq_number,\n"
                    + "nptm_txn_master.pdc_chq_status,\n"
                    + "nptm_txn_master.pdc_chq_status_auth,\n"
                    + "nptm_txn_master.pdc_booking_date,\n"
                    + "nshb_seller_has_buyers.shb_facty_det_tenor,\n"
                    + "nshb_seller_has_buyers.sl_has_byr_warehs_tenor,\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                    + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                    + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                    + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                    + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                    + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                    + "ngmmf_geo_market.geo_market_desc,\n"
                    + "nbmf_branch_master.branch_name\n"
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
                    + "ndb_customer_define ncd_cust_define_buyer on \n"
                    + "ncpm_cust_prod_map_buyer.idndb_customer_define =ncd_cust_define_buyer.idndb_customer_define left join\n"
                    + "ndb_geo_market_master_file ngmmf_geo_market on\n"
                    + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market.idndb_geo_market_master_file left join\n"
                    + "ndb_branch_master_file nbmf_branch_master on\n"
                    + "ncd_cust_define_seller.idndb_branch_master_file = nbmf_branch_master.idndb_branch_master_file where\n"
                    + "nptm_txn_master.pdc_chq_status in ('ACTIVE') and nptm_txn_master.pdc_chq_status_auth in ('AUTHORIZED','UN-AUTHORIZED') and\n"
                    + "nptm_txn_master.pdc_req_financing=? and\n"
                    + "nptm_txn_master.pdc_chq_number=? and\n"
                    + "nptm_txn_master.pdc_bank_code=? and\n"
                    + "nptm_txn_master.pdc_branch_code=?";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _prepStmnt.setString(1, pdc_product);
            _prepStmnt.setString(2, pdc_chq_number);
            _prepStmnt.setString(3, cust_bank_code);
            _prepStmnt.setString(4, cust_branch_code);
            _rs = _prepStmnt.executeQuery();

            log.info(_prepStmnt);

            while (_rs.next()) {

                m_jsObj = new JSONObject();

                m_jsObj.put("idndb_pdc_txn_master", _rs.getString("idndb_pdc_txn_master"));
                m_jsObj.put("idndb_seller_has_buyers", _rs.getString("idndb_seller_has_buyers"));
                m_jsObj.put("idndb_customer_define_seller_id", _rs.getString("idndb_customer_define_seller_id"));
                m_jsObj.put("idndb_customer_define_buyer_id", _rs.getString("idndb_customer_define_buyer_id"));

                m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));

                m_jsObj.put("shb_facty_det_tenor", _rs.getString("shb_facty_det_tenor"));
                m_jsObj.put("sl_has_byr_warehs_tenor", _rs.getString("sl_has_byr_warehs_tenor"));

                m_jsObj.put("geo_market_desc", _rs.getString("geo_market_desc"));
                m_jsObj.put("branch_name", _rs.getString("branch_name"));

                m_jsObj.put("idndb_bank_master_file", _rs.getString("idndb_bank_master_file"));
                m_jsObj.put("idndb_branch_master_file", _rs.getString("idndb_branch_master_file"));
                m_jsObj.put("pdc_chq_status", _rs.getString("pdc_chq_status"));
                m_jsObj.put("pdc_chq_status_auth", _rs.getString("pdc_chq_status_auth"));
                m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
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
                if (_prepStmnt != null) {
                    _prepStmnt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getChequeDetails(JSONObject m_js_parm_obj) {
        JSONArray m_jsArr = new JSONArray();
        JSONObject m_jsObj;

        ResultSet m_rs = null;
        String m_strQry = "";

        int i = 0;
        try {
            DecimalFormat df = new DecimalFormat("#,###.00");
            String idndb_pdc_txn_master = m_js_parm_obj.getString("idndb_pdc_txn_master");

            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            m_strQry = "SELECT \n"
                    + "nptm_txn_master.* ,\n"
                    + "nptm_txn_master.idndb_pdc_txn_master,\n"
                    + "nptm_txn_master.pdc_chq_amu,\n"
                    + "nptm_txn_master.pdc_chq_number,\n"
                    + "nptm_txn_master.pdc_chq_status,\n"
                    + "nptm_txn_master.pdc_chq_status_auth,\n"
                    + "nptm_txn_master.pdc_booking_date,\n"
                    + "nshb_seller_has_buyers.idndb_seller_has_buyers,\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_seller,\n"
                    + "nshb_seller_has_buyers.idndb_customer_define_buyer,\n"
                    + "nshb_seller_has_buyers.shb_facty_det_tenor,\n"
                    + "nshb_seller_has_buyers.sl_has_byr_warehs_tenor,\n"
                    + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                    + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                    + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                    + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                    + "ngmmf_geo_market.geo_market_desc,\n"
                    + "nbmf_branch_master.branch_name\n"
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
                    + "ncpm_cust_prod_map_buyer.idndb_customer_define =ncd_cust_define_buyer.idndb_customer_define left join\n"
                    + "ndb_geo_market_master_file ngmmf_geo_market on\n"
                    + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market.idndb_geo_market_master_file left join\n"
                    + "ndb_branch_master_file nbmf_branch_master on\n"
                    + "ncd_cust_define_seller.idndb_branch_master_file = nbmf_branch_master.idndb_branch_master_file where\n"
                    + "nptm_txn_master.pdc_chq_status in ('ACTIVE') and nptm_txn_master.pdc_chq_status_auth in ('AUTHORIZED','UN-AUTHORIZED') and \n"
                    + "nptm_txn_master.idndb_pdc_txn_master=?";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _prepStmnt.setString(1, idndb_pdc_txn_master);
            _rs = _prepStmnt.executeQuery();

            log.info(_prepStmnt);

            if (_rs.next()) {

                m_jsObj = new JSONObject();

                m_jsObj.put("idndb_pdc_txn_master", _rs.getString("idndb_pdc_txn_master"));
                m_jsObj.put("idndb_seller_has_buyers", _rs.getString("idndb_seller_has_buyers"));
                m_jsObj.put("idndb_customer_define_seller_id", _rs.getString("idndb_customer_define_seller_id"));
                m_jsObj.put("idndb_customer_define_buyer_id", _rs.getString("idndb_customer_define_buyer_id"));

                m_jsObj.put("seller_cust_id", _rs.getString("seller_cust_id"));
                m_jsObj.put("seller_cust_name", _rs.getString("seller_cust_name"));
                m_jsObj.put("buyer_cust_id", _rs.getString("buyer_cust_id"));
                m_jsObj.put("buyer_cust_name", _rs.getString("buyer_cust_name"));

                m_jsObj.put("shb_facty_det_tenor", _rs.getString("shb_facty_det_tenor"));
                m_jsObj.put("sl_has_byr_warehs_tenor", _rs.getString("sl_has_byr_warehs_tenor"));

                m_jsObj.put("geo_market_desc", _rs.getString("geo_market_desc"));
                m_jsObj.put("branch_name", _rs.getString("branch_name"));

                m_jsObj.put("idndb_bank_master_file", _rs.getString("idndb_bank_master_file"));
                m_jsObj.put("idndb_branch_master_file", _rs.getString("idndb_branch_master_file"));
                m_jsObj.put("pdc_chq_status", _rs.getString("pdc_chq_status"));
                m_jsObj.put("pdc_chq_status_auth", _rs.getString("pdc_chq_status_auth"));
                m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                m_jsObj.put("pdc_chq_wh_dr_to_cr_spe_narr", _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr"));
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
                if (_prepStmnt != null) {
                    _prepStmnt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getSellerBranchAcOfficer(JSONObject m_js_parm_obj) {
        JSONArray m_jsArr = new JSONArray();
        JSONObject m_jsObj;

        ResultSet m_rs = null;
        String m_strQry = "";

        int i = 0;
        try {
            DecimalFormat df = new DecimalFormat("#,###.00");
            String idndb_cust_prod_map = m_js_parm_obj.getString("idndb_cust_prod_map");

            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            m_strQry = "SELECT \n"
                    + "ncpm_cust_prod_map_seller.idndb_cust_prod_map,\n"
                    + "ncd_cust_define_seller.cust_id,\n"
                    + "ncd_cust_define_seller.cust_name,\n"
                    + "ngmmf_geo_market.geo_market_desc,\n"
                    + "nbmf_branch_master.branch_name\n"
                    + "FROM \n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_seller inner join\n"
                    + "ndb_customer_define ncd_cust_define_seller on\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define = ncd_cust_define_seller.idndb_customer_define left join\n"
                    + "ndb_geo_market_master_file ngmmf_geo_market on\n"
                    + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market.idndb_geo_market_master_file left join\n"
                    + "ndb_branch_master_file nbmf_branch_master on\n"
                    + "ncd_cust_define_seller.idndb_branch_master_file = nbmf_branch_master.idndb_branch_master_file where\n"
                    + "ncpm_cust_prod_map_seller.idndb_cust_prod_map =?";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _prepStmnt.setString(1, idndb_cust_prod_map);
            _rs = _prepStmnt.executeQuery();

            log.info(_prepStmnt);

            if (_rs.next()) {

                m_jsObj = new JSONObject();
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
                if (m_rs != null) {
                    m_rs.close();
                }
                if (_prepStmnt != null) {
                    _prepStmnt.close();
                }
            } catch (Exception e) {
            }
        }
        return m_jsArr;
    }

    public JSONArray getPDCRFCOVtxnDataToFill(String prm_stridndb_pdc_txn_master) {
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

            m_chstrsql = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master='" + prm_stridndb_pdc_txn_master + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            String m_idndb_seller_has_buyers = "";
            if (m_rs.next()) {

                m_idndb_seller_has_buyers = m_rs.getString("idndb_customer_define_buyer_id");

            }
            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + m_idndb_seller_has_buyers + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            String m_idndb_customer_define_buyer = "";
            String m_idndb_customer_define_seller = "";
            if (m_rs.next()) {
                m_idndb_customer_define_buyer = m_rs.getString("idndb_customer_define_buyer");
                m_idndb_customer_define_seller = m_rs.getString("idndb_customer_define_seller");
            }

            m_chstrsql = "select * from ndb_seller_has_buyers where idndb_customer_define_buyer='" + m_idndb_customer_define_buyer + "' and idndb_customer_define_seller='" + m_idndb_customer_define_seller + "' and sl_has_byr_prorm_type='RF'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            String idndb_customer_define_buyer_id = "";
            if (m_rs.next()) {
                idndb_customer_define_buyer_id = m_rs.getString("idndb_seller_has_buyers");
            }

            m_chstrsql = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master='" + prm_stridndb_pdc_txn_master + "'";
            m_rs = m_stamt.executeQuery(m_chstrsql);

            if (m_rs.next()) {

                m_jsObj = new JSONObject();

                m_jsObj.put("idndb_pdc_txn_master", m_rs.getString("idndb_pdc_txn_master"));
                m_jsObj.put("idndb_customer_define_seller_id", m_rs.getString("idndb_customer_define_seller_id"));
                m_jsObj.put("idndb_customer_define_buyer_id", idndb_customer_define_buyer_id);
                m_jsObj.put("idndb_bank_master_file", m_rs.getString("idndb_bank_master_file"));
                m_jsObj.put("idndb_branch_master_file", m_rs.getString("idndb_branch_master_file"));
                m_jsObj.put("pdc_chq_status", _rs.getString("pdc_chq_status"));
                m_jsObj.put("pdc_chq_status_auth", _rs.getString("pdc_chq_status_auth"));
                m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                m_jsObj.put("pdc_booking_date", _rs.getString("pdc_booking_date"));
                m_jsObj.put("pdc_chq_amu", m_rs.getString("pdc_chq_amu"));
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

    public JSONArray getTodayChequeSending(String booked_date, String idndb_customer_define_seller_id, String PDCBulkUploadFileRFData, String PDCBulkUploadFileCWData) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        int count = 0;
        try {

            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            String modi_quier = "";
            if (!idndb_customer_define_seller_id.equals("all")) {
                modi_quier = "and nptm_txn_master.idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
            }
            String pdc_req_financing = "NA";
            if (PDCBulkUploadFileRFData.equals("ACTIVE")) {
                pdc_req_financing = "RF";

            }
            if (PDCBulkUploadFileCWData.equals("ACTIVE")) {
                pdc_req_financing = "CW";

            }
            m_strQry = "SELECT \n"
                    + "nptm_txn_master.* ,\n"
                    + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                    + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                    + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                    + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                    + "nbmf_branch_master.branch_name,\n"
                    + "ngmmf_geo_market.geo_market_desc\n"
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
                    + "nptm_txn_master.pdc_req_financing='" + pdc_req_financing + "' and\n"
                    + "nptm_txn_master.pdc_chq_status_auth='AUTHORIZED' and\n"
                    + "nptm_txn_master.pdc_chq_status ='ACTIVE' and\n"
                    + "nptm_txn_master.pdc_value_date='" + booked_date + "' and\n"
                    + "nptm_txn_master.pdc_req_financing='" + pdc_req_financing + "'" + modi_quier;

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                count++;
                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_pdc_txn_master", _rs.getString("idndb_pdc_txn_master"));
                m_jsObj.put("pdc_relationship", _rs.getString("branch_name") + "|" + _rs.getString("geo_market_desc"));
               
                m_jsObj.put("seller", _rs.getString("seller_cust_id") + "|" + _rs.getString("seller_cust_name"));
                m_jsObj.put("buyer", _rs.getString("buyer_cust_id") + "|" + _rs.getString("buyer_cust_name"));

                m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                m_jsObj.put("count", count);
                m_jsObj.put("pdc_req_financing", _rs.getString("pdc_req_financing"));
                m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
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

    public JSONArray getTodayChequeSendingAll(String booked_date) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";

        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        int count = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            m_strQry = "SELECT \n"
                    + "nptm_txn_master.* ,\n"
                    + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                    + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                    + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                    + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                    + "nbmf_branch_master.branch_name,\n"
                    + "ngmmf_geo_market.geo_market_desc\n"
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
                    + "nptm_txn_master.pdc_chq_status_auth='AUTHORIZED' and\n"
                    + "nptm_txn_master.pdc_chq_status ='ACTIVE' and\n"
                    + "nptm_txn_master.pdc_value_date='" + booked_date + "'";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {
                count++;
                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_pdc_txn_master", _rs.getString("idndb_pdc_txn_master"));
                m_jsObj.put("pdc_relationship", _rs.getString("branch_name") + "|" + _rs.getString("geo_market_desc"));
               
                m_jsObj.put("seller", _rs.getString("seller_cust_id") + "|" + _rs.getString("seller_cust_name"));
                m_jsObj.put("buyer", _rs.getString("buyer_cust_id") + "|" + _rs.getString("buyer_cust_name"));

                m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                m_jsObj.put("count", count);
                m_jsObj.put("pdc_req_financing", _rs.getString("pdc_req_financing"));
                m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
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

    public JSONArray getTodayBookedChequeSending(String booked_date, String idndb_customer_define_seller_id, String PDCBulkUploadFileRFData, String PDCBulkUploadFileCWData) {
        JSONArray m_jsArr = new JSONArray();
        String m_strQry = "";

        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONObject m_jsObj;
        int i = 0;
        int rec_count = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            String modi_quier = "";
            if (!idndb_customer_define_seller_id.equals("all")) {
                modi_quier = "and nptm_txn_master.idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
            }
            String pdc_req_financing = "NA";
            if (PDCBulkUploadFileRFData.equals("ACTIVE")) {
                pdc_req_financing = "RF";

            }
            if (PDCBulkUploadFileCWData.equals("ACTIVE")) {
                pdc_req_financing = "CW";

            }
            m_strQry = "SELECT \n"
                    + "nptm_txn_master.* ,\n"
                    + "ncd_cust_define_seller.cust_id as seller_cust_id,\n"
                    + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                    + "ncd_cust_define_buyer.cust_id as buyer_cust_id,\n"
                    + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                    + "nbmf_branch_master.branch_name,\n"
                    + "ngmmf_geo_market.geo_market_desc\n"
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
                    + "nptm_txn_master.pdc_req_financing='"+pdc_req_financing+"' and\n"
                    + "nptm_txn_master.pdc_chq_status_auth='AUTHORIZED' and\n"
                    + "nptm_txn_master.pdc_chq_status ='ACTIVE' and\n"
                    + "nptm_txn_master.pdc_booking_date='" + booked_date + "' and\n"
                    + "nptm_txn_master.pdc_req_financing='" + pdc_req_financing + "'" + modi_quier;

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            while (_rs.next()) {

                rec_count++;
                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_pdc_txn_master", _rs.getString("idndb_pdc_txn_master"));
                m_jsObj.put("pdc_relationship", _rs.getString("branch_name") + "|" + _rs.getString("geo_market_desc"));
                m_jsObj.put("seller", _rs.getString("seller_cust_id") + "|" + _rs.getString("seller_cust_name"));
                m_jsObj.put("buyer", _rs.getString("buyer_cust_id") + "|" + _rs.getString("buyer_cust_name"));

                m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                m_jsObj.put("rec_count", rec_count);
                m_jsObj.put("pdc_req_financing", _rs.getString("pdc_req_financing"));
                m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
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

    public JSONArray getSellerBuyerKeyData(String idndb_customer_define_seller_id, String PDCBulkUploadFileRFData, String PDCBulkUploadFileCWData) {
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
            String modi_quier = "";
            if (!idndb_customer_define_seller_id.equals("all")) {
                modi_quier = "and idndb_customer_define_seller='" + idndb_customer_define_seller_id + "'";
            }
            if (PDCBulkUploadFileRFData.equals("ACTIVE")) {
                m_strQry = "SELECT * FROM ndb_seller_has_buyers where sl_has_byr_auth ='AUTHORIZED' and sl_has_byr_status='ACTIVE' and sl_has_byr_prorm_type='RF'" + modi_quier;

            }
            if (PDCBulkUploadFileCWData.equals("ACTIVE")) {
                m_strQry = "SELECT * FROM ndb_seller_has_buyers where sl_has_byr_auth ='AUTHORIZED' and sl_has_byr_status='ACTIVE' and sl_has_byr_prorm_type='CW'" + modi_quier;

            }

            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                m_jsObj = new JSONObject();

                m_jsObj.put("idndb_seller_has_buyers", m_rs1.getString("idndb_seller_has_buyers"));

                m_strQry2 = "SELECT * FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_jsObj.put("seller_id", m_rs3.getString("cust_id"));
                        m_jsObj.put("seller_name", m_rs3.getString("cust_name"));

                    }

                }

                String buyer_idcust_prod_map = m_rs1.getString("idndb_customer_define_buyer");

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + buyer_idcust_prod_map + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name,cust_status"
                            + " FROM ndb_customer_define where cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    //+ " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_jsObj.put("buyer_id", m_rs3.getString("cust_id"));
                        m_jsObj.put("buyer_name", m_rs3.getString("cust_name"));
                        m_jsObj.put("buyer_status", m_rs3.getString("cust_status"));
                    }

                }

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

    public JSONArray getSellerBuyerStatusData(String idndb_customer_define_seller_id, String PDCBulkUploadFileRFData, String PDCBulkUploadFileCWData) {
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
            String modi_quier = "";
            String relationship_mode = "";
            if (!idndb_customer_define_seller_id.equals("all")) {
                modi_quier = "and nsbrs.idndb_customer_define_seller='" + idndb_customer_define_seller_id + "'";
            }
            if (PDCBulkUploadFileRFData.equals("ACTIVE")) {
                relationship_mode = "RF";
            }
            if (PDCBulkUploadFileCWData.equals("ACTIVE")) {
                relationship_mode = "CW";
            }
            m_strQry = "SELECT \n"
                    + "nsbrs.idndb_seller_has_buyers,\n"
                    + "nsbrs.idndb_customer_define_seller,\n"
                    + "nsbrs.idndb_customer_define_buyer,\n"
                    + "nsbrs.sl_has_byr_prorm_type,\n"
                    + "nsbrs.sl_has_byr_status as customer_relship_sellerBuyer_status,\n"
                    + "cpms.prod_relationship_status as customer_prod_map_seller_status,\n"
                    + "cpmb.prod_relationship_status as customer_prod_map_buyer_status,\n"
                    + "cds.cust_status as customer_difine_seller_status,\n"
                    + "cdb.cust_status as customer_difine_buyer_status,\n"
                    + "cds.cust_id as customer_difine_seller_id,\n"
                    + "cdb.cust_id as customer_difine_buyer_id,\n"
                    + "cds.cust_name as customer_difine_seller_name,\n"
                    + "cdb.cust_name as customer_difine_buyer_name,\n"
                    + "bmc.branch_name as branch_name_seller,\n"
                    + "bnkmc.bank_name as bank_name_seller\n"
                    + "\n"
                    + "FROM ndb_seller_has_buyers nsbrs,\n"
                    + "ndb_cust_prod_map cpms,\n"
                    + "ndb_cust_prod_map cpmb,\n"
                    + "ndb_customer_define cds,\n"
                    + "ndb_customer_define cdb,\n"
                    + "ndb_branch_master_file bmc,\n"
                    + "ndb_bank_master_file bnkmc\n"
                    + "\n"
                    + "where \n"
                    + "nsbrs.sl_has_byr_auth ='AUTHORIZED' and \n"
                    + "nsbrs.sl_has_byr_status='ACTIVE' and \n"
                    + "cpms.idndb_cust_prod_map = nsbrs.idndb_customer_define_seller and \n"
                    + "cpmb.idndb_cust_prod_map = nsbrs.idndb_customer_define_buyer and \n"
                    + "cds.idndb_customer_define = cpms.idndb_customer_define and\n"
                    + "cdb.idndb_customer_define = cpmb.idndb_customer_define and\n"
                    + "cds.idndb_branch_master_file =  bmc.idndb_branch_master_file and\n"
                    + "cds.idndb_bank_master_file =  bnkmc.idndb_bank_master_file and\n"
                    + "nsbrs.sl_has_byr_prorm_type='" + relationship_mode + "'" + modi_quier;
//            System.out.println(m_strQry);
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                m_jsObj = new JSONObject();

                m_jsObj.put("customer_relship_sellerBuyer_status", m_rs1.getString("customer_relship_sellerBuyer_status"));
                m_jsObj.put("customer_prod_map_seller_status", m_rs1.getString("customer_prod_map_seller_status"));
                m_jsObj.put("customer_prod_map_buyer_status", m_rs1.getString("customer_prod_map_buyer_status"));
                m_jsObj.put("customer_difine_seller_status", m_rs1.getString("customer_difine_seller_status"));
                m_jsObj.put("customer_difine_buyer_status", m_rs1.getString("customer_difine_buyer_status"));
                m_jsObj.put("customer_difine_seller_id", m_rs1.getString("customer_difine_seller_id"));
                m_jsObj.put("customer_difine_buyer_id", m_rs1.getString("customer_difine_buyer_id"));
                m_jsObj.put("customer_difine_seller_name", m_rs1.getString("customer_difine_seller_name"));
                m_jsObj.put("customer_difine_buyer_name", m_rs1.getString("customer_difine_buyer_name"));
                m_jsObj.put("bank_name_seller", m_rs1.getString("bank_name_seller"));
                m_jsObj.put("branch_name_seller", m_rs1.getString("branch_name_seller"));

                m_jsArr.put(i, m_jsObj);
                i++;
            }

        } catch (Exception e) {
//            e.printStackTrace();
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

    public JSONArray getTodayGEFUREPORT(String booked_date) {
        JSONArray m_jsArr = new JSONArray();
        String m_strData = "";
        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        String m_strQry4 = "";
        ResultSet m_rs1 = null;
        ResultSet m_rs2 = null;
        ResultSet m_rs3 = null;
        ResultSet m_rs4 = null;
        Statement stmt4 = null;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        JSONObject m_jsObj;
        int i = 0;
        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            stmt4 = _currentCon.createStatement();

            String[] sys_date_mode = booked_date.split("/");
            String mod_day = sys_date_mode[0];
            String mod_month = sys_date_mode[1];
            String mod_year = sys_date_mode[2];
            String _mod_system_date = mod_year + mod_month + mod_day;
            DecimalFormat df = new DecimalFormat("#,###.00");

            String m_total_amount = "0.00";
            String m_total_record_count = "0";
            String m_total_credit_amount = "0.00";
            String m_total_credit_record_count = "0";
            String m_total_debit_amount = "0.00";
            String m_total_debit_record_count = "0";

            m_strQry4 = "SELECT sum(amount) as total_amount FROM gefu_file_generation_hst where gefu_creation_status='PROCESSED' and status='AUTHORIZED' and date='" + _mod_system_date + "'";
            m_rs4 = stmt4.executeQuery(m_strQry4);
            if (m_rs4.next()) {
                if (!(m_rs4.getString("total_amount") == null)) {
                    m_total_amount = m_rs4.getString("total_amount");
                }

            }

            m_strQry4 = "SELECT count(idgefu_file_generation) as total_record_count FROM gefu_file_generation_hst where gefu_creation_status='PROCESSED' and status='AUTHORIZED' and date='" + _mod_system_date + "'";
            m_rs4 = stmt4.executeQuery(m_strQry4);
            if (m_rs4.next()) {
                if (!(m_rs4.getString("total_record_count") == null)) {
                    m_total_record_count = m_rs4.getString("total_record_count");
                }
            }

            m_strQry4 = "SELECT sum(amount) as total_credit_amount FROM gefu_file_generation_hst where gefu_creation_status='PROCESSED' and status='AUTHORIZED' and date='" + _mod_system_date + "' and credit_debit='C'";
            m_rs4 = stmt4.executeQuery(m_strQry4);
            if (m_rs4.next()) {
                if (!(m_rs4.getString("total_credit_amount") == null)) {
                    m_total_credit_amount = m_rs4.getString("total_credit_amount");
                }
            }
            m_strQry4 = "SELECT count(idgefu_file_generation) as total_credit_record_count FROM gefu_file_generation_hst where gefu_creation_status='PROCESSED' and status='AUTHORIZED' and date='" + _mod_system_date + "' and credit_debit='C'";
            m_rs4 = stmt4.executeQuery(m_strQry4);
            if (m_rs4.next()) {

                if (!(m_rs4.getString("total_credit_record_count") == null)) {
                    m_total_credit_record_count = m_rs4.getString("total_credit_record_count");
                }
            }

            m_strQry4 = "SELECT sum(amount) as total_debit_amount FROM gefu_file_generation_hst where gefu_creation_status='PROCESSED' and status='AUTHORIZED' and date='" + _mod_system_date + "' and credit_debit='D'";
            m_rs4 = stmt4.executeQuery(m_strQry4);
            if (m_rs4.next()) {

                if (!(m_rs4.getString("total_debit_amount") == null)) {
                    m_total_debit_amount = m_rs4.getString("total_debit_amount");
                }

            }
            m_strQry4 = "SELECT count(idgefu_file_generation) as total_debit_record_count FROM gefu_file_generation_hst where gefu_creation_status='PROCESSED' and status='AUTHORIZED' and date='" + _mod_system_date + "' and credit_debit='D'";
            m_rs4 = stmt4.executeQuery(m_strQry4);
            if (m_rs4.next()) {
                if (!(m_rs4.getString("total_debit_record_count") == null)) {
                    m_total_debit_record_count = m_rs4.getString("total_debit_record_count");
                }
            }

            m_strQry4 = "SELECT * FROM gefu_file_generation_hst where gefu_creation_status='PROCESSED' and status='AUTHORIZED' and date='" + _mod_system_date + "'";
            m_rs4 = stmt4.executeQuery(m_strQry4);

            String m_gefu_idndb_pdc_txn_master = "";
            while (m_rs4.next()) {
                m_gefu_idndb_pdc_txn_master = m_rs4.getString("idndb_pdc_txn_master");

                if (m_gefu_idndb_pdc_txn_master.contains("COMM")) {
                    String[] comm_spliter = m_gefu_idndb_pdc_txn_master.split("COMM");
                    String seller_id = comm_spliter[0];
                    m_jsObj = new JSONObject();

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where idndb_customer_define='" + seller_id + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_jsObj.put("seller", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));
                    }

                    m_jsObj.put("buyer", "N/A");

                    m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", m_rs1.getString("pdc_chq_amu"));
                    m_jsObj.put("credit_debit", m_rs4.getString("credit_debit"));
                    m_jsObj.put("amount", m_rs4.getString("amount"));
                    m_jsObj.put("narration", m_rs4.getString("narration"));
                    m_jsArr.put(i, m_jsObj);
                    i++;

                } else if (m_gefu_idndb_pdc_txn_master.contains("MRG")) {
                    String[] comm_spliter = m_gefu_idndb_pdc_txn_master.split("MRG");
                    String seller_id = comm_spliter[0];

                    m_jsObj = new JSONObject();
                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where idndb_customer_define='" + seller_id + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_jsObj.put("seller", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));
                    }
                    m_jsObj.put("buyer", "N/A");

                    m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", m_rs1.getString("pdc_chq_amu"));
                    m_jsObj.put("credit_debit", m_rs4.getString("credit_debit"));
                    m_jsObj.put("amount", m_rs4.getString("amount"));
                    m_jsObj.put("narration", m_rs4.getString("narration"));
                    m_jsArr.put(i, m_jsObj);
                    i++;

                } else if (m_gefu_idndb_pdc_txn_master.contains("DISBBULKCREDIT")) {
                    String[] comm_spliter = m_gefu_idndb_pdc_txn_master.split("DISBBULKCREDIT");
                    String seller_id = comm_spliter[0];

                    m_jsObj = new JSONObject();
                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where idndb_customer_define='" + seller_id + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_jsObj.put("seller", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));
                    }
                    m_jsObj.put("buyer", "N/A");

                    m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                    m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                    m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                    m_jsObj.put("pdc_chq_amu", m_rs1.getString("pdc_chq_amu"));
                    m_jsObj.put("credit_debit", m_rs4.getString("credit_debit"));
                    m_jsObj.put("amount", m_rs4.getString("amount"));
                    m_jsObj.put("narration", m_rs4.getString("narration"));
                    m_jsArr.put(i, m_jsObj);
                    i++;

                } else {
                    m_strQry = "SELECT * FROM ndb_pdc_txn_master where idndb_pdc_txn_master='" + m_gefu_idndb_pdc_txn_master + "' ";
                    m_rs1 = _stmnt.executeQuery(m_strQry);
                    if (m_rs1.next()) {
                        m_jsObj = new JSONObject();
                        m_jsObj.put("idndb_pdc_txn_master", m_rs1.getString("idndb_pdc_txn_master"));

                        m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller_id") + "'";
                        m_rs2 = _stmnt2.executeQuery(m_strQry2);
                        while (m_rs2.next()) {

                            m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                                    + " FROM ndb_customer_define where idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                            m_rs3 = _stmnt3.executeQuery(m_strQry3);
                            if (m_rs3.next()) {
                                m_jsObj.put("seller", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));

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

                            m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name FROM ndb_customer_define where idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                            m_rs3 = _stmnt3.executeQuery(m_strQry3);
                            if (m_rs3.next()) {
                                m_jsObj.put("buyer", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));
                            }

                        }

                        m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                        m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                        m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                        m_jsObj.put("pdc_chq_amu", m_rs1.getString("pdc_chq_amu"));
                        m_jsObj.put("credit_debit", m_rs4.getString("credit_debit"));
                        m_jsObj.put("amount", m_rs4.getString("amount"));
                        m_jsObj.put("narration", m_rs4.getString("narration"));
                        m_jsArr.put(i, m_jsObj);
                        i++;
                    }

                }

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

    public JSONArray getPdcVdeElqUnauthData(JSONObject param_jsObj) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        JSONArray m_jsArr = new JSONArray();
        ResultSet _rs = null;
        String m_strQry = "";
        JSONObject m_jsObj;
        int i = 0;
        try {
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            String m_selection_param = param_jsObj.getString("selection_param");

            String m_selection = "nptm_txn_master.pdc_chq_status_auth in ('UN-AUTHORIZED')";

            if (m_selection_param.equals("RF_CW_VDE_ELQ_UPD")) {
                m_selection = "nptm_txn_master.pdc_chq_status in ('ACTIVE','ERLYLIQUDED') and nptm_txn_master.pdc_chq_status_auth in ('UN-AUTHORIZED') and nptm_txn_master.pdc_latest_modification in ('VDE','ELQ','UPD')";
            }

            if (m_selection_param.equals("RF_NEW")) {
                String m_idndb_customer_define_seller_id = param_jsObj.getString("idndb_customer_define_seller_id");
                m_selection = "nptm_txn_master.pdc_chq_status in ('ACTIVE') and nptm_txn_master.pdc_chq_status_auth in ('UN-AUTHORIZED') and nptm_txn_master.pdc_latest_modification in ('NEW') and nptm_txn_master.pdc_req_financing='RF' and nptm_txn_master.idndb_customer_define_seller_id='" + m_idndb_customer_define_seller_id + "'";

            }

            if (m_selection_param.equals("CW_NEW")) {
                String m_idndb_customer_define_seller_id = param_jsObj.getString("idndb_customer_define_seller_id");
                m_selection = "nptm_txn_master.pdc_chq_status in ('ACTIVE') and nptm_txn_master.pdc_chq_status_auth in ('UN-AUTHORIZED') and nptm_txn_master.pdc_latest_modification in ('NEW') and nptm_txn_master.pdc_req_financing='CW' and nptm_txn_master.idndb_customer_define_seller_id='" + m_idndb_customer_define_seller_id + "'";

            }

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
                    + "ncpm_cust_prod_map_buyer.idndb_customer_define =ncd_cust_define_buyer.idndb_customer_define where " + m_selection + "";

            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();

            while (_rs.next()) {
                String m_status = "NA";
                if (_rs.getString("pdc_latest_modification").equals("VDE")) {
                    m_status = "VALUE DATE EXTENSION";
                }
                if (_rs.getString("pdc_latest_modification").equals("ELQ")) {
                    m_status = "EARLY LIQUIDATION";
                }

                if (_rs.getString("pdc_latest_modification").equals("UPD")) {
                    m_status = "CHEQUE UPDATE";
                }

                if (_rs.getString("pdc_latest_modification").equals("NEW")) {
                    m_status = "NEW TRANSACTION";
                }

                m_jsObj = new JSONObject();
                m_jsObj.put("pdc_latest_modification", m_status);
                m_jsObj.put("idndb_pdc_txn_master", _rs.getString("idndb_pdc_txn_master"));
                m_jsObj.put("pdc_req_financing", _rs.getString("pdc_req_financing"));
                m_jsObj.put("seller_details", _rs.getString("seller_cust_id") + "|" + _rs.getString("seller_cust_name"));
                m_jsObj.put("buyer_details", _rs.getString("buyer_cust_id") + "|" + _rs.getString("buyer_cust_name"));
                m_jsObj.put("pdc_chq_number", _rs.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", _rs.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", _rs.getString("pdc_branch_code"));
                m_jsObj.put("pdc_value_date", _rs.getString("pdc_value_date"));
                m_jsObj.put("pdc_chq_amu", df.format(Double.parseDouble(_rs.getString("pdc_chq_amu"))));
                m_jsObj.put("pdc_chq_status_auth", _rs.getString("pdc_chq_status_auth"));
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

    public static void main(String[] args) {
        pdc_DAO pdc = new pdc_DAO();
        pdc.getSystemDate();

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
