package org.developerworld.frameworks.yixin.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 条形码API
 * 
 * @author Roy Huang
 * @version 20140310
 * 
 */
public class BarCodeApi {
	
	private final static String DETECT_BAR_CODE_API = "https://api.yixin.im/cgi-bin/barcode/detect";

	private final static ObjectMapper objectMapper = new ObjectMapper();
	private final static String REQUEST_CONTENT_CHARSET = "UTF-8";

	/**
	 * 获取条形码信息
	 * 
	 * @param accessToken
	 * @param picUrl
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String detectBarCode(String accessToken, String picUrl)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map result = new HashMap();
		detectBarCode(accessToken, picUrl, result);
		if (result.get("serialNo") != null)
			return (String) result.get("serialNo");
		return null;
	}

	/**
	 * 获取条形码信息
	 * 
	 * @param accessToken
	 * @param picUrl
	 * @param result
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void detectBarCode(String accessToken, String picUrl,
			Map result) throws JsonGenerationException, JsonMappingException,
			IOException {
		String url = DETECT_BAR_CODE_API + "?access_token=" + accessToken;
		Map data = new LinkedHashMap();
		data.put("url", picUrl);
		String jsonStr = objectMapper.writeValueAsString(data);
		PostMethod method = new PostMethod(url);
		try {
			method.getParams().setContentCharset(REQUEST_CONTENT_CHARSET);
			HttpClient httpClient = new HttpClient();
			RequestEntity requestEntity = new StringRequestEntity(jsonStr,
					"application/x-www-form-urlencoded", "utf-8");
			method.setRequestEntity(requestEntity);
			int status = httpClient.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				if (result != null) {
					String response = method.getResponseBodyAsString();
					Map json = objectMapper.readValue(response, Map.class);
					result.putAll(json);
				}
			}
		} finally {
			method.releaseConnection();
		}
	}
}
