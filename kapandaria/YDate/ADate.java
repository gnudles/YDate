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
 * ADate class is abstract class for date management. it is based on GDN (Genesis Day Number).
 * @author Orr Dvori &lt;dvoreader@gmail.com&gt;
 * @version 4.0.10
 */
public abstract class ADate
{
    /**
     * The day of the unix epoch (Jan. 1 1970). It measured by the "beginning
     * count".
     */
    static final int EPOCH_DAY = 2092591;//1.1.1970 - in gdn

    public static final int SUNDAY = 0;//Sun - Sunne in old english
    public static final int MONDAY = 1;//Moon - Mōna in old english
    public static final int TUESDAY = 2;//Mars - Tīw in old english
    public static final int WEDNESDAY = 3;//Mercury - Wōden in old english
    public static final int THURSDAY = 4;//Jupiter - Þunor in old english
    public static final int FRIDAY = 5;//Venus - frig in old english
    public static final int SATURDAY = 6;//Saturn - Sætern in old english
    
    static final String[] DayInWeekTokens = {"wd_sunday", "wd_monday", "wd_tuesday",
        "wd_wednesday", "wd_thursday", "wd_friday", "wd_saturday"};
        
    /**
     * The difference between the GDN and the Julian count. (the
     * Julian count start earlier)
     */
    static final int JULIAN_DAY_OFFSET = 347997; // offset between gdn and jdn.
    
    private DateSyncGroup m_syncGroup;
    private final EventHandler m_dateChanged = new EventHandler();
    private boolean m_muteTriggers = false;
    /**
     * set the date object by GDN.
     * @param gdn
     * @return true if the gdn is in bounds.
     */
    public abstract boolean setByGDN(int gdn);
    public boolean seekBy(int offset) {
        return setByGDN(GDN()+offset);
    }
    /**
     * Genesis Day Number is an ordinal day count from the estimated genesis.
     * it is equivalent to days since the beginning.
     * day 0 is the estimated sunday of Genesis.
     * @return the Genesis Day Number of the current date.
     */
    public abstract int GDN();
    /**
     * The upper bound of possible valid range.
     * The lower bound is in bounds, but the upper bound is out of the bounds.
     * @return the GDN of the upper bound.
     * related method: {@link #lowerBound}
     * for more information about GDN see {@link kapandaria.YDate.ADate#GDN}
     */
    public abstract int upperBound();
    /**
     * The lower bound of possible valid range.
     * The lower bound is in bounds, but the upper bound is out of the bounds.
     * @return the GDN of the lower bound.
     * related method: {@link #upperBound}
     * for more information about GDN see {@link kapandaria.YDate.ADate#GDN}
     */
    public abstract int lowerBound();
    /**
     *
     * @return whether date represented is valid
     */
    public abstract boolean isValid();
    /**
     *
     * @return whether last operation gave desired result.
     */
    public abstract boolean isDesired();
    /**
     * Register a listner for a date change event.
     * The listner will recieve as a sender the date object.
     * @param listener 
     */
    public void registerOnDateChanged(EventHandler.Listener listener) {
        m_dateChanged.addListener(listener);
    }
    /**
     * Method to be called on each change, in order to update the sync group.
     * The state might change again if clipping occurs. 
     * If there is no sync group, clipping will be still active.
     * @return true if no clipping occured and the date is valid. false otherwise.
     */
    protected boolean stateChanged()
    {
        boolean inBounds = checkBounds(GDN());
        if (!m_muteTriggers)
        {
            if (m_syncGroup!=null)
                return m_syncGroup.syncBy(this);
            else
            {
                
                if (!inBounds)
                {
                    muteTriggers();
                    clip();
                    unmuteTriggers();
                }
                triggerEvents();
                
            }
        }
        return inBounds;
    }
    
    protected void muteTriggers()
    {
        m_muteTriggers = true;
    }
    
    protected void unmuteTriggers()
    {
        m_muteTriggers = false;
    }
    
    public void triggerEvents()
    {
        m_dateChanged.trigger(this);
    }
    
    public void setSyncGroup(DateSyncGroup syncGroup)
    {
        m_syncGroup = syncGroup;
    }
    /**
     * clip the date to fit in bound.
     * @return true if clipping occurs, otherwise false.
     */
    protected boolean clip()
    {
        if (GDN()<lowerBound())
        {
            setByGDN(lowerBound());
            return true;
        }
        if (GDN()>=upperBound())
        {
            setByGDN(upperBound()-1);
            return true;
        }
        return false;
    }
    public boolean checkBounds(int gdn)
    {
        return (gdn >= lowerBound() && gdn < upperBound());
    }
    
    /**
     * Return the upcoming day in week, or the current day if it is that certain day in week.
     * 
     * @param diw day in week. un range 0..6
     * @param days day in GDN or other count that day 0 is sunday
     * @return days + x (0 &lt; = x &lt; = 6). that gives that certain day in week. 
     */
    public static int getNext(int diw, int gdn) // return the upcoming diw (or today if it's that diw)
    {
        int diff = (diw - gdn % 7 + 7) % 7;
        return (gdn + diff);
    }

    /**
     * Return the last day of certain day in week, or the current day if it is that certain day in week.
     * 
     * @param diw day in week. un range 0..6
     * @param days day in GDN or other count that day 0 is sunday
     * @return days - x (0 &lt; = x &lt; = 6). that gives that certain day in week. 
     */
    public static int getPrevious(int diw, int gdn) {
        return getNext(diw, gdn - 6);
    }
    
    public static Date toDate(int days, float hour)//hour in utc
    {
        long millis = (long) (days - ADate.EPOCH_DAY) * 3600L * 24 * 1000L;
        millis += (hour * 3600L * 1000L);
        return new Date(millis);
    }

    public static int JdToDays(double jd)//hour in utc
    {
        return (int) (jd + 0.5001 - ADate.JULIAN_DAY_OFFSET);
    }
    public static double DaysToJd(int gdn) {
            return gdn + ADate.JULIAN_DAY_OFFSET - 0.5;
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

    public static String FormatUTC(Date t, TimeZoneProvider tz, YDateLanguage le ) {
        String lstr;
        int utc_minute_offset = (int) (tz.getOffset(t) * 60);
        t.setTime(t.getTime() + utc_minute_offset * 60000L);
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.setTime(t);
        int gdmonth = c.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        int gdday = c.get(Calendar.DAY_OF_MONTH);
        int gdyear = c.get(Calendar.YEAR);
        int dayInWeek = c.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;// range 0-6

        String clock_type = "UTC" + (utc_minute_offset >= 0 ? "+" : "-") + String.valueOf(Math.abs(utc_minute_offset)) + "MIN";
        int minutes = c.get(Calendar.HOUR_OF_DAY)*60 + c.get(Calendar.MINUTE);
        lstr = Format.GDateString(gdyear, gdmonth, gdday) + " " + Format.FormatMinutes(minutes);
        String day_part_name = dayPartName(minutes);
        lstr += " (" + le.getToken(DayInWeekTokens[dayInWeek]) + " " + day_part_name + ")";
        lstr += " (" + clock_type + ")";
        return lstr;
    }
}
