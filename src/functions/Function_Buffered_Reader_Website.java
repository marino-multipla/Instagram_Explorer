package functions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;

/*
 * DATE: 
 * 29/11/2020
 * AUTHOR: 
 * marino-multipla
 * DESCRIPTION: 
 * This is class contains functions that are used to manage
 * a Buffered Reader that is connected to a web site.
 */
public class Function_Buffered_Reader_Website {

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
		ArrayList<String> list_shortcut = null;
		String img_source_page = null;
		String selected_shortcode = null;
		String site_image = null;

		try {

			list_of_url_images = new ArrayList<String>();
			list_shortcut = new ArrayList<String>();
			//SEARCH AND SAVE all shortcode

			while(site_content.contains("\"shortcode\":\"")){
				site_content = site_content.substring(site_content.indexOf("\"shortcode\":\"")+13);
				selected_shortcode = site_content.substring(0, site_content.indexOf("\""));
				list_shortcut.add(selected_shortcode);
			}			

			//FOR EACH shortcode
			for(int index_shortcut=0; index_shortcut<list_shortcut.size(); index_shortcut++){

				img_source_page = "https://www.instagram.com/p/"+list_shortcut.get(index_shortcut)+"/";

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
					System.out.println(site_image);

					//break;
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
	 * - the buffered reader used to search images;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns a list containing the images found
	 * from the buffered reader;
	 */
	public static void save_images(ArrayList<String> list_of_images)throws Exception{

		//DECLARE variables
		

		try {
			
			for(int index_image = 0; index_image<list_of_images.size(); index_image++){
				
				URL url = new URL(list_of_images.get(index_image));
				InputStream in = new BufferedInputStream(url.openStream());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int n = 0;
				while (-1!=(n=in.read(buf)))
				{
				   out.write(buf, 0, n);
				}
				out.close();
				in.close();
				byte[] response = out.toByteArray();
				
				FileOutputStream fos = new FileOutputStream(index_image+".jpg");
				fos.write(response);
				fos.close();
				
			}//end for cycle over list_of_images			

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method save_images(...)

}//end of class Function_Buffered_Reader_Website