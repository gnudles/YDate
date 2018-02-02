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

import kapandaria.YDate.YDate.JewishDate;

public class TorahReading
{

    //based on shulhan aruch ORACH HAIM SIMAN 428 SEIF 4
    final static int[] double_reading =
    {
        22, 27, 29, 32, 39, 42, 51
    };
    /* outside israel!:
    *  joining   year type      1   2   3   4   5   6   7
    * 22 - Vayakhel Pekudei     V   V   V   V   V   X   V
    * 27 - Tazria Metzora       V   V   V   V   V   V   V
    * 29 - Achrei-Mot Kedoshim  V   V   V   V   V   V   V
    * 32 - Behar Bechukotai     V   V   V   V   V   V   V
    **39 - Chukat Balak         X   X   V   X   V   X   X
    * 42 - Matot Mas'ei         V   V   V   V   V   V   V
    * 51 - Nitzavim Vayelech    V   X   V   X   V   X   V
    * Compatible with Israel    +   +   -   -   -   +   -
    *  joining   year type      8   9   10  11  12  13  14
    * 22 - Vayakhel Pekudei     X   X   X   X   X   X   X
    * 27 - Tazria Metzora       X   X   X   X   X   X   X
    * 29 - Achrei-Mot Kedoshim  X   X   X   X   X   X   X
    * 32 - Behar Bechukotai     X   X   X   X   X   X   X
    **39 - Chukat Balak         V   X   X   X   X   X   V
    * 42 - Matot Mas'ei         V   X   V   V   V   X   V
    * 51 - Nitzavim Vayelech    V   X   V   X   X   V   V
    * Compatible with Israel    -   +   +   -   -   +   -
    * In Israel:
    *  joining   year type      3   4   5   7   8   11  12  14
    * 22 - Vayakhel Pekudei     V   V   V   V   X   X   X   X
    * 27 - Tazria Metzora       V   V   V   V   X   X   X   X
    * 29 - Achrei-Mot Kedoshim  V   V   V   V   X   X   X   X
    * 32 - Behar Bechukotai     V   X   V   V   X   X   X   X
    **39 - Chukat Balak         X   X   X   X   X   X   X   X
    * 42 - Matot Mas'ei         V   V   V   V   V   X   X   V
    * 51 - Nitzavim Vayelech    V   X   V   V   V   X   X   V
    * you can obtain the joining in israel by copying the joining outside IL and removing Chukat Balak joining.
    * except year type 4,11,12 where you should remove Behar Bechukotai in year type 4 and Matot Mas'ei in year types 11,12
    */
    final static byte[][] SidraJoin =
    {//lsb to msb : 22, 27, 29, 32, 39, 42, 51
        { //Diaspora
                0x6f //1, 1, 1, 1, 0, 1, 1
            ,//1
                0x2f //1, 1, 1, 1, 0, 1, 0
            ,//2
                0x7f //1, 1, 1, 1, 1, 1, 1
            ,//3
                0x2f //1, 1, 1, 1, 0, 1, 0
            ,//4
                0x7f //1, 1, 1, 1, 1, 1, 1
            ,//5
                0x2e //0, 1, 1, 1, 0, 1, 0
            ,//6
                0x6f //1, 1, 1, 1, 0, 1, 1
            ,//7
                0x70 //0, 0, 0, 0, 1, 1, 1
            ,//8
                0x00 //0, 0, 0, 0, 0, 0, 0
            ,//9
                0x60 //0, 0, 0, 0, 0, 1, 1
            ,//10
                0x20 //0, 0, 0, 0, 0, 1, 0
            ,//11
                0x20 //0, 0, 0, 0, 0, 1, 0
            ,//12
                0x40 //0, 0, 0, 0, 0, 0, 1
            ,//13
                0x70 //0, 0, 0, 0, 1, 1, 1
             //14
        },
        {
                0x6f //1, 1, 1, 1, 0, 1, 1
            ,//1
                0x2f //1, 1, 1, 1, 0, 1, 0
            ,//2
                0x6f //1, 1, 1, 1, 0, 1, 1
            ,//3
                0x27//1, 1, 1, 0, 0, 1, 0
            ,//4
                0x6f //1, 1, 1, 1, 0, 1, 1
            ,//5
                0x2e //0, 1, 1, 1, 0, 1, 0
            ,//6
                0x6f //1, 1, 1, 1, 0, 1, 1
            ,//7
                0x60 //0, 0, 0, 0, 0, 1, 1
            ,//8
                0x00 //0, 0, 0, 0, 0, 0, 0
            ,//9
                0x60 //0, 0, 0, 0, 0, 1, 1
            ,//10
                0x00 //0, 0, 0, 0, 0, 0, 0
            ,//11
                0x00 //0, 0, 0, 0, 0, 0, 0
            ,//12
                0x40 //0, 0, 0, 0, 0, 0, 1
            ,//13
                0x60 //0, 0, 0, 0, 0, 1, 1
             //14
        }
    };
    final static String[][] sidra =
    {
        {
            "",
            //1-8
            "בראשית", "נח", "לך-לך", "וירא", "חיי-שרה", "תולדות", "ויצא", "וישלח",
            //9-17
            "וישב", "מקץ", "ויגש", "ויחי", "שמות", "וארא", "בא", "בשלח", "יתרו",
            //18-25
            "משפטים", "תרומה", "תצוה", "כי-תשא", "ויקהל", "פקודי", "ויקרא", "צו",
            //26-33
            "שמיני", "תזריע", "מצורע", "אחרי-מות", "קדושים", "אמור", "בהר", "בחקותי",
            //34-41
            "במדבר", "נשא", "בהעלותך", "שלח-לך", "קרח", "חקת", "בלק", "פנחס",
            //42-49
            "מטות", "מסעי", "דברים", "ואתחנן", "עקב", "ראה", "שופטים", "כי-תצא",
            //50-54
            "כי-תבוא", "נצבים", "וילך", "האזינו", "וזאת הברכה"
        },
        {
            "",
            //1-8
            "Bereshit", "Noach", "Lech-Lecha", "Vayera", "Chayei-Sarah", "Toldot", "Vayetze", "Vayishlach",
            //9-17
            "Vayeshev", "Miketz", "Vayigash", "Vayechi", "Shemot", "Vaera", "Bo", "Beshalach", "Yitro",
            //18-25
            "Mishpatim", "Terumah", "Tetzaveh", "Ki-Tisa", "Vayakhel", "Pekudei", "Vayikra", "Tzav",
            //26-33
            "Shemini", "Tazria", "Metzora", "Achrei-Mot", "Kedoshim", "Emor", "Behar", "Bechukotai",
            //34-41
            "Bamidbar", "Naso", "Beha\'alotcha", "Shelach-Lecha", "Korach", "Chukat", "Balak", "Pinchas",
            //42-49
            "Matot", "Mas\'ei", "Devarim", "Vaetchanan", "Ekev", "Re\'eh", "Shoftim", "Ki-Tetze",
            //50-54
            "Ki-Tavo", "Nitzavim", "Vayelech", "Ha\'azinu", "Vezot Haberacha"
        }

    };

