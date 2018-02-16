/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kapandaria.YDate;

/**
 * ADMYDate class is abstract class for date management. it is based on GDN (Genesis Day Number), 
 * and is suitable for dates based on Day, Month and Year.
 * @author Orr Dvori &lt;dvoreader@gmail.com&gt;
 * @version 4.0.10
 */
public abstract class ADMYDate extends ADate
{
    public abstract int year();
    public abstract int month();
    public abstract int dayInMonth();
    public abstract int dayInYear();
    public abstract int yearLength();
    public abstract int monthLength();
    public abstract int previousMonthLength();
    public abstract int yearFirstDayGDN();
    public abstract int monthFirstDayGDN();
    
}
