package mobile.genericAndroidIos;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import mobile.config.Constants;
import mobile.driverManager.DriverManager;
import mobile.driverManager.Service;
import mobile.driverManager.ServiceListener;

public class Driver {

	
	public AppiumDriver  startDriver(AppiumDriver driver)
	{
		if(driver instanceof AndroidDriver)
			 {Service service =DriverManager.getService(Constants.TASK_CODE_ANDROID, new HandleAppiumCallback());
			 	driver=service.startDriver("","","");
			 	return driver;
			 
			 }
		else if(driver instanceof IOSDriver)
		{
			Service service =DriverManager.getService(Constants.TASK_CODE_IOS, new HandleAppiumCallback());
			driver =service.startDriver("","","");
			return driver;
		}
		else
		{
			return null;
		}
			 
	}
	
	 private class HandleAppiumCallback implements ServiceListener
	    {

		
		@Override
		public AppiumDriver onSuccessService(AppiumDriver driver, int taskCode) {
			// TODO Auto-generated method stub
			return driver;
		}

		@Override
		public void onErrorService(AppiumDriver driver, int taskCode) {
			// TODO Auto-generated method stub
			System.out.println("Error in starting appium");
		}
	    }
	
	
	
	
}
