package functions;

import java.io.FileInputStream;
import java.net.URI;
import java.util.Properties;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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
 * operate with the ISP (Internet Service Provider).
 */
public class Functions_ISP {
	
	/*
	 * ###############################################################################
	 * DECLARE variables of the class
	 * ###############################################################################
	 */
	
	private static final String class_name = "Functions_ISP";
	
	/*
	 * ###############################################################################
	 * DECLARE Technicolor methods of the class
	 * ###############################################################################
	 */

	/**
	 * REQUIRES:
	 * - token;
	 * - sessionId;
	 * - wan_status;
	 * MODIFIES:
	 * EFFECTS:
	 * - It forces the router to change the wan_status;
	 * 
	 * It returns 1 if it is executed correctly;
	 * It returns 0 if an error occurs;
	 */
	public static byte POST_change_wan_status_technicolor_tim(final String session_id, final String token,
			int wan_status){

		//DECLARE variables
		String method_name = null;
		byte function_result = 0;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;

		try{

			//INITIALIZE variables
			method_name = "POST_change_wan_status_technicolor_tim";
			HttpPost post = new HttpPost("http://192.168.1.1/modals/internet-modal.lp");

			// add request headers
			post.addHeader("Accept", "text/html, */*; q=0.01");
			post.addHeader("Accept-Encoding", "gzip, deflate");
			post.addHeader("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3");
			post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			post.addHeader("Cookie", "sessionID="+session_id);
			post.addHeader("Host", "192.168.1.1");
			post.addHeader("Origin", "http://192.168.1.1");
			post.addHeader("Referer", "http://192.168.1.1");
			post.addHeader("X-Requested-With", "XMLHttpRequest");

			//add payload
			//uci_wan_auto=0&uci_wan_masq=1&uci_wan_ipv6=0&action=SAVE&fromModal=YES&CSRFtoken=a662f9ffeaa2f9290b26cf7a4ca4cfde2bf6df0ed94456d158c75ee6aa5aea3b
			StringEntity params =new StringEntity("uci_wan_auto="+wan_status+"&uci_wan_masq=1&uci_wan_ipv6=0&action=SAVE&fromModal=YES&CSRFtoken="+token);

			//StringEntity params =new StringEntity("{\"codiceCliente\":\"0602899-03\",\"password\":\"ht45-jh6Dzx7\",\"abi\":\"05156\",\"brand\":\"0\",\"lang\":\"it-IT\",\"captchaInfo\":{\"captchaKey\":\"\",\"captchaValue\":\"\"}}");
			post.setEntity(params);

			CloseableHttpClient httpClient = HttpClients.createDefault();

			response = httpClient.execute(post);

			/*
			 * 200 OK
			 * Standard response for successful HTTP requests.
			 * The actual response will depend on the request method used. 
			 * In a GET request, the response will contain an entity 
			 * corresponding to the requested resource. 
			 * In a POST request, the response will contain an entity 
			 * describing or containing the result of the action. 
			 */
			if(response.getStatusLine().toString().contains("200")){

				entity = response.getEntity();

				if (entity != null) {

					return function_result;

				}//end if entity != null
				else{

					return 0;

				}//end if entity == null

			}//end if response.getStatusLine().toString().contains("200")
			else{

				return 0;

			}//end else response.getStatusLine().toString().contains("200") == false

		}catch (Exception e) {
			
			Controls_Exceptions.report(e, class_name, method_name);
			return 0;

		}//end of catch block

	}//end of method POST_change_wan_status_technicolor_tim(...)

