/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TEST;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Madhawa_4799
 */
public class cop {

    public static void main(String[] args) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date formatted_tenor_date = formatter.parse("01/01/2019");
            Date formatted_value_date = formatter.parse("01/02/2019");
            System.out.println(formatted_value_date.compareTo(formatted_tenor_date));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
