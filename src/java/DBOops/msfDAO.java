/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBOops;

import DBAutoFillBeans.comboDAO;
import static DBOops.customerDAO._currentCon;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import ndb.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

/**
 *
 * @author Madhawa_4799
 */
public class msfDAO {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(msfDAO.class.getName());
    static ConnectionPool _connectionPool;
    static Connection _currentCon = null;
    private Statement _stmnt = null;
    private Statement _stmnt2 = null;
    private ResultSet _rs = null;
    private Exception _exception;

    public String saveBankData(JSONObject prm_obj) {
        String success = "1000";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_bank_master_file = "";

        String m_Strbank_name = "";
        String m_StrOldbank_name = "";

        String m_Strbank_code = "";
        String m_StrOldbank_code = "";

        String m_Strbank_status = "";
        String m_StrOldbank_status = "";

        String m_strQry = "";

        boolean m_ardy_exist_data = true;

        try {
            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_bank_master_file = prm_obj.getString("idndb_bank_master_file");
            m_Strbank_name = prm_obj.getString("bank_name");
            m_Strbank_code = prm_obj.getString("bank_code");
            m_Strbank_status = prm_obj.getString("bank_status");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            if (m_str_idndb_bank_master_file.equals("") && (!m_Strbank_code.equals(""))) {
                m_strQry = "select * from ndb_bank_master_file where bank_code='" + m_Strbank_code + "' and bank_status='ACTIVE'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1101";
                    throw new Exception("Bank All Ready Exist");
                }

            }

            m_strQry = "select * from ndb_bank_master_file where idndb_bank_master_file='" + m_str_idndb_bank_master_file + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                m_StrOldbank_name = _rs.getString("bank_name");
                m_StrOldbank_code = _rs.getString("bank_code");
                m_StrOldbank_status = _rs.getString("bank_status");
                m_ardy_exist_data = false;
            }

