package functions;

import java.text.SimpleDateFormat;
import java.util.Date;
import controls.Controls_Exceptions;

/*
 * DATE: 14/11/2020;
 * AUTHOR: JACK92;
 * DESCRIPTION: This class contains functions that are used to retrieve information
 * on the current timestamp;
 */
public class Functions_Timestamp {

	private final static String AZP_className = "Functions_Timestamp";
	private final static long oneDayInMs = 24*60*60*1000;
	private final static long oneMinuteInMs = 60*1000;
	private final static long oneHourInMs = 60*60*1000;
	
	/**
	 * This function is used to get the date time in the FORMAT yyyy-MM-dd_HH.mm.ss.SSS
	 */
	public static String getTimeNow(){

		try{
			SimpleDateFormat time_formatter_V1 = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss.SSS");
			return time_formatter_V1.format(System.currentTimeMillis());
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getTimeNow");
			return "ERROR";
		}
	}

	/**
	 * This function is used to parse milliseconds in date time in the FORMAT yyyy-MM-dd_HH.mm.ss.SSS
	 */
	public static String getTime(Long milliseconds){
		try{
			SimpleDateFormat time_formatter_V1 = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss.SSS");
			return time_formatter_V1.format(milliseconds);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getTime");
			return "ERROR";
		}
	}//end of method getTime(...)

	/**
	 * This function is used to parse milliseconds in date time in the FORMAT yyyy-MM-dd_HH.mm.ss
	 */
	public static String getTime1(Long milliseconds){
		try{
			SimpleDateFormat time_formatter_V2 = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
			return time_formatter_V2.format(milliseconds);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getTime");
			return "ERROR";
		}
	}//end of method getTime(...)

	/**
	 * This function is used to get the date time in the FORMAT yyyy-MM-dd_HH.mm
	 */
	public static String getTimeNow1(){
		try{
			SimpleDateFormat time_formatter_V3 = new SimpleDateFormat("yyyy-MM-dd_HH.mm");
			return time_formatter_V3.format(System.currentTimeMillis());
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getTimeNow1");
			return "ERROR";
		}
	}

	/**
	 * This function is used to get the date time in the FORMAT yyyy-MM-dd
	 */
	public static String getTimeNow2(){
		try{
			SimpleDateFormat time_formatter_V4 = new SimpleDateFormat("yyyy-MM-dd");
			return time_formatter_V4.format(System.currentTimeMillis());
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getTimeNow2");
			return "ERROR";
		}
	}

	/**
	 * This function is used to get the date time in the FORMAT yyyy-MM-dd_HH.mm.ss
	 */
	public static String getTimeNow3(){
		try{
			SimpleDateFormat time_formatter_V2 = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
			return time_formatter_V2.format(System.currentTimeMillis());
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getTimeNow3");
			return "ERROR";
		}
	}//end of method getTimeNow3(...)

	/**
	 * This function is used to get the current date time in the FORMAT ddMMyyyy
	 * minus X millisecond.
	 */
	public static String getTimeNow4_minusXmillisec(Long x){

		//DECLARE variables
		Long result = null;

		try{
			SimpleDateFormat time_formatter_V5 = new SimpleDateFormat("ddMMyyyy");
			result = System.currentTimeMillis() - x;
			return time_formatter_V5.format(result);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getTimeNow4_minusXmillisec");
			return "ERROR";
		}
	}//end of method getTimeNow4_minusXmillisec(...)

	/**
	 * This function is used to get the current date time in the FORMAT yyyy-MM-dd
	 * minus X millisecond.
	 */
	public static String getTimeNow5_plusXmillisec(Long x){

		//DECLARE variables
		Long result = null;

		try{
			SimpleDateFormat time_formatter_V4 = new SimpleDateFormat("yyyy-MM-dd");
			result = System.currentTimeMillis() + x;
			return time_formatter_V4.format(result);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getTimeNow5_minusXmillisec");
			return "ERROR";
		}
	}//end of method getTimeNow5_minusXmillisec(...)
	
