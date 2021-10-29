/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.account;

import java.io.Serializable;
import java.sql.Date;
import tannv.util.Constants;
import tannv.util.Util;

/**
 *
 * @author TanNV
 */
public class AccountDTO implements Serializable{
    private String email, password;
    private int status,role;
    private Date createDate;

    public AccountDTO() {
    }

    public AccountDTO(String email, String password) {
        // default to store a user
        this.email = email;
        this.password = password;
        this.role = Constants.USER_ROLE;
        this.status = Constants.ACTIVE_ACCOUNT;
        java.util.Date today = new java.util.Date();
        this.createDate = Util.utilDateToSqlDate(today);
    }
    

    public AccountDTO(String email, String password, int status, int role, Date createDate) {
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
        this.createDate = createDate;
    }

    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
