package mobile.driverManager;

import io.appium.java_client.ios.IOSDriver;
import mobile.config.Constants;

public class IosService implements Service{

    private int taskCode = 0;
    private ServiceListener serviceListener = null;
    public IOSDriver driver;
    public IosService(int taskCode, ServiceListener serviceListener)
    {
        this.taskCode = taskCode;
        this.serviceListener = serviceListener;
    }


	@Override
	public IOSDriver startDriver(String appLocation,
			String appPackage, String appActivity) {
		// TODO Auto-generated method stub
		try{
		   serviceListener.onSuccessService(driver,Constants.TASK_CODE_ANDROID);
		}
		catch(Exception e)
		{
			serviceListener.onErrorService(driver, Constants.TASK_CODE_IOS);
		}
		return driver;
	}
    
    
    
}
