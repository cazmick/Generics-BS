package mobile.driverManager;

import io.appium.java_client.AppiumDriver;

public interface ServiceListener {
	
	 
	AppiumDriver onSuccessService(AppiumDriver driver, int taskCode);

	    void onErrorService(AppiumDriver  driver, int taskCode);
	

}
