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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import ndb.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;
import utility.Parameters;

/**
 *
 * @author Madhawa_4799
 */
public class pdcDAO {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(pdcDAO.class.getName());
    static ConnectionPool _connectionPool;
    static Connection _currentCon = null;
    private Statement _stmnt = null;
    private Statement _stmnt2 = null;
    private Statement _stmnt3 = null;
    private PreparedStatement _prepStmnt = null;
    private PreparedStatement _prepStmnt2 = null;
    private PreparedStatement _prepStmnt3 = null;
    private ResultSet _rs = null;

    public String saveCWPDCEntry(JSONObject prm_obj) {
        String _system_date = "";
        String cw_pdc_save_status = "0000";
        DecimalFormat df = new DecimalFormat("###.00");
        DecimalFormat df_report = new DecimalFormat("###.00");

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

        String m_Strchq_wh_dr_to_cr_spe_narr = "";
        String m_StrOldchq_wh_dr_to_cr_spe_narr = "";

        String m_Strcw_cheque_modification = "";

        String old_chq_status = "";

        String message = "";
        String pdc_txn_mod = "";
        String m_strQry = "";
        String NDBCommisionPLAcc = "";

        boolean m_ardy_exist_data = true;
        boolean m_ardy_exist_dcheque = true;
        log.info("MMANUAL PDC CHEQUE ENTRY.");

        try {
            pdcDAO dao = new pdcDAO();
            Parameters para = new Parameters();
            NDBCommisionPLAcc = para.getNDBCommisionPLAcc();
            m_str_user_id = prm_obj.getString("user_id");
            message = prm_obj.getString("message");
            m_str_idndb_pdc_txn_master = prm_obj.getString("idndb_pdc_txn_master");

            m_Stridndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            m_Stridndb_customer_define_buyer_id = prm_obj.getString("idndb_customer_define_buyer_id");
            m_Strcust_bank = prm_obj.getString("cust_bank");
            m_Strcust_branch = prm_obj.getString("cust_branch");
            m_Strcw_value_date_temp = dao.getchequeValueDate(prm_obj.getString("cw_value_date"));
            m_Strcw_chq_number = prm_obj.getString("cw_chq_number");
            m_Strcw_cheque_amu = prm_obj.getString("cw_cheque_amu");
            m_Strchq_wh_dr_to_cr_spe_narr = prm_obj.getString("chq_wh_dr_to_cr_spe_narr");
            m_Strcw_cheque_modification = prm_obj.getString("cw_cheque_modification");

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

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
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }

            m_strQry = "select * from ndb_system_date";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            if (_rs.next()) {
                _system_date = _rs.getString("_system_date");

            }
            m_strQry = "select * from ndb_holiday_marker where ndb_holiday_approval='UN-AUTHORIZED'";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            if (_rs.next()) {
                cw_pdc_save_status = "1104/" + m_Stridndb_customer_define_seller_id;
                log.error("ERROR OCURRED : UNAUTHORIZED HOLIDAYS ARE AVAILABLE IN THE HOLIDAY TABLE.");
                throw new Exception("ERROR OCURRED : UNAUTHORIZED HOLIDAYS ARE AVAILABLE IN THE HOLIDAY TABLE.");
            }

            m_strQry = "SELECT \n"
                    + "nbmf_bank_maser.idndb_bank_master_file,\n"
                    + "nbmf_bank_maser.bank_code,\n"
                    + "nbmf_bank_maser.bank_name,\n"
                    + "nbmf_branch_master.idndb_branch_master_file,\n"
                    + "nbmf_branch_master.branch_id,\n"
                    + "nbmf_branch_master.branch_name\n"
                    + "FROM \n"
                    + "ndb_bank_master_file nbmf_bank_maser inner join\n"
                    + "ndb_branch_master_file nbmf_branch_master on\n"
                    + "nbmf_bank_maser.idndb_bank_master_file = nbmf_branch_master.idndb_bank_master_file and\n"
                    + "nbmf_bank_maser.idndb_bank_master_file =? and\n"
                    + "nbmf_branch_master.idndb_branch_master_file=? ";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _prepStmnt.setString(1, m_Strcust_bank);
            _prepStmnt.setString(2, m_Strcust_branch);
            _rs = _prepStmnt.executeQuery();
            if (_rs.next()) {

                m_Strcust_bank_code = _rs.getString("bank_code");
                m_Strpdc_bank_name = _rs.getString("bank_name");
                m_Strcust_branch_code = _rs.getString("branch_id");
                m_Strpdc_branch_name = _rs.getString("branch_name");

            } else {
                cw_pdc_save_status = "1105/" + m_Stridndb_customer_define_seller_id;

                log.info("Invalid Input, Bank code and branch code missing in the input. ");
                throw new Exception("Invalid Input, Bank code and branch code missing in the input. ");

            }

            String m_uniq_id = m_Stridndb_customer_define_seller_id + m_Stridndb_customer_define_buyer_id + m_Strcw_chq_number + m_Strcust_bank_code + m_Strcust_branch_code;
            m_strQry = "SELECT CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code) as txn_master_unq_id,idndb_pdc_txn_master FROM ndb_pdc_txn_master where CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code)='" + m_uniq_id + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in ('AUTHORIZED','UN-AUTHORIZED')";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            String idndb_pdc_txn_master_uniq = "";
            if (_rs.next()) {
                m_ardy_exist_dcheque = false;
                idndb_pdc_txn_master_uniq = _rs.getString("idndb_pdc_txn_master");
                cw_pdc_save_status = "1101/" + m_Stridndb_customer_define_seller_id;
                // throw new Exception("Error Occured in insert user-roles");

                if (m_Strcw_cheque_modification.equals("NEW")) {
                    log.info("ERROR OCCUREC CHEQUE NUMBER ALREADY EXIST.");
                    throw new Exception("ERROR OCCUREC CHEQUE NUMBER ALREADY EXIST.");
                }

            }

            m_strQry = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master=?";
            log.info("READ PDC CW DATA FROM PDC TRANSCTION MASTER  IDNDB_PDC_TXN_MASTER: " + m_str_idndb_pdc_txn_master);
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _prepStmnt.setString(1, m_str_idndb_pdc_txn_master);
            _rs = _prepStmnt.executeQuery();
            if (_rs.next()) {
                log.info("PDC CW TRANNSACTION DATA RECEIVED IDNDB_PDC_TXN_MASTER: " + m_str_idndb_pdc_txn_master);

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
                m_StrOldchq_wh_dr_to_cr_spe_narr = (_rs.getString("pdc_chq_wh_dr_to_cr_spe_narr") == null) ? "" : _rs.getString("pdc_chq_wh_dr_to_cr_spe_narr");
                old_chq_status = _rs.getString("pdc_chq_status");
                pdc_txn_mod = _rs.getString("pdc_txn_mod");
                m_ardy_exist_data = false;
            }

            // extract cw commision data
            String chq_wh_commision_crg = "NOTDEFINE";
            String idndb_customer_define_seller = "";
            String idndb_customer_define_seller_idndb_customer_define = "";
            String rec_finance_curr_ac = "";
            String rec_cms_curr_acc_number = "";
            String cms_collection_acc_number = "";
            String rec_finance_acc_num = "";
            String cust_name = "";
            String idndb_geo_market_master_file = "";
            String acc_officer_code = "";
            String buyer_name = "";
            double chq_wh_erly_wdr_chg = 0.00;
            double chq_wh_vale_dte_extr_chg = 0.00;
            double chq_wh_erly_stlemnt_chg = 0.00;
            double cw_tran_base_falt_fee = 0.00;
            double cw_tran_base_from_tran = 0.00;
            double cw_fixed_rate_amount = 0.00;
            String cw_fixed_frequency = "DAILY";

            m_strQry = "select \n"
                    + "nshb_seller_has_buyer.chq_wh_commision_crg,\n"
                    + "nshb_seller_has_buyer.idndb_customer_define_seller,\n"
                    + "nshb_seller_has_buyer.cw_tran_base_falt_fee,\n"
                    + "nshb_seller_has_buyer.cw_tran_base_from_tran,\n"
                    + "nshb_seller_has_buyer.cw_fixed_rate_amount,\n"
                    + "nshb_seller_has_buyer.cw_fixed_frequency,\n"
                    + "ncw_chq_wh.chq_wh_erly_wdr_chg,\n"
                    + "ncw_chq_wh.chq_wh_vale_dte_extr_chg,\n"
                    + "ncw_chq_wh.chq_wh_erly_stlemnt_chg,\n"
                    + "ncd_cust_define_seller.rec_finance_curr_ac,\n"
                    + "ncd_cust_define_seller.cms_curr_acc_number,\n"
                    + "ncd_cust_define_seller.cms_collection_acc_number,\n"
                    + "ncd_cust_define_seller.rec_finance_acc_num,\n"
                    + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                    + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                    + "ngmmf_geo_market_file.geo_market_id\n"
                    + "from \n"
                    + "ndb_seller_has_buyers nshb_seller_has_buyer inner join\n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_seller on \n"
                    + "nshb_seller_has_buyer.idndb_customer_define_seller = ncpm_cust_prod_map_seller.idndb_cust_prod_map inner join\n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                    + "nshb_seller_has_buyer.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
                    + "ndb_chq_wh ncw_chq_wh on\n"
                    + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = ncw_chq_wh.idndb_cust_prod_map inner join\n"
                    + "ndb_customer_define ncd_cust_define_seller on\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define = ncd_cust_define_seller.idndb_customer_define inner join\n"
                    + "ndb_customer_define ncd_cust_define_buyer on\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define = ncd_cust_define_buyer.idndb_customer_define inner join\n"
                    + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                    + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file and \n"
                    + "nshb_seller_has_buyer.idndb_seller_has_buyers=?";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _prepStmnt.setString(1, m_Stridndb_customer_define_buyer_id);
            _rs = _prepStmnt.executeQuery();
            if (_rs.next()) {

                chq_wh_commision_crg = _rs.getString("chq_wh_commision_crg");
                idndb_customer_define_seller = _rs.getString("idndb_customer_define_seller");
                cw_tran_base_falt_fee = _rs.getDouble("cw_tran_base_falt_fee");
                cw_tran_base_from_tran = _rs.getDouble("cw_tran_base_from_tran");
                cw_fixed_rate_amount = _rs.getDouble("cw_fixed_rate_amount");
                cw_fixed_frequency = _rs.getString("cw_fixed_frequency");
                chq_wh_erly_wdr_chg = _rs.getDouble("chq_wh_erly_wdr_chg");
                chq_wh_vale_dte_extr_chg = _rs.getDouble("chq_wh_vale_dte_extr_chg");
                chq_wh_erly_stlemnt_chg = _rs.getDouble("chq_wh_erly_stlemnt_chg");
                rec_finance_curr_ac = _rs.getString("rec_finance_curr_ac");
                rec_cms_curr_acc_number = _rs.getString("cms_curr_acc_number");
                cms_collection_acc_number = _rs.getString("cms_collection_acc_number");
                rec_finance_acc_num = _rs.getString("rec_finance_acc_num");
                cust_name = _rs.getString("seller_cust_name");
                buyer_name = _rs.getString("buyer_cust_name");
                acc_officer_code = _rs.getString("geo_market_id");

            }

            String m_strQryMstrEntry = "insert into ndb_pdc_txn_master ("
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
                    + ",pdc_additional_day"
                    + ",pdc_old_value_date"
                    + ",pdc_txn_mod"
                    + ",pdc_latest_modification"
                    + ",pdc_chq_wh_dr_to_cr_spe_narr"
                    + ",pdc_chq_creat_by"
                    + ",pdc_chq_creat_date"
                    + ",pdc_chq_mod_by"
                    + ",pdc_chq_mod_date"
                    + ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())";

            String m_strQryLog = "insert into ndb_change_log (ndb_change_log_type,"
                    + "ndb_attached_id,"
                    + "ndb_change"
                    + ",status"
                    + ",creat_by"
                    + ",creat_date"
                    + ""
                    + ") values(?,?,?,?,?,now())";

