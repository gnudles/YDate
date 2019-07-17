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

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class YDateLanguage {

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

    public static Language guessLanguageFromLocation(TimeZone tz) {
        Language language;
        if (tz.equals(TimeZone.getTimeZone("Asia/Jerusalem"))) {
            language = Language.HEBREW;
        } else if (tz.equals(TimeZone.getTimeZone("Europe/Paris"))) {
            language = Language.FRENCH;
        } else if (tz.equals(TimeZone.getTimeZone("Europe/Madrid"))) {
            language = Language.SPANISH;
        } else {
            language = Language.ENGLISH;
        }
        return language;
    }

    public static Language getLanguageFromCode(String code) {
        Language language = Language.ENGLISH;
        if (code.toLowerCase().equals("en")) {
            language = Language.ENGLISH;
        } else if (code.toLowerCase().equals("he")) {
            language = Language.HEBREW;
        } else if (code.toLowerCase().equals("fr")) {
            language = Language.FRENCH;
        } else if (code.toLowerCase().equals("sp")) {
            language = Language.SPANISH;
        }
        return language;
    }
    ResourceBundle _rbundle;

    private YDateLanguage(String lcl) {
        Locale locale = new Locale(lcl);
        _rbundle = ResourceBundle.getBundle("kapandaria.YDate.DateLang", locale);
    }

    public static YDateLanguage getLanguageEngine(Language language) {

        return LanguageEngine[language.ordinal()];

    }

    static YDateLanguage[] LanguageEngine = {
        new YDateLanguage("heb"),
        new YDateLanguage("en"),
        new YDateLanguage("yid"),
        new YDateLanguage("sp"),
        new YDateLanguage("fr"),
        new YDateLanguage("ru"),
        new YDateLanguage("gr"),
        new YDateLanguage("pr"),
        new YDateLanguage("ar"),
        new YDateLanguage("arm"),
        new YDateLanguage("it"),
        new YDateLanguage("ena")
    };

    /*static String[] LanguageStr={
            "he",
            "en",
            "yid",
            "sp",
            "fr",
            "ru",
            "gr",
            "pr",
            "ar",
            "arm",
            "it",
            "ena"
    };*/
    
    
    static final String[] GregMonthTokens
            = {"gm_january",
                "gm_february",
                "gm_march",
                "gm_april",
                "gm_may",
                "gm_june",
                "gm_july",
                "gm_august",
                "gm_september",
                "gm_october",
                "gm_november",
                "gm_december"};
    
    static final String[] ShortGregMonthTokens
            = { "sgm_jan",
                "sgm_feb",
                "sgm_mar",
                "sgm_apr",
                "sgm_may",
                "sgm_jun",
                "sgm_jul",
                "sgm_aug",
                "sgm_sep",
                "sgm_oct",
                "sgm_nov",
                "sgm_dec"};



    public static final String[] shmita_labels
            = {
                "shmita_1", "shmita_2", "shmita_3", "shmita_4", "shmita_5", "shmita_6", "shmita_7", "yovel"
            };

    final static String[] special_shabbat
            = {
                "Shkalim",
                "Zakhor",
                "Parah",
                "HaChodesh",
                "HaGadol",
                "Shira",
                "Nachamu",
                "Tshuva"
            };

    public String getWeekToken(int token) {
        return _rbundle.getString(ADate.DayInWeekTokens[token]);
    }

    public String getShortWeekToken(int token){
        return _rbundle.getString("s"+ADate.DayInWeekTokens[token]);
    }

    public String getHebMonthToken(int token) {
        return _rbundle.getString(JewishDate.HebMonthTokens[token]);
    }

    public String getShortHebMonthToken(int token){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getGregMonthToken(int token) {
        return _rbundle.getString(GregMonthTokens[token]);
    }

    public String getShortGregMonthToken(int token){
        return _rbundle.getString(ShortGregMonthTokens[token]);
    }

    public String getToken(String token){
        if (token == null || token.isEmpty())
            return "";
        try
        {
            return _rbundle.getString(token);
        }
        catch(Exception e)
                {
                    throw new NullPointerException("Unknwn token: " +token);
                }
    }

    public String getShmitaToken(int token)
    {
        return _rbundle.getString(shmita_labels[token]);
    }

    public  String getRejection(short rejected){
        if (rejected==YDateAnnual.LATE)
            return _rbundle.getString("LATE_EVENT");
        if (rejected==YDateAnnual.PRECEDE)
            return _rbundle.getString("EARLY_EVENT");
        return null;
    }

    public  String getNumber(int num){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public  String FormatGregorianDate(int day, int month, int year, int week_day){
        return FormatGregorianDate( day,  month, year, week_day, _rbundle.getString("def_gregorian_format"));
    }

    public  String FormatGregorianDate(int day, int month, int year, int week_day, String format){
        format = format.replaceAll("_mn_", getGregMonthToken(month));
        format = format.replaceAll("_smn_", getShortGregMonthToken(month));
        format = format.replaceAll("_dw_", getWeekToken(week_day));
        format = format.replaceAll("_sdw_", getShortWeekToken(week_day));
        format = format.replaceAll("_y_", Integer.toString(year));
        format = format.replaceAll("_y2_", Format.Formatter00.format(year)); //_y2_ two digits year in numeric form
        format = format.replaceAll("_dsfx_", Integer.toString(day) + Format.numSuffix(day));
        format = format.replaceAll("_d_", Integer.toString(day));
        return format;
    }

    public String FormatJewishDate(int day, int monthId, int year, int week_day){
        
        return FormatJewishDate( day,  monthId, year, week_day, _rbundle.getString("def_jewish_format"));

    }
    public String FormatPeriod(int monthId){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//tkufa

    /**
     *
     * @param day
     * @param monthId
     * @param year
     * @param week_day day week in range 0..6
     * @param format a string that contains _mn_ will be replaced with month
     * name, _smn_ short month name, _dw_ week day name _sdw_ short week day,
     * _y_ year in numeric form  _yhl_ year
     * in hebrew letters _d_ day in month _dhl_ day in month hebrew letters _ev_
     * holiday or event.
     * @return
     */
    public String FormatJewishDate(int day, int monthId, int year, int week_day, String format) {
        format = format.replaceAll("_mn_", getHebMonthToken(monthId));
        //format = format.replaceAll("_smn_", getShortHebMonthToken(monthId));
        format = format.replaceAll("_dw_", getWeekToken(week_day));
        format = format.replaceAll("_sdw_", getShortWeekToken(week_day));
        format = format.replaceAll("_yhl_", Format.HebIntString(year, true));
        format = format.replaceAll("_y_", Integer.toString(year));
        format = format.replaceAll("_dhl_", Format.HebIntString(day, true));
        format = format.replaceAll("_d_", Integer.toString(day));
        return format;
    }

    
}
