package GenericFunctionsLibrary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang3.SystemUtils;

public class SignApks {

	public static void main(String args[]) {		
		if(SystemUtils.IS_OS_LINUX){
			//sign the apk to prevent bad app error
			try {
				String directory = System.getProperty("user.dir");
				String command = "java -jar /home/tarun/node_modules/appium/node_modules/appium-adb/jars/sign.jar "+directory+"/apks/*.apk --override";//args[0]+"/*.apk --override";
				System.out.println("Signing builds for Appium...");
				writeFile(directory+"/signAPKs", command);
				Runtime.getRuntime().exec("chmod +x "+directory+"/signAPKs").waitFor();
				Runtime.getRuntime().exec("./signAPKs").waitFor();
				System.out.println("Signing complete for Normal Execution");
				String command1 = "java -jar /home/tarun/node_modules/appium/node_modules/appium-adb/jars/sign.jar "+directory+"/apks/BVT/*.apk --override";//args[0]+"/*.apk --override";
				System.out.println("Signing builds for Appium...");
				writeFile(directory+"/signAPKs1", command1);
				Runtime.getRuntime().exec("chmod +x "+directory+"/signAPKs1").waitFor();
				Runtime.getRuntime().exec("./signAPKs1").waitFor();
				System.out.println("Signing complete for BVT Execution");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private static void writeFile(String path, String command) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(path));
			writer.write(command);
		} catch (IOException e) {

		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}
	}
}