	/**
	 * REQUIRES:
	 * - sessionsId;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns the response that
	 * contains the connection status;
	 */
	private static String GET_connection_status_technicolor_TIM(final String session_id)throws Exception{

		//DECLARE variables
		HttpEntity entity = null;
		HttpResponse response = null;
		String source = null;
		String result = null;

		try {

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

			source = "http://192.168.1.1/modals/internet-modal.lp";
			HttpGet getMethod = new HttpGet();
			getMethod.setURI(new URI(source));

			//Cookie
			getMethod.addHeader("Cookie", "sessionID="+session_id);
			/*
			getMethod.addHeader("Accept-Encoding", "gzip, deflate, br");
			getMethod.addHeader("Accept-Language", "it-IT,it;q=0.8,en-US;q=0.5,en;q=0.3");
			getMethod.addHeader("Connection",	"keep-alive");
			getMethod.addHeader("Host","www.instagram.com");
			getMethod.addHeader("TE","Trailers");
			getMethod.addHeader("Upgrade-Insecure-Requests","1");
			getMethod.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:83.0) Gecko/20100101 Firefox/83.0");
			 */

			try{
				response = httpClient.execute(getMethod);
				System.out.println("Request sent at "+source);
			}catch(Exception e){
				//REPEAT request
				response = httpClient.execute(getMethod);
				System.out.println("Request RE - sent at "+source);
			}			

			if(response.getStatusLine().toString().contains("200")){

				entity = response.getEntity();

				if (entity != null) {

					result = EntityUtils.toString(entity); 
					//System.out.println(result);
					return result;

				}//end if entity != null
				else{

					throw new Exception("No content");

				}//end if entity == null

			}//end if response.getStatusLine().toString().contains("200")
			else{

				throw new Exception("ERROR code response:"+response.getStatusLine().toString());

			}//end else response.getStatusLine().toString().contains("200") == false

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method GET_connection_status_technicolor_TIM(...)

	/**
	 * REQUIRES:
	 * - the source url of the website;
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns the content of the GET response
	 */
	public static int renew_ip_technicolor_TIM_fibra()throws Exception{

		//DECLRAE variables
		String result = null;
		String session_id = null;
		String token = null;

		try {

			session_id = load_parameter("session_id");
			token = load_parameter("token");

			//GET connection status
			System.out.println("GET connection status");
			result = GET_connection_status_technicolor_TIM(session_id);

			if(result.contains("Connected")){
				System.out.println("Connected now");
			}

			if(result.contains("Disabled")){
				System.out.println("Disconnected now");
				return 0;
			}

			//Connessione in corso
			if(result.contains("Connecting")){
				System.out.println("Connecting");
				return 0;
			}

			if(result.contains("Login")){
				System.out.println("Login required");
				return 0;
			}

			//WAIT 3 seconds
			System.out.println("Wait 3 seconds");
			Thread.sleep(3000);

			//ATTIVATO OFF
			System.out.println("WAN OFF");
			POST_change_wan_status_technicolor_tim(session_id, token, 0);

			//POST_change_wan_status_technicolor_tim_step2(session_id, token, 0);

			//WAIT 3 seconds
			System.out.println("Wait 3 seconds");
			Thread.sleep(3000);

			//ATTIVATO ON
			System.out.println("WAN ON");
			POST_change_wan_status_technicolor_tim(session_id, token, 1);

			//LOOP CHECK CONNECTION
			while(true){
				System.out.println("GET connection status");
				result = GET_connection_status_technicolor_TIM(session_id);	

				if(result.contains("Connected")){
					System.out.println("Connected now");
					break;
				}

				if(result.contains("Disabled")){
					System.out.println("Disconnected now");
					return 0;
				}

				//Connessione in corso
				if(result.contains("Connecting")){
					System.out.println("Connecting");
				}

				if(result.contains("Login")){
					System.out.println("Login required");
					return 0;
				}

				//WAIT 3 seconds
				System.out.println("Wait 3 seconds");
				Thread.sleep(3000);

			}//loop check connection

			return 1;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}//end of method renew_ip_technicolor_TIM_fibra(...)

	/*
	 * ###############################################################################
	 * DECLARE Android methods of the class
	 * ###############################################################################
	 */

	/** 
	 * REQUIRES:
	 *- All parameters not null;
	 * MODIFIES:
	 * EFFECTS:
	 * - make a call and digits the passed array using SamsungA5 papà;
	 * 
	 * The function returns 1 if is correctly executed;
	 * The function returns 0 if an error occurred;
	 */
	public static void renew_ip_address_samsung_old()throws Exception{

		//DECLARE variables
		String methodName = null;
		Runtime rt = null;
		String path = null;
		String open_change_connection_type = null;
		String tap_WCDMA = null;
		String tap_UMTS = null;
		String tap_OK = null;
		String turnOnScreen = null;
		/*
		 * This variable is expressed in multiple of seconds;
		 * It will be used multiplied by 1000 because the sleep time
		 * requires milliseconds;
		 */
		int wait_time = 1000*5;
		int wait_time_short = 2000;

		try {

			//INITIALIZE variables
			methodName = "renew_ip_address_samsung_old";
			
			//LOAD android sn
			String android_sn = load_parameter("android_sn");
			
			//Navigate through menu
			rt = Runtime.getRuntime();

			//PREPARE commands here

			path = "Lib/Android/Sdk/platform-tools/";
			//call = "adb -s 420305fb63122100 shell am start -a android.intent.action.CALL -d tel:"+phone_to_call;

			//Tap screen at a specific coordinates
			turnOnScreen = "adb -s "+android_sn+" shell input keyevent 26";
			open_change_connection_type = "adb -s "+android_sn+" shell input tap 261 545";
			tap_WCDMA = "adb -s "+android_sn+" shell input tap 246 456";
			tap_UMTS = "adb -s "+android_sn+" shell input tap 200 352";
			tap_OK = "adb -s "+android_sn+" shell input tap 343 594";

			//EXECUTE commands
			System.out.println("Screen on");
			rt.exec(path+turnOnScreen);
			Thread.sleep(wait_time);

			System.out.println("Open change connection type");
			rt.exec(path+open_change_connection_type);
			Thread.sleep(wait_time);

			System.out.println("Change into WCDMA");
			rt.exec(path+tap_WCDMA);
			Thread.sleep(wait_time+wait_time);

			System.out.println("Tap OK");
			rt.exec(path+tap_OK);
			Thread.sleep(wait_time_short+wait_time);

			System.out.println("Open change connection type");
			rt.exec(path+open_change_connection_type);
			Thread.sleep(wait_time_short);

			System.out.println("Change into UMTS");
			rt.exec(path+tap_UMTS);
			Thread.sleep(wait_time);

			System.out.println("Tap OK");
			rt.exec(path+tap_OK);
			Thread.sleep(wait_time_short+wait_time);

			System.out.println("Screen off");
			rt.exec(path+turnOnScreen);
			Thread.sleep(wait_time+wait_time);

			System.out.println("Renew executed");

			//END

		}//end of try block
		catch (Exception e) {
			Controls_Exceptions.report(e, class_name, methodName);
			throw new Exception("ERROR during "+methodName);

		}//end of catch block

	}//end of method renew_ip_address_samsung_old(...)

	/** 
	 * REQUIRES:
	 *- All parameters not null;
	 * MODIFIES:
	 * EFFECTS:
	 * - make a call and digits the passed array using SamsungA5 papà;
	 * 
	 * The function returns 1 if is correctly executed;
	 * The function returns 0 if an error occurred;
	 */
	public static void renew_ip_address_samsung_papera()throws Exception{

		//DECLARE variables
		String methodName = null;
		Runtime rt = null;
		String path = null;
		String open_change_connection_type = null;
		String tap_2G3G = null;
		String tap_3G4G = null;
		String turnOnScreen = null;
		/*
		 * This variable is expressed in multiple of seconds;
		 * It will be used multiplied by 1000 because the sleep time
		 * requires milliseconds;
		 */
		int wait_time = 1000*5;
		int wait_time_short = 2000;

		try {

			//INITIALIZE variables
			methodName = "renew_ip_address_samsung_papera";

			String android_sn = load_parameter("android_sn");
			
			//Navigate through menu
			rt = Runtime.getRuntime();

			//PREPARE commands here

			path = "Lib/Android/Sdk/platform-tools/";
			//call = "adb -s "+android_sn+" shell am start -a android.intent.action.CALL -d tel:"+phone_to_call;

			//Tap screen at a specific coordinates
			turnOnScreen = "adb -s "+android_sn+" shell input keyevent 26";
			open_change_connection_type = "adb -s "+android_sn+" shell input tap 462 608";
			tap_2G3G = "adb -s "+android_sn+" shell input tap 228 754";
			tap_3G4G = "adb -s "+android_sn+" shell input tap 306 629";

			//EXECUTE commands
			System.out.println("Screen on");
			rt.exec(path+turnOnScreen);
			Thread.sleep(wait_time);

			System.out.println("Open change connection type");
			rt.exec(path+open_change_connection_type);
			Thread.sleep(wait_time);

			System.out.println("Change into 3G");
			rt.exec(path+tap_2G3G);
			Thread.sleep(wait_time+wait_time);

			System.out.println("Open change connection type");
			rt.exec(path+open_change_connection_type);
			Thread.sleep(wait_time_short);

			System.out.println("Change into 4G");
			rt.exec(path+tap_3G4G);
			Thread.sleep(wait_time+wait_time);

			System.out.println("Screen off");
			rt.exec(path+turnOnScreen);
			Thread.sleep(wait_time);

			//END

		}//end of try block
		catch (Exception e) {

			Controls_Exceptions.report(e, class_name, methodName);
			throw new Exception("ERROR during renew_ip_address");

		}//end of catch block

	}//end of method renew_ip_address_samsung_papera(...)
	
	/** 
	 * REQUIRES:
	 * MODIFIES:
	 * EFFECTS:
	 * - it returns the android_sn from config_file;
	 */
	private static String load_parameter(String paramater_name)throws Exception{

		//DECLARE variables
		String methodName = null;
		Properties propertiesNet = null; 
		FileInputStream input = null;

		try {

			//INITIALIZE variables
			methodName = "load_parameter";

			//LOAD properties file
			//READ var.properties
			propertiesNet = new Properties(); 
			input = new FileInputStream("Data/Parameters/"+"config_file.properties"); 
			propertiesNet.load(input);
			input.close(); 

			try{
				//READ parameter
				return propertiesNet.getProperty(paramater_name);
			}catch(Exception e){
				Controls_Exceptions.report(e, class_name, methodName);
				throw e;
			}
			
			//END

		}//end of try block
		catch (Exception e) {

			Controls_Exceptions.report(e, class_name, methodName);
			throw new Exception("ERROR during "+methodName);

		}//end of catch block

	}//end of method load_parameter(...)
	
}//end of class Functions_ISP