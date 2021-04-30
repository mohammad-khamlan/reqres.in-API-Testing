package Utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONUtils {

    /**
     *
     * @param filePath: refers to the file we want to read.
     * @return String contains the contents of the File as JSON parsed Object
     * @throws IOException for file not found
     * @throws ParseException for parsing problems
     * description: The function gets the path of the file and read the
     *              file Then parse it as JSON and return Object type to be casted
     */
    public static Object readFile(String filePath) throws IOException, ParseException {
        FileReader reader= new FileReader(filePath);
        JSONParser parser = new JSONParser();
        return parser.parse(reader);
    }


    /**
     * 
     * @param filePath: refers to the file we want to read as json format.
     * @return the String containing the JSON Array.
     * @throws IOException for file not found
     * @throws ParseException for parsing problems
     * description: The function gets the path of the file and read the file Then Convert it into String Contains JSON Array
     */
    public static String readJSONArrayFromFile(String filePath) throws IOException, ParseException {

        JSONArray jsonArray = (JSONArray) readFile(filePath);
        String data = jsonArray.toString();
        return data;

    }

    
    public static JSONArray readJSONArrayFromFileAndReturnJSON(String filePath) throws IOException, ParseException {

        JSONArray jsonArray = (JSONArray) readFile(filePath);
//        String data = jsonArray.toString();
        return jsonArray;

    }
    /**
     * 
     * @param filePath: refers to the file we want to read as json format.
     * @return the String containing the JSON Object.
     * @throws IOException for file not found
     * @throws ParseException for parsing problems
     * description: The function gets the path of the file and read the file Then Convert it into String Contains JSON Object
     */

    public static String readJSONObjectFromFile(String filePath) throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) readFile(filePath);
        String data = jsonObject.toString();
        return data;

    }

    
    public static JSONObject readJSONObjectFromFileAndReturnJSON(String filePath) throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) readFile(filePath);
        return jsonObject;

    }
    /**
     *
     * @param jsonObject String which we are going to convert to JSONObject
     * @return returns JSONObject of Object to be casted to JSONArray or JSONObject
     * @throws ParseException for any parsing problems
     */
    public static Object convertStringToJSON(String jsonObject) throws ParseException {
        JSONParser parser = new JSONParser();
        return parser.parse(jsonObject);
    }






}