    /*final static String[] special_shabat =
    {
        "שקלים",
        "זכור",
        "פרה",
        "החודש",
        "הגדול",
        "שירה",
        "נחמו",
        "תשובה"
    };*/
    private static final int SHABAT_SHKALIM = 0;
    private static final int SHABAT_ZAKHOR = 1;
    private static final int SHABAT_PARA = 2;
    private static final int SHABAT_HACHODESH = 3;
    private static final int SHABAT_HAGADOL = 4;
    private static final int SHABAT_SHIRA = 5;
    private static final int SHABAT_NACHAMU = 6;
    private static final int SHABAT_TSHUVA = 7;

    public static String parshiot4(YDate h, YDateLanguage lang)
    {
        YDate tweaked = YDate.createFrom(h);
        if (getShabbatBereshit(h.hd.yearLength(), h.hd.yearFirstDay()) + 15 * 7 == h.hd.daysSinceBeginning())
        {
            return lang.getSpecialShabbat(SHABAT_SHIRA);
        }
        if (h.hd.dayInWeek() == 7)
        {
            tweaked.seekBy(6);
            if (tweaked.hd.monthID() == YDate.JewishDate.M_ID_NISAN) //maybe shabat hachodesh or shabat hagadol
            {
                if (tweaked.hd.dayInMonth() <= 7)
                {
                    return lang.getSpecialShabbat(SHABAT_HACHODESH);
                }
                if (h.hd.dayInMonth() < 15 && h.hd.dayInMonth() > 7)
                {
                    return lang.getSpecialShabbat(SHABAT_HAGADOL);
                }
            }
            if (tweaked.hd.monthID() == YDate.JewishDate.M_ID_ADAR
                || tweaked.hd.monthID() == YDate.JewishDate.M_ID_ADAR_II)//adar or adar II
            {
                if (tweaked.hd.dayInMonth() <= 7)
                {
                    return lang.getSpecialShabbat(SHABAT_SHKALIM);
                }
                if (h.hd.dayInMonth() < 14 && h.hd.dayInMonth() > 7)
                {
                    return lang.getSpecialShabbat(SHABAT_ZAKHOR);
                }
                if (h.hd.dayInMonth() > 16)
                {
                    return lang.getSpecialShabbat(SHABAT_PARA);
                }
            }
            int shabbat_nachamu = h.hd.yearFirstDay();
            shabbat_nachamu += YDate.JewishDate.calculateDayInYearByMonthId(h.hd.yearLength(), JewishDate.M_ID_AV, 10);
            shabbat_nachamu = YDate.getNext(YDate.SATURDAY, shabbat_nachamu);

            if (h.hd.daysSinceBeginning() == shabbat_nachamu)
            {
                return lang.getSpecialShabbat(SHABAT_NACHAMU);
            }
            int shabbat_tshuva = h.hd.yearFirstDay();
            shabbat_tshuva += YDate.JewishDate.calculateDayInYearByMonthId(h.hd.yearLength(), JewishDate.M_ID_TISHREI, 9);
            shabbat_tshuva = YDate.getPrevious(YDate.SATURDAY, shabbat_tshuva);
            if (h.hd.daysSinceBeginning() == shabbat_tshuva)
            {
                return lang.getSpecialShabbat(SHABAT_TSHUVA);
            }

        }
        return "";
    }
    static final int HOL_DAY = 0;
    static final int HOL_DAY_MONDAY_THURSDAY = 1;
    static final int SHABBAT_DAY = (1 << 1);
    static final int ROSH_HODESH = (1 << 2);
    static final int REGALIM = (1 << 3);
    static final int REGALIM_DIASPORA = (1 << 4);
    static final int CHANUKKAH = (1 << 5);
    static final int PURIM = (1 << 6);
    static final int SHOSHAN_PURIM = (1 << 7);
    static final int TAANIT = (1 << 8);
    static final int EREV_ROSH_HODESH = (1 << 9);
    static final int NINE_AV = (1 << 10);
    static final int KIPPUR = (1 << 11);
    static final int ROSH_HASHANA = (1 << 12);

