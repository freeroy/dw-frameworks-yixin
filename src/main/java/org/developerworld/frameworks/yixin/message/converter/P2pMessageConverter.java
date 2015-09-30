package org.developerworld.frameworks.yixin.message.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.developerworld.frameworks.yixin.message.MsgType;
import org.developerworld.frameworks.yixin.message.P2pMessage;
import org.developerworld.frameworks.yixin.message.p2p.AbstractP2pMessage;
import org.developerworld.frameworks.yixin.message.p2p.CardP2pMessage;
import org.developerworld.frameworks.yixin.message.p2p.ImageP2pMessage;
import org.developerworld.frameworks.yixin.message.p2p.LinkP2pMessage;
import org.developerworld.frameworks.yixin.message.p2p.NewsP2pMessage;
import org.developerworld.frameworks.yixin.message.p2p.NewsP2pMessage.Article;
import org.developerworld.frameworks.yixin.message.p2p.TextP2pMessage;
import org.developerworld.frameworks.yixin.message.p2p.VideoP2pMessage;
import org.developerworld.frameworks.yixin.message.p2p.VoiceP2pMessage;

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
public class P2pMessageConverter {

	private final static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 转换为json
	 * 
	 * @param P2pMessage
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String convertToJson(P2pMessage P2pMessage)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map msg = new LinkedHashMap();
		if (P2pMessage instanceof AbstractP2pMessage) {
			AbstractP2pMessage _msg = (AbstractP2pMessage) P2pMessage;
			msg.put("touser", _msg.getToUser());
		}
		if (P2pMessage instanceof TextP2pMessage) {
			msg.put("msgtype", MsgType.TEXT.toString());
			TextP2pMessage _msg = (TextP2pMessage) P2pMessage;
			Map text = new LinkedHashMap();
			text.put("content", _msg.getContent());
			msg.put("text", text);
		}
		else if (P2pMessage instanceof ImageP2pMessage) {
			msg.put("msgtype", MsgType.IMAGE.toString());
			ImageP2pMessage _msg = (ImageP2pMessage) P2pMessage;
			Map image = new LinkedHashMap();
			image.put("media_id", _msg.getMediaId());
			msg.put("image", image);
		}
		else if (P2pMessage instanceof VoiceP2pMessage) {
			msg.put("msgtype", MsgType.VOICE.toString());
			VoiceP2pMessage _msg = (VoiceP2pMessage) P2pMessage;
			Map voice = new LinkedHashMap();
			voice.put("media_id", _msg.getMediaId());
			msg.put("voice", voice);
		}
		else if (P2pMessage instanceof VideoP2pMessage) {
			msg.put("msgtype", MsgType.VIDEO.toString());
			VideoP2pMessage _msg = (VideoP2pMessage) P2pMessage;
			Map video = new LinkedHashMap();
			video.put("media_id", _msg.getMediaId());
			video.put("thumb_media_id", _msg.getThumbMediaId());
			msg.put("video", video);
		}
		else if (P2pMessage instanceof LinkP2pMessage) {
			msg.put("msgtype", MsgType.LINK.toString());
			LinkP2pMessage _msg = (LinkP2pMessage) P2pMessage;
			Map link = new LinkedHashMap();
			link.put("title", _msg.getTitle());
			link.put("description", _msg.getDescription());
			link.put("url", _msg.getUrl());
			msg.put("link", link);
		}
		else if (P2pMessage instanceof NewsP2pMessage) {
			msg.put("msgtype", MsgType.NEWS.toString());
			NewsP2pMessage _msg = (NewsP2pMessage) P2pMessage;
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
		else if (P2pMessage instanceof CardP2pMessage) {
			msg.put("msgtype", MsgType.CARD.toString());
			CardP2pMessage _msg = (CardP2pMessage) P2pMessage;
			Map card = new LinkedHashMap();
			card.put("card", _msg.getCard());
			msg.put("card", card);
		}
		return objectMapper.writeValueAsString(msg);
	}
}
