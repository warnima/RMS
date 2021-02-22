/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reports;

import DBAutoFillBeans.comboDAO;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import ndb.connection.ConnectionPool;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.apache.logging.log4j.LogManager;
import utility.Parameters;

/**
 *
 * @author Madhawa_4799
 */
public class depositSLIPS {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(depositSLIPS.class.getName());
    static ResourceBundle rb = ResourceBundle.getBundle("System", Locale.getDefault());
    static ConnectionPool _connectionPool;
    static Connection _currentCon = null;
    static ResultSet _rs = null;
    static Exception _exception;
    static Statement _stmnt;
    static Statement _stmnt2;

    public static void main(String[] args) {
        depositSLIPS ds = new depositSLIPS();
        //ds.downloadDeposiSlips("02/05/2016");
    }

    public String downloadDeposiSlips(String m_booked_date, String idndb_customer_define_seller_id, String PDCBulkUploadFileRFData, String PDCBulkUploadFileCWData) {

        ResultSet m_rs1 = null;
        String m_strsql = "";
        String _cust_name = "";
        String _account_number = "";
        String _value_date = "";
        String _currency = "";
        String _chq_number = "";
        String _bank_branch = "";
        String _idndb_pdc_txn_master = "";
        String _idndb_customer_define_seller_id = "";
        String _amount = "0.00";
        String _bank_code = "0000";
        String _branch_code = "000";
        String m_strReportPath = "";
        String m_retvalue = "fail";
        String gene_filename = "";
        String m_strFoldePath = "";

        ResourceBundle rb = ResourceBundle.getBundle("System", Locale.getDefault());

        Parameters param = new Parameters();

        Map parameters = new HashMap();

        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            String modi_quier = "";

            if (!idndb_customer_define_seller_id.equals("all")) {
                modi_quier = "and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
            }

            if (!(PDCBulkUploadFileRFData == null)) {
                m_strsql = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_chq_status_auth ='AUTHORIZED' and pdc_booking_date='" + m_booked_date + "' and pdc_req_financing='RF' " + modi_quier;

            }
            if (!(PDCBulkUploadFileCWData == null)) {
                m_strsql = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_chq_status_auth ='AUTHORIZED' and pdc_booking_date='" + m_booked_date + "' and pdc_req_financing='CW'  " + modi_quier;

            }

            m_rs1 = _stmnt.executeQuery(m_strsql);
            while (m_rs1.next()) {
                log.info("");
                _idndb_customer_define_seller_id = m_rs1.getString("idndb_customer_define_seller_id");
                String idndb_customer_define_seller_idndb_customer_define = "";
                String cust_id = "";
//                String pdc_req_financing = m_rs1.getString("pdc_req_financing");
//                String ndb_customer_define_rec_finance_acc_num = "";
//                String ndb_customer_define_rec_finance_curr_ac = "";
                _account_number = m_rs1.getString("cust_account_number");
                _bank_code = m_rs1.getString("pdc_bank_code");
                _branch_code = m_rs1.getString("pdc_branch_code");

                String m_strQry = "";
//                m_strQry = "SELECT\n"
//                        + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
//                        + "     ndb_cust_prod_map.`idndb_customer_define` AS ndb_cust_prod_map_idndb_customer_define,\n"
//                        + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
//                        + "     ndb_customer_define.`rec_finance_acc_num` AS ndb_customer_define_rec_finance_acc_num,\n"
//                        + "     ndb_customer_define.`rec_finance_curr_ac` AS ndb_customer_define_rec_finance_curr_ac\n"
//                        + "FROM\n"
//                        + "     `ndb_customer_define` ndb_customer_define INNER JOIN `ndb_cust_prod_map` ndb_cust_prod_map ON ndb_customer_define.`idndb_customer_define` = ndb_cust_prod_map.`idndb_customer_define` where ndb_cust_prod_map.`idndb_cust_prod_map`='" + _idndb_customer_define_seller_id + "'";
//                _rs = _stmnt2.executeQuery(m_strQry);
//                if (_rs.next()) {
//                    ndb_customer_define_rec_finance_acc_num = _rs.getString("ndb_customer_define_rec_finance_acc_num");
//                    ndb_customer_define_rec_finance_curr_ac = _rs.getString("ndb_customer_define_rec_finance_curr_ac");
//                }
//                if (pdc_req_financing.equals("CW")) {
//                    _account_number = ndb_customer_define_rec_finance_curr_ac;
//                }
//                if (pdc_req_financing.equals("RF")) {
//                    _account_number = ndb_customer_define_rec_finance_acc_num;
//                }

                m_strQry = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + _idndb_customer_define_seller_id + "'";
                _rs = _stmnt2.executeQuery(m_strQry);
                if (_rs.next()) {
                    idndb_customer_define_seller_idndb_customer_define = _rs.getString("idndb_customer_define");
                }
                m_strQry = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
                _rs = _stmnt2.executeQuery(m_strQry);
                if (_rs.next()) {
                    cust_id = _rs.getString("cust_id");
                }

                String chque_name_date = m_rs1.getString("pdc_value_date").replace("/", "");
                DecimalFormat df = new DecimalFormat("#,###.00");

                String[] date_rec = m_rs1.getString("pdc_value_date").split("/");
                String day = date_rec[0];
                String month = date_rec[1];
                String year = date_rec[2];
                _idndb_pdc_txn_master = m_rs1.getString("idndb_pdc_txn_master");

                _cust_name = m_rs1.getString("cust_name");

                _value_date = day + "/" + month + "/" + year;
                String m_val_date_slip_format = year + month + day;

                _currency = "LKR";
                _chq_number = m_rs1.getString("pdc_chq_number");
                String bank_name = m_rs1.getString("pdc_bank_code");
                String formatted_bank_name = bank_name;
                String fileName = "";
                if (!idndb_customer_define_seller_id.equals("all")) {
                    fileName = m_booked_date.replace("/", ".") + cust_id;
                } else {
                    fileName = m_booked_date.replace("/", ".") + "ALLSELLERS";
                }

//                if (bank_name.length() <= 4) {
//                    formatted_bank_name = bank_name;
//                } else {
//                    formatted_bank_name = bank_name.substring(0, 4);
//                }
                String branch_name = m_rs1.getString("pdc_branch_code");
                String formatted_branch_name = " / " + branch_name;
//                if (branch_name.length() <= 6) {
//                    formatted_branch_name = branch_name;
//                } else {
//                    formatted_branch_name = branch_name.substring(0, 6);
//                }

                _bank_branch = formatted_bank_name + formatted_branch_name;
                _amount = df.format(m_rs1.getDouble("pdc_chq_amu"));

                log.info("slip printing request recieved " + _idndb_pdc_txn_master + ", Cust Name :" + _cust_name + ", _account_number:" + _account_number + ", _value_date:" + _value_date + ", _chq_number:" + _chq_number);

                gene_filename = m_val_date_slip_format + " " + cust_id + " " + _chq_number + " " + _bank_code + " " + _branch_code;

                parameters.put("_idndb_pdc_txn_master", _idndb_pdc_txn_master);
                parameters.put("_cust_name", _cust_name);
                parameters.put("_account_number", _account_number.replace("", "  "));
                parameters.put("_value_date", _value_date);
                parameters.put("_currency", _currency.replace("", "  "));
                parameters.put("_chq_number", _chq_number);
                parameters.put("_bank_branch", _bank_branch);
                parameters.put("_amount", _amount);

                m_retvalue = "success";

                String m_strExtention = ".pdf";
                m_strReportPath = rb.getString("ReportsPath") + File.separator + "NDBCHQDEPOSITSLIP.jasper";
                m_strFoldePath = rb.getString("DepositSlipsDir") + File.separator + fileName;

                File file2 = new File(m_strFoldePath);
                if (!file2.exists()) {
                    if (file2.mkdir()) {
                        System.out.println("Directory is created!");
                    } else {
                        System.out.println("Failed to create directory!");
                    }
                }

                File file = new File(m_strFoldePath + "\\" + gene_filename + m_strExtention);

                File reportFile = new File(m_strReportPath);
                if (!reportFile.exists()) {
                    throw new Exception("File Not Found.");
                }
                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, _currentCon);
                JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());

            }
            m_retvalue = "success=" + m_strFoldePath;

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

        } catch (Exception e) {
            e.printStackTrace();

        }

        return m_retvalue;
    }

    public String downloadPhysicalVerificationReport(String m_verification_date) {

        ResultSet m_rs1 = null;
        String m_strsql = "";
        String m_total_chq_count = "";
        String m_totak_pdc_chq_amount = "";
        String m_strReportPath = "";
        String m_retvalue = "fail";
        String gene_filename = "";
        String m_strFoldePath = "";
        comboDAO cmbDAO = new comboDAO();
        String _system_date = cmbDAO.getSystemDate();

        ResourceBundle rb = ResourceBundle.getBundle("System", Locale.getDefault());
        Parameters param = new Parameters();
        Map parameters = new HashMap();

        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        DecimalFormat df = new DecimalFormat("#,###.00");

        try {
            _stmnt = _currentCon.createStatement();
            m_strsql = "select SUM(pdc_chq_amu) as total_pdc_chq_amu, COUNT(idndb_pdc_txn_master) as pdc_chq_count from ndb_pdc_txn_master where pdc_chq_status = 'ACTIVE' and pdc_chq_status_auth = 'AUTHORIZED' and STR_TO_DATE(pdc_value_date, '%d/%m/%Y') >= STR_TO_DATE('" + _system_date + "', '%d/%m/%Y') and STR_TO_DATE(pdc_booking_date, '%d/%m/%Y') != STR_TO_DATE('" + _system_date + "', '%d/%m/%Y')";
            m_rs1 = _stmnt.executeQuery(m_strsql);
            if (m_rs1.next()) {
                m_total_chq_count = m_rs1.getString("pdc_chq_count");
                m_totak_pdc_chq_amount = m_rs1.getString("total_pdc_chq_amu");
                if (m_total_chq_count == null) {
                    m_total_chq_count = "0";
                }
                if (m_totak_pdc_chq_amount == null) {
                    m_totak_pdc_chq_amount = "0.00";
                }
            }

            parameters.put("REPORT_VERFY_DATE", m_verification_date);
            parameters.put("REPORT_PORTFOIO_CHQ_CNT", m_total_chq_count);
            parameters.put("REPORT_PORTFOIO_CHQ_AMNT", df.format(Double.parseDouble(m_totak_pdc_chq_amount)));

            m_retvalue = "success";

            String m_strExtention = ".pdf";
            m_strReportPath = rb.getString("ReportsPath") + File.separator + "PDC Physical Verification Confirmation report.jasper";
            m_strFoldePath = rb.getString("PDCVerificationReport") + File.separator + m_verification_date.replace("/", "") + "PDCVERIFICATIONREPORT";
            gene_filename = m_verification_date.replace("/", "") + "PDCVERIFICATIONREPORT";
            File file2 = new File(m_strFoldePath);
            if (!file2.exists()) {
                if (file2.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }

            File file = new File(m_strFoldePath + "\\" + gene_filename + m_strExtention);

            File reportFile = new File(m_strReportPath);
            if (!reportFile.exists()) {
                throw new Exception("File Not Found.");
            }
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, _currentCon);
            JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());

            m_retvalue = "success=" + m_strFoldePath;

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

        } catch (Exception e) {
            abortConnection(_currentCon);
            log.info("error occured in downloading report : error " + e);
        }

        return m_retvalue;
    }

    public String downloadUNAUTHREPORT(String system_date, String idndb_customer_define_seller_id, String pdc_type, String user, String txn_bulk) {

        ResultSet m_rs1 = null;
        String m_strsql = "";

        String m_strReportPath = "";
        String m_retvalue = "fail";
        String gene_filename = "";
        String fileName = "";
        String seller_id = "";
        String m_strFoldePath = "";

        Parameters param = new Parameters();

        Map parameters = new HashMap();

        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        try {
            _stmnt = _currentCon.createStatement();
            seller_id = "";
            fileName = system_date.replace("/", "") + " " + idndb_customer_define_seller_id.toUpperCase();
            gene_filename = system_date.replace("/", "") + " " + idndb_customer_define_seller_id.toUpperCase();

            if (!(idndb_customer_define_seller_id.equals("all") || idndb_customer_define_seller_id.equals(""))) {
                m_strsql = "SELECT\n"
                        + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
                        + "     ndb_cust_prod_map.`idndb_customer_define` AS ndb_cust_prod_map_idndb_customer_define,\n"
                        + "     ndb_cust_prod_map.`prod_relationship_key_seller` AS ndb_cust_prod_map_prod_relationship_key_seller,\n"
                        + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
                        + "     ndb_customer_define.`cust_id` AS ndb_customer_define_cust_id,\n"
                        + "     ndb_customer_define.`cust_name` AS ndb_customer_define_cust_name\n"
                        + "FROM\n"
                        + "     `ndb_customer_define` ndb_customer_define INNER JOIN `ndb_cust_prod_map` ndb_cust_prod_map ON ndb_customer_define.`idndb_customer_define` = ndb_cust_prod_map.`idndb_customer_define` where ndb_cust_prod_map.`idndb_cust_prod_map`='" + idndb_customer_define_seller_id + "'";

                m_rs1 = _stmnt.executeQuery(m_strsql);
                if (m_rs1.next()) {
                    seller_id = m_rs1.getString("ndb_customer_define_cust_id");
                }
                fileName = system_date.replace("/", "") + " " + seller_id;
                gene_filename = system_date.replace("/", "") + " " + seller_id;

            } else {
                fileName = system_date.replace("/", "") + " " + "ALLSELLERS";
                gene_filename = system_date.replace("/", "") + " " + "ALLSELLERS";

            }

            if (txn_bulk.equals("INPUTTER")) {

                if (idndb_customer_define_seller_id.equals("all") || idndb_customer_define_seller_id.equals("")) {

                    parameters.put("printed_by", user);
                    parameters.put("system_date", system_date);
                    parameters.put("pdc_type", pdc_type);
                    m_strReportPath = rb.getString("ReportsPath") + File.separator + "pdc_unauth_report_inputter_all_sellers.jasper";

                } else {

                    parameters.put("seller_id", idndb_customer_define_seller_id);
                    parameters.put("printed_by", user);
                    parameters.put("system_date", system_date);
                    parameters.put("pdc_type", pdc_type);
                    m_strReportPath = rb.getString("ReportsPath") + File.separator + "pdc_unauth_report_inputter.jasper";

                }

            } else {
                if (idndb_customer_define_seller_id.equals("all") || idndb_customer_define_seller_id.equals("")) {
                    if (txn_bulk.equals("YES")) {
                        parameters.put("printed_by", user);
                        parameters.put("system_date", system_date);
                        parameters.put("pdc_type", pdc_type);
                        m_strReportPath = rb.getString("ReportsPath") + File.separator + "pdc_unauth_report_bulk_all_sellers.jasper";

                    } else {

                        parameters.put("printed_by", user);
                        parameters.put("system_date", system_date);
                        parameters.put("pdc_type", pdc_type);
                        m_strReportPath = rb.getString("ReportsPath") + File.separator + "pdc_unauth_report_manual_all_sellers.jasper";

                    }

                } else {
                    if (txn_bulk.equals("YES")) {
                        parameters.put("seller_id", idndb_customer_define_seller_id);
                        parameters.put("printed_by", user);
                        parameters.put("system_date", system_date);
                        parameters.put("pdc_type", pdc_type);
                        m_strReportPath = rb.getString("ReportsPath") + File.separator + "pdc_unauth_report_bulk_upld.jasper";

                    } else {
                        parameters.put("seller_id", idndb_customer_define_seller_id);
                        parameters.put("printed_by", user);
                        parameters.put("system_date", system_date);
                        parameters.put("pdc_type", pdc_type);
                        m_strReportPath = rb.getString("ReportsPath") + File.separator + "pdc_unauth_report_single.jasper";

                    }

                }
            }

            String m_strExtention = ".pdf";

            m_strFoldePath = rb.getString("ReportDownloadDir") + File.separator + fileName;

            File file2 = new File(m_strFoldePath);
            if (!file2.exists()) {
                if (file2.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }

            File file = new File(m_strFoldePath + "\\" + gene_filename + m_strExtention);

            File reportFile = new File(m_strReportPath);
            if (!reportFile.exists()) {
                throw new Exception("File Not Found.");
            }
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, _currentCon);
            JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());

            m_retvalue = "success=" + m_strFoldePath;

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

        } catch (Exception e) {
            e.printStackTrace();

        }

        return m_retvalue;
    }

    public String downloadPortFolioReport(String system_date, String user, String file_extention, String txn_auth_status) {

        ResultSet m_rs1 = null;
        String m_strsql = "";

        String m_strReportPath = "";
        String m_retvalue = "fail";
        String gene_filename = "";
        String fileName = "";
        String seller_id = "";
        String m_strFoldePath = "";

        Parameters param = new Parameters();

        Map parameters = new HashMap();

        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        try {
            _stmnt = _currentCon.createStatement();
            seller_id = "";
            SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
            String date = sdf.format(new Date());

            fileName = system_date.replace("/", "") + " " + "PORTFOLIO" + " " + date;
            gene_filename = system_date.replace("/", "") + " " + "PORTFOLIO";

            // parameters.put("printed_by", user);
            // parameters.put("system_date", system_date);
            // parameters.put("txn_auth_status", txn_auth_status);
            parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
            m_strReportPath = rb.getString("ReportsPath") + File.separator + "new_portfolio.jasper";

            String m_strExtention = file_extention;

            m_strFoldePath = rb.getString("ReportDownloadDir") + File.separator + fileName;

            File file2 = new File(m_strFoldePath);
            if (!file2.exists()) {
                if (file2.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }

            File file = new File(m_strFoldePath + "\\" + gene_filename + m_strExtention);

            File reportFile = new File(m_strReportPath);
            if (!reportFile.exists()) {
                throw new Exception("File Not Found.");
            }
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, _currentCon);
            if (file_extention.equalsIgnoreCase(".pdf")) {
                JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());
            } else if (file_extention.equalsIgnoreCase(".html")) {
                JasperExportManager.exportReportToHtmlFile(jasperPrint, file.getPath());
            } else if (file_extention.equalsIgnoreCase(".xls")) {
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file.getPath()));
                SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
                configuration.setOnePagePerSheet(true);
                configuration.setDetectCellType(true);
                configuration.setCollapseRowSpan(false);
                exporter.setConfiguration(configuration);
                exporter.exportReport();
            } else if (file_extention.equalsIgnoreCase(".csv")) {
                JRCsvExporter exporterCSV = new JRCsvExporter();
                exporterCSV.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                exporterCSV.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, file.getPath());
                exporterCSV.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
                exporterCSV.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
                exporterCSV.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, true);
                exporterCSV.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
                exporterCSV.exportReport();
            }

            m_retvalue = "success=" + m_strFoldePath;

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

        } catch (Exception e) {
            e.printStackTrace();

        }

        return m_retvalue;
    }

    public String downloadCLEARINGAUTHREPORT(String system_date, String idndb_customer_define_seller_id, String pdc_type, String user, String txn_value_date) {

        ResultSet m_rs1 = null;
        String m_strsql = "";

        String m_strReportPath = "";
        String m_retvalue = "fail";
        String gene_filename = "";
        String m_strFoldePath = "";

        Parameters param = new Parameters();

        Map parameters = new HashMap();

        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        try {
            _stmnt = _currentCon.createStatement();

            String fileName = "CHQ sending " + txn_value_date.replace("/", "");
            gene_filename = "CHQ sending " + txn_value_date.replace("/", "");

            if (idndb_customer_define_seller_id.equals("all") || idndb_customer_define_seller_id.equals("")) {

                parameters.put("printed_by", user);
                parameters.put("system_date", system_date);
                parameters.put("pdc_type", pdc_type);
                parameters.put("value_date", txn_value_date);
                m_strReportPath = rb.getString("ReportsPath") + File.separator + "pdc_sending_report_all_sellers.jasper";

            } else {

                parameters.put("seller_id", idndb_customer_define_seller_id);
                parameters.put("printed_by", user);
                parameters.put("system_date", system_date);
                parameters.put("pdc_type", pdc_type);
                parameters.put("value_date", txn_value_date);
                m_strReportPath = rb.getString("ReportsPath") + File.separator + "pdc_sending_report_seller_wise.jasper";

            }

            String m_strExtention = ".pdf";

            m_strFoldePath = rb.getString("ReportDownloadDir") + File.separator + fileName;

            File file2 = new File(m_strFoldePath);
            if (!file2.exists()) {
                if (file2.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }

            File file = new File(m_strFoldePath + "\\" + gene_filename + m_strExtention);

            File reportFile = new File(m_strReportPath);
            if (!reportFile.exists()) {
                throw new Exception("File Not Found.");
            }
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, _currentCon);
            JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());

            m_retvalue = "success=" + m_strFoldePath;

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

        } catch (Exception e) {
            e.printStackTrace();

        }

        return m_retvalue;
    }

    public String downloadChqSendingDeposiSlips(String m_booked_date, String idndb_customer_define_seller_id, String PDCBulkUploadFileRFData, String PDCBulkUploadFileCWData) {

        ResultSet m_rs1 = null;
        String m_strsql = "";
        String _cust_name = "";
        String _account_number = "";
        String _value_date = "";
        String _currency = "";
        String _chq_number = "";
        String _bank_branch = "";
        String _idndb_pdc_txn_master = "";
        String _idndb_customer_define_seller_id = "";
        String _amount = "0.00";
        String _bank_code = "0000";
        String _branch_code = "000";
        String m_strReportPath = "";
        String m_retvalue = "fail";
        String gene_filename = "";
        String m_strFoldePath = "";

        Parameters param = new Parameters();
        ResourceBundle rb = ResourceBundle.getBundle("System", Locale.getDefault());

        Map parameters = new HashMap();

        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        try {
            _stmnt = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();
            String modi_quier = "";
            if (!idndb_customer_define_seller_id.equals("all")) {
                modi_quier = "and idndb_customer_define_seller_id='" + idndb_customer_define_seller_id + "'";
            }

            if (!(PDCBulkUploadFileRFData == null)) {
                m_strsql = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_chq_status_auth ='AUTHORIZED' and pdc_value_date='" + m_booked_date + "' and pdc_req_financing='RF' " + modi_quier;

            }
            if (!(PDCBulkUploadFileCWData == null)) {
                m_strsql = "SELECT * FROM ndb_pdc_txn_master where pdc_chq_status='ACTIVE' and pdc_chq_status_auth ='AUTHORIZED' and pdc_value_date='" + m_booked_date + "' and pdc_req_financing='CW'  " + modi_quier;

            }
            m_rs1 = _stmnt.executeQuery(m_strsql);
            while (m_rs1.next()) {

                _idndb_customer_define_seller_id = m_rs1.getString("idndb_customer_define_seller_id");
                String idndb_customer_define_seller_idndb_customer_define = "";
                _account_number = m_rs1.getString("cust_account_number");
                _bank_code = m_rs1.getString("pdc_bank_code");
                _branch_code = m_rs1.getString("pdc_branch_code");
                String cust_id = "";
//                String pdc_req_financing = "";

                String m_strQry = "";

//                String ndb_customer_define_rec_finance_acc_num = "";
//                String ndb_customer_define_rec_finance_curr_ac = "";
//
//                m_strQry = "SELECT\n"
//                        + "     ndb_cust_prod_map.`idndb_cust_prod_map` AS ndb_cust_prod_map_idndb_cust_prod_map,\n"
//                        + "     ndb_cust_prod_map.`idndb_customer_define` AS ndb_cust_prod_map_idndb_customer_define,\n"
//                        + "     ndb_customer_define.`idndb_customer_define` AS ndb_customer_define_idndb_customer_define,\n"
//                        + "     ndb_customer_define.`rec_finance_acc_num` AS ndb_customer_define_rec_finance_acc_num,\n"
//                        + "     ndb_customer_define.`rec_finance_curr_ac` AS ndb_customer_define_rec_finance_curr_ac\n"
//                        + "FROM\n"
//                        + "     `ndb_customer_define` ndb_customer_define INNER JOIN `ndb_cust_prod_map` ndb_cust_prod_map ON ndb_customer_define.`idndb_customer_define` = ndb_cust_prod_map.`idndb_customer_define` where ndb_cust_prod_map.`idndb_cust_prod_map`='" + _idndb_customer_define_seller_id + "'";
//                _rs = _stmnt2.executeQuery(m_strQry);
//                if (_rs.next()) {
//                    ndb_customer_define_rec_finance_acc_num = _rs.getString("ndb_customer_define_rec_finance_acc_num");
//                    ndb_customer_define_rec_finance_curr_ac = _rs.getString("ndb_customer_define_rec_finance_curr_ac");
//                }
//                if (pdc_req_financing.equals("CW")) {
//                    _account_number = ndb_customer_define_rec_finance_curr_ac;
//                }
//                if (pdc_req_financing.equals("RF")) {
//                    _account_number = ndb_customer_define_rec_finance_acc_num;
//                }
                m_strQry = "select * from ndb_cust_prod_map where idndb_cust_prod_map='" + _idndb_customer_define_seller_id + "'";
                _rs = _stmnt2.executeQuery(m_strQry);
                if (_rs.next()) {
                    idndb_customer_define_seller_idndb_customer_define = _rs.getString("idndb_customer_define");
                }
                m_strQry = "select * from ndb_customer_define where idndb_customer_define='" + idndb_customer_define_seller_idndb_customer_define + "'";
                _rs = _stmnt2.executeQuery(m_strQry);
                if (_rs.next()) {
                    cust_id = _rs.getString("cust_id");
                }

                log.info("");
                String chque_name_date = m_rs1.getString("pdc_value_date").replace("/", ".");
                DecimalFormat df = new DecimalFormat("#,###.00");

                String[] date_rec = m_rs1.getString("pdc_value_date").split("/");
                String day = date_rec[0];
                String month = date_rec[1];
                String year = date_rec[2];
                _idndb_pdc_txn_master = m_rs1.getString("idndb_pdc_txn_master");
                _cust_name = m_rs1.getString("cust_name");
                _value_date = day + "/" + month + "/" + year;
                String m_val_date_slip_format = year + month + day;
                _currency = "LKR";
                _chq_number = m_rs1.getString("pdc_chq_number");
                String bank_name = m_rs1.getString("pdc_bank_code");
                String formatted_bank_name = bank_name;
                String fileName = "";
                if (!idndb_customer_define_seller_id.equals("all")) {
                    fileName = m_booked_date.replace("/", ".") + "_" + cust_id;
                } else {
                    fileName = m_booked_date.replace("/", ".") + "_" + "ALLSELLERS";
                }

//                if (bank_name.length() <= 4) {
//                    formatted_bank_name = bank_name;
//                } else {
//                    formatted_bank_name = bank_name.substring(0, 4);
//                }
                String branch_name = m_rs1.getString("pdc_branch_code");
                String formatted_branch_name = " / " + branch_name;
//                if (branch_name.length() <= 6) {
//                    formatted_branch_name = branch_name;
//                } else {
//                    formatted_branch_name = branch_name.substring(0, 6);
//                }

                _bank_branch = formatted_bank_name + formatted_branch_name;
                _amount = df.format(m_rs1.getDouble("pdc_chq_amu"));

                log.info("slip printing request recieved " + _idndb_pdc_txn_master + ", Cust Name :" + _cust_name + ", _account_number:" + _account_number + ", _value_date:" + _value_date + ", _chq_number:" + _chq_number);

                gene_filename = m_val_date_slip_format + "_" + cust_id + "_" + _chq_number + "_" + _bank_code + "_" + _branch_code;

                parameters.put("_idndb_pdc_txn_master", _idndb_pdc_txn_master);
                parameters.put("_cust_name", _cust_name);
                parameters.put("_account_number", _account_number.replace("", "  "));
                parameters.put("_value_date", _value_date);
                parameters.put("_currency", _currency.replace("", "  "));
                parameters.put("_chq_number", _chq_number);
                parameters.put("_bank_branch", _bank_branch);
                parameters.put("_amount", _amount);

                m_retvalue = "success";

                String m_strExtention = ".pdf";
                m_strReportPath = rb.getString("ReportsPath") + File.separator + "NDBCHQDEPOSITSLIP.jasper";
                m_strFoldePath = rb.getString("DepositSlipsDir") + File.separator + fileName;

                File file2 = new File(m_strFoldePath);
                if (!file2.exists()) {
                    if (file2.mkdir()) {
                        System.out.println("Directory is created!");
                    } else {
                        System.out.println("Failed to create directory!");
                    }
                }

                File file = new File(m_strFoldePath + "\\" + gene_filename + m_strExtention);

                File reportFile = new File(m_strReportPath);
                if (!reportFile.exists()) {
                    throw new Exception("File Not Found.");
                }
                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, _currentCon);
                JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());

            }
            m_retvalue = "success=" + m_strFoldePath;

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

        } catch (Exception e) {
            e.printStackTrace();

        }

        return m_retvalue;
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
