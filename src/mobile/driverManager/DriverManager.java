package mobile.driverManager;

import mobile.config.Constants;

public class DriverManager {

	public static Service getService(int taskCode, ServiceListener serviceListener) {
        switch (taskCode) {

            case Constants.TASK_CODE_ANDROID:
                return new AndroidService(taskCode, serviceListener);
            case Constants.TASK_CODE_IOS:
                return new IosService(taskCode,serviceListener);
           
        }

        return null;
    }
}
