package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static long getStartOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
	
	public static long getEndOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
	
	public static long getStartOfMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
	
	public static long getCurrentTime(){
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis();
	}
	
	public static long getTime(String dateString)
	{
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Date date = null;
		try
		{
			date = (Date)formatter.parse(dateString);
			return  date.getTime();
		} 
		catch (ParseException e1) 
		{
			return -1;
		}
	}
	/**
	 * Form data string format: yyyy-MM-dd HH:mm
	 * @author muktadir
	 * @return java util date
	 * @throws ParseException 
	 */
	public static Date getDateFromFormString( String formDateString ) throws ParseException {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date date = null;
		
		date = (Date) formatter.parse( formDateString );
		
		return date;
			
		
	}
	
}
