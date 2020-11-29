package procedures;

import java.io.BufferedReader;
import java.util.ArrayList;

import functions.Function_Buffered_Reader_Website;

/*
 * DATE: 
 * 29/11/2020
 * AUTHOR: 
 * marino-multipla
 * DESCRIPTION: 
 * This is class contains methods that are used to
 * executes procedures (a sequence of tasks) on Instagram.
 */
public class Procedure_Instagram {

	/**
	 * 
	 * REQUIRES:
	 * - The url of the Instagram profile from start the exploration
	 * MODIFIES:
	 * EFFECTS:
	 */
	public static void explore(final String url_instagram_profile)throws Exception{
		
		try {
			
			//OPEN connection
			String site_content = null;
			site_content = Function_Buffered_Reader_Website.get_site_content(url_instagram_profile);
			
			//SEARCH all images
			ArrayList<String> list_of_images = new ArrayList<String>();
			Function_Buffered_Reader_Website.get_all_images(site_content);
			
			System.out.println("Here all saved images");
			//SAVE all images
			
			Function_Buffered_Reader_Website.save_images(list_of_images);
			
			//SEARCH linked profiles
			
			//SAVE linked profiles
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}//end of method explore(...)
	
}//end of class Procedure_Instagram