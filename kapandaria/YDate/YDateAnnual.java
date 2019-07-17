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

import java.util.Arrays;
public class YDateAnnual
{
    public enum JewishEvents
    {
    NO_EVENT,
    ROSH_HASHANA_A,
    ROSH_HASHANA_B, 
    TZOM_GEDALIA, 
    EREV_YOM_KIPPUR, 
    YOM_KIPPUR,    
    SIMCHAT_COHEN, 
    EREV_SUKKOT, 
    SUKKOT, 
    /**
     * Second day of galuyot of Sukkot.
     */
    SDOG_SUKKOT, 
    SUKKOT_HOL_HAMOED, 
    HOSHANA_RABBAH, 
    SHEMINI_ATZERET, 
    SIMCHAT_TORAH, 
    SHEMINI_ATZERET_SIMCHAT_TORAH, 
    HANUKKAH_ONE_CANDLE, 
    HANUKKAH_TWO_CANDLES, 
    HANUKKAH_THREE_CANDLES, 
    HANUKKAH_FOUR_CANDLES, 
    HANUKKAH_FIVE_CANDLES, 
    HANUKKAH_SIX_CANDLES, 
    HANUKKAH_SEVEN_CANDLES, 
    HANUKKAH_EIGHT_CANDLES, 
    TENTH_OF_TEVET, 
    FIFTEEN_SHVAT, 
    TAANIT_ESTHER, 
    PURIM, 
    SHUSHAN_PURIM, 
    PURIM_MESHULASH, 
    PURIM_KATAN, 
    EREV_PESACH, 
    PESACH, //Passover
    /**
     * Sheni Shel Pesach (second yom-tov day of galuyot - diaspora)
     * this is NOT Pesach Sheni!
     */
    SDOG_PESACH, 
    PESACH_HOL_HAMOED, 
    SHVII_PESACH, 
    SHVII_SDOG_PESACH, 
    PESACH_SHENI, 
    RASHBI_THIRTY_THREE, 
    EREV_SHAVUOT, 
    SHAVUOT, 
    SDOG_SHAVUOT, 
    ISRU_HAG, 
    TZOM_SEVENTEEN_TAMMUZ, 
    TZOM_NINE_AV, 
    FIFTEEN_AV, 
    EREV_ROSH_HASHANA, 
    TAANIT_GZEROT_408_409, 
    /**
     *     holocaust day. decided in 27 Nissan 1951 (5711). if on friday, move it backward. if on sunday after 1997 move it afterward.
     */
    HOLOCAUST_DAY, 
    /**
     * memorial day. in 4 Iyar 1951 (5711).
     */
    MEMORIAL_DAY_FALLEN, 
    INDEPENDENTS_DAY, 
    JERUSALEMS_DAY, 
    /**
     * family day. in 30 Shevat since 1973 (5733).
     */
    FAMILY_DAY, 
    ISAAC_RABIN_DAY
    };
    
    public static final short EV_NONE=0;
    public static final short EV_YOM_TOV=1;
    public static final short EV_HOL_HAMOED=2;
    public static final short EV_ISRU_HAG=3;
    public static final short EV_EREV_YOM_TOV=4;
    public static final short EV_MIRACLE=5;
    public static final short EV_CHASIDIC=6;
    public static final short EV_GOOD_DAYS=7;
    public static final short EV_TYPE_MASK=7;
    public static final short EV_NATIONAL=8;
    public static final short EV_TZOM=16;
    public static final short EV_REGALIM=32;
    public static final short EV_MEMORIAL=64;
    public static final short EV_HORBAN=128;
    
