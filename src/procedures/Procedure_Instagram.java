package procedures;

import java.util.ArrayList;
import java.util.Scanner;

import controls.Controls_Exceptions;
import functions.Function_Instagram;
import functions.Functions_ISP;

/*
 * DATE: 
 * 15/12/2020
 * AUTHOR: 
 * marino-multipla
 * DESCRIPTION: 
 * This is class contains procedures that are used to
 * execute a sequence of tasks on Instagram.
 */
public class Procedure_Instagram {

	/*
	 * ###############################################################################
	 * DECLARE variables of the class
	 * ###############################################################################
	 */

	private static final String class_name = "Procedure_Instagram";
	private static final int max_images_to_save = 1;
	private static final int max_profiles_to_explore = 50000000;

	/*
	 * This variable is used to alert the admin if a
	 * number X of consecutive renew_isp_ip are
	 * detected; That means a problem during the isp renew process;
	 */
	private static int number_of_consecutive_renew_isp_ip = 0;
	private static final int max_number_of_consecutive_renew_isp_ip = 20;

	/*
	 * This variable is used to choose the type of
	 * isp to use;
	 */
	public static int isp_mode = 0;

	/*
	 * ###############################################################################
	 * DECLARE main procedures of the class
	 * ###############################################################################
	 */

	/**
	 * This method is used to explore public Instagram profiles;
	 * Start from the first profile and continue by exploring all related
	 * profiles of each explored profile;
	 * For each explored profile:
	 * - all images are saved;
	 * - all related profiles are added to the exploration_list
	 * and only profiles that are not inside the exploration_list will
	 * be added;
	 */
	public static void explore_by_related_profiles() {

		//DECLARE variables
		String method_name = null;

		try {

			method_name = "explore_by_related_profiles";
			System.out.println(method_name);
			System.out.println("#################################");
			System.out.println("Only "+max_images_to_save+" images will be saved for each profile in order to speed up the exploration process");
			System.out.println("#################################");
			System.out.println();

			//INPUT first_profile_name
			boolean valid_profile_name = false;
			int response_code = 0;
			int index_retry_profile = -1;
			String first_profile_name = null;
			Scanner input = new Scanner(System.in);
			//input.reset();

			//System.out.println(input.hasNext());

			while(valid_profile_name == false){

				System.out.println("Digit the first profile name to start exploration: ");
				first_profile_name = input.nextLine();				
				//CHECK existance
				response_code = Function_Instagram.check_profile_existance(first_profile_name);

				if(response_code == 200){
					valid_profile_name = true;
					input.close();
				}else{
					System.out.println("Profile name "+first_profile_name+" does not exists");
				}

			}//end while cycle over first_profile_name			

			ArrayList<String> exploration_list = new ArrayList<String>();
			exploration_list.add(first_profile_name);
			String profile_name = null;
			String profile_site_content = null;
			String owner_id = null;
			int check_result = 0;

			for(int i=0; i<exploration_list.size(); i++){
				try{

					profile_name = exploration_list.get(i);

					System.out.println("Start explore "+profile_name);

					//GET site_content
					String url_instagram_profile = "https://www.instagram.com/"+profile_name+"/";
					profile_site_content = Function_Instagram.get_site_content(url_instagram_profile);

					check_result = check_result_get_site_content(profile_site_content);

					check_number_consecutive_renew_isp_ip();

					if(check_result == 0){
						i--;
						renew_isp_ip();
						continue;
					}

					//COLLECT related_profiles
					collect_related_profiles_and_add_profiles_not_already_inside_exploration_list(profile_site_content, exploration_list);

					//GET owner id
					owner_id = Function_Instagram.get_owner_id(profile_site_content);

					//COLLECT images
					collect_images(profile_name, owner_id);

					System.out.println("EXPLORATION DONE for "+profile_name);

				}catch(Exception e){
					/*
					 * In case of exception the exploration 
					 * will be executed one more time for the same profile;
					 */
					System.out.println("Error during exploration of "+profile_name);
					if(index_retry_profile != i){
						index_retry_profile = i;
						i--;
						System.out.println("Retry exploring "+profile_name);
					}//end case profile not explored previously
				}//end case exception during exploration				
			}//end for cycle over list_of_profiles			

		} catch (Exception e) {
			Controls_Exceptions.report(e, class_name, method_name);
			e.printStackTrace();
		}

	}//end of method explore_by_related_profiles(...)

