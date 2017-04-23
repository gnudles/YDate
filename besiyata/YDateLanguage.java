
package besiyata.YDate;


public abstract class YDateLanguage
{
    static final int SUNDAY = 0;//Sun - Sunne in old english
    static final int MONDAY = 1;//Moon - M?na in old english
    static final int TUESDAY = 2;//Mars - T?w in old english
    static final int WEDNESDAY = 3;//Mercury - W?den in old english
    static final int THURSDAY = 4;//Jupiter - ?unor in old english
    static final int FRIDAY = 5;//Venus - frig in old english
    static final int SATURDAY = 6;//Saturn - S?tern in old english
    public abstract String getWeekToken(int token);
    public abstract String getHebMonthToken(int token);
    public abstract String getGregMonthToken(int token);
    public abstract String getEventToken(int token);
    public abstract String getZodiacToken(int token);
    public abstract String getNumber(int num);
}
