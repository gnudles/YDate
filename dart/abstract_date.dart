library ydate;

enum WeekDay {
  /// Sunday is named after the Sun - Sunne in old english
  SUNDAY, 
  /// Monday is named after the Moon - Mōna in old english
  MONDAY,
  /// Tuesday is named after Mars - Tīw in old english
  TUESDAY,
  /// Wednesday is named after Mercury - Wōden in old english
  WEDNESDAY,
  /// Thursday is named after Jupiter - Þunor in old english
  THURSDAY,
  /// Friday is named after Venus - frig in old english
  FRIDAY,
  /// Saturday is named after Saturn - Sætern in old english
  SATURDAY
}
class DateSyncGroup
{
  List<ADate> dateSystems = new List();
  static const int INVALID_BOUND = -1;
    /*
     * common lower bound in GDN.
     */
     int _lowerCommonBound = INVALID_BOUND;
    /*
     * common upper bound in GDN.
     */
     int _upperCommonBound = INVALID_BOUND;
     EventHandler _dateChanged = new EventHandler();
    /*
     * Register a listner for a date change event.
     * The listner will recieve as a sender the date object that triggered the sync.
     * @param listener 
     */
    void registerOnDateChanged(Function callback) {
        _dateChanged.callbacks.add(callback);
    }

    void _notifyDateChanged(ADate date) {
        _dateChanged.trigger(date);
    }
    void add(ADate dateObject)
    {
        dateObject.setSyncGroup(this);
        dateSystems.add(dateObject);
        if (_lowerCommonBound==INVALID_BOUND || _lowerCommonBound<dateObject.lowerBound())
            _lowerCommonBound=dateObject.lowerBound();
        if (_upperCommonBound==INVALID_BOUND || _upperCommonBound>dateObject.upperBound())
            _upperCommonBound=dateObject.upperBound();
    }
    int _clippedGDN(int gdn)
    {
        if (gdn<_lowerCommonBound)
            return _lowerCommonBound;
        if (gdn>=_upperCommonBound)
            return _upperCommonBound-1;
        return gdn;
    }
  bool syncBy(ADate d){
    return true;
  }
}

class EventHandler
{
  List<Function> callbacks = new List();
  void trigger(Object sender)
  {
    callbacks.forEach((cb) => cb(sender));
  }
}
/// Abstract date class
abstract class ADate {

/// The Unix Epoch date (1.1.1970) - in GDN (Genesis Day Number).
  static const int EPOCH_DAY = 2092591; 
// The offset between GDN (Genesis Day Number) and JDN (Julian Day Number).
  static const int JULIAN_DAY_OFFSET = 347997; 

     DateSyncGroup _syncGroup;
     final EventHandler _dateChanged = new EventHandler();
     bool _muteTriggers = false;
    /// Sets the date by GDN
    /// 
    /// GDN stands for Genesis Day Number. see [GDN()]
    /// The function will return whether the value is in the valid range of the dating system.
     bool setByGDN(int gdn);
     /// Skips to other date by days [offset]
     bool seekBy(int offset) {
        return setByGDN(GDN()+offset);
    }
    /// Returns the GDN of the Date
    /// 
    /// GDN stands for Genesis Day Number. It is an ordinal day count from the estimated sunday of genesis.
    /// this count starts from zero, so day 0 was the first day (sunday).
      int GDN();

    /// Return the upper limit of the dating system in GDN.
    /// 
    /// 
     /* The upper bound of possible valid range.
     * The lower bound is in bounds, but the upper bound is out of the bounds.
     * @return the GDN of the upper bound.
     * related method: {@link #lowerBound}
     * for more information about GDN see {@link kapandaria.YDate.ADate#GDN}
     */
      int upperBound();
    /*
     * The lower bound of possible valid range.
     * The lower bound is in bounds, but the upper bound is out of the bounds.
     * @return the GDN of the lower bound.
     * related method: {@link #upperBound}
     * for more information about GDN see {@link kapandaria.YDate.ADate#GDN}
     */
      int lowerBound();
      bool isValid();
    /*
     * Register a listner for a date change event.
     * The listner will recieve as a sender the date object.
     * @param listener 
     */
     void registerOnDateChanged(Function listener) {
        _dateChanged.callbacks.add(listener);
    }
    /*
     * Method to be called on each change, in order to update the sync group.
     * The state might change again if clipping occurs. 
     * If there is no sync group, clipping will be still active.
     * @return true if no clipping occured and the date is valid. false otherwise.
     */
     bool stateChanged()
    {
        bool inBounds = checkBounds(GDN());
        if (!_muteTriggers)
        {
            if (_syncGroup!=null)
                return _syncGroup.syncBy(this);
            else
            {
                
                if (!inBounds)
                {
                    muteTriggers();
                    clip();
                    unmuteTriggers();
                }
                triggerEvents();
                
            }
        }
        return inBounds;
    }
    
    void muteTriggers()
    {
        _muteTriggers = true;
    }
    
    void unmuteTriggers()
    {
        _muteTriggers = false;
    }
    
    void triggerEvents()
    {
        _dateChanged.trigger(this);
    }
    
     void setSyncGroup(DateSyncGroup syncGroup)
    {
        _syncGroup = syncGroup;
    }
    /*
     * clip the date to fit in bound.
     * @return true if clipping occurs, otherwise false.
     */
     bool clip()
    {
        if (GDN() < lowerBound())
        {
            setByGDN(lowerBound());
            return true;
        }
        if (GDN() >= upperBound())
        {
            setByGDN(upperBound()-1);
            return true;
        }
        return false;
    }
     bool checkBounds(int gdn)
    {
        return (gdn >= lowerBound() && gdn < upperBound());
    }
    
    /*
     * Return the upcoming day in week, or the current day if it is that certain day in week.
     * 
     * @param diw day in week. in range 0..6
     * @param days day in "beginning count" or other count that day 0 is sunday
     * @return days + x (6 &gt; = x &gt; = 0). that gives that certain day in week. 
     */
    static int getNext(WeekDay diw, int gdn) // return the upcoming diw (or today if it's that diw)
    {
        int diff = (diw.index - gdn % 7 + 7) % 7;
        return (gdn + diff);
    }

   static int getPrevious(WeekDay diw, int gdn) {
        return getNext(diw, gdn - 6);
    }
    //days and hour in utc
   static DateTime toDateUtc(int days, double hour)
    {

        int millis = (days - EPOCH_DAY) * 3600 * 24 * 1000;
        millis += (hour * 3600 * 1000).toInt();
        return new DateTime.fromMillisecondsSinceEpoch(millis, isUtc:true);
    }

     static int JDNToGDN(double jd)  => (jd + 0.5001 - JULIAN_DAY_OFFSET).floor();

     static double GDNToJDN(int days) => days + JULIAN_DAY_OFFSET - 0.5;
/*
     static String getTimeString(Date d, TimeZone tz, boolean seconds) {
        Calendar cal = Calendar.getInstance(tz);
        cal.setTime(d);

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);
        if (seconds) {
            return Format.TimeString(hour, min, sec);
        }
        return Format.TimeString(hour, min);
    }
    */
  /// Returns [value] plus 1.
  //int addOne(WeekDay value) => value.index + WeekDay.values[1].index;
}

abstract class ADMYDate extends ADate
{
    int year();
    int month();
    int dayInMonth();
    int dayInYear();
    int yearLength();
    int monthLength();
    int previousMonthLength();
    int yearFirstDayGDN();
    int monthFirstDayGDN();
}