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
library ydate;

import 'abstract_date.dart';
import 'dart:math';

/*
 *
 * @author Orr Dvori
 */
enum JewishMonth {
  TISHREI, //0;
  CHESHVAN, //1;
  KISLEV, //2;
  TEVET, //3;
  SHEVAT, //4;
  ADAR, //5;
  ADAR_I, //6;
  ADAR_II, //7;
  NISAN, //8;
  IYAR, //9;
  SIVAN, //10;
  TAMMUZ, //11;
  AV, //12;
  ELUL //13;
}

enum Star {
  MERCURY, //0;
  MOON, //1;
  SATURN, //2;
  JUPITER, //3;
  MARS, //4;
  SUN, //5;
  VENUS, //6;
}

/// The Four Elements
///
/// The book of Yezira claims the following:
/// Every zodiac sign is related to an element.
/// The signs that are connected with Fire are: Aries, Leo, Sagittarius.
/// The signs that are connected with Earth are: Taurus, Virgo, Capricorn.
/// The signs that are connected with Wind are: Gemini, Libra, Aquarius.
/// The signs that are connected with Water are: Cancer, Scorpio, Pisces.
/// The ones with the element of Fire doesn't get along with those of Water
/// The ones with the element of Earth doesn't get along with those of Wind
enum Element {
  /// Zodiac signs related to Fire: Aries, Leo, Sagittarius.
  FIRE, //0
  /// Zodiac signs related to Earth: Taurus, Virgo, Capricorn.
  EARTH, //1
  /// Zodiac signs related to Wind: Gemini, Libra, Aquarius.
  WIND, //2
  /// Zodiac signs related to Water: Cancer, Scorpio, Pisces.
  WATER, //3
}

class JewishDate extends ADMYDate {
  /// The first day of the year 4119 (GDN). This hebrew dates system was founded in the year 4119 (359CE).
  static const int DAYS_OF_4119 = 1504084;

  /// The year 4117 was the most recent year before the beginning of the dating system which sun blessing cycle of 28 started in.
  /// We start calculations of 4 Tkufut and sun blessing from that year. (365.25 days per year of Shmuel)
  static const int DAYS_OF_TKUFA_CYCLE_4117 = 1503540;

  /// The first day of the year 6001 (GDN). Six thousand years the world shall exists.
  /// This is the upper bound of the jewish dating system.
  static const int DAYS_OF_6001 = 2191466;

  /// Number of year types. an year type is determined by the day in week of the first day and the year's length. Only 14 combinations are valid.
  static const int N_YEAR_TYPES = 14;

  /// Length of hour in hour-parts (1080).
  static const int HOUR = 1080;

  /// Length of day in hour-parts (1080).
  static const int DAY = (24 * HOUR);

  /// Length of week in hour-parts (1080).
  static const int WEEK = (7 * DAY);

  /// Length of month in hour-parts (1080).
  static final int MONTH = 29 * DAY + HP(12, 793);

  /// Molad of the estimated first month was on monday of the seven days of genesis, in the sixth hour (starting from sunset).
  static final int MOLAD = WeekDay.MONDAY.index * DAY + HP(5, 204);

  /// The number of months in 19 years.
  /// The jewish dating system has cycles of 19 years where 7 of the 19 has 13 month in year, and the other 12 has 12 month in year.
  /// So the calculation is as follow: 12*12+7*13 = 235
  /// see also [MONTHS_DIVISION] for the distribution of years.
  static const int MONTHS_IN_19Y = 235;

  /// Molad Zaken happens when the Molad is in the last 6 hours of the day (before sunset).
  static const int MOLAD_ZAKEN_ROUNDING = 6 * HOUR;

  /// Length of Tkufa in hour-parts (1080).
  static const int TKUFA = 91 * DAY + 7 * HOUR + 540;

  /// Length of Mazal in hour-parts (1080). Mazal equals to 365.25 days divided by 12
  static const int MAZAL = 30 * DAY + 10 * HOUR + 540;

  /// The year of Exodos
  static const int YEZIAT_MIZRAIM = 2449;

  /// The year that Documents dating system refers to.
  static const int MINIAN_SHTAROT = 3449;

  /// The year of destruction of first Temple
  static const int HORBAN_BAIT_RISHON = 3339;