            if (m_ardy_exist_data) {
                m_strQry = "insert into ndb_bank_master_file (bank_name,bank_code,bank_status,bank_approval,bank_create_by,bank_creat_date,bank_mod_by,bank_mod_date) values("
                        + "'" + m_Strbank_name + "',"
                        + "'" + m_Strbank_code + "',"
                        + "'" + m_Strbank_status + "',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now(),"
                        + "'" + m_str_user_id + "',"
                        + "now())";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }
                String max_idndb_bank = "";
                m_strQry = "select MAX(idndb_bank_master_file) as idndb_bank_master_file from ndb_bank_master_file";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    max_idndb_bank = _rs.getString("idndb_bank_master_file");
                }
                m_strQry = "insert into ndb_change_log (ndb_change_log_type,"
                        + "ndb_attached_id,"
                        + "ndb_change"
                        + ",status"
                        + ",creat_by"
                        + ",creat_date"
                        + ""
                        + ") values("
                        + "'DEFINEBANK',"
                        + "'" + max_idndb_bank + "',"
                        + "'1 ) New Bank Registered',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            } else {
                int i = 0;
                String m_condition = "";
                String m_change = "";
                if (!m_StrOldbank_code.equals(m_Strbank_code)) {
                    m_condition = "bank_code='" + m_Strbank_code + "',";
                    m_strQry = "select * from ndb_bank_master_file where bank_code='" + m_Strbank_code + "' and bank_status='ACTIVE'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        success = "1101";
                        throw new Exception("Bank All Ready Exist");
                    }

                }

                if (!m_StrOldbank_name.equals(m_Strbank_name)) {
                    m_condition = m_condition + "bank_name='" + m_Strbank_name + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldbank_name + " BANK NAME CHANGE TO " + m_Strbank_name + "<br>";

                }
                if (!m_StrOldbank_status.equals(m_Strbank_status)) {
                    m_condition = m_condition + "bank_status='" + m_Strbank_status + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldbank_status + " BANK STATUS CHANGE TO " + m_Strbank_status + "<br>";

                }

                if (!m_condition.equals("")) {
                    m_condition = m_condition + "bank_approval='UN-AUTHORIZED',";
                }

                m_strQry = "update ndb_bank_master_file set " + m_condition + " "
                        + "bank_mod_by='" + m_str_user_id + "',"
                        + "bank_mod_date=now()"
                        + " where idndb_bank_master_file='" + m_str_idndb_bank_master_file + "'";

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
                        + "'DEFINEBANK',"
                        + "'" + m_str_idndb_bank_master_file + "',"
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
            log.error("Error occured in saving bank data, Exception"+ e);
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

    public String savepdcChequeVerification(JSONObject prm_obj) {
        String success = "1000";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_pdc_txn_master = "";

        String m_strQry = "";

        boolean m_ardy_exist_data = true;

        try {
            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_pdc_txn_master = prm_obj.getString("idndb_pdc_txn_master");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            comboDAO cmbDAO = new comboDAO();
            String _system_date = cmbDAO.getSystemDate();

            m_strQry = "select * from ndb_pdc_chq_verification where idndb_pdc_txn_master='" + m_str_idndb_pdc_txn_master + "' and pdc_verify_date='" + _system_date + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                success = "1101";
                throw new Exception("PDC cheque verivication already exist...");
            }

            if (m_ardy_exist_data) {
                m_strQry = "insert into ndb_pdc_chq_verification (idndb_pdc_txn_master,pdc_verify_date,pdc_verify_user,pdc_verify_date_time) values("
                        + "'" + m_str_idndb_pdc_txn_master + "',"
                        + "'" + _system_date + "',"
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
            log.error("Error occured in saving pdc cheuq verify data, Exception"+ e);
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

    public String saveHolidayData(JSONObject prm_obj) {
        String success = "1000";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_holiday_marker = "";

        String m_Strndb_holiday = "";
        String m_StrOldndb_holidaye = "";

        String m_Strndb_holiday_status = "";
        String m_StrOldndb_holiday_status = "";

        String m_strQry = "";

        boolean m_ardy_exist_data = true;

        try {
            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_holiday_marker = prm_obj.getString("idndb_holiday_marker");
            m_Strndb_holiday = prm_obj.getString("ndb_holiday");
            m_Strndb_holiday_status = prm_obj.getString("ndb_holiday_status");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
            String _system_date = new comboDAO().getSystemDate();
            Date date1 = sdf.parse(_system_date);
            Date date2 = sdf.parse(m_Strndb_holiday);

            if (date1.compareTo(date2) > 0) {
                success = "1102";
                throw new Exception("Holiday Is a back date");
            }
            if (date1.compareTo(date2) == 0) {
                success = "1103";
                throw new Exception("Holiday Is Tody");
            }

            if (m_str_idndb_holiday_marker.equals("") && (!m_Strndb_holiday.equals(""))) {
                m_strQry = "select * from ndb_holiday_marker where ndb_holiday='" + m_Strndb_holiday + "' and ndb_holiday_status='ACTIVE'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1101";
                    throw new Exception("Holiday All Ready Exist");
                }

            }

            String[] date_spliter = m_Strndb_holiday.split("/");
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

            m_strQry = "select * from ndb_holiday_marker where idndb_holiday_marker='" + m_str_idndb_holiday_marker + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                m_StrOldndb_holiday_status = _rs.getString("idndb_holiday_marker");
                m_ardy_exist_data = false;
            }

            if (m_ardy_exist_data) {
                m_strQry = "insert into ndb_holiday_marker (ndb_holiday,ndb_holiday_status,ndb_holiday_approval,ndb_holiday_creat_by,ndb_holiday_creat_date,ndb_holiday_mod_by,ndb_holiday_mod_date) values("
                        + "'" + m_Strndb_holiday + "',"
                        + "'" + m_Strndb_holiday_status + "',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now(),"
                        + "'" + m_str_user_id + "',"
                        + "now())";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            } else {

                String m_condition = "";
                if (!m_StrOldndb_holiday_status.equals(m_Strndb_holiday_status)) {
                    m_condition = "ndb_holiday_status='" + m_Strndb_holiday_status + "',";
                }

                if (!m_condition.equals("")) {
                    m_condition = m_condition + "ndb_holiday_approval='UN-AUTHORIZED',";
                }

                m_strQry = "update ndb_holiday_marker set " + m_condition + " "
                        + "ndb_holiday_mod_by='" + m_str_user_id + "',"
                        + "ndb_holiday_mod_date=now()"
                        + " where idndb_holiday_marker='" + m_str_idndb_holiday_marker + "'";

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
            log.error("Error occured in saving holiday data, Exception"+ e);
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

    public boolean saveAuthBankData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_bank_master_file = "";
        String m_str_idndb_bank_master_file_bulk = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_bank_master_file_bulk = prm_obj.getString("idndb_bank_master_file");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            String[] parts = m_str_idndb_bank_master_file_bulk.split(",");

            for (int j = 0; j < parts.length; j++) {
                m_str_idndb_bank_master_file = parts[j];
                m_strQry = "update ndb_bank_master_file set bank_approval='AUTHORIZED',"
                        + "bank_mod_by='" + m_str_user_id + "',"
                        + "bank_mod_date=now()"
                        + " where idndb_bank_master_file='" + m_str_idndb_bank_master_file + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                m_strQry = "update ndb_change_log set status='AUTHORIZED',"
                        + "authby='" + m_str_user_id + "',"
                        + "auth_date=now()"
                        + " where ndb_attached_id='" + m_str_idndb_bank_master_file + "' and ndb_change_log_type='DEFINEBANK'";

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
            log.error("Error occured in authorizing bank data, Exception"+ e);
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

    public boolean saveRejectBankData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_bank_master_file = "";
        String m_str_idndb_bank_master_file_bulk = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_bank_master_file_bulk = prm_obj.getString("idndb_bank_master_file");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            String[] parts = m_str_idndb_bank_master_file_bulk.split(",");

            for (int j = 0; j < parts.length; j++) {
                m_str_idndb_bank_master_file = parts[j];
                m_strQry = "update ndb_bank_master_file set bank_approval='REJECTED',"
                        + "bank_mod_by='" + m_str_user_id + "',"
                        + "bank_mod_date=now()"
                        + " where idndb_bank_master_file='" + m_str_idndb_bank_master_file + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }
                m_strQry = "update ndb_change_log set status='REJECTED',"
                        + "authby='" + m_str_user_id + "',"
                        + "auth_date=now()"
                        + " where ndb_attached_id='" + m_str_idndb_bank_master_file + "' and ndb_change_log_type='DEFINEBANK'";

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
            log.error("Error occured in rejecting bank data, Exception"+ e);
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

    public boolean saveAuthHolidayData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_holiday_marker = "";
        String m_idndb_holiday_marker_bulk = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            //  m_str_idndb_holiday_marker = prm_obj.getString("idndb_holiday_marker");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            m_idndb_holiday_marker_bulk = prm_obj.getString("idndb_holiday_marker");
            String[] parts = m_idndb_holiday_marker_bulk.split(",");

            for (int j = 0; j < parts.length; j++) {
                m_str_idndb_holiday_marker = parts[j];

                m_strQry = "update ndb_holiday_marker set ndb_holiday_approval='AUTHORIZED',"
                        + "ndb_holiday_mod_by='" + m_str_user_id + "',"
                        + "ndb_holiday_mod_date=now()"
                        + " where idndb_holiday_marker='" + m_str_idndb_holiday_marker + "'";

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
            log.error("Error occured in authorizing holiday marked data, Exception"+ e);
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

    public boolean saveRejectHolidayData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_holiday_marker = "";
        String m_idndb_holiday_marker_bulk = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            //  m_str_idndb_holiday_marker = prm_obj.getString("idndb_holiday_marker");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            m_idndb_holiday_marker_bulk = prm_obj.getString("idndb_holiday_marker");
            String[] parts = m_idndb_holiday_marker_bulk.split(",");

            for (int j = 0; j < parts.length; j++) {
                m_str_idndb_holiday_marker = parts[j];

                m_strQry = "update ndb_holiday_marker set ndb_holiday_approval='REJECTED',"
                        + "ndb_holiday_mod_by='" + m_str_user_id + "',"
                        + "ndb_holiday_mod_date=now()"
                        + " where idndb_holiday_marker='" + m_str_idndb_holiday_marker + "'";

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
            log.error("Error occured in rejecting holiday marked data, Exception"+ e);
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

    public boolean saveAuthBranchData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_branch_master_file = "";
        String m_str_idndb_branch_master_file_bulk = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_branch_master_file_bulk = prm_obj.getString("idndb_branch_master_file");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            String[] parts = m_str_idndb_branch_master_file_bulk.split(",");

            for (int j = 0; j < parts.length; j++) {
                m_str_idndb_branch_master_file = parts[j];
                m_strQry = "update ndb_branch_master_file set branch_approval='AUTHORIZED',"
                        + "branch_mod_by='" + m_str_user_id + "',"
                        + "branch_mod_date=now()"
                        + " where idndb_branch_master_file='" + m_str_idndb_branch_master_file + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                m_strQry = "update ndb_change_log set status='AUTHORIZED',"
                        + "authby='" + m_str_user_id + "',"
                        + "auth_date=now()"
                        + " where ndb_attached_id='" + m_str_idndb_branch_master_file + "' and ndb_change_log_type='DEFINEBRANCH'";

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
            log.error("Error occured in authorizing branch data, Exception"+ e);
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

    public boolean saveRejectBranchData(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_branch_master_file = "";
        String m_str_idndb_branch_master_file_bulk = "";

        String m_strQry = "";

        try {

            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_branch_master_file_bulk = prm_obj.getString("idndb_branch_master_file");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            String[] parts = m_str_idndb_branch_master_file_bulk.split(",");

            for (int j = 0; j < parts.length; j++) {
                m_str_idndb_branch_master_file = parts[j];
                m_strQry = "update ndb_branch_master_file set branch_approval='REJECTED',"
                        + "branch_mod_by='" + m_str_user_id + "',"
                        + "branch_mod_date=now()"
                        + " where idndb_branch_master_file='" + m_str_idndb_branch_master_file + "'";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }
                m_strQry = "update ndb_change_log set status='REJECTED',"
                        + "authby='" + m_str_user_id + "',"
                        + "auth_date=now()"
                        + " where ndb_attached_id='" + m_str_idndb_branch_master_file + "' and ndb_change_log_type='DEFINEBRANCH'";

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
            log.error("Error occured in rejecting branch data, Exception"+ e);
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

    public String saveBranchData(JSONObject prm_obj) {
        String success = "1000";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_branch_master_file = "";

        String m_Stridndb_bank_master_file = "";
        String m_StrOldidndb_bank_master_file = "";

        String m_Strbrach_id = "";
        String m_StrOldbrach_id = "";

        String m_Strbranch_name = "";
        String m_StrOldbranch_name = "";

        String m_Strbranch_status = "";
        String m_StrOldbranch_status = "";

        String m_strQry = "";

        boolean m_ardy_exist_data = true;

        try {
            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_branch_master_file = prm_obj.getString("idndb_branch_master_file");
            m_Stridndb_bank_master_file = prm_obj.getString("bnkcodecmb");
            m_Strbrach_id = prm_obj.getString("branch_id");
            m_Strbranch_name = prm_obj.getString("branch_name");
            m_Strbranch_status = prm_obj.getString("ch_branch_status");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            if (m_str_idndb_branch_master_file.equals("") && (!m_Strbrach_id.equals(""))) {
                m_strQry = "select * from ndb_branch_master_file where branch_id='" + m_Strbrach_id + "' and idndb_bank_master_file='" + m_Stridndb_bank_master_file + "' and branch_status='ACTIVE'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1101";
                    throw new Exception("Bank All Ready Exist");
                }

            }

            m_strQry = "select * from ndb_branch_master_file where idndb_branch_master_file='" + m_str_idndb_branch_master_file + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                m_StrOldbranch_name = _rs.getString("branch_name");
                m_StrOldbrach_id = _rs.getString("branch_id");
                m_StrOldbranch_status = _rs.getString("branch_status");
                m_StrOldidndb_bank_master_file = _rs.getString("idndb_bank_master_file");
                m_ardy_exist_data = false;
            }

            if (m_ardy_exist_data) {
                m_strQry = "insert into ndb_branch_master_file (branch_id,branch_name,idndb_bank_master_file,branch_status,branch_approval,branch_creat_by,branch_creat_date,branch_mod_by,branch_mod_date) values("
                        + "'" + m_Strbrach_id + "',"
                        + "'" + m_Strbranch_name + "',"
                        + "'" + m_Stridndb_bank_master_file + "',"
                        + "'" + m_Strbranch_status + "',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now(),"
                        + "'" + m_str_user_id + "',"
                        + "now())";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

                String max_idndb_branch_master_file = "";
                m_strQry = "select MAX(idndb_branch_master_file) as idndb_branch_master_file from ndb_branch_master_file";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    max_idndb_branch_master_file = _rs.getString("idndb_branch_master_file");
                }
                m_strQry = "insert into ndb_change_log (ndb_change_log_type,"
                        + "ndb_attached_id,"
                        + "ndb_change"
                        + ",status"
                        + ",creat_by"
                        + ",creat_date"
                        + ""
                        + ") values("
                        + "'DEFINEBRANCH',"
                        + "'" + max_idndb_branch_master_file + "',"
                        + "'1 ) New Branch Registered',"
                        + "'UN-AUTHORIZED',"
                        + "'" + m_str_user_id + "',"
                        + "now())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            } else {
                int i = 0;
                String m_condition = "";
                String m_change = "";
                if (!m_StrOldbranch_status.equals(m_Strbranch_status)) {
                    m_condition = "branch_status='" + m_Strbranch_status + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldbranch_status + " BRANCH STATUS CHANGE TO " + m_Strbranch_status + "<br>";

                }
                if (!m_StrOldbrach_id.equals(m_Strbrach_id)) {
                    m_strQry = "select * from ndb_branch_master_file where branch_id='" + m_Strbrach_id + "' and idndb_bank_master_file='" + m_Stridndb_bank_master_file + "' and branch_status='ACTIVE'";
                    _rs = _stmnt.executeQuery(m_strQry);
                    if (_rs.next()) {
                        success = "1101";
                        throw new Exception("Bank All Ready Exist");
                    }
                    m_condition = m_condition + "branch_id='" + m_Strbrach_id + "',";
                }

                if (!m_StrOldbranch_name.equals(m_Strbranch_name)) {
                    m_condition = m_condition + "branch_name='" + m_Strbranch_name + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldbranch_name + " BRANCH NAME CHANGE TO " + m_Strbranch_name + "<br>";

                }
                if (!m_StrOldidndb_bank_master_file.equals(m_Stridndb_bank_master_file)) {
                    m_condition = m_condition + "idndb_bank_master_file='" + m_Stridndb_bank_master_file + "',";
                    i++;
                    m_change = m_change + i + ")" + m_StrOldidndb_bank_master_file + " BANK CHANGE TO " + m_Stridndb_bank_master_file + "(SYSTEM GENERATED ID)<br>";

                }

                if (!m_condition.equals("")) {
                    m_condition = m_condition + "branch_approval='UN-AUTHORIZED',";
                }

                m_strQry = "update ndb_branch_master_file set " + m_condition + " "
                        + "branch_mod_by='" + m_str_user_id + "',"
                        + "branch_mod_date=now()"
                        + " where idndb_branch_master_file='" + m_str_idndb_branch_master_file + "'";

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
                        + "'DEFINEBRANCH',"
                        + "'" + m_str_idndb_branch_master_file + "',"
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
            log.error("Error occured in saving branch data, Exception"+ e);
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

    public String saveIndsrtyData(JSONObject prm_obj) {

        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_industry_master_file = "";

        String m_Strindustry_id = "";
        String m_StrOldindustry_id = "";

        String m_Strindustry_des = "";
        String m_StrOldindustry_des = "";

        String m_Strindustry_status = "";
        String m_StrOldindustry_status = "";
        String success = "1000";

        String m_strQry = "";

        boolean m_ardy_exist_data = true;

        try {
            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_industry_master_file = prm_obj.getString("idndb_industry_master_file");
            m_Strindustry_id = prm_obj.getString("industry_id");
            m_Strindustry_des = prm_obj.getString("industry_des");
            m_Strindustry_status = prm_obj.getString("industry_status");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            if (m_str_idndb_industry_master_file.equals("") && (!m_Strindustry_id.equals(""))) {
                m_strQry = "select * from ndb_industry_master_file where indus_id='" + m_Strindustry_id + "' and indus_status='ACTIVE'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1101";
                    throw new Exception("Industry All Ready Exist");
                }

            }

            m_strQry = "select * from ndb_industry_master_file where idndb_industry_master_file='" + m_str_idndb_industry_master_file + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                m_StrOldindustry_id = _rs.getString("indus_id");
                m_StrOldindustry_des = _rs.getString("indus_des");
                m_StrOldindustry_status = _rs.getString("indus_status");
                m_ardy_exist_data = false;
            }

            if (m_ardy_exist_data) {
                m_strQry = "insert into ndb_industry_master_file (indus_id,indus_des,indus_status,indus_creat_by,indus_creat_date,indus_mod_by,indus_mod_date) values("
                        + "'" + m_Strindustry_id + "',"
                        + "'" + m_Strindustry_des + "',"
                        + "'" + m_Strindustry_status + "',"
                        + "'" + m_str_user_id + "',"
                        + "now(),"
                        + "'" + m_str_user_id + "',"
                        + "now())";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            } else {

                String m_condition = "";
                m_strQry = "select * from ndb_industry_master_file where indus_id='" + m_Strindustry_id + "' and indus_status='ACTIVE'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1101";
                    throw new Exception("Industry All Ready Exist");
                }

                if (!m_StrOldindustry_id.equals(m_Strindustry_id)) {
                    m_condition = "indus_id='" + m_Strindustry_id + "',";
                }
                if (!m_StrOldindustry_des.equals(m_Strindustry_des)) {
                    m_condition = m_condition + "indus_des='" + m_Strindustry_des + "',";
                }
                if (!m_StrOldindustry_status.equals(m_Strindustry_status)) {
                    m_condition = m_condition + "indus_status='" + m_Strindustry_status + "',";
                }

                m_strQry = "update ndb_industry_master_file set " + m_condition + " "
                        + "indus_mod_by='" + m_str_user_id + "',"
                        + "indus_mod_date=now()"
                        + " where idndb_industry_master_file='" + m_str_idndb_industry_master_file + "'";

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
            log.error("Error occured in saving industry data, Exception"+ e);
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

    public String saveGeoMrketData(JSONObject prm_obj) {
        String success = "1000";
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        String m_str_user_id = "";
        String m_str_idndb_geo_market_master_file = "";

        String m_Strgeo_mrket_id = "";
        String m_StrOldgeo_mrket_id = "";

        String m_Strgeo_mrkrt_des = "";
        String m_StrOldgeo_mrkrt_des = "";

        String m_Strch_geomrket_status = "";
        String m_StrOldch_geomrket_status = "";

        String m_strQry = "";

        boolean m_ardy_exist_data = true;

        try {
            m_str_user_id = prm_obj.getString("user_id");
            m_str_idndb_geo_market_master_file = prm_obj.getString("idndb_geo_market_master_file");
            m_Strgeo_mrket_id = prm_obj.getString("geo_mrket_id");
            m_Strgeo_mrkrt_des = prm_obj.getString("geo_mrkrt_des");
            m_Strch_geomrket_status = prm_obj.getString("ch_geomrket_status");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            if (m_str_idndb_geo_market_master_file.equals("") && (!m_Strgeo_mrket_id.equals(""))) {
                m_strQry = "select * from ndb_geo_market_master_file where geo_market_id='" + m_Strgeo_mrket_id + "' and geo_market_status='ACTIVE'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1101";
                    throw new Exception("Industry All Ready Exist");
                }

            }

            m_strQry = "select * from ndb_geo_market_master_file where idndb_geo_market_master_file='" + m_str_idndb_geo_market_master_file + "'";
            _rs = _stmnt.executeQuery(m_strQry);
            if (_rs.next()) {
                m_StrOldgeo_mrket_id = _rs.getString("geo_market_id");
                m_StrOldgeo_mrkrt_des = _rs.getString("geo_market_desc");
                m_StrOldch_geomrket_status = _rs.getString("geo_market_status");
                m_ardy_exist_data = false;
            }

            if (m_ardy_exist_data) {
                m_strQry = "insert into ndb_geo_market_master_file (geo_market_id,geo_market_desc,geo_market_status,geo_creat_by,geo_creat_date,geo_mod_by,geo_mod_date) values("
                        + "'" + m_Strgeo_mrket_id + "',"
                        + "'" + m_Strgeo_mrkrt_des + "',"
                        + "'" + m_Strch_geomrket_status + "',"
                        + "'" + m_str_user_id + "',"
                        + "now(),"
                        + "'" + m_str_user_id + "',"
                        + "now())";

                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }

            } else {

                String m_condition = "";

                m_strQry = "select * from ndb_geo_market_master_file where geo_market_id='" + m_Strgeo_mrket_id + "' and geo_market_status='ACTIVE'";
                _rs = _stmnt.executeQuery(m_strQry);
                if (_rs.next()) {
                    success = "1101";
                    throw new Exception("Industry All Ready Exist");
                }
                if (!m_StrOldgeo_mrket_id.equals(m_Strgeo_mrket_id)) {
                    m_condition = "geo_market_id='" + m_Strgeo_mrket_id + "',";
                }
                if (!m_StrOldgeo_mrkrt_des.equals(m_Strgeo_mrkrt_des)) {
                    m_condition = m_condition + "geo_market_desc='" + m_Strgeo_mrkrt_des + "',";
                }
                if (!m_StrOldch_geomrket_status.equals(m_Strch_geomrket_status)) {
                    m_condition = m_condition + "geo_market_status='" + m_Strch_geomrket_status + "',";
                }

                m_strQry = "update ndb_geo_market_master_file set " + m_condition + " "
                        + "geo_mod_by='" + m_str_user_id + "',"
                        + "geo_mod_date=now()"
                        + " where idndb_geo_market_master_file='" + m_str_idndb_geo_market_master_file + "'";

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
            log.error("Error occured in saving geo market data, Exception"+ e);
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
