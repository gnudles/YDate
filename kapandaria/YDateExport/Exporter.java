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
import kapandaria.YDate.Format;
import kapandaria.YDate.JewishDate;
import kapandaria.YDate.TorahReading;
import kapandaria.YDate.YDateDual;
import kapandaria.YDate.YDateLanguage;

/**
 *
 * @author Orr Dvori
 */
public class Exporter
{
    public static void main(String[] args)  {
        
        

        //System.out.println("kapandaria.YDateExport.Exporter.main()");
        //System.out.println(x.hebrewDate().dayString(YDateLanguage.Language.HEBREW));
        System.out.println("<!DOCTYPE html>\n" +
"<html>\n" +
"<head>\n" +
"<style>\n" +
"table {\n" +
"  font-family: arial, sans-serif;\n" +
"  border-collapse: collapse;\n" +
"  width: 100%;\n" +
"}\n" +
"* {\n" +
"  text-align: right;\n" +
"  direction: rtl;\n" +
"}\n" +
"td, th {\n" +
"  border: 1px solid #dddddd;\n" +
"  padding: 8px;\n" +
"}\n" +
"\n" +
"tr:nth-child(even) {\n" +
"  background-color: #fbfbfb;\n" +
"}\n" +
"</style>\n" +
"</head>\n" +
"<body>");
        if (args.length == 0)
            return;
        String larg = args[args.length-1];
        YDateDual x=YDateDual.getNow();
        
        
        /*if (larg.toLowerCase() == "now" || larg.toLowerCase() == "this")
        {
            x=YDateDual.getNow();
        }
        else if (larg.toLowerCase() == "next")*/
        {
            x=YDateDual.getNow();
            x.step(YDateDual.STEP_TYPE.HEB_YEAR_FORWARD); // print next year
        }
        
        YDateLanguage heb_le = YDateLanguage.getLanguageEngine(YDateLanguage.Language.HEBREW);
        System.out.println("<h2>"+Format.HebIntString(x.hebrewDate().year(), true)+"</h2>");
        x.hebrewDate().setByGDN(x.hebrewDate().yearFirstDayGDN());
        int monthsInYear = x.hebrewDate().monthsInYear();
        for (int m = 1; m <= monthsInYear; ++m)
        {
            System.out.println("<h3>"+x.hebrewDate().monthName(heb_le)+"</h3>");
            System.out.println("<table>");
            int month_length= x.hebrewDate().monthLength();
            for (int i=1; i<= month_length;++i)
            {
                System.out.println("<tr><td>" +x.hebrewDate().dayString(heb_le)+ "</td>");
                System.out.println("<td>" +TorahReading.GetSidra(x.hebrewDate(), false, heb_le)
                        + "</td>");
                System.out.println("<td>" +x.getEventString(YDateLanguage.Language.HEBREW, false)+ "</td></tr>");
                x.hebrewDate().seekBy(1);
            }
            System.out.println("</table>");
        }
        
        System.out.println("<h3>"+"מידע תקופות"+"</h3>");
        System.out.println("<table>");
        for (int y = 5700; y <= 5900; ++y)
        {

                
                x.hebrewDate().setByYearMonthIdDay(y, JewishDate.M_ID_TISHREI, 1);
                System.out.println("<tr><td>" +Format.HebIntString(y, true)+ "</td>");
                String Meyab = "";
                if (x.hebrewDate().TkufatNisanMeshaberetIlanot())
                {
                    Meyab= "תקופת ניסן משברת אילנות";
                }
                if (x.hebrewDate().TkufatTavetMeyabeshetZeraim())
                {
                    if (!Meyab.isEmpty()) Meyab+=", ";
                    Meyab= "תקופת טבת מייבשת זרעים";
                }
                System.out.println("<td>" +Meyab+ "</td>");
                System.out.println("<td>" +x.hebrewDate().yearSign()+ "</td>");
                System.out.println("<td>" +x.hebrewDate().yearLength()+ "</td>");
                System.out.println("<td>" +(x.hebrewDate().yearOfColdWinter()?"חורף קר":"")+ "</td>");
                System.out.println("<td>" +(x.hebrewDate().ShmitaLabel(heb_le))+ "</td>");
                System.out.println("</tr>");
        }
        System.out.println("</table>");
        System.out.println("</body>\n" +
"</html>");


        
        /*try {
            java.io.FileOutputStream t=new FileOutputStream(new File("C:\\Projects\\test.html"));
            t.write("שלום".getBytes(Charset.forName("UTF8")));
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(Exporter.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        
    }
}