  /// The year of destruction of second Temple
  static const int HORBAN_BAIT_SHENI = 3829;

  ///Number of months per year in a 19 years cycle.
  static const List<int> MONTHS_DIVISION = [
    12,
    12,
    13,
    12,
    12,
    13,
    12,
    13,
    12,
    12,
    13,
    12,
    12,
    13,
    12,
    12,
    13,
    12,
    13
  ];

  static int HP(int h, int p) => h * HOUR + p;

  /// Year. valid range: 4119..6000 (year 1 was the first year).
  int m_year = 0;

  /// Month in year. valid range 1..12, or 1..13 in leap year.
  int m_month;

  /// Day in month. valid range: 1..30.
  int m_day;

  /// hour-parts since the beginning of genesis of the first day in the current year.
  int m_yearGParts;

  /// Length of the year in days. The only possible values are - 353, 354, 355, 383, 384, 385.
  int m_yearLength;

  /// The GDN of the first day in the current year.
  int m_yearFirstDay;

  /// Days since first day in the year. For the first day in the year the value will be 0.
  int m_dayInYear;

  /// Whether the date object represents a valid date.
  bool m_valid = false;

  @override
  int dayInMonth() //starts from one
  {
    return this.m_day;
  }

  WeekDay dayInWeekEnum() //starts from zero
  {
    return WeekDay.values[GDN() % 7];
  }

  int dayInWeek() //starts from one
  {
    return GDN() % 7 + 1;
  }

  @override
  int dayInYear() //starts from zero
  {
    return this.m_dayInYear;
  }

  @override
  int monthLength() {
    return monthLengthInYear(this.m_yearLength, this.m_month);
  }

  @override
  int previousMonthLength() {
    if (this.m_month == 1) //Elul is always 29 days
    {
      return 29;
    }
    return monthLengthInYear(this.m_yearLength, this.m_month - 1);
  }

  static int monthLengthInYear(int year_length, int month) {
    int yearLenType = yearLengthType(year_length);
    return _monthsDaysOffsets[yearLenType][month] -
        _monthsDaysOffsets[yearLenType][month - 1];
  }

  /*
     * the ordinal month in year. in regular year the range is 1..12, 
     * and in a leap year the range is 1..13.
     * to get the month ID (eg. TISHREI,CHESHVAN... ADAR_I,ADAR_II...) see 
     * [monthID].
     * return the month of the year of the object's date.
     */
  @override
  int month() {
    return this.m_month;
  }

  /*
     * the number of months in year. in regular year the range is 1..12, 
     * and in a leap year the range is 1..13.
     * to get the month ID (eg. TISHREI,CHESHVAN... ADAR_I,ADAR_II...) see 
     * {@link #monthID() }.
     * @return the month of the year of the object's date.
     */
  int monthsInYear() {
    return calculateYearMonths(this.m_year);
  }

  @override
  int year() {
    return this.m_year;
  }

  /// Empty constructor that construct invalid date.
  JewishDate() {
    this.m_valid = false;
    this.m_year = 1;
    this.m_month = 1;
    this.m_day = 1;
    this.m_yearGParts = 0;
    this.m_yearFirstDay = 0;
    this.m_yearLength = 354;
    this.m_dayInYear = 0;
  }

  /// Simple copy constructor.
  /// note that event handler and sync group are not cloned.
  JewishDate.from(JewishDate o) {
    this.m_valid = o.m_valid;
    this.m_year = o.m_year;
    this.m_month = o.m_month;
    this.m_day = o.m_day;
    this.m_yearGParts = o.m_yearGParts;
    this.m_yearFirstDay = o.m_yearFirstDay;
    this.m_yearLength = o.m_yearLength;
    this.m_dayInYear = o.m_dayInYear;
  }

  /// Creates a date object for the current time.
  ///
  /// This method assumes that the day starts at midnight.
  /// This method concern about TimeZone.
  JewishDate.now() {
    DateTime nowDate = DateTime.now();
    Duration durationSinceEpoch =
        Duration(milliseconds: nowDate.millisecondsSinceEpoch);
    durationSinceEpoch += nowDate.timeZoneOffset;
    int gdn = ADate.EPOCH_DAY + durationSinceEpoch.inDays;
    setByGDN(gdn);
  }

