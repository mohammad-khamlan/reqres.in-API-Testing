package TestClasses;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
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

public class TestPostRestAPI {

	
	public HttpURLConnection GETUser(String userID) throws IOException, ParseException {
		HttpURLConnection GETconnection = RestClientHandler.connectServer(URLs.usersInfo + userID, HTTPMethod.GET, HTTPRequestsContentTypes.JSON);
		GETconnection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		// reading response using input stream
		return GETconnection;
	}


	@Test 
	public void testPOSTNewUser() throws Exception {
		// Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.usersInfo, HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		// Prepare Json Object
		JSONObject resquestJSONObject = JSONUtils.readJSONObjectFromFileAndReturnJSON(FilesPaths.newUserInfo);
		// Post Request
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		RestClientHandler.sendPost(connection, resquestJSONObject.toString(), HTTPRequestsContentTypes.JSON);
		// Reading Response
		String response = RestClientHandler.readResponse(connection);
		// validation Response Code
		assertTrue("unable to connect to webservice", connection.getResponseCode() == 201);	
		// convert String to JSON
		JSONObject jsonObject = (JSONObject) JSONUtils.convertStringToJSON(response);
		//validation data dataFromFile == dataFromResponse
		assertTrue("data doesn't added !", resquestJSONObject.get("id").toString().equals(jsonObject.get("id").toString()));
		//validation if the user was added to the data
		HttpURLConnection GETconnection = GETUser(resquestJSONObject.get("id").toString());
		assertTrue("ERROR 404, Not Found !", GETconnection.getResponseCode() == 200);
		String GETresponse = RestClientHandler.readResponse(GETconnection);
		assertTrue("data doesn't added !", ! GETresponse.equals(""));
	}

	
	@Test
	public void testPOSTExistingUser() throws Exception {
		// Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.usersInfo, HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		// Prepare Json Object
		JSONObject resquestJSONObject = JSONUtils.readJSONObjectFromFileAndReturnJSON(FilesPaths.POSTExistingUser);
		// Post Request
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		RestClientHandler.sendPost(connection, resquestJSONObject.toString(), HTTPRequestsContentTypes.JSON);
		assertTrue("Can't add an existing user !", connection.getResponseCode() != 201);	
		// Reading Response
		String response = RestClientHandler.readResponse(connection);
		assertTrue("User is already exist !", response.equals("")); 
	}
	
	
	@Test
	public void testPOSTWithoutData() throws Exception {
		// Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.usersInfo, HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		// Prepare Json Object
//		JSONObject resquestJSONObject = JSONUtils.readJSONObjectFromFileAndReturnJSON(FilesPaths.newUserInfo);
		// Post Request
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		RestClientHandler.sendPost(connection, "", HTTPRequestsContentTypes.JSON);
		// Reading Response
		String response = RestClientHandler.readResponse(connection);
		// convert String to JSON
		JSONObject jsonObject = (JSONObject) JSONUtils.convertStringToJSON(response); 
		// validation POST response code
		assertTrue("Can't add user without data !", connection.getResponseCode() != 201);
		
		try {
			assertTrue("No Data to POST !", ! jsonObject.get("first_name").equals(null) && ! jsonObject.get("last_name").equals(null)); 
		}
		catch(NullPointerException e) {
			fail("No Data to POST !");
		}
	}
	
	
	@Test
	public void testPOSTNewUsers() throws Exception {
		// Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.usersInfo, HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		// Prepare Json Object
		JSONArray resquestJSONArray = JSONUtils.readJSONArrayFromFileAndReturnJSON(FilesPaths.newUsersInfo);
		// Post Request
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		RestClientHandler.sendPost(connection, resquestJSONArray.toString(), HTTPRequestsContentTypes.JSON);
		assertTrue("Error with adding array of users !", connection.getResponseCode() == 201);
		// Reading Response
		String response = RestClientHandler.readResponse(connection);
		JSONArray jsonArray =  (JSONArray) JSONUtils.convertStringToJSON(response);
		// validation dataFromFile == dataFromResponse
		boolean flag = true;
		
		for(int index = 0; index < jsonArray.size(); index++) {
			String data = ((JSONObject) resquestJSONArray.get(index)).get("id").toString();
			String responseData = ((JSONObject) jsonArray.get(index)).get("id").toString();
			System.out.println(data + "   " + responseData);
			if(! data.equals(responseData)) {
				flag = false;
			}
		}
		assertTrue("data doesn't added !", flag == true);
		// validation if each user in the array was added to the data
		flag = true;
		
		for(int index = 0; index < jsonArray.size(); index++) {
			String userID = ((JSONObject) resquestJSONArray.get(index)).get("id").toString();
			HttpURLConnection GETconnection = GETUser(userID);
			if(GETconnection.getResponseCode() == 404) {
				flag = false;
			}
		}
		assertTrue("Data doesn't added !", flag == true);

	}
	
	
	@Test 
	public void testPOSTNewUserWithIncompleteParameters() throws Exception {
		// Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.usersInfo, HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		// Prepare Json Object
		JSONObject resquestJSONObject = JSONUtils.readJSONObjectFromFileAndReturnJSON(FilesPaths.POSTWithIncompleteParameters);
		// Post Request
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		RestClientHandler.sendPost(connection, resquestJSONObject.toString(), HTTPRequestsContentTypes.JSON);
		// Reading Response
		String response = RestClientHandler.readResponse(connection);
		// validation Response Code
		assertTrue("unable to connect to webservice", connection.getResponseCode() == 201);	
		// convert String to JSON
		JSONObject jsonObject = (JSONObject) JSONUtils.convertStringToJSON(response);
		//validation response code
		assertTrue("Data is Incomplete !", connection.getResponseCode() != 201);
	}

}		




