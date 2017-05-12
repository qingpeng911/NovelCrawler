package com.dsy.util;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * json解析工具类
 * @author qingpeng
 *
 */
public class JSONUtil {
	private static final Log log = LogFactory.getLog(JSONUtil.class);
	static Gson gson = new Gson();
	static JsonParser jParser = new JsonParser();
	
	/**
	 * 将对象转为json字符串
	 */
	public static String toJsonStr(Object object){
		return gson.toJson(object);
	}
	
	/**
	 * 将json字符串转为json对象
	 */
	public static JsonObject toJsonObject(String str){
		return jParser.parse(str).getAsJsonObject();
	}
	
	/**
	 * 获取String的值
	 */
	public static String getValueString(JsonElement element,String key){
		JsonObject object = element.getAsJsonObject();
		if (object == null || object.get(key) == null) {
			return "";
		}
		return object.get(key).getAsString();			
	}
	
	/**
	 * 将json字符串转换为Map对象
	 */
	public static Map<String, Map<String, Object>> toJson(String jsonStr) {
		return gson.fromJson(jsonStr, new TypeToken<HashMap<String,Map<String, Object>>>(){}.getType());
	}
	
	/**
	 * 将对象转为json格式
	 */
	public static Map<String, Object> getMapFromJsonFile(String filePath) {
		try {
			//FileInputStream fis = new FileInputStream("config/nickname_arrarys.json");
			FileInputStream fis = new FileInputStream(filePath);
			byte[] fisBytes = new byte[(int) fis.available()];
			fis.read(fisBytes);
			fis.close();
			String jsonStr = new String(fisBytes,"utf-8").replaceAll("//.*[\\n|\\r\\n]", "");
			jsonStr= jsonStr.replaceAll("/\\*[\\s\\S]*\\*/", "");
			jsonStr= jsonStr.replaceAll("#.*[\\n|\\r\\n]", "");
			Map<String,Object> map = gson.fromJson(jsonStr, new TypeToken<HashMap<String,Object>>(){}.getType()); 
			return map;
		} catch (Exception e) {
			log.error("读取json文件'"+filePath+"'出错，系统退出...", e);
			System.exit(1);
		}
		return null;
	}
	
	
	/**
	 * 将对象转为json格式
	 */
	public static Map<String,Map<String,Object>> getMapFromJsonFile1(String filePath) {
		try {
			//FileInputStream fis = new FileInputStream("config/nickname_arrarys.json");
			FileInputStream fis = new FileInputStream(filePath);
			byte[] fisBytes = new byte[(int) fis.available()];
			fis.read(fisBytes);
			fis.close();
			String jsonStr = new String(fisBytes,"utf-8").replaceAll("//.*[\\n|\\r\\n]", "");
			jsonStr= jsonStr.replaceAll("/\\*[\\s\\S]*\\*/", "");
			jsonStr= jsonStr.replaceAll("#.*[\\n|\\r\\n]", "");
			Map<String,Map<String,Object>> map = gson.fromJson(jsonStr, new TypeToken<HashMap<String,Map<String,Object>>>(){}.getType()); 
			return map;
		} catch (Exception e) {
			log.error("读取json文件'"+filePath+"'出错，系统退出...", e);
			System.exit(1);
		}
		return null;
	}
	
}