    static int getDayType(JewishDate h)
    {
        byte[] events = YDateAnnual.getEvents(h.yearLength(), h.yearFirstDay(), false);
        int ev = events[h.dayInYear()];

        boolean rosh = (h.dayInMonth() == 1 || h.dayInMonth() == 30);
        boolean erev_rosh = (h.dayInMonth() == 29);
        boolean chanukkah = (ev >= 14 && ev <= 21);
        boolean purim = (ev == 25);
        boolean shoshan_purim = (ev == 26);
        boolean sheni_hamishi = (h.dayInWeek() == 2 || h.dayInWeek() == 5);
        boolean shabbat = (h.dayInWeek() == 7);
        boolean four_taaniot = (ev == 3 || ev == 22 || ev == 24 || ev == 40);
        boolean regalim = ((ev >= 29 && ev <= 32) || (ev == 37) || (ev == 13) || (ev >= 7 && ev <= 11));
        boolean regalim_diasp = (ev == 39 || ev == 12 || ev == 38 || ev == 33);
        boolean nine_av = (ev == 41);
        boolean kippur = (ev == 5);
        boolean rosh_hashana = (ev == 1 || ev == 2);

        int type = 0;
        type += rosh ? ROSH_HODESH : 0;
        type += four_taaniot ? TAANIT : 0;
        type += shabbat ? SHABBAT_DAY : 0;
        type += chanukkah ? CHANUKKAH : 0;
        type += nine_av ? NINE_AV : 0;
        type += kippur ? KIPPUR : 0;
        type += rosh_hashana ? ROSH_HASHANA : 0;
        type += regalim ? REGALIM : 0;
        type += (type == 0 && sheni_hamishi) ? HOL_DAY_MONDAY_THURSDAY : 0;
        type += erev_rosh ? EREV_ROSH_HODESH : 0;
        type += purim ? PURIM : 0;
        type += shoshan_purim ? SHOSHAN_PURIM : 0;
        type += regalim_diasp ? REGALIM_DIASPORA : 0;

        return type;
    }

