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