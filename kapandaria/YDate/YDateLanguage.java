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
    static final String[] WeekTokens = {"wd_sunday", "wd_monday", "wd_tuesday",
        "wd_wednesday", "wd_thursday", "wd_friday", "wd_saturday"};
    static final String[] HebMonthTokens = {"hm_tisheri", "hm_cheshvan", "hm_kislev",
        "hm_tevet",
        "hm_shevat",
        "hm_adar",
        "hm_adar1",
        "hm_adar2",
        "hm_nisan",
        "hm_iyar",
        "hm_sivan",
        "hm_tammuz",
        "hm_av",
        "hm_elul"};
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
            = {"Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sep",
                "Oct",
                "Nov",
                "Dec"};

    public static final String[] zodiac_names
            = {
                "zdc_aries",
                "zdc_taurus",
                "zdc_gemini",
                "zdc_cancer",
                "zdc_leo",
                "zdc_virgo",
                "zdc_libra",
                "zdc_scorpio",
                "zdc_sagittarius",
                "zdc_capricorn",
                "zdc_aquarius",
                "zdc_pisces"
            };

    public static final String[] four_elements_names
            = {
                "elmnt_fire",
                "elmnt_earth",
                "elmnt_wind",
                "elmnt_water"
            };
    public static final String[] star_names
            = {
                "star_mercury", "star_moon", "star_saturn", "star_jupiter", "star_mars", "star_sun", "star_venus"
            };
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
        return _rbundle.getString(WeekTokens[token]);
    }

    public String getShortWeekToken(int token){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getHebMonthToken(int token) {
        return _rbundle.getString(HebMonthTokens[token]);
    }

    public String getShortHebMonthToken(int token){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getGregMonthToken(int token) {
        return _rbundle.getString(GregMonthTokens[token]);
    }

    public String getShortGregMonthToken(int token){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public String getZodiacToken(int token) {
        return _rbundle.getString(zodiac_names[token]);
    }

    public String getElementToken(int token)
    {
        return _rbundle.getString(four_elements_names[token]);
    }

    public String getStarToken(int token)
    {
        return _rbundle.getString(star_names[token]);
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

    public  String getSpecialShabbat(int token){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public  String getNumber(int num){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public  String FormatGregorianDate(int day, int month, int year){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public  String FormatGregorianDate(int day, int month, int year, int week_day, String format){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String FormatJewishDate(int day, int monthId, int year){
        
        return FormatJewishDate( day,  monthId, year, 0, 0, _rbundle.getString("def_jewish_format"));

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
     * @param event - event ID from YDateAnnual.
     * @param format a string that contains _mn_ will be replaced with month
     * name, _smn_ short month name, _dw_ week day name _sdw_ short week day,
     * _y_ year in numeric form  _yhl_ year
     * in hebrew letters _d_ day in month _dhl_ day in month hebrew letters _ev_
     * holiday or event.
     * @return
     */
    public String FormatJewishDate(int day, int monthId, int year, int week_day, int event, String format) {
        format = format.replaceAll("_mn_", getHebMonthToken(monthId));
        //format = format.replaceAll("_smn_", getShortHebMonthToken(monthId));
        format = format.replaceAll("_dw_", getWeekToken(week_day));
        //format = format.replaceAll("_sdw_", getShortWeekToken(week_day));
        format = format.replaceAll("_yhl_", Format.HebIntString(year, true));
        format = format.replaceAll("_y_", Integer.toString(year));
        //format = format.replaceAll("_y2_", Format.get00String(year)); //_y2_ two digits year in numeric form
        format = format.replaceAll("_dhl_", Format.HebIntString(day, true));
        format = format.replaceAll("_d_", Integer.toString(day));
        //format = format.replaceAll("_ev_", getEventToken(event));
        return format;
    }

    
}
