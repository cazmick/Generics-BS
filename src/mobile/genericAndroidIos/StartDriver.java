package mobile.genericAndroidIos;

import io.appium.java_client.android.AndroidDriver;

public class StartDriver {

	public  AndroidDriver driver;
	
	public static void main(String[] args)
	{
		
	}
	public void code()
	{
		 Driver mainDriver= new Driver();
		 driver=(AndroidDriver) mainDriver.startDriver(driver);
	}

	
}
