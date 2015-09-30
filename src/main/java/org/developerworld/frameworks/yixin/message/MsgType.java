package org.developerworld.frameworks.yixin.message;

/**
 * 信息类型
 * @author Roy Huang
 * @version 20140312
 *
 */
public enum MsgType {
	
	TEXT("text"),IMAGE("image"),VOICE("voice"),VIDEO("video"),NEWS("news"),MUSIC("music"),LOCATION("location"),LINK("link"),EVENT("event"),CARD("card");
	
	private String value;
	
	private MsgType(String value){
		this.value=value;
	}
	
	@Override
	public String toString(){
		return this.value;
	}

}
