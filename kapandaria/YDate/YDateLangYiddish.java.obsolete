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

/**
 *
 * @author orr
 */
public class YDateLangYiddish extends YDateLangHebrew
{

    static final String [] WeekTokens = {"זונטיק", "מאנטיק", "דינסטיק", "מיטוואך", "דאנערשטיק", "פרייטיק", "שבת"};
    static final String [] GregMonthTokens=
    {"יאנואר", "פעברואר", "מערץ", 
                 "אפריל", "מיי", "יוני", "יולי", 
                 "אויגוסט", "סעפטעמבער", "אקטאבער", 
                 "נאוועמבער", "דעצעמבער"};
    static final String ShortGregMonthTokens []=
    {"ינו", "פבר", "מרץ", 
                 "אפר", "מיי", "יונ", "יול", 
                 "אוג", "ספט", "אקט", 
                 "נאו", "דצמ"};

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
                    "מערקור", "לבנה", "סאטורן", "יופיטער", "מאדים", "זון", "ווענוס"
            };
    final static String[] special_shabbat =
    {
        "שקלים",
        "זכור",
        "פרה",
        "החודש",
        "הגדול",
        "שירה",
        "נחמו",
        "תשובה"
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
    public String getShortGregMonthToken(int token)
    {
        return ShortGregMonthTokens[token];
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
    public String getRejection(short rejected) {
        if (rejected==YDateAnnual.LATE)
            return "נדחה";
        if (rejected==YDateAnnual.PRECEDE)
            return "הוקדם";
        return "";
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

    @Override
    public String getSpecialShabbat(int token) {
        return special_shabbat[token];
    }

}
