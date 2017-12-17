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

package besiyata.GP;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by orr on 05/02/17.
 */


public class EventHandler {
    public interface Listener
    {
        void process( Object sender );
    }
    private List <SoftReference<Listener> > listeners;
    public EventHandler(){
        listeners = new ArrayList<>();
    }
    public void addListener(Listener l)
    {
        listeners.add(new SoftReference<Listener>(l));
    }
    public void removeListener(Listener rm_l)
    {
        for (SoftReference<Listener> sr_l : listeners)
        {
            Listener lptr=sr_l.get();
            if (lptr==rm_l) {
                listeners.remove(sr_l);
            }
        }
    }
    public void trigger( Object sender )
    {
        for (SoftReference<Listener> l : listeners)
        {
            Listener lcall=l.get();
            if (lcall!=null) {
                lcall.process(sender);
            }
            else {
               listeners.remove(l);
            }

        }
    }

}