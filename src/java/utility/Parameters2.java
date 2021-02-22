/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ResourceBundle;
import security.Cryptography;

/**
 *
 * @author Madhawa_10809
 */
public class Parameters2 {
     private static Parameters2 parameters2 = null;
    private static String dbDriver = "com.mysql.jdbc.Driver";
    private static String dbURL = "jdbc:mysql://";
    private static String dbServer = "10.96.197.5";
    private static String dbName = "/chequescan";
    private static String userFlexcube = "";
    private static String passwordFlexcube = "";
    private static String user = "";
    private static String password = "";
    private static String path;
    private static String UsernameFileUSMNG;
    private static String PasswordFileUSMNG;
    private static String messageFile = "message.log";
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

    private Parameters2() {
        try {
            retrieveProperties();
        } catch (Exception e) {
            System.out.println((new StringBuilder("ERROR: Parameters2.retrieveProperties() - ")).append(e.getMessage()).toString());
        }
        try {
            String encryptedUsername = "";
            String encryptedPassword = "";
            FileInputStream fis1 = new FileInputStream(new File(UsernameFileUSMNG));
            BufferedReader br = new BufferedReader(new InputStreamReader(fis1));
            encryptedUsername = br.readLine();
            FileInputStream fis2 = new FileInputStream(new File(PasswordFileUSMNG));
            BufferedReader br1 = new BufferedReader(new InputStreamReader(fis2));
            encryptedPassword = br1.readLine();
            System.out.println("Encr"+encryptedPassword);
            Cryptography cryptography = new Cryptography();
            String tempUser = "";
            String tempPassword = "";
            try {
                tempUser = cryptography.decrypt(encryptedUsername);
                tempPassword = cryptography.decrypt(encryptedPassword);
            } catch (Exception e1) {
                System.out.println((new StringBuilder("ERROR: Parameters2 1 - ")).append(e1.getMessage()).toString());
            }
            user = tempUser;
            password = tempPassword;
        } catch (Exception e) {
            System.out.println((new StringBuilder("ERROR: Parameters2 - .....")).append(e.getMessage()).toString());
        }

    }

    public static synchronized Parameters2 getInstance() {
        if (parameters2 == null) {
            parameters2 = new Parameters2();
        }
        return parameters2;
    }

    private void retrieveProperties() {
        ResourceBundle rb = ResourceBundle.getBundle("System");

        dbDriver = rb.getString("DatabaseDriver").trim();
        dbURL = rb.getString("DatabaseURL").trim();
        dbServer = rb.getString("DBServerUSMNG").trim();
        dbName = rb.getString("DatabaseNameUserManagement").trim();
        outputpath = rb.getString("outputpath").trim();
        path = rb.getString("Path");
        UsernameFileUSMNG = (new StringBuilder(String.valueOf(path))).append(rb.getString("UsernameFileUSMNG").trim()).toString();
        PasswordFileUSMNG = (new StringBuilder(String.valueOf(path))).append(rb.getString("PasswordFileUSMNG").trim()).toString();
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

    public String getDBServer() {
        return dbServer;
    }

    public String getDBName() {
        return dbName;
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

    static {
        path = "C:/";
        UsernameFileUSMNG = (new StringBuilder(String.valueOf(path))).append("Key/t24crib/UserName.txt").toString();
        PasswordFileUSMNG = (new StringBuilder(String.valueOf(path))).append("Key/t24crib/Password.txt").toString();
        logDir = (new StringBuilder(String.valueOf(path))).append("log/t24crib/").toString();
    }
}
