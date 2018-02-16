/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
