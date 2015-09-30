package org.developerworld.frameworks.yixin.message.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.developerworld.frameworks.yixin.message.CsMessage;
import org.developerworld.frameworks.yixin.message.MsgType;
import org.developerworld.frameworks.yixin.message.cs.AbstractCsMessage;
import org.developerworld.frameworks.yixin.message.cs.CardCsMessage;
import org.developerworld.frameworks.yixin.message.cs.ImageCsMessage;
import org.developerworld.frameworks.yixin.message.cs.LinkCsMessage;
import org.developerworld.frameworks.yixin.message.cs.NewsCsMessage;
import org.developerworld.frameworks.yixin.message.cs.NewsCsMessage.Article;
import org.developerworld.frameworks.yixin.message.cs.TextCsMessage;
import org.developerworld.frameworks.yixin.message.cs.VideoCsMessage;
import org.developerworld.frameworks.yixin.message.cs.VoiceCsMessage;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 客服信息转换器
 * 
 * @author Roy Huang
 * @version 20140310
 * 
 */
public class CsMessageConverter {

	private final static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 转换为json
	 * 
	 * @param csMessage
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String convertToJson(CsMessage csMessage)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map msg = new LinkedHashMap();
		if (csMessage instanceof AbstractCsMessage) {
			AbstractCsMessage _msg = (AbstractCsMessage) csMessage;
			msg.put("touser", _msg.getToUser());
		}
		if (csMessage instanceof TextCsMessage) {
			msg.put("msgtype", MsgType.TEXT.toString());
			TextCsMessage _msg = (TextCsMessage) csMessage;
			Map text = new LinkedHashMap();
			text.put("content", _msg.getContent());
			msg.put("text", text);
		}
		else if (csMessage instanceof ImageCsMessage) {
			msg.put("msgtype", MsgType.IMAGE.toString());
			ImageCsMessage _msg = (ImageCsMessage) csMessage;
			Map image = new LinkedHashMap();
			image.put("media_id", _msg.getMediaId());
			msg.put("image", image);
		}
		else if (csMessage instanceof VoiceCsMessage) {
			msg.put("msgtype", MsgType.VOICE.toString());
			VoiceCsMessage _msg = (VoiceCsMessage) csMessage;
			Map voice = new LinkedHashMap();
			voice.put("media_id", _msg.getMediaId());
			msg.put("voice", voice);
		}
		else if (csMessage instanceof VideoCsMessage) {
			msg.put("msgtype", MsgType.VIDEO.toString());
			VideoCsMessage _msg = (VideoCsMessage) csMessage;
			Map video = new LinkedHashMap();
			video.put("media_id", _msg.getMediaId());
			msg.put("video", video);
		}
		else if (csMessage instanceof LinkCsMessage) {
			msg.put("msgtype", MsgType.LINK.toString());
			LinkCsMessage _msg = (LinkCsMessage) csMessage;
			Map link = new LinkedHashMap();
			link.put("title", _msg.getTitle());
			link.put("description", _msg.getDescription());
			link.put("url", _msg.getUrl());
			msg.put("link", link);
		}
		else if (csMessage instanceof CardCsMessage) {
			msg.put("msgtype", MsgType.CARD.toString());
			CardCsMessage _msg = (CardCsMessage) csMessage;
			Map card = new LinkedHashMap();
			card.put("card", _msg.getCard());
			msg.put("card", card);
		}
		else if (csMessage instanceof NewsCsMessage) {
			msg.put("msgtype", MsgType.NEWS.toString());
			NewsCsMessage _msg = (NewsCsMessage) csMessage;
			Map news = new LinkedHashMap();
			List<Map> articles = new ArrayList<Map>();
			for (Article article : _msg.getAtricles()) {
				Map _article = new LinkedHashMap();
				if (StringUtils.isNotBlank(article.getTitle()))
					_article.put("title", article.getTitle());
				if (StringUtils.isNotBlank(article.getDescription()))
					_article.put("description", article.getDescription());
				if (StringUtils.isNotBlank(article.getUrl()))
					_article.put("url", article.getUrl());
				if (StringUtils.isNotBlank(article.getPicUrl()))
					_article.put("picurl", article.getPicUrl());
				articles.add(_article);
			}
			news.put("articles", articles);
			msg.put("news", news);
		}
		return objectMapper.writeValueAsString(msg);
	}
}
