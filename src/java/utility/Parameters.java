package utility;

import security.Cryptography;
import java.io.*;
import java.util.ResourceBundle;

public class Parameters {

    private static Parameters parameters = null;
    private static String dbDriver = "com.mysql.jdbc.Driver";
    private static String dbURL = "jdbc:mysql://";
    private static String dbServer = "10.96.197.5";
    private static String dbName = "/chequescan";
    private static String dumpExePath = "/chequescan";
    private static String userFlexcube = "";
    private static String passwordFlexcube = "";
    private static String user = "";
    private static String password = "";
    private static String path;
    private static String usernameFile;
    private static String passwordFile;
    private static String messageFile = "message.log";
    private static String GEFUfilepath = "";
    private static String NDBCommisionPLAcc = "";
    private static String NDBChqReturnChg = "";
    private static String ReportFiles = "";
    private static String logDir;
    private static String outputpath;

    /**
     * @return the outputpath
     */
    public static String getOutputpath() {
        return outputpath;
    }

    /**
     * @param aOutputpath the outputpath to set
     */
    public static void setOutputpath(String aOutputpath) {
        outputpath = aOutputpath;
    }

    public Parameters() {
        try {
                    retrieveProperties();
        } catch (Exception e) {
            System.out.println((new StringBuilder("ERROR: Parameters.retrieveProperties() - ")).append(e.getMessage()).toString());
        }
        try {
            String encryptedUsername = "";
            String encryptedPassword = "";
            FileInputStream fis1 = new FileInputStream(new File(usernameFile));
            BufferedReader br = new BufferedReader(new InputStreamReader(fis1));
            encryptedUsername = br.readLine();
            FileInputStream fis2 = new FileInputStream(new File(passwordFile));
            BufferedReader br1 = new BufferedReader(new InputStreamReader(fis2));
            encryptedPassword = br1.readLine();
            Cryptography cryptography = new Cryptography();
            String tempUser = "";
            String tempPassword = "";
            try {
                tempUser = cryptography.decrypt(encryptedUsername);
                tempPassword = cryptography.decrypt(encryptedPassword);
            } catch (Exception e1) {
                System.out.println((new StringBuilder("ERROR: Parameters 1 - ")).append(e1.getMessage()).toString());
            }
            user = tempUser;
            password = tempPassword;
        } catch (Exception e) {
            System.out.println((new StringBuilder("ERROR: Parameters - .....")).append(e.getMessage()).toString());
        }

    }

    public static synchronized Parameters getInstance() {
        if (parameters == null) {
            parameters = new Parameters();
        }
        return parameters;
    }

//    Parameters() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    private void retrieveProperties() {
        ResourceBundle rb = ResourceBundle.getBundle("System");

        dbDriver = rb.getString("DatabaseDriver").trim();
        dbURL = rb.getString("DatabaseURL").trim();
        dbServer = rb.getString("DBServer").trim();
        dbName = rb.getString("DatabaseName").trim();
        dumpExePath = rb.getString("dumpExePath").trim();
        outputpath = rb.getString("outputpath").trim();
        NDBCommisionPLAcc = rb.getString("NDBCommisionPLAcc").trim();
        NDBChqReturnChg = rb.getString("NDBChqReturnChg").trim();
        path = rb.getString("Path");
        usernameFile = (new StringBuilder(String.valueOf(path))).append(rb.getString("UsernameFile").trim()).toString();
        passwordFile = (new StringBuilder(String.valueOf(path))).append(rb.getString("PasswordFile").trim()).toString();
        logDir = (new StringBuilder(String.valueOf(path))).append(rb.getString("LogDirectory").trim()).toString();
        messageFile = rb.getString("MessageFile").trim();
        rb = null;
    }

    public int getBankCode() {
        return 7214;
    }

    public int getBranchCode() {
        return 900;
    }

    public String getDBDriver() {
        return dbDriver;
    }

    public String getDBURL() {
        return dbURL;
    }

    public String GEFUfilepath() {
        return path+GEFUfilepath;
    }

    public String getDBServer() {
        return dbServer;
    }

    public String getDBName() {
        return dbName;
    }
    public String getdumpExePath() {
        return dumpExePath;
    }
    /*
    
     */

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getTemplatePath() {
        return path;
    }

    public String getMessageFile() {
        return messageFile;
    }

    public String getLogDirectory() {
        return logDir;
    }

    public String getNDBCommisionPLAcc() {
        return NDBCommisionPLAcc;
    }
    
    public String getNDBChqReturnChg() {
        return NDBChqReturnChg;
    }

    public String getGEFUfilepath() {
        return path+GEFUfilepath;
    }

    static {
        path = "C:/";
        usernameFile = (new StringBuilder(String.valueOf(path))).append("Key/t24crib/UserName.txt").toString();
        passwordFile = (new StringBuilder(String.valueOf(path))).append("Key/t24crib/Password.txt").toString();
        logDir = (new StringBuilder(String.valueOf(path))).append("log/t24crib/").toString();
    }

    public String getReportFiles() {
  return path+ReportFiles; 
    }

}
