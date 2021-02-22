/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import DBOops.proccessDAO;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import static utility.FolderZiper.zipFolder;
import utility.Parameters;

/**
 *
 * @author Madhawa_4799
 */
public class db_backup {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(db_backup.class.getName());

    public static void main(String[] args) {
    }

    public boolean backupDataWithDatabase(String dumpExePath, String host, String port, String user, String password, String database, String backupPath) {
        boolean status = false;
        try {
            Process p = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hhmmss");
            Date date = new Date();
            String filepath = "backup(with_DB)-" + database + "-" + host + "-(" + dateFormat.format(date) + ").sql";

            String batchCommand = "";
            if (password != "") {
                //Backup with database
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password=" + password + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
            } else {
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
            }

            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(batchCommand);
            int processComplete = p.waitFor();

            if (processComplete == 0) {
                status = true;
            } else {
                status = false;
            }
            
            
// Unablee to delete directories...            
//            String m_db_backup_path = backupPath.substring(0, backupPath.length() - 1);
//
//            FolderZiper folderZiper = new FolderZiper();
//            folderZiper.zipFolder(m_db_backup_path, m_db_backup_path + ".zip");
//
//            File dir = new File(m_db_backup_path);
//            if (dir.isDirectory() == false) {
//                log.info("Not a directory. Do nothing");
//            }
//            File[] listFiles = dir.listFiles();
//            for (File file : listFiles) {
//                log.info("Deleting " + file.getName());
//                file.delete();
//            }
//            //now directory is empty, so we can delete it
//            log.info("Deleting Directory. Success = " + dir.delete());

        } catch (IOException ioe) {
            log.error(ioe.getLocalizedMessage());
            ioe.printStackTrace();

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();

        }
        return status;
    }

    public boolean runBeforeELPDBBACKUP() {
        boolean status = false;
        ResourceBundle rb = ResourceBundle.getBundle("System", Locale.getDefault());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy hhmmss");
            String date = sdf.format(new Date());
            Parameters para = new Parameters();
            String dumpExePath = "\\" + para.getdumpExePath();
            String[] host_date = para.getDBServer().split(":");
            String host = host_date[0];
            String port = host_date[1];
            String user = para.getUser();
            String passowrd = para.getPassword();
            String database = para.getDBName().replace("/", "");
            String backupPath = "";
            backupPath = rb.getString("dbDumpErlyLquidPath") + File.separator + date + "BEFORE_ERLLEQD_PROCESS";
            File file2 = new File(backupPath);
            if (!file2.exists()) {
                if (file2.mkdir()) {

                } else {

                }
            }
            String backup_to = backupPath + "\\";

            status = new db_backup().backupDataWithDatabase(dumpExePath, host, port, user, passowrd, database, backup_to);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean runBeforeDEPDBBACKUP() {
        ResourceBundle rb = ResourceBundle.getBundle("System", Locale.getDefault());
        boolean status = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy hhmmss");
            String date = sdf.format(new Date());
            Parameters para = new Parameters();
            String dumpExePath = "\\" + para.getdumpExePath();
            //need when testing with local 
            //String dumpExePath = para.getdumpExePath();
            String[] host_date = para.getDBServer().split(":");
            String host = host_date[0];
            String port = host_date[1];
            String user = para.getUser();
            String passowrd = para.getPassword();
            String database = para.getDBName().replace("/", "");
            String backupPath = "";
            backupPath = rb.getString("dbDumpBeforeDayendPath") + File.separator + date + "BEFORE_DAYENDPROCESS";
            File file2 = new File(backupPath);
            if (!file2.exists()) {
                if (file2.mkdir()) {

                } else {

                }
            }
            String backup_to = backupPath + "\\";

            status = new db_backup().backupDataWithDatabase(dumpExePath, host, port, user, passowrd, database, backup_to);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean runafterDEPDBBACKUP() {
        ResourceBundle rb = ResourceBundle.getBundle("System", Locale.getDefault());
        boolean status = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy hhmmss");
            String date = sdf.format(new Date());
            Parameters para = new Parameters();
            String dumpExePath = "\\" + para.getdumpExePath();
            String[] host_date = para.getDBServer().split(":");
            String host = host_date[0];
            String port = host_date[1];
            String user = para.getUser();
            String passowrd = para.getPassword();
            String database = para.getDBName().replace("/", "");
            String backupPath = "";
            backupPath = rb.getString("dbDumpAfterDayendPath") + File.separator + date + "AFTER_DAYENDPROCESS";
            File file2 = new File(backupPath);
            if (!file2.exists()) {
                if (file2.mkdir()) {

                } else {

                }
            }
            String backup_to = backupPath + "\\";

            status = new db_backup().backupDataWithDatabase(dumpExePath, host, port, user, passowrd, database, backup_to);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public String DEPDBBACKUP() {
        ResourceBundle rb = ResourceBundle.getBundle("System", Locale.getDefault());
        String status = "false";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy hhmmss");
            String date = sdf.format(new Date());
            Parameters para = new Parameters();
            String dumpExePath = "\\" + para.getdumpExePath();
            String[] host_date = para.getDBServer().split(":");
            String host = host_date[0];
            String port = host_date[1];
            String user = para.getUser();
            String passowrd = para.getPassword();
            String database = para.getDBName().replace("/", "");
            String backupPath = "";
            backupPath = rb.getString("dbDumpBeforeDayendPath") + File.separator + date + "BEFORE_DAYENDPROCESS";
            File file2 = new File(backupPath);
            if (!file2.exists()) {
                if (file2.mkdir()) {

                } else {

                }
            }
            String backup_to = backupPath + "\\";
            String backup_to_set = backupPath;

            status = new db_backup().backupDataWithDatabase(dumpExePath, host, port, user, passowrd, database, backup_to, backup_to_set);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public String backupDataWithDatabase(String dumpExePath, String host, String port, String user, String password, String database, String backupPath, String backup_to_set) {
        boolean status = false;
        try {
            Process p = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hhmmss");
            Date date = new Date();
            String filepath = "backup(with_DB)-" + database + "-" + host + "-(" + dateFormat.format(date) + ").sql";

            String batchCommand = "";
            if (password != "") {
                //Backup with database
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password=" + password + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
            } else {
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + backupPath + "\"";
            }

            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(batchCommand);
            int processComplete = p.waitFor();

            if (processComplete == 0) {
                status = true;
            } else {
                status = false;
            }

        } catch (IOException ioe) {

            ioe.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }
        return backup_to_set;
    }

}
