package Benchmarking;

import java.util.ArrayList;

public class LogDataModel {
	
	
	private String headingName;
	private ArrayList<NetworkLoad> loadTime;
	private String network;
	
	public String getHeadingName() {
		return headingName;
	}
	
	
	public void setHeadingName(String headingName) {
		this.headingName = headingName;
	}
	
	public void setLoadTime(NetworkLoad networkload) {
		if(loadTime == null) {
			loadTime = new ArrayList<NetworkLoad>();
		}
		loadTime.add(networkload);
	}
	
	
	public ArrayList<NetworkLoad> getLoadTime() {
		return loadTime;
	}
	
	
	public void setLoadTime(ArrayList<NetworkLoad> loadTime) {
		this.loadTime = loadTime;
	}
	
	
	public String getNetwork() {
		return network;
	}
	
	
	public void setNetwork(String network) {
		this.network = network;
	} 
}
