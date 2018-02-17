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

import java.util.LinkedList;
import java.util.List;
import kapandaria.GP.EventHandler;

/**
 *
 * @author Orr Dvori
 */
public class DateSyncGroup
{
    private List<ADate> m_dates;
    final int INVALID_BOUND = -1;
    /**
     * common lower bound in GDN.
     */
    private int m_lowerCommonBound;
    /**
     * common upper bound in GDN.
     */
    private int m_upperCommonBound;
    private EventHandler m_dateChanged;
    public DateSyncGroup()
    {
        m_dates = new LinkedList<>();
        m_lowerCommonBound = INVALID_BOUND;
        m_upperCommonBound = INVALID_BOUND;
        m_dateChanged = new EventHandler();
    }
        
    /**
     * Register a listner for a date change event.
     * The listner will recieve as a sender the date object that triggered the sync.
     * @param listener 
     */
    public void registerOnDateChanged(EventHandler.Listener listener) {
        m_dateChanged.addListener(listener);
    }

    private void notifyDateChanged(ADate date) {
        m_dateChanged.trigger(date);
    }
    /**
     * add date to the sync group.
     * @param dateObject the date to be added.
     */
    public void add(ADate dateObject)
    {
        dateObject.setSyncGroup(this);
        m_dates.add(dateObject);
        if (m_lowerCommonBound==INVALID_BOUND || m_lowerCommonBound<dateObject.lowerBound())
            m_lowerCommonBound=dateObject.lowerBound();
        if (m_upperCommonBound==INVALID_BOUND || m_upperCommonBound>dateObject.upperBound())
            m_upperCommonBound=dateObject.upperBound();
    }
    private int clippedGDN(int gdn)
    {
        if (gdn<m_lowerCommonBound)
            return m_lowerCommonBound;
        if (gdn>=m_upperCommonBound)
            return m_upperCommonBound-1;
        return gdn;
    }
    /**
     * Synchronize all date object in the group by the following date.
     * If the date exceeds the common dates bound, the date will be clipped.
     * if the specified date is invalid, nothing will happen.
     * @param dateSync the date object to sync with. it doesn't have to be in the group.
     * @return true if no clipping occurs and the date is valid. false otherwise.
     */
    public boolean syncBy(ADate dateSync)
    {
        if (!dateSync.isValid())
            return false;
        int gdn = clippedGDN(dateSync.GDN());
        boolean clipped = (gdn!=dateSync.GDN());
        for (ADate date : m_dates) {
            if (clipped || date != dateSync) //we will skip updating the sync object.
                date.setByGDN(gdn);
        }
        notifyDateChanged(dateSync);
        return !clipped;
    }
}