  /// Mimic other date
  ///
  /// Event-handlers and sync-group are not mimicked.
  bool mimic(JewishDate o) {
    this.m_valid = o.m_valid;
    this.m_year = o.m_year;
    this.m_month = o.m_month;
    this.m_day = o.m_day;
    this.m_yearGParts = o.m_yearGParts;
    this.m_yearFirstDay = o.m_yearFirstDay;
    this.m_yearLength = o.m_yearLength;
    this.m_dayInYear = o.m_dayInYear;
    return stateChanged();
  }

  JewishDate.fromYMidD(int year, JewishMonth monthId, int day) {
    setByYearMonthIdDay(year, monthId, day);
  }
  void _calculateYearVariables() {
    this.m_yearGParts = partsSinceGenesis(m_year);
    this.m_yearFirstDay = days_until_year(m_year, this.m_yearGParts);
    this.m_yearLength =
        days_until_year(m_year + 1, partsSinceGenesis(m_year + 1)) -
            this.m_yearFirstDay;
  }

  bool setByYearMonthIdDay(int year, JewishMonth month_id, int day) {
    if (year >= 4119 && year < 6001) {
      if (this.m_year != year || !m_valid) {
        this.m_year = year;
        _calculateYearVariables();
      }
      this.m_month = monthFromIDByYearLength(this.m_yearLength, month_id);
      int month_length = monthLength();
      this.m_day = max(1, min(month_length, day));
      this.m_dayInYear =
          calculateDayInYear(this.m_yearLength, this.m_month, this.m_day);
      this.m_valid = true;
      int gdn = GDN();
      return stateChanged() && (gdn == GDN());
    }
    return false;
  }

  bool setByYMD(int year, int month, int day) {
    if (year >= 4119 && year < 7001) {
      if (this.m_year != year || !m_valid) {
        this.m_year = year;
        _calculateYearVariables();
      }
      int monthsInYear = (m_yearLength > 355) ? 13 : 12;
      this.m_month = max(1, min(month, monthsInYear));
      int month_length = monthLength();
      this.m_day = max(1, min(month_length, day));
      this.m_dayInYear =
          calculateDayInYear(this.m_yearLength, this.m_month, this.m_day);
      this.m_valid = true;
      return stateChanged();
    } else {
      return false;
    }
  }

  @override
  bool isValid() {
    return m_valid;
  }

  /*bool setByGDN(int gdn) {
        bool result=setByDays(gdn);
        stateChanged();
        return result && (gdn == GDN());
    }*/
  /*
     * don't use that, use setByGDN instead
     * @param gdn
     * @return
     * 
     */
  @override
  bool setByGDN(int gdn) {
    if (!checkBounds(gdn)) {
      return false;
    }
    int orig_parts = gdn * DAY;
    int parts = orig_parts - MOLAD;
    int months = parts ~/ MONTH;
    parts = (parts % MONTH);
    int years = 1; //first year was year one.
    years += 19 * (months ~/ MONTHS_IN_19Y);
    months = months % MONTHS_IN_19Y;
    int year_in_19 = ((months + 1) * 19 - 2) ~/ 235;
    years += year_in_19;
    months = months - (235 * (year_in_19) + 1) ~/ 19;
    parts += months * MONTH;
    this.m_yearGParts = orig_parts - parts;
    this.m_yearFirstDay = days_until_year(years, this.m_yearGParts);
    int next_year_day =
        days_until_year(years + 1, partsSinceGenesis(years + 1));
    int months_in_year;
    if (gdn >= this.m_yearFirstDay && gdn < next_year_day) {
      this.m_year = years;
      this.m_yearLength = next_year_day - this.m_yearFirstDay;
    } else {
      if (gdn < this.m_yearFirstDay) {
        this.m_year = years - 1;
        months_in_year = calculateYearMonths(this.m_year);
        this.m_yearGParts -= months_in_year * MONTH;
        next_year_day = this.m_yearFirstDay;
        this.m_yearFirstDay = days_until_year(this.m_year, this.m_yearGParts);
        this.m_yearLength = next_year_day - this.m_yearFirstDay;
      } else {
        assert(gdn >= next_year_day);
        this.m_year = years + 1;
        months_in_year = calculateYearMonths(years);
        this.m_yearGParts += months_in_year * MONTH;
        this.m_yearFirstDay = next_year_day;
        this.m_yearLength = days_until_year(
                this.m_year + 1, partsSinceGenesis(this.m_year + 1)) -
            this.m_yearFirstDay;
      }
    }
    this.m_dayInYear = gdn - this.m_yearFirstDay;
    setMonthDay(this.m_dayInYear);
    this.m_valid = true;
    return stateChanged();
  }

