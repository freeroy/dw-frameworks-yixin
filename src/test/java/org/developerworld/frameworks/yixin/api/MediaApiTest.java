package org.developerworld.frameworks.yixin.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.developerworld.frameworks.yixin.api.dto.MediaType;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MediaApiTest {

	private static String accessToken;
	private static File imageFile;
	private static File thumbFile;
	private static File videoFile;
	private static File voiceFile;
	private static File voiceFile2;

	//@BeforeClass
	public static void before() throws IOException {
		accessToken = AccessTokenApi.getAccessToken(TestConfig.APP_ID,
				TestConfig.SECRET);
		imageFile = new File(MediaApiTest.class.getResource("/media/image.jpg")
				.getFile());
		thumbFile = new File(MediaApiTest.class.getResource("/media/thumb.jpg")
				.getFile());
		voiceFile = new File(MediaApiTest.class.getResource("/media/voice.amr")
				.getFile());
		voiceFile2 = new File(MediaApiTest.class.getResource("/media/voice.mp3")
				.getFile());
		videoFile = new File(MediaApiTest.class.getResource("/media/video.mp4")
				.getFile());
	}

	//@AfterClass
	public static void after() {
		accessToken = null;
		imageFile = thumbFile = videoFile=voiceFile2 = voiceFile = null;
	}

	//@Test
	public void testUploadMediaStringMediaTypeFile() throws HttpException,
			IOException {
		MediaType[] types = { MediaType.IMAGE, MediaType.THUMB, MediaType.VOICE, MediaType.VOICE,
				MediaType.VIDEO };
		File[] files = { imageFile, thumbFile,voiceFile,voiceFile2, videoFile };
		for (int i = 0; i < types.length; i++) {
			MediaType type = types[i];
			File file = files[i];
			String mediaId = MediaApi.uploadMedia(accessToken, type, file);
			System.out.println("upload media id:" + mediaId);
		}
	}

	//@Test
	public void testUploadMediaStringMediaTypeFileMap() throws HttpException,
			IOException {
		MediaType[] types = { MediaType.IMAGE, MediaType.THUMB, MediaType.VOICE, MediaType.VOICE,
				MediaType.VIDEO };
		File[] files = { imageFile, thumbFile,voiceFile,voiceFile2, videoFile };
		for (int i = 0; i < types.length; i++) {
			MediaType type = types[i];
			File file = files[i];
			Map result = new HashMap();
			MediaApi.uploadMedia(accessToken, type, file, result);
			Assert.assertTrue(result.size() > 0);
			System.out.println("upload media result:" + result);
		}
	}

}
