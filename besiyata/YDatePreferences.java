/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package besiyata.YDate;

import java.util.TimeZone;

/**
 *
 * @author orr
 */
public class YDatePreferences
{
    public YDatePreferences()
    {
        tz=TimeZone.getDefault();
        diaspora=!tz.equals(TimeZone.getTimeZone("Asia/Jerusalem"));
        if (tz.equals(TimeZone.getTimeZone("Asia/Jerusalem")))
        {
            language=Language.HEBREW;
        }
        else if (tz.equals(TimeZone.getTimeZone("Europe/Paris")))
        {
            language=Language.FRENCH;
        }
        else
        {
            language=Language.ENGLISH;
        }
    }
    enum Language {
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
    }
    Language language;
    boolean diaspora;
    TimeZone tz;
}
