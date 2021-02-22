/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ndb.connection;

import utility.Parameters2;
import java.sql.*;
import java.util.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Madhawa_10809
 */
public class ConnectionPool2 {
     static Logger log = LogManager.getLogger(ConnectionPool2.class);

    class PooledConnectionJDBC {

        private Connection conn;
        private boolean inuse;
        final ConnectionPool2 this$0;

        public Connection getConnection() {
            return conn;
        }

        public void setInUse(boolean inuse) {
            this.inuse = inuse;
        }

        public boolean getInUse() {
            return inuse;
        }

        public void close() {
            try {
                if (conn != null) {
                    conn.close();
                }
                conn = null;
            } catch (SQLException e) {
                log.error((new StringBuilder("ERROR: ADMIN:PooledConnectionJDBC.close() - ")).append(e.getMessage()).toString());
            }
        }

        public PooledConnectionJDBC(Connection conn) {
            super();
            this$0 = ConnectionPool2.this;
            this.conn = null;
            inuse = false;
            if (conn != null) {
                this.conn = conn;
            }
        }
    }
    private static ConnectionPool2 connectionPool2;
    private int initialConnections;
    private int maxConnJDBC;
    private int totalConnections;
    private int usedCount;
    private Vector connectionsJDBC;
    private static String user;
    private static String password;
    private static String dbDriver;
    private static String dbURL;
    private static String dbServer;
    private static String dbName;
    private static String flexuser;
    private static String flexpassword;
    private static String flexdbDriver;
    private static String flexdbURL;
    private static String flexdbServer;
    private static String flexdbName;
    private Parameters2 parameters2;

    public ConnectionPool2() {
        initialConnections = 2;
        maxConnJDBC = 5;
        usedCount = 0;
        connectionsJDBC = new Vector();
        user = "";
        password = "";
        dbDriver = "";
        dbURL = "";
        dbServer = "";
        dbName = "";
        parameters2 = Parameters2.getInstance();
        user = parameters2.getUser();
        password = parameters2.getPassword();
        dbDriver = parameters2.getDBDriver();
        dbURL = parameters2.getDBURL();
        dbServer = parameters2.getDBServer();
        dbName = parameters2.getDBName();
        try {
        } catch (Exception e) {
            log.error((new StringBuilder("ERROR: ADMIN:ConnectionPool - ")).append(e.getMessage()).toString());
        }
    }

    public static synchronized ConnectionPool2 getInstance() {
        if (connectionPool2 == null) {
            connectionPool2 = new ConnectionPool2();
        }
        return connectionPool2;
    }

    public synchronized Connection getJDBCConnection() {
        Connection conn = null;
        try {
            Class.forName(dbDriver);
            conn = DriverManager.getConnection((new StringBuilder(String.valueOf(dbURL))).append(dbServer).append(dbName).toString(), user, password);
        } catch (Exception e) {
            log.error("Database Connection Error" + e.getMessage());
        }
        return conn;
    }
}
