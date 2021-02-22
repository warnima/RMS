/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBOops;

import DBAutoFillBeans.comboDAO;
import entities.BranchData;
import entities.Cust_Buyer;
import entities.NDB_Bank_Master_File;
import entities.NDB_Chq_Wh;
import entities.NDB_Customer_Define;
import entities.NDB_Prod_Txn_Master;
import entities.NDB_Rec_Fin;
import entities.NDB_Seller_has_Buyers;
import entities.Ndb_Cust_Prod_Map;
import entities.SellerHasBuyerData;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ndb.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;
import utility.Parameters;

/**
 *
 * @author Madhawa_4799
 */
public class fileUploadDAO {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(fileUploadDAO.class.getName());
    static ConnectionPool _connectionPool;
    static Connection _currentCon = null;
    private Statement _stmnt = null;
    private Statement _stmnt2 = null;
    private PreparedStatement _prepStmnt = null;
    private PreparedStatement _prepStmnt2 = null;
    private PreparedStatement _prepStmnt3 = null;
    private PreparedStatement _prepStmnt4 = null;
    private ResultSet _rs = null;
    private Exception _exception;

    public String uploadAdditionalDayFileData(JSONObject prm_obj) {
        String success = "false-error=";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        Parameters para = new Parameters();

        String m_strQry = "";
        String m_user_id = "";
        String m_AdditionalFileID = "";
        String AdditionalFilePath = "";
        int idgefu_upload = 0;
        ResultSet m_rs = null;

        try {
            m_user_id = prm_obj.getString("user_id");
            m_AdditionalFileID = prm_obj.getString("AdditionalFileID");
            AdditionalFilePath = prm_obj.getString("AdditionalFilePath");
            _stmnt = _currentCon.createStatement();

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            String _system_date = "";

            m_strQry = "select * from ndb_system_date";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                _system_date = _rs.getString("_system_date");

            }
            m_strQry = "SELECT * FROM ndb_bank_master_file where bank_approval='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry = "SELECT * FROM ndb_branch_master_file where branch_approval='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry = "SELECT * FROM ndb_customer_define where cust_auth='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry = "SELECT * FROM ndb_cust_prod_map where prod_relationship_auth='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry = "SELECT * FROM ndb_seller_has_buyers where sl_has_byr_auth='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status_auth='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            FileInputStream fstream = new FileInputStream(AdditionalFilePath);

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String m_recived_line = "";
            if ((strLine = br.readLine()) == null) {
                m_strQry = "insert into ndb_day_today_process (day_today_process_name,"
                        + "processed_date,"
                        + "processed_by"
                        + ",processed_time"
                        + ""
                        + ") values("
                        + "'ADDDAYFILEUPLD',"
                        + "'" + _system_date + "',"
                        + "'" + m_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }
            } else {
                in.close();

                FileInputStream fstream2 = new FileInputStream(AdditionalFilePath);

                DataInputStream in2 = new DataInputStream(fstream2);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
                fileUploadDAO upldao = new fileUploadDAO();

                while ((strLine = br2.readLine()) != null) {
//                    m_recived_line = strLine.trim();
//                    StringBuilder builder = new StringBuilder();
////                    for (int i = 0; i < m_recived_line.length(); i++) {
////                        char c = m_recived_line.charAt(i);
////                        if (Character.isDigit(c)) {
////                            builder.append(c);
////                        }
////                    }
                    String numbers = strLine;

                    if ((numbers.length() > 112) || (numbers.length() < 112)) {
                        success = "0012";
                        throw new Exception("INVALID ADDITIONALDAY FILE FORMAT");
                    }

                    String m_clearingDate = numbers.substring(0, 8);

                    if (!upldao.isThisDateValid(m_clearingDate, "yyyyMMdd")) {
                        success = "0012";
                        throw new Exception("INVALID ADDITIONALDAY FILE FORMAT");

                    }

                    String m_bank_code = numbers.substring(31, 35);
                    String m_idndb_bank_master_file = "";

                    String m_bank_branch_code = numbers.substring(38, 41);
                    String m_idndb_branch_master_file = "";

                    String m_next_additional_date = numbers.substring(104, 112);
                    String m_additional_day_reason = numbers.substring(74, 104);

                    if (!upldao.isThisDateValid(m_next_additional_date, "yyyyMMdd")) {
                        success = "0012";
                        throw new Exception("INVALID ADDITIONALDAY FILE FORMAT");

                    }

                    m_strQry = "select * from ndb_bank_master_file where bank_code='" + m_bank_code + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        m_idndb_bank_master_file = _rs.getString("idndb_bank_master_file");

                    }

                    m_strQry = "select * from ndb_branch_master_file where branch_id='" + m_bank_branch_code + "' and idndb_bank_master_file='" + m_idndb_bank_master_file + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        m_idndb_branch_master_file = _rs.getString("idndb_branch_master_file");

                    }

