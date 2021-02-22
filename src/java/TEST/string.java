/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TEST;

import java.io.File;

/**
 *
 * @author Madhawa_4799
 */
public class string {

    public static void main(String[] args) {
        try {

            File dir = new File("D:\\RMSSYSTEM\\DAILY DB DUMPUS\\DAYEND\\AFTER_DAYEND\\17062019 053503AFTER_DAYENDPROCESS");
            if (dir.isDirectory() == false) {
                System.out.println("Not a directory. Do nothing");
                return;
            }
            File[] listFiles = dir.listFiles();
            for (File file : listFiles) {
                System.out.println("Deleting " + file.getName());
                file.delete();
            }
            //now directory is empty, so we can delete it
            System.out.println("Deleting Directory. Success = " + dir.delete());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
