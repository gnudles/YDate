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

import com.sun.jna.Library;
import com.sun.jna.Native;
import kapandaria.YDate.ADate;

/**
 *
 * @author Orr Dvori
 */
public class Exporter
{
    public interface WkGtkPrinterLibrary extends Library {
        WkGtkPrinterLibrary INSTANCE = (WkGtkPrinterLibrary)
            Native.load(("libwkgtkprinter.so"), //depends on gtk+3.0 and Webkitgtk-4.0
                                WkGtkPrinterLibrary.class);

        void html2pdf(String in_uri, String html_txt, String base_uri, String out_uri, String print_settings);
    }
    
    public static void main(String[] args)  {
        
        
        
        StringBuilder html_text_builder = new StringBuilder();
        //html_text_builder.append("kapandaria.YDateExport.Exporter.main()");
        //html_text_builder.append(x.hebrewDate().dayString(YDateLanguage.Language.HEBREW));
        html_text_builder.append("<!DOCTYPE html>\n" +
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
        /*if (args.length == 0)
            return;
        String larg = args[args.length-1];*/
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
        html_text_builder.append("<h2>"+Format.HebIntString(x.hebrewDate().year(), true)+"</h2>");
        x.hebrewDate().setByGDN(x.hebrewDate().yearFirstDayGDN());
        int monthsInYear = x.hebrewDate().monthsInYear();
        for (int m = 1; m <= monthsInYear; ++m)
        {
            html_text_builder.append("<h3>"+x.hebrewDate().monthName(heb_le)+"</h3>");
            html_text_builder.append("<table>");
            int month_length= x.hebrewDate().monthLength();
            for (int i=1; i<= month_length;++i)
            {
                html_text_builder.append("<tr><td>" +x.hebrewDate().dayString(heb_le)+ "</td>");
                html_text_builder.append("<td>" +TorahReading.GetSidra(x.hebrewDate(), false, heb_le)
                        + "</td>");
                html_text_builder.append("<td>" +x.getEventString(YDateLanguage.Language.HEBREW, false)+ "</td></tr>");
                x.hebrewDate().seekBy(1);
            }
            html_text_builder.append("</table>");
        }
        
        html_text_builder.append("<h3>"+"מידע תקופות"+"</h3>");
        html_text_builder.append("<table>");
        for (int y = 5700; y <= 5900; ++y)
        {

                
                x.hebrewDate().setByYearMonthIdDay(y, JewishDate.M_ID_TISHREI, 1);
                html_text_builder.append("<tr><td>" +Format.HebIntString(y, true)+ "</td>");
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
                html_text_builder.append("<td>" +Meyab+ "</td>");
                html_text_builder.append("<td>" +x.hebrewDate().yearSign()+ "</td>");
                html_text_builder.append("<td>" +x.hebrewDate().yearLength()+ "</td>");
                html_text_builder.append("<td>" +(x.hebrewDate().yearOfColdWinter()?"חורף קר":"")+ "</td>");
                html_text_builder.append("<td>" +(x.hebrewDate().ShmitaLabel(heb_le))+ "</td>");
                html_text_builder.append("</tr>");
        }
        html_text_builder.append("</table>");
        html_text_builder.append("</body>\n" +
"</html>");
        String html_text= html_text_builder.toString();
        //System.out.println(html_text);
        
        //WkGtkPrinterLibrary.INSTANCE.html2pdf(null,html_text,null,"file:///tmp/java1.pdf",null);

        LuahTahara(5780);
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
    static void LuahTahara(int year)
    {
        JewishDate date_orig=JewishDate.createByYearMonthIdDay(year, JewishDate.M_ID_TISHREI, 1);
        //x.hebrewDate().setByGDN(ADate.getPrevious(ADate.SUNDAY, x.hebrewDate().yearFirstDayGDN()) - 5*7); // get 5 weeks back from sunday before new hebrew year.
        JewishDate x = new JewishDate();
        x.MimicDate(date_orig);
        StringBuilder html_text_builder = new StringBuilder();
        html_text_builder.append("<!DOCTYPE html>\n" +
"<html>\n" +
"<head>\n" +
"<style>\n" +
"table {\n" +
"  font-family: arial, sans-serif;\n" +
                "font-size: 12px;"+
"  border-collapse: collapse;\n" +
"  width: 100%;\n" +
"}\n" +
"* {\n" +
"  text-align: right;\n" +
"  direction: rtl;\n" +
"}\n" +
"td, th {\n" +
"  border: 1px solid #666;\n" +
"  padding: 8px;\n" +
"text-align: center;\n"+
"}\n" +
".blacktop{background-color: #101010;border-bottom:dashed 1px #bbb;}\n" +
".blackbtm{background-color: #101010;border-top:dashed 1px #bbb;}\n" +
".night{background-color: #cacaca;border-bottom:dashed 1px #666; padding-top:0; }\n" +
".divnight{font-size: 10px;background-color: #ff000000 ; margin-top:-10px; text-align: center; }\n" +
".day{border-top:none;}\n" +
".nighthead{background-color: #cacaca; }\n" +
".dayhead, .nighthead{border-left:solid 4px #666;font-weight: bold;}\n" +
"</style>\n" +
"</head>\n" +
"<body>");
        YDateLanguage heb_le = YDateLanguage.getLanguageEngine(YDateLanguage.Language.HEBREW);
        html_text_builder.append("<h3>"+"לוח טהרה לשנת " + Format.HebIntString(x.year(),false)  +"</h3>");
        html_text_builder.append("<table style=\"border: solid 4px #666;\">");
        html_text_builder.append("<tr>");
        html_text_builder.append("<th style=\"border-bottom:solid 2px #666;\">חודש</th>");
        html_text_builder.append("<th style=\"border-left:solid 4px #666;border-bottom:solid 2px #666;\">עונה</th>");
        for (int i = 0; i < 30; ++i)
        {
            html_text_builder.append("<th style=\"border-bottom:solid 2px #666;\">"+ Format.HebIntSubString(i+1, false, false)+"</th>");
        }
        html_text_builder.append("</tr>");
        int n_months= x.monthsInYear();
        for (int m = 0; m< n_months;++m)
        {
            html_text_builder.append("<tr>");
            int month_l = x.monthLength();
            
            int day_in_week = x.dayInWeekEnum();
            html_text_builder.append("<td rowspan=\"2\">"+ heb_le.getHebMonthToken(x.monthID()) + "</td>");
            html_text_builder.append("<td class=\"nighthead\">"+ "לילה" + "</td>");
            
            for (int i = 0; i < month_l; ++i)
            {
                html_text_builder.append("<td class=\"night\"><div class=\"divnight\">"+ heb_le.getWeekToken(day_in_week%7)+"</div></td>");
                day_in_week ++;
            }
            if (month_l < 30)
            {
                html_text_builder.append("<td class=\"blacktop\"></td>");
            }
            
            html_text_builder.append("</tr>");
                        html_text_builder.append("<tr>");

            
            html_text_builder.append("<td class=\"dayhead\">"+ "יום" + "</td>");
            
            for (int i = 0; i < month_l; ++i)
            {
                html_text_builder.append("<td class=\"day\"></td>");
                day_in_week ++;
            }
            if (month_l < 30)
            {
                html_text_builder.append("<td class=\"blackbtm\"></td>");
            }
            
            
            x.stepMonthForward(false);
        }
        html_text_builder.append("</table>");
        
        //draw second table
        x.MimicDate(date_orig);
        x.setByGDN(ADate.getPrevious(ADate.SUNDAY, x.GDN()));
        html_text_builder.append("<br/><br/><br/><br/><br/><br/><br/><br/><table style=\"border: solid 4px #666;\">");
        html_text_builder.append("<tr>");
        html_text_builder.append("<th style=\"border-bottom:solid 2px #666;\">חודש</th>");
        html_text_builder.append("<th style=\"border-left:solid 4px #666;border-bottom:solid 2px #666;\">עונה</th>");
        for (int i = 0; i < 28; ++i)
        {
            html_text_builder.append("<th style=\"font-weight: normal;border-bottom:solid 2px #666;\">"+ heb_le.getShortWeekToken(i%7)+"</th>");
        }
        html_text_builder.append("</tr>");
        
        while(x.year() <= date_orig.year())
        {
            html_text_builder.append("<tr>");
            int month_l = x.monthLength();
            int m_id = x.monthID();
            
            String months_to_display;
            months_to_display = Format.HebIntSubString(x.dayInMonth(), false, false) +" "+ heb_le.getHebMonthToken(x.monthID())+" -<br/>";
            x.seekBy(27);
            months_to_display += Format.HebIntSubString(x.dayInMonth(), false, false) +" "+ heb_le.getHebMonthToken(x.monthID());
            
            x.seekBy(-27);
            html_text_builder.append("<td rowspan=\"2\">"+ months_to_display + "</td>");
            
            html_text_builder.append("<td class=\"nighthead\">"+ "לילה" + "</td>");
            
            for (int i = 0; i < 28; ++i)
            {
                html_text_builder.append("<td class=\"night\"><div class=\"divnight\">"+ Format.HebIntSubString(x.dayInMonth(), false, false)+"</div></td>");
                x.seekBy(1);
            }
            
            
            html_text_builder.append("</tr>");
                        html_text_builder.append("<tr>");

            
            html_text_builder.append("<td class=\"dayhead\">"+ "יום" + "</td>");
            
            for (int i = 0; i < 28; ++i)
            {
                html_text_builder.append("<td class=\"day\"></td>");
            }
            
            

        }
        html_text_builder.append("</table>");
        
        
        html_text_builder.append("</body>\n" +
"</html>");
        
        String html_text= html_text_builder.toString();
        System.out.println(html_text);
        String print_settings = "[Print Settings]\n"+
    "quality=high\n"+
    "resolution-x=320\n"+
    "resolution-y=320\n"+
    "resolution=320\n"+
    "output-file-format=pdf\n"+
    "printer=Print to File\n"+
    "page-set=all\n"+
    "\n"+
            "[Page Setup]\n"+
            "PPDName=A3\n"+
            "DisplayName=A3\n"+
            "Width=297\n"+
            "Height=420\n"+
            "MarginTop=6.3499999999999996\n"+
            "MarginBottom=14.224\n"+
            "MarginLeft=6.3499999999999996\n"+
            "MarginRight=6.3499999999999996\n"+
            "Orientation=landscape\n";
        WkGtkPrinterLibrary.INSTANCE.html2pdf(null,html_text,null,"file:///tmp/tahara.pdf", print_settings);
        try
        {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create("file:///tmp/tahara.pdf"));
        }catch(IOException io)
        {
            
        }
        
    }
}
