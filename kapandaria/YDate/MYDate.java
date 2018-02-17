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

/**
 *
 * @author Orr Dvori
 */
public class MYDate
{
    DateSyncGroup m_syncGroup;
    EventsMaintainer m_eventsMaintainer;
    JewishDate m_hd;
    GregorianDate m_gd;
    public MYDate()
    {
        /*m_syncGroup = new DateSyncGroup();
        m_hd= new JewishDate();
        m_gd = new GregorianDate(0, 0, 0); //  TODO : get from Date object.
        m_syncGroup.add(m_hd);
        m_syncGroup.add(m_gd);
        m_syncGroup.syncBy(m_gd);
        m_eventsMaintainer = new EventsMaintainer((YDate)this, true);
        m_syncGroup.registerOnDateChanged(m_eventsMaintainer.dateListener());*/
    }
    
}
