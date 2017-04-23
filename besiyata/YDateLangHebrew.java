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
public class YDateLangHebrew extends YDateLanguage
{

    static final String WeekTokens []={"ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"};
    static final String HebMonthTokens []={"תשרי", "חשוון", "כסלו", "טבת",
                "שבט", "אדר",
                "אדר א'",
                "אדר ב'",
                "ניסן", "אייר",
                "סיוון", "תמוז", "אב", "אלול"};
    static final String GregMonthTokens []=
    {"ינואר", "פברואר", "מרס", 
                 "אפריל", "מאי", "יוני", "יולי", 
                 "אוגוסט", "ספטמבר", "אוקטובר", 
                 "נובמבר", "דצמבר"};
    
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
        return Format.HebIntSubString(num, true, true);
    }

}
