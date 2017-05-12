package com.dsy.util;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.JsonObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CrawlerUtil {
	public static OkHttpClient client = new OkHttpClient();
	
	private static final Log log = LogFactory.getLog(CrawlerUtil.class);
	
	private static final String DEFAULT_MOBILE_UA = "Mozilla/5.0 (Linux; U; Android 4.4.4; zh-CN; CHM-TL00H Build/tt) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 UCNewsApp/2.7.6.957  Mobile Safari/534.30 AliApp(TUnionSDK/0.2.2)";
	
	/**
	 * get请求,失败返回null
	 */
	public static JsonObject get(String url){
		try {
			Request request = new Request.Builder()
					.url(url)
					.header("User-Agent", DEFAULT_MOBILE_UA)
					.build();
			Response response = client.newCall(request).execute();
			String res = response.body().string();
			return JSONUtil.toJsonObject(res);
		} catch (Exception e) {
			log.warn("GET请求失败！",e);
		}
		return null;
	}
	
	
	/**
	 * POST请求,失败返回null
	 */
	public static JsonObject post(String url,Map<String, String> reqParams){
		FormBody.Builder params = new FormBody.Builder();
		//参数转换
		for (Map.Entry<String, String> entry : reqParams.entrySet()) {
			params.add(entry.getKey(), entry.getValue());
		}
		Request request = new Request.Builder().url(url).post(params.build()).build();
		try {
			Response response = client.newCall(request).execute();
			String res = response.body().string();
			return JSONUtil.toJsonObject(res);
		} catch (IOException e) {
			log.warn("POST请求失败！",e);
		}
		return null;
	}
	
	/**
	 * 请求网页
	 */
	public static Document getDocument(String url){
		try {
			Document doc = Jsoup.connect(url)
					.userAgent(DEFAULT_MOBILE_UA)
					.timeout(5000)
					.get();
			return doc;
		} catch (IOException e) {
			log.warn("请求Document失败！",e);
		}
		return null;
	}

}