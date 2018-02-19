package util;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TimeFormat
{
  public TimeFormat()
  {
  }
  public static String formatDuration(long timeInSecond)
  {
//    if(true)      return Double.toString(timeInSecond /60.0);
    String duration = null;
    long minute = timeInSecond /60;
    long second = timeInSecond%60;
    String min = Long.toString(minute);
    String sec = Long.toString(second);

    if( second < 10)
    {
      sec = '0' + sec;
    }

    duration = min+":"+sec;
    return duration;
  }


}
