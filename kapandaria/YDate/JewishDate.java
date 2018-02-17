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

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Orr Dvori
 */
public final class JewishDate extends ADMYDate
{
    static final int DAYS_OF_4119 = 1504084;
    static final int DAYS_OF_TKUFA_CYCLE_4117 = 1503540;
    static final int DAYS_OF_6001 = 2191466;
    static final int N_YEAR_TYPES = 14;
    static final int HOUR = 1080;
    static final int DAY = (24 * HOUR);
    static final int WEEK = (7 * DAY);
    static final int MONTH = 29 * DAY + HP(12, 793);
    static final int MOLAD = MONDAY * DAY + HP(5, 204);
    static final int MONTHS_IN_19Y = 235;//12*12+7*13
    static final int MOLAD_ZAKEN_ROUNDING = 6 * HOUR;
    static final int TKUFA = 91 * DAY + 7 * HOUR + 540;
    static final int MAZAL = 30 * DAY + 10 * HOUR + 540;

    public static final int M_ID_TISHREI = 0;
    public static final int M_ID_CHESHVAN = 1;
    public static final int M_ID_KISLEV = 2;
    public static final int M_ID_TEVET = 3;
    public static final int M_ID_SHEVAT = 4;
    public static final int M_ID_ADAR = 5;
    public static final int M_ID_ADAR_I = 6;
    public static final int M_ID_ADAR_II = 7;
    public static final int M_ID_NISAN = 8;
    public static final int M_ID_IYAR = 9;
    public static final int M_ID_SIVAN = 10;
    public static final int M_ID_TAMMUZ = 11;
    public static final int M_ID_AV = 12;
    public static final int M_ID_ELUL = 13;

    public static final int S_ID_MERCURY = 0;
    public static final int S_ID_MOON = 1;
    public static final int S_ID_SATURN = 2;
    public static final int S_ID_JUPITER = 3;
    public static final int S_ID_MARS = 4;
    public static final int S_ID_SUN = 5;
    public static final int S_ID_VENUS = 6;


    public static final int YEZIAT_MIZRAIM = 2449;
    public static final int MINIAN_SHTAROT = 3449;
    public static final int HORBAN_BAIT_RISHON = 3339;
    public static final int HORBAN_BAIT_SHENI = 3829;
    /**
     * Number of months per year in a 19 years cycle.
     */
    static final int[] MONTHS_DIVISION
            = {
                12, 12, 13, 12, 12, 13, 12, 13, 12, 12, 13, 12, 12, 13, 12, 12, 13, 12, 13
            };