    public static boolean GetMegilatEster(JewishDate h, boolean MukafHoma)
    {
        int diy = h.dayInYear();
        int ydiw = h.yearFirstDay() % 7;
        int day_type = getDayType(h);
        int diw = (diy + ydiw) % 7;
        return  (  ((day_type & PURIM) != 0 && MukafHoma && diw == YDate.FRIDAY)//purim meshulash
            ||((day_type & PURIM) != 0 && (!MukafHoma))
            ||((day_type & SHOSHAN_PURIM) != 0 && MukafHoma));
    }

    public static String GetTorahReading(JewishDate h, boolean diaspora, boolean MukafHoma)
    {
        int diy = h.dayInYear();
        int ydiw = h.yearFirstDay() % 7;
        //int diw = (diy + ydiw) % 7;
        int simhat_torah = diaspora ? 23 : 22;//22 in tishrei or 23 in tishrei.
        int succot = 15;//15 in tishrei.
        int day_type = getDayType(h);
        int pnum = 0;
        if ((diy + 1) == simhat_torah) //diy + 1 is day in month of tishrei. so if we are in simchat torah...
        {
            pnum = 54;//Vezot Haberacha
        }
        else
        {
            byte[] sidra_array = calculateSidraArray(h.yearLength(), h.yearFirstDay(), diaspora);
            if ((day_type & SHABBAT_DAY) != 0)
            {
                pnum = sidra_array[diy / 7];
            }
            else if (((day_type & HOL_DAY_MONDAY_THURSDAY) != 0 && (!(diaspora && (day_type & REGALIM_DIASPORA) != 0))))
            {
                if ((diy + 1) <= simhat_torah && (diy + 1) >= succot)
                {
                    pnum = 54;//Vezot Haberacha
                }
                else
                {
                    int sat = YDate.getNext(YDate.SATURDAY, diy + ydiw) - ydiw;
                    while (pnum == 0)
                    {
                        pnum = sidra_array[sat / 7];
                        if (pnum == 0 && (sat / 7) == 2)
                        {
                            pnum = 54;//Vezot Haberacha
                        }
                        sat += 7;
                    }
                }
            }
        }
        String lstr = "";
        if (pnum < 0)
        {
            pnum = -pnum;
            if ((day_type & SHABBAT_DAY) !=0)
            {
                lstr += "פרשת " + sidra[0][pnum] + ", " + sidra[0][pnum + 1];
            }
            else
            {//on monday and thursday we only read the first of two connected.
                lstr += "פרשת " + sidra[0][pnum];
            }
        }
        else
        {
            if (pnum != 0)
            {
                lstr += "פרשת " + sidra[0][pnum];
            }
        }
        if (((day_type & PURIM) != 0 && (!MukafHoma))||
            ((day_type & SHOSHAN_PURIM) != 0 && MukafHoma))
        {
            if ((day_type & SHABBAT_DAY) == 0) //not shabbat
            {
                lstr = "";
            }
            else
            {
                lstr = ", ";
            }
            lstr += "ויבא עמלק";
        }
        if ((day_type & TAANIT) != 0)
        {
            lstr = "ויחל משה";
        }
        if ((day_type) == ROSH_HODESH)
        {
            lstr = "קריאה לר\"ח";
        }
        if ((day_type) == NINE_AV)
        {
            lstr ="מגילת איכה";
        }
        if ((day_type & CHANUKKAH) !=0)
        {
            if ((day_type & SHABBAT_DAY) == 0) //not shabbat
            {
                lstr = "";
            }
            else
            {
                lstr += ", ";
            }
            if ((day_type & ROSH_HODESH) !=0)
            {
                lstr +="קריאה לר\"ח וחנוכה";
            }
            else
            {
                lstr +="קריאה לחנוכה";
            }
        }
        return lstr;
    }
/**
 * This method gives you the upcoming parasha. it is useful to know what parasha we should start studying.
 * @param h the hebrew date object
 * @param diaspora are we in the diaspora?
 * @return the string of the parasha.
 */
    public static String GetSidra(JewishDate h, boolean diaspora)
    {
        int diy = h.dayInYear();
        int ydiw = h.yearFirstDay() % 7;
        int simhat_torah = diaspora ? 23 : 22;
        int succot = 15;
        int day_type = getDayType(h);
        int pnum = 0;
        if ((diy + 1) == simhat_torah)
        {
            pnum = 54;//Vezot Haberacha
        }
        else
        {
            byte[] sidra_array = calculateSidraArray(h.yearLength(), h.yearFirstDay(), diaspora);
            if ((day_type & SHABBAT_DAY) != 0) // we are in shabbat
            {
                pnum = sidra_array[diy / 7];
            }
            if (pnum == 0)
            {
                if ((diy + 1) <= simhat_torah && (diy + 1) >= succot)
                {
                    pnum = 54;//Vezot Haberacha
                }
                else
                {
                    int sat = YDate.getNext(YDate.SATURDAY, diy + ydiw) - ydiw;// get the day in year of next saturday.
                    while (pnum == 0)
                    {
                        pnum = sidra_array[sat / 7];
                        //if the next saturday is in succot, it means we are already in Vezot Haberacha.
                        if (pnum == 0 && (sat / 7) == 2)//sat>=14 && sat <=20
                        {
                            pnum = 54;//Vezot Haberacha
                        }
                        sat += 7;
                    }
                }
            }
        }
        String lstr = "";
        if (pnum < 0)
        {
            pnum = -pnum;
            lstr += "פרשת " + sidra[0][pnum] + ", " + sidra[0][pnum + 1];
        }
        else
        {
            if (pnum != 0)
            {
                lstr += "פרשת " + sidra[0][pnum];
            }
        }
        return lstr;
    }
    static final byte[][][] sidra_reading = new byte[2][JewishDate.N_YEAR_TYPES][];//[diaspora][year_type][shabbat]
    //reverse access:
    static final byte[][][] sidra_to_shabbat = new byte[2][JewishDate.N_YEAR_TYPES][54];//[diaspora][year_type][sidra]

