package com.itl.pns.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.itl.pns.constant.ApplicationConstants;

public class DateUtility {

	public String getLastDateOfMonth() {
		Date today = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		Date lastDayOfMonth = calendar.getTime();

		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String inputString = sdf.format(lastDayOfMonth);
		return inputString;
	}
	public String getCurrentDateTime() {
		DateFormat df = new SimpleDateFormat(ApplicationConstants.DATE_IN_FORMAT_YYYY_MM_DD_HH_MM_SS);
		Date dt = new Date();
		return df.format(dt);
	}
	 public Timestamp getDateInTimestamp(String timestamp2){
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date parsedDate;
		    Timestamp timestamp = null;
			try {
				 parsedDate = dateFormat.parse(timestamp2);
				 timestamp = new java.sql.Timestamp(parsedDate.getTime());
			} catch (ParseException e) {
				
				System.out.println(" Error while Converting String to Date");
			}
			return timestamp;
		    
	 }


}
