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
import kapandaria.GP.EventHandler;

/**
 * YDate class contains both Jewish and Gregorian date objects, and let you manipulate them.
 * @author Orr Dvori &lt;dvoreader@gmail.com&gt;
 * @version 4.0.5
 */
public class YDate {

    public interface TimeZoneProvider {

        /**
         * returns offset in hours for specific TimeZone.
         * @param d Date object to get TimeZone offset for.
         * @return offset in hours from UTC.
         */
        float getOffset(Date d); //offset in hours from UTC
    }
    /**
     * The day of the unix epoch (Jan. 1 1970). It measured by the "beginning
     * count".
     */
    static final int EPOCH_DAY = 2092591;//1.1.1970

    static final int SUNDAY = 0;//Sun - Sunne in old english
    static final int MONDAY = 1;//Moon - Mōna in old english
    static final int TUESDAY = 2;//Mars - Tīw in old english
    static final int WEDNESDAY = 3;//Mercury - Wōden in old english
    static final int THURSDAY = 4;//Jupiter - Þunor in old english
    static final int FRIDAY = 5;//Venus - frig in old english
    static final int SATURDAY = 6;//Saturn - Sætern in old english
    /**
     * The difference between the "beginning count" and the Julian count. (the
     * Julian count start earlier)
     */
    static final int JULIAN_DAY_OFFSET = 347997;

