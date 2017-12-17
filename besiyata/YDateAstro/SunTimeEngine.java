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
package besiyata.YDateAstro;

import java.util.Date;
import besiyata.YDate.Format;
import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author Orr Dvori
 */
public interface SunTimeEngine {
    public enum RiseSetCalc
    {
        RISE_ONLY,
        SET_ONLY,
        RISE_SET
    }
    public static class RiseSet
    {
        public RiseSet(){ rise=0;set=0;}
		public double rise;
		public double set;
    }
    /**
     * @param jd Julian day in local time
     * @param latitude in degrees, North is positive.
     * @param longitude in degrees, East is positive.
     */
    public void initEngine(double jd, double latitude, double longitude);
/**
 * 
 * @param SunAltitude Rise/Set Altitude (in degrees)
 * @param calc what to calculate?
 * @return time in hours UTC (can be negative)
 */
    public RiseSet calculateSunRiseSet(double SunAltitude, RiseSetCalc calc);
    
    public static String Format(double t)
    {
        while (t<0)
            t+=1440;
        return Format.TimeString(((int)t)/60, ((int)t)%60, ((int)(t*60))%60);
    }
    public static Date toDate(double t,int year,int mon, int day)
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
