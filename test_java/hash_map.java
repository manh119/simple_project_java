package test_java;
import java.util.HashMap;


public class hash_map {
	public static void main(String[] args) {
		HashMap<String, String> people= new HashMap<String, String>();
		
		people.put("manh", "dep trai");
		people.put("minh", "khong dep trai");
		
		for(String key : people.keySet())
			System.out.println(key + " : " + people.get(key));
	}
}
