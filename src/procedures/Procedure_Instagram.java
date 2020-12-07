package procedures;

import java.util.ArrayList;
import functions.Function_Instagram;

/*
 * DATE: 
 * 07/12/2020
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
	public static void explore(final String profile_name)throws Exception{

		try {

			//GET site_content
			System.out.println("Start explore "+profile_name);
			String url_instagram_profile = "https://www.instagram.com/"+profile_name+"/";
			String site_content = null;
			site_content = Function_Instagram.get_site_content(url_instagram_profile);

			//GET owner id
			String owner_id = Function_Instagram.get_owner_id(site_content);

			//LOAD more content
			site_content = Function_Instagram.load_more_objects(owner_id, null);
			
			//GET all images
			System.out.println("GET all images");
			ArrayList<String> list_of_images = new ArrayList<String>();
			list_of_images = Function_Instagram.get_all_images(site_content);

			//SAVE all images
			System.out.println("SAVE all images");			
			Function_Instagram.save_images(profile_name, list_of_images);

			String last_image_id = "-1";
			String prev_last_image_id = "-2";

			while(last_image_id.equals(prev_last_image_id) == false){

				prev_last_image_id = last_image_id;
				
				//GET last_image_id
				last_image_id = Function_Instagram.get_last_image_id(site_content);

				//LOAD more content
				site_content = Function_Instagram.load_more_objects(owner_id, last_image_id);

				//GET all images
				System.out.println("GET all images");
				list_of_images = new ArrayList<String>();
				list_of_images = Function_Instagram.get_all_images(site_content);

				//SAVE all images
				System.out.println("SAVE all images");			
				Function_Instagram.save_images(profile_name, list_of_images);
				
			}//end while cycle over page

			//SEARCH linked profiles

			//SAVE linked profiles

			//END
			System.out.println("EXPLORATION DONE for "+profile_name);	

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method explore(...)

}//end of class Procedure_Instagram