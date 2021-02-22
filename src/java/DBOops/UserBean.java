/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBOops;

import java.io.Serializable;

/**
 *
 * @author indika_4496
 */
public class UserBean implements Serializable{

    private String _strUserId;
    private String _strUserName;
    private String _strpassword;
    private String _strStatus;
    private String _strCurrentStatus;
    private boolean valid;
    private String _paramID;
    private String _strEditingUser;
    private String _strLastLogin;
    
    private String _code;

    /**
     * @return the _strUserId
     */
    public String getStrUserId() {
        return _strUserId;
    }

    /**
     * @param strUserId the _strUserId to set
     */
    public void setStrUserId(String strUserId) {
        this._strUserId = strUserId;
    }

    /**
     * @return the _strUserName
     */
    public String getStrUserName() {
        return _strUserName;
    }

    /**
     * @param strUserName the _strUserName to set
     */
    public void setStrUserName(String strUserName) {
        this._strUserName = strUserName;
    }

    /**
     * @return the _strpassword
     */
    public String getStrpassword() {
        return _strpassword;
    }
    
  

    /**
     * @param strpassword the _strpassword to set
     */
    public void setStrpassword(String strpassword) {
        this._strpassword = strpassword;
    }

    /**
     * @return the _strStatus
     */
    public String getStrStatus() {
        return _strStatus;
    }

    /**
     * @param strStatus the _strStatus to set
     */
    public void setStrStatus(String strStatus) {
        this._strStatus = strStatus;
    }

    /**
     * @return the _strCurrentStatus
     */
    public String getStrCurrentStatus() {
        return _strCurrentStatus;
    }

    /**
     * @param strCurrentStatus the _strCurrentStatus to set
     */
    public void setStrCurrentStatus(String strCurrentStatus) {
        this._strCurrentStatus = strCurrentStatus;
    }

    /**
     * @return the valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @param valid the valid to set
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * @return the _paramID
     */
    public String getParamID() {
        return _paramID;
    }

    /**
     * @param _paramID the _paramID to set
     */
    public void setParamID(String _paramID) {
        this._paramID = _paramID;
    }

    /**
     * @return the _strEditingUser
     */
    public String getStrEditingUser() {
        return _strEditingUser;
    }

    /**
     * @param _strEditingUser the _strEditingUser to set
     */
    public void setStrEditingUser(String _strEditingUser) {
        this._strEditingUser = _strEditingUser;
    }

    /**
     * @return the _strLastLogin
     */
    public String getStrLastLogin() {
        return _strLastLogin;
    }

    /**
     * @param _strLastLogin the _strLastLogin to set
     */
    public void setStrLastLogin(String _strLastLogin) {
        this._strLastLogin = _strLastLogin;
    }

    /**
     * @return the _code
     */
    public String getCode() {
        return _code;
    }

    /**
     * @param _code the _code to set
     */
    public void setCode(String _code) {
        this._code = _code;
    }
}
