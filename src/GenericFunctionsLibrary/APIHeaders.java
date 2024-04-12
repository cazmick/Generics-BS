package GenericFunctionsLibrary;
import java.util.HashMap;

public class APIHeaders {
	
	public HashMap<String,String> getNotLoggedInHeaders()
	{
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");

		return headers;
	}
	
	public HashMap<String,String> getNotLoggedInHeaders(String clientId)
	{
		HashMap<String, String> headers = new HashMap<String,String>();
		headers.put("clientId", clientId);
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");

		return headers;
	}



	public HashMap<String,String> getLoggedInHeadersForNaukri(String authToken)
	{
		HashMap<String, String> headers = getNotLoggedInHeaders();
		headers = addHeader(headers,"Authorization","NAUKRIAUTH id="+authToken);
		return headers;
	}
	
	
	public HashMap<String,String> getLoggedInHeadersForNaukri(String authToken,String clientId)
	{
		HashMap<String, String> headers = getLoggedInHeadersForNaukri(authToken);
		headers = addHeader(headers, "clientId", clientId);
		return headers;
	}
	
	public HashMap<String,String> getLoggedInHeadersForNaukriGulf(String authToken)
	{
		HashMap<String, String> headers = getNotLoggedInHeaders();
		headers = addHeader(headers,"Authorization","NAUKRIGULF id="+authToken+",authsource=mobileapp");
		return headers;
	}
	
	
	public HashMap<String,String> getLoggedInHeadersForNaukriGulf(String authToken,String clientId)
	{
		HashMap<String, String> headers = getLoggedInHeadersForNaukriGulf(authToken);
		headers = addHeader(headers, "clientId", clientId);
		return headers;
	}
	
	public HashMap<String,String> addHeader(HashMap<String,String> existingHeaders,String header,String value)
	{
		existingHeaders.put(header, value);
		return existingHeaders;
	}

}
