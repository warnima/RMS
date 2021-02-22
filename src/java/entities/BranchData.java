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
public class BranchData {

    private String branch_id;
    private String idndb_bank_master_file;
    private String idndb_branch_master_file;
    private String branch_name;

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getIdndb_bank_master_file() {
        return idndb_bank_master_file;
    }

    public void setIdndb_bank_master_file(String idndb_bank_master_file) {
        this.idndb_bank_master_file = idndb_bank_master_file;
    }

    public String getIdndb_branch_master_file() {
        return idndb_branch_master_file;
    }

    public void setIdndb_branch_master_file(String idndb_branch_master_file) {
        this.idndb_branch_master_file = idndb_branch_master_file;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

}