    static int HP(int h, int p) {
        return h * HOUR + p;
    }
    /** to be removed!... */
    public static final String[][] zodiac_names = {
        {"טלה", "שור", "תאומים", "סרטן", "אריה", "בתולה", "מאזנים", "עקרב", "קשת", "גדי", "דלי", "דגים"},
        {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"}
    };
    /*
    fire: Aries Leo Sagittarius
    earth: Taurus Virgo Capricorn
    wind: Gemini Libra Aquarius
    water: Cancer Scorpio Pisces
       fire doesn't connect with water
       earth doesn't connect with wind
     */
    /**
     * Every zodiac sign is connected with an element. this string contains the
     * names of the four elements.
     * The signs that are connected with Fire are: Aries, Leo, Sagittarius.
     * The signs that are connected with Earth are: Taurus, Virgo, Capricorn.
     * The signs that are connected with Wind are: Gemini, Libra, Aquarius.
     * The signs that are connected with Water are: Cancer, Scorpio, Pisces.
     * The ones with the element of fire doesn't get along with those of water.
     * The ones with the element of earth doesn't get along with those of wind.
     */
    /** to be removed!... */
    public static final String[][] four_elements_names = {
        {"אש", "עפר", "רוח", "מים"},
        {"fire", "earth", "wind", "water"}
    };
    /** to be removed!... */
    public static final String[][] day_names = {
        {"ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"},
        {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"},
        {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}};

    private int m_year = 0; // year 1 is the first year.
    private int m_month; // range 1..12,or 1..13 in leap year

    private int m_day; // range 1..30
    private long m_yearGParts; // parts since the beginning
    private int m_yearLength; // days in year - 353,354,355,383,384,385
    private int m_yearFirstDay; // first day of rosh hashana from the beginning
    private int m_dayInYear; // days after year beginning. first day in year  is 0
    private boolean m_valid;

    public JewishDate() {
        this.m_valid = false;
        this.m_year = 1;
        this.m_month = 1;
        this.m_day = 1;
        this.m_yearGParts = 0;
        this.m_yearFirstDay = 0;
        this.m_yearLength = 354;
        this.m_dayInYear = 0;
    }
    public JewishDate(JewishDate o) {
        this.m_valid = o.m_valid;
        this.m_year = o.m_year;
        this.m_month = o.m_month;
        this.m_day = o.m_day;
        this.m_yearGParts = o.m_yearGParts;
        this.m_yearFirstDay = o.m_yearFirstDay;
        this.m_yearLength = o.m_yearLength;
        this.m_dayInYear = o.m_dayInYear;
    }
    public static JewishDate create(int year, int monthId, int day)
    {
        return new JewishDate(year,monthFromIDByYear(year,monthId),day);
    }

    private JewishDate(int year, int month, int day) {
        if (year >= 4119 && year < 7001) {
            this.m_valid = true;
            this.m_year = year;
            this.m_month = month;
            this.m_day = day;
            calculateYearVariables();
            this.m_dayInYear = calculateDayInYear(this.m_yearLength, month, day);
        }
        else {
            this.m_valid = false;
        }
    }

    private void calculateYearVariables() {
        this.m_yearGParts = parts_since_beginning(m_year);
        this.m_yearFirstDay = days_until_year(m_year, this.m_yearGParts);
        this.m_yearLength = days_until_year(m_year + 1, parts_since_beginning(m_year + 1)) - this.m_yearFirstDay;
    }

    public boolean setByYearMonthIdDay(int year, int month_id, int day) {
        if (year >= 4119 && year < 6001) {
            this.m_valid = true;
            if (this.m_year != year) {
                this.m_year = year;
                calculateYearVariables();
            }
            this.m_month = monthFromIDByYearLength(this.m_yearLength, month_id);
            int month_length = monthLength();
            this.m_day = Math.min(month_length, day);
            this.m_dayInYear = calculateDayInYear(this.m_yearLength, this.m_month, this.m_day);
            int gdn=GDN();
            stateChanged();
            return gdn==GDN();
        }
        return false;
    }

    @Override
    public boolean setByGDN(int gdn) {
        boolean result=setByDays(gdn);
        stateChanged();
        return result && (gdn == GDN());
    }

    public JewishDate(int gdn) {
        m_valid = setByDays(gdn);
    }

    /**
     * Gives a cloned object with the next date.
     * That is useful to get the correct date if you are after twilight.
     * @return a cloned object of the next day.
     */
    public JewishDate getDayAfterTwilight() {
        if (m_dayInYear + 1 == m_yearLength)// we reached the end of the year.
        {
            return new JewishDate(m_year + 1, 1, 1);//we must recalculate the year variables.
        }
        else {
            JewishDate cln = new JewishDate(this);
            cln.m_dayInYear++;
            if (cln.m_day == cln.monthLength())// we reached the end of the month.
            {
                cln.m_day = 1;
                cln.m_month++;
            }
            else {
                cln.m_day++;
            }
            return cln;
        }
    }

    public String dayString(YDateLanguage.Language lang) {
        return YDateLanguage.getLanguageEngine(lang).FormatJewishDate(m_day, monthID(), m_year);
    }

    @Override
    public boolean isValid()
    {
        return m_valid;
    }
    /**
     * use setByGDN
     * @param gdn
     * @return
     * 
     */
    private boolean setByDays(int days)
    {
        if (!checkBounds(days)) {
            return false;
        }
        long orig_parts = (long) days * DAY;
        long parts = orig_parts - MOLAD;
        int months = (int) (parts / MONTH);
        parts = (parts % MONTH);
        int years = 1;//first year was year one.
        years += 19 * (months / MONTHS_IN_19Y);
        months = months % MONTHS_IN_19Y;
        int year_in_19 = ((months + 1) * 19 - 2) / 235;
        years += year_in_19;
        months = months - (235 * (year_in_19) + 1) / 19;
        parts += months * MONTH;
        this.m_yearGParts = orig_parts - parts;
        this.m_yearFirstDay = days_until_year(years, this.m_yearGParts);
        int next_year_day = days_until_year(years + 1, parts_since_beginning(years + 1));
        int months_in_year;
        if (days >= this.m_yearFirstDay && days < next_year_day) {
            this.m_year = years;
            this.m_yearLength = next_year_day - this.m_yearFirstDay;
        }
        else {

            if (days < this.m_yearFirstDay) {
                this.m_year = years - 1;
                months_in_year = calculateYearMonths(this.m_year);
                this.m_yearGParts -= months_in_year * MONTH;
                next_year_day = this.m_yearFirstDay;
                this.m_yearFirstDay = days_until_year(this.m_year, this.m_yearGParts);
                this.m_yearLength = next_year_day - this.m_yearFirstDay;
            }
            else {
                assert (days >= next_year_day);
                this.m_year = years + 1;
                months_in_year = calculateYearMonths(years);
                this.m_yearGParts += months_in_year * MONTH;
                this.m_yearFirstDay = next_year_day;
                this.m_yearLength = days_until_year(this.m_year + 1, parts_since_beginning(this.m_year + 1)) - this.m_yearFirstDay;
            }
        }
        this.m_dayInYear = days - this.m_yearFirstDay;
        setMonthDay(this.m_dayInYear);
        return true;
    }

    @Override
    public int GDN()
    {
        return daysSinceBeginning();
    }

    @Override
    public int upperBound()
    {
        return DAYS_OF_6001;
    }

    @Override
    public int lowerBound()
    {
        return DAYS_OF_4119;
    }

    @Override
    public int yearFirstDayGDN()
    {
        return yearFirstDay();
    }

    @Override
    public int monthFirstDayGDN()
    {
        return monthFirstDay();
    }

    public int NumberOfShabbats() {
        int year_diw = m_yearFirstDay % 7;
        int diy = getNext(SATURDAY, year_diw) - year_diw;
        return (m_yearLength - (diy) + 6) / 7;
    }

    public boolean TenTalVeMatar(boolean diaspora) {
        if (diaspora) {
            int starting = getTkufaOfYearDay(0) + 59; // the sixty day from when Tkufat Tishrei begins (first day in count)
            if (daysSinceBeginning() < starting) {
                return false;
            }
        }
        else {
            if (m_dayInYear < 36) { //36 is day in year of 7 in Cheshvan
                return false;
            }
        }
        int pessach_day = calculateDayInYearByMonthId(m_yearLength, M_ID_NISAN, 15);
        if (m_dayInYear >= pessach_day) {
            return false;
        }
        return true;
    }

    /**
     * This method tells if we need to say Mashiv Ha'Ruah in the prayer or
     * Morid Ha'Tal. If we are in summer time, we say Morid Ha'Tal (bless
     * for dew descend). But if we are in the winter, we say Mashiv Ha'Ruah
     *
     * @param MusafAndAfter indicates whether we are about to pray Musaf and
     * after that, or whether we are before Shacharit and that period hasn't
     * began yet.
     * @return true if we need to say Mashiv Ha'Ruah
     */
    public boolean MashivHaruah(boolean MusafAndAfter) {
        final int shmini_azeret = 21;
        if ((m_dayInYear == shmini_azeret && !MusafAndAfter) || m_dayInYear < shmini_azeret) {
            return false;
        }
        int pessach_day = calculateDayInYearByMonthId(m_yearLength, M_ID_NISAN, 15);
        return (m_dayInYear == pessach_day && !MusafAndAfter) || m_dayInYear < pessach_day;
    }



    public enum Gvurot {
        MASHIV_HARUACH,
        MORID_HATAL,
        MASHIV_HARUACH_BECOME_MORID_HATAL,
        MORID_HATAL_BECOME_MASHIV_HARUACH
    }

    /**
     * This method tells if we need to say Mashiv Ha'Ruah in the prayer or
     * Morid Ha'Tal. If we are in summer time, we say Morid Ha'Tal (bless
     * for dew descend). But if we are in the winter, we say Mashiv Ha'Ruah
     *
     */
    public Gvurot getPrayerGvurot() {
        final int shmini_azeret = 21;
        if (m_dayInYear == shmini_azeret) {
            return Gvurot.MORID_HATAL_BECOME_MASHIV_HARUACH;
        }
        int pessach_day = calculateDayInYearByMonthId(m_yearLength, M_ID_NISAN, 15);
        if (m_dayInYear == pessach_day) {
            return Gvurot.MASHIV_HARUACH_BECOME_MORID_HATAL;
        }
        if (m_dayInYear < shmini_azeret || m_dayInYear > pessach_day) {
            return Gvurot.MORID_HATAL;
        }
        return Gvurot.MASHIV_HARUACH;
    }

    public String Segulah() {
        String lstr = "";
        if (TorahReading.getShabbatBereshit(yearLength(), yearFirstDay()) + 15 * 7 - 4 == daysSinceBeginning()) //Tuesday in Beshalach
        {
            return "סגולת פרשת המן שניים מקרא ואחד תרגום\n";
        }
        if (monthID() == M_ID_NISAN) {
            lstr += "ברכת האילנות\n";
            if (dayInMonth() <= 13) {
                lstr += "קרבנות הנשיאים\n";
            }
        }
        if (monthID() == M_ID_IYAR && dayInMonth() == 29) {
            lstr += "תפילת השל\"ה\n";
        }
        return lstr;
    }

    public int ShmitaOrdinal()//unfortunatly we don't have Yovel.
    {
        final int yovel_year = 6000;//to be determined
        if (this.m_year >= yovel_year) {
            return 1 + (this.m_year - yovel_year + 49) % 50;
        }
        return 1 + (this.m_year - 4117) % 7;//4116 was a shmita year
    }

    public String ShmitaTitle() {
        final String[] titles = {
            "ראשונה (מעשר שני)",
            "שניה (מעשר שני)",
            "שלישית (מעשר עני)",
            "רביעית (מעשר שני וביעור מעשרות)",
            "חמישית (מעשר שני)",
            "שישית (מעשר עני)",
            "שביעית (שמיטה וביעור מעשרות)",
            "יובל"
        };
        if (ShmitaOrdinal() == 50) {
            return titles[7];
        }
        return titles[(ShmitaOrdinal() - 1) % 7];
    }

    public int dayInMazal() {
        int d = (TkufotCycle() + 1) * DAY - 1;
        d = d % MAZAL;
        return (int) (d / DAY);
    }

    public int dayInTkufa() {
        int d = (TkufotCycle() + 1) * DAY - 1;
        d = d % TKUFA;
        return (int) (d / DAY);
    }

    /**
     * 
     * @param tkufa 0 -Tishrei, 1- Tevet, 2 - Nisan , 3- Tammuz
     * @return day since beginning for that tkufa in the current year.
     */
    public int getTkufaOfYearDay(int tkufa)//0 -Tishrei, 1- Tevet, 2 - Nisan , 3- Tammuz
    {
        long tkufa_parts = (long) ((m_year - 4117) * 4 - 2 + tkufa) * TKUFA;
        return (int) (tkufa_parts / DAY) + DAYS_OF_TKUFA_CYCLE_4117;
    }
    /**
     * 
     * @param tkufa 0 -Tishrei, 1- Tevet, 2 - Nisan , 3- Tammuz
     * @return tkufa parts from beginning
     */
    /*@untested*/
    public long getTkufaOfYearParts(int tkufa)//0 -Tishrei, 1- Tevet, 2 - Nisan , 3- Tammuz
    {
        long tkufa_parts = (long) ((m_year - 4117) * 4 - 2 + tkufa) * TKUFA +(long) DAYS_OF_TKUFA_CYCLE_4117 * DAY;
        return tkufa_parts;
    }

    public long MazalParts() {
        long mazal_parts = ((long) daysSinceBeginning() + 1 - DAYS_OF_TKUFA_CYCLE_4117) * (long) DAY - 1;
        mazal_parts = mazal_parts - (mazal_parts % MAZAL);
        mazal_parts += (long) DAYS_OF_TKUFA_CYCLE_4117 * DAY;
        return mazal_parts;
    }

    public long TkufaParts() {
        long tkufa_parts = ((long) daysSinceBeginning() + 1 - DAYS_OF_TKUFA_CYCLE_4117) * (long) DAY - 1;
        tkufa_parts = tkufa_parts - (tkufa_parts % TKUFA);
        tkufa_parts += (long) DAYS_OF_TKUFA_CYCLE_4117 * DAY;
        return tkufa_parts;
    }

    public int MazalType() {
        int d = (TkufotCycle() + 1) * DAY - 1;
        return (d / MAZAL) % 12;
    }

    public int TkufaType() {
        int d = (TkufotCycle() + 1) * DAY - 1;
        d = d / TKUFA;
        return (M_ID_NISAN + (d % 4) * 3) % 14;
    }

    public String MazalName(boolean Heb) {
        //TODO: make this return integer of mazal ID...
        int mazal = MazalType();
        if (Heb) {
            return "מזל " + zodiac_names[0][mazal] + " (" + four_elements_names[0][mazal % 4] + ")";
        }
        else {
            return "Zodiac. " + zodiac_names[1][mazal] + " (" + four_elements_names[1][mazal % 4] + ")";
        }
    }

    public String TkufaName(YDateLanguage.Language language) {
        return YDateLanguage.getLanguageEngine(language).FormatPeriod(TkufaType());
    }

    public String MazalBeginning(TimeZoneProvider tz) {
        Date m = partsToUTC(MazalParts());
        return FormatUTC(m, tz, true);
    }

    public String TkufaBeginning(TimeZoneProvider tz) {
        long parts = TkufaParts();
        Date m = partsToUTC(parts);
        return FormatUTC(m, tz, true) + "\nמוסיפים " + ((TkufaType() % M_ID_NISAN == M_ID_TEVET) ? "60" : "30") + " דקות לפני ואחרי."
                + "\nתחילת תקופה ב" + starForHour(parts, YDateLanguage.getLanguageEngine(YDateLanguage.Language.HEBREW));
    }

    /**
     * find out how many days passed from the last sun blessing
     *
     * @return number of days, modulo by 10227
     */
    public int TkufotCycle()//when this method return 0, we need to do sun blessing in nissan.
    {
        return TkufotCycle(daysSinceBeginning());
    }

    static public int TkufotCycle(int days)//when this method return 0, we need to do sun blessing in nissan.
    {
        //10227=number of days in 28 years when year=365.25 days
        return (days - DAYS_OF_TKUFA_CYCLE_4117) % 10227;
        //this date (1503540) is 19 in Nisan, 4117, wednesday
        //there is 112 tkofut in 28 year (4*28) or in 10227 days
        //you can find out which tkufa by tkufa=TkufotCycle()*112/10227
        //and that tkufa started at tkufa*10227/112
        //which is actually 1461/16 or 16/1461
    }
    /**
     * I heard that one said that if 3 Shvat falls on Friday, There will be a cold winter.
     * @return if we will have a cold winter.
     */
    public boolean yearOfColdWinter()
    {
        return monthFirstDay(M_ID_SHEVAT)%7==WEDNESDAY;
    }
    /**
     * based on gmara in eruvin
     * @return 
     */
    /*@untested*/
    public boolean TkufatNisanMeshaberetIlanot()
    {
        int tkufa_star=starForHour(getTkufaOfYearParts(2));
        int molad_star= starForHour(MoladParts(monthFromID(M_ID_NISAN)));
        return (tkufa_star==S_ID_JUPITER && (molad_star == S_ID_JUPITER || molad_star == S_ID_MOON));
    }
    /**
     * based on gmara in eruvin
     * @return 
     */
    /*@untested*/
    public boolean TkufatTavetMeyabeshetZeraim()
    {
        int tkufa_star=starForHour(getTkufaOfYearParts(1));
        int molad_star= starForHour(MoladParts(monthFromID(M_ID_TEVET)));
        return (tkufa_star==S_ID_JUPITER && (molad_star == S_ID_JUPITER || molad_star == S_ID_MOON));
    }

    /**
     * find out when B' H' B' after Sukkot...
     *
     * @return day from beginning for the Shabbat before the first taanit
     * monday.
     */
    public int taanitBetHehBetForCheshvan() {
        /* 1 in tishrey is day 0. tishrey has 30 days. so 30 in tishrey is day 29. and 1 cheshvan is day 30.
         */
        return getNext(SATURDAY, m_yearFirstDay + 31);
    }

    /**
     * find out when B' H' B' after Pesach... There is a tradition to bless
     * those who fast in these days in the Shabbat before the Taaniot
     *
     * @return day from beginning for the Shabbat before the first taanit
     * monday.
     */
    public int taanitBetHehBetForIyar() {
        return getNext(SATURDAY, monthFirstDay(M_ID_IYAR) + 1);//+2 to get first monday, +5 to get thursday, and +9 to get the last monday.
    }

    public static final int[][] possibleMonthDay = {
        {MONDAY, TUESDAY, THURSDAY, SATURDAY},//TISHREI
        {MONDAY, WEDNESDAY, THURSDAY, SATURDAY},//CHESHVAN
        {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY},//KISLEV
        {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, FRIDAY},//TEVET
        {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, SATURDAY},//SHEVAT
        {MONDAY, WEDNESDAY, FRIDAY, SATURDAY},//ADAR
        {MONDAY, WEDNESDAY, THURSDAY, SATURDAY},//ADAR_I
        {MONDAY, WEDNESDAY, FRIDAY, SATURDAY},//ADAR_II
        {SUNDAY, TUESDAY, THURSDAY, SATURDAY},//NISAN
        {MONDAY, TUESDAY, THURSDAY, SATURDAY},//IYAR
        {SUNDAY, TUESDAY, WEDNESDAY, FRIDAY},//SIVAN
        {SUNDAY, TUESDAY, THURSDAY, FRIDAY},//TAMMUZ
        {MONDAY, WEDNESDAY, FRIDAY, SATURDAY},//AV
        {SUNDAY, MONDAY, WEDNESDAY, FRIDAY}//ELUL
    };
    public static final int[][] possibleMonthLength = {
        {30},//TISHREI
        {29, 30},//CHESHVAN
        {29, 30},//KISLEV
        {29},//TEVET
        {30},//SHEVAT
        {29},//ADAR
        {30},//ADAR_I
        {29},//ADAR_II
        {30},//NISAN
        {29},//IYAR
        {30},//SIVAN
        {29},//TAMMUZ
        {30},//AV
        {29}//ELUL
    };

    public String yearSign() {
        final byte[] yeartype = {8, 11, 21};
        int day_of_pessah = JewishDate.calculateDayInYearByMonthId(m_yearLength, JewishDate.M_ID_NISAN, 15) + m_yearFirstDay;
        return Format.alphabeta[(m_yearFirstDay % 7) + 1] + Format.alphabeta[yeartype[m_yearLength % 10 - 3]]
                + Format.alphabeta[(day_of_pessah % 7) + 1] + ((m_yearLength >= 383) ? " מעוברת" : " פשוטה");
    }

    public boolean roshChodesh() {
        return (m_day == 30 || m_day == 1);
    }

    public boolean mevarchinShabbat() {
        return (m_day >= 23 && m_day < 30 && dayInWeekEnum() == SATURDAY && monthID() != M_ID_ELUL);
    }//not mevarchin before Tishrei
    /**
     * shabbat before 17 Tammuz and 10 Tevet the Shliach Tzibur declares the upcoming taanit.
     * 
     * @return 
    */
    /*@untested*/
    public boolean hachrazatTaanit() {
        int day_in_week=dayInWeekEnum();
        return (day_in_week ==SATURDAY &&((monthID() == M_ID_TAMMUZ && (m_day<=17 && m_day>=11) ) ||
                (monthID() == M_ID_TEVET && (m_day<10 && m_day>=4) )));
    }
    /*@untested*/
    public boolean yomHakhel() {
        int day_in_week=dayInWeekEnum(); // yom hakhel is nidcha (postponed) if it falls on saturday.
        return (((m_day == 16 && day_in_week!=SATURDAY ) || (m_day == 17 && day_in_week==SUNDAY)) && monthID() == M_ID_TISHREI
                && (ShmitaOrdinal() - 1) % 7 == 0);
    }// in the year after Shmita, after first day of Succot.
    /*@untested*/
    public boolean yomKippurQatan()
    {
        /* TISHREI = 0;
           CHESHVAN = 1;
           KISLEV = 0;
           TEVET = 1;
           SHEVAT = 1;
           ADAR = 1;
           ADAR_I = 1;
           ADAR_II = 1;
           NISAN = 0;
           IYAR = 1;
           SIVAN = 1;
           TAMMUZ = 1;
           AV = 1;
           ELUL = 0;
        */
    //|   a   |   f   |   e   | 1
    //|0 1 0 1|1 1 1 1|0 1 1 1|1 0
        //final int months_mask=0x1efa;
        final int months_mask = (1<<JewishDate.M_ID_CHESHVAN)|
               (1<<JewishDate.M_ID_TEVET)|
               (1<<JewishDate.M_ID_SHEVAT)|
               (1<<JewishDate.M_ID_ADAR)|
               (1<<JewishDate.M_ID_ADAR_I)|
               (1<<JewishDate.M_ID_ADAR_II)|
               (1<<JewishDate.M_ID_IYAR)|
               (1<<JewishDate.M_ID_SIVAN)|
               (1<<JewishDate.M_ID_TAMMUZ)|
               (1<<JewishDate.M_ID_AV);
        int m_id=monthID();
        int day_in_week=dayInWeekEnum();
        if (((1<<m_id)&months_mask)!=0)
        {
           return ( (m_day == 29 && day_in_week!=SATURDAY && day_in_week!=FRIDAY)
                   || ((m_day == 28 || m_day == 27) && day_in_week==THURSDAY));
        }
        return false;
    }
    /**
     * remember that in this case the day starts from the night before, so it is not recommanded to 
     * make a wedding or so in these days and in the night before them
     * @return true if this is a misfortune day. 
     */
    public boolean isMisfortuneDayFromHaari()
    {
        //bad days to start new things from ha'ari
        final int [/*14*/] day_in_month_mask=
        {
            (1<<6)|(1<<10)|(1<<28),//TISHREI
            (1<<7)|(1<<11)|(1<<15)|(1<<21),//CHESHVAN
            (1<<1)|(1<<8),//KISLEV
            (1<<1)|(1<<2)|(1<<4)|(1<<6)|(1<<7)|(1<<11)|(1<<17)|(1<<20)|(1<<24)|(1<<25)|(1<<26)|(1<<27),//TEVET
            (1<<9)|(1<<17)|(1<<18)|(1<<24)|(1<<25)|(1<<26),//SHEVAT
            (1<<3)|(1<<15)|(1<<17)|(1<<18)|(1<<28),//ADAR
            (1<<3)|(1<<15)|(1<<17)|(1<<18)|(1<<28),//ADAR_I
            (1<<3)|(1<<15)|(1<<17)|(1<<18)|(1<<28),//ADAR_II
            (1<<7)|(1<<9)|(1<<11)|(1<<16)|(1<<21)|(1<<24),//NISAN
            (1<<5)|(1<<7)|(1<<15)|(1<<22),//IYAR
            (1<<1)|(1<<6)|(1<<9)|(1<<26),//SIVAN
            (1<<14)|(1<<15)|(1<<17)|(1<<20)|(1<<29),//TAMMUZ
            (1<<9)|(1<<10)|(1<<19)|(1<<20)|(1<<22)|(1<<27),//AV
            (1<<9)|(1<<17)|(1<<28)|(1<<29)//ELUL
        };
        int m_id=monthID();
        return (day_in_month_mask[m_id]&(1<<dayInMonth()))!=0;
    }

    public boolean shabbaton(YDatePreferences.DiasporaType diaspora) {
        if (diaspora == YDatePreferences.DiasporaType.Both) {
            return shabbaton(true) || shabbaton(false);
        }
        return shabbaton(diaspora == YDatePreferences.DiasporaType.Diaspora);
    }

    public boolean shabbaton(boolean diaspora) {
        byte[] events = YDateAnnual.getEvents(m_yearLength, m_yearFirstDay, diaspora);
        short type = YDateAnnual.getEventType(events[m_dayInYear]);
        return ((type & YDateAnnual.EV_TYPE_MASK) == YDateAnnual.EV_YOM_TOV) || (dayInWeek() == 7);
    }

    /**
     * return list of the thursdays of taanit shovavim - shmot vaera bo beshalach yitro mishpatim truma tezave
     * @return 
     */
    public int[] shovavim() {

        int first_sunday_of_shovavim = TorahReading.getShabbatBereshit(yearLength(), yearFirstDay()) + 12 * 7 - 6;
        int shovavim_thursday = first_sunday_of_shovavim + 4;//day since beginning.
        int shovavim_tat_len = isLeapYear(m_year) ? 8 : 6;
        int taanit[] = new int[shovavim_tat_len];

        for (int i = 0; i < shovavim_tat_len; ++i) {
            //TODO:
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public boolean shovavimDays() {

        int first_sunday_of_shovavim = TorahReading.getShabbatBereshit(yearLength(), yearFirstDay()) + 12 * 7 - 6;
        int shovavim_tat_len = isLeapYear(m_year) ? 8 : 6;
        return (GDN()>=first_sunday_of_shovavim && GDN()<first_sunday_of_shovavim+shovavim_tat_len*7);
    }

    public int dayOfChanukkah() {
        int diy = dayInYear();
        int chnkday = calculateDayInYearByMonthId(m_yearLength, M_ID_KISLEV, 25);
        return (diy >= chnkday && diy < chnkday + 8) ? diy - chnkday + 1 : -1;
    }

    public boolean isKippurDay() {
        return dayInYear() == 9; // 10 in Tishrei.
    }

    /**
     * if nine av is nidcha (postponed), return ten av.
     * @return the day in year of the nine av fast day
     */
    public int nineAvDayInYear() {
        int nine_av = calculateDayInYearByMonthId(m_yearLength, M_ID_AV, 9); // 9 in Av.
        if ((nine_av + m_yearFirstDay) % 7 == SATURDAY) {
            ++nine_av;
        }
        return nine_av;
    }

    public boolean isNineAv() {

        return dayInYear() == nineAvDayInYear(); // 9 in Av.
    }

    public int sfiratHaomer() {
        int before_fisrt_omer_day = calculateDayInYearByMonthId(this.m_yearLength, M_ID_NISAN, 15);//pessah night
        int omer = this.m_dayInYear - before_fisrt_omer_day;
        if (omer < 0 || omer > 49) //if omer ==0 then its the day before the night of hasfira
        {
            return -1;
        }
        return omer;
    }

    public int MasechetAvotChapter(boolean AskenazTradition) {
        //TODO:
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * There are seven stars, each star controls different hour of the day in
     * the following sequence: Saturn, Jupiter, Mars, Sun, Venus, Mercury, Moon.
     * That sequence starts in the beginning of the Wednesday night (right after
     * the stars comes out). So Sunday's night starts with Mercury.
     * Also, according to the book of Yezira, each star is connected with a
     * single day of the week (just as their names suggest).
     */
    String starForHour(long parts, YDateLanguage lang) {
        int hour = (int) ((parts / HOUR) % 7);
        return lang.getStarToken(hour);
    }
    /**
     * There are seven stars, each star controls different hour of the day in
     * the following sequence: Saturn, Jupiter, Mars, Sun, Venus, Mercury, Moon.
     * That sequence starts in the beginning of the Wednesday night (right after
     * the stars comes out). So Sunday's night starts with Mercury.
     * Also, according to the book of Yezira, each star is connected with a
     * single day of the week (just as their names suggest).
     * check the returned value against S_ID_MERCURY...
     */
    int starForHour(long parts) {
        return (int) ((parts / HOUR) % 7);
    }

    public long MoladParts() {
        return m_yearGParts + (m_month - 1) * MONTH;
    }
    public long MoladParts( int _month) {
        return m_yearGParts + (_month - 1) * MONTH;
    }

    public Date partsToUTC(long parts) {
        int days = (int) (parts / DAY);
        int single_parts = (int) (parts % DAY);
        int hours = (int) (single_parts / HOUR);
        single_parts = single_parts % HOUR;
        //all the parts caculation is relative to jerusalem.
        //the longitude of jerusalem is 35.2354. if you divide it by 360 and multiply by 24 hour in a day you get 2.349 hours diff, where we already added the 2,
        //we need to manualy add that 0.349 hour which is 20.9416 minutes (20 minutes and 56.496 seconds)
        //so we subtract 1 day but we add 18 hours (3 quarters). I mean 16 hours . I mean 15:39:03 (to get UTC)
        long millis = (long) (days - EPOCH_DAY - 1) * 3600L * 24 * 1000L;
        final int offset_utc = 15 * 60 * 60 + 39 * 60 + 3;//15:39:03 or you can use 16*60*60 instead if you don't want that 21 minutes thing
        millis += (hours * 3600L * 1000L + single_parts * 10000L / 3 + offset_utc * 1000L);
        return new Date(millis);
    }
    /** move this from here */
    static String dayPartName(int minutes) {
        if (minutes > 23 * 60 || minutes < 3 * 60) {
            return "לילה";
        }
        if (minutes < 5 * 60) {
            return "לפנות בוקר";
        }
        if (minutes < 11 * 60) {
            return "בוקר";
        }
        if (minutes < 15 * 60) {
            return "צהריים";
        }
        if (minutes < 17 * 60) {
            return "אחה\"צ";
        }
        return "ערב";
    }

    /** move this from here */
    public static String FormatUTC(Date t, TimeZoneProvider tz, boolean Heb) {
        String lstr;
        int utc_minute_offset = (int) (tz.getOffset(t) * 60);
        t.setTime(t.getTime() + utc_minute_offset * 60000L);
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.setTime(t);
        int gdmonth = c.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int gdday = c.get(Calendar.DAY_OF_MONTH);
        int gdyear = c.get(Calendar.YEAR);

        String clock_type = "UTC" + (utc_minute_offset >= 0 ? "+" : "-") + String.valueOf(Math.abs(utc_minute_offset)) + "MIN";
        long millis = t.getTime() + (EPOCH_DAY - DAYS_OF_4119) * (24 * 60 * 60000L);//DAYS_OF_4119 is monday
        int minutes = (int) ((millis / 60000L) % (24 * 60));
        lstr = Format.GDateString(gdyear, gdmonth, gdday) + " " + Format.Min2Str(minutes);
        String day_part_name = dayPartName(minutes);
        lstr += " (" + day_names[Heb ? 0 : 1][(int) ((millis / (24 * 60 * 60000)) + 1) % 7] + " " + day_part_name + ")";
        lstr += " (" + clock_type + ")";
        return lstr;
    }
    
    public String MoladString(TimeZoneProvider tz, YDateLanguage.Language language) {
        //TODO: make this multilingual
        long parts = MoladParts();
        int days = (int) (parts / DAY);
        int single_parts = (int) (parts % DAY);
        int hours = (int) (single_parts / HOUR);
        single_parts = single_parts % HOUR;
        String lstr = "המולד לחודש ";
        lstr += monthName(language);
        lstr += " ";
        lstr += Format.HebIntString(year(), true);
        lstr += " ביום ";
        lstr += day_names[0][days % 7];
        lstr += " שעה ";
        lstr += String.valueOf(hours);
        lstr += " ו ";
        lstr += String.valueOf(single_parts);
        lstr += " חלקים";
        lstr += "\n";
        //int minutes=(single_parts)/(1080/60);
        Date m = partsToUTC(parts);
        lstr += FormatUTC(m, tz, true);
        lstr += "\nהמולד ב" + starForHour(parts, YDateLanguage.getLanguageEngine(YDateLanguage.Language.HEBREW));
        Date fill = partsToUTC(parts + MONTH / 2);
        lstr += "\nמילוי הלבנה ";
        lstr += FormatUTC(fill, tz, true);
        return lstr;
    }

    @Override
    public int dayInMonth()//starts from one
    {
        return this.m_day;
    }

    public int dayInWeekEnum()//starts from zero
    {
        return daysSinceBeginning() % 7;
    }

    public int dayInWeek()//starts from one
    {
        return daysSinceBeginning() % 7 + 1;
    }

    public String dayInWeekName(boolean Heb) {
        //TODO... make this multilingual...
        return day_names[Heb ? 0 : 1][dayInWeekEnum()];
    }

    @Override
    public int dayInYear()//starts from zero
    {
        return this.m_dayInYear;
    }

    /**
     * the ordinal month in year. in regular year the range is 1..12, 
     * and in a leap year the range is 1..13.
     * to get the month ID (eg. TISHREI,CHESHVAN... ADAR_I,ADAR_II...) see 
     * {@link #monthID() }.
     * @return the month of the year of the object's date.
     */
    @Override
    public int month()
    {
        return monthInYear();
    }
    /**
     * the ordinal month in year. in regular year the range is 1..12, 
     * and in a leap year the range is 1..13.
     * to get the month ID (eg. TISHREI,CHESHVAN... ADAR_I,ADAR_II...) see 
     * {@link #monthID() }.
     * @return the month of the year of the object's date.
     */
    public int monthInYear() {
        return this.m_month;
    }

    @Override
    public int year() {
        return this.m_year;
    }

    @Override
    public int yearLength() {
        return this.m_yearLength;
    }

    public int yearFirstDay() {
        return this.m_yearFirstDay;
    }

    public int monthFirstDay() { //days since beginning
        int mo_year_t = mo_year_type(this.m_yearLength);
        return this.m_yearFirstDay + months_days_offsets[mo_year_t][this.m_month - 1];
    }

    public int monthFirstDay(int monthId) { //days since beginning
        int mo_year_t = mo_year_type(this.m_yearLength);
        return this.m_yearFirstDay + months_days_offsets[mo_year_t][monthFromID(monthId) - 1];
    }

    public static int monthFromIDByYear(int year, int monthId) {
        return monthFromIDByYearMonths(calculateYearMonths(year), monthId);
    }

    public static int monthFromIDByYearLength(int year_length, int monthId) {
        return monthFromIDByYearMonths((year_length > 355) ? 13 : 12, monthId);
    }

    public int monthFromID(int monthId) {
        return monthFromIDByYearMonths((this.m_yearLength > 355) ? 13 : 12, monthId);
    }

    public static int monthFromIDByYearMonths(int year_months, int monthId) {
        if (monthId < M_ID_ADAR) {
            return monthId + 1;
        }
        if (year_months > 12)//leap year
        {

            if (monthId >= M_ID_ADAR_I) {
                return monthId;
            }
            return 7;//adar II if monthId==M_ID_ADAR
        }
        else {
            if (monthId >= M_ID_NISAN) {
                return monthId - 1;
            }
            return 6; // regular adar
        }
    }

    public static int monthID(int months_in_year, int month) {
        if (months_in_year == 13)//leap year
        {
            if (month > 5)//if Adar or after
            {
                ++month;//skip regular Adar
            }
        }
        else {
            if (month > 6)//if Nisan or after
            {
                month += 2;//skip Adar I+II
            }
        }
        return month - 1;
    }

    public int monthID() {
        return monthID(calculateYearMonths(m_year), this.m_month);
    }

    public static String monthNameByID(int mID, YDateLanguage.Language language) {
        /*final String[][] months =
        {
            {"תשרי", "חשוון", "כסלו", "טבת",
            "שבט", "אדר",
            "אדר א'",
            "אדר ב'",
            "ניסן", "אייר",
            "סיוון", "תמוז", "אב", "אלול"},
            {"Tishrei", "Cheshvan", "Kislev", "Tevet",
            "Shevat", "Adar",
            "Adar I",
            "Adar II",
            "Nisan", "Iyar",
            "Sivan", "Tammuz", "Av", "Elul"},
        };*/
        return YDateLanguage.getLanguageEngine(language).getHebMonthToken(mID);
    }

    public String monthName(YDateLanguage.Language language) {
        return monthNameByID(monthID(), language);
    }

    public int daysSinceBeginning() {
        return m_yearFirstDay + m_dayInYear;
    }
    private static final int[][] months_days_offsets
            = {
                {0, 30, 59, 88, 117, 147, 176, 206, 235, 265, 294, 324, 353},
                {0, 30, 59, 89, 118, 148, 177, 207, 236, 266, 295, 325, 354},
                {0, 30, 60, 90, 119, 149, 178, 208, 237, 267, 296, 326, 355},
                {0, 30, 59, 88, 117, 147, 177, 206, 236, 265, 295, 324, 354, 383},
                {0, 30, 59, 89, 118, 148, 178, 207, 237, 266, 296, 325, 355, 384},
                {0, 30, 60, 90, 119, 149, 179, 208, 238, 267, 297, 326, 356, 385}
            };

    private void setMonthDay(int days)//finds the month by day in year.
    {
        int mo_year_t = mo_year_type(this.m_yearLength);
        int m = days * 2 / 59;
        if (months_days_offsets[mo_year_t][m] > days) {
            m--;
        }
        else if (months_days_offsets[mo_year_t][m + 1] <= days) {
            m++;
        }
        this.m_month = m + 1;
        this.m_day = days - months_days_offsets[mo_year_t][m] + 1;
    }

    /**
     * Return Hebrew year type based on size and first week day of year.
     * p - pshuta  353..355
     * m - meuberet 383..385
     * h - hasera [353,383]
     * k - kesidra [354,384]
     * s - shlema (melea) [355,385]
     * |year type| year length | Tishery 1 day of week
     * | 1       | 353         | 2  ph2
     * | XXXXX   | 353         | 3  ph3 impossible
     * | XXXXX   | 353         | 5  ph5 impossible
     * | 2       | 353         | 7  ph7
     * | XXXXX   | 354         | 2  pk2 impossible
     * | 3       | 354         | 3  pk3
     * | 4       | 354         | 5  pk5
     * | XXXXX   | 354         | 7  pk7 impossible
     * | 5       | 355         | 2  ps2
     * | XXXXX   | 355         | 3  ps3 impossible
     * | 6       | 355         | 5  ps5
     * | 7       | 355         | 7  ps7
     * | 8       | 383         | 2  mh2
     * | XXXXX   | 383         | 3  mh3 impossible
     * | 9       | 383         | 5  mh5
     * |10       | 383         | 7  mh7
     * | XXXXX   | 384         | 2  mk2 impossible
     * |11       | 384         | 3  mk3
     * | XXXXX   | 384         | 5  mk5 impossible
     * | XXXXX   | 384         | 7  mk7 impossible
     * |12       | 385         | 2  ms2
     * | XXXXX   | 385         | 3  ms3 impossible
     * |13       | 385         | 5  ms5
     * |14       | 385         | 7  ms7
     *
     * @param size_of_year Length of year in days
     * @param year_first_dw First week day of year (1..7)
     * @return A number for year type (1..14)
     */
    public static int ld_year_type(int size_of_year, int year_first_dw) {
        final int[] year_type_map
                = {1, 0, 0, 2, 0, 3, 4, 0, 5, 0, 6, 7,
                    8, 0, 9, 10, 0, 11, 0, 0, 12, 0, 13, 14};

        /* the year cannot start at days 1 4 6, so we are left with 2,3,5,7.
           and the possible lengths are 353 354 355 383 384 385...
           so we have 24 combinations, but only 14 are possible. (see table above)
         */
/* 2,3,5,7 -> 0,1,2,3 */
        int offset = (year_first_dw - 1) / 2;
        return year_type_map[4 * mo_year_type(size_of_year) + offset];
    }

    public int getYearTypeWeekDayLength() {
        return ld_year_type(this.m_yearLength, yearWeekDay());
    }

    public int yearWeekDay()//1- sunday,7-saturday
    {
        return this.m_yearFirstDay % 7 + 1;
    }

    public int week() {
        return ((this.m_dayInYear) + (this.m_yearFirstDay % 7)) / 7 + 1;
    }

    private static int mo_year_type(int year_length) {
        //0 hasera,1 kesidra,2 melea,3 meuberet hasera,4 meuberet kesidra,5 meuberet melea
        return ((year_length % 10) - 3) + (year_length - 350) / 10;
    }

    private static int calculateDayInYear(int year_length, int month, int day)//0..385
    {
        int mo_year_t = mo_year_type(year_length);
        return months_days_offsets[mo_year_t][month - 1] + day - 1;
    }

    public static int calculateDayInYearByMonthId(int year_length, int month_id, int day) {
        int month = monthFromIDByYearLength(year_length, month_id);
        return calculateDayInYear(year_length, month, day);
    }

    public boolean stepMonthForward(boolean cyclic) {
        int months_in_year = calculateYearMonths(m_year);
        if (m_month == months_in_year) {
            return setByYearMonthIdDay(m_year + (cyclic ? 0 : 1), M_ID_TISHREI, m_day);
        }
        else {
            return setByYearMonthIdDay(m_year, monthID(months_in_year, m_month + 1), m_day);
        }
    }

    public boolean stepMonthBackward(boolean cyclic) {
        if (m_month == 1) {
            return setByYearMonthIdDay(m_year - (cyclic ? 0 : 1), M_ID_ELUL, m_day);
        }
        else {
            int months_in_year = calculateYearMonths(m_year);
            return setByYearMonthIdDay(m_year, monthID(months_in_year, m_month - 1), m_day);
        }
    }

    @Override
    public int monthLength() {
        return monthLengthInYear(this.m_yearLength, this.m_month);
    }

    @Override
    public int previousMonthLength() {
        if (this.m_month == 1)//Elul is always 29 days
        {
            return 29;
        }
        return monthLengthInYear(this.m_yearLength, this.m_month - 1);
    }

    public static int monthLengthInYear(int year_length, int month) {
        int mo_year_t = mo_year_type(year_length);
        return months_days_offsets[mo_year_t][month] - months_days_offsets[mo_year_t][month - 1];
    }

    /**
     * checks how many months are in a certain year
     *
     * @param year a hebrew year
     * @return the number of months in this year
     */
    public static int calculateYearMonths(int year) {
        /*
         the loop:
         for x in range(0,19):
         &nbsp;&nbsp;print (235*(x+1)+1)/19-(235*x+1)/19
         gives exactly:
         12,12,13,12,12,13,12,13,12,12,13,12,12,13,12,12,13,12,13
         which is the 19-years period month's division
         */
        //return (235 * year + 1) / 19 - (235 * (year - 1) + 1) / 19;
        return MONTHS_DIVISION[(year - 1) % 19];
    }

    public static boolean isLeapYear(int year) {
        return MONTHS_DIVISION[(year - 1) % 19] == 13;
    }

    public static long parts_since_beginning(int year) {
        /*
         the loop:
         for x in range(0,19):
         &nbsp;&nbsp;print (235*(x+1)+1)/19-(235*x+1)/19
         gives exactly:
         12,12,13,12,12,13,12,13,12,12,13,12,12,13,12,12,13,12,13
         which is the 19-years period month's division
         */
        int months = (235 * (year - 1) + 1) / 19;//first year was year one. so we subtract one from year to start from 0.
        long parts = MOLAD + MONTH * (long) months;
        return parts;
    }

    public static int days_until_year(int year, long parts) {
        int days = (int) ((parts + MOLAD_ZAKEN_ROUNDING) / DAY);
        int parts_mod = (int) (parts % DAY);
        int year_type = ((year - 1) * 7 + 1) % 19;
        /* this magic gives us the following array:
         1, 8,15, 3,10,17, 5,12, 0, 7,14, 2, 9,16, 4,11,18, 6,13
         now if we compare it with the 235 months division:
         12,12,13,12,12,13,12,13,12,12,13,12,12,13,12,12,13,12,13
         we can see that all the leap years # >=12 and all the regular years # <12
         also, all the years that comes after a leap year have a number < 7
         */
        int week_day = (days % 7);
        if (parts_mod < DAY - MOLAD_ZAKEN_ROUNDING) {
            if (year_type < 12)//regular year (non leap)
            {
                if (week_day == TUESDAY && parts_mod >= HP(9, 204)) {
                    return days + 2;//we need to add 2 because Wednesday comes next (ADU)
                }
            }
            if (year_type < 7)//a year after a leap year
            {
                if (week_day == MONDAY && parts_mod >= HP(15, 589)) {
                    return days + 1;//we need to add only 1..
                }
            }
        }
        if (week_day == SUNDAY || week_day == WEDNESDAY || week_day == FRIDAY) {
            ++days;
        }
        return days;
    }

    public static int calculateYearLength(int year) {
        return calculateYearFirstDay(year + 1) - calculateYearFirstDay(year);
    }

    public static int calculateYearFirstDay(int year) {
        return days_until_year(year, parts_since_beginning(year));
    }

    public static int days_to_year(int days) {
        long orig_parts = (long) days * DAY;
        long parts = orig_parts - MOLAD;
        int months = (int) (parts / MONTH);
        parts = (parts % MONTH);
        int years = 1;//first year was year one.
        years += 19 * (months / MONTHS_IN_19Y);
        months = months % MONTHS_IN_19Y;
        int year_in_19 = ((months + 1) * 19 - 2) / 235;
        years += year_in_19;
        months = months - (235 * (year_in_19) + 1) / 19;
        parts += months * MONTH;
        int estimated_year_length = 353;
        if (calculateYearMonths(years) == 13) {
            estimated_year_length = 383;
        }
        long year_molad_parts = orig_parts - parts;
        int estimated_first_year_day = (int) ((year_molad_parts) / DAY);
        if (estimated_first_year_day + 2 <= days && days < estimated_first_year_day + estimated_year_length) {
            return years;
        }
        int year_first_day = days_until_year(years, year_molad_parts);
        if (days < year_first_day) {
            return years - 1;
        }
        int next_year_day = days_until_year(years + 1, parts_since_beginning(years + 1));
        if (days >= next_year_day) {
            return years + 1;
        }
        return years;
    }    
}