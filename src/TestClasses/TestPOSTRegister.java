package TestClasses;

import static org.junit.Assert.*;

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

public class TestPOSTRegister {

	@Test
	public void testRegisterWithValidUser() throws Exception {
		// Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.userRegisteration, HTTPMethod.POST,
						HTTPRequestsContentTypes.JSON);
		// Prepare Json Object
		JSONObject resquestJSONObject = JSONUtils.readJSONObjectFromFileAndReturnJSON(FilesPaths.registerData);
		// Post Request
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		RestClientHandler.sendPost(connection, resquestJSONObject.toString(), HTTPRequestsContentTypes.JSON);
		// validation Response Code
		assertTrue("Error, Missing email or password", connection.getResponseCode() == 200);	
	}

	
	@Test (expected = IOException.class)
	public void testRegisterWithInvalidUser() throws Exception {
		// Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.userRegisteration, HTTPMethod.POST,
						HTTPRequestsContentTypes.JSON);
		// Prepare Json Object
		String resquestJSONObject = JSONUtils.readJSONObjectFromFile(FilesPaths.registerData);
		// Post Request
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		RestClientHandler.sendPost(connection, resquestJSONObject.toString(), HTTPRequestsContentTypes.JSON);
		// validation Response Code
		assertTrue("Error, Only defined users succeed registration", connection.getResponseCode() == 400);	
	}
	
	
	@Test
	public void testRegisterWithoutpasswordOrEmail() throws Exception {
		// Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.userRegisteration, HTTPMethod.POST,
						HTTPRequestsContentTypes.JSON);
		// Prepare Json Object
		String resquestJSONObject = JSONUtils.readJSONObjectFromFile(FilesPaths.registerData);
		// Post Request
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		RestClientHandler.sendPost(connection, resquestJSONObject.toString(), HTTPRequestsContentTypes.JSON);
		// validation Response Code
		assertTrue("Error, Missing email or password", connection.getResponseCode() == 400);	
	}

}
