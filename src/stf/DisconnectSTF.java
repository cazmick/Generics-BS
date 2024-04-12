package stf;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import GenericFunctionsLibrary.GenericFunctions;

public class DisconnectSTF {
	private static DeviceApi deviceApi;


	public static void main(String args[]) {
		String enteredDeviceNames = args[0];

		if(enteredDeviceNames==null){
			//Windows machine. Do nothing.
		}
		else{
			String[] devicesArray= enteredDeviceNames.split(",");
			List<String> devicesNames = new ArrayList<String>();
			Collections.addAll(devicesNames, devicesArray);
			//	numberOfDevices = devicesNames.size();
			for(String deviceName: devicesNames){
				String deviceID = GenericFunctions.getDeviceIDFromConfigFile(deviceName);
				if(deviceID.equals("")){
					System.out.println("ERROR!!! : Entered device name '"+deviceName+"' not found. if you are using a new device add it to device Mapping excel sheet in Common Resources folder. Exiting....");
					System.exit(3);
				}
				releaseDevice(deviceID);
			}

		}
	}


	private static void releaseDevice(String deviceID) {
		STFService stfService = null;
		try {
			stfService = new STFService();
		} catch (MalformedURLException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		deviceApi = new DeviceApi(stfService);
		deviceApi.releaseDevice(deviceID);		
	}
}