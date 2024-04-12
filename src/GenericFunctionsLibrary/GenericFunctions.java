package GenericFunctionsLibrary;

import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import stf.ConnectSTF;

public class GenericFunctions {

	@SuppressWarnings("rawtypes")
	public AndroidDriver driver;
	public String browserType = "";
	public String winHandle;
	public WebDriver webDriver;
	public AppiumDriverLocalService service;
	public int port;

	public GenericFunctions() {

	}

	@SuppressWarnings("rawtypes")
	public GenericFunctions(AndroidDriver driver) {
		this.driver = driver;
	}

	public WebDriver StartBrowser(String browserType) {

		ThreadLocal<RemoteWebDriver> threadDriver = null;

		threadDriver = new ThreadLocal<RemoteWebDriver>();
		if (browserType.trim().equalsIgnoreCase("")) {
			System.out.println("Kindly set the 'browserType' variable before calling this function");
			return driver;
		}

		if (browserType.equalsIgnoreCase("FF")) {
			webDriver = new FirefoxDriver();
		} else if (browserType.startsWith("FF")) {
			String BrowserVersion = browserType.split("FF")[1].trim();
			FirefoxProfile profile = new FirefoxProfile();
			webDriver = new FirefoxDriver();
		}

		if (browserType.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Common_Resources");
			webDriver = new ChromeDriver();

			/*
			 * DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			 * System.out.println( "oneone"); try {
			 * 
			 * webDriver=new RemoteWebDriver(new URL("http://localhost:4448/wd/hub"),
			 * capabilities); ((RemoteWebDriver) driver).setFileDetector(new
			 * LocalFileDetector()); SetImplicitWaitInSeconds(30);
			 * threadDriver.set((RemoteWebDriver) driver); } catch (MalformedURLException e)
			 * { // TODO Auto-generated catch block
			 * 
			 * e.printStackTrace(); }
			 */

		} else if (browserType.startsWith("MacChrome")) {
			System.setProperty("webdriver.chrome.driver", "/usr/local/Jenkins_Resources/chromedriver");
			// ChromeOptions options = new ChromeOptions();
			// options.setBinary("/usr/local/Jenkins_Resources/ChromeVersions/Chrome" +
			// BrowserVersion);
			// System.out.println("/usr/local/Jenkins_Resources/ChromeVersions/Chrome" +
			// BrowserVersion);
			// DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			// capabilities.setCapability(ChromeOptions.CAPABILITY,options);
			// capabilities.setCapability("chrome.binary",
			// "/usr/local/Jenkins_Resources/ChromeVersions/Chrome"+BrowserVersion);
			webDriver = new ChromeDriver();
		}

		if (browserType.equalsIgnoreCase("IE32")) {
			System.setProperty("webdriver.ie.driver", "C:/workspace/jars/IEDriverServer32.exe");
			webDriver = new InternetExplorerDriver();
		}

		if (browserType.equalsIgnoreCase("IE64")) {
			System.setProperty("webdriver.ie.driver", "C:/workspace/jars/IEDriverServer64.exe");
			webDriver = new InternetExplorerDriver();
		}

		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return webDriver;

	}

	public WebDriver StartBrowserUbntu(String driverPath) {
		System.setProperty("webdriver.chrome.driver", driverPath);
		webDriver = new ChromeDriver();
		return webDriver;

	}

	public static String getDeviceIDFromConfigFile(String deviceName) {
		Xls_Reader xls = new Xls_Reader(System.getenv("COMMON_RESOURCES") + "/deviceMapping.xls");
		int rowNum = xls.getCellRowNum("devices", "deviceName", deviceName);
		String deviceID = xls.getCellData("devices", "deviceID", rowNum);
		System.out.println("For device name '" + deviceName + "' found device ID as '" + deviceID + "'");
		return deviceID;
	}

