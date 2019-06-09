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

import java.text.DecimalFormat;


public class Format
{
    public static String FormatMinutes(double min)
    {
        int sign=1;
        if (min<0)
        {
            sign=-1;
            min=-min;
        }
        int imin = (int) (min + 0.5);
        imin = imin % (60*24);
        String stime = ((sign==-1)?"-":"") + (imin / 60);
        imin = imin % 60;
        stime += ":"+Formatter00.format(imin);
        return stime;
    }
    public static char getDigitChar(int d)
    {
        return (char)('0'+(d%10));
    }
    public static DecimalFormat Formatter00 = new DecimalFormat("00");

    public static String GDateString(int y,int m,int d)
    {
        String sdate = Formatter00.format(d);
        sdate += "."+Formatter00.format(m);
        sdate += "."+Formatter00.format(y%100);
        return sdate;
    }
    public static String TimeString(int h,int m)
    {
        String stime = Formatter00.format(h);
        stime += ":"+Formatter00.format(m);
        return stime;
    }
    public static String TimeString(int h,int m,int s)
    {
        String stime = Formatter00.format(h);
        stime += ":"+Formatter00.format(m);
        stime += ":"+Formatter00.format(s);
        return stime;
    }
    public static final String[] heb_alphabeta =
    {
        " ", "א", "ב", "ג", "ד", "ה", "ו", "ז", "ח", "ט",
        "י", "כ", "ל", "מ", "נ", "ס", "ע", "פ", "צ",
        "ק", "ר", "ש", "ת"
    };
    
    
    public static String HebIntSubString(int n, boolean geresh,boolean gereshim)
    {
        final int ONES=0;
        final int TET=9;
        final int TENS=9;
        final int HUNDREDS=18;

        if (n<1000 && n>=1)
        {
            String strstrm="";
            int a,t;
            a=n/100;
            n=n%100;
            while (a>0)
            {
                t=Math.min(a, 4);
                a-=t;
                if (a==0 && n==0 && !strstrm.equals("") && gereshim)
                    strstrm+="\"";
                strstrm+=heb_alphabeta[t+HUNDREDS];
            }
            
            if (n == 15 || n == 16)
            {
                n-=9;
                strstrm+=heb_alphabeta[TET];
            }
            a=n/10;
            n=n%10;
            if (a>0)
            {
                if (n==0 && !strstrm.equals("") && gereshim)
                    strstrm+="\"";
                strstrm+=heb_alphabeta[TENS+a];
            }
            
            if (n>0)
            {
                if (!strstrm.equals("") && gereshim)
                    strstrm+="\"";
                strstrm+=heb_alphabeta[ONES+n];
            }
            if (strstrm.length()==1 && geresh)
                strstrm+="\'";
            return strstrm;
        }
        else
            return String.valueOf(n);

    }
    public static String HebIntString(int n, boolean pg) //pg - prat gadol
    {
        boolean geresh,gereshim;
        int a;
        String out="";
        a=n/1000;
        n=n%1000;
        if ( a>0 && pg )
        {
            out=HebIntSubString(a,true,true);
            if (n==0 || a>10)
                out+=" אלפים ";
        }
        if (n!=0)
            out+=HebIntSubString(n,true,true);
        return out;
    }
    public static int getOnesDigit(int num)
    {
        return num%10;
    }
    public static int getTenthsDigit(int num)
    {
        return (num/10)%10;
    }
    public static int getHundredsDigit(int num)
    {
        return (num/100)%10;
    }
    public static String numSuffix(int num) {
        if (getTenthsDigit(num) == 1) {
            return "th";
        }
        switch (getOnesDigit(num)) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

}
