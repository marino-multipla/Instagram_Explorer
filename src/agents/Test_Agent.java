package agents;

import java.net.URLEncoder;
import java.util.ArrayList;
import functions.Function_Instagram;
import procedures.Procedure_Instagram;

/*
 * DATE: 
 * 14/12/2020
 * AUTHOR: 
 * marino-multipla
 * DESCRIPTION: 
 * This is class is used to test 
 * single functions or a composition of them.
 */
public class Test_Agent {

	public static void main(String[] args) {
		try {

			//Procedure_Instagram.explore_by_related_profiles();
			
			int code = Function_Instagram.check_profile_existance("chiaraferragni");
			
			System.out.println(code);
			
			//test_save_images_from_owner_id();
			
			//test_private_usernames();
			
			//test_new_get_images();

			//test_get_username_from_owner_id();

			//Function_Instagram.renew_ip_address();

			//System.out.println(Function_Instagram.load_last_owner_id("Data/", "username-id"));

			//test_play_sound();

			//test_get_username_from_owner_id();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}//end method main(...)

	public static void test_play_sound() {
		try {

			while(true){
				Function_Instagram.play_sound();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}//end method test_get_username_from_owner_id(...)

	public static void test_get_username_from_owner_id() {
		try {

			//test_get_owner_id();

			//
			//LOAD more content
			String site_content = Function_Instagram.load_more_objects(250, String.valueOf(1000091), null);

			//GET username
			String username = Function_Instagram.get_username(site_content);

			System.out.println(username);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}//end method test_get_username_from_owner_id(...)

	private static void test_get_owner_id() throws Exception{
		String profile_name = "--";
		String site_content = null;
		String url_instagram_profile = "https://www.instagram.com/"+profile_name+"/";
		site_content = Function_Instagram.get_site_content(url_instagram_profile);	

		System.out.println("Owner id: "+Function_Instagram.get_owner_id(site_content));

	}//end method test_save_image(...)

	private static void test_get_all_profiles() throws Exception{
		String profile_name = "valentinaferragni";
		String site_content = null;
		String url_instagram_profile = "https://www.instagram.com/"+profile_name+"/";
		site_content = Function_Instagram.get_site_content(url_instagram_profile);	

		Function_Instagram.extract_all_related_profiles(site_content);

		System.out.println("Owner id: "+Function_Instagram.get_owner_id(site_content));

	}//end method test_save_image(...)

	private static void test_save_image() throws Exception{
		String image_url = "https://it.wikipedia.org/wiki/Sole#/media/File:The_Sun_by_the_Atmospheric_Imaging_Assembly_of_NASA's_Solar_Dynamics_Observatory_-_20100819.jpg";

		Function_Instagram.save_image("sun", "345", image_url);

	}//end method test_save_image(...)

	private static void test_load_more_images() throws Exception{
		String url = null;
		url = "https://www.instagram.com/graphql/query/?query_hash=003056d32c2554def87228bc3fd9668a&variables="+URLEncoder.encode("{\"id\":\"254470481\",\"first\":250}", "UTF-8");


		String site_content = null;
		ArrayList<String> urls = new ArrayList<>();

		//site_content= Function_Instagram.load_more_objects(url);

		System.out.println(site_content);

		Function_Instagram.get_all_images(site_content, urls);

		//Function_Instagram.save_images(urls);

		System.out.println(urls.size());
	}//end method test_load_more_images(...)

	private static void test_new_get_images() throws Exception{

		//robydag67 private profile
		String profile_name = "-";
		String site_content = null;
		String url_instagram_profile = "https://www.instagram.com/"+profile_name+"/";
		site_content = Function_Instagram.get_site_content(url_instagram_profile);	

		String owner_id = Function_Instagram.get_owner_id(site_content);

		site_content = Function_Instagram.load_more_objects(250, owner_id, null);

		ArrayList<String> list_of_images = new ArrayList<String>();
		Function_Instagram.get_all_images(site_content, list_of_images);

		Function_Instagram.save_images(profile_name, list_of_images);

	}//end method test_load_more_images(...)
	
	private static void test_save_images_from_owner_id() throws Exception{

		String owner_id = "7";
		//String owner_id = "67";

		String site_content = Function_Instagram.load_more_objects(12, owner_id, null);

		String username = Function_Instagram.get_username(site_content);
		
		ArrayList<String> list_of_images = new ArrayList<String>();
		Function_Instagram.get_all_images(site_content, list_of_images);

		Function_Instagram.save_images(username, list_of_images);

	}//end method test_save_images_from_owner_id(...)

	private static void test_private_usernames() throws Exception{

		//LOAD list_of_username_owner_id
		ArrayList<String> list_of_username_owner_id = new ArrayList<String>();
		Function_Instagram.load_all_username_owner_id("Data/", "username-id", list_of_username_owner_id);

		String site_content = null;
		String username = null;
		String owner_id = null;
		String line = null;
		
		for(int i=0; i<list_of_username_owner_id.size(); i++){
			line = list_of_username_owner_id.get(i);
			
			//EXTRACT owner_id and username
			owner_id = line.substring(line.lastIndexOf("-")+2);
			username = line.substring(0, line.lastIndexOf("-")-1);
						
			String url_instagram_profile = "https://www.instagram.com/"+username+"/";
			site_content = Function_Instagram.get_site_content(url_instagram_profile);	

			String owner_id_extracted = Function_Instagram.get_owner_id(site_content);

			if(owner_id_extracted.equals("-1")){
				System.out.println(line);
			}
			
		}//end for cycle over list_of_username_owner_id

		
		/*
		//## OLD CODE ##
		//robydag67 private profile
		
		String profile_name = "robydag67";
		//String site_content = null;
		String url_instagram_profile = "https://www.instagram.com/"+profile_name+"/";
		site_content = Function_Instagram.get_site_content_with_timeout(url_instagram_profile);	
		//String owner_id = Function_Instagram.get_owner_id(site_content);
	
		//2179786613673214754
		owner_id = "2179786613673214754";
		site_content = Function_Instagram.load_more_objects_with_timeout(250, owner_id, null);

		ArrayList<String> list_of_images = new ArrayList<String>();
		Function_Instagram.get_all_images(site_content, list_of_images);

		Function_Instagram.save_images(profile_name, list_of_images);
		*/

	}//end method test_load_more_images(...)

}//end class Test_Agent