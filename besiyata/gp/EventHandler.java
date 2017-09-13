package besiyata.gp;

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