                    m_strQry = "insert into ndb_additional_day_file (addit_clearing_date,addit_bank_code,idndb_bank_master_file,addit_branch_code,idndb_branch_master_file,addit_reason,addit_next_clearing_date,addit_file_id,addit_status,creat_by,creat_date_time,mod_by,mod_date_time) values("
                            + "'" + m_clearingDate + "',"
                            + "'" + m_bank_code + "',"
                            + "'" + m_idndb_bank_master_file + "',"
                            + "'" + m_bank_branch_code + "',"
                            + "'" + m_idndb_branch_master_file + "',"
                            + "'" + m_additional_day_reason + "',"
                            + "'" + m_next_additional_date + "',"
                            + "'" + m_AdditionalFileID + "',"
                            + "'ACTIVE',"
                            + "'" + m_user_id + "',"
                            + "now(),"
                            + "'" + m_user_id + "',"
                            + "now())";

                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                    m_strQry = "insert into ndb_day_today_process (day_today_process_name,"
                            + "processed_date,"
                            + "processed_by"
                            + ",processed_time"
                            + ""
                            + ") values("
                            + "'ADDDAYFILEUPLD',"
                            + "'" + _system_date + "',"
                            + "'" + m_user_id + "',"
                            + "now())";
                    if (_stmnt.executeUpdate(m_strQry) <= 0) {
                        throw new Exception("Error Occured in insert user-roles");
                    }

                }

                in2.close();
            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end of upod");
            }
            success = "success";
        } catch (Exception e) {
            success = success;
            abortConnection(_currentCon);
            log.error("Error occured in additionalday file upload process, Exception" + e);
        } finally {
            try {
                if (m_rs != null) {
                    m_rs.close();
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

    public String uploadPDCBulkFileData1(JSONObject prm_obj) {
        DecimalFormat df = new DecimalFormat("###.00");
        String success = "false-error=";
        String _system_date = "";
        DBoperationsDBO.pdcDAO pdc = new DBoperationsDBO.pdcDAO();
        _system_date = pdc.getSystemDate();
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        Parameters para = new Parameters();
        JSONObject m_jsObj = new JSONObject();
        String m_strQry = "";
        String m_user_id = "";
        String m_PDCBulkUploadFileID = "";
        String PDCBulkUploadFilePath = "";
        String m_PDCBulkUploadFileCWData = "";
        String m_PDCBulkUploadFileRFData = "";
        String m_idndb_customer_define_seller_id = "";
        String m_pdc_req_financing = "";

        String NDBCommisionPLAcc = "";
        int idgefu_upload = 0;
        ResultSet m_rs = null;

        try {
            m_user_id = prm_obj.getString("user_id");
            m_PDCBulkUploadFileID = prm_obj.getString("PDCBulkUploadFileID");
            PDCBulkUploadFilePath = prm_obj.getString("PDCBulkUploadFilePath");
            m_PDCBulkUploadFileCWData = prm_obj.getString("PDCBulkUploadFileCWData");
            m_PDCBulkUploadFileRFData = prm_obj.getString("PDCBulkUploadFileRFData");
            m_idndb_customer_define_seller_id = prm_obj.getString("idndb_customer_define_seller_id");

            if (m_PDCBulkUploadFileCWData.equals("ACTIVE")) {
                m_pdc_req_financing = "CW";
            }
            if (m_PDCBulkUploadFileRFData.equals("ACTIVE")) {
                m_pdc_req_financing = "RF";
            }

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            m_strQry = "select * from ndb_holiday_marker where ndb_holiday_approval='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "error/Transactions file cannot be upload. Un-authorized holiday marked data is available in the system. !";
                throw new Exception("Error Occured in saving pdc entry");
            }

            DBOops.CsvReader products = new DBOops.CsvReader(PDCBulkUploadFilePath);
            products.readHeaders();
            int y = 0;
            int m_line = 0;
            while (products.readRecord()) {
                y++;
                m_line = y;

                String BUYERID = products.get("BUYERID");
                String CHEQUENUMBER = products.get("CHEQUENUMBER");
                String BANKCODE = products.get("BANKCODE");
                String BRANCHCODE = products.get("BRANCHCODE");
                String VALUEDATE = products.get("VALUEDATE");
                String CHEQUEAMOUNT = products.get("CHEQUEAMOUNT");
                String m_Strpdc_bank_name = "";
                String m_Strpdc_branch_name = "";

                fileUploadDAO upldao = new fileUploadDAO();
                if (!upldao.isThisDateValid(VALUEDATE, "yyyyMMdd")) {

                    success = "error/Invalid Date Format Found at line " + m_line + " !";
                    throw new Exception("Error Occured in uploading Transaction File");

                }

                if (CHEQUEAMOUNT.equals("")) {
                    success = "error/Invalid Amount Found at line " + m_line + " !";
                    throw new Exception("Error Occured in uploading GEFU");
                }

                if (!upldao.isNumeric(CHEQUEAMOUNT, "D")) {
                    success = "error/Invalid Amount Found at line " + m_line + " !";
                    throw new Exception("Error Occured in uploading GEFU");
                }

                if (!upldao.isNumeric(CHEQUENUMBER, "I")) {
                    success = "error/Invalid Cheque Number Found at line " + m_line + " !";
                    throw new Exception("Error Occured in uploading GEFU");
                }

                if (!(CHEQUENUMBER.length() == 6)) {
                    success = "error/Invalid Cheque Number Found at line " + m_line + " ! . Cheque number must be 6 digitis only.";
                    throw new Exception("Error Occured in uploading GEFU");
                }

                if (Double.parseDouble(CHEQUEAMOUNT) < 0) {
                    success = "error/Invalid Amount Found at line " + m_line + " !";
                    throw new Exception("Error Occured in uploading GEFU");
                }

                if (!(CHEQUEAMOUNT.contains("."))) {
                    success = "error/Invalid Amount Found at line " + m_line + " !";
                    throw new Exception("Error Occured in uploading GEFU");
                }

                String[] splitter = CHEQUEAMOUNT.toString().split("\\.");
                splitter[0].length();   // Before Decimal Count
                int decimalLength = splitter[1].length();  // After Decimal Count

                if (!(decimalLength == 2)) {
                    success = "error/Invalid Decimal Places Found at line" + m_line + " !";
                    throw new Exception("Error Occured in uploading GEFU");
                }

                String m_valuedate_year = VALUEDATE.substring(0, 4);
                String m_valuedate_month = VALUEDATE.substring(4, 6);
                String m_valuedate_date = VALUEDATE.substring(6, 8);

                String TEMP_CHQ_VALUE_DATE = m_valuedate_date + "/" + m_valuedate_month + "/" + m_valuedate_year;

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date1 = sdf.parse(TEMP_CHQ_VALUE_DATE);
                Date date2 = sdf.parse(_system_date);
                if (date1.compareTo(date2) == 0) {
                    success = "error/Invalid value date, Found at line " + m_line + ", Value date cannot be today !";
                    throw new Exception("Error Occured in uploading GEFU");
                }
                if (!(date1.compareTo(date2) > 0)) {
                    success = "error/Invalid value date, Found at line " + m_line + ", Value date cannot be backdate !";
                    throw new Exception("Error Occured in uploading GEFU");
                }

                DBoperationsDBO.pdcDAO pdcDAO = new DBoperationsDBO.pdcDAO();
                String ACTUAL_CHQUE_VALUEDATE = pdcDAO.getchequeValueDate(TEMP_CHQ_VALUE_DATE);

                double FORCHEQUEAMOUNT = Double.parseDouble(new DecimalFormat("####00.00").format(Double.parseDouble(CHEQUEAMOUNT)));
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                String[] date_spliter = ACTUAL_CHQUE_VALUEDATE.split("/");
                int m_year = Integer.parseInt(date_spliter[2]);
                int m_month = Integer.parseInt(date_spliter[1]) - 1;
                int m_day = Integer.parseInt(date_spliter[0]);
                Calendar ced = Calendar.getInstance();

                ced.set(Calendar.YEAR, m_year); // set the year
                ced.set(Calendar.MONTH, m_month); // set the month
                ced.set(Calendar.DAY_OF_MONTH, m_day);
                ced.add(Calendar.DATE, 1);
                String ACTUAL_CHEQUE_LEQUID_DATE = pdcDAO.getchequeValueDate(formatter.format(ced.getTime()));
                String idndb_bank_master_file = "";
                String idndb_branch_master_file = "";
                m_strQry = "select * from ndb_bank_master_file where bank_code='" + BANKCODE + "' and bank_status='ACTIVE' and bank_approval='AUTHORIZED'";
                m_rs = _stmnt.executeQuery(m_strQry);
                if (m_rs.next()) {
                    idndb_bank_master_file = m_rs.getString("idndb_bank_master_file");
                    m_Strpdc_bank_name = m_rs.getString("bank_name");
                } else {
                    success = "error/Invalid Bank Code found at line " + m_line + " !";
                    throw new Exception("Error Occured in pdc bulk upload");

                }

                BRANCHCODE = String.format("%03d", Integer.parseInt(BRANCHCODE));

                m_strQry = "select * from ndb_branch_master_file where branch_id='" + BRANCHCODE + "' and branch_status='ACTIVE' and branch_approval='AUTHORIZED' and idndb_bank_master_file='" + idndb_bank_master_file + "'";
                m_rs = _stmnt.executeQuery(m_strQry);
                if (m_rs.next()) {
                    idndb_branch_master_file = m_rs.getString("idndb_branch_master_file");
                    m_Strpdc_branch_name = m_rs.getString("branch_name");
                } else {
                    success = "error/Invalid Branch Code found at line " + m_line + " !";
                    throw new Exception("Error Occured in pdc bulk upload");

                }

                //String m_uniq_id = CHEQUENUMBER + BANKCODE + BRANCHCODE;
                String m_uniq_id = m_idndb_customer_define_seller_id + BUYERID + CHEQUENUMBER + BANKCODE + BRANCHCODE;
                m_strQry = "SELECT CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code) as txn_master_unq_id FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_chq_status_auth in ('AUTHORIZED','UN-AUTHORIZED')";
                _rs = _stmnt.executeQuery(m_strQry);
                boolean m_ardy_exist_dcheque = true;
                while (_rs.next()) {
                    if (m_uniq_id.equals(_rs.getString("txn_master_unq_id"))) {
                        m_ardy_exist_dcheque = false;

                    }

                }
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
                        + "     INNER JOIN `ndb_customer_define` ndb_customer_define ON ndb_cust_prod_map.`idndb_customer_define` = ndb_customer_define.`idndb_customer_define` where  ndb_seller_has_buyers.`idndb_seller_has_buyers`='" + BUYERID + "'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    buyer_name = _rs.getString("ndb_customer_define_cust_name");
                }

                if (TEMP_CHQ_VALUE_DATE.equals(_system_date)) {
                    success = "error/Cheque valu date cant be today " + m_line + " !";
                    throw new Exception("Error Occured in pdc bulk upload");
                }

                NDBCommisionPLAcc = para.getNDBCommisionPLAcc();

                m_strQry = "select * from ndb_system_date";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {

                    _system_date = _rs.getString("_system_date");

                }

                if (m_ardy_exist_dcheque) {

                    if (m_pdc_req_financing.equals("CW")) {
                        // extract cw commision data

                        String chq_wh_commision_crg = "DAILY";
                        String idndb_customer_define_seller = "";
                        String idndb_customer_define_seller_idndb_customer_define = "";
                        String cms_collection_acc_number = "";
                        String cust_name = "";

                        String sl_has_byr_warehs_tenor = "";
                        double sl_has_byr_warehs_fmax_chq_amu = 0.00;
                        double sl_has_byr_warehs_limit = 0.00;

                        double chq_wh_erly_wdr_chg = 0.00;
                        double chq_wh_vale_dte_extr_chg = 0.00;
                        double chq_wh_erly_stlemnt_chg = 0.00;
                        double cw_tran_base_falt_fee = 0.00;
                        double cw_tran_base_from_tran = 0.00;
                        double cw_fixed_rate_amount = 0.00;
                        String cw_fixed_frequency = "DAILY";

                        m_strQry = "SELECT\n"
                                + "     ndb_seller_has_buyers.`idndb_seller_has_buyers` AS ndb_seller_has_buyers_idndb_seller_has_buyers,\n"
                                + "     ndb_seller_has_buyers.`idndb_customer_define_seller` AS ndb_seller_has_buyers_idndb_customer_define_seller,\n"
                                + "     ndb_seller_has_buyers.`idndb_customer_define_buyer` AS ndb_seller_has_buyers_idndb_customer_define_buyer,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_prorm_type` AS ndb_seller_has_buyers_sl_has_byr_prorm_type,\n"
                                + "     ndb_seller_has_buyers.`shb_facty_det_crd_loam_limit` AS ndb_seller_has_buyers_shb_facty_det_crd_loam_limit,\n"
                                + "     ndb_seller_has_buyers.`shb_facty_det_os` AS ndb_seller_has_buyers_shb_facty_det_os,\n"
                                + "     ndb_seller_has_buyers.`shb_facty_det_tenor` AS ndb_seller_has_buyers_shb_facty_det_tenor,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_max_chq_amu` AS ndb_seller_has_buyers_sl_has_byr_max_chq_amu,\n"
                                + "     ndb_seller_has_buyers.`shb_facty_det_irest_trry` AS ndb_seller_has_buyers_shb_facty_det_irest_trry,\n"
                                + "     ndb_seller_has_buyers.`rec_finance_commision_crg` AS ndb_seller_has_buyers_rec_finance_commision_crg,\n"
                                + "     ndb_seller_has_buyers.`rf_tran_base_falt_fee` AS ndb_seller_has_buyers_rf_tran_base_falt_fee,\n"
                                + "     ndb_seller_has_buyers.`rf_tran_base_from_tran` AS ndb_seller_has_buyers_rf_tran_base_from_tran,\n"
                                + "     ndb_seller_has_buyers.`rf_fixed_rate_amount` AS ndb_seller_has_buyers_rf_fixed_rate_amount,\n"
                                + "     ndb_seller_has_buyers.`rf_fixed_frequency` AS ndb_seller_has_buyers_rf_fixed_frequency,\n"
                                + "     ndb_seller_has_buyers.`shb_chq_dis_adv_rate_prectange` AS ndb_seller_has_buyers_shb_chq_dis_adv_rate_prectange,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_remarks` AS ndb_seller_has_buyers_sl_has_byr_remarks,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_status` AS ndb_seller_has_buyers_sl_has_byr_status,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_auth` AS ndb_seller_has_buyers_sl_has_byr_auth,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_creat_by` AS ndb_seller_has_buyers_sl_has_byr_creat_by,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_creat_date` AS ndb_seller_has_buyers_sl_has_byr_creat_date,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_mod_by` AS ndb_seller_has_buyers_sl_has_byr_mod_by,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_mod_date` AS ndb_seller_has_buyers_sl_has_byr_mod_date,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_warehs_limit` AS ndb_seller_has_buyers_sl_has_byr_warehs_limit,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_warehs_otstaning` AS ndb_seller_has_buyers_sl_has_byr_warehs_otstaning,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_warehs_tenor` AS ndb_seller_has_buyers_sl_has_byr_warehs_tenor,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_warehs_fmax_chq_amu` AS ndb_seller_has_buyers_sl_has_byr_warehs_fmax_chq_amu,\n"
                                + "     ndb_seller_has_buyers.`chq_wh_commision_crg` AS ndb_seller_has_buyers_chq_wh_commision_crg,\n"
                                + "     ndb_seller_has_buyers.`cw_tran_base_falt_fee` AS ndb_seller_has_buyers_cw_tran_base_falt_fee,\n"
                                + "     ndb_seller_has_buyers.`cw_tran_base_from_tran` AS ndb_seller_has_buyers_cw_tran_base_from_tran,\n"
                                + "     ndb_seller_has_buyers.`cw_fixed_rate_amount` AS ndb_seller_has_buyers_cw_fixed_rate_amount,\n"
                                + "     ndb_seller_has_buyers.`cw_fixed_frequency` AS ndb_seller_has_buyers_cw_fixed_frequency,\n"
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
                                + "     `ndb_cust_prod_map` ndb_cust_prod_map INNER JOIN `ndb_seller_has_buyers` ndb_seller_has_buyers ON ndb_cust_prod_map.`idndb_cust_prod_map` = ndb_seller_has_buyers.`idndb_customer_define_buyer`\n"
                                + "     INNER JOIN `ndb_customer_define` ndb_customer_define ON ndb_cust_prod_map.`idndb_customer_define` = ndb_customer_define.`idndb_customer_define` where ndb_seller_has_buyers.`sl_has_byr_status`='ACTIVE' and ndb_seller_has_buyers.`sl_has_byr_auth`='AUTHORIZED' and ndb_seller_has_buyers.`sl_has_byr_prorm_type`='CW' and ndb_cust_prod_map.`prod_relationship_status`='ACTIVE' and ndb_cust_prod_map.`prod_relationship_auth`='AUTHORIZED' and ndb_customer_define.`cust_status`='ACTIVE' and ndb_customer_define.`cust_auth`='AUTHORIZED' and ndb_seller_has_buyers.`idndb_seller_has_buyers`='" + BUYERID + "'";
                        //m_strQry = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + BUYERID + "' and sl_has_byr_status='ACTIVE' and sl_has_byr_auth ='AUTHORIZED'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (_rs.next()) {
                            chq_wh_commision_crg = _rs.getString("ndb_seller_has_buyers_chq_wh_commision_crg");
                            idndb_customer_define_seller = _rs.getString("ndb_seller_has_buyers_idndb_customer_define_seller");
                            sl_has_byr_warehs_tenor = _rs.getString("ndb_seller_has_buyers_sl_has_byr_warehs_tenor");
                            sl_has_byr_warehs_fmax_chq_amu = _rs.getDouble("ndb_seller_has_buyers_sl_has_byr_warehs_fmax_chq_amu");
                            sl_has_byr_warehs_limit = _rs.getDouble("ndb_seller_has_buyers_sl_has_byr_warehs_limit");

                            cw_tran_base_falt_fee = _rs.getDouble("ndb_seller_has_buyers_cw_tran_base_falt_fee");
                            cw_tran_base_from_tran = _rs.getDouble("ndb_seller_has_buyers_cw_tran_base_from_tran");

                            cw_fixed_rate_amount = _rs.getDouble("ndb_seller_has_buyers_cw_fixed_rate_amount");
                            cw_fixed_frequency = _rs.getString("ndb_seller_has_buyers_cw_fixed_frequency");

                        } else {
                            success = "error/Invalid buyer ID found at " + m_line + " !";
                            throw new Exception("Error Occured in pdc bulk upload");

                        }

                        if (!m_idndb_customer_define_seller_id.equals(idndb_customer_define_seller)) {
                            success = "error/Invalid buyer for sellected seller. error found at line " + m_line + " !";
                            throw new Exception("Error Occured in pdc bulk upload");
                        }
                        String message = "";

                        Calendar cw_tenor_date = Calendar.getInstance();
                        String[] split_system_date = _system_date.split("/");

                        int _m_year = Integer.parseInt(split_system_date[2]);
                        int _m_month = Integer.parseInt(split_system_date[1]) - 1;
                        int _m_day = Integer.parseInt(split_system_date[0]);

                        cw_tenor_date.set(Calendar.YEAR, _m_year); // set the year
                        cw_tenor_date.set(Calendar.MONTH, _m_month); // set the month
                        cw_tenor_date.set(Calendar.DAY_OF_MONTH, _m_day);
                        cw_tenor_date.add(Calendar.DATE, Integer.parseInt(sl_has_byr_warehs_tenor));

                        String tenor_date = formatter.format(cw_tenor_date.getTime());
                        String value_date = ACTUAL_CHQUE_VALUEDATE;

                        Date formatted_tenor_date = formatter.parse(tenor_date);
                        Date formatted_value_date = formatter.parse(value_date);
                        int i = 1;

                        message = i + ") NEW CW TXN ENTRY </br>";

                        if (!TEMP_CHQ_VALUE_DATE.equals(ACTUAL_CHQUE_VALUEDATE)) {
                            i++;
                            message = message + i + " ) " + TEMP_CHQ_VALUE_DATE + " date is a holiday and value date updated to next workind daya on " + ACTUAL_CHQUE_VALUEDATE + "</br>";
                        }

                        if (formatted_value_date.compareTo(formatted_tenor_date) > 0) {
                            i++;
                            message = message + i + ") Tenor exceeded </br>";
                        }

                        m_strQry = "select SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + BUYERID + "' and idndb_customer_define_seller_id='" + m_idndb_customer_define_seller_id + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED')";
                        _rs = _stmnt.executeQuery(m_strQry);
                        double pdc_chq_amu = 0.00;
                        double available_amu = 0.00;

                        if (!(_rs == null)) {
                            if (_rs.next()) {

                                pdc_chq_amu = _rs.getDouble("pdc_chq_amu");
                                available_amu = sl_has_byr_warehs_limit - (pdc_chq_amu + FORCHEQUEAMOUNT);

                            }
                        } else {
                            pdc_chq_amu = 0.00;
                            available_amu = sl_has_byr_warehs_limit - pdc_chq_amu;

                        }

                        if (FORCHEQUEAMOUNT > sl_has_byr_warehs_fmax_chq_amu) {
                            i++;
                            message = i + ") Buyer per cheque amount exceeded. Buyer definded per cheque amu. " + sl_has_byr_warehs_fmax_chq_amu + "</br>";
                        }

                        if (FORCHEQUEAMOUNT > available_amu) {
                            i++;
                            message = i + ") Buyer cheque warehousing available balance exceeded. Buyer available balance . " + available_amu + " </br";

                        }

                        m_strQry = "select * from ndb_chq_wh where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        double cw_seller_limit = 0.00;
                        double cw_seller_all_txn_amu = 0.00;
                        double cw_seller_limit_exeeded_total = 0.00;
                        String seller_message = "";
                        if (!(_rs == null)) {
                            if (_rs.next()) {
                                cw_seller_limit = _rs.getDouble("chq_wh_limit");
                            }
                        }
                        m_strQry = "select SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED') and pdc_req_financing='CW'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (!(_rs == null)) {
                            if (_rs.next()) {
                                cw_seller_all_txn_amu = _rs.getDouble("pdc_chq_amu");
                            }
                        }
                        if (cw_seller_limit < (cw_seller_all_txn_amu + FORCHEQUEAMOUNT)) {
                            cw_seller_limit_exeeded_total = (cw_seller_all_txn_amu + FORCHEQUEAMOUNT) - cw_seller_limit;
                            seller_message = "Seller cheque warehousing limit has exceeded.Exceeded Amount : " + df.format(cw_seller_limit_exeeded_total) + ".";

                        }
                        message = message + seller_message;

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
                            cms_collection_acc_number = _rs.getString("cms_collection_acc_number");
                            cust_name = _rs.getString("cust_name");
                        }

                        // end of extract cw commision data
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
                                + ",pdc_additional_day"
                                + ",pdc_old_value_date"
                                + ",pdc_txn_mod"
                                + ",pdc_chq_creat_by"
                                + ",pdc_chq_creat_date"
                                + ",pdc_chq_mod_by"
                                + ",pdc_chq_mod_date"
                                + ""
                                + ") values("
                                + "'CW',"
                                + "'" + m_idndb_customer_define_seller_id + "',"
                                + "'" + BUYERID + "',"
                                + "'" + idndb_bank_master_file + "',"
                                + "'" + BANKCODE + "',"
                                + "'" + idndb_branch_master_file + "',"
                                + "'" + BRANCHCODE + "',"
                                + "'" + CHEQUENUMBER + "',"
                                + "'" + _system_date + "',"
                                + "'" + ACTUAL_CHQUE_VALUEDATE + "',"
                                + "'" + ACTUAL_CHEQUE_LEQUID_DATE + "',"
                                + "'" + df.format(FORCHEQUEAMOUNT) + "',"
                                + "'0.00',"
                                + "'0.00',"
                                + "'" + df.format(FORCHEQUEAMOUNT) + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_PDCBulkUploadFileID + "',"
                                + "'" + cms_collection_acc_number + "',"
                                + "'" + cust_name + "',"
                                + "'" + m_Strpdc_bank_name + "',"
                                + "'" + m_Strpdc_branch_name + "',"
                                + "'DEACTIVE',"
                                + "'DEACTIVE',"
                                + "'" + TEMP_CHQ_VALUE_DATE + "',"
                                + "'NEWTXN',"
                                + "'" + m_user_id + "',"
                                + "now(),"
                                + "'" + m_user_id + "',"
                                + "now())";

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            success = "error/Error Occured in pdc bulk upload at line " + m_line + " !";
                            throw new Exception("Error Occured in pdc bulk upload");

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
                                + "'" + message + "',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_user_id + "',"
                                + "now())";
                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                        log.info("Maximum idndb_pdc_txn_master recevied  :" + max_idndb_pdc_txn_master);

                    }
                    if (m_pdc_req_financing.equals("RF")) {

                        // start of extract receivable finance commision data.....................................
                        String rec_finance_commision_crg = "NOTDEFINE";
                        String idndb_customer_define_seller = "";
                        String idndb_customer_define_seller_idndb_customer_define = "";
                        String rec_finance_curr_ac = "";
                        String rec_finance_acc_num = "";
                        String rec_finance_cr_dsc_proc_acc_num = "";
                        String rec_finance_margin_ac = "";
                        String rec_finance_margin = "";
                        String rec_finance_bulk_credit = "";
                        double rec_finance_erly_wdr_chg = 0.00;
                        double rec_finance_vale_dte_extr_chg = 0.00;
                        double rec_finance_erly_stlemnt_chg = 0.00;
                        double rf_tran_base_falt_fee = 0.00;
                        double rf_tran_base_from_tran = 0.00;
                        double rf_fixed_rate_amount = 0.00;
                        String rf_fixed_frequency = "DAILY";

                        double sl_has_byr_max_chq_amu = 0.00;
                        double shb_facty_det_crd_loam_limit = 0.00;
                        String shb_facty_det_tenor = "";

                        String cust_name = "";
                        m_strQry = "SELECT\n"
                                + "     ndb_seller_has_buyers.`idndb_seller_has_buyers` AS ndb_seller_has_buyers_idndb_seller_has_buyers,\n"
                                + "     ndb_seller_has_buyers.`idndb_customer_define_seller` AS ndb_seller_has_buyers_idndb_customer_define_seller,\n"
                                + "     ndb_seller_has_buyers.`idndb_customer_define_buyer` AS ndb_seller_has_buyers_idndb_customer_define_buyer,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_prorm_type` AS ndb_seller_has_buyers_sl_has_byr_prorm_type,\n"
                                + "     ndb_seller_has_buyers.`shb_facty_det_crd_loam_limit` AS ndb_seller_has_buyers_shb_facty_det_crd_loam_limit,\n"
                                + "     ndb_seller_has_buyers.`shb_facty_det_os` AS ndb_seller_has_buyers_shb_facty_det_os,\n"
                                + "     ndb_seller_has_buyers.`shb_facty_det_tenor` AS ndb_seller_has_buyers_shb_facty_det_tenor,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_max_chq_amu` AS ndb_seller_has_buyers_sl_has_byr_max_chq_amu,\n"
                                + "     ndb_seller_has_buyers.`shb_facty_det_irest_trry` AS ndb_seller_has_buyers_shb_facty_det_irest_trry,\n"
                                + "     ndb_seller_has_buyers.`rec_finance_commision_crg` AS ndb_seller_has_buyers_rec_finance_commision_crg,\n"
                                + "     ndb_seller_has_buyers.`rf_tran_base_falt_fee` AS ndb_seller_has_buyers_rf_tran_base_falt_fee,\n"
                                + "     ndb_seller_has_buyers.`rf_tran_base_from_tran` AS ndb_seller_has_buyers_rf_tran_base_from_tran,\n"
                                + "     ndb_seller_has_buyers.`rf_fixed_rate_amount` AS ndb_seller_has_buyers_rf_fixed_rate_amount,\n"
                                + "     ndb_seller_has_buyers.`rf_fixed_frequency` AS ndb_seller_has_buyers_rf_fixed_frequency,\n"
                                + "     ndb_seller_has_buyers.`shb_chq_dis_adv_rate_prectange` AS ndb_seller_has_buyers_shb_chq_dis_adv_rate_prectange,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_remarks` AS ndb_seller_has_buyers_sl_has_byr_remarks,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_status` AS ndb_seller_has_buyers_sl_has_byr_status,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_auth` AS ndb_seller_has_buyers_sl_has_byr_auth,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_creat_by` AS ndb_seller_has_buyers_sl_has_byr_creat_by,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_creat_date` AS ndb_seller_has_buyers_sl_has_byr_creat_date,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_mod_by` AS ndb_seller_has_buyers_sl_has_byr_mod_by,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_mod_date` AS ndb_seller_has_buyers_sl_has_byr_mod_date,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_warehs_limit` AS ndb_seller_has_buyers_sl_has_byr_warehs_limit,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_warehs_otstaning` AS ndb_seller_has_buyers_sl_has_byr_warehs_otstaning,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_warehs_tenor` AS ndb_seller_has_buyers_sl_has_byr_warehs_tenor,\n"
                                + "     ndb_seller_has_buyers.`sl_has_byr_warehs_fmax_chq_amu` AS ndb_seller_has_buyers_sl_has_byr_warehs_fmax_chq_amu,\n"
                                + "     ndb_seller_has_buyers.`chq_wh_commision_crg` AS ndb_seller_has_buyers_chq_wh_commision_crg,\n"
                                + "     ndb_seller_has_buyers.`cw_tran_base_falt_fee` AS ndb_seller_has_buyers_cw_tran_base_falt_fee,\n"
                                + "     ndb_seller_has_buyers.`cw_tran_base_from_tran` AS ndb_seller_has_buyers_cw_tran_base_from_tran,\n"
                                + "     ndb_seller_has_buyers.`cw_fixed_rate_amount` AS ndb_seller_has_buyers_cw_fixed_rate_amount,\n"
                                + "     ndb_seller_has_buyers.`cw_fixed_frequency` AS ndb_seller_has_buyers_cw_fixed_frequency,\n"
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
                                + "     `ndb_cust_prod_map` ndb_cust_prod_map INNER JOIN `ndb_seller_has_buyers` ndb_seller_has_buyers ON ndb_cust_prod_map.`idndb_cust_prod_map` = ndb_seller_has_buyers.`idndb_customer_define_buyer`\n"
                                + "     INNER JOIN `ndb_customer_define` ndb_customer_define ON ndb_cust_prod_map.`idndb_customer_define` = ndb_customer_define.`idndb_customer_define` where ndb_seller_has_buyers.`sl_has_byr_status`='ACTIVE' and ndb_seller_has_buyers.`sl_has_byr_auth`='AUTHORIZED' and ndb_seller_has_buyers.`sl_has_byr_prorm_type`='RF' and ndb_cust_prod_map.`prod_relationship_status`='ACTIVE' and ndb_cust_prod_map.`prod_relationship_auth`='AUTHORIZED' and ndb_customer_define.`cust_status`='ACTIVE' and ndb_customer_define.`cust_auth`='AUTHORIZED' and ndb_seller_has_buyers.`idndb_seller_has_buyers`='" + BUYERID + "'";

                        //m_strQry = "select * from ndb_seller_has_buyers where idndb_seller_has_buyers='" + BUYERID + "' and sl_has_byr_status='ACTIVE' and sl_has_byr_auth ='AUTHORIZED'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (_rs.next()) {
                            rec_finance_commision_crg = _rs.getString("ndb_seller_has_buyers_rec_finance_commision_crg");
                            idndb_customer_define_seller = _rs.getString("ndb_seller_has_buyers_idndb_customer_define_seller");
                            shb_facty_det_tenor = _rs.getString("ndb_seller_has_buyers_shb_facty_det_tenor");

                            sl_has_byr_max_chq_amu = _rs.getDouble("ndb_seller_has_buyers_sl_has_byr_max_chq_amu");
                            shb_facty_det_crd_loam_limit = _rs.getDouble("ndb_seller_has_buyers_shb_facty_det_crd_loam_limit");

                            rf_tran_base_falt_fee = _rs.getDouble("ndb_seller_has_buyers_rf_tran_base_falt_fee");
                            rf_tran_base_from_tran = _rs.getDouble("ndb_seller_has_buyers_rf_tran_base_from_tran");

                            rf_fixed_rate_amount = _rs.getDouble("ndb_seller_has_buyers_rf_fixed_rate_amount");
                            rf_fixed_frequency = _rs.getString("ndb_seller_has_buyers_rf_fixed_frequency");

                        } else {
                            success = "error/Invalid buyer for sellected seller. error found at line " + m_line + " !";
                            throw new Exception("Error Occured in pdc bulk upload");

                        }

                        if (!m_idndb_customer_define_seller_id.equals(idndb_customer_define_seller)) {
                            success = "error/Invalid buyer for sellected seller. error found at line " + m_line + " !";
                            throw new Exception("Error Occured in pdc bulk upload");
                        }

                        String message = "";

                        Calendar rf_tenor_date = Calendar.getInstance();
                        String[] split_system_date = _system_date.split("/");

                        int _m_year = Integer.parseInt(split_system_date[2]);
                        int _m_month = Integer.parseInt(split_system_date[1]) - 1;
                        int _m_day = Integer.parseInt(split_system_date[0]);

                        rf_tenor_date.set(Calendar.YEAR, _m_year); // set the year
                        rf_tenor_date.set(Calendar.MONTH, _m_month); // set the month
                        rf_tenor_date.set(Calendar.DAY_OF_MONTH, _m_day);
                        rf_tenor_date.add(Calendar.DATE, Integer.parseInt(shb_facty_det_tenor));

                        String tenor_date = formatter.format(rf_tenor_date.getTime());
                        String value_date = ACTUAL_CHQUE_VALUEDATE;

                        Date formatted_tenor_date = formatter.parse(tenor_date);
                        Date formatted_value_date = formatter.parse(value_date);

                        m_strQry = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (_rs.next()) {
                            idndb_customer_define_seller_idndb_customer_define = _rs.getString("idndb_customer_define");
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

                            cust_name = _rs.getString("cust_name");
                            rec_finance_curr_ac = _rs.getString("rec_finance_curr_ac");
                            rec_finance_acc_num = _rs.getString("rec_finance_acc_num");
                            rec_finance_cr_dsc_proc_acc_num = _rs.getString("rec_finance_cr_dsc_proc_acc_num");
                            rec_finance_margin_ac = _rs.getString("rec_finance_margin_ac");
                            rec_finance_margin = _rs.getString("rec_finance_margin");
                        }

                        // End of extract receivable finance commision data....................................................
                        double m_discounting_advance_rate = 0.00;
                        double m_discounting_amu_from_cheque_amu = 0.00;
                        double m_alternat_amu = 0.00;

                        m_strQry = "select shb_chq_dis_adv_rate_prectange from ndb_seller_has_buyers where idndb_seller_has_buyers='" + BUYERID + "'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (_rs.next()) {
                            m_discounting_advance_rate = Double.parseDouble(_rs.getString("shb_chq_dis_adv_rate_prectange"));

                            m_discounting_amu_from_cheque_amu = FORCHEQUEAMOUNT / 100 * m_discounting_advance_rate;
                            m_alternat_amu = FORCHEQUEAMOUNT - m_discounting_amu_from_cheque_amu;

                        }

                        int i = 1;

                        message = i + ") NEW RF TXN ENTRY </br>";

                        if (!TEMP_CHQ_VALUE_DATE.equals(ACTUAL_CHQUE_VALUEDATE)) {
                            i++;
                            message = message + i + " ) " + TEMP_CHQ_VALUE_DATE + " date is a holiday and value date updated to next workind daya on " + ACTUAL_CHQUE_VALUEDATE + "</br>";
                        }

                        if (formatted_value_date.compareTo(formatted_tenor_date) > 0) {
                            i++;
                            message = message + i + ") Tenor exceeded </br>";
                        }

                        m_strQry = "select SUM(pdc_chq_discounting_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_buyer_id='" + BUYERID + "' and idndb_customer_define_seller_id='" + m_idndb_customer_define_seller_id + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED')";
                        _rs = _stmnt.executeQuery(m_strQry);
                        double pdc_chq_amu = 0.00;
                        double available_amu = 0.00;

                        if (!(_rs == null)) {
                            if (_rs.next()) {

                                pdc_chq_amu = _rs.getDouble("pdc_chq_amu");
                                available_amu = shb_facty_det_crd_loam_limit - (pdc_chq_amu + m_discounting_amu_from_cheque_amu);

                            }
                        } else {
                            pdc_chq_amu = 0.00;
                            available_amu = shb_facty_det_crd_loam_limit - pdc_chq_amu;

                        }

                        if (FORCHEQUEAMOUNT > sl_has_byr_max_chq_amu) {
                            i++;
                            message = message + i + ") Buyer per cheque amount exceeded. Buyer Definded per cheque amu. " + sl_has_byr_max_chq_amu + "</br>";
                        }

                        if (m_discounting_amu_from_cheque_amu > available_amu) {
                            i++;
                            message = message + i + ") Buyer available balance exceeded. Buyer available balance . " + available_amu + " </br";

                        }

                        m_strQry = "select * from ndb_rec_fin where idndb_cust_prod_map='" + idndb_customer_define_seller + "'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        double rf_seller_limit = 0.00;
                        double rf_seller_all_txn_amu = 0.00;
                        double rf_seller_limit_exeeded_total = 0.00;
                        String seller_message = "";
                        if (!(_rs == null)) {
                            if (_rs.next()) {
                                rf_seller_limit = _rs.getDouble("rec_finance_limit");
                            }
                        }
                        m_strQry = "select SUM(pdc_chq_amu) as pdc_chq_amu from ndb_pdc_txn_master where idndb_customer_define_seller_id='" + idndb_customer_define_seller + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED') and pdc_req_financing='RF'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (!(_rs == null)) {
                            if (_rs.next()) {
                                rf_seller_all_txn_amu = _rs.getDouble("pdc_chq_amu");
                            }
                        }

                        if (rf_seller_limit < (rf_seller_all_txn_amu + m_discounting_amu_from_cheque_amu)) {
                            rf_seller_limit_exeeded_total = rf_seller_limit - (rf_seller_all_txn_amu + m_discounting_amu_from_cheque_amu);

                            seller_message = "Seller receviable finance loan limit has exceeded.Exceeded Amount : " + df.format(rf_seller_limit_exeeded_total) + "";
                        }

                        message = message + seller_message;

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
                                + ",pdc_additional_day"
                                + ",pdc_old_value_date"
                                + ",pdc_txn_mod"
                                + ",pdc_chq_creat_by"
                                + ",pdc_chq_creat_date"
                                + ",pdc_chq_mod_by"
                                + ",pdc_chq_mod_date"
                                + ""
                                + ") values("
                                + "'RF',"
                                + "'" + m_idndb_customer_define_seller_id + "',"
                                + "'" + BUYERID + "',"
                                + "'" + idndb_bank_master_file + "',"
                                + "'" + BANKCODE + "',"
                                + "'" + idndb_branch_master_file + "',"
                                + "'" + BRANCHCODE + "',"
                                + "'" + CHEQUENUMBER + "',"
                                + "'" + _system_date + "',"
                                + "'" + ACTUAL_CHQUE_VALUEDATE + "',"
                                + "'" + ACTUAL_CHEQUE_LEQUID_DATE + "',"
                                + "'" + df.format(FORCHEQUEAMOUNT) + "',"
                                + "'" + df.format(m_discounting_amu_from_cheque_amu) + "',"
                                + "'" + df.format(m_alternat_amu) + "',"
                                + "'" + df.format(m_alternat_amu) + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_PDCBulkUploadFileID + "',"
                                + "'" + rec_finance_acc_num + "',"
                                + "'" + cust_name + "',"
                                + "'" + m_Strpdc_bank_name + "',"
                                + "'" + m_Strpdc_branch_name + "',"
                                + "'DEACTIVE',"
                                + "'DEACTIVE',"
                                + "'" + TEMP_CHQ_VALUE_DATE + "',"
                                + "'NEWTXN',"
                                + "'" + m_user_id + "',"
                                + "now(),"
                                + "'" + m_user_id + "',"
                                + "now())";

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            success = "error/Error Occured in pdc bulk upload at line " + m_line + " !";
                            throw new Exception("Error Occured in pdc bulk upload");
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
                                + "'" + message + "',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_user_id + "',"
                                + "now())";
                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                        //...................................................gefu entries...................................
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

                        String[] gefu_date = _system_date.split("/");
                        String gefu_day = gefu_date[0];
                        String gefu_month = gefu_date[1];
                        String gefu_year = gefu_date[2];
                        date = gefu_year + gefu_month + gefu_day;

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
                        String gefu_rec_finance_bulk_credit = rec_finance_bulk_credit;

                        _amount = m_discounting_amu_from_cheque_amu;
                        _account = rec_finance_acc_num;
                        _credit_debit = "D";
                        _narration = "DISB" + CHEQUENUMBER + "RS" + CHEQUEAMOUNT + buyer_name;
                        _d_amount = m_discounting_amu_from_cheque_amu;

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
                                + "'" + df.format(_amount) + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + df.format(_c_amount) + "',"
                                + "'" + df.format(_d_amount) + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_user_id + "',"
                                + "NOW(),"
                                + "'" + m_user_id + "',"
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
                        _narration = "DISB" + CHEQUENUMBER + "RS" + FORCHEQUEAMOUNT + buyer_name;
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
                                + "'" + df.format(_amount) + "',"
                                + "'" + _narration + "',"
                                + "'" + _credit_debit + "',"
                                + "'" + profit_centre + "',"
                                + "'" + DAO + "',"
                                + "'" + df.format(_c_amount) + "',"
                                + "'" + df.format(_d_amount) + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_user_id + "',"
                                + "NOW(),"
                                + "'" + m_user_id + "',"
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

                    }
                } else {
                    success = "error/Cheque Already exist line number " + m_line + " !";
                    throw new Exception("Error Occured in pdc bulk upload");

                }

            }
            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end of upod");
            }

            success = "success/success";
        } catch (Exception e) {
            //success = "error|" + e.getLocalizedMessage() + " !";
            e.printStackTrace();
            _exception = e;
            abortConnection(_currentCon);
        } finally {
            try {
                if (m_rs != null) {
                    m_rs.close();
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

    public String uploadPDCBulkFileData(JSONObject prm_obj) {

        DecimalFormat df = new DecimalFormat("###.00");
        DecimalFormat df2 = new DecimalFormat("##,###.00");
        String success = "false-error=";
        String _system_date = "";
        pdcDAO pdc = new pdcDAO();
        _system_date = pdc.getSystemDate();

        Parameters para = new Parameters();
        String m_strQry = "";
        String m_user_id = "";
        String m_pdc_bulk_file_id = "";
        String m_pdc_bulk_file_upload_path = "";
        String m_cw_pdc_bulk_upload = "";
        String m_rf_pdc_bulk_upload = "";
        String m_idndb_cust_prod_map_seller = "";
        String m_pdc_req_financing = "";

        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        try {

            _stmnt = _currentCon.createStatement();
            String idndb_pdc_txn_master = "";

            m_user_id = prm_obj.getString("user_id");
            m_pdc_bulk_file_id = prm_obj.getString("PDCBulkUploadFileID");
            m_pdc_bulk_file_upload_path = prm_obj.getString("PDCBulkUploadFilePath");
            m_cw_pdc_bulk_upload = prm_obj.getString("PDCBulkUploadFileCWData");
            m_rf_pdc_bulk_upload = prm_obj.getString("PDCBulkUploadFileRFData");
            m_idndb_cust_prod_map_seller = prm_obj.getString("idndb_customer_define_seller_id");

            if (m_cw_pdc_bulk_upload.equals("ACTIVE")) {
                m_pdc_req_financing = "CW";
            }
            if (m_rf_pdc_bulk_upload.equals("ACTIVE")) {
                m_pdc_req_financing = "RF";
            }

            if (!startConnection(_currentCon)) {
                success = "error/Error occured in starting DB connection. !";
                throw new Exception("error/Error occured in starting DB connection. !");

            }
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            m_strQry = "select * from ndb_holiday_marker where ndb_holiday_approval='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "error/Transactions file cannot be upload. Un-authorized holiday marked data is available in the system. !";
                throw new Exception("Error Occured in saving pdc entry");
            }

            m_strQry = "select * from ndb_holiday_marker where ndb_holiday_approval='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "error/Transactions file cannot be upload. Un-authorized holiday marked data is available in the system. !";
                throw new Exception("error/Transactions file cannot be upload. Un-authorized holiday marked data is available in the system. !");
            }

            m_strQry = "select bank_code, idndb_bank_master_file, bank_name from ndb_bank_master_file where bank_status='ACTIVE' and bank_approval='AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            List<NDB_Bank_Master_File> NDB_Bank_Master_File_List = new ArrayList<NDB_Bank_Master_File>();
            while (_rs.next()) {
                NDB_Bank_Master_File file = new NDB_Bank_Master_File();
                file.setBank_code(_rs.getString("bank_code"));
                file.setBank_name(_rs.getString("bank_name"));
                file.setIdndb_bank_master_file(_rs.getString("idndb_bank_master_file"));
                NDB_Bank_Master_File_List.add(file);
            }

            _stmnt.clearBatch();

            m_strQry = "select branch_id, idndb_bank_master_file, idndb_branch_master_file, branch_name "
                    + " from ndb_branch_master_file     "
                    + " where branch_status='ACTIVE' "
                    + " and branch_approval='AUTHORIZED' ";
            _rs = _stmnt.executeQuery(m_strQry);
            List<BranchData> branchList = new ArrayList<BranchData>();
            while (_rs.next()) {
                BranchData branch = new BranchData();
                branch.setBranch_id(_rs.getString("branch_id"));
                branch.setIdndb_bank_master_file(_rs.getString("idndb_bank_master_file"));
                branch.setIdndb_branch_master_file(_rs.getString("idndb_branch_master_file"));
                branch.setBranch_name(_rs.getString("branch_name"));
                branchList.add(branch);
            }
            _stmnt.clearBatch();

            m_strQry = "    SELECT a.cust_name as ndb_customer_define_cust_name,   "
                    + "                 b.idndb_seller_has_buyers       "
                    + "     FROM 	ndb_seller_has_buyers b, ndb_customer_define a, ndb_cust_prod_map c "
                    + "     where c.idndb_cust_prod_map=b.idndb_customer_define_buyer       "
                    + "     and a.idndb_customer_define=b.idndb_seller_has_buyers and b.idndb_customer_define_seller='" + m_idndb_cust_prod_map_seller + "'";

            _rs = _stmnt.executeQuery(m_strQry);
            List<Cust_Buyer> buyer_name_List = new ArrayList<Cust_Buyer>();
            if (_rs.next()) {
                Cust_Buyer cb = new Cust_Buyer();
                cb.setIdndb_seller_has_buyers(_rs.getString("idndb_seller_has_buyers"));
                cb.setNdb_customer_define_cust_name(_rs.getString("ndb_customer_define_cust_name"));
                buyer_name_List.add(cb);
            }
            _stmnt.clearBatch();

            m_strQry = "    SELECT b.sl_has_byr_prorm_type, b.idndb_seller_has_buyers,   "
                    + "     b.chq_wh_commision_crg AS ndb_seller_has_buyers_chq_wh_commision_crg,   "
                    + "     b.idndb_customer_define_seller AS ndb_seller_has_buyers_idndb_customer_define_seller,   "
                    + "     b.idndb_customer_define_buyer AS ndb_seller_has_buyers_idndb_customer_define_buyer,   "
                    + "     b.sl_has_byr_warehs_tenor AS ndb_seller_has_buyers_sl_has_byr_warehs_tenor,     "
                    + "     b.sl_has_byr_warehs_fmax_chq_amu AS ndb_seller_has_buyers_sl_has_byr_warehs_fmax_chq_amu,   "
                    + "     b.sl_has_byr_warehs_limit AS ndb_seller_has_buyers_sl_has_byr_warehs_limit,     "
                    + "     b.cw_tran_base_falt_fee AS ndb_seller_has_buyers_cw_tran_base_falt_fee,     "
                    + "     b.cw_tran_base_from_tran AS ndb_seller_has_buyers_cw_tran_base_from_tran,       "
                    + "     b.cw_fixed_rate_amount AS ndb_seller_has_buyers_cw_fixed_rate_amount,       "
                    + "     b.cw_fixed_frequency AS ndb_seller_has_buyers_cw_fixed_frequency,       "
                    + "     b.rec_finance_commision_crg AS ndb_seller_has_buyers_rec_finance_commision_crg,                     "
                    + "     b.shb_facty_det_tenor AS ndb_seller_has_buyers_shb_facty_det_tenor,    "
                    + "     b.shb_facty_det_crd_loam_limit AS Nb_seller_has_buyers_shb_facty_det_crd_loam_limit,    "
                    + "     b.sl_has_byr_max_chq_amu AS ndb_seller_has_buyers_sl_has_byr_max_chq_amu,    "
                    + "     b.rf_tran_base_falt_fee AS ndb_seller_has_buyers_rf_tran_base_falt_fee,      "
                    + "     b.rf_tran_base_from_tran AS ndb_seller_has_buyers_rf_tran_base_from_tran,        "
                    + "     b.rf_fixed_rate_amount AS ndb_seller_has_buyers_rf_fixed_rate_amount,    "
                    + "     b.rf_fixed_frequency AS ndb_seller_has_buyers_rf_fixed_frequency        "
                    + "     FROM    ndb_cust_prod_map a, ndb_seller_has_buyers b, ndb_customer_define c, ndb_cust_prod_map d    "
                    + "     WHERE   a.idndb_cust_prod_map=b.idndb_customer_define_buyer "
                    + "     and     d.idndb_customer_define=c.idndb_customer_define     "
                    + "     and     d.idndb_cust_prod_map=b.idndb_customer_define_buyer    "
                    + "     and     b.sl_has_byr_status='ACTIVE'    "
                    + "     and     b.sl_has_byr_auth='AUTHORIZED'  "
                    + "     and     d.prod_relationship_status='ACTIVE'     "
                    + "     and     d.prod_relationship_auth='AUTHORIZED'   "
                    + "     and     c.cust_status='ACTIVE'      "
                    + "     and     c.cust_auth='AUTHORIZED' and b.idndb_customer_define_seller='" + m_idndb_cust_prod_map_seller + "'";

            _rs = _stmnt.executeQuery(m_strQry);
            List<SellerHasBuyerData> sales_buyer_data_List = new ArrayList<SellerHasBuyerData>();
            while (_rs.next()) {
                SellerHasBuyerData data = new SellerHasBuyerData();
                data.setSl_has_byr_prorm_type(_rs.getString("sl_has_byr_prorm_type"));
                data.setIdndb_seller_has_buyers(_rs.getString("idndb_seller_has_buyers"));
                data.setNdb_seller_has_buyers_chq_wh_commision_crg(_rs.getString("ndb_seller_has_buyers_chq_wh_commision_crg"));
                data.setNdb_seller_has_buyers_idndb_customer_define_seller(_rs.getString("ndb_seller_has_buyers_idndb_customer_define_seller"));
                data.setNdb_seller_has_buyers_idndb_customer_define_buyer(_rs.getString("ndb_seller_has_buyers_idndb_customer_define_buyer"));
                data.setNdb_seller_has_buyers_sl_has_byr_warehs_tenor(_rs.getString("ndb_seller_has_buyers_sl_has_byr_warehs_tenor"));
                data.setNdb_seller_has_buyers_sl_has_byr_warehs_limit(_rs.getString("ndb_seller_has_buyers_sl_has_byr_warehs_limit"));
                data.setNdb_seller_has_buyers_cw_tran_base_falt_fee(_rs.getString("ndb_seller_has_buyers_cw_tran_base_falt_fee"));
                data.setNdb_seller_has_buyers_cw_tran_base_from_tran(_rs.getString("ndb_seller_has_buyers_cw_tran_base_from_tran"));
                data.setNdb_seller_has_buyers_sl_has_byr_warehs_fmax_chq_amu(_rs.getString("ndb_seller_has_buyers_sl_has_byr_warehs_fmax_chq_amu"));
                data.setNdb_seller_has_buyers_cw_fixed_rate_amount(_rs.getString("ndb_seller_has_buyers_cw_fixed_rate_amount"));
                data.setNdb_seller_has_buyers_cw_fixed_frequency(_rs.getString("ndb_seller_has_buyers_cw_fixed_frequency"));
                data.setNdb_seller_has_buyers_rec_finance_commision_crg(_rs.getString("ndb_seller_has_buyers_rec_finance_commision_crg"));
                data.setNdb_seller_has_buyers_shb_facty_det_tenor(_rs.getString("ndb_seller_has_buyers_shb_facty_det_tenor"));
                data.setNdb_seller_has_buyers_shb_facty_det_crd_loam_limit(_rs.getString("Nb_seller_has_buyers_shb_facty_det_crd_loam_limit"));

                data.setNdb_seller_has_buyers_sl_has_byr_max_chq_amu(_rs.getString("ndb_seller_has_buyers_sl_has_byr_max_chq_amu"));
                data.setNdb_seller_has_buyers_rf_tran_base_falt_fee(_rs.getString("ndb_seller_has_buyers_rf_tran_base_falt_fee"));
                data.setNdb_seller_has_buyers_rf_tran_base_from_tran(_rs.getString("ndb_seller_has_buyers_rf_tran_base_from_tran"));
                data.setNdb_seller_has_buyers_rf_fixed_rate_amount(_rs.getString("ndb_seller_has_buyers_rf_fixed_rate_amount"));
                data.setNdb_seller_has_buyers_rf_fixed_frequency(_rs.getString("ndb_seller_has_buyers_rf_fixed_frequency"));
                sales_buyer_data_List.add(data);
            }
            _stmnt.clearBatch();

            m_strQry = "    select idndb_customer_define_buyer_id, idndb_customer_define_seller_id, pdc_req_financing, SUM(pdc_chq_amu) as pdc_chq_amu, SUM(pdc_chq_discounting_amu) as pdc_chq_discounting_amu     "
                    + "     from ndb_pdc_txn_master     "
                    + "     where pdc_chq_status='ACTIVE' and idndb_customer_define_seller_id='" + m_idndb_cust_prod_map_seller + "' and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED')  "
                    + "     group by idndb_customer_define_buyer_id, idndb_customer_define_seller_id, pdc_req_financing    ";

            List<NDB_Prod_Txn_Master> prod_txn_sum_List = new ArrayList<NDB_Prod_Txn_Master>();
            _rs = _stmnt.executeQuery(m_strQry);
            while (_rs.next()) {
                NDB_Prod_Txn_Master pr = new NDB_Prod_Txn_Master();
                pr.setIdndb_customer_define_buyer_id(_rs.getString("idndb_customer_define_buyer_id"));
                pr.setIdndb_customer_define_seller_id(_rs.getString("idndb_customer_define_seller_id"));
                pr.setPdc_chq_amu(_rs.getDouble("pdc_chq_amu"));
                pr.setPdc_req_financing(_rs.getString("pdc_req_financing"));
                pr.setPdc_chq_discounting_amu(_rs.getDouble("pdc_chq_discounting_amu"));
                prod_txn_sum_List.add(pr);
            }
            _stmnt.clearBatch();

            m_strQry = "SELECT \n"
                    + "ncpm_cust_prod_map.idndb_cust_prod_map,\n"
                    + "ncd_cust_define.idndb_customer_define, \n"
                    + "ncd_cust_define.cust_id,\n"
                    + "ncd_cust_define.cust_name,\n"
                    + "ncd_cust_define.cms_collection_acc_number, \n"
                    + "ncd_cust_define.rec_finance_curr_ac,\n"
                    + "ncd_cust_define.rec_finance_acc_num, \n"
                    + "ncd_cust_define.rec_finance_cr_dsc_proc_acc_num, \n"
                    + "ncd_cust_define.rec_finance_margin_ac, \n"
                    + "ncd_cust_define.rec_finance_margin,\n"
                    + "nrf_rec_fin_data.rec_finance_bulk_credit, \n"
                    + "nrf_rec_fin_data.rec_finance_erly_wdr_chg, \n"
                    + "nrf_rec_fin_data.rec_finance_vale_dte_extr_chg, \n"
                    + "nrf_rec_fin_data.rec_finance_erly_stlemnt_chg, \n"
                    + "nrf_rec_fin_data.rec_finance_limit,\n"
                    + "ncw_chq_wh_data.chq_wh_dr_to_cr_spe_narr, \n"
                    + "ncw_chq_wh_data.chq_wh_limit,\n"
                    + "ncw_chq_wh_data.chq_wh_erly_wdr_chg, \n"
                    + "ncw_chq_wh_data.chq_wh_vale_dte_extr_chg, \n"
                    + "ncw_chq_wh_data.chq_wh_erly_stlemnt_chg,\n"
                    + "bacd_buyer_acc_details.idbuyer_accs_details \n"
                    + "FROM \n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map inner join\n"
                    + "ndb_customer_define ncd_cust_define on\n"
                    + "ncpm_cust_prod_map.idndb_customer_define = ncd_cust_define.idndb_customer_define left join\n"
                    + "ndb_rec_fin nrf_rec_fin_data on\n"
                    + "ncpm_cust_prod_map.idndb_cust_prod_map = nrf_rec_fin_data.idndb_cust_prod_map left join\n"
                    + "ndb_chq_wh ncw_chq_wh_data on\n"
                    + "ncpm_cust_prod_map.idndb_cust_prod_map = ncw_chq_wh_data.idndb_cust_prod_map left join\n"
                    + "buyer_accs_details bacd_buyer_acc_details on\n"
                    + "ncpm_cust_prod_map.idndb_cust_prod_map = bacd_buyer_acc_details.idndb_cust_prod_map \n"
                    + "where\n"
                    + "ncpm_cust_prod_map.idndb_cust_prod_map='" + m_idndb_cust_prod_map_seller + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            String m_seller_cust_name = "";
            String m_seller_cms_collection_acc_number = "";
            String m_seller_rec_finance_acc_num = "";
            String m_seller_rec_finance_cr_dsc_proc_acc_num = "";
            String m_seller_rec_finance_bulk_credit = "";
            String m_seller_rec_finance_limit = "";
            String m_seller_chq_wh_dr_to_cr_spe_narr = "";
            String m_seller_chq_wh_limit = "";

            if (_rs.next()) {

                m_seller_cust_name = _rs.getString("cust_name");
                m_seller_cms_collection_acc_number = _rs.getString("cms_collection_acc_number");
                m_seller_rec_finance_acc_num = _rs.getString("rec_finance_acc_num");
                m_seller_rec_finance_cr_dsc_proc_acc_num = _rs.getString("rec_finance_cr_dsc_proc_acc_num");
                m_seller_rec_finance_bulk_credit = _rs.getString("rec_finance_bulk_credit");
                m_seller_rec_finance_limit = _rs.getString("rec_finance_limit");
                m_seller_chq_wh_dr_to_cr_spe_narr = _rs.getString("chq_wh_dr_to_cr_spe_narr");
                m_seller_chq_wh_limit = _rs.getString("chq_wh_limit");

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

///////////////////////////////////////////Inserts///////////////////////////////////////////////////////////////
            String insert_Qry = "insert into ndb_pdc_txn_master ("
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
                    + ",pdc_reference_1"
                    + ",pdc_reference_2"
                    + ",pdc_reference_3"
                    + ",pdc_reference_4"
                    + ",pdc_reference_5"
                    + ",pdc_chq_creat_by"
                    + ",pdc_chq_creat_date"
                    + ",pdc_chq_mod_by"
                    + ",pdc_chq_mod_date"
                    + ""
                    + ") values("
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "'ACTIVE',"
                    + "'UN-AUTHORIZED',"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "'DEACTIVE',"
                    + "'DEACTIVE',"
                    + "?,"
                    + "'NEWTXN',"
                    + "'NEW',"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "now(),"
                    + "?,"
                    + "now())";

            _prepStmnt = _currentCon.prepareStatement(insert_Qry, Statement.RETURN_GENERATED_KEYS);

            insert_Qry = "insert into gefu_file_generation ("
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
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "'ACTIVE',"
                    + "'UN-AUTHORIZED',"
                    + "?,"
                    + "NOW(),"
                    + "?,"
                    + "NOW(),"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?)";

            _prepStmnt2 = _currentCon.prepareStatement(insert_Qry);

            insert_Qry = "insert into ndb_pdc_uploaded_customized_data ("
                    + "idndb_pdc_txn_master"
                    + ",pdc_reference_1"
                    + ",pdc_reference_2"
                    + ",pdc_reference_3"
                    + ",pdc_reference_4"
                    + ",pdc_reference_5"
                    + ",npucd_creat_user"
                    + ",npucd_creat_date"
                    + ",npucd_modified_user"
                    + ",npucd_modified_date"
                    + ") values("
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "now(),"
                    + "?,"
                    + "now())";
            _prepStmnt3 = _currentCon.prepareStatement(insert_Qry);

            insert_Qry = "insert into ndb_change_log (ndb_change_log_type,"
                    + "ndb_attached_id,"
                    + "ndb_change"
                    + ",status"
                    + ",creat_by"
                    + ",creat_date"
                    + ""
                    + ") values("
                    + "'PDCTXN',"
                    + "?,"
                    + "?,"
                    + "'UN-AUTHORIZED',"
                    + "'" + m_user_id + "',"
                    + "now())";

            _prepStmnt4 = _currentCon.prepareStatement(insert_Qry);

            CsvReader products = new CsvReader(m_pdc_bulk_file_upload_path);
            products.readHeaders();
            int y = 0;
            int m_line = 0;

            while (products.readRecord()) {

                y++;
                m_line = y;

                String BUYERID = products.get("BUYERID");
                String CHEQUENUMBER = products.get("CHEQUENUMBER");
                String BANKCODE = products.get("BANKCODE");
                String BRANCHCODE = products.get("BRANCHCODE");
                String VALUEDATE = products.get("VALUEDATE");
                String CHEQUEAMOUNT = products.get("CHEQUEAMOUNT");
                String NARRATION = "";

                String m_Strpdc_bank_name = "";
                String m_Strpdc_branch_name = "";

                fileUploadDAO upldao = new fileUploadDAO();
                if (!upldao.isThisDateValid(VALUEDATE, "yyyyMMdd")) {

                    success = "error/Invalid cheque value date found at line no " + m_line + ". Value date format should be \"YYYYMMDD\" !";
                    throw new Exception("error/Invalid valude date found at line no " + m_line + ". Value date format should be \"YYYYMMDD\" !");

                }

                if (CHEQUEAMOUNT.equals("")) {
                    success = "error/Invalid cheque amount found at line no " + m_line + ". Cheque amount cannot be empty. !";
                    throw new Exception("error/Invalid cheque amount found at line no " + m_line + ". Cheque amount cannot be empty. !");
                }

                if (!upldao.isNumeric(CHEQUEAMOUNT, "D")) {
                    success = "error/Invalid cheque amount found at line no " + m_line + ". Cheuque amount format should be \"000.00\" !";
                    throw new Exception("error/Invalid cheque amount found at line no " + m_line + ". Cheuque amount format should be \"000.00\" !");
                }

                if (!upldao.isNumeric(CHEQUENUMBER, "I")) {
                    success = "error/Invalid cheque number found at line no " + m_line + ". Cheque amount shuld be numaric value. !";
                    throw new Exception("error/Invalid cheque number found at line no " + m_line + ". Cheque amount shuld be numaric value. !");
                }

                if (!(CHEQUENUMBER.length() == 6)) {
                    success = "error/Invalid cheque number found at line " + m_line + " ! . Cheque number should be 6 digitis only.";
                    throw new Exception("error/Invalid cheque number found at line " + m_line + " ! . Cheque number should be 6 digitis only.");
                }

                if (Double.parseDouble(CHEQUEAMOUNT) < 0) {
                    success = "error/Invalid cheque number found at line " + m_line + " ! . Cheque number should be 6 digitis only.";
                    throw new Exception("error/Invalid cheque number found at line " + m_line + " ! . Cheque number should be 6 digitis only.");
                }

                if (!(CHEQUEAMOUNT.contains("."))) {
                    success = "error/Invalid cheque amount found at line no " + m_line + ". Cheuque amount format should be \"000.00\" !";
                    throw new Exception("error/Invalid cheque amount found at line no " + m_line + ". Cheuque amount format should be \"000.00\" !");
                }

                String[] splitter = CHEQUEAMOUNT.toString().split("\\.");
                splitter[0].length();   // Before Decimal Count
                int decimalLength = splitter[1].length();  // After Decimal Count

                if (!(decimalLength == 2)) {
                    success = "error/Invalid cheque amount found at line no " + m_line + ". Cheuque amount format should be \"000.00\" !";
                    throw new Exception("error/Invalid cheque amount found at line no " + m_line + ". Cheuque amount format should be \"000.00\" !");
                }

                String m_valuedate_year = VALUEDATE.substring(0, 4);
                String m_valuedate_month = VALUEDATE.substring(4, 6);
                String m_valuedate_date = VALUEDATE.substring(6, 8);

                String TEMP_CHQ_VALUE_DATE = m_valuedate_date + "/" + m_valuedate_month + "/" + m_valuedate_year;

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date1 = sdf.parse(TEMP_CHQ_VALUE_DATE);
                Date date2 = sdf.parse(_system_date);
                if (date1.compareTo(date2) == 0) {
                    success = "error/Invalid value date, Found at line " + m_line + ", Value date cannot be today !";
                    throw new Exception("error/Invalid value date, Found at line " + m_line + ", Value date cannot be today !");
                }
                if (!(date1.compareTo(date2) > 0)) {
                    success = "error/Invalid value date, Found at line " + m_line + ", Value date cannot be backdate !";
                    throw new Exception("error/Invalid value date, Found at line " + m_line + ", Value date cannot be backdate !");
                }

                pdcDAO pdcDAO = new pdcDAO();
                String ACTUAL_CHQUE_VALUEDATE = pdcDAO.getchequeValueDate(TEMP_CHQ_VALUE_DATE);

                double FORCHEQUEAMOUNT = Double.parseDouble(new DecimalFormat("####00.00").format(Double.parseDouble(CHEQUEAMOUNT)));
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                String[] date_spliter = ACTUAL_CHQUE_VALUEDATE.split("/");
                int m_year = Integer.parseInt(date_spliter[2]);
                int m_month = Integer.parseInt(date_spliter[1]) - 1;
                int m_day = Integer.parseInt(date_spliter[0]);
                Calendar ced = Calendar.getInstance();

                ced.set(Calendar.YEAR, m_year); // set the year
                ced.set(Calendar.MONTH, m_month); // set the month
                ced.set(Calendar.DAY_OF_MONTH, m_day);
                ced.add(Calendar.DATE, 1);
                String ACTUAL_CHEQUE_LEQUID_DATE = pdcDAO.getchequeValueDate(formatter.format(ced.getTime()));
                String idndb_bank_master_file = null;
                String idndb_branch_master_file = null;
                boolean bank_code_found = false;
                if (NDB_Bank_Master_File_List.size() > 0) {
                    for (NDB_Bank_Master_File nd : NDB_Bank_Master_File_List) {
                        if (nd.getBank_code().equals(BANKCODE)) {
                            idndb_bank_master_file = nd.getIdndb_bank_master_file();
                            m_Strpdc_bank_name = nd.getBank_name();
                            bank_code_found = true;
                            break;
                        }
                    }
                } else {
                    success = "error/Invalid bank code found at line " + m_line + " !";
                    throw new Exception("error/Invalid bank code found at line " + m_line + " !");
                }

                if (bank_code_found == false) {
                    success = "error/Invalid Bank Code found at line " + m_line + " !";
                    throw new Exception("error/Invalid Bank Code found at line " + m_line + " !");
                }

                BRANCHCODE = String.format("%03d", Integer.parseInt(BRANCHCODE));

                boolean branchFound = false;
                for (BranchData bd : branchList) {
                    if (bd.getBranch_id().equals(BRANCHCODE) && bd.getIdndb_bank_master_file().equals(idndb_bank_master_file)) {
                        idndb_branch_master_file = bd.getIdndb_branch_master_file();// _rs.getString("idndb_branch_master_file");
                        m_Strpdc_branch_name = bd.getBranch_name();
                        branchFound = true;
                        break;
                    }
                }
                if (branchFound == false) {
                    success = "error/Invalid Branch Code found at line " + m_line + " !";
                    throw new Exception("error/Invalid Branch Code found at line " + m_line + " !");
                }

                String m_uniq_id = m_idndb_cust_prod_map_seller + BUYERID + CHEQUENUMBER + BANKCODE + BRANCHCODE;

                m_strQry = m_strQry = "SELECT CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code) as txn_master_unq_id,idndb_pdc_txn_master FROM ndb_pdc_txn_master where CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code)='" + m_uniq_id + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in ('AUTHORIZED','UN-AUTHORIZED')";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "error/Cheque number already exist in the system for given bank and branch. Cheque number found at line no " + m_line + ". !";
                    throw new Exception("error/Cheque number already exist in the system for given bank and branch. Cheque number found at line no " + m_line + ". !");
                }

                String buyer_name = "";
                for (Cust_Buyer c : buyer_name_List) {
                    if (c.getIdndb_seller_has_buyers().equals(BUYERID)) {
                        buyer_name = c.getNdb_customer_define_cust_name();
                        break;
                    }
                }

                if (TEMP_CHQ_VALUE_DATE.equals(_system_date)) {
                    success = "error/Cheque value date cant be today " + m_line + " !";
                    throw new Exception("error/Cheque valu date cant be today " + m_line + " !");
                }

                if (m_pdc_req_financing.equals("CW")) {
                    String idndb_customer_define_seller = "";
                    String idndb_cust_prod_map_buyer = "";

                    String sl_has_byr_warehs_tenor = "";
                    double sl_has_byr_warehs_fmax_chq_amu = 0.00;
                    double sl_has_byr_warehs_limit = 0.00;

                    if (sales_buyer_data_List.size() > 0) {
                        for (SellerHasBuyerData s : sales_buyer_data_List) {
                            if (s.getSl_has_byr_prorm_type().equals("CW") && s.getIdndb_seller_has_buyers().equals(BUYERID)) {
                                idndb_customer_define_seller = s.getNdb_seller_has_buyers_idndb_customer_define_seller();
                                idndb_cust_prod_map_buyer = s.getNdb_seller_has_buyers_idndb_customer_define_buyer();
                                sl_has_byr_warehs_tenor = s.getNdb_seller_has_buyers_sl_has_byr_warehs_tenor();
                                sl_has_byr_warehs_fmax_chq_amu = Double.parseDouble(s.getNdb_seller_has_buyers_sl_has_byr_warehs_fmax_chq_amu());
                                sl_has_byr_warehs_limit = Double.parseDouble(s.getNdb_seller_has_buyers_sl_has_byr_warehs_limit());

                                break;
                            }
                        }
                    } else {
                        success = "error/Invalid buyer for sellected seller. error found at line " + m_line + " !";
                        throw new Exception("error/Invalid buyer for sellected seller. error found at line no " + m_line + ". !");
                    }

                    if (!m_idndb_cust_prod_map_seller.equals(idndb_customer_define_seller)) {
                        success = "error/Invalid buyer for sellected seller. error found at line " + m_line + " !";
                        throw new Exception("error/Invalid buyer for sellected seller. error found at line no " + m_line + ". !");
                    }

                    boolean m_buyer_acc_dtails_availability = false;
                    m_strQry = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        m_buyer_acc_dtails_availability = true;
                    }

                    boolean m_buyer_acc_dtails_validity = false;

                    m_strQry = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "' and buyer_accs_bank_code='" + BANKCODE + "' and buyer_accs_branch_code='" + BRANCHCODE + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        m_buyer_acc_dtails_validity = true;
                    }
                    String message = "";

                    Calendar cw_tenor_date = Calendar.getInstance();
                    String[] split_system_date = _system_date.split("/");

                    int _m_year = Integer.parseInt(split_system_date[2]);
                    int _m_month = Integer.parseInt(split_system_date[1]) - 1;
                    int _m_day = Integer.parseInt(split_system_date[0]);

                    cw_tenor_date.set(Calendar.YEAR, _m_year); // set the year
                    cw_tenor_date.set(Calendar.MONTH, _m_month); // set the month
                    cw_tenor_date.set(Calendar.DAY_OF_MONTH, _m_day);
                    cw_tenor_date.add(Calendar.DATE, Integer.parseInt(sl_has_byr_warehs_tenor));

                    String tenor_date = formatter.format(cw_tenor_date.getTime());
                    String value_date = ACTUAL_CHQUE_VALUEDATE;

                    Date formatted_tenor_date = formatter.parse(tenor_date);
                    Date formatted_value_date = formatter.parse(value_date);
                    int i = 1;

                    message = i + ") NEW CW TXN ENTRY </br>";

                    if (!TEMP_CHQ_VALUE_DATE.equals(ACTUAL_CHQUE_VALUEDATE)) {
                        i++;
                        message = message + i + " ) " + TEMP_CHQ_VALUE_DATE + " date is a holiday and value date updated to next workind daya on " + ACTUAL_CHQUE_VALUEDATE + "</br>";
                    }

                    if (formatted_value_date.compareTo(formatted_tenor_date) > 0) {
                        i++;
                        message = message + i + ") Tenor exceeded </br>";
                    }

                    //changing places of cw_seller_limits for CFU-BRD-4
                    double cw_seller_limit = 0.00;
                    double cw_seller_all_txn_amu = 0.00;
                    double cw_seller_limit_exeeded_total = 0.00;
                    String seller_message = "";

                    cw_seller_limit = Double.parseDouble(m_seller_chq_wh_limit);
                    //adding for limit percentages for CFU-BRD-4

                    sl_has_byr_warehs_limit = sl_has_byr_warehs_limit / 100 * cw_seller_limit;

                    double pdc_chq_amu = 0.00;
                    double available_amu = 0.00;
                    for (NDB_Prod_Txn_Master pr : prod_txn_sum_List) {
                        if (pr.getIdndb_customer_define_buyer_id().equals(BUYERID) && pr.getIdndb_customer_define_seller_id().equals(m_idndb_cust_prod_map_seller)) {
                            pdc_chq_amu = pr.getPdc_chq_amu();
                            available_amu = sl_has_byr_warehs_limit - (pdc_chq_amu + FORCHEQUEAMOUNT);
                            break;
                        }
                    }
                    if (pdc_chq_amu == 0.00 && available_amu == 0.00) {
                        pdc_chq_amu = 0.00;
                        available_amu = sl_has_byr_warehs_limit - pdc_chq_amu;
                    }

                    if (FORCHEQUEAMOUNT > sl_has_byr_warehs_fmax_chq_amu) {
                        i++;
                        message = i + ") Buyer per cheque amount exceeded. Buyer definded per cheque amu. " + df2.format(sl_has_byr_warehs_fmax_chq_amu) + "</br>";
                    }

                    if (FORCHEQUEAMOUNT > available_amu) {
                        i++;
                        message = i + ") Buyer cheque warehousing available balance exceeded. Buyer available balance . " + df2.format(available_amu) + " </br";

                    }

                    if (m_buyer_acc_dtails_availability && !m_buyer_acc_dtails_validity) {

                        i++;
                        message = i + ") Cheque bank, branch and account details are not available for the selected buyer(Bank Code :" + BANKCODE + ", Branch code : " + BRANCHCODE + "). < /br> ";

                    }

                    if (m_seller_chq_wh_dr_to_cr_spe_narr.equals("1")) {
                        NARRATION = products.get("NARRATION");

                        if (NARRATION.equals("")) {
                            success = "error/Invalid Narration found at line " + m_line + " !";
                            throw new Exception("error/Invalid Narration found at line " + m_line + " !");
                        }

                        if (NARRATION.length() > 35) {
                            success = "error/Invalid Narration found at line (Narration filed length 35 digits) " + m_line + " !";
                            throw new Exception("error/Invalid Narration found at line (Narration filed length 35 digits) " + m_line + " !");
                        }
                    }

                    for (NDB_Prod_Txn_Master pt : prod_txn_sum_List) {
                        if (pt.getIdndb_customer_define_seller_id().equals(idndb_customer_define_seller) && pt.getPdc_req_financing().equals("CW")) {
                            cw_seller_all_txn_amu = pt.getPdc_chq_amu();
                            break;
                        }
                    }

                    if (cw_seller_limit < (cw_seller_all_txn_amu + FORCHEQUEAMOUNT)) {
                        cw_seller_limit_exeeded_total = (cw_seller_all_txn_amu + FORCHEQUEAMOUNT) - cw_seller_limit;
                        seller_message = "Seller cheque warehousing limit has exceeded.Exceeded Amount : " + df2.format(cw_seller_limit_exeeded_total) + ".";

                    }
                    message = message + seller_message;

                    _prepStmnt.setString(1, "CW");
                    _prepStmnt.setString(2, m_idndb_cust_prod_map_seller);
                    _prepStmnt.setString(3, BUYERID);
                    _prepStmnt.setString(4, idndb_bank_master_file);
                    _prepStmnt.setString(5, BANKCODE);
                    _prepStmnt.setString(6, idndb_branch_master_file);
                    _prepStmnt.setString(7, BRANCHCODE);
                    _prepStmnt.setString(8, CHEQUENUMBER);
                    _prepStmnt.setString(9, _system_date);
                    _prepStmnt.setString(10, ACTUAL_CHQUE_VALUEDATE);
                    _prepStmnt.setString(11, ACTUAL_CHEQUE_LEQUID_DATE);
                    _prepStmnt.setString(12, df.format(FORCHEQUEAMOUNT));
                    _prepStmnt.setString(13, "0.00");
                    _prepStmnt.setString(14, "0.00");
                    _prepStmnt.setString(15, df.format(FORCHEQUEAMOUNT));
                    _prepStmnt.setString(16, m_pdc_bulk_file_id);
                    _prepStmnt.setString(17, m_seller_cms_collection_acc_number);
                    _prepStmnt.setString(18, m_seller_cust_name);
                    _prepStmnt.setString(19, m_Strpdc_bank_name);
                    _prepStmnt.setString(20, m_Strpdc_branch_name);
                    _prepStmnt.setString(21, TEMP_CHQ_VALUE_DATE);
                    _prepStmnt.setString(22, NARRATION);
                    _prepStmnt.setString(23, "");
                    _prepStmnt.setString(24, "");
                    _prepStmnt.setString(25, "");
                    _prepStmnt.setString(26, "");
                    _prepStmnt.setString(27, "");
                    _prepStmnt.setString(28, m_user_id);
                    _prepStmnt.setString(29, m_user_id);

                    if (_prepStmnt.executeUpdate() <= 0) {
                        success = "error/Error occured, Unable to insert pdc cheque details into the database. Error occured when executing line no " + m_line + ". !";
                        throw new Exception("error/Error occured, Unable to insert pdc cheque details into the database. Error occured when executing line no " + m_line + ". !");
                    }

                    _rs = _prepStmnt.getGeneratedKeys();
                    if (_rs.next()) {
                        idndb_pdc_txn_master = _rs.getString(1);
                    }

                    _prepStmnt4.setString(1, idndb_pdc_txn_master);
                    _prepStmnt4.setString(2, message);
                    _prepStmnt4.addBatch();

                }

                if (m_pdc_req_financing.equals("RF")) {

                    String idndb_customer_define_seller = "";

                    String rec_finance_bulk_credit = "";
                    String rf_fixed_frequency = "DAILY";

                    double sl_has_byr_max_chq_amu = 0.00;
                    double shb_facty_det_crd_loam_limit = 0.00;
                    String shb_facty_det_tenor = "";
                    String idndb_cust_prod_map_buyer = "";

                    if (sales_buyer_data_List.size() > 0) {
                        for (SellerHasBuyerData s : sales_buyer_data_List) {
                            if (s.getSl_has_byr_prorm_type().equals("RF") && s.getIdndb_seller_has_buyers().equals(BUYERID)) {

                                idndb_customer_define_seller = s.getNdb_seller_has_buyers_idndb_customer_define_seller();
                                idndb_cust_prod_map_buyer = s.getNdb_seller_has_buyers_idndb_customer_define_buyer();
                                idndb_cust_prod_map_buyer = s.getNdb_seller_has_buyers_idndb_customer_define_buyer();

                                shb_facty_det_tenor = s.getNdb_seller_has_buyers_shb_facty_det_tenor();

                                sl_has_byr_max_chq_amu = Double.parseDouble(s.getNdb_seller_has_buyers_sl_has_byr_max_chq_amu());

                                if (s.getNdb_seller_has_buyers_shb_facty_det_crd_loam_limit() != null) {
                                    shb_facty_det_crd_loam_limit = Double.parseDouble(s.getNdb_seller_has_buyers_shb_facty_det_crd_loam_limit());
                                }

                                rf_fixed_frequency = s.getNdb_seller_has_buyers_rf_fixed_frequency();
                                break;
                            }
                        }
                    } else {
                        success = "error/Invalid buyer for sellected seller. error found at line " + m_line + " !";
                        throw new Exception("error/Invalid buyer for sellected seller. error found at line no " + m_line + ". !");
                    }

                    if (!m_idndb_cust_prod_map_seller.equals(idndb_customer_define_seller)) {
                        success = "error/Invalid buyer for sellected seller. error found at line " + m_line + " !";
                        throw new Exception("error/Invalid buyer for sellected seller. error found at line no " + m_line + ". !");
                    }

                    String message = "";
                    Calendar rf_tenor_date = Calendar.getInstance();
                    String[] split_system_date = _system_date.split("/");

                    int _m_year = Integer.parseInt(split_system_date[2]);
                    int _m_month = Integer.parseInt(split_system_date[1]) - 1;
                    int _m_day = Integer.parseInt(split_system_date[0]);

                    rf_tenor_date.set(Calendar.YEAR, _m_year); // set the year
                    rf_tenor_date.set(Calendar.MONTH, _m_month); // set the month
                    rf_tenor_date.set(Calendar.DAY_OF_MONTH, _m_day);
                    rf_tenor_date.add(Calendar.DATE, Integer.parseInt(shb_facty_det_tenor));

                    String tenor_date = formatter.format(rf_tenor_date.getTime());
                    String value_date = ACTUAL_CHQUE_VALUEDATE;

                    Date formatted_tenor_date = formatter.parse(tenor_date);
                    Date formatted_value_date = formatter.parse(value_date);

                    double rf_seller_limit = 0.00;

                    rec_finance_bulk_credit = m_seller_rec_finance_bulk_credit;
                    rf_seller_limit = Double.parseDouble(m_seller_rec_finance_limit);
                    //adding CFU-BRD-4 Changes - loan perecentage
                    shb_facty_det_crd_loam_limit = shb_facty_det_crd_loam_limit / 100 * rf_seller_limit;

                    // End of extract receivable finance commision data....................................................
                    double m_discounting_advance_rate = 0.00;
                    double m_discounting_amu_from_cheque_amu = 0.00;
                    double m_alternat_amu = 0.00;

                    for (NDB_Seller_has_Buyers buy : ndb_seeler_has_buyers_List) {
                        if (buy.getIdndb_seller_has_buyers().equals(BUYERID)) {
                            m_discounting_advance_rate = Double.parseDouble(buy.getShb_chq_dis_adv_rate_prectange());
                            m_discounting_amu_from_cheque_amu = FORCHEQUEAMOUNT / 100 * m_discounting_advance_rate;
                            m_alternat_amu = FORCHEQUEAMOUNT - m_discounting_amu_from_cheque_amu;
                            break;
                        }
                    }

                    boolean m_buyer_acc_dtails_availability = false;
                    m_strQry = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        m_buyer_acc_dtails_availability = true;
                    }

                    boolean m_buyer_acc_dtails_validity = false;

                    m_strQry = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "' and buyer_accs_bank_code='" + BANKCODE + "' and buyer_accs_branch_code='" + BRANCHCODE + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        m_buyer_acc_dtails_validity = true;
                    }

                    int i = 1;

                    message = i + ") NEW RF TXN ENTRY </br>";

                    if (!TEMP_CHQ_VALUE_DATE.equals(ACTUAL_CHQUE_VALUEDATE)) {
                        i++;
                        message = message + i + " ) " + TEMP_CHQ_VALUE_DATE + " date is a holiday and value date updated to next workind daya on " + ACTUAL_CHQUE_VALUEDATE + "</br>";
                    }

                    if (formatted_value_date.compareTo(formatted_tenor_date) > 0) {
                        i++;
                        message = message + i + ") Tenor exceeded </br>";
                    }

                    double pdc_chq_amu = 0.00;
                    double available_amu = shb_facty_det_crd_loam_limit - pdc_chq_amu;
                    for (NDB_Prod_Txn_Master n : prod_txn_sum_List) {
                        if (n.getIdndb_customer_define_buyer_id().equals(BUYERID) && n.getIdndb_customer_define_seller_id().equals(m_idndb_cust_prod_map_seller)) {
                            pdc_chq_amu = n.getPdc_chq_discounting_amu();
                            available_amu = shb_facty_det_crd_loam_limit - (pdc_chq_amu + m_discounting_amu_from_cheque_amu);
                            break;
                        }
                    }

                    if (FORCHEQUEAMOUNT > sl_has_byr_max_chq_amu) {
                        i++;
                        message = message + i + ") Buyer per cheque amount exceeded. Buyer Definded per cheque amu. " + df2.format(sl_has_byr_max_chq_amu) + "</br>";
                    }

                    //if (m_discounting_amu_from_cheque_amu > available_amu) {
                    //CFU-BRD-4 buyer balnce message wrong at the edge cases
                    if (m_discounting_amu_from_cheque_amu > 0) {
                        i++;
                        message = message + i + ") Buyer available balance exceeded. Buyer available balance . " + df2.format(available_amu) + " </br";

                    }

                    if (m_buyer_acc_dtails_availability && !m_buyer_acc_dtails_validity) {

                        i++;
                        message = i + ") Cheque bank, branch and account details are not available for the selected buyer(Bank Code :" + BANKCODE + ", Branch code : " + BRANCHCODE + "). < /br> ";

                    }

                    double rf_seller_all_txn_amu = 0.00;
                    double rf_seller_limit_exeeded_total = 0.00;
                    for (NDB_Prod_Txn_Master n : prod_txn_sum_List) {
                        if (n.getIdndb_customer_define_seller_id().equals(idndb_customer_define_seller) && n.getPdc_req_financing().equals("RF")) {
                            rf_seller_all_txn_amu = n.getPdc_chq_amu();
                            break;
                        }
                    }

                    String seller_message = "";
                    if (rf_seller_limit < (rf_seller_all_txn_amu + m_discounting_amu_from_cheque_amu)) {
                        rf_seller_limit_exeeded_total = rf_seller_limit - (rf_seller_all_txn_amu + m_discounting_amu_from_cheque_amu);

                        seller_message = "Seller receviable finance loan limit has exceeded.Exceeded Amount : " + df2.format(rf_seller_limit_exeeded_total) + "";
                    }

                    message = message + seller_message;

                    _prepStmnt.setString(1, "RF");
                    _prepStmnt.setString(2, m_idndb_cust_prod_map_seller);
                    _prepStmnt.setString(3, BUYERID);
                    _prepStmnt.setString(4, idndb_bank_master_file);
                    _prepStmnt.setString(5, BANKCODE);
                    _prepStmnt.setString(6, idndb_branch_master_file);
                    _prepStmnt.setString(7, BRANCHCODE);
                    _prepStmnt.setString(8, CHEQUENUMBER);
                    _prepStmnt.setString(9, _system_date);
                    _prepStmnt.setString(10, ACTUAL_CHQUE_VALUEDATE);
                    _prepStmnt.setString(11, ACTUAL_CHEQUE_LEQUID_DATE);
                    _prepStmnt.setString(12, df.format(FORCHEQUEAMOUNT));
                    _prepStmnt.setString(13, df.format(m_discounting_amu_from_cheque_amu));
                    _prepStmnt.setString(14, df.format(m_alternat_amu));
                    _prepStmnt.setString(15, df.format(m_alternat_amu));
                    _prepStmnt.setString(16, m_pdc_bulk_file_id);
                    _prepStmnt.setString(17, m_seller_rec_finance_acc_num);
                    _prepStmnt.setString(18, m_seller_cust_name);
                    _prepStmnt.setString(19, m_Strpdc_bank_name);
                    _prepStmnt.setString(20, m_Strpdc_branch_name);
                    _prepStmnt.setString(21, TEMP_CHQ_VALUE_DATE);
                    _prepStmnt.setString(22, "");
                    _prepStmnt.setString(23, "");
                    _prepStmnt.setString(24, "");
                    _prepStmnt.setString(25, "");
                    _prepStmnt.setString(26, "");
                    _prepStmnt.setString(27, "");
                    _prepStmnt.setString(28, m_user_id);
                    _prepStmnt.setString(29, m_user_id);

                    if (_prepStmnt.executeUpdate() <= 0) {
                        success = "error/Error occured, Unable to insert pdc cheque details into the database. Error occured when executing line no " + m_line + ". !";
                        throw new Exception("error/Error occured, Unable to insert pdc cheque details into the database. Error occured when executing line no " + m_line + ". !");
                    }

                    _rs = _prepStmnt.getGeneratedKeys();
                    if (_rs.next()) {
                        idndb_pdc_txn_master = _rs.getString(1);
                    }

                    ////Eneter Log data
                    _prepStmnt4.setString(1, idndb_pdc_txn_master);
                    _prepStmnt4.setString(2, message);
                    _prepStmnt4.addBatch();

                    //...................................................gefu entries...................................
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

                    String[] gefu_date_arr = _system_date.split("/");
                    String gefu_day = gefu_date_arr[0];
                    String gefu_month = gefu_date_arr[1];
                    String gefu_year = gefu_date_arr[2];
                    gefu_date = gefu_year + gefu_month + gefu_day;

