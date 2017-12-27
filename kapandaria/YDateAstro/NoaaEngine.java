/**
 * Data provided by NOAA/ESRL Global Monitoring Division, Boulder, Colorado, USA (http://www.esrl.noaa.gov/gmd/)
 * Adjusted and converted to Java by Orr Dvori <dvoreader@gmail.com>.
 * This Code is in Public Domain. For more information see https://www.esrl.noaa.gov/gmd/about/disclaimer.html
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package kapandaria.YDateAstro;

public class NoaaEngine implements SunTimeEngine
{
    double m_jd;
    double m_latitude;
    double m_longitude;
    public static double radToDeg(double angleRad)
    {
        return (180.0 * angleRad / Math.PI);
    }

    public static double degToRad(double angleDeg)
    {
        return (Math.PI * angleDeg / 180.0);
    }
    /**
     * 
     * @param rise
     * @param SunAlt sun altitude in degrees. most common use is 90.833 .
     * @return 
     */
private double calcSunriseSetUTC(double jd, boolean rise, double SunAlt)
{
        SolarState s_state= new SolarState(jd);
        double solarDec=s_state.getTheta();
        double eqTime=s_state.calcEquationOfTime();
        double hourAngle = SolarState.calcHourAngle(m_latitude, solarDec, SunAlt);
        if (!rise) {
            hourAngle = -hourAngle;
        }
        double delta = m_longitude + radToDeg(hourAngle);
        double timeUTC = 720 - (4.0 * delta) - eqTime;	// in minutes
        return timeUTC;
    }

    /**
     *
     * @param SunAltitude
     * @param calc
     * @return
     */
    @Override
    public RiseSet calculateSunRiseSet(double SunAltitude, RiseSetCalc calc)
    {
        RiseSet result=new RiseSet();
        if (calc ==RiseSetCalc.RISE_ONLY || calc ==RiseSetCalc.RISE_SET)
        {
            double timeUTC = calcSunriseSetUTC( m_jd,true,SunAltitude);
            result.rise = calcSunriseSetUTC(m_jd + timeUTC/1440.0,true,SunAltitude); 
        }
        if (calc ==RiseSetCalc.SET_ONLY || calc ==RiseSetCalc.RISE_SET)
        {
            double timeUTC = calcSunriseSetUTC( m_jd,false,SunAltitude);
            result.set = calcSunriseSetUTC(m_jd + timeUTC/1440.0,false,SunAltitude);
        }
        return result;
    }    

    @Override
    public void initEngine(double jd, double latitude, double longitude)
    {
        m_jd = jd;
        m_latitude=latitude;
        m_longitude=longitude;
    }

    

    public static class SolarState
    {

        private double my_t;
        private double my_L0;
        private double my_M;
        private double my_C;
        private double my_O;
        private double my_v;
        private double my_ec;
        private double my_R;
        private double my_omega;
        private double my_lambda;
        private double my_e0;
        private double my_e;
        private double my_theta;

        public double getTheta()
        {
            return my_theta;
        }

        public double getR()
        {
            return my_R;
        }

        public SolarState(double jd)
        {

            my_t = calcTimeJulianCent(jd);
            calcGeomMeanLongSun();//my_L0
            calcGeomMeanAnomalySun();//my_M
            calcSunEqOfCenter();//my_C
            calcSunTrueLong();//my_O
            calcSunTrueAnomaly();//my_v
            calcEccentricityEarthOrbit();//my_ec
            calcSunRadVector();//my_R
            calcSunApparentLong();//my_lambda,my_omega
            calcMeanObliquityOfEcliptic();//my_e0
            calcObliquityCorrection();//my_e
            calcSunDeclination();//my_theta

        }

        public static double calcHourAngle(double lat, double solarDec, double alt_angle)//for sunset multiply by -1
        {
            double latRad = degToRad(lat);
            double sdRad = degToRad(solarDec);

            double HAarg = (Math.cos(degToRad(alt_angle)) / (Math.cos(latRad) * Math.cos(sdRad)) - Math.tan(latRad) * Math.tan(sdRad));

            double HA = (Math.acos(HAarg));

            return HA;		// in radians
        }

        /**
         * convert Julian Day to centuries since J2000.0.
         * @param jd the Julian Day to convert
         * @return the T value corresponding to the Julian Day
         */
        public static double calcTimeJulianCent(double jd)
        {
            double t = (jd - 2451545.0) / 36525.0;
            return t;
        }

        private void calcGeomMeanLongSun()
        {
            my_L0 = 280.46646 + my_t * (36000.76983 + 0.0003032 * my_t);
            while (my_L0 > 360.0) {
                my_L0 -= 360.0;
            }
            while (my_L0 < 0.0) {
                my_L0 += 360.0;
            }
            // in degrees
        }

        /**
         * calculate the distance to the sun in AU
         * sets the my_R variable with sun radius vector in AUs
         */
        private void calcSunRadVector()
        {
            my_R = (1.000001018 * (1 - my_ec * my_ec)) / (1 + my_ec * Math.cos(degToRad(my_v)));// in AUs
        }

        private void calcGeomMeanAnomalySun()
        {
            my_M = 357.52911 + my_t * (35999.05029 - 0.0001537 * my_t);
            // in degrees
        }

        private void calcEccentricityEarthOrbit()
        {
            my_ec = 0.016708634 - my_t * (0.000042037 + 0.0000001267 * my_t);
            // unitless
        }

        private void calcSunEqOfCenter()
        {
            double mrad = degToRad(my_M);
            double sinm = Math.sin(mrad);
            double sin2m = Math.sin(mrad + mrad);
            double sin3m = Math.sin(mrad + mrad + mrad);
            my_C = sinm * (1.914602 - my_t * (0.004817 + 0.000014 * my_t)) + sin2m * (0.019993 - 0.000101 * my_t) + sin3m * 0.000289;
            // in degrees
        }

        private void calcSunTrueLong()
        {
            my_O = my_L0 + my_C;
            // in degrees
        }

        private void calcSunTrueAnomaly()
        {

            my_v = my_M + my_C;
            // in degrees
        }

        private void calcSunApparentLong()
        {
            my_omega = 125.04 - 1934.136 * my_t;
            my_lambda = my_O - 0.00569 - 0.00478 * Math.sin(degToRad(my_omega));
        }

        private void calcMeanObliquityOfEcliptic()
        {
            double seconds = 21.448 - my_t * (46.8150 + my_t * (0.00059 - my_t * (0.001813)));
            my_e0 = 23.0 + (26.0 + (seconds / 60.0)) / 60.0;
            // in degrees
        }

        private void calcObliquityCorrection()
        {
            my_omega = 125.04 - 1934.136 * my_t;
            my_e = my_e0 + 0.00256 * Math.cos(degToRad(my_omega));
            // in degrees
        }

        /**
         * calculate the right ascension of the sun
         * @return sun's right ascension in degrees
         */
        public double calcSunRtAscension()
        {
            double tananum = (Math.cos(degToRad(my_e)) * Math.sin(degToRad(my_lambda)));
            double tanadenom = (Math.cos(degToRad(my_lambda)));
            double alpha = radToDeg(Math.atan2(tananum, tanadenom));
            return alpha;
            // in degrees
        }

        private void calcSunDeclination()
        {
            double sint = Math.sin(degToRad(my_e)) * Math.sin(degToRad(my_lambda));
            my_theta = radToDeg(Math.asin(sint));
            // in degrees
        }

        public double getSunDeclination()// in degrees
        {
            return my_theta;
        }

        public double calcEquationOfTime()
        {

            double y = Math.tan(degToRad(my_e) / 2.0);
            y *= y;
            double sin2l0 = Math.sin(2.0 * degToRad(my_L0));
            double sinm = Math.sin(degToRad(my_M));
            double cos2l0 = Math.cos(2.0 * degToRad(my_L0));
            double sin4l0 = Math.sin(4.0 * degToRad(my_L0));
            double sin2m = Math.sin(2.0 * degToRad(my_M));

            double Etime = y * sin2l0 - 2.0 * my_ec * sinm + 4.0 * my_ec * y * sinm * cos2l0
                    - 0.5 * y * y * sin4l0 - 1.25 * my_ec * my_ec * sin2m;
            return radToDeg(Etime) * 4.0;	// in minutes of time
        }
    }

}
