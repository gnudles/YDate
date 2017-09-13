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
        if (tz.equals(TimeZone.getTimeZone("Asia/Jerusalem")))
            diaspora=DiasporaType.ErezIsrael;
        else
            diaspora=DiasporaType.Diaspora;
    }
    public YDatePreferences(YDate.TimeZoneProvider _timeZoneProvider, DiasporaType _diaspora)
    {
        timeZoneProvider=_timeZoneProvider;
        diaspora=_diaspora;
    }
    public enum DiasporaType{ ErezIsrael,Diaspora,Both};
    public double longitude;
    public double latitude;
    public double altitude;//in meters from MSL
    public DiasporaType diaspora;
    public YDate.TimeZoneProvider timeZoneProvider;
}