    private static int getNextJoinPointer(byte joining, int jp)
    {
        for (; jp < double_reading.length; ++jp)
        {
            if ((joining&(1<<jp)) != 0)
            {
                break;
            }
        }
        return jp;
    }

    private static int getJoin(int jp)
    {
        if (jp >= double_reading.length)
        {
            return 55;//return invalid parasha. valid number is only in the range 1..54
        }
        return double_reading[jp];
    }
/**
 * this method calculate all the parashot of a given year.
 * there are 14 possible year types, and for each one of the 14 we have different settings for diaspora.
 * the year can start in 4 out of 7 possible day in week (not in sunday, wednesday,friday). the year length might be one of the following: 353,354,355,383,384,385.
 * but some of the combinations are not possible so we don't have 24(6*4) combination, we only have 14.
 * the calculations of this method are cached for rapid access.
 * @param year_length
 * @param year_first_day
 * @param diaspora
 * @return 
 */
    private static byte[] calculateSidraArray(int year_length, int year_first_day, boolean diaspora)
    {
        int year_diw = year_first_day % 7; // can be only 1(+1=MON) 2(+1=TUE) 4(+1=THU) 6(+1=SAT) 
        
        
        int ldt = YDate.JewishDate.ld_year_type(year_length, year_diw + 1);//the year type out of 14 possible types ( the method gives us range of 1..14)
        if (sidra_reading[diaspora ? 0 : 1][ldt - 1] != null)
        {
            return sidra_reading[diaspora ? 0 : 1][ldt - 1];
        }
        byte joining = SidraJoin[diaspora ? 0 : 1][ldt - 1];

        int s = 0;

        int diy = YDate.getNext(YDate.SATURDAY, year_diw) - year_diw;
        int shabats = (year_length - (diy) + 6) / 7;//number of shabbats in the given year.
        shabats++; // one for the next year
        byte[] reading = new byte[shabats];
        sidra_reading[diaspora ? 0 : 1][ldt - 1] = reading;
        //the following if is like if (year_diw  == YDate.MONDAY || year_diw  == YDate.TUESDAY)
        // or like doing if (year_diw > 2)
        if ((year_diw >> 2) == 0) //if the year started in monday or tuesday - pat bag
        {
            reading[s] = 52;//Vayelech
            ++s;
            reading[s] = 53;//Ha'azinu
            ++s;
            reading[s] = 0;//none
            ++s;
            diy += 21;
        }
        else
        {
            if (year_diw == YDate.SATURDAY)
            {
                reading[s] = 0;//none
                ++s;
                diy += 7;
            }
            reading[s] = 53;//Ha'azinu
            ++s;
            reading[s] = 0;//none
            ++s;
            reading[s] = 0;//none
            ++s;
            diy += 21;
        }
        int pesah_day = YDate.JewishDate.calculateDayInYearByMonthId(year_length, JewishDate.M_ID_NISAN, 15); // day in year of pessach night.
        int pesah_length = diaspora ? 8 : 7;//how much days in pessach?
        int azeret_day = 50 + pesah_day;//SHAVOUT day in year.
        int azeret_length = diaspora ? 2 : 1;
        int tr = 1;
        //now s points to shabat bereshit
        int jp = 0;
        jp = getNextJoinPointer(joining, jp);
        int next_join = getJoin(jp);
        while (s < shabats)
        {
            if ((diy >= pesah_day && diy < pesah_day + pesah_length)
                || (diy >= azeret_day && diy < azeret_day + azeret_length))
            {
                reading[s] = 0;//none
                ++s;
                diy += 7;
            }
            else
            {
                if (tr == next_join)
                {
                    reading[s] = (byte) (-tr);
                    ++s;
                    diy += 7;
                    tr += 2;
                    jp = getNextJoinPointer(joining, jp + 1);
                    next_join = getJoin(jp);
                }
                else
                {
                    reading[s] = (byte) tr;
                    ++tr;
                    ++s;
                    diy += 7;
                }
            }
        }
        return reading;
    }
    private static byte[] generateSidraToShabbatArray(int year_length, int year_first_day, boolean diaspora)
    {
        int year_diw = year_first_day % 7; // can be only 1 2 4 6 (+1 = 2 3 5 7)
        int ldt = YDate.JewishDate.ld_year_type(year_length, year_diw + 1);
        byte[] reading = calculateSidraArray(year_length, year_first_day, diaspora);
        byte[] rev_access = sidra_to_shabbat[diaspora ? 0 : 1][ldt - 1];
        if (rev_access[0]!=0)
            return rev_access;
        rev_access[54-1]=-1;//Vezot Habracha.
        int r=0;
        for (int i=0; i< reading.length;)
        {
            if (reading[i]>0)
            {
                rev_access[reading[i]-1]=(byte)i;
            }
            else if(reading[i]<0)//joined
            {
                rev_access[-reading[i]-1]=(byte)i;
                rev_access[-reading[i]]=(byte)i;
            }
        }
        return rev_access;        
    }
    
