package procedures;

import java.util.ArrayList;
import functions.Function_Instagram;

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
			
			//GET site_content
			System.out.println("Start explore "+url_instagram_profile);
			String site_content = null;
			site_content = Function_Instagram.get_site_content(url_instagram_profile);
			
			//GET all images
			System.out.println("GET all images");
			ArrayList<String> list_of_images = new ArrayList<String>();
			list_of_images = Function_Instagram.get_all_images(site_content);
			
			//SAVE all images
			System.out.println("SAVE all images");			
			Function_Instagram.save_images(list_of_images);
			
			//SEARCH linked profiles
			
			//SAVE linked profiles
			
			//END
			System.out.println("EXPLORATION DONE for "+url_instagram_profile);	
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}//end of method explore(...)
	
}//end of class Procedure_Instagram