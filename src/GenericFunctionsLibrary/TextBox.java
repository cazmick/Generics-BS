package GenericFunctionsLibrary;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;

public class TextBox {

	public AndroidDriver driver;
	public GenericFunctions generic;

	public TextBox(AndroidDriver driver, GenericFunctions generic)
	{
		this.driver = driver;
		this.generic = generic;
	}


	/***********************************************************************************************
	 * Function Description : Fill given Text in TextBox
	 * author: Rashi Atry, 
	 * date: 19-Nov-2013
	 * *********************************************************************************************/

	public void FillTextBox_Android(By keywords_Txt,String inputdata)
	{
		if(inputdata.trim().equals(""))
		{
			driver.findElement(keywords_Txt).click();
			driver.findElement(keywords_Txt).clear();
			return;
		}
		
		driver.findElement(keywords_Txt).click();
		driver.findElement(keywords_Txt).clear();
		driver.findElement(keywords_Txt).sendKeys(inputdata);
	}



	/***********************************************************************************************
	 * Function Description : Get Text from TextBox Android
	 * author: Rajat Jain, 
	 * date: 16-March-2015
	 * *********************************************************************************************/

	public String GetValueFromTextBox_Android(By locator)
	{
		String result="";
		result=driver.findElement(locator).getText();
		return result;
	}

	/***********************************************************************************************
	 * Function Description : Fill multi-line text box with suggester
	 * author: nikita.arora,
	 * date: 9-Sep-2015
	 * *********************************************************************************************/

	public void FillTextBoxwithSuggestor_Android(By field_MuTxt, String inputdata, String suggestorYesNo)
	{
		MobileActions act = new MobileActions(driver, generic);
		if(inputdata.trim().length()==0) {
			driver.findElement(field_MuTxt).clear();
			act.CloseKeyboard_Android();
			return;
		}
		this.FillTextBox_Android(field_MuTxt, inputdata);
		generic.GoToSleep(1000);
		if(suggestorYesNo.equalsIgnoreCase("yes")) {
			act.CloseKeyboardIfSuggestorPresent_Android();
		}
		else {
			act.CloseKeyboard_Android();
		}
	}

	/***********************************************************************************************
	 * Function Description : Fill multi-line text box with suggester on editor screen
	 * author: nikita.arora,
	 * date: 9-Sep-2015
	 * @return: String - to get error on editor page
	 * *********************************************************************************************/

	public String FillTextBoxwithSuggestor_Android(By field_MuTxt, String inputdata, String suggestorYesNo, String editorYesNo,
			By editorfield_Txt, By doneOnEditor_Btn, By errorOnEditor_Lbl)
	{
		/*System.out.println("all values -- " +inputdata +" + " +suggestorYesNo +" + " +editorYesNo);*/
		String error_editor = "";
		if(inputdata.equals(""))
		{
			return "";
		}
		MobileActions act = new MobileActions(driver, generic);
		act.TapOnElement_Android(field_MuTxt); 
		driver.findElement(editorfield_Txt).clear();
		act.CloseKeyboard_Android();
		driver.findElement(editorfield_Txt).sendKeys(inputdata);
		
		if(suggestorYesNo.equalsIgnoreCase("yes")) { 
			act.CloseKeyboardIfSuggestorPresent_Android();
		}
		else {
			act.CloseKeyboard_Android();
		}
		act.TapOnElement_Android(doneOnEditor_Btn);
		if(editorYesNo.equalsIgnoreCase("yes"))
		{	
			if (generic.isVisible(errorOnEditor_Lbl)) {
				System.out.println("ERROR EXISTS");
				error_editor = driver.findElement(errorOnEditor_Lbl).getText();
			}
		}
		
	

		return error_editor;

	}
	
	/***********************************************************************************************
	 * Function Description : Fill LogIn Credentials in ID and Password TextBox
	 * author: Chirag Tiwari, 
	 * date: 30-Jan-2023
	 * *********************************************************************************************/

	public void FillLogInID(By logIn_Txt,String userName)
	{
		generic.WaitUntilVisibilityOfElement(logIn_Txt);
		driver.findElement(logIn_Txt).sendKeys(userName);

	}

	public void FilltextBox(By locator,String inputdata)
	{
		if(inputdata.trim().equals(""))
		{
			driver.findElement(locator).click();
			driver.findElement(locator).clear();
			return;
		}
		generic.WaitUntilVisibilityOfElement(locator);
		driver.findElement(locator).sendKeys(inputdata);
	}
	
	public void FillLogInPassword(By passWord_Txt, String password)
	{
		
		generic.WaitUntilVisibilityOfElement(passWord_Txt);
		driver.findElement(passWord_Txt).sendKeys(password);
	}


}




