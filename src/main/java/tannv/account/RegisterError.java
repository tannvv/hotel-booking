/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.account;

import java.sql.SQLException;
import javax.naming.NamingException;
import tannv.infor_user.InforUserDTO;

/**
 *
 * @author TanNV
 */
public class RegisterError {
    private String email, password, rePassword, name, phone,address;

    public RegisterError() {
    }

    public RegisterError(String email, String password, String rePassword, String name, String phone, String address) {
        this.email = email;
        this.password = password;
        this.rePassword = rePassword;
        this.name = name;
        this.phone = phone;
        this.address = address;
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

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "RegisterError{" + "email=" + email + "\n, password=" + password + "\n, rePassword=" + rePassword + "\n, name=" + name + "\n, phone=" + phone + "\n, address=" + address + '}';
    }
    
    
    
    public static RegisterError checkValidAccount(AccountDTO account, InforUserDTO user, String rePassword) throws NamingException, SQLException{
        RegisterError error = new RegisterError();
        if (!account.getEmail().matches("[a-zA-Z0-9._]+@{1}+[a-zA-Z0-9.]{5,30}")) {
            error.setEmail("Email invalid, format email have to ***@***, don't have special character, 5-30 characters");
        }
        AccountDAO dao = new AccountDAO();
        boolean chechExist = dao.checkEmail(account.getEmail());
        if (chechExist) {   // email is existed
            error.setEmail("Email is existed");
        }
        if (!account.getPassword().matches("[a-zA-Z0-9]{5,30}")) {
            error.setPassword("Invalid password, password have to 5-30 character and don't have any special chracter");
        }
        if (!account.getPassword().equals(rePassword)) {
            error.setRePassword("Re password not same password");
        }
        
        if (user.getName().trim().length() < 5 || user.getName().trim().length() >= 30) {
            error.setName("Invalid name, name have to 5-30 character and don't have any special chracter");
        }
        
        if (user.getAddress().trim().length() < 5 || user.getAddress().trim().length() > 100) {
            error.setAddress("Invalid address, address have to 5-100 character and don't have any special chracter");
        }
        if (!user.getPhone().matches("[0-9]{5,15}")) {
            error.setPhone("Invalid phone, phone have to 5-30 character, contain only number");
        }
        return error;
    } 
}
