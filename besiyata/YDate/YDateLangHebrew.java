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
    static final String ShortGregMonthTokens []=
    {"ינו", "פב", "מרס", 
                 "אפר", "מאי", "יונ", "יול", 
                 "אוג", "ספט", "אוק", 
                 "נוב", "דצמ"};
    public static final String[] zodiac_names =
            {
                    "טלה", "שור", "תאומים", "סרטן", "אריה", "בתולה", "מאזנים", "עקרב", "קשת", "גדי", "דלי", "דגים"
            };
    /*
    fire: Aries Leo Sagittarius
    earth: Taurus Virgo Capricorn
    wind: Gemini Libra Aquarius
    water: Cancer Scorpio Pisces
       fire doesn't connect with water
       earth doesn't connect with wind
    */
    public static final String[] four_elements_names =
            {
                    "אש", "עפר", "רוח", "מים"
            };
    public static final String[] star_names =
            {
                    "כוכב", "לבנה", "שבתאי", "צדק", "מאדים", "חמה", "נגה"
            };
    
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
    public String getShortGregMonthToken(int token)
    {
        return ShortGregMonthTokens[token];
    }

    @Override
    public String getZodiacToken(int token)
    {
        return zodiac_names[token];
    }

    @Override
    public String getElementToken(int token) {
        return four_elements_names[token];
    }

    @Override
    public String getStarToken(int token) {
        return star_names[token];
    }

    @Override
    public String getEventToken(int token)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNumber(int num)
    {
        return Format.HebIntString(num, true);
    }

    @Override
    public String FormatGregorianDate(int day, int month, int year) {
        return Integer.toString(day)+" ב"+getGregMonthToken(month-1)+" "+Integer.toString(year);
    }

    @Override
    public String FormatJewishDate(int day, int monthId, int year) {
        return Format.HebIntString(day, true)+" ב"+getHebMonthToken(monthId)+" "+Format.HebIntString(year, true);
    }
    @Override
    public String FormatPeriod(int monthId) {
        return "תקופת "+getHebMonthToken(monthId);
    }

    @Override
    public String getShortWeekToken(int token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getShortHebMonthToken(int token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
