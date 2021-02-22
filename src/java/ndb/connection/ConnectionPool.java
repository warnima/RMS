/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ndb.connection;

/**
 *
 * @author Amila_10367
 */
import utility.Parameters;
import java.sql.*;
import java.util.Vector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {

    static Logger log = LogManager.getLogger(ConnectionPool.class);

    class PooledConnectionJDBC {

        private Connection conn;
        private boolean inuse;
        final ConnectionPool this$0;

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
            this$0 = ConnectionPool.this;
            this.conn = null;
            inuse = false;
            if (conn != null) {
                this.conn = conn;
            }
        }
    }
    private static ConnectionPool connectionPool;
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
    private static String dumpExePath;
    private static String flexuser;
    private static String flexpassword;
    private static String flexdbDriver;
    private static String flexdbURL;
    private static String flexdbServer;
    private static String flexdbName;
    private Parameters parameters;

    public ConnectionPool() {
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
        dumpExePath = "";
        parameters = Parameters.getInstance();
        user = parameters.getUser();
        password = parameters.getPassword();
        dbDriver = parameters.getDBDriver();
        dbURL = parameters.getDBURL();
        dbServer = parameters.getDBServer();
        dbName = parameters.getDBName();
        dumpExePath = parameters.getdumpExePath();
        try {
        } catch (Exception e) {
            log.error((new StringBuilder("ERROR: ADMIN:ConnectionPool - ")).append(e.getMessage()).toString());
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
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

//    public synchronized void releaseJDBCConnection(Connection conn) {
//        PooledConnectionJDBC tempPooledConn = null;
//        try {
//            conn.close();
//        } catch (Exception e) {
//            log.error("Database Connection Release Error " + e.getMessage());
//        }
//    }

    public static void main(String args[]) {
        try {
            ConnectionPool a = new ConnectionPool();
            System.out.println(dbURL + a.dbServer + a.dbName + a.user + a.password);
            Connection con = a.getJDBCConnection();
            System.out.println(con);
        
        } catch (Exception e) {
            System.out.println("ERRRRRRRRRRRR Nilesh" + e.getMessage());
        }
    }
}