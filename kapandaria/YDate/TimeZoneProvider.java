/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kapandaria.YDate;

import java.util.Date;

/**
 *
 * @author Orr Dvori
 */
public interface TimeZoneProvider
{
        /**
         * returns offset in hours for specific TimeZone.
         * @param d Date object to get TimeZone offset for.
         * @return offset in hours from UTC.
         */
        float getOffset(Date d); //offset in hours from UTC
}
