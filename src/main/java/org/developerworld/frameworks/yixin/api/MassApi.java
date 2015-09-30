package org.developerworld.frameworks.yixin.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.developerworld.frameworks.yixin.message.MassMessage;
import org.developerworld.frameworks.yixin.message.converter.MassMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 群发API
 * 
 * @author Roy Huang
 * @version 20140409
 * 
 */
public class MassApi {

	private final static String MASS_API = "https://api.yixin.im/cgi-bin/message/group/send";

	private final static MassMessageConverter CONVERTER = new MassMessageConverter();

	private final static ObjectMapper objectMapper = new ObjectMapper();

	private final static String REQUEST_CONTENT_CHARSET = "UTF-8";

	/**
	 * 群发信息
	 * 
	 * @param accessToken
	 * @param message
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static boolean sendMessage(String accessToken, MassMessage message)
			throws HttpException, IOException {
		Map result = new HashMap();
		sendMessage(accessToken, message, result);
		if (result.get("errcode") == null
				|| result.get("errcode").toString().equals("0"))
			return true;
		return false;
	}

	/**
	 * 发送客服信息
	 * 
	 * @param accessToken
	 * @param message
	 * @param result
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void sendMessage(String accessToken, MassMessage message,
			Map result) throws HttpException, IOException {
		String url = MASS_API + "?access_token=" + accessToken;
		String content = CONVERTER.convertToJson(message);
		PostMethod method = new PostMethod(url);
		try {
			method.getParams().setContentCharset(REQUEST_CONTENT_CHARSET);
			HttpClient httpClient = new HttpClient();
			RequestEntity requestEntity = new StringRequestEntity(content,
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
