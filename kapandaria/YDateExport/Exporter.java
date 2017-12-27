/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kapandaria.YDateExport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Orr Dvori
 */
public class Exporter
{
    public static void main(String[] args)  {
        
        try {
            java.io.FileOutputStream t=new FileOutputStream(new File("C:\\Projects\\test.html"));
            t.write("שלום".getBytes(Charset.forName("UTF8")));
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
