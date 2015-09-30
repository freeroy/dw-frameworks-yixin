package org.developerworld.frameworks.yixin.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.developerworld.frameworks.yixin.api.dto.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 媒体API
 * 
 * @author Roy Huang
 * @version 20140307
 * 
 */
public class MediaApi {

	private final static ObjectMapper objectMapper = new ObjectMapper();

	private final static String UPLOAD_MEDIA_API = "https://api.yixin.im/cgi-bin/media/upload";

//	private final static int UPLOAD_CONNECTION_TIMEOUT = 5000;

	private final static String REQUEST_CONTENT_CHARSET = "UTF-8";

	private final static Map<String, String> CONTENT_TYPE = new HashMap<String, String>();
	static {
		CONTENT_TYPE.put("jpg", "image/jpeg");
		CONTENT_TYPE.put("amr", "audio/amr");
		CONTENT_TYPE.put("mp3", "audio/mp3");
		CONTENT_TYPE.put("mp4", "video/mp4");
	}

	/**
	 * 上传媒体
	 * 
	 * @param accessToken
	 * @param type
	 * @param media
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String uploadMedia(String accessToken, MediaType type,
			File media) throws HttpException, IOException {
		Map result = new HashMap();
		uploadMedia(accessToken, type, media, result);
		if (result.get("type") != null) {
			return result.get("media_id").toString();
//			if (type.equals(MediaType.IMAGE) && result.get("media_id") != null)
//				return (String) result.get("media_id");
//			else if (type.equals(MediaType.THUMB)
//					&& result.get("thumb_media_id") != null)
//				return (String) result.get("thumb_media_id");
//			else if (type.equals(MediaType.VIDEO)
//					&& result.get("media_id") != null)
//				return (String) result.get("media_id");
//			else if (type.equals(MediaType.VOICE)
//					&& result.get("media_id") != null)
//				return (String) result.get("media_id");
		}
		return null;
	}

	public static void uploadMedia(String accessToken, MediaType type,
			File media, Map result) throws HttpException, IOException {
		String url = UPLOAD_MEDIA_API + "?access_token=" + accessToken
				+ "&type=" + type;
		PostMethod method = new PostMethod(url);
		try {
			method.getParams().setContentCharset(REQUEST_CONTENT_CHARSET);
			// 避免缓存
			method.getParams().setBooleanParameter(
					HttpMethodParams.USE_EXPECT_CONTINUE, false);
			// 添加附件
			method.setRequestEntity(new MultipartRequestEntity(
					new Part[] { new FilePart("media", media) }, method
							.getParams()));
			HttpClient client = new HttpClient();
			// 设置超时时间
			// client.getHttpConnectionManager().getParams()
			// .setConnectionTimeout(UPLOAD_CONNECTION_TIMEOUT);
			// 执行上传
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
