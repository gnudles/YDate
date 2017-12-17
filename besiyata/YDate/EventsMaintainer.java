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

package besiyata.YDate;

import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.util.AbstractMap;
import java.util.TreeMap;

import besiyata.GP.EventHandler;

/**
 * EventsMaintainer manages events for specific YDate object.
 * @author Orr Dvori &lt;dvoreader@gmail.com&gt;
 */

public class EventsMaintainer {

    YDate m_YDate;
    private boolean m_diaspora;
    private YDateAnnual events_previous=null;
    private YDateAnnual events_current=null;
    private YDateAnnual events_next=null;
    private static AbstractMap<Integer ,SoftReference<YDateAnnual> > annuals_cache = new TreeMap<>();
    private static AbstractMap<Integer ,SoftReference<YDateAnnual> > annuals_cache_diaspora = new TreeMap<>();

    EventHandler.Listener dateChangedListener = new EventHandler.Listener() {
        @Override
        public void process(Object sender) {
            updateEvents();
        }
    };
    public EventsMaintainer(YDate ydate, boolean diaspora)
    {
        m_YDate = ydate;
        m_diaspora = diaspora;
        m_YDate.registerOnDateChanged(dateChangedListener);
        setMaintainEvents();
    }

    private static YDateAnnual getAnnualFromCache(int hd_year,int hd_year_length, int hd_year_first_day,boolean diaspora)
    {
        AbstractMap<Integer ,SoftReference<YDateAnnual> > cache;
        if (diaspora)
        {
            cache=annuals_cache_diaspora;
        }
        else
            cache=annuals_cache;
        YDateAnnual annual;
        SoftReference<YDateAnnual> sa = cache.get(hd_year);
        if (sa!=null)
        {
            annual=sa.get();
            if (annual!=null)
                return annual;
        }
        annual = new YDateAnnual(hd_year,hd_year_length,hd_year_first_day,diaspora);
        cache.put(hd_year,new SoftReference<>(annual));
        return annual;
    }
    public void setMaintainEvents()
    {
        if (events_current==null)
        {
            events_current=getAnnualFromCache(m_YDate.hd.year(),m_YDate.hd.yearLength(),m_YDate.hd.yearFirstDay(),m_diaspora);

            events_next=getAnnualFromCache(m_YDate.hd.year()+1, YDate.JewishDate.calculateYearLength(m_YDate.hd.year()+1),m_YDate.hd.yearFirstDay()+m_YDate.hd.yearLength(),m_diaspora);

            events_previous=getAnnualFromCache(m_YDate.hd.year()-1, YDate.JewishDate.calculateYearLength(m_YDate.hd.year()-1), YDate.JewishDate.calculateYearFirstDay(m_YDate.hd.year()-1),m_diaspora);
        }
    }
    public YDateAnnual yearEvents(){ return events_current;}

    public byte getEvent()
    {
        return getEvent(m_YDate.hd.dayInYear());
    }
    public byte getEvent(int day_in_year)
    {
        if (day_in_year<-events_previous.yearLength() || day_in_year>= events_current.yearLength() + events_next.yearLength())
            return 0;
        if (day_in_year<0)
            return events_previous.getYearEvents()[events_previous.yearLength()+day_in_year];
        if (day_in_year<events_current.yearLength())
            return events_current.getYearEvents()[day_in_year];
        day_in_year-=events_current.yearLength();
        return events_next.getYearEvents()[day_in_year];
    }

    private void updateEvents()
    {
        if (events_current!=null)
        {
            if (events_current.year()!=m_YDate.hd.year())
            {
                events_current=null;
                setMaintainEvents();
            }
        }
    }
}
