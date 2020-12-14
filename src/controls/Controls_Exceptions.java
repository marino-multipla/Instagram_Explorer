package controls;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import functions.Functions_Timestamp;

/*
 * DATE: 14/12/2020;
 * AUTHOR: JACK92;
 * DESCRIPTION: This class contains functions that are used to
 * manage exceptions; 
 * This class belongs to Control Classes and it will use the getTimeNow from Functions_Timestamp class;
 * Only this function will be used; The main idea is to consider each Functions_XXX class independent from
 * each other; Functions_XXX class cannot use Functions_YYY class;
 * Only Procedure Classes and Control Classes can use functions that belongs to Functions package;
 */
public class Controls_Exceptions {

	/*
	 * This functions is used to report the details of an exception specifying CLASS, METHOD, DESCRIPTION
	 */
	public static void report(Exception e, final String class_name, final String method_name, final String description){
		String m = "ERROR CLASS:"+class_name+" METHOD:"+method_name+" DESCRIPTION:"+description;
		System.err.println(m);
		if(e != null)
			e.printStackTrace();
		appendToLogFile(e, class_name, m);
	}
	
	/*
	 * This functions is used to report the details of an exception specifying CLASS, METHOD, DESCRIPTION
	 */
	public static void report_important_event(Exception e, final String class_name, final String method_name, final String description){
		String m = "ERROR CLASS:"+class_name+" METHOD:"+method_name+" DESCRIPTION:"+description;
		System.err.println(m);
		if(e != null)
			e.printStackTrace();
		appendToLogFile(e, class_name, m);
	}//end of method report_important_event(...)

	/*
	 * This functions is used to report the details of an exception specifying CLASS, METHOD
	 */
	public static void report(Exception e, final String class_name, final String method_name){
		String m = "ERROR CLASS:"+class_name+" METHOD:"+method_name;
		System.err.println(m);
		if(e != null)
			e.printStackTrace();
		appendToLogFile(e, class_name, m);
	}	

	/**
	 * This function is used to save the exception details on a file
	 */
	private static void appendToLogFile(Exception e, final String class_name,
			final String custom_message){

		//DECALRE variables
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;
		File f_pathDirectory = null;
		String pathDirectory = null;

		try {

			pathDirectory = "Exceptions/"+class_name+"/";
						
			//CREATE Exceptions directory if does not exists
			f_pathDirectory = new File(pathDirectory);
			if(f_pathDirectory.exists() == false){
				f_pathDirectory.mkdirs();
			}				

			fw = new FileWriter(pathDirectory+"LOG"+Functions_Timestamp.getTimeNow()+".txt", true);
			bw = new BufferedWriter(fw);
			out = new PrintWriter(bw);
			out.println(custom_message);
			if(e != null)
				e.printStackTrace(out);
			out.close();
			bw.close();
			fw.close();

			//FREE variables
			fw = null;
			bw = null;
			out = null;
			f_pathDirectory = null;
			pathDirectory = null;

		} catch (Exception ex) {
			System.err.println("ERROR located in CLASS:Controls_Exceptions METHOD:appendToLogFile");
			ex.printStackTrace();
		}
		finally {
			try {
				if(out != null)
					out.close();
			} catch (Exception ex) {
				System.err.println("ERROR located in CLASS:Controls_Exceptions METHOD:appendToLogFile DESCRIPTION:closing out");
				ex.printStackTrace();
			}
			try {
				if(bw != null)
					bw.close();
			} catch (Exception ex) {
				System.err.println("ERROR located in CLASS:Controls_Exceptions METHOD:appendToLogFile DESCRIPTION:closing bw");
				ex.printStackTrace();
			}
			try {
				if(fw != null)
					fw.close();
			} catch (Exception ex) {
				System.err.println("ERROR located in CLASS:Controls_Exceptions METHOD:appendToLogFile DESCRIPTION:closing fw");
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * This functions is used to test the network connectivity
	 * of GOOGLE , so using https://www.google.it website;
	 * The catch block does not produce System.out values, because this function is used by FerrettiG 
	 * in order to test the connectivity;
	 * 
	 * The function returns TRUE if the connection is OK;
	 * The function returns FALSE if the connection has some problems;
	 */
	public static boolean testNetworkConnectivity_GOOGLE(){

		//DECLARE variables
		InputStream is = null;
		URL url = null;
		int retry = 0;

		try {
			//READ URL			
			url = new URL("https://www.google.it");

			while(retry < 4){

				try {
					is = url.openStream();
					break;
				} catch (Exception e) {
					retry++;
					Thread.sleep(3000);
				}

			}//end of while over retry

			if(is != null){
				is.close();	
				return true;
			}else{
				return false;
			}			

		}//end try block
		catch (Exception e) {
			return false;
		}//end catch block
		finally {
			try {

				if (is != null)
					is.close();

			} catch (Exception e) {
				return false;
			}

			//FREE VARIABLES
			is = null;
			url = null;

		}//end finally block		

	}//end of method testNetworkConnectivity_GOOGLE(...)

}//end of class Controls_Exceptions