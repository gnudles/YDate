package besiyata.YDate;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import besiyata.YDate.YDate.*;
/**
 * Created by Orr Dvori on 7/11/2017.
 */

public class NativeTzProvider implements YDate.TimeZoneProvider {
    TimeZone tz;
    public NativeTzProvider()
    {
        tz=Calendar.getInstance().getTimeZone();
    }
    public NativeTzProvider(TimeZone timeZone)
    {
        tz=timeZone;
    }
    @Override
    public float getOffset(Date d) {

        return (tz.getOffset(d.getTime())/1000L)/3600.0f;
    }
}
