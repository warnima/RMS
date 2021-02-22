/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBOops;

import DBAutoFillBeans.comboDAO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import ndb.connection.ConnectionPool;
import utility.Parameters;
import utility.Utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Madhawa_4799
 */
public class GEFUFileGenerator {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(GEFUFileGenerator.class.getName());
    static ConnectionPool _connectionPool;
    static Connection _currentCon = null;
    static ResultSet _rs = null;
    static ResultSet _rs2 = null;
    static ResultSet _rs3 = null;
    static Exception _exception;
    static Statement _stmnt;
    static Statement _stmnt2;
    static Statement _stmnt3;

    public static void main(String[] args) {
        GEFUFileGenerator gfg = new GEFUFileGenerator();
        // gfg.processRFCommisionEntries("30/05/2016", "Madhawa_4799");
        gfg.generateGEFUFile("Madhawa_4799", "28/05/2016");

    }

    public String generateGEFUFile(String str_user_id, String acc_gen_date) {
        DecimalFormat df = new DecimalFormat("####.00");
        String m_strsql = "";
        String m_strsql2 = "";
        ResultSet m_rs1 = null;
        String result = "faill";
        String m_amountsum = "";
        String m_NumericValofAccount = "";
        String m_checksumval = "";
        long m_sumNumericValofAccount = 0;

        //  Last 5 Numbers from m_sumNumericValofAccount 
        Parameters para = new Parameters();
        ResourceBundle rb = ResourceBundle.getBundle("System", Locale.getDefault());

        // Square Root of X1
        double X2 = 0;

        // Integer Value of X2
        int X3 = 0;

        // SumOfAmounts*100
        double Y1 = 0.0;

        //  Last 5 Numbers from Y1
        // Square Root of Y2
        double Y3 = 0.00;

        // Integer Value of Y3
        int Y4 = 0;
        // Check Sum Value - Concat X1,X3,Y2,Y4

        String m_cheksum = "0";

        try {
            log.info("GEFU Request, start process rf commision entries and cw commision entries...");
            if (processRFCommisionEntries(acc_gen_date, str_user_id) && processCWCommisionEntries(acc_gen_date, str_user_id)) {
                log.info("GEFU Request, start processing marging entries...");
                if (processMarginEntries(acc_gen_date, str_user_id)) {
                    log.info("GEFU Request, start processing bulk credits...");
                    if (processBulkCredits(acc_gen_date, str_user_id)) {

                        log.info("GEFU Request , start genearate GEFU file...");
                        _connectionPool = ConnectionPool.getInstance();
                        _currentCon = _connectionPool.getJDBCConnection();
                        if (!startConnection(_currentCon)) {
                            throw new Exception("Error occured in start transaction");
                        }
                        _stmnt = _currentCon.createStatement();
                        _stmnt2 = _currentCon.createStatement();
                        _stmnt3 = _currentCon.createStatement();
                        String _system_date = acc_gen_date;

                        String[] sys_date_mode = _system_date.split("/");
                        String mod_day = sys_date_mode[0];
                        String mod_month = sys_date_mode[1];
                        String mod_year = sys_date_mode[2];
                        String _mod_system_date = mod_year + mod_month + mod_day;

                        m_strsql = "SELECT SUM(amount) as sumamount FROM gefu_file_generation WHERE gefu_creation_status='ACTIVE' and status='AUTHORIZED' and date='" + _mod_system_date + "'";
                        m_rs1 = _stmnt.executeQuery(m_strsql);
                        if (m_rs1.next()) {
                            m_amountsum = m_rs1.getString("sumamount");
                        }
                        if (m_amountsum == null) {
                            throw new Exception("Transactional data not available for GEFU file creation");
                        }
                        String m_NumericValofAccount_regen = "";
                        m_strsql = "SELECT account,idgefu_file_generation FROM gefu_file_generation WHERE gefu_creation_status='ACTIVE' and status='AUTHORIZED'";
                        m_rs1 = _stmnt.executeQuery(m_strsql);
                        while (m_rs1.next()) {

                            String str = m_rs1.getString("account");

                            m_NumericValofAccount_regen = str.replaceAll("[^0-9]", "");

                            m_sumNumericValofAccount = m_sumNumericValofAccount + Long.valueOf(m_NumericValofAccount_regen);

                        }

                        String X1_word = new DecimalFormat("#").format(m_sumNumericValofAccount);

                        String X1 = X1_word.substring(X1_word.length() - 5);

                        X2 = (int) Math.sqrt(Integer.parseInt(X1));
                        X3 = (int) X2;

                        String Y1_word = "";
                        DecimalFormat dft = new DecimalFormat("##0");
                        if (Double.parseDouble(m_amountsum) % 1 == 0) {

                            Y1_word = dft.format(Double.parseDouble(m_amountsum)) + "00";

                        } else {

                            Y1_word = String.valueOf(dft.format(Double.parseDouble(m_amountsum) * 100));

                        }

                        String Y2 = "0";
                        if (Y1_word.length() < 5) {
                            Y2 = Y1_word;
                        } else {

                            Y2 = Y1_word.substring(Y1_word.length() - 5);
                        }

                        Y3 = (int) Math.sqrt(Integer.parseInt(Y2));
                        Y4 = (int) Y3;

                        m_cheksum = "" + X1 + X3 + Y2 + Y4;

                        String filename = "PDCHQ." + _mod_system_date + ".001";
                        String folserpath = rb.getString("GEFUDir") + File.separator + _mod_system_date;

                        File file_dir = new File(folserpath);
                        if (!file_dir.exists()) {
                            if (file_dir.mkdir()) {

                            } else {

                            }
                        }
                        File file = new File(folserpath + "\\" + filename);
                        try {
                            log.info("GEFU Request, pre- requested gefu data generated.....");

                            FileOutputStream outputStream = new FileOutputStream(file);
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                            m_strsql = "SELECT account,currency,date,amount,narration,credit_debit,profit_centre,DAO FROM gefu_file_generation where gefu_creation_status='ACTIVE' and status='AUTHORIZED' and date='" + _mod_system_date + "'";
                            m_rs1 = _stmnt.executeQuery(m_strsql);
                            while (m_rs1.next()) {
                                String idgefu_file_generation = "";
                                String account = "";
                                String currency = "";
                                String date = "";
                                String amount = "";
                                String narration = "";
                                String credit_debit = "";
                                String profit_centre = "";
                                String _des_narration = "";
                                String DAO = "";

                                if (m_rs1.getString("account").equals("")) {
                                    throw new Exception("Error occured in file generate");
                                } else {
                                    account = m_rs1.getString("account");
                                }

                                if (m_rs1.getString("currency").equals("")) {
                                    throw new Exception("Error occured in file generate");
                                } else {
                                    currency = m_rs1.getString("currency");
                                }

                                if (m_rs1.getString("date").equals("")) {
                                    throw new Exception("Error occured in file generate");
                                } else {
                                    date = m_rs1.getString("date");
                                }

                                if (m_rs1.getString("amount").equals("")) {
                                    throw new Exception("Error occured in file generate");
                                } else {
                                    amount = m_rs1.getString("amount");
                                }

                                if (m_rs1.getString("narration").equals("")) {
                                    throw new Exception("Error occured in file generate");
                                } else {
                                    narration = m_rs1.getString("narration");
                                    narration = narration.replaceAll("[^a-zA-Z0-9 ]+", "");
                                    int m_Narrlength = narration.length();

                                    if (!(m_Narrlength <= 35)) {
                                        _des_narration = narration.substring(0, 35);
                                    } else {
                                        _des_narration = narration;
                                    }
                                }

                                if (m_rs1.getString("credit_debit").equals("")) {
                                    throw new Exception("Error occured in file generate");
                                } else {
                                    credit_debit = m_rs1.getString("credit_debit");
                                }

                                if (!m_rs1.getString("profit_centre").equals("")) {
                                    profit_centre = m_rs1.getString("profit_centre") + ",";
                                }
                                if (!m_rs1.getString("DAO").equals("")) {
                                    DAO = m_rs1.getString("DAO") + ",";
                                }
                                String line = account + "," + currency + "," + date + "," + df.format(Double.parseDouble(amount)) + "," + _des_narration + "," + credit_debit + "," + DAO + "";

                                bufferedWriter.write(line);
                                bufferedWriter.newLine();
                            }

                            bufferedWriter.write(m_cheksum);
                            bufferedWriter.newLine();
                            String line_emty = "";

                            bufferedWriter.write(line_emty);

                            bufferedWriter.close();

                            log.info("GEFU Request, GEFU file generated....");
                            log.info("GEFU REquest, Generated GEFU file data update in db process start...");
                            m_strsql = "SELECT * FROM gefu_file_generation where gefu_creation_status='ACTIVE' and status='AUTHORIZED' and date='" + _mod_system_date + "'";
                            m_rs1 = _stmnt.executeQuery(m_strsql);
                            while (m_rs1.next()) {
                                String idgefu_file_generation = m_rs1.getString("idgefu_file_generation");
                                m_strsql2 = "Update gefu_file_generation set gefu_creation_status='PROCESSED',"
                                        + "mod_by='" + str_user_id + "',"
                                        + "mod_date=now() where idgefu_file_generation='" + idgefu_file_generation + "'";
                                if (_stmnt2.executeUpdate(m_strsql2) <= 0) {
                                    throw new Exception("Error Occured in insert user-roles");
                                }
                            }
                            log.info("GEFU REquest, Generated GEFU file data update in db process end...");

                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            Date _system_date_formatted = formatter.parse(_system_date);
                            String last_day_of_month = formatter.format(c.getTime());

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
                            Date d = formatter.parse(last_day_of_month);

                            Date date_last_day_of_month = formatter.parse(last_day_of_month);

                            log.info("GEFU Request, start update day today process update..");
                            String m_strQry2 = "select * from ndb_day_today_process where day_today_process_name='MONTHENDPROFIT' and processed_date='" + last_day_of_month + "'";
                            _rs2 = _stmnt2.executeQuery(m_strQry2);
                            if (!_rs2.next()) {
                                if ((date_last_day_of_month.equals(_system_date_formatted)) || (d.after(min) && d.before(max))) {

                                    m_strsql = "insert into ndb_day_today_process (day_today_process_name,"
                                            + "processed_date,"
                                            + "processed_by"
                                            + ",processed_time"
                                            + ""
                                            + ") values("
                                            + "'MONTHENDPROFIT',"
                                            + "'" + last_day_of_month + "',"
                                            + "'" + str_user_id + "',"
                                            + "now())";
                                    if (_stmnt3.executeUpdate(m_strsql) <= 0) {
                                        throw new Exception("Error Occured in insert user-roles");
                                    }
                                }
                            }
                            log.info("GEFU Request, end update day today process update..");

                            log.info("GEFU Request, start update last day ened date...");
                            String _last_system_date = new pdcDAO().getSystemLastCOBDate();

                            m_strsql = "insert into ndb_day_today_process (day_today_process_name,"
                                    + "processed_date,"
                                    + "processed_by"
                                    + ",processed_time"
                                    + ""
                                    + ") values("
                                    + "'GEFUPROCESS',"
                                    + "'" + _last_system_date + "',"
                                    + "'" + str_user_id + "',"
                                    + "now())";
                            if (_stmnt.executeUpdate(m_strsql) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                            m_strsql = "INSERT INTO gefu_file_generation_hst SELECT * FROM gefu_file_generation";
                            if (_stmnt.executeUpdate(m_strsql) <= 0) {
                                throw new Exception("Error Occured GEFU data history file inseting");
                            }
                            m_strsql = "delete from gefu_file_generation";
                            if (_stmnt.executeUpdate(m_strsql) <= 0) {
                                throw new Exception("Error Occured deleting GEFU data");
                            }
                            log.info("GEFU Request, GEFU file generated successfully.....");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (!endConnection(_currentCon)) {
                            throw new Exception("Error occured in end transaction");
                        }
                        result = "success=" + folserpath;
                    } else {
                        result = "fail=BULKCRDT&DATE";

                    }
                } else {
                    result = "fail=MARGINGEFU&DATE";

                }
            } else {
                result = "fail=COMMISIONCHGCPOMRESS&DATE";
            }
        } catch (Exception e) {
            result = "faill=" + e.getLocalizedMessage();
            abortConnection(_currentCon);
            log.error("Error occured in generating GEFU file, Exception" + e);
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

        return result;

    }

    public boolean processBulkCredits(String _gefu_gen_date, String m_user_id) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("###.00");

        String m_strQry = "";
        String m_strQry2 = "";

        boolean m_ardy_exist_data = true;
        log.info("GEFU Request, Bulk credit process start...");

        try {

            if (!startConnection(_currentCon)) {

                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            double _amount = 0.00;
            String _account = "";
            String _credit_debit = "";
            String date = "";
            String _narration = "";
            String profit_centre = "";
            String DAO = "";
            String currency = "";
            String system_date = "";
            String m_str_user_id = "";
            double _c_amount = 0.00;
            double _d_amount = 0.00;
            m_strQry2 = "SELECT * FROM ndb_day_today_process where processed_date='" + _gefu_gen_date + "' and day_today_process_name='BULKCREDITPROCESS'";
            _rs2 = _stmnt2.executeQuery(m_strQry2);
            boolean bulk_credit_process = false;
            if (!_rs2.next()) {
                bulk_credit_process = true;
            }

            if (bulk_credit_process) {

                m_strQry = "SELECT SUM(amount) as sum_bulk_credit,currency,date, idgefu_file_generation,idndb_pdc_txn_master,account,date,creat_by,system_date,narration FROM gefu_file_generation where gefu_creation_status='ACTIVE' and status='AUTHORIZED' and bulk_credit='BULK CREDIT ACTIVE' group by account";
                _rs = _stmnt.executeQuery(m_strQry);
                while (_rs.next()) {
                    String idgefu_file_generation = _rs.getString("idgefu_file_generation");
                    String idndb_customer_define_seller_id = "";
                    String cust_name = "";

                    m_strQry2 = "SELECT\n"
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
                            + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
                            + "     ndb_customer_define.`cust_id` AS ndb_customer_define_cust_id,\n"
                            + "     ndb_customer_define.`cust_name` AS ndb_customer_define_cust_name\n"
                            + "FROM\n"
                            + "     `ndb_pdc_txn_master` ndb_pdc_txn_master INNER JOIN `gefu_file_generation` gefu_file_generation ON ndb_pdc_txn_master.`idndb_pdc_txn_master` = gefu_file_generation.`idndb_pdc_txn_master`\n"
                            + "     INNER JOIN `ndb_cust_prod_map` ndb_cust_prod_map ON ndb_pdc_txn_master.`idndb_customer_define_seller_id` = ndb_cust_prod_map.`idndb_cust_prod_map`\n"
                            + "     INNER JOIN `ndb_customer_define` ndb_customer_define ON ndb_cust_prod_map.`idndb_customer_define` = ndb_customer_define.`idndb_customer_define` where gefu_file_generation.`idgefu_file_generation`='" + idgefu_file_generation + "'";
                    _rs2 = _stmnt2.executeQuery(m_strQry2);
                    if (_rs2.next()) {
                        idndb_customer_define_seller_id = _rs2.getString("ndb_customer_define_idndb_customer_define");
                        cust_name = _rs2.getString("ndb_customer_define_cust_name");

                    }

                    log.info("Bulk Credit Tranasction Recevied Account Number :" + _rs.getString("account"));

                    _amount = Double.parseDouble(_rs.getString("sum_bulk_credit"));
                    _account = _rs.getString("account");
                    _credit_debit = "C";
                    _narration = "DISBBULKCREDIT" + cust_name;
                    _c_amount = _amount;
                    _d_amount = 0.00;
                    m_str_user_id = _rs.getString("creat_by");
                    system_date = _rs.getString("system_date");
                    currency = _rs.getString("currency");
                    date = _rs.getString("date");

                    // RF facility customer Collection Account Credit entry ...............................
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
                            + "'" + idndb_customer_define_seller_id + "DISBBULKCREDIT" + cust_name + "',"
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
                            + "'AUTHORIZED',"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + m_str_user_id + "',"
                            + "NOW(),"
                            + "'" + system_date + "',"
                            + "'RFCDBC',"
                            + "'NOT DEFINE',"
                            + "'DAILY')";

                    if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                        log.info("Error Occured in inserting bulk credit combine tranaction _account : " + _account);
                        throw new Exception("Error Occured in insert user-roles");
                    }
                    log.info("Update as one tranaction in gefu file generation table/(Bulk Credit option) account Number :" + _rs.getString("account"));

                }

                m_strQry = "select currency, idgefu_file_generation,idndb_pdc_txn_master,account,date,creat_by,system_date FROM gefu_file_generation where gefu_creation_status='ACTIVE' and status='AUTHORIZED' and bulk_credit='BULK CREDIT ACTIVE'";
                _rs = _stmnt.executeQuery(m_strQry);
                while (_rs.next()) {

                    String idgefu_file_generation = _rs.getString("idgefu_file_generation");
                    String str_user_id = _rs.getString("creat_by");
                    m_strQry2 = "Update gefu_file_generation set gefu_creation_status='PROCESSEDBLKCRDT',"
                            + "mod_by='" + str_user_id + "',"
                            + "mod_date=now() where idgefu_file_generation='" + idgefu_file_generation + "'";
                    if (_stmnt2.executeUpdate(m_strQry2) <= 0) {
                        log.info("Error Occured in uphdating trasctions idgefu_file_generation : " + idgefu_file_generation);
                        throw new Exception("Error Occured in uphdating trasctions idgefu_file_generation : " + idgefu_file_generation);
                    }

                    log.info("Update bulk credit account Number :" + _rs.getString("account") + " all tranactions as PROCESSEDBLKCRDT. these are not allowed to GEFU file. Updated gefu_file_generation " + idgefu_file_generation);

                }

                m_strQry = "insert into ndb_day_today_process (day_today_process_name,"
                        + "processed_date,"
                        + "processed_by"
                        + ",processed_time"
                        + ""
                        + ") values("
                        + "'BULKCREDITPROCESS',"
                        + "'" + _gefu_gen_date + "',"
                        + "'" + m_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            log.info("GEFU Request, Bulk credit entries generated...");

            success = true;
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in processing bulk credit data, Exception" + e);

        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
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

//    public boolean processBulkCommisionCharges() {
//        boolean success = false;
//        _connectionPool = ConnectionPool.getInstance();
//        _currentCon = _connectionPool.getJDBCConnection();
//
//        String m_strQry = "";
//        String m_strQry2 = "";
//        String m_strQry3 = "";
//
//        boolean m_ardy_exist_data = true;
//        log.info("Start Cheking for bulk credit transaction to combine.");
//
//        try {
//
//            if (!startConnection(_currentCon)) {
//
//                throw new Exception("Error occured in start transaction");
//            }
//            _stmnt = _currentCon.createStatement();
//            _stmnt2 = _currentCon.createStatement();
//            _stmnt3 = _currentCon.createStatement();
//
//            double total_debit_amu = 0.00;
//            double _amount = 0.00;
//            String _account = "";
//            String _credit_debit = "";
//            String date = "";
//            String _narration = "";
//            String profit_centre = "";
//            String DAO = "";
//            String currency = "";
//            String system_date = "";
//            String m_str_user_id = "";
//            String bulk_credit = "";
//            String cw_fixed_frequency = "";
//            String idgefu_file_generation = "";
//            String str_user_id = "";
//            double _c_amount = 0.00;
//            double _d_amount = 0.00;
//
//            boolean gefu_entry_check = false;
//
//            m_strQry = "select * from ndb_cust_prod_map where prod_relationship_status='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_key_seller='ACTIVE' and prod_relationship_res_fin='ACTIVE'";
//            _rs = _stmnt.executeQuery(m_strQry);
//            while (_rs.next()) {
//                gefu_entry_check = false;
//
//                m_strQry2 = "SELECT\n"
//                        + "     ndb_pdc_txn_master.`idndb_pdc_txn_master` AS ndb_pdc_txn_master_idndb_pdc_txn_master,\n"
//                        + "     ndb_pdc_txn_master.`pdc_req_financing` AS ndb_pdc_txn_master_pdc_req_financing,\n"
//                        + "     ndb_pdc_txn_master.`idndb_customer_define_seller_id` AS ndb_pdc_txn_master_idndb_customer_define_seller_id,\n"
//                        + "     ndb_pdc_txn_master.`idndb_customer_define_buyer_id` AS ndb_pdc_txn_master_idndb_customer_define_buyer_id,\n"
//                        + "     gefu_file_generation.`idgefu_file_generation` AS gefu_file_generation_idgefu_file_generation,\n"
//                        + "     gefu_file_generation.`idndb_pdc_txn_master` AS gefu_file_generation_idndb_pdc_txn_master,\n"
//                        + "     gefu_file_generation.`account` AS gefu_file_generation_account,\n"
//                        + "     gefu_file_generation.`currency` AS gefu_file_generation_currency,\n"
//                        + "     gefu_file_generation.`date` AS gefu_file_generation_date,\n"
//                        + "     gefu_file_generation.`amount` AS gefu_file_generation_amount,\n"
//                        + "     gefu_file_generation.`narration` AS gefu_file_generation_narration,\n"
//                        + "     gefu_file_generation.`credit_debit` AS gefu_file_generation_credit_debit,\n"
//                        + "     gefu_file_generation.`profit_centre` AS gefu_file_generation_profit_centre,\n"
//                        + "     gefu_file_generation.`DAO` AS gefu_file_generation_DAO,\n"
//                        + "     gefu_file_generation.`c_amount` AS gefu_file_generation_c_amount,\n"
//                        + "     gefu_file_generation.`d_amount` AS gefu_file_generation_d_amount,\n"
//                        + "     gefu_file_generation.`gefu_creation_status` AS gefu_file_generation_gefu_creation_status,\n"
//                        + "     gefu_file_generation.`status` AS gefu_file_generation_status,\n"
//                        + "     gefu_file_generation.`creat_by` AS gefu_file_generation_creat_by,\n"
//                        + "     gefu_file_generation.`creat_date` AS gefu_file_generation_creat_date,\n"
//                        + "     gefu_file_generation.`mod_by` AS gefu_file_generation_mod_by,\n"
//                        + "     gefu_file_generation.`mod_date` AS gefu_file_generation_mod_date,\n"
//                        + "     gefu_file_generation.`system_date` AS gefu_file_generation_system_date,\n"
//                        + "     gefu_file_generation.`cw_fixed_frequency` AS gefu_file_generation_cw_fixed_frequency,\n"
//                        + "     gefu_file_generation.`gefu_type` AS gefu_file_generation_gefu_type,\n"
//                        + "     gefu_file_generation.`bulk_credit` AS gefu_file_generation_bulk_credit\n"
//                        + "FROM\n"
//                        + "     `gefu_file_generation` gefu_file_generation INNER JOIN `ndb_pdc_txn_master` ndb_pdc_txn_master ON gefu_file_generation.`idndb_pdc_txn_master` = ndb_pdc_txn_master.`idndb_pdc_txn_master` where ndb_pdc_txn_master.`idndb_customer_define_seller_id`='" + _rs.getString("idndb_cust_prod_map") + "' and ndb_pdc_txn_master.`pdc_req_financing`='RF' and gefu_file_generation.`gefu_type`='COMCHGCD' and gefu_file_generation.`status`='AUTHORIZED' and gefu_file_generation.`gefu_creation_status`='ACTIVE';";
//                _rs2 = _stmnt2.executeQuery(m_strQry2);
//                System.out.println(m_strQry2);
//
//                while (_rs2.next()) {
//                    total_debit_amu = total_debit_amu + Double.parseDouble(_rs2.getString("gefu_file_generation_d_amount"));
//
//                    _account = _rs2.getString("gefu_file_generation_account");
//                    _narration = _rs2.getString("gefu_file_generation_narration");
//                    m_str_user_id = _rs2.getString("gefu_file_generation_creat_by");
//                    system_date = _rs2.getString("gefu_file_generation_system_date");
//                    currency = _rs2.getString("gefu_file_generation_currency");
//                    date = _rs2.getString("gefu_file_generation_date");
//                    bulk_credit = _rs2.getString("gefu_file_generation_bulk_credit");
//                    cw_fixed_frequency = _rs2.getString("gefu_file_generation_cw_fixed_frequency");
//
//                    idgefu_file_generation = _rs2.getString("gefu_file_generation_idgefu_file_generation");
//                    str_user_id = _rs2.getString("gefu_file_generation_creat_by");
//                    gefu_entry_check = true;
//
//                    m_strQry3 = "Update gefu_file_generation set gefu_creation_status='PROCESSEDCOMMCHG',"
//                            + "mod_by='" + str_user_id + "',"
//                            + "mod_date=now() where idgefu_file_generation='" + idgefu_file_generation + "'";
//                    if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
//
//                        log.info("Error Occured in uphdating trasctions idgefu_file_generation : " + idgefu_file_generation);
//                        throw new Exception("Error Occured in uphdating trasctions idgefu_file_generation : " + idgefu_file_generation);
//                    }
//
//                }
//
//                if (gefu_entry_check) {
//                    System.out.println("id");
//
//                    _amount = total_debit_amu;
//                    System.out.println("DEBIT AMU>" + total_debit_amu);
//                    _credit_debit = "D";
//                    _c_amount = 0.00;
//                    _d_amount = total_debit_amu;
//
//                    // RF facility customer Collection Account Credit entry ...............................
//                    m_strQry3 = "insert into gefu_file_generation ("
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
//                            + "'999999',"
//                            + "'" + _account + "',"
//                            + "'" + currency + "',"
//                            + "'" + date + "',"
//                            + "'" + _amount + "',"
//                            + "'" + _narration + "',"
//                            + "'" + _credit_debit + "',"
//                            + "'" + profit_centre + "',"
//                            + "'" + DAO + "',"
//                            + "'" + _c_amount + "',"
//                            + "'" + _d_amount + "',"
//                            + "'ACTIVE',"
//                            + "'AUTHORIZED',"
//                            + "'" + m_str_user_id + "',"
//                            + "NOW(),"
//                            + "'" + m_str_user_id + "',"
//                            + "NOW(),"
//                            + "'" + system_date + "',"
//                            + "'COMCHGCD',"
//                            + "'" + bulk_credit + "',"
//                            + "'" + cw_fixed_frequency + "')";
//
//                    if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
//                        log.info("Error Occured in inserting bulk credit combine tranaction _account : " + _account);
//                        throw new Exception("Error Occured in insert user-roles");
//                    }
//                    log.info("Update as one tranaction in gefu file generation table/(Bulk Credit option) account Number :" + _account);
//
//                }
//
//            }
//
//            gefu_entry_check = false;
//
//            m_strQry = "select * from ndb_cust_prod_map where prod_relationship_status='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_key_seller='ACTIVE' and prod_relationship_res_fin='ACTIVE'";
//            _rs = _stmnt.executeQuery(m_strQry);
//            while (_rs.next()) {
//                gefu_entry_check = false;
//
//                m_strQry2 = "SELECT\n"
//                        + "     ndb_pdc_txn_master.`idndb_pdc_txn_master` AS ndb_pdc_txn_master_idndb_pdc_txn_master,\n"
//                        + "     ndb_pdc_txn_master.`pdc_req_financing` AS ndb_pdc_txn_master_pdc_req_financing,\n"
//                        + "     ndb_pdc_txn_master.`idndb_customer_define_seller_id` AS ndb_pdc_txn_master_idndb_customer_define_seller_id,\n"
//                        + "     ndb_pdc_txn_master.`idndb_customer_define_buyer_id` AS ndb_pdc_txn_master_idndb_customer_define_buyer_id,\n"
//                        + "     gefu_file_generation.`idgefu_file_generation` AS gefu_file_generation_idgefu_file_generation,\n"
//                        + "     gefu_file_generation.`idndb_pdc_txn_master` AS gefu_file_generation_idndb_pdc_txn_master,\n"
//                        + "     gefu_file_generation.`account` AS gefu_file_generation_account,\n"
//                        + "     gefu_file_generation.`currency` AS gefu_file_generation_currency,\n"
//                        + "     gefu_file_generation.`date` AS gefu_file_generation_date,\n"
//                        + "     gefu_file_generation.`amount` AS gefu_file_generation_amount,\n"
//                        + "     gefu_file_generation.`narration` AS gefu_file_generation_narration,\n"
//                        + "     gefu_file_generation.`credit_debit` AS gefu_file_generation_credit_debit,\n"
//                        + "     gefu_file_generation.`profit_centre` AS gefu_file_generation_profit_centre,\n"
//                        + "     gefu_file_generation.`DAO` AS gefu_file_generation_DAO,\n"
//                        + "     gefu_file_generation.`c_amount` AS gefu_file_generation_c_amount,\n"
//                        + "     gefu_file_generation.`d_amount` AS gefu_file_generation_d_amount,\n"
//                        + "     gefu_file_generation.`gefu_creation_status` AS gefu_file_generation_gefu_creation_status,\n"
//                        + "     gefu_file_generation.`status` AS gefu_file_generation_status,\n"
//                        + "     gefu_file_generation.`creat_by` AS gefu_file_generation_creat_by,\n"
//                        + "     gefu_file_generation.`creat_date` AS gefu_file_generation_creat_date,\n"
//                        + "     gefu_file_generation.`mod_by` AS gefu_file_generation_mod_by,\n"
//                        + "     gefu_file_generation.`mod_date` AS gefu_file_generation_mod_date,\n"
//                        + "     gefu_file_generation.`system_date` AS gefu_file_generation_system_date,\n"
//                        + "     gefu_file_generation.`cw_fixed_frequency` AS gefu_file_generation_cw_fixed_frequency,\n"
//                        + "     gefu_file_generation.`gefu_type` AS gefu_file_generation_gefu_type,\n"
//                        + "     gefu_file_generation.`bulk_credit` AS gefu_file_generation_bulk_credit\n"
//                        + "FROM\n"
//                        + "     `gefu_file_generation` gefu_file_generation INNER JOIN `ndb_pdc_txn_master` ndb_pdc_txn_master ON gefu_file_generation.`idndb_pdc_txn_master` = ndb_pdc_txn_master.`idndb_pdc_txn_master` where ndb_pdc_txn_master.`idndb_customer_define_seller_id`='" + _rs.getString("idndb_cust_prod_map") + "' and ndb_pdc_txn_master.`pdc_req_financing`='RF' and gefu_file_generation.`gefu_type`='COMCHGBC' and gefu_file_generation.`status`='AUTHORIZED' and gefu_file_generation.`gefu_creation_status`='ACTIVE';";
//                _rs2 = _stmnt2.executeQuery(m_strQry2);
//                System.out.println(m_strQry2);
//                double total_credit_amu = 0.00;
//
//                while (_rs2.next()) {
//                    total_credit_amu = total_credit_amu + Double.parseDouble(_rs2.getString("gefu_file_generation_c_amount"));
//
//                    _account = _rs2.getString("gefu_file_generation_account");
//                    _narration = _rs2.getString("gefu_file_generation_narration");
//                    m_str_user_id = _rs2.getString("gefu_file_generation_creat_by");
//                    system_date = _rs2.getString("gefu_file_generation_system_date");
//                    currency = _rs2.getString("gefu_file_generation_currency");
//                    date = _rs2.getString("gefu_file_generation_date");
//                    bulk_credit = _rs2.getString("gefu_file_generation_bulk_credit");
//                    cw_fixed_frequency = _rs2.getString("gefu_file_generation_cw_fixed_frequency");
//
//                    idgefu_file_generation = _rs2.getString("gefu_file_generation_idgefu_file_generation");
//                    str_user_id = _rs2.getString("gefu_file_generation_creat_by");
//                    gefu_entry_check = true;
//
//                    m_strQry3 = "Update gefu_file_generation set gefu_creation_status='PROCESSEDCOMCHGBC',"
//                            + "mod_by='" + str_user_id + "',"
//                            + "mod_date=now() where idgefu_file_generation='" + idgefu_file_generation + "'";
//                    if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
//
//                        log.info("Error Occured in uphdating trasctions idgefu_file_generation : " + idgefu_file_generation);
//                        throw new Exception("Error Occured in uphdating trasctions idgefu_file_generation : " + idgefu_file_generation);
//                    }
//
//                }
//
//                if (gefu_entry_check) {
//                    System.out.println("id");
//
//                    _amount = total_credit_amu;
//                    System.out.println("CREDIT AMU>" + total_credit_amu);
//                    _credit_debit = "C";
//                    _c_amount = total_credit_amu;
//                    _d_amount = 0.00;
//
//                    // RF facility customer Collection Account Credit entry ...............................
//                    m_strQry3 = "insert into gefu_file_generation ("
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
//                            + "'999999',"
//                            + "'" + _account + "',"
//                            + "'" + currency + "',"
//                            + "'" + date + "',"
//                            + "'" + _amount + "',"
//                            + "'" + _narration + "',"
//                            + "'" + _credit_debit + "',"
//                            + "'" + profit_centre + "',"
//                            + "'" + DAO + "',"
//                            + "'" + _c_amount + "',"
//                            + "'" + _d_amount + "',"
//                            + "'ACTIVE',"
//                            + "'AUTHORIZED',"
//                            + "'" + m_str_user_id + "',"
//                            + "NOW(),"
//                            + "'" + m_str_user_id + "',"
//                            + "NOW(),"
//                            + "'" + system_date + "',"
//                            + "'COMCHGBC',"
//                            + "'" + bulk_credit + "',"
//                            + "'" + cw_fixed_frequency + "')";
//
//                    if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
//                        log.info("Error Occured in inserting bulk credit combine tranaction _account : " + _account);
//                        throw new Exception("Error Occured in insert user-roles");
//                    }
//                    log.info("Update as one tranaction in gefu file generation table/(Bulk Credit option) account Number :" + _account);
//
//                }
//
//            }
//
//            // cw commission charg combinig processs
//            gefu_entry_check = false;
//
//            m_strQry = "select * from ndb_cust_prod_map where prod_relationship_status='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_key_seller='ACTIVE' and prod_relationship_chq_ware='ACTIVE'";
//            _rs = _stmnt.executeQuery(m_strQry);
//            while (_rs.next()) {
//                gefu_entry_check = false;
//
//                m_strQry2 = "SELECT\n"
//                        + "     ndb_pdc_txn_master.`idndb_pdc_txn_master` AS ndb_pdc_txn_master_idndb_pdc_txn_master,\n"
//                        + "     ndb_pdc_txn_master.`pdc_req_financing` AS ndb_pdc_txn_master_pdc_req_financing,\n"
//                        + "     ndb_pdc_txn_master.`idndb_customer_define_seller_id` AS ndb_pdc_txn_master_idndb_customer_define_seller_id,\n"
//                        + "     ndb_pdc_txn_master.`idndb_customer_define_buyer_id` AS ndb_pdc_txn_master_idndb_customer_define_buyer_id,\n"
//                        + "     gefu_file_generation.`idgefu_file_generation` AS gefu_file_generation_idgefu_file_generation,\n"
//                        + "     gefu_file_generation.`idndb_pdc_txn_master` AS gefu_file_generation_idndb_pdc_txn_master,\n"
//                        + "     gefu_file_generation.`account` AS gefu_file_generation_account,\n"
//                        + "     gefu_file_generation.`currency` AS gefu_file_generation_currency,\n"
//                        + "     gefu_file_generation.`date` AS gefu_file_generation_date,\n"
//                        + "     gefu_file_generation.`amount` AS gefu_file_generation_amount,\n"
//                        + "     gefu_file_generation.`narration` AS gefu_file_generation_narration,\n"
//                        + "     gefu_file_generation.`credit_debit` AS gefu_file_generation_credit_debit,\n"
//                        + "     gefu_file_generation.`profit_centre` AS gefu_file_generation_profit_centre,\n"
//                        + "     gefu_file_generation.`DAO` AS gefu_file_generation_DAO,\n"
//                        + "     gefu_file_generation.`c_amount` AS gefu_file_generation_c_amount,\n"
//                        + "     gefu_file_generation.`d_amount` AS gefu_file_generation_d_amount,\n"
//                        + "     gefu_file_generation.`gefu_creation_status` AS gefu_file_generation_gefu_creation_status,\n"
//                        + "     gefu_file_generation.`status` AS gefu_file_generation_status,\n"
//                        + "     gefu_file_generation.`creat_by` AS gefu_file_generation_creat_by,\n"
//                        + "     gefu_file_generation.`creat_date` AS gefu_file_generation_creat_date,\n"
//                        + "     gefu_file_generation.`mod_by` AS gefu_file_generation_mod_by,\n"
//                        + "     gefu_file_generation.`mod_date` AS gefu_file_generation_mod_date,\n"
//                        + "     gefu_file_generation.`system_date` AS gefu_file_generation_system_date,\n"
//                        + "     gefu_file_generation.`cw_fixed_frequency` AS gefu_file_generation_cw_fixed_frequency,\n"
//                        + "     gefu_file_generation.`gefu_type` AS gefu_file_generation_gefu_type,\n"
//                        + "     gefu_file_generation.`bulk_credit` AS gefu_file_generation_bulk_credit\n"
//                        + "FROM\n"
//                        + "     `gefu_file_generation` gefu_file_generation INNER JOIN `ndb_pdc_txn_master` ndb_pdc_txn_master ON gefu_file_generation.`idndb_pdc_txn_master` = ndb_pdc_txn_master.`idndb_pdc_txn_master` where ndb_pdc_txn_master.`idndb_customer_define_seller_id`='" + _rs.getString("idndb_cust_prod_map") + "' and ndb_pdc_txn_master.`pdc_req_financing`='CW' and gefu_file_generation.`gefu_type`='COMCHGCD' and gefu_file_generation.`status`='AUTHORIZED' and gefu_file_generation.`gefu_creation_status`='ACTIVE';";
//                _rs2 = _stmnt2.executeQuery(m_strQry2);
//                System.out.println(m_strQry2);
//
//                while (_rs2.next()) {
//                    total_debit_amu = total_debit_amu + Double.parseDouble(_rs2.getString("gefu_file_generation_d_amount"));
//
//                    _account = _rs2.getString("gefu_file_generation_account");
//                    _narration = _rs2.getString("gefu_file_generation_narration");
//                    m_str_user_id = _rs2.getString("gefu_file_generation_creat_by");
//                    system_date = _rs2.getString("gefu_file_generation_system_date");
//                    currency = _rs2.getString("gefu_file_generation_currency");
//                    date = _rs2.getString("gefu_file_generation_date");
//                    bulk_credit = _rs2.getString("gefu_file_generation_bulk_credit");
//                    cw_fixed_frequency = _rs2.getString("gefu_file_generation_cw_fixed_frequency");
//
//                    idgefu_file_generation = _rs2.getString("gefu_file_generation_idgefu_file_generation");
//                    str_user_id = _rs2.getString("gefu_file_generation_creat_by");
//                    gefu_entry_check = true;
//
//                    m_strQry3 = "Update gefu_file_generation set gefu_creation_status='PROCESSEDCOMMCHG',"
//                            + "mod_by='" + str_user_id + "',"
//                            + "mod_date=now() where idgefu_file_generation='" + idgefu_file_generation + "'";
//                    if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
//
//                        log.info("Error Occured in uphdating trasctions idgefu_file_generation : " + idgefu_file_generation);
//                        throw new Exception("Error Occured in uphdating trasctions idgefu_file_generation : " + idgefu_file_generation);
//                    }
//
//                }
//
//                if (gefu_entry_check) {
//                    System.out.println("id");
//
//                    _amount = total_debit_amu;
//                    System.out.println("DEBIT CW AMU>" + total_debit_amu);
//                    _credit_debit = "D";
//                    _c_amount = 0.00;
//                    _d_amount = total_debit_amu;
//
//                    // RF facility customer Collection Account Credit entry ...............................
//                    m_strQry3 = "insert into gefu_file_generation ("
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
//                            + "'999999',"
//                            + "'" + _account + "',"
//                            + "'" + currency + "',"
//                            + "'" + date + "',"
//                            + "'" + _amount + "',"
//                            + "'" + _narration + "',"
//                            + "'" + _credit_debit + "',"
//                            + "'" + profit_centre + "',"
//                            + "'" + DAO + "',"
//                            + "'" + _c_amount + "',"
//                            + "'" + _d_amount + "',"
//                            + "'ACTIVE',"
//                            + "'AUTHORIZED',"
//                            + "'" + m_str_user_id + "',"
//                            + "NOW(),"
//                            + "'" + m_str_user_id + "',"
//                            + "NOW(),"
//                            + "'" + system_date + "',"
//                            + "'COMCHGCD',"
//                            + "'" + bulk_credit + "',"
//                            + "'" + cw_fixed_frequency + "')";
//
//                    if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
//                        log.info("Error Occured in inserting bulk credit combine tranaction _account : " + _account);
//                        throw new Exception("Error Occured in insert user-roles");
//                    }
//                    log.info("Update as one tranaction in gefu file generation table/(Bulk Credit option) account Number :" + _account);
//
//                }
//
//            }
//
//            gefu_entry_check = false;
//
//            m_strQry = "select * from ndb_cust_prod_map where prod_relationship_status='ACTIVE' and prod_relationship_auth='AUTHORIZED' and prod_relationship_key_seller='ACTIVE' and prod_relationship_chq_ware='ACTIVE'";
//            _rs = _stmnt.executeQuery(m_strQry);
//            while (_rs.next()) {
//                gefu_entry_check = false;
//
//                m_strQry2 = "SELECT\n"
//                        + "     ndb_pdc_txn_master.`idndb_pdc_txn_master` AS ndb_pdc_txn_master_idndb_pdc_txn_master,\n"
//                        + "     ndb_pdc_txn_master.`pdc_req_financing` AS ndb_pdc_txn_master_pdc_req_financing,\n"
//                        + "     ndb_pdc_txn_master.`idndb_customer_define_seller_id` AS ndb_pdc_txn_master_idndb_customer_define_seller_id,\n"
//                        + "     ndb_pdc_txn_master.`idndb_customer_define_buyer_id` AS ndb_pdc_txn_master_idndb_customer_define_buyer_id,\n"
//                        + "     gefu_file_generation.`idgefu_file_generation` AS gefu_file_generation_idgefu_file_generation,\n"
//                        + "     gefu_file_generation.`idndb_pdc_txn_master` AS gefu_file_generation_idndb_pdc_txn_master,\n"
//                        + "     gefu_file_generation.`account` AS gefu_file_generation_account,\n"
//                        + "     gefu_file_generation.`currency` AS gefu_file_generation_currency,\n"
//                        + "     gefu_file_generation.`date` AS gefu_file_generation_date,\n"
//                        + "     gefu_file_generation.`amount` AS gefu_file_generation_amount,\n"
//                        + "     gefu_file_generation.`narration` AS gefu_file_generation_narration,\n"
//                        + "     gefu_file_generation.`credit_debit` AS gefu_file_generation_credit_debit,\n"
//                        + "     gefu_file_generation.`profit_centre` AS gefu_file_generation_profit_centre,\n"
//                        + "     gefu_file_generation.`DAO` AS gefu_file_generation_DAO,\n"
//                        + "     gefu_file_generation.`c_amount` AS gefu_file_generation_c_amount,\n"
//                        + "     gefu_file_generation.`d_amount` AS gefu_file_generation_d_amount,\n"
//                        + "     gefu_file_generation.`gefu_creation_status` AS gefu_file_generation_gefu_creation_status,\n"
//                        + "     gefu_file_generation.`status` AS gefu_file_generation_status,\n"
//                        + "     gefu_file_generation.`creat_by` AS gefu_file_generation_creat_by,\n"
//                        + "     gefu_file_generation.`creat_date` AS gefu_file_generation_creat_date,\n"
//                        + "     gefu_file_generation.`mod_by` AS gefu_file_generation_mod_by,\n"
//                        + "     gefu_file_generation.`mod_date` AS gefu_file_generation_mod_date,\n"
//                        + "     gefu_file_generation.`system_date` AS gefu_file_generation_system_date,\n"
//                        + "     gefu_file_generation.`cw_fixed_frequency` AS gefu_file_generation_cw_fixed_frequency,\n"
//                        + "     gefu_file_generation.`gefu_type` AS gefu_file_generation_gefu_type,\n"
//                        + "     gefu_file_generation.`bulk_credit` AS gefu_file_generation_bulk_credit\n"
//                        + "FROM\n"
//                        + "     `gefu_file_generation` gefu_file_generation INNER JOIN `ndb_pdc_txn_master` ndb_pdc_txn_master ON gefu_file_generation.`idndb_pdc_txn_master` = ndb_pdc_txn_master.`idndb_pdc_txn_master` where ndb_pdc_txn_master.`idndb_customer_define_seller_id`='" + _rs.getString("idndb_cust_prod_map") + "' and ndb_pdc_txn_master.`pdc_req_financing`='CW' and gefu_file_generation.`gefu_type`='COMCHGBC' and gefu_file_generation.`status`='AUTHORIZED' and gefu_file_generation.`gefu_creation_status`='ACTIVE';";
//                _rs2 = _stmnt2.executeQuery(m_strQry2);
//                System.out.println(m_strQry2);
//                double total_credit_amu = 0.00;
//
//                while (_rs2.next()) {
//                    total_credit_amu = total_credit_amu + Double.parseDouble(_rs2.getString("gefu_file_generation_c_amount"));
//
//                    _account = _rs2.getString("gefu_file_generation_account");
//                    _narration = _rs2.getString("gefu_file_generation_narration");
//                    m_str_user_id = _rs2.getString("gefu_file_generation_creat_by");
//                    system_date = _rs2.getString("gefu_file_generation_system_date");
//                    currency = _rs2.getString("gefu_file_generation_currency");
//                    date = _rs2.getString("gefu_file_generation_date");
//                    bulk_credit = _rs2.getString("gefu_file_generation_bulk_credit");
//                    cw_fixed_frequency = _rs2.getString("gefu_file_generation_cw_fixed_frequency");
//
//                    idgefu_file_generation = _rs2.getString("gefu_file_generation_idgefu_file_generation");
//                    str_user_id = _rs2.getString("gefu_file_generation_creat_by");
//                    gefu_entry_check = true;
//
//                    m_strQry3 = "Update gefu_file_generation set gefu_creation_status='PROCESSEDCOMCHGBC',"
//                            + "mod_by='" + str_user_id + "',"
//                            + "mod_date=now() where idgefu_file_generation='" + idgefu_file_generation + "'";
//                    if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
//
//                        log.info("Error Occured in uphdating trasctions idgefu_file_generation : " + idgefu_file_generation);
//                        throw new Exception("Error Occured in uphdating trasctions idgefu_file_generation : " + idgefu_file_generation);
//                    }
//
//                }
//
//                if (gefu_entry_check) {
//                    System.out.println("id");
//
//                    _amount = total_credit_amu;
//                    System.out.println("CREDIT CW AMU>" + total_credit_amu);
//                    _credit_debit = "C";
//                    _c_amount = total_credit_amu;
//                    _d_amount = 0.00;
//
//                    // RF facility customer Collection Account Credit entry ...............................
//                    m_strQry3 = "insert into gefu_file_generation ("
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
//                            + "'999999',"
//                            + "'" + _account + "',"
//                            + "'" + currency + "',"
//                            + "'" + date + "',"
//                            + "'" + _amount + "',"
//                            + "'" + _narration + "',"
//                            + "'" + _credit_debit + "',"
//                            + "'" + profit_centre + "',"
//                            + "'" + DAO + "',"
//                            + "'" + _c_amount + "',"
//                            + "'" + _d_amount + "',"
//                            + "'ACTIVE',"
//                            + "'AUTHORIZED',"
//                            + "'" + m_str_user_id + "',"
//                            + "NOW(),"
//                            + "'" + m_str_user_id + "',"
//                            + "NOW(),"
//                            + "'" + system_date + "',"
//                            + "'COMCHGBC',"
//                            + "'" + bulk_credit + "',"
//                            + "'" + cw_fixed_frequency + "')";
//
//                    if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
//                        log.info("Error Occured in inserting bulk credit combine tranaction _account : " + _account);
//                        throw new Exception("Error Occured in insert user-roles");
//                    }
//                    log.info("Update as one tranaction in gefu file generation table/(Bulk Credit option) account Number :" + _account);
//
//                }
//
//            }
//
//            if (!endConnection(_currentCon)) {
//                throw new Exception("Error occured in end transaction");
//            }
//            success = true;
//        } catch (Exception e) {
//            log.info("Error occred in bulk credit updating error : " + e.getLocalizedMessage());
//
//            e.printStackTrace();
//
//        } finally {
//            try {
//                if (_rs != null) {
//                    _rs.close();
//                }
//                if (_stmnt != null) {
//                    _stmnt.close();
//                }
//                if (_stmnt2 != null) {
//                    _stmnt2.close();
//                }
//                if (_currentCon != null) {
//                    _currentCon.close();
//                }
//            } catch (Exception e) {
//            }
//        }
//        return success;
//    }
    public boolean processMarginEntries(String _gefu_gen_date, String str_user_id) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("###.00");

        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";

        boolean m_ardy_exist_data = true;
        log.info("GEFU Request, start margin entry process....");

        try {

            if (!startConnection(_currentCon)) {

                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();
            String[] sys_date_mode = _gefu_gen_date.split("/");
            String mod_day = sys_date_mode[0];
            String mod_month = sys_date_mode[1];
            String mod_year = sys_date_mode[2];
            String _mod_system_date = mod_year + mod_month + mod_day;

            double total_debit_amu = 0.00;
            double _amount = 0.00;
            String _account = "";
            String _credit_debit = "";
            String date = _mod_system_date;
            String _narration = "";
            String profit_centre = "";
            String DAO = "";
            String currency = "LKR";
            String system_date = _gefu_gen_date;
            String m_str_user_id = "";
            String bulk_credit = "";
            String cw_fixed_frequency = "";
            String idgefu_file_generation = "";
            double _c_amount = 0.00;
            double _d_amount = 0.00;

            String rec_finance_curr_ac = "";
            String rec_finance_acc_num = "";
            String rec_finance_cr_dsc_proc_acc_num = "";
            String rec_finance_margin_ac = "";
            String rec_finance_margin = "";
            String cust_name = "";
            double m_Strrf_cheque_amu = 0.00;
            boolean gefu_entry_check = false;

            m_strQry = "select \n"
                    + "\n"
                    + "  SUM(nptm.pdc_chq_amu) as pdc_chq_amu,\n"
                    + "  ncd.rec_finance_curr_ac,\n"
                    + "  ncd.rec_finance_acc_num,\n"
                    + "  ncd.rec_finance_cr_dsc_proc_acc_num,\n"
                    + "  ncd.rec_finance_margin_ac,\n"
                    + "  ncd.rec_finance_margin,\n"
                    + "  ncd.cust_name,\n"
                    + "  ncd.idndb_customer_define\n"
                    + "\n"
                    + "from ndb_cust_prod_map ncpm,\n"
                    + "    ndb_pdc_txn_master nptm,\n"
                    + "    ndb_customer_define ncd\n"
                    + "\n"
                    + "where \n"
                    + "\n"
                    + "    ncpm.prod_relationship_status='ACTIVE' \n"
                    + "and ncpm.prod_relationship_auth='AUTHORIZED' \n"
                    + "and ncpm.prod_relationship_key_seller='ACTIVE' \n"
                    + "and ncpm.prod_relationship_res_fin='ACTIVE'\n"
                    + "\n"
                    + "and nptm.idndb_customer_define_seller_id = ncpm.idndb_cust_prod_map\n"
                    + "and nptm.pdc_req_financing='RF' \n"
                    + "and nptm.pdc_chq_status='ACTIVE' \n"
                    + "and nptm.pdc_chq_status_auth='AUTHORIZED' \n"
                    + "and nptm.pdc_booking_date='" + _gefu_gen_date + "'\n"
                    + "\n"
                    + "and ncd.idndb_customer_define =ncpm.idndb_customer_define\n"
                    + "\n"
                    + "group by ncpm.idndb_cust_prod_map";
            _rs = _stmnt.executeQuery(m_strQry);
            while (_rs.next()) {
                gefu_entry_check = false;
                m_Strrf_cheque_amu = 0.00;

                if (!(_rs.getString("pdc_chq_amu") == null)) {
                    m_Strrf_cheque_amu = Double.parseDouble(_rs.getString("pdc_chq_amu"));
                }

                rec_finance_curr_ac = _rs.getString("rec_finance_curr_ac");
                rec_finance_acc_num = _rs.getString("rec_finance_acc_num");
                rec_finance_cr_dsc_proc_acc_num = _rs.getString("rec_finance_cr_dsc_proc_acc_num");
                rec_finance_margin_ac = _rs.getString("rec_finance_margin_ac");
                rec_finance_margin = _rs.getString("rec_finance_margin");
                cust_name = _rs.getString("cust_name");

                m_strQry2 = "SELECT * from gefu_file_generation where date ='" + _mod_system_date + "' and idndb_pdc_txn_master='" + _rs.getString("idndb_customer_define") + "DISMRG' and gefu_creation_status='ACTIVE' and status='AUTHORIZED' ";
                _rs2 = _stmnt2.executeQuery(m_strQry2);
                if (!_rs2.next()) {
                    if (!(m_Strrf_cheque_amu == 0.00)) {

                        if (!(((rec_finance_margin_ac.length() < 8) || rec_finance_margin_ac.equals("")) && (rec_finance_margin.equals("") || rec_finance_margin.equals("0")))) {
                            log.info("Start Of marging entry");

                            double margin_amu = m_Strrf_cheque_amu / 100 * Double.parseDouble(rec_finance_margin);

                            _amount = margin_amu;
                            _account = rec_finance_curr_ac;
                            _credit_debit = "D";
                            _narration = "DISMRG" + cust_name + df.format(m_Strrf_cheque_amu);
                            _d_amount = margin_amu;

                            log.info("ACC. ENTRY : Seller current account number debit & seller marging account credit");
                            log.info("ACC. ENTRY : Sellercurrent account number debit / debit Acc: " + _account + ". Debit Amu: " + _d_amount + "Narration : " + _narration + " Type :RFMARCDCD");

                            // RF facility customer RFF ACC debit entry ...............................
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
                                    + "'" + _rs.getString("idndb_customer_define") + "DISMRG',"
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
                                    + "'AUTHORIZED',"
                                    + "'" + str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'RFMARCDCD',"
                                    + "'NO',"
                                    + "'NOT DEFINE')";

                            log.info("ACC. ENTRY : Seller current account number debit /YSQL QUIERY :" + m_strQry3);

                            if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }

                            _amount = margin_amu;
                            _account = rec_finance_margin_ac;
                            _credit_debit = "C";
                            _narration = "DISMRG" + cust_name + df.format(m_Strrf_cheque_amu);
                            _c_amount = margin_amu;
                            _d_amount = 0.00;

                            log.info("ACC. ENTRY : Seller Marging account number credit / credit Acc: " + _account + ". credit Amu: " + _c_amount + "Narration : " + _narration + " Type :RFMARCDCD");

                            // RF facility customer Collection Account Credit entry ...............................
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
                                    + "'" + _rs.getString("idndb_customer_define") + "DISMRG',"
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
                                    + "'AUTHORIZED',"
                                    + "'" + str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + str_user_id + "',"
                                    + "NOW(),"
                                    + "'" + system_date + "',"
                                    + "'RFMARCDBC',"
                                    + "'NO',"
                                    + "'NOT DEFINE')";

                            log.info("ACC. ENTRY : Seller Marging account number credit / MY SQL QUIERY :" + m_strQry3);

                            if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                                throw new Exception("Error Occured in insert user-roles");
                            }
                            log.info("End of marging entries");
                        }
                    }
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            log.info("GEFU Request,margin entries generated....");
            success = true;
        } catch (Exception e) {
             abortConnection(_currentCon);
            log.error("Error occured in processing marging entries data, Exception" + e);

        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
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

    public boolean processRFCommisionEntries(String _gefu_gen_date, String m_str_user_id) {
        boolean success = false;
        DecimalFormat df = new DecimalFormat("###.00");
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        Parameters para = new Parameters();
        String NDBCommisionPLAcc = para.getNDBCommisionPLAcc();

        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";

        log.info("GEF Request, start generate rf commision entries..");

        try {

            if (!startConnection(_currentCon)) {

                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            double commision_total_amu = 0.00;
            double commision_buyer_wise_total_amu = 0.00;

            String rec_finance_curr_ac = "";
            String cust_name = "";
            String idndb_geo_market_master_file = "";
            String acc_officer_code = "";

            m_strQry = "SELECT  A.rec_finance_curr_ac,\n"
                    + "        A.cust_name,\n"
                    + "        A.geo_market_id,\n"
                    + "        A.idndb_customer_define,\n"
                    + "        SUM(A.commision_amt) as commision_amt\n"
                    + "FROM (\n"
                    + "\n"
                    + "    select  ncd.rec_finance_curr_ac,\n"
                    + "            ncd.cust_name, \n"
                    + "            ngmmf.geo_market_id,\n"
                    + "            ncpm.idndb_cust_prod_map,\n"
                    + "            ncpm.idndb_customer_define,\n"
                    + "            nshb.rec_finance_commision_crg,\n"
                    + "            nshb.idndb_seller_has_buyers,\n"
                    + "            nshb.rf_tran_base_falt_fee,\n"
                    + "            nshb.rf_tran_base_from_tran,\n"
                    + "            nptm.pdc_booking_date,\n"
                    + "            nptm.idndb_pdc_txn_master,\n"
                    + "            CASE\n"
                    + "            WHEN rec_finance_commision_crg = 'TRANSACTION BASED' AND nshb.rf_tran_base_falt_fee=0 THEN SUM(nptm.pdc_chq_amu)/100*rf_tran_base_from_tran\n"
                    + "            WHEN rec_finance_commision_crg = 'TRANSACTION BASED' AND nshb.rf_tran_base_falt_fee>0 THEN COUNT(nptm.idndb_pdc_txn_master)*nshb.rf_tran_base_falt_fee\n"
                    + "            WHEN rec_finance_commision_crg = 'FIXED CHARGE BASED' THEN SUM(nptm.pdc_chq_amu)/100*rf_tran_base_from_tran\n"
                    + "            ELSE NULL\n"
                    + "        END AS commision_amt\n"
                    + "\n"
                    + "\n"
                    + "    from ndb_customer_define ncd ,\n"
                    + "         ndb_geo_market_master_file ngmmf ,\n"
                    + "         ndb_cust_prod_map ncpm,\n"
                    + "         ndb_seller_has_buyers nshb,\n"
                    + "         ndb_pdc_txn_master nptm\n"
                    + "\n"
                    + "    where ncd.idndb_geo_market_master_file = ngmmf.idndb_geo_market_master_file \n"
                    + "    and ncpm.prod_relationship_status='ACTIVE' \n"
                    + "    and ncpm.prod_relationship_auth='AUTHORIZED' \n"
                    + "    and ncpm.prod_relationship_key_seller='ACTIVE' \n"
                    + "    and ncpm.prod_relationship_res_fin='ACTIVE'\n"
                    + "    and ncd.idndb_customer_define=ncpm.idndb_customer_define\n"
                    + "    and nshb.idndb_customer_define_seller = ncpm.idndb_cust_prod_map\n"
                    + "    and nshb.sl_has_byr_status='ACTIVE' \n"
                    + "    and nshb.sl_has_byr_auth='AUTHORIZED' \n"
                    + "    and nshb.sl_has_byr_prorm_type='RF'\n"
                    + "    and nptm.idndb_customer_define_buyer_id = nshb.idndb_seller_has_buyers\n"
                    + "    and nptm.pdc_req_financing='RF' \n"
                    + "    and nptm.pdc_chq_status='ACTIVE' \n"
                    + "    and nptm.pdc_chq_status_auth='AUTHORIZED' \n"
                    + "    and nptm.pdc_booking_date='" + _gefu_gen_date + "'\n"
                    + "    group by nptm.idndb_customer_define_seller_id, nptm.idndb_customer_define_buyer_id\n"
                    + ") A\n"
                    + "group by A.idndb_cust_prod_map\n"
                    + "    ";
            _rs = _stmnt.executeQuery(m_strQry);
            while (_rs.next()) {

                rec_finance_curr_ac = _rs.getString("rec_finance_curr_ac");
                cust_name = _rs.getString("cust_name").toUpperCase();
                acc_officer_code = _rs.getString("geo_market_id");
                commision_total_amu = Double.parseDouble(_rs.getString("commision_amt"));

                String[] sys_date_mode = _gefu_gen_date.split("/");
                String mod_day = sys_date_mode[0];
                String mod_month = sys_date_mode[1];
                String mod_year = sys_date_mode[2];
                String _mod_system_date = mod_year + mod_month + mod_day;

                m_strQry2 = "SELECT * from gefu_file_generation where date ='" + _mod_system_date + "' and idndb_pdc_txn_master='" + _rs.getString("idndb_customer_define") + "COMMCHRGRF' and gefu_creation_status='ACTIVE' and status='AUTHORIZED' ";
                _rs2 = _stmnt2.executeQuery(m_strQry2);
                if (!_rs2.next()) {
                    if (!(commision_total_amu == 0.00)) {

                        String max_idndb_pdc_txn_master = _mod_system_date;
                        String account = "";
                        String currency = "LKR";
                        String date = "";
                        double amount = commision_total_amu;
                        String narration = "";
                        String credit_debit = "";
                        String profit_centre = "";
                        String DAO = "";
                        double c_amount = 0.00;
                        double d_amount = 0.00;
                        String system_date = "";
                        String gefu_rf_fixed_frequency = "";
                        String gefu_rec_finance_bulk_credit = "";

                        d_amount = amount;
                        account = rec_finance_curr_ac;
                        narration = "COMM" + cust_name;
                        date = _mod_system_date;
                        system_date = _gefu_gen_date;

                        log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                        log.info("ACC. ENTRY : Seller current account number debit Acc: " + rec_finance_curr_ac + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

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
                                + "'" + _rs.getString("idndb_customer_define") + "COMMCHRGRF" + "',"
                                + "'" + rec_finance_curr_ac + "',"
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
                                + "'" + gefu_rf_fixed_frequency + "')";
                        log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry);
                        if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        // commision charges ndb bank credit entry............................................................
                        d_amount = 0.00;
                        c_amount = amount;
                        account = NDBCommisionPLAcc;
                        log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGCD");

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
                                + "'" + _rs.getString("idndb_customer_define") + "COMMCHRGRF" + "',"
                                + "'" + account + "',"
                                + "'" + currency + "',"
                                + "'" + date + "',"
                                + "'" + df.format(amount) + "',"
                                + "'" + narration + "',"
                                + "'C',"
                                + "'PL',"
                                + "'" + acc_officer_code + "',"
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
                                + "'" + gefu_rf_fixed_frequency + "')";
                        log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ MY SQL Quiery :" + m_strQry);

                        if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        log.info("End of ACC Commision entries");
                        //..................................................end ...........................................

                    }
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            log.info("GEF Request,rf commision entries generated..");

            success = true;
        } catch (Exception e) {
             abortConnection(_currentCon);
            log.error("Error occured in processing RF commision entries, Exception" + e);

        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
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

    public boolean processCWCommisionEntries(String _gefu_gen_date, String m_str_user_id) {
        boolean success = false;
        DecimalFormat df = new DecimalFormat("###.00");
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        Parameters para = new Parameters();
        String NDBCommisionPLAcc = para.getNDBCommisionPLAcc();

        String m_strQry = "";
        String m_strQry2 = "";
        String m_strQry3 = "";

        log.info("GEF Request, start generate cw commision entries..");

        try {

            if (!startConnection(_currentCon)) {

                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            _stmnt3 = _currentCon.createStatement();

            double commision_total_amu = 0.00;
            double commision_buyer_wise_total_amu = 0.00;

            String cms_curr_acc_number = "";
            String cust_name = "";
            String idndb_geo_market_master_file = "";
            String acc_officer_code = "";

            m_strQry = "SELECT  A.cms_curr_acc_number,\n"
                    + "        A.cust_name,\n"
                    + "        A.geo_market_id,\n"
                    + "        A.idndb_customer_define,\n"
                    + "        SUM(A.commision_amt) as commision_amt\n"
                    + "FROM (\n"
                    + "\n"
                    + "    select  ncd.cms_curr_acc_number,\n"
                    + "            ncd.cust_name, \n"
                    + "            ngmmf.geo_market_id,\n"
                    + "            ncpm.idndb_cust_prod_map,\n"
                    + "            ncpm.idndb_customer_define,\n"
                    + "            nshb.chq_wh_commision_crg,\n"
                    + "            nshb.idndb_seller_has_buyers,\n"
                    + "            nshb.cw_tran_base_falt_fee,\n"
                    + "            nshb.cw_tran_base_from_tran,\n"
                    + "            nptm.pdc_booking_date,\n"
                    + "            nptm.idndb_pdc_txn_master,\n"
                    + "            CASE\n"
                    + "            WHEN chq_wh_commision_crg = 'TRANSACTION BASED' AND nshb.cw_tran_base_falt_fee=0 THEN SUM(nptm.pdc_chq_amu)/100*cw_tran_base_from_tran\n"
                    + "            WHEN chq_wh_commision_crg = 'TRANSACTION BASED' AND nshb.cw_tran_base_falt_fee>0 THEN COUNT(nptm.idndb_pdc_txn_master)*nshb.cw_tran_base_falt_fee\n"
                    + "            WHEN chq_wh_commision_crg = 'FIXED CHARGE BASED' THEN SUM(nptm.pdc_chq_amu)/100*cw_tran_base_from_tran\n"
                    + "            ELSE NULL\n"
                    + "        END AS commision_amt\n"
                    + "\n"
                    + "\n"
                    + "    from ndb_customer_define ncd ,\n"
                    + "         ndb_geo_market_master_file ngmmf ,\n"
                    + "         ndb_cust_prod_map ncpm,\n"
                    + "         ndb_seller_has_buyers nshb,\n"
                    + "         ndb_pdc_txn_master nptm\n"
                    + "where ncd.idndb_geo_market_master_file = ngmmf.idndb_geo_market_master_file \n"
                    + "    and ncpm.prod_relationship_status='ACTIVE' \n"
                    + "    and ncpm.prod_relationship_auth='AUTHORIZED' \n"
                    + "    and ncpm.prod_relationship_key_seller='ACTIVE' \n"
                    + "    and ncpm.prod_relationship_chq_ware='ACTIVE'\n"
                    + "    and ncd.idndb_customer_define=ncpm.idndb_customer_define\n"
                    + "    and nshb.idndb_customer_define_seller = ncpm.idndb_cust_prod_map\n"
                    + "    and nshb.sl_has_byr_status='ACTIVE' \n"
                    + "    and nshb.sl_has_byr_auth='AUTHORIZED' \n"
                    + "    and nshb.sl_has_byr_prorm_type='CW'\n"
                    + "    and nptm.idndb_customer_define_buyer_id = nshb.idndb_seller_has_buyers\n"
                    + "    and nptm.pdc_req_financing='CW' \n"
                    + "    and nptm.pdc_chq_status='ACTIVE' \n"
                    + "    and nptm.pdc_chq_status_auth='AUTHORIZED' \n"
                    + "    and nptm.pdc_booking_date='" + _gefu_gen_date + "'\n"
                    + "    group by nptm.idndb_customer_define_seller_id, nptm.idndb_customer_define_buyer_id\n"
                    + ") A\n"
                    + "group by A.idndb_cust_prod_map";
            _rs = _stmnt.executeQuery(m_strQry);
            while (_rs.next()) {
                commision_total_amu = 0.00;
                String chq_wh_commision_crg = "NOTDEFINE";

                cms_curr_acc_number = _rs.getString("cms_curr_acc_number");
                cust_name = _rs.getString("cust_name").toUpperCase();

                acc_officer_code = _rs.getString("geo_market_id");

                commision_buyer_wise_total_amu = 0.00;

                commision_total_amu = Double.parseDouble(_rs.getString("commision_amt"));

                String[] sys_date_mode = _gefu_gen_date.split("/");
                String mod_day = sys_date_mode[0];
                String mod_month = sys_date_mode[1];
                String mod_year = sys_date_mode[2];
                String _mod_system_date = mod_year + mod_month + mod_day;

                m_strQry2 = "SELECT * from gefu_file_generation where date ='" + _mod_system_date + "' and idndb_pdc_txn_master='" + _rs.getString("idndb_customer_define") + "COMMCHRGCW' and gefu_creation_status='ACTIVE' and status='AUTHORIZED' ";
                _rs2 = _stmnt2.executeQuery(m_strQry2);
                if (!_rs2.next()) {

                    if (!(commision_total_amu == 0.00)) {

                        String max_idndb_pdc_txn_master = _mod_system_date;
                        String account = "";
                        String currency = "LKR";
                        String date = "";
                        double amount = commision_total_amu;
                        String narration = "";
                        String credit_debit = "";
                        String profit_centre = "";
                        String DAO = "";
                        double c_amount = 0.00;
                        double d_amount = 0.00;
                        String system_date = "";
                        String gefu_rf_fixed_frequency = "";
                        String gefu_rec_finance_bulk_credit = "";

                        d_amount = amount;
                        account = cms_curr_acc_number;
                        narration = "COMM" + cust_name;
                        date = _mod_system_date;
                        system_date = _gefu_gen_date;

                        log.info("ACC. ENTRY : Seller current account number debit & NDB bank commision PL Credit");
                        log.info("ACC. ENTRY : Seller current account number debit Acc: " + cms_curr_acc_number + ". Debit Amu: " + d_amount + "Narration : " + narration + " Type :COMCHGCD");

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
                                + "'" + _rs.getString("idndb_customer_define") + "COMMCHRGCW" + "',"
                                + "'" + cms_curr_acc_number + "',"
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
                                + "'" + gefu_rf_fixed_frequency + "')";
                        log.info("ACC. ENTRY : Seller current account number debit. MYSQL QUIERY" + m_strQry);
                        if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        // commision charges ndb bank credit entry............................................................
                        d_amount = 0.00;
                        c_amount = amount;
                        account = NDBCommisionPLAcc;
                        log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ credit Acc: " + account + ". credit Amu: " + c_amount + "Narration : " + narration + " Type :COMCHGCD");

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
                                + "'" + _rs.getString("idndb_customer_define") + "COMMCHRGCW" + "',"
                                + "'" + account + "',"
                                + "'" + currency + "',"
                                + "'" + date + "',"
                                + "'" + df.format(amount) + "',"
                                + "'" + narration + "',"
                                + "'C',"
                                + "'PL',"
                                + "'" + acc_officer_code + "',"
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
                                + "'" + gefu_rf_fixed_frequency + "')";
                        log.info("ACC. ENTRY :NDB BAnk Commission pl Credit/ MY SQL Quiery :" + m_strQry);

                        if (_stmnt3.executeUpdate(m_strQry3) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }
                        log.info("End of ACC Commision entries");
                        //..................................................end ...........................................

                    }
                }

            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            log.info("GEF Request,cw commision entries generated..");
            success = true;
        } catch (Exception e) {
             abortConnection(_currentCon);
            log.error("Error occured in processing cw entries data, Exception" + e);

        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
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

    public static String checkSum(String path) {
        String m_cheksumval = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            FileInputStream fis = new FileInputStream(path);
            byte[] dataBytes = new byte[1024];

            int nread = 0;

            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            };

            byte[] mdbytes = md.digest();

            //convert the byte to hex format
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            m_cheksumval = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return m_cheksumval;
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