            String m_strQryGefuEntry = "insert into gefu_file_generation ("
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
                    + ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?,?,?,?)";

            int i = 0;
            String m_change = "";
            // end of extract cw commision data
            if (m_ardy_exist_data) {
                if (m_ardy_exist_dcheque) {
                    log.info("NEW CW PDC MANUAL ENRY PDC_TRANSACTION_MASTER");

                    _prepStmnt = _currentCon.prepareStatement(m_strQryMstrEntry, Statement.RETURN_GENERATED_KEYS);
                    _prepStmnt.setString(1, "CW");
                    _prepStmnt.setString(2, m_Stridndb_customer_define_seller_id);
                    _prepStmnt.setString(3, m_Stridndb_customer_define_buyer_id);
                    _prepStmnt.setString(4, m_Strcust_bank);
                    _prepStmnt.setString(5, m_Strcust_bank_code);
                    _prepStmnt.setString(6, m_Strcust_branch);
                    _prepStmnt.setString(7, m_Strcust_branch_code);
                    _prepStmnt.setString(8, m_Strcw_chq_number);
                    _prepStmnt.setString(9, _system_date);
                    _prepStmnt.setString(10, m_Strcw_value_date_temp);
                    _prepStmnt.setString(11, m_Strcw_cheque_liq_date_temp);
                    _prepStmnt.setString(12, df.format(Double.parseDouble(m_Strcw_cheque_amu)));
                    _prepStmnt.setString(13, "0.00");
                    _prepStmnt.setString(14, "0.00");
                    _prepStmnt.setString(15, df.format(Double.parseDouble(m_Strcw_cheque_amu)));
                    _prepStmnt.setString(16, "ACTIVE");
                    _prepStmnt.setString(17, "UN-AUTHORIZED");
                    _prepStmnt.setString(18, "*");
                    _prepStmnt.setString(19, cms_collection_acc_number);
                    _prepStmnt.setString(20, cust_name);
                    _prepStmnt.setString(21, m_Strpdc_bank_name);
                    _prepStmnt.setString(22, m_Strpdc_branch_name);
                    _prepStmnt.setString(23, "DEACTIVE");
                    _prepStmnt.setString(24, "DEACTIVE");
                    _prepStmnt.setString(25, m_Strcw_value_date_temp);
                    _prepStmnt.setString(26, "NEWTXN");
                    _prepStmnt.setString(27, m_Strcw_cheque_modification);
                    _prepStmnt.setString(28, m_Strchq_wh_dr_to_cr_spe_narr);
                    _prepStmnt.setString(29, m_str_user_id);
                    _prepStmnt.setString(30, m_str_user_id);

                    log.info("CW PDC MANUAL ENRY PDC_TRANSACTION_MASTER : " + _prepStmnt);

                    if (_prepStmnt.executeUpdate() <= 0) {
                        cw_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                        log.error("ERROR OCCURED : UNABLE TO INSERT PDC_TRANSACTION_MASTER DATA.");
                        throw new Exception("ERROR OCCURED : UNABLE TO INSERT PDC_TRANSACTION_MASTER DATA.");

                    }

                    String max_idndb_pdc_txn_master = "";
                    _rs = _prepStmnt.getGeneratedKeys();
                    if (_rs.next()) {
                        max_idndb_pdc_txn_master = _rs.getString(1);
                        log.info("PDC_TRANSACTION_MASTER GENERATED ID_PDC_TRANSACTION_MASTER : " + max_idndb_pdc_txn_master);
                    }

                    log.info("max_idndb_pdc_txn_master recevied :" + max_idndb_pdc_txn_master);

                    if (!prm_obj.getString("cw_value_date").equals(m_Strcw_value_date_temp)) {
                        message = "2 ) " + prm_obj.getString("cw_value_date") + " date is a holiday and value date updated to next working day on " + m_Strcw_value_date_temp + "</br>" + message;
                    }

                    _prepStmnt = _currentCon.prepareStatement(m_strQryLog);
                    _prepStmnt.setString(1, "PDCTXN");
                    _prepStmnt.setString(2, max_idndb_pdc_txn_master);
                    _prepStmnt.setString(3, "1 ) NEW CW TXN ENTRY </br> " + message);
                    _prepStmnt.setString(4, "UN-AUTHORIZED");
                    _prepStmnt.setString(5, m_str_user_id);

                    if (_prepStmnt.executeUpdate() <= 0) {
                        cw_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                        log.info("ERROR OCCURED IN INSERT CHANGES LOG CHEQUE_NUMBER:" + m_Strcw_chq_number);
                        throw new Exception("Error Occured in insert user-roles");
                    }

                }
                cw_pdc_save_status = "1100/" + m_Stridndb_customer_define_seller_id;

                if (!endConnection(_currentCon)) {
                    log.error("ERROR OCCURED : UNABLE TO COMMIT TRANSANS TO DB.");
                    throw new Exception("ERROR OCCURED : UNABLE TO COMMIT TRANSANS TO DB.");
                }

            } else {

                if (!m_str_idndb_pdc_txn_master.equals(idndb_pdc_txn_master_uniq) && (!idndb_pdc_txn_master_uniq.equals(""))) {
                    cw_pdc_save_status = "1101/" + m_Stridndb_customer_define_seller_id;
                    throw new Exception("ERROR OCCURED : INVALID IDNDB_PDC_TXN_MASTER_ID FOR CW PDC-UPDATE.");
                }

                String m_condition = "";

                if (m_Strcw_cheque_modification.equals("UPD")) {

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
                    if (!(Double.parseDouble(m_StrOldcw_cheque_amu) == Double.parseDouble(m_Strcw_cheque_amu))) {
                        m_condition = m_condition + "pdc_chq_amu='" + m_Strcw_cheque_amu + "',";
                        m_condition = m_condition + "pdc_chq_cr_amu='" + m_Strcw_cheque_amu + "',";
                        i++;
                        m_change = m_change + i + ")" + df_report.format(Double.parseDouble(m_StrOldcw_cheque_amu)) + " CHQ AMU. CHANGE TO " + df_report.format(Double.parseDouble(m_Strcw_cheque_amu)) + "<br>";

                    }

                    if (!m_StrOldchq_wh_dr_to_cr_spe_narr.equals(m_Strchq_wh_dr_to_cr_spe_narr)) {
                        m_condition = m_condition + "pdc_chq_wh_dr_to_cr_spe_narr='" + m_Strchq_wh_dr_to_cr_spe_narr + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldchq_wh_dr_to_cr_spe_narr + " DR/CR TRANSFER (SPECIAL NARRATION) TO " + m_Strchq_wh_dr_to_cr_spe_narr + "<br>";

                    }

                    if (!m_StrOldcw_value_date.equals(m_Strcw_value_date_temp)) {
                        m_condition = m_condition + "pdc_value_date='" + m_Strcw_value_date_temp + "',";
                        m_condition = m_condition + "pdc_old_value_date='" + m_StrOldcw_value_date + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldcw_value_date + " VALUE DATE CHANGE TO " + m_Strcw_value_date_temp + "<br>";

                    }
                    if (!m_StrOldcw_cheque_liq_date.equals(m_Strcw_cheque_liq_date_temp)) {
                        m_condition = m_condition + "pdc_lquid_date='" + m_Strcw_cheque_liq_date_temp + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldcw_cheque_liq_date + " LIQUIDATE DATE CHANGE TO " + m_Strcw_cheque_liq_date_temp + "<br>";

                    }

                    m_condition = m_condition + "pdc_latest_modification='UPD',";

                }

                if (m_Strcw_cheque_modification.equals("VDE")) {

                    if (!m_StrOldcw_value_date.equals(m_Strcw_value_date_temp)) {
                        m_condition = m_condition + "pdc_value_date='" + m_Strcw_value_date_temp + "',";
                        m_condition = m_condition + "pdc_value_date_ext='ACTIVE',";
                        m_condition = m_condition + "pdc_latest_modification='VDE',";
                        m_condition = m_condition + "pdc_old_value_date='" + m_StrOldcw_value_date + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldcw_value_date + " VALUE DATE EXTENDED TO " + m_Strcw_value_date_temp + "<br>";

                    }
                    if (!m_StrOldcw_cheque_liq_date.equals(m_Strcw_cheque_liq_date_temp)) {
                        m_condition = m_condition + "pdc_lquid_date='" + m_Strcw_cheque_liq_date_temp + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldcw_cheque_liq_date + " LIQUIDATE DATE CHANGE TO " + m_Strcw_cheque_liq_date_temp + "<br>";

                    }

                }

                if (m_Strcw_cheque_modification.equals("ELQ") && pdc_txn_mod.equals("AUTHORIZED")) {
                    m_condition = m_condition + "pdc_chq_status='ERLYLIQUDED',";
                    m_condition = m_condition + "pdc_latest_modification='ELQ',";
                    i++;
                    m_change = m_change + i + ") PDC CHEQUE EARLY LIQUIDATED <br>";

                }

                if (!message.equals("")) {
                    i++;
                    m_change = m_change + i + ") " + message + " <br>";

                }

                if ((m_Strcw_cheque_modification.equals("ELQ") && pdc_txn_mod.equals("AUTHORIZED")) && (!(m_StrOldcw_value_date.equals(m_Strcw_value_date_temp)) && pdc_txn_mod.equals("AUTHORIZED"))) {
                    cw_pdc_save_status = "1103/" + m_Stridndb_customer_define_seller_id;
                    throw new Exception("Error Occured in insert user-roles");

                }

                if (m_Strcw_cheque_modification.equals("VDE") && pdc_txn_mod.equals("AUTHORIZED")) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date date1 = sdf.parse(m_StrOldcw_value_date);
                    Date date2 = sdf.parse(m_Strcw_value_date_temp);

                    if (date1.compareTo(date2) > 0) {
                        cw_pdc_save_status = "1102/" + m_Stridndb_customer_define_seller_id;
                        throw new Exception("Error Occured in insert user-roles");

                    }

                }

                log.info("UPDATE PDC CW CHEQUE IDNDB_PDC_TXN_MASTER :" + m_str_idndb_pdc_txn_master);
                m_strQry = "update ndb_pdc_txn_master set " + m_condition + "pdc_chq_status_auth='UN-AUTHORIZED',pdc_txn_mod='NEWTXN',"
                        + "pdc_chq_batch_no='*',"
                        + "pdc_chq_mod_by='" + m_str_user_id + "',"
                        + "pdc_chq_mod_date=now()"
                        + " where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";
                log.info("UPDATE PDC CW CHEQUE QUIERY :" + m_strQry);

                _prepStmnt = _currentCon.prepareStatement(m_strQry);
                if (_prepStmnt.executeUpdate() <= 0) {
                    cw_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                    log.error("ERROR OCCURED IN UPDATE NDB_PDC_TXN_MASTER DATA TABLE CHQ NUMBER:" + m_Strcw_chq_number);
                    throw new Exception("ERROR OCCURED IN UPDATE NDB_PDC_TXN_MASTER DATA TABLE CHQ NUMBER:" + m_Strcw_chq_number);
                }

                _prepStmnt = _currentCon.prepareStatement(m_strQryLog);
                _prepStmnt.setString(1, "PDCTXN");
                _prepStmnt.setString(2, m_str_idndb_pdc_txn_master);
                _prepStmnt.setString(3, m_change);
                _prepStmnt.setString(4, "UN-AUTHORIZED");
                _prepStmnt.setString(5, m_str_user_id);

                if (_prepStmnt.executeUpdate() <= 0) {
                    cw_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                    log.info("ERROR OCCURED IN INSERT CHANGES LOG CHEQUE_NUMBER:" + m_Strcw_chq_number);
                    throw new Exception("Error Occured in insert user-roles");
                }

                if (m_Strcw_cheque_modification.equals("VDE") && pdc_txn_mod.equals("AUTHORIZED")) {

                    if (!(chq_wh_vale_dte_extr_chg == 0.00)) {

                        log.info("ACC. ENTRY :PDC CW VALUE DATE EXTENTION CHARGES ENTRY PROCESSING START.");

                        String gefu_account = "";
                        String gefu_currency = "LKR";
                        String gefu_date = "";
                        double gefu_amount = chq_wh_vale_dte_extr_chg;
                        String gefu_narration = "";
                        String gefu_credit_debit = "";
                        String gefu_profit_centre = "";
                        String gefu_dao = "";
                        double gefu_c_amount = 0.00;
                        double gefu_d_amount = 0.00;
                        String gefu_cw_fixed_frequency = cw_fixed_frequency;

                        String[] gefu_date_arr = _system_date.split("/");
                        String gefu_day = gefu_date_arr[0];
                        String gefu_month = gefu_date_arr[1];
                        String gefu_year = gefu_date_arr[2];
                        gefu_date = gefu_year + gefu_month + gefu_day;

                        gefu_d_amount = gefu_amount;
                        gefu_account = rec_cms_curr_acc_number;
                        gefu_credit_debit = "D";
                        gefu_narration = "VDE" + m_Strcw_chq_number + "RS" + m_Strcw_cheque_amu + buyer_name;

                        log.info("ACC. ENTRY :PDC RF VALUE DATE EXTENTION CHARGES ENTRY. DEBIT CUST CMS CURRENT ACCOUNT.");

                        _prepStmnt = _currentCon.prepareStatement(m_strQryGefuEntry);
                        _prepStmnt.setString(1, m_str_idndb_pdc_txn_master);
                        _prepStmnt.setString(2, gefu_account);
                        _prepStmnt.setString(3, gefu_currency);
                        _prepStmnt.setString(4, gefu_date);
                        _prepStmnt.setString(5, df.format(gefu_amount));
                        _prepStmnt.setString(6, gefu_narration);
                        _prepStmnt.setString(7, gefu_credit_debit);
                        _prepStmnt.setString(8, gefu_profit_centre);
                        _prepStmnt.setString(9, gefu_dao);
                        _prepStmnt.setString(10, df.format(gefu_c_amount));
                        _prepStmnt.setString(11, df.format(gefu_d_amount));
                        _prepStmnt.setString(12, "ACTIVE");
                        _prepStmnt.setString(13, "UN-AUTHORIZED");
                        _prepStmnt.setString(14, m_str_user_id);
                        _prepStmnt.setString(15, m_str_user_id);
                        _prepStmnt.setString(16, _system_date);
                        _prepStmnt.setString(17, "VDEXTRCD");
                        _prepStmnt.setString(18, "NO");
                        _prepStmnt.setString(19, gefu_cw_fixed_frequency);

                        log.info("ACC. ENTRY :PDC CW VALUE DATE EXTENTION CHARGES ENTRY. DEBIT CUST CMS CUURENT ACCOUNT. :" + _prepStmnt);

                        if (_prepStmnt.executeUpdate() <= 0) {
                            cw_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                            log.error("ERROR OCCURED: UNABLE TO INSERT SELLER CMS ACCOUNT DEBIT ENTRY.");
                            throw new Exception("ERROR OCCURED: UNABLE TO INSERT SELLER CMS ACCOUNT DEBIT ENTRY.");
                        }

                        gefu_d_amount = 0.00;
                        gefu_c_amount = gefu_amount;
                        gefu_account = NDBCommisionPLAcc;
                        gefu_credit_debit = "C";
                        gefu_profit_centre = "PL";
                        gefu_dao = acc_officer_code;
                        gefu_narration = "VDE" + m_Strcw_chq_number + "RS" + m_Strcw_cheque_amu + buyer_name;

                        log.info("ACC. ENTRY :PDC CW VALUE DATE EXTENTION CHARGES ENTRY. CREDIT NDB PL ACCOUNT.");

                        _prepStmnt = _currentCon.prepareStatement(m_strQryGefuEntry);
                        _prepStmnt.setString(1, m_str_idndb_pdc_txn_master);
                        _prepStmnt.setString(2, gefu_account);
                        _prepStmnt.setString(3, gefu_currency);
                        _prepStmnt.setString(4, gefu_date);
                        _prepStmnt.setString(5, df.format(gefu_amount));
                        _prepStmnt.setString(6, gefu_narration);
                        _prepStmnt.setString(7, gefu_credit_debit);
                        _prepStmnt.setString(8, gefu_profit_centre);
                        _prepStmnt.setString(9, gefu_dao);
                        _prepStmnt.setString(10, df.format(gefu_c_amount));
                        _prepStmnt.setString(11, df.format(gefu_d_amount));
                        _prepStmnt.setString(12, "ACTIVE");
                        _prepStmnt.setString(13, "UN-AUTHORIZED");
                        _prepStmnt.setString(14, m_str_user_id);
                        _prepStmnt.setString(15, m_str_user_id);
                        _prepStmnt.setString(16, _system_date);
                        _prepStmnt.setString(17, "VDEXTRBC");
                        _prepStmnt.setString(18, "NO");
                        _prepStmnt.setString(19, gefu_cw_fixed_frequency);

                        log.info("ACC. ENTRY :PDC CW VALUE DATE EXTENTION CHARGES ENTRY. CREDIT NDB PL ACCOUNT. :" + _prepStmnt);

                        if (_prepStmnt.executeUpdate() <= 0) {
                            cw_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                            log.error("ERROR OCCURED: UNABLE TO INSERT NDB PLT ACCOUNT CREDIT ENTRY.");
                            throw new Exception("ERROR OCCURED: UNABLE TO INSERT NDB PLT ACCOUNT CREDIT ENTRY.");
                        }

                    }

                }

                if (m_Strcw_cheque_modification.equals("ELQ")) {

                    if (!(chq_wh_erly_wdr_chg == 0.00)) {
                        log.info("ACC. ENTRY : ERLY LEQUDATION ENTRIES PROCESSING.");

                        String gefu_account = "";
                        String gefu_currency = "LKR";
                        String gefu_date = _system_date;
                        double gefu_amount = chq_wh_erly_wdr_chg;
                        String gefu_narration = "";
                        String gefu_credit_debit = "";
                        String gefu_profit_centre = "";
                        String gefu_dao = "";
                        double gefu_c_amount = 0.00;
                        double gefu_d_amount = 0.00;
                        String gefu_cw_fixed_frequency = cw_fixed_frequency;

                        String[] gefu_date_arr = _system_date.split("/");
                        String gefu_day = gefu_date_arr[0];
                        String gefu_month = gefu_date_arr[1];
                        String gefu_year = gefu_date_arr[2];
                        gefu_date = gefu_year + gefu_month + gefu_day;

                        gefu_d_amount = gefu_amount;
                        gefu_account = rec_cms_curr_acc_number;
                        gefu_credit_debit = "D";
                        gefu_narration = "EL" + m_Strcw_chq_number + "RS" + m_Strcw_cheque_amu + buyer_name;

                        log.info("ACC. ENTRY : ERLY LEQUDATION CHARGES ENTRIES DEBIT CUSTOMER CMS CURRENT ACCOUNT.");

                        _prepStmnt = _currentCon.prepareStatement(m_strQryGefuEntry);
                        _prepStmnt.setString(1, m_str_idndb_pdc_txn_master);
                        _prepStmnt.setString(2, gefu_account);
                        _prepStmnt.setString(3, gefu_currency);
                        _prepStmnt.setString(4, gefu_date);
                        _prepStmnt.setString(5, df.format(gefu_amount));
                        _prepStmnt.setString(6, gefu_narration);
                        _prepStmnt.setString(7, gefu_credit_debit);
                        _prepStmnt.setString(8, gefu_profit_centre);
                        _prepStmnt.setString(9, gefu_dao);
                        _prepStmnt.setString(10, df.format(gefu_c_amount));
                        _prepStmnt.setString(11, df.format(gefu_d_amount));
                        _prepStmnt.setString(12, "ACTIVE");
                        _prepStmnt.setString(13, "UN-AUTHORIZED");
                        _prepStmnt.setString(14, m_str_user_id);
                        _prepStmnt.setString(15, m_str_user_id);
                        _prepStmnt.setString(16, _system_date);
                        _prepStmnt.setString(17, "ERLEQDCD");
                        _prepStmnt.setString(18, "NO");
                        _prepStmnt.setString(19, gefu_cw_fixed_frequency);

                        log.info("ACC. ENTRY : ERLY LEQUDATION CHARGES ENTRIES DEBIT CUSTOMER CMS CURRENT ACCOUNT." + _prepStmnt);

                        if (_prepStmnt.executeUpdate() <= 0) {
                            cw_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                            log.error("ERROR OCCURED: UNABLE TO INSERT NDB CUST CMS CURRENT ACCOUNT DBIT ENTRY.");
                            throw new Exception("ERROR OCCURED: UNABLE TO INSERT NDB CUST CMS CURRENT ACCOUNT DBIT ENTRY.");
                        }

                        gefu_d_amount = 0.00;
                        gefu_c_amount = gefu_amount;
                        gefu_account = NDBCommisionPLAcc;
                        gefu_credit_debit = "C";
                        gefu_profit_centre = "PL";
                        gefu_dao = acc_officer_code;
                        gefu_narration = "EL" + m_Strcw_chq_number + "RS" + m_Strcw_cheque_amu + buyer_name;

                        log.info("ACC. ENTRY : ERLY LEQUDATION CHARGES ENTRIES CREDIT NDB PL ACCOUNT.");

                        _prepStmnt = _currentCon.prepareStatement(m_strQryGefuEntry);
                        _prepStmnt.setString(1, m_str_idndb_pdc_txn_master);
                        _prepStmnt.setString(2, gefu_account);
                        _prepStmnt.setString(3, gefu_currency);
                        _prepStmnt.setString(4, gefu_date);
                        _prepStmnt.setString(5, df.format(gefu_amount));
                        _prepStmnt.setString(6, gefu_narration);
                        _prepStmnt.setString(7, gefu_credit_debit);
                        _prepStmnt.setString(8, gefu_profit_centre);
                        _prepStmnt.setString(9, gefu_dao);
                        _prepStmnt.setString(10, df.format(gefu_c_amount));
                        _prepStmnt.setString(11, df.format(gefu_d_amount));
                        _prepStmnt.setString(12, "ACTIVE");
                        _prepStmnt.setString(13, "UN-AUTHORIZED");
                        _prepStmnt.setString(14, m_str_user_id);
                        _prepStmnt.setString(15, m_str_user_id);
                        _prepStmnt.setString(16, _system_date);
                        _prepStmnt.setString(17, "ERLEQDBC");
                        _prepStmnt.setString(18, "NO");
                        _prepStmnt.setString(19, gefu_cw_fixed_frequency);

                        log.info("ACC. ENTRY : ERLY LEQUDATION CHARGES ENTRIES CREDIT NDB PL ACCOUNT." + _prepStmnt);

                        if (_prepStmnt.executeUpdate() <= 0) {
                            cw_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                            log.error("ERROR OCCURED: UNABLE TO INSERT LIQUDATE CHARGES ENTRY CREDIT NDB PL ACCOUNT.");
                            throw new Exception("ERROR OCCURED: UNABLE TO INSERT LIQUDATE CHARGES ENTRY CREDIT NDB PL ACCOUNT.");
                        }
                    }
                }

                if (!cw_pdc_save_status.equals("1102/" + m_Stridndb_customer_define_seller_id)) {
                    cw_pdc_save_status = "1100/" + m_Stridndb_customer_define_seller_id;

                }
                if (!endConnection(_currentCon)) {
                    cw_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                    throw new Exception("ERROR OCCURED UNABLE TO COMMIT TRANSACTION TO DB.");
                }
            }

        } catch (Exception e) {

            abortConnection(_currentCon);
            log.error("ERROR OCCURED UNABLE TO ABORT THE CONNECTION. " + e);
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_prepStmnt != null) {
                    _prepStmnt.close();
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
        DecimalFormat df = new DecimalFormat("###.00");
        DecimalFormat df_report = new DecimalFormat("###,###.00");
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

        String m_Strrf_cheque_modification = "";

        String m_Strrf_chq_number = "";
        String m_StrOldrf_chq_number = "";

        String m_Strrf_cheque_amu = "";
        String m_StrOldrf_cheque_amu = "";
        double m_StrOldpdc_chq_cr_amu = 0.00;

        String m_strQry = "";
        String _system_date = "";
        String pdc_txn_mod = "";
        String NDBCommisionPLAcc = "";
        String message = "";

        boolean m_ardy_exist_data = true;
        boolean m_ardy_exist_dcheque = true;

        try {
            log.info("RF PDC manual entry receved");
            pdcDAO dao = new pdcDAO();
            Parameters para = new Parameters();
            NDBCommisionPLAcc = para.getNDBCommisionPLAcc();

            m_str_user_id = prm_obj.getString("user_id");
            message = prm_obj.getString("message");
            m_str_idndb_pdc_txn_master = prm_obj.getString("idndb_pdc_txn_master");
            m_Stridndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");
            m_Stridndb_customer_define_buyer_id = prm_obj.getString("idndb_customer_define_buyer_id");
            m_Strcust_bank = prm_obj.getString("cust_bank");
            m_Strcust_branch = prm_obj.getString("cust_branch");
            m_Strrf_value_date_temp = dao.getchequeValueDate(prm_obj.getString("rf_value_date"));
            m_Strrf_chq_number = prm_obj.getString("rf_chq_number");
            m_Strrf_cheque_amu = prm_obj.getString("rf_cheque_amu");
            m_Strrf_cheque_modification = prm_obj.getString("rf_cheque_modification");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            String[] date_spliter = m_Strrf_value_date_temp.split("/");
            int m_year = Integer.parseInt(date_spliter[2]);
            int m_month = Integer.parseInt(date_spliter[1]) - 1;
            int m_day = Integer.parseInt(date_spliter[0]) + 1;
            Calendar ced = Calendar.getInstance();

            ced.set(Calendar.YEAR, m_year); // set the year
            ced.set(Calendar.MONTH, m_month); // set the month
            ced.set(Calendar.DAY_OF_MONTH, m_day);

            m_Strrf_cheque_liq_date_temp = dao.getchequeValueDate(formatter.format(ced.getTime()));
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }

            m_strQry = "select * from ndb_system_date";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            if (_rs.next()) {
                _system_date = _rs.getString("_system_date");
            }

            m_strQry = "select * from ndb_holiday_marker where ndb_holiday_approval='UN-AUTHORIZED'";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            if (_rs.next()) {
                rf_pdc_save_status = "1104/" + m_Stridndb_customer_define_seller_id;
                log.error("ERROR OCURRED : UNAUTHORIZED HOLIDAYS ARE AVAILABLE IN THE HOLIDAY TABLE.");
                throw new Exception("ERROR OCURRED : UNAUTHORIZED HOLIDAYS ARE AVAILABLE IN THE HOLIDAY TABLE.");
            }

            m_strQry = "SELECT \n"
                    + "nbmf_bank_maser.idndb_bank_master_file,\n"
                    + "nbmf_bank_maser.bank_code,\n"
                    + "nbmf_bank_maser.bank_name,\n"
                    + "nbmf_branch_master.idndb_branch_master_file,\n"
                    + "nbmf_branch_master.branch_id,\n"
                    + "nbmf_branch_master.branch_name\n"
                    + "FROM \n"
                    + "ndb_bank_master_file nbmf_bank_maser inner join\n"
                    + "ndb_branch_master_file nbmf_branch_master on\n"
                    + "nbmf_bank_maser.idndb_bank_master_file = nbmf_branch_master.idndb_bank_master_file and\n"
                    + "nbmf_bank_maser.idndb_bank_master_file =? and\n"
                    + "nbmf_branch_master.idndb_branch_master_file=? ";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _prepStmnt.setString(1, m_Strcust_bank);
            _prepStmnt.setString(2, m_Strcust_branch);
            _rs = _prepStmnt.executeQuery();
            if (_rs.next()) {

                m_Strcust_bank_code = _rs.getString("bank_code");
                m_Strpdc_bank_name = _rs.getString("bank_name");
                m_Strcust_branch_code = _rs.getString("branch_id");
                m_Strpdc_branch_name = _rs.getString("branch_name");

            } else {
                rf_pdc_save_status = "1105/" + m_Stridndb_customer_define_seller_id;

                log.info("Invalid Input, Bank code and branch code missing in the input. ");
                throw new Exception("Invalid Input, Bank code and branch code missing in the input.");

            }

            String m_uniq_id = m_Stridndb_customer_define_seller_id + m_Stridndb_customer_define_buyer_id + m_Strrf_chq_number + m_Strcust_bank_code + m_Strcust_branch_code;
            m_strQry = "SELECT CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code) as txn_master_unq_id,idndb_pdc_txn_master FROM ndb_pdc_txn_master where CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code)='" + m_uniq_id + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in ('AUTHORIZED','UN-AUTHORIZED')";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _rs = _prepStmnt.executeQuery();
            String idndb_pdc_txn_master_uniq = "";
            if (_rs.next()) {
                m_ardy_exist_dcheque = false;
                //cheque number all ready exist
                idndb_pdc_txn_master_uniq = _rs.getString("idndb_pdc_txn_master");
                rf_pdc_save_status = "1101/" + m_Stridndb_customer_define_seller_id;
                // throw new Exception("Error Occured in insert user-roles");
                if (m_Strrf_cheque_modification.equals("NEW")) {
                    log.info("ERROR OCCUREC CHEQUE NUMBER ALREADY EXIST.");
                    throw new Exception("ERROR OCCUREC CHEQUE NUMBER ALREADY EXIST.");
                }

            }

            double m_discounting_advance_rate = 0.00;
            double m_discounting_amu_from_cheque_amu = 0.00;
            double m_Olddiscounting_amu_from_cheque_amu = 0.00;
            double m_alternat_amu = 0.00;

            // start of extract receivable finance commision data.....................................
            String rec_finance_commision_crg = "NOTDEFINE";
            String idndb_customer_define_seller = "";
            String rec_finance_curr_ac = "";
            String rec_finance_acc_num = "";
            String rec_finance_cr_dsc_proc_acc_num = "";
            String rec_finance_margin_ac = "";
            String rec_finance_margin = "";
            String cust_name = "";
            String idndb_geo_market_master_file = "";
            String acc_officer_code = "";
            String buyer_name = "";
            String rec_finance_bulk_credit = "";
            double rec_finance_erly_wdr_chg = 0.00;
            double rec_finance_vale_dte_extr_chg = 0.00;
            double rec_finance_erly_stlemnt_chg = 0.00;
            double rf_tran_base_falt_fee = 0.00;
            double rf_tran_base_from_tran = 0.00;
            double rf_fixed_rate_amount = 0.00;
            String rf_fixed_frequency = "DAILY";

            m_strQry = "select \n"
                    + "nshb_seller_has_buyer.rec_finance_commision_crg,\n"
                    + "nshb_seller_has_buyer.idndb_customer_define_seller,\n"
                    + "nshb_seller_has_buyer.rf_tran_base_falt_fee,\n"
                    + "nshb_seller_has_buyer.rf_tran_base_from_tran,\n"
                    + "nshb_seller_has_buyer.rf_fixed_rate_amount,\n"
                    + "nshb_seller_has_buyer.rf_fixed_frequency,\n"
                    + "nshb_seller_has_buyer.shb_chq_dis_adv_rate_prectange,\n"
                    + "nrf_rec_fin.rec_finance_bulk_credit,\n"
                    + "nrf_rec_fin.rec_finance_erly_wdr_chg,\n"
                    + "nrf_rec_fin.rec_finance_vale_dte_extr_chg,\n"
                    + "nrf_rec_fin.rec_finance_erly_stlemnt_chg,\n"
                    + "ncd_cust_define_seller.rec_finance_curr_ac,\n"
                    + "ncd_cust_define_seller.rec_finance_acc_num,\n"
                    + "ncd_cust_define_seller.rec_finance_cr_dsc_proc_acc_num,\n"
                    + "ncd_cust_define_seller.rec_finance_margin_ac,\n"
                    + "ncd_cust_define_seller.rec_finance_margin,\n"
                    + "ncd_cust_define_seller.cust_name as seller_cust_name,\n"
                    + "ncd_cust_define_buyer.cust_name as buyer_cust_name,\n"
                    + "ngmmf_geo_market_file.geo_market_id\n"
                    + "from \n"
                    + "ndb_seller_has_buyers nshb_seller_has_buyer inner join\n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_seller on \n"
                    + "nshb_seller_has_buyer.idndb_customer_define_seller = ncpm_cust_prod_map_seller.idndb_cust_prod_map inner join\n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map_buyer on\n"
                    + "nshb_seller_has_buyer.idndb_customer_define_buyer = ncpm_cust_prod_map_buyer.idndb_cust_prod_map inner join\n"
                    + "ndb_rec_fin nrf_rec_fin on\n"
                    + "ncpm_cust_prod_map_seller.idndb_cust_prod_map = nrf_rec_fin.idndb_cust_prod_map inner join\n"
                    + "ndb_customer_define ncd_cust_define_seller on\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define = ncd_cust_define_seller.idndb_customer_define inner join\n"
                    + "ndb_customer_define ncd_cust_define_buyer on\n"
                    + "ncpm_cust_prod_map_seller.idndb_customer_define = ncd_cust_define_buyer.idndb_customer_define inner join\n"
                    + "ndb_geo_market_master_file ngmmf_geo_market_file on\n"
                    + "ncd_cust_define_seller.idndb_geo_market_master_file = ngmmf_geo_market_file.idndb_geo_market_master_file and \n"
                    + "nshb_seller_has_buyer.idndb_seller_has_buyers=?";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _prepStmnt.setString(1, m_Stridndb_customer_define_buyer_id);
            _rs = _prepStmnt.executeQuery();
            if (_rs.next()) {

                m_discounting_advance_rate = Double.parseDouble(_rs.getString("shb_chq_dis_adv_rate_prectange"));
                m_discounting_amu_from_cheque_amu = Double.parseDouble(m_Strrf_cheque_amu) / 100 * m_discounting_advance_rate;
                m_alternat_amu = Double.parseDouble(m_Strrf_cheque_amu) - m_discounting_amu_from_cheque_amu;

                rec_finance_commision_crg = _rs.getString("rec_finance_commision_crg");
                idndb_customer_define_seller = _rs.getString("idndb_customer_define_seller");

                rf_tran_base_falt_fee = _rs.getDouble("rf_tran_base_falt_fee");
                rf_tran_base_from_tran = _rs.getDouble("rf_tran_base_from_tran");

                rf_fixed_rate_amount = _rs.getDouble("rf_fixed_rate_amount");
                rf_fixed_frequency = _rs.getString("rf_fixed_frequency");

                rec_finance_bulk_credit = _rs.getString("rec_finance_bulk_credit");
                rec_finance_erly_wdr_chg = _rs.getDouble("rec_finance_erly_wdr_chg");
                rec_finance_vale_dte_extr_chg = _rs.getDouble("rec_finance_vale_dte_extr_chg");
                rec_finance_erly_stlemnt_chg = _rs.getDouble("rec_finance_erly_stlemnt_chg");

                rec_finance_curr_ac = _rs.getString("rec_finance_curr_ac");
                rec_finance_acc_num = _rs.getString("rec_finance_acc_num");
                rec_finance_cr_dsc_proc_acc_num = _rs.getString("rec_finance_cr_dsc_proc_acc_num");
                rec_finance_margin_ac = _rs.getString("rec_finance_margin_ac");
                rec_finance_margin = _rs.getString("rec_finance_margin");
                cust_name = _rs.getString("seller_cust_name");
                acc_officer_code = _rs.getString("geo_market_id");

                buyer_name = _rs.getString("buyer_cust_name");

            }

            // End of extract receivable finance commision data....................................................
            log.info("Check for RF PDC manual entry receved already exist to update");

            m_strQry = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master=?";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);
            _prepStmnt.setString(1, m_str_idndb_pdc_txn_master);
            _rs = _prepStmnt.executeQuery();
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
                pdc_txn_mod = _rs.getString("pdc_txn_mod");
                m_Olddiscounting_amu_from_cheque_amu = _rs.getDouble("pdc_chq_discounting_amu");

                m_ardy_exist_data = false;
            }

            String m_strQryMstrEntry = "insert into ndb_pdc_txn_master ("
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
                    + ",pdc_additional_day"
                    + ",pdc_old_value_date"
                    + ",pdc_txn_mod"
                    + ",pdc_latest_modification"
                    + ",pdc_chq_creat_by"
                    + ",pdc_chq_creat_date"
                    + ",pdc_chq_mod_by"
                    + ",pdc_chq_mod_date"
                    + ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now())";

            String m_strQryLog = "insert into ndb_change_log (ndb_change_log_type,"
                    + "ndb_attached_id,"
                    + "ndb_change"
                    + ",status"
                    + ",creat_by"
                    + ",creat_date"
                    + ""
                    + ") values(?,?,?,?,?,now())";

            String m_strQryGefuEntry = "insert into gefu_file_generation ("
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
                    + ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,now(),?,?,?,?)";

            int i = 0;
            String m_change = "";
            if (m_ardy_exist_data) {
                if (m_ardy_exist_dcheque) {

                    log.info("NEW RF PDC MANUAL ENRY PDC_TRANSACTION_MASTER");

                    _prepStmnt = _currentCon.prepareStatement(m_strQryMstrEntry, Statement.RETURN_GENERATED_KEYS);
                    _prepStmnt.setString(1, "RF");
                    _prepStmnt.setString(2, m_Stridndb_customer_define_seller_id);
                    _prepStmnt.setString(3, m_Stridndb_customer_define_buyer_id);
                    _prepStmnt.setString(4, m_Strcust_bank);
                    _prepStmnt.setString(5, m_Strcust_bank_code);
                    _prepStmnt.setString(6, m_Strcust_branch);
                    _prepStmnt.setString(7, m_Strcust_branch_code);
                    _prepStmnt.setString(8, m_Strrf_chq_number);
                    _prepStmnt.setString(9, _system_date);
                    _prepStmnt.setString(10, m_Strrf_value_date_temp);
                    _prepStmnt.setString(11, m_Strrf_cheque_liq_date_temp);
                    _prepStmnt.setString(12, df.format(Double.parseDouble(m_Strrf_cheque_amu)));
                    _prepStmnt.setString(13, df.format(m_discounting_amu_from_cheque_amu));
                    _prepStmnt.setString(14, df.format(m_alternat_amu));
                    _prepStmnt.setString(15, df.format(m_alternat_amu));
                    _prepStmnt.setString(16, "ACTIVE");
                    _prepStmnt.setString(17, "UN-AUTHORIZED");
                    _prepStmnt.setString(18, "*");
                    _prepStmnt.setString(19, rec_finance_acc_num);
                    _prepStmnt.setString(20, cust_name);
                    _prepStmnt.setString(21, m_Strpdc_bank_name);
                    _prepStmnt.setString(22, m_Strpdc_branch_name);
                    _prepStmnt.setString(23, "DEACTIVE");
                    _prepStmnt.setString(24, "DEACTIVE");
                    _prepStmnt.setString(25, m_Strrf_value_date_temp);
                    _prepStmnt.setString(26, "NEWTXN");
                    _prepStmnt.setString(27, m_Strrf_cheque_modification);
                    _prepStmnt.setString(28, m_str_user_id);
                    _prepStmnt.setString(29, m_str_user_id);

                    log.info("RF PDC MANUAL ENRY PDC_TRANSACTION_MASTER : " + _prepStmnt);

                    if (_prepStmnt.executeUpdate() <= 0) {
                        rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                        log.error("ERROR OCCURED : UNABLE TO INSERT PDC_TRANSACTION_MASTER DATA.");
                        throw new Exception("ERROR OCCURED : UNABLE TO INSERT PDC_TRANSACTION_MASTER DATA.");

                    }
                    String max_idndb_pdc_txn_master = "";
                    _rs = _prepStmnt.getGeneratedKeys();
                    if (_rs.next()) {
                        max_idndb_pdc_txn_master = _rs.getString(1);
                        log.info("PDC_TRANSACTION_MASTER GENERATED ID_PDC_TRANSACTION_MASTER : " + max_idndb_pdc_txn_master);
                    }

                    if (!prm_obj.getString("rf_value_date").equals(m_Strrf_value_date_temp)) {
                        message = "2 ) " + prm_obj.getString("rf_value_date") + " date is a holiday and value date updated to next working day on " + m_Strrf_value_date_temp + "</br>" + message;
                    }

                    _prepStmnt = _currentCon.prepareStatement(m_strQryLog);
                    _prepStmnt.setString(1, "PDCTXN");
                    _prepStmnt.setString(2, max_idndb_pdc_txn_master);
                    _prepStmnt.setString(3, "1 ) NEW RF TXN ENTRY </br> " + message);
                    _prepStmnt.setString(4, "UN-AUTHORIZED");
                    _prepStmnt.setString(5, m_str_user_id);

                    if (_prepStmnt.executeUpdate() <= 0) {
                        rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                        log.info("ERROR OCCURED IN INSERT CHANGES LOG CHEQUE_NUMBER:" + m_Strrf_chq_number);
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    log.info("NEW RF PDC MANUAL. GEFU RF PDC DISBURSMENT ENTRIES PROCESSING START");
                    log.info("NEW RF PDC MANUAL. GEFU RF PDC DISBURSMENT ENTRIES PROCESSING START");

                    String gefu_account = "";
                    String gefu_currency = "LKR";
                    String gefu_date = "";
                    double gefu_amount = 0.00;
                    String gefu_narration = "";
                    String gefu_credit_debit = "";
                    String gefu_profit_centre = "";
                    String gefu_dao = "";
                    double gefu_c_amount = 0.00;
                    double gefu_d_amount = 0.00;
                    String gefu_rf_fixed_frequency = rf_fixed_frequency;
                    String gefu_rec_finance_bulk_credit = rec_finance_bulk_credit;

                    String[] gefu_date_arr = _system_date.split("/");
                    String gefu_day = gefu_date_arr[0];
                    String gefu_month = gefu_date_arr[1];
                    String gefu_year = gefu_date_arr[2];
                    gefu_date = gefu_year + gefu_month + gefu_day;

                    gefu_amount = m_discounting_amu_from_cheque_amu;
                    gefu_account = rec_finance_acc_num;
                    gefu_d_amount = m_discounting_amu_from_cheque_amu;
                    gefu_c_amount = 0.00;
                    gefu_credit_debit = "D";
                    gefu_narration = "DISB" + m_Strrf_chq_number + "RS" + m_Strrf_cheque_amu + buyer_name;

                    log.info("NEW RF PDC MANUAL. GEFU RF PDC DISBURSMENT ENTRIES. SELLER REC FINANCE ACCOUNT DEBIT ENTRY.");

                    _prepStmnt = _currentCon.prepareStatement(m_strQryGefuEntry);
                    _prepStmnt.setString(1, max_idndb_pdc_txn_master);
                    _prepStmnt.setString(2, gefu_account);
                    _prepStmnt.setString(3, gefu_currency);
                    _prepStmnt.setString(4, gefu_date);
                    _prepStmnt.setString(5, df.format(gefu_amount));
                    _prepStmnt.setString(6, gefu_narration);
                    _prepStmnt.setString(7, gefu_credit_debit);
                    _prepStmnt.setString(8, gefu_profit_centre);
                    _prepStmnt.setString(9, gefu_dao);
                    _prepStmnt.setString(10, df.format(gefu_c_amount));
                    _prepStmnt.setString(11, df.format(gefu_d_amount));
                    _prepStmnt.setString(12, "ACTIVE");
                    _prepStmnt.setString(13, "UN-AUTHORIZED");
                    _prepStmnt.setString(14, m_str_user_id);
                    _prepStmnt.setString(15, m_str_user_id);
                    _prepStmnt.setString(16, _system_date);
                    _prepStmnt.setString(17, "RFCDCD");
                    _prepStmnt.setString(18, "NO");
                    _prepStmnt.setString(19, gefu_rf_fixed_frequency);

                    log.info("NEW RF PDC MANUAL. GEFU RF PDC DISBURSMENT ENTRIES. SELLER REC FINANCE ACCOUNT DEBIT ENTRY. :" + _prepStmnt);

                    if (_prepStmnt.executeUpdate() <= 0) {
                        rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                        log.error("ERROR OCCURED: UNABLE TO INSERT SELLER REC FIN ACC DEBIT ENTRY.");
                        throw new Exception("ERROR OCCURED: UNABLE TO INSERT SELLER REC FIN ACC DEBIT ENTRY.");
                    }

                    gefu_amount = m_discounting_amu_from_cheque_amu;
                    gefu_account = rec_finance_cr_dsc_proc_acc_num;
                    gefu_credit_debit = "C";
                    gefu_d_amount = 0.00;
                    gefu_c_amount = m_discounting_amu_from_cheque_amu;
                    gefu_narration = "DISB" + m_Strrf_chq_number + "RS" + m_Strrf_cheque_amu + buyer_name;

                    log.info("NEW RF PDC MANUAL. GEFU RF PDC DISBURSMENT ENTRIES. SELLER COLLECTION ACCOUNT CREDIT ENTRY. :");

                    _prepStmnt = _currentCon.prepareStatement(m_strQryGefuEntry);
                    _prepStmnt.setString(1, max_idndb_pdc_txn_master);
                    _prepStmnt.setString(2, gefu_account);
                    _prepStmnt.setString(3, gefu_currency);
                    _prepStmnt.setString(4, gefu_date);
                    _prepStmnt.setString(5, df.format(gefu_amount));
                    _prepStmnt.setString(6, gefu_narration);
                    _prepStmnt.setString(7, gefu_credit_debit);
                    _prepStmnt.setString(8, gefu_profit_centre);
                    _prepStmnt.setString(9, gefu_dao);
                    _prepStmnt.setString(10, df.format(gefu_c_amount));
                    _prepStmnt.setString(11, df.format(gefu_d_amount));
                    _prepStmnt.setString(12, "ACTIVE");
                    _prepStmnt.setString(13, "UN-AUTHORIZED");
                    _prepStmnt.setString(14, m_str_user_id);
                    _prepStmnt.setString(15, m_str_user_id);
                    _prepStmnt.setString(16, _system_date);
                    _prepStmnt.setString(17, "RFCDBC");
                    _prepStmnt.setString(18, gefu_rec_finance_bulk_credit);
                    _prepStmnt.setString(19, gefu_rf_fixed_frequency);

                    log.info("NEW RF PDC MANUAL. GEFU RF PDC DISBURSMENT ENTRIES. SELLER COLLECTION ACCOUNT CREDIT ENTRY. :" + _prepStmnt);

                    if (_prepStmnt.executeUpdate() <= 0) {
                        rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                        log.error("ERROR OCCURED: UNABLE TO INSERT SELLER COLL ACC CREDIT ENTRY.");
                        throw new Exception("ERROR OCCURED: UNABLE TO INSERT SELLER COLL ACC CREDIT ENTRY.");

                    }

                    rf_pdc_save_status = "1100/" + m_Stridndb_customer_define_seller_id;
                    log.info("NEW RF PDC MANUAL. PDC_TXN_MASTER AND GEFU ENTRIES PROCESSED.");
                    // end
                }

                if (!endConnection(_currentCon)) {
                    rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                    log.error("ERROR OCCURED : UNABLE TO COMMIT TRANSANS TO DB.");
                    throw new Exception("ERROR OCCURED : UNABLE TO COMMIT TRANSANS TO DB.");
                }

            } else {

                log.info("RF PDC MANUAL UPDATE REQUEST FOR IDNDB_PDC_TXN_MASTER : " + m_str_idndb_pdc_txn_master);

                if (!m_str_idndb_pdc_txn_master.equals(idndb_pdc_txn_master_uniq) && (!idndb_pdc_txn_master_uniq.equals(""))) {
                    rf_pdc_save_status = "1101/" + m_Stridndb_customer_define_seller_id;
                    throw new Exception("ERROR OCCURED : INVALID IDNDB_PDC_TXN_MASTER_ID FOR CRF PDC-UPDATE.");
                }

                String m_condition = "";

                if (m_Strrf_cheque_modification.equals("UPD")) {

                    if (!m_StrOldcust_bank.equals(m_Strcust_bank)) {
                        m_condition = m_condition + "idndb_bank_master_file='" + m_Strcust_bank + "',";
                    }
                    if (!m_StrOldcust_bank_code.equals(m_Strcust_bank_code)) {
                        m_condition = m_condition + "pdc_bank_name='" + m_Strpdc_bank_name + "',";
                        m_condition = m_condition + "pdc_bank_code='" + m_Strcust_bank_code + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldcust_bank_code + " BANK CODE CHANGE TO " + m_Strcust_bank_code + "<br>";

                    }
                    if (!m_StrOldcust_branch.equals(m_Strcust_branch)) {
                        m_condition = m_condition + "idndb_branch_master_file='" + m_Strcust_branch + "',";
                    }
                    if (!m_StrOldcust_branch_code.equals(m_Strcust_branch_code)) {
                        m_condition = m_condition + "pdc_branch_name='" + m_Strpdc_branch_name + "',";
                        m_condition = m_condition + "pdc_branch_code='" + m_Strcust_branch_code + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldcust_branch_code + " BRANCH CODE CHANGE TO " + m_Strcust_branch_code + "<br>";

                    }
                    if (!m_StrOldrf_chq_number.equals(m_Strrf_chq_number)) {
                        m_condition = m_condition + "pdc_chq_number='" + m_Strrf_chq_number + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrf_chq_number + " CHQ NUM. CHANGE TO " + m_Strrf_chq_number + "<br>";

                    }

                    if (!(Double.parseDouble(m_StrOldrf_cheque_amu) == Double.parseDouble(m_Strrf_cheque_amu))) {
                        m_condition = m_condition + "pdc_chq_amu='" + m_Strrf_cheque_amu + "',";
                        m_condition = m_condition + "pdc_chq_discounting_amu='" + m_discounting_amu_from_cheque_amu + "',";
                        m_condition = m_condition + "pdc_chq_net_amu='" + m_alternat_amu + "',";
                        m_condition = m_condition + "pdc_chq_cr_amu='" + m_alternat_amu + "',";

                        i++;
                        m_change = m_change + i + ")" + df_report.format(Double.parseDouble(m_StrOldrf_cheque_amu)) + " CHQ AMU. CHANGE TO " + df_report.format(Double.parseDouble(m_Strrf_cheque_amu)) + " <br>";

                    }

                    if (!m_StrOldrf_value_date.equals(m_Strrf_value_date_temp)) {
                        m_condition = m_condition + "pdc_value_date='" + m_Strrf_value_date_temp + "',";
                        m_condition = m_condition + "pdc_old_value_date='" + m_Strrf_value_date_temp + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrf_value_date + " VALUE DATE CHANGE TO " + m_Strrf_value_date_temp + "<br>";

                    }

                    if (!m_StrOldrf_cheque_liq_date.equals(m_Strrf_cheque_liq_date_temp)) {
                        m_condition = m_condition + "pdc_lquid_date='" + m_Strrf_cheque_liq_date_temp + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrf_cheque_liq_date + "LIQD DATE CHANGE TO " + m_Strrf_cheque_liq_date_temp + "<br>";

                    }

                    m_condition = m_condition + "pdc_latest_modification='UPD',";

                }

                if (m_Strrf_cheque_modification.equals("VDE")) {

                    m_condition = m_condition + "pdc_value_date='" + m_Strrf_value_date_temp + "',";
                    m_condition = m_condition + "pdc_value_date_ext='ACTIVE',";
                    m_condition = m_condition + "pdc_latest_modification='VDE',";
                    m_condition = m_condition + "pdc_old_value_date='" + m_StrOldrf_value_date + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldrf_value_date + "VALUE DATE EXTENDED TO " + m_Strrf_value_date_temp + "<br>";

                    if (!m_StrOldrf_cheque_liq_date.equals(m_Strrf_cheque_liq_date_temp)) {
                        m_condition = m_condition + "pdc_lquid_date='" + m_Strrf_cheque_liq_date_temp + "',";
                        i++;
                        m_change = m_change + i + ")" + m_StrOldrf_cheque_liq_date + "LIQUIDATE DATE CHANGE TO " + m_Strrf_cheque_liq_date_temp + "<br>";

                    }
                }

                if (m_Strrf_cheque_modification.equals("ELQ")) {
                    m_condition = m_condition + "pdc_chq_status='ERLYLIQUDED',";
                    m_condition = m_condition + "pdc_latest_modification='ELQ',";
                    i++;
                    m_change = m_change + i + ") PDC CHEQUE EARLY LIQUIDATED <br>";

                }
                if (!message.equals("")) {
                    i++;
                    m_change = m_change + i + ") " + message + " <br>";

                }

                if ((m_Strrf_cheque_modification.equals("ELQ") && pdc_txn_mod.equals("AUTHORIZED")) && (!(m_StrOldrf_value_date.equals(m_Strrf_value_date_temp)) && pdc_txn_mod.equals("AUTHORIZED"))) {
                    rf_pdc_save_status = "1103/" + m_Stridndb_customer_define_seller_id;
                    throw new Exception("Error Occured in insert user-roles");

                }

                if (m_Strrf_cheque_modification.equals("VDE") && pdc_txn_mod.equals("AUTHORIZED")) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date date1 = sdf.parse(m_StrOldrf_value_date);
                    Date date2 = sdf.parse(m_Strrf_value_date_temp);

                    if (date1.compareTo(date2) > 0) {
                        rf_pdc_save_status = "1102/" + m_Stridndb_customer_define_seller_id;
                        throw new Exception("Error Occured in insert user-roles");

                    }

                }

                m_strQry = "update ndb_pdc_txn_master set " + m_condition + " pdc_chq_status_auth='UN-AUTHORIZED',pdc_txn_mod='NEWTXN', "
                        + "pdc_chq_batch_no='*',"
                        + "pdc_chq_mod_by='" + m_str_user_id + "',"
                        + "pdc_chq_mod_date=now()"
                        + " where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";
                _prepStmnt = _currentCon.prepareStatement(m_strQry);

                if (_prepStmnt.executeUpdate() <= 0) {
                    rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;

                    log.error("ERROR OCCURED IN UPDATE NDB_PDC_TXN_MASTER DATA TABLE CHQ NUMBER:" + m_Strrf_chq_number);
                    throw new Exception("ERROR OCCURED IN UPDATE NDB_PDC_TXN_MASTER DATA TABLE CHQ NUMBER:" + m_Strrf_chq_number);
                }

                _prepStmnt = _currentCon.prepareStatement(m_strQryLog);
                _prepStmnt.setString(1, "PDCTXN");
                _prepStmnt.setString(2, m_str_idndb_pdc_txn_master);
                _prepStmnt.setString(3, m_change);
                _prepStmnt.setString(4, "UN-AUTHORIZED");
                _prepStmnt.setString(5, m_str_user_id);

                if (_prepStmnt.executeUpdate() <= 0) {
                    rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                    log.info("ERROR OCCURED IN INSERT CHANGES LOG CHEQUE_NUMBER:" + m_Strrf_chq_number);
                    throw new Exception("Error Occured in insert user-roles");
                }

                if (!(Double.parseDouble(m_StrOldrf_cheque_amu) == Double.parseDouble(m_Strrf_cheque_amu))) {

                    double gefu_amount = m_discounting_amu_from_cheque_amu;
                    String gefu_narration = "DISB" + m_Strrf_chq_number + "RS" + m_Strrf_cheque_amu + buyer_name;

                    log.info("PDC_REF_ENTRY AMOUNT CHANGED. GEFU CUSTOMER REC FIN ACCOUNT ENTRY UPDATE PROCESS.");
                    m_strQry = "update gefu_file_generation set amount=?"
                            + ",d_amount=?"
                            + ",narration=?"
                            + ",status=?"
                            + ",mod_by=?"
                            + ",mod_date=NOW()"
                            + " where idndb_pdc_txn_master=? and gefu_creation_status=? and credit_debit=? and gefu_type=?";
                    _prepStmnt = _currentCon.prepareStatement(m_strQry);
                    _prepStmnt.setString(1, df.format(gefu_amount));
                    _prepStmnt.setString(2, df.format(gefu_amount));
                    _prepStmnt.setString(3, gefu_narration);
                    _prepStmnt.setString(4, "UN-AUTHORIZED");
                    _prepStmnt.setString(5, m_str_user_id);
                    _prepStmnt.setString(6, m_str_idndb_pdc_txn_master);
                    _prepStmnt.setString(7, "ACTIVE");
                    _prepStmnt.setString(8, "D");
                    _prepStmnt.setString(9, "RFCDCD");

                    if (_prepStmnt.executeUpdate() <= 0) {
                        rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                        throw new Exception("ERROR OCCURED : UNABLE TO UPDATE REC FIN ACCOUNT ENTRY UPDATE.");
                    }

                    log.info("PDC_REF_ENTRY AMOUNT CHANGED. GEFU CUSTOMER COLLECTION ACCOUNT ENTRY UPDATE PROCESS.");

                    m_strQry = "update gefu_file_generation set amount=?"
                            + ",c_amount=?"
                            + ",narration=?"
                            + ",status=?"
                            + ",mod_by=?"
                            + ",mod_date=NOW()"
                            + " where idndb_pdc_txn_master=? and gefu_creation_status=? and credit_debit=? and gefu_type=?";
                    _prepStmnt = _currentCon.prepareStatement(m_strQry);

                    _prepStmnt.setString(1, df.format(gefu_amount));
                    _prepStmnt.setString(2, df.format(gefu_amount));
                    _prepStmnt.setString(3, gefu_narration);
                    _prepStmnt.setString(4, "UN-AUTHORIZED");
                    _prepStmnt.setString(5, m_str_user_id);
                    _prepStmnt.setString(6, m_str_idndb_pdc_txn_master);
                    _prepStmnt.setString(7, "ACTIVE");
                    _prepStmnt.setString(8, "C");
                    _prepStmnt.setString(9, "RFCDBC");

                    if (_prepStmnt.executeUpdate() <= 0) {
                        rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                        throw new Exception("ERROR OCCURED : UNABLE TO UPDATE CUST COLLECTION ACCOUNT ENTRY UPDATE.");
                    }

                }

                if (m_Strrf_cheque_modification.equals("VDE") && pdc_txn_mod.equals("AUTHORIZED")) {
                    if (!(rec_finance_vale_dte_extr_chg == 0.00)) {
                        log.info("ACC. ENTRY :PDC RF VALUE DATE EXTENTION CHARGES ENTRY PROCESSING START.");
                        String gefu_account = rec_finance_curr_ac;
                        String gefu_currency = "LKR";
                        String gefu_date = "";
                        double gefu_amount = rec_finance_vale_dte_extr_chg;
                        String gefu_narration = "";
                        String gefu_credit_debit = "";
                        String gefu_profit_centre = "";
                        String gefu_dao = "";
                        double gefu_c_amount = 0.00;
                        double gefu_d_amount = 0.00;
                        String gefu_rf_fixed_frequency = rf_fixed_frequency;

                        String[] gefu_date_arr = _system_date.split("/");
                        String gefu_day = gefu_date_arr[0];
                        String gefu_month = gefu_date_arr[1];
                        String gefu_year = gefu_date_arr[2];
                        gefu_date = gefu_year + gefu_month + gefu_day;

                        gefu_d_amount = gefu_amount;
                        gefu_credit_debit = "D";
                        gefu_narration = "VDE" + m_Strrf_chq_number + "RS" + m_Strrf_cheque_amu + buyer_name;

                        log.info("ACC. ENTRY :PDC RF VALUE DATE EXTENTION CHARGES ENTRY. DEBIT CUST CURRENT ACCOUNT.");

                        _prepStmnt = _currentCon.prepareStatement(m_strQryGefuEntry);
                        _prepStmnt.setString(1, m_str_idndb_pdc_txn_master);
                        _prepStmnt.setString(2, gefu_account);
                        _prepStmnt.setString(3, gefu_currency);
                        _prepStmnt.setString(4, gefu_date);
                        _prepStmnt.setString(5, df.format(gefu_amount));
                        _prepStmnt.setString(6, gefu_narration);
                        _prepStmnt.setString(7, gefu_credit_debit);
                        _prepStmnt.setString(8, gefu_profit_centre);
                        _prepStmnt.setString(9, gefu_dao);
                        _prepStmnt.setString(10, df.format(gefu_c_amount));
                        _prepStmnt.setString(11, df.format(gefu_d_amount));
                        _prepStmnt.setString(12, "ACTIVE");
                        _prepStmnt.setString(13, "UN-AUTHORIZED");
                        _prepStmnt.setString(14, m_str_user_id);
                        _prepStmnt.setString(15, m_str_user_id);
                        _prepStmnt.setString(16, _system_date);
                        _prepStmnt.setString(17, "VDEXTRCD");
                        _prepStmnt.setString(18, "NO");
                        _prepStmnt.setString(19, gefu_rf_fixed_frequency);

                        log.info("ACC. ENTRY :PDC RF VALUE DATE EXTENTION CHARGES ENTRY. DEBIT CUST CURRENT ACCOUNT. :" + _prepStmnt);

                        if (_prepStmnt.executeUpdate() <= 0) {
                            rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                            log.error("ERROR OCCURED: UNABLE TO INSERT SELLER REC FIN CURRENT ACCOUNT DEBIT ENTRY.");
                            throw new Exception("ERROR OCCURED: UNABLE TO INSERT SELLER REC FIN CURRENT ACCOUNT DEBIT ENTRY.");
                        }

                        gefu_d_amount = 0.00;
                        gefu_c_amount = gefu_amount;
                        gefu_account = NDBCommisionPLAcc;
                        gefu_credit_debit = "C";
                        gefu_profit_centre = "PL";
                        gefu_dao = acc_officer_code;
                        gefu_narration = "VDE" + m_Strrf_chq_number + "RS" + m_Strrf_cheque_amu + buyer_name;

                        log.info("ACC. ENTRY :PDC RF VALUE DATE EXTENTION CHARGES ENTRY. CREDIT NDB PL ACCOUNT.");

                        _prepStmnt = _currentCon.prepareStatement(m_strQryGefuEntry);
                        _prepStmnt.setString(1, m_str_idndb_pdc_txn_master);
                        _prepStmnt.setString(2, gefu_account);
                        _prepStmnt.setString(3, gefu_currency);
                        _prepStmnt.setString(4, gefu_date);
                        _prepStmnt.setString(5, df.format(gefu_amount));
                        _prepStmnt.setString(6, gefu_narration);
                        _prepStmnt.setString(7, gefu_credit_debit);
                        _prepStmnt.setString(8, gefu_profit_centre);
                        _prepStmnt.setString(9, gefu_dao);
                        _prepStmnt.setString(10, df.format(gefu_c_amount));
                        _prepStmnt.setString(11, df.format(gefu_d_amount));
                        _prepStmnt.setString(12, "ACTIVE");
                        _prepStmnt.setString(13, "UN-AUTHORIZED");
                        _prepStmnt.setString(14, m_str_user_id);
                        _prepStmnt.setString(15, m_str_user_id);
                        _prepStmnt.setString(16, _system_date);
                        _prepStmnt.setString(17, "VDEXTRBC");
                        _prepStmnt.setString(18, "NO");
                        _prepStmnt.setString(19, gefu_rf_fixed_frequency);

                        log.info("ACC. ENTRY :PDC RF VALUE DATE EXTENTION CHARGES ENTRY. CREDIT NDB PL ACCOUNT. :" + _prepStmnt);

                        if (_prepStmnt.executeUpdate() <= 0) {
                            rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                            log.error("ERROR OCCURED: UNABLE TO INSERT NDB PLT ACCOUNT CREDIT ENTRY.");
                            throw new Exception("ERROR OCCURED: UNABLE TO INSERT NDB PLT ACCOUNT CREDIT ENTRY.");
                        }

                    }

                }
                if (m_Strrf_cheque_modification.equals("ELQ")) {
                    log.info("ACC. ENTRY : ERLY LEQUDATION ENTRIES PROCESSING.");
                    if (!(rec_finance_erly_wdr_chg == 0.00)) {
                        String gefu_account = rec_finance_curr_ac;
                        String gefu_currency = "LKR";
                        String gefu_date = "";
                        double gefu_amount = rec_finance_erly_wdr_chg;
                        String gefu_narration = "";
                        String gefu_credit_debit = "";
                        String gefu_profit_centre = "";
                        String gefu_dao = "";
                        double gefu_c_amount = 0.00;
                        double gefu_d_amount = 0.00;
                        String gefu_system_date = _system_date;
                        String gefu_rf_fixed_frequency = rf_fixed_frequency;

                        gefu_d_amount = gefu_amount;
                        String[] gefu_date_arr = _system_date.split("/");
                        String gefu_day = gefu_date_arr[0];
                        String gefu_month = gefu_date_arr[1];
                        String gefu_year = gefu_date_arr[2];
                        gefu_date = gefu_year + gefu_month + gefu_day;
                        gefu_narration = "EL" + m_Strrf_chq_number + "RS" + m_Strrf_cheque_amu + buyer_name;
                        gefu_credit_debit = "D";

                        log.info("ACC. ENTRY : ERLY LEQUDATION CHARGES ENTRIES DEBIT CUSTOMER REC FIN CURRENT ACCOUNT.");

                        _prepStmnt = _currentCon.prepareStatement(m_strQryGefuEntry);
                        _prepStmnt.setString(1, m_str_idndb_pdc_txn_master);
                        _prepStmnt.setString(2, gefu_account);
                        _prepStmnt.setString(3, gefu_currency);
                        _prepStmnt.setString(4, gefu_date);
                        _prepStmnt.setString(5, df.format(gefu_amount));
                        _prepStmnt.setString(6, gefu_narration);
                        _prepStmnt.setString(7, gefu_credit_debit);
                        _prepStmnt.setString(8, gefu_profit_centre);
                        _prepStmnt.setString(9, gefu_dao);
                        _prepStmnt.setString(10, df.format(gefu_c_amount));
                        _prepStmnt.setString(11, df.format(gefu_d_amount));
                        _prepStmnt.setString(12, "ACTIVE");
                        _prepStmnt.setString(13, "UN-AUTHORIZED");
                        _prepStmnt.setString(14, m_str_user_id);
                        _prepStmnt.setString(15, m_str_user_id);
                        _prepStmnt.setString(16, _system_date);
                        _prepStmnt.setString(17, "ERLEQDCD");
                        _prepStmnt.setString(18, "NO");
                        _prepStmnt.setString(19, gefu_rf_fixed_frequency);

                        log.info("ACC. ENTRY : ERLY LEQUDATION CHARGES ENTRIES DEBIT CUSTOMER REC FIN CURRENT ACCOUNT." + _prepStmnt);

                        if (_prepStmnt.executeUpdate() <= 0) {
                            rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                            log.error("ERROR OCCURED: UNABLE TO INSERT NDB CUST REC FIN CURRENT ACCOUNT DBIT ENTRY.");
                            throw new Exception("ERROR OCCURED: UNABLE TO INSERT NDB CUST REC FIN CURRENT ACCOUNT DBIT ENTRY.");
                        }

                        gefu_d_amount = 0.00;
                        gefu_c_amount = gefu_amount;
                        gefu_account = NDBCommisionPLAcc;
                        gefu_credit_debit = "C";
                        gefu_profit_centre = "PL";
                        gefu_dao = acc_officer_code;
                        gefu_narration = "EL" + m_Strrf_chq_number + "RS" + m_Strrf_cheque_amu + buyer_name;

                        log.info("ACC. ENTRY : ERLY LEQUDATION CHARGES ENTRIES CREDIT NDB PL ACCOUNT.");

                        _prepStmnt = _currentCon.prepareStatement(m_strQryGefuEntry);
                        _prepStmnt.setString(1, m_str_idndb_pdc_txn_master);
                        _prepStmnt.setString(2, gefu_account);
                        _prepStmnt.setString(3, gefu_currency);
                        _prepStmnt.setString(4, gefu_date);
                        _prepStmnt.setString(5, df.format(gefu_amount));
                        _prepStmnt.setString(6, gefu_narration);
                        _prepStmnt.setString(7, gefu_credit_debit);
                        _prepStmnt.setString(8, gefu_profit_centre);
                        _prepStmnt.setString(9, gefu_dao);
                        _prepStmnt.setString(10, df.format(gefu_c_amount));
                        _prepStmnt.setString(11, df.format(gefu_d_amount));
                        _prepStmnt.setString(12, "ACTIVE");
                        _prepStmnt.setString(13, "UN-AUTHORIZED");
                        _prepStmnt.setString(14, m_str_user_id);
                        _prepStmnt.setString(15, m_str_user_id);
                        _prepStmnt.setString(16, _system_date);
                        _prepStmnt.setString(17, "ERLEQDBC");
                        _prepStmnt.setString(18, "NO");
                        _prepStmnt.setString(19, gefu_rf_fixed_frequency);

                        log.info("ACC. ENTRY : ERLY LEQUDATION CHARGES ENTRIES CREDIT NDB PL ACCOUNT." + _prepStmnt);

                        if (_prepStmnt.executeUpdate() <= 0) {
                            rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                            log.error("ERROR OCCURED: UNABLE TO INSERT LIQUDATE CHARGES ENTRY CREDIT NDB PL ACCOUNT.");
                            throw new Exception("ERROR OCCURED: UNABLE TO INSERT LIQUDATE CHARGES ENTRY CREDIT NDB PL ACCOUNT.");
                        }

                    }

                    log.info("PDC RF EARLY LIQUDATION LOAN RECOVERY ENTRIES PROCESS START");

                    String gefu_account = "";
                    double gefu_amount = 0.00;
                    String gefu_narration = "";
                    String gefu_currency = "LKR";
                    String gefu_credit_debit = "";
                    String gefu_profit_centre = "";
                    String gefu_dao = "";
                    double gefu_c_amount = 0.00;
                    double gefu_d_amount = 0.00;

                    gefu_account = "";
                    gefu_amount = 0.00;
                    gefu_narration = "";
                    gefu_credit_debit = "";
                    gefu_profit_centre = "";
                    gefu_dao = "";
                    gefu_c_amount = 0.00;
                    gefu_d_amount = 0.00;

                    String[] gefu_date_arr = _system_date.split("/");
                    String gefu_day = gefu_date_arr[0];
                    String gefu_month = gefu_date_arr[1];
                    String gefu_year = gefu_date_arr[2];
                    String gefu_date = gefu_year + gefu_month + gefu_day;

                    gefu_amount = m_Olddiscounting_amu_from_cheque_amu;
                    gefu_account = rec_finance_curr_ac;
                    gefu_credit_debit = "D";
                    gefu_narration = "EL" + m_Strrf_chq_number + "RS" + m_Strrf_cheque_amu + buyer_name;
                    gefu_d_amount = gefu_amount;

                    log.info("ACC. ENTRY :EARLY LIQUIDATION LOAN RECOVERY DEBIT CUSTOMER REC FIN CURRENT ACCOUNT ENTRY.");

                    _prepStmnt = _currentCon.prepareStatement(m_strQryGefuEntry);
                    _prepStmnt.setString(1, m_str_idndb_pdc_txn_master);
                    _prepStmnt.setString(2, gefu_account);
                    _prepStmnt.setString(3, gefu_currency);
                    _prepStmnt.setString(4, gefu_date);
                    _prepStmnt.setString(5, df.format(gefu_amount));
                    _prepStmnt.setString(6, gefu_narration);
                    _prepStmnt.setString(7, gefu_credit_debit);
                    _prepStmnt.setString(8, gefu_profit_centre);
                    _prepStmnt.setString(9, gefu_dao);
                    _prepStmnt.setString(10, df.format(gefu_c_amount));
                    _prepStmnt.setString(11, df.format(gefu_d_amount));
                    _prepStmnt.setString(12, "ACTIVE");
                    _prepStmnt.setString(13, "UN-AUTHORIZED");
                    _prepStmnt.setString(14, m_str_user_id);
                    _prepStmnt.setString(15, m_str_user_id);
                    _prepStmnt.setString(16, _system_date);
                    _prepStmnt.setString(17, "RFCDRETCD");
                    _prepStmnt.setString(18, "NO");
                    _prepStmnt.setString(19, "DAILY");

                    log.info("ACC. ENTRY :EARLY LIQUIDATION LOAN RECOVERY DEBIT CUSTOMER REC FIN CURRENT ACCOUNT ENTRY. " + _prepStmnt);

                    if (_prepStmnt.executeUpdate() <= 0) {
                        rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                        log.error("ERROR OCCURED: UNABLE TO INSERT SELLER REC FIN CURRENT ACC DEBIT ENTRY");
                        throw new Exception("ERROR OCCURED: UNABLE TO INSERT SELLER REC FIN CURRENT ACC DEBIT ENTRY");
                    }

                    gefu_account = rec_finance_acc_num;
                    gefu_c_amount = m_Olddiscounting_amu_from_cheque_amu;
                    gefu_d_amount = 0.00;
                    gefu_credit_debit = "C";
                    gefu_narration = "EL" + m_Strrf_chq_number + "RS" + m_Strrf_cheque_amu + buyer_name;

                    log.info("ACC. ENTRY :EARLY LIQUIDATION LOAN RECOVERY CREDIT CUSTOMER REC FIN ACCOUNT ENTRY.");

                    _prepStmnt = _currentCon.prepareStatement(m_strQryGefuEntry);
                    _prepStmnt.setString(1, m_str_idndb_pdc_txn_master);
                    _prepStmnt.setString(2, gefu_account);
                    _prepStmnt.setString(3, gefu_currency);
                    _prepStmnt.setString(4, gefu_date);
                    _prepStmnt.setString(5, df.format(gefu_amount));
                    _prepStmnt.setString(6, gefu_narration);
                    _prepStmnt.setString(7, gefu_credit_debit);
                    _prepStmnt.setString(8, gefu_profit_centre);
                    _prepStmnt.setString(9, gefu_dao);
                    _prepStmnt.setString(10, df.format(gefu_c_amount));
                    _prepStmnt.setString(11, df.format(gefu_d_amount));
                    _prepStmnt.setString(12, "ACTIVE");
                    _prepStmnt.setString(13, "UN-AUTHORIZED");
                    _prepStmnt.setString(14, m_str_user_id);
                    _prepStmnt.setString(15, m_str_user_id);
                    _prepStmnt.setString(16, _system_date);
                    _prepStmnt.setString(17, "RFCDRETBC");
                    _prepStmnt.setString(18, "NO");
                    _prepStmnt.setString(19, "DAILY");

                    log.info("ACC. ENTRY :EARLY LIQUIDATION LOAN RECOVERY CREDIT CUSTOMER REC FIN ACCOUNT ENTRY. :" + _prepStmnt);

                    if (_prepStmnt.executeUpdate() <= 0) {
                        rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                        log.error("ERROR OCCURED: UNABLE TO INSERT SELLER REC FIN ACC ACCOUNT CREDIT ENTRY");
                        throw new Exception("ERROR OCCURED: UNABLE TO INSERT SELLER REC FIN ACC ACCOUNT CREDIT ENTRY");
                    }

                }

                rf_pdc_save_status = "1100/" + m_Stridndb_customer_define_seller_id;
                if (!endConnection(_currentCon)) {
                    rf_pdc_save_status = "0000/" + m_Stridndb_customer_define_seller_id;
                    throw new Exception("ERROR OCCURED UNABLE TO COMMIT TRANCSTIOSN TO DB");
                }

            }

        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("ERROR OCCURED UNABLE TO ABORT THE CONNECTION. " + e);
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_prepStmnt != null) {
                    _prepStmnt.close();
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
        DecimalFormat df = new DecimalFormat("###.00");

        String m_str_user_id = "";
        String message = "";
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
            pdcDAO dao = new pdcDAO();

            log.info("CW to RF PDC conversion receved");

            m_str_user_id = prm_obj.getString("user_id");
            message = prm_obj.getString("message");
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
            m_strQry = "select * from ndb_holiday_marker where ndb_holiday_approval='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                rf_pdc_save_status = "1104";
                throw new Exception("Error Occured in saving pdc entry");
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

            String m_uniq_id = m_Stridndb_customer_define_seller_id + m_Stridndb_customer_define_buyer_id + m_Strrf_chq_number + m_Strcust_bank_code + m_Strcust_branch_code;
            m_strQry = "SELECT CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code) as txn_master_unq_id FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_chq_status_auth in ('AUTHORIZED','UN-AUTHORIZED')";
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
                    m_condition = m_condition + "pdc_chq_amu='" + df.format(m_Strrf_cheque_amu) + "',";
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
                if (!message.equals("")) {
                    i++;
                    m_change = m_change + i + ") " + message + " <br>";

                }

                i++;
                m_change = m_change + i + ") CW CHANGE TO RF <br>";

                m_strQry = "update ndb_pdc_txn_master set " + m_condition + " pdc_chq_status='ACTIVE',pdc_chq_status_auth='UN-AUTHORIZED',"
                        + "pdc_req_financing='RF',pdc_chq_batch_no='*',cust_account_number='" + rec_finance_acc_num + "',pdc_chq_discounting_amu='" + m_discounting_amu_from_cheque_amu + "',pdc_chq_net_amu='" + m_alternat_amu + "',pdc_chq_cr_amu='" + m_alternat_amu + "',pdc_booking_date='" + _system_date + "',"
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
//                log.info("Start of ACC Commision entries");
//
                String max_idndb_pdc_txn_master = m_str_idndb_pdc_txn_master;
                String account = "";
                String currency = "LKR";
                String date = "";
                double amount = 0.00;
                String narration = "COMM" + cust_name;
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
//
//                // cutomer debit entry
//                if (rec_finance_commision_crg.equals("TRANSACTION BASED")) {
//
//                    if (!(rf_tran_base_falt_fee == 0.00)) {
//                        amount = rf_tran_base_falt_fee;
//
//                    } else {
//                        amount = Double.parseDouble(m_Strrf_cheque_amu) / 100 * rf_tran_base_from_tran;
//
//                    }
//
//                    d_amount = amount;
//                    account = rec_finance_curr_ac;
//
//                    log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
//                    log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");
//
//                    m_strQry = "insert into gefu_file_generation ("
//                            + "idndb_pdc_txn_master"
//                            + ",account"
//                            + ",currency"
//                            + ",date"
//                            + ",amount"
//                            + ",narration"
//                            + ",credit_debit"
//                            + ",profit_centre"
//                            + ",DAO"
//                            + ",c_amount"
//                            + ",d_amount"
//                            + ",gefu_creation_status"
//                            + ",status"
//                            + ",creat_by"
//                            + ",creat_date"
//                            + ",mod_by"
//                            + ",mod_date"
//                            + ",system_date"
//                            + ",gefu_type"
//                            + ",bulk_credit"
//                            + ",cw_fixed_frequency"
//                            + ") values("
//                            + "'" + max_idndb_pdc_txn_master + "',"
//                            + "'" + account + "',"
//                            + "'" + currency + "',"
//                            + "'" + date + "',"
//                            + "'" + df.format(amount) + "',"
//                            + "'" + narration + "',"
//                            + "'D',"
//                            + "'" + profit_centre + "',"
//                            + "'" + DAO + "',"
//                            + "'" + df.format(c_amount) + "',"
//                            + "'" + df.format(d_amount) + "',"
//                            + "'ACTIVE',"
//                            + "'UN-AUTHORIZED',"
//                            + "'" + m_str_user_id + "',"
//                            + "NOW(),"
//                            + "'" + m_str_user_id + "',"
//                            + "NOW(),"
//                            + "'" + system_date + "',"
//                            + "'COMCHGCD',"
//                            + "'NO',"
//                            + "'" + gefu_rf_fixed_frequency + "')";
//
//                    log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry);
//
//                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
//                        throw new Exception("Error Occured in insert user-roles");
//                    }
//
//                    d_amount = 0.00;
//                    c_amount = amount;
//                    account = NDBCommisionPLAcc;
//
//                    log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGCD");
//
//                    // ndb credit entry
//                    m_strQry = "insert into gefu_file_generation ("
//                            + "idndb_pdc_txn_master"
//                            + ",account"
//                            + ",currency"
//                            + ",date"
//                            + ",amount"
//                            + ",narration"
//                            + ",credit_debit"
//                            + ",profit_centre"
//                            + ",DAO"
//                            + ",c_amount"
//                            + ",d_amount"
//                            + ",gefu_creation_status"
//                            + ",status"
//                            + ",creat_by"
//                            + ",creat_date"
//                            + ",mod_by"
//                            + ",mod_date"
//                            + ",system_date"
//                            + ",gefu_type"
//                            + ",bulk_credit"
//                            + ",cw_fixed_frequency"
//                            + ") values("
//                            + "'" + max_idndb_pdc_txn_master + "',"
//                            + "'" + account + "',"
//                            + "'" + currency + "',"
//                            + "'" + date + "',"
//                            + "'" + df.format(amount) + "',"
//                            + "'" + narration + "',"
//                            + "'C',"
//                            + "'" + profit_centre + "',"
//                            + "'" + DAO + "',"
//                            + "'" + df.format(c_amount) + "',"
//                            + "'" + df.format(d_amount) + "',"
//                            + "'ACTIVE',"
//                            + "'UN-AUTHORIZED',"
//                            + "'" + m_str_user_id + "',"
//                            + "NOW(),"
//                            + "'" + m_str_user_id + "',"
//                            + "NOW(),"
//                            + "'" + system_date + "',"
//                            + "'COMCHGBC',"
//                            + "'NO',"
//                            + "'" + gefu_rf_fixed_frequency + "')";
//                    log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ MY SQL Quiery :" + m_strQry);
//                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
//                        throw new Exception("Error Occured in insert user-roles");
//                    }
//
//                }

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
                _narration = "DISB" + m_Strrf_chq_number + "RS" + m_Strrf_cheque_amu + cust_name;
                _d_amount = m_discounting_amu_from_cheque_amu;

                log.info("ACC. ENTRY : Seller receviable finance account number debit & seller collection account credit");
                log.info("ACC. ENTRY : Seller receviable finance number debit/ debit Acc: " + _account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :RFCDCD");
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
                        + "'" + df.format(_amount) + "',"
                        + "'" + _narration + "',"
                        + "'" + _credit_debit + "',"
                        + "'" + profit_centre + "',"
                        + "'" + DAO + "',"
                        + "'" + df.format(_c_amount) + "',"
                        + "'" + df.format(_d_amount) + "',"
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
                _narration = "DISB" + m_Strrf_chq_number + "RS" + m_Strrf_cheque_amu + cust_name;
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
                        + "'" + df.format(_amount) + "',"
                        + "'" + _narration + "',"
                        + "'" + _credit_debit + "',"
                        + "'" + profit_centre + "',"
                        + "'" + DAO + "',"
                        + "'" + df.format(_c_amount) + "',"
                        + "'" + df.format(_d_amount) + "',"
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

                rf_pdc_save_status = "1100";

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }

        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in saving cw to rf entry data, Exception" + e);
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

    public boolean pdcChequesAuthorization(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        log.info("PDC authorization request recveied.");

        String m_str_user_id = "";
        String m_str_idndb_pdc_txn_master = "";
        String m_idndb_pdc_txn_master_bulk = "";
        String m_auth_status = "";
        String _system_date = "";

        String m_strQry = "";
        String m_str_gefu_qry = "";
        String m_str_log_qry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");

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

            m_strQry = "update ndb_pdc_txn_master set pdc_chq_status_auth=?,pdc_txn_mod='AUTHORIZED',"
                    + "pdc_chq_mod_by=?,"
                    + "pdc_chq_mod_date=now()"
                    + " where idndb_pdc_txn_master=?";
            _prepStmnt = _currentCon.prepareStatement(m_strQry);

            m_str_gefu_qry = "update gefu_file_generation set status=?,"
                    + "mod_by=?,"
                    + "system_date=?,"
                    + "mod_date=now()"
                    + " where idndb_pdc_txn_master=?";
            _prepStmnt2 = _currentCon.prepareStatement(m_str_gefu_qry);

            m_str_log_qry = "update ndb_change_log set status=?,"
                    + "authby=?,"
                    + "auth_date=now()"
                    + " where ndb_attached_id=? and ndb_change_log_type='PDCTXN'";
            _prepStmnt3 = _currentCon.prepareStatement(m_str_log_qry);

            for (int j = 0; j < parts.length; j++) {

                m_str_idndb_pdc_txn_master = parts[j];
                _prepStmnt.setString(1, m_auth_status);
                _prepStmnt.setString(2, m_str_user_id);
                _prepStmnt.setString(3, m_str_idndb_pdc_txn_master);
                _prepStmnt.addBatch();

                _prepStmnt2.setString(1, m_auth_status);
                _prepStmnt2.setString(2, m_str_user_id);
                _prepStmnt2.setString(3, _system_date);
                _prepStmnt2.setString(4, m_str_idndb_pdc_txn_master);
                _prepStmnt2.addBatch();

                _prepStmnt3.setString(1, m_auth_status);
                _prepStmnt3.setString(2, m_str_user_id);
                _prepStmnt3.setString(3, m_str_idndb_pdc_txn_master);
                _prepStmnt3.addBatch();

            }

            log.info("PDC authorization request master table update.(Btach update quiery :" + m_strQry + ")");
            log.info("PDC authorization request GEFU table update.(Btach update quiery : " + m_str_gefu_qry + ")");
            log.info("PDC authorization request log table update.(Btach update quiery :" + m_str_log_qry + ")");

            _prepStmnt.executeBatch();
            _prepStmnt.close();

            _prepStmnt2.executeBatch();
            _prepStmnt2.close();

            _prepStmnt3.executeBatch();
            _prepStmnt3.close();

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            success = true;
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in authorizing cw & rf entry data, Exception" + e);
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_prepStmnt != null) {
                    _prepStmnt.close();
                }
                if (_prepStmnt2 != null) {
                    _prepStmnt2.close();
                }
                if (_prepStmnt3 != null) {
                    _prepStmnt3.close();
                }
                if (_currentCon != null) {
                    _currentCon.close();
                }
            } catch (Exception e) {
            }
        }
        return success;
    }

    public boolean savePDCStatusUpdateRejecetData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        Parameters para = new Parameters();
        String NDBCommisionPLAcc = para.getNDBCommisionPLAcc();
        DecimalFormat df = new DecimalFormat("###.00");

        String m_str_user_id = "";
        String m_str_idndb_pdc_txn_master = "";
        String m_idndb_pdc_txn_master_bulk = "";
        String m_auth_status = "";
        String _system_date = "";
        ResultSet _rs = null;
        String mysql = "";
        String mysql2 = "";
        String mysql3 = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            //  m_str_idndb_holiday_marker = prm_obj.getString("idndb_holiday_marker");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
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
                String pdc_req_financing = "";
                String pdc_booking_date = "";
                String idndb_customer_define_buyer_id = "";
                String pdc_chq_amu = "";
                double pdc_chq_discounting_amu = 0.00;
                String pdc_chq_number = "";

                mysql = "select * from ndb_pdc_txn_master where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";
                _rs = _stmnt.executeQuery(mysql);
                if (_rs.next()) {
                    pdc_booking_date = _rs.getString("pdc_booking_date");
                    pdc_req_financing = _rs.getString("pdc_req_financing");
                    idndb_customer_define_buyer_id = _rs.getString("idndb_customer_define_buyer_id");
                    pdc_chq_amu = _rs.getString("pdc_chq_amu");
                    pdc_chq_number = _rs.getString("pdc_chq_number");
                    pdc_chq_discounting_amu = _rs.getDouble("pdc_chq_discounting_amu");

                }

                if (pdc_req_financing.equals("CW")) {
                    String chq_wh_commision_crg = "NOTDEFINE";
                    String idndb_customer_define_seller = "";
                    String idndb_customer_define_seller_idndb_customer_define = "";
                    String cms_curr_acc_number = "";
                    String cms_collection_acc_number = "";
                    String cust_name = "";
                    double chq_wh_erly_wdr_chg = 0.00;
                    double chq_wh_vale_dte_extr_chg = 0.00;
                    double chq_wh_erly_stlemnt_chg = 0.00;
                    double cw_tran_base_falt_fee = 0.00;
                    double cw_tran_base_from_tran = 0.00;
                    double cw_fixed_rate_amount = 0.00;
                    String cw_fixed_frequency = "DAILY";
                    m_strQry = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_customer_define_buyer_id + "'";
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
                        cms_curr_acc_number = _rs.getString("cms_curr_acc_number");
                        cms_collection_acc_number = _rs.getString("cms_collection_acc_number");
                        cust_name = _rs.getString("cust_name");
                    }

                    if (pdc_booking_date.equals(_system_date)) {

                        m_strQry = "update ndb_pdc_txn_master set pdc_chq_status_auth='" + m_auth_status + "',pdc_txn_mod='AUTHORIZED',"
                                + "pdc_chq_mod_by='" + m_str_user_id + "',"
                                + "pdc_chq_mod_date=now()"
                                + " where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                        m_strQry = "select * from gefu_file_generation where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and system_date='" + _system_date + "'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        boolean gefu_update = false;
                        if (_rs.next()) {
                            gefu_update = true;
                        }
                        if (gefu_update) {

                            m_strQry = "update gefu_file_generation set status='" + m_auth_status + "',"
                                    + "mod_by='" + m_str_user_id + "',"
                                    + "system_date='" + _system_date + "',"
                                    + "mod_date=now()"
                                    + " where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and system_date='" + _system_date + "'";

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }
                        }
                        m_strQry = "update ndb_change_log set status='" + m_auth_status + "',"
                                + "authby='" + m_str_user_id + "',"
                                + "auth_date=now()"
                                + " where ndb_attached_id='" + m_str_idndb_pdc_txn_master + "' and ndb_change_log_type='PDCTXN'";

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                    } else {
                        m_strQry = "update ndb_pdc_txn_master set pdc_chq_status_auth='" + m_auth_status + "',pdc_txn_mod='AUTHORIZED',"
                                + "pdc_chq_mod_by='" + m_str_user_id + "',"
                                + "pdc_chq_mod_date=now()"
                                + " where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                        m_strQry = "select * from gefu_file_generation where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and system_date='" + _system_date + "'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        boolean gefu_update = false;
                        if (_rs.next()) {
                            gefu_update = true;
                        }
                        if (gefu_update) {

                            m_strQry = "update gefu_file_generation set status='" + m_auth_status + "',"
                                    + "mod_by='" + m_str_user_id + "',"
                                    + "system_date='" + _system_date + "',"
                                    + "mod_date=now()"
                                    + " where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and system_date='" + _system_date + "'";

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }
                        }
                        m_strQry = "update ndb_change_log set status='" + m_auth_status + "',"
                                + "authby='" + m_str_user_id + "',"
                                + "auth_date=now()"
                                + " where ndb_attached_id='" + m_str_idndb_pdc_txn_master + "' and ndb_change_log_type='PDCTXN'";

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        String account = "";
                        String currency = "LKR";
                        String date = "";
                        String narration = "";
                        String credit_debit = "";
                        String profit_centre = "";
                        String DAO = "";
                        double amount = 0.00;
                        double c_amount = 0.00;
                        double d_amount = 0.00;
                        String system_date = _system_date;
                        String gefu_cw_fixed_frequency = cw_fixed_frequency;
                        String[] gefu_date = _system_date.split("/");
                        String gefu_day = gefu_date[0];
                        String gefu_month = gefu_date[1];
                        String gefu_year = gefu_date[2];
                        date = gefu_year + gefu_month + gefu_day;

                        mysql = "select * from gefu_file_generation where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and gefu_type='VDEXTRCD'";
                        _rs = _stmnt.executeQuery(mysql);
                        if (_rs.next()) {
                            amount = _rs.getDouble("amount");

                            d_amount = amount;
                            c_amount = 0.00;
                            account = NDBCommisionPLAcc;

                            narration = "RVSLVDE" + pdc_chq_number + "RS" + pdc_chq_amu + cust_name;
                            log.info("ACC. ENTRY : seller current account debit/ value date extraction charges & ndb commision pl credit value date extraaction charges");
                            log.info("ACC. ENTRY : NDB commision PL credit value date extraction charges debit Acc: " + account + ". debit Amu: " + d_amount + "Narration : " + narration + " Type :VDEXTRBC");

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
                                    + "'" + df.format(amount) + "',"
                                    + "'" + narration + "',"
                                    + "'D',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + df.format(c_amount) + "',"
                                    + "'" + df.format(d_amount) + "',"
                                    + "'ACTIVE',"
                                    + "'AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'VDEXTRBD',"
                                    + "'NO',"
                                    + "'" + gefu_cw_fixed_frequency + "')";

                            log.info("ACC. ENTRY : Seller current account nummber debit value date extraction charges / MYSQL QUIERY :" + m_strQry);
                            log.info("ACC. ENTRY : Seller current account nummber debit value date extraction charges credit Acc: " + account + ". Credit Amu: " + d_amount + "Narration : " + narration + " Type :VDEXTRCD");

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                            d_amount = 0.00;
                            c_amount = amount;
                            account = cms_curr_acc_number;
                            narration = "RVSLVDE" + pdc_chq_number + "RS" + pdc_chq_amu + cust_name;
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
                                    + "'" + df.format(amount) + "',"
                                    + "'" + narration + "',"
                                    + "'C',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + df.format(c_amount) + "',"
                                    + "'" + df.format(d_amount) + "',"
                                    + "'ACTIVE',"
                                    + "'AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'VDEXTRCC',"
                                    + "'NO',"
                                    + "'" + gefu_cw_fixed_frequency + "')";
                            log.info("ACC. ENTRY : NDB commision PL credit value date extraction charges credit Acc: MYSQL QUIERY :" + m_strQry);

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                        }

                        // update reversel entry 
                        if (chq_wh_commision_crg.equals("TRANSACTION BASED")) {

                            if (!(cw_tran_base_falt_fee == 0.00)) {
                                amount = cw_tran_base_falt_fee;

                            } else {
                                amount = Double.parseDouble(pdc_chq_amu) / 100 * cw_tran_base_from_tran;

                            }
                            if (!(amount == 0.00)) {

                                c_amount = amount;
                                d_amount = 0.00;
                                account = cms_curr_acc_number;
                                narration = "REVSLCOMM" + cust_name;
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
                                        + "'" + df.format(amount) + "',"
                                        + "'" + narration + "',"
                                        + "'C',"
                                        + "'" + profit_centre + "',"
                                        + "'" + DAO + "',"
                                        + "'" + df.format(c_amount) + "',"
                                        + "'" + df.format(d_amount) + "',"
                                        + "'ACTIVE',"
                                        + "'AUTHORIZED',"
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

                                // ndb debit recersel entry
                                d_amount = amount;
                                c_amount = 0.00;
                                account = NDBCommisionPLAcc;
                                narration = "REVSLCOMM" + cust_name;
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
                                        + "'" + df.format(amount) + "',"
                                        + "'" + narration + "',"
                                        + "'D',"
                                        + "'" + profit_centre + "',"
                                        + "'" + DAO + "',"
                                        + "'" + df.format(c_amount) + "',"
                                        + "'" + df.format(d_amount) + "',"
                                        + "'ACTIVE',"
                                        + "'AUTHORIZED',"
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

                }

                if (pdc_req_financing.equals("RF")) {
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
                    m_strQry = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + idndb_customer_define_buyer_id + "'";
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
                    if (pdc_booking_date.equals(_system_date)) {

                        m_strQry = "update ndb_pdc_txn_master set pdc_chq_status_auth='" + m_auth_status + "',pdc_txn_mod='AUTHORIZED',"
                                + "pdc_chq_mod_by='" + m_str_user_id + "',"
                                + "pdc_chq_mod_date=now()"
                                + " where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                        m_strQry = "select * from gefu_file_generation where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and system_date='" + _system_date + "'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        boolean gefu_update = false;
                        if (_rs.next()) {
                            gefu_update = true;
                        }
                        if (gefu_update) {

                            m_strQry = "update gefu_file_generation set status='" + m_auth_status + "',"
                                    + "mod_by='" + m_str_user_id + "',"
                                    + "system_date='" + _system_date + "',"
                                    + "mod_date=now()"
                                    + " where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "'";

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }
                        }
                        m_strQry = "update ndb_change_log set status='" + m_auth_status + "',"
                                + "authby='" + m_str_user_id + "',"
                                + "auth_date=now()"
                                + " where ndb_attached_id='" + m_str_idndb_pdc_txn_master + "' and ndb_change_log_type='PDCTXN'";

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                    } else {

                        m_strQry = "update ndb_pdc_txn_master set pdc_chq_status_auth='" + m_auth_status + "',pdc_txn_mod='AUTHORIZED',"
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
//                            throw new Exception("Error Occured in insert user-roles");
                        }
                        m_strQry = "update ndb_change_log set status='" + m_auth_status + "',"
                                + "authby='" + m_str_user_id + "',"
                                + "auth_date=now()"
                                + " where ndb_attached_id='" + m_str_idndb_pdc_txn_master + "' and ndb_change_log_type='PDCTXN'";

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        String account = "";
                        String currency = "LKR";
                        String date = "";
                        String narration = "";
                        String credit_debit = "";
                        String profit_centre = "";
                        String DAO = "";
                        double amount = 0.00;
                        double c_amount = 0.00;
                        double d_amount = 0.00;
                        String system_date = _system_date;
                        String gefu_rf_fixed_frequency = rf_fixed_frequency;
                        String[] gefu_date = _system_date.split("/");
                        String gefu_day = gefu_date[0];
                        String gefu_month = gefu_date[1];
                        String gefu_year = gefu_date[2];
                        date = gefu_year + gefu_month + gefu_day;

                        mysql = "select * from gefu_file_generation where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and gefu_type='VDEXTRCD'";
                        _rs = _stmnt.executeQuery(mysql);
                        if (_rs.next()) {
                            amount = _rs.getDouble("amount");

                            d_amount = amount;
                            c_amount = 0.00;
                            account = NDBCommisionPLAcc;

                            narration = "RVSLVDE" + pdc_chq_number + "RS" + pdc_chq_amu + cust_name;
                            log.info("ACC. ENTRY : seller current account debit/ value date extraction charges & ndb commision pl credit value date extraaction charges");
                            log.info("ACC. ENTRY : NDB commision PL credit value date extraction charges debit Acc: " + account + ". debit Amu: " + d_amount + "Narration : " + narration + " Type :VDEXTRBC");

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
                                    + "'" + df.format(amount) + "',"
                                    + "'" + narration + "',"
                                    + "'D',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + df.format(c_amount) + "',"
                                    + "'" + df.format(d_amount) + "',"
                                    + "'ACTIVE',"
                                    + "'UN-AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'VDEXTRBD',"
                                    + "'NO',"
                                    + "'" + gefu_rf_fixed_frequency + "')";

                            log.info("ACC. ENTRY : Seller current account nummber debit value date extraction charges / MYSQL QUIERY :" + m_strQry);
                            log.info("ACC. ENTRY : Seller current account nummber debit value date extraction charges credit Acc: " + account + ". Credit Amu: " + d_amount + "Narration : " + narration + " Type :VDEXTRCD");

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                            d_amount = 0.00;
                            c_amount = amount;
                            account = rec_finance_curr_ac;
                            narration = "RVSLVDE" + pdc_chq_number + "RS" + pdc_chq_amu + cust_name;
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
                                    + "'" + df.format(amount) + "',"
                                    + "'" + narration + "',"
                                    + "'C',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + df.format(c_amount) + "',"
                                    + "'" + df.format(d_amount) + "',"
                                    + "'ACTIVE',"
                                    + "'UN-AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'VDEXTRCC',"
                                    + "'NO',"
                                    + "'" + gefu_rf_fixed_frequency + "')";
                            log.info("ACC. ENTRY : NDB commision PL credit value date extraction charges credit Acc: MYSQL QUIERY :" + m_strQry);

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                        }

                        // update reversel entry 
                        if (rec_finance_commision_crg.equals("TRANSACTION BASED")) {

                            if (!(rf_tran_base_falt_fee == 0.00)) {
                                amount = rf_tran_base_falt_fee;

                            } else {
                                amount = Double.parseDouble(pdc_chq_amu) / 100 * rf_tran_base_from_tran;

                            }

                            c_amount = amount;
                            d_amount = 0.00;
                            account = rec_finance_curr_ac;
                            narration = "REVSLCOMM" + cust_name;
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
                                    + "'" + df.format(amount) + "',"
                                    + "'" + narration + "',"
                                    + "'C',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + df.format(c_amount) + "',"
                                    + "'" + df.format(d_amount) + "',"
                                    + "'ACTIVE',"
                                    + "'AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'COMCHGCDR',"
                                    + "'NO',"
                                    + "'" + gefu_rf_fixed_frequency + "')";
                            log.info("ACC. REVERSEL ENTRY : Seller current account number credit/ MYSQL QUIER" + m_strQry);

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                            // ndb debit recersel entry
                            d_amount = amount;
                            c_amount = 0.00;
                            account = NDBCommisionPLAcc;
                            narration = "REVSLCOMM" + cust_name;
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
                                    + "'" + df.format(amount) + "',"
                                    + "'" + narration + "',"
                                    + "'D',"
                                    + "'" + profit_centre + "',"
                                    + "'" + DAO + "',"
                                    + "'" + df.format(c_amount) + "',"
                                    + "'" + df.format(d_amount) + "',"
                                    + "'ACTIVE',"
                                    + "'AUTHORIZED',"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + m_str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'COMCHGBCR',"
                                    + "'NO',"
                                    + "'" + gefu_rf_fixed_frequency + "')";

                            log.info("ACC. REVERSEL ENTRY : NDB ank commision pl debit/MYSQL QUIERY : " + m_strQry);

                            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                        }
                        // Return advance
                        String buyer_name = "";
                        m_strQry = "SELECT\n"
                                + "     ndb_seller_has_buyers.`idndb_seller_has_buyers` AS ndb_seller_has_buyers_idndb_seller_has_buyers,\n"
                                + "     ndb_seller_has_buyers.`idndb_customer_define_seller` AS ndb_seller_has_buyers_idndb_customer_define_seller,\n"
                                + "     ndb_seller_has_buyers.`idndb_customer_define_buyer` AS ndb_seller_has_buyers_idndb_customer_define_buyer,\n"
                                + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                                + "     ndb_cust_prod_map.`idndb_customer_define` AS ndb_cust_prod_map_idndb_customer_define,\n"
                                + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
                                + "     ndb_customer_define.`cust_id` AS ndb_customer_define_cust_id,\n"
                                + "     ndb_customer_define.`cust_name` AS ndb_customer_define_cust_name\n"
                                + "FROM\n"
                                + "     `ndb_cust_prod_map` ndb_cust_prod_map INNER JOIN `ndb_seller_has_buyers` ndb_seller_has_buyers ON ndb_cust_prod_map.`idndb_cust_prod_map` = ndb_seller_has_buyers.`idndb_customer_define_buyer`\n"
                                + "     INNER JOIN `ndb_customer_define` ndb_customer_define ON ndb_cust_prod_map.`idndb_customer_define` = ndb_customer_define.`idndb_customer_define` where  ndb_seller_has_buyers.`idndb_seller_has_buyers`='" + idndb_customer_define_buyer_id + "'";
                        _rs = _stmnt3.executeQuery(m_strQry);
                        if (_rs.next()) {
                            buyer_name = _rs.getString("ndb_customer_define_cust_name");
                        }

                        amount = pdc_chq_discounting_amu;
                        account = rec_finance_curr_ac;
                        credit_debit = "D";
                        narration = "RVSL" + pdc_chq_number + "RS" + pdc_chq_amu + buyer_name;
                        d_amount = amount;

                        log.info("ACC. ENTRY : Seller collection account debit & seller receivable finance account credit");
                        log.info("ACC. ENTRY : Seller collection account debit/ debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :RFCDRETCD");

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
                                + "'" + account + "',"
                                + "'LKR',"
                                + "'" + date + "',"
                                + "'" + df.format(amount) + "',"
                                + "'" + narration + "',"
                                + "'" + credit_debit + "',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + df.format(c_amount) + "',"
                                + "'" + df.format(d_amount) + "',"
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

                        log.info("ACC. ENTRY : Seller collection account debit/ MY SQL QUIERY " + m_strQry);

                        if (_stmnt3.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                        account = rec_finance_acc_num;
                        c_amount = amount;
                        d_amount = 0.00;
                        credit_debit = "C";
                        narration = "RVSL" + pdc_chq_number + "RS" + pdc_chq_amu + buyer_name;

                        log.info("ACC. ENTRY : Seller receivable finance account creditt/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :RFCDRETBC");

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
                                + "'" + account + "',"
                                + "'LKR',"
                                + "'" + date + "',"
                                + "'" + df.format(amount) + "',"
                                + "'" + narration + "',"
                                + "'" + credit_debit + "',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + df.format(c_amount) + "',"
                                + "'" + df.format(d_amount) + "',"
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
                        log.info("ACC. ENTRY : Seller receivable finance account creditt/ MY SQL Quiery " + m_strQry);

                        if (_stmnt3.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                    }

                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            success = true;
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in rejecting cw & rf entry data, Exception" + e);
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

    public JSONArray getPDCCWtxnDataToFillAllReadyExist(String cw_chq_number, String cust_bank_code, String cust_branch_code) {
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

            String m_uniq_id = cw_chq_number + bank_code + branch_id;
            m_chstrsql = "SELECT CONCAT(pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code) as txn_master_unq_id,idndb_pdc_txn_master FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE'";
            _rs = m_stamt.executeQuery(m_chstrsql);
            while (_rs.next()) {
                if (m_uniq_id.equals(_rs.getString("txn_master_unq_id"))) {
                    prm_stridndb_pdc_txn_master = _rs.getString("idndb_pdc_txn_master");

                }

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
                    m_jsObj.put("pdc_chq_amu", m_rs.getString("pdc_chq_amu"));
                    m_jsObj.put("pdc_req_financing", m_rs.getString("pdc_req_financing"));

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

            m_strQry4 = "SELECT * FROM gefu_file_generation where gefu_creation_status='ACTIVE' and status='AUTHORIZED' and date='" + _mod_system_date + "'";
            m_rs4 = stmt4.executeQuery(m_strQry4);

            String m_gefu_idndb_pdc_txn_master = "";
            while (m_rs4.next()) {
                m_gefu_idndb_pdc_txn_master = m_rs4.getString("idndb_pdc_txn_master");

                m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_chq_status_auth ='AUTHORIZED' and pdc_booking_date='" + booked_date + "' and idndb_pdc_txn_master='" + m_gefu_idndb_pdc_txn_master + "' ";
                m_rs1 = _stmnt.executeQuery(m_strQry);
                if (m_rs1.next()) {
                    m_jsObj = new JSONObject();
                    m_jsObj.put("idndb_pdc_txn_master", m_rs1.getString("idndb_pdc_txn_master"));

                    m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller_id") + "'";
                    m_rs2 = _stmnt2.executeQuery(m_strQry2);
                    while (m_rs2.next()) {

                        m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                                + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
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

                        m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                                + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
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

    public JSONArray getTodayChequeSending(String value_date) {
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

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_chq_status_auth ='AUTHORIZED' and pdc_value_date='" + value_date + "' ";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_pdc_txn_master", m_rs1.getString("idndb_pdc_txn_master"));

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
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

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_jsObj.put("buyer", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));
                    }

                }

                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_chq_amu", m_rs1.getString("pdc_chq_amu"));
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

    public JSONArray getTodayBookedChequeSending(String booked_date) {
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

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_chq_status_auth ='AUTHORIZED' and pdc_booking_date='" + booked_date + "' ";
            m_rs1 = _stmnt.executeQuery(m_strQry);
            while (m_rs1.next()) {
                m_jsObj = new JSONObject();
                m_jsObj.put("idndb_pdc_txn_master", m_rs1.getString("idndb_pdc_txn_master"));

                m_strQry2 = "SELECT idndb_customer_define,idndb_cust_prod_map"
                        + " FROM ndb_cust_prod_map where idndb_cust_prod_map='" + m_rs1.getString("idndb_customer_define_seller_id") + "'";
                m_rs2 = _stmnt2.executeQuery(m_strQry2);
                while (m_rs2.next()) {

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
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

                    m_strQry3 = "SELECT idndb_customer_define,cust_id,cust_name"
                            + " FROM ndb_customer_define where cust_status='ACTIVE' and cust_auth='AUTHORIZED' and idndb_customer_define='" + m_rs2.getString("idndb_customer_define") + "' ";
                    m_rs3 = _stmnt3.executeQuery(m_strQry3);
                    if (m_rs3.next()) {
                        m_jsObj.put("buyer", m_rs3.getString("cust_id") + "," + m_rs3.getString("cust_name"));
                    }

                }

                m_jsObj.put("pdc_chq_number", m_rs1.getString("pdc_chq_number"));
                m_jsObj.put("pdc_bank_code", m_rs1.getString("pdc_bank_code"));
                m_jsObj.put("pdc_branch_code", m_rs1.getString("pdc_branch_code"));
                m_jsObj.put("pdc_chq_amu", m_rs1.getString("pdc_chq_amu"));
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

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -2);
            Date todate1 = cal.getTime();
            String fromdate = sdf.format(todate1);

            String[] system_date_spliter = fromdate.split("/");
            int sys_m_year = Integer.parseInt(system_date_spliter[2]);
            int sys_m_month = Integer.parseInt(system_date_spliter[1]) - 1;
            int sys_m_day = Integer.parseInt(system_date_spliter[0]);

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

    public static void main(String[] args) {
        pdcDAO pdc = new pdcDAO();

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