    public static int getShabbatBereshit(int year_length, int year_first_day)
    {
        int bereshit_saturday = year_first_day;
        bereshit_saturday += YDate.JewishDate.calculateDayInYearByMonthId(year_length, JewishDate.M_ID_TISHREI, 23);
        bereshit_saturday = YDate.getNext(YDate.SATURDAY, bereshit_saturday);
        return bereshit_saturday;
    }
    /**
     * return the Vayelech shabbat that is at the end of this year or at the beginning of the next year.
     * @param year_length
     * @param year_first_day
     * @return 
     */
    public static int getLastShabbatVayelech(int year_length, int year_first_day)
    {
        return getFirstShabbatVayelech(year_first_day + year_length);
    }
    public static int getFirstShabbatVayelech(int year_first_day)
    {
        int year_diw= (year_first_day)%7;
        if ((year_diw >> 2) == 0) //if the year starts in monday or tuesday - pat bag
        {
            return YDate.getNext(YDate.SATURDAY, year_first_day);
        }
        return YDate.getPrevious(YDate.SATURDAY, year_first_day - 1);
    }
    /**
     * UNTESTED. should give you day in "beginning count" for specific sidra.
     * Vaelech might be twice in year or just once or zero. if there are two shabbats Vayelech,
     * it gives the one in the end of the year.
     * there are two methods to get the desired Vayelech shabbat.
     * @param sidra 1..54
     * @param year_length
     * @param year_first_day
     * @param diaspora
     * @return -1 if not found. (might happen with "Vayelech")
     */
    public static int getParashaDayInYear(int sidra,int year_length, int year_first_day, boolean diaspora)
    {
        if (sidra < 1 || sidra > 54)
            return -1;
        if (sidra==54)
        {
            // Simchat torah is in Tishrey 22 in Israel or 23 in Galuyot.
            // but while day in month starts from 1, our day in year count starts from 0.
            return year_first_day + (diaspora ? 22 : 21); 
        }
        int sat_num = generateSidraToShabbatArray(year_length,year_first_day,diaspora)[sidra-1];
        if (sat_num<0)
            return -1;
        return YDate.getNext(YDate.SATURDAY, year_first_day) + sat_num * 7;
    }
    
