package TestClasses;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import Links.FilesPaths;
import Links.URLs;
import Utils.JSONUtils;
import enums.HTTPMethod;
import enums.HTTPRequestsContentTypes;
import requestHandling.RestClientHandler;

public class TestDELETERestAPI {

	public HttpURLConnection GETUser(String userID) throws IOException, ParseException {
		HttpURLConnection GETconnection = RestClientHandler.connectServer(URLs.usersInfo + userID, HTTPMethod.GET, HTTPRequestsContentTypes.JSON);
		GETconnection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		// 3. reading response using input stream
		return GETconnection;
	}
	
	
	@Test 
	public void testDELETEUser() throws Exception {
		String userID = "5";
		// 1. Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.usersInfo + userID, HTTPMethod.DELETE,
				HTTPRequestsContentTypes.JSON);
		// 3. DELETE Request
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		RestClientHandler.sendDelete(connection, "", HTTPRequestsContentTypes.JSON);
		// validation Response Code
		assertTrue("Failed to delete data !", connection.getResponseCode() == 204);	
		
		//validation if the user was deleted from the data, it should return 404 response code
		HttpURLConnection GETconnection = GETUser(userID);
		assertTrue("Failed to delete data !", GETconnection.getResponseCode() == 404);
	}
	
	
	@Test 
	public void testDELETEUserDoesntExisting() throws Exception {
		String userID = "510";
		// 1. Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.usersInfo + userID, HTTPMethod.DELETE,
				HTTPRequestsContentTypes.JSON);
		// 3. DELETE Request
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		RestClientHandler.sendDelete(connection, "", HTTPRequestsContentTypes.JSON);
		// validation D Response Code
		assertTrue("Failed to delete data !", connection.getResponseCode() != 204);	
	}
	
}
