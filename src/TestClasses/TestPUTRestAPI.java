package TestClasses;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;

import Links.FilesPaths;
import Links.URLs;
import Utils.JSONUtils;
import enums.HTTPMethod;
import enums.HTTPRequestsContentTypes;
import requestHandling.RestClientHandler;

public class TestPUTRestAPI {

	public String GETUser(String userID) throws IOException, ParseException {
		HttpURLConnection GETconnection = RestClientHandler.connectServer(URLs.usersInfo + userID, HTTPMethod.GET, HTTPRequestsContentTypes.JSON);
		GETconnection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		// reading response using input stream
		String GETresponse = RestClientHandler.readResponse(GETconnection);
		return GETresponse;
	}
	
	
	@Test
	public void testUpdateUser() throws Exception {
		// Open Connection --- HttpURLConnection
		String userID = "1";
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.usersInfo + userID, HTTPMethod.PUT,
				HTTPRequestsContentTypes.JSON);
		// Prepare Json Object
		JSONObject resquestJSONObject = JSONUtils.readJSONObjectFromFileAndReturnJSON(FilesPaths.UpdateUserJSONFile);
		// PUT Request
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		RestClientHandler.sendPut(connection, resquestJSONObject.toJSONString(), HTTPRequestsContentTypes.JSON);
		assertTrue("Error with updating user Data !", connection.getResponseCode() == 200);
		// Reading Response
		String response = RestClientHandler.readResponse(connection);
		// convert String to JSON
		JSONObject jsonObject = (JSONObject) JSONUtils.convertStringToJSON(response);
		// validation dataFromFile == dataFromResponse 
		assertTrue("User doesn't updated !", jsonObject.get("first_name").equals(resquestJSONObject.get("first_name")));
		// validation if the data was updated successfully
		String GETresponse = GETUser(userID);
		JSONObject GETjsonObject = (JSONObject) JSONUtils.convertStringToJSON(GETresponse);
		assertTrue("Data doesn't updated !", jsonObject.equals(GETjsonObject));
	}
	
	
	@Test
	public void testUpdateUserDoesntExist() throws Exception {
		// Open Connection --- HttpURLConnection
		String url = URLs.usersInfo+"510";
		HttpURLConnection connection = RestClientHandler.connectServer(url, HTTPMethod.PUT,
				HTTPRequestsContentTypes.JSON);
		// Prepare Json Object
		JSONObject resquestJSONObject = JSONUtils.readJSONObjectFromFileAndReturnJSON(FilesPaths.UpdateUserDoesntExist);
		// PUT Request
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		RestClientHandler.sendPut(connection, resquestJSONObject.toJSONString(), HTTPRequestsContentTypes.JSON);
		// validation PUT response code
		assertTrue("User doesn't exist !", connection.getResponseCode() != 200);
	}


	@Test
	public void testUpdateWithoutData() throws Exception {
		// 1. Open Connection --- HttpURLConnection
		String url = URLs.usersInfo+"1";
		HttpURLConnection connection = RestClientHandler.connectServer(url, HTTPMethod.PUT,
				HTTPRequestsContentTypes.JSON);
		// 2. Prepare Json Object
//		JSONObject resquestJSONObject = JSONUtils.readJSONObjectFromFileAndReturnJSON(FilesPaths.UpdateUserJSONFile);
		// 3. PUT Request
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		RestClientHandler.sendPut(connection, "", HTTPRequestsContentTypes.JSON);
		assertTrue("No Data to update !", connection.getResponseCode() != 200);
		// 4. Reading Response
		String response = RestClientHandler.readResponse(connection);
		// 5. convert String to JSON
		JSONObject jsonObject = (JSONObject) JSONUtils.convertStringToJSON(response);
		System.out.println(jsonObject.get("first_name"));
		try {
			assertTrue("No Data to UPDATE !", ! jsonObject.get("first_name").equals(null));
		}
		catch(NullPointerException e) {
			fail("No Data to UPDATE !");
		}
	}
	
	
	
}














