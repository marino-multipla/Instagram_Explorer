package functions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import controls.Controls_Exceptions;

/*
 * DATE: 
 * 14/12/2020
 * AUTHOR: 
 * marino-multipla
 * DESCRIPTION: 
 * This is class contains functions that are used to
 * operate on Instagram; 
 */
public class Function_Instagram {

	/*
	 * ###############################################################################
	 * DECLARE variables of the class
	 * ###############################################################################
	 */

	private static final String class_name = "Function_Instagram";
	private static final int sleep_time = 30000;
	public static int FIREFOX_VERSION = 0;
	public static int sent_requests = 0;
	public static boolean renew_isp_ip = false;

	/*
	 * ###############################################################################
	 * DECLARE web methods of the class
	 * ###############################################################################
	 */

	/**
	 * REQUIRES:
	 * - the source url of the website;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns the content of the GET response
	 */
	public static String get_site_content(final String source)throws Exception{

		//DECLARE variables
		HttpEntity entity = null;
		HttpResponse response = null;
		String method_name = null;

		try {

			method_name = "get_site_content";

			/*
			 * the Connection Timeout (http.connection.timeout) – the time to establish the connection with the remote host
			 * the Socket Timeout (http.socket.timeout) – the time waiting for data – after establishing the connection; maximum time of inactivity between two data packets
			 * the Connection Manager Timeout (http.connection-manager.timeout) – the time to wait for a connection from the connection manager/pool
			 * The first two parameters – the connection and socket timeouts – are the most important. However, setting a timeout for obtaining a connection is definitely important in high load scenarios, which is why the third parameter shouldn't be ignored. 
			 */
			int timeout = 5;
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(timeout * 1000)
					.setConnectionRequestTimeout(timeout * 1000)
					.setSocketTimeout(timeout * 1000).build();
			CloseableHttpClient httpClient =  HttpClientBuilder.create().setDefaultRequestConfig(config).build();

			HttpGet getMethod = new HttpGet();
			getMethod.setURI(new URI(source));

			getMethod.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			getMethod.addHeader("Accept-Encoding", "gzip, deflate, br");
			//getMethod.addHeader("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3");
			getMethod.addHeader("Connection",	"keep-alive");
			getMethod.addHeader("Host","www.instagram.com");
			getMethod.addHeader("TE","Trailers");
			getMethod.addHeader("Upgrade-Insecure-Requests","1");
			getMethod.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0");

			try{
				System.out.println("Send request at "+source);
				response = httpClient.execute(getMethod);			
			}catch(Exception e){
				//REPEAT request
				Thread.sleep(sleep_time);
				Controls_Exceptions.report(e, class_name, method_name, "Error during GET at "+source);
				System.out.println("RE send Request at "+source);
				response = httpClient.execute(getMethod);
			}	
			
			sent_requests++;
			
			if(response.getStatusLine().toString().contains("200")){

				entity = response.getEntity();

				if (entity != null) {

					return EntityUtils.toString(entity);

				}//end if entity != null
				else{

					return "-1";

				}//end if entity == null

			}//end if response.getStatusLine().toString().contains("200")
			else if(response.getStatusLine().toString().contains("429")){
				renew_isp_ip = true;
				return "-1";
			}//end case code 429
			else{
				Controls_Exceptions.report(null, class_name, method_name, "Error during GET at "+source+ "response code:"+response.getStatusLine().toString());
				throw new Exception("ERROR code response:"+response.getStatusLine().toString());
			}//end case response with error code

		} catch (Exception e) {
			Controls_Exceptions.report(e, class_name, method_name, "Error during GET at "+source);
			throw e;
		}

	}//end of method get_site_content(...)

	/**
	 * REQUIRES:
	 * - the source url of the website;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns the code of the GET request;
	 */
	public static int check_profile_existance(final String profile_name)throws Exception{

		//DECLARE variables
		HttpGet request = null;
		CloseableHttpResponse response = null;
		String source = null;
		String method_name = null;
		
		try {

			method_name = "check_profile_existance";
			
			source = "https://www.instagram.com/"+profile_name+"/";
			request = new HttpGet(source);

			// add request headers

			request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			request.addHeader("Accept-Encoding", "gzip, deflate, br");
			request.addHeader("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3");
			request.addHeader("Connection",	"keep-alive");
			request.addHeader("Host","www.instagram.com");
			request.addHeader("TE","Trailers");
			request.addHeader("Upgrade-Insecure-Requests","1");
			request.addHeader("User-Agent","Mozilla/5.0 (mAc NT OSX6.3; Win64; x64; rv:83.0) Gecko/20100101 Firefox/"+FIREFOX_VERSION+20);

			try{
				response = HttpClients.createDefault().execute(request);
				//System.out.println("Request sent at "+source);
			}catch(Exception e){
				//REPEAT request
				Thread.sleep(sleep_time);
				response = HttpClients.createDefault().execute(request);
				//System.out.println("Request RE - sent at "+source);
			}		

			return response.getStatusLine().getStatusCode();

		} catch (Exception e) {
			Controls_Exceptions.report(e, class_name, method_name, "Error during GET at "+source);
			throw e;
		}

	}//end of method check_profile_existance(...)

