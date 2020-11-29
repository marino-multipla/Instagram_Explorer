package agents;

import java.util.ArrayList;
import functions.Function_Buffered_Reader_Website;

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
			String url_instagram_profile = "https://www.instagram.com/cristiano/";

			System.out.println("Start exploring "+url_instagram_profile);
			
			//CALL explore procedure
			//Procedure_Instagram.explore(url_instagram_profile);
			
			ArrayList<String> list_of_images = new ArrayList<String>();
			list_of_images.add("https://scontent-mxp1-1.cdninstagram.com/v/t51.2885-15/e35/s1080x1080/125044072_1669503199890872_5310981635898938666_n.jpg?_nc_ht=scontent-mxp1-1.cdninstagram.com&_nc_cat=1&_nc_ohc=1indJHYCnPIAX8_hltn&tp=1&oh=04f657517a501224100d8419870c2c8e&oe=5FEF3CC3");
			Function_Buffered_Reader_Website.save_images(list_of_images);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}//end of method main(...)

}//end of class First_Agent