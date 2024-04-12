package mobile.driverManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class AndroidService implements Service {

	private int taskCode = 0;
	private ServiceListener serviceListener = null;
	public AndroidDriver driver;

	public AndroidService(int taskCode, ServiceListener serviceListener) {

		this.taskCode = taskCode;
		this.serviceListener = serviceListener;
	}

	@Override
	public AndroidDriver startDriver(String appLocation, String appPackage, String appActivity) {
		// TODO Auto-generated method stub
		String Platform = "";
		String AppiumHost = "";
		// GridDrivers grid = new GridDrivers();
		String ip = "127.0.0.1";// grid.GetIpAddress();
		int numberOfDevices = getconnectedDevicesNumber();

		/*
		 * if (SystemUtils.IS_OS_WINDOWS) { if (numberOfDevices == 0) {
		 * System.out.println("Number of connected devices is ZERO"); return null; }
		 * else if (numberOfDevices == 1) { try { stopAppiumServer(); } catch
		 * (IOException e) { e.printStackTrace(); } GoToSleep(5000); Platform =
		 * "Android"; AppiumHost = "http://" + ip + ":4723/wd/hub"; try {
		 * startAppiumServer(); System.out.println("After start"); } catch (IOException
		 * | InterruptedException e) { e.printStackTrace(); } } else {
		 * 
		 * System.out.println("In grid");
		 * 
		 * Platform = "Android"; AppiumHost = "http://" + ip + ":4444/wd/hub"; } } else
		 * { Platform = "Linux"; AppiumHost = "http://0.0.0.0:4723/wd/hub";
		 * 
		 * RuntimeExec appiumObj = new RuntimeExec();
		 * appiumObj.stopAppiumUbuntu("killall -9 node"); GoToSleep(2000); appiumObj
		 * .startAppiumUbuntu("/usr/bin/node /usr/bin/appium --address 0.0.0.0 --port 4723 --no-reset --command-timeout 60"
		 * ); }
		 * 
		 * GoToSleep(5000);
		 */
		DesiredCapabilities capabilities = new DesiredCapabilities();

		System.out.println("==set browser==");
		// capabilities.setCapability(MobileCapabilityType.PLATFORM, Platform);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		System.out.println("==set device==");

		capabilities.setCapability(MobileCapabilityType.APP, appLocation);
		capabilities.setCapability("appPackage", appPackage);
		capabilities.setCapability("appActivity", appActivity);
		System.out.println("==set app==");

		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 1000);

		// driver = new AndroidDriver(new URL(AppiumHost), capabilities);

		driver.manage().timeouts().implicitlyWait(120000, TimeUnit.MILLISECONDS);
		System.out.println("==========complete launchApp========");
		setImplicitWaitInSeconds(15);

		return driver;
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

	public String setImplicitWaitInSeconds(int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		return "Timeout set to " + timeOut + " seconds.";
	}
}