	/**
	 * REQUIRES:
	 * - owner_id of the profile;
	 * - number of images to retrieve;
	 * - the last_image_id from start retrieving other images;
	 * MODIFIES:
	 * EFFECTS:
	 * - It performs a GET request in order to obtain the
	 * next 250 images available;
	 */
	public static String load_more_objects(final int number_of_objects, final String owner_id, final String last_image_id)throws Exception{

		//DECLARE variables
		HttpEntity entity = null;
		String url_source = null;
		HttpResponse response = null;
		String method_name = null;

		try {

			method_name = "load_more_objects";

			//003056d32c2554def87228bc3fd9668a
			//query_hash new anna d4d88dc1500312af6f937f7b804c68c3
			//query_hash 003056d32c2554def87228bc3fd9668a&variables
			url_source = "https://www.instagram.com/graphql/query/?query_hash=003056d32c2554def87228bc3fd9668a&variables=";

			if(last_image_id == null){
				url_source = url_source + URLEncoder.encode("{\"id\":\""+owner_id+"\",\"first\":"+number_of_objects+"}", "UTF-8");
			}else{
				url_source = url_source + URLEncoder.encode("{\"id\":\""+owner_id+"\",\"first\":"+number_of_objects+",\"after\":\""+last_image_id+"\"}", "UTF-8");				
			}	

			/*
			 * the Connection Timeout (http.connection.timeout) – the time to establish the connection with the remote host
			 * the Socket Timeout (http.socket.timeout) – the time waiting for data – after establishing the connection; maximum time of inactivity between two data packets
			 * the Connection Manager Timeout (http.connection-manager.timeout) – the time to wait for a connection from the connection manager/pool
			 * The first two parameters – the connection and socket timeouts – are the most important. However, setting a timeout for obtaining a connection is definitely important in high load scenarios, which is why the third parameter shouldn't be ignored. 
			 */
			int timeout = 5;
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(timeout * 1000)
					.setConnectionRequestTimeout(timeout * 1000)
					.setSocketTimeout(timeout * 1000).build();
			CloseableHttpClient httpClient =  HttpClientBuilder.create().setDefaultRequestConfig(config).build();

			HttpGet getMethod = new HttpGet();
			getMethod.setURI(new URI(url_source));

			getMethod.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			getMethod.addHeader("Accept-Encoding", "gzip, deflate, br");
			getMethod.addHeader("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3");
			//getMethod.addHeader("Connection",	"keep-alive");
			getMethod.addHeader("Host","www.instagram.com");
			//getMethod.addHeader("Referer","https://www.instagram.com/valentinaferragni/");
			getMethod.addHeader("Upgrade-Insecure-Requests","1");
			getMethod.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0");


			try{
				response = httpClient.execute(getMethod);
				//System.out.println("Request sent at "+url_source);
			}catch(Exception e){
				//REPEAT request
				Thread.sleep(sleep_time);
				response = httpClient.execute(getMethod);
				//System.out.println("Request RE - sent at "+url_source);
			}			
			
			sent_requests++;

			if(response.getStatusLine().toString().contains("200")){

				entity = response.getEntity();

				if (entity != null) {

					sent_requests++;
					return EntityUtils.toString(entity);

				}//end if entity != null
				else{

					return "-1";

				}//end if entity == null

			}//end if response.getStatusLine().toString().contains("200")
			else if(response.getStatusLine().toString().contains("429")){
				renew_isp_ip = true;
				return "-1";
			}//end case code 429
			else{
				Controls_Exceptions.report(null, class_name, method_name, "Error during GET at "+url_source+ "response code:"+response.getStatusLine().toString());
				throw new Exception("ERROR code response:"+response.getStatusLine().toString());
			}//end case response with error code
		} catch (Exception e) {
			Controls_Exceptions.report(e, class_name, method_name, "Error during GET at "+url_source);
			throw e;
		}

	}//end of method load_more_objects(...)

