package ftn.ra122013.webshop.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import ftn.ra122013.webshop.beans.SimpleResponse;

public class JSONParser {
	public static String toJSON(Object obj){
		String retVal = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			retVal = mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static String getSimpleResponse(String msg){
		SimpleResponse response = new SimpleResponse(msg);
		return toJSON(response);
	}
}