	/***********************************************************************************************
	 * Function Description : To start grid through build.xml
	 *********************************************************************************************/
	public static void main(String args[]) {
		GenericFunctions generic = new GenericFunctions();

	

		int numberOfDevicesConnected = generic.getconnectedDevicesNumber();
		// String mode = args[0];
		String enteredDeviceNames = args[0];
		// System.out.println("mode"+mode);
		List<String> devices = new ArrayList<String>();
		int numberOfDevices;
		if (enteredDeviceNames == null) {
			devices = generic.getConnectedDevicesList();
			numberOfDevices = devices.size();
			numberOfDevicesConnected = numberOfDevices;
		} else {
			System.out.println("Entered Device Names" + enteredDeviceNames);
			String[] devicesArray = enteredDeviceNames.split(",");
			List<String> devicesNames = new ArrayList<String>();
			Collections.addAll(devicesNames, devicesArray);
			numberOfDevices = devicesNames.size();
			for (String deviceName : devicesNames) {
				String deviceID = getDeviceIDFromConfigFile(deviceName);
				if (deviceID.equals("")) {
					System.out.println("ERROR!!! : Entered device name '" + deviceName
							+ "' not found. if you are using a new device add it to device Mapping excel sheet in Common Resources folder. Exiting....");
					System.exit(3);
				}
				devices.add(deviceID);
				ConnectSTF stfConnect = new ConnectSTF();
				stfConnect.connectToStfDevice(deviceID);
			}
		}

		setPropertyValue("numberOfDevices", numberOfDevices + "");

		if (numberOfDevices == 0) {
			System.out.println("ERROR!!! : 'devices' value not set. It should be set to 1 or more. Exiting....");
			System.exit(1);
		} else if (numberOfDevices > numberOfDevicesConnected) {
			System.out.println("ERROR!!! : Wanted number of devices are " + numberOfDevices
					+ ". But actual connected are only " + numberOfDevicesConnected + " . Exiting....");
			System.exit(2);
		}
		if (numberOfDevices == 0) {
			System.out.println("Number of connected devices = " + numberOfDevices);
			return;
		} else if (numberOfDevices == 1) {
			System.out.println("Executing in Single Device mode");
			setPropertyValue("connectedDevice", devices.get(0));
		} else {
			System.out.println("Executing in Grid mode");
			System.out.println("Number of Connected devices = " + numberOfDevicesConnected);
			System.out.println("Number of devices on which automation needs to run on = " + numberOfDevices);
			GridDrivers grid = new GridDrivers();
			try {
				grid.StartGrid(devices);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String convertYYYY_MM_DD_To_DD_MMM(String YYYY_MM_DD) {
		// DateFormat format = DateFormat.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd MMM");
		Date date = null;
		try {
			date = format1.parse(YYYY_MM_DD);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return format2.format(date);
	}

	public String getCurrentTimeStampInDD_MMMFormat() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM"); // dd/MM/yyyy

		Date now = new Date();
		String strDate = sdfDate.format(now);
		System.out.println(strDate);
		return strDate;

	}

	public String ConvertStringMonth_Numeric(String month) throws ParseException {
		Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int numericMonth = cal.get(Calendar.MONTH);
		numericMonth++;
		if (numericMonth < 10) {
			return "0" + numericMonth;
		} else {
			return numericMonth + "";
		}

	}

	/***********************************************************************************************
	 * Function Description : Method to get value set in config.properties file
	 *********************************************************************************************/

	public String getPropertyValue(String propertyName) {

		String directory = System.getProperty("user.dir");
		String propFileName = directory + "/Config.properties";
		File file = new File(propFileName);
		FileInputStream fileInput = null;
		Properties prop = new Properties();
		String propertyValue = "";

		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		propertyValue = prop.getProperty(propertyName);

		return propertyValue;
	}

	/***********************************************************************************************
	 * Function Description : Method to get value set in config.properties file
	 * 
	 * @throws IOException
	 *********************************************************************************************/
	public static void setPropertyValue(String propertyKey, String propertyValue) {

		String directory = System.getProperty("user.dir");
		File configfile = new File(directory + "/Config.properties");
		try {
			FileWriter fileWritter = new FileWriter(configfile, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.newLine();
			bufferWritter.append(propertyKey + "=" + propertyValue);
			bufferWritter.close();
			fileWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***********************************************************************************************
	 * Function Description : Method to delete value set in config.properties file
	 *********************************************************************************************/

	public void deletePropertyValue(String propertyName) {

		String directory = System.getProperty("user.dir");
		String propFileName = directory + "/Config.properties";
		File file = new File(propFileName);
		FileInputStream fileInput = null;
		Properties prop = new Properties();
		String propertyValue = "";

		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		prop.remove(propertyName);
	}

	/***********************************************************************************************
	 * Function Description : Sets implicit Wait by accepting timeout in seconds
	 * author: Tarun Narula, date: 25-Feb-2013
	 *********************************************************************************************/

	public String SetImplicitWaitInSeconds(int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		return "Timeout set to " + timeOut + " seconds.";
	}

	public void GoToSleep(int TimeInMillis) {
		try {
			Thread.sleep(TimeInMillis);
		} catch (Exception e) {
		}
	}

	/***********************************************************************************************
	 * Function Description : Wait till invisibility of an element author: Rajat
	 * Jain, date: 09-April-2015
	 *********************************************************************************************/
	public void WaitUntilInvisibilityOfElement(By element) {
		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

			wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
		} catch (Exception e) {
		}
	}

	/***********************************************************************************************
	 * Function Description : Wait till visibility of an element author: Rajat Jain,
	 * date: 09-April-2015
	 *********************************************************************************************/
	public void WaitUntilVisibilityOfElement(By element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		} catch (Exception e) {
		}
	}

	/***********************************************************************************************
	 * Function Description : Sets implicit Wait by accepting timeout in
	 * milliseconds author: Tarun Narula, date: 25-Feb-2013
	 *********************************************************************************************/

	public String SetImplicitWaitInMilliSeconds(int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.MILLISECONDS);
		return "Timeout set to " + timeOut + " milli seconds.";
	}

	/***********************************************************************************************
	 * Function Description : Initiates the appium server via commandline
	 * 
	 * @author rashi.atry, date: 19-May-2014
	 * @throws IOException, InterruptedException
	 *********************************************************************************************/

	public void startAppiumServer(int port) throws IOException, InterruptedException {

		if (this.getconnectedDevicesNumber() > 0) {

			Runtime.getRuntime().exec("cmd.exe");
			CommandLine command = new CommandLine("cmd");
			command.addArgument("/c");
			command.addArgument("C:/Program Files/nodejs/node.exe");
			command.addArgument("C:/Users/kanishka.mogha/node_modules/appium/lib/appium.js");
			command.addArgument("--address", false);
			command.addArgument("127.0.0.1");
			command.addArgument("--port", false);
			command.addArgument(Integer.toString(port));
			command.addArgument("--full-reset", false);
			command.addArgument("--command-timeout", false);
			command.addArgument("60");
			command.addArgument("--full-reset", false);
			command.addArgument("-U");
			command.addArgument(getPropertyValue("connectedDevice"));

			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);
			executor.execute(command, resultHandler);

			Thread.sleep(5000);
			System.out.println("------------>>> Appium server started");
		} else if (this.getconnectedDevicesNumber() <= 0) {

			Assert.assertTrue(false, "Device Not Found");
			System.out.println("----------->>> Device Not Found");
		}

	}

	public void startAppiumServerWear(String portNumber) throws IOException, InterruptedException {

		if (this.getconnectedDevicesNumber() > 0) {

			Runtime.getRuntime().exec("cmd.exe");
			CommandLine command = new CommandLine("cmd");
			command.addArgument("/c");
			command.addArgument("C:/nodejs/node.exe");
			command.addArgument("C:/Appium/node_modules/appium/bin/appium.js");
			command.addArgument("--address", false);
			command.addArgument("127.0.0.1");
			command.addArgument("--port", false);
			command.addArgument(portNumber);
			command.addArgument("--command-timeout", false);
			command.addArgument("120");
			command.addArgument("--full-reset", false);

			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);
			executor.execute(command, resultHandler);

			Thread.sleep(5000);
			System.out.println("------------>>> Appium server started");
		} else if (this.getconnectedDevicesNumber() <= 0) {

			Assert.assertTrue(false, "Device Not Found");
			System.out.println("----------->>> Device Not Found");
		}
	}

	/***********************************************************************************************
	 * Function Description : Initiates the app with all the settings and main
	 * package and Activity are declared here
	 * 
	 * @author rashi.atry, date: 29-Oct-2014
	 * @throws Exception
	 *********************************************************************************************/

	public AndroidDriver StartDriverAndroidApp(String appLocation, String appPackage, String appActivity)
			throws Exception {

		System.out.println("This is the device mapping file path: " + System.getenv("COMMON_RESOURCES"));
		System.out.println("New Jar");
		String Platform = "";
		String AppiumHost = "";
		GridDrivers grid = new GridDrivers();
		ServerSocket se = new ServerSocket(0);
		System.out.println("listening on port: " + se.getLocalPort());
		port = se.getLocalPort();
		String ip = "0.0.0.0";// grid.GetIpAddress();
		int numberOfDevices = getconnectedDevicesNumber();
		// int numberOfDevices = Integer.parseInt(getPropertyValue("numberOfDevices"));
		System.out.println("Checking which OS we are running on!");
		if (SystemUtils.IS_OS_WINDOWS) {
			System.out.println("It's Windows.");
			if (numberOfDevices == 0) {
				System.out.println("Number of connected devices is ZERO");
				return null;
			} else if (numberOfDevices == 1) {
				ip = "127.0.0.1";
				// GoToSleep(5000);
				Platform = "Android";
				AppiumHost = "http://" + ip + ":4723/wd/hub";
				// startAppiumServer(port);
				// C:\Users\kanishka.mogha\node_modules\appium\lib
				// service = new AppiumServiceBuilder().usingDriverExecutable(new
				// File("C:/Program Files/nodejs/node.exe"))
				// .withAppiumJS(new
				// File("C:/Users/kanishka.mogha/node_modules/appium/lib/appium.js")).withIPAddress("127.0.0.1").usingAnyFreePort().build();
				// service.start();
				// AppiumHost = service.getUrl().toString();
				System.out.println("After start");
			}

			else {

				System.out.println("In grid");

				Platform = "Android";
				AppiumHost = "http://" + ip + ":4444/wd/hub";
			}
		} else if (SystemUtils.IS_OS_MAC) {

			try {
				service = new AppiumServiceBuilder().usingDriverExecutable(new File("/usr/local/bin/node"))
						.withAppiumJS(new File("/usr/local/bin/appium")).withIPAddress("127.0.0.1").usingAnyFreePort()
						.build();
				service.start();
			} catch (Exception e) {
				service = new AppiumServiceBuilder().usingDriverExecutable(new File("/usr/local/bin/node"))
						.withAppiumJS(new File("/opt/homebrew/bin/appium")).withIPAddress("127.0.0.1")
						.usingAnyFreePort().build();
				service.start();
			}
			AppiumHost = service.getUrl().toString();

		}

		else {
			System.out.println("It's Linux.");
			if (numberOfDevices == 0) {
				System.out.println("Number of connected devices is ZERO");
				return null;
			} else if (numberOfDevices == 1) {
				service = new AppiumServiceBuilder().usingDriverExecutable(new File("/usr/bin/node"))
						.withAppiumJS(new File("/usr/local/bin/appium")).withIPAddress("127.0.0.1").usingAnyFreePort()
						.build();

				service.start();
				AppiumHost = service.getUrl().toString();

			} else {

				System.out.println("In grid");

				Platform = "Android";
				AppiumHost = "http://" + ip + ":4444/wd/hub";
			}
		}

		System.out.println("Launching app...");
		runtimeExec("adb uninstall io.appium.uiautomator2.server");
		runtimeExec("adb uninstall io.appium.uiautomator2.server.test");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		System.out.println("==set browser==");
		capabilities.setCapability("platformName", org.openqa.selenium.Platform.ANDROID);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		System.out.println("==set device==");
		capabilities.setCapability(MobileCapabilityType.APP, appLocation);
		capabilities.setCapability("appPackage", appPackage);
		capabilities.setCapability("appActivity", appActivity);
		capabilities.setCapability("automationName", "UiAutomator2");
		capabilities.setCapability("fullReset", true);
		capabilities.setCapability("skipDeviceInitialization", true);
		capabilities.setCapability("autoGrantPermissions", "false");

		System.out.println("==set app==");
		try {
			driver = new AndroidDriver(new URL(AppiumHost), capabilities);
		} catch (Exception s) {
			GoToSleep(5000);
			System.out.println("Unable to launch App...Retrying...");
			driver = new AndroidDriver(new URL(AppiumHost), capabilities);
		}
		driver.manage().timeouts().implicitlyWait(120000, TimeUnit.MILLISECONDS);
		System.out.println("==========complete launchApp========");
		SetImplicitWaitInSeconds(2);

		return driver;
	}


	public void startAppium(String comand) {
		Runtime rt = Runtime.getRuntime();
		RuntimeExec rte = new RuntimeExec();
		GenericFunctionsLibrary.GenericFunctions.RuntimeExec.StreamWrapper error, output;

		try {
			Process proc = rt.exec(comand);
			error = rte.getStreamWrapper(proc.getErrorStream(), "ERROR");
			output = rte.getStreamWrapper(proc.getInputStream(), "OUTPUT");
			// int exitVal = 0;

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String s;
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
				if (s.contains("Appium REST http")) {
					break;
				}
			}
			error.start();
			output.start();
			error.join(3000);
			output.join(3000);
			// exitVal = proc.waitFor();
			System.out.println("Output: " + output.message + "\nError: " + error.message);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class RuntimeExec {
		public StreamWrapper getStreamWrapper(InputStream is, String type) {
			return new StreamWrapper(is, type);
		}

		private class StreamWrapper extends Thread {
			InputStream is = null;
			@SuppressWarnings("unused")
			String type = null;
			String message = null;

			StreamWrapper(InputStream is, String type) {
				this.is = is;
				this.type = type;
			}

			public void run() {
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					StringBuffer buffer = new StringBuffer();
					String line = null;
					while ((line = br.readLine()) != null) {
						buffer.append(line);// .append("\n");
					}
					message = buffer.toString();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

		public void startAppiumUbuntu(String comand) {
			Runtime rt = Runtime.getRuntime();
			RuntimeExec rte = new RuntimeExec();
			StreamWrapper error, output;

			try {
				Process proc = rt.exec(comand);
				GoToSleep(5000);
				error = rte.getStreamWrapper(proc.getErrorStream(), "ERROR");
				output = rte.getStreamWrapper(proc.getInputStream(), "OUTPUT");
				// int exitVal = 0;

				BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				String s;
				while ((s = stdInput.readLine()) != null) {
					System.out.println(s);
					if (s.contains("Appium REST http")) {
						break;
					}
				}
				error.start();
				output.start();
				error.join(3000);
				output.join(3000);
				// exitVal = proc.waitFor();
				System.out.println("Output: " + output.message + "\nError: " + error.message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void stopAppiumUbuntu(String comand) {
			Runtime rt = Runtime.getRuntime();
			RuntimeExec rte = new RuntimeExec();
			StreamWrapper error, output;

			try {
				Process proc = rt.exec(comand);
				GoToSleep(5000);
				error = rte.getStreamWrapper(proc.getErrorStream(), "ERROR");
				output = rte.getStreamWrapper(proc.getInputStream(), "OUTPUT");
				error.start();
				output.start();
				error.join(3000);
				output.join(3000);
				if (error.message.equals("") && output.message.equals("")) {
					// closed appium server
				} else if (error.message.contains("No matching processes belonging to you were found")) {
					// Display nothing as no instances of Appium Server were found running
				} else {
					System.out.println("Output: " + output.message + "\nError: " + error.message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/***********************************************************************************************
	 * Function Description : Kill the appium server via commandline
	 * 
	 * @author rashi.atry, date: 19-May-2014
	 * @throws IOException, InterruptedException
	 *********************************************************************************************/

	public void stopAppiumServer(int port) throws IOException {

		Runtime.getRuntime().exec("cmd.exe");
		String AppiumServerPortNumber = Integer.toString(port);// getPropertyValue("port");
		String command = "cmd /c echo off & FOR /F \"usebackq tokens=5\" %a in" + " (`netstat -nao ^| findstr /R /C:\""
				+ AppiumServerPortNumber + "\"`) do (FOR /F \"usebackq\" %b in"
				+ " (`TASKLIST /FI \"PID eq %a\" ^| findstr /I node.exe`) do taskkill /F /PID %a)";

		String s = null;
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read the output from the command
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException e) {
			System.out.println(" ---------->>> Exception happened: ");
			e.printStackTrace();
		}

		System.out.println("------------>>> Appium server stopped");
	}

	public void stopAppiumServerWear(String portNumber) throws IOException {

		Runtime.getRuntime().exec("cmd.exe");
		String AppiumServerPortNumber = portNumber;
		String command = "cmd /c echo off & FOR /F \"usebackq tokens=5\" %a in" + " (`netstat -nao ^| findstr /R /C:\""
				+ AppiumServerPortNumber + "\"`) do (FOR /F \"usebackq\" %b in"
				+ " (`TASKLIST /FI \"PID eq %a\" ^| findstr /I node.exe`) do taskkill /F /PID %a)";

		String s = null;
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read the output from the command
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException e) {
			System.out.println(" ---------->>> Exception happened: ");
			e.printStackTrace();
		}

		System.out.println("------------>>> Appium server stopped");
	}

	/***********************************************************************************************
	 * Function Description : Stops the driver
	 * 
	 * @author rashi.atry, date: 25-May-2015
	 * @throws Exception
	 *********************************************************************************************/

	public String StopDriver(String appPackage) {
		System.out.println("Stopping driver...");

		driver.removeApp(appPackage);
		driver.quit();
		return ("------------>>> Browser closed");

		// driver.removeApp(appPackage);
		//
		// int numberOfDevices = getconnectedDevicesNumber();
		// if (SystemUtils.IS_OS_WINDOWS) {
		// if (numberOfDevices == 1) {
		// try {
		// stopAppiumServer(port);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// System.out.println("stopped driver");
		// }
		// } else if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX) {
		// service.stop();
		// try {
		// Runtime.getRuntime().exec("netstat -vanp tcp | grep " + port);
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		// } else {
		// if (numberOfDevices == 1) {
		// RuntimeExec appiumObj = new RuntimeExec();
		// GridDrivers grid = new GridDrivers();
		// grid.stopPort(Integer.toString(port));
		// // appiumObj.stopAppiumUbuntu("killall -9 node");
		// System.out.println("stopped driver.");
		// }
		// }
		//
		// return ("------------>>> Browser closed");
	}

	public String StopDriverWear(String appPackage, String portNumber) {

		driver.removeApp(appPackage);

		try {
			stopAppiumServerWear(portNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ("------------>>> Browser closed");
	}

	/***********************************************************************************************
	 * Function Description : Takes ScreenShot and returns the screenshot name in
	 * PNG author: Surbhit Aggarwal, date: 11-Nov-2022
	 *********************************************************************************************/

	public String TakeScreenshot() throws IOException {
		String directory = System.getProperty("user.dir");
		directory = directory.replace("\\", "\\\\");
		String SaveName = Calendar.getInstance().getTime().toString().replace(":", "").replace(" ", "").trim();
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile,
					new File(directory + File.separator + "screenshots" + File.separator + SaveName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String directoryT = System.getProperty("user.dir");
		String filename = directoryT + File.separator + "screenshots" + File.separator + SaveName + ".png";
		System.out.println(filename);
		this.conversion(filename, filename);
		return filename;
	}

	public void conversion(String inputFile, String outputFile) throws IOException {
		// read a jpeg from a inputFile
		BufferedImage bufferedImage = ImageIO.read(new File(inputFile));

		// write the bufferedImage back to outputFile
		ImageIO.write(bufferedImage, "png", new File(outputFile));

	}

	/***********************************************************************************************
	 * Function Description : Accepts the alert box message author: Tarun Narula,
	 * date: 25-Feb-2013
	 *********************************************************************************************/
	public String AlertBox_Accept() {
		// Get a handle to the open alert, prompt or confirmation
		Alert alert = driver.switchTo().alert();
		// And acknowledge the alert (equivalent to clicking "OK")
		alert.accept();
		return ("accepted");
	}

	/***********************************************************************************************
	 * Function Description : Dismisses the alert box message author: Tarun Narula,
	 * date: 25-Feb-2013
	 *********************************************************************************************/
	public String AlertBox_Dismiss() {
		// Get a handle to the open alert, prompt or confirmation
		Alert alert = driver.switchTo().alert();
		// And acknowledge the alert (equivalent to clicking "cancel")
		alert.dismiss();
		return ("Alert '" + alert.getText() + "' dismissed");
	}

	/***********************************************************************************************
	 * Function Description : gets the handle for the current window author: Tarun
	 * Narula, date: 25-Feb-2013
	 *********************************************************************************************/
	public void GetWindowHandle() {
		winHandle = driver.getWindowHandle();
	}

	/***********************************************************************************************
	 * Function Description : Switches to the most recent window opened author:
	 * Tarun Narula, date: 25-Feb-2013
	 *********************************************************************************************/
	public void SwitchtoNewWindow() {
		for (String windowsHandle : driver.getWindowHandles()) {
			driver.switchTo().window(windowsHandle);
		}
	}

	/***********************************************************************************************
	 * Function Description : Closes the window author: Tarun Narula, date:
	 * 25-Feb-2013
	 *********************************************************************************************/
	public void CloseNewWindow() {
		driver.close();
	}

	/***********************************************************************************************
	 * Function Description : Switches back to original window author: Tarun Narula,
	 * date: 25-Feb-2013
	 *********************************************************************************************/
	public void SwitchtoOriginalWindow() {
		driver.switchTo().window(winHandle);
	}

	/***********************************************************************************************
	 * Function Description : author: Ankit Choudhary, date: 5-Apr-2013
	 *********************************************************************************************/

	public String CompareTwoGivenCommaSeperatedList(String ListA, String ListB) {
		String result = "";
		String ListAresult = "";
		String ListBresult = "";
		if (!ListA.contains(",") && !ListB.contains(",")) {
			if (ListA.trim().equals(ListB.trim())) {
				return "Pass";
			} else {
				return "Fail";
			}
		}
		String[] ListArray1 = ListA.split(",");
		String[] ListArray2 = ListB.split(",");
		String tokenA;
		String tokenB;

		for (int i = 0; i < ListArray1.length; i++) {
			tokenA = ListArray1[i];

			for (int j = 0; j < ListArray2.length; j++) {
				if (tokenA.trim().equals(ListArray2[j].trim())) {
					break;
				} else if (j == ListArray2.length - 1) {
					ListAresult = ListAresult + "::" + tokenA;
				}
			}

		}

		for (int i = 0; i < ListArray2.length; i++) {
			tokenB = ListArray2[i];

			for (int j = 0; j < ListArray1.length; j++) {
				if (tokenB.trim().equals(ListArray1[j].trim())) {
					break;
				} else if (j == ListArray1.length - 1) {
					ListBresult = ListBresult + "::" + tokenB;
				}
			}

		}

		if (ListAresult.equals("") && ListBresult.equals("")) {
			result = "Lists are equal so Pass";
		} else {
			result = "Extra in List A =>" + ListAresult + "  Extra in List B =>" + ListBresult;
		}
		System.out.println(result);
		return result;
	}

	/***********************************************************************************************
	 * Function Description : It Checks The Presence of a element on page of given
	 * path author: Ankit Choudhary, date: 11-Apr-2013
	 *********************************************************************************************/

	public boolean isPresent(By element) {

		SetImplicitWaitInMilliSeconds(500);
		if (driver.findElements(element).size() != 0) {
			SetImplicitWaitInMilliSeconds(10000);
			return true;
		} else {
			SetImplicitWaitInMilliSeconds(10000);
			return false;
		}

	}

	/***********************************************************************************************
	 * Function Description : Checks Presence and Visibility of an element on page
	 * of given path using id of element
	 * 
	 * @author rashi.atry, date: 27-Nov-2014
	 *********************************************************************************************/

	public Boolean isVisible(By element) {
		try {

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/***********************************************************************************************
	 * Function Description : Checks Presence and Visibility of an element on page
	 * of given path using id of element with custom timeout
	 * 
	 * @author rashi.atry, date: 27-Nov-2014
	 *********************************************************************************************/

	public Boolean isVisible(By element, int timeOutInSec) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSec));
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String CheckMaxLimit(By element, int ExpectedMaxLimit) {

		for (int count = 1; count <= ExpectedMaxLimit + 5; count++) {
			driver.findElement(element).sendKeys("1");
		}

		int ActualMaxLimit = driver.findElement(element).getAttribute("value").length();
		if (ActualMaxLimit == ExpectedMaxLimit) {
			return "Pass";
		} else {
			return "Fail";
		}

	}

	/***********************************************************************************************
	 * Function Description : It Generates Name using timestamp author: Ankit
	 * Choudhary, date: 20-May-2013
	 *********************************************************************************************/

	public String GetNameAsCurrentTimeStamp() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Calendar.getInstance().getTime().toString().replaceAll(":", "").replace(" ", "");
	}

	/***********************************************************************************************
	 * Function Description : It returns a unique name derived from Timestamp
	 * author: Tarun Narula, date: 5-May-2013
	 *********************************************************************************************/

	public String getNameAsTimeStamp() {
		String TimeStamp = Calendar.getInstance().getTime().toString();
		TimeStamp = TimeStamp.replace(":", "").replace(" ", "").replace("+", "");
		return TimeStamp;
	}

	/***********************************************************************************************
	 * Function Description : It returns the value for the attribute specified
	 * author: Tarun Narula, date: 14-Jun-2013
	 *********************************************************************************************/

	public String getAttribute(By element, String attributeName) {
		return driver.findElement(element).getAttribute(attributeName);
	}

	/***********************************************************************************************
	 * Function Description : It Checks a specific value is present in given list
	 * author: Ankit Choudhary, date: 23-July-2013
	 *********************************************************************************************/

	public Boolean CheckExistenceOfAValueInAList(String Value, List<String> InputList) {
		Boolean result = false;
		for (String option : InputList) {
			if (option.trim().equals(Value.trim())) {
				result = true;
				break;
			}

		}

		return result;
	}

	/***********************************************************************************************
	 * Function Description : Set/Write String List at given excel path sheetname
	 * and column. author: Omkar, date: 13-Jun-2013
	 *********************************************************************************************/
	public void SetStringListInXLColumn(Xls_Reader datatable, String sheetname, String colname, List<String> list) {

		for (int i = 0; i < list.size(); i++) {
			datatable.setCellData(sheetname, colname, i + 2, list.get(i));
		}

	}

	/***********************************************************************************************
	 * Function Description : Compares two provided xl columns and set the status
	 * author: Omkar, date: 13-Jun-2013
	 *********************************************************************************************/
	public void CompareTwoXLColumns(Xls_Reader datatable, String sheetname, String colname1, String colname2,
			String statuscol) {
		for (int i = 2; i < datatable.getRowCount(sheetname) + 1; i++) {
			if (datatable.getCellData(sheetname, colname1, i).equals(datatable.getCellData(sheetname, colname2, i))) {
				datatable.setCellData(sheetname, statuscol, i, "pass");
			} else {
				datatable.setCellData(sheetname, statuscol, i, "fail");
			}
		}
	}

	public void CompareTwoXLColumnsSaveResultInOtherXL(Xls_Reader datatable, String sheetname, String resultsheetname,
			String colname1, String colname2, String statuscol) {
		String col1 = "";
		String col2 = "";

		for (int i = 2; i < datatable.getRowCount(sheetname) + 1; i++) {
			col1 = datatable.getCellData(sheetname, colname1, i);
			col2 = datatable.getCellData(sheetname, colname2, i);
			if (col1.equals(col2)) {
				datatable.setCellData(resultsheetname, statuscol, i, "pass");
			} else {
				datatable.setCellData(resultsheetname, statuscol, i, "fail : => " + col1 + " " + col2 + "");
			}
		}
	}

	public void CompareTwoDifferentXLColumnsSaveResultInOtherXL(Xls_Reader datatable, String sheetname1,
			String sheetname2, String resultsheetname, String colname1, String colname2, String statuscol) {
		String col1 = "";
		String col2 = "";

		for (int i = 2; i < datatable.getRowCount(sheetname1) + 1; i++) {
			col1 = datatable.getCellData(sheetname1, colname1, i);
			col2 = datatable.getCellData(sheetname2, colname2, i);
			if (col1.equals(col2)) {
				datatable.setCellData(resultsheetname, statuscol, i, "pass");
			} else {
				datatable.setCellData(resultsheetname, statuscol, i, "fail : => " + col1 + " " + col2 + "");
			}
		}
	}

	/***********************************************************************************************
	 * Function Description : Will go to provided div/span/xpath and fetch the
	 * attribute of provided tagname Example: Fetching links text from a provided
	 * div: input required div xpath, tagname as "a" and attribute as"text" Example:
	 * Fetching links url from a provided div: input required div xpath, tagname as
	 * "a" and attribute as"href" author: Omkar, date: 13-Jun-2013
	 *********************************************************************************************/
	public List<String> FetchFromParent(By parent_element, String tagname, String attribute) {

		ArrayList<String> list = new ArrayList<String>();
		List<WebElement> elements = driver.findElement(parent_element).findElements(By.tagName(tagname));
		for (int i = 0; i < elements.size(); i++) {
			if (attribute.equalsIgnoreCase("text"))
				list.add(elements.get(i).getText());
			else
				list.add(elements.get(i).getAttribute(attribute));
		}
		return list;
	}

	/***********************************************************************************************
	 * Function Description : Navigate through all pages from SRP and fetches
	 * provided attribute and return all attributes as list author: Omkar, date:
	 * 13-Jun-2013
	 *********************************************************************************************/
	@SuppressWarnings("unchecked")
	public List<String> FetchFromSRP(By by_whatToFetch, By by_nextbuttonONSRP, String attributeToFetch) {

		ArrayList<String> list = new ArrayList<String>();
		boolean pagesDone = false;
		while (pagesDone == false) {

			List<WebElement> we = driver.findElements(by_whatToFetch);
			for (int i = 0; i < we.size(); i++) {
				if (attributeToFetch.equalsIgnoreCase("text"))
					list.add(we.get(i).getText());
				else
					list.add(we.get(i).getAttribute(attributeToFetch));

			}

			if (isVisible(by_nextbuttonONSRP)) {
				driver.findElement(by_nextbuttonONSRP).click();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				pagesDone = true;
			}

		}
		return list;

	}

	/***********************************************************************************************
	 * Function Description : Compares two given String Lists author: Omkar, date:
	 * 13-Jun-2013
	 *********************************************************************************************/
	public String CompareTwoStringLists(List<String> list1, List<String> list2) {
		String result = "";
		if (list1.size() != list2.size()) {
			return "fail count is not same";
		} else {

			for (int i = 0; i < list1.size(); i++) {
				if (!(list1.get(i).equals(list2.get(i)))) {
					result = result + list1.get(i) + "is not same as:" + list2.get(i) + ", ";
				}

				if (result.equals("")) {
					result = "pass";
				} else {
					return "Fail, " + result;
				}
			}
		}

		return result;
	}

	/***********************************************************************************************
	 * Function Description : Returns the current directory absolute path author:
	 * Ankur, date: 18-Apr-2014
	 *********************************************************************************************/
	public String GetCurrentDirectoryAbsolutePath() {
		String current = "";
		try {
			current = new java.io.File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return current;
	}

	public String convertYYYY_MM_DD_To_MMM_DD_YYYY(String YYYY_MM_DD) {
		// DateFormat format = DateFormat.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MMM dd yyy");
		Date date = null;
		try {
			date = format1.parse(YYYY_MM_DD);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return format2.format(date);
	}

	@SuppressWarnings("deprecation")
	public String SetKIOSKEmailasCurrentTimeStamp() {
		String Email = "";
		/*
		 * String Date = String.valueOf(Calendar.getInstance().getTime().getDate());
		 * String Month = String.valueOf(Calendar.getInstance().getTime().getMonth());
		 * String Year =
		 * String.valueOf(Calendar.getInstance().getTime().getYear()+1900); String Hours
		 * = String.valueOf(Calendar.getInstance().getTime().getHours()); String Minutes
		 * = String.valueOf(Calendar.getInstance().getTime().getMinutes()); String
		 * Seconds = String.valueOf(Calendar.getInstance().getTime().getSeconds());
		 * 
		 * if(Date.length() < 2) Date = "0" + Date;
		 * 
		 * if(Month.length() < 2) Month = "0" + Month;
		 * 
		 * if(Year.length() < 4) Year = "0000";
		 * 
		 * if(Hours.length() < 2) Hours = "0" + Hours;
		 * 
		 * if(Minutes.length() < 2) Minutes = "0" + Minutes;
		 * 
		 * if(Seconds.length() < 2) Seconds = "0" + Seconds;
		 * 
		 * Email = "KIOSK-" + Date + Month + Year + "-" + Hours + Minutes + Seconds +
		 * "@yopmail.com";
		 */
		Email = "KIOSK-" + Calendar.getInstance().getTimeInMillis() + "@yopmail.com";

		return Email;
	}

	/***********************************************************************************************
	 * Function Description : To get list of all connected Android devices (with
	 * UDIDs of Android Devices)
	 * 
	 * @author rashi.atry, date: 19-Oct-2015
	 *********************************************************************************************/

	public List<String> getConnectedDevicesList() {

		List<String> devicesID = new ArrayList<String>();

		String command = "adb devices";
		try {
			Process process = Runtime.getRuntime().exec(command);

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String s;
			while ((s = reader.readLine()) != null) {
				if (s.contains("device") && !s.contains("attached")) {
					String[] device = s.split("\t");
					devicesID.add(device[0]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return devicesID;
	}

	/***********************************************************************************************
	 * Function Description : To get list of all connected Android devices (with
	 * UDIDs of Android Devices)
	 * 
	 * @author rashi.atry, date: 19-Oct-2015
	 *********************************************************************************************/

	public int getconnectedDevicesNumber() {

		int connectDevices;
		connectDevices = this.getConnectedDevicesList().size();

		return connectDevices;
	}

	/***********************************************************************************************
	 * Function Description : To clear cache of app
	 * 
	 * @author rashi.atry, date: 02-Aug-2016
	 *********************************************************************************************/

	public void clearAppCache(String appPackage) {

		String command = "adb shell pm clear " + appPackage;

		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String consoleResponse;
			while ((consoleResponse = reader.readLine()) != null) {
				Assert.assertTrue(consoleResponse.equalsIgnoreCase("success"), "Faill -- couldn't clear app cache");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*********************************************************************
	 * Function Description : To wait for AJAX calls on the page to finish Function
	 * Return Type: void Function Parameters : null
	 ********************************************************/
	public void WaitForAJAX() {
		double sleepValue = 0;
		while (!(Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0")
				&& sleepValue <= 10) {
			GoToSleep(200);
			sleepValue = sleepValue + 0.2;
		}
	}

	/*********************************************************************
	 * Function Description : To wait for all the network calls on the page to end
	 * Function Return Type: void Function Parameters : null
	 ********************************************************/
	public void WaitForCompletePageLoad() {
		int i = 10;

		while (i > 0) {
			if (!((JavascriptExecutor) driver).executeScript("return window.performance.timing.loadEventEnd").toString()
					.equals("0"))
				break;

			GoToSleep(1000);
			i--;
		}
	}

	public String runtimeExec(String command) {
		String s = null;

		try {

			// Process provides control of native processes started by ProcessBuilder.start
			// and Runtime.exec.
			// getRuntime() returns the runtime object associated with the current Java
			// application.
			Process p = Runtime.getRuntime().exec(command);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read the output from the command
			while ((s = stdInput.readLine()) != null) {
				return s;
			}

			// read any errors from the attempted command
			while ((s = stdError.readLine()) != null) {
				return s;
			}

		} catch (IOException e) {
			System.out.println("exception happened - here's what I know: ");
			e.printStackTrace();
		}
		return s;
	}

	// ************************************ BrowserStack changes
	// ************************************

	/*******************************************************************************************************
	 * Function Description : To get BrowserStack Devices List
	 * 
	 * @author : Nancy Date: 28-Apr-2023
	 *******************************************************************************************************/

	public String getBrowserStackDevicesList() {
		Response response = null;
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		String encodeBytes = Base64.getEncoder().encodeToString(
				(getPropertyValue("BrowserStack_Username") + ":" + getPropertyValue("BrowserStack_AccessKey"))
						.getBytes());
		Request request = new Request.Builder().url("https://api-cloud.browserstack.com/app-automate/devices.json")
				.header("Authorization", "Basic " + encodeBytes).get().build();
		try {
			response = client.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*******************************************************************************************************
	 * Function Description : To randomly select a device from a list of
	 * BrowserStack devices
	 * 
	 * @author : Nancy Date: 25-July-2023
	 *******************************************************************************************************/

	public String getRandomlySelectedBrowserStackDevice(String devicesList) {
		List<String> st = Arrays.asList(devicesList.split(","));
		System.out.println("Received devices list = " + st);

		Random random = new Random();
		int randomIndex = random.nextInt(st.size());

		String randomDevice = st.get(randomIndex);

		return randomDevice;
	}

	/*******************************************************************************************************
	 * Function Description : Start Driver method for BrowserStack
	 * 
	 * @author : Nancy Date: 28-Apr-2023
	 *******************************************************************************************************/

	public AndroidDriver StartDriverAndroidAppBrowserStack(String AppId, String local, String BrowserStackDevicesList,
			String geoLocationRequired) throws Exception {

		String entireDeviceInfo = getRandomlySelectedBrowserStackDevice(BrowserStackDevicesList);
		System.out.println("Device selected : " + entireDeviceInfo);
		String[] words = entireDeviceInfo.split("\\|");

		String deviceManufacturer = words[0];

		String OSVersion = words[1];

		DesiredCapabilities capabilities = new DesiredCapabilities();
		HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
		browserstackOptions.put("appiumVersion", "2.0.0");
		browserstackOptions.put("local", local);
		browserstackOptions.put("idleTimeout", "600");
		browserstackOptions.put("deviceLogs", "true");
		browserstackOptions.put("video", "true");
		browserstackOptions.put("debug", "true");
		browserstackOptions.put("networkLogs", "true");
		browserstackOptions.put("appiumLogs", "true");

		if (geoLocationRequired.contentEquals("true")) {

			browserstackOptions.put("geoLocation", "IN");

		}

		capabilities.setCapability("bstack:options", browserstackOptions);

		HashMap<String, Object> networkLogsOptions = new HashMap<String, Object>();
		networkLogsOptions.put("captureContent", true);
		capabilities.setCapability("browserstack.networkLogsOptions", networkLogsOptions);

		capabilities.setCapability("userName", getPropertyValue("BrowserStack_Username"));
		capabilities.setCapability("accessKey", getPropertyValue("BrowserStack_AccessKey"));

		capabilities.setCapability("platformName", "android");
		capabilities.setCapability("os_version", OSVersion);
		capabilities.setCapability("deviceName", deviceManufacturer);
		capabilities.setCapability("app", AppId);
		capabilities.setCapability("automationName", "UIAutomator2");
		capabilities.setCapability("autoGrantPermissions", "true"); // for auto accepting permission pop-ups

		System.out.println(capabilities);

		try {

			System.out.println("http://" + getPropertyValue("BrowserStack_Username") + ":"
					+ getPropertyValue("BrowserStack_AccessKey") + "@hub-cloud.browserstack.com/wd/hub");

			driver = new AndroidDriver(
					new URL("http://" + getPropertyValue("BrowserStack_Username") + ":"
							+ getPropertyValue("BrowserStack_AccessKey") + "@hub-cloud.browserstack.com/wd/hub"),
					capabilities);

		} catch (Exception e) {
			// System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("==========complete launchApp========");
		SetImplicitWaitInSeconds(4);
		return driver;
	}
	
	
	
	public AndroidDriver StartDriverAndroidAppBrowserStack(String AppId, String local, String BrowserStackDevicesList,
            String geoLocationRequired , String permissionLayer) throws Exception {
        

        String entireDeviceInfo = getRandomlySelectedBrowserStackDevice(BrowserStackDevicesList);
        System.out.println("Device selected : " + entireDeviceInfo);

        String[] words = entireDeviceInfo.split("\\|");
        String deviceManufacturer = words[0];
        String OSVersion = words[1];

        DesiredCapabilities capabilities = new DesiredCapabilities();
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("appiumVersion", "2.0.0");
        browserstackOptions.put("local", local);
        
        
        browserstackOptions.put("idleTimeout", "600");
        browserstackOptions.put("deviceLogs", "true");
        browserstackOptions.put("video", "true");
        browserstackOptions.put("debug", "true");
        browserstackOptions.put("networkLogs", "true");
        browserstackOptions.put("appiumLogs", "true");

        if (geoLocationRequired.contentEquals("true")) {
            browserstackOptions.put("geoLocation", "IN");
        }
        


        capabilities.setCapability("bstack:options", browserstackOptions);

        HashMap<String, Object> networkLogsOptions = new HashMap<String, Object>();
        networkLogsOptions.put("captureContent", true);
       
        capabilities.setCapability("browserstack.networkLogsOptions", networkLogsOptions);

        capabilities.setCapability("userName", getPropertyValue("BrowserStack_Username"));
        capabilities.setCapability("accessKey", getPropertyValue("BrowserStack_AccessKey"));
        
   

        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("os_version", OSVersion);
        capabilities.setCapability("deviceName", deviceManufacturer);
        capabilities.setCapability("app", AppId);
        capabilities.setCapability("automationName", "UIAutomator2");
        capabilities.setCapability("autoGrantPermissions", permissionLayer); // for auto accepting permission pop-ups

        System.out.println(capabilities);

        try {

            driver = new AndroidDriver(
                    new URL("http://" + getPropertyValue("BrowserStack_Username") + ":"
                            + getPropertyValue("BrowserStack_AccessKey") + "@hub-cloud.browserstack.com/wd/hub"),
                    capabilities);

        } catch (Exception e) {
            // System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("==========complete launchApp========");
        SetImplicitWaitInSeconds(4);
        return driver;
    }

}