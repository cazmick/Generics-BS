package GenericFunctionsLibrary;

import java.time.Duration

;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.mobile.NetworkConnection;
import org.openqa.selenium.mobile.NetworkConnection.ConnectionType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class MobileActions {

	public AndroidDriver driver;
	public GenericFunctions generic;
	public double MIN_PERCENTAGE = 0.35;
	public double MAX_PERCENTAGE = 0.65;
	public double CENTER_PERCENTAGE = 0.5;

	public MobileActions(AndroidDriver driver, GenericFunctions generic) {
		this.driver = driver;
		this.generic = generic;
	}

	/***********************************************************************************************
	 * Function Description : Used to tap on any element date: 23-Nov-2014
	 *********************************************************************************************/

	public void TapOnElement_Android(By locator) {
		try {
			driver.findElement(locator).click();
		} catch (Exception e) {
			generic.GoToSleep(2000);
			driver.findElement(locator).click();
		}
	}

	/***********************************************************************************************
	 * Function Description : Used to swipe up on dropdown
	 * 
	 * @author rajat.jain, date: 09-July-2015
	 *********************************************************************************************/
	public void swipeUpDropDown_Android() {
		// this.swipe(510, 900,510,300,1000);
		this.customSwipe(0.8, 0.7, 0.8, 0.3, 1000);
	}

	/***********************************************************************************************
	 * Function Description : Used to swipe up on dropdown
	 * 
	 * @author nikita.arora, date: 27-Jan-2016
	 *********************************************************************************************/
	public void swipeDownDropDown_Android() {
		// this.swipe(510, 190, 510, 1000 ,1000);
		this.customSwipe(0.8, 0.2, 0.8, 0.9, 1000);
	}

	/***********************************************************************************************
	 * Function Description : Used to swipe right on the device and display the
	 * contents
	 * 
	 * @author rashi.atry, date: 12-Mar-2014
	 *********************************************************************************************/
	public void swipeRightOnDashboardRibbon() {
		// this.swipe(90, 270, 650, 270, 3000);
		this.customSwipe(0.1, 0.25, 0.9, 0.25, 1000);
	}

	/***********************************************************************************************
	 * Function Description : Used to swipe left on the device and display the
	 * contents
	 * 
	 * @author rashi.atry, date: 12-Mar-2014
	 *********************************************************************************************/
	public void swipeLeftOnDashboardRibbon() {
		// this.swipe(650, 270, 90, 270, 3000);
		this.customSwipe(0.9, 0.25, 0.1, 0.25, 1000);
	}

	/***********************************************************************************************
	 * Function Description : Close keyboard if suggestor is displayed
	 * 
	 * @author rashi.atry, date: 25-Nov-2014
	 *********************************************************************************************/

	public void CloseKeyboardIfSuggestorPresent_Android() {
		generic.GoToSleep(500);
		try {
			driver.hideKeyboard();
			generic.GoToSleep(3000);
			driver.hideKeyboard();
			generic.GoToSleep(4000);
		} catch (Exception e) {
			// driver.hideKeyboard();
			System.out.println("catch of keyboard band");
		}
	}

	/***********************************************************************************************
	 * Function Description : Close keyboard if displayed
	 * 
	 * @author rashi.atry, date: 25-Nov-2014
	 *********************************************************************************************/

	public void CloseKeyboard_Android() {
		boolean isKeyboardShown = driver.isKeyboardShown();
		if (isKeyboardShown == true) {
			driver.hideKeyboard();
		}
	}

	/***********************************************************************************************
	 * Function Description : Choose experience on exp slider present on Search Form
	 * 
	 * @author rashi.atry, date: 17-Jan-2015
	 *********************************************************************************************/

	// Not in use will remove in a bit
//	@SuppressWarnings("static-access")
//	public void ChooseExperience_Android(By ExperienceSliderBy, String Exp, String selectedExp) {
//		By exp_By = By.xpath("//android.widget.TextView[@text = '" + Exp + "']");
//		if (Exp.equals("")) {
//			return;
//		}
//		String pointer;
//		int selectednumericvalue;
//		int toSelectnumericvalue;
//		int startX;
//		if (!(selectedExp.equals("-") || selectedExp.equals("30+"))) {
//			selectednumericvalue = Integer.parseInt(selectedExp);
//			if (!(Exp.equals("30+"))) {
//				toSelectnumericvalue = Integer.parseInt(Exp);
//			} else {
//				toSelectnumericvalue = 31;
//			}
//			if (selectednumericvalue > 3 && (toSelectnumericvalue - 3) < selectednumericvalue) {
//				int counter = 0;
//				pointer = (selectednumericvalue - 2) + "";
//				System.out.println("pointer :" + pointer);
//				@SuppressWarnings("static-access")
//				WebElement element = driver
//						.findElement(ExperienceSliderBy.xpath("//android.widget.TextView[@text = '" + pointer + "']"));
//				Point p = ((Locatable) element).getCoordinates().onPage();
//				startX = p.getX();
//				generic.GoToSleep(1000);
//				while (!generic.isVisible(exp_By) && counter < 10) {
//					this.swipe(startX, 650, 350, 650, 2000);
//					counter++;
//				}
//				this.TapOnElement_Android(exp_By);
//			} else if (selectednumericvalue > 3 && (toSelectnumericvalue - 3) > selectednumericvalue) {
//				int counter = 0;
//				pointer = (selectednumericvalue + 2) + "";
//				System.out.println("pointer :" + pointer);
//				WebElement element = driver
//						.findElement(ExperienceSliderBy.xpath("//android.widget.TextView[@text = '" + pointer + "']"));
//				Point p = ((Locatable) element).getCoordinates().onPage();
//				startX = p.getX();
//				generic.GoToSleep(1000);
//				while (!generic.isVisible(exp_By) && counter < 10) {
//					this.swipe(startX, 650, 350, 650, 2000);
//					counter++;
//				}
//				this.TapOnElement_Android(exp_By);
//			} else {
//				this.TapOnElement_Android(exp_By);
//			}
//
//		} else {
//			if (selectedExp.equals("-")) {
//				int counter = 0;
//				WebElement element = driver
//						.findElement(ExperienceSliderBy.xpath("//android.widget.TextView[@text = '2']"));
//				selectedExp = element.getText();
//				Point p = ((Locatable) element).getCoordinates().onPage();
//				startX = p.getX();
//				generic.GoToSleep(1000);
//				while (!generic.isVisible(exp_By) && counter < 12) {
//					this.swipe(startX, 650, 350, 650, 1000);
//					counter++;
//				}
//				this.TapOnElement_Android(exp_By);
//
//			} else {
//				int counter = 0;
//				WebElement element = driver
//						.findElement(ExperienceSliderBy.xpath("//android.widget.TextView[@text = '28']"));
//				selectedExp = element.getText();
//				Point p = ((Locatable) element).getCoordinates().onPage();
//				startX = p.getX();
//				generic.GoToSleep(1000);
//				while (!generic.isVisible(exp_By) && counter < 12) {
//					this.swipe(startX, 650, 350, 650, 1000);
//					counter++;
//				}
//				this.TapOnElement_Android(exp_By);
//			}
//		}
//	}

	/***********************************************************************************************
	 * Function Description : Used to swipe up and swipe down on the device to
	 * search field or error and will return true if visible.In this function if
	 * bottom xpath is not given than it will swipe up 15 times maximum to search a
	 * field
	 * 
	 * @author rajat.jain, date: 22-Jan-2015
	 *********************************************************************************************/
	public boolean SearchElementOnWholePage(By FieldBy, By TopBy, By BottomBy) {
		int count = 0;
		if (generic.isVisible(FieldBy)) {
			return true;
		} else {
			while (!generic.isVisible(FieldBy)) {
				if (generic.isVisible(TopBy)) {
					break;
				}
				swipedown();
				count++;
				if (count == 7) {
					break;
				}
			}
			count = 0;
			while (!generic.isVisible(FieldBy)) {
				if (BottomBy.equals("")) {
					while ((count < 7) && !(generic.isVisible(FieldBy))) {
						swipeup();
						count++;
					}
				} else {
					if (generic.isVisible(BottomBy)) {
						break;
					}
					swipeup();
					count++;
					if (count == 7) {
						break;
					}
				}
			}

			if (generic.isVisible(FieldBy)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/***********************************************************************************************
	 * Function Description : Get all network connection status, true(On)/false(Off)
	 * 
	 * @author rashi.atry, date: 16-July-2015
	 *********************************************************************************************/

	NetworkConnection network = (NetworkConnection) driver;

	public ConnectionType GetNetworkConnection() {
//use appium function

		ConnectionType connection = network.getNetworkConnection();

		return connection;
	}

	/***********************************************************************************************
	 * Function Description : Set network true(On)/false(Off), as per the
	 * requirement by passing true for same
	 * 
	 * @author rashi.atry, date: 16-July-2015
	 *********************************************************************************************/

	public void SetNetworkConnection(boolean setAirplaneMode, boolean setWifi, boolean setData) {
		network.setNetworkConnection(this.GetNetworkConnection());

	}

	public void swipeup(By fromElement, By toElement) {
		// this.swipe(500, 900, 500, 200, 1000);
		Point fromPoint = driver.findElement(fromElement).getLocation();
		Point toPoint = driver.findElement(toElement).getLocation();

		Dimension fromSize = driver.findElement(fromElement).getSize();
		Dimension toSize = driver.findElement(toElement).getSize();

		System.out.println(fromPoint.getX() + ".." + fromPoint.getY() + "................." + toPoint.getX() + ".."
				+ toPoint.getY());

		int startx = fromPoint.getX() + (fromSize.getWidth() / 2);
		int starty = fromPoint.getY() + (fromSize.getHeight() / 2);
		int endx = fromPoint.getX() + (toSize.getWidth() / 2);
		int endy = fromPoint.getY() + (toSize.getHeight() / 2);
		this.swipe(startx, starty, endx, endy, 1000);

	}

	public void swipeup() {
		Dimension size = driver.manage().window().getSize();
		int width = size.getWidth();
		int height = size.getHeight();
		int startx = width / 2;
		int starty = (int) (height * 0.9);
		int endx = width / 2;
		int endy = (int) (height * 0.2);
		System.out.println("width: " + width + " height: " + height + " startx: " + startx + " starty: " + starty
				+ " endx: " + endx + " endy: " + endy);
		this.swipe(startx, starty, endx, endy, 1000);

	}

	public void swipeupHalfPage_Android() {
		Dimension size = driver.manage().window().getSize();
		int width = size.getWidth();
		int height = size.getHeight();
		int startx = width / 2;
		int starty = (int) (height * 0.7);
		int endx = width / 2;
		int endy = (int) (height * 0.3);
		System.out.println("width: " + width + " height: " + height + " startx: " + startx + " starty: " + starty
				+ " endx: " + endx + " endy: " + endy);
		this.swipe(startx, starty, endx, endy, 1000);

	}

	public void swipeupLittleHalfPage_Android() {
		Dimension size = driver.manage().window().getSize();
		int width = size.getWidth();
		int height = size.getHeight();
		int startx = width / 2;
		int starty = (int) (height * 0.6);
		int endx = width / 2;
		int endy = (int) (height * 0.3);
		System.out.println("width: " + width + " height: " + height + " startx: " + startx + " starty: " + starty
				+ " endx: " + endx + " endy: " + endy);
		this.swipe(startx, starty, endx, endy, 1000);

	}

	public void swipeupFromRightEdge() {
		Dimension size = driver.manage().window().getSize();
		int width = size.getWidth();
		int height = size.getHeight();
		int startx = (int) (width * 0.9);
		int starty = (int) (height * 0.7);
		int endx = (int) (width * 0.9);
		int endy = (int) (height * 0.3);
		System.out.println("width: " + width + " height: " + height + " startx: " + startx + " starty: " + starty
				+ " endx: " + endx + " endy: " + endy);
		this.swipe(startx, starty, endx, endy, 1000);
	}

	public void swipedown() {
		Dimension size = driver.manage().window().getSize();
		int width = size.getWidth();
		int height = size.getHeight();
		int startx = width / 2;
		int starty = (int) (height * 0.2);
		int endx = width / 2;
		int endy = (int) (height * 0.9);
		System.out.println("width: " + width + " height: " + height + " startx: " + startx + " starty: " + starty
				+ " endx: " + endx + " endy: " + endy);
		this.swipe(startx, starty, endx, endy, 1000);

	}

	public void swipedownHalfPage_Android() {
		Dimension size = driver.manage().window().getSize();
		int width = size.getWidth();
		int height = size.getHeight();
		int startx = width / 2;
		int starty = (int) (height * 0.3);
		int endx = width / 2;
		int endy = (int) (height * 0.7);
		System.out.println("width: " + width + " height: " + height + " startx: " + startx + " starty: " + starty
				+ " endx: " + endx + " endy: " + endy);
		this.swipe(startx, starty, endx, endy, 1000);

	}

	public void customSwipe(double startx, double starty, double endx, double endy) {

		Dimension size = driver.manage().window().getSize();
		int width = size.getWidth();
		int height = size.getHeight();
		int actual_startx = (int) (width * startx);
		int actual_starty = (int) (height * starty);
		int actual_endx = (int) (width * endx);
		int actual_endy = (int) (height * endy);
		System.out.println("width: " + width + " height: " + height + " startx: " + actual_startx + " starty: "
				+ actual_starty + " endx: " + actual_endx + " endy: " + actual_endy);
		this.swipe(actual_startx, actual_starty, actual_endx, actual_endy, 1000);
	}

	public void customSwipe(double startx, double starty, double endx, double endy, int swipe_time_in_milisecs) {

		Dimension size = driver.manage().window().getSize();
		int width = size.getWidth();
		int height = size.getHeight();
		int actual_startx = (int) (width * startx);
		int actual_starty = (int) (height * starty);
		int actual_endx = (int) (width * endx);
		int actual_endy = (int) (height * endy);
		System.out.println("width: " + width + " height: " + height + " startx: " + actual_startx + " starty: "
				+ actual_starty + " endx: " + actual_endx + " endy: " + actual_endy);
		this.swipe(actual_startx, actual_starty, actual_endx, actual_endy, swipe_time_in_milisecs);
	}

//	public void swipe(int x, int y, int x1, int y1) {
//		TouchAction action = new TouchAction(driver);
//		action.press(PointOption.point(x, y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
//				.moveTo(PointOption.point(x1, y1)).release().perform();
//
//	}

	// Appium2

	public void swipe(int x, int y, int x1, int y1) {
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence swipe = new Sequence(finger, 1);
		swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), x, y));
		swipe.addAction(finger.createPointerDown(0));
		swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), x1, y1));
		swipe.addAction(finger.createPointerUp(0));
		driver.perform(Arrays.asList(swipe));

	}