	/**
	 * This method is used to explore public Instagram profiles;
	 * Start from the first profile and continue by exploring
	 * the owner_id consecutive to the owner_id of the first inserted instagram
	 * profile;
	 * For each explored profile:
	 * - all images are saved;
	 */
	public static void explore_by_consecutive_owner_id() {

		try {

			System.out.println("explore_by_consecutive_owner_id");
			System.out.println("#################################");
			System.out.println("Only "+max_images_to_save+" images will be saved for each profile in order to speed up the exploration process");
			System.out.println("Only "+max_profiles_to_explore+" consecutive profiles will be explored in order to speed up the exploration process");
			System.out.println("#################################");
			System.out.println();

			//INPUT first_profile_name
			boolean valid_profile_name = false;
			int response_code = 0;
			int index_retry_profile = -1;
			String first_profile_name = null;
			Scanner input = new Scanner(System.in);

			while(valid_profile_name == false){

				System.out.println("Digit the first profile name to start exploration: ");
				first_profile_name = input.nextLine();				
				//CHECK existance
				response_code = Function_Instagram.check_profile_existance(first_profile_name);

				if(response_code == 200){
					valid_profile_name = true;
					input.close();
				}else{
					System.out.println("Profile name "+first_profile_name+" does not exists");
				}

			}//end while cycle over first_profile_name	

			String url_instagram_profile = "https://www.instagram.com/"+first_profile_name+"/";
			String profile_site_content = Function_Instagram.get_site_content(url_instagram_profile);

			int check_result = check_result_get_site_content(profile_site_content);

			if(check_result == 0){
				//RENEW IP ADDRESS
				System.err.println("Renew ip address and restart application");
				System.exit(0);
			}

			//GET owner id
			String owner_id = Function_Instagram.get_owner_id(profile_site_content);

			int progressive_owner_id = Integer.parseInt(owner_id)-1;
			for(int i=0; i<max_profiles_to_explore; i++){
				try{
					progressive_owner_id++;

					check_number_consecutive_renew_isp_ip();

					if(Function_Instagram.renew_isp_ip == true){
						renew_isp_ip();
						i--;
						progressive_owner_id--;
					}

					//COLLECT images
					collect_images(String.valueOf(progressive_owner_id), String.valueOf(progressive_owner_id));

				}catch(Exception e){
					System.err.println("Error during exploration of "+progressive_owner_id);
					if(index_retry_profile != i){
						index_retry_profile = i;
						i--;
						System.out.println("Retry exploring "+progressive_owner_id);
					}
				}				
			}//end for cycle over list_of_profiles			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}//end of method explore_by_consecutive_owner_id(...)

	/**
	 * This method is used to create a list of:
	 * username - owner_id;
	 * There are two possible collection mode:
	 * - It starts from an owner_id inserted by the user;
	 * OR
	 * - It starts from the last collected owner_id;
	 */
	public static void collect_usernames_owner_ids() {

		//DECLARE variables
		String method_name = null;

		try {

			method_name = "collect_usernames_owner_ids";

			System.out.println("collect_usernames_owner_ids");
			System.out.println("#################################");
			System.out.println();

			String selected_action = null;
			int progressive_owner_id = 0;
			String first_owner_id = null;
			Scanner input = new Scanner(System.in);

			while(true){

				//SELECT action
				System.out.println("Select where start from: ");
				System.out.println(" - Digit 1 to enter the owner_id: ");
				System.out.println(" - Digit 2 to resume last collected owner_id: ");
				selected_action = input.nextLine();

				if(selected_action.equals("1")){
					break;
				}else if(selected_action.equals("2")){
					break;
				}

				System.out.println("Choice incorrect - Re take choice");

			}//end while cycle over selected_action

			if(selected_action.equals("1")){
				//ENTER the owner_id
				System.out.println("Digit the owner_id to start with: ");
				first_owner_id = input.nextLine();				
				progressive_owner_id = Integer.parseInt(first_owner_id);
				progressive_owner_id--;
				input.close();
			}else if(selected_action.equals("2")){
				//LOAD last owner id if exists
				first_owner_id = Function_Instagram.load_last_owner_id("Data/Lists/", "username-id");
				progressive_owner_id = Integer.parseInt(first_owner_id);
				input.close();
			}			

			//INPUT first_profile_name
			int index_retry_profile = -1;

			for(int i=0; i<max_profiles_to_explore; i++){
				try{

					check_number_consecutive_renew_isp_ip();

					if(Function_Instagram.sent_requests > 180 || Function_Instagram.renew_isp_ip == true){
						renew_isp_ip();
						Function_Instagram.sent_requests = 0;
						progressive_owner_id--;
						i--;
					}

					progressive_owner_id++;
					//CALL explore procedure
					Procedure_Instagram.collect_username(progressive_owner_id);

				}catch(Exception e){
					Controls_Exceptions.report(e, class_name, method_name, "Error during collect username for "+progressive_owner_id);
					System.err.println();
					if(index_retry_profile != progressive_owner_id){
						index_retry_profile = progressive_owner_id;
						progressive_owner_id--;
						i--;
						Function_Instagram.FIREFOX_VERSION++;
						System.out.println("Retry exploring "+progressive_owner_id);

					}
				}//end case exception during collect_username			
			}//end for cycle over list_of_profiles			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}//end of method collect_usernames_owner_ids(...)

	/*
	 * ###############################################################################
	 * DECLARE partial procedures of the class
	 * ###############################################################################
	 */

	/**
	 * REQUIRES:
	 * MODIFIES:
	 * EFFECTS:
	 * - It checks the number of consecutive renew of isp ip;
	 * If it is over the max value, an alert is triggered;
	 */
	private static void check_number_consecutive_renew_isp_ip()throws Exception{

		try {
			if(Function_Instagram.renew_isp_ip == true){
				number_of_consecutive_renew_isp_ip++;
				if(number_of_consecutive_renew_isp_ip > max_number_of_consecutive_renew_isp_ip){
					Function_Instagram.play_sound();	
				}						
			}else{
				number_of_consecutive_renew_isp_ip = 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method check_number_consecutive_renew_isp_ip(...)

	/**
	 * REQUIRES:
	 * - site_content;
	 * - exploration_list;
	 * MODIFIES:
	 * EFFECTS:
	 * - It extracts all related profiles;
	 * - Add only related_profiles that are
	 * not already inside the exploration_list;
	 */
	private static void collect_related_profiles_and_add_profiles_not_already_inside_exploration_list(final String profile_site_content, ArrayList<String> exploration_list)throws Exception{

		try {

			//EXTRACT related profiles
			ArrayList<String> list_of_new_profiles = new ArrayList<String>();
			list_of_new_profiles = Function_Instagram.extract_all_related_profiles(profile_site_content);

			//SAVE related profiles not already inside exploration list
			Function_Instagram.add_distinct_profiles(list_of_new_profiles, exploration_list);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method collect_related_profiles_and_add_profiles_not_already_inside_exploration_list(...)

	/**
	 * REQUIRES:
	 * - profile_name;
	 * - owner_id;
	 * MODIFIES:
	 * EFFECTS:
	 * - Collects and saves all images;
	 */
	private static void collect_images(final String profile_name, 
			final String owner_id)throws Exception{

		try {

			//LOAD more content
			String site_content = Function_Instagram.load_more_objects(250, owner_id, null);

			//GET all images
			System.out.println("GET all images");
			ArrayList<String> list_of_images = new ArrayList<String>();
			Function_Instagram.get_all_images(site_content, list_of_images);

			String last_image_id = "-1";
			String prev_last_image_id = "-2";

			while(last_image_id.equals(prev_last_image_id) == false){

				prev_last_image_id = last_image_id;

				//GET last_image_id
				last_image_id = Function_Instagram.get_last_image_id(site_content);

				if(last_image_id.equals("-1")){
					break;
				}

				try{
					//LOAD more content
					site_content = Function_Instagram.load_more_objects(250, owner_id, last_image_id);

					//GET all images
					System.out.println("GET all images - list_images.size:"+list_of_images.size());
					Function_Instagram.get_all_images(site_content, list_of_images);
				}catch(Exception e){
					e.printStackTrace();
				}

				if(list_of_images.size()> max_images_to_save){
					//Use a limit in order to test a full exploration case
					break;
				}

			}//end while cycle over page

			//LIMIT the number of images to save
			for(int i=0; i<list_of_images.size(); i++){
				if(i >= max_images_to_save){
					list_of_images.remove(i);
					i--;
				}
			}

			//SAVE all images
			System.out.println("SAVE all images");			
			Function_Instagram.save_images(profile_name, list_of_images);

			//END

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method collect_images(...)

	/**
	 * 
	 * REQUIRES:
	 * - the owner_id of the profile;
	 * MODIFIES:
	 * EFFECTS:
	 * - It extracts the username related to the owner_id
	 * and save this correlation inside a local file;
	 */
	private static void collect_username(final int owner_id)throws Exception{

		try {

			//LOAD more content
			String site_content = Function_Instagram.load_more_objects(12, String.valueOf(owner_id), null);

			int check_result = check_result_get_site_content(site_content);

			if(check_result == 0){
				Function_Instagram.renew_isp_ip = true;
				return;
			}

			//GET username
			String username = Function_Instagram.get_username(site_content);

			if(username.equals("-1") == false){
				//TODO test instruction
				//Function_Instagram.save_String("Data/"+owner_id+"/", "graphql_site_content", site_content, false);
				Function_Instagram.save_String("Data/Lists/", "username-id", username+" - "+owner_id, true);	
			}else{

				if(site_content.contains("count") == true){
					username = "[private_profile]";
					Function_Instagram.save_String("Data/Lists/", "username-id", username+" - "+owner_id, true);
				}
				//TODO test instruction
				//Function_Instagram.save_String("Data/"+owner_id+"no_user/", "graphql_site_content", site_content, false);
			}			

			//END
			System.out.println(Function_Instagram.getTimeNow()+" - EXPLORATION DONE for "+username+" - "+owner_id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method collect_username(...)

	/**
	 * REQUIRES:
	 * - site_content
	 * MODIFIES:
	 * EFFECTS:
	 * - It checks if the site_content has 
	 * the features to trigger the renew isp ip address;
	 * - It returns 1 if the site_content does not require the
	 * renew of the ip address;
	 * - It returns 0 if the site_content require
	 * the renew of the ip address;
	 */
	private static int check_result_get_site_content(final String site_content)throws Exception{

		//DECLARE variables
		String method_name = null;

		try {
			method_name = "check_result_get_site_content";

			if(site_content.equals("-1")){

			}else if(site_content.contains("Accesso • Instagram") || site_content.contains("Login • Instagram")){
				System.err.println("Login is requested so renew ip address");
				return 0;
			}

			//END
			return 1;

		} catch (Exception e) {
			Controls_Exceptions.report(e, class_name, method_name);
			throw e;
		}

	}//end of method check_result_get_site_content(...)

	/**
	 * REQUIRES:
	 * MODIFIES:
	 * EFFECTS:
	 * - It renews the isp ip address;
	 */
	private static int renew_isp_ip()throws Exception{

		//DECLARE variables
		String method_name = null;

		try {

			method_name = "renew_isp_ip";

			Function_Instagram.renew_isp_ip = false;
			System.err.println("Renew ip address");

			if(isp_mode == 0){
				//CASE when no router is connected
				System.err.println("You have 40 seconds to renew manually the IP");
				Thread.sleep(40000);				
			}else if(isp_mode == 1){
				//CASE when you use Samsung phone as router
				Functions_ISP.renew_ip_address_samsung_papera();				
			}else if(isp_mode == 2){
				//CASE when you use Technicolor as router
				Functions_ISP.renew_ip_technicolor_TIM_fibra();
			}

			//END
			return 1;

		} catch (Exception e) {
			Controls_Exceptions.report(e, class_name, method_name);
			throw e;
		}

	}//end of method renew_isp_ip(...)

}//end of class Procedure_Instagram