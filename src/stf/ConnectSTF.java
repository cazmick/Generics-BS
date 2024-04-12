package stf;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class ConnectSTF {
	
	private static DeviceApi deviceApi;

	public void connectToStfDevice(String deviceSerial) {

        STFService stfService = null;
		try {
			stfService = new STFService();
		} catch (MalformedURLException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        deviceApi = new DeviceApi(stfService);
        deviceApi.connectDevice(deviceSerial);
    }
}
