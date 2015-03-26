package net.sfka.sac.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class TimeUtils {

	public static String timeToDateString(long time) {
		Date date = new Date(time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int m = cal.get(Calendar.MONTH) + 1;
		String ms = String.valueOf(m);
		if (m < 10)
			ms = "0" + ms;
		int d = cal.get(Calendar.DAY_OF_MONTH);
		String days = String.valueOf(d);
		if (d < 10)
			days = "0" + days;
		int min = cal.get(Calendar.MINUTE);
		String minutes = String.valueOf(min);
		if (min < 10)
			minutes = "0" + minutes;
		String ds = days + "-" + ms + "-" + String.valueOf(cal.get(Calendar.YEAR)) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + minutes;
		return ds;
	}

	public static String getMonth(int m) {
		switch (m) {
		case 1:
			return "Jan";
		case 2:
			return "Feb";
		case 3:
			return "Mar";
		case 4:
			return "Apr";
		case 5:
			return "May";
		case 6:
			return "Jun";
		case 7:
			return "Jul";
		case 8:
			return "Aug";
		case 9:
			return "Sep";
		case 10:
			return "Oct";
		case 11:
			return "Nov";
		case 12:
			return "Dec";
		default:
			break;
		}
		return String.valueOf(m);
	}

	public static Properties timeToDate(long time) {
		Properties p = new Properties();
		Date date = new Date(time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int m = cal.get(Calendar.MONTH) + 1;
		String ms = String.valueOf(m);
		if (m < 10)
			ms = "0" + ms;
		int d = cal.get(Calendar.DAY_OF_MONTH);
		String days = String.valueOf(d);
		if (d < 10)
			days = "0" + days;
		int h = cal.get(Calendar.HOUR_OF_DAY);
		String hour = String.valueOf(h);
		if (h < 10) {
			hour = "0" + hour;
		}
		int min = cal.get(Calendar.MINUTE);
		String minutes = String.valueOf(min);
		if (min < 10)
			minutes = "0" + minutes;
		int sec = cal.get(Calendar.SECOND);
		String seconds = String.valueOf(sec);
		if (sec < 10)
			seconds = "0" + seconds;
		p.setProperty("year", String.valueOf(cal.get(Calendar.YEAR)));
		p.setProperty("month", ms);
		p.setProperty("day", days);
		p.setProperty("hour", hour);
		p.setProperty("minute", minutes);
		p.setProperty("second", seconds);

		return p;
	}

}
