package mobile.driverManager;

import io.appium.java_client.AppiumDriver;

public interface Service {

	AppiumDriver startDriver(String appLocation,
			String appPackage, String appActivity);
}
