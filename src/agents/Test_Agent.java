package agents;

import java.net.URLEncoder;
import java.util.ArrayList;
import functions.Function_Instagram;

/*
 * DATE: 
 * 07/12/2020
 * AUTHOR: 
 * marino-multipla
 * DESCRIPTION: 
 * This is class is used to test 
 * single functions or a composition of them.
 */
public class Test_Agent {

	public static void main(String[] args) {
		try {

			test_get_all_profiles();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}//end method main(...)

	private static void test_get_all_profiles() throws Exception{
		String profile_name = "valentinaferragni";
		String site_content = null;
		String url_instagram_profile = "https://www.instagram.com/"+profile_name+"/";
		site_content = Function_Instagram.get_site_content(url_instagram_profile);	
		
		Function_Instagram.get_all_related_profiles(site_content);

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
		ArrayList<String> urls = null;

		//site_content= Function_Instagram.load_more_objects(url);

		System.out.println(site_content);

		urls = Function_Instagram.get_all_images(site_content);

		//Function_Instagram.save_images(urls);

		System.out.println(urls.size());
	}//end method test_load_more_images(...)
	
	private static void test_new_get_images() throws Exception{
		
		String profile_name = "valentinaferragni";
		String site_content = null;
		String url_instagram_profile = "https://www.instagram.com/"+profile_name+"/";
		site_content = Function_Instagram.get_site_content(url_instagram_profile);	
		
		String owner_id = Function_Instagram.get_owner_id(site_content);
		
		site_content = Function_Instagram.load_more_objects(owner_id, null);
		
		ArrayList<String> list_of_images = null;
		list_of_images = Function_Instagram.get_all_images(site_content);

		Function_Instagram.save_images(profile_name, list_of_images);
		
	}//end method test_load_more_images(...)

}//end class Test_Agent