    public static final String[][] day_names = {
        {"ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"},
        {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"},
        {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}};
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
    public static final String[][] four_elements_names = {
        {"אש", "עפר", "רוח", "מים"},
        {"fire", "earth", "wind", "water"}
    };

    public static final class GregorianDate {

        /**
         * Number of days in 400 Gregorian years. The calculation is as follows:
         * Every four years we have an extra day in February. But every 100
         * years the previous rule doesn't take a place. In addition, every 400
         * year the rule of the 100 years doesn't take a place, and thus we do
         * have an extra day. So with the first rule we have in 400 years, 100
         * years with 366 days in them, and 300 years 365. And with the second
         * rule we have in 400 years, 96 years with 366 days in them, and 304
         * years 365. And with the third rule we have in 400 years, 97 years
         * with 366 days in them, and 303 years 365. To summerize this:
         * 97*366+303*365=146097.
         */
        static final int DAYS_IN_400 = 146097;
        /**
         * Number of days in regular four Gregorian years. The calculation is as
         * follows: Every regular four years we have an extra day in February.
         * To summerize this: 3*366+1*365=1461.
         */
        static final int DAYS_IN_4 = 1461;
        /**
         * The day of Jan. 1 1600 in the "beginning count". This constant is
         * important because for simplicity, we start all the calculations, only
         * after that date. Also this date starts a new cycle of 400 years.
         */
        static final int DAYS_OF_1600 = 1957451;// days since beginning up to year 1.1.1600 (14 in tevet, 5360)
        /**
         * The day of Jan. 1 2300 in the "beginning count". I limited the date
         * calculation to that date.
         */
        static final int DAYS_OF_2300 = 2213121;

        static final int[] HUNDRED_OFFSET = {0, 36525, 36525 * 2 - 1, 36525 * 3 - 2};
        /**
         * The gregorian year. year 1 is the first year in CE. There is no year
         * zero. Year -1 is the last year before Common Era. Anyway, the valid
         * range for this variable is 1600..2299
         */
        private int year;
        /**
         * The month in the year. There are twelve months in a year. The value
         * is in the range 1..12, while 1 denotes January, 2 denotes February ..
         * 12 denotes December
         */
        private int month;
        /**
         * The day in the month. The value is in the range 1..31 .
         */
        private int day;
        /**
         * The number of days in the year. The value may be only 365 or 366.
         */
        private int year_length;
        /**
         * The number of days from the beginning to the year's first day (1st of
         * January of the year)
         */
        private int year_first_day;
        /**
         * The day in the year (or number of days after year beginning). 0
         * denotes the year's first day (1st of January of the year). the value
         * is in the range 0..364 in regular year and in the range 0..365 in a
         * leap year.
         */
        private int day_in_year;
        /**
         * Is the date valid?
         */
        private boolean valid;

        /**
         * Simple copy constructor.
         */
        GregorianDate(GregorianDate o) {
            this.valid = o.valid;
            this.year = o.year;
            this.month = o.month;
            this.day = o.day;
            this.year_first_day = o.year_first_day;
            this.year_length = o.year_length;
            this.day_in_year = o.day_in_year;
        }

        /**
         * Constructs a date object by day, month and year.
         *
         * @param year Gregorian year in the range 1600..2299.
         * @param month Gregorian month (range 1..12, while 1 denotes January, 2
         * denotes February .. 12 denotes December).
         * @param day Gregorian day in month (range 1..31).
         */
        GregorianDate(int year, int month, int day) {
            valid = setByYearMonthDay(year, month, day);
        }

        /**
         * Set the members of the class by day, month and year.
         *
         * @return true if that date can be calculated properly and false
         * otherwise.
         */
        private boolean setByYearMonthDay(int year, int month, int day) {
            if (year >= 1600 && year < 2300) {
                this.year = year;
                //fix the month parameter
                if (month > 12) {
                    month = 12;
                }
                else if (month < 1) {
                    month = 1;
                }
                this.month = month;
                //calculate the year's first day in the "beginning count".
                this.year_first_day = days_until_year(this.year);
                //calculate the year's length.
                this.year_length = isLeap(this.year) ? 366 : 365;
                //calculate the month's length.
                int month_length = monthLength();
                //fix the day parameter
                if (day > month_length) {
                    day = month_length;
                }
                else if (day < 1) {
                    day = 1;
                }

                this.day = day;
                this.day_in_year = calculateDayInYear(this.year_length, this.month, this.day);
                return true; //validity
            }
            else {
                return false;
            }
        }

        /*public static int test_setByDays_new() {
            GregorianDate gd1 = new GregorianDate(1600, 1, 1);
            GregorianDate gd2 = new GregorianDate(1600, 1, 1);
            int i;
            for (i = DAYS_OF_1600; i < DAYS_OF_2300; ++i) {
                gd1.setByDaysDemystified(i);
                gd2.setByDays(i);
                if (gd1.year != gd2.year) {
                    break;
                }
                if (gd1.month != gd2.month) {
                    break;
                }
                if (gd1.day != gd2.day) {
                    break;
                }
                if (gd1.year_first_day != gd2.year_first_day) {
                    break;
                }
                if (gd1.day_in_year != gd2.day_in_year) {
                    break;
                }
            }
            return i;
        }*/
        private boolean setByDaysDemystified(int days) {
            if (days >= DAYS_OF_1600 && days < DAYS_OF_2300) {
                int gd_day;
                int gd_month;
                int gd_year;
                int gd_year_first_day = days;

                days -= DAYS_OF_1600;
                gd_year = 1600 + 400 * ((days) / DAYS_IN_400);
                days = days % DAYS_IN_400;
                int h = (days * 4) / DAYS_IN_400;// what hundred are we? 0 1 2 3 ?
                int not_h0 = (h + 3) / 4; //if h>0
                gd_year += 100 * h;
                days = days - HUNDRED_OFFSET[h];//get days after the currect century.
                int y_in_h = ((days + not_h0) * 4) / DAYS_IN_4;// which year in this hundred?
                gd_year += y_in_h;// add years in century.
                int y_in_h_not0 = (y_in_h + 99) / 100;//if y_in_h > 0 (maybe I'd just write that condition)?
                days = days - (y_in_h * DAYS_IN_4 + 3) / 4 + y_in_h_not0 * not_h0;//we subtract one day too much if we are not in the first year and not in the first hundred.
                gd_year_first_day -= days;

                int mo_year_t = isLeap(gd_year) ? 1 : 0;
                int m = days * 2 / 61;
                if (months_days_offsets[mo_year_t][m] > days) {
                    m--;
                }
                else if (months_days_offsets[mo_year_t][m + 1] <= days) {
                    m++;
                }
                gd_month = m + 1;
                gd_day = days - months_days_offsets[mo_year_t][m] + 1;

                this.year = gd_year;
                this.month = gd_month;
                this.day = gd_day;
                this.year_first_day = gd_year_first_day;
                this.year_length = isLeap(this.year) ? 366 : 365;
                this.day_in_year = days;
                return true;
            }
            else {
                return false;
            }
        }

        private boolean setByDays(int days) {
            if (days >= DAYS_OF_1600 && days < DAYS_OF_2300) {
                int gd_day;
                int gd_month;
                int gd_year;
                {
                    int l, n, i, j;
                    l = days + 68569 + JULIAN_DAY_OFFSET;
                    n = (4 * l) / DAYS_IN_400;
                    l = l - (DAYS_IN_400 * n + 3) / 4;
                    i = (4000 * (l + 1)) / 1461001;
                    /* that's 1,461,001 */
                    l = l - (DAYS_IN_4 * i) / 4 + 31;
                    j = (80 * l) / 2447;
                    gd_day = l - (2447 * j) / 80;
                    l = j / 11;
                    gd_month = j + 2 - (12 * l);
                    gd_year = 100 * (n - 49) + i + l;
                }

                this.year = gd_year;
                this.month = gd_month;
                this.day = gd_day;

                this.year_length = isLeap(this.year) ? 366 : 365;
                this.day_in_year = calculateDayInYear(this.year_length, this.month, this.day);
                this.year_first_day = days - this.day_in_year; //previously it was days_until_year(this.year);
                return true;
            }
            else {
                return false;
            }
        }

        /**
         * Constructs a date object by a day in the "beginning count". The
         * "beginning count" starts in the extrapolated creation day according
         * to the jewish tradition.
         *
         * @param days number of days since the beginning to construct the date
         * object from it.
         */
        public GregorianDate(int days) {
            valid = setByDaysDemystified(days);
        }

        /**
         * This method gives you a formatted string of the current date.
         * You may subclass the language class with a new FormatGregorianDate method,
         * to achieve different formatting options.
         * @param language The language object in which the string will be formatted.
         * @return a string in the format "August 1, 1999".
         */
        public String dayString(YDateLanguage.Language language) {
            return YDateLanguage.getLanguageEngine(language).FormatGregorianDate(day, month, year);
        }

        /**
         * Get the day in the week for that specific date.
         * @return the week day number in range 1..7, where 1 denotes sunday and 7 denotes saturday.
         */
        public int dayInWeek() {
            return daysSinceBeginning() % 7 + 1;
        }

        /**
         * Get the day in the month for that specific date.
         * @return the day number in range 1..31 .
         */
        public int dayInMonth() {
            return this.day;
        }

        /**
         * Get the day in the year for that specific date.
         * Please note that the first day of the year (January 1) gives 0.
         * @return the day in range 0..364 or 0..365 in a leap year.
         */
        public int dayInYear() {
            return this.day_in_year;
        }

        public int year() {
            return this.year;
        }

        public int month() {
            return this.month;
        }

        public int daysSinceBeginning() {
            return year_first_day + day_in_year;
        }

        /**
         * @return The number of days in the "beginning count" to the year's first day (1st of
         * January of the year)
         */
        public int yearFirstDay() {
            return this.year_first_day;
        }

        /**
         * see YDate.GregorianDate.yearFirstDay for more information
         * @return The number of days in the "beginning count" to the first day in the current month.
         */
        public int monthFirstDay() {
            return year_first_day + day_in_year - day + 1;
        }

        public int previousMonthLength() {
            if (this.month == 1)//December is always 31 days
            {
                return 31;
            }
            int mo_year_t = year_length - 365;
            return months_days_offsets[mo_year_t][month - 1] - months_days_offsets[mo_year_t][month - 2];
        }

        public double JulianDay() {
            return daysSinceBeginning() + JULIAN_DAY_OFFSET - 0.5;
        }

        public String monthName(YDateLanguage.Language language) {
            /*
            final String[][] months =
            {
                {"ינואר", "פברואר", "מרס", 
                 "אפריל", "מאי", "יוני", "יולי", 
                 "אוגוסט", "ספטמבר", "אוקטובר", 
                 "נובמבר", "דצמבר"},
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
                 "December"},
                {"Jan", 
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
                 "Dec"},
            };*/
            return YDateLanguage.getLanguageEngine(language).getGregMonthToken(this.month - 1);
        }
        private static final int[][] months_days_offsets
                = {
                    {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365},
                    {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366}
                };

        /**
         * Sets the month and day in month according to the day in year.
         *
         * @param days day in the year.
         */
        private void setMonthDay(int days) {
            int mo_year_t = isLeap(this.year) ? 1 : 0;
            int m = days * 2 / 61;
            if (months_days_offsets[mo_year_t][m] > days) {
                m--;
            }
            else if (months_days_offsets[mo_year_t][m + 1] <= days) {
                m++;
            }
            this.month = m + 1;
            this.day = days - months_days_offsets[mo_year_t][m] + 1;
            //TODO: create a static version for this method.
        }

        private static int calculateDayInYear(int year_length, int month, int day) {
            int mo_year_t = year_length - 365;
            return months_days_offsets[mo_year_t][month - 1] + day - 1;
        }

        public static int calculateDayInYear(boolean leap_year, int month, int day) {
            return months_days_offsets[leap_year ? 1 : 0][month - 1] + day - 1;
        }

        public int monthLength() {
            int mo_year_t = year_length - 365;
            return months_days_offsets[mo_year_t][month] - months_days_offsets[mo_year_t][month - 1];
        }

        public int yearLength() {
            return this.year_length;
        }

        public static boolean isLeap(int year) {
            return ( (year % 400) == 0)||( (year % 4) == 0 && (year % 100) != 0);
        }

        public static int days_until_year(int year) {

            int years_since_1600 = year - 1600;
            int year_first_day = DAYS_OF_1600;
            year_first_day += DAYS_IN_400 * (years_since_1600 / 400);
            year = years_since_1600 % 400;
            int hundred= year / 100;
            year_first_day += HUNDRED_OFFSET[hundred];
            if (hundred == 0) {//if we are in the first hundred of the four hundreds
                year_first_day += ((year + 3) / 4) + year * 365;
                return year_first_day; //finito!
            }
            //else if we are in one of the other 3 hundreds 
            year = year % 100;//get the year in this hundred.
            //in the first four years of those 3 hundreds we don't have a leap february.
            //so we try to do some tricks to skip an extra "if".
            //if the assumption (-1)/4 ==0 is incorrect (like in python), use the following code.
            if ((-1) / 4 != 0) {//we can't save that extra "if" so we do the abs function.
                year_first_day += (Math.abs(year - 1) / 4) + year * 365;
                //year_first_day += (((year == 0) ? 0 : year - 1) / 4) + year * 365;
            }
            else {//but if we are in java, that former "if" is optimized out (omitted).
                year_first_day += ((year - 1) / 4) + year * 365;
            }
            return year_first_day;
        }

        public void stepMonthForward(boolean cyclic) {
            setByYearMonthDay(year + (cyclic ? 0 : (month == 12 ? 1 : 0)), (month % 12) + 1, day);
        }

        public void stepMonthBackward(boolean cyclic) {
            setByYearMonthDay(year - (cyclic ? 0 : (month == 1 ? 1 : 0)), ((month + 10) % 12) + 1, day);
        }
    }

    public static final class JewishDate {

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

        private int year = 0; // year 1 is the first year.
        private int month; // range 1..12,or 1..13 in leap year

        private int day; // range 1..30
        private long year_molad_parts; // parts since the beginning
        private int year_length; // days in year - 353,354,355,383,384,385
        private int year_first_day; // first day of rosh hashana from the beginning
        private int day_in_year; // days after year beginning. first day in year  is 0
        private boolean valid;

        public JewishDate(JewishDate o) {
            this.valid = o.valid;
            this.year = o.year;
            this.month = o.month;
            this.day = o.day;
            this.year_molad_parts = o.year_molad_parts;
            this.year_first_day = o.year_first_day;
            this.year_length = o.year_length;
            this.day_in_year = o.day_in_year;
        }

        private JewishDate(int year, int month, int day) {
            if (year >= 4119 && year < 7001) {
                this.valid = true;
                this.year = year;
                this.month = month;
                this.day = day;
                calculateYearVariables();
                this.day_in_year = calculateDayInYear(this.year_length, month, day);
            }
            else {
                this.valid = false;
            }
        }

        private void calculateYearVariables() {
            this.year_molad_parts = parts_since_beginning(year);
            this.year_first_day = days_until_year(year, this.year_molad_parts);
            this.year_length = days_until_year(year + 1, parts_since_beginning(year + 1)) - this.year_first_day;
        }

        private void setByYearMonthIdDay(int year, int month_id, int day) {
            if (year >= 4119 && year < 7001) {
                this.valid = true;
                if (this.year != year) {
                    this.year = year;
                    calculateYearVariables();
                }
                this.month = monthFromIDByYearLength(this.year_length, month_id);
                int month_length = monthLength();
                this.day = Math.min(month_length, day);
                this.day_in_year = calculateDayInYear(this.year_length, this.month, this.day);
            }
        }

        private boolean setByDays(int days) {
            if (!(days >= DAYS_OF_4119 && days < DAYS_OF_6001)) {
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
            this.year_molad_parts = orig_parts - parts;
            this.year_first_day = days_until_year(years, this.year_molad_parts);
            int next_year_day = days_until_year(years + 1, parts_since_beginning(years + 1));
            int months_in_year;
            if (days >= this.year_first_day && days < next_year_day) {
                this.year = years;
                this.year_length = next_year_day - this.year_first_day;
            }
            else {

                if (days < this.year_first_day) {
                    this.year = years - 1;
                    months_in_year = calculateYearMonths(this.year);
                    this.year_molad_parts -= months_in_year * MONTH;
                    next_year_day = this.year_first_day;
                    this.year_first_day = days_until_year(this.year, this.year_molad_parts);
                    this.year_length = next_year_day - this.year_first_day;
                }
                else {

                    if (days >= next_year_day) {
                        this.year = years + 1;
                        months_in_year = calculateYearMonths(years);
                        this.year_molad_parts += months_in_year * MONTH;
                        this.year_first_day = next_year_day;
                        this.year_length = days_until_year(this.year + 1, parts_since_beginning(this.year + 1)) - this.year_first_day;
                    }
                    else {
// should not get here
                    }
                }
            }
            this.day_in_year = days - this.year_first_day;
            setMonthDay(this.day_in_year);
            return true;
        }

        public JewishDate(int days) {
            valid = setByDays(days);
        }

        /**
         * Gives a cloned object with the next date.
         * That is useful to get the correct date if you are after twilight.
         * @return a cloned object of the next day.
         */
        public JewishDate getDayAfterTwilight() {
            if (day_in_year + 1 == year_length)// we reached the end of the year.
            {
                return new JewishDate(year + 1, 1, 1);//we must recalculate the year variables.
            }
            else {
                JewishDate cln = new JewishDate(this);
                cln.day_in_year++;
                if (cln.day == cln.monthLength())// we reached the end of the month.
                {
                    cln.day = 1;
                    cln.month++;
                }
                else {
                    cln.day++;
                }
                return cln;
            }
        }

        public String dayString(YDateLanguage.Language lang) {
            return YDateLanguage.getLanguageEngine(lang).FormatJewishDate(day, monthID(), year);
        }

        public int NumberOfShabbats() {
            int year_diw = year_first_day % 7;
            int diy = YDate.getNext(YDate.SATURDAY, year_diw) - year_diw;
            return (year_length - (diy) + 6) / 7;
        }

        public boolean TenTalVeMatar(boolean diaspora) {
            if (diaspora) {
                //int day_tkufa=dayInTkufa();
                int starting = getTkufaDay(0) + 59; // the sixty day from when Tkufat Tishrei begins (first day in count)
                if (daysSinceBeginning() < starting) {
                    return false;
                }
            }
            else {
                if (day_in_year < 36) { //36 is day in year of 7 in Cheshvan
                    return false;
                }
            }
            int pessach_day = calculateDayInYearByMonthId(year_length, M_ID_NISAN, 15);
            if (day_in_year >= pessach_day) {
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
            int shmini_azeret = 21;
            if ((day_in_year == shmini_azeret && !MusafAndAfter) || day_in_year < shmini_azeret) {
                return false;
            }
            int pessach_day = calculateDayInYearByMonthId(year_length, M_ID_NISAN, 15);
            return (day_in_year == pessach_day && !MusafAndAfter) || day_in_year < pessach_day;
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
            int shmini_azeret = 21;
            if (day_in_year == shmini_azeret) {
                return Gvurot.MORID_HATAL_BECOME_MASHIV_HARUACH;
            }
            int pessach_day = calculateDayInYearByMonthId(year_length, M_ID_NISAN, 15);
            if (day_in_year == pessach_day) {
                return Gvurot.MASHIV_HARUACH_BECOME_MORID_HATAL;
            }
            if (day_in_year < shmini_azeret || day_in_year > pessach_day) {
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
            if (this.year >= yovel_year) {
                return 1 + (this.year - yovel_year + 49) % 50;
            }
            return 1 + (this.year - 4117) % 7;//4116 was a shmita year
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
        public int getTkufaDay(int tkufa)//0 -Tishrei, 1- Tevet, 2 - Nisan , 3- Tammuz
        {
            long tkufa_parts = (long) ((year - 4117) * 4 - 2 + tkufa) * TKUFA;
            return (int) (tkufa_parts / DAY) + DAYS_OF_TKUFA_CYCLE_4117;
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
         * find out when B' H' B' after Sukkot...
         *
         * @return day from beginning for the Shabbat before the first taanit
         * monday.
         */
        public int taanitBetHehBetForCheshvan() {
            /* 1 in tishrey is day 0. tishrey has 30 days. so 30 in tishrey is day 29. and 1 cheshvan is day 30.
             */
            return YDate.getNext(YDate.SATURDAY, year_first_day + 31);
        }

        /**
         * find out when B' H' B' after Pesach... There is a tradition to bless
         * those who fast in these days in the Shabbat before the Taaniot
         *
         * @return day from beginning for the Shabbat before the first taanit
         * monday.
         */
        public int taanitBetHehBetForIyar() {
            return YDate.getNext(YDate.SATURDAY, monthFirstDay(M_ID_IYAR) + 1);//+2 to get first monday, +5 to get thursday, and +9 to get the last monday.
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
            int day_of_pessah = JewishDate.calculateDayInYearByMonthId(year_length, JewishDate.M_ID_NISAN, 15) + year_first_day;
            return Format.alphabeta[(year_first_day % 7) + 1] + Format.alphabeta[yeartype[year_length % 10 - 3]]
                    + Format.alphabeta[(day_of_pessah % 7) + 1] + ((year_length >= 383) ? " מעוברת" : " פשוטה");
        }

        public boolean roshChodesh() {
            return (day == 30 || day == 1);
        }

        public boolean mevarchinShabbat() {
            return (day >= 23 && day < 30 && dayInWeek() == 7 && monthID() != M_ID_ELUL);
        }//not mevarchin before Tishrei

        public boolean yomHakhel() {
            return (day == 16 && monthID() == M_ID_TISHREI
                    && (ShmitaOrdinal() - 1) % 7 == 0);
        }// in the year after Shmita, after first day of Succot.

        public boolean shabbaton(YDatePreferences.DiasporaType diaspora) {
            if (diaspora == YDatePreferences.DiasporaType.Both) {
                return shabbaton(true) || shabbaton(false);
            }
            return shabbaton(diaspora == YDatePreferences.DiasporaType.Diaspora);
        }

        public boolean shabbaton(boolean diaspora) {
            byte[] events = YDateAnnual.getEvents(year_length, year_first_day, diaspora);
            short type = YDateAnnual.getEventType(events[day_in_year]);
            return ((type & YDateAnnual.EV_TYPE_MASK) == YDateAnnual.EV_YOM_TOV) || (dayInWeek() == 7);
        }

        /**
         * return list of the thursdays of taanit shovavim - shmot vaera bo beshalach yitro mishpatim truma tezave
         * @return 
         */
        public int[] shovavim() {

            int first_sunday_of_shovavim = TorahReading.getShabbatBereshit(yearLength(), yearFirstDay()) + 12 * 7 - 6;
            int shovavim_thursday = first_sunday_of_shovavim + 4;//day since beginning.
            int shovavim_tat_len = isLeapYear(year) ? 8 : 6;
            int taanit[] = new int[shovavim_tat_len];

            for (int i = 0; i < shovavim_tat_len; ++i) {
                //TODO:
            }
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int dayOfChanukkah() {
            int diy = dayInYear();
            int chnkday = YDate.JewishDate.calculateDayInYearByMonthId(year_length, M_ID_KISLEV, 25);
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
            int nine_av = YDate.JewishDate.calculateDayInYearByMonthId(year_length, M_ID_AV, 9); // 9 in Av.
            if ((nine_av + year_first_day) % 7 == SATURDAY) {
                ++nine_av;
            }
            return nine_av;
        }

        public boolean isNineAv() {

            return dayInYear() == nineAvDayInYear(); // 9 in Av.
        }

        public int sfiratHaomer() {
            int before_fisrt_omer_day = calculateDayInYearByMonthId(this.year_length, M_ID_NISAN, 15);//pessah night
            int omer = this.day_in_year - before_fisrt_omer_day;
            if (omer < 0 || omer > 49) //if omer ==0 then its the day before the night of hasfira
            {
                return -1;
            }
            return omer;
        }

        public int MasechetAvotChapter(boolean AskenazTradition) {
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

        public long MoladParts() {
            return year_molad_parts + (month - 1) * MONTH;
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

        public int dayInMonth()//starts from one
        {
            return this.day;
        }

        public int dayInWeek()//starts from one
        {
            return daysSinceBeginning() % 7 + 1;
        }

        public String dayInWeekName(boolean Heb) {
            return day_names[Heb ? 0 : 1][daysSinceBeginning() % 7];
        }

        public int dayInYear()//starts from zero
        {
            return this.day_in_year;
        }

        public int monthInYear() {
            return this.month;
        }

        public int year() {
            return this.year;
        }

        public int yearLength() {
            return this.year_length;
        }

        public int yearFirstDay() {
            return this.year_first_day;
        }

        public int monthFirstDay() {
            int mo_year_t = mo_year_type(this.year_length);
            return this.year_first_day + months_days_offsets[mo_year_t][this.month - 1];
        }

        public int monthFirstDay(int monthId) {
            int mo_year_t = mo_year_type(this.year_length);
            return this.year_first_day + months_days_offsets[mo_year_t][monthFromID(monthId) - 1];
        }

        public static int monthFromIDByYear(int year, int monthId) {
            return monthFromIDByYearMonths(calculateYearMonths(year), monthId);
        }

        public static int monthFromIDByYearLength(int year_length, int monthId) {
            return monthFromIDByYearMonths((year_length > 355) ? 13 : 12, monthId);
        }

        public int monthFromID(int monthId) {
            return monthFromIDByYearMonths((this.year_length > 355) ? 13 : 12, monthId);
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
            return monthID(calculateYearMonths(year), this.month);
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
            return year_first_day + day_in_year;
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
            int mo_year_t = mo_year_type(this.year_length);
            int m = days * 2 / 59;
            if (months_days_offsets[mo_year_t][m] > days) {
                m--;
            }
            else if (months_days_offsets[mo_year_t][m + 1] <= days) {
                m++;
            }
            this.month = m + 1;
            this.day = days - months_days_offsets[mo_year_t][m] + 1;
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
            return ld_year_type(this.year_length, yearWeekDay());
        }

        public int yearWeekDay()//1- sunday,7-saturday
        {
            return this.year_first_day % 7 + 1;
        }

        public int week() {
            return ((this.day_in_year) + (this.year_first_day % 7)) / 7 + 1;
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

        public void stepMonthForward(boolean cyclic) {
            int months_in_year = calculateYearMonths(year);
            if (month == months_in_year) {
                setByYearMonthIdDay(year + (cyclic ? 0 : 1), M_ID_TISHREI, day);
            }
            else {
                setByYearMonthIdDay(year, monthID(months_in_year, month + 1), day);
            }
        }

        public void stepMonthBackward(boolean cyclic) {
            if (month == 1) {
                setByYearMonthIdDay(year - (cyclic ? 0 : 1), M_ID_ELUL, day);
            }
            else {
                int months_in_year = calculateYearMonths(year);
                setByYearMonthIdDay(year, monthID(months_in_year, month - 1), day);
            }
        }

        public int monthLength() {
            return monthLengthInYear(this.year_length, this.month);
        }

        public int previousMonthLength() {
            if (this.month == 1)//Elul is always 29 days
            {
                return 29;
            }
            return monthLengthInYear(this.year_length, this.month - 1);
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

    public JewishDate hd;
    public GregorianDate gd;

    private EventHandler dateChanged = new EventHandler();

    public void registerOnDateChanged(EventHandler.Listener listener) {
        dateChanged.addListener(listener);
    }

    private void notifyDateChanged() {
        dateChanged.trigger(this);
    }

    private static boolean commonRange(int days) {
        return (days < JewishDate.DAYS_OF_6001 && days >= GregorianDate.DAYS_OF_1600);
    }

    public boolean seekBy(int offset) {
        int days = hd.daysSinceBeginning() + offset;
        return setByDays(days);
    }

    public boolean setByDays(int days) {
        if (commonRange(days)) {
            gd.setByDays(days);
            hd.setByDays(days);
            notifyDateChanged();
            return true;
        }
        return false;
    }

    public enum STEP_TYPE {
        HEB_MONTH_FORWARD,
        HEB_MONTH_BACKWARD,
        HEB_MONTH_FORWARD_CYCLIC,
        HEB_MONTH_BACKWARD_CYCLIC,
        HEB_YEAR_FORWARD,
        HEB_YEAR_BACKWARD,
        GRE_MONTH_FORWARD,
        GRE_MONTH_BACKWARD,
        GRE_MONTH_FORWARD_CYCLIC,
        GRE_MONTH_BACKWARD_CYCLIC,
        GRE_YEAR_FORWARD,
        GRE_YEAR_BACKWARD,
    }

    public boolean step(STEP_TYPE st) {
        boolean cyclic = (st == STEP_TYPE.HEB_MONTH_BACKWARD_CYCLIC
                || st == STEP_TYPE.HEB_MONTH_FORWARD_CYCLIC
                || st == STEP_TYPE.GRE_MONTH_BACKWARD_CYCLIC
                || st == STEP_TYPE.GRE_MONTH_FORWARD_CYCLIC);
        boolean heb_changed = (st == STEP_TYPE.HEB_MONTH_BACKWARD_CYCLIC
                || st == STEP_TYPE.HEB_MONTH_BACKWARD || st == STEP_TYPE.HEB_MONTH_FORWARD || st == STEP_TYPE.HEB_MONTH_FORWARD_CYCLIC
                || st == STEP_TYPE.HEB_YEAR_BACKWARD || st == STEP_TYPE.HEB_YEAR_FORWARD);
        JewishDate new_hd = null;
        GregorianDate new_gd = null;
        if (heb_changed) {
            new_hd = new JewishDate(hd);
        }
        else {
            new_gd = new GregorianDate(gd);
        }
        switch (st) {
            case HEB_MONTH_BACKWARD_CYCLIC:
            case HEB_MONTH_BACKWARD:
                new_hd.stepMonthBackward(cyclic);
                break;
            case HEB_MONTH_FORWARD_CYCLIC:
            case HEB_MONTH_FORWARD:
                new_hd.stepMonthForward(cyclic);
                break;
            case HEB_YEAR_BACKWARD:
                new_hd.setByYearMonthIdDay(hd.year() - 1, hd.monthID(), hd.dayInMonth());
                break;
            case HEB_YEAR_FORWARD:
                new_hd.setByYearMonthIdDay(hd.year() + 1, hd.monthID(), hd.dayInMonth());
                break;
            case GRE_MONTH_BACKWARD_CYCLIC:
            case GRE_MONTH_BACKWARD:
                new_gd.stepMonthBackward(cyclic);
                break;
            case GRE_MONTH_FORWARD_CYCLIC:
            case GRE_MONTH_FORWARD:
                new_gd.stepMonthForward(cyclic);
                break;
            case GRE_YEAR_BACKWARD:
                new_gd.setByYearMonthDay(gd.year() - 1, gd.month(), gd.dayInMonth());
                break;
            case GRE_YEAR_FORWARD:
                new_gd.setByYearMonthDay(gd.year() + 1, gd.month(), gd.dayInMonth());
                break;
        }
        if (heb_changed) {
            return ApplyJewishToGregorian(new_hd);
        }
        else {
            return ApplyGregorianToJewish(new_gd);
        }
    }

    private boolean ApplyGregorianToJewish(GregorianDate new_gd) {
        int days = new_gd.daysSinceBeginning();
        if (commonRange(days) && new_gd.valid) {
            gd = new_gd;
            hd.setByDays(days);
            notifyDateChanged();
            return true;
        }
        return false;
    }

    private boolean ApplyJewishToGregorian(JewishDate new_hd) {
        int days = new_hd.daysSinceBeginning();
        if (commonRange(days) && new_hd.valid) {
            hd = new_hd;
            gd.setByDays(days);
            notifyDateChanged();
            return true;
        }
        return false;
    }

    public void setByHebrewYearMonthIdDay(int year, int month_id, int day) {
        JewishDate new_hd = new JewishDate(hd);
        new_hd.setByYearMonthIdDay(year, month_id, day);
        ApplyJewishToGregorian(new_hd);
    }

    public void setByDate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int gd_day = cal.get(Calendar.DAY_OF_MONTH);
        int gd_mon = cal.get(Calendar.MONTH) + 1;
        int gd_year = cal.get(Calendar.YEAR);
        setByGregorianYearMonthDay(gd_year, gd_mon, gd_day);
    }

    public void setByGregorianYearMonthDay(int year, int month, int day) {
        if (gd.year() != year || gd.month() != month || gd.dayInMonth() != day) {
            GregorianDate new_gd = new GregorianDate(gd);
            new_gd.setByYearMonthDay(year, month, day);
            ApplyGregorianToJewish(new_gd);
        }
    }

    private static final byte INIT_JD = 0;
    private static final byte INIT_JD_MID = 1;
    private static final byte INIT_GD = 2;

    private YDate(short year, byte mon, byte day, byte init) {
        switch (init) {
            case INIT_JD:
                hd = new JewishDate(year, mon, day);
            case INIT_JD_MID:
                if (init == INIT_JD_MID) {
                    hd = new JewishDate(year, JewishDate.monthFromIDByYear(year, mon), day);
                }
                gd = new GregorianDate(hd.daysSinceBeginning());
                break;
            default:
                gd = new GregorianDate(year, mon, day);
                hd = new JewishDate(gd.daysSinceBeginning());
        }
    }

    private YDate(JewishDate hd, GregorianDate gd) {
        this.gd = new GregorianDate(gd);
        this.hd = new JewishDate(hd);
    }

    private YDate(int days) {
        gd = new GregorianDate(days);
        hd = new JewishDate(days);
    }

    public static YDate createFrom(YDate other) {
        return new YDate(other.hd, other.gd);
    }

    public static YDate createFrom(int days) {
        return new YDate(days);
    }

    public static YDate createFrom(Date d, Calendar cal) {
        cal.setTime(d);
        int gd_day = cal.get(Calendar.DAY_OF_MONTH);
        int gd_mon = cal.get(Calendar.MONTH) + 1;
        int gd_year = cal.get(Calendar.YEAR);
        //long t = d.getTime(); //milliseconds since 1.1.70 00:00 GMT+
        return new YDate((short) gd_year, (byte) gd_mon, (byte) gd_day, INIT_GD);
    }

    public static YDate createFrom(Date d, TimeZone tz) {
        Calendar cal = Calendar.getInstance(tz);
        return createFrom(d, cal);
    }

    public static YDate createFrom(Date d) {
        Calendar cal = Calendar.getInstance();
        return createFrom(d, cal);
    }

    public static YDate createFromJewishMonthId(int year, int month_id, int day) {
        return new YDate((short) year, (byte) month_id, (byte) day, INIT_JD_MID);
    }

    public static YDate createFromJewish(int year, int month, int day) {
        return new YDate((short) year, (byte) month, (byte) day, INIT_JD);
    }

    public static YDate createFromGregorian(int year, int month, int day) {
        return new YDate((short) year, (byte) month, (byte) day, INIT_GD);
    }

    public static YDate getNow() {
        Date d = new Date();
        return createFrom(d);
    }

    /**
     * Return the upcoming day in week, or the current day if it is that certain day in week.
     * 
     * @param diw day in week. un range 0..6
     * @param days day in "beginning count" or other count that day 0 is sunday
     * @return days + x (6 &gt; = x &gt; = 0). that gives that certain day in week. 
     */
    public static int getNext(int diw, int days) // return the upcoming diw (or today if it's that diw)
    {
        int diff = (diw - days % 7 + 7) % 7;
        return (days + diff);
    }

    public static int getPrevious(int diw, int days) {
        return getNext(diw, days - 6);
    }

    public static Date toDate(int days, float hour)//hour in utc
    {
        long millis = (long) (days - EPOCH_DAY) * 3600L * 24 * 1000L;
        millis += (hour * 3600L * 1000L);
        return new Date(millis);
    }

    public static int JdToDays(double jd)//hour in utc
    {
        return (int) (jd + 0.5001 - JULIAN_DAY_OFFSET);
    }
    public static double DaysToJd(int days) {
            return days + JULIAN_DAY_OFFSET - 0.5;
        }

    public static String getTimeString(Date d, TimeZone tz, boolean seconds) {
        Calendar cal = Calendar.getInstance(tz);
        cal.setTime(d);

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);
        if (seconds) {
            return Format.TimeString(hour, min, sec);
        }
        return Format.TimeString(hour, min);
    }

    public boolean shabbaton(YDatePreferences.DiasporaType diaspora) {
        return hd.shabbaton(diaspora);
    }

}
