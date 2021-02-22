package TEST;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import ndb.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Madhawa_4799
 */
public class save_data {

    static org.apache.logging.log4j.Logger log = LogManager.getLogger(save_data.class.getName());
    static ConnectionPool _connectionPool;
    static Connection _currentCon = null;
    private PreparedStatement _stmnt = null;
    private Statement _stmnt2 = null;
    private Statement _stmnt3 = null;
    private ResultSet _rs = null;

    public boolean saveData(String quiery) {
        boolean success = false;
        _connectionPool = ConnectionPool.getInstance();
        _currentCon = _connectionPool.getJDBCConnection();


        String m_strQry = quiery.replace("\\", "");

        try {

            if (!startConnection(_currentCon)) {
                throw new Exception("Error occured in start transaction");
            }
            _stmnt = _currentCon.prepareStatement(m_strQry);
            System.out.println(_stmnt);

            if (_stmnt.executeUpdate() <= 0) {
                throw new Exception("Error Occured in insert user-roles");
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
