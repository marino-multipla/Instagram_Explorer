package agents;

import procedures.Procedure_Instagram;

/*
 * DATE: 
 * 07/12/2020
 * AUTHOR: 
 * marino-multipla
 * DESCRIPTION: 
 * This is the agent that will perform the exploration 
 * of Instagram profiles
 */
public class First_Agent {

	public static void main(String[] args) {

		try {

			//SET instagram profile
			//String profile_name = "valentinaferragni";
			String profile_name = "marcobianchioff";
			
			//CALL explore procedure
			Procedure_Instagram.explore(profile_name);
						
		} catch (Exception e) {
			e.printStackTrace();
		}

	}//end of method main(...)

}//end of class First_Agent