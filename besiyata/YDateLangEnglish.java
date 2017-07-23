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

    public static final String[] zodiac_names =
            {
                    "Aries","Taurus","Gemini","Cancer","Leo","Virgo","Libra","Scorpio","Sagittarius","Capricorn","Aquarius","Pisces"
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
                    "fire", "earth", "wind", "water"
            };
    public static final String[] star_names =
            {
                    "Mercury","Moon","Saturn","Jupiter","Mars","Sun","Venus"
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
        return String.valueOf(num);
    }

    @Override
    public String FormatGregorianDate(int day, int month, int year) {
        return getGregMonthToken(month-1)+" "+Integer.toString(day)+", "+Integer.toString(year);
    }

    @Override
    public String FormatJewishDate(int day, int monthId, int year) {
        return getHebMonthToken(monthId)+" "+Integer.toString(day)+", "+Integer.toString(year);
    }

    @Override
    public String FormatPeriod(int monthId) {
        return getHebMonthToken(monthId)+" Period";
    }
}
