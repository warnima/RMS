/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBOops;

import DBAutoFillBeans.comboDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import ndb.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import utility.Parameters;

/**
 *
 * @author Amila_3270
 */
public class proccessDAO {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(proccessDAO.class.getName());
    static ConnectionPool _connectionPool;
    static Connection _currentCon = null;
    private Statement _stmnt = null;
    private Statement _stmnt2 = null;
    private Statement _stmnt3 = null;
    private ResultSet _rs = null;
    private ResultSet _rs2 = null;
    private ResultSet _rs3 = null;
    private Exception _exception;

    public static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public String runAdditionalProccess() {
        String additional_day_proccess = "0000";

        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        String m_idndb_additional_day_file = "";
        String m_adittional_day_bank = "";
        String m_adittional_day_bank_branch = "";
        String m_idndb_pdc_txn_master;

        try {
            pdcDAO dao = new pdcDAO();
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            m_strQry = "select * from ndb_system_date";
            _rs = _stmnt.executeQuery(m_strQry);
            String _system_date = "";
            if (_rs.next()) {

                _system_date = _rs.getString("_system_date");

            }

            m_strQry2 = "SELECT * FROM ndb_bank_master_file where bank_approval='UN-AUTHORIZED'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                additional_day_proccess = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry2 = "SELECT * FROM ndb_branch_master_file where branch_approval='UN-AUTHORIZED'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                additional_day_proccess = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }
            m_strQry2 = "SELECT * FROM ndb_holiday_marker where ndb_holiday_approval='UN-AUTHORIZED'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                additional_day_proccess = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry2 = "SELECT * FROM ndb_customer_define where cust_auth='UN-AUTHORIZED'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                additional_day_proccess = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry2 = "SELECT * FROM ndb_cust_prod_map where prod_relationship_auth='UN-AUTHORIZED'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                additional_day_proccess = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry2 = "SELECT * FROM ndb_seller_has_buyers where sl_has_byr_auth='UN-AUTHORIZED'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                additional_day_proccess = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry2 = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status_auth='UN-AUTHORIZED'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                additional_day_proccess = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry2 = "SELECT * FROM ndb_day_today_process where processed_date='" + _system_date + "' and day_today_process_name='ADDDAYFILEUPLD'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (!_rs2.next()) {
                additional_day_proccess = "0022";
                throw new Exception("Return ,Adional day and lequdation process cannot be processed until addional day file upload completed.");
            }

            m_strQry2 = "SELECT * FROM ndb_day_today_process where processed_date='" + _system_date + "' and day_today_process_name='RETRNFILEUPLD'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (!_rs2.next()) {
                additional_day_proccess = "0023";
                throw new Exception("Return ,Additional day and liquidation process cannot be processed until return file day file upload completed.");
            }
            m_strQry2 = "SELECT * FROM ndb_day_today_process where processed_date='" + _system_date + "' and day_today_process_name='RETLEQDPROCESS'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                additional_day_proccess = "0024";
                throw new Exception("Return ,Additional day and liquidation process already completed. please proceed with day end process.");
            }

            m_strQry = "SELECT addit_bank_code,addit_branch_code,idndb_additional_day_file, addit_clearing_date,addit_next_clearing_date FROM ndb_additional_day_file where addit_status='ACTIVE'";
            _rs = _stmnt.executeQuery(m_strQry);
            String m_value_date_that_gone_additional_date;
            String m_nextlequdationdate_for_additional_date;

            while (_rs.next()) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                m_idndb_additional_day_file = _rs.getString("idndb_additional_day_file");
                m_adittional_day_bank = _rs.getString("addit_bank_code");
                m_adittional_day_bank_branch = _rs.getString("addit_branch_code");
                m_value_date_that_gone_additional_date = _rs.getString("addit_clearing_date");
                m_nextlequdationdate_for_additional_date = _rs.getString("addit_next_clearing_date");

                m_value_date_that_gone_additional_date = m_value_date_that_gone_additional_date.substring(6, 8) + "/" + m_value_date_that_gone_additional_date.substring(4, 6) + "/" + m_value_date_that_gone_additional_date.substring(0, 4);
                m_nextlequdationdate_for_additional_date = m_nextlequdationdate_for_additional_date.substring(6, 8) + "/" + m_nextlequdationdate_for_additional_date.substring(4, 6) + "/" + m_nextlequdationdate_for_additional_date.substring(0, 4);

