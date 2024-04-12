package GenericFunctionsLibrary;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class DataFormatter {

	

public JSONObject createExpectedData(String path,String pageName) throws JSONException
	{
		Xls_Reader data=new Xls_Reader(path);
		String sheet=pageName;
		
		JSONObject checkData;
		JSONArray dataContainer=new JSONArray();
		JSONObject expectedData=new JSONObject();
		JSONObject eventData=new JSONObject();
		JSONObject individualRow=new JSONObject();;
		String value,label,event="",page="",eventComp,pageComp,keyNames="",type="",isMandatory="";
		int colCount=data.getColumnCount(sheet);
		int rowCount=data.getRowCount(sheet);
		for(int rowIter=2;rowIter<=rowCount;rowIter++)
		{
			
			System.out.println("Going for row"+rowIter);
			eventComp=data.getCellData(sheet, "Event Name", rowIter);
			pageComp=data.getCellData(sheet, "Page Name", rowIter);
			for(int colIter=1;colIter<=colCount;colIter++)
			{
				label=data.getCellData(sheet, 0, rowIter);
				value=data.getCellData(sheet, colIter, rowIter);
				individualRow.put(label, value);  //this has each row data
				keyNames=data.getCellData(sheet, "KeyNames", rowIter);
				type=data.getCellData(sheet, "Data Type", rowIter);
				isMandatory=data.getCellData(sheet, "Mandatory", rowIter);
				
			}
			//appending content to the previous event Json
				if(!event.equals(eventComp))
				{
					dataContainer=new JSONArray();
				}
				checkData=new JSONObject();
				
				checkData.put("keyNames", keyNames);
				checkData.put("type", type);
				checkData.put("isMandatory", isMandatory);
				dataContainer.put(checkData);
			//create new json for new event
				System.out.println("updated jar");
				if(!event.equals(eventComp))
				{
					eventData.put("data", dataContainer);
			    	eventData.put("page", pageComp);
			    	expectedData.put(eventComp, eventData);
			    	eventData=new JSONObject();
			    	
			    	
			  
				}
				if(rowIter==rowCount) //added for the last event
		    	{
		    		eventData.put("data", dataContainer);
			    	eventData.put("page", pageComp);
			    	expectedData.put(event, eventData);
			    	eventData=new JSONObject();
			    	dataContainer=new JSONArray();
		    	}
		event=eventComp;
		page=pageComp;
		}
		return expectedData;
	}
	

	
	public String compareIndividualJson(JSONObject response, JSONObject expected,String pageName) throws JSONException
	{
		Iterator<String> keys = response.keys();
		JSONObject eventData,toCompare;
		JSONArray data,responseMsg;
		String type,key,isMandatory,strVal="",msg="";
		int intVal=0,flag=0;
		Boolean boolVal=null;
		String status="";
		//iterating over each event in the response
		while(keys.hasNext())
		{
			 key=keys.next();
			 flag=1; //to check if event present
			 eventData=response.getJSONObject(key);
			toCompare=expected.getJSONObject(key);
			
			// comparing all the keys to be checked for this event
			data=toCompare.getJSONArray("data");
			for(int i=0;i<data.length();i++)
			{
				key=data.getJSONObject(i).getString("keyNames");
				type=data.getJSONObject(i).getString("type");
				isMandatory=data.getJSONObject(i).getString("isMandatory");
				
				switch(type)
				{
				case "string":
				{
					if(isMandatory.equalsIgnoreCase("true"))
					{
					try
					{
						strVal=eventData.getString(key).trim();
						
					}
					catch(JSONException e)
					{
						status=status+"string expected but value coming of different type for key:"+key;
						
						
					}
					
					if(!(strVal.length()>0))
					{
						status=status+"|| issue with key:"+key+", expected string but coming empty";
					
					}
					}
				break;
				}
				case "int":
				{
					if(isMandatory.equalsIgnoreCase("true"))
					{
					try
					{
						intVal=eventData.getInt(key);
						
					}
					catch(JSONException e)
					{
						status=status+"|| string expected but value coming of different type for key "+key;
						
						
					}
					
					status=status+"|| issue with key:"+key+", expected string but coming empty";
					
				
					}
					break;
				}
				case "boolean":
				{
					if(isMandatory.equalsIgnoreCase("true"))
					{
					try
					{
					boolVal=eventData.getBoolean(key);
					}
					catch(JSONException e)
					{
						status=status+"|| boolean expected but value coming of different type for key "+key;
							
					}
					
					if(boolVal==true||boolVal==false)
					{
						status=status+"issue with key:"+key+", not coming as expected";
						
					}
					
					}
					break;
				}	
				
				
				default:
				{
					status= status+"data type not available for the key:"+key;
				}
				
				
				
				}
				
				if(!eventData.getString("pageName").contentEquals(pageName))
				{
					System.out.println(eventData.getString("pageName"));
					System.out.println(pageName);
					status=status+"||page name not as expected for the key:"+key;
				
				}
				
				//System.out.println("rishabh"+response.getString("responseMessage"));
				
				
				
			
	}
		if(flag==0)
		{
			status= status+"||event not present in the recorded data!";
		}
			
}
		return status;
	}
	
	public String compareEventData(JSONObject aggResponse,JSONObject expected,String pageName) throws JSONException
	{
		String respData="";
		Iterator keys=aggResponse.keys();
		while(keys.hasNext())
		{
			String response=compareIndividualJson(new JSONObject(keys.next()), expected, pageName);
			respData=respData+response;
		}
		return respData;
	}
	
	
	
}
