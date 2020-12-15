package agents;

import java.util.Scanner;
import procedures.Procedure_Instagram;

/*
 * DATE: 
 * 15/12/2020
 * AUTHOR: 
 * marino-multipla
 * DESCRIPTION: 
 * This is the agent that will perform procedures
 * on Instagram.
 */
public class First_Agent {

	/*
	 * ###############################################################################
	 * DECLARE variables of the class
	 * ###############################################################################
	 */

	private static final String Instagram_Explorer_version = "15/12/2020 10:59";

	/*
	 * ###############################################################################
	 * DECLARE main methods of the class
	 * ###############################################################################
	 */

	public static void main(String[] args) {

		try {

			start_agent();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}//end of method main(...)	

	private static void start_agent() {

		try {

			//PRINT welcome message
			System.out.println("#################################");
			System.out.println("Instagram Explorer version "+Instagram_Explorer_version);
			System.out.println("#################################");
			System.out.println();
			
			String selected_action = null;
			Scanner input = new Scanner(System.in);

			//SELECT isp_mode
			while(true){

				//PRINT menu
				System.out.println("Select which isp_mode to use: ");
				System.out.println(" - Digit 0 to update ip manually");
				System.out.println(" - Digit 1 to using Samsung papera as router;");
				System.out.println(" - Digit 2 to using Techniclor TIM as router;");

				//SELECT action
				selected_action = input.nextLine();

				if(selected_action.equals("0")){
					Procedure_Instagram.isp_mode = 0;
					break;
				}else if(selected_action.equals("1")){
					Procedure_Instagram.isp_mode = 1;
					System.out.println("Specify android_sn inside config_file");
					Thread.sleep(5000);
					break;
				}else if(selected_action.equals("2")){
					Procedure_Instagram.isp_mode = 2;
					System.out.println("Specify session_id and token inside config_file");
					Thread.sleep(5000);
					break;
				}

				System.out.println("Choice incorrect - Re take choice");

			}//end while cycle over selected_action
			
			System.out.println();
			System.out.println("#################################");
			System.out.println();
			System.out.println("#################################");
			System.out.println();
			
			//SELECT procedure
			while(true){

				//PRINT menu
				System.out.println("Select which procedure to execute: ");
				System.out.println(" - Digit 1 to explore public Instagram profiles using related profiles;");
				System.out.println(" - Digit 2 to explore public Instagram profiles using consecutive owner_id;");
				System.out.println(" - Digit 3 to save username - owner_id;");

				//SELECT action
				selected_action = input.nextLine();

				if(selected_action.equals("1")){
					break;
				}else if(selected_action.equals("2")){
					break;
				}else if(selected_action.equals("3")){
					break;
				}

				System.out.println("Choice incorrect - Re take choice");

			}//end while cycle over selected_action

			//EXCEUTE choice
			if(selected_action.equals("1")){
				Procedure_Instagram.explore_by_related_profiles();
			}else if(selected_action.equals("2")){
				Procedure_Instagram.explore_by_consecutive_owner_id();
			}else if(selected_action.equals("3")){
				Procedure_Instagram.collect_usernames_owner_ids();	
			}	
			
			input.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}//end of method start_agent(...)

}//end of class First_Agent