  static int days_until_year(int year, int parts) {
    int days = ((parts + MOLAD_ZAKEN_ROUNDING) ~/ DAY);
    int parts_mod = (parts % DAY);
    int year_type = ((year - 1) * 7 + 1) % 19;
    /* this magic gives us the following array:
         1, 8,15, 3,10,17, 5,12, 0, 7,14, 2, 9,16, 4,11,18, 6,13
         now if we compare it with the 235 months division:
         12,12,13,12,12,13,12,13,12,12,13,12,12,13,12,12,13,12,13
         we can see that all the leap years # >=12 and all the regular years # <12
         also, all the years that comes after a leap year have a number < 7
         */
    WeekDay week_day = WeekDay.values[days % 7];
    if (parts_mod < DAY - MOLAD_ZAKEN_ROUNDING) {
      if (year_type < 12) //regular year (non leap)
      {
        if (week_day == WeekDay.TUESDAY && parts_mod >= HP(9, 204)) {
          return days + 2; //we need to add 2 because Wednesday comes next (ADU)
        }
      }
      if (year_type < 7) //a year after a leap year
      {
        if (week_day == WeekDay.MONDAY && parts_mod >= HP(15, 589)) {
          return days + 1; //we need to add only 1..
        }
      }
    }
    if (week_day == WeekDay.SUNDAY ||
        week_day == WeekDay.WEDNESDAY ||
        week_day == WeekDay.FRIDAY) {
      ++days;
    }
    return days;
  }

