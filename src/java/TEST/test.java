/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TEST;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Madhawa_4799
 */
public class test {

    public static void main(String[] args) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

       
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -2);
            Date todate1 = cal.getTime();
            String fromdate = dateFormat.format(todate1);
            System.out.println(fromdate);

        } catch (Exception e) {
        }
    }

}
