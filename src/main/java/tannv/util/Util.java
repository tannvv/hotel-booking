/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tannv.util;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author TanNV
 */
public class Util {

    public static String toSha256(String password) {
        String sha256hex = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;
    }

    public static int numberPage(int element, int size) {
        int result = 0;
        if (element <= 0) {
            throw new IllegalArgumentException();
        }
        result = element / size;
        if ((element % size) != 0) {
            result = result + 1;
        }
        return result;
    }

    public static java.sql.Date utilDateToSqlDate(java.util.Date utilDate) {
        java.sql.Date sqlDate = null;
        if (utilDate != null) {
            sqlDate = new java.sql.Date(utilDate.getTime());
        }
        return sqlDate;
    }

    public static java.util.Date sqlDateToUtilDate(java.sql.Date sqlDate) {
        java.util.Date utilDate = null;
        if (sqlDate != null) {
            utilDate = new java.util.Date(sqlDate.getTime());
        }
        return utilDate;
    }
    
    public static long getSubtractDate(Date startDate, Date endDate){
        long duration = endDate.getTime() - startDate.getTime();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
        return diffInDays;
    }
    
    public static long getNumberDay(Date checkIn, Date checkOut){
        long duration =  checkOut.getTime() - checkIn.getTime();
        long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
        return diffInDays;
    }
    
    public static void sendEmail(String emailInput, int random){
        try {            
           Email email = new SimpleEmail();

           // Cấu hình thông tin Email Server
           email.setHostName("smtp.googlemail.com");
           email.setSmtpPort(465);
           email.setAuthenticator(new DefaultAuthenticator(Constants.MY_EMAIL,
                   Constants.MY_PASSWORD));
           
           // Với gmail cái này là bắt buộc.
           email.setSSLOnConnect(true);
           
           // Người gửi
           email.setFrom(Constants.MY_EMAIL);
           
           // Tiêu đề
           email.setSubject("Xac nhan thanh toan");
           
           // Nội dung email
           email.setMsg("Ma xac nhan cua ban la : " + random);
           
           // Người nhận
           email.addTo(emailInput);            
           email.send();
           System.out.println("Sent!!");
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
    
    public static int getRandom(){
        return (int)(Math.random() * (9999 - 1000) + 1) + 1000;
    }
    
    public static int getNumberOrder(int[] numberHotel){
        int result = 0;
        int size = numberHotel.length;
        for (int i = 0; i < size; i++) {
            for (int j = i+1; j < size; j++) {
                if (numberHotel[i] == numberHotel[j]) {
                    numberHotel[j] = -1;
                }
            }
        }     
        for (int i = 0; i < size; i++) {
            if (numberHotel[i] != -1) {
                result = result+1;
            }
        }
        
        return result ;
    }
}
