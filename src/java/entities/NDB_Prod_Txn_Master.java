/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Dasun_5282
 */
public class NDB_Prod_Txn_Master {

    private String idndb_customer_define_buyer_id;
    private String idndb_customer_define_seller_id;
    private double pdc_chq_amu;
    private String pdc_req_financing;
    private double pdc_chq_discounting_amu;

    public String getIdndb_customer_define_buyer_id() {
        return idndb_customer_define_buyer_id;
    }

    public void setIdndb_customer_define_buyer_id(String idndb_customer_define_buyer_id) {
        this.idndb_customer_define_buyer_id = idndb_customer_define_buyer_id;
    }

    public String getIdndb_customer_define_seller_id() {
        return idndb_customer_define_seller_id;
    }

    public void setIdndb_customer_define_seller_id(String idndb_customer_define_seller_id) {
        this.idndb_customer_define_seller_id = idndb_customer_define_seller_id;
    }

    public double getPdc_chq_amu() {
        return pdc_chq_amu;
    }

    public void setPdc_chq_amu(double pdc_chq_amu) {
        this.pdc_chq_amu = pdc_chq_amu;
    }

    public String getPdc_req_financing() {
        return pdc_req_financing;
    }

    public void setPdc_req_financing(String pdc_req_financing) {
        this.pdc_req_financing = pdc_req_financing;
    }

    public double getPdc_chq_discounting_amu() {
        return pdc_chq_discounting_amu;
    }

    public void setPdc_chq_discounting_amu(double pdc_chq_discounting_amu) {
        this.pdc_chq_discounting_amu = pdc_chq_discounting_amu;
    }

}
