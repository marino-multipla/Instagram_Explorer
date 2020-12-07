package functions;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/*
 * DATE: 
 * 07/12/2020
 * AUTHOR: 
 * marino-multipla
 * DESCRIPTION: 
 * This is class contains functions that are used to
 * operate on Instagram site; 
 */
public class Function_Instagram {

	/**
	 * REQUIRES:
	 * - the source url of the website;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns the content of the GET response
	 */
	public static String get_site_content(final String source)throws Exception{

		//DECLARE variables
		HttpGet request = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;

		try {

			request = new HttpGet(source);

			// add request headers
			
			//request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			//request.addHeader("Accept-Encoding", "gzip, deflate, br");
			//request.addHeader("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3");
			//request.addHeader("Connection",	"keep-alive");
			//request.addHeader("Host","www.instagram.com");
			//request.addHeader("TE","Trailers");
			//request.addHeader("Upgrade-Insecure-Requests","1");
			//request.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0");
			
			
			try{
				response = HttpClients.createDefault().execute(request);
				System.out.println("Request sent at "+source);
			}catch(Exception e){
				//REPEAT request
				Thread.sleep(3000);
				response = HttpClients.createDefault().execute(request);
				System.out.println("Request RE - sent at "+source);
			}			

			if(response.getStatusLine().toString().contains("200")){

				entity = response.getEntity();

				if (entity != null) {

					return EntityUtils.toString(entity);

				}//end if entity != null
				else{

					throw new Exception("Non content");

				}//end if entity == null

			}//end if response.getStatusLine().toString().contains("200")
			else{

				throw new Exception("ERROR code response:"+response.getStatusLine().toString());

			}//end else response.getStatusLine().toString().contains("200") == false

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method get_site_content(...)
	
	/**
	 * REQUIRES:
	 * - the source url of the website;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns the content of the GET response
	 */
	public static String load_more_objects(final String owner_id, final String last_image_id)throws Exception{

		//DECLARE variables
		HttpGet request = null;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String url_source = null;

		try {

			url_source = "https://www.instagram.com/graphql/query/?query_hash=003056d32c2554def87228bc3fd9668a&variables=";
			
			if(last_image_id == null){
				url_source = url_source + URLEncoder.encode("{\"id\":\""+owner_id+"\",\"first\":250}", "UTF-8");
			}else{
				url_source = url_source + URLEncoder.encode("{\"id\":\""+owner_id+"\",\"first\":250,\"after\":\""+last_image_id+"\"}", "UTF-8");				
			}		
			
			request = new HttpGet();
			request.setURI(new URI(url_source));
			//request = new HttpGet(source);

			// add request headers
			//request.addHeader("Accept", "*/*");
			/*
			request.addHeader("Accept-Encoding", "gzip, deflate, br");
			request.addHeader("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3");
			request.addHeader("Connection",	"keep-alive");
			request.addHeader("DNT","1");
			request.addHeader("Host","www.instagram.com");
			request.addHeader("Pragma","no-cache");
			request.addHeader("Referer","https://www.instagram.com/valentinaferragni/");
			request.addHeader("TE","Trailers");
			request.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:83.0) Gecko/20100101 Firefox/80.0");
			*/

			try{
				response = HttpClients.createDefault().execute(request);
				System.out.println("Request sent at "+url_source);
			}catch(Exception e){
				//REPEAT request
				Thread.sleep(3000);
				response = HttpClients.createDefault().execute(request);
				System.out.println("Request RE - sent at "+url_source);
			}			

			if(response.getStatusLine().toString().contains("200")){

				entity = response.getEntity();

				if (entity != null) {

					return EntityUtils.toString(entity);

				}//end if entity != null
				else{

					throw new Exception("Non content");

				}//end if entity == null

			}//end if response.getStatusLine().toString().contains("200")
			else{

				throw new Exception("ERROR code response:"+response.getStatusLine().toString());

			}//end else response.getStatusLine().toString().contains("200") == false

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method get_site_content(...)

	/**
	 * REQUIRES:
	 * - the buffered reader used to search images;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns a list containing the images found
	 * from the buffered reader;
	 */
	public static ArrayList<String> get_all_images(String site_content)throws Exception{

		//DECLARE variables
		ArrayList<String> list_of_url_images = null;
		String img_source_page = null;
		
		String display_url_tag = "\"display_url\":\""; 

		try {

			list_of_url_images = new ArrayList<String>();
			//SEARCH AND SAVE all shortcode

			while(site_content.contains(display_url_tag)){
				site_content = site_content.substring(site_content.indexOf(display_url_tag)+display_url_tag.length());
				img_source_page = site_content.substring(0, site_content.indexOf("\""));
				list_of_url_images.add(img_source_page);
			}	

			return list_of_url_images;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method search_all_images(...)
	
	/**
	 * REQUIRES:
	 * - the buffered reader used to search images;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns a list containing the images found
	 * from the buffered reader;
	 */
	public static ArrayList<String> get_all_related_profiles(String site_content)throws Exception{

		//DECLARE variables
		ArrayList<String> list_of_profiles = null;
		
		String profile_name = null;
		String user_name_section = null;

		try {

			//INITIALIZE variables
			list_of_profiles = new ArrayList<String>();
			user_name_section = "\"username\":\"";

			//SEARCH section: related_profiles
			if(site_content.contains("related_profiles") == true){
				
				site_content = site_content.substring(site_content.indexOf("related_profiles"));
				
				while(site_content.contains("node")){
					site_content = site_content.substring(site_content.indexOf("node")+4);
					profile_name = site_content.substring(site_content.indexOf(user_name_section)+user_name_section.length());
					profile_name = profile_name.substring(0, profile_name.indexOf("\""));
					list_of_profiles.add(profile_name);
				}	
				
			}//end case site contains related_profiles
			else{
				System.out.println("No related profiles found");
			}
			
			

					

			System.out.println("Found "+list_of_profiles.size()+" profiles");

			//FOR EACH shortcode
			for(int index_profile=0; index_profile<list_of_profiles.size(); index_profile++){

				System.out.println(list_of_profiles.get(index_profile));

			}//end for cycle over list_of_profiles

			return list_of_profiles;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method get_all_related_profiles(...)
	
	/**
	 * REQUIRES:
	 * - the buffered reader used to search images;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns the id of the profile_name;
	 */
	public static String get_owner_id(String site_content)throws Exception{

		//DECLARE variables		
		String owner_id = null;

		try {

			//INITIALIZE variables

			//SEARCH section: related_profiles
			if(site_content.contains("owner") == true){
				
				site_content = site_content.substring(site_content.indexOf("owner"));
				site_content = site_content.substring(site_content.indexOf("id")+5);
				owner_id = site_content.substring(0, site_content.indexOf("\""));
				return owner_id;
				
			}//end case site contains related_profiles
			else{
				System.err.println("No owner id is found");
				return "-1";
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method get_owner_id(...)
	
	/**
	 * REQUIRES:
	 * - the buffered reader used to search images;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns the id of the profile_name;
	 */
	public static String get_last_image_id(String site_content)throws Exception{

		//DECLARE variables		
		String last_image_id = null;

		try {

			//INITIALIZE variables

			//SEARCH section: related_profiles
			if(site_content.contains("has_next_page\":true") == true){
				
				site_content = site_content.substring(site_content.indexOf("has_next_page\":true"));
				site_content = site_content.substring(site_content.indexOf("\"end_cursor\":\"")+14);
				last_image_id = site_content.substring(0, site_content.indexOf("\""));
				return last_image_id;
				
			}//end case site contains related_profiles
			else{
				System.err.println("No owner id is found");
				return "-1";
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method get_last_image_id(...)

	/**
	 * REQUIRES:
	 * - image_url;
	 * MODIFIES:
	 * EFFECTS:
	 * - it saves the image locally;
	 */
	public static void save_image(final String folder_name, final String image_name, final String image_url)throws Exception{

		//DECLARE variables
		URL url = null;
		InputStream in = null;
		ByteArrayOutputStream out = null;
		FileOutputStream fos = null;
		File directory = null;
		String path = null;
		
		try {

			//CREATE Image directory
			path = "Image/"+folder_name+"/";
			directory = new File(path);
			//directory = new File("Image/");
			if(directory.exists() == false){
				directory.mkdirs();
			}

			url = new URL(image_url);
			in = new BufferedInputStream(url.openStream());
			out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1!=(n=in.read(buf)))
			{
				out.write(buf, 0, n);
			}
			out.close();
			in.close();
			byte[] response = out.toByteArray();

			fos = new FileOutputStream(path+image_name+".jpg");
			fos.write(response);
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method save_image(...)

	/**
	 * REQUIRES:
	 * - a list of url of images;
	 * MODIFIES:
	 * EFFECTS:
	 * - it saves the list of images locally;
	 */
	public static void save_images(final String profile_name, ArrayList<String> list_of_images)throws Exception{

		//DECLARE variables
		int size_list_of_images = 0;
		int number_of_saved_images = 0;

		try {
			size_list_of_images = list_of_images.size();
			System.out.println("Start saving "+size_list_of_images+" images");

			for(int index_image = 0; index_image<list_of_images.size(); index_image++){
				
				/*
				image_name = list_of_images.get(index_image);
				image_name = image_name.substring(image_name.indexOf("/p/")+3);
				image_name = image_name.substring(0, image_name.indexOf("/"));
				*/
				
				try{
					save_image(profile_name, "img_"+System.currentTimeMillis(), list_of_images.get(index_image));
					number_of_saved_images++;
				}catch(Exception e){
					System.out.println("Skip saving image at:"+list_of_images.get(index_image));
				}
				
				System.out.println("Saved "+number_of_saved_images+"/"+size_list_of_images);

			}//end for cycle over list_of_images			

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method save_images(...)

}//end of class Function_Instagram