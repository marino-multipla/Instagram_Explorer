package functions;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/*
 * DATE: 
 * 29/11/2020
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
			request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			request.addHeader("Accept-Encoding", "gzip, deflate, br");
			request.addHeader("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3");
			request.addHeader("Connection",	"keep-alive");
			request.addHeader("Host","www.instagram.com");
			request.addHeader("TE","Trailers");
			request.addHeader("Upgrade-Insecure-Requests","1");
			request.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0");

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
	 * - the buffered reader used to search images;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns a list containing the images found
	 * from the buffered reader;
	 */
	public static ArrayList<String> get_all_images(String site_content)throws Exception{

		//DECLARE variables
		ArrayList<String> list_of_url_images = null;
		ArrayList<String> list_shortcode = null;
		String img_source_page = null;
		String selected_shortcode = null;
		String site_image = null;

		int number_of_found_images = 0;
		int size_list_shortcode = 0;

		try {

			list_of_url_images = new ArrayList<String>();
			list_shortcode = new ArrayList<String>();
			//SEARCH AND SAVE all shortcode

			while(site_content.contains("\"shortcode\":\"")){
				site_content = site_content.substring(site_content.indexOf("\"shortcode\":\"")+13);
				selected_shortcode = site_content.substring(0, site_content.indexOf("\""));
				list_shortcode.add(selected_shortcode);
			}	

			size_list_shortcode = list_shortcode.size();
			System.out.println("Found "+size_list_shortcode+" images");

			//FOR EACH shortcode
			for(int index_shortcut=0; index_shortcut<size_list_shortcode; index_shortcut++){

				img_source_page = "https://www.instagram.com/p/"+list_shortcode.get(index_shortcut)+"/";

				try{
					site_image = get_site_content(img_source_page);
				}catch(Exception e){
					System.out.println("Skip this url:"+img_source_page);
				}

				//Thread.sleep(5000);

				if(site_image.contains("<meta property=\"og:image\" content=\"")){

					site_image = site_image.substring(site_image.indexOf("<meta property=\"og:image\" content=\"")+35);
					site_image = site_image.substring(0, site_image.indexOf("\""));
					list_of_url_images.add(site_image);
					number_of_found_images++;
					System.out.println("Found "+number_of_found_images+"/"+size_list_shortcode);
					//System.out.println(site_image);
				}

			}//end for cycle over list_shortcut

			return list_of_url_images;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method search_all_images(...)

	/**
	 * REQUIRES:
	 * - image_url;
	 * MODIFIES:
	 * EFFECTS:
	 * - it saves the image locally;
	 */
	public static void save_image(final String image_name, final String image_url)throws Exception{

		//DECLARE variables
		URL url = null;
		InputStream in = null;
		ByteArrayOutputStream out = null;
		FileOutputStream fos = null;

		try {

			//CREATE Image directory
			if((new File("Image/")).exists() == false){
				(new File("Image/")).mkdir();
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

			fos = new FileOutputStream("Image/"+image_name+".jpg");
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
	public static void save_images(ArrayList<String> list_of_images)throws Exception{

		//DECLARE variables
		int size_list_of_images = 0;
		int number_of_saved_images = 0;

		try {
			size_list_of_images = list_of_images.size();
			System.out.println("Start saving "+size_list_of_images+" images");

			for(int index_image = 0; index_image<list_of_images.size(); index_image++){

				try{
					save_image("img_"+index_image, list_of_images.get(index_image));
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