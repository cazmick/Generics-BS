package Benchmarking;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import GenericFunctionsLibrary.Xls_Reader;

public class BenchmarkingParser {

	static String benchmarkingLogFilePath
	="E:\\workspace\\androidautomationnaukrigulf\\Benchmarkinglog\\ExcelTesting.xls";
	static String benchmarkingLogFileName =
			"E:\\workspace\\androidautomationnaukrigulf\\Benchmarkinglog\\gulf_logger";

	ArrayList<LogDataModel> allData = new ArrayList<LogDataModel>();

	public BenchmarkingParser(String benchmarkingLogFilePath,
			String benchmarkingLogFileName) {
	}
	
	public static void main(String[] args) throws IOException
	{
		BenchmarkingParser pas = new BenchmarkingParser(benchmarkingLogFilePath, benchmarkingLogFileName);
		pas.parser(benchmarkingLogFilePath, benchmarkingLogFileName);
	}
	
	@SuppressWarnings("unused")
	public void writeToExcel(String benchmarkingLogFilePath,
			ArrayList<LogDataModel> allData) {
		Xls_Reader datatable = new Xls_Reader(benchmarkingLogFilePath);
		
		//remove data that have blank loadtime
		for (int i = 0; i <= allData.size(); i++) {

			LogDataModel data = allData.get(i);
			List<NetworkLoad> loadTime = data.getLoadTime();
			
			if (loadTime == null || loadTime.isEmpty()) {
				allData.remove(i);
			}
		}

		for (int i = 0; i < allData.size() - 1; i++) {

			LogDataModel data = allData.get(i);
			List<NetworkLoad> loadTime = data.getLoadTime();

			int j = 2;
			
			if (loadTime == null || loadTime.isEmpty()) {
				allData.remove(i);
				continue;
			}

			datatable.setCellData("Wifi", i, 1, data.getHeadingName());
			datatable.setCellData("3G", i, 1, data.getHeadingName());
			datatable.setCellData("2G", i, 1, data.getHeadingName());

			for (NetworkLoad networkLoad : loadTime) {

				String network = networkLoad.getNetwork();
				String loadTme = networkLoad.getLoadTime();

				if (network == null) {
					continue;
				}
				if (network.equalsIgnoreCase("WIFI")) {
					
					int rowCount = datatable.getRowCount("Wifi");
					datatable.setCellData("Wifi", i, j, loadTme);
					j++;
				}
				if (network.equalsIgnoreCase("3G")) {

					int rowCount = datatable.getRowCount("3G");
					datatable.setCellData("3G", i, j, loadTme);
					j++;
				}
				if (network.equalsIgnoreCase("2G")) {

					int rowCount = datatable.getRowCount("2G");
					datatable.setCellData("2G", i, j, loadTme);
					j++;
				}
			}
		}
	}

	public void parser(String benchmarkingLogFilePath,
			String benchmarkingLogFileName) throws IOException {

		FileInputStream fstream = new FileInputStream(benchmarkingLogFileName);
		InputStreamReader in = new InputStreamReader(fstream);
		BufferedReader br = new BufferedReader(in);

		try {

			String categoryLine, headingName = null, network, loadTime;
			LogDataModel logData = null;

			while ((categoryLine = br.readLine()) != null) {

				if (categoryLine.contains("View Category")) {

					String[] split = categoryLine.split(":");
					headingName = split[2].trim();
					logData = handleLogData(headingName);
					addLogDataModel(logData);
				}

				if (categoryLine.contains("Load Time")) {

					String[] split = categoryLine.split(":");
					loadTime = split[1];
					if (headingName != null)
						logData = handleLogData(headingName);

					NetworkLoad load = new NetworkLoad();
					load.setLoadTime(loadTime);
					logData.setLoadTime(load);

					// logData.setLoadTime(loadTime);
					addLogDataModel(logData);
				}

				if (categoryLine.contains("Network")) {

					String[] split = categoryLine.split(":");
					network = split[1];
					System.out.println("Network : --" + network);
					if (headingName != null)
						logData = handleLogData(headingName);
					ArrayList<NetworkLoad> networkLoadList = logData
							.getLoadTime();
					NetworkLoad networkLoad = networkLoadList
							.get(networkLoadList.size() - 1);
					networkLoad.setNetwork(network);

					addLogDataModel(logData);
				}
			}
		} catch (Exception e) {
		} finally {
			br.close();
			writeToExcel(benchmarkingLogFilePath, allData);
		}
	}

	private void addLogDataModel(LogDataModel logModel) {

		boolean flag = false;
		if (allData.isEmpty()) {

			allData.add(logModel);
			flag = true;
		} else {
			for (LogDataModel dataM : allData) {

				if (dataM.getHeadingName().equals(logModel.getHeadingName())) {
					flag = true;
					break;
				}
			}
		}
		if (!flag) {
			allData.add(logModel);
		}
	}

	private LogDataModel handleLogData(String headingName) {

		boolean flag = false;
		if (allData.isEmpty()) {

			LogDataModel lgModel = new LogDataModel();
			lgModel.setHeadingName(headingName);
			return lgModel;
		}
		for (LogDataModel dataM : allData) {

			if (dataM.getHeadingName().equals(headingName)) {
				flag = true;
				return dataM;
			}
		}
		if (!flag) {
			LogDataModel lgModel = new LogDataModel();
			lgModel.setHeadingName(headingName);
			return lgModel;
		}

		return null;
	}

	private HashMap<Integer, String> addToHeading(
			HashMap<Integer, String> heading, String value) {
		int headingCount = 0;
		if (heading.containsValue(value)) {
			return heading;
		} else if (value.equals("")) {
			return heading;
		} else {
			heading.put(headingCount++, value);
		}
		return heading;
	}






}
