package org.developerworld.frameworks.yixin.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class BarCodeApiTest {

	private static String accessToken;
	private static String picUrl;

	//@BeforeClass
	public static void before() throws IOException {
		accessToken = AccessTokenApi.getAccessToken(TestConfig.APP_ID,
				TestConfig.SECRET);
		picUrl = "http://pigimg.zhongso.com/space/gallery/2011/09/26/16/b2b_20110826044426635446.jpg";
	}

	//@AfterClass
	public static void after() {
		accessToken = picUrl = null;
	}

	//@Test
	public void testDetectBarCodeStringString() throws JsonGenerationException,
			JsonMappingException, IOException {
		String serialNo=BarCodeApi.detectBarCode(accessToken, picUrl);
		System.out.println("detect bar code serialNo:" + serialNo);
	}

	//@Test
	public void testDetectBarCodeStringStringMap()
			throws JsonGenerationException, JsonMappingException, IOException {
		Map result = new HashMap();
		BarCodeApi.detectBarCode(accessToken, picUrl, result);
		Assert.assertTrue(result.size() > 0);
		System.out.println("detect bar code result:" + result);
	}

}
