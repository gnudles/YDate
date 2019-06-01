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
import java.util.TimeZone;

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
    public YDatePreferences(TimeZoneProvider _timeZoneProvider, DiasporaType _diaspora)
    {
        timeZoneProvider=_timeZoneProvider;
        diaspora=_diaspora;
    }
    public enum DiasporaType{ ErezIsrael,Diaspora,Both}
    public enum HaftaraMinhag{ SFARADIM,ASHKENAZ,ITALKI,TEIMANI,CHABAD,FRANKFURT}
    public double longitude;
    public double latitude;
    public double altitude;//in meters from MSL
    public DiasporaType diaspora;
    public TimeZoneProvider timeZoneProvider;
}
