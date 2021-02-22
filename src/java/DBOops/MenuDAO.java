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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import ndb.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

/**
 *
 * @author Madhawa
 */
public class MenuDAO {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(MenuDAO.class.getName());
    static ConnectionPool _connectionPool;
    static Connection _currentCon = null;
    private Statement _stmnt = null;
    private Statement _stmnt1 = null;
    private Statement _stmnt2 = null;
    private ResultSet _rs = null;
    private ResultSet _rs1 = null;
    private ResultSet _rs2 = null;
    private ResultSet _rs3 = null;
    private Exception _exception;

    public boolean saveRoleForms(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        String m_strQry = "";
        String m_idndb_user_level_menues = "";
        String m_idndb_user_levels = "";
        String m_idndb_user_forms = "";
        String m_ndb_user_form_mode = "";
        String m_ndb_user_form_main_id = "";

        String m_user_id = "";
        try {

            m_idndb_user_levels = prm_obj.getString("idndb_user_levels");
            m_user_id = prm_obj.getString("user_id");

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            m_strQry = "delete from ndb_user_level_menues where m_idndb_user_levels='" + m_idndb_user_levels + "'";
            _stmnt.executeUpdate(m_strQry);

            for (Iterator iterator = prm_obj.keys(); iterator.hasNext();) {
                String key = (String) iterator.next();
                String[] sep_data = key.split("*");
                m_idndb_user_forms = sep_data[0];
                m_ndb_user_form_main_id = sep_data[1];

                if (m_idndb_user_forms.equals("m_ndb_user_form_main_id")) {
                    m_ndb_user_form_mode = "MM";
                } else {
                    m_ndb_user_form_mode = "SB";
                }

                m_strQry = "insert into ndb_user_level_menues (idndb_user_levels,idndb_user_forms,ndb_user_form_mode,ndb_user_form_main_id,ndb_usr_menu_acc_creat_by,ndb_usr_menu_acc_creat_date,ndb_usr_menu_acc_mod_by,ndb_usr_menu_acc_mod_date) values("
                        + "'" + m_idndb_user_levels + "',"
                        + "'" + m_idndb_user_forms + "',"
                        + "'" + m_ndb_user_form_mode + "',"
                        + "'" + m_ndb_user_form_main_id + "',"
                        + "'" + m_user_id + "',"
                        + "NOW(),"
                        + "'" + m_user_id + "',"
                        + "NOW())";
                if (_stmnt.executeUpdate(m_strQry) <= 0) {
                    throw new Exception("Error Occured in insert role-forms");
                }
            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            success = true;
        } catch (Exception e) {
            _exception = e;
            abortConnection(_currentCon);
            e.printStackTrace();

        } finally {
            try {
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

    public String getUserMenus(String user_id, String user_level) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        Statement stmt2 = null;
        String getFINALHTML = "";
        try {
            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt1 = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            String main_sql;
            String main_form_sql;
            String sub_form_sql;
            String sub_sql;
            String getHTML = "";

            String ndb_form_name = "";
            String form_path = "";
            String idndb_user_forms = "";
            String idndb_user_forms_sub = "";
            String getSubMenus = "";
            main_sql = "SELECT * FROM ndb_user_level_menues where idndb_user_levels ='" + user_level + "' and ndb_user_form_mode='MM'";
            _rs = _stmnt.executeQuery(main_sql);
            int activMenu = 0;
            while (_rs.next()) {
                activMenu++;
                idndb_user_forms = _rs.getString("idndb_user_forms");
                main_form_sql = "SELECT * FROM ndb_user_forms where idndb_user_forms ='" + idndb_user_forms + "'";
                _rs1 = _stmnt1.executeQuery(main_form_sql);

                if (_rs1.next()) {
                    if (activMenu == 1) {
                        ndb_form_name = _rs1.getString("ndb_form_name");
                        getHTML = getHTML + " <li class=\"active treeview\">";
                        getHTML = getHTML + "<a href=\"#\">";
                        getHTML = getHTML + "  <i class=\"fa fa-dashboard\"></i> <span>" + ndb_form_name + "</span> <i class=\"fa fa-angle-left pull-right\"></i>";
                        getHTML = getHTML + " </a>";

                    } else {
                        ndb_form_name = _rs1.getString("ndb_form_name");
                        getHTML = " <li class=\"treeview\">";
                        getHTML = getHTML + "<a href=\"#\">";
                        getHTML = getHTML + "  <i class=\"fa fa-dashboard\"></i> <span>" + ndb_form_name + "</span> <i class=\"fa fa-angle-left pull-right\"></i>";
                        getHTML = getHTML + " </a>";

                    }

                    main_form_sql = "SELECT * FROM ndb_user_level_menues where idndb_user_levels ='" + user_level + "' and ndb_user_form_main_id='" + idndb_user_forms + "' and ndb_user_form_mode='SB'";
                    _rs2 = _stmnt1.executeQuery(main_form_sql);
                    getHTML = getHTML + "<ul class=\"treeview-menu\">";
                    while (_rs2.next()) {
                        idndb_user_forms_sub = _rs2.getString("idndb_user_forms");
                        sub_form_sql = "SELECT * FROM ndb_user_forms where idndb_user_forms ='" + idndb_user_forms_sub + "'";
                        _rs3 = _stmnt2.executeQuery(sub_form_sql);

                        while (_rs3.next()) {
                            form_path = _rs3.getString("ndb_form_path");
                            ndb_form_name = _rs3.getString("ndb_form_name");

                            getHTML = getHTML + "<li><a href=\"" + form_path + "\"><i class=\"fa fa-circle-o\"></i> " + ndb_form_name + "</a></li>";

                        }

                    }
                    getHTML = getHTML + "</ul>";
                    getFINALHTML = getFINALHTML + getHTML;

                }

            }

            getFINALHTML = getFINALHTML + " </li>";

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            return getFINALHTML;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_rs1 != null) {
                    _rs1.close();
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
                if (_stmnt1 != null) {
                    _stmnt1.close();
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
        return getFINALHTML;

    }

    public String getSupperUserMenues() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"active treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-laptop\"></i> <span>Master Files</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_bank.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_branch_file.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_industr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Industry File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_geogr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Geographical Market File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_holiday_marker.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_holiday_file_upload.jsp\"><i class=\"fa fa-circle-o\"></i>Holiday Marker File Upload</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_customer_master.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_prorelationship_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_relationshipestab_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"atreeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-edit\"></i> <span>Master File Authorization</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_bank_authorization.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File Authorization</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_branch_file_authorization.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File Authorization</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_holiday_marker_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker Authorization</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_customer_master_Authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer Authorization</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_prorelationship_setup_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping Authorization</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_relationshipestab_setup_Authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment Authorization</a></li>\n"
                + "                                \n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-credit-card\"></i> <span>Transactions</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_rf_manual.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry RF PDC</a></li>\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_cw_manual.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry CW PDC</a></li>\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_updates.jsp\"><i class=\"fa fa-circle-o\"></i>PDC Liquidations & Extensions</a></li>\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_fileupld.jsp\"><i class=\"fa fa-circle-o\"></i>PDC Bulk Upload</a></li>\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_paydtl_upld.jsp\"><i class=\"fa fa-circle-o\"></i>PDC Bulk Upload (Payments)</a></li>\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_cw_rf_conversion.jsp\"><i class=\"fa fa-circle-o\"></i>PDC CW To RF Conversion</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-edit\"></i><span>Transactions Authorization</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_rf_manual_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry RF PDC Authorization</a></li>\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_cw_manual_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry CW PDC Authorization</a></li>\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_bulk_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Bulk Upload PDC Entry Authorization</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                               <i class=\"fa fa-gears\"></i><span>Process</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/process/ndb_pdc_additionalupld.jsp\"><i class=\"fa fa-circle-o\"></i> Additional Day File Upload</a></li>\n"
                + "                                <li><a href=\"pages/process/ndb_pdc_retupld.jsp\"><i class=\"fa fa-circle-o\"></i> Return Upload</a></li>\n"
                + "                                <li><a href=\"pages/process/ndb_pdc_retleq.jsp\"><i class=\"fa fa-circle-o\"></i> Return / Liquidation Upload</a></li>\n"
                + "                                <li><a href=\"pages/process/ndb_pdc_dayend.jsp\"><i class=\"fa fa-circle-o\"></i> Day End Process</a></li>\n"
                + "                                <li><a href=\"pages/process/ndb_pdc_acc_gen.jsp\"><i class=\"fa fa-circle-o\"></i> GEFU File Download</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-book\"></i><span>Physical Verification</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/PhysicalVerification/ndb_cheque_physical_verification.jsp\"><i class=\"fa fa-circle-o\"></i>Physical Verification Input</a></li>\n"
                + "                                <li><a href=\"pages/PhysicalVerification/ndb_cheque_physical_verification_report.jsp\"><i class=\"fa fa-circle-o\"></i>Physical Verification Report</a></li>\n"
                + "                             </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>CW Reports And Charts</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>Other Reports And Charts</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getPDCRFMasterFileSMSInputter() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"active treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-laptop\"></i> <span>Master Files</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_bank.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_branch_file.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_industr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Industry File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_geogr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Geographical Market File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_holiday_marker.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_holiday_file_upload.jsp\"><i class=\"fa fa-circle-o\"></i></i>Holiday Marker File Upload</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>Other Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getPDCRFMasterFileSMSAuthorizor() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"active atreeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-edit\"></i> <span>Master File Authorization</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_bank_authorization.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File Authorization</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_branch_file_authorization.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File Authorization</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_holiday_marker_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker Authorization</a></li>\n"
                + "                                \n"
                + "                            </ul>\n"
                + "                        </li>"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>Other Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getPDCRFMasterFileInputter() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"active treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-laptop\"></i> <span>Master Files</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_customer_master.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_prorelationship_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_relationshipestab_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>Other Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getPDCRFMasterFileAuthorizor() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-laptop\"></i> <span>Master Files</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_bank.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_branch_file.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_industr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Industry File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_geogr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Geographical Market File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_holiday_marker.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_customer_master.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_prorelationship_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_relationshipestab_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "<li class=\"active atreeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-edit\"></i> <span>Master File Authorization</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_customer_master_Authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer Authorization</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_prorelationship_setup_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping Authorization</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_relationshipestab_setup_Authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment Authorization</a></li>\n"
                + "                                \n"
                + "                            </ul>\n"
                + "                        </li>"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>RF Reports And Charts</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>Other Reports And Charts</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getPDCRFTransactionInputter() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-laptop\"></i> <span>Master Files</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_bank.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_branch_file.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_industr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Industry File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_geogr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Geographical Market File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_holiday_marker.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_customer_master.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_prorelationship_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_relationshipestab_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>"
                + " <li class=\"active treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-credit-card\"></i>  <span>Transactions</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_rf_manual.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry RF PDC</a></li>\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_cw_manual.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry CW PDC</a></li>\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_updates.jsp\"><i class=\"fa fa-circle-o\"></i>PDC Liquidations & Extensions</a></li>\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_fileupld.jsp\"><i class=\"fa fa-circle-o\"></i>PDC Bulk Upload</a></li>\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_cw_rf_conversion.jsp\"><i class=\"fa fa-circle-o\"></i>PDC CW To RF Conversion</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-book\"></i> <span>Physical Verification</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/PhysicalVerification/ndb_cheque_physical_verification.jsp\"><i class=\"fa fa-circle-o\"></i>Physical Verification Input</a></li>\n"
                + "                             </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>Other Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getPDCRFTransactionAuthorizor() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-laptop\"></i> <span>Master Files</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_bank.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_branch_file.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_industr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Industry File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_geogr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Geographical Market File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_holiday_marker.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_customer_master.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_prorelationship_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_relationshipestab_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "<li class=\"active atreeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-edit\"></i> <span>Master Files Authorization</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_bank_authorization.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File Authorization</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_branch_file_authorization.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File Authorization</a></li>\n"
                + "                                \n"
                + "                            </ul>\n"
                + "                        </li>"
                + " <li class=\"active treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-edit\"></i><span>Transactions Authorization</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_rf_manual_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry RF PDC Authorization</a></li>\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_cw_manual_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry CW PDC Authorization</a></li>\n"
                + "                                <li><a href=\"pages/pdc_txns/ndb_pdc_bulk_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Bulk Upload PDC Entry Authorization</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-gears\"></i><span>Process</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/process/ndb_pdc_additionalupld.jsp\"><i class=\"fa fa-circle-o\"></i> Additional Day File Upload</a></li>\n"
                + "                                <li><a href=\"pages/process/ndb_pdc_retupld.jsp\"><i class=\"fa fa-circle-o\"></i> Return Upload</a></li>\n"
                + "                                <li><a href=\"pages/process/ndb_pdc_retleq.jsp\"><i class=\"fa fa-circle-o\"></i> Return / Liquidation Upload</a></li>\n"
                + "                                <li><a href=\"pages/process/ndb_pdc_dayend.jsp\"><i class=\"fa fa-circle-o\"></i> Day End Process</a></li>\n"
                + "                                <li><a href=\"pages/process/ndb_pdc_acc_gen.jsp\"><i class=\"fa fa-circle-o\"></i> GEFU File Download</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-book\"></i> <span>Physical Verification</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                //                + "                                <li><a href=\"pages/PhysicalVerification/ndb_cheque_physical_verification.jsp\"><i class=\"fa fa-circle-o\"></i>Physical Verification Input</a></li>\n"
                + "                                <li><a href=\"pages/PhysicalVerification/ndb_cheque_physical_verification_report.jsp\"><i class=\"fa fa-circle-o\"></i>Physical Verification Report</a></li>\n"
                + "                             </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>Other Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getPDCBussinessUser() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"active treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                               <i class=\"fa fa-laptop\"></i> <span>Master Files</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_bank.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_branch_file.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_industr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Industry File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_geogr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Geographical Market File</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_holiday_marker.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_customer_master.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_prorelationship_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping</a></li>\n"
                + "                                <li><a href=\"pages/MasterFiles/ndb_relationshipestab_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>Other Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"pages/RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getSubSupperUserMenues() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"active treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-laptop\"></i> <span>Master Files</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../MasterFiles/ndb_bank.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_branch_file.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_industr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Industry File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_geogr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Geographical Market File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_holiday_marker.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_holiday_file_upload.jsp\"><i class=\"fa fa-circle-o\"></i>Holiday Marker File Upload</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_customer_master.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_prorelationship_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_relationshipestab_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"atreeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                        <i class=\"fa fa-edit\"></i> <span>Master Files Authorization</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../MasterFiles/ndb_bank_authorization.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File Authorization</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_branch_file_authorization.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File Authorization</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_holiday_marker_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker Authorization</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_customer_master_Authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer Authorization</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_prorelationship_setup_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping Authorization</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_relationshipestab_setup_Authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment Authorization</a></li>\n"
                + "                                \n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-credit-card\"></i>  <span>Transactions</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_rf_manual.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry RF PDC</a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_cw_manual.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry CW PDC</a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_updates.jsp\"><i class=\"fa fa-circle-o\"></i>PDC Liquidations & Extensions</a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_fileupld.jsp\"><i class=\"fa fa-circle-o\"></i>PDC Bulk Upload</a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_paydtl_upld.jsp\"><i class=\"fa fa-circle-o\"></i>PDC Bulk Upload (Payments) </a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_cw_rf_conversion.jsp\"><i class=\"fa fa-circle-o\"></i>PDC CW To RF Conversion</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-edit\"></i> <span>Transactions Authorization</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_rf_manual_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry RF PDC Authorization</a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_cw_manual_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry CW PDC Authorization</a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_bulk_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Bulk Upload PDC Entry Authorization</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "\n"
                + "\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-gears\"></i><span>Process</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                               <li><a href=\"../process/ndb_pdc_additionalupld.jsp\"><i class=\"fa fa-circle-o\"></i>Additional Day File Upload</a></li>\n"
                + "                                <li><a href=\"../process/ndb_pdc_retupld.jsp\"><i class=\"fa fa-circle-o\"></i>Return Upload</a></li>\n"
                + "                                <li><a href=\"../process/ndb_pdc_retleq.jsp\"><i class=\"fa fa-circle-o\"></i>Return / Liquidation Upload</a></li>\n"
                + "                                <li><a href=\"../process/ndb_pdc_dayend.jsp\"><i class=\"fa fa-circle-o\"></i>Day End Process</a></li>\n"
                + "                                <li><a href=\"../process/ndb_pdc_acc_gen.jsp\"><i class=\"fa fa-circle-o\"></i>GEFU File Download</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-book\"></i><span>Physical Verification</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../PhysicalVerification/ndb_cheque_physical_verification.jsp\"><i class=\"fa fa-circle-o\"></i>Physical Verification Input</a></li>\n"
                + "                                <li><a href=\"../PhysicalVerification/ndb_cheque_physical_verification_report.jsp\"><i class=\"fa fa-circle-o\"></i>Physical Verification Report</a></li>\n"
                + "                             </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>Other Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getSubPDCRFMasterFileSMSInputter() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"active treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                               <i class=\"fa fa-laptop\"></i> <span>Master Files</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../MasterFiles/ndb_bank.jsp\"><i class=\"fa fa-circle-o\"></i>Bank File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_branch_file.jsp\"><i class=\"fa fa-circle-o\"></i>Branch File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_industr_file.jsp\"><i class=\"fa fa-circle-o\"></i>Industry File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_geogr_file.jsp\"><i class=\"fa fa-circle-o\"></i>Geographical Market File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_holiday_marker.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_holiday_file_upload.jsp\"><i class=\"fa fa-circle-o\"></i>Holiday Marker File Upload</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                               <i class=\"fa fa-globe\"></i><span>Other Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getSubPDCRFMasterFileSMSAuthorizor() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"active atreeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-edit\"></i> <span>Master Files Authorization</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../MasterFiles/ndb_bank_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Bank File Authorization</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_branch_file_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Branch File Authorization</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_holiday_marker_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker Authorization</a></li>\n"
                + "                                \n"
                + "                            </ul>\n"
                + "                        </li>"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i><span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                               <i class=\"fa fa-globe\"></i> <span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>Other Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getSubPDCRFMasterFileInputter() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"active treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-laptop\"></i> <span>Master Files</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../MasterFiles/ndb_customer_master.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_prorelationship_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_relationshipestab_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>Other Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getSubPDCRFMasterFileAuthorizor() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-laptop\"></i> <span>Master Files</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../MasterFiles/ndb_bank.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_branch_file.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_industr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Industry File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_geogr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Geographical Market File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_holiday_marker.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_customer_master.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_prorelationship_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_relationshipestab_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "<li class=\"active atreeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-edit\"></i> <span>Master Files Authorization</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../MasterFiles/ndb_customer_master_Authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer Authorization</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_prorelationship_setup_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping Authorization</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_relationshipestab_setup_Authorization.jsp\"<i class=\"fa fa-circle-o\"></i>Relationship Establishment Authorization</a></li>\n"
                + "                                \n"
                + "                            </ul>\n"
                + "                        </li>"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>Other Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getSubPDCRFTransactionInputter() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i><img src=\"../../dist/img/transaction_1.png\" alt=\"User Image\"></i> <span>Master Files</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../MasterFiles/ndb_bank.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_branch_file.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_industr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Industry File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_geogr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Geographical Market File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_holiday_marker.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_customer_master.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_prorelationship_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_relationshipestab_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>"
                + " <li class=\"active treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i><img src=\"../../dist/img/transaction_1.png\" alt=\"User Image\"></i> <span>Transactions</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_rf_manual.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry RF PDC</a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_cw_manual.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry CW PDC</a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_updates.jsp\"><i class=\"fa fa-circle-o\"></i>PDC Early Liquidations & Value Date Extensions</a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_fileupld.jsp\"><i class=\"fa fa-circle-o\"></i>PDC Bulk Upload</a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_paydtl_upld.jsp\"><i class=\"fa fa-circle-o\"></i>PDC Bulk Upload (Payments)</a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_cw_rf_conversion.jsp\"><i class=\"fa fa-circle-o\"></i>PDC CW To RF Conversion</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-book\"></i> <span>Physical Verification</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../PhysicalVerification/ndb_cheque_physical_verification.jsp\"><i class=\"fa fa-circle-o\"></i>Physical Verification Input</a></li>\n"
                + "                                 </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>CW Reports And Charts</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                               <i class=\"fa fa-globe\"></i> <span>Other Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getSubPDCRFTransactionAuthorizor() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i><img src=\"../../dist/img/png_master_1.png\" alt=\"User Image\"></i> <span>Master Files</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../MasterFiles/ndb_bank.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_branch_file.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_industr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Industry File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_geogr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Geographical Market File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_holiday_marker.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_customer_master.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_prorelationship_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_relationshipestab_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "<li class=\"active atreeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                        <i class=\"fa fa-edit\"></i><span>Master Files Authorization</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../MasterFiles/ndb_bank_authorization.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File Authorization</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_branch_file_authorization.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File Authorization</a></li>\n"
                + "                                \n"
                + "                            </ul>\n"
                + "                        </li>"
                + " <li class=\"active treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-edit\"></i> <span>Transactions Authorization</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_rf_manual_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry RF PDC Authorization</a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_cw_manual_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Entry CW PDC Authorization</a></li>\n"
                + "                                <li><a href=\"../pdc_txns/ndb_pdc_bulk_authorization.jsp\"><i class=\"fa fa-circle-o\"></i>Bulk Upload PDC Entry Authorization</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-book\"></i> <span>Physical Verification</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                //                + "                                <li><a href=\"../PhysicalVerification/ndb_cheque_physical_verification.jsp\"><i class=\"fa fa-circle-o\"></i>Physical Verification Input</a></li>\n"
                + "                                <li><a href=\"../PhysicalVerification/ndb_cheque_physical_verification_report.jsp\"><i class=\"fa fa-circle-o\"></i>Physical Verification Report</a></li>\n"
                + "                             </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                               <i class=\"fa fa-gears\"></i><span>Process</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                               <li><a href=\"../process/ndb_pdc_additionalupld.jsp\"><i class=\"fa fa-circle-o\"></i>Additional Day File Upload</a></li>\n"
                + "                                <li><a href=\"../process/ndb_pdc_retupld.jsp\"><i class=\"fa fa-circle-o\"></i>Return Upload</a></li>\n"
                + "                                <li><a href=\"../process/ndb_pdc_retleq.jsp\"><i class=\"fa fa-circle-o\"></i>Return / Liquidation Upload</a></li>\n"
                + "                                <li><a href=\"../process/ndb_pdc_dayend.jsp\"><i class=\"fa fa-circle-o\"></i>Day End Process</a></li>\n"
                + "                                <li><a href=\"../process/ndb_pdc_acc_gen.jsp\"><i class=\"fa fa-circle-o\"></i>GEFU File Download</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>CW Reports And Charts</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                               <i class=\"fa fa-globe\"></i><span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>Other Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getSubPDCBussinessUser() {
        String _system_date = new comboDAO().getSystemDate();
        String menues = "";
        menues = "<li>\n"
                + "                            <a href=\"../calendar.html\">\n"
                + "                                <i class=\"fa fa-calendar\"></i> <span>RMS DATE : " + _system_date + "</span>\n"
                + "                            </a>\n"
                + "                        </li><li class=\"active treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i><img src=\"../../dist/img/png_master_1.png\" alt=\"User Image\"></i> <span>Master Files</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../MasterFiles/ndb_bank.jsp\"><i class=\"fa fa-circle-o\"></i> Bank File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_branch_file.jsp\"><i class=\"fa fa-circle-o\"></i> Branch File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_industr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Industry File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_geogr_file.jsp\"><i class=\"fa fa-circle-o\"></i> Geographical Market File</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_holiday_marker.jsp\"><i class=\"fa fa-circle-o\"></i>Manual Holiday Marker</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_customer_master.jsp\"><i class=\"fa fa-circle-o\"></i>Define Customer</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_prorelationship_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Customer Product Mapping</a></li>\n"
                + "                                <li><a href=\"../MasterFiles/ndb_relationshipestab_setup.jsp\"><i class=\"fa fa-circle-o\"></i>Relationship Establishment</a></li>\n"
                + "                               \n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>CW Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>CW Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_return_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_cw_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>CW Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>RF Reports And Charts</span><i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_clearing_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Received Cheques Status - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_value_date_ext_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Value Date Extension</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_additional_day_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Additional Day</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_buyer_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Detail Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_seller_wise_facility.jsp\"><i class=\"fa fa-circle-o\"></i>RF Dealer Facility - Summary Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_status_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Status Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_returned_cheques_seller_wise.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return & Cleared Report </a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (SW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_ret_analist_bw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Cheque Return Analysis - (BW)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_rf_chq_charges_sw.jsp\"><i class=\"fa fa-circle-o\"></i>RF Commission Charges - (SW)</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n"
                + "                        <li class=\"treeview\">\n"
                + "                            <a href=\"#\">\n"
                + "                                <i class=\"fa fa-globe\"></i> <span>Other Reports And Charts</span> <i class=\"fa fa-angle-left pull-right\"></i>\n"
                + "                            </a>\n"
                + "                            <ul class=\"treeview-menu\">\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_un_authorized_data_report.jsp\"><i class=\"fa fa-circle-o\"></i>RMS All Un-Authorized Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_find_buyers.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Relationship Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_seller_buyer_status.jsp\"><i class=\"fa fa-circle-o\"></i>Seller Buyer Status Data</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_rms_full_portpolio_report.jsp\"><i class=\"fa fa-circle-o\"></i>Portfolio Report</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_booked_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Discounted Cheques Slips Download</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_cheque_sending.jsp\"><i class=\"fa fa-circle-o\"></i>Cheques for Clearing (Sending)</a></li>\n"
                + "                                <li><a href=\"../RMSreports/ndb_today_gefu_report.jsp\"><i class=\"fa fa-circle-o\"></i>Today GEFU Report</a></li>\n"
                + "                            </ul>\n"
                + "                        </li>\n";
        return menues;

    }

    public String getRMSPDCUserMenus() {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        Statement stmt2 = null;
        String getFINALHTML = "";
        try {
            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt1 = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            String main_sql;
            String sub_form_sql;

            main_sql = "SELECT * FROM ndb_user_forms where ndb_user_system_group ='RMSACTIVE' and ndb_form_main_menu_id='#' and ndb_form_status ='ACTIVE'";
            _rs = _stmnt.executeQuery(main_sql);
            int first_lable = 0;
            while (_rs.next()) {
                first_lable++;

                String m_main_form_name = _rs.getString("ndb_form_name");
                String m_id_main_form_name = _rs.getString("idndb_user_forms");
                if (first_lable == 1) {
                    getFINALHTML = getFINALHTML + "<div class=\"col-sm-10\"><input type=\"checkbox\" id=\"" + m_id_main_form_name + "\" value=\"" + m_id_main_form_name + "*" + m_id_main_form_name + "\">" + m_main_form_name + "</div>";

                } else {
                    getFINALHTML = getFINALHTML + "<div class=\"col-sm-10\" style=\"margin-left: 169px;\"><input type=\"checkbox\" id=\"" + m_id_main_form_name + "\" value=\"" + m_id_main_form_name + "*" + m_id_main_form_name + "\">" + m_main_form_name + "</div>";

                }
                getFINALHTML = getFINALHTML + "<div class=\"col-sm-10\" style=\"margin-left: 250px;\">";

                sub_form_sql = "SELECT * FROM ndb_user_forms where ndb_form_main_menu_id='" + m_id_main_form_name + "' and ndb_form_status ='ACTIVE'";
                _rs2 = _stmnt2.executeQuery(sub_form_sql);
                while (_rs2.next()) {
                    String m_sub_form_name = _rs2.getString("ndb_form_name");
                    String m_sub_form_id = _rs2.getString("idndb_user_forms");
                    getFINALHTML = getFINALHTML + "<input type=\"checkbox\" id=\"" + m_sub_form_id + "\" value=\"" + m_sub_form_id + "*" + m_id_main_form_name + "\">" + m_sub_form_name + " </br>";

                }

                getFINALHTML = getFINALHTML + " </div>";
            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            return getFINALHTML;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_rs1 != null) {
                    _rs1.close();
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
                if (_stmnt1 != null) {
                    _stmnt1.close();
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
        return getFINALHTML;

    }

    public String getPOMSUserMenus() {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        Statement stmt2 = null;
        String getFINALHTML = "";
        try {
            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt1 = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            String main_sql;
            String sub_form_sql;

            main_sql = "SELECT * FROM ndb_user_forms where ndb_user_system_group ='POMSACTIVE' and ndb_form_main_menu_id='#' and ndb_form_status ='ACTIVE'";
            _rs = _stmnt.executeQuery(main_sql);
            int first_lable = 0;
            while (_rs.next()) {
                first_lable++;

                String m_main_form_name = _rs.getString("ndb_form_name");
                String m_id_main_form_name = _rs.getString("idndb_user_forms");
                if (first_lable == 1) {
                    getFINALHTML = getFINALHTML + "<div class=\"col-sm-10\"><input type=\"checkbox\" id=\"" + m_id_main_form_name + "\" value=\"" + m_id_main_form_name + "*" + m_id_main_form_name + "\">" + m_main_form_name + "</div>";

                } else {
                    getFINALHTML = getFINALHTML + "<div class=\"col-sm-10\" style=\"margin-left: 169px;\"><input type=\"checkbox\" id=\"" + m_id_main_form_name + "\" value=\"" + m_id_main_form_name + "*" + m_id_main_form_name + "\">" + m_main_form_name + "</div>";

                }
                getFINALHTML = getFINALHTML + "<div class=\"col-sm-10\" style=\"margin-left: 250px;\">";

                sub_form_sql = "SELECT * FROM ndb_user_forms where ndb_form_main_menu_id='" + m_id_main_form_name + "' and ndb_form_status ='ACTIVE'";
                _rs2 = _stmnt2.executeQuery(sub_form_sql);
                while (_rs2.next()) {
                    String m_sub_form_name = _rs2.getString("ndb_form_name");
                    String m_sub_form_id = _rs2.getString("idndb_user_forms");
                    getFINALHTML = getFINALHTML + "<input type=\"checkbox\" id=\"" + m_sub_form_id + "\" value=\"" + m_sub_form_id + "*" + m_id_main_form_name + "\">" + m_sub_form_name + " </br>";

                }

                getFINALHTML = getFINALHTML + " </div>";
            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            return getFINALHTML;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_rs1 != null) {
                    _rs1.close();
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
                if (_stmnt1 != null) {
                    _stmnt1.close();
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
        return getFINALHTML;

    }

    public String getUMUserMenus() {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();

        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        Statement stmt2 = null;
        String getFINALHTML = "";
        try {
            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            _stmnt1 = _currentCon.createStatement();
            _stmnt2 = _currentCon.createStatement();

            String main_sql;
            String sub_form_sql;

            main_sql = "SELECT * FROM ndb_user_forms where ndb_user_system_group ='UMACTIVE' and ndb_form_main_menu_id='#' and ndb_form_status ='ACTIVE'";
            _rs = _stmnt.executeQuery(main_sql);
            int first_lable = 0;
            while (_rs.next()) {
                first_lable++;

                String m_main_form_name = _rs.getString("ndb_form_name");
                String m_id_main_form_name = _rs.getString("idndb_user_forms");
                if (first_lable == 1) {
                    getFINALHTML = getFINALHTML + "<div class=\"col-sm-10\"><input type=\"checkbox\" id=\"" + m_id_main_form_name + "\" value=\"" + m_id_main_form_name + "*" + m_id_main_form_name + "\">" + m_main_form_name + "</div>";

                } else {
                    getFINALHTML = getFINALHTML + "<div class=\"col-sm-10\" style=\"margin-left: 169px;\"><input type=\"checkbox\" id=\"" + m_id_main_form_name + "\" value=\"" + m_id_main_form_name + "*" + m_id_main_form_name + "\">" + m_main_form_name + "</div>";

                }

                getFINALHTML = getFINALHTML + "<div class=\"col-sm-10\" style=\"margin-left: 250px;\">";

                sub_form_sql = "SELECT * FROM ndb_user_forms where ndb_form_main_menu_id='" + m_id_main_form_name + "' and ndb_form_status ='ACTIVE'";
                _rs2 = _stmnt2.executeQuery(sub_form_sql);
                while (_rs2.next()) {
                    String m_sub_form_name = _rs2.getString("ndb_form_name");
                    String m_sub_form_id = _rs2.getString("idndb_user_forms");
                    getFINALHTML = getFINALHTML + "<input type=\"checkbox\" id=\"" + m_sub_form_id + "\" value=\"" + m_sub_form_id + "*" + m_id_main_form_name + "\">" + m_sub_form_name + " </br>";

                }

                getFINALHTML = getFINALHTML + " </div>";
            }

            if (!endConnection(_currentCon)) {
                throw new Exception("Error occured in end transaction");
            }
            return getFINALHTML;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (_rs != null) {
                    _rs.close();
                }
                if (_rs1 != null) {
                    _rs1.close();
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
                if (_stmnt1 != null) {
                    _stmnt1.close();
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
        return getFINALHTML;

    }

    public boolean creatUserLevel(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {

            String sql_qry;
            boolean newdata = true;

            String m_str_user_id = prm_obj.getString("user_id");
            String m_str_idndb_user_levels = prm_obj.getString("idndb_user_levels");
            String m_str_ndb_user_level = prm_obj.getString("ndb_user_level");
            String m_str_ndb_user_level_status = prm_obj.getString("ndb_user_level_status");

            String m_str_condition = "";

            String m_str_old_idndb_user_levels = "";
            String m_str_old_ndb_user_level = "";
            String m_str_old_ndb_user_level_status = "";
            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();

            sql_qry = "SELECT * FROM ndb_user_levels where idndb_user_levels ='" + m_str_idndb_user_levels + "'";
            _rs = _stmnt.executeQuery(sql_qry);
            if (_rs.next()) {
                m_str_old_ndb_user_level = _rs.getString("ndb_user_level");
                newdata = false;
            }

            if (newdata) {
                sql_qry = "INSERT INTO ndb_user_levels ( ndb_user_level,ndb_user_level_status,ndb_user_level_creat_by,ndb_user_level_creat_date,ndb_user_level_mod_by,ndb_user_level_mod_date ) VALUES("
                        + " '" + m_str_ndb_user_level + "',"
                        + " '" + m_str_ndb_user_level_status + "',"
                        + " '" + m_str_user_id + "',"
                        + " NOW(),"
                        + " '" + m_str_user_id + "',"
                        + "NOW())";
                if (_stmnt.executeUpdate(sql_qry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }
            } else {
                if (!m_str_ndb_user_level.equals(m_str_old_ndb_user_level)) {
                    m_str_condition = m_str_condition + "ndb_user_level='" + m_str_ndb_user_level + "',";
                }
                if (!m_str_ndb_user_level_status.equals(m_str_old_ndb_user_level_status)) {
                    m_str_condition = m_str_condition + "ndb_user_level_status='" + m_str_ndb_user_level_status + "',";
                }

                sql_qry = "UPDATE ndb_user_levels SET " + m_str_condition + ","
                        + "ndb_user_level_mod_by='" + m_str_user_id + "'ndb_user_level_mod_date=NOW() WHERE idndb_user_levels='" + m_str_idndb_user_levels + "'";
                if (_stmnt.executeUpdate(sql_qry) <= 0) {
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

    public boolean creatForm(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }

            String sql_qry;
            boolean newdata = true;
            String m_str_condition = "";

            String m_str_user_id = prm_obj.getString("user_id");
            String m_str_idndb_user_forms = prm_obj.getString("idndb_user_forms");
            String m_str_ndb_form_name = prm_obj.getString("ndb_form_name");
            String m_str_ndb_form_path = prm_obj.getString("ndb_form_path");
            String m_str_ndb_form_status = prm_obj.getString("ndb_form_status");
            String m_str_ndb_form_main_menu_id = "";

            if (prm_obj.getString("ndb_form_main_menu_id").equals("MM")) {
                m_str_ndb_form_main_menu_id = "#";
            } else {
                m_str_ndb_form_main_menu_id = prm_obj.getString("ndb_form_main_menu_id");
            }

            String m_str_ndb_form_oder_by = prm_obj.getString("ndb_form_oder_by");
            String m_str_ndb_user_system_group = "";

            if (prm_obj.getString("ndb_user_rms").equals("RMSACTIVE")) {
                m_str_ndb_user_system_group = "RMSACTIVE";
            }
            if (prm_obj.getString("ndb_user_poms").equals("RPOMSACTIVE")) {
                m_str_ndb_user_system_group = "RPOMSACTIVE";
            }
            if (prm_obj.getString("ndb_user_um").equals("UMACTIVE")) {
                m_str_ndb_user_system_group = "UMACTIVE";
            }

            String m_str_old_idndb_user_forms = "";
            String m_str_old_ndb_form_name = "";
            String m_str_old_ndb_form_status = "";
            String m_str_old_ndb_form_path = "";
            String m_str_old_ndb_form_main_menu_id = "";
            String m_str_old_ndb_form_oder_by = "";
            String m_str_old_ndb_user_system_group = "";
            _stmnt = _currentCon.createStatement();
            sql_qry = "SELECT * FROM ndb_user_forms where idndb_user_forms ='" + m_str_idndb_user_forms + "'";
            _rs = _stmnt.executeQuery(sql_qry);
            if (_rs.next()) {
                m_str_old_idndb_user_forms = _rs.getString("idndb_user_forms");
                m_str_old_ndb_form_name = _rs.getString("ndb_form_name");
                m_str_old_ndb_form_path = _rs.getString("ndb_form_path");
                m_str_old_ndb_form_status = _rs.getString("ndb_form_status");
                m_str_old_ndb_form_main_menu_id = _rs.getString("ndb_form_main_menu_id");
                m_str_old_ndb_form_oder_by = _rs.getString("ndb_form_oder_by");
                m_str_old_ndb_user_system_group = _rs.getString("ndb_user_system_group");
                newdata = false;
            }

            if (newdata) {
                sql_qry = "INSERT INTO ndb_user_forms ( ndb_form_name,ndb_form_path,ndb_form_status,ndb_form_main_menu_id,ndb_form_oder_by,ndb_user_system_group,ndb_form_creat_by,ndb_form_creat_date,ndb_form_mod_by,ndb_form_mod_date ) VALUES("
                        + " '" + m_str_ndb_form_name + "',"
                        + " '" + m_str_ndb_form_path + "',"
                        + " '" + m_str_ndb_form_status + "',"
                        + " '" + m_str_ndb_form_main_menu_id + "',"
                        + " '" + m_str_ndb_form_oder_by + "',"
                        + " '" + m_str_ndb_user_system_group + "',"
                        + " '" + m_str_user_id + "',"
                        + " NOW(),"
                        + "'" + m_str_user_id + "',"
                        + "NOW())";
                if (_stmnt.executeUpdate(sql_qry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }
            } else {
                if (!m_str_ndb_form_name.equals(m_str_old_ndb_form_name)) {
                    m_str_condition = m_str_condition + "ndb_form_name='" + m_str_ndb_form_name + "',";
                }
                if (!m_str_ndb_form_path.equals(m_str_old_ndb_form_path)) {
                    m_str_condition = m_str_condition + "ndb_form_path='" + m_str_ndb_form_path + "',";
                }

                if (!m_str_ndb_form_main_menu_id.equals(m_str_old_ndb_form_main_menu_id)) {
                    m_str_condition = m_str_condition + "ndb_form_main_menu_id='" + m_str_ndb_form_main_menu_id + "',";
                }

                if (!m_str_ndb_form_oder_by.equals(m_str_old_ndb_form_oder_by)) {
                    m_str_condition = m_str_condition + "ndb_form_oder_by='" + m_str_ndb_form_oder_by + "',";
                }

                if (!m_str_ndb_user_system_group.equals(m_str_old_ndb_user_system_group)) {
                    m_str_condition = m_str_condition + "ndb_user_system_group='" + m_str_ndb_user_system_group + "',";
                }

                if (!m_str_ndb_form_status.equals(m_str_old_ndb_form_status)) {
                    m_str_condition = m_str_condition + "ndb_form_status='" + m_str_ndb_form_status + "',";
                }

                sql_qry = "UPDATE ndb_user_forms SET " + m_str_condition + ""
                        + "ndb_form_mod_by='" + m_str_user_id + "',ndb_form_mod_date=NOW() WHERE idndb_user_forms='" + m_str_idndb_user_forms + "'";
                if (_stmnt.executeUpdate(sql_qry) <= 0) {
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

    public boolean creatUser(JSONObject prm_obj) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();
        try {

            boolean newdata = true;

            String m_str_user_id = prm_obj.getString("user_id");
            String m_str_idndb_user_master = prm_obj.getString("idndb_user_master");
            String m_str_ndb_user_user_id = prm_obj.getString("ndb_user_user_id");
            String m_str_ndb_user_name = prm_obj.getString("ndb_user_name");
            String m_str_ndb_user_department = prm_obj.getString("ndb_user_department");
            String m_str_ndb_user_status = prm_obj.getString("ndb_user_status");
            String m_str_idndb_user_levels = prm_obj.getString("idndb_user_levels");
            String m_str_ndb_user_rms = prm_obj.getString("ndb_user_rms");
            String m_str_ndb_user_poms = prm_obj.getString("ndb_user_poms");
            String m_str_ndb_user_um = prm_obj.getString("ndb_user_um");

            String m_str_condition = "";
            String sql_qry = "";

            String m_str_old_idndb_user_master = "";
            String m_str_old_ndb_user_user_id = "";
            String m_str_old_ndb_user_name = "";
            String m_str_old_ndb_user_department = "";
            String m_str_old_ndb_user_status = "";
            String m_str_old_idndb_user_levels = "";
            String m_old_str_ndb_user_rms = "";
            String m_old_str_ndb_user_poms = "";
            String m_old_str_ndb_user_um = "";
            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.createStatement();
            sql_qry = "SELECT * FROM ndb_user_master  where idndb_user_master ='" + m_str_idndb_user_master + "'";
            _rs = _stmnt.executeQuery(sql_qry);
            if (_rs.next()) {
                m_str_old_ndb_user_user_id = _rs.getString("ndb_user_user_id");
                m_str_old_ndb_user_name = _rs.getString("ndb_user_name");
                m_str_old_ndb_user_department = _rs.getString("ndb_user_department");
                m_str_old_ndb_user_status = _rs.getString("ndb_user_status");
                m_str_old_idndb_user_levels = _rs.getString("idndb_user_levels");
                m_old_str_ndb_user_rms = _rs.getString("ndb_user_rms");
                m_old_str_ndb_user_poms = _rs.getString("ndb_user_poms");
                m_old_str_ndb_user_um = _rs.getString("ndb_user_um");
                newdata = false;
            }

            if (newdata) {
                sql_qry = "INSERT INTO ndb_user_master ( ndb_user_user_id,ndb_user_name,ndb_user_department,ndb_user_status,idndb_user_levels,ndb_user_rms,ndb_user_poms,ndb_user_um,ndb_user_creat_by,ndb_user_creat_date,ndb_user_mod_by,ndb_user_mod_date ) VALUES("
                        + " '" + m_str_ndb_user_user_id + "',"
                        + " '" + m_str_ndb_user_name + "',"
                        + " '" + m_str_ndb_user_department + "',"
                        + " '" + m_str_ndb_user_status + "',"
                        + " '" + m_str_idndb_user_levels + "',"
                        + " '" + m_str_ndb_user_rms + "',"
                        + " '" + m_str_ndb_user_poms + "',"
                        + " '" + m_str_ndb_user_um + "',"
                        + " '" + m_str_user_id + "',"
                        + " NOW(),"
                        + " '" + m_str_user_id + "',"
                        + "NOW(), )";
                if (_stmnt.executeUpdate(sql_qry) <= 0) {
                    throw new Exception("Error Occured in insert user-roles");
                }
            } else {
                if (!m_str_ndb_user_user_id.equals(m_str_old_ndb_user_user_id)) {
                    m_str_condition = m_str_condition + "ndb_user_user_id='" + m_str_ndb_user_user_id + "',";
                }
                if (!m_str_ndb_user_name.equals(m_str_old_ndb_user_name)) {
                    m_str_condition = m_str_condition + "ndb_user_name='" + m_str_ndb_user_name + "',";
                }
                if (!m_str_ndb_user_department.equals(m_str_old_ndb_user_department)) {
                    m_str_condition = m_str_condition + "ndb_user_department='" + m_str_ndb_user_department + "',";
                }
                if (!m_str_ndb_user_status.equals(m_str_old_ndb_user_status)) {
                    m_str_condition = m_str_condition + "ndb_user_status='" + m_str_ndb_user_status + "',";
                }
                if (!m_str_idndb_user_levels.equals(m_str_old_idndb_user_levels)) {
                    m_str_condition = m_str_condition + "idndb_user_levels='" + m_str_idndb_user_levels + "',";
                }

                if (!m_old_str_ndb_user_rms.equals(m_str_old_idndb_user_levels)) {
                    m_str_condition = m_str_condition + "ndb_user_rms='" + m_old_str_ndb_user_rms + "',";
                }

                if (!m_old_str_ndb_user_poms.equals(m_str_ndb_user_poms)) {
                    m_str_condition = m_str_condition + "ndb_user_poms='" + m_old_str_ndb_user_poms + "',";
                }

                if (!m_old_str_ndb_user_um.equals(m_str_ndb_user_um)) {
                    m_str_condition = m_str_condition + "ndb_user_um='" + m_old_str_ndb_user_um + "',";
                }

                sql_qry = "UPDATE ndb_user_levels SET " + m_str_condition + ","
                        + "ndb_user_mod_by='" + m_str_user_id + "'ndb_user_mod_date=NOW() WHERE idndb_user_master='" + m_str_idndb_user_master + "'";
                if (_stmnt.executeUpdate(sql_qry) <= 0) {
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

    public static String getDate() {
        String m_crrent_date = "";
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            Date date = new Date();
            m_crrent_date = sdf.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return m_crrent_date;

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

    public static void main(String[] args) {

        MenuDAO me = new MenuDAO();
        me.getUserMenus("1", "1");
    }

}
