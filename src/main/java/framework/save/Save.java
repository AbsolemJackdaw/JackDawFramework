package framework.save;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;


public class Save {

	/**returns an object that is a JsonObject. 
	 * @param s String name for file*/
	public static JSONObject read(String s) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("saves/"+s+".json")));
			String line = "";
			StringBuilder src = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				src.append(line + "\n");
			}
			reader.close();
			
			JSONObject object = new JSONObject(src.toString());
			return object;
		} catch (FileNotFoundException e) {
			// System.out.println("[SAVING] Save file '" + s + "' not found.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**Writes a json file from the given DataTag
	 * @param s String name for file*/
	public static void write(String s, DataTag obj){
		try {

			File theDir = new File("saves/"+s+".json");
			theDir.getParentFile().mkdirs();

			FileWriter file = new FileWriter(theDir);
			file.write(obj.toString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}