//                String m_next_value_date_for_additionla_day = dao.getchequeValueDate(m_value_date_that_gone_additional_date);
                String[] date_spliter = m_value_date_that_gone_additional_date.split("/");
                int m_year = Integer.parseInt(date_spliter[2]);
                int m_month = Integer.parseInt(date_spliter[1]) - 1;
                int m_day = Integer.parseInt(date_spliter[0]);
                Calendar ced = Calendar.getInstance();

                ced.set(Calendar.YEAR, m_year); // set the year
                ced.set(Calendar.MONTH, m_month); // set the month
                ced.set(Calendar.DAY_OF_MONTH, m_day);
                ced.add(Calendar.DATE, 1);

                String m_next_value_date_for_additionla_day = dao.getchequeValueDate(formatter.format(ced.getTime()));

                m_strQry2 = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_bank_code='" + m_adittional_day_bank + "' and pdc_branch_code='" + m_adittional_day_bank_branch + "' and pdc_value_date='" + m_value_date_that_gone_additional_date + "'";
                _rs2 = _stmnt2.executeQuery(m_strQry2);
                while (_rs2.next()) {

                    m_idndb_pdc_txn_master = _rs2.getString("idndb_pdc_txn_master");
                    m_strQry3 = "update ndb_pdc_txn_master set pdc_value_date='" + m_next_value_date_for_additionla_day + "',"
                            + "pdc_lquid_date='" + m_nextlequdationdate_for_additional_date + "',pdc_chq_status='ADDITIONALDAY',pdc_additional_day='ACTIVE'"
                            + " where idndb_pdc_txn_master='" + _rs2.getString("idndb_pdc_txn_master") + "'";

                    if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                }

                m_strQry3 = "update ndb_additional_day_file set addit_status='PROCESSED' where idndb_additional_day_file='" + m_idndb_additional_day_file + "'";

                if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                // date range
            }
            additional_day_proccess = "1111";

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }

        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in addition day process, Exception" + e);
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_rs2 != null) {
                    _rs2.close();
                }
                if (_rs3 != null) {
                    _rs3.close();
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
        return additional_day_proccess;
    }

    public String runHolidayMarkedProccess(String user_id) {
        String additional_day_proccess = "0000";
        log.info("start holiday mark day end process");

        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        String m_idndb_holiday_marker = "";
        String m_ndb_holiday = "";
        String m_idndb_pdc_txn_master = "";
        String m_holiday_day_formatted_mod = "";
        String m_holiday_day_formatted = "";
        String m_holiday_day_after_date = "";

        String m_next_value_day = "";
        String m_next_value_day_formatted = "";
        String m_liquiddate_formatted = "";

        boolean m_ardy_exist_data = true;

        try {
            DBoperationsDBO.pdcDAO dao = new DBoperationsDBO.pdcDAO();
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c_year = Calendar.getInstance();
            c_year.set(Calendar.DAY_OF_YEAR, c_year.getActualMaximum(Calendar.DAY_OF_YEAR));
            String last_day_of_year = formatter.format(c_year.getTime());
            Calendar c = Calendar.getInstance();
            Date date = formatter.parse(last_day_of_year);
            c.setTime(date);
            c.add(Calendar.DATE, 10);
            String gen_last_date = formatter.format(c.getTime());

            String m_today = new comboDAO().getSystemDate();

            m_strQry = "SELECT * FROM ndb_holiday_marker where ndb_holiday_status='ACTIVE' and ndb_holiday_approval ='AUTHORIZED' and STR_TO_DATE(ndb_holiday, '%d/%m/%Y') BETWEEN STR_TO_DATE('" + m_today + "', '%d/%m/%Y') AND STR_TO_DATE('" + gen_last_date + "', '%d/%m/%Y')";
            _rs = _stmnt.executeQuery(m_strQry);
            while (_rs.next()) {
                log.info("recevied marked holiday holiday");
                m_idndb_holiday_marker = _rs.getString("idndb_holiday_marker");
                m_ndb_holiday = _rs.getString("ndb_holiday");
                log.info("recevied marked holiday holiday" + m_ndb_holiday);

                m_holiday_day_after_date = dao.getchequeValueDate(m_ndb_holiday);

                log.info("holiday after working day" + m_holiday_day_after_date);

                String[] m_holiday_day_after_datesplit = m_holiday_day_after_date.split("/");

                String next_value_day_year = m_holiday_day_after_datesplit[2];
                String next_value_day_month = m_holiday_day_after_datesplit[1];
                String next_value_day_day = m_holiday_day_after_datesplit[0];
                m_next_value_day_formatted = next_value_day_day + "/" + next_value_day_month + "/" + next_value_day_year;

                int m_year = Integer.parseInt(next_value_day_year);
                int m_month = Integer.parseInt(next_value_day_month) - 1;
                int m_day = Integer.parseInt(next_value_day_day);
                Calendar ced = Calendar.getInstance();
                ced.set(Calendar.YEAR, m_year); // set the year
                ced.set(Calendar.MONTH, m_month); // set the month
                ced.set(Calendar.DAY_OF_MONTH, m_day);
                ced.add(Calendar.DATE, 1);

                m_liquiddate_formatted = dao.getchequeValueDate(formatter.format(ced.getTime()));
                log.info("holiday after lequid date" + m_liquiddate_formatted);

                m_strQry2 = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE'  and pdc_value_date='" + m_ndb_holiday + "'";
                _rs2 = _stmnt2.executeQuery(m_strQry2);
                while (_rs2.next()) {

                    log.info("recevied txn marked holiday holiday:" + m_ndb_holiday + " m_idndb_pdc_txn_master:" + m_idndb_pdc_txn_master);

                    m_idndb_pdc_txn_master = _rs2.getString("idndb_pdc_txn_master");
                    m_strQry3 = "update ndb_pdc_txn_master set pdc_value_date='" + m_holiday_day_after_date + "',"
                            + "pdc_lquid_date='" + m_liquiddate_formatted + "'"
                            + " where idndb_pdc_txn_master='" + m_idndb_pdc_txn_master + "'";

                    if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }
                    log.info("updated recevied txn marked holiday holiday:" + m_ndb_holiday + " m_idndb_pdc_txn_master:" + m_idndb_pdc_txn_master);

                }

                m_strQry2 = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE'  and pdc_lquid_date='" + m_ndb_holiday + "'";
                _rs2 = _stmnt2.executeQuery(m_strQry2);
                while (_rs2.next()) {

                    log.info("recevied txn marked holiday after lequied:" + m_ndb_holiday + " m_idndb_pdc_txn_master:" + m_idndb_pdc_txn_master);

                    m_idndb_pdc_txn_master = _rs2.getString("idndb_pdc_txn_master");
                    m_strQry3 = "update ndb_pdc_txn_master set pdc_lquid_date='" + m_holiday_day_after_date + "'"
                            + " where idndb_pdc_txn_master='" + m_idndb_pdc_txn_master + "'";

                    if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }
                    log.info("updated recevied txn marked holiday holiday:" + m_ndb_holiday + " m_idndb_pdc_txn_master:" + m_idndb_pdc_txn_master);

                }

            }

            additional_day_proccess = "1111";

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }

        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in holiday marked cheque process, Exception" + e);
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_rs2 != null) {
                    _rs2.close();
                }
                if (_rs3 != null) {
                    _rs3.close();
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
        return additional_day_proccess;
    }

    public String runFixedChargedBaseCommisionProccess(String m_str_user_id) {
        String Status_CommisionProccess = "0000";
        log.info("saart run fixed charg base commsion charg entries process");
        DecimalFormat df = new DecimalFormat("###.00");

        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        String _system_date = "";

        try {
            pdcDAO dao = new pdcDAO();
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            m_strQry = "select * from ndb_system_date";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                _system_date = _rs.getString("_system_date");

            }
            Parameters para = new Parameters();
            String NDBCommisionPLAcc = para.getNDBCommisionPLAcc();

            m_strQry = "SELECT * FROM ndb_seller_has_buyers where sl_has_byr_status='ACTIVE' and sl_has_byr_auth ='AUTHORIZED' and chq_wh_commision_crg='FIXED CHARGE BASED' or rec_finance_commision_crg='FIXED CHARGE BASED'";
            _rs = _stmnt.executeQuery(m_strQry);
            while (_rs.next()) {

                String idndb_seller_has_buyers = _rs.getString("idndb_seller_has_buyers");
                String idndb_customer_define_seller = _rs.getString("idndb_customer_define_seller");
                String idndb_customer_define_buyer = _rs.getString("idndb_customer_define_buyer");
                String sl_has_byr_prorm_type = _rs.getString("sl_has_byr_prorm_type");
                String rec_finance_commision_crg = _rs.getString("rec_finance_commision_crg");
                String chq_wh_commision_crg = _rs.getString("chq_wh_commision_crg");
                log.info("receved idndb_customer_define_buyer :" + idndb_customer_define_buyer);
                double rf_fixed_rate_amount = 0.00;
                String rf_fixed_frequency = "NOT DEFINE";

                double cw_fixed_rate_amount = 0.00;
                String cw_fixed_frequency = "NOT DEFINE";
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date _system_date_formatted = formatter.parse(_system_date);

                String idndb_customer_define_seller_idndb_customer_define = "";
                String rec_finance_curr_ac = "";
                String rec_cms_curr_acc_number = "";
                String rec_finance_acc_num = "";
                String rec_finance_cr_dsc_proc_acc_num = "";
                String cust_name = "";
                String dao_cust_officer = "";

                m_strQry2 = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
                _rs2 = _stmnt2.executeQuery(m_strQry2);
                if (_rs2.next()) {
                    idndb_customer_define_seller_idndb_customer_define = _rs2.getString("idndb_customer_define");
                    log.info("receved idndb_customer_define_seller_idndb_customer_define :" + idndb_customer_define_seller_idndb_customer_define);

                }

                m_strQry2 = "select \n"
                        + "ncd.rec_finance_curr_ac,\n"
                        + "ncd.cms_curr_acc_number,\n"
                        + "ncd.rec_finance_acc_num,\n"
                        + "ncd.rec_finance_cr_dsc_proc_acc_num,\n"
                        + "ncd.cust_name,\n"
                        + "ngmmf.geo_market_id\n"
                        + "\n"
                        + "from \n"
                        + "\n"
                        + "ndb_customer_define ncd,\n"
                        + "ndb_geo_market_master_file ngmmf\n"
                        + "\n"
                        + "where\n"
                        + "\n"
                        + "ncd.idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "' and \n"
                        + "ngmmf.idndb_geo_market_master_file = ncd.idndb_geo_market_master_file";
                _rs2 = _stmnt2.executeQuery(m_strQry2);
                if (_rs2.next()) {
                    rec_finance_curr_ac = _rs2.getString("rec_finance_curr_ac");
                    rec_cms_curr_acc_number = _rs2.getString("cms_curr_acc_number");
                    rec_finance_acc_num = _rs2.getString("rec_finance_acc_num");
                    rec_finance_cr_dsc_proc_acc_num = _rs2.getString("rec_finance_cr_dsc_proc_acc_num");
                    cust_name = _rs2.getString("cust_name");
                    dao_cust_officer = _rs2.getString("geo_market_id");

                    log.info("receved rec_finance_curr_ac :" + rec_finance_curr_ac + "dao_cust_officer:" + dao_cust_officer);

                }

                String[] gefu_date = _system_date.split("/");
                String gefu_day = gefu_date[0];
                String gefu_month = gefu_date[1];
                String gefu_year = gefu_date[2];
                String gefu_formatted_date = gefu_year + gefu_month + gefu_day;
                log.info("receved syatem date formatted to : :" + gefu_formatted_date);

                if (sl_has_byr_prorm_type.equals("RF")) {
                    log.info("receved RF ");

                    String max_idndb_pdc_txn_master = "";
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
                    String gefu_cw_fixed_frequency = "";

                    if (rec_finance_commision_crg.equals("FIXED CHARGE BASED")) {
                        log.info("receved RF FIXED CHARGE BASED");
                        rf_fixed_rate_amount = _rs.getDouble("rf_fixed_rate_amount");
                        rf_fixed_frequency = _rs.getString("rf_fixed_frequency");
                        date = gefu_formatted_date;

                        amount = rf_fixed_rate_amount;
                        gefu_cw_fixed_frequency = rf_fixed_frequency;
                        log.info("receved RF FIXED CHARGE BASED rf_fixed_frequency :" + rf_fixed_frequency);

                        if (rf_fixed_frequency.equals("DAILY")) {
                            if (!(amount == 0.00)) {
                                d_amount = amount;
                                account = rec_finance_curr_ac;
                                narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDDAILY" + cust_name;

                                log.info("Start of ACC Commision entries");
                                // commision charges ndb customer debit entry............................................................
                                log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                                log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

                                m_strQry2 = "insert into gefu_file_generation ("
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
                                        + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGRF" + "',"
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
                                        + "'COMCHGCD',"
                                        + "'NO',"
                                        + "'" + gefu_cw_fixed_frequency + "')";
                                log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry);
                                if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                    throw new Exception("Error Occured in insert user-roles");
                                }
                                // commision charges ndb bank credit entry............................................................

                                d_amount = 0.00;
                                c_amount = amount;
                                account = NDBCommisionPLAcc;
                                narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDDAILY" + cust_name;
                                log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGCD");

                                m_strQry2 = "insert into gefu_file_generation ("
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
                                        + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGRF" + "',"
                                        + "'" + account + "',"
                                        + "'" + currency + "',"
                                        + "'" + date + "',"
                                        + "'" + df.format(amount) + "',"
                                        + "'" + narration + "',"
                                        + "'C',"
                                        + "'PL',"
                                        + "'" + dao_cust_officer + "',"
                                        + "'" + df.format(c_amount) + "',"
                                        + "'" + df.format(d_amount) + "',"
                                        + "'ACTIVE',"
                                        + "'AUTHORIZED',"
                                        + "'" + m_str_user_id + "',"
                                        + "NOW(),"
                                        + "'" + m_str_user_id + "',"
                                        + "NOW(),"
                                        + "'" + system_date + "',"
                                        + "'COMCHGBC',"
                                        + "'NO',"
                                        + "'" + gefu_cw_fixed_frequency + "')";
                                log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ MY SQL Quiery :" + m_strQry);

                                if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                    throw new Exception("Error Occured in insert user-roles");
                                }
                                log.info("End of BULK ACC Commision entries");

                                m_strQry2 = "Insert into ndb_fixed_chg_commison_pro (_date_run,_date_run_freq,idndb_seller_has_buyers) values ('" + _system_date + "','DAILY','" + idndb_seller_has_buyers + "')";
                                if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                    throw new Exception("Error Occured in insert user-roles");
                                }

                            }

                        }

                        if (rf_fixed_frequency.equals("WEEKLY")) {

                            log.info("receved RF FIXED CHARGE BASED rf_fixed_frequency :" + rf_fixed_frequency);

                            Calendar c_week = Calendar.getInstance();
                            c_week.set(Calendar.DAY_OF_WEEK, c_week.getActualMaximum(Calendar.DAY_OF_WEEK));
                            String first_day_of_week = formatter.format(c_week.getTime());

                            Date week_sart_date = formatter.parse(first_day_of_week);

                            String m_next_system_date = "";
                            comboDAO cmbDAO = new comboDAO();
                            String _system_date_act = cmbDAO.getSystemDate();

                            String[] date_spliter = _system_date_act.split("/");
                            int m_year = Integer.parseInt(date_spliter[2]);
                            int m_month = Integer.parseInt(date_spliter[1]) - 1;
                            int m_day = Integer.parseInt(date_spliter[0]);
                            Calendar ced = Calendar.getInstance();

                            ced.set(Calendar.YEAR, m_year); // set the year
                            ced.set(Calendar.MONTH, m_month); // set the month
                            ced.set(Calendar.DAY_OF_MONTH, m_day);
                            ced.add(Calendar.DATE, 1);

                            m_next_system_date = cmbDAO.getchequeValueDate(formatter.format(ced.getTime()));
                            Date min = formatter.parse(_system_date_act);
                            Date max = formatter.parse(m_next_system_date);
                            Date d = formatter.parse(first_day_of_week);

                            m_strQry2 = "select * from ndb_fixed_chg_commison_pro where _date_run_freq='WEEKLY' and _date_run='" + first_day_of_week + "' and idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                            _rs2 = _stmnt3.executeQuery(m_strQry2);
                            if (!_rs2.next()) {

                                if ((week_sart_date.equals(_system_date_formatted)) || (d.after(min) && d.before(max))) {
                                    if (!(amount == 0.00)) {
                                        d_amount = amount;
                                        account = rec_finance_curr_ac;
                                        narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDWEEKLY" + cust_name;

                                        log.info("Start of ACC Commision entries");
                                        // commision charges ndb customer debit entry............................................................
                                        log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                                        log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

                                        m_strQry2 = "insert into gefu_file_generation ("
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
                                                + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGRF" + "',"
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
                                                + "'COMCHGCD',"
                                                + "'NO',"
                                                + "'" + gefu_cw_fixed_frequency + "')";
                                        log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry2);
                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                        // commision charges ndb bank credit entry............................................................

                                        d_amount = 0.00;
                                        c_amount = amount;
                                        account = NDBCommisionPLAcc;
                                        narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDWEEKLY" + cust_name;
                                        log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGCD");

                                        m_strQry2 = "insert into gefu_file_generation ("
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
                                                + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGRF" + "',"
                                                + "'" + account + "',"
                                                + "'" + currency + "',"
                                                + "'" + date + "',"
                                                + "'" + df.format(amount) + "',"
                                                + "'" + narration + "',"
                                                + "'C',"
                                                + "'PL',"
                                                + "'" + dao_cust_officer + "',"
                                                + "'" + df.format(c_amount) + "',"
                                                + "'" + df.format(d_amount) + "',"
                                                + "'ACTIVE',"
                                                + "'AUTHORIZED',"
                                                + "'" + m_str_user_id + "',"
                                                + "NOW(),"
                                                + "'" + m_str_user_id + "',"
                                                + "NOW(),"
                                                + "'" + system_date + "',"
                                                + "'COMCHGBC',"
                                                + "'NO',"
                                                + "'" + gefu_cw_fixed_frequency + "')";
                                        log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ MY SQL Quiery :" + m_strQry2);

                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                        log.info("End of BULK ACC Commision entries");
                                        m_strQry2 = "Insert into ndb_fixed_chg_commison_pro (_date_run,_date_run_freq,idndb_seller_has_buyers) values ('" + first_day_of_week + "','WEEKLY','" + idndb_seller_has_buyers + "')";
                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                    }
                                }

                            }

                        }

                        if (rf_fixed_frequency.equals("MONTHLY")) {
                            log.info("receved RF FIXED CHARGE BASED rf_fixed_frequency :" + rf_fixed_frequency);

                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                            String first_day_of_month = formatter.format(c.getTime());

                            Date month_sart_date = formatter.parse(first_day_of_month);

                            String m_next_system_date = "";
                            comboDAO cmbDAO = new comboDAO();
                            String _system_date_act = cmbDAO.getSystemDate();

                            String[] date_spliter = _system_date_act.split("/");
                            int m_year = Integer.parseInt(date_spliter[2]);
                            int m_month = Integer.parseInt(date_spliter[1]) - 1;
                            int m_day = Integer.parseInt(date_spliter[0]);
                            Calendar ced = Calendar.getInstance();

                            ced.set(Calendar.YEAR, m_year); // set the year
                            ced.set(Calendar.MONTH, m_month); // set the month
                            ced.set(Calendar.DAY_OF_MONTH, m_day);
                            ced.add(Calendar.DATE, 1);

                            m_next_system_date = cmbDAO.getchequeValueDate(formatter.format(ced.getTime()));
                            Date min = formatter.parse(_system_date_act);
                            Date max = formatter.parse(m_next_system_date);
                            Date d = formatter.parse(first_day_of_month);

                            m_strQry2 = "select * from ndb_fixed_chg_commison_pro where _date_run_freq='MONTHLY' and _date_run='" + first_day_of_month + "' and idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                            _rs2 = _stmnt3.executeQuery(m_strQry2);
                            if (!_rs2.next()) {

                                if ((month_sart_date.equals(_system_date_formatted)) || (d.after(min) && d.before(max))) {
                                    if (!(amount == 0.00)) {
                                        d_amount = amount;
                                        account = rec_finance_curr_ac;
                                        narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDMONTHLY" + cust_name;

                                        log.info("Start of ACC Commision entries");
                                        // commision charges ndb customer debit entry............................................................
                                        log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                                        log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

                                        m_strQry2 = "insert into gefu_file_generation ("
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
                                                + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGRF" + "',"
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
                                                + "'COMCHGCD',"
                                                + "'NO',"
                                                + "'" + gefu_cw_fixed_frequency + "')";
                                        log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry2);
                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                        // commision charges ndb bank credit entry............................................................

                                        d_amount = 0.00;
                                        c_amount = amount;
                                        account = NDBCommisionPLAcc;
                                        narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDMONTHLY" + cust_name;
                                        log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGCD");

                                        m_strQry2 = "insert into gefu_file_generation ("
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
                                                + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGRF" + "',"
                                                + "'" + account + "',"
                                                + "'" + currency + "',"
                                                + "'" + date + "',"
                                                + "'" + df.format(amount) + "',"
                                                + "'" + narration + "',"
                                                + "'C',"
                                                + "'PL',"
                                                + "'" + dao_cust_officer + "',"
                                                + "'" + df.format(c_amount) + "',"
                                                + "'" + df.format(d_amount) + "',"
                                                + "'ACTIVE',"
                                                + "'AUTHORIZED',"
                                                + "'" + m_str_user_id + "',"
                                                + "NOW(),"
                                                + "'" + m_str_user_id + "',"
                                                + "NOW(),"
                                                + "'" + system_date + "',"
                                                + "'COMCHGBC',"
                                                + "'NO',"
                                                + "'" + gefu_cw_fixed_frequency + "')";
                                        log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ MY SQL Quiery :" + m_strQry2);

                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                        log.info("End of BULK ACC Commision entries");
                                        m_strQry2 = "Insert into ndb_fixed_chg_commison_pro (_date_run,_date_run_freq,idndb_seller_has_buyers) values ('" + first_day_of_month + "','MONTHLY','" + idndb_seller_has_buyers + "')";
                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                    }
                                }

                            }

                        }

                        if (rf_fixed_frequency.equals("YEARLY")) {
                            log.info("receved RF FIXED CHARGE BASED rf_fixed_frequency :" + rf_fixed_frequency);

                            Calendar c_year = Calendar.getInstance();
                            c_year.set(Calendar.DAY_OF_YEAR, c_year.getActualMaximum(Calendar.DAY_OF_YEAR));
                            String first_day_of_year = formatter.format(c_year.getTime());
                            Date year_sart_date = formatter.parse(first_day_of_year);

                            String m_next_system_date = "";
                            comboDAO cmbDAO = new comboDAO();
                            String _system_date_act = cmbDAO.getSystemDate();

                            String[] date_spliter = _system_date_act.split("/");
                            int m_year = Integer.parseInt(date_spliter[2]);
                            int m_month = Integer.parseInt(date_spliter[1]) - 1;
                            int m_day = Integer.parseInt(date_spliter[0]);
                            Calendar ced = Calendar.getInstance();

                            ced.set(Calendar.YEAR, m_year); // set the year
                            ced.set(Calendar.MONTH, m_month); // set the month
                            ced.set(Calendar.DAY_OF_MONTH, m_day);
                            ced.add(Calendar.DATE, 1);

                            m_next_system_date = cmbDAO.getchequeValueDate(formatter.format(ced.getTime()));
                            Date min = formatter.parse(_system_date_act);
                            Date max = formatter.parse(m_next_system_date);
                            Date d = formatter.parse(first_day_of_year);

                            m_strQry2 = "select * from ndb_fixed_chg_commison_pro where _date_run_freq='YEARLY' and _date_run='" + first_day_of_year + "' and idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                            _rs2 = _stmnt3.executeQuery(m_strQry2);
                            if (!_rs2.next()) {
                                if ((year_sart_date.equals(_system_date_formatted)) || (d.after(min) && d.before(max))) {
                                    if (!(amount == 0.00)) {
                                        d_amount = amount;
                                        account = rec_finance_curr_ac;
                                        narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDYEARLT" + cust_name;

                                        log.info("Start of ACC Commision entries");
                                        // commision charges ndb customer debit entry............................................................
                                        log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                                        log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

                                        m_strQry2 = "insert into gefu_file_generation ("
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
                                                + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGRF" + "',"
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
                                                + "'COMCHGCD',"
                                                + "'NO',"
                                                + "'" + gefu_cw_fixed_frequency + "')";
                                        log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry2);
                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                        // commision charges ndb bank credit entry............................................................

                                        d_amount = 0.00;
                                        c_amount = amount;
                                        account = NDBCommisionPLAcc;
                                        narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDYEARLT" + cust_name;
                                        log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGCD");

                                        m_strQry2 = "insert into gefu_file_generation ("
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
                                                + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGRF" + "',"
                                                + "'" + account + "',"
                                                + "'" + currency + "',"
                                                + "'" + date + "',"
                                                + "'" + df.format(amount) + "',"
                                                + "'" + narration + "',"
                                                + "'C',"
                                                + "'PL',"
                                                + "'" + dao_cust_officer + "',"
                                                + "'" + df.format(c_amount) + "',"
                                                + "'" + df.format(d_amount) + "',"
                                                + "'ACTIVE',"
                                                + "'AUTHORIZED',"
                                                + "'" + m_str_user_id + "',"
                                                + "NOW(),"
                                                + "'" + m_str_user_id + "',"
                                                + "NOW(),"
                                                + "'" + system_date + "',"
                                                + "'COMCHGBC',"
                                                + "'NO',"
                                                + "'" + gefu_cw_fixed_frequency + "')";
                                        log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ MY SQL Quiery :" + m_strQry2);

                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                        log.info("End of BULK ACC Commision entries");
                                        m_strQry2 = "Insert into ndb_fixed_chg_commison_pro (_date_run,_date_run_freq,idndb_seller_has_buyers) values ('" + first_day_of_year + "','YEARLY','" + idndb_seller_has_buyers + "')";
                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                    }

                                }

                            }

                        }

                    }

                }

                if (sl_has_byr_prorm_type.equals("CW")) {
                    log.info("receved CW");

                    String max_idndb_pdc_txn_master = "0000";
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
                    String gefu_cw_fixed_frequency = "";

                    if (chq_wh_commision_crg.equals("FIXED CHARGE BASED")) {
                        log.info("receved RF FIXED CHARGE BASED ");

                        cw_fixed_rate_amount = _rs.getDouble("cw_fixed_rate_amount");
                        cw_fixed_frequency = _rs.getString("cw_fixed_frequency");
                        amount = cw_fixed_rate_amount;
                        gefu_cw_fixed_frequency = cw_fixed_frequency;
                        date = gefu_formatted_date;

                        if (cw_fixed_frequency.equals("DAILY")) {
                            if (!(amount == 0.00)) {
                                log.info("receved CW FIXED CHARGE BASED rf_fixed_frequency :" + rf_fixed_frequency);

                                d_amount = amount;
                                account = rec_cms_curr_acc_number;
                                narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDDAILY" + cust_name;

                                log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                                log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

                                m_strQry2 = "insert into gefu_file_generation ("
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
                                        + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGCW" + "',"
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
                                        + "'COMCHGCD',"
                                        + "'NO',"
                                        + "'" + gefu_cw_fixed_frequency + "')";
                                log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry2);

                                if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                    throw new Exception("Error Occured in insert user-roles");
                                }

                                d_amount = 0.00;
                                c_amount = amount;
                                account = NDBCommisionPLAcc;
                                narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDDAILY" + cust_name;
                                log.info("ACC. ENTRY : NDB bank Commision PL Credit.Credit Acc: " + account + ". Debit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGBC");

                                // gefu ndb bank credit cw commision entry.....................................
                                m_strQry2 = "insert into gefu_file_generation ("
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
                                        + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGCW" + "',"
                                        + "'" + account + "',"
                                        + "'" + currency + "',"
                                        + "'" + date + "',"
                                        + "'" + df.format(amount) + "',"
                                        + "'" + narration + "',"
                                        + "'C',"
                                        + "'PL',"
                                        + "'" + dao_cust_officer + "',"
                                        + "'" + df.format(c_amount) + "',"
                                        + "'" + df.format(d_amount) + "',"
                                        + "'ACTIVE',"
                                        + "'AUTHORIZED',"
                                        + "'" + m_str_user_id + "',"
                                        + "NOW(),"
                                        + "'" + m_str_user_id + "',"
                                        + "NOW(),"
                                        + "'" + system_date + "',"
                                        + "'COMCHGBC',"
                                        + "'NO',"
                                        + "'" + gefu_cw_fixed_frequency + "')";
                                log.info("ACC. ENTRY : NDB Bank Commision PL Credit Entry. MYSQL QUIERY" + m_strQry2);

                                if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                    throw new Exception("Error Occured in insert user-roles");
                                }
                                log.info("End of BULK ACC Commision entries");

                                m_strQry2 = "Insert into ndb_fixed_chg_commison_pro (_date_run,_date_run_freq,idndb_seller_has_buyers) values ('" + _system_date + "','DAILY','" + idndb_seller_has_buyers + "')";
                                if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                    throw new Exception("Error Occured in insert user-roles");
                                }
                            }

                        }

                        if (cw_fixed_frequency.equals("WEEKLY")) {
                            log.info("receved CW FIXED CHARGE BASED rf_fixed_frequency :" + rf_fixed_frequency);

                            Calendar c_week = Calendar.getInstance();
                            c_week.set(Calendar.DAY_OF_WEEK, c_week.getActualMaximum(Calendar.DAY_OF_WEEK));
                            String first_day_of_week = formatter.format(c_week.getTime());

                            Date week_sart_date = formatter.parse(first_day_of_week);

                            String m_next_system_date = "";
                            comboDAO cmbDAO = new comboDAO();
                            String _system_date_act = cmbDAO.getSystemDate();

                            String[] date_spliter = _system_date_act.split("/");
                            int m_year = Integer.parseInt(date_spliter[2]);
                            int m_month = Integer.parseInt(date_spliter[1]) - 1;
                            int m_day = Integer.parseInt(date_spliter[0]);
                            Calendar ced = Calendar.getInstance();

                            ced.set(Calendar.YEAR, m_year); // set the year
                            ced.set(Calendar.MONTH, m_month); // set the month
                            ced.set(Calendar.DAY_OF_MONTH, m_day);
                            ced.add(Calendar.DATE, 1);

                            m_next_system_date = cmbDAO.getchequeValueDate(formatter.format(ced.getTime()));
                            Date min = formatter.parse(_system_date_act);
                            Date max = formatter.parse(m_next_system_date);
                            Date d = formatter.parse(first_day_of_week);

                            m_strQry2 = "select * from ndb_fixed_chg_commison_pro where _date_run_freq='WEEKLY' and _date_run='" + first_day_of_week + "' and idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                            _rs2 = _stmnt3.executeQuery(m_strQry2);
                            if (!_rs2.next()) {

                                if ((week_sart_date.equals(_system_date_formatted)) || (d.after(min) && d.before(max))) {
                                    if (!(amount == 0.00)) {

                                        d_amount = amount;
                                        account = rec_cms_curr_acc_number;
                                        narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDWEEKLY" + cust_name;

                                        log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                                        log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

                                        m_strQry2 = "insert into gefu_file_generation ("
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
                                                + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGCW" + "',"
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
                                                + "'COMCHGCD',"
                                                + "'NO',"
                                                + "'" + gefu_cw_fixed_frequency + "')";
                                        log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry2);

                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }

                                        d_amount = 0.00;
                                        c_amount = amount;
                                        account = NDBCommisionPLAcc;
                                        narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDWEEKLY" + cust_name;

                                        log.info("ACC. ENTRY : NDB bank Commision PL Credit.Credit Acc: " + account + ". Debit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGBC");

                                        // gefu ndb bank credit cw commision entry.....................................
                                        m_strQry2 = "insert into gefu_file_generation ("
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
                                                + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGCW" + "',"
                                                + "'" + account + "',"
                                                + "'" + currency + "',"
                                                + "'" + date + "',"
                                                + "'" + df.format(amount) + "',"
                                                + "'" + narration + "',"
                                                + "'C',"
                                                + "'PL',"
                                                + "'" + dao_cust_officer + "',"
                                                + "'" + df.format(c_amount) + "',"
                                                + "'" + df.format(d_amount) + "',"
                                                + "'ACTIVE',"
                                                + "'AUTHORIZED',"
                                                + "'" + m_str_user_id + "',"
                                                + "NOW(),"
                                                + "'" + m_str_user_id + "',"
                                                + "NOW(),"
                                                + "'" + system_date + "',"
                                                + "'COMCHGBC',"
                                                + "'NO',"
                                                + "'" + gefu_cw_fixed_frequency + "')";
                                        log.info("ACC. ENTRY : NDB Bank Commision PL Credit Entry. MYSQL QUIERY" + m_strQry2);

                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                        log.info("End of BULK ACC Commision entries");
                                        m_strQry2 = "Insert into ndb_fixed_chg_commison_pro (_date_run,_date_run_freq,idndb_seller_has_buyers) values ('" + first_day_of_week + "','WEEKLY','" + idndb_seller_has_buyers + "')";

                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                    }
                                }

                            }

                        }

                        if (cw_fixed_frequency.equals("MONTHLY")) {
                            log.info("receved CW FIXED CHARGE BASED rf_fixed_frequency :" + rf_fixed_frequency);

                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                            String first_day_of_month = formatter.format(c.getTime());

                            Date month_sart_date = formatter.parse(first_day_of_month);

                            String m_next_system_date = "";
                            comboDAO cmbDAO = new comboDAO();
                            String _system_date_act = cmbDAO.getSystemDate();

                            String[] date_spliter = _system_date_act.split("/");
                            int m_year = Integer.parseInt(date_spliter[2]);
                            int m_month = Integer.parseInt(date_spliter[1]) - 1;
                            int m_day = Integer.parseInt(date_spliter[0]);
                            Calendar ced = Calendar.getInstance();

                            ced.set(Calendar.YEAR, m_year); // set the year
                            ced.set(Calendar.MONTH, m_month); // set the month
                            ced.set(Calendar.DAY_OF_MONTH, m_day);
                            ced.add(Calendar.DATE, 1);

                            m_next_system_date = cmbDAO.getchequeValueDate(formatter.format(ced.getTime()));
                            Date min = formatter.parse(_system_date_act);
                            Date max = formatter.parse(m_next_system_date);
                            Date d = formatter.parse(first_day_of_month);

                            m_strQry2 = "select * from ndb_fixed_chg_commison_pro where _date_run_freq='MONTHLY' and _date_run='" + first_day_of_month + "' and idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                            _rs2 = _stmnt3.executeQuery(m_strQry2);
                            if (!_rs2.next()) {

                                if ((month_sart_date.equals(_system_date_formatted)) || (d.after(min) && d.before(max))) {

                                    if (!(amount == 0.00)) {

                                        d_amount = amount;
                                        account = rec_cms_curr_acc_number;
                                        narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDMONTHLY" + cust_name;

                                        log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                                        log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

                                        m_strQry2 = "insert into gefu_file_generation ("
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
                                                + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGCW" + "',"
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
                                                + "'COMCHGCD',"
                                                + "'NO',"
                                                + "'" + gefu_cw_fixed_frequency + "')";
                                        log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry2);

                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }

                                        d_amount = 0.00;
                                        c_amount = amount;
                                        account = NDBCommisionPLAcc;
                                        narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDMONTHLY" + cust_name;

                                        log.info("ACC. ENTRY : NDB bank Commision PL Credit.Credit Acc: " + account + ". Debit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGBC");

                                        // gefu ndb bank credit cw commision entry.....................................
                                        m_strQry2 = "insert into gefu_file_generation ("
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
                                                + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGCW" + "',"
                                                + "'" + account + "',"
                                                + "'" + currency + "',"
                                                + "'" + date + "',"
                                                + "'" + df.format(amount) + "',"
                                                + "'" + narration + "',"
                                                + "'C',"
                                                + "'PL',"
                                                + "'" + dao_cust_officer + "',"
                                                + "'" + df.format(c_amount) + "',"
                                                + "'" + df.format(d_amount) + "',"
                                                + "'ACTIVE',"
                                                + "'AUTHORIZED',"
                                                + "'" + m_str_user_id + "',"
                                                + "NOW(),"
                                                + "'" + m_str_user_id + "',"
                                                + "NOW(),"
                                                + "'" + system_date + "',"
                                                + "'COMCHGBC',"
                                                + "'NO',"
                                                + "'" + gefu_cw_fixed_frequency + "')";
                                        log.info("ACC. ENTRY : NDB Bank Commision PL Credit Entry. MYSQL QUIERY" + m_strQry2);

                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                        log.info("End of BULK ACC Commision entries");
                                        m_strQry2 = "Insert into ndb_fixed_chg_commison_pro (_date_run,_date_run_freq,idndb_seller_has_buyers) values ('" + first_day_of_month + "','MONTHLY','" + idndb_seller_has_buyers + "')";
                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                    }
                                }

                            }

                        }

                        if (cw_fixed_frequency.equals("YEARLY")) {
                            log.info("receved CW FIXED CHARGE BASED rf_fixed_frequency :" + rf_fixed_frequency);

                            Calendar c_year = Calendar.getInstance();
                            c_year.set(Calendar.DAY_OF_YEAR, c_year.getActualMaximum(Calendar.DAY_OF_YEAR));
                            String first_day_of_year = formatter.format(c_year.getTime());
                            Date year_sart_date = formatter.parse(first_day_of_year);

                            String m_next_system_date = "";
                            comboDAO cmbDAO = new comboDAO();
                            String _system_date_act = cmbDAO.getSystemDate();

                            String[] date_spliter = _system_date_act.split("/");
                            int m_year = Integer.parseInt(date_spliter[2]);
                            int m_month = Integer.parseInt(date_spliter[1]) - 1;
                            int m_day = Integer.parseInt(date_spliter[0]);
                            Calendar ced = Calendar.getInstance();

                            ced.set(Calendar.YEAR, m_year); // set the year
                            ced.set(Calendar.MONTH, m_month); // set the month
                            ced.set(Calendar.DAY_OF_MONTH, m_day);
                            ced.add(Calendar.DATE, 1);

                            m_next_system_date = cmbDAO.getchequeValueDate(formatter.format(ced.getTime()));
                            Date min = formatter.parse(_system_date_act);
                            Date max = formatter.parse(m_next_system_date);
                            Date d = formatter.parse(first_day_of_year);

                            m_strQry2 = "select * from ndb_fixed_chg_commison_pro where _date_run_freq='YEARLY' and _date_run='" + first_day_of_year + "' and idndb_seller_has_buyers='" + idndb_seller_has_buyers + "'";
                            _rs2 = _stmnt3.executeQuery(m_strQry2);
                            if (!_rs2.next()) {
                                if ((year_sart_date.equals(_system_date_formatted)) || (d.after(min) && d.before(max))) {
                                    if (!(amount == 0.00)) {

                                        d_amount = amount;
                                        account = rec_cms_curr_acc_number;
                                        narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDYEARLT" + cust_name;

                                        log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                                        log.info("ACC. ENTRY : Seller current account number debit Acc: " + account + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

                                        m_strQry2 = "insert into gefu_file_generation ("
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
                                                + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGCW" + "',"
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
                                                + "'COMCHGCD',"
                                                + "'NO',"
                                                + "'" + gefu_cw_fixed_frequency + "')";
                                        log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry2);

                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }

                                        d_amount = 0.00;
                                        c_amount = amount;
                                        account = NDBCommisionPLAcc;
                                        narration = idndb_customer_define_seller_idndb_customer_define + "COMMFIXEDYEARLT" + cust_name;

                                        log.info("ACC. ENTRY : NDB bank Commision PL Credit.Credit Acc: " + account + ". Debit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGBC");

                                        // gefu ndb bank credit cw commision entry.....................................
                                        m_strQry2 = "insert into gefu_file_generation ("
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
                                                + "'" + idndb_customer_define_seller_idndb_customer_define + "COMMCHRGCW" + "',"
                                                + "'" + account + "',"
                                                + "'" + currency + "',"
                                                + "'" + date + "',"
                                                + "'" + df.format(amount) + "',"
                                                + "'" + narration + "',"
                                                + "'C',"
                                                + "'PL',"
                                                + "'" + dao_cust_officer + "',"
                                                + "'" + df.format(c_amount) + "',"
                                                + "'" + df.format(d_amount) + "',"
                                                + "'ACTIVE',"
                                                + "'AUTHORIZED',"
                                                + "'" + m_str_user_id + "',"
                                                + "NOW(),"
                                                + "'" + m_str_user_id + "',"
                                                + "NOW(),"
                                                + "'" + system_date + "',"
                                                + "'COMCHGBC',"
                                                + "'NO',"
                                                + "'" + gefu_cw_fixed_frequency + "')";
                                        log.info("ACC. ENTRY : NDB Bank Commision PL Credit Entry. MYSQL QUIERY" + m_strQry2);

                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                        log.info("End of BULK ACC Commision entries");
                                        m_strQry2 = "Insert into ndb_fixed_chg_commison_pro (_date_run,_date_run_freq,idndb_seller_has_buyers) values ('" + first_day_of_year + "','YEARLY','" + idndb_seller_has_buyers + "')";
                                        if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                                            throw new Exception("Error Occured in insert user-roles");
                                        }
                                    }

                                }

                            }

                        }

                    }

                }

            }

            m_strQry = "insert into ndb_day_today_process (day_today_process_name,"
                    + "processed_date,"
                    + "processed_by"
                    + ",processed_time"
                    + ""
                    + ") values("
                    + "'RETLEQDPROCESS',"
                    + "'" + _system_date + "',"
                    + "'" + m_str_user_id + "',"
                    + "now())";
            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                throw new Exception("Error Occured in insert user-roles");
            }
            Status_CommisionProccess = "1111";

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }

        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured cw & rf fixed chaged base commision generating process, Exception" + e);
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_rs2 != null) {
                    _rs2.close();
                }
                if (_rs3 != null) {
                    _rs3.close();
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
        return Status_CommisionProccess;
    }

    public String runReturnChequeProccess(String str_user_id) {
        DecimalFormat df = new DecimalFormat("###.00");
        log.info("Erly Lqd Process, Return cheque checking process start...");
        String return_file_proccess = "0000";
        String m_str_user_id = str_user_id;

        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";
        String m_idndb_return_file_upload = "";
        String m_rtn_chq_number = "";
        String m_rtn_bank_code = "";
        String m_rtn_branch_code = "";
        String m_rtn_chq_amu = "";
        String rtn_ndb_acc = "";

        String m_idndb_pdc_txn_master = "";
        String m_Stridndb_customer_define_buyer_id = "";
        String m_pdc_req_financing = "";
        String _system_date = "";
        String buyer_name = "";
        String NDBCommisionPLAcc = "";
        String NDBChqReturnChg = "";
        String idndb_customer_define_seller_id = "";
        String idndb_customer_define_buyer_id = "";
        double pdc_chq_discounting_amu = 0.00;

        try {
            pdcDAO dao = new pdcDAO();
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            m_strQry = "select * from ndb_system_date";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                _system_date = _rs.getString("_system_date");

            }

            Parameters para = new Parameters();
            NDBCommisionPLAcc = para.getNDBCommisionPLAcc();
            NDBChqReturnChg = para.getNDBChqReturnChg();

            m_strQry = "SELECT \n"
                    + "\n"
                    + "nrfu.idndb_return_file_upload,\n"
                    + "nrfu.rtn_chq_number,\n"
                    + "nrfu.rtn_bank_code,\n"
                    + "nrfu.rtn_branch_code,\n"
                    + "nrfu.rtn_chq_amu,\n"
                    + "nptm.idndb_pdc_txn_master,\n"
                    + "nptm.idndb_customer_define_buyer_id,\n"
                    + "nptm.pdc_req_financing,\n"
                    + "nptm.idndb_customer_define_seller_id,\n"
                    + "nptm.idndb_customer_define_buyer_id,\n"
                    + "nptm.pdc_chq_discounting_amu,\n"
                    + "ncd.cust_name as ndb_customer_define_cust_name,\n"
                    + "ncd2.rec_finance_curr_ac,\n"
                    + "ncd2.cms_curr_acc_number,\n"
                    + "ncd2.cms_collection_acc_number,\n"
                    + "ncd2.rec_finance_acc_num,\n"
                    + "ncd2.rec_finance_cr_dsc_proc_acc_num,\n"
                    + "ncd2.rec_finance_margin_ac,\n"
                    + "ncd2.rec_finance_margin,\n"
                    + "ncd2.cust_name\n"
                    + "\n"
                    + "\n"
                    + "FROM \n"
                    + "ndb_return_file_upload nrfu,\n"
                    + "ndb_pdc_txn_master nptm,\n"
                    + "ndb_cust_prod_map ncpm,\n"
                    + "ndb_cust_prod_map ncpm2,\n"
                    + "ndb_seller_has_buyers nshb,\n"
                    + "ndb_customer_define ncd,\n"
                    + "ndb_customer_define ncd2\n"
                    + "\n"
                    + "where nrfu.rtn_status='ACTIVE' and\n"
                    + "\n"
                    + "nptm.pdc_chq_status in ('ACTIVE','ADDITIONALDAY') and\n"
                    + "nptm.pdc_chq_status_auth='AUTHORIZED' and\n"
                    + "(STR_TO_DATE(nptm.pdc_value_date, '%d/%m/%Y') < STR_TO_DATE('" + _system_date + "', '%d/%m/%Y')) and\n"
                    + "nptm.pdc_bank_code = nrfu.rtn_bank_code and\n"
                    + "nptm.pdc_branch_code =nrfu.rtn_branch_code and\n"
                    + "nptm.pdc_chq_number =nrfu.rtn_chq_number and\n"
                    + "ncpm.idndb_cust_prod_map = nptm.idndb_customer_define_seller_id and\n"
                    + "nshb.idndb_seller_has_buyers = nptm.idndb_customer_define_buyer_id and \n"
                    + "nshb.idndb_customer_define_buyer = ncpm2.idndb_cust_prod_map and\n"
                    + "ncd.idndb_customer_define=ncpm2.idndb_customer_define and\n"
                    + "ncd2.idndb_customer_define=ncpm.idndb_customer_define ORDER BY nrfu.idndb_return_file_upload ASC\n"
                    + "\n"
                    + "\n"
                    + "";
            _rs = _stmnt.executeQuery(m_strQry);
            while (_rs.next()) {
                m_idndb_return_file_upload = _rs.getString("idndb_return_file_upload");
                m_rtn_chq_number = _rs.getString("rtn_chq_number");
                m_rtn_bank_code = _rs.getString("rtn_bank_code");
                m_rtn_branch_code = _rs.getString("rtn_branch_code");
                m_rtn_chq_amu = _rs.getString("rtn_chq_amu");

                log.info("get return cheqck m_strQry2 : " + m_strQry2);

                m_idndb_pdc_txn_master = _rs.getString("idndb_pdc_txn_master");
                m_Stridndb_customer_define_buyer_id = _rs.getString("idndb_customer_define_buyer_id");
                m_pdc_req_financing = _rs.getString("pdc_req_financing");
                idndb_customer_define_seller_id = _rs.getString("idndb_customer_define_seller_id");
                idndb_customer_define_buyer_id = _rs.getString("idndb_customer_define_buyer_id");
                pdc_chq_discounting_amu = _rs.getDouble("pdc_chq_discounting_amu");

                m_strQry3 = "update ndb_pdc_txn_master set pdc_chq_status='RETURNED'"
                        + " where idndb_pdc_txn_master='" + m_idndb_pdc_txn_master + "'";

                if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                String rec_finance_curr_ac = "";
                String rec_finance_acc_num = "";
                String cms_curr_acc_number = "";
                String cms_collection_acc_number = "";

                buyer_name = _rs.getString("ndb_customer_define_cust_name");

                rec_finance_curr_ac = _rs.getString("rec_finance_curr_ac");
                cms_curr_acc_number = _rs.getString("cms_curr_acc_number");
                cms_collection_acc_number = _rs.getString("cms_collection_acc_number");
                rec_finance_acc_num = _rs.getString("rec_finance_acc_num");

                if (m_pdc_req_financing.equals("RF")) {
                    log.info("Return charges entry start");

                    String _account = "";
                    double _amount = 0.00;
                    String _narration = "";
                    String _credit_debit = "";
                    String _profit_centre = "";
                    String _DAO = "";
                    double _c_amount = 0.00;
                    double _d_amount = 0.00;
                    String[] gefu_date = _system_date.split("/");
                    String gefu_day = gefu_date[0];
                    String gefu_month = gefu_date[1];
                    String gefu_year = gefu_date[2];
                    String date = gefu_year + gefu_month + gefu_day;

                    _amount = Double.parseDouble(NDBChqReturnChg);
                    _account = rec_finance_curr_ac;
                    _credit_debit = "D";

                    _narration = "RTCH" + m_rtn_chq_number + "RS" + m_rtn_chq_amu + buyer_name;
                    _d_amount = _amount;
                    log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                    log.info("ACC. ENTRY : Seller current account number debit Acc: " + _account + ". Debit Amu: " + _d_amount + "Narration : " + _narration + " Type :CHQRETCHGCD");

                    if (_amount != 0) {

                        // customer curr ACC debit entry ...............................
                        m_strQry3 = "insert into gefu_file_generation ("
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
                                + "'" + m_idndb_pdc_txn_master + "',"
                                + "'" + _account + "',"
                                + "'LKR',"
                                + "'" + date + "',"
                                + "'" + df.format(_amount) + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + _profit_centre + "',"
                                + "'" + _DAO + "',"
                                + "'" + df.format(_c_amount) + "',"
                                + "'" + df.format(_d_amount) + "',"
                                + "'ACTIVE',"
                                + "'AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + _system_date + "',"
                                + "'CHQRETCHGCD',"
                                + "'NO',"
                                + "'DAILY')";

                        log.info("ACC. ENTRY : Seller current account number debit Acc: MY SQL QUIERY" + m_strQry);

                        if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        _c_amount = _amount;
                        _d_amount = 0.00;
                        _account = rec_finance_acc_num;
                        _credit_debit = "C";
                        _narration = "RTCH" + m_rtn_chq_number + "RS" + m_rtn_chq_amu + buyer_name;
                        log.info("ACC. ENTRY : customer RF account Credit ACC Number: " + _account + ". credit Amu: " + _c_amount + "Narration : " + _narration + " Type :CHQRETCHGBD");

                        // NDB bank collection account Credit entry ...............................
                        m_strQry3 = "insert into gefu_file_generation ("
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
                                + "'" + m_idndb_pdc_txn_master + "',"
                                + "'" + rec_finance_acc_num + "',"
                                + "'LKR',"
                                + "'" + date + "',"
                                + "'" + df.format(_amount) + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + _profit_centre + "',"
                                + "'" + _DAO + "',"
                                + "'" + df.format(_c_amount) + "',"
                                + "'" + df.format(_d_amount) + "',"
                                + "'ACTIVE',"
                                + "'AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + _system_date + "',"
                                + "'CHQRETCHGBD',"
                                + "'NO',"
                                + "'DAILY')";

                        if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        log.info("ACC. ENTRY : NDB Collection PL Credit/  MY SQL QUIERY" + m_strQry);

                    }

                    // end of return chagers entries................
                    // RF Return entry.............................
                    log.info("IF Recevible finace .. RF return entry");

                    _account = "";
                    _amount = 0.00;
                    _narration = "";
                    _credit_debit = "";
                    _profit_centre = "";
                    _DAO = "";
                    _c_amount = 0.00;
                    _d_amount = 0.00;

                    _amount = pdc_chq_discounting_amu;
                    _account = rec_finance_curr_ac;
                    _credit_debit = "D";
                    _narration = "RT" + m_rtn_chq_number + "RS" + m_rtn_chq_amu + buyer_name;
                    _d_amount = _amount;

                    log.info("ACC. ENTRY : Seller collection account debit & seller receivable finance account credit");
                    log.info("ACC. ENTRY : Seller collection account debit/ debit Acc: " + _account + ". Debit Amu: " + _d_amount + "Narration : " + _narration + " Type :RFCDRETCD");

                    // customer curr ACC debit entry ...............................
                    m_strQry3 = "insert into gefu_file_generation ("
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
                            + "'" + m_idndb_pdc_txn_master + "',"
                            + "'" + _account + "',"
                            + "'LKR',"
                            + "'" + date + "',"
                            + "'" + df.format(_amount) + "',"
                            + "'" + _narration + "',"
                            + "'" + _credit_debit + "',"
                            + "'" + _profit_centre + "',"
                            + "'" + _DAO + "',"
                            + "'" + df.format(_c_amount) + "',"
                            + "'" + df.format(_d_amount) + "',"
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

                    log.info("ACC. ENTRY : Seller collection account debit/ MY SQL QUIERY " + m_strQry3);

                    if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    _account = rec_finance_acc_num;
                    _c_amount = _amount;
                    _d_amount = 0.00;
                    _credit_debit = "C";
                    _narration = "RT" + m_rtn_chq_number + "RS" + m_rtn_chq_amu + buyer_name;

                    log.info("ACC. ENTRY : Seller receivable finance account creditt/ credit Acc: " + _account + ". credit Amu: " + _c_amount + "Narration : " + _narration + " Type :RFCDRETBC");

                    // NDB bank collection account Credit entry ...............................
                    m_strQry3 = "insert into gefu_file_generation ("
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
                            + "'" + m_idndb_pdc_txn_master + "',"
                            + "'" + _account + "',"
                            + "'LKR',"
                            + "'" + date + "',"
                            + "'" + df.format(_amount) + "',"
                            + "'" + _narration + "',"
                            + "'" + _credit_debit + "',"
                            + "'" + _profit_centre + "',"
                            + "'" + _DAO + "',"
                            + "'" + df.format(_c_amount) + "',"
                            + "'" + df.format(_d_amount) + "',"
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
                    log.info("ACC. ENTRY : Seller receivable finance account creditt/ MY SQL Quiery " + m_strQry3);

                    if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    // end of return chagers entries................
                }

                if (m_pdc_req_financing.equals("CW")) {

                    String _account = "";
                    double _amount = 0.00;
                    String _narration = "";
                    String _credit_debit = "";
                    String _profit_centre = "";
                    String _DAO = "";
                    double _c_amount = 0.00;
                    double _d_amount = 0.00;
                    String[] gefu_date = _system_date.split("/");
                    String gefu_day = gefu_date[0];
                    String gefu_month = gefu_date[1];
                    String gefu_year = gefu_date[2];
                    String date = gefu_year + gefu_month + gefu_day;

                    _amount = Double.parseDouble(NDBChqReturnChg);
                    _account = cms_curr_acc_number;
                    _credit_debit = "D";

                    _narration = "RTCH" + m_rtn_chq_number + "RS" + m_rtn_chq_amu + buyer_name;
                    _d_amount = _amount;

                    log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                    log.info("ACC. ENTRY : Seller current account number debit Acc: " + _account + ". Debit Amu: " + _d_amount + "Narration : " + _narration + " Type :CHQRETCHGCD");

                    if (_amount != 0) {
                        // customer curr ACC debit entry ...............................
                        m_strQry3 = "insert into gefu_file_generation ("
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
                                + "'" + m_idndb_pdc_txn_master + "',"
                                + "'" + _account + "',"
                                + "'LKR',"
                                + "'" + date + "',"
                                + "'" + df.format(_amount) + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + _profit_centre + "',"
                                + "'" + _DAO + "',"
                                + "'" + df.format(_c_amount) + "',"
                                + "'" + df.format(_d_amount) + "',"
                                + "'ACTIVE',"
                                + "'AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + _system_date + "',"
                                + "'CHQRETCHGCD',"
                                + "'NO',"
                                + "'DAILY')";

                        log.info("ACC. ENTRY : Seller current account number debit Acc: MY SQL QUIERY" + m_strQry);

                        if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        _c_amount = _amount;
                        _d_amount = 0.00;
                        _account = cms_collection_acc_number;
                        _credit_debit = "C";
                        _narration = "RTCH" + m_rtn_chq_number + "RS" + m_rtn_chq_amu + buyer_name;
                        log.info("ACC. ENTRY : customer RF account Credit ACC Number: " + _account + ". credit Amu: " + _c_amount + "Narration : " + _narration + " Type :CHQRETCHGBD");

                        // NDB bank collection account Credit entry ...............................
                        m_strQry3 = "insert into gefu_file_generation ("
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
                                + "'" + m_idndb_pdc_txn_master + "',"
                                + "'" + _account + "',"
                                + "'LKR',"
                                + "'" + date + "',"
                                + "'" + df.format(_amount) + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + _profit_centre + "',"
                                + "'" + _DAO + "',"
                                + "'" + df.format(_c_amount) + "',"
                                + "'" + df.format(_d_amount) + "',"
                                + "'ACTIVE',"
                                + "'AUTHORIZED',"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + m_str_user_id + "',"
                                + "NOW(),"
                                + "'" + _system_date + "',"
                                + "'CHQRETCHGBD',"
                                + "'NO',"
                                + "'DAILY')";

                        if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        log.info("ACC. ENTRY : NDB Collection PL Credit/  MY SQL QUIERY" + m_strQry);
                    }

                }

                m_strQry3 = "update ndb_return_file_upload set rtn_status='DEACTIVE'"
                        + " where idndb_return_file_upload='" + m_idndb_return_file_upload + "'";

                if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            }

            m_strQry3 = "update ndb_return_file_upload set rtn_status='DEACTIVE'";

            if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                throw new Exception("Error Occured in insert user-roles");
            }

            return_file_proccess = "1111";

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }

            log.info("Erly Lqd Process,, Return cheque checking process and gefu antries generated....");

        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in cheque return proceee, Exception" + e);
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_rs2 != null) {
                    _rs2.close();
                }
                if (_rs3 != null) {
                    _rs3.close();
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
                log.info("error occured" + e.getLocalizedMessage());
            }
        }
        return return_file_proccess;
    }

    public String dayEndProccess(String str_user_id) {
        DecimalFormat df = new DecimalFormat("###.00");
        log.info("Day end proccess start.");
        String day_end_proccess = "0000";
        String m_str_user_id = str_user_id;

        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";

        String m_idndb_pdc_txn_master = "";
        String m_pdc_req_financing = "";
        String _system_date = "";
        String buyer_name = "";
        String pdc_chq_number = "";
        String rec_finance_balance_transfer = "";
        double pdc_chq_discounting_amu = 0.00;
        double pdc_chq_cr_amu = 0.00;
        double pdc_chq_amu = 0.00;

        try {
            pdcDAO dao = new pdcDAO();
            _connectionPool = ConnectionPool.getInstance();
            _currentCon = _connectionPool.getJDBCConnection();
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            m_strQry = "select * from ndb_system_date";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                _system_date = _rs.getString("_system_date");

            }

            m_strQry2 = "SELECT * FROM ndb_day_today_process where processed_date='" + _system_date + "' and day_today_process_name='RETLEQDPROCESS'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (!_rs2.next()) {
                day_end_proccess = "0022";
                throw new Exception("Day end process cant be process until return lequidation process end.");
            }

            m_strQry2 = "SELECT * FROM ndb_bank_master_file where bank_approval='UN-AUTHORIZED'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                day_end_proccess = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry2 = "SELECT * FROM ndb_branch_master_file where branch_approval='UN-AUTHORIZED'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                day_end_proccess = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry2 = "SELECT * FROM ndb_customer_define where cust_auth='UN-AUTHORIZED'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                day_end_proccess = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry2 = "SELECT * FROM ndb_cust_prod_map where prod_relationship_auth='UN-AUTHORIZED'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                day_end_proccess = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry2 = "SELECT * FROM ndb_seller_has_buyers where sl_has_byr_auth='UN-AUTHORIZED'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                day_end_proccess = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry2 = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status_auth='UN-AUTHORIZED'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            if (_rs2.next()) {
                day_end_proccess = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry2 = "SELECT\n"
                    + "nptm.idndb_pdc_txn_master,\n"
                    + "nptm.pdc_req_financing,\n"
                    + "nptm.idndb_customer_define_seller_id,\n"
                    + "nptm.idndb_customer_define_buyer_id,\n"
                    + "nptm.pdc_chq_amu,\n"
                    + "nptm.pdc_chq_number,\n"
                    + "nptm.pdc_chq_discounting_amu,\n"
                    + "nptm.pdc_chq_cr_amu,\n"
                    + "ncd.cust_name as ndb_customer_define_cust_name,\n"
                    + "ncd2.rec_finance_curr_ac,\n"
                    + "ncd2.rec_finance_acc_num,\n"
                    + "ncd2.rec_finance_cr_dsc_proc_acc_num,\n"
                    + "ncd2.rec_finance_margin_ac,\n"
                    + "ncd2.rec_finance_margin,\n"
                    + "ncd2.cust_name,\n"
                    + "IFNULL(nrf.rec_finance_balance_transfer,'BALANCE TRANSFER ACTIVE') as rec_finance_balance_transfer\n"
                    + "FROM ndb_pdc_txn_master nptm\n"
                    + "INNER JOIN  ndb_seller_has_buyers nshb\n"
                    + "ON nshb.idndb_seller_has_buyers=nptm.idndb_customer_define_buyer_id\n"
                    + "INNER JOIN ndb_cust_prod_map ncpm\n"
                    + "ON ncpm.idndb_cust_prod_map= nshb.idndb_customer_define_buyer\n"
                    + "INNER JOIN ndb_cust_prod_map ncpm2\n"
                    + "ON ncpm2.idndb_cust_prod_map =nptm.idndb_customer_define_seller_id \n"
                    + "INNER JOIN ndb_customer_define ncd\n"
                    + "ON ncd.idndb_customer_define=ncpm.idndb_customer_define\n"
                    + "INNER JOIN ndb_customer_define ncd2\n"
                    + "ON ncd2.idndb_customer_define=ncpm2.idndb_customer_define \n"
                    + "INNER JOIN ndb_rec_fin nrf\n"
                    + "ON  nrf.idndb_cust_prod_map =ncpm2.idndb_cust_prod_map\n"
                    + "where nptm.pdc_chq_status in ('ACTIVE','ADDITIONALDAY') \n"
                    + "and nptm.pdc_chq_status_auth='AUTHORIZED' \n"
                    + "and nptm.pdc_lquid_date='" + _system_date + "'\n"
                    + "and nptm.pdc_req_financing='RF'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);

            log.info("Day end proccess start with sql quiery " + m_strQry2);
            while (_rs2.next()) {

                m_idndb_pdc_txn_master = _rs2.getString("idndb_pdc_txn_master");
                m_pdc_req_financing = _rs2.getString("pdc_req_financing");
                rec_finance_balance_transfer = _rs2.getString("rec_finance_balance_transfer");
                pdc_chq_amu = _rs2.getDouble("pdc_chq_amu");
                pdc_chq_number = _rs2.getString("pdc_chq_number");
                pdc_chq_discounting_amu = _rs2.getDouble("pdc_chq_discounting_amu");
                pdc_chq_cr_amu = _rs2.getDouble("pdc_chq_cr_amu");

                m_strQry3 = "update ndb_pdc_txn_master set pdc_chq_status='PROCESSED',"
                        + "pdc_chq_mod_by='" + m_str_user_id + "',"
                        + "pdc_chq_mod_date=now()"
                        + " where idndb_pdc_txn_master='" + m_idndb_pdc_txn_master + "'";
                log.info("Day end proccess start & update idndb_pdc_txn_master as processed  MY SQL Quiery" + m_strQry3);
                if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                buyer_name = _rs2.getString("ndb_customer_define_cust_name");

                // extract cw commision data
                String rec_finance_curr_ac = "";
                String rec_finance_acc_num = "";

                rec_finance_curr_ac = _rs2.getString("rec_finance_curr_ac");
                rec_finance_acc_num = _rs2.getString("rec_finance_acc_num");
                String _account = "";
                double _amount = 0.00;
                String _narration = "";
                String _credit_debit = "";
                String _profit_centre = "";
                String _DAO = "";
                double _c_amount = 0.00;
                double _d_amount = 0.00;

                if (m_pdc_req_financing.equals("RF") & rec_finance_balance_transfer.equals("BALANCE TRANSFER ACTIVE")) {
                    log.info("RF Faaclity balance amount crediting start ");

                    // RF Return entry.............................
                    _account = "";
                    _amount = 0.00;
                    _narration = "";
                    _credit_debit = "";
                    _profit_centre = "";
                    _DAO = "";
                    _c_amount = 0.00;
                    _d_amount = 0.00;

                    String[] gefu_date = _system_date.split("/");
                    String gefu_day = gefu_date[0];
                    String gefu_month = gefu_date[1];
                    String gefu_year = gefu_date[2];
                    String date = gefu_year + gefu_month + gefu_day;

                    _amount = pdc_chq_cr_amu;
                    _account = rec_finance_acc_num;
                    _credit_debit = "D";
                    _narration = "BT" + pdc_chq_number + "RS" + pdc_chq_amu + buyer_name;
                    _d_amount = _amount;
                    if (!(_amount == 0 || _amount == 0.00)) {

                        log.info("ACC. ENTRY : Seller receviable finance account number debit & seller collection account credit");
                        log.info("ACC. ENTRY : Seller receviable finance number debit/ debit Acc: " + _account + ". Debit Amu: " + _d_amount + "Narration : " + _narration + " Type :RFCDRETCD");

                        // customer curr ACC debit entry ...............................
                        m_strQry3 = "insert into gefu_file_generation ("
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
                                + "'" + m_idndb_pdc_txn_master + "',"
                                + "'" + _account + "',"
                                + "'LKR',"
                                + "'" + date + "',"
                                + "'" + df.format(_amount) + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + _profit_centre + "',"
                                + "'" + _DAO + "',"
                                + "'" + df.format(_c_amount) + "',"
                                + "'" + df.format(_d_amount) + "',"
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

                        if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                        _account = rec_finance_curr_ac;
                        _c_amount = _amount;
                        _d_amount = 0.00;
                        _credit_debit = "C";

                        _narration = "BT" + pdc_chq_number + "RS" + pdc_chq_amu + buyer_name;

                        log.info("ACC. ENTRY : Seller collection account credit/ credit Acc: " + _account + ". credit Amu: " + _c_amount + "Narration : " + _narration + " Type :RFCDRETBC");

                        // NDB bank collection account Credit entry ...............................
                        m_strQry3 = "insert into gefu_file_generation ("
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
                                + "'" + m_idndb_pdc_txn_master + "',"
                                + "'" + _account + "',"
                                + "'LKR',"
                                + "'" + date + "',"
                                + "'" + df.format(_amount) + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + _profit_centre + "',"
                                + "'" + _DAO + "',"
                                + "'" + df.format(_c_amount) + "',"
                                + "'" + df.format(_d_amount) + "',"
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

                        if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                    }

                }

            }

            // Madhawa_4799 CR to DR CW cheque deposit transfer -----------------------------------------
            m_strQry2 = "SELECT\n"
                    + "nptm.idndb_pdc_txn_master,\n"
                    + "nptm.pdc_req_financing,\n"
                    + "nptm.idndb_customer_define_seller_id,\n"
                    + "nptm.idndb_customer_define_buyer_id,\n"
                    + "nptm.pdc_chq_amu,\n"
                    + "nptm.pdc_chq_number,\n"
                    + "nptm.pdc_chq_discounting_amu,\n"
                    + "nptm.pdc_chq_cr_amu,\n"
                    + "IF(nptm.pdc_chq_wh_dr_to_cr_spe_narr IS NULL, concat(\"CHQNO\",nptm.pdc_chq_number), pdc_chq_wh_dr_to_cr_spe_narr) as pdc_chq_wh_dr_to_cr_spe_narr,\n"
                    + "ncd.cust_name as ndb_customer_define_cust_name,\n"
                    + "ncd2.rec_finance_curr_ac,\n"
                    + "ncd2.rec_finance_acc_num,\n"
                    + "ncd2.rec_finance_cr_dsc_proc_acc_num,\n"
                    + "ncd2.rec_finance_margin_ac,\n"
                    + "ncd2.rec_finance_margin,\n"
                    + "ncd2.cms_collection_acc_number,\n"
                    + "ncd2.cms_curr_acc_number,\n"
                    + "ncd2.cust_name,\n"
                    + "ncw.chq_wh_dr_to_cr_spe_narr\n"
                    + "FROM ndb_pdc_txn_master nptm\n"
                    + "INNER JOIN  ndb_seller_has_buyers nshb\n"
                    + "ON nshb.idndb_seller_has_buyers=nptm.idndb_customer_define_buyer_id\n"
                    + "INNER JOIN ndb_cust_prod_map ncpm\n"
                    + "ON ncpm.idndb_cust_prod_map= nshb.idndb_customer_define_buyer\n"
                    + "INNER JOIN ndb_cust_prod_map ncpm2\n"
                    + "ON ncpm2.idndb_cust_prod_map =nptm.idndb_customer_define_seller_id \n"
                    + "INNER JOIN ndb_customer_define ncd\n"
                    + "ON ncd.idndb_customer_define=ncpm.idndb_customer_define\n"
                    + "INNER JOIN ndb_customer_define ncd2\n"
                    + "ON ncd2.idndb_customer_define=ncpm2.idndb_customer_define \n"
                    + "INNER JOIN ndb_chq_wh ncw\n"
                    + "ON  ncw.idndb_cust_prod_map =ncpm2.idndb_cust_prod_map\n"
                    + "where nptm.pdc_chq_status in ('ACTIVE','ADDITIONALDAY') \n"
                    + "and nptm.pdc_chq_status_auth='AUTHORIZED' \n"
                    + "and nptm.pdc_lquid_date='" + _system_date + "' \n"
                    + "and nptm.pdc_req_financing='CW'";

            _rs2 = _stmnt2.executeQuery(m_strQry2);

            log.info("cr to dr with cw special narration " + m_strQry2);
            while (_rs2.next()) {

                m_idndb_pdc_txn_master = _rs2.getString("idndb_pdc_txn_master");
                pdc_chq_amu = _rs2.getDouble("pdc_chq_amu");
                pdc_chq_number = _rs2.getString("pdc_chq_number");

                m_strQry3 = "update ndb_pdc_txn_master set pdc_chq_status='PROCESSED',"
                        + "pdc_chq_mod_by='" + m_str_user_id + "',"
                        + "pdc_chq_mod_date=now()"
                        + " where idndb_pdc_txn_master='" + m_idndb_pdc_txn_master + "'";
                log.info("Day end proccess start & update idndb_pdc_txn_master as processed  MY SQL Quiery" + m_strQry3);
                if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                String cms_collection_acc_number = "";
                String cms_curr_acc_number = "";
                String pdc_chq_wh_dr_to_cr_spe_narr = "";
                String chq_wh_dr_to_cr_spe_narr = "";

                cms_collection_acc_number = _rs2.getString("cms_collection_acc_number");
                cms_curr_acc_number = _rs2.getString("cms_curr_acc_number");
                pdc_chq_wh_dr_to_cr_spe_narr = _rs2.getString("pdc_chq_wh_dr_to_cr_spe_narr");
                chq_wh_dr_to_cr_spe_narr = _rs2.getString("chq_wh_dr_to_cr_spe_narr");
                String _account = "";
                double _amount = 0.00;
                String _narration = "";
                String _credit_debit = "";
                String _profit_centre = "";
                String _DAO = "";
                double _c_amount = 0.00;
                double _d_amount = 0.00;

                if (chq_wh_dr_to_cr_spe_narr.equals("1")) {
                    log.info("RF Faaclity balance amount crediting start ");

                    // RF Return entry.............................
                    _account = "";
                    _amount = 0.00;
                    _narration = "";
                    _credit_debit = "";
                    _profit_centre = "";
                    _DAO = "";
                    _c_amount = 0.00;
                    _d_amount = 0.00;

                    String[] gefu_date = _system_date.split("/");
                    String gefu_day = gefu_date[0];
                    String gefu_month = gefu_date[1];
                    String gefu_year = gefu_date[2];
                    String date = gefu_year + gefu_month + gefu_day;

                    _amount = pdc_chq_amu;
                    _account = cms_collection_acc_number;
                    _credit_debit = "D";
                    _narration = pdc_chq_wh_dr_to_cr_spe_narr;
                    _d_amount = _amount;
                    if (!(_amount == 0 || _amount == 0.00)) {
                        m_strQry3 = "insert into gefu_file_generation ("
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
                                + "'" + m_idndb_pdc_txn_master + "',"
                                + "'" + _account + "',"
                                + "'LKR',"
                                + "'" + date + "',"
                                + "'" + df.format(_amount) + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + _profit_centre + "',"
                                + "'" + _DAO + "',"
                                + "'" + df.format(_c_amount) + "',"
                                + "'" + df.format(_d_amount) + "',"
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

                        if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                        _account = cms_curr_acc_number;
                        _c_amount = _amount;
                        _d_amount = 0.00;
                        _credit_debit = "C";

                        _narration = pdc_chq_wh_dr_to_cr_spe_narr;

                        m_strQry3 = "insert into gefu_file_generation ("
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
                                + "'" + m_idndb_pdc_txn_master + "',"
                                + "'" + _account + "',"
                                + "'LKR',"
                                + "'" + date + "',"
                                + "'" + df.format(_amount) + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + _profit_centre + "',"
                                + "'" + _DAO + "',"
                                + "'" + df.format(_c_amount) + "',"
                                + "'" + df.format(_d_amount) + "',"
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

                        if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                    }

                }

            }

            //-------------------------------------------------------------------------------------------
            String[] date_spliter = _system_date.split("/");
            int m_year = Integer.parseInt(date_spliter[2]);
            int m_month = Integer.parseInt(date_spliter[1]) - 1;
            int m_day = Integer.parseInt(date_spliter[0]);
            Calendar ced = Calendar.getInstance();

            ced.set(Calendar.YEAR, m_year); // set the year
            ced.set(Calendar.MONTH, m_month); // set the month
            ced.set(Calendar.DAY_OF_MONTH, m_day);
            ced.add(Calendar.DATE, 1);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            String m_next_system_date = dao.getchequeValueDate(formatter.format(ced.getTime()));

            m_strQry = "update ndb_system_date set _system_date='" + m_next_system_date + "' where idndb_system_date='1'";

            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                throw new Exception("Error Occured in insert user-roles");
            }

            m_strQry = "insert into ndb_day_today_process (day_today_process_name,"
                    + "processed_date,"
                    + "processed_by"
                    + ",processed_time"
                    + ""
                    + ") values("
                    + "'DAYENDPROCESS',"
                    + "'" + _system_date + "',"
                    + "'" + m_str_user_id + "',"
                    + "now())";
            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                throw new Exception("Error Occured in insert user-roles");
            }

            m_strQry = "insert into ndb_day_end (cob_date,"
                    + "run_by,"
                    + "run_time"
                    + ""
                    + ") values("
                    + "'" + _system_date + "',"
                    + "'" + m_str_user_id + "',"
                    + "now())";
            if (_stmnt.executeUpdate(m_strQry) <= 0) {
                throw new Exception("Error Occured in insert user-roles");
            }

            day_end_proccess = "1111";

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }

        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in dayend process, Exception" + e);
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_rs2 != null) {
                    _rs2.close();
                }
                if (_rs3 != null) {
                    _rs3.close();
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
        return day_end_proccess;
    }

    public static void main(String[] args) {
        proccessDAO pcsDAO = new proccessDAO();
        pcsDAO.runFixedChargedBaseCommisionProccess("Madhawa_4799");
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
