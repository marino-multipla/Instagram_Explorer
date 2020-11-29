package agents;

import procedures.Procedure_Instagram;

/*
 * DATE: 
 * 29/11/2020
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
			//String url_instagram_profile = "https://www.instagram.com/cristiano/";
			String url_instagram_profile = "https://www.instagram.com/francescototti/";

			//CALL explore procedure
			Procedure_Instagram.explore(url_instagram_profile);
						
		} catch (Exception e) {
			e.printStackTrace();
		}

	}//end of method main(...)

}//end of class First_Agent