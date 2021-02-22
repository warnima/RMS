/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAutoFillBeans;

import org.json.JSONArray;

/**
 *
 * @author Madhawa_4799
 */
public class FillDataComboBean {

    private JSONArray _fillData;
    private String[] _strArrData;
    private String _strData;

    public JSONArray getFillData() {
        return _fillData;
    }

    public void setFillData(JSONArray fillData) {
        this._fillData = fillData;
    }

    public JSONArray getBankDataLi() {
        try {
            comboDAO tcmbodo = new comboDAO();
            _fillData = tcmbodo.getBankDataLi();
        } catch (Exception e) {
        }
        return _fillData;
    }

    public String getFileBankCodeDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getFileBankCodeDataCmb(user_id);

    }

    public String getFileBankNameDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getFileBankNameDataCmb(user_id);

    }
    
    public String getFormAttachedMenusDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getFormAttachedMenusDataCmb(user_id);

    }
    
     public String getFileUserLevelsDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getFileUserLevelsDataCmb(user_id);

    }

    public String getFileIndustryActiveDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getFileIndustryActiveDataCmb(user_id);

    }

    public String getFileGeoMrketActiveDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getFileGeoMrketActiveDataCmb(user_id);

    }

    public String getCust_name_ActiveDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getCust_name_ActiveDataCmb(user_id);

    }

    public String getCust_Id_ActiveDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getCust_Id_ActiveDataCmb(user_id);

    }

    public String getCust_Id_ActiveSellerDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getCust_Id_ActiveSellerDataCmb(user_id);

    }
    
    public String getCust_Id_ActiveRFSellerDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getCust_Id_ActiveRFSellerDataCmb(user_id);

    }
    
    public String getCust_Id_ActiveCWSellerDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getCust_Id_ActiveCWSellerDataCmb(user_id);

    }
    
     public String getCust_Name_ActiveSellerDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getCust_Name_ActiveSellerDataCmb(user_id);

    }
     
     public String getCust_Name_ActiveRFSellerDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getCust_Name_ActiveRFSellerDataCmb(user_id);

    }
      public String getCust_Name_ActiveCWSellerDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getCust_Name_ActiveCWSellerDataCmb(user_id);

    }
     
     public String getCust_Id_ActiveBuyerDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getCust_Id_ActiveBuyerDataCmb(user_id);

    }
    
     public String getCust_Name_ActiveBuyerDataCmb(String user_id) {
        comboDAO tcmbodo = new comboDAO();
        return tcmbodo.getCust_Name_ActiveSBuyerDataCmb(user_id);

    }
}
