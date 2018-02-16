/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kapandaria.YDate;


/**
 * ADate class is abstract class for date management. it is based on GDN (Genesis Day Number).
 * @author Orr Dvori &lt;dvoreader@gmail.com&gt;
 * @version 4.0.10
 */
public abstract class ADate
{
    /**
     * The day of the unix epoch (Jan. 1 1970). It measured by the "beginning
     * count".
     */
    static final int EPOCH_DAY = 2092591;//1.1.1970

    static final int SUNDAY = 0;//Sun - Sunne in old english
    static final int MONDAY = 1;//Moon - Mōna in old english
    static final int TUESDAY = 2;//Mars - Tīw in old english
    static final int WEDNESDAY = 3;//Mercury - Wōden in old english
    static final int THURSDAY = 4;//Jupiter - Þunor in old english
    static final int FRIDAY = 5;//Venus - frig in old english
    static final int SATURDAY = 6;//Saturn - Sætern in old english
    /**
     * The difference between the "beginning count" and the Julian count. (the
     * Julian count start earlier)
     */
    static final int JULIAN_DAY_OFFSET = 347997;
    
    DateSyncGroup m_syncGroup;
    /**
     * set the date object by GDN.
     * @param gdn
     * @return true if the gdn is in bounds.
     */
    public abstract boolean setByGDN(int gdn);
    /**
     * Genesis Day Number is an ordinal day count from the estimated genesis.
     * it is equivalent to days since the beginning.
     * day 0 is the estimated sunday of genesis.
     * @return the Genesis Day Number of the current date.
     */
    public abstract int GDN();
    /**
     * The upper bound of possible valid range.
     * The lower bound is in bounds, but the upper bound is out of the bounds.
     * @return the GDN of the upper bound.
     * related method: {@link #lowerBound}
     * for more information about GDN see {@link kapandaria.YDate.ADate#GDN}
     */
    public abstract int upperBound();
    /**
     * The lower bound of possible valid range.
     * The lower bound is in bounds, but the upper bound is out of the bounds.
     * @return the GDN of the lower bound.
     * related method: {@link #upperBound}
     * for more information about GDN see {@link kapandaria.YDate.ADate#GDN}
     */
    public abstract int lowerBound();
    public abstract boolean isValid();
    /**
     * Method to be called on each change, in order to update the sync group.
     * The state might change again if clipping occurs. 
     * If there is no sync group, clipping will be still active.
     * @return true if no clipping occurs and the date is valid. false otherwise.
     */
    protected boolean stateChanged()
    {
        if (m_syncGroup!=null)
            return m_syncGroup.syncBy(this);
        return !clip();
    }
    public void setSyncGroup(DateSyncGroup syncGroup)
    {
        m_syncGroup = syncGroup;
    }
    /**
     * clip the date to fit in bound.
     * @return true if clipping occurs, otherwise false.
     */
    protected boolean clip()
    {
        if (GDN()<lowerBound())
        {
            setByGDN(lowerBound());
            return true;
        }
        if (GDN()>=upperBound())
        {
            setByGDN(upperBound()-1);
            return true;
        }
        return false;
    }
    public boolean checkBounds(int gdn)
    {
        return (gdn >= lowerBound() && gdn < upperBound());
    }
}