  static int calculateYearMonths(int year) {
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

  static bool isLeapYear(int year) {
    return MONTHS_DIVISION[(year - 1) % 19] == 13;
  }

  static int partsSinceGenesis(int year) {
    /*
         the loop:
         ```
         for x in range(0,19):
           print (235*(x+1)+1)/19-(235*x+1)/19
         ```
         gives exactly:
         12,12,13,12,12,13,12,13,12,12,13,12,12,13,12,12,13,12,13
         which is the 19-years period month's division
         */
    int months = (235 * (year - 1) + 1) ~/
        19; //first year was year one. so we subtract one from year to start from 0.
    int parts = MOLAD + MONTH * months;
    return parts;
  }

  void setMonthDay(int days) //finds the month by day in year.
  {
    int yearLen_t = yearLengthType(this.m_yearLength);
    int m = days * 2 ~/ 59;
    if (_monthsDaysOffsets[yearLen_t][m] > days) {
      m--;
    } else if (_monthsDaysOffsets[yearLen_t][m + 1] <= days) {
      m++;
    }
    this.m_month = m + 1;
    this.m_day = days - _monthsDaysOffsets[yearLen_t][m] + 1;
  }

  static int calculateYearLength(int year) {
    return calculateYearFirstDay(year + 1) - calculateYearFirstDay(year);
  }

  static int calculateYearFirstDay(int year) {
    return days_until_year(year, partsSinceGenesis(year));
  }

  static int yearLengthType(int year_length) {
    //0 hasera,1 kesidra,2 melea,3 meuberet hasera,4 meuberet kesidra,5 meuberet melea
    return ((year_length % 10) - 3) + (year_length - 350) ~/ 10;
  }

  static const List<List<int>> _monthsDaysOffsets = [
    [0, 30, 59, 88, 117, 147, 176, 206, 235, 265, 294, 324, 353],
    [0, 30, 59, 89, 118, 148, 177, 207, 236, 266, 295, 325, 354],
    [0, 30, 60, 90, 119, 149, 178, 208, 237, 267, 296, 326, 355],
    [0, 30, 59, 88, 117, 147, 177, 206, 236, 265, 295, 324, 354, 383],
    [0, 30, 59, 89, 118, 148, 178, 207, 237, 266, 296, 325, 355, 384],
    [0, 30, 60, 90, 119, 149, 179, 208, 238, 267, 297, 326, 356, 385]
  ];
  static int calculateDayInYear(int year_length, int month, int day) //0..385
  {
    int yearLen_t = yearLengthType(year_length);
    return _monthsDaysOffsets[yearLen_t][month - 1] + day - 1;
  }

  static int days_to_year(int days) {
    int orig_parts = days * DAY;
    int parts = orig_parts - MOLAD;
    int months = parts ~/ MONTH;
    parts = (parts % MONTH);
    int years = 1; //first year was year one.
    years += 19 * (months ~/ MONTHS_IN_19Y);
    months = months % MONTHS_IN_19Y;
    int year_in_19 = ((months + 1) * 19 - 2) ~/ 235;
    years += year_in_19;
    months = months - (235 * (year_in_19) + 1) ~/ 19;
    parts += months * MONTH;
    int estimated_year_length = 353;
    if (calculateYearMonths(years) == 13) {
      estimated_year_length = 383;
    }
    int year_molad_parts = orig_parts - parts;
    int estimated_first_year_day = year_molad_parts ~/ DAY;
    if (estimated_first_year_day + 2 <= days &&
        days < estimated_first_year_day + estimated_year_length) {
      return years;
    }
    int year_first_day = days_until_year(years, year_molad_parts);
    if (days < year_first_day) {
      return years - 1;
    }
    int next_year_day =
        days_until_year(years + 1, partsSinceGenesis(years + 1));
    if (days >= next_year_day) {
      return years + 1;
    }
    return years;
  }

  @override
  int GDN() => m_yearFirstDay + m_dayInYear;

  @override
  int upperBound() {
    return DAYS_OF_6001;
  }

  @override
  int lowerBound() {
    return DAYS_OF_4119;
  }

  @override
  int yearFirstDayGDN() {
    return yearFirstDay();
  }

  int monthFirstDay() {
    //days since beginning
    int yearLen_t = yearLengthType(this.m_yearLength);
    return this.m_yearFirstDay +
        _monthsDaysOffsets[yearLen_t][this.m_month - 1];
  }

  int monthIdFirstDay(JewishMonth monthId) {
    //days since beginning
    int yearLen_t = yearLengthType(this.m_yearLength);
    return this.m_yearFirstDay +
        _monthsDaysOffsets[yearLen_t][monthFromMID(monthId) - 1];
  }

  @override
  int yearLength() {
    return this.m_yearLength;
  }

  int yearFirstDay() {
    return this.m_yearFirstDay;
  }

  @override
  int monthFirstDayGDN() {
    return monthFirstDay();
  }

  static int monthFromIDByYear(int year, JewishMonth monthId) {
    return monthFromMIDByYearMonths(calculateYearMonths(year), monthId);
  }

  static int monthFromIDByYearLength(int year_length, JewishMonth monthId) {
    return monthFromMIDByYearMonths((year_length > 355) ? 13 : 12, monthId);
  }

  /// finds the ordinal month number of the month
  int monthFromMID(JewishMonth monthId) {
    return monthFromMIDByYearMonths(
        (this.m_yearLength > 355) ? 13 : 12, monthId);
  }

  JewishMonth monthID() {
    return monthToMIDByYearMonths(calculateYearMonths(m_year), this.m_month);
  }

  static JewishMonth monthToMIDByYearMonths(int monthsInYear, int month) {
    if (monthsInYear == 13) //leap year
    {
      if (month > 5) //if Adar or after
      {
        ++month; //skip regular Adar
      }
    } else {
      if (month > 6) //if Nisan or after
      {
        month += 2; //skip Adar I+II
      }
    }
    return JewishMonth.values[month - 1];
  }

/* renamed from monthFromIDByYearMonths */
  /// Convert MonthId to Month Ordinal number.
  ///
  /// This function is tolerate to invalid monthIds.
  static int monthFromMIDByYearMonths(int monthsInYear, JewishMonth monthId) {
    if (monthId.index < JewishMonth.ADAR.index) {
      return monthId.index + 1;
    }
    if (monthsInYear == 13) //leap year
    {
      if (monthId.index >= JewishMonth.ADAR_I.index) {
        return monthId.index;
      }
      //return the index of adar II if monthId equal to ADAR
      return 7;
    } else {
      if (monthId.index >= JewishMonth.NISAN.index) {
        return monthId.index - 1;
      }
      // if monthId is equal to ADAR or ADAR_I or ADAR_II return the same index for all of them.
      return 6;
    }
  }
}
