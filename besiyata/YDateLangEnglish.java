/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package besiyata.YDate;

/**
 *
 * @author orr
 */
public class YDateLangEnglish extends YDateLanguage
{
        static final String WeekTokens []={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    static final String HebMonthTokens []={"Tishrei", "Cheshvan", "Kislev", "Tevet",
                "Shevat", "Adar",
                "Adar I",
                "Adar II",
                "Nisan", "Iyar",
                "Sivan", "Tammuz", "Av", "Elul"};
    static final String GregMonthTokens []=
    {"January", 
                 "February",
                 "March",
                 "April",
                 "May",
                 "June",
                 "July",
                 "August",
                 "September",
                 "October",
                 "November",
                 "December"};
    
    @Override
    public String getWeekToken(int token)
    {
        return WeekTokens[token];
    }

    @Override
    public String getHebMonthToken(int token)
    {
        return HebMonthTokens[token];
    }

    @Override
    public String getGregMonthToken(int token)
    {
        return GregMonthTokens[token];
    }

    @Override
    public String getZodiacToken(int token)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String getEventToken(int token)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNumber(int num)
    {
        return String.valueOf(num);
    }
}