    static String[] events_str_id;
    static short[] events_type;
    static {
        events_str_id = new String[JewishEvents.values().length];
        for (int i=1; i<JewishEvents.values().length ; ++i) //start from 1 because zero is null string
        {
            events_str_id[i]=JewishEvents.values()[i].name();
        };
        
        events_type = new short[JewishEvents.values().length];
        
        events_type[JewishEvents.NO_EVENT.ordinal()]=EV_NONE;
        events_type[JewishEvents.ROSH_HASHANA_A.ordinal()]=EV_YOM_TOV;
        events_type[JewishEvents.ROSH_HASHANA_B.ordinal()]=EV_YOM_TOV;//
        events_type[JewishEvents.TZOM_GEDALIA.ordinal()]=EV_TZOM|EV_HORBAN;
        events_type[JewishEvents.EREV_YOM_KIPPUR.ordinal()]=EV_EREV_YOM_TOV;
        events_type[JewishEvents.YOM_KIPPUR.ordinal()]=EV_TZOM|EV_YOM_TOV;
        events_type[JewishEvents.SIMCHAT_COHEN.ordinal()]=EV_GOOD_DAYS;
        events_type[JewishEvents.EREV_SUKKOT.ordinal()]=EV_EREV_YOM_TOV;
        events_type[JewishEvents.SUKKOT.ordinal()]=EV_YOM_TOV|EV_REGALIM;
        events_type[JewishEvents.SDOG_SUKKOT.ordinal()]=EV_YOM_TOV|EV_REGALIM;
        events_type[JewishEvents.SUKKOT_HOL_HAMOED.ordinal()]=EV_HOL_HAMOED|EV_REGALIM;
        events_type[JewishEvents.HOSHANA_RABBAH.ordinal()]=EV_HOL_HAMOED|EV_REGALIM;
        events_type[JewishEvents.SHEMINI_ATZERET.ordinal()]=EV_YOM_TOV|EV_REGALIM;
        events_type[JewishEvents.SIMCHAT_TORAH.ordinal()]=EV_YOM_TOV|EV_REGALIM;
        events_type[JewishEvents.SHEMINI_ATZERET_SIMCHAT_TORAH.ordinal()]=EV_YOM_TOV|EV_REGALIM;
        events_type[JewishEvents.HANUKKAH_ONE_CANDLE.ordinal()]=EV_MIRACLE;
        events_type[JewishEvents.HANUKKAH_TWO_CANDLES.ordinal()]=EV_MIRACLE;
        events_type[JewishEvents.HANUKKAH_THREE_CANDLES.ordinal()]=EV_MIRACLE;
        events_type[JewishEvents.HANUKKAH_FOUR_CANDLES.ordinal()]=EV_MIRACLE;
        events_type[JewishEvents.HANUKKAH_FIVE_CANDLES.ordinal()]=EV_MIRACLE;
        events_type[JewishEvents.HANUKKAH_SIX_CANDLES.ordinal()]=EV_MIRACLE;
        events_type[JewishEvents.HANUKKAH_SEVEN_CANDLES.ordinal()]=EV_MIRACLE;
        events_type[JewishEvents.HANUKKAH_EIGHT_CANDLES.ordinal()]=EV_MIRACLE;
        events_type[JewishEvents.TENTH_OF_TEVET.ordinal()]=EV_TZOM|EV_HORBAN;
        events_type[JewishEvents.FIFTEEN_SHVAT.ordinal()]=EV_GOOD_DAYS;
        events_type[JewishEvents.TAANIT_ESTHER.ordinal()]=EV_TZOM|EV_MIRACLE;
        events_type[JewishEvents.PURIM.ordinal()]=EV_MIRACLE;
        events_type[JewishEvents.SHUSHAN_PURIM.ordinal()]=EV_MIRACLE;
        events_type[JewishEvents.PURIM_MESHULASH.ordinal()]=EV_MIRACLE;
        events_type[JewishEvents.PURIM_KATAN.ordinal()]=EV_MIRACLE;
        events_type[JewishEvents.EREV_PESACH.ordinal()]=EV_EREV_YOM_TOV;
        events_type[JewishEvents.PESACH.ordinal()]=EV_YOM_TOV|EV_REGALIM;
        events_type[JewishEvents.SDOG_PESACH.ordinal()]=EV_YOM_TOV|EV_REGALIM;
        events_type[JewishEvents.PESACH_HOL_HAMOED.ordinal()]=EV_HOL_HAMOED|EV_REGALIM;
        events_type[JewishEvents.SHVII_PESACH.ordinal()]=EV_YOM_TOV|EV_REGALIM;
        events_type[JewishEvents.SHVII_SDOG_PESACH.ordinal()]=EV_YOM_TOV|EV_REGALIM;
        events_type[JewishEvents.PESACH_SHENI.ordinal()]=EV_REGALIM;
        events_type[JewishEvents.RASHBI_THIRTY_THREE.ordinal()]=EV_GOOD_DAYS;
        events_type[JewishEvents.EREV_SHAVUOT.ordinal()]=EV_EREV_YOM_TOV;
        events_type[JewishEvents.SHAVUOT.ordinal()]=EV_YOM_TOV|EV_REGALIM;
        events_type[JewishEvents.SDOG_SHAVUOT.ordinal()]=EV_YOM_TOV|EV_REGALIM;
        events_type[JewishEvents.ISRU_HAG.ordinal()]=EV_ISRU_HAG;
        events_type[JewishEvents.TZOM_SEVENTEEN_TAMMUZ.ordinal()]=EV_TZOM|EV_HORBAN;
        events_type[JewishEvents.TZOM_NINE_AV.ordinal()]=EV_TZOM|EV_HORBAN;
        events_type[JewishEvents.FIFTEEN_AV.ordinal()]=EV_GOOD_DAYS;
        events_type[JewishEvents.EREV_ROSH_HASHANA.ordinal()]=EV_EREV_YOM_TOV;
        events_type[JewishEvents.TAANIT_GZEROT_408_409.ordinal()]=EV_TZOM|EV_MEMORIAL;
        events_type[JewishEvents.HOLOCAUST_DAY.ordinal()]=EV_NATIONAL|EV_MEMORIAL;
        events_type[JewishEvents.MEMORIAL_DAY_FALLEN.ordinal()]=EV_NATIONAL|EV_MEMORIAL;
        events_type[JewishEvents.INDEPENDENTS_DAY.ordinal()]=EV_NATIONAL;
        events_type[JewishEvents.JERUSALEMS_DAY.ordinal()]=EV_NATIONAL;
        events_type[JewishEvents.FAMILY_DAY.ordinal()]=EV_NATIONAL;
        events_type[JewishEvents.ISAAC_RABIN_DAY.ordinal()]=EV_NATIONAL|EV_MEMORIAL;

    }


