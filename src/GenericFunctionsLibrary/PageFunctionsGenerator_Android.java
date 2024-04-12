package GenericFunctionsLibrary;




import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import org.openqa.selenium.By;
//import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;


public class PageFunctionsGenerator_Android {
	WebDriver driver;
	GenericFunctions generic;
	Config con = new Config();
	String FolderPath = con.BasePath;
	String TextFilePath= FolderPath+"code_generated.txt";
	String TextFilePath_FF=FolderPath+"code_fillform.txt";
	String TextFilePath_DP=FolderPath+"code_dataprovider.txt";
	
	
	/* This PageFunctionsGenerator uses XPaths named using the following standards
	 * Naming Standard		Example Class Types										Functions Generated
	 * ===============		===================										===================
	 * 	_Img				android.widget.ImageView								click, isDisplayed
	 *	_Btn				android.widget.Button									click, isDisplayed
	 *	_MuTxt				android.widget.MultiAutoCompleteTextView, 				fill, getText, isDisplayed
	 *						android.widget.EditText									fill, getText, isDisplayed
	 *	_Lnk				android.widget.TextView									click, getText, isDisplayed
	 *	_Lbl 				android.widget.TextView									click, getText, isDisplayed
	 *	_Chk																		select,Deselect,isSelected, isDisplayed
	 *	_Rd																			select, isSelected, isDisplayed
	 *	_WE				, , Others												click, isDisplayed
	 */

	private class Bys {

		// Put your Bys here
		public By srpSsaLayerCount_Lbl = By.id("naukriApp.appModules.login:id/tv_srp_ssa_no");
		public By srpSsaLayerDescription_Lbl = By.id("naukriApp.appModules.login:id/tv_srp_ssa_text");
		public By srpSsaLayer_Btn = By.id("naukriApp.appModules.login:id/bt_srp_ssa_info");
		public By srpRefineLayerCount_Lbl = By.id("naukriApp.appModules.login:id/tv_srp_refine_no");
		public By srpRefineLayerDescription_Lbl = By.id("naukriApp.appModules.login:id/tv_srp_refine_text");
		public By srpRefineLayer_Btn = By.id("naukriApp.appModules.login:id/bt_srp_refine_info");
		
		public By applyStatusLayerCount_Lbl = By.id("naukriApp.appModules.login:id/tv_as_no");
		public By applyStatusLayerDescription_Lbl = By.id("naukriApp.appModules.login:id/tv_as_text");
		public By applyStatusLayer_Btn = By.id("naukriApp.appModules.login:id/bt_as_info");
		public By applySimilarJobLayerDescription_Lbl = By.id("naukriApp.appModules.login:id/tv_srp_ssa_text");
		public By applySimilarLayer_Btn = By.id("naukriApp.appModules.login:id/bt_as_sim_info");
	}

