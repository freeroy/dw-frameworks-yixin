package org.developerworld.frameworks.yixin.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.developerworld.frameworks.yixin.api.dto.PublicAccount;
import org.developerworld.frameworks.yixin.api.dto.User;
import org.developerworld.frameworks.yixin.api.dto.WebAuthScope;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 网页授权api
 * 
 * @author Roy Huang
 * @version 20140309
 * 
 */
public class WebAuthApi {

	private final static String AUTH_API = "http://open.plus.yixin.im/connect/oauth2/authorize";
	private final static String GET_TOKEN_API = "https://api.yixin.im/sns/oauth2/access_token";
	private final static String REFRESH_TOKEN_API = "https://api.yixin.im/sns/oauth2/refresh_token";
	private final static String GET_USER_INFO_API="https://api.yixin.im/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
	private final static String GET_ACCOUNT_INFO = "http://open.plus.yixin.im/app-connect/oauth2/pbinfo";

	private final static ObjectMapper objectMapper = new ObjectMapper();

	private final static String REQUEST_CONTENT_CHARSET = "UTF-8";

	/**
	 * 获取授权地址
	 * 
	 * @param appId
	 * @param redirectUri
	 * @param scope
	 * @param state
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getAuthUrl(String appId, String redirectUri,
			WebAuthScope scope, String state)
			throws UnsupportedEncodingException {
		String rst = AUTH_API + "?appid=" + appId + "&redirect_uri="
				+ URLEncoder.encode(redirectUri, "utf-8")
				+ "&response_type=code" + "&scope=" + scope.toString()
				+ "&state=" + state+"#yixin_redirect";
		return rst;
	}

	/**
	 * 获取access token
	 * 
	 * @param appId
	 * @param secret
	 * @param code
	 * @param state
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String getAccessToken(String appId, String secret,
			String code, String state)
			throws HttpException, IOException {
		String rst = null;
		Map result = new HashMap();
		getAccessToken(appId, secret, code, state, result);
		if (result.get("access_token") != null)
			rst = (String) result.get("access_token");
		return rst;
	}

	/**
	 * 获取access token
	 * 
	 * @param appId
	 * @param secret
	 * @param code
	 * @param state
	 * @param result
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void getAccessToken(String appId, String secret,
			String code, String state, Map result)
			throws HttpException, IOException {
		String url = GET_TOKEN_API + "?appid=" + appId
				+ "&secret=" + secret + "&code=" + code
				+ "&grant_type=authorization_code";
		fillUrlDataToResult(url, result);
	}

	/**
	 * 刷新access token
	 * 
	 * @param appId
	 * @param refreshToken
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String refreshAccessToken(String appId,
			String refreshToken) throws HttpException,
			IOException {
		String rst = null;
		Map result = new HashMap();
		refreshAccessToken(appId, refreshToken, result);
		if (result.get("access_token") != null)
			rst = (String) result.get("access_token");
		return rst;
	}

	/**
	 * 刷新access token
	 * 
	 * @param appId
	 * @param refreshToken
	 * @param result
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void refreshAccessToken(String appId, String refreshToken,
			Map result) throws HttpException, IOException {
		String url = REFRESH_TOKEN_API + "?appid=" + appId
				+ "&grant_type=refresh_token" + "&refresh_token="
				+ refreshToken;
		fillUrlDataToResult(url, result);
	}
	
	public User getUserInfo(String accessToken,String openId) throws HttpException, IOException{
		User rst=null;
		Map result=new HashMap();
		getUserInfo(accessToken,openId,result);
		return UserApi.getUserByResult(result);
	}

	public void getUserInfo(String accessToken, String openId, Map result) throws HttpException, IOException {
		String url = GET_USER_INFO_API + "?access_token=" + accessToken
				+ "&openid=" +openId;
		fillUrlDataToResult(url, result);
	}

	/**
	 * 获取公众平台信息
	 * 
	 * @param openpid
	 * @param accessToken
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static PublicAccount getPublicAccount(String openpid,
			String accessToken) throws HttpException, IOException {
		Map result = new HashMap();
		getPublicAccount(openpid, accessToken, result);
		if (result.get("nick") != null) {
			PublicAccount rst = new PublicAccount();
			rst.setAppId((String) result.get("appid"));
			rst.setNick((String) result.get("nick"));
			rst.setOpenPid((String) result.get("openpid"));
			return rst;
		}
		return null;
	}

	/**
	 * 获取公众平台信息
	 * 
	 * @param openpid
	 * @param accessToken
	 * @param result
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void getPublicAccount(String openpid, String accessToken,
			Map result) throws HttpException, IOException {
		String url = GET_ACCOUNT_INFO + "?openpid=" + openpid
				+ "&access_token=" + accessToken;
		fillUrlDataToResult(url, result);
	}
	
	/**
	 * 注入请求地址的数据
	 * @param url
	 * @param result
	 * @throws IOException
	 * @throws HttpException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 */
	private static void fillUrlDataToResult(String url, Map result) throws IOException,
			HttpException, JsonParseException, JsonMappingException {
		GetMethod method = new GetMethod(url);
		try {
			method.getParams().setContentCharset(REQUEST_CONTENT_CHARSET);
			HttpClient client = new HttpClient();
			int status = client.executeMethod(method);
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