    static final byte [][] event_db= 
    {// month_id,day,array index,# of days,jump/dhia(if #_days==1). if dhia>=7 then dhia%7 is the only day of week possible
        {JewishDate.M_ID_TISHREI,1,(byte)JewishEvents.ROSH_HASHANA_A.ordinal(),2,1},//two days of rosh hashana
        {JewishDate.M_ID_TISHREI,3,(byte)JewishEvents.TZOM_GEDALIA.ordinal(),1,1},//zom gdalia, dhia
        {JewishDate.M_ID_TISHREI,9,(byte)JewishEvents.EREV_YOM_KIPPUR.ordinal(),2,1},//yom kippur
        {JewishDate.M_ID_TISHREI,11,(byte)JewishEvents.SIMCHAT_COHEN.ordinal(),1,0},//yom Simhat Cohen
        {JewishDate.M_ID_TISHREI,14,(byte)JewishEvents.EREV_SUKKOT.ordinal(),2,1},//Erev Sukkot+Sukkot
        {JewishDate.M_ID_TISHREI,16,(byte)JewishEvents.SUKKOT_HOL_HAMOED.ordinal(),5,0},//hol hamoed sukkot
        {JewishDate.M_ID_TISHREI,21,(byte)JewishEvents.HOSHANA_RABBAH.ordinal(),1,0},//hoshana raba
        {JewishDate.M_ID_TISHREI,22,(byte)JewishEvents.SHEMINI_ATZERET_SIMCHAT_TORAH.ordinal(),1,0},//shmini azeret simhat_tora
        {JewishDate.M_ID_TISHREI,23,(byte)JewishEvents.ISRU_HAG.ordinal(),1,0},//isru hag
        {JewishDate.M_ID_KISLEV,25,(byte)JewishEvents.HANUKKAH_ONE_CANDLE.ordinal(),8,1},//Chanukah
        {JewishDate.M_ID_TEVET,10,(byte)JewishEvents.TENTH_OF_TEVET.ordinal(),1,0},//Tzom Asara B'Tevet
        {JewishDate.M_ID_SHEVAT,15,(byte)JewishEvents.FIFTEEN_SHVAT.ordinal(),1,0},//Tu B'Shvat
        {JewishDate.M_ID_ADAR,13,(byte)JewishEvents.TAANIT_ESTHER.ordinal(),1,-2},//taanit ester, dhia
        {JewishDate.M_ID_ADAR,14,(byte)JewishEvents.PURIM.ordinal(),2,1},//Purim+Shushan
        {JewishDate.M_ID_ADAR,16,(byte)JewishEvents.PURIM_MESHULASH.ordinal(),1,7},//Purim Meshulash only on sunday
        {JewishDate.M_ID_ADAR_I,14,(byte)JewishEvents.PURIM_KATAN.ordinal(),2,0},//Purim katan - two days
        {JewishDate.M_ID_ADAR_II,13,(byte)JewishEvents.TAANIT_ESTHER.ordinal(),1,-2},//taanit ester, dhia
        {JewishDate.M_ID_ADAR_II,14,(byte)JewishEvents.PURIM.ordinal(),2,1},//Purim+Shushan
        {JewishDate.M_ID_ADAR_II,16,(byte)JewishEvents.PURIM_MESHULASH.ordinal(),1,7},//Purim Meshulash only on sunday
        {JewishDate.M_ID_NISAN,14,(byte)JewishEvents.EREV_PESACH.ordinal(),2,1},//Erev Pesah+Pesah
        {JewishDate.M_ID_NISAN,16,(byte)JewishEvents.PESACH_HOL_HAMOED.ordinal(),5,0},//Hol Ha'moed Pesah
        {JewishDate.M_ID_NISAN,21,(byte)JewishEvents.SHVII_PESACH.ordinal(),1,0},//Shvi'i Pesah
        {JewishDate.M_ID_NISAN,22,(byte)JewishEvents.ISRU_HAG.ordinal(),1,0},//isru hag
        {JewishDate.M_ID_IYAR,14,(byte)JewishEvents.PESACH_SHENI.ordinal(),1,0},//Pesah Sheni
        {JewishDate.M_ID_IYAR,18,(byte)JewishEvents.RASHBI_THIRTY_THREE.ordinal(),1,0},//Lag Ba'Omer
        {JewishDate.M_ID_SIVAN,5,(byte)JewishEvents.EREV_SHAVUOT.ordinal(),2,1},//Erev Shavu'ot+Shavu'ot
        {JewishDate.M_ID_SIVAN,7,(byte)JewishEvents.ISRU_HAG.ordinal(),1,0},//isru hag
        {JewishDate.M_ID_TAMMUZ,17,(byte)JewishEvents.TZOM_SEVENTEEN_TAMMUZ.ordinal(),1,1},//Tzom 17 Tamuz, dhia
        {JewishDate.M_ID_AV,9,(byte)JewishEvents.TZOM_NINE_AV.ordinal(),1,1},//Tzom 9 Av, dhia
        {JewishDate.M_ID_AV,15,(byte)JewishEvents.FIFTEEN_AV.ordinal(),1,0},//15 Av
        {JewishDate.M_ID_ELUL,29,(byte)JewishEvents.EREV_ROSH_HASHANA.ordinal(),1,0},//Erev Rosh Hashana
        {JewishDate.M_ID_SIVAN,20,(byte)JewishEvents.TAANIT_GZEROT_408_409.ordinal(),1,0},//5408-5409 memorial 
//TODO: maybe add event since year parameter. for 5408 memorial.
    };
    static final byte [][] event_db_diaspora= 
    {// month_id,day,array index,# of days,jump/dhia(if #_days==1)
        {JewishDate.M_ID_TISHREI,16,(byte)JewishEvents.SDOG_SUKKOT.ordinal(),1,0},//sukkot II
        {JewishDate.M_ID_TISHREI,22,(byte)JewishEvents.SHEMINI_ATZERET.ordinal(),1,0},//shmini azeret
        {JewishDate.M_ID_TISHREI,23,(byte)JewishEvents.SIMCHAT_TORAH.ordinal(),1,0},//simhat_tora
        {JewishDate.M_ID_NISAN,16,(byte)JewishEvents.SDOG_PESACH.ordinal(),1,0},//Pesah II
        {JewishDate.M_ID_NISAN,22,(byte)JewishEvents.SHVII_SDOG_PESACH.ordinal(),1,0},//Shmi'ni Pesah
        {JewishDate.M_ID_SIVAN,7,(byte)JewishEvents.SDOG_SHAVUOT.ordinal(),1,0},//Shavu'ot II
    };

    
    private byte [] current_year_events;
    private boolean diaspora;
    private int year;
    private int year_length;
    public int year()
    {
        return this.year;
    }
    public int yearLength()
    {
        return this.year_length;
    }
    public boolean diaspora()
    {
        return this.diaspora;
    }
    public String getYearEventForDay(JewishDate d, YDateLanguage language)
    {
        return language.getToken(events_str_id[current_year_events[d.dayInYear()]]);
    }
    public String getYearEventForDayRejection(JewishDate d, YDateLanguage language)
    {
        String out=language.getToken(events_str_id[current_year_events[d.dayInYear()]]);
        short rej= isRejected(d);
        if (rej!=0)
            out+=" ("+language.getRejection(rej)+")";
        return out;
    }
    public short getYearEventTypeForDay(JewishDate d)
    {
        return events_type[current_year_events[d.dayInYear()]];
    }
    static public short getEventType(int event_id)
    {
        return events_type[event_id];
    }
    public byte [] getYearEvents()
    {
        return current_year_events;
    }
    private static byte [] cloneArray(byte [] arr)
    {
        byte [] new_arr=new byte[arr.length];
        for (int i=0;i<arr.length;++i)
        {
            new_arr[i]=arr[i];
        }
        return new_arr;
    }
    public YDateAnnual(int year, int year_length, int year_first_day,boolean diaspora)
    {
        this.year=year;
        this.year_length=year_length;
        this.diaspora=diaspora;
        int year_ld_t= JewishDate.ld_year_type(year_length,year_first_day%7+1);
        initialize_year(diaspora,year_ld_t,year_length,year_first_day);
        current_year_events=cloneArray(annual_events[diaspora?1:0][year_ld_t-1]);
        addNationalEvents(current_year_events,year, year_length, year_first_day);
    }
    private static void addNationalEvents(byte [] year_events,int year, int year_length, int year_first_day)
    {
        //Holocaust day
        if (year >= 5718)//1958
        {
            int day_in_year = JewishDate.calculateDayInYearByMonthId(year_length, JewishDate.M_ID_NISAN, 27);
            int dayweek = (day_in_year + year_first_day) % 7;
            if (dayweek == ADate.FRIDAY)//friday
            {
                day_in_year--;
            }
            else if (dayweek == ADate.SUNDAY)//sunday
            {

                day_in_year++;
            }
            year_events[day_in_year] = (byte)JewishEvents.HOLOCAUST_DAY.ordinal();
        }
        //Yom Azma'ut and Yom HaZikaron
        if (year >= 5708)//1948
        {
            int day_in_year = JewishDate.calculateDayInYearByMonthId(year_length, JewishDate.M_ID_IYAR, 5);
            int dayweek = (day_in_year + year_first_day) % 7;

            if (dayweek == ADate.SATURDAY)//on saturday
            {
                day_in_year -= 2;

            }
            else if (dayweek == ADate.FRIDAY)//on friday
            {
                day_in_year -= 1;

            }
            else if (dayweek == ADate.MONDAY && year >= 5764)//on monday (2004) then Yom HaZikaron is on sunday, and that's no good...
            {
                day_in_year += 1;

            }
            year_events[day_in_year - 1] = (byte)JewishEvents.MEMORIAL_DAY_FALLEN.ordinal();//Yom HaZikaron
            year_events[day_in_year] = (byte)JewishEvents.INDEPENDENTS_DAY.ordinal();//Yom Azma'ut
        }
        //Jerusalem day
        if (year >= 5728)//1968
	{
            int day_in_year = JewishDate.calculateDayInYearByMonthId(year_length, JewishDate.M_ID_IYAR, 28);
            year_events[day_in_year] = (byte)JewishEvents.JERUSALEMS_DAY.ordinal();
	}
        //Family day
        if (year >= 5733)//1973
	{
            int day_in_year = JewishDate.calculateDayInYearByMonthId(year_length, JewishDate.M_ID_SHEVAT, 30);
            year_events[day_in_year] = (byte)JewishEvents.FAMILY_DAY.ordinal();
	}
        //Rabin's Day   
        if (year >= 5758)//cheshvan 1997
        {
            int day_in_year = JewishDate.calculateDayInYearByMonthId(year_length, JewishDate.M_ID_CHESHVAN, 12);
            int dayweek = (day_in_year + year_first_day) % 7;
            if (dayweek == ADate.FRIDAY)
            {
                day_in_year--;
            }
            year_events[day_in_year] = (byte)JewishEvents.ISAAC_RABIN_DAY.ordinal();
        }
    }
    static final byte [][][] annual_events = new byte [2][JewishDate.N_YEAR_TYPES][];//[diaspora][year_type][day_in_year]
    static final short [][] annual_events_dhia = new short [JewishDate.N_YEAR_TYPES][4];//[year_type][5]->[day_in_year]
    public static String getEventForDay(JewishDate d, boolean diaspora, YDateLanguage language)
    {
        return language.getToken(events_str_id[getEvents(d, diaspora)[d.dayInYear()]]);
    }
    
