/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package besiyata.YDate;

import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author orr
 */
public class YDatePreferences
{
    public YDatePreferences()
    {
        TimeZone tz= Calendar.getInstance().getTimeZone();
        timeZoneProvider=new NativeTzProvider(tz);
        diaspora=!tz.equals(TimeZone.getTimeZone("Asia/Jerusalem"));
    }
    public YDatePreferences(YDate.TimeZoneProvider _timeZoneProvider, boolean _diaspora)
    {
        timeZoneProvider=_timeZoneProvider;
        diaspora=_diaspora;
    }

    public double longitude;
    public double latitude;
    public double altitude;//in meters from MSL
    public boolean diaspora;
    public YDate.TimeZoneProvider timeZoneProvider;
}