	/*
	 * ###############################################################################
	 * DECLARE string analysis methods of the class
	 * ###############################################################################
	 */

	/**
	 * REQUIRES:
	 * - the site_content in string format;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns the owner_id of the profile_name;
	 */
	public static String get_username(String site_content)throws Exception{

		//DECLARE variables		
		String username = null;
		String username_tag = null;

		try {

			//INITIALIZE variables

			username_tag = "\"username\":\"";

			//SEARCH section: related_profiles
			if(site_content.contains(username_tag) == true){

				site_content = site_content.substring(site_content.indexOf(username_tag)+username_tag.length());
				username = site_content.substring(0, site_content.indexOf("\""));
				return username;

			}//end case site contains related_profiles
			else{
				System.err.println("No username is found");
				return "-1";
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method get_username(...)

	/**
	 * REQUIRES:
	 * - the site_content in string format;
	 * MODIFIES:
	 * EFFECTS:
	 * - It extracts all images found inside site_content;
	 */
	public static void get_all_images(String site_content, ArrayList<String> list_of_url_images)throws Exception{

		//DECLARE variables
		String img_source_page = null;

		String display_url_tag = "\"display_url\":\""; 

		try {
			//SEARCH AND SAVE all display_url

			while(site_content.contains(display_url_tag)){
				site_content = site_content.substring(site_content.indexOf(display_url_tag)+display_url_tag.length());
				img_source_page = site_content.substring(0, site_content.indexOf("\""));
				list_of_url_images.add(img_source_page);
			}//end while cycle over site_content

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method search_all_images(...)

	/**
	 * REQUIRES:
	 * - new profile to check;
	 * - list of all profiles already visited;
	 * MODIFIES:
	 * EFFECTS:
	 * - It extracts all images found inside site_content;
	 */
	private static int add_distinct_profile(String new_profile, ArrayList<String> list_of_profiles)throws Exception{

		//DECLARE variables

		try {

			for(int i=0; i<list_of_profiles.size(); i++){
				if(new_profile.equals(list_of_profiles.get(i))){
					return 2;
				}
			}//end for cycle over list_of_profiles

			list_of_profiles.add(new_profile);
			return 1;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method search_all_images(...)

	/**
	 * REQUIRES:
	 * - new profile to check;
	 * - list of all profiles already visited;
	 * MODIFIES:
	 * EFFECTS:
	 * - It extracts all images found inside site_content;
	 */
	public static void add_distinct_profiles(ArrayList<String> list_of_new_profiles, ArrayList<String> list_of_profiles)throws Exception{

		//DECLARE variables

		try {

			for(int i=0; i<list_of_new_profiles.size(); i++){
				add_distinct_profile(list_of_new_profiles.get(i), list_of_profiles);
			}//end for cycle over list_of_profiles

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method search_all_images(...)

	/**
	 * REQUIRES:
	 * - the site_content in string format;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns a list all found related profiles;
	 */
	public static ArrayList<String> extract_all_related_profiles(String site_content)throws Exception{

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

			//PRINT all profiles
			/*
			for(int index_profile=0; index_profile<list_of_profiles.size(); index_profile++){
				System.out.println(list_of_profiles.get(index_profile));
			}//end for cycle over list_of_profiles
			 */

			return list_of_profiles;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method get_all_related_profiles(...)

	/**
	 * REQUIRES:
	 * - the site_content in string format;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns the owner_id of the profile_name;
	 */
	public static String get_owner_id(String site_content)throws Exception{

		//DECLARE variables		
		String owner_id = null;

		try {

			//INITIALIZE variables

			//SEARCH section: related_profiles
			if(site_content.contains("\"owner\":") == true){

				site_content = site_content.substring(site_content.indexOf("\"owner\":"));
				site_content = site_content.substring(site_content.indexOf("id")+5);
				owner_id = site_content.substring(0, site_content.indexOf("\""));
				return owner_id;

			}//end case site contains related_profiles
			else{
				System.err.println("No owner id is found");
				return "-1";
			}

			//return owner_id;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method get_owner_id(...)

	/**
	 * REQUIRES:
	 * - the site_content in string format;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns the identifier of the last
	 * image inside;
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
				System.err.println("No last_image_id is found");
				return "-1";
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method get_last_image_id(...)

	/*
	 * ###############################################################################
	 * DECLARE OS methods of the class
	 * ###############################################################################
	 */

	/**
	 * This function is used to get the date time in the FORMAT yyyy-MM-dd_HH.mm
	 */
	public static String getTimeNow()throws Exception{
		try{
			SimpleDateFormat time_formatter_V3 = new SimpleDateFormat("yyyy-MM-dd_HH.mm");
			return time_formatter_V3.format(System.currentTimeMillis());
		} catch (Exception e) {

			throw new Exception("ERROR during getTimeNow");
		}
	}//end method getTimeNow(...)

	/**
	 * This function is used to save a string to TXT file;
	 * 
	 * If append=true the new lines will be added to the existing file;
	 * If append=false a new file will be created;
	 */
	public static void save_String(final String path, 
			final String fileName, final String string,
			boolean append)throws Exception{

		//DECLARE variables
		BufferedWriter bw = null;
		FileWriter fw = null;
		File f_pathDirectory = null;
		String txtFile = null;
		String methodName = null;


		try {

			//INITIALIZE variables
			methodName = "save_String";

			//CREATE path directory if does not exists
			f_pathDirectory = new File(path);
			if(f_pathDirectory.exists() == false){
				f_pathDirectory.mkdirs();
			}				

			txtFile = path+fileName+".txt";
			fw = new FileWriter(txtFile, append);
			bw = new BufferedWriter(fw);

			bw.write(string);	
			bw.newLine();

		}//end of try block
		catch (Exception e) {
			Controls_Exceptions.report(e, class_name, methodName);
			throw new Exception("Error during save TXT "+string);

		}finally {
			try {

				//FREE variables
				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

				bw = null;
				fw = null;
				f_pathDirectory = null;
				txtFile = null;


			} catch (Exception e) {
				throw new Exception("Error during save TXT "+string);
			}
		}//end of finally block

	}//end of method save_String(...)

	/**
	 * This function is used to save a string to TXT file;
	 * 
	 * If append=true the new lines will be added to the existing file;
	 * If append=false a new file will be created;
	 */
	public static String load_last_owner_id(final String path, 
			final String fileName)throws Exception{

		//DECLARE variables
		String methodName = null;
		BufferedReader br = null;
		FileReader fr = null;
		File f = null;
		String sCurrentLine = null;
		String last_line = null;

		try {

			//INITIALIZE variables
			methodName = "load_last_owner_id";

			f = new File(path+fileName+".txt");

			if(f.exists() == true){

				fr = new FileReader(path+fileName+".txt");
				br = new BufferedReader(fr);

				while ((sCurrentLine = br.readLine()) != null) {
					last_line = sCurrentLine;
				}

				//EXTRACT owner_id
				last_line = last_line.substring(last_line.lastIndexOf("-")+2);

				return last_line;

			}else{				
				return "-1";
			}

		}//end of try block
		catch (Exception e) {

			throw new Exception("Error during "+methodName);

		}finally {
			try {

				//FREE variables
				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (Exception e) {
				throw new Exception("Error during "+methodName);
			}
		}//end of finally block

	}//end of method load_last_owner_id(...)

	/**
	 * This function is used to save a string to TXT file;
	 * 
	 * If append=true the new lines will be added to the existing file;
	 * If append=false a new file will be created;
	 */
	public static void load_all_username_owner_id(final String path, 
			final String fileName, ArrayList<String> list_username_ownwer_id)throws Exception{

		//DECLARE variables
		String methodName = null;
		BufferedReader br = null;
		FileReader fr = null;
		File f = null;
		String sCurrentLine = null;

		try {

			//INITIALIZE variables
			methodName = "load_last_owner_id";

			f = new File(path+fileName+".txt");

			if(f.exists() == true){

				fr = new FileReader(path+fileName+".txt");
				br = new BufferedReader(fr);

				while ((sCurrentLine = br.readLine()) != null) {
					list_username_ownwer_id.add(sCurrentLine);
				}

			}else{				
				throw new Exception("File not found - Error during "+methodName);
			}

		}//end of try block
		catch (Exception e) {

			throw new Exception("Error during "+methodName);

		}finally {
			try {

				//FREE variables
				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (Exception e) {
				throw new Exception("Error during "+methodName);
			}
		}//end of finally block

	}//end of method load_all_username_owner_id(...)

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
			path = "Data/Images/"+folder_name+"/";
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
			Thread.sleep(sleep_time);
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

			list_of_images.clear();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method save_images(...)

	/**
	 * REQUIRES:
	 * - a list of url of images;
	 * MODIFIES:
	 * EFFECTS:
	 * - it saves the list of images locally;
	 */
	public static void play_sound()throws Exception{

		//DECLARE variables

		try {

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("M.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			Thread.sleep(5*60000);
			clip.close();
			System.gc();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method play_sound(...)
		
}//end of class Function_Instagram