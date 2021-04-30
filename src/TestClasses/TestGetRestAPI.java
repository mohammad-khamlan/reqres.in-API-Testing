package TestClasses;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import Links.FilesPaths;
import Links.URLs;
import Utils.JSONUtils;
import enums.HTTPMethod;
import enums.HTTPRequestsContentTypes;
import requestHandling.HandleRequestReponse;
import requestHandling.RestClientHandler;


public class TestGetRestAPI {
	
	static String GETresponse;
	
	@BeforeClass
	public static void GETallData() throws IOException {
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.usersInfo, HTTPMethod.GET,
		HTTPRequestsContentTypes.JSON);
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		// 3. reading response using input stream
		GETresponse = RestClientHandler.readResponse(connection);
	}
	
	
	@Test
	public void TestGETDataWithExistUserID() throws IOException {
		String userID = "1";
		// 1. connect to server and open connection (get HttpURLConnection object)
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.usersInfo + userID, HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		// 2. validate if the connection is successfully openned
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("unable to connect to webservice", connection.getResponseCode() == 200);
		// 3. reading response using input stream
		String response = RestClientHandler.readResponse(connection);
		System.out.println(response);
		assertTrue("Data is empty", !response.equals(""));

	}
	
	
	@Test (expected = FileNotFoundException.class)
	public void TestGETDataWithDoesntExistUserID() throws IOException {
		String userID = "15";
		// 1. connect to server and open connection (get HttpURLConnection object)
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.usersInfo + userID, HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		// 2. validate if the connection is successfully openned
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("ERROR 404, Not Found !", connection.getResponseCode() == 404);
		String response = RestClientHandler.readResponse(connection);
	}

	
	@Test
	public void TestGETDataWithBadParameter() throws IOException {
		String userID = "?color=red";
		// 1. connect to server and open connection (get HttpURLConnection object)
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.usersInfo + userID, HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		connection.addRequestProperty("User-Agent", "Mozella/4.0 (compatible; MSIE 6.0; windows NT 5.0)");
		// 2. validate if the connection is successfully openned
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("unable to connect to webservice", connection.getResponseCode() == 200);
		String response = RestClientHandler.readResponse(connection);
		assertTrue("Searched with bad parameters !", ! response.equals(GETresponse));
	}


}