	@Test
	public void GenerateXPaths() throws IOException{

		//Change Class name here if generating from a different file/class, else put your xPaths in the "XPaths" Class above
		Bys p1 =new  Bys();
		String Page_Name= "PersonalDetailsPage";
		//System.out.println("mylen"+"   ".length());
		System.out.println("START.....");

		//File for generated POM Functions
		System.out.println("Output File : "+TextFilePath);

		BufferedWriter out;
		out= null;
		try {
			out = new BufferedWriter(new FileWriter(TextFilePath, false));
			out.newLine();
			out.write("public int sleepTime=4000;");
			out.newLine();
			out.newLine();
			out.newLine();
			out.newLine();
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		
		BufferedWriter out2;
		out2= null;

		try {
			out2 = new BufferedWriter(new FileWriter(TextFilePath_FF, true));
			out2.newLine();
			out2.write("public void fillForm_"+Page_Name+"(HashMap<String, String> hmdata){");
			out2.newLine();
			out2.close();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		BufferedWriter outDP;

		outDP= null;
		try {
			outDP = new BufferedWriter(new FileWriter(TextFilePath_DP, true));
			outDP.newLine();
			outDP.write("@DataProvider");	 
			outDP.newLine();
			outDP.write("public Object[][] getData(){");
			outDP.newLine();			
			outDP.write("Xls_Reader datatable"+"=new"+" Xls_Reader"+"(\"change sheet path here\");");
			outDP.newLine();
			outDP.write("int rowcount=datatable.getRowCount(\""+Page_Name+"\");");
			outDP.newLine();
			outDP.write("Object[][] 	data = new Object[rowcount-1][1];");
			outDP.newLine();
			outDP.write("for (int i=2; i<rowcount+1; i++){");
			outDP.newLine();
			outDP.write("String sheetname=\"Sheet1\";");
			outDP.newLine();
			outDP.write("HashMap<String,String> hm = new HashMap<String,String>();");
			outDP.newLine();
			outDP.write("hm.put(\"Expected\", datatable.getCellData(sheetname, \"Expected\", i));");
			outDP.close();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		int col=0;
		int colnum=0;


		for (Field field : p1.getClass().getDeclaredFields()) {

			String bydet = field.getName();
			if(!bydet.contains("this"))
				System.out.println("Generating for xPath : "+ bydet);


			String label_name;
			BufferedWriter out_fillform;
			out= null;
			out_fillform= null;
			outDP = null;
			//out = new BufferedWriter(new FileWriter(TextFilePath, true));


			/////GENERATING POM FUNCTIONS 


			try {
				
				
				
				/////TextBox	
				if(bydet.endsWith("_Txt")){
					label_name = bydet.substring(0, bydet.length()-4);
					run2(label_name, colnum);
					colnum=colnum+1;
					
					out = new BufferedWriter(new FileWriter(TextFilePath, true));
					out_fillform = new BufferedWriter(new FileWriter(TextFilePath_FF, true));
					outDP =  new BufferedWriter(new FileWriter(TextFilePath_DP, true));
					
					outDP.write("hm.put(\""+label_name+"\", "+"datatable.getCellData(sheetname, \""+label_name+"\", i));");
					outDP.newLine();
					outDP.close();
					
					
					out_fillform.write("fill_"+label_name+"_Txt(hmdata.get(\""+label_name+"\").toString());");
					out_fillform.newLine();
					out_fillform.close();
					
					//********Fill**************************

					out.newLine();
					out.write("public  void Fill_"+label_name+"_Txt(String inputdata){");
					out.newLine();
					out.write("if(inputdata.trim().length()==0)return;");
					out.newLine();
					out.write("MobileActions act = new MobileActions(driver, generic);");
					out.newLine();
					out.write("TextBox tb = new TextBox(driver, generic);");
					out.newLine();
					out.write("tb.FillTextBox_Android("+bydet+", inputdata);");
					out.newLine();
					out.write("act.CloseKeyboard_Android();");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();


					//********GetText**************************

					out.newLine();
					out.write("public String GetText_"+label_name+"_Txt(){");
					out.newLine();
					out.write("TextBox tb = new TextBox(driver, generic);");
					out.newLine();
					out.write("return tb.GetValueFromTextBox_Android("+bydet+");");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();


					//************isDisplayed

					out.newLine();
					out.write("public boolean isDisplayed_"+label_name+"_Txt() {");
					out.newLine();
					out.write("if(generic.isVisible("+bydet+")) {");
					out.newLine();
					out.write("return true;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.write("else ");
					out.newLine();
					out.write("return false;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();
					out.close();
				}


				/////Multi suggester TextBox
				if(bydet.endsWith("_MuTxt")){
					label_name = bydet.substring(0, bydet.length()-6);
					run2(label_name, colnum);
					colnum=colnum+1;
					
					out = new BufferedWriter(new FileWriter(TextFilePath, true));
					out_fillform = new BufferedWriter(new FileWriter(TextFilePath_FF, true));
					outDP =  new BufferedWriter(new FileWriter(TextFilePath_DP, true));
					
					outDP.write("hm.put(\""+label_name+"\", "+"datatable.getCellData(sheetname, \""+label_name+"\", i));");
					outDP.newLine();
					outDP.close();
					
					
					out_fillform.write("fill_"+label_name+"_MuTxt(hmdata.get(\""+label_name+"\").toString());");
					out_fillform.newLine();
					out_fillform.close();

					//********Fill**************************

					out.newLine();
					out.write("public  void Fill_"+label_name+"_MuTxt(String inputdata){");
					out.newLine();
					out.write("if(inputdata.trim().length()==0)return;");
					out.newLine();
					out.write("MobileActions act = new MobileActions(driver, generic);");
					out.newLine();
					out.write("TextBox tb = new TextBox(driver, generic);");
					out.newLine();
					out.write("tb.FillTextBox_Android("+bydet+", inputdata);");
					out.newLine();
					out.write("act.CloseKeyboardIfSuggestorPresent_Android();");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();


					//********GetText**************************

					out.newLine();
					out.write("public String GetText_"+label_name+"_MuTxt(){");
					out.newLine();
					out.write("TextBox tb = new TextBox(driver, generic);");
					out.newLine();
					out.write("return tb.GetValueFromTextBox_Android("+bydet+");");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();

					//************isDisplayed

					out.newLine();
					out.write("public boolean isDisplayed_"+label_name+"_MuTxt() {");
					out.newLine();
					out.write("if(generic.isVisible("+bydet+")) {");
					out.newLine();
					out.write("return true;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.write("else ");
					out.newLine();
					out.write("return false;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();
					out.close();
				}


				//******************Select Checkbox
				if(bydet.endsWith("_Chk")){


					label_name = bydet.substring(0, bydet.length()-4);
					
					run2(label_name, colnum);
					colnum=colnum+1;
					out = new BufferedWriter(new FileWriter(TextFilePath, true));
					out_fillform = new BufferedWriter(new FileWriter(TextFilePath_FF, true));
					//out_vform = new BufferedWriter(new FileWriter(TextFilePath_VF, true));

					outDP =  new BufferedWriter(new FileWriter(TextFilePath_DP, true));
					outDP.write("hm.put(\""+label_name+"\", "+"datatable.getCellData(sheetname, \""+label_name+"\", i));");
					outDP.newLine();
					outDP.close();

					out_fillform.newLine();

					out_fillform.write("String "+label_name+"_td="+"hmdata.get(\""+label_name+"\").toString();");
					out_fillform.newLine();
					out_fillform.write("if("+label_name+"_td.equalsIgnoreCase("+"\"yes\""+")){");
					out_fillform.write("Select_"+label_name+"_Chk();");
					out_fillform.write("}");	

					out_fillform.newLine();
					out_fillform.write("if("+label_name+"_td.equalsIgnoreCase("+"\"no\""+")){");
					out_fillform.write("DeSelect_"+label_name+"_Chk();");
					out_fillform.write("}");	
					out_fillform.newLine();
					out_fillform.close();
					
					out.newLine();
					out.write("public void Select_"+label_name+"_Chk(){");
					out.newLine();	
					out.write("try{");
					out.newLine();

					out.write("if(driver.findElement("+bydet+").isSelected()){}else{");
					out.newLine();
					out.write("driver.findElement("+bydet+").click();");						
					out.newLine();	
					out.write("}");							
					out.newLine();

					out.write("}catch(Exception e){");
					out.newLine();
					out.write("generic.GoToSleep(sleepTime);");
					out.newLine();

					out.write("if(driver.findElement("+bydet+").isSelected()){}else{");
					out.newLine();
					out.write("driver.findElement("+bydet+").click();");						


					out.newLine();
					out.write("}");
					out.newLine();

					out.write("}");							
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();

					//****************************DeSelect Checkbox

					out.newLine();
					out.write("public void DeSelect_"+label_name+"_Chk(){");
					out.newLine();	

					out.write("try{");
					out.newLine();

					out.write("if(driver.findElement("+bydet+").isSelected()){");
					out.newLine();
					out.write("driver.findElement("+bydet+").click();");						
					out.newLine();
					out.write("}");							
					out.newLine();

					out.write("}catch(Exception e){");
					out.newLine();
					out.write("generic.GoToSleep(sleepTime);");
					out.newLine();

					out.write("if(driver.findElement("+bydet+").isSelected()){");
					out.newLine();
					out.write("driver.findElement("+bydet+").click();");						
					out.newLine();

					out.newLine();
					out.write("}");
					out.newLine();

					out.write("}");							
					out.newLine();
					out.write("}");
					out.newLine();

					//************IsSelected Checkbox


					out.newLine();
					out.write("public boolean isChecked_"+label_name+"_Chk(){");
					out.newLine();
					out.write("try{");
					out.newLine();

					out.write("return driver.findElement("+bydet);
					out.write(").isSelected();");
					out.newLine();		
					out.write("}catch(Exception e){");
					out.newLine();
					out.write("generic.GoToSleep(sleepTime);");
					out.newLine();

					out.write("return driver.findElement("+bydet);
					out.write(").isSelected();");
					out.newLine();
					out.newLine();
					out.write("}");
					out.newLine();

					out.write("}");
					out.newLine();
					out.newLine();
					out.close();

				}


				//********************************Radio button
				//******************Select 
					if(bydet.endsWith("_Rd")){


					label_name = bydet.substring(0, bydet.length()-3);
					run2(label_name, colnum);
					colnum=colnum+1;
					out = new BufferedWriter(new FileWriter(TextFilePath, true));
					out_fillform = new BufferedWriter(new FileWriter(TextFilePath_FF, true));
					//out_vform = new BufferedWriter(new FileWriter(TextFilePath_VF, true));

					outDP =  new BufferedWriter(new FileWriter(TextFilePath_DP, true));
					outDP.write("hm.put(\""+label_name+"\", "+"datatable.getCellData(sheetname, \""+label_name+"\", i));");
					outDP.newLine();
					outDP.close();

					out_fillform.newLine();
					out_fillform.write("String "+label_name+"_td="+"hmdata.get(\""+label_name+"\").toString();");
					out_fillform.newLine();					
					out_fillform.write("if("+label_name+"_td.equalsIgnoreCase("+"\"yes\""+")){");
					out_fillform.write("Select_"+label_name+"_Rd();");
					out_fillform.write("}");							
					out_fillform.newLine();
					out_fillform.close();


					out.newLine();
					out.write("public void Select_"+label_name+"_Rd(){");
					out.newLine();
					out.write("try{");
					out.newLine();

					out.write("driver.findElement("+bydet+").click();");						
					out.newLine();	
					out.write("}catch(Exception e){");
					out.newLine();
					out.write("generic.GoToSleep(sleepTime);");
					out.newLine();


					out.write("driver.findElement("+bydet+").click();");						
					out.newLine();	
					out.newLine();
					out.write("}");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();



					//************IsSelected 



					out.newLine();
					out.write("public boolean isSelected_"+label_name+"_Rd(){");
					out.newLine();

					out.write("try{");
					out.newLine();
					out.write("return driver.findElement("+bydet);
					out.write(").isSelected();");
					out.newLine();	
					out.write("}catch(Exception e){");
					out.newLine();
					out.write("generic.GoToSleep(sleepTime);");
					out.newLine();

					out.write("return driver.findElement("+bydet);
					out.write(").isSelected();");
					out.newLine();	
					out.newLine();
					out.write("}");
					out.newLine();

					out.write("}");
					out.newLine();
					out.newLine();


					//************isDisplayed

					out.newLine();
					out.write("public boolean isDisplayed_"+label_name+"_Rd() {");
					out.newLine();
					out.write("if(generic.isVisible("+bydet+")) {");
					out.newLine();
					out.write("return true;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.write("else ");
					out.newLine();
					out.write("return false;");
					out.newLine();
					out.write("}");	
					out.newLine();
					out.newLine();
					out.close();
				}


				//********************************Link
				//******************Click 
				if(bydet.endsWith("_Lnk")){


					label_name = bydet.substring(0, bydet.length()-4);
					out = new BufferedWriter(new FileWriter(TextFilePath, true));

					out.newLine();
					out.write("public void click_"+label_name+"_Btn(){");
					out.newLine();
					out.write("MobileActions act = new MobileActions(driver, generic);");
					out.newLine();
					out.write("act.TapOnElement_Android("+bydet+");");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();

					//************GetText 

					out.newLine();
					out.write("public String GetLinkText_"+label_name+"_Lnk(){");
					out.newLine();
					out.write("TextBox tb = new TextBox(driver, generic);");
					out.newLine();
					out.write("return tb.GetValueFromTextBox_Android("+bydet+");");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();


					//************isDisplayed

					out.newLine();
					out.write("public boolean isDisplayed_"+label_name+"_Lnk() {");
					out.newLine();
					out.write("if(generic.isVisible("+bydet+")) {");
					out.newLine();
					out.write("return true;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.write("else ");
					out.newLine();
					out.write("return false;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();
					out.close();
				}


				//********************************Button
				//******************Click 
				if(bydet.endsWith("_Btn")){

					label_name = bydet.substring(0, bydet.length()-4);
					out = new BufferedWriter(new FileWriter(TextFilePath, true));

					out.newLine();
					out.write("public void click_"+label_name+"_Btn(){");
					out.newLine();
					out.write("MobileActions act = new MobileActions(driver, generic);");
					out.newLine();
					out.write("act.TapOnElement_Android("+bydet+");");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();

					//************isDisplayed

					out.newLine();
					out.write("public boolean isDisplayed_"+label_name+"_Btn() {");
					out.newLine();
					out.write("if(generic.isVisible("+bydet+")) {");
					out.newLine();
					out.write("return true;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.write("else ");
					out.newLine();
					out.write("return false;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();
					out.close();
				}

				//********************************Image
				//******************Click 
				if(bydet.endsWith("_Img")){


					label_name = bydet.substring(0, bydet.length()-4);
					out = new BufferedWriter(new FileWriter(TextFilePath, true));

					out.newLine();
					out.write("public void click_"+label_name+"_Btn(){");
					out.newLine();
					out.write("MobileActions act = new MobileActions(driver, generic);");
					out.newLine();
					out.write("act.TapOnElement_Android("+bydet+");");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();


					//************isDisplayed

					out.newLine();
					out.write("public boolean isDisplayed_"+label_name+"_Img() {");
					out.newLine();
					out.write("if(generic.isVisible("+bydet+")) {");
					out.newLine();
					out.write("return true;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.write("else ");
					out.newLine();
					out.write("return false;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();
					out.close();
				}


				//********************************Label
				if(bydet.endsWith("_Lbl")){


					label_name = bydet.substring(0, bydet.length()-4);
					out = new BufferedWriter(new FileWriter(TextFilePath, true));

					//************GetText 

					out.newLine();
					out.write("public String GetLabelText_"+label_name+"_Lbl(){");
					out.newLine();
					out.write("TextBox tb = new TextBox(driver, generic);");
					out.newLine();
					out.write("return tb.GetValueFromTextBox_Android("+bydet+");");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();


					//************isDisplayed

					out.newLine();
					out.write("public boolean isDisplayed_"+label_name+"_Lbl() {");
					out.newLine();
					out.write("if(generic.isVisible("+bydet+")) {");
					out.newLine();
					out.write("return true;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.write("else ");
					out.newLine();
					out.write("return false;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();
					out.close();
				}



				//********************************Layout
				//******************Click 
				if(bydet.endsWith("_WE")){


					label_name = bydet.substring(0, bydet.length()-7);
					out = new BufferedWriter(new FileWriter(TextFilePath, true));


					out.newLine();
					out.write("public void click_"+label_name+"_AE(){");
					out.newLine();
					out.write("try{");
					out.newLine();
					out.write("driver.findElement("+bydet+").click();");						
					out.newLine();	
					out.write("}catch(Exception e){");
					out.newLine();
					out.write("generic.GoToSleep(sleepTime);");
					out.newLine();
					out.write("driver.findElement("+bydet+").click();");						
					out.newLine();	
					out.newLine();
					out.write("}");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();

					//************isDisplayed

					out.newLine();
					out.write("public boolean isDisplayed_"+label_name+"_WE() {");
					out.newLine();
					out.write("if(generic.isVisible("+bydet+")) {");
					out.newLine();
					out.write("return true;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.write("else ");
					out.newLine();
					out.write("return false;");
					out.newLine();
					out.write("}");
					out.newLine();
					out.newLine();
					out.close();
				}


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
		
		try {
			outDP = new BufferedWriter(new FileWriter(TextFilePath_DP, true));
			outDP.newLine();
			outDP.write("data[i-2][0]=hm;");	 
			outDP.newLine();
			outDP.write("}");
			outDP.newLine();
			outDP.write("return data;");
			outDP.write("}");
			outDP.close();
		} catch (Exception e1) {

			e1.printStackTrace();
		}


		///////////////////////#####################
		//	
		out2=null; 


		System.out.println("1111111111111111111111111111111111111");
		//out2= null;
		try {
			out2 = new BufferedWriter(new FileWriter(TextFilePath_FF, true));
			//out2.newLine();
			out2.write("}");
			System.out.println("1111111111111111111111111111111111111");
			out2.newLine();
			out2.newLine();
			out2.newLine();
			out2.newLine();
			out2.newLine();
			out2.write("public String Capture_Errors(){");
			out2.newLine();
			out2.write("String total_errors=\" \";");
			out2.newLine();
			out2.write("if(driver.findElements(By.xpath("+Page_Name+"_Error_WE"+")).size()> 0 ){");
			out2.newLine();
			out2.write("List<WebElement> errors= driver.findElements(By.xpath("+Page_Name+"_Error_WE));");
			out2.newLine();
			out2.write("for(int i=0; i<errors.size(); i++){");
			out2.newLine();
			out2.write("if(errors.get(i).isDisplayed()){");
			out2.newLine();
			out2.write("total_errors= total_errors"+"+"+"\", \""+"+errors.get(i).getText();");
			out2.newLine();
			out2.write("}");
			out2.newLine();
			out2.write("}");
			out2.newLine();
			out2.write("}");
			out2.newLine();
			out2.write("return total_errors;");
			out2.newLine();
			out2.write("}");
			out2.newLine();
			out2.newLine();
			out2.newLine();
			out2.newLine();
			out2.newLine();
			out2.newLine();


			//FLV
			out2.write("@Test(dataProvider=\"getData\")");
			out2.newLine();

			//out2.write("@DataProvider");	  
			//out2.write("public Object[][] getData() {");


			out2.write("public void FieldValidations_"+Page_Name+"(HashMap<String, String> hm){");
			out2.newLine();


			//out2.write("String sheetname=\""+Page_Name+"\";");
			//out2.write("Xls_Reader datatable"+"=new"+" Xls_Reader"+"(\"change sheet path here\");");
			//out2.newLine();
			/*out2.write("int rowcount=datatable.getRowCount(\""+Page_Name+"\");");
				out2.newLine();
				out2.write("for (int i=2; i<rowcount+1; i++){");
				out2.newLine();*/


			out2.write(Page_Name+" "+Page_Name+" = new "+Page_Name+"(driver, generic);");
			out2.newLine();
			out2.write("String confirmMsg=\"\";");
			out2.newLine();
			out2.write(Page_Name+".fillForm_"+Page_Name+"(hm);");
			out2.newLine();
			out2.write("String expected=hm.get(\"Expected\").toString();");
			out2.newLine();

			/*out2.write("if(generic.isVisible(confirmMsg)){");
				out2.newLine();*/
			out2.write("if(expected.equalsIgnoreCase(\"submitted\")){");
			out2.newLine();
			out2.write("Assert.assertTrue(generic.isVisible(confirmMsg), \"Fail-\"+"+Page_Name+".Capture_Errors()"+");");
			//out2.write("datatable.setCellData("+"\""+Page_Name+"\""+", \"Result\", i, \"Pass\");");
			out2.newLine();
			out2.write("}else {");
			out2.newLine();
			out2.write("Assert.assertFalse(generic.isVisible(confirmMsg), \"Fail-Page Submitted for Negative data\""+");");

			out2.newLine();
			out2.write("}");
			out2.newLine();

			out2.newLine();
			out2.write("}");
			out2.newLine();
			out2.close();

		} catch (Exception e1) {

			e1.printStackTrace();
		}



		System.out.println("FINISHED.....");

	}

	public void run2(String heading, int j){
		Xls_Reader datatable = new Xls_Reader("C:\\workspace\\td.xls");
		datatable.setCellData("Sheet1", 0, 1, "Expected");
		datatable.setCellData("Sheet1", 1, 1,"Expected ErrorMessage");
		datatable.setCellData("Sheet1", j+2, 1, heading);
		datatable.setCellData("Sheet1", j+2, 1, heading);		
	}
	
}
