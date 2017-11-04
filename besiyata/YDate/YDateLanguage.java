
package besiyata.YDate;


import java.util.TimeZone;

public abstract class YDateLanguage
{

    public enum Language {
        HEBREW,
        ENGLISH,
        YIDDISH,
        SPANISH,
        FRENCH,
        RUSSIAN,
        GERMAN,
        PERSIAN,
        ARABIC,
        ARAMIC,
        ITALIAN
    }
    public static Language guessLanguageFromLocation(TimeZone tz)
    {
        Language language;
        if (tz.equals(TimeZone.getTimeZone("Asia/Jerusalem")))
        {
            language= Language.HEBREW;
        }
        else if (tz.equals(TimeZone.getTimeZone("Europe/Paris")))
        {
            language= Language.FRENCH;
        }
        else if (tz.equals(TimeZone.getTimeZone("Europe/Madrid")))
        {
            language= Language.SPANISH;
        }
        else
        {
            language= Language.ENGLISH;
        }
        return language;
    }
    public static Language getLanguageFromCode(String code)
    {
        Language language = Language.ENGLISH;
        if (code.toLowerCase().equals("en"))
            language=Language.ENGLISH;
        else if (code.toLowerCase().equals("he"))
            language=Language.HEBREW;
        else if (code.toLowerCase().equals("fr"))
            language=Language.FRENCH;
        else if (code.toLowerCase().equals("sp"))
            language=Language.SPANISH;
        return language;
    }
    public static YDateLanguage getLanguageEngine(Language language){
        return LanguageEngine[language.ordinal()];
    }

    static YDateLanguage[] LanguageEngine={
            new YDateLangHebrew(),
            new YDateLangEnglish(),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    };
    static final int SUNDAY = 0;//Sun - Sunne in old english
    static final int MONDAY = 1;//Moon - M?na in old english
    static final int TUESDAY = 2;//Mars - T?w in old english
    static final int WEDNESDAY = 3;//Mercury - W?den in old english
    static final int THURSDAY = 4;//Jupiter - ?unor in old english
    static final int FRIDAY = 5;//Venus - frig in old english
    static final int SATURDAY = 6;//Saturn - S?tern in old english
    public abstract String getWeekToken(int token);
    public abstract String getHebMonthToken(int token);
    public abstract String getGregMonthToken(int token);
    public abstract String getEventToken(int token);
    public abstract String getZodiacToken(int token);
    public abstract String getElementToken(int token);
    public abstract String getStarToken(int token);
    public abstract String getNumber(int num);
    public abstract String FormatGregorianDate(int day,int month,int year);
    public abstract String FormatJewishDate(int day,int monthId,int year);
    public abstract String FormatPeriod(int monthId);
}
