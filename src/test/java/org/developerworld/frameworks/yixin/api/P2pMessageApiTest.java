package org.developerworld.frameworks.yixin.api;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.developerworld.frameworks.yixin.message.p2p.LinkP2pMessage;
import org.developerworld.frameworks.yixin.message.p2p.TextP2pMessage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class P2pMessageApiTest {

	private static String accessToken;
	private static String openId;

	//@BeforeClass
	public static void before() throws IOException {
		accessToken = AccessTokenApi.getAccessToken(TestConfig.APP_ID,
				TestConfig.SECRET);
		List<String> users = UserApi.getUsers(accessToken);
		openId = users.size() > 1 ? users.get(1) : null;
	}

	//@AfterClass
	public static void after() {
		accessToken = openId = null;
	}

	//@Test
	public void testSendMessageStringP2pMessage() throws HttpException,
			IOException {
		TextP2pMessage message1 = new TextP2pMessage();
		message1.setContent("text");
		message1.setToUser(openId);
		boolean rst = P2pMessageApi.sendMessage(accessToken, message1);
		//Assert.assertTrue(rst);

		LinkP2pMessage message2 = new LinkP2pMessage();
		message2.setTitle("title");
		message2.setDescription("description");
		message2.setToUser(openId);
		message2.setUrl("http://www.163.com");
		rst = P2pMessageApi.sendMessage(accessToken, message2);
		//Assert.assertTrue(rst);
	}

	//@Test
	public void testSendMessageStringP2pMessageMap() {
		Assert.assertTrue(true);
	}

}