    public static byte [] getEvents(JewishDate d,boolean diaspora)
    {
        int year_ld_t= JewishDate.ld_year_type(d.yearLength(),d.yearFirstDay()%7+1);
        return initialize_year(diaspora,year_ld_t,d.yearLength(),d.yearFirstDay());
    }
    public static final short PRECEDE = 512;
    public static final short LATE = 1024;
    public static short isRejected(JewishDate d)
    {
        int year_ld_t= JewishDate.ld_year_type(d.yearLength(),d.yearFirstDay()%7+1);
        short []dhia=annual_events_dhia[year_ld_t-1];
        if (annual_events[0][year_ld_t-1]!=null)
        {
            int diy=d.dayInYear();
            for (short i: dhia)
            {
                if ((i&~(PRECEDE|LATE))==diy)
                {
                    return (short)(i&(PRECEDE|LATE));
                }
            }
        }
        return 0;
    }
    public static byte [] getEvents(int year_length, int year_first_day,boolean diaspora)
    {
        int year_ld_t= JewishDate.ld_year_type(year_length,year_first_day%7+1);
        return initialize_year(diaspora,year_ld_t,year_length,year_first_day);
    }
    static private void ArrayReplace(short[] arr, short find, short put)
    {
        for (int i=0;i<arr.length;++i)
        {
            if (arr[i]==find)
            {
                arr[i]=put;
                return;
            }
        }
    }
    /*static private <T> void ArrayReplace(T[] arr, T find, T put)
    {
        for (int i=0;i<arr.length;++i)
        {
            if (arr[i].equals(find))
            {
                arr[i]=put;
                return;
            }
        }
    }*/
    private static void expandDB(int year_length,int year_first_day,final byte [][] evdb, byte [] year_events, short [] dhia)
    {
        boolean leap=year_length>355;
        final int IDX_M_ID = 0;
        final int IDX_DAY = 1;
        final int IDX_IDX = 2;
        final int IDX_LEN = 3;
        final int IDX_JMP = 4;
        for (int ev = 0; ev < evdb.length; ++ev)
        {
            int m_id = evdb[ev][IDX_M_ID];
            if (m_id == JewishDate.M_ID_ADAR && leap)
            {
                continue;
            }
            if ((m_id == JewishDate.M_ID_ADAR_I || m_id == JewishDate.M_ID_ADAR_II) && !leap)
            {
                continue;
            }
            int diy = JewishDate.calculateDayInYearByMonthId(year_length, m_id, evdb[ev][IDX_DAY]);
            if (evdb[ev][IDX_LEN] == 1)
            {
                boolean enable_event = true;
                if (evdb[ev][IDX_JMP] != 0)// dhia
                {
                    if (evdb[ev][IDX_JMP] < 7)
                    {
                        if ((year_first_day + diy) % 7 == ADate.SATURDAY)
                        {
                            diy += evdb[ev][IDX_JMP];
                            if (dhia!=null)
                            {
                                ArrayReplace(dhia,(short)-1,(short)(diy|(evdb[ev][IDX_JMP]>0 ?LATE:PRECEDE)));
                            }
                        }
                    }
                    else if (evdb[ev][IDX_JMP] >= 7) //enable the date only if it falls on a certain day of week.
                    {
                        if(evdb[ev][IDX_JMP] % 7 != (year_first_day + diy) % 7)
                        {
                            enable_event = false;
                        }
                    }
                }
                if (enable_event)
                    year_events[diy] = evdb[ev][IDX_IDX];
            }
            else
            {
                byte idx = evdb[ev][IDX_IDX];
                for (int l = 0; l < evdb[ev][IDX_LEN]; ++l)
                {
                    year_events[diy + l] = idx;
                    idx += evdb[ev][IDX_JMP];
                }
            }
        }
    }
    private static byte[] initialize_year(boolean diaspora,int year_ld_t,int year_length,int year_first_day)
    {
        if (annual_events[diaspora?1:0][year_ld_t-1]==null)
        {
            annual_events[diaspora?1:0][year_ld_t-1]=new byte [year_length];
            Arrays.fill(annual_events_dhia[year_ld_t-1], (short)-1);
            expandDB(year_length,year_first_day,event_db, annual_events[diaspora?1:0][year_ld_t-1],annual_events_dhia[year_ld_t-1]);
            if (diaspora)
                expandDB(year_length,year_first_day,event_db_diaspora, annual_events[diaspora?1:0][year_ld_t-1],null);
        }
        return annual_events[diaspora?1:0][year_ld_t-1];
    }

/*
hamilinzki כ סיון גזירות תח תט
*/
}