//	public void swipe(int x, int y, int x1, int y1, int time) {
//		TouchAction action = new TouchAction(driver);
//		action.press(PointOption.point(x, y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
//				.moveTo(PointOption.point(x1, y1)).release().perform();
//
//	}

	// Appium2 Swipe with time

	public void swipe(int x, int y, int x1, int y1, int time) {
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence swipe = new Sequence(finger, 1);
		swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), x, y));
		swipe.addAction(finger.createPointerDown(0));
		swipe.addAction(finger.createPointerMove(Duration.ofMillis(time), PointerInput.Origin.viewport(), x1, y1));
		swipe.addAction(finger.createPointerUp(0));
		driver.perform(Arrays.asList(swipe));

	}

	public boolean swipeUpHalfTillElementFound(By element, int count) {

		MobileActions act = new MobileActions(driver, generic);

		int num = 0;

		while (!generic.isVisible(element) && num <= count) {

			act.swipeupHalfPage_Android();

			num++;

		}

		if (num == count) {

			return false;

		}

		return true;

	}

	public boolean swipeUpTillElementFound(By element, int count) {

		MobileActions act = new MobileActions(driver, generic);

		int num = 0;

		while (!generic.isVisible(element) && num <= count) {

			act.swipeup();

			num++;

		}

		if (num == count) {

			return false;

		}

		return true;

	}

	public void scrollUpToExactElement(String keyword) {
		List<WebElement> isFoundElement = driver.findElements(By.className("android.widget.TextView"));
		int count = 0;
		while (count == 0) {
			for (WebElement mobileElement : isFoundElement) {
				if (mobileElement.getText().contains(keyword)) {
					count = 1;
					mobileElement.click();

				}
			}
			if (count == 0) {
				this.swipeup();
			}
		}

	}

	public void waitAndClickPermission() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("com.android.packageinstaller:id/permission_allow_button")));
			driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")).click();
			generic.GoToSleep(2000);
			driver.navigate().back();
		} catch (Exception e) {
		}
	}

	public void waitAndClickAllowPermission() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("com.android.packageinstaller:id/permission_allow_button")));
			while (generic.isVisible(By.id("com.android.packageinstaller:id/permission_allow_button"))) {
				driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")).click();
				generic.GoToSleep(2000);
			}
		} catch (Exception e) {
		}
	}

	public void waitAndClick(By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.visibilityOfElementLocated((locator)));
			driver.findElement(locator).click();
		} catch (Exception e) {
		}
	}

	public void selectFromPickerDown(int wheelIndex) {
		List<WebElement> wheels = driver.findElements(By.className("android.widget.NumberPicker"));
		System.out.println(wheels.size());
		Point p1 = ((WebElement) wheels.get(wheelIndex)).getLocation();
		MobileActions action = new MobileActions(driver, generic);
		action.swipe(p1.getX(), p1.getY(), p1.getX(), p1.getY() - 300, 2000);

	}

	public void loginDeeplinkForNG(String Username, String Password) {
		driver.get("ng://www.naukrigulftest.com/naukriLoginForTesting/username=kiosk-n99@gmail.com&password=qwerty");
	}

	public List<WebElement> getElementsInList(By locator) {
		return driver.findElements(locator);
	}

	public void TapOnElementInList_Android(By locator, int index) {
		WebElement e = (WebElement) driver.findElements(locator).get(index);
		e.click();
	}
}
