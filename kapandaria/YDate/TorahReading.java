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

import java.util.LinkedList;
import java.util.List;

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
    * you can obtain the joining in israel by copying the joining outside IL and removing Chukat Balak joining,
    * except year type 4,11,12 where you should remove Behar Bechukotai in year type 4 and Matot Mas'ei in year types 11,12
    */
    final static byte[][] SIDRA_JOIN =
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
    static final int NUM_SIDRA_54 = 54;
    public enum Sidra
    {
        NONE,
        BERESHIT,
        NOACH,
        LECH_LECHA,
        VAYERA,
        CHAYEI_SARAH,
        TOLDOT,
        VAYETZE,
        VAYISHLACH,
        VAYESHEV,
        MIKETZ,
        VAYIGASH,
        VAYECHI,
        SHEMOT,
        VAERA,
        BO,
        BESHALACH,
        YITRO,
        MISHPATIM,
        TERUMAH,
        TETZAVEH,
        KI_TISA,
        VAYAKHEL,
        PEKUDEI,
        VAYIKRA,
        TZAV,
        SHEMINI,
        TAZRIA,
        METZORA,
        ACHREI_MOT,
        KEDOSHIM,
        EMOR,
        BEHAR,
        BECHUKOTAI,
        BAMIDBAR,
        NASO,
        BEHAALOTCHA,
        SHELACH_LECHA,
        KORACH,
        CHUKAT,
        BALAK,
        PINCHAS,
        MATOT,
        MASEI,
        DEVARIM,
        VAETCHANAN,
        EKEV,
        REEH,
        SHOFTIM,
        KI_TETZE,
        KI_TAVO,
        NITZAVIM,
        VAYELECH,
        HAAZINU,
        VEZOT_HABERACHA,     
        __1_55,
        __2_56,
        __3_57,
        __4_58,
        __5_59,
        __6_60,
        __7_61,
        __8_62,
        __9_63,
        __10_64,
        __11_65,
        __12_66,
        __13_67,
        __14_68,
        __15_69,
        __16_70,
        __17_71,
        __18_72,
        __19_73,
        __20_74,
        __21_75,
        VAYAKHEL__PEKUDEI,
        __23_77,
        __24_78,
        __25_79,
        __26_80,
        TAZRIA__METZORA,
        __28_82,
        ACHREI_MOT__KEDOSHIM,
        __30_84,
        __31_85,
        BEHAR__BECHUKOTAI,
        __33_87,
        __34_88,
        __35_89,
        __36_90,
        __37_91,
        __38_92,
        CHUKAT__BALAK,
        __40_94,
        __41_95,
        MATOT__MASEI,
        __43_97,
        __44_98,
        __45_99,
        __46_100,
        __47_101,
        __48_102,
        __49_103,
        __50_104,
        NITZAVIM__VAYELECH,
        __52_106,
        __53_107,
        __54_108,
    };
    final static String[] sidraToken
            = {
                "",
                "sidra_Bereshit",
                "sidra_Noach",
                "sidra_Lech_Lecha",
                "sidra_Vayera",
                "sidra_Chayei_Sarah",
                "sidra_Toldot",
                "sidra_Vayetze",
                "sidra_Vayishlach",
                "sidra_Vayeshev",
                "sidra_Miketz",
                "sidra_Vayigash",
                "sidra_Vayechi",
                "sidra_Shemot",
                "sidra_Vaera",
                "sidra_Bo",
                "sidra_Beshalach",
                "sidra_Yitro",
                "sidra_Mishpatim",
                "sidra_Terumah",
                "sidra_Tetzaveh",
                "sidra_Ki_Tisa",
                "sidra_Vayakhel",
                "sidra_Pekudei",
                "sidra_Vayikra",
                "sidra_Tzav",
                "sidra_Shemini",
                "sidra_Tazria",
                "sidra_Metzora",
                "sidra_Achrei_Mot",
                "sidra_Kedoshim",
                "sidra_Emor",
                "sidra_Behar",
                "sidra_Bechukotai",
                "sidra_Bamidbar",
                "sidra_Naso",
                "sidra_Behaalotcha",
                "sidra_Shelach_Lecha",
                "sidra_Korach",
                "sidra_Chukat",
                "sidra_Balak",
                "sidra_Pinchas",
                "sidra_Matot",
                "sidra_Masei",
                "sidra_Devarim",
                "sidra_Vaetchanan",
                "sidra_Ekev",
                "sidra_Reeh",
                "sidra_Shoftim",
                "sidra_Ki_Tetze",
                "sidra_Ki_Tavo",
                "sidra_Nitzavim",
                "sidra_Vayelech",
                "sidra_Haazinu",
                "sidra_Vezot_Haberacha"
            };

    private static final int SHABBAT_SHKALIM = 1;
    private static final int SHABBAT_ZAKHOR = 2;
    private static final int SHABBAT_PARA = 3;
    private static final int SHABBAT_HACHODESH = 4;
    private static final int SHABBAT_HAGADOL = 5;
    private static final int SHABBAT_SHIRA = 6;
    private static final int SHABBAT_NACHAMU = 7;
    private static final int SHABBAT_TSHUVA = 8;

    public static int FourParshiotEnum(JewishDate h)
    {
        JewishDate tweaked = new JewishDate(h);
        
        if (h.dayInWeek() == 7)
        {
            tweaked.seekBy(6);
            if (tweaked.monthID() == JewishDate.M_ID_NISAN) //maybe shabbat hachodesh or shabbat hagadol
            {
                if (tweaked.dayInMonth() <= 7)
                {
                    return SHABBAT_HACHODESH;
                }
            }
            if (tweaked.monthID() == JewishDate.M_ID_ADAR
                || tweaked.monthID() == JewishDate.M_ID_ADAR_II)//adar or adar II
            {
                if (tweaked.dayInMonth() <= 7)
                {
                    return SHABBAT_SHKALIM;
                }
                if (h.dayInMonth() < 14 && h.dayInMonth() > 7)
                {
                    return SHABBAT_ZAKHOR;
                }
                if (h.dayInMonth() > 16)
                {
                    return SHABBAT_PARA;
                }
            }
        }
        return 0;
    }
    public static int SpecialShabbatEnum(JewishDate h)
    {
        int four_shabbats = FourParshiotEnum(h);
        if (four_shabbats <= 0)
        {
            return four_shabbats;
        }
        final int FromBereshitToBeshalach = Sidra.BESHALACH.ordinal() - Sidra.BERESHIT.ordinal(); // 15
        if (getShabbatBereshit(h.yearLength(), h.yearFirstDay()) + FromBereshitToBeshalach * 7 == h.GDN())
        {
            return SHABBAT_SHIRA;
        }
        if (h.dayInWeek() == 7)
        {
            if (h.dayInMonth() < 15 && h.dayInMonth() > 7 && h.monthID() == JewishDate.M_ID_NISAN)
            {
                return SHABBAT_HAGADOL;
            }
            int shabbat_nachamu = h.yearFirstDay();
            shabbat_nachamu += JewishDate.calculateDayInYearByMonthId(h.yearLength(), JewishDate.M_ID_AV, 10);
            shabbat_nachamu = ADate.getNext(ADate.SATURDAY, shabbat_nachamu);

            if (h.GDN() == shabbat_nachamu)
            {
                return SHABBAT_NACHAMU;
            }
            int shabbat_tshuva = h.yearFirstDay();
            shabbat_tshuva += JewishDate.calculateDayInYearByMonthId(h.yearLength(), JewishDate.M_ID_TISHREI, 9);
            shabbat_tshuva = ADate.getPrevious(ADate.SATURDAY, shabbat_tshuva);
            if (h.GDN() == shabbat_tshuva)
            {
                return SHABBAT_TSHUVA;
            }
        }
        return 0;
    }
    static final String ShabbatTokens[] = {
        "",
        "shabbat_shkalim",
        "shabbat_zakhor",
        "shabbat_parah",
        "shabbat_hachodesh",
        "shabbat_hagadol",
        "shabbat_shira",
        "shabbat_nachamu",
        "shabbat_teshuva"
    };

    public static String SpecialShabbat(JewishDate h, YDateLanguage lang)
    {
        int shabbat = SpecialShabbatEnum(h);
        if (shabbat > 0)
        {
            return lang.getToken(ShabbatTokens[shabbat]);
        }
        return "";
    }
    static final int HOL_DAY = 0;
    static final int HOL_DAY_MONDAY_THURSDAY = 1;
    static final int SHABBAT_DAY = (1 << 1);
    static final int ROSH_CHODESH = (1 << 2);
    static final int REGALIM = (1 << 3);
    static final int REGALIM_DIASPORA = (1 << 4);
    static final int CHANUKKAH = (1 << 5);
    static final int PURIM = (1 << 6);
    static final int SHOSHAN_PURIM = (1 << 7);
    static final int TAANIT = (1 << 8);
    static final int EREV_ROSH_CHODESH = (1 << 9);
    static final int NINE_AV = (1 << 10);
    static final int KIPPUR = (1 << 11);
    static final int ROSH_HASHANA = (1 << 12);

    static int getDayType(JewishDate h)
    {
        
        boolean rosh_chodesh = h.roshChodesh();
        boolean erev_rosh_chodesh = (h.dayInMonth() == 29) || (h.dayInMonth() == 30);
        boolean chanukkah = (h.dayOfChanukkah() > 0);
        boolean purim = h.isPurimPerazim();
        boolean shoshan_purim = h.isShushanPurim();
        boolean sheni_hamishi = (h.dayInWeekEnum() == ADate.MONDAY || h.dayInWeekEnum() == ADate.THURSDAY);
        boolean shabbat = (h.dayInWeekEnum() == ADate.SATURDAY);
        boolean four_taaniot = (h.isTzomGedaliah() || h.isTzomTenthTevet()
                || h.isTaanitEsther() || h.isTzomSeventeenTammuz());
        boolean regalim = h.isRegel(false);
        boolean regalim_diasp = h.isRegel(true);
        boolean nine_av = h.isNineAv();
        boolean kippur = h.isKippurDay();
        boolean rosh_hashana = h.isRoshHaShana();

        int type = 0;
        type += rosh_chodesh ? ROSH_CHODESH : 0;
        type += four_taaniot ? TAANIT : 0;
        type += shabbat ? SHABBAT_DAY : 0;
        type += chanukkah ? CHANUKKAH : 0;
        type += nine_av ? NINE_AV : 0;
        type += kippur ? KIPPUR : 0;
        type += rosh_hashana ? ROSH_HASHANA : 0;
        type += regalim ? REGALIM : 0;
        type += (type == 0 && sheni_hamishi) ? HOL_DAY_MONDAY_THURSDAY : 0;
        
        type += erev_rosh_chodesh ? EREV_ROSH_CHODESH : 0;
        type += purim ? PURIM : 0;
        type += shoshan_purim ? SHOSHAN_PURIM : 0;
        type += regalim_diasp ? REGALIM_DIASPORA : 0;
        return type;
    }

    public static boolean GetMegilatEsther(JewishDate h, boolean MukafHoma)
    {
        int diy = h.dayInYear();
        int ydiw = h.yearFirstDay() % 7;
        int day_type = getDayType(h);
        int diw = (diy + ydiw) % 7;
        return  (  ((day_type & PURIM) != 0 && MukafHoma && diw == ADate.FRIDAY)//purim meshulash
            ||((day_type & PURIM) != 0 && (!MukafHoma))
            ||((day_type & SHOSHAN_PURIM) != 0 && MukafHoma && diw != ADate.SATURDAY));
    }

    public static String GetTorahReading(JewishDate h, boolean diaspora, boolean MukafHoma, YDateLanguage.Language language)
    {
        YDateLanguage le = YDateLanguage.getLanguageEngine(language);
        int diy = h.dayInYear();
        int ydiw = h.yearWeekDayEnum();
        //int diw = (diy + ydiw) % 7;
        
        int day_type = getDayType(h);
        int pnum = 0;
        if (h.isSimchatTorah(diaspora)) //if we are in simchat torah...
        {
            pnum = 54;//Vezot Haberacha
        }
        else
        {
            byte[] sidra_array = _calculateSidraArray(h.yearLength(), h.yearFirstDay(), diaspora);
            if ((day_type & SHABBAT_DAY) != 0)
            {
                pnum = sidra_array[diy / 7];
            }
            else if (((day_type & HOL_DAY_MONDAY_THURSDAY) != 0 && (!(diaspora && (day_type & REGALIM_DIASPORA) != 0)))) // if Hol Day of monday or thursday
            {
                int sat = ADate.getNext(ADate.SATURDAY, diy + ydiw) - ydiw;
                while (pnum == 0 && (sat/7) < sidra_array.length )
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
        String lstr = "";
        if (pnum > 0)
        {
            int sidra = pnum;
            if ((day_type & HOL_DAY_MONDAY_THURSDAY) !=0 && sidra > NUM_SIDRA_54)
            {
                sidra -= NUM_SIDRA_54;//on monday and thursday we only read the first of two connected.
            }
            lstr += SidraEnumToString(sidra, le);
        }
        if (((day_type & PURIM) != 0 && (!MukafHoma)) ||
            ((day_type & SHOSHAN_PURIM) != 0 && MukafHoma))
        {
            if ((day_type & SHABBAT_DAY) == 0) //not shabbat
            {
                lstr = "";
            }
            else
            {
                lstr += ", ";
            }
            lstr += "ויבא עמלק";
        }
        if ((day_type & TAANIT) != 0)
        {
            lstr = "ויחל משה";
        }
        if ((day_type) == ROSH_CHODESH)
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
            if ((day_type & ROSH_CHODESH) !=0)
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
    public static Sidra GetSidraEnum(JewishDate h, boolean diaspora)
    {
        int diy = h.dayInYear();
        int ydiw = h.yearFirstDay() % 7;
        int day_type = getDayType(h);
        int pnum = 0;
        if (h.isSimchatTorah(diaspora))
        {
            pnum = Sidra.VEZOT_HABERACHA.ordinal();//Vezot Haberacha
        }
        else
        {
            byte[] sidra_array = _calculateSidraArray(h.yearLength(), h.yearFirstDay(), diaspora);
            if ((day_type & SHABBAT_DAY) != 0) // we are in shabbat
            {
                pnum = sidra_array[diy / 7];
            }
            if (pnum == 0)
            {
                if (h.isSuccotShminiAtzeret(diaspora))
                {
                    pnum = Sidra.VEZOT_HABERACHA.ordinal();//Vezot Haberacha
                }
                else
                {
                    int sat = ADate.getNext(ADate.SATURDAY, diy + ydiw) - ydiw;// get the day in year of next saturday.
                    while (pnum == 0)
                    {
                        pnum = sidra_array[sat / 7];
                        //if the next saturday is in succot, it means we are already in Vezot Haberacha.
                        if (pnum == 0 && (sat / 7) == 2)//sat>=14 && sat <=20
                        {
                            pnum = Sidra.VEZOT_HABERACHA.ordinal();//Vezot Haberacha
                        }
                        sat += 7;
                    }
                }
            }
        }
        return Sidra.values()[pnum];
    }
/**
 * This method gives you the upcoming parasha. it is useful to know what parasha we should start studying.
 * @param h the hebrew date object
 * @param diaspora are we in the diaspora?
 * @return the string of the parasha.
 */
    public static String GetSidra(JewishDate h, boolean diaspora, YDateLanguage.Language language)
    {
        YDateLanguage le = YDateLanguage.getLanguageEngine(language);
        int pnum = GetSidraEnum(h, diaspora).ordinal();
        return SidraEnumToString(pnum, le);
    }
    public static String SidraEnumToString(int Sidra, YDateLanguage language)
    {
        
        String lstr = "";
        if (Sidra > NUM_SIDRA_54) {
            Sidra = Sidra - NUM_SIDRA_54;

            String fp = language.getToken("format_parasha2");
            fp = fp.replaceAll("_sd1_", language.getToken(sidraToken[Sidra]));
            fp = fp.replaceAll("_sd2_", language.getToken(sidraToken[Sidra + 1]));
            lstr += fp;
        }
        else
        {
            if (Sidra > 0)
            {
                String fp = language.getToken("format_parasha");
                fp = fp.replaceAll("_sd_", language.getToken(sidraToken[Sidra]));
                lstr += fp;
            }
        }
        return lstr;
    }
    static final byte[][][] sidra_reading = new byte[2][JewishDate.N_YEAR_TYPES][];//[diaspora][year_type][shabbat]
    //reverse access:
    static final byte[][][] sidra_to_shabbat = new byte[2][JewishDate.N_YEAR_TYPES][54];//[diaspora][year_type][sidra]

    private static int _getNextJoinPointer(byte joining, int jp)
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

    private static int _getJoin(int jp)
    {
        if (jp >= double_reading.length)
        {
            return 55;//return invalid parasha. valid number is only in the range 1..54
        }
        return double_reading[jp];
    }
/**
 * This method calculate all the parashot of a given year.
 * There are 14 possible year types, and for each one of the 14 we have different settings for diaspora.
 * The year can start in *FOUR* out of seven possible day in week (not in sunday, wednesday,friday).
 * The year length might be one of those *SIX* following: 353,354,355,383,384,385.
 * But, some of the combinations are not possible so we actually don't have 24(6*4) combination, but only 14 year types.
 * See ld_year_type for more information.
 * the calculations of this method are cached for each of the 14 year types.
 * @param year_length
 * @param year_first_day
 * @param diaspora
 * @return 
 */
    private static byte[] _calculateSidraArray(int year_length, int year_first_day, boolean diaspora)
    {
        int year_diw = year_first_day % 7; // can be only 1(+1=MON) 2(+1=TUE) 4(+1=THU) 6(+1=SAT) 
        
        
        int ldt = JewishDate.ld_year_type(year_length, year_diw + 1);//the year type out of 14 possible types ( the method gives us range of 1..14)
        if (sidra_reading[diaspora ? 0 : 1][ldt - 1] != null)
        {
            return sidra_reading[diaspora ? 0 : 1][ldt - 1];
        }
        byte joining = SIDRA_JOIN[diaspora ? 0 : 1][ldt - 1];

        int s = 0;

        int diy = ADate.getNext(ADate.SATURDAY, year_diw) - year_diw;
        int shabbats = (year_length - (diy) + 6) / 7;//number of shabbats in the given year.
        shabbats++; // one for the next year
        byte[] reading = new byte[shabbats];
        sidra_reading[diaspora ? 0 : 1][ldt - 1] = reading;
        //the following if is like if (year_diw  == ADate.MONDAY || year_diw  == ADate.TUESDAY)
        if (year_diw <= 2 ) //if the year started in monday or tuesday - pat bag
        {
            reading[s] = (byte)Sidra.VAYELECH.ordinal();//Vayelech
            ++s;
            reading[s] = (byte)Sidra.HAAZINU.ordinal();//Ha'azinu
            ++s;
            reading[s] = 0;//none
            ++s;
            diy += 21;//jump to after sukkuth.
        }
        else
        {
            if (year_diw == ADate.SATURDAY)
            {
                reading[s] = 0;//none
                ++s;
                diy += 7;
            }
            reading[s] = (byte)Sidra.HAAZINU.ordinal();//Ha'azinu
            ++s;
            reading[s] = 0;//none
            ++s;
            reading[s] = 0;//none
            ++s;
            diy += 21;//jump to after sukkuth.
        }
        int pesah_day = JewishDate.calculateDayInYearByMonthId(year_length, JewishDate.M_ID_NISAN, 15); // day in year of pessach night.
        int pesah_length = diaspora ? 8 : 7;//how much days in pessach?
        int azeret_day = 50 + pesah_day;//SHAVOUT day in year.
        int azeret_length = diaspora ? 2 : 1;
        int tr = 1;
        //now s points to shabat bereshit
        int jp = 0;
        jp = _getNextJoinPointer(joining, jp);
        int next_join = _getJoin(jp);
        while (s < shabbats)
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
                    reading[s] = (byte) (tr + NUM_SIDRA_54);
                    ++s;
                    diy += 7;
                    tr += 2;
                    jp = _getNextJoinPointer(joining, jp + 1);
                    next_join = _getJoin(jp);
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
    private static byte[] _generateSidraToShabbatArray(int year_length, int year_first_day, boolean diaspora)
    {
        int year_diw = year_first_day % 7; // can be only 1 2 4 6 (+1 = 2 3 5 7)
        int ldt = JewishDate.ld_year_type(year_length, year_diw + 1);
        byte[] reading = _calculateSidraArray(year_length, year_first_day, diaspora);
        byte[] rev_access = sidra_to_shabbat[diaspora ? 0 : 1][ldt - 1];
        if (rev_access[0]!=0)
            return rev_access;
        rev_access[Sidra.VEZOT_HABERACHA.ordinal()-1]=-1;//Vezot Habracha.
        int r=0;
        for (int i=0; i< reading.length;)
        {
            
            if( reading[i] > NUM_SIDRA_54 )//joined
            {
                rev_access[reading[i] - NUM_SIDRA_54 - 1]=(byte)i;
                rev_access[reading[i] - NUM_SIDRA_54 + 1 - 1]=(byte)i;
            }
            else if (reading[i] > 0 )
            {
                rev_access[reading[i] -1]=(byte)i;
            }
        }
        return rev_access;        
    }
    /**
     * get the GDN of Shabbat Bereshit of a certain year.
     * @param year_length
     * @param year_first_day
     * @return 
     */
    public static int getShabbatBereshit(int year_length, int year_first_day)
    {
        int bereshit_saturday = year_first_day;
        bereshit_saturday += JewishDate.calculateDayInYearByMonthId(year_length, JewishDate.M_ID_TISHREI, 23);
        bereshit_saturday = ADate.getNext(ADate.SATURDAY, bereshit_saturday);
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
            return ADate.getNext(ADate.SATURDAY, year_first_day);
        }
        return ADate.getPrevious(ADate.SATURDAY, year_first_day - 1);
    }
    /**
     * UNTESTED. should give you day in "beginning count" (GDN) for specific Sidra.
     * Vaelech might be twice in year or just once or zero. if there are two Shabbats Vayelech,
     * it gives the one in the end of the year.
     * there are two methods to get the desired Vayelech shabbat.
     * @param sidra 1..54
     * @param year_length
     * @param year_first_day
     * @param diaspora
     * @return -1 if not found. (might happen with "Vayelech")
     */
    public static int getParashaDayInYear(Sidra sidra,int year_length, int year_first_day, boolean diaspora)
    {
        if (sidra.ordinal() < Sidra.BERESHIT.ordinal() || sidra.ordinal() > Sidra.VEZOT_HABERACHA.ordinal())
            return -1;
        if (sidra == Sidra.VEZOT_HABERACHA)  // we read VeZot HaBeracha on Simchat Torah.
        {
            // Simchat torah is in Tishrey 22 in Israel or 23 in Galuyot.
            // but while day in month starts from 1, our "day in year" count starts from 0. so 23,22 become 22,21 respectively.
            return year_first_day + (diaspora ? 22 : 21); 
        }
        int sat_num = _generateSidraToShabbatArray(year_length,year_first_day,diaspora)[sidra.ordinal()-1];
        if (sat_num < 0)
            return -1;
        return ADate.getNext(ADate.SATURDAY, year_first_day) + sat_num * 7;
    }
    
    static class BibleIndex
    {
        /*
        
        
Bereishit - Genesis
Shemot - Exodus
Vayikra - Leviticus
Bamidbar - Numbers
Devarim - Deuteronomy
Nevi'im - Prophets
Yehoshua - Joshua
Shoftim - Judges
Shmuel I - I Samuel
Shmuel II - II Samuel
Melachim I - I Kings
Melachim II - II Kings
Yeshayahu - Isaiah
Yirmiyahu - Jeremiah
Yechezkel - Ezekiel
Hoshea - Hosea
Yoel - Joel
Amos
Ovadiah - Obadiah
Yonah - Jonah
Michah - Micah
Nachum - Nahum
Chavakuk - Habakkuk
Tzefaniah - Zephaniah
Chaggai - Haggai
Zechariah
Malachi
Ketuvim - Scriptures
Tehillim - Psalms
Mishlei - Proverbs
Iyov - Job
Shir Hashirim - Song of Songs
Rut - Ruth
Eichah - Lamentations
Kohelet - Ecclesiastes
Esther
Daniel
Ezra
Nechemiah - Nehemiah
Divrei Hayamim I - I Chronicles
Divrei Hayamim II - II Chronicles
        */
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
        
        public BibleParagraph(int start, int end)
        {
            m_start_index=start;
            m_end_index=end;
        }
    }
    class BibleText
    {
        String m_name;
        List<BibleParagraph> m_paragraphs;
        public BibleText(String name)
        {
            m_name=name;
            m_paragraphs =new LinkedList<>();
        }
        public BibleText(String name, BibleParagraph p)
        {
            m_name=name;
            m_paragraphs =new LinkedList<>();
            m_paragraphs.add(p);
        }
        public BibleText(BibleText obj)
        {
            m_name = obj.m_name;
            m_paragraphs = new LinkedList<>(obj.m_paragraphs);
        }
        public BibleText append(BibleParagraph p)
        {
            m_paragraphs.add(p);
            return this;
        }
        public BibleText prepend(BibleParagraph p)
        {
            m_paragraphs.add(0,p);
            return this;
        }
        
    }
    public enum HaftaraMinhag{ SFARADIM,ASHKENAZ,ITALKI,TEIMANI,CHABAD,FRANKFURT};
    BibleText ko_amar_yeshaayah_42_5__43_10 = new BibleText("כה אמר האל").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 42, 5 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 43, 10 )));
    BibleText ko_amar_yeshaayah_42_5__42_21 = new BibleText("כה אמר האל").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 42, 5 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 42, 21 )));
    BibleText hen_avdi_yeshaayah_42_1__42_21 = new BibleText("הן עבדי").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 42, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 42, 21 )));
    BibleText hen_avdi_yeshaayah_42_1__42_16 = new BibleText("הן עבדי").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 42, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 42, 16 )));
    
    BibleText roni_akara_yeshaayah_54_1__55_5 = new BibleText("רני עקרה").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 54, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 55, 5 )));
    BibleText roni_akara_yeshaayah_54_1__54_10 = new BibleText("רני עקרה").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 54, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 54, 10 )));
    BibleText roni_akara_yeshaayah_54_1__55_3 = new BibleText("רני עקרה").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 54, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 55, 3 )));
    BibleText lama_tomar_yaakov_yeshaayah_40_27__41_16 = new BibleText("למה תאמר יעקב").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 40, 27 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 41, 16 )));
    BibleText vel_mi_tedamyuni_yeshaayah_40_25__41_17 = new BibleText("ואל מי תדמיוני").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 40, 25 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 41, 17 )));
    BibleText veisha_akhat_melachim_ii_4_1__4_37 = new BibleText("ואשה אחת").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Melachim_II, 4, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Melachim_II, 4, 37 )));
    BibleText veisha_akhat_melachim_ii_4_1__4_23 = new BibleText("ואשה אחת").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Melachim_II, 4, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Melachim_II, 4, 23 )));
    
    BibleText vehamelech_david_melachim_i_1_1__1_31 = new BibleText("והמלך דוד").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Melachim_I, 1, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Melachim_I, 1, 31 )));
    BibleText vehamelech_david_melachim_i_1_1__1_34 = new BibleText("והמלך דוד").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Melachim_I, 1, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Melachim_I, 1, 34 )));
    
    BibleText masa_devar_malachi_1_1__2_7 = new BibleText("משא דבר ה'").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Malachi, 1, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Malachi, 2, 7 )));
    BibleText masa_devar_malachi_1_1__3_4 = new BibleText("משא דבר ה'").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Malachi, 1, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Malachi, 3, 4 )));
    
    BibleText veami_teluim_hushea_11_7__12_12 = new BibleText("ועמי תלואים").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Hushea, 11, 7 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Hushea, 12, 12 )));
    BibleText veami_teluim_hushea_11_7__12_14 = new BibleText("ועמי תלואים").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Hushea, 11, 7 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Hushea, 12, 14 )));
    BibleText vaivrach_yaakov_hushea_12_13__14_10 = new BibleText("ויברח יעקב").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Hushea, 12, 13 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Hushea, 14, 10 )));
    BibleText vaivrach_yaakov_hushea_12_13__14_10_yoel = new BibleText("ויברח יעקב")
            .append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Hushea, 12, 13 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Hushea, 14, 10 )))
            .append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yoel, 2, 26 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yoel, 2, 27 )));
    
    BibleText khazon_ovadiah_1_1__1_21 = new BibleText("חזון עבדיה").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Ovadiah, 1, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Ovadiah, 1, 21 )));
    
    BibleText ko_amar_amos_2_6__3_8 = new BibleText("כה אמר ה'").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Amos, 2, 6 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Amos, 3, 8 )));
    
    BibleText vaikatz_shlomo_melachim_i_3_15__4_1 = new BibleText("ויקץ שלמה").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Melachim_I, 3, 15 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Melachim_I, 4, 1 )));
    
    BibleText vayehi_devar_yechezkel_37_15__37_28 = new BibleText("ויהי דבר ה'").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yechezkel, 37, 15 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yechezkel, 37, 28 )));
    
    BibleText vaykrevo_yemei_melachim_i_2_1__2_12 = new BibleText("ויקרבו ימי דוד").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Melachim_I, 2, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Melachim_I, 2, 12 )));
    
    BibleText haftarot[][] = 
    {
        {//Bereshit
            ko_amar_yeshaayah_42_5__42_21,//sefardim
            ko_amar_yeshaayah_42_5__43_10,//ashkenaz
            hen_avdi_yeshaayah_42_1__42_21,//italian
            hen_avdi_yeshaayah_42_1__42_16,//teimani
            ko_amar_yeshaayah_42_5__42_21,//chabad
            ko_amar_yeshaayah_42_5__42_21,//frankfurt
        },
        {//Noah
            roni_akara_yeshaayah_54_1__54_10,//sefardim
            roni_akara_yeshaayah_54_1__55_5,//ashkenaz
            roni_akara_yeshaayah_54_1__55_5,//italian
            roni_akara_yeshaayah_54_1__55_3,//teimani
            roni_akara_yeshaayah_54_1__54_10,//chabad
            roni_akara_yeshaayah_54_1__55_5,//frankfurt
        },
        {//Lech Lecha
            lama_tomar_yaakov_yeshaayah_40_27__41_16,//sefardim
            lama_tomar_yaakov_yeshaayah_40_27__41_16,//ashkenaz
            vel_mi_tedamyuni_yeshaayah_40_25__41_17,//italian
            vel_mi_tedamyuni_yeshaayah_40_25__41_17,//teimani
            lama_tomar_yaakov_yeshaayah_40_27__41_16,//chabad
            lama_tomar_yaakov_yeshaayah_40_27__41_16,//frankfurt
        },
        {//Vayera
            veisha_akhat_melachim_ii_4_1__4_23,//sefardim
            veisha_akhat_melachim_ii_4_1__4_37,//ashkenaz
            veisha_akhat_melachim_ii_4_1__4_37,//italian
            veisha_akhat_melachim_ii_4_1__4_37,//teimani
            veisha_akhat_melachim_ii_4_1__4_37,//chabad
            veisha_akhat_melachim_ii_4_1__4_23,//frankfurt
        },
        {//Chayei_Sarah
            vehamelech_david_melachim_i_1_1__1_31,//sefardim
            vehamelech_david_melachim_i_1_1__1_31,//ashkenaz
            vehamelech_david_melachim_i_1_1__1_34,//italian
            vehamelech_david_melachim_i_1_1__1_31,//teimani
            vehamelech_david_melachim_i_1_1__1_31,//chabad
            vehamelech_david_melachim_i_1_1__1_31,//frankfurt
        },
        {//Toldot
            masa_devar_malachi_1_1__2_7,//sefardim
            masa_devar_malachi_1_1__2_7,//ashkenaz
            masa_devar_malachi_1_1__2_7,//italian
            masa_devar_malachi_1_1__3_4,//teimani
            masa_devar_malachi_1_1__2_7,//chabad
            masa_devar_malachi_1_1__2_7,//frankfurt
        },
        {//Vayetze
            veami_teluim_hushea_11_7__12_12,//sefardim
            vaivrach_yaakov_hushea_12_13__14_10_yoel,//ashkenaz
            veami_teluim_hushea_11_7__12_14,//italian
            veami_teluim_hushea_11_7__12_14,//teimani
            vaivrach_yaakov_hushea_12_13__14_10_yoel,//chabad
            vaivrach_yaakov_hushea_12_13__14_10//frankfurt
        },
        {//Vayishlach
            khazon_ovadiah_1_1__1_21,//sefardim
            khazon_ovadiah_1_1__1_21,//ashkenaz
            khazon_ovadiah_1_1__1_21,//italian
            khazon_ovadiah_1_1__1_21,//teimani
            khazon_ovadiah_1_1__1_21,//chabad
            khazon_ovadiah_1_1__1_21,//frankfurt
        },
        {//Vayeshev
            ko_amar_amos_2_6__3_8,//sefardim
            ko_amar_amos_2_6__3_8,//ashkenaz
            ko_amar_amos_2_6__3_8,//italian
            ko_amar_amos_2_6__3_8,//teimani
            ko_amar_amos_2_6__3_8,//chabad
            ko_amar_amos_2_6__3_8,//frankfurt
        },
        {//Miketz
            vaikatz_shlomo_melachim_i_3_15__4_1,//sefardim
            vaikatz_shlomo_melachim_i_3_15__4_1,//ashkenaz
            vaikatz_shlomo_melachim_i_3_15__4_1,//italian
            vaikatz_shlomo_melachim_i_3_15__4_1,//teimani
            vaikatz_shlomo_melachim_i_3_15__4_1,//chabad
            vaikatz_shlomo_melachim_i_3_15__4_1,//frankfurt
        },
        {//Vayigash
            vayehi_devar_yechezkel_37_15__37_28,//sefardim
            vayehi_devar_yechezkel_37_15__37_28,//ashkenaz
            vayehi_devar_yechezkel_37_15__37_28,//italian
            vayehi_devar_yechezkel_37_15__37_28,//teimani
            vayehi_devar_yechezkel_37_15__37_28,//chabad
            vayehi_devar_yechezkel_37_15__37_28,//frankfurt
        },
        {//Vayechi
            vaykrevo_yemei_melachim_i_2_1__2_12,//sefardim
            vaykrevo_yemei_melachim_i_2_1__2_12,//ashkenaz
            vaykrevo_yemei_melachim_i_2_1__2_12,//italian
            vaykrevo_yemei_melachim_i_2_1__2_12,//teimani
            vaykrevo_yemei_melachim_i_2_1__2_12,//chabad
            vaykrevo_yemei_melachim_i_2_1__2_12,//frankfurt
        },
        {//Shemot
            //sefardim
            //ashkenaz
            //italian
            //teimani
            //chabad
            //frankfurt
        },
        {//Vaera
            //sefardim
            //ashkenaz
            //italian
            //teimani
            //chabad
            //frankfurt
        },
        {//Bo
            //sefardim
            //ashkenaz
            //italian
            //teimani
            //chabad
            //frankfurt
        },
        {//Beshalach
            //sefardim
            //ashkenaz
            //italian
            //teimani
            //chabad
            //frankfurt
        },
    };

    
    
    BibleText gimel_depuranuta_sheva_denechamata[]={
        //gimel depuranuta
    new BibleText("דברי ירמיהו").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yirmiyah, 1, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yirmiyah, 2, 3 ))),
    new BibleText("שמעו דבר ה").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yirmiyah, 2, 4 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yirmiyah, 2, 28 ))),
    new BibleText("חזון ישעיהו").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 1, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 1, 27 ))),
    //sheva denechamata
    new BibleText("נחמו").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 40, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 40, 26 ))),
    new BibleText("ותאמר ציון").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 49, 14 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 51, 3 ))),
    new BibleText("עניה סוערה").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 54, 11 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 55, 5 ))),
    new BibleText("אנכי").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 51, 12 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 52, 12 ))),
    new BibleText("רני עקרה").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 54, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 54, 10 ))),
    new BibleText("קומי אורי").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 60, 1 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 60, 22 ))),
    new BibleText("שוש אשיש").append(new BibleParagraph(BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 61, 10 ),
            BibleIndex.BibleIndex(BibleIndex.BibleBook.Yeshaayah, 63, 9 ))),
};
    BibleText gimel_depuranuta_chabad[]=
    {
        gimel_depuranuta_sheva_denechamata[0],
        new BibleText(gimel_depuranuta_sheva_denechamata[1]),
        gimel_depuranuta_sheva_denechamata[2]
    };
    
    
    BibleText getHaftaraShabbat(JewishDate h, boolean diaspora, HaftaraMinhag minhag)
    {
        
        if (h.dayOfChanukkah() > 0 && h.dayInWeekEnum() == ADate.SATURDAY) //channukkah && shabbat
        {
            if (h.roshChodesh())// rosh chodesh also...
            {
                
            }
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
    BibleText getHaftaraShaharit(JewishDate h, boolean diaspora, HaftaraMinhag minhag)
    {
       
        throw new UnsupportedOperationException("Not supported yet.");
        
    }
    BibleText getHaftaraMincha(JewishDate h, HaftaraMinhag minhag)
    {
        throw new UnsupportedOperationException("Not supported yet.");
        
    }
    
}