// .................................................... RF facilityGEFU  Entries...............................
                    String gefu_rec_finance_bulk_credit = rec_finance_bulk_credit;

                    gefu_amount = m_discounting_amu_from_cheque_amu;
                    gefu_account = m_seller_rec_finance_acc_num;
                    gefu_credit_debit = "D";
                    gefu_narration = "DISB" + CHEQUENUMBER + "RS" + CHEQUEAMOUNT + buyer_name;
                    gefu_d_amount = m_discounting_amu_from_cheque_amu;
                    gefu_c_amount = 0.00;

                    // RF facility customer RFF ACC debit entry ...............................
                    _prepStmnt2.setString(1, idndb_pdc_txn_master);
                    _prepStmnt2.setString(2, gefu_account);
                    _prepStmnt2.setString(3, gefu_currency);
                    _prepStmnt2.setString(4, gefu_date);
                    _prepStmnt2.setString(5, df.format(gefu_amount));
                    _prepStmnt2.setString(6, gefu_narration);
                    _prepStmnt2.setString(7, gefu_credit_debit);
                    _prepStmnt2.setString(8, gefu_profit_centre);
                    _prepStmnt2.setString(9, gefu_dao);
                    _prepStmnt2.setString(10, df.format(gefu_c_amount));
                    _prepStmnt2.setString(11, df.format(gefu_d_amount));
                    _prepStmnt2.setString(12, m_user_id);
                    _prepStmnt2.setString(13, m_user_id);
                    _prepStmnt2.setString(14, _system_date);
                    _prepStmnt2.setString(15, "RFCDCD");
                    _prepStmnt2.setString(16, "NO");
                    _prepStmnt2.setString(17, gefu_rf_fixed_frequency);
                    _prepStmnt2.addBatch();

                    gefu_amount = m_discounting_amu_from_cheque_amu;
                    gefu_account = m_seller_rec_finance_cr_dsc_proc_acc_num;
                    gefu_credit_debit = "C";
                    gefu_narration = "DISB" + CHEQUENUMBER + "RS" + FORCHEQUEAMOUNT + buyer_name;
                    gefu_c_amount = m_discounting_amu_from_cheque_amu;
                    gefu_d_amount = 0.00;

                    // RF facility customer Collection Account Credit entry ...............................
                    _prepStmnt2.setString(1, idndb_pdc_txn_master);
                    _prepStmnt2.setString(2, gefu_account);
                    _prepStmnt2.setString(3, gefu_currency);
                    _prepStmnt2.setString(4, gefu_date);
                    _prepStmnt2.setString(5, df.format(gefu_amount));
                    _prepStmnt2.setString(6, gefu_narration);
                    _prepStmnt2.setString(7, gefu_credit_debit);
                    _prepStmnt2.setString(8, gefu_profit_centre);
                    _prepStmnt2.setString(9, gefu_dao);
                    _prepStmnt2.setString(10, df.format(gefu_c_amount));
                    _prepStmnt2.setString(11, df.format(gefu_d_amount));
                    _prepStmnt2.setString(12, m_user_id);
                    _prepStmnt2.setString(13, m_user_id);
                    _prepStmnt2.setString(14, _system_date);
                    _prepStmnt2.setString(15, "RFCDBC");
                    _prepStmnt2.setString(16, gefu_rec_finance_bulk_credit);
                    _prepStmnt2.setString(17, gefu_rf_fixed_frequency);
                    _prepStmnt2.addBatch();

                }

            } //End of file loop

            int[] insetrStatus2 = _prepStmnt2.executeBatch();
            for (int i : insetrStatus2) {
                if (i == 0) {
                    success = "error/Error occured.Unable to insert gefu entries into the database. !";
                    throw new Exception("error/Error occured.Unable to insert gefu entries into the database. !");

                }
            }

            _prepStmnt4.executeBatch();

            if (!endConnection(_currentCon)) {
                success = "error/Error occured.Unable to commit transactions into the database. !";
                throw new Exception("error/Error occured.Unable to commit transactions into the database. !");

            }

            success = "success/success";
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in uploading cw & rf entry data, Exception" + e);
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_prepStmnt != null) {
                    _stmnt.close();
                }
                if (_prepStmnt2 != null) {
                    _stmnt2.close();
                }
                if (_prepStmnt3 != null) {
                    _prepStmnt3.close();
                }
                if (_prepStmnt4 != null) {
                    _prepStmnt4.close();
                }
                if (_currentCon != null) {
                    _currentCon.close();
                }

            } catch (Exception e) {
            }
        }
        return success;

    }

    public String uploadCustomizedPDCBulkFileData(JSONObject prm_obj) {

        DecimalFormat df = new DecimalFormat("###.00");
        DecimalFormat df2 = new DecimalFormat("##,###.00");
        String success = "false-error=";
        String _system_date = "";
        pdcDAO pdc = new pdcDAO();
        _system_date = pdc.getSystemDate();

        Parameters para = new Parameters();
        String m_strQry = "";
        String m_user_id = "";
        String m_pdc_bulk_file_id = "";
        String m_pdc_bulk_file_upload_path = "";
        String m_cw_pdc_bulk_upload = "";
        String m_rf_pdc_bulk_upload = "";
        String m_idndb_cust_prod_map_seller = "";
        String m_pdc_req_financing = "";

        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        try {
            _stmnt = _currentCon.createStatement();

            String idndb_pdc_txn_master = "";

            m_user_id = prm_obj.getString("user_id");
            m_pdc_bulk_file_id = prm_obj.getString("PDCBulkUploadFileID");
            m_pdc_bulk_file_upload_path = prm_obj.getString("PDCBulkUploadFilePath");
            m_cw_pdc_bulk_upload = prm_obj.getString("PDCBulkUploadFileCWData");
            m_rf_pdc_bulk_upload = prm_obj.getString("PDCBulkUploadFileRFData");
            m_idndb_cust_prod_map_seller = prm_obj.getString("idndb_customer_define_seller_id");

            if (m_cw_pdc_bulk_upload.equals("ACTIVE")) {
                m_pdc_req_financing = "CW";
            }
            if (m_rf_pdc_bulk_upload.equals("ACTIVE")) {
                m_pdc_req_financing = "RF";
            }

            if (!startConnection(_currentCon)) {
                success = "error/Error occured in starting DB connection. !";
                throw new Exception("error/Error occured in starting DB connection. !");

            }

            m_strQry = "select * from ndb_holiday_marker where ndb_holiday_approval='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "error/Transactions file cannot be upload. Un-authorized holiday marked data is available in the system. !";
                throw new Exception("error/Transactions file cannot be upload. Un-authorized holiday marked data is available in the system. !");
            }

            m_strQry = "select bank_code, idndb_bank_master_file, bank_name from ndb_bank_master_file where bank_status='ACTIVE' and bank_approval='AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            List<NDB_Bank_Master_File> NDB_Bank_Master_File_List = new ArrayList<NDB_Bank_Master_File>();
            while (_rs.next()) {
                NDB_Bank_Master_File file = new NDB_Bank_Master_File();
                file.setBank_code(_rs.getString("bank_code"));
                file.setBank_name(_rs.getString("bank_name"));
                file.setIdndb_bank_master_file(_rs.getString("idndb_bank_master_file"));
                NDB_Bank_Master_File_List.add(file);
            }

            _stmnt.clearBatch();

            m_strQry = "select branch_id, idndb_bank_master_file, idndb_branch_master_file, branch_name "
                    + " from ndb_branch_master_file     "
                    + " where branch_status='ACTIVE' "
                    + " and branch_approval='AUTHORIZED' ";
            _rs = _stmnt.executeQuery(m_strQry);
            List<BranchData> branchList = new ArrayList<BranchData>();
            while (_rs.next()) {
                BranchData branch = new BranchData();
                branch.setBranch_id(_rs.getString("branch_id"));
                branch.setIdndb_bank_master_file(_rs.getString("idndb_bank_master_file"));
                branch.setIdndb_branch_master_file(_rs.getString("idndb_branch_master_file"));
                branch.setBranch_name(_rs.getString("branch_name"));
                branchList.add(branch);
            }
            _stmnt.clearBatch();

            m_strQry = "    SELECT a.cust_name as ndb_customer_define_cust_name,   "
                    + "                 b.idndb_seller_has_buyers       "
                    + "     FROM 	ndb_seller_has_buyers b, ndb_customer_define a, ndb_cust_prod_map c "
                    + "     where c.idndb_cust_prod_map=b.idndb_customer_define_buyer       "
                    + "     and a.idndb_customer_define=b.idndb_seller_has_buyers and b.idndb_customer_define_seller='" + m_idndb_cust_prod_map_seller + "'";

            _rs = _stmnt.executeQuery(m_strQry);
            List<Cust_Buyer> buyer_name_List = new ArrayList<Cust_Buyer>();
            if (_rs.next()) {
                Cust_Buyer cb = new Cust_Buyer();
                cb.setIdndb_seller_has_buyers(_rs.getString("idndb_seller_has_buyers"));
                cb.setNdb_customer_define_cust_name(_rs.getString("ndb_customer_define_cust_name"));
                buyer_name_List.add(cb);
            }
            _stmnt.clearBatch();

            m_strQry = "    SELECT b.sl_has_byr_prorm_type, b.idndb_seller_has_buyers,   "
                    + "     b.chq_wh_commision_crg AS ndb_seller_has_buyers_chq_wh_commision_crg,   "
                    + "     b.idndb_customer_define_seller AS ndb_seller_has_buyers_idndb_customer_define_seller,   "
                    + "     b.idndb_customer_define_buyer AS ndb_seller_has_buyers_idndb_customer_define_buyer,   "
                    + "     b.sl_has_byr_warehs_tenor AS ndb_seller_has_buyers_sl_has_byr_warehs_tenor,     "
                    + "     b.sl_has_byr_warehs_fmax_chq_amu AS ndb_seller_has_buyers_sl_has_byr_warehs_fmax_chq_amu,   "
                    + "     b.sl_has_byr_warehs_limit AS ndb_seller_has_buyers_sl_has_byr_warehs_limit,     "
                    + "     b.cw_tran_base_falt_fee AS ndb_seller_has_buyers_cw_tran_base_falt_fee,     "
                    + "     b.cw_tran_base_from_tran AS ndb_seller_has_buyers_cw_tran_base_from_tran,       "
                    + "     b.cw_fixed_rate_amount AS ndb_seller_has_buyers_cw_fixed_rate_amount,       "
                    + "     b.cw_fixed_frequency AS ndb_seller_has_buyers_cw_fixed_frequency,       "
                    + "     b.rec_finance_commision_crg AS ndb_seller_has_buyers_rec_finance_commision_crg,                     "
                    + "     b.shb_facty_det_tenor AS ndb_seller_has_buyers_shb_facty_det_tenor,    "
                    + "     b.shb_facty_det_crd_loam_limit AS Nb_seller_has_buyers_shb_facty_det_crd_loam_limit,    "
                    + "     b.sl_has_byr_max_chq_amu AS ndb_seller_has_buyers_sl_has_byr_max_chq_amu,    "
                    + "     b.rf_tran_base_falt_fee AS ndb_seller_has_buyers_rf_tran_base_falt_fee,      "
                    + "     b.rf_tran_base_from_tran AS ndb_seller_has_buyers_rf_tran_base_from_tran,        "
                    + "     b.rf_fixed_rate_amount AS ndb_seller_has_buyers_rf_fixed_rate_amount,    "
                    + "     b.rf_fixed_frequency AS ndb_seller_has_buyers_rf_fixed_frequency        "
                    + "     FROM    ndb_cust_prod_map a, ndb_seller_has_buyers b, ndb_customer_define c, ndb_cust_prod_map d    "
                    + "     WHERE   a.idndb_cust_prod_map=b.idndb_customer_define_buyer "
                    + "     and     d.idndb_customer_define=c.idndb_customer_define     "
                    + "     and     d.idndb_cust_prod_map=b.idndb_customer_define_buyer    "
                    + "     and     b.sl_has_byr_status='ACTIVE'    "
                    + "     and     b.sl_has_byr_auth='AUTHORIZED'  "
                    + "     and     d.prod_relationship_status='ACTIVE'     "
                    + "     and     d.prod_relationship_auth='AUTHORIZED'   "
                    + "     and     c.cust_status='ACTIVE'      "
                    + "     and     c.cust_auth='AUTHORIZED' and b.idndb_customer_define_seller='" + m_idndb_cust_prod_map_seller + "'";

            _rs = _stmnt.executeQuery(m_strQry);
            List<SellerHasBuyerData> sales_buyer_data_List = new ArrayList<SellerHasBuyerData>();
            while (_rs.next()) {
                SellerHasBuyerData data = new SellerHasBuyerData();
                data.setSl_has_byr_prorm_type(_rs.getString("sl_has_byr_prorm_type"));
                data.setIdndb_seller_has_buyers(_rs.getString("idndb_seller_has_buyers"));
                data.setNdb_seller_has_buyers_chq_wh_commision_crg(_rs.getString("ndb_seller_has_buyers_chq_wh_commision_crg"));
                data.setNdb_seller_has_buyers_idndb_customer_define_seller(_rs.getString("ndb_seller_has_buyers_idndb_customer_define_seller"));
                data.setNdb_seller_has_buyers_idndb_customer_define_buyer(_rs.getString("ndb_seller_has_buyers_idndb_customer_define_buyer"));
                data.setNdb_seller_has_buyers_sl_has_byr_warehs_tenor(_rs.getString("ndb_seller_has_buyers_sl_has_byr_warehs_tenor"));
                data.setNdb_seller_has_buyers_sl_has_byr_warehs_limit(_rs.getString("ndb_seller_has_buyers_sl_has_byr_warehs_limit"));
                data.setNdb_seller_has_buyers_cw_tran_base_falt_fee(_rs.getString("ndb_seller_has_buyers_cw_tran_base_falt_fee"));
                data.setNdb_seller_has_buyers_cw_tran_base_from_tran(_rs.getString("ndb_seller_has_buyers_cw_tran_base_from_tran"));
                data.setNdb_seller_has_buyers_sl_has_byr_warehs_fmax_chq_amu(_rs.getString("ndb_seller_has_buyers_sl_has_byr_warehs_fmax_chq_amu"));
                data.setNdb_seller_has_buyers_cw_fixed_rate_amount(_rs.getString("ndb_seller_has_buyers_cw_fixed_rate_amount"));
                data.setNdb_seller_has_buyers_cw_fixed_frequency(_rs.getString("ndb_seller_has_buyers_cw_fixed_frequency"));
                data.setNdb_seller_has_buyers_rec_finance_commision_crg(_rs.getString("ndb_seller_has_buyers_rec_finance_commision_crg"));
                data.setNdb_seller_has_buyers_shb_facty_det_tenor(_rs.getString("ndb_seller_has_buyers_shb_facty_det_tenor"));
                data.setNdb_seller_has_buyers_shb_facty_det_crd_loam_limit(_rs.getString("Nb_seller_has_buyers_shb_facty_det_crd_loam_limit"));

                data.setNdb_seller_has_buyers_sl_has_byr_max_chq_amu(_rs.getString("ndb_seller_has_buyers_sl_has_byr_max_chq_amu"));
                data.setNdb_seller_has_buyers_rf_tran_base_falt_fee(_rs.getString("ndb_seller_has_buyers_rf_tran_base_falt_fee"));
                data.setNdb_seller_has_buyers_rf_tran_base_from_tran(_rs.getString("ndb_seller_has_buyers_rf_tran_base_from_tran"));
                data.setNdb_seller_has_buyers_rf_fixed_rate_amount(_rs.getString("ndb_seller_has_buyers_rf_fixed_rate_amount"));
                data.setNdb_seller_has_buyers_rf_fixed_frequency(_rs.getString("ndb_seller_has_buyers_rf_fixed_frequency"));
                sales_buyer_data_List.add(data);
            }
            _stmnt.clearBatch();

            m_strQry = "    select idndb_customer_define_buyer_id, idndb_customer_define_seller_id, pdc_req_financing, SUM(pdc_chq_amu) as pdc_chq_amu, SUM(pdc_chq_discounting_amu) as pdc_chq_discounting_amu     "
                    + "     from ndb_pdc_txn_master     "
                    + "     where pdc_chq_status='ACTIVE' and idndb_customer_define_seller_id='" + m_idndb_cust_prod_map_seller + "' and pdc_chq_status_auth in('AUTHORIZED','UN-AUTHORIZED')  "
                    + "     group by idndb_customer_define_buyer_id, idndb_customer_define_seller_id, pdc_req_financing    ";

            List<NDB_Prod_Txn_Master> prod_txn_sum_List = new ArrayList<NDB_Prod_Txn_Master>();
            _rs = _stmnt.executeQuery(m_strQry);
            while (_rs.next()) {
                NDB_Prod_Txn_Master pr = new NDB_Prod_Txn_Master();
                pr.setIdndb_customer_define_buyer_id(_rs.getString("idndb_customer_define_buyer_id"));
                pr.setIdndb_customer_define_seller_id(_rs.getString("idndb_customer_define_seller_id"));
                pr.setPdc_chq_amu(_rs.getDouble("pdc_chq_amu"));
                pr.setPdc_req_financing(_rs.getString("pdc_req_financing"));
                pr.setPdc_chq_discounting_amu(_rs.getDouble("pdc_chq_discounting_amu"));
                prod_txn_sum_List.add(pr);
            }
            _stmnt.clearBatch();

            m_strQry = "SELECT \n"
                    + "ncpm_cust_prod_map.idndb_cust_prod_map,\n"
                    + "ncd_cust_define.idndb_customer_define, \n"
                    + "ncd_cust_define.cust_id,\n"
                    + "ncd_cust_define.cust_name,\n"
                    + "ncd_cust_define.cms_collection_acc_number, \n"
                    + "ncd_cust_define.rec_finance_curr_ac,\n"
                    + "ncd_cust_define.rec_finance_acc_num, \n"
                    + "ncd_cust_define.rec_finance_cr_dsc_proc_acc_num, \n"
                    + "ncd_cust_define.rec_finance_margin_ac, \n"
                    + "ncd_cust_define.rec_finance_margin,\n"
                    + "nrf_rec_fin_data.rec_finance_bulk_credit, \n"
                    + "nrf_rec_fin_data.rec_finance_erly_wdr_chg, \n"
                    + "nrf_rec_fin_data.rec_finance_vale_dte_extr_chg, \n"
                    + "nrf_rec_fin_data.rec_finance_erly_stlemnt_chg, \n"
                    + "nrf_rec_fin_data.rec_finance_limit,\n"
                    + "ncw_chq_wh_data.chq_wh_dr_to_cr_spe_narr, \n"
                    + "ncw_chq_wh_data.chq_wh_limit,\n"
                    + "ncw_chq_wh_data.chq_wh_erly_wdr_chg, \n"
                    + "ncw_chq_wh_data.chq_wh_vale_dte_extr_chg, \n"
                    + "ncw_chq_wh_data.chq_wh_erly_stlemnt_chg,\n"
                    + "bacd_buyer_acc_details.idbuyer_accs_details \n"
                    + "FROM \n"
                    + "ndb_cust_prod_map ncpm_cust_prod_map inner join\n"
                    + "ndb_customer_define ncd_cust_define on\n"
                    + "ncpm_cust_prod_map.idndb_customer_define = ncd_cust_define.idndb_customer_define left join\n"
                    + "ndb_rec_fin nrf_rec_fin_data on\n"
                    + "ncpm_cust_prod_map.idndb_cust_prod_map = nrf_rec_fin_data.idndb_cust_prod_map left join\n"
                    + "ndb_chq_wh ncw_chq_wh_data on\n"
                    + "ncpm_cust_prod_map.idndb_cust_prod_map = ncw_chq_wh_data.idndb_cust_prod_map left join\n"
                    + "buyer_accs_details bacd_buyer_acc_details on\n"
                    + "ncpm_cust_prod_map.idndb_cust_prod_map = bacd_buyer_acc_details.idndb_cust_prod_map \n"
                    + "where\n"
                    + "ncpm_cust_prod_map.idndb_cust_prod_map='" + m_idndb_cust_prod_map_seller + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            String m_seller_cust_name = "";
            String m_seller_cms_collection_acc_number = "";
            String m_seller_rec_finance_acc_num = "";
            String m_seller_rec_finance_cr_dsc_proc_acc_num = "";
            String m_seller_rec_finance_bulk_credit = "";
            String m_seller_rec_finance_limit = "";
            String m_seller_chq_wh_dr_to_cr_spe_narr = "";
            String m_seller_chq_wh_limit = "";

            if (_rs.next()) {

                m_seller_cust_name = _rs.getString("cust_name");
                m_seller_cms_collection_acc_number = _rs.getString("cms_collection_acc_number");
                m_seller_rec_finance_acc_num = _rs.getString("rec_finance_acc_num");
                m_seller_rec_finance_cr_dsc_proc_acc_num = _rs.getString("rec_finance_cr_dsc_proc_acc_num");
                m_seller_rec_finance_bulk_credit = _rs.getString("rec_finance_bulk_credit");
                m_seller_rec_finance_limit = _rs.getString("rec_finance_limit");
                m_seller_chq_wh_dr_to_cr_spe_narr = _rs.getString("chq_wh_dr_to_cr_spe_narr");
                m_seller_chq_wh_limit = _rs.getString("chq_wh_limit");

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

///////////////////////////////////////////Inserts///////////////////////////////////////////////////////////////
            String insert_Qry = "insert into ndb_pdc_txn_master ("
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
                    + ",pdc_reference_1"
                    + ",pdc_reference_2"
                    + ",pdc_reference_3"
                    + ",pdc_reference_4"
                    + ",pdc_reference_5"
                    + ",pdc_chq_creat_by"
                    + ",pdc_chq_creat_date"
                    + ",pdc_chq_mod_by"
                    + ",pdc_chq_mod_date"
                    + ""
                    + ") values("
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "'ACTIVE',"
                    + "'UN-AUTHORIZED',"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "'DEACTIVE',"
                    + "'DEACTIVE',"
                    + "?,"
                    + "'NEWTXN',"
                    + "'NEW',"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "now(),"
                    + "?,"
                    + "now())";

            _prepStmnt = _currentCon.prepareStatement(insert_Qry, Statement.RETURN_GENERATED_KEYS);

            insert_Qry = "insert into gefu_file_generation ("
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
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "'ACTIVE',"
                    + "'UN-AUTHORIZED',"
                    + "?,"
                    + "NOW(),"
                    + "?,"
                    + "NOW(),"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?)";

            _prepStmnt2 = _currentCon.prepareStatement(insert_Qry);

            insert_Qry = "insert into ndb_pdc_uploaded_customized_data ("
                    + "idndb_pdc_txn_master"
                    + ",pdc_reference_1"
                    + ",pdc_reference_2"
                    + ",pdc_reference_3"
                    + ",pdc_reference_4"
                    + ",pdc_reference_5"
                    + ",npucd_creat_user"
                    + ",npucd_creat_date"
                    + ",npucd_modified_user"
                    + ",npucd_modified_date"
                    + ") values("
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "now(),"
                    + "?,"
                    + "now())";
            _prepStmnt3 = _currentCon.prepareStatement(insert_Qry);

            insert_Qry = "insert into ndb_change_log (ndb_change_log_type,"
                    + "ndb_attached_id,"
                    + "ndb_change"
                    + ",status"
                    + ",creat_by"
                    + ",creat_date"
                    + ""
                    + ") values("
                    + "'PDCTXN',"
                    + "?,"
                    + "?,"
                    + "'UN-AUTHORIZED',"
                    + "'" + m_user_id + "',"
                    + "now())";

            _prepStmnt4 = _currentCon.prepareStatement(insert_Qry);

            CsvReader products = new CsvReader(m_pdc_bulk_file_upload_path);
            products.readHeaders();
            int y = 0;
            int m_line = 0;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
            while (products.readRecord()) {

                String BUYER_ID = products.get("BUYER_ID");
                String CHEQUE_NUMBER = products.get("CHEQUE_NUMBER");
                String BANK_CODE = products.get("BANK_CODE");
                String BRANCH_CODE = products.get("BRANCH_CODE");
                String VALUE_DATE = products.get("VALUE_DATE");
                String CHEQUE_AMOUNT = products.get("CHEQUE_AMOUNT");
                String NARRATION = products.get("NARRATION"); // iF THIS FIELD MANDOTARY IT WILL BE READ UNDER CONDITIONS
                String REFERENCE_1 = products.get("REFERENCE_1");
                String REFERENCE_2 = products.get("REFERENCE_2");
                String REFERENCE_3 = products.get("REFERENCE_3");
                String REFERENCE_4 = products.get("REFERENCE_4");
                String REFERENCE_5 = products.get("REFERENCE_5");
                y++;
                m_line = y;

                if (!(BUYER_ID.equals("") && CHEQUE_NUMBER.equals("") && BANK_CODE.equals("") && BRANCH_CODE.equals("") && VALUE_DATE.equals("") && CHEQUE_AMOUNT.equals(""))) {

                    String m_Strpdc_bank_name = "";
                    String m_Strpdc_branch_name = "";

                    fileUploadDAO upldao = new fileUploadDAO();
                    if (!upldao.isThisDateValid(VALUE_DATE, "yyyyMMdd")) {

                        success = "error/Invalid cheque value date found at line no " + m_line + ". Value date format should be \"YYYYMMDD\" !";
                        throw new Exception("error/Invalid valude date found at line no " + m_line + ". Value date format should be \"YYYYMMDD\" !");

                    }

                    if (CHEQUE_AMOUNT.equals("")) {
                        success = "error/Invalid cheque amount found at line no " + m_line + ". Cheque amount cannot be empty. !";
                        throw new Exception("error/Invalid cheque amount found at line no " + m_line + ". Cheque amount cannot be empty. !");
                    }

                    if (!upldao.isNumeric(CHEQUE_AMOUNT, "D")) {
                        success = "error/Invalid cheque amount found at line no " + m_line + ". Cheuque amount format should be \"000.00\" !";
                        throw new Exception("error/Invalid cheque amount found at line no " + m_line + ". Cheuque amount format should be \"000.00\" !");
                    }

                    if (!upldao.isNumeric(CHEQUE_NUMBER, "I")) {
                        success = "error/Invalid cheque number found at line no " + m_line + ". Cheque amount shuld be numaric value. !";
                        throw new Exception("error/Invalid cheque number found at line no " + m_line + ". Cheque amount shuld be numaric value. !");
                    }

                    if (!(CHEQUE_NUMBER.length() == 6)) {
                        success = "error/Invalid cheque number found at line " + m_line + " ! . Cheque number should be 6 digitis only.";
                        throw new Exception("error/Invalid cheque number found at line " + m_line + " ! . Cheque number should be 6 digitis only.");
                    }

                    if (Double.parseDouble(CHEQUE_AMOUNT) < 0) {
                        success = "error/Invalid cheque number found at line " + m_line + " ! . Cheque number should be 6 digitis only.";
                        throw new Exception("error/Invalid cheque number found at line " + m_line + " ! . Cheque number should be 6 digitis only.");
                    }

                    if (!(CHEQUE_AMOUNT.contains("."))) {
                        success = "error/Invalid cheque amount found at line no " + m_line + ". Cheuque amount format should be \"000.00\" !";
                        throw new Exception("error/Invalid cheque amount found at line no " + m_line + ". Cheuque amount format should be \"000.00\" !");
                    }

                    String[] splitter = CHEQUE_AMOUNT.toString().split("\\.");
                    splitter[0].length();   // Before Decimal Count
                    int decimalLength = splitter[1].length();  // After Decimal Count

                    if (!(decimalLength == 2)) {
                        success = "error/Invalid cheque amount found at line no " + m_line + ". Cheuque amount format should be \"000.00\" !";
                        throw new Exception("error/Invalid cheque amount found at line no " + m_line + ". Cheuque amount format should be \"000.00\" !");
                    }

                    String m_valuedate_year = VALUE_DATE.substring(0, 4);
                    String m_valuedate_month = VALUE_DATE.substring(4, 6);
                    String m_valuedate_date = VALUE_DATE.substring(6, 8);

                    String TEMP_CHQ_VALUE_DATE = m_valuedate_date + "/" + m_valuedate_month + "/" + m_valuedate_year;

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date date1 = sdf.parse(TEMP_CHQ_VALUE_DATE);
                    Date date2 = sdf.parse(_system_date);
                    if (date1.compareTo(date2) == 0) {
                        success = "error/Invalid value date, Found at line " + m_line + ", Value date cannot be today !";
                        throw new Exception("error/Invalid value date, Found at line " + m_line + ", Value date cannot be today !");
                    }
                    if (!(date1.compareTo(date2) > 0)) {
                        success = "error/Invalid value date, Found at line " + m_line + ", Value date cannot be backdate !";
                        throw new Exception("error/Invalid value date, Found at line " + m_line + ", Value date cannot be backdate !");
                    }

                    pdcDAO pdcDAO = new pdcDAO();
                    String ACTUAL_CHQUE_VALUEDATE = pdcDAO.getchequeValueDate(TEMP_CHQ_VALUE_DATE);

                    double FORCHEQUEAMOUNT = Double.parseDouble(new DecimalFormat("####00.00").format(Double.parseDouble(CHEQUE_AMOUNT)));
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                    String[] date_spliter = ACTUAL_CHQUE_VALUEDATE.split("/");
                    int m_year = Integer.parseInt(date_spliter[2]);
                    int m_month = Integer.parseInt(date_spliter[1]) - 1;
                    int m_day = Integer.parseInt(date_spliter[0]);
                    Calendar ced = Calendar.getInstance();

                    ced.set(Calendar.YEAR, m_year); // set the year
                    ced.set(Calendar.MONTH, m_month); // set the month
                    ced.set(Calendar.DAY_OF_MONTH, m_day);
                    ced.add(Calendar.DATE, 1);
                    String ACTUAL_CHEQUE_LEQUID_DATE = pdcDAO.getchequeValueDate(formatter.format(ced.getTime()));
                    String idndb_bank_master_file = null;
                    String idndb_branch_master_file = null;
                    boolean bank_code_found = false;
                    if (NDB_Bank_Master_File_List.size() > 0) {
                        for (NDB_Bank_Master_File nd : NDB_Bank_Master_File_List) {
                            if (nd.getBank_code().equals(BANK_CODE)) {
                                idndb_bank_master_file = nd.getIdndb_bank_master_file();
                                m_Strpdc_bank_name = nd.getBank_name();
                                bank_code_found = true;
                                break;
                            }
                        }
                    } else {
                        success = "error/Invalid bank code found at line " + m_line + " !";
                        throw new Exception("error/Invalid bank code found at line " + m_line + " !");
                    }

                    if (bank_code_found == false) {
                        success = "error/Invalid Bank Code found at line " + m_line + " !";
                        throw new Exception("error/Invalid Bank Code found at line " + m_line + " !");
                    }

                    BRANCH_CODE = String.format("%03d", Integer.parseInt(BRANCH_CODE));

                    boolean branchFound = false;
                    for (BranchData bd : branchList) {
                        if (bd.getBranch_id().equals(BRANCH_CODE) && bd.getIdndb_bank_master_file().equals(idndb_bank_master_file)) {
                            idndb_branch_master_file = bd.getIdndb_branch_master_file();// _rs.getString("idndb_branch_master_file");
                            m_Strpdc_branch_name = bd.getBranch_name();
                            branchFound = true;
                            break;
                        }
                    }
                    if (branchFound == false) {
                        success = "error/Invalid Branch Code found at line " + m_line + " !";
                        throw new Exception("error/Invalid Branch Code found at line " + m_line + " !");
                    }

                    String m_uniq_id = m_idndb_cust_prod_map_seller + BUYER_ID + CHEQUE_NUMBER + BANK_CODE + BRANCH_CODE;

                    m_strQry = m_strQry = "SELECT CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code) as txn_master_unq_id,idndb_pdc_txn_master FROM ndb_pdc_txn_master where CONCAT(idndb_customer_define_seller_id,'',idndb_customer_define_buyer_id,'',pdc_chq_number, '', pdc_bank_code,'',pdc_branch_code)='" + m_uniq_id + "' and pdc_chq_status='ACTIVE' and pdc_chq_status_auth in ('AUTHORIZED','UN-AUTHORIZED')";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        success = "error/Cheque number already exist in the system for given bank and branch. Cheque number found at line no " + m_line + ". !";
                        throw new Exception("error/Cheque number already exist in the system for given bank and branch. Cheque number found at line no " + m_line + ". !");
                    }

                    String buyer_name = "";
                    for (Cust_Buyer c : buyer_name_List) {
                        if (c.getIdndb_seller_has_buyers().equals(BUYER_ID)) {
                            buyer_name = c.getNdb_customer_define_cust_name();
                            break;
                        }
                    }

                    if (TEMP_CHQ_VALUE_DATE.equals(_system_date)) {
                        success = "error/Cheque value date cant be today " + m_line + " !";
                        throw new Exception("error/Cheque valu date cant be today " + m_line + " !");
                    }

//                    if (m_seller_chq_wh_dr_to_cr_spe_narr.equals("1")) {
//                        if (NARRATION.equals("")) {
//                            success = "error/Invalid Narration found at line " + m_line + " !";
//                            throw new Exception("error/Invalid Narration found at line " + m_line + " !");
//                        }
//                    }
                    if (!(NARRATION.isEmpty())) {
                        if (NARRATION.length() > 35) {
                            success = "error/Invalid Narration found at line (Narration filed length 35 digits) " + m_line + " !";
                            throw new Exception("error/Invalid Narration found at line (Narration filed length 35 digits) " + m_line + " !");
                        }
                    }
                    if (!(REFERENCE_1.isEmpty())) {
                        if (REFERENCE_1.length() > 65) {
                            success = "error/Invalid reference 1 found at line (Reference 1 filed length 65 digits) " + m_line + " !";
                            throw new Exception("error/Invalid reference 1 found at line (Reference 1 filed length 65 digits) " + m_line + " !");
                        }
                    }
                    if (!(REFERENCE_2.isEmpty())) {
                        if (REFERENCE_2.length() > 65) {
                            success = "error/Invalid reference 2 found at line (Reference 2 filed length 65 digits) " + m_line + " !";
                            throw new Exception("error/Invalid reference 2 found at line (Reference 2 filed length 65 digits) " + m_line + " !");
                        }
                    }

                    if (!(REFERENCE_3.isEmpty())) {
                        if (REFERENCE_3.length() > 65) {
                            success = "error/Invalid reference 3 found at line (Reference 3 filed length 65 digits) " + m_line + " !";
                            throw new Exception("error/Invalid reference 3 found at line (Reference 3 filed length 65 digits) " + m_line + " !");
                        }
                    }

                    if (!(REFERENCE_4.isEmpty())) {
                        if (REFERENCE_4.length() > 65) {
                            success = "error/Invalid reference 4 found at line (Reference 4 filed length 65 digits) " + m_line + " !";
                            throw new Exception("error/Invalid reference 4 found at line (Reference 4 filed length 65 digits) " + m_line + " !");
                        }
                    }

                    if (!(REFERENCE_5.isEmpty())) {
                        if (REFERENCE_5.length() > 65) {
                            success = "error/Invalid reference 5 found at line (Reference 5 filed length 65 digits) " + m_line + " !";
                            throw new Exception("error/Invalid reference 5 found at line (Reference 5 filed length 65 digits) " + m_line + " !");
                        }
                    }

                    if (m_pdc_req_financing.equals("CW")) {

                        String idndb_customer_define_seller = "";
                        String idndb_cust_prod_map_buyer = "";

                        String sl_has_byr_warehs_tenor = "";
                        double sl_has_byr_warehs_fmax_chq_amu = 0.00;
                        double sl_has_byr_warehs_limit = 0.00;

                        if (sales_buyer_data_List.size() > 0) {
                            for (SellerHasBuyerData s : sales_buyer_data_List) {
                                if (s.getSl_has_byr_prorm_type().equals("CW") && s.getIdndb_seller_has_buyers().equals(BUYER_ID)) {
                                    idndb_customer_define_seller = s.getNdb_seller_has_buyers_idndb_customer_define_seller();
                                    idndb_cust_prod_map_buyer = s.getNdb_seller_has_buyers_idndb_customer_define_buyer();
                                    sl_has_byr_warehs_tenor = s.getNdb_seller_has_buyers_sl_has_byr_warehs_tenor();
                                    sl_has_byr_warehs_fmax_chq_amu = Double.parseDouble(s.getNdb_seller_has_buyers_sl_has_byr_warehs_fmax_chq_amu());
                                    sl_has_byr_warehs_limit = Double.parseDouble(s.getNdb_seller_has_buyers_sl_has_byr_warehs_limit());

                                    break;
                                }
                            }
                        } else {
                            success = "error/Invalid buyer for sellected seller. error found at line " + m_line + " !";
                            throw new Exception("error/Invalid buyer for sellected seller. error found at line no " + m_line + ". !");
                        }

                        if (!m_idndb_cust_prod_map_seller.equals(idndb_customer_define_seller)) {
                            success = "error/Invalid buyer for sellected seller. error found at line " + m_line + " !";
                            throw new Exception("error/Invalid buyer for sellected seller. error found at line no " + m_line + ". !");
                        }

                        boolean m_buyer_acc_dtails_availability = false;
                        m_strQry = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (_rs.next()) {
                            m_buyer_acc_dtails_availability = true;
                        }

                        boolean m_buyer_acc_dtails_validity = false;

                        m_strQry = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "' and buyer_accs_bank_code='" + BANK_CODE + "' and buyer_accs_branch_code='" + BRANCH_CODE + "'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (_rs.next()) {
                            m_buyer_acc_dtails_validity = true;
                        }
                        String message = "";

                        Calendar cw_tenor_date = Calendar.getInstance();
                        String[] split_system_date = _system_date.split("/");

                        int _m_year = Integer.parseInt(split_system_date[2]);
                        int _m_month = Integer.parseInt(split_system_date[1]) - 1;
                        int _m_day = Integer.parseInt(split_system_date[0]);

                        cw_tenor_date.set(Calendar.YEAR, _m_year); // set the year
                        cw_tenor_date.set(Calendar.MONTH, _m_month); // set the month
                        cw_tenor_date.set(Calendar.DAY_OF_MONTH, _m_day);
                        cw_tenor_date.add(Calendar.DATE, Integer.parseInt(sl_has_byr_warehs_tenor));

                        String tenor_date = formatter.format(cw_tenor_date.getTime());
                        String value_date = ACTUAL_CHQUE_VALUEDATE;

                        Date formatted_tenor_date = formatter.parse(tenor_date);
                        Date formatted_value_date = formatter.parse(value_date);
                        int i = 1;

                        message = i + ") NEW CW TXN ENTRY </br>";

                        if (!TEMP_CHQ_VALUE_DATE.equals(ACTUAL_CHQUE_VALUEDATE)) {
                            i++;
                            message = message + i + " ) " + TEMP_CHQ_VALUE_DATE + " date is a holiday and value date updated to next workind daya on " + ACTUAL_CHQUE_VALUEDATE + "</br>";
                        }

                        if (formatted_value_date.compareTo(formatted_tenor_date) > 0) {
                            i++;
                            message = message + i + ") Tenor exceeded </br>";
                        }

                        //changing places of cw_seller_limits for CFU-BRD-4
                        double cw_seller_limit = 0.00;
                        double cw_seller_all_txn_amu = 0.00;
                        double cw_seller_limit_exeeded_total = 0.00;
                        String seller_message = "";

                        cw_seller_limit = Double.parseDouble(m_seller_chq_wh_limit);
                        //adding for limit percentages for CFU-BRD-4

                        sl_has_byr_warehs_limit = sl_has_byr_warehs_limit / 100 * cw_seller_limit;

                        double pdc_chq_amu = 0.00;
                        double available_amu = 0.00;
                        for (NDB_Prod_Txn_Master pr : prod_txn_sum_List) {
                            if (pr.getIdndb_customer_define_buyer_id().equals(BUYER_ID) && pr.getIdndb_customer_define_seller_id().equals(m_idndb_cust_prod_map_seller)) {
                                pdc_chq_amu = pr.getPdc_chq_amu();
                                available_amu = sl_has_byr_warehs_limit - (pdc_chq_amu + FORCHEQUEAMOUNT);
                                break;
                            }
                        }
                        if (pdc_chq_amu == 0.00 && available_amu == 0.00) {
                            pdc_chq_amu = 0.00;
                            available_amu = sl_has_byr_warehs_limit - pdc_chq_amu;
                        }

                        if (FORCHEQUEAMOUNT > sl_has_byr_warehs_fmax_chq_amu) {
                            i++;
                            message = i + ") Buyer per cheque amount exceeded. Buyer definded per cheque amu. " + df2.format(sl_has_byr_warehs_fmax_chq_amu) + "</br>";
                        }

                        if (FORCHEQUEAMOUNT > available_amu) {
                            i++;
                            message = i + ") Buyer cheque warehousing available balance exceeded. Buyer available balance . " + df2.format(available_amu) + " </br";

                        }

                        if (m_buyer_acc_dtails_availability && !m_buyer_acc_dtails_validity) {

                            i++;
                            message = i + ") Cheque bank, branch and account details are not available for the selected buyer(Bank Code :" + BANK_CODE + ", Branch code : " + BRANCH_CODE + "). < /br> ";

                        }

                        for (NDB_Prod_Txn_Master pt : prod_txn_sum_List) {
                            if (pt.getIdndb_customer_define_seller_id().equals(idndb_customer_define_seller) && pt.getPdc_req_financing().equals("CW")) {
                                cw_seller_all_txn_amu = pt.getPdc_chq_amu();
                                break;
                            }
                        }

                        if (cw_seller_limit < (cw_seller_all_txn_amu + FORCHEQUEAMOUNT)) {
                            cw_seller_limit_exeeded_total = (cw_seller_all_txn_amu + FORCHEQUEAMOUNT) - cw_seller_limit;
                            seller_message = "Seller cheque warehousing limit has exceeded.Exceeded Amount : " + df2.format(cw_seller_limit_exeeded_total) + ".";

                        }
                        message = message + seller_message;

                        _prepStmnt.setString(1, "CW");
                        _prepStmnt.setString(2, m_idndb_cust_prod_map_seller);
                        _prepStmnt.setString(3, BUYER_ID);
                        _prepStmnt.setString(4, idndb_bank_master_file);
                        _prepStmnt.setString(5, BANK_CODE);
                        _prepStmnt.setString(6, idndb_branch_master_file);
                        _prepStmnt.setString(7, BRANCH_CODE);
                        _prepStmnt.setString(8, CHEQUE_NUMBER);
                        _prepStmnt.setString(9, _system_date);
                        _prepStmnt.setString(10, ACTUAL_CHQUE_VALUEDATE);
                        _prepStmnt.setString(11, ACTUAL_CHEQUE_LEQUID_DATE);
                        _prepStmnt.setString(12, df.format(FORCHEQUEAMOUNT));
                        _prepStmnt.setString(13, "0.00");
                        _prepStmnt.setString(14, "0.00");
                        _prepStmnt.setString(15, df.format(FORCHEQUEAMOUNT));
                        _prepStmnt.setString(16, m_pdc_bulk_file_id);
                        _prepStmnt.setString(17, m_seller_cms_collection_acc_number);
                        _prepStmnt.setString(18, m_seller_cust_name);
                        _prepStmnt.setString(19, m_Strpdc_bank_name);
                        _prepStmnt.setString(20, m_Strpdc_branch_name);
                        _prepStmnt.setString(21, TEMP_CHQ_VALUE_DATE);
                        _prepStmnt.setString(22, NARRATION);
                        _prepStmnt.setString(23, REFERENCE_1);
                        _prepStmnt.setString(24, REFERENCE_2);
                        _prepStmnt.setString(25, REFERENCE_3);
                        _prepStmnt.setString(26, REFERENCE_4);
                        _prepStmnt.setString(27, REFERENCE_5);
                        _prepStmnt.setString(28, m_user_id);
                        _prepStmnt.setString(29, m_user_id);

                        if (_prepStmnt.executeUpdate() <= 0) {
                            success = "error/Error occured, Unable to insert pdc cheque details into the database. Error occured when executing line no " + m_line + ". !";
                            throw new Exception("error/Error occured, Unable to insert pdc cheque details into the database. Error occured when executing line no " + m_line + ". !");
                        }

                        _rs = _prepStmnt.getGeneratedKeys();
                        if (_rs.next()) {
                            idndb_pdc_txn_master = _rs.getString(1);
                        }

                        _prepStmnt4.setString(1, idndb_pdc_txn_master);
                        _prepStmnt4.setString(2, message);
                        _prepStmnt4.addBatch();

                    }

                    if (m_pdc_req_financing.equals("RF")) {

                        String idndb_customer_define_seller = "";

                        String rec_finance_bulk_credit = "";
                        String rf_fixed_frequency = "DAILY";

                        double sl_has_byr_max_chq_amu = 0.00;
                        double shb_facty_det_crd_loam_limit = 0.00;
                        String shb_facty_det_tenor = "";
                        String idndb_cust_prod_map_buyer = "";

                        if (sales_buyer_data_List.size() > 0) {
                            for (SellerHasBuyerData s : sales_buyer_data_List) {
                                if (s.getSl_has_byr_prorm_type().equals("RF") && s.getIdndb_seller_has_buyers().equals(BUYER_ID)) {

                                    idndb_customer_define_seller = s.getNdb_seller_has_buyers_idndb_customer_define_seller();
                                    idndb_cust_prod_map_buyer = s.getNdb_seller_has_buyers_idndb_customer_define_buyer();
                                    idndb_cust_prod_map_buyer = s.getNdb_seller_has_buyers_idndb_customer_define_buyer();

                                    shb_facty_det_tenor = s.getNdb_seller_has_buyers_shb_facty_det_tenor();

                                    sl_has_byr_max_chq_amu = Double.parseDouble(s.getNdb_seller_has_buyers_sl_has_byr_max_chq_amu());

                                    if (s.getNdb_seller_has_buyers_shb_facty_det_crd_loam_limit() != null) {
                                        shb_facty_det_crd_loam_limit = Double.parseDouble(s.getNdb_seller_has_buyers_shb_facty_det_crd_loam_limit());
                                    }

                                    rf_fixed_frequency = s.getNdb_seller_has_buyers_rf_fixed_frequency();
                                    break;
                                }
                            }
                        } else {
                            success = "error/Invalid buyer for sellected seller. error found at line " + m_line + " !";
                            throw new Exception("error/Invalid buyer for sellected seller. error found at line no " + m_line + ". !");
                        }

                        if (!m_idndb_cust_prod_map_seller.equals(idndb_customer_define_seller)) {
                            success = "error/Invalid buyer for sellected seller. error found at line " + m_line + " !";
                            throw new Exception("error/Invalid buyer for sellected seller. error found at line no " + m_line + ". !");
                        }

                        String message = "";
                        Calendar rf_tenor_date = Calendar.getInstance();
                        String[] split_system_date = _system_date.split("/");

                        int _m_year = Integer.parseInt(split_system_date[2]);
                        int _m_month = Integer.parseInt(split_system_date[1]) - 1;
                        int _m_day = Integer.parseInt(split_system_date[0]);

                        rf_tenor_date.set(Calendar.YEAR, _m_year); // set the year
                        rf_tenor_date.set(Calendar.MONTH, _m_month); // set the month
                        rf_tenor_date.set(Calendar.DAY_OF_MONTH, _m_day);
                        rf_tenor_date.add(Calendar.DATE, Integer.parseInt(shb_facty_det_tenor));

                        String tenor_date = formatter.format(rf_tenor_date.getTime());
                        String value_date = ACTUAL_CHQUE_VALUEDATE;

                        Date formatted_tenor_date = formatter.parse(tenor_date);
                        Date formatted_value_date = formatter.parse(value_date);

                        double rf_seller_limit = 0.00;

                        rec_finance_bulk_credit = m_seller_rec_finance_bulk_credit;
                        rf_seller_limit = Double.parseDouble(m_seller_rec_finance_limit);
                        //adding CFU-BRD-4 Changes - loan perecentage
                        shb_facty_det_crd_loam_limit = shb_facty_det_crd_loam_limit / 100 * rf_seller_limit;

                        // End of extract receivable finance commision data....................................................
                        double m_discounting_advance_rate = 0.00;
                        double m_discounting_amu_from_cheque_amu = 0.00;
                        double m_alternat_amu = 0.00;

                        for (NDB_Seller_has_Buyers buy : ndb_seeler_has_buyers_List) {
                            if (buy.getIdndb_seller_has_buyers().equals(BUYER_ID)) {
                                m_discounting_advance_rate = Double.parseDouble(buy.getShb_chq_dis_adv_rate_prectange());
                                m_discounting_amu_from_cheque_amu = FORCHEQUEAMOUNT / 100 * m_discounting_advance_rate;
                                m_alternat_amu = FORCHEQUEAMOUNT - m_discounting_amu_from_cheque_amu;
                                break;
                            }
                        }

                        boolean m_buyer_acc_dtails_availability = false;
                        m_strQry = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (_rs.next()) {
                            m_buyer_acc_dtails_availability = true;
                        }

                        boolean m_buyer_acc_dtails_validity = false;

                        m_strQry = "select * from buyer_accs_details where idndb_cust_prod_map='" + idndb_cust_prod_map_buyer + "' and buyer_accs_bank_code='" + BANK_CODE + "' and buyer_accs_branch_code='" + BRANCH_CODE + "'";
                        _rs = _stmnt.executeQuery(m_strQry);
                        if (_rs.next()) {
                            m_buyer_acc_dtails_validity = true;
                        }

                        int i = 1;

                        message = i + ") NEW RF TXN ENTRY </br>";

                        if (!TEMP_CHQ_VALUE_DATE.equals(ACTUAL_CHQUE_VALUEDATE)) {
                            i++;
                            message = message + i + " ) " + TEMP_CHQ_VALUE_DATE + " date is a holiday and value date updated to next workind daya on " + ACTUAL_CHQUE_VALUEDATE + "</br>";
                        }

                        if (formatted_value_date.compareTo(formatted_tenor_date) > 0) {
                            i++;
                            message = message + i + ") Tenor exceeded </br>";
                        }

                        double pdc_chq_amu = 0.00;
                        double available_amu = shb_facty_det_crd_loam_limit - pdc_chq_amu;
                        for (NDB_Prod_Txn_Master n : prod_txn_sum_List) {
                            if (n.getIdndb_customer_define_buyer_id().equals(BUYER_ID) && n.getIdndb_customer_define_seller_id().equals(m_idndb_cust_prod_map_seller)) {
                                pdc_chq_amu = n.getPdc_chq_discounting_amu();
                                available_amu = shb_facty_det_crd_loam_limit - (pdc_chq_amu + m_discounting_amu_from_cheque_amu);
                                break;
                            }
                        }

                        if (FORCHEQUEAMOUNT > sl_has_byr_max_chq_amu) {
                            i++;
                            message = message + i + ") Buyer per cheque amount exceeded. Buyer Definded per cheque amu. " + df2.format(sl_has_byr_max_chq_amu) + "</br>";
                        }

                        //if (m_discounting_amu_from_cheque_amu > available_amu) {
                        //CFU-BRD-4 buyer balnce message wrong at the edge cases
                        if (m_discounting_amu_from_cheque_amu > 0) {
                            i++;
                            message = message + i + ") Buyer available balance exceeded. Buyer available balance . " + df2.format(available_amu) + " </br";

                        }

                        if (m_buyer_acc_dtails_availability && !m_buyer_acc_dtails_validity) {

                            i++;
                            message = i + ") Cheque bank, branch and account details are not available for the selected buyer(Bank Code :" + BANK_CODE + ", Branch code : " + BRANCH_CODE + "). < /br> ";

                        }

                        double rf_seller_all_txn_amu = 0.00;
                        double rf_seller_limit_exeeded_total = 0.00;
                        for (NDB_Prod_Txn_Master n : prod_txn_sum_List) {
                            if (n.getIdndb_customer_define_seller_id().equals(idndb_customer_define_seller) && n.getPdc_req_financing().equals("RF")) {
                                rf_seller_all_txn_amu = n.getPdc_chq_amu();
                                break;
                            }
                        }

                        String seller_message = "";
                        if (rf_seller_limit < (rf_seller_all_txn_amu + m_discounting_amu_from_cheque_amu)) {
                            rf_seller_limit_exeeded_total = rf_seller_limit - (rf_seller_all_txn_amu + m_discounting_amu_from_cheque_amu);

                            seller_message = "Seller receviable finance loan limit has exceeded.Exceeded Amount : " + df2.format(rf_seller_limit_exeeded_total) + "";
                        }

                        message = message + seller_message;

                        _prepStmnt.setString(1, "RF");
                        _prepStmnt.setString(2, m_idndb_cust_prod_map_seller);
                        _prepStmnt.setString(3, BUYER_ID);
                        _prepStmnt.setString(4, idndb_bank_master_file);
                        _prepStmnt.setString(5, BANK_CODE);
                        _prepStmnt.setString(6, idndb_branch_master_file);
                        _prepStmnt.setString(7, BRANCH_CODE);
                        _prepStmnt.setString(8, CHEQUE_NUMBER);
                        _prepStmnt.setString(9, _system_date);
                        _prepStmnt.setString(10, ACTUAL_CHQUE_VALUEDATE);
                        _prepStmnt.setString(11, ACTUAL_CHEQUE_LEQUID_DATE);
                        _prepStmnt.setString(12, df.format(FORCHEQUEAMOUNT));
                        _prepStmnt.setString(13, df.format(m_discounting_amu_from_cheque_amu));
                        _prepStmnt.setString(14, df.format(m_alternat_amu));
                        _prepStmnt.setString(15, df.format(m_alternat_amu));
                        _prepStmnt.setString(16, m_pdc_bulk_file_id);
                        _prepStmnt.setString(17, m_seller_rec_finance_acc_num);
                        _prepStmnt.setString(18, m_seller_cust_name);
                        _prepStmnt.setString(19, m_Strpdc_bank_name);
                        _prepStmnt.setString(20, m_Strpdc_branch_name);
                        _prepStmnt.setString(21, TEMP_CHQ_VALUE_DATE);
                        _prepStmnt.setString(22, NARRATION);
                        _prepStmnt.setString(23, REFERENCE_1);
                        _prepStmnt.setString(24, REFERENCE_2);
                        _prepStmnt.setString(25, REFERENCE_3);
                        _prepStmnt.setString(26, REFERENCE_4);
                        _prepStmnt.setString(27, REFERENCE_5);
                        _prepStmnt.setString(28, m_user_id);
                        _prepStmnt.setString(29, m_user_id);

                        if (_prepStmnt.executeUpdate() <= 0) {
                            success = "error/Error occured, Unable to insert pdc cheque details into the database. Error occured when executing line no " + m_line + ". !";
                            throw new Exception("error/Error occured, Unable to insert pdc cheque details into the database. Error occured when executing line no " + m_line + ". !");
                        }

                        _rs = _prepStmnt.getGeneratedKeys();
                        if (_rs.next()) {
                            idndb_pdc_txn_master = _rs.getString(1);
                        }

                        ////Eneter Log data
                        _prepStmnt4.setString(1, idndb_pdc_txn_master);
                        _prepStmnt4.setString(2, message);
                        _prepStmnt4.addBatch();

                        //...................................................gefu entries...................................
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

                        String[] gefu_date_arr = _system_date.split("/");
                        String gefu_day = gefu_date_arr[0];
                        String gefu_month = gefu_date_arr[1];
                        String gefu_year = gefu_date_arr[2];
                        gefu_date = gefu_year + gefu_month + gefu_day;

// .................................................... RF facilityGEFU  Entries...............................
                        String gefu_rec_finance_bulk_credit = rec_finance_bulk_credit;

                        gefu_amount = m_discounting_amu_from_cheque_amu;
                        gefu_account = m_seller_rec_finance_acc_num;
                        gefu_credit_debit = "D";
                        gefu_narration = "DISB" + CHEQUE_NUMBER + "RS" + CHEQUE_AMOUNT + buyer_name;
                        gefu_d_amount = m_discounting_amu_from_cheque_amu;
                        gefu_c_amount = 0.00;

                        // RF facility customer RFF ACC debit entry ...............................
                        _prepStmnt2.setString(1, idndb_pdc_txn_master);
                        _prepStmnt2.setString(2, gefu_account);
                        _prepStmnt2.setString(3, gefu_currency);
                        _prepStmnt2.setString(4, gefu_date);
                        _prepStmnt2.setString(5, df.format(gefu_amount));
                        _prepStmnt2.setString(6, gefu_narration);
                        _prepStmnt2.setString(7, gefu_credit_debit);
                        _prepStmnt2.setString(8, gefu_profit_centre);
                        _prepStmnt2.setString(9, gefu_dao);
                        _prepStmnt2.setString(10, df.format(gefu_c_amount));
                        _prepStmnt2.setString(11, df.format(gefu_d_amount));
                        _prepStmnt2.setString(12, m_user_id);
                        _prepStmnt2.setString(13, m_user_id);
                        _prepStmnt2.setString(14, _system_date);
                        _prepStmnt2.setString(15, "RFCDCD");
                        _prepStmnt2.setString(16, "NO");
                        _prepStmnt2.setString(17, gefu_rf_fixed_frequency);
                        _prepStmnt2.addBatch();

                        gefu_amount = m_discounting_amu_from_cheque_amu;
                        gefu_account = m_seller_rec_finance_cr_dsc_proc_acc_num;
                        gefu_credit_debit = "C";
                        gefu_narration = "DISB" + CHEQUE_NUMBER + "RS" + FORCHEQUEAMOUNT + buyer_name;
                        gefu_c_amount = m_discounting_amu_from_cheque_amu;
                        gefu_d_amount = 0.00;

                        // RF facility customer Collection Account Credit entry ...............................
                        _prepStmnt2.setString(1, idndb_pdc_txn_master);
                        _prepStmnt2.setString(2, gefu_account);
                        _prepStmnt2.setString(3, gefu_currency);
                        _prepStmnt2.setString(4, gefu_date);
                        _prepStmnt2.setString(5, df.format(gefu_amount));
                        _prepStmnt2.setString(6, gefu_narration);
                        _prepStmnt2.setString(7, gefu_credit_debit);
                        _prepStmnt2.setString(8, gefu_profit_centre);
                        _prepStmnt2.setString(9, gefu_dao);
                        _prepStmnt2.setString(10, df.format(gefu_c_amount));
                        _prepStmnt2.setString(11, df.format(gefu_d_amount));
                        _prepStmnt2.setString(12, m_user_id);
                        _prepStmnt2.setString(13, m_user_id);
                        _prepStmnt2.setString(14, _system_date);
                        _prepStmnt2.setString(15, "RFCDBC");
                        _prepStmnt2.setString(16, gefu_rec_finance_bulk_credit);
                        _prepStmnt2.setString(17, gefu_rf_fixed_frequency);
                        _prepStmnt2.addBatch();

                    }

                } else {

                    if (!CHEQUE_NUMBER.equals("")) {
                        success = "error/Cheuqe payment entries cannot be contained cheque number. Error found at line no " + m_line + ". !";
                        throw new Exception("error/Cheuqe payment entries cannot be contained cheque number. Error found at line no " + m_line + ". !");
                    }
                    if (!BANK_CODE.equals("")) {
                        success = "error/Cheuqe payment entries cannot be contained bank code. Error found at line no " + m_line + ". !";
                        throw new Exception("error/Cheuqe payment entries cannot be contained bank code. Error found at line no " + m_line + ". !");
                    }
                    if (!BRANCH_CODE.equals("")) {
                        success = "error/Cheuqe payment entries cannot be contained branch code. Error found at line no " + m_line + ". !";
                        throw new Exception("error/Cheuqe payment entries cannot be contained branch code. Error found at line no " + m_line + ". !");
                    }
                    if (!VALUE_DATE.equals("")) {
                        success = "error/Cheuqe payment entries cannot be contained value date. Error found at line no " + m_line + ". !";
                        throw new Exception("error/Cheuqe payment entries cannot be contained value date. Error found at line no " + m_line + ". !");
                    }

                    if (!CHEQUE_AMOUNT.equals("")) {
                        success = "error/Cheuqe payment entries cannot be contained cheque amount. Error found at line no " + m_line + ". !";
                        throw new Exception("error/Cheuqe payment entries cannot be contained cheque amount. Error found at line no " + m_line + ". !");
                    }

                    if ((BUYER_ID.equals("") && CHEQUE_NUMBER.equals("") && BANK_CODE.equals("") && BRANCH_CODE.equals("") && VALUE_DATE.equals("") && CHEQUE_AMOUNT.equals(""))) {

                        if (!REFERENCE_1.equals("") && REFERENCE_1.length() > 65) {
                            success = "error/Cheuqe payment reference 1 maximum length exceeded (reference 1 maximum length is 65 digits.). Error found at line no " + m_line + ". !";
                            throw new Exception("error/Cheuqe payment entries cannot be contained cheque amount. Error found at line no " + m_line + ". !");
                        }
                        if (!REFERENCE_2.equals("") && REFERENCE_2.length() > 65) {
                            success = "error/Cheuqe payment reference 2 maximum length exceeded (reference 1 maximum length is 65 digits.). Error found at line no " + m_line + ". !";
                            throw new Exception("error/Cheuqe payment entries cannot be contained cheque amount. Error found at line no " + m_line + ". !");
                        }
                        if (!REFERENCE_3.equals("") && REFERENCE_3.length() > 65) {
                            success = "error/Cheuqe payment reference 3 maximum length exceeded (reference 1 maximum length is 65 digits.). Error found at line no " + m_line + ". !";
                            throw new Exception("error/Cheuqe payment entries cannot be contained cheque amount. Error found at line no " + m_line + ". !");
                        }
                        if (!REFERENCE_4.equals("") && REFERENCE_4.length() > 65) {
                            success = "error/Cheuqe payment reference 4 maximum length exceeded (reference 1 maximum length is 65 digits.). Error found at line no " + m_line + ". !";
                            throw new Exception("error/Cheuqe payment entries cannot be contained cheque amount. Error found at line no " + m_line + ". !");
                        }
                        if (!REFERENCE_5.equals("") && REFERENCE_5.length() > 65) {
                            success = "error/Cheuqe payment reference 5 maximum length exceeded (reference 1 maximum length is 65 digits.). Error found at line no " + m_line + ". !";
                            throw new Exception("error/Cheuqe payment entries cannot be contained cheque amount. Error found at line no " + m_line + ". !");
                        }

                        _prepStmnt3.setString(1, idndb_pdc_txn_master);
                        _prepStmnt3.setString(2, REFERENCE_1);
                        _prepStmnt3.setString(3, REFERENCE_2);
                        _prepStmnt3.setString(4, REFERENCE_3);
                        _prepStmnt3.setString(5, REFERENCE_4);
                        _prepStmnt3.setString(6, REFERENCE_5);
                        _prepStmnt3.setString(7, m_user_id);
                        _prepStmnt3.setString(8, m_user_id);
                        _prepStmnt3.addBatch();

                    }
                }
            } //End of file loop

            int[] insetrStatus2 = _prepStmnt2.executeBatch();
            for (int i : insetrStatus2) {
                if (i == 0) {
                    success = "error/Error occured.Unable to insert gefu entries into the database. !";
                    throw new Exception("error/Error occured.Unable to insert gefu entries into the database. !");

                }
            }

            int[] insetrStatus3 = _prepStmnt3.executeBatch();
            for (int i : insetrStatus3) {
                if (i == 0) {
                    success = "error/Error occured.Unable to insert payament details into the database. !";
                    throw new Exception("error/Error occured.Unable to insert payament details into the database. !");

                }
            }

            _prepStmnt4.executeBatch();

            if (!endConnection(_currentCon)) {
                success = "error/Error occured.Unable to commit transactions into the database. !";
                throw new Exception("error/Error occured.Unable to commit transactions into the database. !");

            }

            success = "success/success";
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in uploading cw & rf entry data, Exception" + e);
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_stmnt != null) {
                    _stmnt.close();
                }
                if (_prepStmnt != null) {
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

    public String uploadReturnFileData(JSONObject prm_obj) {
        String success = "false-error=";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        Parameters para = new Parameters();

        String m_strQry = "";
        String m_user_id = "";
        String m_ReturnFileID = "";
        String m_ReturnFilePath = "";
        int idgefu_upload = 0;
        ResultSet m_rs = null;

        try {
            m_user_id = prm_obj.getString("user_id");
            m_ReturnFileID = prm_obj.getString("ReturnFileID");
            m_ReturnFilePath = prm_obj.getString("ReturnFilePath");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }

            _stmnt = _currentCon.createStatement();

            String _system_date = "";

            m_strQry = "select * from ndb_system_date";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {

                _system_date = _rs.getString("_system_date");

            }
            m_strQry = "SELECT * FROM ndb_bank_master_file where bank_approval='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry = "SELECT * FROM ndb_branch_master_file where branch_approval='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry = "SELECT * FROM ndb_customer_define where cust_auth='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry = "SELECT * FROM ndb_cust_prod_map where prod_relationship_auth='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry = "SELECT * FROM ndb_seller_has_buyers where sl_has_byr_auth='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            m_strQry = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status_auth='UN-AUTHORIZED'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "0011";
                throw new Exception("UN-AUTHORIZED Records found please refer the un-authorized report for more detailss");
            }

            FileInputStream fstream = new FileInputStream(m_ReturnFilePath);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String m_recived_line = "";

            if ((strLine = br.readLine()) == null) {
                m_strQry = "insert into ndb_day_today_process (day_today_process_name,"
                        + "processed_date,"
                        + "processed_by"
                        + ",processed_time"
                        + ""
                        + ") values("
                        + "'RETRNFILEUPLD',"
                        + "'" + _system_date + "',"
                        + "'" + m_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }
            } else {
                in.close();
                FileInputStream fstream2 = new FileInputStream(m_ReturnFilePath);

                DataInputStream in2 = new DataInputStream(fstream2);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
                while ((strLine = br2.readLine()) != null) {

                    m_recived_line = strLine.trim();
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < m_recived_line.length(); i++) {
                        char c = m_recived_line.charAt(i);
                        if (Character.isDigit(c)) {
                            builder.append(c);
                        }
                    }
                    String numbers = builder.toString();

                    if (numbers.length() < 25) {
                        success = "0012";
                        throw new Exception("INVALID RETURN FILE FORMAT");
                    }
                    boolean m_rtn_file_upload_record = true;

                    String m_rtn_chq_number = numbers.substring(0, 6);

                    String m_rtn_bank_code = numbers.substring(6, 10);
                    String m_idndb_bank_master_file = "";

                    String m_rtn_branch_code = numbers.substring(10, 13);
                    String m_idndb_branch_master_file = "";

                    String m_rtn_ndb_acc = "10" + numbers.substring(13, 23);

                    m_strQry = "select * from ndb_return_file_upload where rtn_bank_code='" + m_rtn_bank_code + "' and rtn_branch_code='" + m_rtn_branch_code + "' and rtn_chq_number='" + m_rtn_chq_number + "' and rtn_file_upload_date ='" + _system_date + "'";
                    _rs = _stmnt.executeQuery(m_strQry);

                    if (_rs.next()) {
                        m_rtn_file_upload_record = false;
                        log.info("Error occured in return file upload process , return chequq record already exist in the table. m_rtn_chq_number : " + m_rtn_chq_number + ", rtn_bank_code : " + m_rtn_bank_code + " , m_rtn_branch_code : " + m_rtn_branch_code + ".");

                    }

                    m_strQry = "select * from ndb_bank_master_file where bank_code='" + m_rtn_bank_code + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        m_idndb_bank_master_file = _rs.getString("idndb_bank_master_file");

                    }

                    m_strQry = "select * from ndb_branch_master_file where branch_id='" + m_rtn_branch_code + "' and idndb_bank_master_file='" + m_idndb_bank_master_file + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        m_idndb_branch_master_file = _rs.getString("idndb_branch_master_file");

                    }
                    if (m_rtn_file_upload_record) {
                        m_strQry = "insert into ndb_return_file_upload (rtn_chq_number,"
                                + "rtn_bank_code,"
                                + "idndb_bank_master_file,"
                                + "rtn_branch_code,"
                                + "idndb_branch_master_file,"
                                + "rtn_ndb_acc,"
                                + "rtn_chq_amu,"
                                + "rtn_bank_code_send,"
                                + "idndb_bank_master_file_send,"
                                + "rtn_branch_code_send,"
                                + "idndb_branch_master_file_send,"
                                + "rtn_date,"
                                + "rtn_reject_code,"
                                + "rtn_status,"
                                + "rtn_fileid,"
                                + "rtn_file_upload_date,"
                                + "creat_by,"
                                + "creat_date_time,"
                                + "mod_by,"
                                + "mod_date_time) values("
                                + "'" + m_rtn_chq_number + "',"
                                + "'" + m_rtn_bank_code + "',"
                                + "'" + m_idndb_bank_master_file + "',"
                                + "'" + m_rtn_branch_code + "',"
                                + "'" + m_idndb_branch_master_file + "',"
                                + "'" + m_rtn_ndb_acc + "',"
                                + "'0.00',"
                                + "'7214',"
                                + "'900',"
                                + "'',"
                                + "'',"
                                + "'',"
                                + "'',"
                                + "'ACTIVE',"
                                + "'" + m_ReturnFileID + "',"
                                + "'" + _system_date + "',"
                                + "'" + m_user_id + "',"
                                + "now(),"
                                + "'" + m_user_id + "',"
                                + "now())";

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                    }
                    m_strQry = "select * from ndb_day_today_process where day_today_process_name='RETRNFILEUPLD' and processed_date='" + _system_date + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    boolean ret_upld = true;
                    if (_rs.next()) {
                        ret_upld = false;

                    }

                    if (ret_upld) {
                        m_strQry = "insert into ndb_day_today_process (day_today_process_name,"
                                + "processed_date,"
                                + "processed_by"
                                + ",processed_time"
                                + ""
                                + ") values("
                                + "'RETRNFILEUPLD',"
                                + "'" + _system_date + "',"
                                + "'" + m_user_id + "',"
                                + "now())";
                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert user-roles");
                        }

                    }

                }
                in.close();

            }
            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end of upod");
            }
            success = "success";
        } catch (Exception e) {
            success = success;
            abortConnection(_currentCon);
            log.error("Error occured in return file upload process, Exception" + e);
        } finally {
            try {
                if (m_rs != null) {
                    m_rs.close();
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

    public String uploadHolidayFileData(JSONObject prm_obj) {
        String success = "false-error=";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        Parameters para = new Parameters();

        String m_strQry = "";
        String m_user_id = "";
        String m_HolidayFileID = "";
        String m_HolidayFilePath = "";
        int idgefu_upload = 0;
        ResultSet m_rs = null;

        try {
            m_user_id = prm_obj.getString("user_id");
            m_HolidayFileID = prm_obj.getString("HolidayFileID");
            m_HolidayFilePath = prm_obj.getString("HolidayFilePath");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }

            FileInputStream fstream = new FileInputStream(m_HolidayFilePath);

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String m_recived_line = "";
            _stmnt = _currentCon.createStatement();
            int i = 1;
            while ((strLine = br.readLine()) != null) {
                i++;
                m_recived_line = strLine.trim();
                boolean m_sexist_data = false;

                fileUploadDAO fuldDAO = new fileUploadDAO();
                if (fuldDAO.isThisDateValid(m_recived_line, "dd/MM/yyyy")) {

                    String[] date_spliter = m_recived_line.split("/");
                    int m_year = Integer.parseInt(date_spliter[2]);
                    int m_month = Integer.parseInt(date_spliter[1]) - 1;
                    int m_day = Integer.parseInt(date_spliter[0]);
                    Calendar ced = Calendar.getInstance();

                    ced.set(Calendar.YEAR, m_year); // set the year
                    ced.set(Calendar.MONTH, m_month); // set the month
                    ced.set(Calendar.DAY_OF_MONTH, m_day);
                    ced.add(Calendar.DATE, 0);
                    ced.getTime();
                    if (ced.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        success = "1104";
                        throw new Exception("Holiday Is WEEKEND");
                    }
                    if (ced.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        success = "1104";
                        throw new Exception("Holiday Is WEEKEND");
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                    String _system_date = new comboDAO().getSystemDate();
                    Date date1 = sdf.parse(_system_date);
                    Date date2 = sdf.parse(m_recived_line);

                    if (date1.compareTo(date2) > 0) {
                        success = "1102";
                        throw new Exception("Holiday Is a back date");
                    }
                    if (date1.compareTo(date2) == 0) {
                        success = "1103";
                        throw new Exception("Holiday Is Tody");
                    }

                    m_strQry = "select * from ndb_holiday_marker where ndb_holiday='" + m_recived_line + "' and ndb_holiday_status='ACTIVE'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        success = "1101";
                        throw new Exception(i + " Holiday already marked in the system.");
                    }

                    m_strQry = "select * from ndb_holiday_marker where ndb_holiday='" + m_recived_line + "'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        m_sexist_data = true;

                    }

                    if (!m_sexist_data) {
                        m_strQry = "insert into ndb_holiday_marker (ndb_holiday,ndb_holiday_status,ndb_holiday_approval,ndb_holiday_creat_by,ndb_holiday_creat_date,ndb_holiday_mod_by,ndb_holiday_mod_date) values("
                                + "'" + m_recived_line + "',"
                                + "'ACTIVE',"
                                + "'UN-AUTHORIZED',"
                                + "'" + m_user_id + "',"
                                + "now(),"
                                + "'" + m_user_id + "',"
                                + "now())";

                        if (_stmnt.executeUpdate(m_strQry) <= 0) {
                            throw new Exception("Error Occured in insert holiday data");
                        }

                    }

                } else {
                    throw new Exception("Invalid date found at line" + i);
                }

            }

            in.close();

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end of upod");
            }
            success = "success";
        } catch (Exception e) {
            abortConnection(_currentCon);
            log.error("Error occured in holiday file upload process, Exception" + e);
        } finally {
            try {
                if (m_rs != null) {
                    m_rs.close();
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

    public boolean isThisDateValid(String dateToValidate, String dateFromat) {

        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean isNumeric(String str, String type) {
        try {
            if (type.equalsIgnoreCase("D")) {
                double d = Double.parseDouble(str);
            }
            if (type.equalsIgnoreCase("I")) {
                if (!str.matches("[0-9]+")) {
                    return false;
                }
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
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
