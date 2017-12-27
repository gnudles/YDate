/* This is free and unencumbered software released into the public domain.
 *
 * THIS SOFTWARE IS PROVIDED THE CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE CONTRIBUTORS BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; BUSINESS
 * INTERRUPTION; OR ANY SPIRITUAL DAMAGE) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package kapandaria.YDate;


import java.util.TimeZone;

public abstract class YDateLanguage
{

    public enum Language {
        HEBREW,
        ENGLISH,
        YIDDISH,//דעצעמבער
        SPANISH,
        FRENCH,
        RUSSIAN,
        GERMAN,
        PERSIAN,
        ARABIC,
        ARAMIC,
        ITALIAN,
        ENGLISH_ASKENAZ
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
            new YDateLangFrench(),
            null,
            null,
            null,
            null,
            null,
            null
    };
    static final int SUNDAY = 0;
    static final int MONDAY = 1;
    static final int TUESDAY = 2;
    static final int WEDNESDAY = 3;
    static final int THURSDAY = 4;
    static final int FRIDAY = 5;
    static final int SATURDAY = 6;
    public abstract String getWeekToken(int token);
    public abstract String getSpecialShabbat(int token);
    public abstract String getShortWeekToken(int token);
    public abstract String getHebMonthToken(int token);
    public abstract String getShortHebMonthToken(int token);
    public abstract String getGregMonthToken(int token);
    public abstract String getShortGregMonthToken(int token);
    public abstract String getEventToken(int token);
    public abstract String getZodiacToken(int token);
    public abstract String getElementToken(int token);
    public abstract String getStarToken(int token);
    public abstract String getRejection(short rejected);
    public abstract String getNumber(int num);
    public abstract String FormatGregorianDate(int day,int month,int year);
    public String FormatGregorianDate(int day,int month,int year, int week_day, String format)
    {
        //TODO..
        return "";
    }
    public abstract String FormatJewishDate(int day,int monthId,int year);
            /**
             * 
             * @param day
             * @param monthId
             * @param year
             * @param week_day day week in range 0..6
             * @param event - event ID from YDateAnnual.
             * @param format a string that contains _mn_ will be replaced with month name, 
             * _smn_ short month name,
             * _dw_ week day name
             * _sdw_ short week day,
             * _y_ year in numeric form
             * _y2_ two digits year in numeric form
             * _yhl_ year in hebrew letters
             * _d_ day in month
             * _dhl_ day in month hebrew letters
             * _ev_ holiday or event.
             * @return 
             */
    public String FormatJewishDate(int day,int monthId,int year, int week_day, int event, String format)
    {
        format = format.replaceAll("_mn_", getHebMonthToken(monthId));
        format = format.replaceAll("_smn_", getShortHebMonthToken(monthId));
        format = format.replaceAll("_dw_", getWeekToken(week_day));
        format = format.replaceAll("_sdw_", getShortWeekToken(week_day));
        format = format.replaceAll("_yhl_", Format.HebIntString(year, true));
        format = format.replaceAll("_y_", Integer.toString(year));
        format = format.replaceAll("_y2_", Format.get00String(year));
        format = format.replaceAll("_dhl_", Format.HebIntString(day, true));
        format = format.replaceAll("_d_", Integer.toString(day));
        format = format.replaceAll("_ev_", getEventToken(event));
        return format;
    }
    public abstract String FormatPeriod(int monthId);//tkufa
}
