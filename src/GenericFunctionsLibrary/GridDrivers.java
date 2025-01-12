package GenericFunctionsLibrary;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class GridDrivers  {
	

	/***********************************************************************************************
	 * Function Description : This will write any file. 
	 * For Grid we are using this to write json and bat commands for starting grid hub and nodes
	 * @author rajat.jain, date: 08-April-2016
	 * *********************************************************************************************/

	public void writeFile(String path, String content) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(path));
			writer.write(content);
		} catch (IOException e) {

		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}

	}
	
	/***********************************************************************************************
	 * Function Description : This will start grid hub and node. 
	 * Pass the command to start grid and node  
	 * @author rajat.jain, date: 08-April-2016
	 * *********************************************************************************************/

	public void StartGridHubAndNode(String command, int i) throws IOException,
	InterruptedException {
		String directory;
		directory = System.getProperty("user.dir");
		directory = directory.replace("\\", "/");
		System.out.println(command);

		if(SystemUtils.IS_OS_WINDOWS){

			writeFile(directory + "/" + "node" + i + ".bat", command);

			try {
				String cmmd = "cmd /c start " + directory + "/" + "node" + i
						+ ".bat";
				Process p = Runtime.getRuntime().exec(cmmd);
				i++;
			} catch (IOException ex) {
			}
		}
		else{
			writeFile(directory+"/node"+i, command);
			Runtime.getRuntime().exec("chmod +x "+directory+"/node"+i).waitFor();
			Runtime.getRuntime().exec("/usr/bin/gnome-terminal -e ./node"+i);
			Thread.sleep(5000);
		}
	}

	/* Function :- This will return a free port  
	 * @author: 
	 */
	public int getFreePort() 
	{
		int port=-1;
		try{
		ServerSocket socket = new ServerSocket(0);
		socket.setReuseAddress(true);
		port = socket.getLocalPort(); 
		socket.close();
		System.out.println("Port being used:"+port);
		}
		catch(Exception e){
			System.out.println("ERROR WHILE FETCHING FREE PORT:");
			e.printStackTrace();
		}
		return port;
	}
	
	/***********************************************************************************************
	 * Function Description :This will stop port.  
	 * Pass the command to start grid and node  
	 * @author rajat.jain, date: 08-April-2016
	 * *********************************************************************************************/

	public void stopPort(String port)  {
		if(SystemUtils.IS_OS_WINDOWS){
			try {
				Runtime.getRuntime().exec("cmd.exe");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String command = "cmd /c echo off & FOR /F \"tokens=5 delims= \" %a IN ('netstat -a -n -o ^| findstr :"
					+ port + "') do taskkill /F /PID  %a";

			String s = null;
			try {
				Process p = Runtime.getRuntime().exec(command);
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				BufferedReader stdError = new BufferedReader(new InputStreamReader(
						p.getErrorStream()));

				// read the output from the command
				while ((s = stdInput.readLine()) != null) {
					System.out.println(s);
				}

				// read any errors from the attempted command
				while ((s = stdError.readLine()) != null) {
					System.out.println(s);
				}
			} catch (IOException e) {
				System.out.println(" ---------->>> Exception happened:While stopping port ");
				e.printStackTrace();
			}

		}
		else{

			String command ="fuser -k "+port+"/tcp";
			//String command ="kill -9 $(lsof -n -i :"+port+" | grep LISTEN | awk '{print $2}')";
			try {
				//this.runCommand("killall -9 node");
				Runtime.getRuntime().exec(command);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}


		System.out.println("------------>>> Port : "+port+" has been stopped");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Function :- This will Get the ip address of system.Require to generate jsons  
	 * author: Rajat Jain, date: 08-April-2016
	 */
	public String GetIpAddress() {
		String ipname = "";
		Enumeration<NetworkInterface> nets = null;
		try {
			nets = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block

			e1.printStackTrace();
		}
		for (NetworkInterface netint : Collections.list(nets)) {
			try {
				ipname = displayInterfaceInformation(netint);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!ipname.equals("")) {

				break;
			}
		}
		ipname = ipname.replace("/", "");
		ipname = ipname.trim();
		System.out.println(ipname);
		return ipname;
	}

	/* Function :- Complete process to start grid and nodes. Description mentioned in code  
	 * author: Rajat Jain, date: 08-April-2016
	 */
	public void StartGrid(List<String> devices) throws IOException, InterruptedException {

		final GridDrivers grid = new GridDrivers(); 
	//	List<String> devices = new ArrayList<String>();
		GenericFunctions generic = new GenericFunctions();
		HashMap<String, String> data = new HashMap<String,String>();
		String directory;
		String commonResources;
		String appiumDirectory;
		directory = System.getProperty("user.dir");
		
		if(SystemUtils.IS_OS_WINDOWS){
			commonResources = System.getenv("Common_Resources");
			appiumDirectory = "C:/Appium";
			commonResources = commonResources.replace("\\", "/");
			directory = directory.replace("\\", "/");
		}
		else{
			commonResources=System.getenv("COMMON_RESOURCES");
			appiumDirectory = "/usr/local/bin/appium";
		}
		
		grid.stopPort("4444"); // stop grid hub if already working
		String ip = "127.0.0.1"; // Get Ip address
	//	devices = generic.getConnectedDevicesList();
	//	int numberOfDevices = generic.getconnectedDevicesNumber();
		int numberOfDevices = devices.size();
		String device[] = new String[numberOfDevices];

		
		String command[]= new String[numberOfDevices+1]; // In this array grid command and node command will get save
		Thread[] threads = new Thread[numberOfDevices+1]; // thread to start grid and nodes

		if (numberOfDevices == 0) {
			return;
		}
		for (int i = 0; i < numberOfDevices; i++) {
			device[i] = devices.get(i).toString();

			int port;
			if (i == 0) {
				port = 4723;
			} else {
				port = 4723 + i * 10; //making different port numbers for different devices
			}

			// Note If You are changing hub port than you have to change it in start driver of android in generic function class
			grid.makeJson(4444, ip, port, device[i].toString().trim(),device[i]+".json");
			data.put("port"+i, port+"");
			data.put("deviceId"+i,device[i]);
			data.put("jsonFile"+i, device[i]+".json");
			File dir = new File(device[i]+"_Grid");
			dir.mkdir();
			data.put("Gridfolder" + i, device[i] + "_Grid");

			if (i == 0) {
				grid.stopPort("4723");
			} else {
				int portnum = 4723 + i * 10;
				String portToStop = portnum + "";
				grid.stopPort(portToStop);
			}
		}

		// This for loop is till i equals numberofDevices and above for loop is for i equals numberofdevices-1 		
		for (int i = 0; i <= numberOfDevices; i++) {
			if (i == 0) {
				command[i] = "java -jar "
						+ commonResources
						+ "/jars/selenium-server-standalone-2.47.1.jar -host 127.0.0.1 -port 4444 -role hub";
			} else {
				int j = i - 1;
//				String bp=2000*i+"";
				int freePort = this.getFreePort();
				String bp=freePort+"";
				//grid.stopPort(bp);
				if(SystemUtils.IS_OS_WINDOWS){
				command[i] = appiumDirectory + "/node.exe " + appiumDirectory
						+ "/node_modules/appium/bin/appium.js --session-override --nodeconfig "
						+ directory + "/" + data.get("jsonFile" + j) + " -p "
						+ data.get("port" + j).trim()+" -bp " +bp+ " -U "
						+ data.get("deviceId" + j) + " --tmp " + directory
						+ "/" + data.get("Gridfolder" + j);
				}
				else{
					command[i] = appiumDirectory + "/node " + appiumDirectory
							+ "/appium --session-override --full-reset --nodeconfig "
							+ directory + "/" + data.get("jsonFile" + j) + " -p "
							+ data.get("port" + j).trim()+" -bp " +bp+ " -U "
							+ data.get("deviceId" + j) + " --tmp " + directory
							+ "/" + data.get("Gridfolder" + j)+" --log " + directory
							+ "/" + data.get("Gridfolder" + j)+"/appium-logs";
				}

			}
			final String commands = command[i].toString();
			final int j = i;

			threads[i] = new Thread() {
				public void run() {

					try {
						grid.StartGridHubAndNode(commands, j);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			threads[i].start();
		}
	}

	/* Function :- This will make json of devices.   
	 * author: Rajat Jain, date: 08-April-2016
	 */

	public void makeJson(int hubPort, String ip, int nodePort, String deviceId,
			String FileName) throws FileNotFoundException, IOException {
		APIFunctions api = new APIFunctions();
		JsonObject json = new JsonObject();
		JsonObject json2 = new JsonObject();
		JsonArray json3 = new JsonArray();
		JsonObject json5 = new JsonObject();
		String url = "http://" + ip + ":" + nodePort + "/wd/hub";
		String hub = "http://" + ip + ":" + hubPort + "/grid/register/";

		json.addProperty("cleanUpCycle", 2000);
		json.addProperty("timeout", 30000);
		json.addProperty("browserTimeout", 30000);
		json.addProperty("proxy",
				"org.openqa.grid.selenium.proxy.DefaultRemoteProxy");

		json.addProperty("url", url);
		json.addProperty("hub", hub);
		json.addProperty("host", ip);
		json.addProperty("port", nodePort);
		json.addProperty("maxSession", 1);
		json.addProperty("register", true);
		json.addProperty("registerCycle", 5000);
		json.addProperty("hubPort", hubPort);
		json.addProperty("hubHost", ip);

		JsonObject json1 = api.MakeJsonObjectOfKeyAndJsonObject(
				"configuration", json);
		json2.addProperty("browserName", "Android");
		json2.addProperty("deviceName", deviceId);
		String version;
		//json2.addProperty("version", "4.3");
		json2.addProperty("maxInstances", 1);
		json2.addProperty("platform", "Android");
		json2.addProperty("seleniumProtocol", "WebDriver");
		json2.addProperty("newCommandTimeout", 300);
		json3.add(json2);
		JsonObject json4 = api.MakeJsonObjectOfKeyAndJsonArray("capabilities",
				json3);
		String finalJson = api.mergeJsonObjects(json4, json1);
		// System.out.println(json5);
		String directory;
		directory = System.getProperty("user.dir");
		directory = directory.replace("\\", "/");
		writeFile(directory + "/" + FileName, finalJson);

	}


	/* Function :- Used in getipaddress function for getting ip  
	 * author: Rajat Jain, date: 08-April-2016
	 */
	public String displayInterfaceInformation(NetworkInterface netint)
			throws SocketException {

		Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();

		// Realtek PCIe GBE Family Controller
		if (netint.getDisplayName().contains("WiFi")) {
			for (InetAddress inetAddress : Collections.list(inetAddresses)) {
				return inetAddress + "";
				// out.printf("InetAddress: %s\n", inetAddress);
			}
			return "";
		} else if (netint.getDisplayName().contains(
				"Realtek PCIe GBE Family Controller")) {
			for (InetAddress inetAddress : Collections.list(inetAddresses)) {
				return inetAddress + "";
				// out.printf("InetAddress: %s\n", inetAddress);
			}
			return "";
		} else {
			return "";
		}
	}


}