	/**
	 * This function is used to get the current date time in the FORMAT yyyy-MM-dd
	 * plus X millisecond.
	 */
	public static String getDate_plusXDays(final String date, int days){

		//DECLARE variables
		Long result = null;
		Date d = null;

		try{
			SimpleDateFormat time_formatter_V4 = new SimpleDateFormat("yyyy-MM-dd");
			d = time_formatter_V4.parse(date);
			result = d.getTime() + days*oneDayInMs;
			return time_formatter_V4.format(result);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getDate_plusXDays");
			return "ERROR";
		}
	}//end of method getDate_plusXDays(...)
	
	/**
	 * This function is used to compute the 
	 * input date +  plus X millisecond.
	 * If the obtained day is equal to Saturday or Sunday, the next
	 * useful working day is computed.
	 * The used FORMAT is yyyy-MM-dd.
	 */
	public static String getDate_plusXDays_only_working_days(final String date, int days){

		//DECLARE variables
		Long result = null;
		Date d = null;

		try{
			SimpleDateFormat time_formatter_V4 = new SimpleDateFormat("yyyy-MM-dd");
			d = time_formatter_V4.parse(date);
			result = d.getTime() + days*oneDayInMs;
						
			if(getDayOfTheWeek(result).equals("sabato")){
				result = result + 2*oneDayInMs;
			}else if(getDayOfTheWeek(result).equals("domenica")){
				result = result + 1*oneDayInMs;
			}
			
			return time_formatter_V4.format(result);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getDate_plusXDays_only_working_days");
			return "ERROR";
		}
	}//end of method getDate_plusXDays_only_working_days(...)
	
	/**
	 * This function is used to get the current date time in the FORMAT yyyy-MM-dd_HH.mm
	 * minus X millisecond.
	 */
	public static String getDate_minusXDays(final String date, int days){

		//DECLARE variables
		Long result = null;
		Date d = null;

		try{
			SimpleDateFormat time_formatter_V4 = new SimpleDateFormat("yyyy-MM-dd_HH.mm");
			d = time_formatter_V4.parse(date);
			result = d.getTime() - days*oneDayInMs;
			return time_formatter_V4.format(result);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getDate_plusXDays");
			return "ERROR";
		}
	}//end of method getDate_minusXDays(...)

