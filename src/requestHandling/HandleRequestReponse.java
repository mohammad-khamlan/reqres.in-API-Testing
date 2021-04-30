package requestHandling;

import Utils.JSONUtils;

import enums.HTTPMethod;
import enums.HTTPRequestsContentTypes;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.net.HttpURLConnection;




/**
 *  
 * Description: This class is used to handle HTTP requests mentioned in the requirements : PUT DELETE GET POST
 *              through the functions in this class.
 */
public class HandleRequestReponse {


    /**
     *  .
     * @param filePath: specifies the path of the file we want to read and Send through the request.
     * @param con: specifies the connection that was used to send the request , which is going to be used to send data.
     * @throws IOException: for file not found or connection lost.
     * @throws ParseException: for parsing errors.
     * Description: the function is used to read the JSON Array and send the data.
     */
    public static void readJSONArrayAndSend(String filePath,HttpURLConnection con) throws IOException, ParseException {
        String originalData= JSONUtils.readJSONArrayFromFile(filePath);
        RestClientHandler.sendRequest(con,originalData);

    }







    /**
     *  .
     * @param filePath: specifies the path of the file we want to read and Send through the request.
     * @param con: specifies the connection that was used to send the request , which is going to be used get the response.
     * @throws IOException: for file not found or connection lost.
     * @throws ParseException: for parsing errors.
     * Description: the function is used to read the JSON Object and send the data.
     */
    public static void readJSONObjectAndSend(String filePath,HttpURLConnection con) throws IOException, ParseException {
        String originalData = JSONUtils.readJSONObjectFromFile(filePath);
        RestClientHandler.sendRequest(con,originalData);

    }








   


    /**
     *  .
     * @param Url:Specifies the URL we are going to send the request through.
     * @return returns the response of the GET request.
     * @throws IOException for connection lost.
     * Description: the function is used to send GET HTTP Request to API with specified URL.
     */
    public static String sendGetRequest(String Url) throws IOException {
        HttpURLConnection connection=RestClientHandler.connectServer(Url, HTTPMethod.GET,HTTPRequestsContentTypes.JSON);
        
        System.out.println("Response Code from Rest Framework : " + connection.getResponseCode());
        assertEquals("Response code is wrong", connection.getResponseCode(), 200);
        
        String response=RestClientHandler.readResponse(connection);
       
        return response;
    }





    /**
     *  .
     * @param Url:Specifies the URL we are going to send the request through.
     * @param filePath:Specifies the path of the file we are going to read and send the data through.
     * @return returns the response of the PUT request.
     * @throws IOException for connection lost.
     * @throws ParseException for parsing problems of file reading.
     * Description: the function is used to send PUT HTTP Request to API with specified URL with reading files as JSON FORMAT.
     */
    public static String sendPutRequest(String Url, String filePath,String status) throws IOException, ParseException {
        HttpURLConnection con=RestClientHandler.connectServer(Url, HTTPMethod.PUT,HTTPRequestsContentTypes.JSON);
        readJSONObjectAndSend(filePath,con);
        String response=RestClientHandler.readResponse(con);
        JSONObject resultData = (JSONObject) JSONUtils.convertStringToJSON(response);
        String expectedResult= (String) resultData.get("status");

        return response;
    }


    /**
     *
     * @param Url: Specifies the URL we are going to send the request through.
     * @param filePath: Specifies the path of the file we are going to read and send the data through.
     * @return the response of the POST request.
     * @throws IOException for connection lost.
     * @throws ParseException for parsing problems of file reading.
     * Description: the function is used to send PUT HTTP Request to API with specified URL with reading files as JSON FORMAT.
     */
    public static String sendPostRequest(String Url, String filePath) throws IOException, ParseException {
        HttpURLConnection con=RestClientHandler.connectServer(Url, HTTPMethod.POST,HTTPRequestsContentTypes.JSON);
        readJSONArrayAndSend(filePath,con);
        String response=RestClientHandler.readResponse(con);
        return response;
    }


    /**
     *
     * @param Url: Specifies the URL we are going to send the request through.
     * @return the response of the DELETE request.
     * @throws IOException for connection lost.
     * Description: the function is used to send DELETE HTTP Request to API with specified URL
     */
    public static String sendDeleteRequest(String Url) throws IOException {
        HttpURLConnection con=RestClientHandler.connectServer(Url, HTTPMethod.DELETE,HTTPRequestsContentTypes.JSON);
        String response=RestClientHandler.readResponse(con);
        return response;
    }




}
