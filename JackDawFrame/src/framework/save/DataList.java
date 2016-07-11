package framework.save;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataList {

	private final JSONArray array;

	public DataList(){
		array = new JSONArray();
	}
	
	public DataList(JSONArray array){
		this.array = array;
	}

	public void write(DataTag tag){
		array.put(tag.getData());
	}
	
	public JSONArray data(){
		return array;
	}
	
	public DataTag readArray(int i){
		
		if(i > array.length()){
			System.out.println("Index " + i + " is out of bounds ! No DataTag found in the DataList");
			return null;
		}
		
		JSONObject obj = (JSONObject)array.get(i);
		DataTag tag = new DataTag(obj);
		
		return tag;
	}

}
