package org.developerworld.frameworks.yixin.api;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.developerworld.frameworks.yixin.message.mass.NewsMassMessage;
import org.developerworld.frameworks.yixin.message.mass.TextMassMessage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 群发接口测试
 * @author Roy Huang
 *
 */
public class MassApiTest {
	
	private static String accessToken;

	//@BeforeClass
	public static void before() throws IOException {
		accessToken = AccessTokenApi.getAccessToken(TestConfig.APP_ID,
				TestConfig.SECRET);
	}

	//@AfterClass
	public static void after() {
		accessToken = null;
	}

	//@Test
	public void testSendMessageStringMassMessage() throws HttpException, IOException {
		TextMassMessage textMassMessage=new TextMassMessage();
		textMassMessage.setContent("测试测试");
		boolean rst=MassApi.sendMessage(accessToken, textMassMessage);
		
		NewsMassMessage newsMassMessage=new NewsMassMessage();
		NewsMassMessage.Article atricle=new NewsMassMessage.Article();
		atricle.setTitle("title");
		atricle.setPicUrl("http://tp2.sinaimg.cn/2000453301/180/5603082689/1");
		atricle.setUrl("http://www.163.com");
		atricle.setDescription("description");
		newsMassMessage.getAtricles().add(atricle);
		newsMassMessage.getAtricles().add(atricle);
		rst=MassApi.sendMessage(accessToken, newsMassMessage);
		
		Assert.assertTrue(true);
	}

	//@Test
	public void testSendMessageStringMassMessageMap() {
		Assert.assertTrue(true);
	}

}
