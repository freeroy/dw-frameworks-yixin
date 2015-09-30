package org.developerworld.frameworks.yixin.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.developerworld.frameworks.yixin.api.dto.User;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 用户api
 * 
 * @author Roy Huang
 * @version 20140309
 * 
 */
public class UserApi {

	private final static String GET_USER_API = "https://api.yixin.im/cgi-bin/user/info";

	private final static String GET_USER_DETAIL_API = "https://api.yixin.im/cgi-bin/user/uinfo";

	private final static String GET_USER_PRIVATE_INFO = "https://api.yixin.im/private/user/info";

	private final static String GET_USERS_API = "https://api.yixin.im/cgi-bin/user/get";

	private final static String VALID_USERS_API = "https://api.yixin.im/cgi-bin/user/valid";

	private final static String GET_USER_FRIENDS = "https://api.yixin.im/cgi-bin/user/friends";

	private final static String GET_USER_FRIEND_COUNT = "https://api.yixin.im/cgi-bin/user/friendCount";

	private final static ObjectMapper objectMapper = new ObjectMapper();

	private final static String REQUEST_CONTENT_CHARSET = "UTF-8";

	private final static int MAX_GET_USER_COUNT = 10000;

	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @param result
	 * @throws IOException
	 */
	public static void getUser(String accessToken, String openId, Map result)
			throws IOException {
		String url = GET_USER_API + "?access_token=" + accessToken + "&openid="
				+ openId;
		fillUrlDataToResult(url, result);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @param result
	 * @throws IOException
	 */
	public static User getUser(String accessToken, String openId)
			throws IOException {
		Map result = new HashMap();
		getUser(accessToken, openId, result);
		return getUserByResult(result);
	}

	/**
	 * 获取用户信息
	 * @param result
	 * @return
	 */
	static User getUserByResult(Map result) {
		User rst = null;
		if (result.get("subscribe") != null) {
			rst=new User();
			if (result.containsKey("subscribe"))
				rst.setSubscribe(Byte.valueOf(result.get("subscribe").toString()));
			if (result.containsKey("city"))
				rst.setCity((String) result.get("city"));
			if (result.containsKey("language"))
				rst.setLanguage((String) result.get("language"));
			if (result.containsKey("nickname"))
				rst.setNickname((String) result.get("nickname"));
			if (result.containsKey("openid"))
				rst.setOpenId((String) result.get("openid"));
			if (result.containsKey("sex"))
				rst.setSex(Byte.valueOf(result.get("sex").toString()));
			if (result.containsKey("mobile"))
				rst.setMobile((String) result.get("mobile"));
			if (result.containsKey("headimgurl"))
				rst.setHeadImgUrl(result.get("headimgurl").toString());
			if (result.containsKey("subscribe_time"))
				rst.setSubscribeTime(Long.valueOf(result.get("subscribe_time")
						.toString()));
			if (result.containsKey("bkImage"))
				rst.setBkImage(result.get("bkImage").toString());
		}
		return rst;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @param result
	 * @throws IOException
	 */
	public static User getPrivateUser(String accessToken, String openId)
			throws IOException {
		Map result = new HashMap();
		getPrivateUser(accessToken, openId, result);
		return getUserByResult(result);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @param result
	 * @throws IOException
	 */
	public static void getPrivateUser(String accessToken, String openId,
			Map result) throws IOException {
		String url = GET_USER_PRIVATE_INFO + "?access_token=" + accessToken
				+ "&openid=" + openId;
		fillUrlDataToResult(url, result);
	}

	/**
	 * 获取用户详细信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @param result
	 * @throws IOException
	 */
	public static User getUserDetail(String accessToken, String openId)
			throws IOException {
		Map result = new HashMap();
		getUserDetail(accessToken, openId, result);
		return getUserByResult(result);
	}

	/**
	 * 获取用户详细信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @param result
	 * @throws IOException
	 */
	public static void getUserDetail(String accessToken, String openId,
			Map result) throws IOException {
		String url = GET_USER_DETAIL_API + "?access_token=" + accessToken
				+ "&openid=" + openId;
		fillUrlDataToResult(url, result);
	}

	/**
	 * 获取用户数
	 * 
	 * @param accessToken
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static Long getUserCount(String accessToken) throws HttpException,
			IOException {
		Map result = new HashMap();
		getUsers(accessToken, null, result);
		if (result.get("total") != null)
			return Long.valueOf(result.get("total").toString());
		return null;
	}

	/**
	 * 获取用户数据
	 * 
	 * @param accessToken
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static List<String> getUsers(String accessToken, int pageNum,
			int pageSize) throws HttpException, IOException {
		// 根据pageNum、pageSize.获取开始检索位置
		int bIndex = (pageNum - 1) * pageSize;
		// 由于每次最大检索1W，所以需要知道要执行多少次接口操作
		int callTime = bIndex / MAX_GET_USER_COUNT + 1;
		String nextOpenId = null;
		Map result = null;
		while (callTime > 0) {
			--callTime;
			result = new HashMap();
			getUsers(accessToken, nextOpenId, result);
			nextOpenId = (String) result.get("next_openid");
			// 若已经不能再向后获取，就退出
			if (nextOpenId == null)
				break;
		}
		// 若没完成预计的执行次数，代表无法获取指定分页条件的数据，返回空集合
		if (callTime > 0)
			return new ArrayList<String>();
		else {
			List<String> users = new ArrayList<String>();
			if (result.get("data") != null && result.get("data") instanceof Map) {
				Object openidObj = ((Map) result.get("data")).get("openid");
				if (openidObj != null && openidObj instanceof Collection) {
					Collection openIdColl = (Collection) openidObj;
					List openIdsList = null;
					if (!(openIdColl instanceof List))
						openIdsList = new ArrayList(openIdColl);
					else
						openIdsList = (List) openIdColl;
					// 根据分页参数，计算在本《=1W条数据中，提取pagesize条
					int begin = bIndex % MAX_GET_USER_COUNT;
					int end = Math.min(begin + pageSize, openIdColl.size());
					for (int i = begin; i < end; i++)
						users.add(openIdsList.get(i).toString());
				}
			}
			return users;
		}
	}

	/**
	 * 获取关注用户信息
	 * 
	 * @param accessToken
	 * @param nextOpenId
	 * @param result
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void getUsers(String accessToken, String nextOpenId,
			Map result) throws HttpException, IOException {
		String url = GET_USERS_API + "?access_token=" + accessToken;
		if (nextOpenId != null)
			url += "&next_openid=" + nextOpenId;
		fillUrlDataToResult(url, result);
	}

	/**
	 * 获取关注用户信息
	 * 
	 * @param accessToken
	 * @param result
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void getUsers(String accessToken, Map result)
			throws HttpException, IOException {
		getUsers(accessToken, null, result);
	}

	/**
	 * 获取关注用户信息
	 * 
	 * @param accessToken
	 * @param nextOpenId
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static List<String> getUsers(String accessToken, String nextOpenId)
			throws HttpException, IOException {
		List<String> rst = new ArrayList<String>();
		Map result = new HashMap();
		getUsers(accessToken, nextOpenId, result);
		if (result.get("data") != null && result.get("data") instanceof Map) {
			Object openid = ((Map) result.get("data")).get("openid");
			if (openid != null && openid instanceof Collection) {
				for (Object data : (Collection) openid)
					rst.add(data.toString());
			}
		}
		return rst;
	}

	/**
	 * 获取关注用户信息
	 * 
	 * @param accessToken
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static List<String> getUsers(String accessToken)
			throws HttpException, IOException {
		return getUsers(accessToken, (String) null);
	}

	/**
	 * 验证用户信息
	 * 
	 * @param accessToken
	 * @param mobile
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public static String validUser(String accessToken, String mobile)
			throws HttpException, IOException {
		Map result = new HashMap();
		validUser(accessToken, mobile, result);
		if (result.get("openid") != null)
			return (String) result.get("openid");
		return null;
	}

	/**
	 * 验证用户信息
	 * 
	 * @param accessToken
	 * @param mobile
	 * @param result
	 * @throws IOException
	 * @throws HttpException
	 */
	public static void validUser(String accessToken, String mobile, Map result)
			throws HttpException, IOException {
		String url = VALID_USERS_API + "?access_token=" + accessToken
				+ "&mobile=" + mobile;
		fillUrlDataToResult(url, result);
	}

	/**
	 * 获取统一公众号中，指定用户的关注好友信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static List<String> getUserFriends(String accessToken, String openId)
			throws HttpException, IOException {
		Map result = new HashMap();
		getUserFriends(accessToken, openId, result);
		if (result.get("openids") != null)
			return Arrays.asList(((String) result.get("openids")).split(","));
		return null;
	}

	/**
	 * 获取统一公众号中，指定用户的关注好友信息
	 * 
	 * @param accessToken
	 * @param openId
	 * @param result
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void getUserFriends(String accessToken, String openId,
			Map result) throws HttpException, IOException {
		String url = GET_USER_FRIENDS + "?access_token=" + accessToken
				+ "&openid=" + openId;
		fillUrlDataToResult(url, result);
	}

	/**
	 * 获取用户好友数目
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public static Long getUserFriendCount(String accessToken, String openId)
			throws HttpException, IOException {
		Map result = new HashMap();
		getUserFriendCount(accessToken, openId, result);
		if (result.get("count") != null)
			return Long.valueOf(result.get("count").toString());
		return null;
	}

	/**
	 * 获取用户好友数量
	 * 
	 * @param accessToken
	 * @param openId
	 * @param result
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void getUserFriendCount(String accessToken, String openId,
			Map result) throws HttpException, IOException {
		String url = GET_USER_FRIEND_COUNT + "?access_token=" + accessToken
				+ "&openid=" + openId;
		fillUrlDataToResult(url, result);
	}

	/**
	 * 注入请求地址数据
	 * 
	 * @param url
	 * @param result
	 * @throws IOException
	 * @throws HttpException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 */
	private static void fillUrlDataToResult(String url, Map result)
			throws IOException, HttpException, JsonParseException,
			JsonMappingException {
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