    static class BibleIndex
    {
        enum BibleBook
        {
           Bereshit,
           Shemot,
           Vayikra,
           Bamidbar,
           Devarim,
           Yehosuah,
           Shoftim,
           Shmuel_I,
           Shmuel_II,
           Melachim_I,
           Melachim_II,
           Yeshaayah,
           Yirmiyah,
           Yechezkel,
           Hushea,
           Yoel,
           Amos,
           Ovadiah,
           Yonah,
           Michah,
           Nachum,
           Chavakuk,
           Tzefaniah,
           Chagay,
           Zechariah,
           Malachi,
           Tehilim,
           Mishley,
           Iyob,
           ShirHashirim,
           Ruth,
           Eichah,
           Kohelet,
           Esther,
           Daniel,
           Ezra,
           Nechemiah,
           DivreyHayamim_I,
           DivreyHayamim_II,
        }
        
        public static int BibleIndex(BibleBook book,int chapter, int verse)
        {
            return book.ordinal() | (chapter & 0x3ff) << 6 | (verse) << 16;  
        }
        public static BibleBook getBook(int bible_index)
        {
            return BibleBook.values()[bible_index&0x3f];
        }
        public static int getChapter(int bible_index)
        {
            return (bible_index>>6)&0x3ff;
        }
        public static int getVerse(int bible_index)
        {
            return (bible_index>>16);
        }
        //TODO: add formating of verses.
    }
    class BibleParagraph
    {
        int m_start_index;
        int m_end_index;
        String m_name;
        public BibleParagraph(String name, int start, int end)
        {
            m_name=name;
            m_start_index=start;
            m_end_index=end;
        }
    }
    
}
