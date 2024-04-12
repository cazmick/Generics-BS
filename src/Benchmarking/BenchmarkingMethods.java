package Benchmarking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import GenericFunctionsLibrary.GenericFunctions;
import GenericFunctionsLibrary.Xls_Reader;
import io.appium.java_client.android.AndroidDriver;

public class BenchmarkingMethods {

	public WebDriver driver;
	GenericFunctions generic;

	public BenchmarkingMethods(AndroidDriver driver, GenericFunctions generic) {
		this.driver = driver;
		this.generic = generic ;
	}


	public double getTimeInSecond(By sourceBy, By destinationBy) {
		long start = 0;
		// long start1 = 0;
		long finish = 0;
		double totalTime = 0;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		if (generic.isVisible(sourceBy)) {
			wait.until(ExpectedConditions.presenceOfElementLocated(sourceBy));
			driver.findElement(sourceBy).click();

			start = System.nanoTime();
			System.out.println("Start - " + start);

			wait.until(ExpectedConditions
					.presenceOfElementLocated(destinationBy));

			finish = System.nanoTime();
			System.out.println("Finish - " + finish);

			totalTime = finish - start;
			totalTime = totalTime / 1000000000;
			System.out.println("Total Time for page load => " + totalTime);
		} else {
			System.out.println("Source page is not displaying");
		}

		return totalTime;
	}



	public void copyFileToSystem(String copyPathFrom, String copyPathTo) {

		String command = "adb pull "+ copyPathFrom + " " + copyPathTo;

		try {
			Process process = Runtime.getRuntime().exec(command);       
			BufferedReader reader=new BufferedReader( new InputStreamReader(process.getInputStream()));
			String consoleResponse;                
			while ((consoleResponse = reader.readLine()) != null){         
				Assert.assertTrue(consoleResponse.contains("bytes"), "Faill -- couldn't copy file from phone");
			}  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String returnAverageSheetName( String sheetNameData) {
		String sheetNameAverage = "";

		if (sheetNameData.equalsIgnoreCase("3G")) {
			sheetNameAverage = "Average3G";
		} else if (sheetNameData.equalsIgnoreCase("2G")) {
			sheetNameAverage = "Average2G";
		} else if (sheetNameData.equalsIgnoreCase("Wifi")) {
			sheetNameAverage = "AverageWiFi";
		}

		return sheetNameAverage;
	}

	public List<String> returnHeading(String benchmarkingLogFilePath,
			String sheetNameData) throws FilloException {
		Fillo fillo = new Fillo();

		Connection connection = fillo.getConnection(benchmarkingLogFilePath);
		String strQuery = "SELECT * from " + sheetNameData;
		Recordset recordset = connection.executeQuery(strQuery);
		List<String> headingNames = recordset.getFieldNames();
		return headingNames;

	}

	public void FindAverage(String benchmarkingLogFilePath,
			String sheetNameData) throws FilloException {

		Xls_Reader datatable = new Xls_Reader(benchmarkingLogFilePath);
		List<String> headingNames = returnHeading(benchmarkingLogFilePath,
				sheetNameData);
		String sheetNameAverage = returnAverageSheetName(sheetNameData);

		int row = 2;
		for (String headings : headingNames) {

			float temp = 0;
			float sum = 0;

			datatable.setCellData(sheetNameAverage, 0, row, headings);
			datatable.setCellData(sheetNameAverage, 0, 1, "Screens");
			datatable.setCellData(sheetNameAverage, 1, 1, "Average");
			datatable.setCellData(sheetNameAverage, 2, 1, "Median");

			int rowCount = ReturnRowNumber(benchmarkingLogFilePath,
					sheetNameData, headings);

			System.out.println("HEADING ---------- " + headings);
			for (int i = 2; i < rowCount; i++) {

				String loadTime = datatable
						.getCellData(sheetNameData, headings, i).trim()
						.replace("s", "");
				loadTime = loadTime.replace(" ", "");
				temp = Float.valueOf(loadTime);
				sum = temp + sum;
			}
			float averageValue = sum / (rowCount - 2);
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(3);
			System.out.println("AVERAGE VALUE ------ " + averageValue);

			datatable.setCellDataWithDoubleValue(sheetNameAverage, "Average",
					row, averageValue);
			row++;
		}
	}

	public static int ReturnRowNumber(String benchmarkingLogFilePath,
			String sheetName, String columnName) {
		Xls_Reader datatable = new Xls_Reader(benchmarkingLogFilePath);
		for (int i = 2;; i++) {
			String a = datatable.getCellData(sheetName, columnName, i);
			if (a.equals("") || a == null) {
				return i;
			} else {
				continue;
			}
		}
	}



	public void FindMedian(String sheetNameData,
			String benchmarkingLogFilePath) throws FilloException {

		Xls_Reader datatable = new Xls_Reader(benchmarkingLogFilePath);
		List<String> headings = returnHeading(benchmarkingLogFilePath,
				sheetNameData);
		String sheetNameAverage = returnAverageSheetName(sheetNameData);

		double medianValue = 0;
		int row = 2;
		int loadTimeRowCount = 0;
		double temp = 0;
		

		for (String heading : headings) {
			int rowCount = ReturnRowNumber(benchmarkingLogFilePath,
					sheetNameData, heading);
			if(rowCount != 2) {
				loadTimeRowCount = rowCount - 1;
			} else {
				loadTimeRowCount = 1;
			}

			if (loadTimeRowCount % 2 == 1) {
				int middleRow = loadTimeRowCount / 2;
					String loadTime = datatable
							.getCellData(sheetNameData, heading, middleRow)
							.trim().replace("s", "");
					loadTime = loadTime.replace(" ", "");
					medianValue = Float.valueOf(loadTime);
			} else {
				int middleRow = loadTimeRowCount / 2;
				for (int center = middleRow; center <= middleRow + 1; center++) {
					String loadTime = datatable
							.getCellData(sheetNameData, heading, center).trim()
							.replace("s", "");
					loadTime = loadTime.replace(" ", "");
					medianValue = Float.valueOf(loadTime);
					temp = medianValue + temp;
				}
				medianValue = temp / 2;
			}
			System.out.println("Median VALUE ------ " + medianValue);

			datatable.setCellDataWithDoubleValue(sheetNameAverage, "Median",
					row, medianValue);
			row++;
		}
	}










}