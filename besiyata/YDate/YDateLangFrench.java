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
public class YDateLangFrench extends YDateLangEnglish
{
    static final String WeekTokens []={"Dimanche","Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi"};

    static final String GregMonthTokens []=
    {"Janvier", 
                 "Février",
                 "Mars",
                 "Avril",
                 "Mai",
                 "Juin",
                 "Juillet",
                 "Août",
                 "Septembre",
                 "Octobre",
                 "Novembre",
                 "Décembre"};

    public static final String[] zodiac_names =
            {
                    "Bélier","Taureau","Gémeaux","Cancer","Leo","Vierge","Libra","Scorpion","Sagittaire","Capricorne","Verseau","Poissons"
            };

    public static final String[] four_elements_names =
            {
                    "feu", "terre", "vent", "eau"
            };
    public static final String[] star_names =
            {
                    "Mercure","Lune","Saturne","Jupiter","Mars","Soleil","Vénus"
            };

    @Override
    public String getWeekToken(int token)
    {
        return WeekTokens[token];
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
