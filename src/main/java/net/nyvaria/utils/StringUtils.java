/**
 * Copyright (c) 2013-2014
 * Paul Thompson <captbunzo@gmail.com> / Nyvaria <geeks@nyvaria.net>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * 
 */
package net.nyvaria.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Paul Thompson
 *
 */
public class StringUtils {
	private static final String timeFormat         = "h:mm:ss a z";
	private static final String dateFormat         = "EEE, dd MMMMM yyyy";
	private static final String yearlessDateFormat = "EEE, dd MMMMM";
	
	public static String splitCamelCase(String text) {
	   return text.replaceAll(
	      String.format("%s|%s|%s",
	         "(?<=[A-Z])(?=[A-Z][a-z])",
	         "(?<=[^A-Z])(?=[A-Z])",
	         "(?<=[A-Za-z])(?=[^A-Za-z])"
	      ),
	      " "
	   );
	}
	
	public static String join(List<String> list, String separator) {
		StringBuilder result = new StringBuilder();
		int x = 0;
		
		for (String text : list) {
			if (++x > 1) result.append(separator);
			result.append(text);
		}
		
		return result.toString();
	}
	
	public static String join(List<String> list) {
		StringBuilder result = new StringBuilder();
		
		for (String text : list) {
			result.append(text);
		}
		
		return result.toString();
	}
	
	public static String getFormattedDate(Date date) {
		return StringUtils.getFormattedDate(date, null);
	}
	
	public static String getFormattedDate(Date date, String boringPrefix) {
		String format;
		
		// Turn the submitted date into a Calendar
		Calendar submitted = Calendar.getInstance();
		submitted.setTime(date);
		
		// Create Calendars for today and yesterday
		Calendar today     = Calendar.getInstance();
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_YEAR, -1);

		// First see if the submitted date is today
		
		if ( (submitted.get(Calendar.YEAR) == today.get(Calendar.YEAR)) && (submitted.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) ) {
			format = "'today'";
			
		} else if ( (submitted.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR)) && (submitted.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) ) {
			format = "'yesterday'";
		
		} else {
			format = (boringPrefix != null ? "'" + boringPrefix +"' " : "");
			
			if (submitted.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
				format += StringUtils.yearlessDateFormat;
			} else {
				format += StringUtils.dateFormat;
			}
		}
		
		// Tack on the time
		format += " 'at' " + StringUtils.timeFormat;
		
		// Some debugging
	    //NyvariaCore.instance.log(Level.FINER, "getFormattedDate: boring prefix  = " + boringPrefix);
	    //NyvariaCore.instance.log(Level.FINER, "getFormattedDate: derived format = " + format);
	    
		// Create the formatter and return the string
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    return sdf.format(date);
	}
	
	public static String getFormattedDate(long epoch) {
		return StringUtils.getFormattedDate(epoch, null);
	}

	public static String getFormattedDate(long epoch, String boringPrefix) {
		return StringUtils.getFormattedDate(new Date(epoch), boringPrefix);
	}
}
