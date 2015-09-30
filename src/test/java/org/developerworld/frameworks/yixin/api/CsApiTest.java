package org.developerworld.frameworks.yixin.api;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.developerworld.frameworks.yixin.api.dto.MediaType;
import org.developerworld.frameworks.yixin.message.cs.CardCsMessage;
import org.developerworld.frameworks.yixin.message.cs.ImageCsMessage;
import org.developerworld.frameworks.yixin.message.cs.LinkCsMessage;
import org.developerworld.frameworks.yixin.message.cs.NewsCsMessage;
import org.developerworld.frameworks.yixin.message.cs.NewsCsMessage.Article;
import org.developerworld.frameworks.yixin.message.cs.TextCsMessage;
import org.developerworld.frameworks.yixin.message.cs.VideoCsMessage;
import org.developerworld.frameworks.yixin.message.cs.VoiceCsMessage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsApiTest {

	private static String accessToken;
	private static String openId;
	private static File imageFile;
	private static File videoFile;
	private static File voiceFile;

	//@BeforeClass
	public static void before() throws IOException {
		accessToken = AccessTokenApi.getAccessToken(TestConfig.APP_ID,
				TestConfig.SECRET);
		List<String> users = UserApi.getUsers(accessToken);
		openId = users.size() > 1 ? users.get(1) : null;
		imageFile = new File(MediaApiTest.class.getResource("/media/image.jpg")
				.getFile());
		voiceFile = new File(MediaApiTest.class.getResource("/media/voice.amr")
				.getFile());
		videoFile = new File(MediaApiTest.class.getResource("/media/video.mp4")
				.getFile());
	}

	//@AfterClass
	public static void after() {
		accessToken = openId = null;
		imageFile = videoFile = voiceFile = null;
	}

	//@Test
	public void testSendMessage() throws HttpException, IOException {
		TextCsMessage message1 = new TextCsMessage();
		message1.setToUser(openId);
		message1.setContent("text");
		CsApi.sendMessage(accessToken, message1);

		ImageCsMessage message2 = new ImageCsMessage();
		String imageMediaId = MediaApi.uploadMedia(accessToken,
				MediaType.IMAGE, imageFile);
		if (imageMediaId != null) {
			message2.setToUser(openId);
			message2.setMediaId(imageMediaId);
			CsApi.sendMessage(accessToken, message2);
		}
		
		LinkCsMessage message3 = new LinkCsMessage();
		message3.setToUser(openId);
		message3.setTitle("链接title");
		message3.setDescription("链接描述");
		message3.setUrl("http://www.163.com");
		CsApi.sendMessage(accessToken, message3);

		VideoCsMessage message4 = new VideoCsMessage();
		String voiceMediaId = MediaApi.uploadMedia(accessToken,
				MediaType.VOICE, voiceFile);
		if (voiceMediaId != null) {
			message4.setToUser(openId);
			message4.setMediaId(voiceMediaId);
			CsApi.sendMessage(accessToken, message4);
		}

		VoiceCsMessage message5 = new VoiceCsMessage();
		String videoMediaId = MediaApi.uploadMedia(accessToken,
				MediaType.VIDEO, videoFile);
		if (videoMediaId != null) {
			message5.setToUser(openId);
			message5.setMediaId(videoMediaId);
			CsApi.sendMessage(accessToken, message5);
		}

		NewsCsMessage message6 = new NewsCsMessage();
		message6.setToUser(openId);
		Article article = new Article();
		article.setDescription("description");
		article.setPicUrl("http://www.developerworld.org/aaa.jpg");
		article.setTitle("title");
		article.setUrl("http://www.163.com");
		message6.getAtricles().add(article);
		article = new Article();
		article.setDescription("description");
		article.setPicUrl("http://www.developerworld.org/aaa.jpg");
		article.setTitle("title");
		article.setUrl("http://www.163.com");
		message6.getAtricles().add(article);
		CsApi.sendMessage(accessToken, message6);
		
		CardCsMessage message7 = new CardCsMessage();
		message7.setToUser(openId);
		message7.setCard("YIXINID");
		CsApi.sendMessage(accessToken, message7);
	}

}