	/**
	 * This function is used to get the a validityDate that will be used
	 * to submit a transaction order. The used FORMAT is yyyy-MM-dd;
	 */
	public static String getValidityDate(){

		//DECLARE variables
		Long result = null;
		String methodName = null;

		try{
			SimpleDateFormat time_formatter_V4 = new SimpleDateFormat("yyyy-MM-dd");
			methodName = "getValidityDate";
			result = System.currentTimeMillis();

			if(getDayOfTheWeek(result).equals("venerdì")){

				result = result+3*oneDayInMs;

			}else if(Integer.parseInt(getHours()) > 16){
				result = result + oneDayInMs;

				if(getDayOfTheWeek(result).equals("venerdì")){
					result = result+3*oneDayInMs;
				}else if(getDayOfTheWeek(result).equals("giovedì")){
					result = result+4*oneDayInMs;
				}

			}//end if(Integer.parseInt(getHours()) > 16)

			return time_formatter_V4.format(result);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, methodName);
			return "ERROR";
		}
	}//end of method getValidityDate(...)

	/*
	 * This function is used to retrieve the day of the week;
	 */
	public static String getDayOfTheWeek(){
		try{			
			SimpleDateFormat time_formatter_V51 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat time_formatter_V6 = new SimpleDateFormat("EEEE");

			Date dt1=time_formatter_V51.parse(time_formatter_V51.format(System.currentTimeMillis()));
			return time_formatter_V6.format(dt1);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getDayOfTheWeek");
			return "ERROR";
		}
	}

	/*
	 * This function is used to retrieve the 
	 * day of the week of the date passed as input;
	 */
	public static String getDayOfTheWeek(Long date){
		try{			

			SimpleDateFormat time_formatter_V51 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat time_formatter_V6 = new SimpleDateFormat("EEEE");

			Date dt1=time_formatter_V51.parse(time_formatter_V51.format(date));
			return time_formatter_V6.format(dt1);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getDayOfTheWeek");
			return "ERROR";
		}
	}//end of method getDayOfTheWeek(...)
	
	/*
	 * This function is used to retrieve the 
	 * day of the week of the date passed as input;
	 */
	public static String getDayOfTheWeek(final String date){
		try{			

			SimpleDateFormat time_formatter_V4 = new SimpleDateFormat("yyyy-MM-dd_HH.mm");
			SimpleDateFormat time_formatter_V6 = new SimpleDateFormat("EEEE");
			Date dt1=time_formatter_V4.parse(date);
			
			return time_formatter_V6.format(dt1);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getDayOfTheWeek");
			return "ERROR";
		}
	}//end of method getDayOfTheWeek(...)

	/*
	 * This function is used to retrieve the hours
	 */
	public static String getHours(){
		try{			
			SimpleDateFormat time_formatter_V7 = new SimpleDateFormat("HH");
			return time_formatter_V7.format(System.currentTimeMillis());
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getHours");
			return "ERROR";
		}
	}

	/**
	 * This function is used to retrieve the hours
	 * from the date in format 2020-04-21_12.44
	 */
	public static String getHours(final String date){
		try{			

			return date.substring(date.indexOf("_")+1, date.indexOf("."));
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getHours");
			return "ERROR";
		}
	}

	/**
	 * This function is used to retrieve the date only
	 * from the date in format 2020-04-21_12.44;
	 * Input 2020-04-21_12.44;
	 * Output 2020-04-21;
	 */
	public static String getDate(final String date){
		try{			

			return date.substring(0, date.indexOf("_"));
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getDate");
			return "ERROR";
		}
	}

	/**
	 * This function is used to retrieve the minutes
	 * from the date in format 2020-04-21_12.44
	 */
	public static String getMinutes(final String date){
		try{			

			return date.substring(date.indexOf(".")+1);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getMinutes");
			return "ERROR";
		}
	}

	/**
	 * This function is used to retrieve the minutes;
	 */
	public static String getMinutes(){
		try{			
			SimpleDateFormat time_formatter_V8 = new SimpleDateFormat("mm");
			return time_formatter_V8.format(System.currentTimeMillis());
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getMinutes");
			return "ERROR";
		}
	}

	/**
	 * This function is used to retrieve the seconds;
	 */
	public static String getSeconds(){
		try{			
			SimpleDateFormat time_formatter_V9 = new SimpleDateFormat("ss");
			return time_formatter_V9.format(System.currentTimeMillis());
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getSeconds");
			return "ERROR";
		}
	}

	/**
	 * This function is used to retrieve the current year
	 */
	public static String getYear(){
		try{			
			SimpleDateFormat time_formatter_V10 = new SimpleDateFormat("yyyy");
			return time_formatter_V10.format(System.currentTimeMillis());
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getYear","retrieve the current year");
			return "ERROR";
		}
	}

	/**
	 * This function is used to retrieve the year of the date in input
	 * The used format is yyyy-MM-dd
	 */
	public static String getYear(final String date){
		try{			
			return date.substring(0,date.indexOf("-"));
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getYear", "retrieve the year of the dat in input");
			return "ERROR";
		}
	}

	/**
	 * This function is used to retrieve the current month
	 */
	public static String getMonth(){
		try{			
			SimpleDateFormat time_formatter_V11 = new SimpleDateFormat("MM");
			return time_formatter_V11.format(System.currentTimeMillis());
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getMonth", "retrieve the current month");
			return "ERROR";
		}
	}

	/**
	 * This function is used to retrieve the month of the date in input
	 * The used format is yyyy-MM-dd
	 */
	public static String getMonth(final String date){
		try{			
			String temp = date.substring(date.indexOf("-")+1);
			return temp.substring(0, temp.indexOf("-"));
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getMonth", "retrieve the month of the date in input");
			return "ERROR";
		}
	}

	/**
	 * This function is used to retrieve the current day
	 */
	public static String getDay(){
		try{			
			SimpleDateFormat time_formatter_V12 = new SimpleDateFormat("dd");
			return time_formatter_V12.format(System.currentTimeMillis());
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getDay", "retrieve the current day");
			return "ERROR";
		}
	}

	/**
	 * This function is used to retrieve the day of the date in input
	 * The used format is yyyy-MM-dd
	 */
	public static String getDay(final String date){
		try{			
			String temp = date.substring(date.indexOf("-")+1);
			return temp.substring(temp.indexOf("-")+1);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getDay", "retrieve the day of the date in input");
			return "ERROR";
		}
	}

	/**
	 * This function is used to retrieve the yyyy-MM-dd of the date in input
	 * that is yyyy-MM-dd_HH_00.00
	 */
	public static String removeTime(final String date){
		try{			
			return date.substring(0,date.indexOf("_"));
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "removeTime", "remove the time part of the date in input");
			return "ERROR";
		}
	}

	/**
	 * This function is used to retrieve System.currentTimeMillis() in seconds
	 */
	public static long getCurrentTimeMillisInSeconds(){
		try{			
			long total_seconds = 0;
			total_seconds = System.currentTimeMillis()/1000;
			return total_seconds;
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getCurrentTimeMillisInSeconds");
			return -1;
		}
	}

	/**
	 * This function is used to format a string FROM 2019-02-25_09.01.06.312 TO 2019-02-25_09.01
	 */
	public static String formatDateRemoveFromSecondsV1(final String date){
		try{
			Date d = null;
			SimpleDateFormat time_formatter_V1 = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss.SSS");
			SimpleDateFormat time_formatter_V3 = new SimpleDateFormat("yyyy-MM-dd_HH.mm");
			d = time_formatter_V1.parse(date);
			//in milliseconds
			long diff = d.getTime();
			return time_formatter_V3.format(diff);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "formatDateRemoveFromSeconds");
			return "ERROR";
		}
	}

	/**
	 * This function is used to format
	 * a string FROM 2019-02-25_09.01.06 TO 2019-02-25_09.01
	 */
	public static String formatDateRemoveFromSecondsV2(final String date){
		try{
			Date d = null;
			SimpleDateFormat time_formatter_V2 = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
			SimpleDateFormat time_formatter_V3 = new SimpleDateFormat("yyyy-MM-dd_HH.mm");
			d = time_formatter_V2.parse(date);
			//in milliseconds
			long diff = d.getTime();
			return time_formatter_V3.format(diff);
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "formatDateRemoveFromSecondsV2");
			return "ERROR";
		}
	}

	/**
	 * This function is used to return the next USEFUL date, given a date;
	 * The next USEFUL date is intended to be a working day from Monday to Friday;
	 * If the given date is a Friday, the next USEFUL date will be the Monday, so the Saturday and
	 * Sunday will be skipped;
	 * The input date format is: yyyy-MM-dd_HH.mm
	 * The output date format is: yyyy-MM-dd
	 */
	public static String getNextUsefulDay(final String date){
		try{

			String nextDate = null;

			String dayOfTheWeek = null;

			Date d = null;

			SimpleDateFormat time_formatter_V3 = new SimpleDateFormat("yyyy-MM-dd_HH.mm");
			SimpleDateFormat time_formatter_V6 = new SimpleDateFormat("EEEE");
			SimpleDateFormat time_formatter_V4 = new SimpleDateFormat("yyyy-MM-dd");

			d = time_formatter_V3.parse(date);

			//in milliseconds
			long diff = d.getTime();

			//This in one day expressed in milliseconds
			long day = 86400000;

			diff = diff+day;

			nextDate = time_formatter_V3.format(diff);

			d = time_formatter_V3.parse(nextDate);

			dayOfTheWeek = time_formatter_V6.format(d);

			if(dayOfTheWeek.equals("sabato")){
				diff = diff+day+day;
			}else if(dayOfTheWeek.equals("domenica")){
				diff = diff+day;
			}			
			return time_formatter_V4.format(diff);

		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "getNextUsefulDay");
			return "ERROR";
		}
	}

	/**
	 * This function is used to compare date;
	 * The format is: yyyy-MM-dd
	 * The function returns TRUE if the date in input is less or equal than today;
	 * The function returns FALSE if the date in input is greater than today;
	 */
	public static boolean isDateLessThanNow(final String date){
		try{

			SimpleDateFormat time_formatter_V4 = new SimpleDateFormat("yyyy-MM-dd");
			Date inputDate = time_formatter_V4.parse(date);
			Date today = time_formatter_V4.parse(time_formatter_V4.format(System.currentTimeMillis()));

			if (inputDate.compareTo(today) <= 0) {
				return true;
			} else if (inputDate.compareTo(today) > 0) {
				return false;
			}
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "formatDateRemoveFromSeconds");

		}

		return false;

	}

	/**
	 * This function is used to compare dates in input;
	 * The format is: yyyy-MM-dd
	 * The function returns FALSE if the date1 in input is less or equal than date2;
	 * The function returns TRUE if the date1 in input is greater than date2;
	 */
	public static boolean compareDates(final String date1, final String date2){
		try{

			SimpleDateFormat time_formatter_V4 = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = time_formatter_V4.parse(date1);
			Date d2 = time_formatter_V4.parse(date2);

			if (d1.compareTo(d2) <= 0) {
				return false;
			} else if (d1.compareTo(d2) > 0) {
				return true;
			}
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "compareDates");
		}
		return false;
	}

	/**
	 * This function is used to calculate the difference in absolute value in days between two dates;
	 * The format is: yyyy-MM-dd_HH.mm
	 * The function returns 1 if the dates distance is greater than offset;
	 * The function returns 2 if the dates distance is less or equal than the offset;
	 * The function returns 0 if an error occurs;
	 */
	public static byte checkDaysOffset(final String date1, final String date2, int offset){

		if(date1 == null || date2 == null || date1.equals("") || date2.equals(""))
			return 0;
		
		SimpleDateFormat time_formatter_V3 = new SimpleDateFormat("yyyy-MM-dd_HH.mm");

		try{

			Date date_d1 = null;
			Date date_d2 = null;

			long diff = 0;
			long computed_offset = 0;

			/*
			date_d1 = Controls_Timestamp.getTimeFormatterV3().parse(date1);
			date_d2 = Controls_Timestamp.getTimeFormatterV3().parse(date2);
			 */

			date_d1 = time_formatter_V3.parse(date1);
			date_d2 = time_formatter_V3.parse(date2);

			//in milliseconds
			long long_d1 = date_d1.getTime();
			long long_d2 = date_d2.getTime();

			diff = long_d2-long_d1;

			if(diff < 0){
				diff = diff*(-1);
			}

			//long diffDays = diff / (24 * 60 * 60 * 1000);
			computed_offset = oneDayInMs*offset;

			if (diff > computed_offset) {
				return 1;
			} else {
				return 2;
			}
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "checkDaysOffset");
			return 0;
		}
		
	}//end of method checkDaysOffset(...)

	/**
	 * REQUIRES: 
	 * - All parameters not null;
	 * MODIFIES: 
	 * EFFECTS:
	 * This function is used to calculate the difference in absolute value in days between two dates;
	 * The input format is: yyyy-MM-dd_HH.mm
	 * - It returns 1 if it is executed correctly;
	 * - It returns 0 if an error occurs;  
	 */
	public static short compute_distance_between_dates_in_days(final String date1, final String date2)throws Exception{

		//DECLARE variables
		String methodName = null;
		Date date_d1 = null;
		Date date_d2 = null;
		SimpleDateFormat time_formatter_V3 = null;	

		try{

			//INITIALIZE variables
			methodName = "compute_distance_between_dates_in_days";
			time_formatter_V3 = new SimpleDateFormat("yyyy-MM-dd_HH.mm");
			long diff = 0;

			date_d1 = time_formatter_V3.parse(date1);
			date_d2 = time_formatter_V3.parse(date2);

			//in milliseconds
			long long_d1 = date_d1.getTime();
			long long_d2 = date_d2.getTime();

			diff = long_d2-long_d1;

			if(diff < 0){
				diff = diff*(-1);
			}

			return (short) (diff/oneDayInMs);

		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, methodName);
			throw e;
		}

	}//end of method compute_distance_between_dates_in_days(...)

	/**
	 * This function is used to calculate the difference in absolute value in minutes between two dates;
	 * The format is: "yyyy-MM-dd_HH.mm"
	 * The function returns TRUE if the difference in minutes is greater than the offset;
	 * The function returns FALSE if the difference in minutes is less or equal than the offset;
	 */
	public static boolean checkMinutesOffset(final String date1, final String date2, int offset){
		try{

			Date date_d1 = null;
			Date date_d2 = null;

			long diff = 0;

			SimpleDateFormat time_formatter_V3 = new SimpleDateFormat("yyyy-MM-dd_HH.mm");

			date_d1 = time_formatter_V3.parse(date1);
			date_d2 = time_formatter_V3.parse(date2);

			//in milliseconds
			long long_d1 = date_d1.getTime();
			long long_d2 = date_d2.getTime();

			diff = long_d2-long_d1;

			if(diff < 0){
				diff = diff*(-1);
			}

			//long diffMinutes = diff / (60 * 1000);

			if (diff > oneMinuteInMs*offset) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, "checkMinutesOffset");
		}

		return false;
	}

	/**
	 * This function is used to calculate the difference in absolute value in hours between two dates;
	 * The format is: "yyyy-MM-dd_HH.mm"
	 * The function returns TRUE if the difference in hours is greater than the offset;
	 * The function returns FALSE if the difference in hours is less or equal than the offset;
	 */
	public static boolean checkHoursOffset(final String date1, final String date2, int offset){

		//DECLARE variables
		String methodName = null;

		try{

			//INITIALIZE variables
			methodName = "checkHoursOffset";

			Date date_d1 = null;
			Date date_d2 = null;

			long diff = 0;

			SimpleDateFormat time_formatter_V3 = new SimpleDateFormat("yyyy-MM-dd_HH.mm");

			date_d1 = time_formatter_V3.parse(date1);
			date_d2 = time_formatter_V3.parse(date2);

			//in milliseconds
			long long_d1 = date_d1.getTime();
			long long_d2 = date_d2.getTime();

			diff = long_d2-long_d1;

			if(diff < 0){
				diff = diff*(-1);
			}

			//long diffMinutes = diff / (60 * 1000);

			if (diff > oneHourInMs*offset) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, methodName);
		}

		return false;

	}//end of method checkHoursOffset(...)

	/**
	 * This function is used to verify if the to input dates have different day;
	 * The format is: "yyyy-MM-dd_HH.mm"
	 * The function returns 1 if the days are different;
	 * The function returns 2 if the days are the same;
	 * The function returns 0 if an error occurs;
	 */
	public static int check_different_day(final String date1, final String date2){

		//DECLARE variables
		String methodName = null;

		try{

			//INITIALIZE variables
			methodName = "check_different_day";
			
			if(date1 == null || date2 == null || date1.length()<2 || date2.length()<2){
				Controls_Exceptions.report(null, AZP_className, methodName,
						"The format of inputs date is not correct date1:"+date1+" date2:"+date2);
				return 0;
			}

			if(getDate(date1).equals(getDate(date2)) == true){
				return 2;
			}else{
				return 1;
			}

			/*
			if(getDay(getDate(date1)).equals(getDay(getDate(date2))) == false){
				return 2;
			}else{

				if(getMonth(getDate(date1)).equals(getMonth(getDate(date2))) == false){
					return 2;
				}else{

					if(getYear(getDate(date1)).equals(getYear(getDate(date2))) == false){
						return 2;
					}else{
						return 1;
					}//same year

				}//same month

			}//same day		
			 */	

		} catch (Exception e) {
			Controls_Exceptions.report(e, AZP_className, methodName);
			return 0;
		}

	}//end of method check_different_day(...)

	/**
	 * This function is used to select a portion of a dataset;
	 * Given in input the String from, that is a date and the Integer next, that represents a number of
	 * days; For each String date given in input this method it will return TRUE if the String date is between
	 * String from and String form + Integer next;
	 * The @param boolean central_step variable is used in order to optimize the comparison of many dates.
	 * So this method must be used in a specific context, otherwise will not produce a good result;
	 * If @param boolean central_step == false then the comparison will be @param (String date >= String from);
	 * If @param boolean central_step == true then the comparison will be @param (String date <= String from + Integer next);
	 */
	public static boolean selectInterval_FROM_NEXT(final String from, Integer next, boolean central_step, final String date){
		try{
			//DECLARE variables
			Date date_d1 = null;
			Date date_d2 = null;

			//in milliseconds
			Long long_d1 = null;
			Long long_d2 = null;
			Long long_days = null;

			//result
			boolean result = false;

			SimpleDateFormat time_formatter_V3 = new SimpleDateFormat("yyyy-MM-dd_HH.mm");

			//MAIN work

			date_d1 = time_formatter_V3.parse(from);
			date_d2 = time_formatter_V3.parse(date);

			long_d1 = date_d1.getTime();
			long_d2 = date_d2.getTime();

			if(central_step == false){
				//central step == false -> search when "date" >= "from"
				if(long_d2 >= long_d1 == true){
					result = true;
				}else{
					result = false;
				}

			}else{
				//central step == true -> search when "date" <= "from"+"next"
				long_days = new Long("86400000"); //is 86400000 that is equal to 24*60*60*1000;
				long_days = long_days*next;
				if(long_d2 <= (long_d1+long_days) == true){
					result = true;
				}else{
					result = false;
				}
			}

			//FREE variables
			date_d1 = null;
			date_d2 = null;
			long_d1 = null;
			long_d2 = null;
			long_days = null;

			return result;

		}catch(Exception e){
			Controls_Exceptions.report(e, AZP_className, "selectInterval_FROM_NEXT");
			return false;
		}
	}

	/**
	 * This function is used to select a portion of a dataset;
	 * Given in input the String from, that is a date and the Integer back, that represents a number of
	 * days; For each String date given in input this method it will return TRUE if the String date is between
	 * String from - Integer back and String form;
	 * The @param boolean central_step variable is used in order to optimize the comparison of many dates.
	 * So this method must be used in a specific context, otherwise will not produce a good result;
	 * If @param boolean central_step == false then the comparison will be @param (String date >= String from-Integer back);
	 * If @param boolean central_step == true then the comparison will be @param (String date <= String from);
	 */
	public static boolean selectInterval_BACK_FROM(final String from, Integer back, boolean central_step, final String date){
		try{
			//DECLARE variables
			Date date_d1 = null;
			Date date_d2 = null;

			//in milliseconds
			Long long_d1 = null;
			Long long_d2 = null;
			Long long_days = null;

			//result
			boolean result = false;

			SimpleDateFormat time_formatter_V3 = new SimpleDateFormat("yyyy-MM-dd_HH.mm");

			//MAIN work

			date_d1 = time_formatter_V3.parse(from);
			date_d2 = time_formatter_V3.parse(date);

			long_d1 = date_d1.getTime();
			long_d2 = date_d2.getTime();

			if(central_step == false){
				//central step == false -> search when "date" >= "from"-"back"
				long_days = new Long("86400000"); //is 86400000 that is equal to 24*60*60*1000;
				long_days = long_days*back;
				if(long_d2 >= (long_d1 - long_days)== true){
					result = true;
				}else{
					result = false;
				}

			}else{
				//central step == true -> search when "date" <= "from"
				if(long_d2 <= long_d1 == true){
					result = true;
				}else{
					result = false;
				}
			}

			//FREE variables
			date_d1 = null;
			date_d2 = null;
			long_d1 = null;
			long_d2 = null;
			long_days = null;

			return result;

		}catch(Exception e){
			Controls_Exceptions.report(e, AZP_className, "selectInterval_BACK_FROM");
			return false;
		}
	}//end of method selectInterval_BACK_FROM(...)

	/**
	 * REQUIRES: 
	 * - All parameters not null;
	 * MODIFIES: 
	 * EFFECTS:
	 * - It returns 1 if the current time is >= 8 a.m. and < 21 p.m.;
	 * - It returns 2 if the current time is < 8 a.m. or >= 21 p.m.; 
	 * - It returns 0 if an error occurs; 
	 */
	public static int work_time(){

		//DECLARE variables
		String methodName = null;
		Integer hours = null;

		try{
			//INITIALIZE variables
			methodName = "work_time";

			hours = Integer.parseInt(Functions_Timestamp.getHours());

			if(hours.intValue() != 17 && hours.intValue() != 9){

				if(hours >= 8 && hours < 21){
					return 1;
				}else{
					return 2;
				}


			}else{
				return 2;
			}

			//END

		}//end of try block
		catch(Exception e){

			Controls_Exceptions.report(e, AZP_className, methodName);
			return 0;		

		}//end of catch block

	}//end of method work_time(...)

	/**
	 * This function is used to compute the sleep_time;
	 * The main idea is to sleep a time X necessary 
	 * to arrive at HH:00 or HH:30;
	 */
	public static Integer computeSleepTime(){

		//DECLARE variables
		Integer result = null;

		try{

			result = Integer.parseInt(Functions_Timestamp.getMinutes());

			if(result < 30){
				return (30-result)*60*1000;
			}else{
				return (60-result)*60*1000;
			}			

		}catch(Exception e){
			Controls_Exceptions.report(e, AZP_className, "computeSleepTime");
			return 0;
		}

	}//end of method computeSleepTime(..)

}//end of class Functions_Timestamp