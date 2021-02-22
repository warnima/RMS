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
public class FIllDataTableBean {
    private JSONArray _fillData;
    private String[] _strArrData;
    private String _strData;
    
     public JSONArray getFillData() {
        return _fillData;
    }
     public void setFillData(JSONArray fillData) {
        this._fillData = fillData;
    }
     
     public String getBankData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getBankData(user_id);

    }
     
      public String getHolidayData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getHolidayData(user_id);

    }
      
      
       public String getCWPDCData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getCWPDCData(user_id);

    }
       
            public String getCWRFENABLEPDCData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getCWRFENABLEPDCData(user_id);

    }
       
       
        public String getCWPDCUnAuthData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getCWPDCUnAuthData(user_id);

    }
       
        public String getRFPDCData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getRFPDCData(user_id);

    }
        
         public String getRFPDCUnAuthData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getRFPDCUnAuthData(user_id);

    }
       
      
      public String getUnAuthHolidayData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getUnAuthHolidayData(user_id);

    }
     
     public String getUnAuthBankData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getUnAuthBankData(user_id);

    }
     
     public String getUserData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getUserData(user_id);

    }
     
     public String getUserLevelsData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getUserLevelsData(user_id);

    }
     
     public String getuBranchData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getBranchData(user_id);

    }
     
     public String getUnAuthBranchData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getUnAuthBranchData(user_id);

    }
     public String getUserFormsData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getUserFormsData(user_id);

    }
     
      public String getReturnFileData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getReturnFileData(user_id);

    }
      
       public String getAdditionalDayData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getAdditionalDayData(user_id);

    }
     
     public String getIndustryData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getIndustryData(user_id);

    }
     
       public String getGeoMrketData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getGeoMarketData(user_id);

    }
       
        public String getCustomerDifineData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getCustomerDifineData(user_id);

    }
        
         public String getCustomerDifineUnAuthData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getCustomerDifineUnAuthData(user_id);

    }
        
         public String getCustomerRelationEstblishData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getCustomerRelationEstblishData(user_id);

    }
         
             public String getCustomerRelationEstblishUnAuthData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getCustomerRelationEstblishUnAuthData(user_id);

    }
        
           public String getCustomerProdMapData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getCustomerProdMapData(user_id);

    }
           
              public String getCustomerProdMapAuthData(String user_id) {
        TableDAO tdo = new TableDAO();
        return tdo.getCustomerProdMapUnauthData(user_id);

    }
       
    
}
