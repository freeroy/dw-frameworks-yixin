package org.developerworld.frameworks.yixin.message.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.developerworld.frameworks.yixin.message.MassMessage;
import org.developerworld.frameworks.yixin.message.MsgType;
import org.developerworld.frameworks.yixin.message.mass.CardMassMessage;
import org.developerworld.frameworks.yixin.message.mass.NewsMassMessage;
import org.developerworld.frameworks.yixin.message.mass.NewsMassMessage.Article;
import org.developerworld.frameworks.yixin.message.mass.TextMassMessage;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 群发信息转换器
 * 
 * @author Roy Huang
 * @version 20140310
 * 
 */
public class MassMessageConverter {

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
	public String convertToJson(MassMessage message)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map msg = new LinkedHashMap();
		if (message instanceof TextMassMessage) {
			msg.put("msgtype", MsgType.TEXT.toString());
			TextMassMessage _msg = (TextMassMessage) message;
			Map text = new LinkedHashMap();
			text.put("content", _msg.getContent());
			msg.put("text", text);
		}
		if (message instanceof CardMassMessage) {
			msg.put("msgtype", MsgType.CARD.toString());
			CardMassMessage _msg = (CardMassMessage) message;
			Map card = new LinkedHashMap();
			card.put("card", _msg.getCard());
			msg.put("card", card);
		}
		if (message instanceof NewsMassMessage) {
			msg.put("msgtype", MsgType.NEWS.toString());
			NewsMassMessage _msg = (NewsMassMessage) message;
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
