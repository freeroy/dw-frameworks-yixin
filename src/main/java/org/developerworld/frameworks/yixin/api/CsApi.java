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
import org.developerworld.frameworks.yixin.message.CsMessage;
import org.developerworld.frameworks.yixin.message.converter.CsMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 客服信息API
 * 
 * @author Roy Huang
 * @version 20140307
 */
public class CsApi {

	private final static String SEND_MESSAGE_API = "https://api.yixin.im/cgi-bin/message/custom/send";

	private final static CsMessageConverter csMessageConverter = new CsMessageConverter();

	private final static ObjectMapper objectMapper = new ObjectMapper();

	private final static String REQUEST_CONTENT_CHARSET = "UTF-8";

	/**
	 * 发送客服信息
	 * 
	 * @param accessToken
	 * @param csMessage
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static boolean sendMessage(String accessToken, CsMessage csMessage)
			throws HttpException, IOException {
		Map result = new HashMap();
		sendMessage(accessToken, csMessage, result);
		if (result.get("errcode") == null
				|| result.get("errcode").toString().equals("0"))
			return true;
		return false;
	}

	/**
	 * 发送客服信息
	 * 
	 * @param accessToken
	 * @param csMessage
	 * @param result
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void sendMessage(String accessToken, CsMessage csMessage,
			Map result) throws HttpException, IOException {
		String url = SEND_MESSAGE_API + "?access_token=" + accessToken;
		String content = csMessageConverter.convertToJson(csMessage);
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
