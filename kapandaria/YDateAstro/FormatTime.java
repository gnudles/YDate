/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kapandaria.YDateAstro;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class FormatTime
{
    public static String Format(double t)
    {
        while (t<0)
            t+=1440;
        return kapandaria.YDate.Format.TimeString(((int)t)/60, ((int)t)%60, ((int)(t*60))%60);
    }
    public static Date toDate(double t, int year, int mon, int day)
    {
        Calendar cal=Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, mon-1);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.SECOND, (int)(t*60L));
        return cal.getTime();
    }
}
