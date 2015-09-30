package org.developerworld.frameworks.yixin.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.developerworld.frameworks.yixin.api.dto.User;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserApiTest {

	private static String accessToken;
	private static String openId;
	private static String mobile;

	//@BeforeClass
	public static void before() throws IOException {
		accessToken = AccessTokenApi.getAccessToken(TestConfig.APP_ID,
				TestConfig.SECRET);
		List<String> users = UserApi.getUsers(accessToken);
		openId = users.size() > 1 ? users.get(1) : null;
		mobile = "18620900463";
	}

	//@AfterClass
	public static void after() {
		accessToken = openId = mobile = null;
	}

	//@Test
	public void testGetUserStringStringMap() throws IOException {
		if (openId != null) {
			Map result = new HashMap();
			UserApi.getUser(accessToken, openId, result);
			Assert.assertTrue(result.size() > 0);
			System.out.println("get user result:" + result);
		}
	}

	//@Test
	public void testGetUserStringString() throws IOException {
		if (openId != null) {
			User user = UserApi.getUser(accessToken, openId);
			System.out.println("get user:" + user);
		}
	}

	//@Test
	public void testGetUsersStringIntInt() throws HttpException, IOException {
		List<String> users = UserApi.getUsers(accessToken, 1, 50);
		Assert.assertNotNull(users);
		System.out.println("get user pageNum,pageSize:" + users);
		users = UserApi.getUsers(accessToken, 101, 100);
		Assert.assertNotNull(users);
		System.out.println("get user pageNum,pageSize:" + users);
	}

	//@Test
	public void testGetUsersStringStringMap() throws HttpException, IOException {
		if (openId != null) {
			Map result = new HashMap();
			UserApi.getUsers(accessToken, openId, result);
			Assert.assertTrue(result.size() > 0);
			System.out.println("get users result:" + result);
		}
	}

	//@Test
	public void testGetUsersStringMap() throws HttpException, IOException {
		Map result = new HashMap();
		UserApi.getUsers(accessToken, result);
		Assert.assertTrue(result.size() > 0);
		System.out.println("get users result:" + result);
	}

	//@Test
	public void testGetUsersStringString() throws HttpException, IOException {
		if (openId != null) {
			List<String> users = UserApi.getUsers(accessToken, openId);
			Assert.assertNotNull(users);
			System.out.println("get users:" + users);
		}
	}

	//@Test
	public void testGetUsersString() throws HttpException, IOException {
		List<String> users = UserApi.getUsers(accessToken);
		Assert.assertNotNull(users);
		System.out.println("get users:" + users);
	}

	//@Test
	public void testGetUserDetailStringString() throws IOException {
		User user = UserApi.getUserDetail(accessToken, openId);
		Assert.assertNotNull(true);
		System.out.println("get user detail:" + user);
	}

	//@Test
	public void testGetUserDetailStringStringMap() throws IOException {
		Map result = new HashMap();
		UserApi.getUserDetail(accessToken, openId, result);
		System.out.println("get user detail result:" + result);
	}

	//@Test
	public void testGetUserCount() throws HttpException, IOException {
		Long count = UserApi.getUserCount(accessToken);
		//Assert.assertNotNull(count);
	}

	//@Test
	public void testValidUserStringString() throws HttpException, IOException {
		String openId = UserApi.validUser(accessToken, mobile);
		//Assert.assertNotNull(openId);
	}

	//@Test
	public void testValidUserStringStringMap() throws HttpException,
			IOException {
		Map result = new HashMap();
		UserApi.validUser(accessToken, mobile, result);
		Assert.assertNotNull(result);
	}

	//@Test
	public void testGetUserFriendsStringString() throws HttpException,
			IOException {
		List<String> openIds = UserApi.getUserFriends(accessToken, openId);
		//Assert.assertNotNull(openIds);
	}

	//@Test
	public void testGetUserFriendsStringStringMap() throws HttpException,
			IOException {
		Map result = new HashMap();
		UserApi.getUserFriends(accessToken, openId, result);
		Assert.assertNotNull(result);
	}

	//@Test
	public void testGetUserFriendCountStringString() throws HttpException,
			IOException {
		Long count = UserApi.getUserFriendCount(accessToken, openId);
		//Assert.assertNotNull(count);
	}

	//@Test
	public void testGetUserFriendCountStringStringMap() throws HttpException,
			IOException {
		Map result = new HashMap();
		UserApi.getUserFriendCount(accessToken, openId, result);
		Assert.assertNotNull(result);
	}
	
	//@Test
	public void testGetPrivateUserStringStringMap() throws IOException{
		Map result = new HashMap();
		UserApi.getPrivateUser(accessToken, openId, result);
		Assert.assertNotNull(result);
	}
	
	//@Test
	public void testGetPrivateUserStringString() throws IOException{
		User user=UserApi.getPrivateUser(accessToken, openId);
		//Assert.assertNotNull(user);//高级接口可能不开通
	}

}
