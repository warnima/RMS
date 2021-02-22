/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAutoFillBean;

import DBAutoFillBeans.*;
import org.json.JSONArray;

/**
 *
 * @author Madhawa_4799
 */
public class AutoFillBean {

    private JSONArray _fillData;
    private String[] _strArrData;
    private String _strData;

    /**
     * @
    return the _fillData

    */
    public JSONArray getFillData() {
        return _fillData;
    }

    /**
     * @param fillData the _fillData to set
     */
    public void setFillData(JSONArray fillData) {
        this._fillData = fillData;
    }
    
     public JSONArray customerAutoFill() {
        try {
            comboDAO comboDAO = new comboDAO();
            _fillData = comboDAO.getCustomers();
        } catch (Exception e) {
        }
        return _fillData;
    }
    
    
    
}
