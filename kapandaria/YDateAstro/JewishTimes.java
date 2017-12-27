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
package kapandaria.YDateAstro;

import kapandaria.YDate.Format;

public class JewishTimes
{
    private double noonEqTime, noonSolarDec, firstHourAngle, risemin, setmin;
    private double risemin_16, setmin_16, setmin_8, setmin_6;
    private double risemin_11,risemin_19;
    private double noonmin;
    public static JewishTimes createWith(SunTimeEngine engine)
    {

        return new JewishTimes(engine);
    }
    private JewishTimes(SunTimeEngine engine)
    {
        SunTimeEngine.RiseSet res;
        res = engine.calculateSunRiseSet(90.833,SunTimeEngine.RiseSetCalc.RISE_SET);
        risemin= res.rise;
        setmin=res.set;
        noonmin= (risemin+setmin)/2;
        res = engine.calculateSunRiseSet(106.01,SunTimeEngine.RiseSetCalc.RISE_SET);
        risemin_16=res.rise;
        setmin_16=res.set;
        setmin_8=engine.calculateSunRiseSet(98.5,SunTimeEngine.RiseSetCalc.SET_ONLY).set;
        setmin_6=engine.calculateSunRiseSet(95.95,SunTimeEngine.RiseSetCalc.SET_ONLY).set;
        risemin_11=engine.calculateSunRiseSet(101.5,SunTimeEngine.RiseSetCalc.RISE_ONLY).rise;
        risemin_19=engine.calculateSunRiseSet(109.75,SunTimeEngine.RiseSetCalc.RISE_ONLY).rise;
    }
    public double getNoon()
    {
        return noonmin;
    }

    public double getSunset()
    {
        return setmin;
    }

    public double getSunrise()
    {
        return risemin;
    }
    public double getDawn()// 72 minutes before the sunrise, 16 degrees
    {  
        return risemin_16;
    }
    public double getDawn90()// 90 minutes before the sunrise, 19.75 degrees
    {  
        return risemin_19;
    }
    

    public double getRecognize()// 50 minutes before the sunrise, 11.5 degrees
    {
        return risemin_11;
    }

    public double getEndTimeKriatShma()// 3 day-hours (shaot zmaniot) after the sunrise
    {
        return risemin + (noonmin - risemin) / 2.0;//
    }

    public double getEndTimeShahrit()// 4 day-hours (shaot zmaniot) after the sunrise
    {
        return risemin + 2 * (noonmin - risemin) / 3.0;//
    }
    public double getEndTimeKriatShmaMGA()// 3 day-hours (shaot zmaniot) after the sunrise
    {
        //return (risemin-72)+3*((setmin +72) - (risemin-72))/12.0 ;
        return risemin_16+3*(setmin_8 - risemin_16)/12.0 ;
    }

    public double getEndTimeShahritMGA()// 4 day-hours (shaot zmaniot) after the sunrise
    {
        //return (risemin-72)+4*((setmin +72) - (risemin-72))/12.0 ;
        return risemin_16+4*(setmin_8 - risemin_16)/12.0 ;
    }

    public double getMinhaGdolaZmaniot()// half day-hour (shaot zmaniot) after the noon
    {
        return noonmin + 1 * (setmin - noonmin) / 12.0;//
    }
    public double getMinhaGdola()// half hour after the noon
    {
        return noonmin + 30;
    }

    public double getMinhaKtana()// 3.5 day-hour (shaot zmaniot) after the noon
    {
        return noonmin + 7 * (setmin - noonmin) / 12.0;//
    }

    public double getPlagMinha()// 3.5 day-hour (shaot zmaniot) after the noon
    {
        return noonmin + 19 * (setmin - noonmin) / 24.0;//
    }
    public double getVisibleStars()// 8.5 degrees
    {
        return setmin_8;
    }
    public double getVisibleStarsGeonim()// 5.95 degrees
    {
        return setmin_6;
    }
    
    public double getVisibleStarsZmaniot24()// 24*3/4 minutes zmaniot after the sunset
    {
        return setmin + (setmin - noonmin) / 20.0;//(24.0*3/4/60)/6=1/20
    }
    public double getVisibleStarsZmaniot18()// 18*3/4 minutes zmaniot after the sunset
    {
        return setmin + (setmin - noonmin)*(18.0*3/4/60)/6;
    }
    public double getVisibleStarsZmaniot225()// 22.5*3/4 minutes zmaniot after the sunset
    {
        return setmin + (setmin - noonmin)*(22.5*3/4/60)/6;
    }
    public double getBiurHametz()
    {
        //return (risemin-72)+5*((setmin +72) - (risemin-72))/12.0;//
        return risemin_16+5*(setmin_8 - risemin_16)/12.0;
    }
    public double getRabenuTam()
    {
        //int minutes_time=(int)(1.2*(setmin - noonmin) / 12.0); //72 minutes is 1.2 hours
        return setmin_16;
    }

    
}
